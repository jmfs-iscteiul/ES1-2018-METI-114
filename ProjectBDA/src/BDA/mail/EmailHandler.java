package BDA.mail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.codec.DecodeMonitor;
import org.apache.james.mime4j.message.DefaultBodyDescriptorBuilder;
import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.james.mime4j.parser.MimeStreamParser;
import org.apache.james.mime4j.stream.BodyDescriptorBuilder;
import org.apache.james.mime4j.stream.MimeConfig;
import org.apache.james.mime4j.stream.MimeConfig.Builder;

import tech.blueglacier.email.Attachment;
import tech.blueglacier.email.Email;
import tech.blueglacier.parser.CustomContentHandler;

/**
 * Classe encarregue da funcionalidade de email. Contém um construtor que recebe as informações de email do utilizador e prepara a sessão de email.
 * Existem 2 funções principais, uma para o envio de emails e outra para a receção destes.
 * 
 * @author darsa-iscteiul
 *
 */
public class EmailHandler {

	private String hostEnvio = "smtp-mail.outlook.com";			//Servidor de envio de emails
	private String hostRececao = "imap-mail.outlook.com";		//Servidor de receção de emails
	private String user;										//email do user
	private String password;									//Password do user

	private Session mailSession;								//Sessão de email
	private String diretoria;									//diretoria onde gravar anexos de email


	/**
	 * Construtor da classe email. Recebe as informações e prepara a sessão para o envio e a receção de emails.
	 * 
	 * @param user Email do utilizador
	 * @param password Password do utilizador
	 */
	public EmailHandler(String user, String password) {
		this.user = user;
		this.password = password; 

		try {
			String temp = EmailHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			temp = URLDecoder.decode(temp, "UTF-8");
			diretoria = temp.substring(1,temp.lastIndexOf("/") );
			diretoria += File.separator + "temp";
		} catch (URISyntaxException | UnsupportedEncodingException e) {
			System.err.println("Não é possível obter a diretoria onde guardar os ficheiros");
		}

		File directory = new File(diretoria);
		if(!directory.exists())
			directory.mkdir();

		Properties props = new Properties(); //Propriedades para a sessão de Email

		//Propriedades de envio
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", hostEnvio);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port.auth", "true");

		//Propriedades de Receção
		props.put("mail.store.protocol", "imaps");
		props.put("mail.imaps.host", hostRececao);
		props.put("mail.imaps.port", "993");

		//Criar Sessão de Email
		mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
	}


	/**
	 * Função que efetua o envio de um email.
	 * 
	 * @param assunto Assunto do email
	 * @param texto Corpo de texto do email
	 * @param to Endereço de destino do email
	 * @param cc Endereço cc do email
	 * @param bcc Endereço bcc do email
	 * @param attachment anexo a enviar
	 * @return boolean que representa envio correto do email
	 */
	public boolean enviarEmail(String assunto, String texto, InternetAddress to, InternetAddress cc, InternetAddress bcc, File attachment) {

		Message msg = new MimeMessage(mailSession);														//Mensagem a enviar

		try {
			Transport transport = mailSession.getTransport();
			msg.setFrom(new InternetAddress(user));														//Remetente
			InternetAddress [] toAddress = {to};
			msg.setRecipients(Message.RecipientType.TO, toAddress);										//Destinos do email
			msg.setSubject(assunto);																	//Assunto do email
			msg.setSentDate(new Date());																//Data de envio

			if(cc != null) {
				InternetAddress [] ccAddress = {cc};
				msg.setRecipients(Message.RecipientType.CC, ccAddress); 								//Destinos cc do email
			}

			if(bcc != null)	{
				InternetAddress [] bccAddress = {bcc};
				msg.setRecipients(Message.RecipientType.BCC, bccAddress);								//Destinos bcc do email
			}

			Multipart multipart = new MimeMultipart();

			MimeBodyPart textpart = new MimeBodyPart();
			textpart.setText(texto);
			multipart.addBodyPart(textpart);															//Texto a enviar

			if(attachment != null) {
				MimeBodyPart attachmentpart = new MimeBodyPart();
				DataSource ds = new FileDataSource(attachment);
				attachmentpart.setDataHandler(new DataHandler(ds));
				attachmentpart.setFileName(attachment.getName());
				multipart.addBodyPart(attachmentpart);													//Anexo a enviar
			}

			msg.setContent(multipart);
			msg.saveChanges();

			transport.connect(hostEnvio, user, password); 												//Conexão para envio de email
			transport.sendMessage(msg, msg.getAllRecipients());	
			transport.close();

			System.out.println("Mensagem Enviada");
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * Função que recebe email da caixa de correio do email do utilizador e filtra os mais recentes.
	 * 
	 * @return	Lista de todos os emails recebidos que se apliquem ao filtro
	 */
	public List<MailInfoStruct> receberEmail() {
		List<MailInfoStruct> emails = new ArrayList<>();													//Lista de emails recebidos

		try {
			Store store = mailSession.getStore("imaps");
			store.connect(hostRececao, user, password);														//Conectar ao endereço de email
			System.out.println("Conectado");

			Folder inbox = store.getFolder("inbox");														//Pasta do email em que os emails recebidos se encontram
			inbox.open(Folder.READ_ONLY);

			Message[] messages = inbox.getMessages();														//Vai buscar os emails na caixa de email

			for(Message message : messages) {
				ContentHandler contentHandler = new CustomContentHandler();

				Builder builder = new MimeConfig.Builder().setMaxLineLen(-1).setMaxHeaderCount(-1);			//Fazer setup ao Parser de email
				MimeConfig mime4jParserConfig = builder.build();
				BodyDescriptorBuilder bodyDescriptorBuilder = new DefaultBodyDescriptorBuilder();
				MimeStreamParser mime4jParser = new MimeStreamParser(mime4jParserConfig, DecodeMonitor.SILENT, bodyDescriptorBuilder);
				mime4jParser.setContentDecoding(true);
				mime4jParser.setContentHandler(contentHandler);												//Fazer setup ao Parser de email

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				message.writeTo(out);
				byte [] t = out.toByteArray();
				InputStream mailIn = new ByteArrayInputStream(t);											//Obter InputStream para o email

				try {
					mime4jParser.parse(mailIn);																//Fazer parse ao email
				} catch (EmptyStackException e) {
					System.err.println("Erro no parse");
				}

				Email email = ((CustomContentHandler) contentHandler).getEmail();

				Attachment at;
				if(email.getHTMLEmailBody() != null) {														//Se o email for em HTML
					at = email.getHTMLEmailBody();
				} else {																					//Se o email for plain text
					at = email.getPlainTextEmailBody();
				}

				String texto = IOUtils.toString(at.getIs(), Charset.forName("UTF-8"));						//Corpo de texto do email

				List<Attachment> attachments =  email.getAttachments();										//Anexos do email
				List<File> anexos = new ArrayList<>();														//Lista de anexos de email na classe File

				for(Attachment attachment : attachments) {													//Gravar anexos no computador e adiciona-los a lista de anexos
					File anexo = new File(diretoria + File.separator + attachment.getAttachmentName());
					FileUtils.copyInputStreamToFile(attachment.getIs(), anexo);
					anexos.add(anexo);
				}

				if(anexos.size() > 0) {																		//Email com anexos
					emails.add(new MailInfoStruct(message.getReceivedDate(), email.getFromEmailHeaderValue(), texto, email.getEmailSubject(), email.getToEmailHeaderValue(), email.getCCEmailHeaderValue(), anexos));
				} else {																					//Email sem anexos
					emails.add(new MailInfoStruct(message.getReceivedDate(), email.getFromEmailHeaderValue(), texto, email.getEmailSubject(), email.getToEmailHeaderValue(), email.getCCEmailHeaderValue()));
				}
				mime4jParser.stop();

			}
			store.close();																					//Fechar a store
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MimeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return emails;
	}


	/**
	 * Devolve a string que contém a diretoria a gravar os emails.
	 * 
	 * @return Diretoria a gravar os emails.
	 */
	public String getDiretoria() {
		return diretoria;
	}


	/**
	 * Devolve o servidor utilizado para enviar os emails.
	 * 
	 * @return Servidor usado para envio.
	 */
	public String getHostEnvio() {
		return hostEnvio;
	}


	/**
	 * Devolve o servidor utilizado para receber os emails.
	 * 
	 * @return Servidor usado para receção.
	 */
	public String getHostRececao() {
		return hostRececao;
	}


	/**
	 * Devolve o email do utilizador
	 * 
	 * @return Email do utilizador
	 */
	public String getUser() {
		return user;
	}
}
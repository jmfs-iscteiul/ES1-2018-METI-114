package mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;

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
		System.out.println("Diretoria: " + directory.getAbsolutePath());

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
	 * @param to Endereços de destino do email
	 * @param cc Endereços cc do email
	 * @param bcc Endereços bcc do email
	 */
	public void enviarEmail(String assunto, String texto, InternetAddress[] to, InternetAddress [] cc, InternetAddress [] bcc) {

		Message msg = new MimeMessage(mailSession);														//Mensagem a enviar

		try {
			Transport transport = mailSession.getTransport();
			msg.setFrom(new InternetAddress(user));														//Remetente
			msg.setRecipients(Message.RecipientType.TO, to);											//Destinos do email
			msg.setSubject(assunto);																	//Assunto do email
			msg.setSentDate(new Date());																//Data de envio
			msg.setText(texto);																			//Texto a enviar

			if(cc != null)	msg.setRecipients(Message.RecipientType.CC, cc); 							//Destinos cc do email

			if(bcc != null)	msg.setRecipients(Message.RecipientType.BCC, bcc);							//Destinos bcc do email

			msg.saveChanges();

			transport.connect(hostEnvio, user, password); //Conexão para envio de email

			transport.sendMessage(msg, msg.getAllRecipients());	

			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		System.out.println("Mensagem Enviada");
	}


	/*public List<MailInfoStruct> receberEmail() {
		List<MailInfoStruct> emails = new ArrayList<>();

		try (Store store = mailSession.getStore("imaps")){
			System.out.println(user);
			store.connect(hostRececao, user, password);
			System.out.println("Conectado");

			Folder inbox = store.getFolder("inbox");													//Pasta do email em que os emails recebidos se encontram
			inbox.open(Folder.READ_ONLY);

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -24);															//Alterar para puder variar a data de filtro
			Date oneDayAgo = cal.getTime();

			Message[] messages = inbox.search(new ReceivedDateTerm(ComparisonTerm.GE, oneDayAgo));		//Vai buscar emails consoante o filtro de tempo
			System.out.println(messages.length);

			for(Message message : messages) {
				//				String contentType = message.getContentType();
				System.out.println("--------" + message.getSubject() + "--------");

				if(message.isMimeType("multipart/*")) {
//					emails.add(obterMensagemMultipart(message));

					List<File> anexos = new ArrayList<>();
					String texto = obterMensagemMultipartTeste((Multipart)message.getContent(), anexos);

					if(anexos.size() == 0)
						emails.add(new MailInfoStruct(message.getReceivedDate(), InternetAddress.toString(message.getFrom()), 
								texto, message.getSubject(), juntarEmails(message.getRecipients(Message.RecipientType.TO)), juntarEmails(message.getRecipients(Message.RecipientType.CC))));
					else
						emails.add(new MailInfoStruct(message.getReceivedDate(), InternetAddress.toString(message.getFrom()), 
								texto, message.getSubject(), juntarEmails(message.getRecipients(Message.RecipientType.TO)), juntarEmails(message.getRecipients(Message.RecipientType.CC)), anexos));

					//				} else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
				} else if (message.isMimeType("text/*")) {
					Object content = message.getContent();
					System.out.println("Mensagem simples");
					if(content != null)
						emails.add(new MailInfoStruct(message.getReceivedDate(), InternetAddress.toString(message.getFrom()), 
								message.getContent().toString(), message.getSubject(), juntarEmails(message.getRecipients(Message.RecipientType.TO)), juntarEmails(message.getRecipients(Message.RecipientType.CC))));
				} else {
					System.out.println("Não pertencem a um dos tipos de email compativeis: " + message.getContentType());
				}
				System.out.println("--------" + message.getSubject() + "--------");
			}
		}  catch (MessagingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return emails;
	}*/

	/**
	 * Função que recebe email da caixa de correio do email do utilizador e filtra os mais recentes.
	 * 
	 * @return	Lista de todos os emails recebidos que se apliquem ao filtro
	 */
	public List<MailInfoStruct> receberEmail() {
		List<MailInfoStruct> emails = new ArrayList<>();													//Lista de emails recebidos

		try {
			Store store = mailSession.getStore("imaps");
			System.out.println(user);
			store.connect(hostRececao, user, password);														//Conectar ao endereço de email
			System.out.println("Conectado");

			Folder inbox = store.getFolder("inbox");														//Pasta do email em que os emails recebidos se encontram
			inbox.open(Folder.READ_ONLY);

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -24);																//Alterar para aplicar filtros dependendo da escolha
			Date oneDayAgo = cal.getTime();

			Message[] messages = inbox.search(new ReceivedDateTerm(ComparisonTerm.GE, oneDayAgo));			//Vai buscar emails consoante o filtro
			System.out.println(messages.length);

			for(Message message : messages) {
				ContentHandler contentHandler = new CustomContentHandler();

				Builder builder = new MimeConfig.Builder().setMaxLineLen(-1).setMaxHeaderCount(-1);			//Fazer setup ao Parser de email
				MimeConfig mime4jParserConfig = builder.build();
				BodyDescriptorBuilder bodyDescriptorBuilder = new DefaultBodyDescriptorBuilder();
				MimeStreamParser mime4jParser = new MimeStreamParser(mime4jParserConfig, DecodeMonitor.SILENT, bodyDescriptorBuilder);
				mime4jParser.setContentDecoding(true);
				mime4jParser.setContentHandler(contentHandler);												//Fazer setup ao Parser de email

				/*ByteArrayOutputStream out2 = new ByteArrayOutputStream();
				message.writeTo(out2);
				InputStream mailIn = new ByteArrayInputStream(out2.toByteArray());*/

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				message.writeTo(out);
				String temp = new String(out.toByteArray(), Charset.forName("UTF-8"));
				InputStream mailIn = IOUtils.toInputStream(temp, Charset.forName("UTF-8"));					//Obter InputStream para o email

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
			store.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MimeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return emails;
	}


	/*private String obterMensagemMultipartTeste (Multipart parteInicial, List<File> anexos) throws MessagingException, IOException {
		String texto = "";
		int numeroPartes = parteInicial.getCount();

		for (int partCount = 0; partCount < numeroPartes; partCount++) {
			MimeBodyPart part = (MimeBodyPart) parteInicial.getBodyPart(partCount);

			if(part.getContent() instanceof Multipart) {
				texto += obterMensagemMultipartTeste((Multipart)part.getContent(), anexos);
			} else if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				String fileName = part.getFileName();
				//				part.saveFile(diretoria + File.separator + fileName);
				anexos.add(new File(diretoria + File.separator + fileName));
			} else if (part.getDisposition() == null) {
				System.out.println("A disposição retornou null");
				DataHandler data = part.getDataHandler();
				InputStream stream = data.getInputStream();
				//				texto += convertStreamToString(stream);

				try {
					texto += IOUtils.toString(MimeUtility.decode(stream, part.getEncoding()), "UTF-8");
				} catch (DecodingException | NullPointerException e) {
					System.err.println("Erro no decoding");
				}

				System.out.println("Teste: " + part.getContent().toString());

				System.out.println("Fim da disposição null");
			} else {
				if(part.getContent() instanceof BASE64DecoderStream) {
					BASE64DecoderStream stream = (BASE64DecoderStream) part.getContent();
					try {
						//						texto += IOUtils.toString(MimeUtility.decode(stream, part.getEncoding()), "UTF-8");
						byte [] encoded = IOUtils.toByteArray(stream);
						byte [] decoded = Base64.getMimeDecoder().decode(encoded);
						System.out.println("Decoded Base 64:");

						String result = new String(decoded, "UTF-8");
						System.out.println(result);
						texto += result;
					}
					catch (DecodingException e) {
						System.err.println("Erro no decoding");
					}
				} else {
					System.out.println("Parte do texto");
					System.out.println(part.getContent().toString());
					texto += part.getContent().toString();
				}
			}
		}

		return texto;
	}*/

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
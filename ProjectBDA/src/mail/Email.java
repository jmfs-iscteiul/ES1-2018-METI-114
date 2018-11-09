package mail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;

/**
 * Classe encarregue da funcionalidade de email. Contém um construtor que recebe as informações de email do utilizador e prepara a sessão de email.
 * Existem também 2 funções, uma para o envio de emails e outra para a receção destes. 
 * 
 * @author darsa-iscteiul
 *
 */
public class Email {

	private String hostEnvio;
	private String hostRececao;
	private String user;
	private String password;

	private Session mailSession;
	private static String diretoria;		//diretoria onde gravar anexos de email

	/**
	 * Construtor da classe email. Recebe as informações e prepara a sessão para o envio e a receção de emails.
	 * 
	 * @param hostEnvio Endereço do servidor de envio SMTP
	 * @param hostRececao Endereço do servidor de receção IMAP
	 * @param user Email do utilizador
	 * @param password Password do utilizador
	 */
	public Email(String hostEnvio, String hostRececao, String user, String password) {
		this.hostEnvio = hostEnvio;
		this.hostRececao = hostRececao;
		this.user = user;
		this.password = password;
		
		try {
			String temp = Email.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			temp = URLDecoder.decode(temp, "UTF-8");
			diretoria = temp.substring(1,temp.lastIndexOf("/") );
			diretoria += File.separator + "temp";
		} catch (URISyntaxException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		File directory = new File(diretoria);
		if(!directory.exists())
			directory.mkdir();
		System.out.println(directory.getAbsolutePath());

		Properties props = new Properties(); //Propriedades para a sessão de Email

		//Propriedades de envio
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
	 * @param enderecos Endereços de destino do email
	 */
	public void enviarEmail(String assunto, String texto, InternetAddress[] enderecos) {

		Message msg = new MimeMessage(mailSession);						//Mensagem a enviar

		try {
			msg.setFrom(new InternetAddress(user));						//Remetente
			msg.setRecipients(Message.RecipientType.TO, enderecos);		//Destinos do email
			msg.setSubject(assunto);									//Assunto do email
			msg.setSentDate(new Date());								//Data de envio
			msg.setText(texto);											//Texto a enviar

			Transport transport = mailSession.getTransport();
			transport.connect(hostEnvio, user, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();


		} catch (MessagingException e) {
			e.printStackTrace();
		}

		System.out.println("Mensagem Enviada");
	}


	/**
	 * Função que recebe email da caixa de correio do email do utilizador e filtra os mais recentes.
	 */
	public List<MailInfoStruct> receberEmail() {
		List<MailInfoStruct> emails = new ArrayList<>();
		
		try (Store store = mailSession.getStore("imaps")){
			store.connect(hostRececao, user, password);

			Folder inbox = store.getFolder("inbox");													//Pasta do email em que os emails recebidos se encontram
			inbox.open(Folder.READ_ONLY);

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -24);															//Alterar para puder variar a data de filtro
			Date oneDayAgo = cal.getTime();

			Message[] messages = inbox.search(new ReceivedDateTerm(ComparisonTerm.GE, oneDayAgo));		//Vai buscar emails consoante o filtro de tempo
			System.out.println(messages.length);
			
			for(Message message : messages) {
				/*System.out.println("---------------------------------");
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Date: " + message.getReceivedDate());
				System.out.println("Text: " + message.getContent()); */

				String contentType = message.getContentType();

				if(contentType.contains("multipart")) {
					emails.add(getMultipartMessage(message));
				} else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
					Object content = message.getContent();
					if(content != null)
						emails.add(new MailInfoStruct(message.getReceivedDate().toString(), InternetAddress.toString(message.getFrom()), 
								message.getContent().toString(), message.getSubject(), juntarEmails(message.getRecipients(Message.RecipientType.TO)), juntarEmails(message.getRecipients(Message.RecipientType.CC))));
				} else {
					System.out.println("Não pertencem a um dos tipos de email compativeis: " + contentType);
				}
			}
		}  catch (MessagingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return emails;
	}

	private MailInfoStruct getMultipartMessage(Message message) throws MessagingException, IOException{
		// content may contain attachments
		Multipart multiPart = (Multipart) message.getContent();
		int numberOfParts = multiPart.getCount();
		
		List<File> attachments = null;
		String texto = "";
		
		for (int partCount = 0; partCount < numberOfParts; partCount++) {
			MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
			if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				// this part is attachment
				String fileName = part.getFileName();
//				attachFiles += fileName + ", ";
				part.saveFile(diretoria + File.separator + fileName);
				if(attachments == null)
					attachments = new ArrayList<>();;
				attachments.add(new File(diretoria + File.separator + fileName));
			} else {
				// this part may be the message content
//				messageContent = part.getContent().toString();
				texto += part.getContent().toString();
			}
		}

		/*if (attachFiles.length() > 1) {
			attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
		}*/
		
		if(attachments == null)
			return new MailInfoStruct(message.getReceivedDate().toString(), InternetAddress.toString(message.getFrom()), 
					texto, message.getSubject(), juntarEmails(message.getRecipients(Message.RecipientType.TO)), juntarEmails(message.getRecipients(Message.RecipientType.CC)));
		else
			return new MailInfoStruct(message.getReceivedDate().toString(), InternetAddress.toString(message.getFrom()), 
					texto, message.getSubject(), juntarEmails(message.getRecipients(Message.RecipientType.TO)), juntarEmails(message.getRecipients(Message.RecipientType.CC)), attachments);
	}
	
	private String juntarEmails(Address[] emails) {
		if(emails == null) {
			System.out.println("Lista de emails era null");
			return null;
		}
		if(emails.length == 0)
			return "";
		String f = emails[0].toString();
		for(int i = 1; i <  emails.length - 1; i++) {
			f += ", " + emails[i];
		}
		return f;
	}

}
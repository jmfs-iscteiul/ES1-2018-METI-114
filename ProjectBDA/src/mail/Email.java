package mail;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;

public class Email {

	private String hostEnvio;
	private String hostRececao;
	private String user;
	private String password;

	private Session mailSession;

	public Email(String hostEnvio, String hostRececao, String user, String password) {
		this.hostEnvio = hostEnvio;
		this.hostRececao = hostRececao;
		this.user = user;
		this.password = password;

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


	public void enviarEmail(String assunto, String texto, InternetAddress[] endereços) {

		Message msg = new MimeMessage(mailSession);

		try {
			msg.setFrom(new InternetAddress(user));
			msg.setRecipients(Message.RecipientType.TO, endereços);
			msg.setSubject(assunto);
			msg.setSentDate(new Date());
			msg.setText(texto);

			Transport transport = mailSession.getTransport();
			transport.connect(hostEnvio, user, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();


		} catch (MessagingException e) {
			e.printStackTrace();
		}

		System.out.println("Mensagem Enviada");
	}


	public void receberEmail() {
		try (Store store = mailSession.getStore("imaps")){
			store.connect(hostRececao, user, password);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -24);
			Date oneDayAgo = cal.getTime();

			Message[] messages = inbox.search(new ReceivedDateTerm(ComparisonTerm.GT, oneDayAgo));
			System.out.println(messages.length);

			for(Message message : messages) { //Temporário
				System.out.println("---------------------------------");
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Date: " + message.getReceivedDate());
				System.out.println("Text: " + message.getContent().toString());
			}
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

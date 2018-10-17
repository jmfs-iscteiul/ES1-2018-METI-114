package mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	private String host;
	private String user;
	private String password;
	
	private Session mailSession;
	
	public Email(String host, String user, String password) {
		this.host = host;
		this.user = user;
		this.password = password;
		
		Properties props = new Properties(); //Propriedades para a sessão de Email
		
		//Propriedades de envio
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port.auth", "true");
		
		//Propriedades de Receção
		props.put("mail.pop3.host", host);
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");
		
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
			
			//Falta adicionar código para ceonseguir enviar anexos
			
			Transport transport = mailSession.getTransport();
			transport.connect(host, user, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Mensagem Enviada");
	}
	
	
	
	public void receberEmail() {
		
	}

}

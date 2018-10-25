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
	public void receberEmail() {
		try (Store store = mailSession.getStore("imaps")){
			store.connect(hostRececao, user, password);

			Folder inbox = store.getFolder("INBOX");													//Pasta do email em que os emails recebidos se encontram
			inbox.open(Folder.READ_ONLY);

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -24);		//Alterar para puder variar a data de filtro
			Date oneDayAgo = cal.getTime();

			Message[] messages = inbox.search(new ReceivedDateTerm(ComparisonTerm.GT, oneDayAgo));		//Vai buscar emails consoante o filtro de tempo
			System.out.println(messages.length);

			for(Message message : messages) { //Temporário
				System.out.println("---------------------------------");
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Date: " + message.getReceivedDate());
				System.out.println("Text: " + message.getContent());
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

package mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
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
import javax.mail.internet.MimeUtility;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;

import org.apache.commons.io.IOUtils;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.DecodingException;

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
	 * @param user Email do utilizador
	 * @param password Password do utilizador
	 */
	public Email(String user, String password) {
		//		Login l = new Login();
		this.hostEnvio = "smtp-mail.outlook.com";
		this.hostRececao = "imap-mail.outlook.com";
		this.user = user;
		this.password = password; 

		try {
			String temp = Email.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
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
	 * 
	 * @return Lista de emails recebidos pelo Utilizador em formato MailInfoStruct
	 */
	public List<MailInfoStruct> receberEmail() {
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
	}


/*	private MailInfoStruct obterMensagemMultipart(Message message) throws MessagingException, IOException{
		// content may contain attachments
		System.out.println("Contem anexos");
		Multipart multiPart = (Multipart) message.getContent();
		int numberOfParts = multiPart.getCount();

		List<File> attachments = null;
		String texto = "";

		for (int partCount = 0; partCount < numberOfParts; partCount++) {
			MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
			if(part.getContent() instanceof Multipart) {
				System.out.println("Afinal há partes");
			}
			if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				// this part is attachment
				String fileName = part.getFileName();
				//				attachFiles += fileName + ", ";
				//				part.saveFile(diretoria + File.separator + fileName);
				if(attachments == null)
					attachments = new ArrayList<>();

				attachments.add(new File(diretoria + File.separator + fileName));
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



				System.out.println("Fim da disposição null");
			} else {
				System.out.println("Parte do texto");
				//				if(part.getContent() instanceof InputStream) { //Por resolver
				//					BASE64DecoderStream stream = (BASE64DecoderStream)part.getContent();
				//					int b = stream.read();
				//					List<Integer> bytes = new ArrayList<>();
				//					while(b != -1) {
				//						bytes.add(b);
				//						b = stream.read();
				//					}
				//					
				//					
				//					
				//					texto += stream.toString();

				//				InputStream stream = (InputStream) part.getContent();
				//					//					byte[] encodeBase64 = Base64.getDecoder().decode(streambytes);
				//					byte[] encodebase64 = IOUtils.toByteArray(stream);
				//					System.out.println("Base 64:");
				//					System.out.println(IOUtils.toString(stream, "UTF-8"));
				//					try {
				//						byte[] decoded = Base64.getDecoder().decode(encodebase64);
				//						texto += IOUtils.toString(decoded, "UTF-8");
				//					} catch(IllegalArgumentException e) {
				//						System.err.println("Erro na base 64");
				//					}

				//					texto += MimeUtility.decode(stream, part.getEncoding());
				//				try {
				//					texto += IOUtils.toString(MimeUtility.decode(stream, part.getEncoding()), "UTF-8");
				//				}
				//				catch (DecodingException e) {
				//					System.err.println("Erro no decoding");
				//				}
				//				} else {
				//					System.out.println(part.getContent().toString());
				//					texto += part.getContent().toString();
				//				}
			}
		}

		if(attachments == null)
			return new MailInfoStruct(message.getReceivedDate().toString(), InternetAddress.toString(message.getFrom()), 
					texto, message.getSubject(), juntarEmails(message.getRecipients(Message.RecipientType.TO)), juntarEmails(message.getRecipients(Message.RecipientType.CC)));
		else
			return new MailInfoStruct(message.getReceivedDate().toString(), InternetAddress.toString(message.getFrom()), 
					texto, message.getSubject(), juntarEmails(message.getRecipients(Message.RecipientType.TO)), juntarEmails(message.getRecipients(Message.RecipientType.CC)), attachments);
	}*/

	private String obterMensagemMultipartTeste (Multipart parteInicial, List<File> anexos) throws MessagingException, IOException {
		String texto = "";
		int numeroPartes = parteInicial.getCount();

		for (int partCount = 0; partCount < numeroPartes; partCount++) {
			MimeBodyPart part = (MimeBodyPart) parteInicial.getBodyPart(partCount);

			if(part.getContent() instanceof Multipart) {
				texto += obterMensagemMultipartTeste((Multipart)part.getContent(), anexos);
			} else if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
//				String fileName = part.getFileName();
				String fileName = MimeUtility.decodeText(part.getFileName());
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
				/*if(part.getContent() instanceof BASE64DecoderStream) {
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
				} else {*/
					System.out.println("Parte do texto");
					System.out.println(part.getContent().toString());
					texto += part.getContent().toString();
//				}
			}
		}

		return texto;
	}

	/**
	 * Função que serve para converter uma InputStream recebida por email para uma String com o texto recebido
	 * 
	 * @param stream InputStream a converter
	 * @return String que resulta da conversão da InputStream
	 */
	private String convertStreamToString(InputStream stream) {
		Scanner s = new Scanner(stream, "UTF-8");
		s.useDelimiter("\\A");
		try {
			return s.hasNext() ? s.next() : "";
		} finally {
			s.close();
		}
	}

	/**
	 * Função que serve para converter uma lista de emails na classe Address para uma string
	 * 
	 * @param emails Lista de emails
	 * @return String com todos os emails na lista de emails
	 */
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
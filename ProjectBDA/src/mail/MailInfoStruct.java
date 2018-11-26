package mail;

import java.io.File;
import java.util.Date;
import java.util.List;

import common.standardInfoStruct;


/**
 * Classe de mensagem comum para todos os serviços direcionada em especifico para o email, para ser compativel com algumas funcionalidades extra deste.
 * 
 * @author darsa-iscteiul
 *
 */
public class MailInfoStruct extends standardInfoStruct {

	private String subject;
	private String to;
	private String cc;

	private List<File> attachments = null;

	/**
	 * Construtor para um email sem anexos.
	 * 
	 * @param date Data do email.
	 * @param author Autor do email.
	 * @param text Corpo de texto do email.
	 * @param subject Assunto do email.
	 * @param to Destino do email.
	 * @param cc Destino cc do email.
	 */
	public MailInfoStruct(Date date, String author, String text, String subject, String to, String cc) {
		super(date, author, text);
		this.subject = subject;
		this.to = to;
		this.cc = cc;
	}

	/**
	 * Construtor para um email com anexos.
	 * 
	 * @param date Data do email.
	 * @param author Autor do email.
	 * @param text Corpo de texto do email.
	 * @param subject Assunto do email.
	 * @param to Destino do email.
	 * @param cc Destino cc do email.
	 * @param attachments Lista de anexos do email.
	 */
	public MailInfoStruct(Date date, String author, String text, String subject, String to, String cc, List<File> attachments) {
		super(date, author, text);
		this.subject = subject;
		this.to = to;
		this.cc = cc;
		this.attachments = attachments;
	}

	/**
	 * Devolve o assunto do email.
	 * 
	 * @return String com assunto do email.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Devolve os destinos do email
	 * 
	 * @return String com destinos do email.
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Devolve os destinos cc do email.
	 * 
	 * @return String com destinos cc do email.
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * Devolve a lista com todos os anexos de email.
	 * 
	 * @return List com todos os anexos do email.
	 */
	public List<File> getAttachments() {
		return attachments;
	}
	
	
	/**
	 * String que representa a classe.
	 * 
	 * @return String que representa o toString da classe
	 */
	@Override
	public String toString() {
		return getDate().toString() + " - " +  subject;
	}
}

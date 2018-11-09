package mail;

import java.io.File;
import java.util.List;

import common.standardInfoStruct;


/**
 * 
 * @author darsa-iscteiul
 *
 */
public class MailInfoStruct extends standardInfoStruct {
	
	private String subject;
	private String to;
	private String cc;
	
	private List<File> attachments = null;
	
	public MailInfoStruct(String date, String author, String text, String subject, String to, String cc) {
		super(date, author, text);
		this.subject = subject;
		this.to = to;
		this.cc = cc;
	}

	public MailInfoStruct(String date, String author, String text, String subject, String to, String cc, List<File> attachments) {
		super(date, author, text);
		this.to = to;
		this.cc = cc;
		this.attachments = attachments;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getTo() {
		return to;
	}
	
	public String getCc() {
		return cc;
	}
	
	public List<File> getAttachments() {
		return attachments;
	}
}

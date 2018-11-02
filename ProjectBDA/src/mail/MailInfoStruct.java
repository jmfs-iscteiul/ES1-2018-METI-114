package mail;

import java.io.File;
import java.util.List;

import commun.standardInfoStruct;


/**
 * 
 * @author darsa-iscteiul
 *
 */
public class MailInfoStruct extends standardInfoStruct {
	
	private String to;
	private String cc;
	
	private List<File> attachments;
	
	public MailInfoStruct(String date, String author, String title, String to, String cc) {
		super(date, author, title);
		this.to = to;
		this.cc = cc;
	}

	public MailInfoStruct(String date, String author, String title, String to, String cc, List<File> attachments) {
		super(date, author, title);
		this.to = to;
		this.cc = cc;
		this.attachments = attachments;
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

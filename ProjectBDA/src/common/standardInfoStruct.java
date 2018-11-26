package common;

import java.util.Date;

public class standardInfoStruct implements Comparable<standardInfoStruct>{
	
	private Date date;
	private String author;
	private String text;
	
	public standardInfoStruct(Date d, String a, String t) {
		this.date = d;
		this.author = a;
		this.text = t;
	}

	public void setSender(String author) {
		this.author = author;
	}

	public void setContent(String title) {
		this.text = title;
	}
	
	public Date getDate() {
		return date;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return text;
	}
	
	@Override
	public String toString() {
		return date + " - " + text;
	}

	@Override
	public int compareTo(standardInfoStruct other) {
		if(this.getDate().compareTo(other.getDate()) < 0)
			return 1;
		if(this.getDate().compareTo(other.getDate()) > 0)
			return -1;
		return 0;
			
		
	}
	
	
}

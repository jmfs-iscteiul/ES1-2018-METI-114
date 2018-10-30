package common;

public class standardInfoStruct {
	
	private String date;
	private String author;
	private String title;
	
	public standardInfoStruct(String d, String a, String t) {
		this.date = d;
		this.author = a;
		this.title = t;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSender(String author) {
		this.author = author;
	}

	public void setContent(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}
}

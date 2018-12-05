package common;

import java.util.Date;

/**
 * Classe que define a estrutura das notificações.
 * 
 * @author jose_santos
 */

public class standardInfoStruct implements Comparable<standardInfoStruct>{
	
	private Date date;
	private String author;
	private String text;
	
	public standardInfoStruct(Date d, String a, String t) {
		this.date = d;
		this.author = a;
		this.text = t;
	}
	/**
	 * 
	 * @return a data da notificação escolhida.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @return o autor da notificação escolhida.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 
	 * @return o titulo da notificação escolhida.
	 */
	public String getTitle() {
		return text;
	}
	
	/**
	 * 
	 * @return uma nova forma de visualização de notificações.
	 */
	@Override
	public String toString() {
		return date + " - " + text;
	}

	/**
	 * 
	 * @return valor entre -1 e 1 para que as notificações sejam organizadas por ordem de cronologica da data
	 * do mais recente para o mais antigo.
	 */
	@Override
	public int compareTo(standardInfoStruct other) {
		if(this.getDate().compareTo(other.getDate()) < 0)
			return 1;
		if(this.getDate().compareTo(other.getDate()) > 0)
			return -1;
		return 0;
	}
	
}
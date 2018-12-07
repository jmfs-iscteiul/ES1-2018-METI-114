package jUnitTests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import BDA.mail.MailInfoStruct;

public class MailInfoStructTest {
	
	MailInfoStruct email;
	Date date;
	
	@Before
	public void startTest() {
		Calendar cal = Calendar.getInstance();
		date = cal.getTime();
		List<File> files = new ArrayList<>();
		files.add(new File("Teste"));
		
		email = new MailInfoStruct(date, "Autor", "Texto", "Assunto", "Para", "Cc", files);
	}
	
	@Test
	public void testMailInfoStructDateStringStringStringStringString() {
		MailInfoStruct teste = new MailInfoStruct(date, "Autor", "Texto", "Assunto", "Para", "Cc");
		
		assertEquals(teste.getDate().getTime(), date.getTime());
		assertEquals("Autor", teste.getAuthor());
		assertEquals("Texto", teste.getTitle());
		assertEquals("Assunto", teste.getSubject());
		assertEquals("Para", teste.getTo());
		assertEquals("Cc", teste.getCc());
	}

	@Test
	public void testMailInfoStructDateStringStringStringStringStringListOfFile() {
		List<File> files = new ArrayList<>();
		files.add(new File("Teste"));
		
		MailInfoStruct teste = new MailInfoStruct(date, "Autor", "Texto", "Assunto", "Para", "Cc", files);
		
		assertEquals(teste.getDate().getTime(), date.getTime());
		assertEquals("Autor", teste.getAuthor());
		assertEquals("Texto", teste.getTitle());
		assertEquals("Assunto", teste.getSubject());
		assertEquals("Para", teste.getTo());
		assertEquals("Cc", teste.getCc());
		assertEquals(1, teste.getAttachments().size());
		assertEquals(new File("Teste").getAbsolutePath(), teste.getAttachments().get(0).getAbsolutePath());
	}
	
	@Test
	public void testToString() {
		String s = date.toString() + " - " + "Assunto";
		
		assertEquals(s, email.toString());
	}

	@Test
	public void testGetSubject() {
		assertEquals("Assunto", email.getSubject());
	}

	@Test
	public void testGetTo() {
		assertEquals("Para", email.getTo());
	}

	@Test
	public void testGetCc() {
		assertEquals("Cc", email.getCc());
	}

	@Test
	public void testGetAttachments() {
		assertEquals(1, email.getAttachments().size());
		assertEquals(new File("Teste").getAbsolutePath(), email.getAttachments().get(0).getAbsolutePath());
	}

}

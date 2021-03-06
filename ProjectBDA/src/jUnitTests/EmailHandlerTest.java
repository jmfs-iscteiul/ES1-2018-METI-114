package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Test;

import BDA.mail.EmailHandler;
import BDA.mail.MailInfoStruct;

public class EmailHandlerTest {

	EmailHandler email = new EmailHandler("testeES114@outlook.pt", "grupo114");
	@Before

	@Test
	public void testEmailHandler() {
		assertEquals(email.getHostEnvio(), "smtp-mail.outlook.com");
		assertEquals(email.getHostRececao(), "imap-mail.outlook.com");
		assertEquals(email.getUser(), "testeES114@outlook.pt");
	}

	@Test
	public void testEnviarEmail() {
		try {
			InternetAddress to = new InternetAddress("testeES114@outlook.pt");
			
			assertEquals(true, email.enviarEmail("Assunto Teste JUnit", "Corpo de email do JUnit", to, null, null, null));
		} catch (AddressException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReceberEmail() {
		List<MailInfoStruct> listaEmails = email.receberEmail();

		assertNotNull(listaEmails);

		for(MailInfoStruct mail : listaEmails) {
			assertNotNull(mail.getAuthor());
			assertNotNull(mail.getDate());
			assertNotNull(mail.getSubject());
			assertNotNull(mail.getTitle());
			assertNotNull(mail.getTo());
		}
	}

	@Test
	public void testGetDiretoria() {
		assertNotNull(email.getDiretoria());
	}

	@Test
	public void testGetHostEnvio() {
		assertEquals("smtp-mail.outlook.com", email.getHostEnvio());
	}

	@Test
	public void testGetHostRececao() {
		assertEquals("imap-mail.outlook.com", email.getHostRececao());
	}

	@Test
	public void testGetUser() {
		assertEquals("testeES114@outlook.pt", email.getUser());
	}
}

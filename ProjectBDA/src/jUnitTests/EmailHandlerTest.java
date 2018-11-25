package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mail.EmailHandler;

public class EmailHandlerTest {
	
	EmailHandler email = new EmailHandler("Teste", "Teste");
	@Before

	@Test
	public void testEmailHandler() {
		assertEquals(email.getHostEnvio(), "smtp-mail.outlook.com");
		assertEquals(email.getHostRececao(), "imap-mail.outlook.com");
		assertEquals(email.getUser(), "Teste");
	}

	@Test
	public void testEnviarEmail() {
		fail("Not yet implemented");
	}

	@Test
	public void testReceberEmail() {
		fail("Not yet implemented");
	}

}

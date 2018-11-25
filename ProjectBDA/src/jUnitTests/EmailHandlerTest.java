package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	public void testGetDiretoria() {
		assertNotNull(email.getDiretoria());
	}
}

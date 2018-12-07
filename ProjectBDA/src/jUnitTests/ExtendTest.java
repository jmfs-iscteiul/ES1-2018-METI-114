package jUnitTests;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import BDA.common.Xml;
import BDA.facebook.Extend;

public class ExtendTest {
	
	Extend e;
	
	@Before
	public void start() {
		Xml xml = new Xml();
		e = new Extend(xml.leituraXML("Facebook", "Token"));
	}

	@Test
	public void testExtend() {
		assertNotNull(e);
	}

	@Test
	public void testGetExtension() {
		assertNotNull(e.getExtension());
	}

}

package jUnitTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import BDA.common.Xml;
import BDA.common.standardInfoStruct;

public class XmlTest {

	Xml x = new Xml();
	
	@Test
	public void testEscreverXML() {
		x.escreverXML("Teste", "Junit", "Sucesso");
	}

	@Test
	public void testLeituraXML() {
		assertNull(x.leituraXML("efef", "sdcdscds"));
		assertNotNull(x.leituraXML("Facebook", "Token"));
	}

	@Test
	public void testEscreverDeVarias() {
		standardInfoStruct s1 = new standardInfoStruct(new Date (), "Autor 1", "texto 1");
		standardInfoStruct s2 = new standardInfoStruct(new Date (), "Autor 2", "texto 2");
		ArrayList<standardInfoStruct> lista = new ArrayList<standardInfoStruct>();
		lista.add(s1);
		lista.add(s2);
		x.escreverDeVarias(lista);
		
	}

	@Test
	public void testLerDeVarias() {
		assertNotNull(x.lerDeVarias());
	}

}

package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import BDA.common.standardInfoStruct;

public class StandardInfoStructTest {

	standardInfoStruct s = new standardInfoStruct(new Date (), "Autor", "texto");
	@Before
	
	
	
	@Test
	public void testStandardInfoStruct() {
		standardInfoStruct s1 = new standardInfoStruct(new Date (), "Autor", "texto");
		assertNotNull(s1);
		
	}

	@Test
	public void testGetDate() {
		assertNotNull(s.getDate());
	}

	@Test
	public void testGetAuthor() {
		assertNotNull(s.getAuthor());
	}

	@Test
	public void testGetTitle() {
		assertNotNull(s.getTitle());
	}

	@Test
	public void testToString() {
//		return date + " - " + text;
		String temp = s.getDate() + " - " + s.getTitle();
		assertEquals(temp, s.toString());
	}

	@Test
	public void testCompareTo() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		standardInfoStruct s2 = new standardInfoStruct(new Date (), "Autor", "texto");
		assertEquals(-1, s2.compareTo(s));
	}

}

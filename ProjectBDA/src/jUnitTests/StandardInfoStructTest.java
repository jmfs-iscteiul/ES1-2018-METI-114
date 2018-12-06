package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import BDA.common.standardInfoStruct;


public class StandardInfoStructTest {

	standardInfoStruct test1;
	standardInfoStruct test2;
	Calendar cal;
	Date date1;
	String s1;
	
	@Before
	public void startTest() {
		cal = Calendar.getInstance();
		date1 = cal.getTime();
		test1 = new standardInfoStruct(date1, "Autor", "Texto");
	}
	
	@Test
	public void testStandardInfoStruct() {
		
		standardInfoStruct teste = new standardInfoStruct(date1, "Autor", "Text");
		assertEquals(teste.getDate().getTime(), date1.getTime());
		assertEquals("Autor", teste.getAuthor());
		assertEquals("Text", teste.getTitle());
	
	}
	
	@Test
	public void testToString() {
		
		s1 = date1.toString() + " - " + "Texto";
		
		assertEquals(s1, test1.toString());
		
	}
	
	@Test
	public void testGetContent() {
		assertEquals("Texto", test1.getTitle());
	}
	
	@Test
	public void testAuthor() {
		assertEquals("Autor", test1.getAuthor());
	}
	
	@Test
	public void testDate() {
		assertEquals(test1.getDate().getTime(), date1.getTime());
	}
	
	@Test
	public void testCompareTo() throws InterruptedException {
		
		Date date1 = new Date();
		
		test1 = new standardInfoStruct(date1, "Author", "Text");
		
		Thread.sleep(2000);
		
		Date date2 = new Date();
		
		test2 = new standardInfoStruct(date2, "Author", "Text");
		
		assertTrue(test2.compareTo(test1) == -1);
		
	}
	
	
	
	

}

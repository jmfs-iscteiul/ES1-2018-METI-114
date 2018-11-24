package jUnitTests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import org.junit.Test;

import facebook.Extend;

public class ExtendTest {
	
	Extend e = new Extend ();

	@Test
	public void testExtend() {
		assertNotNull(e);
	}

	@Test
	public void testGetExtension() {
		
	}

}

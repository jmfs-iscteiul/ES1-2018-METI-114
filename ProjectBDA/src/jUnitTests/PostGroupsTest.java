package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import BDA.facebook.PostGroups;

public class PostGroupsTest {
	
	private PostGroups pc = new PostGroups ("EAAEdPLJA8d0BAE7rgGTkhPdfjtEz7kimJHsSJKqj8RR4v7ErVSRft53OyLZBbed8odScy5fZBTZA9e1R4reX4gDa1pA4RtapKGIzUavVZBsFFYQn37ZASV1IKZC27VBfqo9TG9IDk7br9p8We52RnEInHovoCG16gZD");
	@Before
	
	
	@Test
	public void testPostGroups() {
		assertNotNull(pc);
	}

	
	/*@Test
	public void testPostartexto() {
		pc.postarTexto("ES1 testes Iscte", "JUNIT TEST");
		
	}*/
	
	@Test 
	public void testPostarimagem() {
		pc.postarimagem("/Users/ricardo/Desktop/fb9e98de414cf7be3651d2ce4fed4d263a312e5b.jpg","fb9e98de414cf7be3651d2ce4fed4d263a312e5b.jpg", "JUNIT TEST", "ES1 testes Iscte");
		
	}
	
	@Test
	public void testGetResult() {
		assertNotNull(pc.getResult());
	}

}

package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import facebook.PostGroups;

public class PostGroupsTest {
	
	private PostGroups p = new PostGroups ("");
	private PostGroups pc = new PostGroups ("EAAEdPLJA8d0BAE7rgGTkhPdfjtEz7kimJHsSJKqj8RR4v7ErVSRft53OyLZBbed8odScy5fZBTZA9e1R4reX4gDa1pA4RtapKGIzUavVZBsFFYQn37ZASV1IKZC27VBfqo9TG9IDk7br9p8We52RnEInHovoCG16gZD");

	@Test
	public void testPostGroups() {
		assertNull(p);
		assertNotNull(pc);
	}

	
	@Test
	public void testPostartexto() {
		assertEquals(true, pc.postarTexto("ES1 Teste", "oi"));
		
	}
	
	
	@Test
	public void testGetResult() {
		assertNotNull(pc.getResult());
	}

}

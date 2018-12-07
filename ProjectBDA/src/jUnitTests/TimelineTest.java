package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import BDA.facebook.Timeline;

public class TimelineTest {
	
	Timeline t = new Timeline("");
	Timeline tc = new Timeline("EAAEdPLJA8d0BAE7rgGTkhPdfjtEz7kimJHsSJKqj8RR4v7ErVSRft53OyLZBbed8odScy5fZBTZA9e1R4reX4gDa1pA4RtapKGIzUavVZBsFFYQn37ZASV1IKZC27VBfqo9TG9IDk7br9p8We52RnEInHovoCG16gZD");
	

	@Test
	public void testTimeline() {
		assertNotNull(t);
		assertNotNull(tc);
	}

	@Test
	public void testSetFilter() {
		String s = "Iscte";
		t.setFilter(s);
		assertEquals(s,t.getFilter());
	}
	

	@Test
	public void testGetTimeline() {
		assertNotNull(tc.getTimeline());
		
	}


	
}

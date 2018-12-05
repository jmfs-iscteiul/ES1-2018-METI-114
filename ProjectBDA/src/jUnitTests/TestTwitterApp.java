package jUnitTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

import common.standardInfoStruct;
import common.Xml;
import twitter.TwitterApp;

public class TestTwitterApp {
	
	@Test
	public void testTimeLine() {
		TwitterApp test = new TwitterApp();
		test.authenticateMyAccount("QRFyiUq64mbtFLxZUGAMoFb7T", "SJCB1ZmR0xxZtV95zUEfxNSkZWXU76pf1jIhG7WXEzDiHIrd0I",
				"1052294113620611073-wO7U7iqqQ4v7jFqb3Pb3pqnJa7HCdM", "O94jOOxoSRsAfQAvu4GHrykJUrZ0nzoubUpKdGEd6TXGF");
		ArrayList<standardInfoStruct> timelineTest = test.fetchTimeline();
		assertNotNull(timelineTest);
	}
	
	@Test
	public void testAuthenticate(){
		TwitterApp test2 = new TwitterApp();
		test2.authenticateMyAccount("QRFyiUq64mbtFLxZUGAMoFb7T", "SJCB1ZmR0xxZtV95zUEfxNSkZWXU76pf1jIhG7WXEzDiHIrd0I",
				"1052294113620611073-wO7U7iqqQ4v7jFqb3Pb3pqnJa7HCdM", "O94jOOxoSRsAfQAvu4GHrykJUrZ0nzoubUpKdGEd6TXGF");
		Xml xml = new Xml();
	
		assertEquals("QRFyiUq64mbtFLxZUGAMoFb7T", xml.leituraXML("Twitter", "ConsumerKey"));
		assertEquals("SJCB1ZmR0xxZtV95zUEfxNSkZWXU76pf1jIhG7WXEzDiHIrd0I", xml.leituraXML("Twitter", "ConsumerSecret"));
		assertEquals("1052294113620611073-wO7U7iqqQ4v7jFqb3Pb3pqnJa7HCdM", xml.leituraXML("Twitter", "AccessToken"));
		assertEquals("O94jOOxoSRsAfQAvu4GHrykJUrZ0nzoubUpKdGEd6TXGF", xml.leituraXML("Twitter", "AccessTokenSecret"));
	}
	
	@Test
	public void testTwitterApplication(){
		TwitterApp test3 = new TwitterApp();
		assertNotNull(test3);
	}

	@Test
	public void testPostTweet(){
		TwitterApp test4 = new TwitterApp();
		test4.authenticateMyAccount("QRFyiUq64mbtFLxZUGAMoFb7T", "SJCB1ZmR0xxZtV95zUEfxNSkZWXU76pf1jIhG7WXEzDiHIrd0I",
				"1052294113620611073-wO7U7iqqQ4v7jFqb3Pb3pqnJa7HCdM", "O94jOOxoSRsAfQAvu4GHrykJUrZ0nzoubUpKdGEd6TXGF");
		
		assertEquals(true, test4.postTweet("tweet"));
	}
	
}

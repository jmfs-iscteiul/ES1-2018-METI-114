package twitter;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import common.standardInfoStruct;

public class TestTwitterApp {
	
	@Test
	public void testTimeLine() {
		TwitterApp test = new TwitterApp();
		test.authenticateMyAccount();
		ArrayList<standardInfoStruct> timelineTest = test.fetchTimeline();
		assertNotNull(timelineTest);
	}
	
	@Test
	public void testAuthenticate(){
		TwitterApp test2 = new TwitterApp();
		test2.authenticateMyAccount();
		
		assertNotNull("o");
	}
	
	@Test
	public void testTwitterApplication(){
		TwitterApp test3 = new TwitterApp();
		assertNotNull(test3);
	}
	
}

package twitter;

import java.util.Iterator;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterApp {

	private List<Status> timelineStatuses;
	private Twitter twitter;
	
	public static void main(String[] args) {
		TwitterApp t = new TwitterApp();
		t.setCredentials();
		t.fetchTimeline();
        
     }
	
	public void setCredentials(){
		
		try {
        	ConfigurationBuilder cb = new ConfigurationBuilder();
        	cb.setDebugEnabled(true)
        	  .setOAuthConsumerKey("W1f0VvgWPfT8OBqVxvy4Mw")
        	  .setOAuthConsumerSecret("zKH2yAtRyefwsgOO8h8Szc4kru68iEm95QmIG7svw")
        	  .setOAuthAccessToken("36481851-VhzByC4f9MSsZES1QZQ4e4iBvA9bWGLyv9HKFpy7c")
        	  .setOAuthAccessTokenSecret("OahDuXF2Lhl5xlNYALhYZir6xSflAxKP9Zh89T05po");
        	TwitterFactory tf = new TwitterFactory(cb.build());
        	twitter = tf.getInstance();
		}catch (Exception e) { System.out.println(e.getMessage()); }
	}
	
	public void fetchTimeline(){
		        		
		try{
			timelineStatuses = twitter.getHomeTimeline();
            System.out.println("------------------------\n Showing home timeline \n------------------------");
    		int counter=0;
    		int counterTotal = 0;
            for (Status status : timelineStatuses) {
				// Filters only tweets from user "catarina"
				if (status.getUser().getName() != null /* && status.getUser().getName().contains("")*/) {
					System.out.println(status.getUser().getName() + ":" + status.getText());
					System.out.println("____________________________________________________________");
					counter++;
				}
				counterTotal++;
            }
    		System.out.println("-------------\nNº of Results: " + counter+"/"+counterTotal);
        } catch (Exception e) { System.out.println(e.getMessage()); }
	}
	
	
	
	
	
	
	
	
	
	
	
	/*public static void main(String [] args){
		
		new TwitterApp().fetchTweets("#Programming");
		
	}

	private void fetchTweets(String hashtag) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
  	  .setOAuthConsumerKey("W1f0VvgWPfT8OBqVxvy4Mw")
  	  .setOAuthConsumerSecret("zKH2yAtRyefwsgOO8h8Szc4kru68iEm95QmIG7svw")
  	  .setOAuthAccessToken("36481851-VhzByC4f9MSsZES1QZQ4e4iBvA9bWGLyv9HKFpy7c")
  	  .setOAuthAccessTokenSecret("OahDuXF2Lhl5xlNYALhYZir6xSflAxKP9Zh89T05po");
		
		TwitterFactory twitterFactory = new TwitterFactory();
		Twitter twitter = twitterFactory.getInstance();
		
		Query query = new Query(hashtag);
		query.setCount(100);
		query.setSince("16-10-2018");
		QueryResult searchResult = null;
		
		try{
			
			
			
			searchResult = twitter.search(query);
			
			List<Status> tweetList = searchResult.getTweets();
			
			Iterator<Status> itTweet = tweetList.iterator();
			
			while(itTweet.hasNext()){
				
				Status tweet = (Status) itTweet.next();
				
				System.out.println(tweet.getUser() + " - " + tweet.getText());
				System.out.println("======================================");
				
			}
			
		}catch(TwitterException e){
			e.printStackTrace();
		}
		
		
		
	}
	*/
	
}

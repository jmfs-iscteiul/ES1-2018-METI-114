package twitter;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterApp {

	private List<Status> timelineStatuses;
	private Twitter twitter;
	
	public static void main(String[] args) {
		TwitterApp t = new TwitterApp();
		t.authenticate();
		//t.fetchTimeline();
        //t.searchHashtag("iscte");
		t.searchPerson("ana");
     }
	
	public void authenticate(){
		
		try {
			
        	ConfigurationBuilder cb = new ConfigurationBuilder();
        	cb.setDebugEnabled(true)
        	  .setOAuthConsumerKey("W1f0VvgWPfT8OBqVxvy4Mw")
        	  .setOAuthConsumerSecret("zKH2yAtRyefwsgOO8h8Szc4kru68iEm95QmIG7svw")
        	  .setOAuthAccessToken("36481851-VhzByC4f9MSsZES1QZQ4e4iBvA9bWGLyv9HKFpy7c")
        	  .setOAuthAccessTokenSecret("OahDuXF2Lhl5xlNYALhYZir6xSflAxKP9Zh89T05po");
        	TwitterFactory tf = new TwitterFactory(cb.build());
        	twitter = tf.getInstance();
        	
		}catch (Exception e) { 
			System.out.println(e.getMessage()); 
		}
	}
	
	public void fetchTimeline(){
		        		
		try{
			timelineStatuses = twitter.getHomeTimeline();
            System.out.println("|||||||||||||||||||||||| MOSTRAR TIMELINE ||||||||||||||||||||||||");
    		int counter=0;
    		int counterTotal = 0;
            for (Status status : timelineStatuses) {
				// Filters only tweets from user "catarina"
				if (status.getUser().getName() != null) {
					System.out.println(status.getUser().getName() + ":" + status.getText());
					System.out.println("____________________________________________________________");
					counter++;
				}
				counterTotal++;
            }
    		System.out.println("-------------\nNº of Results: " + counter+"/"+counterTotal);
        } catch (Exception e) { 
        	System.out.println(e.getMessage()); }
	}
	
	
	
	public void searchHashtag(String hashtag){ // Procura uma hashtag mas so mostra uns poucos resultados
		
		try{
			Query query = new Query(hashtag);
			QueryResult result = twitter.search(query);
			int counter = 0;
			for(Status status : result.getTweets()){
				counter++;
				System.out.println(counter + "-> " + " @" + status.getUser().getScreenName() + ": " + status.getText() + " : " + status.getGeoLocation());
				System.out.println("-----------------------------------------------------------------------");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void searchPerson(String person){
		
		try{
			Query query = new Query(person);
			ResponseList<User> result = twitter.searchUsers(query.toString(), -1);
			int counter = 0;
			System.out.println("estou aqui");
			for(User user : result){
				System.out.println("entrei");
				if(user.getName() != null){
					System.out.println("aqui depois");
					counter++;
					System.out.println(counter + "-> " + user.getName() );
					System.out.println("----------------------------------");	
				}
			}	
		}catch(TwitterException e){
			System.out.println(e.getMessage());
		}
		
		
	}
	
}

package twitter;

import java.util.ArrayList;
import java.util.List;

import common.standardInfoStruct;
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

	private List<twitter4j.Status> timelineStatuses;
	private Twitter twitter;
	private ArrayList<standardInfoStruct> listaTweets;
	private standardInfoStruct mensagem;
	
	public static void main(String[] args) {
		TwitterApp t = new TwitterApp();
		
		t.authenticateMyAccount();
		t.fetchTimeline();
		//t.postTweet("As coisas hoje n�o me est�o a correr bem. HELP!");
		//Status retweet = t.getChooseRetweet();
		//t.retweetPost(retweet.getId());
		//t.searchHashtag("#university");
		
		
		//t.searchPerson("ana");
		//t.authenticateIscteAccount();
		
     }
	
	public void authenticateMyAccount(){
		
		try {
        	ConfigurationBuilder cb = new ConfigurationBuilder();
        	cb.setDebugEnabled(true)
        	  .setOAuthConsumerKey("QRFyiUq64mbtFLxZUGAMoFb7T")
        	  .setOAuthConsumerSecret("SJCB1ZmR0xxZtV95zUEfxNSkZWXU76pf1jIhG7WXEzDiHIrd0I")
        	  .setOAuthAccessToken("1052294113620611073-wO7U7iqqQ4v7jFqb3Pb3pqnJa7HCdM")
        	  .setOAuthAccessTokenSecret("O94jOOxoSRsAfQAvu4GHrykJUrZ0nzoubUpKdGEd6TXGF");
        	TwitterFactory tf = new TwitterFactory(cb.build());
        	twitter = tf.getInstance();
        	
		}catch (Exception e) { 
			System.out.println(e.getMessage()); 
		}
	}
	
	public void authenticateIscteAccount(){
		
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
	
	public void fetchTimeline(){  //METER NA ESTRUTURA DA MENSAGEM
		        		
		try{
			timelineStatuses = twitter.getHomeTimeline();
			listaTweets = new ArrayList<standardInfoStruct>();
            System.out.println("                              MOSTRAR TIMELINE ");
            int counter = 1;
            for (Status status : timelineStatuses) {
				if (status.getUser().getName() != null) {
					//System.out.println(counter + " -> "  + status.getCreatedAt() + " - " + "@" + status.getUser().getName() + ":" + status.getText());
					mensagem = new standardInfoStruct(status.getCreatedAt().toString(), status.getUser().getScreenName(), status.getText());
					listaTweets.add(mensagem);
				}
				counter++;
            } 
        } catch (Exception e) { 
        	System.out.println(); 
        	}
		//System.out.println(listaTweets );
	}
	
	public void postTweet(String tweet){
		
		try{
			
			Status status = twitter.updateStatus(tweet);
			System.out.println(status.getText());			
			
		}catch(TwitterException e){
			System.out.println("Catch de publicar Tweet: Falhou");
			System.out.println(e.getMessage());
		}
	}
	
	public void retweetPost(long statusID){
		
		try {
			twitter.retweetStatus(statusID);
		} catch (TwitterException e) {
			System.out.println("Falhou no Retweet");
			e.getMessage();
		}
	}
	
	public Status getChooseRetweet(){
		Status primeiroTweet = timelineStatuses.get(13);
		return primeiroTweet;
	}
	
	public void searchHashtag(String hashtag){ // Procura uma hashtag mas so mostra uns poucos resultados
		
		try{
			Query query = new Query(hashtag);
			QueryResult result = twitter.search(query);
			int counter = 0;
			for(Status status : result.getTweets()){
				counter++;
				System.out.println(counter + "-> "+ "@" + status.getUser().getScreenName() + ": " + status.getText() + " : " + status.getGeoLocation());
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

package twitter;

import java.util.ArrayList;
import java.util.List;

import common.standardInfoStruct;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Esta classe servirá para interagir com o Twitter, permitindo várias funcionalidades abaixo descritas.
 * @author João Oliveira
 *
 */
public class TwitterApp {
	
	private Twitter twitter;								//Instancia da classe twitter4j
	private ArrayList<standardInfoStruct> tweetsList;		//Lista de tweets manipulada com a estrutura da mensagem
	
//	public static void main(String[] args) {
//		TwitterApp t = new TwitterApp();
//		
//		t.authenticateMyAccount();
//		t.fetchTimeline();
		//t.postTweet("As coisas hoje n�o me est�o a correr bem. HELP!");
		//Status retweet = t.getChooseRetweet();
		//t.retweetPost(retweet.getId());
		//t.searchHashtag("#university");
		
		
		//t.searchPerson("ana");
		//t.authenticateIscteAccount();
		
//     }
	
	/**
	 * Esta função serve para autenticar o utilizador na conta do twitter com as credênciais de Developer.
	 */
	
	public void authenticateMyAccount(){
		try {
        	ConfigurationBuilder cb = new ConfigurationBuilder();      //Variável que constrói as definições padrão de autenticação
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
	
	/**
	 * Esta função irá buscar a Timeline do Twitter.
	 * @return Devolve uma lista que contém as mensagens organizadas com data, nome de utilizador e conteúdo da mensagem.
	 */
	
	public ArrayList<standardInfoStruct> fetchTimeline(){  		
		try{
			List<twitter4j.Status> timelineStatuses = twitter.getHomeTimeline();  //Lista da timeline em formato status
			tweetsList = new ArrayList<standardInfoStruct>();
            for (Status status : timelineStatuses) {
				if (status.getUser().getName() != null) {
					standardInfoStruct message = new standardInfoStruct(status.getCreatedAt(), status.getUser().getScreenName(), status.getText());   //Conversão das mensagens status em standardInfoStruct
					tweetsList.add(message);
				}
            } 
        } catch (Exception e) { 
        	System.out.println(e.getMessage());
        	}
		return tweetsList;
	}
	
	
	/**
	 * Esta função publica um Tweet na aplicação
	 * @param tweet Este parâmetro é o conteúdo da mensagem que será publicada.
	 */
	
	public boolean postTweet(String tweet){
		try{
			Status status = twitter.updateStatus(tweet);	//Variável 		
		}catch(TwitterException e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	
//	public void retweetPost(long statusID){
//		
//		try {
//			twitter.retweetStatus(statusID);
//		} catch (TwitterException e) {
//			System.out.println("Falhou no Retweet");
//			e.getMessage();
//		}
//	}
	
//	public Status getChooseRetweet(){
//		Status primeiroTweet = timelineStatuses.get(13);
//		return primeiroTweet;
//	}

}

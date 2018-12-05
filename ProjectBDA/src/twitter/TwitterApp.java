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
	
	/**
	 * Esta função serve para autenticar o utilizador na conta do twitter com as credenciais de Developer.
	 */
	public void authenticateMyAccount(String ck, String cs, String at, String ats){
		try {
        	ConfigurationBuilder cb = new ConfigurationBuilder();      //Variável que constrói as definições padrão de autenticação
        	cb.setDebugEnabled(true)
        	  .setOAuthConsumerKey(ck)
        	  .setOAuthConsumerSecret(cs)
        	  .setOAuthAccessToken(at)
        	  .setOAuthAccessTokenSecret(ats);
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
			twitter.updateStatus(tweet);	//Variável que define o novo tweet	
		}catch(TwitterException e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	

}

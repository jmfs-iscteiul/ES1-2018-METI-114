package facebook;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;

import common.standardInfoStruct;

public class Timeline {

	private String accessToken;
	private FacebookClient fbClient;
	private Connection<Group> result1;
	private Connection<Post> result;
	private String filter= "ISCTE";

	public Timeline (String accessToken) {
		this.accessToken = accessToken;
		fbClient = new DefaultFacebookClient(accessToken);
	}
	
	

	public void setFilter(String filter) {
		this.filter = filter;
	}



	public ArrayList <standardInfoStruct> getTimeline () {
		ArrayList<standardInfoStruct> lista = new ArrayList<standardInfoStruct> ();
		result1 = fbClient.fetchConnection("me/groups",Group.class); //Lista de Grupos
		int counter = 0;
		int counterTotal = 0;

		//		for (List<Group> GroupPages : result1) {
		//			for(Group aGrupo : GroupPages) {
		result = fbClient.fetchConnection("me/feed",Post.class);	
		//				Connection<Post> result = fbClient.fetchConnection(aGrupo.getId() + "/feed",Post.class); //Lista de Posts
		for (List<Post> page : result) {
			for (Post aPost : page) {
				// Filters only posts that contain the word "Inform"
				if (aPost.getMessage() != null && aPost.getMessage().contains(filter)) {
					System.out.println("\nPosts:");
					System.out.println("---- Post "+ counter + " ----");
					System.out.println("Id: "+"fb.com/"+aPost.getId());
					System.out.println("Message: "+aPost.getMessage());
					System.out.println("Created: "+aPost.getCreatedTime());
					counter++;
					standardInfoStruct sis = new standardInfoStruct(aPost.getCreatedTime().toString(), aPost.getName(), aPost.getMessage());
					lista.add(sis);
				}
				counterTotal++;
			}
			//				}
			//			}


		}
		//	}
		return lista;
		//		System.out.println("-------------\nN� of Results: " + counter+"/"+counterTotal);		
	}



@SuppressWarnings("deprecation")
public static void main(String[] args) {
	
	
	Timeline timeline = new Timeline ("EAAEdPLJA8d0BAKBpufqqEP96zJusMI6EhV9ErThejmx0ZBgEhFnyhZCTCZADRdWV3WIsPgzeUwyBbd17ucBcITE3sCZBdXbP1n0pUUZBDHPXE1BqqZCHz6sFvpTOZBhb3Wiy6M4RoAYHP1Acul3SaM3NK0SvLkAqBmIcYcEZBYOMFwZDZD");
	timeline.setFilter(" ");
	timeline.getTimeline();

//	@SuppressWarnings("deprecation")
//	Scanner s = new Scanner ("tokens.txt");
//	//		if (s.nextLine().startsWith("Facebook")) {
//	//			String linha = s.nextLine();
//	//			String [] line = linha.split(" ");
//	String accessToken = "";
//	FacebookClient fbClient = new DefaultFacebookClient(accessToken);
//
//
//	Connection<Group> result1 = fbClient.fetchConnection("me/groups",Group.class); //Lista de Grupos
//
//
//
//	int counter = 0;
//	int counterTotal = 0;
//
//	//		for (List<Group> GroupPages : result1) {
//	//			for(Group aGrupo : GroupPages) {
//	Connection<Post> result = fbClient.fetchConnection("me/feed",Post.class);	
//	//				Connection<Post> result = fbClient.fetchConnection(aGrupo.getId() + "/feed",Post.class); //Lista de Posts
//	for (List<Post> page : result) {
//		for (Post aPost : page) {
//			// Filters only posts that contain the word "Inform"
//			if (aPost.getMessage() != null && aPost.getMessage().contains("Jorge")) {
//				System.out.println("\nPosts:");
//				System.out.println("---- Post "+ counter + " ----");
//				System.out.println("Id: "+"fb.com/"+aPost.getId());
//				System.out.println("Message: "+aPost.getMessage());
//				System.out.println("Created: "+aPost.getCreatedTime());
//				counter++;
//			}
//			counterTotal++;
//		}
//		//				}
//		//			}
//
//
//	}
//	//		}
//
//	//		System.out.println("-------------\nN� of Results: " + counter+"/"+counterTotal);		
}
}

package facebook;

import java.util.List;
import java.util.Scanner;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;

public class Timeline {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		@SuppressWarnings("deprecation")
		Scanner s = new Scanner ("tokens.txt");
//		if (s.nextLine().startsWith("Facebook")) {
//			String linha = s.nextLine();
//			String [] line = linha.split(" ");
			String accessToken = "";
			FacebookClient fbClient = new DefaultFacebookClient(accessToken);


			Connection<Group> result1 = fbClient.fetchConnection("me/groups",Group.class); //Lista de Grupos



			int counter = 0;
			int counterTotal = 0;

			//		for (List<Group> GroupPages : result1) {
			//			for(Group aGrupo : GroupPages) {
			Connection<Post> result = fbClient.fetchConnection("me/feed",Post.class);	
			//				Connection<Post> result = fbClient.fetchConnection(aGrupo.getId() + "/feed",Post.class); //Lista de Posts
			for (List<Post> page : result) {
				for (Post aPost : page) {
					// Filters only posts that contain the word "Inform"
					if (aPost.getMessage() != null && aPost.getMessage().contains("ETI")) {
						System.out.println("\nPosts:");
						System.out.println("---- Post "+ counter + " ----");
						System.out.println("Id: "+"fb.com/"+aPost.getId());
						System.out.println("Message: "+aPost.getMessage());
						System.out.println("Created: "+aPost.getCreatedTime());
						counter++;
					}
					counterTotal++;
				}
				//				}
				//			}


			}
//		}

		//		System.out.println("-------------\nN� of Results: " + counter+"/"+counterTotal);		
	}
}

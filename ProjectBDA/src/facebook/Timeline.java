package facebook;

import java.util.Date;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;

public class Timeline {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		@SuppressWarnings("deprecation")
		String accessToken = "EAAEdPLJA8d0BAE7rgGTkhPdfjtEz7kimJHsSJKqj8RR4v7ErVSRft53OyLZBbed8odScy5fZBTZA9e1R4reX4gDa1pA4RtapKGIzUavVZBsFFYQn37ZASV1IKZC27VBfqo9TG9IDk7br9p8We52RnEInHovoCG16gZD";
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
									if (aPost.getMessage() != null && aPost.getMessage().contains("Jorge")) {
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
		//		System.out.println("-------------\nN� of Results: " + counter+"/"+counterTotal);		
	}
}

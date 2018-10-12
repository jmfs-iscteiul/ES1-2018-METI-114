package facebook;

import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

public class Timeline {

	public static void main(String[] args) {

		@SuppressWarnings("deprecation")
		String accessToken = "EAAEdPLJA8d0BAJjAufWYP8MrZBZB5WmtNvKFetkwXemeUm0xjW7iYCWtnd7LO9CRiuCL1ZBikGRdJA93LBL3efU1Lh4wMIeUB4AEVBjg9IMwvtSfZAPZCqhcDoHqSywrccHQkRwxl3kD6bI4zjLznZBZA6TStSfyMOYUJzpTi473wZDZD";
		FacebookClient fbClient5 = new DefaultFacebookClient(accessToken);

		Connection<Post> result = fbClient5.fetchConnection("me/feed",Post.class);
		System.out.println("\nPosts:");

		int counter = 0;
		int counterTotal = 0;
		for (List<Post> page : result) {
			for (Post aPost : page) {
				// Filters only posts that contain the word "Inform"
				if (aPost.getMessage() != null && aPost.getMessage().contains("")) {
					System.out.println("---- Post "+ counter + " ----");
					System.out.println("Id: "+"fb.com/"+aPost.getId());
					System.out.println("Message: "+aPost.getMessage());
					System.out.println("Created: "+aPost.getCreatedTime());
					counter++;
				}
				counterTotal++;
			}
		}
		//		System.out.println("-------------\nN� of Results: " + counter+"/"+counterTotal);		
	}
}

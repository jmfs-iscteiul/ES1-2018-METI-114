package facebook;

import java.util.List;
import java.util.Scanner;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;

public class postGroups {

	public static void main(String[] args) {


		String accessToken = "EAAEdPLJA8d0BAE7rgGTkhPdfjtEz7kimJHsSJKqj8RR4v7ErVSRft53OyLZBbed8odScy5fZBTZA9e1R4reX4gDa1pA4RtapKGIzUavVZBsFFYQn37ZASV1IKZC27VBfqo9TG9IDk7br9p8We52RnEInHovoCG16gZDyes";
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		Connection<Group> result = fbClient.fetchConnection("me/groups",Group.class);
		Scanner s = new Scanner (System.in);
		for (List <Group> GroupPage : result) {
			for (Group aGroup : GroupPage ) {
//				if(aGroup.getName().contains("Iscte") || aGroup.getName().contains("ISCTE") || aGroup.getName().contains("ETI") ) {
					System.out.println("Do you want to print on this group " + aGroup.getName() + "?");
					String answer = s.nextLine();
					if (answer.equalsIgnoreCase("Yes")) {
						System.out.println("Write your post");
						String post = s.nextLine();
						FacebookType response = fbClient.publish(aGroup.getId() + "/feed", FacebookType.class,Parameter.with("message", post));
						System.out.println("fb.com"+ response.getId());
					}
//				}
			}
		}

	}

}

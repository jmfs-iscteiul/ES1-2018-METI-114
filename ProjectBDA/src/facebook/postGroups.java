package facebook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.restfb.BinaryAttachment;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;

public class postGroups {


	private String accessToken;
	private FacebookClient fbClient;
	private Connection<Group> result;

	public postGroups (String accessToken) {
		this.accessToken = accessToken;
		fbClient = new DefaultFacebookClient(accessToken);
		result = fbClient.fetchConnection("me/groups",Group.class);
	}
	
	public void postar () {
		Scanner s = new Scanner (System.in);
		for (List <Group> GroupPage : result) {
			for (Group aGroup : GroupPage ) {
				if(aGroup.getName().contains("Iscte") || aGroup.getName().contains("ISCTE") || aGroup.getName().contains("ETI") ) {
					System.out.println("Do you want to publish on this group " + aGroup.getName() + "?");
					String answer = s.nextLine();
					if (answer.equalsIgnoreCase("Yes")) {
						System.out.println("Post or upload file?");
						String resposta = s.nextLine();
						if (resposta.equalsIgnoreCase("post")) {
							System.out.println("Write your post");
							String post = s.nextLine();
							FacebookType response = fbClient.publish(aGroup.getId() + "/feed", FacebookType.class,Parameter.with("message", post));
							System.out.println("fb.com"+ response.getId());
						}
						if (resposta.equalsIgnoreCase("upload file")) {
							System.out.println("Write file location");
							String attachmentName = s.nextLine();
							System.out.println("Write file name");
							String name = s.nextLine();
							System.out.println("Write description");
							String description = s.nextLine();
							FileInputStream fis;
							try {
								fis = new FileInputStream (new File (attachmentName));
								FacebookType publish = fbClient.publish(aGroup.getId() + "/photos", FacebookType.class,
										BinaryAttachment.with(name, fis), Parameter.with("message", description));
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}

						}

					}if (answer.equalsIgnoreCase("fim")) {
						System.exit(0);
					}
				}
			}
		}
	}

	public static void main(String[] args) {


//		String accessToken = "";
//		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
//		Connection<Group> result = fbClient.fetchConnection("me/groups",Group.class);
//		Scanner s = new Scanner (System.in);
//		for (List <Group> GroupPage : result) {
//			for (Group aGroup : GroupPage ) {
//				if(aGroup.getName().contains("Iscte") || aGroup.getName().contains("ISCTE") || aGroup.getName().contains("ETI") ) {
//					System.out.println("Do you want to publish on this group " + aGroup.getName() + "?");
//					String answer = s.nextLine();
//					if (answer.equalsIgnoreCase("Yes")) {
//						System.out.println("Post or upload file?");
//						String resposta = s.nextLine();
//						if (resposta.equalsIgnoreCase("post")) {
//							System.out.println("Write your post");
//							String post = s.nextLine();
//							FacebookType response = fbClient.publish(aGroup.getId() + "/feed", FacebookType.class,Parameter.with("message", post));
//							System.out.println("fb.com"+ response.getId());
//						}
//						if (resposta.equalsIgnoreCase("upload file")) {
//							System.out.println("Write file location");
//							String attachmentName = s.nextLine();
//							System.out.println("Write file name");
//							String name = s.nextLine();
//							System.out.println("Write description");
//							String description = s.nextLine();
//							FileInputStream fis;
//							try {
//								fis = new FileInputStream (new File (attachmentName));
//								FacebookType publish = fbClient.publish(aGroup.getId() + "/photos", FacebookType.class,
//										BinaryAttachment.with(name, fis), Parameter.with("message", description));
//							} catch (FileNotFoundException e) {
//								e.printStackTrace();
//							}
//
//						}
//
//					}if (answer.equalsIgnoreCase("fim")) {
//						System.exit(0);
//					}
//				}
//			}
//		}
//
	}

}

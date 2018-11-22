package facebook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.restfb.BinaryAttachment;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;

public class PostGroups {


	private String accessToken;
	private FacebookClient fbClient;
	private Connection<Group> result;
	private Map <String,String> Grupo;


	public PostGroups (String accessToken) {
		this.accessToken = accessToken;
		fbClient = new DefaultFacebookClient(accessToken);
		result = fbClient.fetchConnection("me/groups",Group.class);
		Grupo = new HashMap <String, String> ();
	}



	public void postarTexto (String nomeGrupo, String message) {
		String post = message;
		FacebookType response = fbClient.publish(Grupo.get(nomeGrupo) + "/feed", FacebookType.class,Parameter.with("message", post));


	}


	public void postarimagem (String caminho, String nome, String descricao, String nomeGrupo) {

		String attachmentName = caminho;
		String name = nome;
		String description = descricao;
		FileInputStream fis;
		try {
			fis = new FileInputStream (new File (attachmentName));
			FacebookType publish = fbClient.publish(Grupo.get(nomeGrupo) + "/photos", FacebookType.class,
					BinaryAttachment.with(name, fis), Parameter.with("message", description));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

	public ArrayList <String> getResult() {
		ArrayList <String > nomesGrupo = new ArrayList <String> ();
		for (List <Group> GroupPage : result) {
			for (Group aGroup : GroupPage ) {
				if(aGroup.getName().contains("Iscte") || aGroup.getName().contains("ISCTE") || aGroup.getName().contains("ETI") ) {
					Grupo.put(aGroup.getName(), aGroup.getId());
					nomesGrupo.add(aGroup.getName());
				}
			}

		}
		return nomesGrupo;
	}
}

//
//
//		String accessToken = "EAAEdPLJA8d0BAKBpufqqEP96zJusMI6EhV9ErThejmx0ZBgEhFnyhZCTCZADRdWV3WIsPgzeUwyBbd17ucBcITE3sCZBdXbP1n0pUUZBDHPXE1BqqZCHz6sFvpTOZBhb3Wiy6M4RoAYHP1Acul3SaM3NK0SvLkAqBmIcYcEZBYOMFwZDZD";
//
//		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
//		Connection<Group>result = fbClient.fetchConnection("me/groups",Group.class);
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
//						System.out.println(result);
//						System.exit(0);
//					}
//				}
//			}
//		}
//	}
//
//}


//			public void postar () {
//				Scanner s = new Scanner (System.in);
//				for (List <Group> GroupPage : result) {
//					for (Group aGroup : GroupPage ) {
//						if(aGroup.getName().contains("Iscte") || aGroup.getName().contains("ISCTE") || aGroup.getName().contains("ETI") ) {
//							System.out.println("Do you want to publish on this group " + aGroup.getName() + "?");
//							String answer = s.nextLine();
//							if (answer.equalsIgnoreCase("Yes")) {
//								System.out.println("Post or upload file?");
//								String resposta = s.nextLine();
//								if (resposta.equalsIgnoreCase("post")) {
//									System.out.println("Write your post");
//									String post = s.nextLine();
//									FacebookType response = fbClient.publish(aGroup.getId() + "/feed", FacebookType.class,Parameter.with("message", post));
//									System.out.println("fb.com"+ response.getId());
//								}
//								if (resposta.equalsIgnoreCase("upload file")) {
//									System.out.println("Write file location");
//									String attachmentName = s.nextLine();
//									System.out.println("Write file name");
//									String name = s.nextLine();
//									System.out.println("Write description");
//									String description = s.nextLine();
//									FileInputStream fis;
//									try {
//										fis = new FileInputStream (new File (attachmentName));
//										FacebookType publish = fbClient.publish(aGroup.getId() + "/photos", FacebookType.class,
//												BinaryAttachment.with(name, fis), Parameter.with("message", description));
//									} catch (FileNotFoundException e) {
//										e.printStackTrace();
//									}
//
//								}
//
//							}if (answer.equalsIgnoreCase("fim")) {
//								System.out.println(result);
//								System.exit(0);
//							}
//						}
//					}
//				}
//			}



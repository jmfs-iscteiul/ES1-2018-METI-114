package facebook;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;

import common.standardInfoStruct;

/**
 * Esta classe permite-nos ir buscar à timeline do utilizador posts que contenham palavras chave indicadas pelo utilizador.
 * @author ricardo
 *
 */

public class Timeline {

	private String accessToken;
	private FacebookClient fbClient;
	private Connection<Group> result1;
	private Connection<Post> result;
	private String filter= "";

	public Timeline (String accessToken) {
		this.accessToken = accessToken;
		fbClient = new DefaultFacebookClient(accessToken);
	}


	/**
	 * Altera o parâmetro filtro para o escolhido pelo utilizador.
	 * @param filter String com o filtro escolhido pelo utilizador.
	 */

	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Função que devolve o valor da string filter
	 * @return String filter
	 */

	public String getFilter() {
		return filter;
	}
	/**
	 * Esta função vai buscar os posts à timeline do utilizador, tendo em conta o filtro forneido pelo mesmo.
	 * @return Devolve uma lista com os posts.
	 */

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
					standardInfoStruct sis = new standardInfoStruct(aPost.getCreatedTime(), aPost.getName(), aPost.getMessage());
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


}

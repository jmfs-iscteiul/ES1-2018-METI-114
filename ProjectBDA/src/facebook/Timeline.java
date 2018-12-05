package facebook;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

import common.standardInfoStruct;

/**
 * Esta classe permite-nos ir buscar à timeline do utilizador posts que contenham palavras chave indicadas pelo utilizador.
 * @author ricardo
 *
 */

public class Timeline {

	private FacebookClient fbClient;
	private Connection<Post> result;
	private String filter= "";

	@SuppressWarnings("deprecation")
	public Timeline (String accessToken) {
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
	 * Esta função vai buscar os posts à timeline do utilizador, tendo em conta o filtro fornecido pelo mesmo.
	 * @return Devolve uma lista com os posts.
	 */
	public ArrayList <standardInfoStruct> getTimeline () {
		ArrayList<standardInfoStruct> lista = new ArrayList<standardInfoStruct> ();
		result = fbClient.fetchConnection("me/feed",Post.class);	
		
		for (List<Post> page : result) {
			for (Post aPost : page) {
				// Filters only posts that contain the word "Inform"
				if (aPost.getMessage() != null && aPost.getMessage().contains(filter)) {
					standardInfoStruct sis = new standardInfoStruct(aPost.getCreatedTime(), aPost.getName(), aPost.getMessage());
					lista.add(sis);
				}
			}
		}
		return lista;
	}
}

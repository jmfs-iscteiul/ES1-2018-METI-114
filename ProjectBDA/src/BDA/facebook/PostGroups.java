package BDA.facebook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.BinaryAttachment;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;

/**
 * Esta classe como o nome indica permite ao utilizador publicar em grupos do facebook em que somos administradores.
 * @author ricardo
 *
 */
public class PostGroups {


	private FacebookClient fbClient;
	private Connection<Group> result;
	private Map <String,String> Grupo;


	@SuppressWarnings("deprecation")
	public PostGroups (String accessToken) {
		fbClient = new DefaultFacebookClient(accessToken);
		result = fbClient.fetchConnection("me/groups",Group.class);
		Grupo = new HashMap <String, String> ();
	}


	/**
	 * Esta função fornecendo o grupo onde queremos partilhar e o texto que queremos partilhar, permite-nos fazer um post de texto nesse grupo.
	 * @param nomeGrupo String com nome do Grupo em que queremos publicar.
	 * @param message String com o post que queremos fazer.
	 */
	public void postarTexto (String nomeGrupo, String message) {
		String post = message;
		fbClient.publish(Grupo.get(nomeGrupo) + "/feed", FacebookType.class,Parameter.with("message", post));
	}

	/**
	 * Fornecendo os parâmetros indicados abaixo permite publicar no grupo uma foto e descrição para a mesma.
	 * 
	 * @param caminho String com caminho da imagem.
	 * @param nome String com nome da imagem.
	 * @param descricao String com o texto que queremos postar como descrição da imagem.
	 * @param nomeGrupo String com o nome do grupo onde queremos partilhar.
	 */
	@SuppressWarnings("deprecation")
	public void postarimagem (String caminho, String nome, String descricao, String nomeGrupo) {

		String attachmentName = caminho;
		String name = nome;
		String description = descricao;
		FileInputStream fis;
		try {
			fis = new FileInputStream (new File (attachmentName));
			fbClient.publish(Grupo.get(nomeGrupo) + "/photos", FacebookType.class,
					BinaryAttachment.with(name, fis), Parameter.with("message", description));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

	/**
	 * Getter do result
	 * 
	 * @return Lista com os grupos
	 */
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
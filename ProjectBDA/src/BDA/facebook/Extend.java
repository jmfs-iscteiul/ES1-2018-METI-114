package BDA.facebook;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;


/**
 * Esta classe tem como função, fornecendo um acesstoken do facebook, fornecer um access token com período de validade alargado.
 * 
 * @author rmcmc-iscteiul
 */
public class Extend {
	private String accessToken = "";
	FacebookClient fbClient4;

	@SuppressWarnings("deprecation")
	public Extend (String accessToken) {
		this.accessToken = accessToken;
		fbClient4 = new DefaultFacebookClient(accessToken);
	}

	/**
	 * Função que imprime o token com validade alargada no ficheiro txt.
	 * 
	 * @return devolve a String do token alargado.
	 */
	public String getExtension () {
		AccessToken extendedAccessToken4 = fbClient4.obtainExtendedAccessToken("313621502554589","f2cc137fdfa12d9e959fec3e7be184e9");
		PrintWriter out;
		try {
			out = new PrintWriter("tokens.txt");
			out.println("Facebook Token " + "\n" + extendedAccessToken4.getAccessToken());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return extendedAccessToken4.getAccessToken();
	}
}

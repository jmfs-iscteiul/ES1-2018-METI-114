package facebook;

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
	
	public Extend () {
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
	
//	public static void main(String[] args) {
//		String accessToken = "EAAEdPLJA8d0BAE7rgGTkhPdfjtEz7kimJHsSJKqj8RR4v7ErVSRft53OyLZBbed8odScy5fZBTZA9e1R4reX4gDa1pA4RtapKGIzUavVZBsFFYQn37ZASV1IKZC27VBfqo9TG9IDk7br9p8We52RnEInHovoCG16gZD";
//		FacebookClient fbClient4 = new DefaultFacebookClient(accessToken);
//		AccessToken extendedAccessToken4 = fbClient4.obtainExtendedAccessToken("313621502554589","f2cc137fdfa12d9e959fec3e7be184e9");
//		System.out.println("ExtendedAccessToken: "+extendedAccessToken4.getAccessToken());
//		System.out.println("Expires: " + extendedAccessToken4.getExpires());
//		PrintWriter out;
//		try {
//			out = new PrintWriter("tokens.txt");
//			out.println("Facebook Token " + "\n" + extendedAccessToken4);
//			out.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
}

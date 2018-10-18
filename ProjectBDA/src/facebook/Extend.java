package facebook;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;

public class Extend {

	public static void main(String[] args) {
		String accessToken = "";
		FacebookClient fbClient4 = new DefaultFacebookClient(accessToken);
		AccessToken extendedAccessToken4 = fbClient4.obtainExtendedAccessToken("313621502554589","f2cc137fdfa12d9e959fec3e7be184e9");
		System.out.println("ExtendedAccessToken: "+extendedAccessToken4.getAccessToken());
		System.out.println("Expires: " + extendedAccessToken4.getExpires());
		PrintWriter out;
		try {
			out = new PrintWriter("tockens.txt");
			out.println("Facebook Token " + "\n" + extendedAccessToken4);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}

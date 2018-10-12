package facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;

public class Extend {

	public static void main(String[] args) {
		String accessToken = "EAAEdPLJA8d0BADGWiC5vxbZA9YfLN8XRpgTDnxd9zd3A4O7doNHfjnRUVnDX1PJNWXkmQyLi4hZBlqP4EuHs71eZAZAX4DjozqZARhJ181ZAwP71pYz2ZCLzmkZBbfZCk8PuZAvP1HjaCtJGgcVtGXAXQBZBl3R0nyPPU6wZB10ZCjqhFc5DVNiSzFIALytZCEKlW66Muqwm4nBcVdjwZDZD";
		FacebookClient fbClient4 = new DefaultFacebookClient(accessToken);
		AccessToken extendedAccessToken4 = fbClient4.obtainExtendedAccessToken("313621502554589","f2cc137fdfa12d9e959fec3e7be184e9");
		System.out.println("ExtendedAccessToken: "+extendedAccessToken4.getAccessToken());
		System.out.println("Expires: " + extendedAccessToken4.getExpires());
	}
	
}

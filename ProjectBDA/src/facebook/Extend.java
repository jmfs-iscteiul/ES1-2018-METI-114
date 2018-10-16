package facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;

public class Extend {

	public static void main(String[] args) {
		String accessToken = "EAAEdPLJA8d0BAJZBOdexmPR426ZAOTetFhhHqkjQ36gZC3plBZAyuKDl4UppYNR5qhU6r9eIxmNkhWaEbkHwmmHYmpxJXFCIAVgk6L9ZCtQvvDd3ZAhF6JMEKFlISWDPiPaddeZAj9t1JE500huRZAkMRrruW6tUYchEs2q6b7bKOEtO1SZBKGeZCnzQ4xQEcJ7yZAZBroaGscKvVgZDZD";
		FacebookClient fbClient4 = new DefaultFacebookClient(accessToken);
		AccessToken extendedAccessToken4 = fbClient4.obtainExtendedAccessToken("313621502554589","f2cc137fdfa12d9e959fec3e7be184e9");
		System.out.println("ExtendedAccessToken: "+extendedAccessToken4.getAccessToken());
		System.out.println("Expires: " + extendedAccessToken4.getExpires());
	}
	
}

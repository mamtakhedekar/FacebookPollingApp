package edu.stevens.cs581.yelp;

import java.util.logging.Logger;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;
import com.yelp.v2.Business;
import com.yelp.v2.YelpSearchResult;

public class YelpAPI {

	private static Logger log = Logger.getLogger("edu.stevens.cs581.YelpAPI");
	
	private OAuthService service ;
	private Token accessToken;
	public YelpAPI()
	{
	
		// Define your keys, tokens and secrets.  These are available from the Yelp website.  
		String CONSUMER_KEY = "S9L0ugC17d5PH1rCw2oZhQ";
		String CONSUMER_SECRET = "GDf6iDnACSYdO0yXwmO-VmoIIEI";
		String TOKEN = "NFpJRl9Nd0PTwFRXB4w76UXoY38inDvU";
		String TOKEN_SECRET = "F6Xh2uG473zluWPKAcAdM-_rht4";
		// Execute a signed call to the Yelp service.  
		service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
		accessToken = new Token(TOKEN, TOKEN_SECRET);
	}
	
	public String searchYelp(String location, String searchString)
	{
		String result = "";

		
		// Some example values to pass into the Yelp search service.  
		/*
		String lat = "30.361471";
		String lng = "-87.164326";
		String category = "Hardware Store";
		*/
		
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		//request.addQuerystringParameter("ll", lat + "," + lng);
		request.addQuerystringParameter("location", location);
		request.addQuerystringParameter("category", searchString);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String rawData = response.getBody();
		 
		// Sample of how to turn that text into Java objects.  
		try {
			YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class);
			
			log.info("YelpAPI: Your search found " + places.getTotal() + " results.");
			log.info("YelpAPI: Yelp returned " + places.getBusinesses().size() + " businesses in this request.");
			
			for(Business biz : places.getBusinesses()) {
				result += ("\n Name: " + (biz.getName()));
				for(String address : biz.getLocation().getAddress()) {					
					result += ("\n Address: " + address);
				}
				result += ("\n Location: " + biz.getLocation().getCity());
				result += ("\n WebAddressURL : " + biz.getUrl());
				result += ("\n Rating : " + biz.getRatingImgUrlSmall() == null ? "Not Rated" : biz.getRatingImgUrlSmall());
			}
			
			
		} catch(Exception e) {
			log.info("Error, could not parse returned data!");
			log.info(rawData);			
		}
		
		return result;
	}
}

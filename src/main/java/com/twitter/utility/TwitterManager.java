package com.twitter.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.twitter.domain.TwitterData;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterManager {
	
	ConfigurationBuilder cb;
	Twitter twitter;
	
	long maxID = -1;
	private static final int MAX_QUERIES = 100;

	//	Always nice to see these things when debugging code...
	public TwitterManager() {
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("");
		cb.setOAuthConsumerSecret("");
		cb.setOAuthAccessToken("");
		cb.setOAuthAccessTokenSecret("");
		
		twitter = new TwitterFactory(cb.build()).getInstance();
	}

	public List performQuery(String inQuery) throws InterruptedException, IOException, TwitterException {
		
		Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");

		RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");
		
		System.out.printf("You have %d calls remaining out of %d, Limit resets in %d seconds\n",
				  searchTweetsRateLimit.getRemaining(),
				  searchTweetsRateLimit.getLimit(),
				  searchTweetsRateLimit.getSecondsUntilReset());
		
		Query query = new Query(inQuery);
		query.setCount(100);
		query.setLang("en");
		List twitterSearchList = new ArrayList();
	    GeoLocation location = new GeoLocation(41.627016, -99.704764); //latitude, longitude
        Unit unit = Query.MILES;
        query.setGeoCode(location, 1000, unit); 

		
		try {
			
			QueryResult r;
			for(int queryNumber=0; queryNumber < MAX_QUERIES; queryNumber++)
			{
				if (searchTweetsRateLimit.getRemaining() == 0)
				{
					System.out.printf("!!! Sleeping for %d seconds due to rate limits\n", searchTweetsRateLimit.getSecondsUntilReset());

					Thread.sleep((searchTweetsRateLimit.getSecondsUntilReset()+2) * 10000);
				}
				
				if (maxID != -1)
				{
					query.setMaxId(maxID - 1);
				}
				
				r = twitter.search(query);
				
				if (r.getTweets().size() == 0)
				{
					break;			
				}

				for (Status s: r.getTweets())	
				{
				twitterSearchList.add(s);
				
				if (maxID == -1 || s.getId() < maxID)
				{
					maxID = s.getId();
				}
			}
				searchTweetsRateLimit = r.getRateLimitStatus();
		} 	
	}
		
		catch (TwitterException te) {
			System.out.println("Couldn't connect: " + te);
		}
		return twitterSearchList;
		
	}
	
	
	
}

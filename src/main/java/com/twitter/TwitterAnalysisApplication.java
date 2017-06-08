package com.twitter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twitter.service.TwitterDataService;
import com.twitter.utility.TwitterManager;

@SpringBootApplication
public class TwitterAnalysisApplication implements CommandLineRunner{
	
	@Autowired
	private TwitterDataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(TwitterAnalysisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		TwitterManager manager = new TwitterManager();
		
		List tweets = manager.performQuery("#CMTawards");
		
		dataService.addAllData(tweets);
		
	}
}

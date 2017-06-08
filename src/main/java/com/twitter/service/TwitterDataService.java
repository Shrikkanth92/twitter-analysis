package com.twitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.domain.TwitterData;
import com.twitter.repository.TwitterDataRepository;
import com.twitter.utility.NLP;

import twitter4j.Status;

@Service
public class TwitterDataService {
	
	@Autowired
	private TwitterDataRepository twitterDataRepository;
	
	
	public void addAllData(List twitterSearchList){
		NLP.init();
		
		for (int i = 0; i < twitterSearchList.size(); i++) {
			TwitterData data = new TwitterData();
			Status t = (Status) twitterSearchList.get(i);
			data.setText(t.getText());
			data.setUserName(t.getUser().getScreenName());
			data.setLocation(t.getUser().getLocation());
			data.setSentimentScore(NLP.findSentiment(t.getText()));
			data.setCreatedDate(t.getCreatedAt());
			if(data.getLocation() != null && !data.getLocation().equals(""))
				twitterDataRepository.save(data);
	}
		
	
	}
}
	
	

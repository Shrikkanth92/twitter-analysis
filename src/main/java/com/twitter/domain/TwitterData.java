package com.twitter.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import twitter4j.HashtagEntity;


@Entity
public class TwitterData implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String userName;
	private Date createdDate;
	private String text;
	private String location;
	private int sentimentScore;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getSentimentScore() {
		return sentimentScore;
	}
	public void setSentimentScore(int sentimentScore) {
		this.sentimentScore = sentimentScore;
	}
	
}

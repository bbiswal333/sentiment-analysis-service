package com.tools.model;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class SentimentAnalysis {
	
	private String text;
	private int score;
	private String scoreText;
	private String rating;
	private int ratingScale;
	private List<SentenceSentiment> sentenceSentimentList;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getScoreText() {
		return scoreText;
	}
	public void setScoreText(String scoreText) {
		this.scoreText = scoreText;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getRatingScale() {
		return ratingScale;
	}
	public void setRatingScale(int ratingScale) {
		this.ratingScale = ratingScale;
	}
	public List<SentenceSentiment> getSentenceSentimentList() {
		return sentenceSentimentList;
	}
	public void setSentenceSentimentList(List<SentenceSentiment> sentenceSentimentList) {
		this.sentenceSentimentList = sentenceSentimentList;
	}



	int sumOfScore = 0;
	int totalScore = 0;
	
	public void resetFlags(){
		this.sumOfScore = 0;
		this.totalScore = 0;
	}
	
	public String getRating(Map<Integer,Integer> inputs,int ratingScale){
		//reseting all flags
		resetFlags();
		//calculating avg rating by default rating scale
		for(Map.Entry<Integer, Integer> entry : inputs.entrySet()){
			int key = entry.getKey();
			int val = entry.getValue();
			this.sumOfScore += key * val;
			this.totalScore += val;
		}
		//formatter
		DecimalFormat numberFormat = new DecimalFormat("#.0");
		double res = sumOfScore / totalScore;
		
		//default scale = 5
		double percentage = ( res / 5 ) * 100;
		
		res = (percentage * ratingScale ) / 100;
		
		return numberFormat.format(res);
	}
	
	public int getOverallScore(Map<Integer,Integer> inputs){
		//reseting all flags
		resetFlags();
		//calculating avg score
		for(Map.Entry<Integer, Integer> entry : inputs.entrySet()){
			int key = entry.getKey();
			int val = entry.getValue();
			this.sumOfScore += key * val;
			this.totalScore += val;
		}
		if(Math.round(sumOfScore / totalScore) <= 0){
			return 1;
		}
		if(Math.round(sumOfScore / totalScore) >= 5 ){
			return 5;
		}
		return Math.round(sumOfScore / totalScore);
	}
	
	public String getOverAllScoreText(int score){
		String scoreText = "";
		switch(score){
			case 1:
				scoreText = "Very Negative";
	            break;
			case 2:
				scoreText = "Negative";
				break;
			case 3:
				scoreText = "Neutral";
				break;
			case 4:
				scoreText = "Positive";
				break;
			case 5:
				scoreText = "Very Positive";
				break;
		}
		return scoreText;
		
	}
	
}

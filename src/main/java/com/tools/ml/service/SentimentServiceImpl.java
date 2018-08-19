package com.tools.ml.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tools.model.SentenceSentiment;
import com.tools.model.SentimentAnalysis;

@Service
public class SentimentServiceImpl implements SentimentService {

	@Autowired
	private NLPService nlpService;
	
	private Map<Integer,Integer> inputs;
	
	//
	final int _VERY_NEGATIVE_ = 1;
	final int _NEGATIVE_ = 2;
	final int _NEUTRAL_ = 3;
	final int _POSITIVE_ = 4;
	final int _VERY_POSITIVE_ = 5;
	
	@Override
	public SentimentAnalysis getSentimentAnalysis(String text) {
		List<SentenceSentiment> sentenceSentimentList = NLPService.computeSentiment(text);
		
		//creating inputs
		inputs = new HashMap<Integer,Integer>();
		inputs.put(_VERY_NEGATIVE_, 0);
		inputs.put(_NEGATIVE_, 0);
		inputs.put(_NEUTRAL_, 0);
		inputs.put(_POSITIVE_, 0);
		inputs.put(_VERY_POSITIVE_, 0);
		
		for(SentenceSentiment s : sentenceSentimentList){
			switch(s.getScore()){
				case 0:
					inputs.put(_VERY_NEGATIVE_,inputs.get(_VERY_NEGATIVE_) + 1);
					break;
				case 1:
					inputs.put(_NEGATIVE_,inputs.get(_NEGATIVE_) + 1);
					break;
				case 2:
					inputs.put(_NEUTRAL_,inputs.get(_NEUTRAL_) + 1);
					break;
				case 3:
					inputs.put(_POSITIVE_,inputs.get(_POSITIVE_) + 1);
					break;
				case 4:
					inputs.put(_VERY_POSITIVE_,inputs.get(_VERY_POSITIVE_) + 1);
					break;
					
			}
		
		}
		
		//creating Sentiment Analysis
		SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
		
		sentimentAnalysis.setSentenceSentimentList(sentenceSentimentList);
		sentimentAnalysis.setScore(sentimentAnalysis.getOverallScore(inputs));
		sentimentAnalysis.setScoreText(sentimentAnalysis.getOverAllScoreText(sentimentAnalysis.getOverallScore(inputs)));
		sentimentAnalysis.setRating(sentimentAnalysis.getRating(inputs, 5));
		sentimentAnalysis.setRatingScale(5);
		
		return sentimentAnalysis;
	}

}

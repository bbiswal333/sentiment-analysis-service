package com.tools.ml.service;

import org.springframework.stereotype.Service;

import com.tools.model.SentimentAnalysis;

@Service
public interface SentimentService {
	
	SentimentAnalysis getSentimentAnalysis(String text);
}

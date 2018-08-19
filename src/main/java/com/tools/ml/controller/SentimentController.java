package com.tools.ml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tools.ml.service.SentimentService;
import com.tools.model.Sentence;
import com.tools.model.SentimentAnalysis;

@RestController
@RequestMapping({"/sentiment.svc"})
public class SentimentController {

	protected static final String V1_PATH = "/api/v1";
	
	@Autowired
	private SentimentService sentimentService;
	
	@PostMapping("/sentiment/analysis")
	public ResponseEntity<SentimentAnalysis> getSentimentAnalysis(
			@RequestBody final Sentence sentence ){
		
		SentimentAnalysis sentimentAnalysis = sentimentService.getSentimentAnalysis(sentence.getText());
		if(sentimentAnalysis != null)
			return new ResponseEntity<SentimentAnalysis>(sentimentAnalysis, HttpStatus.OK);
		return errorMessage("Somethin wrong happened!", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	/**
	 * @param message
	 * @param status
	 * @return ResponseEntity with HTTP status,headers and body
	 */
	private ResponseEntity errorMessage(String message, HttpStatus status) {
		return ResponseEntity.status(status).body(message);
	}

}
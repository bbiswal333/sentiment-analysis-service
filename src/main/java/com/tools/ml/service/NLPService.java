package com.tools.ml.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.tools.model.SentenceSentiment;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

@Service
public class NLPService {
	
	static StanfordCoreNLP pipeline;
	
	@PostConstruct
	public static void init()  {
    	 // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        props.setProperty("coref.algorithm", "neural");
        pipeline = new StanfordCoreNLP(props);
    }
	
	public static List<SentenceSentiment> computeSentiment(String text) {
		List<SentenceSentiment> sList = new ArrayList<SentenceSentiment>();
 		int score = 2; // Default as Neutral. 1 = Negative, 2 = Neutral, 3 = Positive
    	String scoreStr; 
    	Annotation annotation = pipeline.process(text);
    	for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
    	{
    		scoreStr = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
    		Tree tree = sentence.get(SentimentAnnotatedTree.class);
    		score = RNNCoreAnnotations.getPredictedClass(tree);
    		
    		//creating sentence sentiment object
    		SentenceSentiment sentenceSentiment = new SentenceSentiment();
    		sentenceSentiment.setSentence(sentence.toString());
    		sentenceSentiment.setScore(score);
    		sentenceSentiment.setScoreText(scoreStr);
    		
    		//pushing to list
    		sList.add(sentenceSentiment);
    		
    		//System.out.println(scoreStr + "\t" + score + "\t" + sentence);
    	}
    	return(sList);
    }
}

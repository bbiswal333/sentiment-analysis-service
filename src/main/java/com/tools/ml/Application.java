package com.tools.ml;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

		/**
    	 * This is the main method.
    	 * 
    	 * @param args
    	 */
    	public static void main(String[] args){
    		new SpringApplicationBuilder(Application.class)
    		.run(args);
    	}
}
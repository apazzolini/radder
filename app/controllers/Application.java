package controllers;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;

public class Application extends Controller {
	

    public static void index() {
    	/*
    	Player one = new Player();
    	one.firstName = "Andre";
    	one.lastName = "Azzolini";
    	one.save();
    	
    	one = new Player();
    	one.firstName = "John";
    	one.lastName = "Doe";
    	one.save();
    	*/
    	
    	
    	
        render();
    }
    
    public static void showRule(String rule) {
    	if (StringUtils.isBlank(rule)) {
    		rule = "index";
    	}
        renderTemplate("Rules/" + rule + ".html");
    }

}
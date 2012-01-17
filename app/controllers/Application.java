package controllers;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;

public class Application extends Controller {
	

    public static void index() {
        render();
    }
    
    public static void showRule(String rule) {
    	if (StringUtils.isBlank(rule)) {
    		rule = "index";
    	}
        renderTemplate("Rules/" + rule + ".html");
    }

}
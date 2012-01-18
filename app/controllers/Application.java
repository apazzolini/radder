package controllers;

import java.util.List;

import models.Player;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;

public class Application extends Controller {
	

    public static void index() {
    	List<Player> players = Player.find("order by rating desc").fetch();
    	render(players);
        render();
    }
    
    public static void showRule(String rule) {
    	if (StringUtils.isBlank(rule)) {
    		rule = "index";
    	}
        renderTemplate("Rules/" + rule + ".html");
    }

}
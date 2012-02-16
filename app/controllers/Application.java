package controllers;

import java.util.List;

import models.Comment;
import models.Game;
import models.Player;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;

public class Application extends Controller {
	
    public static void index() {
    	List<Player> players = Player.find("order by rating desc").fetch();
    	List<Game> games = Game.findAllResults().fetch();
    	List<Comment> comments = Comment.find("game is null order by time desc").fetch();
    	List<Game> challenges = null;
    	if (session.get("userid") != null) { 
    		Long currentUserId = Long.parseLong(session.get("userid"));
    		challenges = Game.findUserChallenges(currentUserId).fetch();
    	}
    	render(players, games, challenges, comments);
    }
    
    public static void showRule(String rule) {
    	if (StringUtils.isBlank(rule)) {
    		rule = "rating";
    	}
        renderTemplate("Rules/" + rule + ".html");
    }

}
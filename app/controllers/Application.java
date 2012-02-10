package controllers;

import java.util.List;

import javax.inject.Inject;

import models.Comment;
import models.Game;
import models.Player;

import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.LdapTemplate;

import play.mvc.Controller;

public class Application extends Controller {
	
	@Inject private static LdapTemplate ldap;
	
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
//    	String username = "aazzolini";
//        EqualsFilter filter = new EqualsFilter("uid", username);
//    	boolean authenticated = ldap.authenticate(DistinguishedName.EMPTY_PATH, filter.encode(), password);
//    	System.out.println("User " + username + " " + authenticated);
    	
    	if (StringUtils.isBlank(rule)) {
    		rule = "rating";
    	}
        renderTemplate("Rules/" + rule + ".html");
    }

}
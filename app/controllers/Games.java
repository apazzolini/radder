package controllers;

import java.util.List;

import models.Game;
import models.Player;

public class Games extends CRUD {
	
    public static void challenge() {
    	List<Player> players = Player.find("email != ? order by rating desc", session.get("username")).fetch();
    	render(players);
    }
    
    public static void createChallenge(String email) {
    	Game game = new Game();
    	game.one = Player.find("byEmail", session.get("username")).first();
    	game.two = Player.find("byEmail", email).first();
    	game.save();
    	redirect("/");
    }
    
    public static void results() {
    	List<Game> games = Game.findAll();
    	render(games);
    }
    
    public static void resultsForUser(String email) {
    	List<Game> games = Game.find("one.email = ? or two.email = ? order by timeResultRecorded desc", email, email).fetch();
    	renderArgs.put("singleUser", true);
    	renderTemplate("Games/results.html", games);
    }

}

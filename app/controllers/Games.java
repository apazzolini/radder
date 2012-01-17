package controllers;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import models.Game;
import models.Player;

public class Games extends CRUD {
	
    public static void challenge() {
    	List<Player> players = Player.findAll();
    	Collections.sort(players, Player.ratingComparator());
    	ListIterator<Player> it = players.listIterator();
    	while (it.hasNext()) {
    		Player player = it.next();
    		if (player.email.equals(session.get("username"))) {
    			it.remove();
    		}
    	}
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
    
    public static void myGames() {
    	List<Game> games = Game.findAll();
    	render(games);
    }

}

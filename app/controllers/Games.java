package controllers;

import java.util.Date;
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
    	game.timeChallengeSent = new Date();
    	game.save();
    	redirect("/");
    }
    
    public static void logResult(Long gameId, Integer oneScore, Integer twoScore) {
    	Game game = Game.findById(gameId);
    	
    	game.oneScore = oneScore;
    	game.twoScore = twoScore;
    	game.winner = (oneScore > twoScore) ? 1 : 2;
    	game.timeResultRecorded = new Date();
    	
    	Player winner = (game.winner == 1) ? game.one : game.two;
    	Player loser = (game.winner == 1) ? game.two : game.one;
    	winner.wonAgainst(loser);
    	loser.lostTo(winner);
    	
    	game.save();
    	winner.save();
    	loser.save();
    	
    	redirect("/");
    }
    
    public static void results() {
    	List<Game> games = Game.findAll();
    	render(games);
    }
    
    public static void resultsForUser(String email) {
    	List<Game> games = Game.find("one.email = ? or two.email = ? order by timeResultRecorded desc", email, email).fetch();
    	renderArgs.put("singleUser", true);
    	if (email.equals(session.get("username"))) {
    		renderTemplate("Games/myResults.html", games);
    	} else {
    		renderTemplate("Games/results.html", games);
    	}
    }

}

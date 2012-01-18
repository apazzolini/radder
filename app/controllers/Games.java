package controllers;

import java.util.Date;
import java.util.List;

import models.Game;
import models.Player;

public class Games extends CRUD {
	
    public static void createChallenge(String email) {
    	Game game = new Game();
    	game.one = Player.find("byEmail", session.get("username")).first();
    	game.two = Player.find("byEmail", email).first();
    	game.timeChallengeSent = new Date();
    	game.save();
    	resultsForUser(session.get("username"));
    }
    
    public static void logResult(Long gameId, Integer oneScore, Integer twoScore) {
    	Game game = Game.findById(gameId);
    	
    	game.oneScore = oneScore;
    	game.twoScore = twoScore;
    	game.winner = (oneScore > twoScore) ? 1 : 2;
    	game.timeResultRecorded = new Date();
    	
    	Player winner = (game.winner == 1) ? game.one : game.two;
    	Player loser = (game.winner == 1) ? game.two : game.one;
    	winner.games.add(game);
    	loser.games.add(game);
    	
    	winner.wonAgainst(loser, game);
    	
    	game.save();
    	winner.save();
    	loser.save();
    	
    	renderTemplate("Games/gameResult.html", game);
    }
    
    public static void results() {
    	List<Game> games = Game.findAll();
    	render(games);
    }
    
    public static void resultsForUser(String email) {
    	List<Game> games = Game.find("one.email = ? or two.email = ? order by timeResultRecorded desc", email, email).fetch();
    	renderArgs.put("resultsUser", Player.find("byEmail", email).first());
    	renderTemplate("Games/results.html", games);
    }

}

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
    	
    	validation.range(oneScore, 0, 99);
    	validation.range(twoScore, 0, 99);
    	
    	if (validation.hasErrors() || oneScore.equals(twoScore)) {
    		renderTemplate("Games/gameResult.html", game);
    	}
    	
    	if (!game.one.email.equals(session.get("username")) && !game.two.email.equals(session.get("username"))) {
    		renderTemplate("Games/gameResult.html", game);
    	}
    	
    	game.oneScore = oneScore;
    	game.twoScore = twoScore;
    	game.winner = (oneScore > twoScore) ? 1 : 2;
    	game.timeResultRecorded = new Date();
    	
    	Player winner = (game.winner == 1) ? game.one : game.two;
    	Player loser = (game.winner == 1) ? game.two : game.one;
    	
    	winner.wonAgainst(loser, game);
    	
    	game.save();
    	winner.save();
    	loser.save();
    	
    	renderTemplate("Games/gameResult.html", game);
    }
    
    public static void results() {
    	List<Game> unplayedGames = Game.find("winner is null order by timeChallengeSent asc").fetch();
    	List<Game> playedGames = Game.find("winner is not null order by timeResultRecorded desc").fetch();
    	render(unplayedGames, playedGames);
    }
    
    public static void resultsForUser(String email) {
    	List<Game> unplayedGames = Game.find("(one.email = ? or two.email = ?) and winner is null order by timeChallengeSent asc", email, email).fetch();
    	List<Game> playedGames = Game.find("(one.email = ? or two.email = ?) and winner is not null order by timeResultRecorded desc", email, email).fetch();
    	renderArgs.put("resultsUser", Player.find("byEmail", email).first());
    	renderTemplate("Games/results.html", unplayedGames, playedGames);
    }

}

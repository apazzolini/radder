package controllers;

import java.util.Date;
import java.util.List;

import models.Comment;
import models.Game;
import models.Player;

public class Games extends CRUD {
	
    public static void challenge(Long userid) {
    	Long currentUserId = Long.parseLong(session.get("userid"));
    	Game game = new Game();
    	game.one = Player.find("byId", currentUserId).first();
    	game.two = Player.find("byId", userid).first();
    	game.timeChallenged = new Date();
    	game.save();
    	renderText("OK");
    }
    
    public static void logResult(Long gameId, Integer oneScore, Integer twoScore) {
    	Long currentUserId = Long.parseLong(session.get("userid"));
    	Game game = Game.findById(gameId);
    	
    	if (!Game.isValidResult(oneScore, twoScore)) {
    		renderText("Invalid score");
    	}
    	
    	if (!(game.one.id.equals(currentUserId) || game.two.id.equals(currentUserId))) {
    		renderText("You were not a part of this game");
    	}
    	
    	if (!(game.oneScore == null && game.twoScore == null)) {
    		flash.put("challengesMessage", "Result already submitted - approve or reject it");
    		renderText("REFRESH");
    	}
    	
    	game.oneScore = oneScore;
    	game.twoScore = twoScore;
    	game.winner = (oneScore > twoScore) ? 1 : 2;
    	game.timePlayed = new Date();
    	game.scoreReporter = game.one.id.equals(currentUserId) ? 1 : 2;
    	
    	Double beforeOneRating = game.one.rating;
    	Double beforeTwoRating = game.two.rating;
    	
    	Player winner = (game.winner == 1) ? game.one : game.two;
    	Player loser = (game.winner == 1) ? game.two : game.one;
    	
    	winner.wonAgainst(loser, game);
    	
    	game.onePointsChange = game.one.rating - beforeOneRating;
    	game.twoPointsChange = game.two.rating - beforeTwoRating;
    	
    	game.save();
    	winner.save();
    	loser.save();
    	
    	renderText("OK");
    }
    
    public static void approveResult(Long gameId) {
    	Long currentUserId = Long.parseLong(session.get("userid"));
    	Game game = Game.findById(gameId);
    	
    	if (!(game.one.id.equals(currentUserId) || game.two.id.equals(currentUserId))) {
    		renderText("You were not a part of this game");
    	}
    	
    	game.scoreApproved = true;
    	
    	game.save();
    	
    	renderText("OK");
    }
    
    public static void rejectResult(Long gameId) {
    	Long currentUserId = Long.parseLong(session.get("userid"));
    	Game game = Game.findById(gameId);
    	
    	if (!(game.one.id.equals(currentUserId) || game.two.id.equals(currentUserId))) {
    		renderText("You were not a part of this game");
    	}
    	
    	Player one = game.one;
    	Player two = game.two;
    	
    	one.rating = one.rating - game.onePointsChange;
    	two.rating = two.rating - game.twoPointsChange;
    	
    	game.oneScore = null;
    	game.twoScore = null;
    	game.winner = null;
    	game.scoreReporter = null;
    	
    	game.save();
    	one.save();
    	two.save();
    	
    	renderText("OK");
    }
    
    public static void comment(Long gameid, String comment) {
    	Long currentUserId = Long.parseLong(session.get("userid"));
    	Game game = Game.findById(gameid);
    	
    	Comment newComment = new Comment();
    	newComment.comment = comment;
    	newComment.time = new Date();
    	newComment.player = Player.findById(currentUserId);
    	newComment.game = game;
    	
    	game.comments.add(newComment);
    	
    	newComment.save();
    	game.save();
    	renderText("kk");
    }
    
    public static void allChallenges() {
    	List<Game> games = Game.findAllChallenges().fetch();
    	renderArgs.put("challengesFor", "all");
    	renderTemplate("tags/resultsTable.html", games);
    }
    
    public static void userChallenges(Long userid) {
    	List<Game> games = Game.findUserChallenges(userid).fetch();
    	renderArgs.put("challengesFor", Player.find("byId", userid).first());
    	renderTemplate("tags/resultsTable.html", games);
    }
    
    public static void allResults() {
    	List<Game> games = Game.findAllResults().fetch();
    	renderArgs.put("resultsFor", "all");
    	renderTemplate("tags/resultsTable.html", games);
    }
    
    public static void userResults(Long userid) {
    	List<Game> games = Game.findUserResults(userid).fetch();
    	renderArgs.put("resultsFor", Player.find("byId", userid).first());
    	renderTemplate("tags/resultsTable.html", games);
    }

}

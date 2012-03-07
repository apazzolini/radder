package controllers;

import java.util.List;

import models.Comment;
import models.Game;
import models.Player;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		List<Player> players = Player.find("hasPlayedGame = ? order by rating desc", true).fetch();
		List<Player> newPlayers = Player.find("hasPlayedGame is null or hasPlayedGame != ? order by rating desc", true).fetch();

		for (Player newPlayer : newPlayers) {
			players.add(newPlayer);
		}

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

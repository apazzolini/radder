package models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Game extends Model {
	@ManyToOne public Player one;
	@ManyToOne public Player two;
	
	/**
	 * If this is null, the game has not yet been played. Otherwise, it will hold 1 or 2 depending on which player won
	 */
	public Integer winner;
	
	public Integer oneScore;
	public Integer twoScore;
	
	public Double onePointsChange;
	public Double twoPointsChange;
	
	public Date timeChallenged;
	public Date timePlayed;
	
	public static JPAQuery findAllChallenges() {
    	return Game.find("winner is null order by timeChallenged asc");
    }
    
	public static JPAQuery findUserChallenges(Long userid) {
    	return Game.find("(one.id = ? or two.id = ?) and winner is null order by timeChallenged asc", userid, userid);
    }
    
	public static JPAQuery findAllResults() {
    	return Game.find("winner is not null order by timePlayed desc");
	}
	
	public static JPAQuery findUserResults(Long userid) {
    	return Game.find("(one.id = ? or two.id = ?) and winner is not null order by timePlayed desc", userid, userid);
	}
	
	public Player getWinningPlayer() {
		if (winner == null) { 
			return null;
		}
		
		return (winner == 1) ? one : two;
	}
	
	public Player getLosingPlayer() {
		if (winner == null) { 
			return null;
		}
		
		return (winner == 1) ? two : one;
	}
	
	public String getDatePlayedOrChallenged() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		if (timePlayed == null) { 
			return sdf.format(timeChallenged);
		} else {
			return sdf.format(timePlayed);
		}
	}
	
	public static boolean isValidResult(Integer oneScore, Integer twoScore) {
		// You need at least 21 points to win
		if (oneScore < 21 && twoScore < 21) { 
			return false;
		}
		
		// You must win by two points
		if (Math.abs(oneScore - twoScore) < 2) {
			return false;
		}
		
		return true;
	}
	
}

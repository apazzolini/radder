package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	public Integer scoreReporter;
	public Boolean scoreApproved;

	@OneToMany public List<Comment> comments;

	public static JPAQuery findAllChallenges() {
		return Game.find("winner is null or scoreApproved is null order by timeChallenged asc");
	}

	public static JPAQuery findUserChallenges(Long userid) {
		return Game.find("(one.id = ? or two.id = ?) and (winner is null or scoreApproved is null) order by timeChallenged asc", userid, userid);
	}

	public static JPAQuery findAllResults() {
		return Game.find("winner is not null and scoreApproved is true order by timePlayed desc");
	}

	public static JPAQuery findUserResults(Long userid) {
		return Game.find("(one.id = ? or two.id = ?) and winner is not null and scoreApproved is true order by timePlayed desc", userid, userid);
	}

	public Boolean getScoreApproved() {
		return scoreApproved == null ? false : scoreApproved;
	}

	public String oneRatingChange() { 
		if (onePointsChange != null) { 
			return (onePointsChange > 0) ? "+" + onePointsChange.intValue() : "" + onePointsChange.intValue();
		} 
		return null;
	}

	public String twoRatingChange() { 
		if (twoPointsChange != null) { 
			return (twoPointsChange > 0) ? "+" + twoPointsChange.intValue() : "" + twoPointsChange.intValue();
		}
		return null;
	}

	public Player getScoreReportingPlayer() {
		if (scoreReporter == null) {
			return null;
		}

		return (scoreReporter == 1) ? one : two;
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

	public String getDateChallenged() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(timeChallenged);
	}

	public String getDatePlayed() {
		if (winner == null) { 
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(timePlayed);
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

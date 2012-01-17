package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Game extends Model {
	@ManyToOne public Player one;
	@ManyToOne public Player two;
	
	public Integer winner;
	public Integer oneScore;
	public Integer twoScore;
	
	public Date timeChallengeSent;
	public Date timeResultRecorded;
	
	public String getWinnerName() {
		if (winner != null) { 
			switch (winner) {
				case 1: return one.toString();
				case 2: return two.toString();
			}
		} 
		return "Not yet played";
	}
}

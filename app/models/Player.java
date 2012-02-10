package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Player extends Model {
	public String name;
	public String email;
	
	public Double rating;
	public Boolean pro;
	public Boolean beginner;
	
	public String toString() {
		return name;
	}
	
	public int numGamesPlayed() {
		return (int) Game.count("(one.id = ? or two.id = ?) and winner is not null", id, id);
	}
	
	public int numGamesWon() { 
		return (int) (Game.count("one.id = ? and winner is 1", id) + Game.count("two.id = ? and winner is 2", id));
	}
	
	public int numGamesLost() { 
		return (int) (Game.count("one.id = ? and winner is 2", id) + Game.count("two.id = ? and winner is 1", id));
	}
	
	public void wonAgainst(Player other, Game game) { 
		double RA = this.rating;
		double RB = other.rating;
		
		double QA = Math.pow(10, RA/400);
		double QB = Math.pow(10, RB/400);

		double k = 32;

		double EA = QA / (QA + QB);
		double EB = QB / (QA + QB);	    	

		double SA = 1;
		double SB = 0;

		this.rating = RA + (k * (SA - EA));
		other.rating = RB + (k * (SB - EB));
	}
}

package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Player extends Model {
	public String name;
	public String email;
	public String password;
	
	public Double rating;
	public Boolean pro;
	public Boolean beginner;
	
	@OneToMany public List<Game> games;
	
	public String toString() {
		return name;
	}
	
	public int numGamesPlayed() {
		return Game.findUserResults(id).fetch().size();
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

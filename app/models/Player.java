package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.db.jpa.Model;

@Entity
public class Player extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	
	public Double rating;
	public Boolean pro;
	public Boolean beginner;
	
	@ManyToMany public List<Game> games;
	
	public String toString() {
		return firstName + " " + lastName;
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

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
	
	public Integer rating;
	public Boolean pro;
	public Boolean beginner;
	
	@ManyToMany public List<Game> games;
	
	public String toString() {
		return firstName + " " + lastName;
	}
	
	public void wonAgainst(Player other) { 
		this.rating += 20;
	}
	
	public void lostTo(Player other) { 
		this.rating -= 20;
	}

}

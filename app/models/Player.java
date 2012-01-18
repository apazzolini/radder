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
	
	public void wonAgainst(Player other, Game game) { 
		this.rating += 20;
		games.add(game);
	}
	
	public void lostTo(Player other, Game game) { 
		this.rating -= 20;
		games.add(game);
	}
	

}

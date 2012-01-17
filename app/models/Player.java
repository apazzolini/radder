package models;

import java.util.Comparator;
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
	
	public static Comparator<Player> ratingComparator() {
		return ratingComparator(false);
	}
	
	public static Comparator<Player> ratingComparator(final Boolean ascending) {
		return new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				return ascending ? o1.rating.compareTo(o2.rating) : o2.rating.compareTo(o1.rating);
			}
		};
	}

}

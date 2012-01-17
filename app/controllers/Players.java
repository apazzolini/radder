package controllers;

import java.util.ArrayList;

import models.Game;
import models.Player;

public class Players extends CRUD {
	protected final static Integer DEFAULT_RATING = 1500;
	
    public static void signup() {
        render();
    }
    
    public static void createPlayer(String firstName, String lastName, String email, String password, String passwordConfirm) {
    	// Create the new player with the default rating
    	Player player = new Player();
    	player.firstName = firstName;
    	player.lastName = lastName;
    	player.email = email;
    	player.password = password;
    	player.rating = DEFAULT_RATING;
    	player.games = new ArrayList<Game>();
    	player.save();
    	
    	// Log the user in automatically and return to the homepage
    	session.put("username", email);
    	session.put("firstName", firstName);
    	redirect("/");
    }

}

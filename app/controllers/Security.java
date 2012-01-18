package controllers;

import java.util.ArrayList;

import models.Game;
import models.Player;


public class Security extends Secure.Security {
	protected final static Double DEFAULT_RATING = 1500.0;
	
    public static void signup() {
    	renderTemplate("Secure/signup.html");
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
    	onAuthenticated();
    	redirect("/");
    }
	
	static boolean authentify(String email, String password) {
		Player player = Player.find("byEmail", email).first();
        return player != null && player.password.equals(password);
    }  
	
	static void onAuthenticated() {
		Player player = Player.find("byEmail", session.get("username")).first();
		session.put("firstName", player.firstName.trim());
	}
	
	static void onDisconnected() {
		Application.index();
	}
}

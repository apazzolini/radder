package controllers;

import models.Player;
import play.libs.Crypto;


public class Security extends Secure.Security {
	protected final static Double DEFAULT_RATING = 1500.0;
	
    public static void signup() {
    	renderTemplate("Secure/signup.html");
    }
    
    public static void createPlayer(String name, String email, String password, String passwordConfirm) {
    	validation.required(name);
    	validation.required(email);
    	validation.email(email);
    	validation.required(password);
    	validation.equals(password, passwordConfirm);
    	
    	if (Player.find("byEmail", email).first() != null) { 
    		validation.addError("email", "validation.emailAlreadyRegistered");
    	}
    	
    	if (validation.hasErrors()) {
    		params.flash();
    		validation.keep();
    		signup();
    	}
    	
    	
    	// Create the new player with the default rating
    	Player player = new Player();
    	player.name = name;
    	player.email = email;
    	player.password = Crypto.passwordHash(password);
    	player.rating = DEFAULT_RATING;
    	player.save();
    	
    	// Log the user in automatically and return to the homepage
    	session.put("username", email);
    	onAuthenticated();
    	Application.index();
    }
	
	static boolean authentify(String email, String password) {
		Player player = Player.find("byEmail", email).first();
        return player != null && player.password.equals(Crypto.passwordHash(password));
    }  
	
	static void onAuthenticated() {
		Player player = Player.find("byEmail", session.get("username")).first();
		session.put("friendlyName", player.name);
		session.put("userid", player.id);
	}
	
	static void onDisconnected() {
		Application.index();
	}
}

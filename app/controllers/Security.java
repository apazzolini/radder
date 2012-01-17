package controllers;

import models.Player;


public class Security extends Secure.Security {
	
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

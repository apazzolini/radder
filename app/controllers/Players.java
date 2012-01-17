package controllers;

import models.Player;

public class Players extends CRUD {
	
    public static void signup() {
        render();
    }
    
    public static void createPlayer(String firstName, String lastName, String email, String password, String passwordConfirm) {
    	Player player = new Player();
    	player.firstName = firstName;
    	player.lastName = lastName;
    	player.email = email;
    	player.password = password;
    	player.save();
    	
    	session.put("username", email);
    	session.put("firstName", firstName);
    	redirect("/");
    }

}

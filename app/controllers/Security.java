package controllers;

import javax.inject.Inject;

import models.Player;

import org.springframework.ldap.core.LdapTemplate;


public class Security extends Secure.Security {
	protected final static Double DEFAULT_RATING = 1500.0;
	
	@Inject private static LdapTemplate ldap;
	
    protected static void createPlayer(String email, String password) {
    	// Create the new player with the default rating
    	Player player = new Player();
    	player.name = email; // Should query LDAP for full name
    	player.email = email;
    	player.rating = DEFAULT_RATING;
    	player.save();
    }
	
	static boolean authentify(String email, String password) {
//    	String username = "aazzolini";
//        EqualsFilter filter = new EqualsFilter("uid", username);
//    	boolean authenticated = ldap.authenticate(DistinguishedName.EMPTY_PATH, filter.encode(), password);
//    	System.out.println("User " + username + " " + authenticated);
		boolean ldapAuthenticated = true;
		
		if (ldapAuthenticated) { 
			Player player = Player.find("byEmail", email).first();
			if (player == null) { 
				createPlayer(email, password);
			}
		}
		
		return ldapAuthenticated;
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

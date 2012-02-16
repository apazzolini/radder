package controllers;

import javax.inject.Inject;
import javax.naming.directory.DirContext;

import models.Player;

import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;


public class Security extends Secure.Security {
	protected final static Double DEFAULT_RATING = 1500.0;
	
	@Inject private static LdapContextSource contextSource;
	//@Inject private static LdapTemplate ldapTemplate;
	
    protected static void createPlayer(String email, String password) {
    	// Create the new player with the default rating
    	Player player = new Player();
    	player.name = email.substring(email.indexOf('@'));
    	player.email = email;
    	player.rating = DEFAULT_RATING;
    	player.save();
    }
	
	static boolean authentify(String email, String password) {
    	Boolean ldapAuthenticated;
    	
    	DirContext ctx = null;
    	try {
    		ctx = contextSource.getContext(email, password);
    	    ldapAuthenticated = true;
    	} catch (Exception e) {
    		ldapAuthenticated = false; // Context creation failed - authentication did not succeed
    	} finally {
    		LdapUtils.closeContext(ctx); // It is imperative that the created DirContext instance is always closed
    	}
    	
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
package controllers;

import models.Player;


public class Players extends CRUD {

    public static void getName(Long userid) {
    	Player player = Player.findById(userid);
    	renderText(player.name);
    }
}

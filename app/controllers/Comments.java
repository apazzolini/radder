package controllers;

import java.util.Date;

import models.Comment;
import models.Player;

public class Comments extends CRUD {

    public static void comment(String comment) {
    	Long currentUserId = Long.parseLong(session.get("userid"));
    	Comment newComment = new Comment();
    	newComment.comment = comment;
    	newComment.time = new Date();
    	newComment.player = Player.find("byId", currentUserId).first();
    	newComment.save();
    	renderText("OK");
    }
    
}

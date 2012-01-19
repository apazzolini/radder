package controllers;

import java.util.Date;

import models.Comment;
import models.Player;
import play.mvc.Controller;

public class Comments extends Controller {

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

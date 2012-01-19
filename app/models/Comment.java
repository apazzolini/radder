package models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Comment extends Model {
	@ManyToOne public Player player;
	public String comment;
	public Date time;
	
	public String getPrettyTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(time);
	}
	
	public String getPrettyDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		return sdf.format(time);
	}
    
}

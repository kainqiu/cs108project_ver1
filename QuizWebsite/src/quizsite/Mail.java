package quizsite;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Mail {

//	static public enum Type {
//		FRIEND_REQ, CHALLENGE, NOTE;  
//	}
	static public int FRIEND_REQ = 1;
	static public int CHALLENGE = 2;
	static public int NOTE = 3;

	private int id;
	private Date createdAt;
	private int fromUserId;
	private int toUserId;
	private String content;
	private int type;
	
	public Mail(int fromId, int type, String content) {
		this.fromUserId = fromId;
		this.type = type;
		this.content = content;
	}
	
	static public boolean createMail(int fromId, int toId, int type, String content, DBConnection dbCon) {
		try {
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement("INSERT INTO mails (fromId, toId, type, content) VALUES (?, ?, ?, ?)");
			preStmt.setInt(1, fromId);
			preStmt.setInt(2, toId);
			preStmt.setInt(3, type);
			preStmt.setString(4, content);
			preStmt.executeUpdate();
			System.out.println("in createMail");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	public int getFromId() {
		return this.fromUserId;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getContent() {
		return this.content;
	}
	
}

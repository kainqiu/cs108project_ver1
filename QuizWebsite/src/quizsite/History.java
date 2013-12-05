package quizsite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class History {

	private int userId;
	private int quizId;
	private int score;
	private double elapsedTime;
	private java.sql.Timestamp finishAt;
	
	public History(int quizId, int score, double elapsedTime, java.sql.Timestamp finishAt) {
		//this.userId = userId;
		this.quizId = quizId;
		this.score = score;
		this.elapsedTime = elapsedTime;
		this.finishAt = finishAt;
	}
	
	static public boolean createHistory(int userId, int quizId, int score, double elapsedTime, DBConnection dbCon) {
		try {
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement("INSERT INTO histories (userId, quizId, score, elapsedTime, finishAt) VALUES (?, ?, ?, ?, ?)");
			preStmt.setInt(1, userId);
			preStmt.setInt(2, quizId);
			preStmt.setInt(3, score);
			preStmt.setDouble(4, elapsedTime);
			java.util.Date finishAt = new Date();
			//Object param = new java.sql.Timestamp(createdAt.getTime());
			Object timestamp = new java.sql.Timestamp(finishAt.getTime());
			preStmt.setObject(5, timestamp);
			preStmt.executeUpdate();
			System.out.println("in createHistory");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getQuizId() {
		return this.quizId;
	}
	
	public String getElapsedTime() {
		return String.valueOf(this.elapsedTime);
	}
	
	public String getFinishAt() {
		String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.finishAt);
		return datetime;
	}
	
}

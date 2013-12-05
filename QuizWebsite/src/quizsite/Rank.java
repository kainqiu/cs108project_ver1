package quizsite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Rank {
	
	static public class QuizInfo {
		public int id;
		public String title;
		
		public QuizInfo(int id, String title) {
			this.id = id;
			this.title = title;
		}
	}

	static public ArrayList<QuizInfo> getPopularQuiz(DBConnection dbCon) {
		ArrayList<QuizInfo> popular = new ArrayList<QuizInfo>();
		try {
			String selectSQL = "SELECT id, title FROM quizzes ORDER BY timesTaken DESC LIMIT 5";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				QuizInfo qi = new QuizInfo(rs.getInt("id"), rs.getString("title"));
				popular.add(qi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		return popular;
	}
	
	static public ArrayList<QuizInfo> getRecentCreatedQuiz(DBConnection dbCon) {
		ArrayList<QuizInfo> recentCreated = new ArrayList<QuizInfo>();
		try {
			String selectSQL = "SELECT id, title FROM quizzes ORDER BY createdAt DESC LIMIT 5";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				QuizInfo qi = new QuizInfo(rs.getInt("id"), rs.getString("title"));
				recentCreated.add(qi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		return recentCreated;
	}
	
	static public ArrayList<QuizInfo> getQuizCreatedByUserId(DBConnection dbCon, int userId) {
		ArrayList<QuizInfo> createdByUser = new ArrayList<QuizInfo>();
		try {
			String selectSQL = "SELECT id, title FROM quizzes WHERE creatorId = ? ORDER BY createdAt DESC LIMIT 5";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, userId);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				QuizInfo qi = new QuizInfo(rs.getInt("id"), rs.getString("title"));
				createdByUser.add(qi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		return createdByUser;
	}
	
	static public ArrayList<QuizInfo> getRecentTakenQuizByUserId(DBConnection dbCon, int userId) {
		ArrayList<QuizInfo> recentTakenByUserId = new ArrayList<QuizInfo>();
		try {
			String selectSQL = "SELECT quizId FROM histories WHERE userId = ? ORDER BY finishAt DESC LIMIT 5";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, userId);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				int quizId = rs.getInt("quizId");
				String quizTitle = Quiz.getTitleById(quizId, dbCon);
				QuizInfo qi = new QuizInfo(quizId, quizTitle);
				recentTakenByUserId.add(qi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		return recentTakenByUserId;
	}
	
}

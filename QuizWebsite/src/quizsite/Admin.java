package quizsite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Admin {

	static public class UserInfo {
		public int userId;
		public String username;
		
		public UserInfo(int userId, String username) {
			this.userId = userId;
			this.username = username;
		}
	}
	
	static public ArrayList<UserInfo> getAllUsers(DBConnection dbCon) {
		ArrayList<UserInfo> userList = new ArrayList<UserInfo>();
		try {
			String selectSQL = "SELECT id, username FROM users ORDER BY id DESC";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				UserInfo ui = new UserInfo(id, username);
				userList.add(ui);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return userList;
	}
	
	static public boolean removeUser(int userId, DBConnection dbCon) {
		try {
			String selectSQL = "DELETE FROM users WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, userId);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	static public class QuizInfo {
		public int quizId;
		public String quizTitle;
		
		public QuizInfo(int quizId, String quizTitle) {
			this.quizId = quizId;
			this.quizTitle = quizTitle;
		}
	}
	
	static public ArrayList<QuizInfo> getAllQuizzes(DBConnection dbCon) {
		ArrayList<QuizInfo> quizList = new ArrayList<QuizInfo>();
		try {
			String selectSQL = "SELECT id, title FROM quizzes ORDER BY id DESC";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				QuizInfo qi = new QuizInfo(id, title);
				quizList.add(qi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return quizList;
	}
	
	static public boolean removeQuiz(int quizId, DBConnection dbCon) {
		try {
			String selectSQL = "DELETE FROM quizzes WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, quizId);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	static public boolean removeQuizHistory(int quizId, DBConnection dbCon) {
		try {
			String selectSQL = "DELETE FROM histories WHERE quizId = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, quizId);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
}

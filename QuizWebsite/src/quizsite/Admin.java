package quizsite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
	
	static public boolean promoteUser(int userId, DBConnection dbCon) {
		try {
			String selectSQL = "UPDATE users SET isAdmin=true where id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, userId);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	static public boolean removeAdmin(int userId, DBConnection dbCon) {
		try {
			String selectSQL = "UPDATE users SET isAdmin=false where id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, userId);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	static public class AdminAnnounceInfo {
		public int adminId;
		public String content;
		public int announceId;
		
		public AdminAnnounceInfo(int adminId, String content, int announceId) {
			this.adminId = adminId;
			this.content = content;
			this.announceId = announceId;
		}
	}
	
	static public ArrayList<AdminAnnounceInfo> getAllAdminAnnounce(DBConnection dbCon) {
		ArrayList<AdminAnnounceInfo> announceList = new ArrayList<AdminAnnounceInfo>();
		try {
			String selectSQL = "SELECT adminId, content, id FROM adminAnnounce ORDER BY id DESC";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				int adminId = rs.getInt("adminId");
				String content = rs.getString("content");
				int announceId = rs.getInt("id");
				AdminAnnounceInfo aa = new AdminAnnounceInfo(adminId, content, announceId);
				announceList.add(aa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return announceList;
	}
	
	static public boolean registerAnnounce(int adminId, String content, DBConnection dbCon) {		
		try {
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement("INSERT INTO adminAnnounce (adminId, content) VALUES (?, ?)");
			preStmt.setInt(1, adminId);
			preStmt.setString(2, content);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	static public boolean removeAnnounce(int announceId, DBConnection dbCon) {
		try {
			String selectSQL = "DELETE FROM adminAnnounce WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, announceId);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	static public String getAnnounceContentById(int announceId, DBConnection dbCon) {
		String content = "";
		try {
			String selectSQL = "SELECT content FROM adminAnnounce WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, announceId);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				content = rs.getString("content");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return content;
	}
	
}

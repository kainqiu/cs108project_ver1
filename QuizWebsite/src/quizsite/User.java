package quizsite;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class User {
	
	public static int count = 0;
	
	private int id;
	private String username;
	private String password;
	private String salt;
	private Date createdAt;
	private ArrayList<User> friends;
	private ArrayList<Quiz> quizCreated;
	private ArrayList<Quiz> quizTaken;
	private ArrayList<Mail> mailReceived;
	private ArrayList<History> history;
	private boolean hasNewMail;
	
	DBConnection dbCon;
	
	public User(String username, DBConnection dbCon) {
		this.username = username;
		try {
			// get user id
			String selectSQL = "SELECT id FROM users WHERE username = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setString(1, username);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				int recordedId = rs.getInt("id");
				this.id = recordedId;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		this.friends = new ArrayList<User>();
//		this.quizCreated = new ArrayList<Quiz>();
//		this.quizTaken = new ArrayList<Quiz>();
//		this.mailReceived = new ArrayList<Mail>();
//		this.hasNewMail = false;
		
		this.dbCon = dbCon;
	}
	
	
	public User(int userId, DBConnection dbCon) {
		this.id = userId;
		try {
			// get user id
			String selectSQL = "SELECT username FROM users WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, id);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				String recordedUsername = rs.getString("username");
				this.username = recordedUsername;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		this.dbCon = dbCon;
	}
	
	static public boolean registerUser(String username, String pw, DBConnection dbCon) {
		//User.count++;
		//int id = User.count;
		Date createdAt = new Date();
		String salt = createdAt.toString();
		System.out.println("user created at " + salt);
		String password = generateHash(pw + salt);
		System.out.println("hashed pw is  " + password);		
		try {
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement("INSERT INTO users (username, password, salt, numNewMail) VALUES (?, ?, ?, ?)");
			//preStmt.setInt(1, id);
			preStmt.setString(1, username);
			preStmt.setString(2, password);
			preStmt.setString(3, salt);
			preStmt.setInt(4, 0);
			preStmt.executeUpdate();
			System.out.println("in registerUser");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	static public boolean existUser(String username, DBConnection dbCon) {
		try {
			String selectSQL = "SELECT username FROM users WHERE username = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setString(1, username);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				System.out.println("user already exist");
				return true;
			}
			System.out.println("in existUser");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
		return false;
	}
	
	static public boolean Login(String username, String pw, DBConnection dbCon) {
		try {
			String selectSQL = "SELECT password, salt FROM users WHERE username = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setString(1, username);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				String recordedPw = rs.getString("password");
				String recordedSalt = rs.getString("salt");
				String inputPwWithSalt = generateHash(pw + recordedSalt);
				if(inputPwWithSalt.equals(recordedPw)) {
					System.out.println("login success");
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println("login failed");
		return false;
	}
	
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	public static String generateHash(String pw) {	
		byte[] byteData = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(pw.getBytes());
			byteData = md.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexToString(byteData);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public ArrayList<Mail> getMails() {
		this.mailReceived = new ArrayList<Mail>();
		try {
			String selectSQL = "SELECT fromId, type, content FROM mails WHERE toId = ? ORDER BY id DESC";
			PreparedStatement preStmt = this.dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, this.id);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				int fromId = rs.getInt("fromId");
				int type = rs.getInt("type");
				String content = rs.getString("content");
				Mail mail = new Mail(fromId, type, content);
				this.mailReceived.add(mail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return this.mailReceived;
	}
	
	public ArrayList<History> getHistories() {
		this.history = new ArrayList<History>();
		try {
			String selectSQL = "SELECT quizId, score, elapsedTime, finishAt FROM histories WHERE userId = ? ORDER BY id DESC";
			PreparedStatement preStmt = this.dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, this.id);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				int quizId = rs.getInt("quizId");
				int score = rs.getInt("score");
				double elapsedTime = rs.getDouble("elapsedTime");
				java.sql.Timestamp finishAt = (Timestamp) rs.getObject("finishAt");
				int maxscore = rs.getInt("maxPossibleScore");
				History h = new History(quizId, score, elapsedTime, finishAt, maxscore);
				this.history.add(h);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return this.history;
	}
	
	public ArrayList<User> getFriendsList() {
		this.friends = new ArrayList<User>();
		ArrayList<Integer> friendsIdList = new ArrayList<Integer>();
		try {
			String selectSQL = "SELECT user2 FROM friends WHERE user1 = ?";
			PreparedStatement preStmt = this.dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, this.id);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				int friendId = rs.getInt("user2");
				friendsIdList.add(friendId);
				User u = new User(friendId, dbCon);
				this.friends.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		return this.friends;
	}
	
	static public class Activity {
		public String username;
		public int userId;
		public String quizTitle;
		public int quizId;
		public boolean isCreate;
		public java.sql.Timestamp timestamp;
		
		public Activity(String username, int userId, String quizTitle, int quizId, boolean isCreate, java.sql.Timestamp timestamp) {
			this.username = username;
			this.userId = userId;
			this.quizId = quizId;
			this.quizTitle = quizTitle;
			this.isCreate = isCreate;
			this.timestamp = timestamp;
		}
	}
	
	public ArrayList<Activity> getRecentFriendsActivities() {
		ArrayList<Activity> friendsAct = new ArrayList<Activity>();
		ArrayList<User> friends = getFriendsList();
		try {
			for(User u : friends) {
				// query for quiz taken
				String selectSQL = "SELECT quizId, finishAt FROM histories WHERE userId = ? AND ADDTIME(finishAt, '20 0:0:0.000000') > ? ORDER BY finishAt DESC LIMIT 5";
				PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
				preStmt.setInt(1, u.getId());
				java.util.Date finishAt = new Date();
				Object timestamp = new java.sql.Timestamp(finishAt.getTime());
				preStmt.setString(2, timestamp.toString());
				//System.out.println("sql select is : " + preStmt.toString());
				ResultSet rs = preStmt.executeQuery();
				while(rs.next()) {
					Activity act = new Activity(u.getUsername(), u.getId(), Quiz.getTitleById(rs.getInt("quizId"), dbCon), rs.getInt("quizId"), false, rs.getTimestamp("finishAt"));
					friendsAct.add(act);
				}
				
				// query for quiz created
				selectSQL = "SELECT id, title, createdAt FROM quizzes WHERE creatorId = ? AND ADDTIME(createdAt, '20 0:0:0.000000') > ? ORDER BY createdAt DESC LIMIT 5";
				preStmt = dbCon.getConnection().prepareStatement(selectSQL);
				preStmt.setInt(1, u.getId());
				preStmt.setString(2, timestamp.toString());
				//System.out.println("sql select is : " + preStmt.toString());
				rs = preStmt.executeQuery();
				while(rs.next()) {
					Activity act = new Activity(u.getUsername(), u.getId(), rs.getString("title"), rs.getInt("id"), true, rs.getTimestamp("createdAt"));
					friendsAct.add(act);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return friendsAct;
	}
	
	static public String getUsernameById(int id, DBConnection dbCon) {
		try {
			String selectSQL = "SELECT username FROM users WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, id);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				return rs.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		return null;
	}
	
	static public int getNumNewMailById(int id, DBConnection dbCon) {
		try {
			String selectSQL = "SELECT numNewMail FROM users WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, id);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("numNewMail");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} 
		return 0;
	}
	
	static public boolean incrementNumNewMailByOne(int id, DBConnection dbCon) {
		try {
			int currNumNewMail = User.getNumNewMailById(id, dbCon);
			currNumNewMail++;
			String updateSQL = "UPDATE users SET numNewMail = ? WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(updateSQL);
			preStmt.setInt(1, currNumNewMail);
			preStmt.setInt(2, id);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	static public boolean setNumNewMailToZeroById(int id, DBConnection dbCon) {
		try {
			String updateSQL = "UPDATE users SET numNewMail=0 WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(updateSQL);
			preStmt.setInt(1, id);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	static public int getNumQuizTakenByUserId(int userId, DBConnection dbCon) {
		try {
			String selectSQL = "SELECT COUNT(*) AS rowCount FROM histories WHERE userId = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, userId);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} 
		return 0;
	}
	
}

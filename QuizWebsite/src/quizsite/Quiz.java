package quizsite;

import java.awt.List;
import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.el.lang.ELArithmetic;

import quizsite.Rank.QuizInfo;
import sun.tools.tree.ThisExpression;

public class Quiz {

	//public static int count = 0;
	boolean display_random = false;
	boolean display_one_page = false;
	boolean display_multiple_pages = false;
	boolean display_immediate_correction = false;
	boolean display_final_correction= false;
	
	private boolean randomize;
	private boolean onePage;
	private boolean immediateCorrection;

	private String title;
	private String description;
	private int quizID;

	private static java.sql.Timestamp param;
	ArrayList<Question> questions = new ArrayList<Question>();

	DBConnection dbCon;
	
	// added by Kain
	private int creatorId;

	//empty parameter constructor... added by Sarah
	public Quiz(){
		randomize = false;
		onePage = false;
		immediateCorrection = false;
	}

	public void setID(int id){
		quizID = id;
	}

	public int getID(){
		return quizID;
	}

	public static boolean registerQuiz(DBConnection dbCon, User currentUser, String title, String description, boolean random, boolean pages, boolean correction) {

		Date createdAt = new Date();
		param = new java.sql.Timestamp(createdAt.getTime());
		
		int timeTaken = 0;
		int userID = currentUser.getId();
		//String key = currentUser.getUsername() + Integer.toString(qzID);
		//System.out.println("userID: "  + userID + " timeCreated: " + param +  " title: " + title + " description: " + description);

		try {
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement("INSERT INTO quizzes(creatorId, createdAt, title, description, timesTaken, randomQuestions, onePage, immediateCorrection) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			preStmt.setInt(1, userID);
			preStmt.setTimestamp(2, param);
			preStmt.setString(3, title);
			preStmt.setString(4, description);
			preStmt.setInt(5, timeTaken);
			preStmt.setBoolean(6, random);
			preStmt.setBoolean(7, pages);
			preStmt.setBoolean(8, correction);

			preStmt.executeUpdate();
			//System.out.println("in registerQuiz");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}

	
	public void addQuizTitle(String str){
		title = str;
	}

	public String getQuizTitle(){
		return title;
	}

	public void addQuizDescription(String str){
		description = str;
	}

	public String getQuizDescription(){
		return description;
	}

	public void addQuestion(Question ques){
		questions.add(ques);
	}

	public ArrayList<Question> setOfQuestions(){
		return questions;
	}

	public void setDisplayRandomTrue(){
		display_random = true;
	}
	public boolean isDisplayRandom(){
		return display_random;
	}

	public void setDisplayOnePageTrue(){
		display_one_page = true;
	}
	public boolean isDisplayOnePageTrue(){
		return display_one_page;
	}

	public void setDisplayMultiplePagesTrue(){
		display_multiple_pages = true;
	}

	public boolean isDisplayMultiplePagesTrue(){
		return display_multiple_pages;
	}
	public void setImmediateCorrectionTrue(){
		display_immediate_correction = true;
	}
	public boolean isImmediateCorrectionTrue(){
		return display_immediate_correction;
	}

	public void setFinalCorrectionTrue(){
		display_final_correction = true;
	}
	public boolean isFinalCorrectionTrue(){
		return display_final_correction;
	}
	
	//added by Sarah
	public void setRandomize(boolean randomize){
		this.randomize = randomize;
	}
	
	public boolean isRandom(){
		return randomize;
	}
	
	public void setDisplay(boolean display){
		onePage = display;
	}
	
	public boolean isOnePage(){
		return onePage;
	}
	
	public void setCorrection(boolean option){
		immediateCorrection = option;
	}
	
	public boolean isImmediateCorrection(){
		return immediateCorrection;
	}
	
	//add by Kain
	
	static public String getTitleById(int id, DBConnection dbCon) {
		try {
			String selectSQL = "SELECT title FROM quizzes WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, id);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				return rs.getString("title");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		return null;
	}
	

	public Quiz(int id, DBConnection dbCon) {
		this.quizID = id;
		try {
			String selectSQL = "SELECT creatorId, title, description FROM quizzes WHERE id = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, id);
			ResultSet rs = preStmt.executeQuery();
			if(rs.next()) {
				this.creatorId = rs.getInt("creatorId");
				this.title = rs.getString("title");
				this.description = rs.getString("description");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		this.dbCon = dbCon;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getCreatorId() {
		return this.creatorId;
	}
	
	static public ArrayList<RecordInfo> getHighestRecord(int quizId, DBConnection dbCon) {
		ArrayList<RecordInfo> highRec = new ArrayList<RecordInfo>();
		try {
			String selectSQL = "SELECT userId, score, elapsedTime, finishAt FROM histories WHERE quizId = ? ORDER BY score DESC, elapsedTime ASC LIMIT 5";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, quizId);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				RecordInfo ri = new RecordInfo(rs.getInt("userId"), rs.getInt("score"), rs.getDouble("elapsedTime"), rs.getTimestamp("finishAt"));
				highRec.add(ri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return highRec;
	}
	
	static public ArrayList<RecordInfo> getRecentRecords(int quizId, DBConnection dbCon) {
		ArrayList<RecordInfo> recentRec = new ArrayList<RecordInfo>();
		try {
			String selectSQL = "SELECT userId, score, elapsedTime, finishAt FROM histories WHERE quizId = ? ORDER BY finishAt DESC LIMIT 5";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, quizId);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				RecordInfo ri = new RecordInfo(rs.getInt("userId"), rs.getInt("score"), rs.getDouble("elapsedTime"), rs.getTimestamp("finishAt"));
				recentRec.add(ri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return recentRec;
	}
	
	static public class QuizSum {
		public int numOfTaken;
		public double avgScore;
		
		public QuizSum(int numOfTaken, double avgScore) {
			this.numOfTaken = numOfTaken;
			this.avgScore = avgScore;
		}
	}
	
	static public QuizSum getQuizSummary(int quizId, DBConnection dbCon) {
		int numOfTaken = 0;
		int sum = 0;
		try {
			String selectSQL = "SELECT score FROM histories WHERE quizId = ?";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, quizId);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				sum += rs.getInt("score");
				numOfTaken++;
			}
			QuizSum qs = new QuizSum(numOfTaken, (double)sum/numOfTaken);
			return qs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static public ArrayList<RecordInfo> getLastDayTop(int quizId, DBConnection dbCon) {
		ArrayList<RecordInfo> lastRec = new ArrayList<RecordInfo>();
		try {
			String selectSQL = "SELECT userId, score, elapsedTime, finishAt FROM histories WHERE quizId = ? AND ADDTIME(finishAt, '1 0:0:0.000000') > ? ORDER BY score DESC, elapsedTime ASC LIMIT 10";
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, quizId);
			java.util.Date finishAt = new Date();
			Object timestamp = new java.sql.Timestamp(finishAt.getTime());
			//System.out.println("timestamp is " + timestamp.toString());
			preStmt.setString(2, timestamp.toString());
			//System.out.println("sql select is : " + preStmt.toString());
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				RecordInfo ri = new RecordInfo(rs.getInt("userId"), rs.getInt("score"), rs.getDouble("elapsedTime"), rs.getTimestamp("finishAt"));
				lastRec.add(ri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return lastRec;
	}
	
	static public ArrayList<RecordInfo> getPastRecByUserId(int quizId, int userId, String orderBy, DBConnection dbCon) {
		ArrayList<RecordInfo> pastRec = new ArrayList<RecordInfo>();
		System.out.println("sort in getPast is " + orderBy);
		try {
			String selectSQL = "SELECT userId, score, elapsedTime, finishAt FROM histories WHERE quizId = ? AND userId = ? ORDER BY ";
			if(orderBy.equals("finishAt")) {
				selectSQL += "finishAt DESC";
			} else if(orderBy.equals("elapsedTime")) {
				selectSQL += "elapsedTime ASC, score DESC";
			} else {
				selectSQL += "score DESC, elapsedTime ASC";
			}
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, quizId);
			preStmt.setInt(2, userId);
			//preStmt.setString(3, orderBy);
			//System.out.println("sql is : " + preStmt.toString());
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				RecordInfo ri = new RecordInfo(rs.getInt("userId"), rs.getInt("score"), rs.getDouble("elapsedTime"), rs.getTimestamp("finishAt"));
				pastRec.add(ri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return pastRec;
	}
	
	// time and score

}
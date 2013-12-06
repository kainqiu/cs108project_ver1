package quizsite;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Tags {
	
	static public boolean putTags(int quizID,String title, String tag, DBConnection dbCon) {		
		try {
			Statement stmt = dbCon.getConnection().createStatement();
			stmt.executeQuery("USE " + DBConnection.database);
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement("INSERT INTO tags (quizID, title, tag) VALUES (?, ?, ?)");
			preStmt.setInt(1, quizID);
			preStmt.setString(2, title);
			preStmt.setString(3, tag);
			preStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
	

}

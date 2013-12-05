package quizsite;

import java.sql.Time;

public class RecordInfo {

	public int userId;
	public int score;
	public double elapsedTime;
	public java.sql.Timestamp finishAt;
	
	public RecordInfo(int userId, int score, double elapsedTime, java.sql.Timestamp finishAt) {
		this.userId = userId;
		this.score = score;
		this.elapsedTime = elapsedTime;
		this.finishAt = finishAt;
	}
}

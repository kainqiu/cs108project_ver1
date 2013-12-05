package quizsite;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Question {
	private String question;
	private String answer;
	private ArrayList<String> answers;
	public final String DELIM = "\n";
	private int type;

	Question(String question, String answer){
		this.question = question;
		//                System.out.println("question: " + this.question);
		this.answer = answer;
		//                System.out.println("answer: " + this.answer);
		answers = new ArrayList<String>();
		parseAnswers();
		type = -1;
	}
	
	public int getType(){
		return type;
	}
	
	public void setType(int newT){
		this.type = newT;
	}

	public boolean checkAnswer(String response) {
		for(int i=0; i<answers.size(); i++){
			if(answers.get(i).equals(response.trim())) return true;
		}
		return false;
	}

	public void resetQuestion(String question){
		this.question = question;
		//                System.out.println("reset question to: " + this.question);
	}

	private void parseAnswers(){
		String[] parsedAnswers = answer.split(DELIM);
		for(int i=0; i<parsedAnswers.length; i++){
			answers.add(parsedAnswers[i].trim());
		}
	}

	static public boolean registerQuestion(int qzID, int type, String question, String answer, String MC, DBConnection dbCon) {
		//User.count++;
		//int id = User.count;
		System.out.println("quizID: " + qzID + ", type: " + type + " - " + question + ", answer: " + answer);                
		try {
			PreparedStatement preStmt = dbCon.getConnection().prepareStatement("INSERT INTO questions(quizID, type, question, answer, MCoption) VALUES (?, ?, ?, ?, ?)");
			//preStmt.setInt(1, id);
			preStmt.setInt(1, qzID);
			preStmt.setInt(2, type);
			preStmt.setString(3, question);
			preStmt.setString(4, answer);
			preStmt.setString(5, MC);
			preStmt.executeUpdate();
			System.out.println("in registerQuestion");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}

	@Override
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Question))
			return false;
		return question == ((Question) obj).getQuestion();
	}

	public void addAnswer(String answ){
		answers.add(answ);
	}

	public String getQuestion(){
		return question;
	}

	public ArrayList<String> getAllPossAnswers(){
		return answers;
	}

	public String getAnswer(){
		return answer;
	}
	
	public void printString(){
		System.out.println(question + "\n" + answer);
	}
}
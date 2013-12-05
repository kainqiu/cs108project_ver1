package quizsite;

import java.util.HashSet;
import java.util.Set;

public class Quiz {

//	private int question_response;
//	private int multiple_choice;
//	private int picture_response;
//	private int fill_inblank;
	
	boolean display_random = false;
	boolean display_one_page = false;
	boolean display_multiple_pages = false;
	boolean display_immediate_correction = false;
	boolean display_final_correction= false;
	
	private String title;
	private String description;
	
	Set<Question> questions = new HashSet<Question>();
	
//	public void setNumQuestionResponse(int a){
//		question_response = a;
//	}
//	public void setFillInBlank(int a){
//		fill_inblank = a;
//	}
//	public void setNumMultipleChoice(int a){
//		multiple_choice = a;
//	}
//	public void setNumPictureResponse(int a){
//		picture_response = a;
//	}
//	public int getNumQuestionResponse(){
//		return question_response;
//	}
//	public int getNumMultipleChoice(){
//		return multiple_choice;
//	}
//	public int getNumPictureResponse(){
//		return picture_response;
//	}
//	public int getNumFillInBlank(){
//		return fill_inblank;
//	}
//	
	
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
	
	public Set<Question> setOfQuestions(){
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
	
	// time and score
	
}

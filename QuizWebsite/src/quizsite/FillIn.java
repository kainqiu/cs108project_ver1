package quizsite;

public class FillIn extends Question{
	public final String DELIM = "%_" ;
	public final String BLANK = "_____________";
	
	public FillIn(String question, String answer){
		super(question, answer);
		String FITB = "";
		String[] parsed = question.split(DELIM);
		for(int i=0; i<parsed.length - 1; i++){
			FITB += parsed[i] + BLANK;
		}
		FITB += parsed[parsed.length-1];
		resetQuestion(FITB);
	}
	
	

}

package quizsite;

import java.util.ArrayList;

public class MultiChoice extends Question{
	
	private ArrayList<String> options;
	
	public MultiChoice(String question, String answer){
		super(question, answer);
		options = new ArrayList<String>();
	}
	
	public void addMCOptions(String MCoptions){
		String[] choices = MCoptions.split(DELIM);
		for(int i=0; i<choices.length; i++){
			options.add(choices[i]);
		}
	}
	
	public ArrayList<String> getMCOptions(){
		return options;
	}

}

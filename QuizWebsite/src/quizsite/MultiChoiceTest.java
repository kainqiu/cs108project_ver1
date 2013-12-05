package quizsite;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class MultiChoiceTest {
	private String question = "Where is Stanford?";
	private String answer = "CA";
	private String A = "Mexico";
	private String B = "In my pants";
	private MultiChoice test;

	@Test
	public void testMultiChoice() {
		test = new MultiChoice(question, answer);
		test.addAnswer("Bay Area, CA");
//	}
//
//	@Test
//	public void testAddMCOption() {
		test.addMCOptions(B);
		test.addMCOptions(A);
		assertEquals(2, test.getMCOptions().size());
//	}
//
//	@Test
//	public void testGetMCOptions() {
		ArrayList<String> check = new ArrayList<String>();
		check.add(B);
		check.add(A);
		assertEquals(check, test.getMCOptions());
//	}
//
//	@Test
//	public void testGetAllPossAnswers() {
//		String[] check2 = {answer, "Bay Area, CA"};
		ArrayList<String>check2 = new ArrayList<String>();
		check2.add(answer);
		check2.add("Bay Area, CA");
		assertEquals(check2, test.getAllPossAnswers());
	}

}

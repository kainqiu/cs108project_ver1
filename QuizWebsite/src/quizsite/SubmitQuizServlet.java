package quizsite;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SubmitQuiz
 */
@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitQuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Quiz quiz = (Quiz) session.getAttribute("currQuiz");
		ArrayList<String> answers = (ArrayList<String>) session.getAttribute("answers");
		ArrayList<Question> questions = quiz.setOfQuestions();
		ArrayList<Boolean> results = (ArrayList<Boolean>) session.getAttribute("results");
		if(quiz.isOnePage()){
			for(int i=0; i<questions.size(); i++){
				String currAnswer = request.getParameter(Integer.toString(i));
				boolean curr = questions.get(i).checkAnswer(currAnswer);
				results.add(curr);
				answers.add(currAnswer);
			}
			session.setAttribute("results", results);
			session.setAttribute("answers", answers);
			RequestDispatcher dispatch = request.getRequestDispatcher("QuizResults.jsp");
			dispatch.forward(request, response);
		}else{
			Integer idx = (Integer) session.getAttribute("currIdx");
			String currAnswer = request.getParameter(Integer.toString(idx));
			boolean curr = questions.get(idx).checkAnswer(currAnswer);
			results.add(curr);
			answers.add(currAnswer);

			idx++;
			session.setAttribute("currIdx", idx);
			session.setAttribute("results", results);
			session.setAttribute("answers", answers);
			RequestDispatcher dispatch = request.getRequestDispatcher("TakeQuiz.jsp");
			if(idx>=questions.size()){
				dispatch = request.getRequestDispatcher("QuizResults.jsp");
			}
			if(quiz.isImmediateCorrection()){
				dispatch = request.getRequestDispatcher("ImmediateCorrection.jsp");
			}
			dispatch.forward(request, response);
		}

	}

}

package quizsite;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuizDatabaseServlet
 */
@WebServlet("/QuizDatabaseServlet")
public class QuizDatabaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizDatabaseServlet() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Quiz quiz = (Quiz) session.getAttribute("newQuiz");

		String randomQuestionsCheckBoxValue = request.getParameter("randomQuestions");

		if(randomQuestionsCheckBoxValue.equals("1")){
			quiz.setDisplayRandomTrue();
		}

		String pageNumbers = request.getParameter("pagenumbers");
		if(pageNumbers.equals("1")){
			quiz.setDisplayOnePageTrue();
		}
		else{
			quiz.setDisplayMultiplePagesTrue();
		}

		String immediateCorrectionCheckBoxValue = request.getParameter("immediateCorrection");
		if(immediateCorrectionCheckBoxValue.equals("1")){
			quiz.setImmediateCorrectionTrue();
		}
		else{
			quiz.setFinalCorrectionTrue();
		}


		DBConnection con = (DBConnection) session.getAttribute("connection");

		String title = quiz.getQuizTitle();
		String description = quiz.getQuizDescription();

		User currUser = (User) session.getAttribute("user");

		boolean random = quiz.isDisplayRandom();
		boolean pages = quiz.isDisplayOnePageTrue();
		boolean correction = quiz.isImmediateCorrectionTrue();


		Quiz.registerQuiz(con, currUser, title, description, random, pages, correction);

		String selectSQL = "SELECT id FROM quizzes ORDER BY createdAt DESC";

		PreparedStatement preStmt;
		try {
			preStmt = con.getConnection().prepareStatement(selectSQL);
			ResultSet rs = preStmt.executeQuery();
			if (rs.next()){
				quiz.setID(rs.getInt("id"));
			}				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatch = request.getRequestDispatcher("chooseQuestionType.jsp");
		dispatch.forward(request, response);
	}

}

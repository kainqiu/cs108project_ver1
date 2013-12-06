package quizsite;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
		DBConnection con = (DBConnection) session.getAttribute("connection");
		if(con == null) {
			con = new DBConnection();
			session.setAttribute("connection", con);
			//System.out.println("con is NULL");
		}

		String userName = request.getParameter("name");
		String userPw = request.getParameter("pw");
		if(User.existUser(userName, con)) {
			RequestDispatcher dispatch = request.getRequestDispatcher("NameInUse.html");
			dispatch.forward(request, response);
		} else {
			// create new user
			System.out.println("in reg servlet, create new user");
			if(User.registerUser(userName, userPw, con)) {
				// register succeed
				User currUser = new User(userName, con);
				session.setAttribute("user", currUser);
				//session.setAttribute("username", userName);
				RequestDispatcher dispatch = request.getRequestDispatcher("Home.jsp");
				dispatch.forward(request, response);
			} else {
				RequestDispatcher dispatch = request.getRequestDispatcher("Error.html");
				dispatch.forward(request, response);
			}
		}
	}

}

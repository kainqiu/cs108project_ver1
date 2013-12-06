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
 * Servlet implementation class SearchFriendServlet
 */
@WebServlet("/SearchFriendServlet")
public class SearchFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFriendServlet() {
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
		}

		String searchStr = request.getParameter("friend_name");
		//session.setAttribute("searchStr", searchStr);
		RequestDispatcher dispatch = request.getRequestDispatcher("SearchResults.jsp?search=" + searchStr);
		dispatch.forward(request, response);
	}

}

package quizsite;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FriendServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendServlet() {
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
		User currUser = (User) session.getAttribute("user");
		int addFriendId = Integer.parseInt(request.getParameter("addFriendId"));
		System.out.println("in create Friends, currUser name is " + currUser.getUsername() + ", add friend id is" + addFriendId);

		// update page html
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
				+ " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<title>Confirm Friend Request Result</title>");
		out.println("<link rel=\"stylesheet\" href=\"/QuizWebsite/Home.css\" type=\"text/css\">");
		out.println("</head>");
		out.println("<body>");
		if(Friend.areFriends(currUser.getId(), addFriendId, con)) {
			out.println("<h3>You are already friends.</h3>");
		} else {
			if(Friend.createFriends(currUser.getId(), addFriendId, con)) {
				out.println("<h3>Successfully adding friend.</h3>");
			} else {
				out.println("<h3>Adding friend failed.</h3>");
			}
		}
		out.println("<a href=\"Home.jsp\">&lt;&lt Back to Home</a>");
		out.println("</body>");
		out.println("</html>");
	}

}

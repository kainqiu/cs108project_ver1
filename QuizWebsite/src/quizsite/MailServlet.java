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
 * Servlet implementation class MailServlet
 */
@WebServlet("/MailServlet")
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailServlet() {
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
		String reqType = request.getParameter("reqType");
		String usernameMailTo = request.getParameter("usernameMailTo");
		int idMailTo = Integer.parseInt(request.getParameter("idMailTo"));
		
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
		
		boolean sendSuccess = false;
		if(reqType.equals("friend")) {
			sendSuccess = Mail.createMail(currUser.getId(), idMailTo, Mail.FRIEND_REQ, "Friend request from " + usernameMailTo, con);
		} else if(reqType.equals("challenge")) {
			sendSuccess = Mail.createMail(currUser.getId(), idMailTo, Mail.CHALLENGE, request.getParameter("challengeUrl"), con);
		} else {
			//System.out.println("in create mail, currUser name is " + currUser.getUsername() + ", friend id is " + Integer.parseInt(request.getParameter("id")));
			sendSuccess = Mail.createMail(currUser.getId(), idMailTo, Mail.NOTE, request.getParameter("noteContent"), con);
		}
		
		if(sendSuccess) {
			User.incrementNumNewMailByOne(idMailTo, con);
			out.println("<h3>Mail successfully sent.</h3>");
		} else {
			out.println("<h3>Fail to send the mail.</h3>");
		}
		out.println("<a href=\"Home.jsp\">&lt;&lt Back to Home</a>");
		out.println("</body>");
		out.println("</html>");
	}

}

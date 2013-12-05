<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="quizsite.DBConnection, quizsite.User, quizsite.Mail, java.util.*, java.sql.ResultSet, java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%
DBConnection con = (DBConnection) session.getAttribute("connection");
if(con == null) {
	con = new DBConnection();
	session.setAttribute("connection", con);
}

User u = new User(Integer.parseInt(request.getParameter("id")), con);
User currUser = (User) session.getAttribute("user");
if(currUser != null && u.getId() == currUser.getId()) {
	RequestDispatcher dispatch = request.getRequestDispatcher("Home.jsp");
	dispatch.forward(request, response);
}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Home.css" type="text/css">
<title>User Page</title>
</head>
<body>

<p>Username: <%= u.getUsername() %></p>

<form action="MailServlet" method="post">
<input name="usernameMailTo" type="hidden" value="<%= u.getUsername() %>" />
<input name="idMailTo" type="hidden" value="<%= u.getId() %>" />
<button name="reqType" type="submit" value="friend">Add as Friend</button><br/>
<button name="reqType" type="submit" value="challenge">Send Challenge</button>
<span>Challenge url: <input type="text" name="challengeUrl"></span><br/>
<p><textarea cols="40" rows="5" name="noteContent" placeholder="Write your msg here..."></textarea></p>
<button name="reqType" type="submit" value="note">Send Note</button>
</form>

</body>
</html>
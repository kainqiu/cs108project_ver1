<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="quizsite.DBConnection, quizsite.User, quizsite.Mail, java.util.*, java.sql.ResultSet, java.text.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
User currUser = (User) session.getAttribute("user");
ArrayList<Mail> mails = currUser.getMails();
DBConnection con = (DBConnection) session.getAttribute("connection");
if(con == null) {
	con = new DBConnection();
	session.setAttribute("connection", con);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Home.css" type="text/css">
<title>All Mails</title>
</head>
<body>

<p>Username: <%= currUser.getUsername() %></p>
<div class="msg">
<p class="block_title">All Mails Received</p>
<%
for(int i = 0; i < mails.size(); i++) {
	int fromId = mails.get(i).getFromId();
	String fromUsername = User.getUsernameById(fromId, con);
	if(mails.get(i).getType() == Mail.FRIEND_REQ) {
		out.println("<p class='each_mail'>From <a href=\"User.jsp?id=" + fromId + "\">" + fromUsername + "</a>: </p><table><tr><td>Friend request: </td><td><form action=\"FriendServlet\" method=\"post\"><input type=\"submit\" value=\"Confirm\"/><input type=\"hidden\" name=\"addFriendId\" value=\"" + fromId + "\" /></form></td></tr></table>");
	} else if(mails.get(i).getType() == Mail.CHALLENGE) {
		out.println("<p class='each_mail'>From <a href=\"User.jsp?id=" + fromId + "\">" + fromUsername + "</a>: </p><p class='mail_msg'>Challenge url link: " + mails.get(i).getContent() + "</p>");
	} else {
		out.println("<p class='each_mail'>From <a href=\"User.jsp?id=" + fromId + "\">" + fromUsername + "</a>: </p><p class='mail_msg'>Message: " + mails.get(i).getContent() + "</p>");
	}
}
%>
</div>
<br/><br/><a href="Home.jsp">&lt;&lt; Back to Home</a>
</body>
</html>
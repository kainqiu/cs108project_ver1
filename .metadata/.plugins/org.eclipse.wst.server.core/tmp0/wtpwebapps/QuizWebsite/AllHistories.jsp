<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="quizsite.DBConnection, quizsite.User, quizsite.History, java.util.*, java.sql.ResultSet, java.text.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
User currUser = (User) session.getAttribute("user");
ArrayList<History> histories = currUser.getHistories();
DBConnection con = (DBConnection) session.getAttribute("connection");
if(con == null) {
	con = new DBConnection();
	session.setAttribute("connection", con);
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Home.css" type="text/css">
<title>User History</title>
</head>
<body>

<p>Username: <%= currUser.getUsername() %></p>
<div class="msg">
<p class="block_title">User History on Quiz Taken</p>
<%
for(int i = 0; i < histories.size(); i++) {
	History h = histories.get(i);
	out.println("<p class='each_history'>Take quiz " + h.getQuizId() + ", using " + h.getElapsedTime() + ", score is " + h.getScore() + ", at " + h.getFinishAt() + ". </p>");
}
%>
</div>
<br/><br/><a href="Home.jsp">&lt;&lt; Back to Home</a>
</body>
</html>
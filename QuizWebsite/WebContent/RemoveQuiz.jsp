<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="quizsite.DBConnection, quizsite.User, quizsite.Quiz, quizsite.Admin, quizsite.Mail, java.util.*, java.sql.ResultSet, java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
DBConnection con = (DBConnection) session.getAttribute("connection");
if(con == null) {
	con = new DBConnection();
	session.setAttribute("connection", con);
}

int quizId = Integer.parseInt(request.getParameter("id"));
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Home.css" type="text/css">
<title>Remove Quiz</title>
</head>
<body>
<p>Are you sure to remove the quiz "<%= Quiz.getTitleById(quizId, con) %>" from the database?</p>
<form action="RemoveQuizServlet" method="post">
<input type="submit" value="Remove"/>
<input name="quizIdToRemove" type="hidden" value="<%= quizId %>" />
</form>
<br />
<a href="Admin.jsp">&lt;&lt; Back</a>
</body>
</html>
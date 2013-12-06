<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="quizsite.*, quizsite.DBConnection, quizsite.History, quizsite.User, quizsite.Mail, java.util.*, java.sql.ResultSet, java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String tag = request.getParameter("tag");
DBConnection con = (DBConnection) session.getAttribute("connection");
if(con == null) {
	con = new DBConnection();
	session.setAttribute("connection", con);
}
%>

<p> Quizzes that are tagged as <%= tag %></p>
<%
ArrayList<Rank.QuizInfo> quizzes = Rank.getQuizzesByTag(con, tag);

if(quizzes.isEmpty()){
	%>
	<p> There are no quizzes of this tag. </p>
	<% 
}

for(int i = 0; i < quizzes.size(); i++) {
	out.println("<p class='each_quiz'><a href='QSummary.jsp?id=" + quizzes.get(i).id + "'>" + quizzes.get(i).title + "</a></p>");
}
%>

<form action="Home.jsp" method = "post">
<p> <input type="submit" value = "Return to Homepage" /></p>
</form>
</body>
</html>
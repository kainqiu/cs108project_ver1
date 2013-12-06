<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="quizsite.DBConnection, quizsite.User, quizsite.Admin, quizsite.Mail, java.util.*, java.sql.ResultSet, java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
DBConnection con = (DBConnection) session.getAttribute("connection");
if(con == null) {
	con = new DBConnection();
	session.setAttribute("connection", con);
}

String str = request.getParameter("search");
ArrayList<User.UserInfo> searchResult = User.getAllUsersWithString(str, con);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Home.css" type="text/css">
<title>Search Result</title>
</head>
<body>
<p>Search Result:</p>
<br/>
<table>
<%
for(int i = 0; i < searchResult.size(); i++) {
	User.UserInfo ui = searchResult.get(i);
	out.println("<tr><td class='each_history'><a href='User.jsp?id=" + ui.userId + "'>" + ui.username + "</a></td></tr>");
}
%>
</table>
<br/>
<a href="Home.jsp">&lt;&lt; Home</a>
</body>
</html>
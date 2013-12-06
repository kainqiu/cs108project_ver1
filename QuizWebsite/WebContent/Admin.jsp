<%@page import="quizsite.Admin"%>
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

ArrayList<Admin.UserInfo> userInfoList = Admin.getAllUsers(con);
ArrayList<Admin.QuizInfo> quizInfoList = Admin.getAllQuizzes(con);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Home.css" type="text/css">
<title>Admin Page</title>
</head>
<body>

<p>Hello, admin!</p>
<p>Currently there are <%= userInfoList.size() %> users in all.</p>
<div class="friends_activity">
<p class="block_title">All Users</p>
<table>
<%
for(int i = 0; i < userInfoList.size(); i++) {
	Admin.UserInfo ui = userInfoList.get(i);
	out.println("<tr><td class='each_history'><a href='User.jsp?id=" + ui.userId + "'>" + ui.username + "</a></td><td> took " + User.getNumQuizTakenByUserId(ui.userId, con) + " quizzes</td><td><a href='RemoveUser.jsp?id=" + ui.userId + "'>Remove</a></td></tr>");
}
%>
</table>
</div>

<div class="friends_activity">
<p class="block_title">All Quizzes</p>
<table>
<%
for(int i = 0; i < quizInfoList.size(); i++) {
	Admin.QuizInfo qi = quizInfoList.get(i);
	out.println("<tr><td class='each_history'><a href='QSummary.jsp?id=" + qi.quizId + "'>" + qi.quizTitle + "</a></td><td><a href='RemoveQuiz.jsp?id=" + qi.quizId + "'>Remove Quiz</a></td><td><a href='RemoveQuizHistory.jsp?id=" + qi.quizId + "'>Remove History</a></td></tr>");
}
%>
</table>
</div>

</body>
</html>
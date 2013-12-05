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
ArrayList<Admin.AdminAnnounceInfo> announceList = Admin.getAllAdminAnnounce(con);

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Home.css" type="text/css">
<title>Admin Page</title>
</head>
<body>

<p>Hello, admin!</p>
<p><a href="Home.jsp">&lt;&lt; Home</a></p>
<p>Currently there are <%= userInfoList.size() %> users and <%= quizInfoList.size() %> quizzes in all.</p>
<p>Create a new announcement:</p>
<form action="AnnounceServlet" method="post">
<p><textarea cols="40" rows="5" name="announceContent" placeholder="Write your announcement here..."></textarea></p>
<button name="reqType" type="submit" value="note">Submit</button>
</form>
<br/>

<div class="friends_activity">
<p class="block_title">All Announcements</p>
<table>
<%
for(int i = 0; i < announceList.size(); i++) {
	Admin.AdminAnnounceInfo aai = announceList.get(i);
	out.println("<tr><td class='each_history'><a href='User.jsp?id=" + aai.adminId + "'>" + User.getUsernameById(aai.adminId, con) + "</a></td><td> - " + aai.content + "</td><td><a href='RemoveAnnounce.jsp?id=" + aai.announceId + "'>Remove</a></td></tr>");
}
%>
</table>
</div>

<div class="friends_activity">
<p class="block_title">All Users</p>
<table>
<%
for(int i = 0; i < userInfoList.size(); i++) {
	Admin.UserInfo ui = userInfoList.get(i);
	if(User.isUserAdmin(ui.userId, con)) {
		out.println("<tr><td class='each_history'><a href='User.jsp?id=" + ui.userId + "'>" + ui.username + "</a></td><td> took " + User.getNumQuizTakenByUserId(ui.userId, con) + " quizzes</td><td><a href='RemoveUser.jsp?id=" + ui.userId + "'>Remove User</a></td><td><a href='RemoveAdmin.jsp?id=" + ui.userId + "'>Remove Admin</a></td></tr>");
	} else {
		out.println("<tr><td class='each_history'><a href='User.jsp?id=" + ui.userId + "'>" + ui.username + "</a></td><td> took " + User.getNumQuizTakenByUserId(ui.userId, con) + " quizzes</td><td><a href='RemoveUser.jsp?id=" + ui.userId + "'>Remove User</a></td><td><a href='PromoteUser.jsp?id=" + ui.userId + "'>Promote to Admin</a></td></tr>");
	}
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
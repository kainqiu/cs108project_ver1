<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="quizsite.*, quizsite.DBConnection, quizsite.History, quizsite.User, quizsite.Mail, java.util.*, java.sql.ResultSet, java.text.*"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
User currUser = (User) session.getAttribute("user");
ArrayList<Mail> mails = currUser.getMails();
ArrayList<History> histories = currUser.getHistories();
ArrayList<User> friends = currUser.getFriendsList();
ArrayList<User.Activity> friendsAct = currUser.getRecentFriendsActivities();
DBConnection con = (DBConnection) session.getAttribute("connection");
if(con == null) {
	con = new DBConnection();
	session.setAttribute("connection", con);
}
int numNewMail = User.getNumNewMailById(currUser.getId(), con);
User.setNumNewMailToZeroById(currUser.getId(), con);

// get rank quiz
ArrayList<Rank.QuizInfo> popularQuizList = Rank.getPopularQuiz(con);
ArrayList<Rank.QuizInfo> recentQuizList = Rank.getRecentCreatedQuiz(con);
ArrayList<Rank.QuizInfo> recentCreatedByUserList = Rank.getQuizCreatedByUserId(con, currUser.getId());
ArrayList<Rank.QuizInfo> recentTakenQuizByUserList = Rank.getRecentTakenQuizByUserId(con, currUser.getId());
session.setAttribute("sortType", "score");
ArrayList<Admin.AdminAnnounceInfo> announceList = Admin.getAllAdminAnnounce(con);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Home.css" type="text/css">
<title>Home</title>
</head>

<body>
<p class="logout"><a href="Login.html">Logout</a></p>
<h3>Hello, <%= currUser.getUsername() %>! Welcome to Quizzard Training!</h3>
<%
if(User.isUserAdmin(currUser.getId(), con)) {
	out.println("<p><a href='Admin.jsp'>Go to Admin Page &gt;&gt;</a></p>");
}
%>


<br/>

<div class="msg">
<p class="block_title">Your Inbox
<% if(numNewMail != 0) {
	out.println("<span class='new_mail_notice'>(" + numNewMail + " new mails)</span>");
}
%>
</p>
<%
for(int i = 0; i < numNewMail; i++) {
	int fromId = mails.get(i).getFromId();
	String fromUsername = User.getUsernameById(fromId, con);
	if(mails.get(i).getType() == Mail.FRIEND_REQ) {
		out.println("<p class='each_mail'>From <a href=\"User.jsp?id=" + fromId + "\">" + fromUsername + "</a>: </p><table><tr><td>Friend request: </td><td><form action=\"FriendServlet\" method=\"post\"><input type=\"submit\" value=\"Confirm\"/><input type=\"hidden\" name=\"addFriendId\" value=\"" + fromId + "\" /></form></td></tr></table>");
	} else if(mails.get(i).getType() == Mail.CHALLENGE) {
		out.println("<p class='each_mail'>From <a href=\"User.jsp?id=" + fromId + "\">" + fromUsername + "</a>: </p><p class='mail_msg'>Challenge url link: <a href='" + mails.get(i).getContent() + "'>Take the quiz</a></p>");
	} else {
		out.println("<p class='each_mail'>From <a href=\"User.jsp?id=" + fromId + "\">" + fromUsername + "</a>: </p><p class='mail_msg'>Message: " + mails.get(i).getContent() + "</p>");
	}
}
%>
<a href="AllMails.jsp">Check All Mail &gt;&gt;</a>
</div>
<br/><br/>
<div class="msg">
<p class="block_title">Admin Announcements</p>
<table>
<%
for(int i = 0; i < announceList.size(); i++) {
	Admin.AdminAnnounceInfo aai = announceList.get(i);
	out.println("<tr><td class='each_history'><a href='User.jsp?id=" + aai.adminId + "'>" + User.getUsernameById(aai.adminId, con) + "</a></td><td> - " + aai.content + "</td></tr>");
}
%>
</table>
</div>
<br/>
<br/>

<div class="msg">
<p class="block_title"> Browse Quizzes </p>
<a href="quizzesWebpage.jsp">View All Quizzes </a>
<form action="quizByTag.jsp" method = "post">
<p>Browse by tag: <input type="text" name="tag"> 
<input type = "submit" value = "Search!"/></p>
<form action="QuizServlet" method = "post">
<input type="submit" value = "Create New Quiz" />
</form>
</form>
</div>


<div class="lists">
<div class="pop_quiz list_block">
<p class="block_title">Popular Quizzes</p>
<%
for(int i = 0; i < popularQuizList.size(); i++) {
	out.println("<p class='each_quiz'><a href='QSummary.jsp?id=" + popularQuizList.get(i).id + "'>" + popularQuizList.get(i).title + "</a></p>");
}
%>
</div>
<div class="recent_created_quiz list_block">
<p class="block_title">Recently Created Quizzes</p>
<%
for(int i = 0; i < recentQuizList.size(); i++) {
	out.println("<p class='each_quiz'><a href='QSummary.jsp?id=" + recentQuizList.get(i).id + "'>" + recentQuizList.get(i).title + "</a></p>");
}
%>
</div>
<div class="taken_quiz_activity list_block">
<p class="block_title">Your Recently Quizzes Taken</p>
<%
for(int i = 0; i < recentTakenQuizByUserList.size(); i++) {
	out.println("<p class='each_quiz'><a href='QSummary.jsp?id=" + recentTakenQuizByUserList.get(i).id + "'>" + recentTakenQuizByUserList.get(i).title + "</a></p>");
}
%>
</div>
<div class="recent_self_created_quiz list_block">
<p class="block_title">Quizzes Created by You</p>
<%
for(int i = 0; i < recentCreatedByUserList.size(); i++) {
	out.println("<p class='each_quiz'><a href='QSummary.jsp?id=" + recentCreatedByUserList.get(i).id + "'>" + recentCreatedByUserList.get(i).title + "</a></p>");
}
%>
</div>
</div>

<div class="friends_activity">
<p class="block_title">Friends' Activity</p>
<p> 
<form action="SearchFriendServlet" method="post" style="text-align:right !important;">
<span>Search Users:<input type="text" name="friend_name"></span>
<button name="search_string" type="submit" value="friend_name">Search</button>
</form></p>
<%
for(int i = 0; i < friendsAct.size(); i++) {
	User.Activity f = friendsAct.get(i);
	if(f.isCreate) {
		out.println("<p class='each_history'>Your friend <a href='User.jsp?id=" + f.userId + "'>" + f.username + "</a> created a quiz <a href='QSummary.jsp?id=" + f.quizId + "'>" + f.quizTitle + "</a> at " + f.timestamp + ". </p>");
	} else {
		out.println("<p class='each_history'>Your friend <a href='User.jsp?id=" + f.userId + "'>" + f.username + "</a> took a quiz <a href='QSummary.jsp?id=" + f.quizId + "'>" + f.quizTitle + "</a> at " + f.timestamp + ". </p>");
	}
}
%>
</div>

<div class="user_history">
<p class="block_title">Your Quiz-Taking History</p>
<%
for(int i = 0; i < histories.size(); i++) {
	if(i > 10) break;
	History h = histories.get(i);
	out.println("<p class='each_history'>Took quiz <b><a href='QSummary.jsp?id=" + h.getQuizId() + "'>" + Quiz.getTitleById(h.getQuizId(), con) + "</a></b>, at <i>" + h.getFinishAt() + "</i> and received <b>" + h.getScore() + "</b> out of <b>" + h.getMaxPossScore() + " </b> points in <b>"+ h.getElapsedTime() + "</b> seconds. </p>");

}
%>
<a href="AllHistories.jsp">Check Full History &gt;&gt;</a>
</div>



<!--<form action="FriendServlet" method="post">
<input type="submit" value="Create Friend"/>
</form>

<form action="MailServlet" method="post">
<input type="submit" value="Create Mail"/>
</form>

-->
</body>
</html>
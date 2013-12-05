<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="quizsite.DBConnection, quizsite.*, java.util.*, java.sql.*, java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
User currUser = (User) session.getAttribute("user");
DBConnection con = (DBConnection) session.getAttribute("connection");
int quizId = Integer.parseInt(request.getParameter("id"));
Quiz q = new Quiz(quizId, con);
User creator = new User(q.getCreatorId(), con);
ArrayList<RecordInfo> highRec = Quiz.getHighestRecord(quizId, con);
ArrayList<RecordInfo> recentRec = Quiz.getRecentRecords(quizId, con);
ArrayList<RecordInfo> lastRec = Quiz.getLastDayTop(quizId, con);
String sortType = (String) session.getAttribute("sortType");
System.out.println("init type is " + sortType);
if(sortType == null || sortType.equals("")) {
	sortType = "score";
}
ArrayList<RecordInfo> pastRec = Quiz.getPastRecByUserId(quizId, currUser.getId(), sortType, con);
Quiz.QuizSum qs = Quiz.getQuizSummary(quizId, con);

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Quiz.css" type="text/css">
<script type="text/javascript" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
<title>Quiz: <%= q.getTitle() %></title>
</head>
<body>
<p>Quiz Title: <%= q.getTitle() %></p>
<p>Creator: <a href="User.jsp?id=<%= creator.getId() %>"><%= creator.getUsername() %></a></p>
<p>Description: <%= q.getDescription() %></p>
<p><a href="TakeQuiz.jsp?id=<%= q.getID() %>&title=<%= q.getTitle() %>">Start taking quiz &gt;&gt;</a></p>
<p>Quiz Statistics: <%= qs.numOfTaken %> users have taken this quiz and the average score is <%= (int)qs.avgScore %>.</p>
<p><a href="Home.jsp">&lt;&lt; Home</a></p>

<div class="block">
<p class="block_title">Your Past Performance</p>
<p>Order By:</p>
<form action="QuizSummaryServlet" method="post">
<select name="type" id="type_select">
<%
if(sortType.equals("score")) {
	out.println("<option value='score' selected>score</option><option value='finishAt'>date taken</option><option value='elapsedTime'>time elapsed</option>");
} else if (sortType.equals("elapsedTime")) {
	out.println("<option value='score'>score</option><option value='finishAt'>date taken</option><option value='elapsedTime' selected>time elapsed</option>");
} else {
	out.println("<option value='score'>score</option><option value='finishAt' selected>date taken</option><option value='elapsedTime'>time elapsed</option>");
}
%>
</select>
<input name="quizId" type="hidden" value="<%= request.getParameter("id") %>" />
<button name="resort" type="submit" value="sort">Update</button><br/>
</form>

<%
for(int i = 0; i < pastRec.size(); i++) {
	out.println("<p class='each_rec'>Score: " + pastRec.get(i).score + ", time used: " + pastRec.get(i).elapsedTime + " - <a href='User.jsp?id=" + pastRec.get(i).userId + "'>" + User.getUsernameById(pastRec.get(i).userId, con) +  "</a> @" + pastRec.get(i).finishAt + "</p>");
}
%>
</div>

<div class="block">
<p class="block_title">Highest Records</p>
<%
for(int i = 0; i < highRec.size(); i++) {
	out.println("<p class='each_rec'>Score: " + highRec.get(i).score + ", time used: " + highRec.get(i).elapsedTime + " - <a href='User.jsp?id=" + highRec.get(i).userId + "'>" + User.getUsernameById(highRec.get(i).userId, con) +  "</a> @" + highRec.get(i).finishAt + "</p>");
}
%>
</div>

<div class="block">
<p class="block_title">Top Performers Last Day</p>
<%
for(int i = 0; i < lastRec.size(); i++) {
	out.println("<p class='each_rec'>Score: " + lastRec.get(i).score + ", time used: " + lastRec.get(i).elapsedTime + " - <a href='User.jsp?id=" + lastRec.get(i).userId + "'>" + User.getUsernameById(lastRec.get(i).userId, con) +  "</a> @" + lastRec.get(i).finishAt + "</p>");
}
%>
</div>

<div class="block">
<p class="block_title">Recent Test Records</p>
<%
for(int i = 0; i < recentRec.size(); i++) {
	out.println("<p class='each_rec'>Score: " + recentRec.get(i).score + ", time used: " + recentRec.get(i).elapsedTime + " - <a href='User.jsp?id=" + recentRec.get(i).userId + "'>" + User.getUsernameById(recentRec.get(i).userId, con) +  "</a> @" + recentRec.get(i).finishAt + "</p>");
}
%>
</div>

</body>
<script>
</script>
</html>
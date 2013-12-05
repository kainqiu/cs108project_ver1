<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@page
	import="quizsite.DBConnection, quizsite.*, java.util.*, java.sql.*, java.text.*"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Quiz.css" type="text/css">
<title><%=session.getAttribute("quizTitle")%>: Results</title>
</head>
<body>
<h1>Quiz Results</h1>
<div class="results">
<ol>
<%
ArrayList<String> answers = (ArrayList<String>) session.getAttribute("answers");
ArrayList<Question> questions = ((Quiz)session.getAttribute("currQuiz")).setOfQuestions();
ArrayList<Boolean> results = (ArrayList<Boolean>) session.getAttribute("results");
int score = 0;
for(int i=0; i<answers.size(); i++){
	if(questions.get(i).getType() != 4){
		out.print("<li>" + questions.get(i).getQuestion() + "<br>");
	}else{
		out.print("<li><br><img src=\"" + questions.get(i).getQuestion() + "\" /><br>");
	}
	out.print("<b>Your answer:</b> " + answers.get(i));
	out.println("<br><b>Correct answer(s):</b> ");
	ArrayList<String> correctAnswers = questions.get(i).getAllPossAnswers();
	for(int j=0; j<correctAnswers.size(); j++){
		out.println(correctAnswers.get(j) + "<br>");
	}
	if(results.get(i)){
		out.print("<font color=\"green\"><b>Correct!</b></font></li><br><br>");
		score++;
	}else{
		out.print("<font color=\"red\"><b>Wrong</b></font></li><br>");
	}
}
long start = (Long) session.getAttribute("startTime");
java.util.Date date= new java.util.Date();
double elapsed = (date.getTime() - start)/1000.0;

out.print("</ol> <h3>Final Score: " + score + "/" + questions.size() + ", Time Elapsed: " + elapsed + " seconds </h3>");

//need to change parameters within history	
History.createHistory(((User) session.getAttribute("user")).getId(), ((Quiz) session.getAttribute("currQuiz")).getID(), score, elapsed, (DBConnection)session.getAttribute("con"));

session.setAttribute("currQuiz", null);
session.setAttribute("answers", null);
session.setAttribute("results", null);
session.setAttribute("startTime", null);

%>

<form method="get" action="Home.jsp">
    <button type="submit">Home</button>
</form>

</div>
</body>
</html>
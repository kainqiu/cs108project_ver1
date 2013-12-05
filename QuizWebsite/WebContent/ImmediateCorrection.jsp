<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@page
	import="quizsite.*, java.util.*, java.text.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=session.getAttribute("quizTitle")%>: Immediate Correction</title>
</head>
<body>
<h1>Question <%=session.getAttribute("currIdx") %>: Immediate Correction</h1>
<%
Quiz quiz = (Quiz) session.getAttribute("currQuiz");
ArrayList<Question> questions = quiz.setOfQuestions();
ArrayList<String> answers = (ArrayList<String>) session.getAttribute("answers");
ArrayList<Boolean> results =(ArrayList<Boolean>) session.getAttribute("results");
int i = (Integer)session.getAttribute("currIdx") - 1 ;

if(questions.get(i).getType() != 4){
	out.print("<b>Question: </b>" + questions.get(i).getQuestion() + "<br>");
}else{
	out.print("<br><img src=\"" + questions.get(i).getQuestion() + "\" /><br>");
}
out.print("<b>Your answer:</b> " + answers.get(i));
out.println("<br><b>Correct answer(s):</b> ");
ArrayList<String> correctAnswers = questions.get(i).getAllPossAnswers();
for(int j=0; j<correctAnswers.size(); j++){
	out.println(correctAnswers.get(j) + "<br>");
}
if(results.get(i)){
	out.print("<font color=\"green\"><b>Correct!</b></font><br><br>");
}else{
	out.print("<font color=\"red\"><b>Wrong</b></font><br>");
}

if(i < questions.size()-1){
	session.setAttribute("next", "TakeQuiz.jsp");
}else{
	session.setAttribute("next", "QuizResults.jsp");
}
%>
<br>
<form method="post" action=<%=session.getAttribute("next") %>>
    <button type="submit">Next >></button>
</form>
</body>
</html>
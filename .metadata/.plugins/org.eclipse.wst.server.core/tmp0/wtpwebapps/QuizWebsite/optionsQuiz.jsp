<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quizsite.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Quiz Options </title>
</head>
<form action="finishCreationQuiz.jsp" method="post">

<body>
<%
Quiz quiz = (Quiz) session.getAttribute("newQuiz");
%>

<h1> Choose options for your quiz</h1>

<h2> Random Questions? </h2>

<p> If you choose yes, the questions will be presented in random order </p>
<p> If you choose no, the questions will always be presented in the same order </p>

<select name = "randomQuestions">
  <option value="1"> Yes </option>
  <option value="2"> No </option>
</select>

<h2> Choose how many pages to display questions</h2>
<p> If you choose one page, all the questions will be displayed on a single webpage and there will be one submit button</p>
<p> If you choose multiple pages, the quiz will display a single question and allow the user to submit the answer
	and then display the next question</p>
<select name = "pagenumbers">
  <option value="1"> One page</option>
  <option value="2"> Multiple pages</option>
</select>

<h2> Immediate Correction? (only for multiple page quizzes)</h2>
<p> If you choose yes, the user will receive immediate feedback on an answer.</p>
<p> If you choose no, the quiz will only be graded once all the questions have been seen and responded to. </p>

<select name = "immediateCorrection">
  <option value="1"> Yes </option>
  <option value="2"> No </option>
</select>

<p> <input type = "submit"  value = "Finish creating quiz!"  /> </p>

</form>

</body>
</html>

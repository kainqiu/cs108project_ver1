<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quizsite.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Quiz.css" type="text/css">
<title>Creating Quiz</title>
</head>
<body>
<form action="optionsQuiz.jsp" method = "post">

<h1> Create your quiz! </h1>

<h2> Enter the title of your quiz: </h2>
<p> Please have the title be less than 50 characters </p>
<input type = "text" name = "quiztitle">

<h2> Enter your overall description of the purpose of this quiz: </h2>
<p> Please have the description be less than 200 characters </p>

<input type="text" name = "quizdescription">

<p> Click next to choose options for your quiz: <input type="submit" value = "Next" /></p>

</form>

</body>
</html>
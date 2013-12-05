<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quizsite.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Creating Quiz</title>
</head>
<body>
<form action="chooseQuestionType.jsp" method = "post">

<h1> Create your quiz! </h1>

<h2> Enter the title of your quiz: </h2>
<input type = "text" name = "quiztitle">

<h2> Enter your overall description of the purpose of this quiz: </h2>
<input type="text" name = "quizdescription">

<p> Click next to create your questions for this quiz: <input type="submit" value = "Next" /></p>

</form>

</body>
</html>
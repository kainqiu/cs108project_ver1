<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*, quizsite.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Quiz.css" type="text/css">
<title>Question Type</title>
</head>
<h1> Choose the question type </h1>
<body>

<form action="CreateQR.html" method = "post">
<p> <input type="submit" value = "Question-Response" /> </p>
</form>

<form action="CreateFITB.html" method = "post">
<p> <input type="submit" value = "Fill In the Blank" /> </p>
</form>

<form action="CreateMC.html" method = "post">
<p> <input type="submit" value = "Multiple Choice" /> </p>
</form>

<form action="CreatePR.html" method = "post">
<p> <input type="submit" value = "Picture-Response Questions" /> </p>
</form>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,quizsite.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz tags</title>
</head>
<body>

<%
	Quiz quiz = (Quiz) session.getAttribute("newQuiz");

	String categoriesValue = request.getParameter("categories");
	quiz.setCategory(categoriesValue);
%>

<h1>Attach tags to your quiz! </h1>
<h3>If you have multiple tags, please type each tag on a new line.</h3>

<form action="optionsQuiz.jsp" method="post">
<p>Tag(s): <br> <textarea rows = "4" cols = "50" name="tags"></textarea> </p>
<input name = "type" type = "hidden" value = "1"/>
<input type="submit" name="action" value="Choose options for your quiz!"/>
</form>




</body>
</html>
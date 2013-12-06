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

int userId = Integer.parseInt(request.getParameter("id"));
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove User</title>
</head>
<body>
<p>Are you sure to remove the user "<%= User.getUsernameById(userId, con) %>" from the database?</p>
<form action="RemoveUserServlet" method="post">
<input type="submit" value="Remove"/>
<input name="userIdToRemove" type="hidden" value="<%= userId %>" />
</form>
<br />
<a href="Admin.jsp">&lt;&lt; Back</a>
</body>
</html>
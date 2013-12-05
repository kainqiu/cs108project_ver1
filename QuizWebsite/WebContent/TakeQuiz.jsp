<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="quizsite.DBConnection,quizsite.*,java.util.*,java.sql.*,java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	DBConnection con = (DBConnection) session.getAttribute("connection");
	if (con == null) {
		con = new DBConnection();
		session.setAttribute("connection", con);
	}

	Quiz currQuiz = (Quiz) session.getAttribute("currQuiz");
	if (currQuiz == null) {
		int quizID = Integer.parseInt(request.getParameter("id"));
		currQuiz = new Quiz();
		try {
			String selectSQL = "SELECT type, question, answer, MCoption FROM questions WHERE quizId = ? ORDER BY id";
			PreparedStatement preStmt = con.getConnection()
					.prepareStatement(selectSQL);
			preStmt.setInt(1, quizID);
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				Question newQn = null;
				int type = rs.getInt("type");
				String question = rs.getString("question");
				String answer = rs.getString("answer");
				String MC = rs.getString("MCoption");
				switch (type) {
				case 1:
					newQn = new QResponse(question, answer);
					break;
				case 2:
					newQn = new FillIn(question, answer);
					break;
				case 3:
					newQn = new MultiChoice(question, answer);
					((MultiChoice) newQn).addMCOptions(MC);
					break;
				case 4:
					newQn = new PictureResponse(question, answer);
					break;
				default:
					newQn = null;
					break;
				}
				if (!newQn.equals(null)) {
					newQn.setType(type);
					currQuiz.addQuestion(newQn);
				}
			}
			selectSQL = "SELECT title, randomQuestions, onePage, immediateCorrection FROM quizzes WHERE id = ?";
			preStmt = con.getConnection().prepareStatement(selectSQL);
			preStmt.setInt(1, quizID);
			rs = preStmt.executeQuery();
			if (rs.next()) {
				currQuiz.setRandomize(rs.getBoolean("randomQuestions"));
				currQuiz.setDisplay(rs.getBoolean("onePage"));
				currQuiz.setCorrection(rs.getBoolean("immediateCorrection"));
				currQuiz.setID(quizID);
				session.setAttribute("quizTitle", rs.getString("title"));
			} else
				System.out.println("ERROR. QUIZ QUERY EMPTY!!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<Question> questions = currQuiz.setOfQuestions();
		if (currQuiz.isRandom())
			Collections.shuffle(currQuiz.setOfQuestions());
		
		ArrayList<String> answers = new ArrayList<String>();
		ArrayList<Boolean> results = new ArrayList<Boolean>(questions.size());
		
		java.util.Date date= new java.util.Date();
		
		session.setAttribute("startTime", date.getTime());
		session.setAttribute("currQuiz", currQuiz);
		session.setAttribute("results", results);
		session.setAttribute("answers", answers);
	}
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/QuizWebsite/Quiz.css" type="text/css">
<title>Take Quiz: <%=session.getAttribute("quizTitle")%></title>
</head>
<body>
<div class="quiz">
<h1><%=session.getAttribute("quizTitle")%></h1>

<form action="SubmitQuizServlet" method="post">
<ol>
	<%
	ArrayList<Question> questions = currQuiz.setOfQuestions();
	if(currQuiz.isOnePage()){
		for (int i = 0; i < questions.size(); i++) {
			out.print("<li>");
			Question curr = questions.get(i);
			int currType = curr.getType();
			switch (currType) {
			case 1: //Question-Response
			case 2: //Fill-in
				out.print(curr.getQuestion() + "<br>");
				out.print("<input type = \"text\" value=\"\" name="
						+ Integer.toString(i) + " />");
				break;
			case 3: //Multiple Choice
				out.print(curr.getQuestion() + "<br>");
				ArrayList<String> options = ((MultiChoice) curr)
						.getMCOptions();
				options.add(curr.getAnswer());
				Collections.shuffle(options);
				for (int j = 0; j < options.size(); j++) {
					out.print("<input type = \"radio\" name = \"" + i
							+ "\" value = \"" + options.get(j) + "\">"
							+ options.get(j) + "<br>");
				}
				break;
			case 4: //Picture-Response
				out.print("<br><img src= \"" + curr.getQuestion()
						+ "\" /> <br><br>");
				out.print("<input type = \"text\" value=\"\" name=\"" + i + "\" />");
				break;
			default:
				out.print("ERROR");
				break;
			}
			out.print("</li><br>");

		}
		out.print("</ol><input type=\"submit\" value=\"Submit\"></form>");
	}else{
		Integer currIdx = (Integer) session.getAttribute("currIdx");
		if(currIdx == null){
			currIdx = 0;
			session.setAttribute("currIdx", currIdx);
		}
		Question curr = questions.get(currIdx);
		out.print("<p>" + (currIdx+1) + ". ");
		int currType = curr.getType();
		switch (currType) {
		case 1: //Question-Response
		case 2: //Fill-in
			out.print(curr.getQuestion() + "<br>");
			out.print("<input type = \"text\" value=\"\" name="
					+ Integer.toString(currIdx) + " />");
			break;
		case 3: //Multiple Choice
			out.print(curr.getQuestion() + "<br>");
			ArrayList<String> options = ((MultiChoice) curr)
					.getMCOptions();
			options.add(curr.getAnswer());
			Collections.shuffle(options);
			for (int j = 0; j < options.size(); j++) {
				out.print("<input type = \"radio\" name = \"" + currIdx
						+ "\" value = \"" + options.get(j) + "\">"
						+ options.get(j) + "<br>");
			}
			break;
		case 4: //Picture-Response
			out.print("<br><img src= \"" + curr.getQuestion()
					+ "\" /> <br><br>");
			out.print("<input type = \"text\" value=\"\" name=\"" + currIdx + "\" />");
			break;
		default:
			out.print("ERROR");
			break;
		}
		out.print("<br><br><input type=\"submit\" value=\"Next >>\"></form>");
		
	}
	%>

</div>



</body>
</html>
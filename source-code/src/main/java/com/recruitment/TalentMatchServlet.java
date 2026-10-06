package com.recruitment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/TalentMatchServlet")
public class TalentMatchServlet extends HttpServlet {

@Override
protected void doPost(HttpServletRequest request,
HttpServletResponse response)
throws ServletException, IOException {

int candidateId =
Integer.parseInt(request.getParameter("candidate_id"));

int jobId =
Integer.parseInt(request.getParameter("job_id"));

String candidateSkills = null;
String requiredSkills = null;
String candidateName = null;
String jobTitle = null;

String candidateSql =
"SELECT name, skills FROM candidate WHERE candidate_id = ?";

String jobSql =
"SELECT title, required_skills FROM job WHERE job_id = ?";

response.setContentType("text/html");

try (Connection con = DBConnection.getConnection()) {

// Get candidate skills
try (PreparedStatement ps =
con.prepareStatement(candidateSql)) {

ps.setInt(1, candidateId);

ResultSet rs = ps.executeQuery();

if (rs.next()) {
candidateName = rs.getString("name");
candidateSkills = rs.getString("skills");
}
}

// Get job required skills
try (PreparedStatement ps =
con.prepareStatement(jobSql)) {

ps.setInt(1, jobId);

ResultSet rs = ps.executeQuery();

if (rs.next()) {
jobTitle = rs.getString("title");
requiredSkills = rs.getString("required_skills");
}
}

PrintWriter out = response.getWriter();

out.println("<html>");
out.println("<head>");
out.println("<title>Talent Match Result</title>");
out.println("<link rel='stylesheet' href='style.css'>");
out.println("</head>");
out.println("<body>");

out.println("<div class='container'>");

if (candidateSkills == null) {

out.println("<h1>Candidate Not Found</h1>");

} else if (requiredSkills == null) {

out.println("<h1>Job Not Found</h1>");

} else {

int score =
TalentMatcher.calculateMatchScore(
candidateSkills,
requiredSkills
);

out.println("<h1>Talent Matching Result</h1>");

out.println("<h2>Candidate</h2>");
out.println("<p>" + candidateName + "</p>");

out.println("<p><b>Candidate Skills:</b> "
+ candidateSkills + "</p>");

out.println("<h2>Job</h2>");
out.println("<p>" + jobTitle + "</p>");

out.println("<p><b>Required Skills:</b> "
+ requiredSkills + "</p>");

out.println("<h2>Match Score: "
+ score + "%</h2>");

if (score >= 70) {

out.println("<h2>Strong Match</h2>");

} else if (score >= 40) {

out.println("<h2>Moderate Match</h2>");

} else {

out.println("<h2>Low Match</h2>");
}
}

out.println("</div>");
out.println("</body>");
out.println("</html>");

} catch (Exception e) {

e.printStackTrace();

response.getWriter()
.println("Database error: " + e.getMessage());
}
}
}
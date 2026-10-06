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

@WebServlet("/ViewInterviewServlet")
public class ViewInterviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String sql = """
                SELECT
                    i.interview_id,
                    c.name AS candidate_name,
                    j.title AS job_title,
                    i.interview_date,
                    i.interview_time,
                    i.interview_mode,
                    i.interviewer,
                    i.status
                FROM interview i
                JOIN application a
                    ON i.application_id = a.application_id
                JOIN candidate c
                    ON a.candidate_id = c.candidate_id
                JOIN job j
                    ON a.job_id = j.job_id
                ORDER BY i.interview_id
                """;

        response.setContentType("text/html");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Scheduled Interviews</title>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<h1>Scheduled Interviews</h1>");

            out.println("<table border='1'>");

            out.println("<tr>");
            out.println("<th>Interview ID</th>");
            out.println("<th>Candidate</th>");
            out.println("<th>Job</th>");
            out.println("<th>Date</th>");
            out.println("<th>Time</th>");
            out.println("<th>Mode</th>");
            out.println("<th>Interviewer</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            while (rs.next()) {

                out.println("<tr>");

                out.println("<td>" +
                        rs.getInt("interview_id") +
                        "</td>");

                out.println("<td>" +
                        rs.getString("candidate_name") +
                        "</td>");

                out.println("<td>" +
                        rs.getString("job_title") +
                        "</td>");

                out.println("<td>" +
                        rs.getDate("interview_date") +
                        "</td>");

                out.println("<td>" +
                        rs.getTime("interview_time") +
                        "</td>");

                out.println("<td>" +
                        rs.getString("interview_mode") +
                        "</td>");

                out.println("<td>" +
                        rs.getString("interviewer") +
                        "</td>");

                out.println("<td>" +
                        rs.getString("status") +
                        "</td>");

                out.println("</tr>");
            }

            out.println("</table>");

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
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

@WebServlet("/ApplicationServlet")
public class ApplicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        int candidateId =
                Integer.parseInt(request.getParameter("candidate_id"));

        int jobId =
                Integer.parseInt(request.getParameter("job_id"));

        String sql = "INSERT INTO application "
                   + "(candidate_id, job_id, status) "
                   + "VALUES (?, ?, 'Applied')";

        response.setContentType("text/html");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, candidateId);
            ps.setInt(2, jobId);

            ps.executeUpdate();

            PrintWriter out = response.getWriter();

            out.println("<h1>Application Submitted Successfully!</h1>");
            out.println("<p>Candidate ID: " + candidateId + "</p>");
            out.println("<p>Job ID: " + jobId + "</p>");
            out.println("<p>Status: Applied</p>");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter()
                    .println("Database error: " + e.getMessage());
        }
    }
}
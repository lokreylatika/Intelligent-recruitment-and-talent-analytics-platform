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

@WebServlet("/CandidateServlet")
public class CandidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String skills = request.getParameter("skills");

        String sql = "INSERT INTO candidate "
                   + "(name, email, phone, skills) "
                   + "VALUES (?, ?, ?, ?)";

        response.setContentType("text/html;charset=UTF-8");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, skills);

            ps.executeUpdate();

            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println("<meta name='viewport' " +
                    "content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Candidate Registered</title>");

            out.println("<link rel='stylesheet' href='style.css?v=2'>");

            out.println("</head>");

            out.println("<body>");

            /* =========================
               NAVIGATION BAR
               ========================= */

            out.println("<nav class='navbar'>");

            out.println("<div class='logo'>");
            out.println("Recruitment Platform");
            out.println("</div>");

            out.println("<div class='nav-links'>");

            out.println("<a href='index.html'>Home</a>");

            out.println("<a href='ViewJobServlet'>Jobs</a>");

            out.println("<a href='candidate.html'>Candidate</a>");

            out.println("<a href='interview.html'>Interview</a>");

            out.println("<a href='AnalyticsServlet'>Analytics</a>");

            out.println("</div>");

            out.println("</nav>");

            /* =========================
               SUCCESS CONTENT
               ========================= */

            out.println("<div class='success-container'>");

            out.println("<h1>");
            out.println("Candidate Registered Successfully!");
            out.println("</h1>");

            out.println("<p><strong>Name:</strong> "
                    + name + "</p>");

            out.println("<p><strong>Email:</strong> "
                    + email + "</p>");

            out.println("<p><strong>Phone:</strong> "
                    + phone + "</p>");

            out.println("<p><strong>Skills:</strong> "
                    + skills + "</p>");

            out.println("<div class='success-buttons'>");

            out.println("<a href='candidate.html' class='hero-button'>");
            out.println("Register Another Candidate");
            out.println("</a>");

            out.println("<a href='index.html' class='hero-button'>");
            out.println("Back to Home");
            out.println("</a>");

            out.println("</div>");

            out.println("</div>");

            /* =========================
               FOOTER
               ========================= */

            out.println("<footer>");
            out.println("Recruitment Platform");
            out.println("</footer>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html;charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");
            out.println("<link rel='stylesheet' href='style.css?v=2'>");
            out.println("</head>");

            out.println("<body>");

            out.println("<nav class='navbar'>");

            out.println("<div class='logo'>");
            out.println("Recruitment Platform");
            out.println("</div>");

            out.println("<div class='nav-links'>");
            out.println("<a href='index.html'>Home</a>");
            out.println("<a href='ViewJobServlet'>Jobs</a>");
            out.println("<a href='candidate_register.html'>Candidate</a>");
            out.println("<a href='schedule_interview.html'>Interview</a>");
            out.println("<a href='AnalyticsServlet'>Analytics</a>");
            out.println("</div>");

            out.println("</nav>");

            out.println("<div class='error-container'>");

            out.println("<h1>Registration Failed</h1>");

            out.println("<p>");
            out.println("There was a problem registering the candidate.");
            out.println("</p>");

            out.println("<a href='candidate.html' class='hero-button'>");
            out.println("Try Again");
            out.println("</a>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}
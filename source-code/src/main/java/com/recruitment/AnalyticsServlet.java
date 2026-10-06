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

@WebServlet("/AnalyticsServlet")
public class AnalyticsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        int totalInterviews = 0;
        int scheduledInterviews = 0;
        int completedInterviews = 0;
        int cancelledInterviews = 0;

        String totalSql =
                "SELECT COUNT(*) FROM interview";

        String scheduledSql =
                "SELECT COUNT(*) FROM interview WHERE status = 'Scheduled'";

        String completedSql =
                "SELECT COUNT(*) FROM interview WHERE status = 'Completed'";

        String cancelledSql =
                "SELECT COUNT(*) FROM interview WHERE status = 'Cancelled'";

        try (Connection con = DBConnection.getConnection()) {

            /* =========================
               TOTAL INTERVIEWS
               ========================= */

            try (PreparedStatement ps =
                         con.prepareStatement(totalSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    totalInterviews = rs.getInt(1);
                }
            }

            /* =========================
               SCHEDULED INTERVIEWS
               ========================= */

            try (PreparedStatement ps =
                         con.prepareStatement(scheduledSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    scheduledInterviews = rs.getInt(1);
                }
            }

            /* =========================
               COMPLETED INTERVIEWS
               ========================= */

            try (PreparedStatement ps =
                         con.prepareStatement(completedSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    completedInterviews = rs.getInt(1);
                }
            }

            /* =========================
               CANCELLED INTERVIEWS
               ========================= */

            try (PreparedStatement ps =
                         con.prepareStatement(cancelledSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    cancelledInterviews = rs.getInt(1);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        /* =========================
           HTML PAGE
           ========================= */

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' " +
                "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Interview Analytics</title>");

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

        out.println("<a href='" + request.getContextPath() + "/index.html'>Home</a>");

        out.println("<a href='" + request.getContextPath() + "/ViewJobServlet'>Jobs</a>");

        out.println("<a href='" + request.getContextPath() + "/candidate_register.html'>Candidate</a>");

        out.println("<a href='" + request.getContextPath() + "/schedule_interview.html'>Interview</a>");

        out.println("<a href='" + request.getContextPath() + "/AnalyticsServlet'>Analytics</a>");

        out.println("</div>");

        out.println("</nav>");

        /* =========================
           PAGE HEADING
           ========================= */

        out.println("<h1>Interview Analytics Dashboard</h1>");

        out.println("<p class='analytics-intro'>");
        out.println("Overview of interview activities and current status");
        out.println("</p>");

        /* =========================
           ANALYTICS CARDS
           ========================= */

        out.println("<div class='analytics-container'>");

        /* TOTAL */

        out.println("<div class='analytics-card total'>");

        out.println("<div class='icon'>📊</div>");

        out.println("<h2>Total Interviews</h2>");

        out.println("<p>" + totalInterviews + "</p>");

        out.println("<span>All interviews</span>");

        out.println("</div>");

        /* SCHEDULED */

        out.println("<div class='analytics-card scheduled'>");

        out.println("<div class='icon'>📅</div>");

        out.println("<h2>Scheduled</h2>");

        out.println("<p>" + scheduledInterviews + "</p>");

        out.println("<span>Upcoming interviews</span>");

        out.println("</div>");

        /* COMPLETED */

        out.println("<div class='analytics-card completed'>");

        out.println("<div class='icon'>✓</div>");

        out.println("<h2>Completed</h2>");

        out.println("<p>" + completedInterviews + "</p>");

        out.println("<span>Completed interviews</span>");

        out.println("</div>");

        /* CANCELLED */

        out.println("<div class='analytics-card cancelled'>");

        out.println("<div class='icon'>✕</div>");

        out.println("<h2>Cancelled</h2>");

        out.println("<p>" + cancelledInterviews + "</p>");

        out.println("<span>Cancelled interviews</span>");

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
    }
}
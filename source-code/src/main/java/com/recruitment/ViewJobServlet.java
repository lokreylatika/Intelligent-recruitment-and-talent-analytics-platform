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

@WebServlet("/ViewJobServlet")
public class ViewJobServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String sql = "SELECT job_id, title, description, required_skills, experience "
                   + "FROM job ORDER BY job_id";

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Available Jobs</title>");

        out.println("<link rel='stylesheet' href='"
                + request.getContextPath()
                + "/style.css'>");

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

        out.println("<a href='"
                + request.getContextPath()
                + "/index.html'>Home</a>");

        out.println("<a href='"
                + request.getContextPath()
                + "/ViewJobServlet'>Jobs</a>");

        out.println("<a href='"
                + request.getContextPath()
                + "/candidate_register.html'>Candidate</a>");

        out.println("<a href='"
                + request.getContextPath()
                + "/schedule_interview.html'>Interview</a>");

        out.println("<a href='"
                + request.getContextPath()
                + "/AnalyticsServlet'>Analytics</a>");

        out.println("</div>");

        out.println("</nav>");

        /* =========================
           PAGE HEADING
           ========================= */

        out.println("<h1>Available Jobs</h1>");

        out.println("<div class='jobs-container'>");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasJobs = false;

            while (rs.next()) {

                hasJobs = true;

                /* =========================
                   JOB CARD
                   ========================= */

                out.println("<div class='job-card'>");

                out.println("<h2>"
                        + rs.getString("title")
                        + "</h2>");

                out.println("<p><strong>Job ID:</strong> "
                        + rs.getInt("job_id")
                        + "</p>");

                out.println("<p><strong>Description:</strong> "
                        + rs.getString("description")
                        + "</p>");

                out.println("<p><strong>Required Skills:</strong> "
                        + rs.getString("required_skills")
                        + "</p>");

                out.println("<p><strong>Experience:</strong> "
                        + rs.getInt("experience")
                        + " years</p>");

                out.println("<a href='"
                        + request.getContextPath()
                        + "/apply_job.html' "
                        + "class='hero-button'>");

                out.println("Apply for this Job");

                out.println("</a>");

                out.println("</div>");
            }

            /* =========================
               NO JOBS
               ========================= */

            if (!hasJobs) {

                out.println("<div class='container'>");

                out.println("<h2>No Jobs Available</h2>");

                out.println("<p>");
                out.println("There are currently no job opportunities available.");
                out.println("</p>");

                out.println("</div>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            /* =========================
               ERROR
               ========================= */

            out.println("<div class='error-container'>");

            out.println("<h1>Unable to Load Jobs</h1>");

            out.println("<p>");
            out.println("There was a problem loading the available jobs.");
            out.println("</p>");

            out.println("<a href='"
                    + request.getContextPath()
                    + "/index.html' "
                    + "class='hero-button'>");

            out.println("Back to Home");

            out.println("</a>");

            out.println("</div>");
        }

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
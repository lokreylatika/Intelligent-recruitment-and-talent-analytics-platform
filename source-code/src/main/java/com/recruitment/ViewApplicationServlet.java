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

@WebServlet("/ViewApplicationServlet")
public class ViewApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String sql = """
            SELECT
                a.application_id,
                c.name AS candidate_name,
                j.title AS job_title,
                a.status
            FROM application a
            JOIN candidate c
                ON a.candidate_id = c.candidate_id
            JOIN job j
                ON a.job_id = j.job_id
            ORDER BY a.application_id
            """;

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Job Applications</title>");

        /* =========================
           INTERNAL CSS
           ========================= */

        out.println("<style>");

        out.println("* {");
        out.println("    box-sizing: border-box;");
        out.println("    margin: 0;");
        out.println("    padding: 0;");
        out.println("}");

        out.println("body {");
        out.println("    font-family: Arial, Helvetica, sans-serif;");
        out.println("    background: #f4f7fb;");
        out.println("    color: #1f2937;");
        out.println("    min-height: 100vh;");
        out.println("}");

        /* =========================
           NAVBAR
           ========================= */

        out.println(".navbar {");
        out.println("    width: 100%;");
        out.println("    background: #1f2937;");
        out.println("    padding: 18px 50px;");
        out.println("    display: flex;");
        out.println("    align-items: center;");
        out.println("    justify-content: space-between;");
        out.println("    box-shadow: 0 3px 10px rgba(0,0,0,0.15);");
        out.println("}");

        out.println(".logo {");
        out.println("    color: white;");
        out.println("    font-size: 24px;");
        out.println("    font-weight: bold;");
        out.println("}");

        out.println(".nav-links {");
        out.println("    display: flex;");
        out.println("    align-items: center;");
        out.println("    gap: 25px;");
        out.println("}");

        out.println(".nav-links a {");
        out.println("    color: white;");
        out.println("    text-decoration: none;");
        out.println("    font-size: 16px;");
        out.println("    font-weight: 500;");
        out.println("    transition: 0.3s;");
        out.println("}");

        out.println(".nav-links a:hover {");
        out.println("    color: #60a5fa;");
        out.println("}");

        /* =========================
           APPLICATION CONTAINER
           ========================= */

        out.println(".applications-container {");
        out.println("    width: 92%;");
        out.println("    max-width: 1100px;");
        out.println("    margin: 50px auto;");
        out.println("    background: white;");
        out.println("    padding: 35px;");
        out.println("    border-radius: 15px;");
        out.println("    box-shadow: 0 8px 30px rgba(0,0,0,0.10);");
        out.println("}");

        out.println(".applications-container h1 {");
        out.println("    text-align: center;");
        out.println("    margin-bottom: 30px;");
        out.println("    font-size: 34px;");
        out.println("    color: #111827;");
        out.println("}");

        /* =========================
           TABLE
           ========================= */

        out.println(".table-wrapper {");
        out.println("    width: 100%;");
        out.println("    overflow-x: auto;");
        out.println("}");

        out.println(".applications-table {");
        out.println("    width: 100%;");
        out.println("    border-collapse: collapse;");
        out.println("}");

        out.println(".applications-table th {");
        out.println("    background: #1f2937;");
        out.println("    color: white;");
        out.println("    padding: 16px;");
        out.println("    text-align: left;");
        out.println("    font-size: 15px;");
        out.println("}");

        out.println(".applications-table td {");
        out.println("    padding: 16px;");
        out.println("    border-bottom: 1px solid #e5e7eb;");
        out.println("    color: #374151;");
        out.println("    font-size: 15px;");
        out.println("}");

        out.println(".applications-table tbody tr:hover {");
        out.println("    background: #f9fafb;");
        out.println("}");

        /* =========================
           STATUS BADGES
           ========================= */

        out.println(".status-badge {");
        out.println("    display: inline-block;");
        out.println("    padding: 6px 12px;");
        out.println("    border-radius: 20px;");
        out.println("    font-size: 13px;");
        out.println("    font-weight: bold;");
        out.println("}");

        out.println(".status-badge.applied {");
        out.println("    background: #dbeafe;");
        out.println("    color: #1d4ed8;");
        out.println("}");

        out.println(".status-badge.scheduled {");
        out.println("    background: #fef3c7;");
        out.println("    color: #92400e;");
        out.println("}");

        out.println(".status-badge.completed {");
        out.println("    background: #dcfce7;");
        out.println("    color: #166534;");
        out.println("}");

        out.println(".status-badge.cancelled {");
        out.println("    background: #fee2e2;");
        out.println("    color: #991b1b;");
        out.println("}");

        /* =========================
           NO DATA
           ========================= */

        out.println(".no-data {");
        out.println("    text-align: center;");
        out.println("    padding: 30px;");
        out.println("    color: #6b7280;");
        out.println("}");

        out.println(".error-text {");
        out.println("    color: #dc2626;");
        out.println("}");

        /* =========================
           FOOTER
           ========================= */

        out.println("footer {");
        out.println("    width: 100%;");
        out.println("    margin-top: 60px;");
        out.println("    padding: 25px;");
        out.println("    text-align: center;");
        out.println("    background: #1f2937;");
        out.println("    color: white;");
        out.println("}");

        /* =========================
           MOBILE
           ========================= */

        out.println("@media (max-width: 768px) {");

        out.println("    .navbar {");
        out.println("        flex-direction: column;");
        out.println("        gap: 15px;");
        out.println("        padding: 20px;");
        out.println("    }");

        out.println("    .nav-links {");
        out.println("        flex-wrap: wrap;");
        out.println("        justify-content: center;");
        out.println("        gap: 15px;");
        out.println("    }");

        out.println("    .applications-container {");
        out.println("        width: 94%;");
        out.println("        padding: 20px;");
        out.println("        margin: 30px auto;");
        out.println("    }");

        out.println("    .applications-container h1 {");
        out.println("        font-size: 28px;");
        out.println("    }");

        out.println("    .applications-table th,");
        out.println("    .applications-table td {");
        out.println("        padding: 12px;");
        out.println("        font-size: 14px;");
        out.println("    }");

        out.println("}");

        out.println("</style>");

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
           APPLICATIONS
           ========================= */

        out.println("<div class='applications-container'>");

        out.println("<h1>Job Applications</h1>");

        out.println("<div class='table-wrapper'>");

        out.println("<table class='applications-table'>");

        out.println("<thead>");

        out.println("<tr>");

        out.println("<th>Application ID</th>");
        out.println("<th>Candidate Name</th>");
        out.println("<th>Job Title</th>");
        out.println("<th>Status</th>");

        out.println("</tr>");

        out.println("</thead>");

        out.println("<tbody>");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasApplications = false;

            while (rs.next()) {

                hasApplications = true;

                out.println("<tr>");

                out.println("<td>"
                        + rs.getInt("application_id")
                        + "</td>");

                out.println("<td>"
                        + rs.getString("candidate_name")
                        + "</td>");

                out.println("<td>"
                        + rs.getString("job_title")
                        + "</td>");

                out.println("<td>");

                String status = rs.getString("status");

                if ("Applied".equalsIgnoreCase(status)) {

                    out.println("<span class='status-badge applied'>");
                    out.println(status);
                    out.println("</span>");

                } else if ("Scheduled".equalsIgnoreCase(status)) {

                    out.println("<span class='status-badge scheduled'>");
                    out.println(status);
                    out.println("</span>");

                } else if ("Completed".equalsIgnoreCase(status)) {

                    out.println("<span class='status-badge completed'>");
                    out.println(status);
                    out.println("</span>");

                } else if ("Cancelled".equalsIgnoreCase(status)) {

                    out.println("<span class='status-badge cancelled'>");
                    out.println(status);
                    out.println("</span>");

                } else {

                    out.println("<span class='status-badge'>");
                    out.println(status);
                    out.println("</span>");
                }

                out.println("</td>");

                out.println("</tr>");
            }

            if (!hasApplications) {

                out.println("<tr>");

                out.println("<td colspan='4' class='no-data'>");
                out.println("No applications available.");
                out.println("</td>");

                out.println("</tr>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<tr>");

            out.println("<td colspan='4' class='no-data error-text'>");
            out.println("Unable to load applications.");
            out.println("</td>");

            out.println("</tr>");
        }

        out.println("</tbody>");

        out.println("</table>");

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
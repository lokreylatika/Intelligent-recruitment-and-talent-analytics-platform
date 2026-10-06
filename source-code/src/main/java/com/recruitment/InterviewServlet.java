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
import java.sql.Date;
import java.sql.Time;

@WebServlet("/InterviewServlet")
public class InterviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String applicationIdString = request.getParameter("application_id");
        String dateString = request.getParameter("date");
        String timeString = request.getParameter("time");
        String mode = request.getParameter("mode");
        String interviewer = request.getParameter("interviewer");

        // Fallback parameter names
        if (dateString == null || dateString.isEmpty()) {
            dateString = request.getParameter("interview_date");
        }

        if (timeString == null || timeString.isEmpty()) {
            timeString = request.getParameter("interview_time");
        }

        if (mode == null || mode.isEmpty()) {
            mode = request.getParameter("interview_mode");
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {

            /* =========================
               VALIDATION
               ========================= */

            if (applicationIdString == null || applicationIdString.isEmpty()
                    || dateString == null || dateString.isEmpty()
                    || timeString == null || timeString.isEmpty()
                    || mode == null || mode.isEmpty()
                    || interviewer == null || interviewer.isEmpty()) {

                throw new Exception("Please fill all interview details.");
            }

            /* =========================
               CONVERT VALUES
               ========================= */

            int applicationId = Integer.parseInt(applicationIdString);

            Date interviewDate = Date.valueOf(dateString);

            Time interviewTime = Time.valueOf(timeString + ":00");

            String status = "Scheduled";

            /* =========================
               DATABASE INSERT
               ========================= */

            String sql =
                    "INSERT INTO interview "
                    + "(application_id, interview_date, interview_time, "
                    + "interview_mode, interviewer, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, applicationId);
                ps.setDate(2, interviewDate);
                ps.setTime(3, interviewTime);
                ps.setString(4, mode);
                ps.setString(5, interviewer);
                ps.setString(6, status);

                ps.executeUpdate();
            }

            /* =====================================================
               SUCCESS PAGE
               ===================================================== */

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");

            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println("<meta name='viewport' "
                    + "content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Interview Scheduled</title>");

            /*
             * Internal CSS is used here intentionally so this
             * servlet-generated page does not depend on style.css.
             */

            out.println("<style>");

            out.println("* {");
            out.println("    box-sizing: border-box;");
            out.println("}");

            out.println("body {");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("    font-family: Arial, Helvetica, sans-serif;");
            out.println("    background: #f4f7fb;");
            out.println("    color: #1f2937;");
            out.println("    min-height: 100vh;");
            out.println("}");

            /* NAVBAR */

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

            /* SUCCESS CONTAINER */

            out.println(".success-container {");
            out.println("    width: 90%;");
            out.println("    max-width: 750px;");
            out.println("    margin: 60px auto;");
            out.println("    background: white;");
            out.println("    padding: 40px;");
            out.println("    border-radius: 15px;");
            out.println("    text-align: center;");
            out.println("    box-shadow: 0 8px 30px rgba(0,0,0,0.10);");
            out.println("}");

            out.println(".success-container h1 {");
            out.println("    color: #16a34a;");
            out.println("    font-size: 34px;");
            out.println("    margin: 0 0 35px;");
            out.println("}");

            /* SUCCESS DETAILS */

            out.println(".success-details {");
            out.println("    width: 100%;");
            out.println("    background: #f9fafb;");
            out.println("    padding: 25px 30px;");
            out.println("    border-radius: 10px;");
            out.println("    border-left: 5px solid #16a34a;");
            out.println("    text-align: left;");
            out.println("    margin-bottom: 30px;");
            out.println("}");

            out.println(".success-details p {");
            out.println("    font-size: 17px;");
            out.println("    color: #374151;");
            out.println("    margin: 16px 0;");
            out.println("    padding-bottom: 12px;");
            out.println("    border-bottom: 1px solid #e5e7eb;");
            out.println("}");

            out.println(".success-details p:first-child {");
            out.println("    margin-top: 0;");
            out.println("}");

            out.println(".success-details p:last-child {");
            out.println("    margin-bottom: 0;");
            out.println("    padding-bottom: 0;");
            out.println("    border-bottom: none;");
            out.println("}");

            out.println(".success-details strong {");
            out.println("    color: #111827;");
            out.println("}");

            /* BUTTON AREA */

            out.println(".success-buttons {");
            out.println("    display: flex;");
            out.println("    justify-content: center;");
            out.println("    align-items: center;");
            out.println("    gap: 15px;");
            out.println("    flex-wrap: wrap;");
            out.println("    margin-top: 25px;");
            out.println("}");

            out.println(".hero-button {");
            out.println("    display: inline-block;");
            out.println("    padding: 13px 25px;");
            out.println("    border-radius: 8px;");
            out.println("    text-decoration: none;");
            out.println("    font-size: 15px;");
            out.println("    font-weight: bold;");
            out.println("    transition: 0.3s;");
            out.println("}");

            out.println(".hero-button:hover {");
            out.println("    transform: translateY(-2px);");
            out.println("}");

            out.println(".hero-button.primary {");
            out.println("    background: #2563eb;");
            out.println("    color: white;");
            out.println("}");

            out.println(".hero-button.primary:hover {");
            out.println("    background: #1d4ed8;");
            out.println("}");

            out.println(".hero-button.secondary {");
            out.println("    background: #374151;");
            out.println("    color: white;");
            out.println("}");

            out.println(".hero-button.secondary:hover {");
            out.println("    background: #111827;");
            out.println("}");

            /* MOBILE */

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

            out.println("    .success-container {");
            out.println("        width: 92%;");
            out.println("        padding: 30px 20px;");
            out.println("        margin-top: 40px;");
            out.println("    }");

            out.println("    .success-container h1 {");
            out.println("        font-size: 28px;");
            out.println("    }");

            out.println("    .success-details {");
            out.println("        padding: 20px;");
            out.println("    }");

            out.println("    .success-details p {");
            out.println("        font-size: 16px;");
            out.println("    }");

            out.println("}");

            out.println("@media (max-width: 500px) {");

            out.println("    .logo {");
            out.println("        font-size: 21px;");
            out.println("    }");

            out.println("    .nav-links {");
            out.println("        gap: 10px;");
            out.println("    }");

            out.println("    .nav-links a {");
            out.println("        font-size: 14px;");
            out.println("    }");

            out.println("    .success-container h1 {");
            out.println("        font-size: 24px;");
            out.println("    }");

            out.println("    .success-buttons {");
            out.println("        flex-direction: column;");
            out.println("    }");

            out.println("    .hero-button {");
            out.println("        width: 100%;");
            out.println("        text-align: center;");
            out.println("    }");

            out.println("}");

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");

            /* =====================================================
               NAVIGATION
               ===================================================== */

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

            /* =====================================================
               SUCCESS CONTENT
               ===================================================== */

            out.println("<div class='success-container'>");

            out.println("<h1>");
            out.println("Interview Scheduled Successfully!");
            out.println("</h1>");

            out.println("<div class='success-details'>");

            out.println("<p><strong>Application ID:</strong> "
                    + applicationId + "</p>");

            out.println("<p><strong>Date:</strong> "
                    + dateString + "</p>");

            out.println("<p><strong>Time:</strong> "
                    + timeString + "</p>");

            out.println("<p><strong>Mode:</strong> "
                    + mode + "</p>");

            out.println("<p><strong>Interviewer:</strong> "
                    + interviewer + "</p>");

            out.println("<p><strong>Status:</strong> "
                    + status + "</p>");

            out.println("</div>");

            /* =====================================================
               BUTTONS
               ===================================================== */

            out.println("<div class='success-buttons'>");

            out.println("<a href='"
                    + request.getContextPath()
                    + "/schedule_interview.html' "
                    + "class='hero-button primary'>");

            out.println("Schedule Another Interview");

            out.println("</a>");

            out.println("<a href='"
                    + request.getContextPath()
                    + "/index.html' "
                    + "class='hero-button secondary'>");

            out.println("Back to Home");

            out.println("</a>");

            out.println("</div>");

            out.println("</div>");

            out.println("</body>");

            out.println("</html>");

        } catch (Exception e) {

            e.printStackTrace();

            /* =====================================================
               ERROR PAGE
               ===================================================== */

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");

            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println("<meta name='viewport' "
                    + "content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Interview Scheduling Error</title>");

            out.println("<style>");

            out.println("* { box-sizing: border-box; }");

            out.println("body {");
            out.println("    margin: 0;");
            out.println("    font-family: Arial, Helvetica, sans-serif;");
            out.println("    background: #f4f7fb;");
            out.println("    color: #1f2937;");
            out.println("}");

            out.println(".navbar {");
            out.println("    width: 100%;");
            out.println("    background: #1f2937;");
            out.println("    padding: 18px 50px;");
            out.println("    display: flex;");
            out.println("    align-items: center;");
            out.println("    justify-content: space-between;");
            out.println("}");

            out.println(".logo {");
            out.println("    color: white;");
            out.println("    font-size: 24px;");
            out.println("    font-weight: bold;");
            out.println("}");

            out.println(".nav-links {");
            out.println("    display: flex;");
            out.println("    gap: 25px;");
            out.println("}");

            out.println(".nav-links a {");
            out.println("    color: white;");
            out.println("    text-decoration: none;");
            out.println("    font-size: 16px;");
            out.println("}");

            out.println(".nav-links a:hover {");
            out.println("    color: #60a5fa;");
            out.println("}");

            out.println(".error-container {");
            out.println("    width: 90%;");
            out.println("    max-width: 700px;");
            out.println("    margin: 60px auto;");
            out.println("    background: white;");
            out.println("    padding: 40px;");
            out.println("    border-radius: 15px;");
            out.println("    text-align: center;");
            out.println("    box-shadow: 0 8px 30px rgba(0,0,0,0.10);");
            out.println("}");

            out.println(".error-container h1 {");
            out.println("    color: #dc2626;");
            out.println("    font-size: 32px;");
            out.println("    margin-bottom: 20px;");
            out.println("}");

            out.println(".error-container p {");
            out.println("    color: #374151;");
            out.println("    font-size: 17px;");
            out.println("    margin-bottom: 25px;");
            out.println("}");

            out.println(".hero-button {");
            out.println("    display: inline-block;");
            out.println("    padding: 13px 25px;");
            out.println("    background: #2563eb;");
            out.println("    color: white;");
            out.println("    text-decoration: none;");
            out.println("    border-radius: 8px;");
            out.println("    font-weight: bold;");
            out.println("}");

            out.println(".hero-button:hover {");
            out.println("    background: #1d4ed8;");
            out.println("}");

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

            out.println("    .error-container {");
            out.println("        width: 92%;");
            out.println("        padding: 30px 20px;");
            out.println("    }");

            out.println("}");

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");

            /* ERROR NAVBAR */

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

            /* ERROR CONTENT */

            out.println("<div class='error-container'>");

            out.println("<h1>Interview Scheduling Failed</h1>");

            out.println("<p>");
            out.println("There was a problem scheduling the interview.");
            out.println("</p>");

            out.println("<a href='"
                    + request.getContextPath()
                    + "/schedule_interview.html' "
                    + "class='hero-button'>");

            out.println("Try Again");

            out.println("</a>");

            out.println("</div>");

            out.println("</body>");

            out.println("</html>");
        }
    }
}
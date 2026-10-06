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

@WebServlet("/JobServlet")
public class JobServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String requiredSkills = request.getParameter("required_skills");
        int experience = Integer.parseInt(request.getParameter("experience"));

        String sql = "INSERT INTO job (title, description, required_skills, experience) VALUES (?, ?, ?, ?)";

        response.setContentType("text/html");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, requiredSkills);
            ps.setInt(4, experience);

            ps.executeUpdate();

            PrintWriter out = response.getWriter();

            out.println("<h1>Job Added Successfully!</h1>");
            out.println("<p>Title: " + title + "</p>");
            out.println("<p>Description: " + description + "</p>");
            out.println("<p>Required Skills: " + requiredSkills + "</p>");
            out.println("<p>Experience: " + experience + " years</p>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
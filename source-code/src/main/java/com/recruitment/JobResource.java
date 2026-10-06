package com.recruitment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jobs")
public class JobResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJobs() {

        StringBuilder json = new StringBuilder("[");

        String sql = """
                SELECT job_id, title, description, required_skills, experience
                FROM job
                ORDER BY job_id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                json.append("{")
                    .append("\"job_id\":").append(rs.getInt("job_id")).append(",")
                    .append("\"title\":\"").append(escapeJson(rs.getString("title"))).append("\",")
                    .append("\"description\":\"").append(escapeJson(rs.getString("description"))).append("\",")
                    .append("\"required_skills\":\"").append(escapeJson(rs.getString("required_skills"))).append("\",")
                    .append("\"experience\":").append(rs.getInt("experience"))
                    .append("}");

                first = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Database error\"}";
        }

        json.append("]");
        return json.toString();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJob(@PathParam("id") int id) {

        String sql = """
                SELECT job_id, title, description, required_skills, experience
                FROM job
                WHERE job_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return "{"
                        + "\"job_id\":" + rs.getInt("job_id") + ","
                        + "\"title\":\"" + escapeJson(rs.getString("title")) + "\","
                        + "\"description\":\"" + escapeJson(rs.getString("description")) + "\","
                        + "\"required_skills\":\"" + escapeJson(rs.getString("required_skills")) + "\","
                        + "\"experience\":" + rs.getInt("experience")
                        + "}";
                }

                return "{\"error\":\"Job not found\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Database error\"}";
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJob(String json) {

        try {
            String title = extractValue(json, "title");
            String description = extractValue(json, "description");
            String requiredSkills = extractValue(json, "required_skills");
            int experience = Integer.parseInt(extractValue(json, "experience"));

            String sql = """
                    INSERT INTO job (title, description, required_skills, experience)
                    VALUES (?, ?, ?, ?)
                    RETURNING job_id
                    """;

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, title);
                statement.setString(2, description);
                statement.setString(3, requiredSkills);
                statement.setInt(4, experience);

                try (ResultSet rs = statement.executeQuery()) {

                    if (rs.next()) {

                        int newJobId = rs.getInt("job_id");

                        return Response.status(Response.Status.CREATED)
                                .entity("{\"message\":\"Job created successfully\",\"job_id\":"
                                        + newJobId + "}")
                                .build();
                    }
                }
            }

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Job could not be created\"}")
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid job data\"}")
                    .build();
        }
    }

    private String extractValue(String json, String key) {

        String search = "\"" + key + "\"";

        int keyPosition = json.indexOf(search);

        if (keyPosition == -1) {
            throw new IllegalArgumentException("Missing field: " + key);
        }

        int colonPosition = json.indexOf(":", keyPosition);
        int start = colonPosition + 1;

        while (start < json.length()
                && Character.isWhitespace(json.charAt(start))) {
            start++;
        }

        if (json.charAt(start) == '"') {

            start++;

            int end = json.indexOf("\"", start);

            if (end == -1) {
                throw new IllegalArgumentException("Invalid JSON");
            }

            return json.substring(start, end);
        }

        int end = start;

        while (end < json.length()
                && json.charAt(end) != ','
                && json.charAt(end) != '}') {
            end++;
        }

        return json.substring(start, end).trim();
    }

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
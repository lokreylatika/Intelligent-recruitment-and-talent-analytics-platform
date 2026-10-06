# Recruitment Management System

## Project Description

The Recruitment Management System is a web-based application developed to automate and simplify the recruitment process. The system enables recruiters to manage job postings, candidate registrations, job applications, interview scheduling, and recruitment analytics through a centralized platform.

This project reduces manual effort, improves data management, and provides real-time insights into recruitment activities.

---

## Features

### Job Management
- View available job openings
- Display job details including skills and experience requirements

### Candidate Registration
- Register candidates
- Store candidate information in the database
- Manage candidate records

### Job Application Management
- Apply for available jobs
- Track application status
- Store application details

### Interview Scheduling
- Schedule interviews
- Assign interview date and time
- Select interview mode (Online/Offline)
- Assign interviewer
- Track interview status

### Analytics Dashboard
- Total Applications
- Scheduled Interviews
- Completed Interviews
- Cancelled Interviews

---

## Technologies Used

### Frontend
- HTML5
- CSS3

### Backend
- Java Servlets
- JDBC

### Database
- PostgreSQL

### Server
- Apache Tomcat 10

### Development Environment
- Eclipse IDE

---

## Project Structure

```text
RecruitmentManagementSystem/
│
├── src/
│   └── com.recruitment/
│       ├── DBConnection.java
│       ├── CandidateServlet.java
│       ├── ApplicationServlet.java
│       ├── InterviewServlet.java
│       ├── ViewJobServlet.java
│       └── AnalyticsServlet.java
│
├── WebContent/
│   ├── index.html
│   ├── candidate_register.html
│   ├── apply_job.html
│   ├── schedule_interview.html
│   └── style.css
│
└── README.md
```

---

## Database Setup

### Create Database

```sql
CREATE DATABASE Recruitment_db;
```

### Create Required Tables

#### Candidate Table

```sql
CREATE TABLE candidate (
    candidate_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    skills TEXT,
    experience INT
);
```

#### Job Table

```sql
CREATE TABLE job (
    job_id SERIAL PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    required_skills TEXT,
    experience INT
);
```

#### Application Table

```sql
CREATE TABLE application (
    application_id SERIAL PRIMARY KEY,
    candidate_id INT,
    job_id INT,
    status VARCHAR(50)
);
```

#### Interview Table

```sql
CREATE TABLE interview (
    interview_id SERIAL PRIMARY KEY,
    application_id INT,
    interview_date DATE,
    interview_time TIME,
    interview_mode VARCHAR(50),
    interviewer VARCHAR(100),
    status VARCHAR(50)
);
```

---

## Configuration

Update the database credentials inside:

```java
DBConnection.java
```

```java
private static final String URL =
        "jdbc:postgresql://localhost:5432/Recruitment_db";

private static final String USER = "postgres";

private static final String PASSWORD = "YOUR_PASSWORD";
```

---

## How to Run the Project

### Step 1
Install:

- Java JDK 17 or later
- PostgreSQL
- Apache Tomcat 10
- Eclipse IDE

### Step 2
Clone the repository:

```bash
git clone https://github.com/lokreylatika/Intelligent-recruitment-and-talent-analytics-platform.git
```

### Step 3
Import the project into Eclipse.

### Step 4
Configure Apache Tomcat Server.

### Step 5
Add PostgreSQL JDBC Driver to the project.

### Step 6
Create the database and tables.

### Step 7
Run the project on Tomcat.

### Step 8
Open:

```text
http://localhost:8080/RecruitmentManagementSystem/
```

---

## Application Workflow

```text
Candidate Registration
          ↓
     View Jobs
          ↓
     Apply Job
          ↓
 Interview Scheduling
          ↓
 Analytics Dashboard
```

## Project Outcomes

- Automated recruitment process
- Centralized data management
- Improved interview tracking
- Real-time analytics reporting
- Practical implementation of Java Servlets and PostgreSQL integration

---

## Future Enhancements

- User Authentication
- Resume Upload
- Email Notifications
- Role-Based Access Control
- Advanced Analytics
- AI-Based Candidate Screening

---

## Student Details

**Team Number:** 13

**Team Members:** Oddesar Sudeeptha, Lokrey Latika, Samineni Sai Siri, Muvva Kovidha

**Department:** Artificial Intelligence and Data Science

**Guide:** Laiphangbam Melinda

---

## License

This project is developed for academic and educational purposes.


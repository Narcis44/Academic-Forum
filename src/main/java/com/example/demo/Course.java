package com.example.demo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String courseCode;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor;

    // STOP THE LOOP: Do not send list of students when asking for a Course
    @ManyToMany
    @JoinTable(
            name = "enrollments",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> enrolledStudents = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public User getProfessor() {
        return professor;
    }
    public void setProfessor(User professor) {
        this.professor = professor;
    }

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
    }
    public void enrollStudent(User student) {
        this.enrolledStudents.add(student);
    }
}
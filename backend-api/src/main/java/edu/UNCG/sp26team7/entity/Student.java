package edu.UNCG.sp26team7.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "student_id")
public class Student extends User {

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "StudentSchedule_id")
    private StudentSchedule studentSchedules;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("student")
    private List<Review> reviews;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentSchedule getStudentSchedules() {
        return studentSchedules;
    }

    public void setStudentSchedules(StudentSchedule studentSchedules) {
        this.studentSchedules = studentSchedules;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
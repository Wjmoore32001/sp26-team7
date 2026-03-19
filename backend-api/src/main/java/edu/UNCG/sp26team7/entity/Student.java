package edu.UNCG.sp26team7.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Student extends User {

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("student")
  private List<StudentSchedule> studentSchedules;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("student")
  private List<Review> reviews;

  public Student() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<StudentSchedule> getStudentSchedules() {
    return studentSchedules;
  }

  public void setStudentSchedules(List<StudentSchedule> studentSchedules) {
    this.studentSchedules = studentSchedules;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

}
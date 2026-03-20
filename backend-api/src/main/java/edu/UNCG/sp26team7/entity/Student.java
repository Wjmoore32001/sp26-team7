package edu.UNCG.sp26team7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "student_id")
public class Student extends User {

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("student")
  private List<StudentSchedule> studentSchedules;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("student")
  private List<Review> reviews;

}

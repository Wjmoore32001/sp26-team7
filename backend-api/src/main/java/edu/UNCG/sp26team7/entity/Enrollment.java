package edu.UNCG.sp26team7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enrollments", uniqueConstraints = @UniqueConstraint(columnNames = { "classSessionId", "studentId" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long enrollmentId;

  @Column(nullable = false)
  private Long classSessionId;

  @Column(nullable = false)
  private Long studentId;
}

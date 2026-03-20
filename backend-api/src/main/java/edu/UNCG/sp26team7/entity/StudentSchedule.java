package edu.UNCG.sp26team7.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_schedule", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "student_id", "class_session_id" }) })
public class StudentSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long studentScheduleId;

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  @JsonIgnoreProperties("studentSchedules")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "class_session_id", nullable = false)
  @JsonIgnoreProperties("studentSchedules")
  private ClassSession classSession;

  @Column(nullable = false)
  private LocalDateTime enrolledAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BookingStatus bookingStatus;

  public StudentSchedule() {
  }

  public Long getStudentScheduleId() {
    return studentScheduleId;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public ClassSession getClassSession() {
    return classSession;
  }

  public void setClassSession(ClassSession classSession) {
    this.classSession = classSession;
  }

  public LocalDateTime getEnrolledAt() {
    return enrolledAt;
  }

  public void setEnrolledAt(LocalDateTime enrolledAt) {
    this.enrolledAt = enrolledAt;
  }

  public BookingStatus getBookingStatus() {
    return bookingStatus;
  }

  public void setBookingStatus(BookingStatus bookingStatus) {
    this.bookingStatus = bookingStatus;
  }

}

enum BookingStatus {
  ENROLLED,
  CANCELLED,
  WAITLISTED
}

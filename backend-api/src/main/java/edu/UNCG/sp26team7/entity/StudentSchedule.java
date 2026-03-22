package edu.UNCG.sp26team7.entity;

import jakarta.persistence.*;
import edu.UNCG.sp26team7.entity.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}

package edu.UNCG.sp26team7.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long classSessionId;

  @Column(nullable = false)
  private LocalDateTime scheduledAt;

  @ManyToOne
  @JoinColumn(name = "template_id", nullable = false)
  private ClassTemplate classTemplate;
}


package edu.UNCG.sp26team7.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import edu.UNCG.sp26team7.entity.enums.ClassTemplateType;
import edu.UNCG.sp26team7.entity.enums.IntensityLevels;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ClassTemplates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long template_id;

  @ManyToOne
  @JoinColumn(name = "instructor_id")
  private Instructor instructor;

  @Column
  private String title;

  @Column
  @Enumerated(EnumType.STRING)
  private ClassTemplateType classType;

  @Column
  @Enumerated(EnumType.STRING)
  private IntensityLevels intensity;

  @Column
  private int duration;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false, precision = 6, scale = 2)
  private BigDecimal price;

  @Column
  private String description;

  @PrePersist
  private void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  private void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}

package edu.UNCG.sp26team7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  @JsonIgnoreProperties("reviews")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "class_template_id", nullable = false)
  @JsonIgnoreProperties("reviews")
  private ClassTemplate classTemplate;

  @Column(nullable = false)
  private Integer rating;

  @Column(columnDefinition = "TEXT")
  private String comment;

  @Column(columnDefinition = "TEXT")
  private String replyText;

}

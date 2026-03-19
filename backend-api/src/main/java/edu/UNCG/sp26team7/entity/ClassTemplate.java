
package edu.UNCG.sp26team7.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}

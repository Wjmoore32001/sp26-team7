package edu.UNCG.sp26team7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instructors")
@PrimaryKeyJoinColumn(name = "instructor_id")
public class Instructor extends User {

  @Column(nullable = false)
  private String name;

}

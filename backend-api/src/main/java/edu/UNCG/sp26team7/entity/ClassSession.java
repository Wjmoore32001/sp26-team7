package edu.UNCG.sp26team7.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ClassSessions")
public class ClassSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long session_id;
}

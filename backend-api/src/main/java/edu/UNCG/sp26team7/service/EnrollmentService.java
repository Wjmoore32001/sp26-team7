package edu.UNCG.sp26team7.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.Enrollment;
import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.repository.ClassSessionRepository;
import edu.UNCG.sp26team7.repository.EnrollmentRepository;
import edu.UNCG.sp26team7.repository.StudentRepository;

@Service
public class EnrollmentService {

  private final EnrollmentRepository enrollmentRepository;
  private final ClassSessionRepository classSessionRepository;
  private final StudentRepository studentRepository;

  public EnrollmentService(
      EnrollmentRepository enrollmentRepository,
      ClassSessionRepository classSessionRepository,
      StudentRepository studentRepository) {
    this.enrollmentRepository = enrollmentRepository;
    this.classSessionRepository = classSessionRepository;
    this.studentRepository = studentRepository;
  }

  public List<Enrollment> getAllEnrollments() {
    return enrollmentRepository.findAll();
  }

  public List<Enrollment> getEnrollmentsByClassSessionId(Long classSessionId) {
    return enrollmentRepository.findByClassSessionClassSessionId(classSessionId);
  }

  public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
    return enrollmentRepository.findByStudentUserId(studentId);
  }

  public Enrollment createEnrollment(Enrollment enrollment) {
    if (enrollment.getStudent() == null || enrollment.getStudent().getUserId() == null) {
      throw new RuntimeException("Student id is required");
    }

    if (enrollment.getClassSession() == null || enrollment.getClassSession().getClassSessionId() == null) {
      throw new RuntimeException("Class session id is required");
    }

    Student realStudent = studentRepository
        .findById(enrollment.getStudent().getUserId())
        .orElseThrow(() -> new RuntimeException("Student not found"));

    ClassSession realClassSession = classSessionRepository
        .findById(enrollment.getClassSession().getClassSessionId())
        .orElseThrow(() -> new RuntimeException("Class session not found"));

    enrollment.setStudent(realStudent);
    enrollment.setClassSession(realClassSession);

    return enrollmentRepository.save(enrollment);
  }

  public Optional<Enrollment> getEnrollmentById(Long id) {
    return enrollmentRepository.findById(id);
  }

  public List<Enrollment> getAllStudentSchedules() {
    return enrollmentRepository.findAll();
  }

  public void deleteEnrollment(Long enrollment) {
    enrollmentRepository.deleteById(enrollment);
  }
}

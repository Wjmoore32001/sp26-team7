package edu.UNCG.sp26team7.service;

import java.util.List;

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
    return enrollmentRepository.findByClassSessionId(classSessionId);
  }

  public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
    return enrollmentRepository.findByStudentId(studentId);
  }

  public Enrollment createEnrollment(Enrollment enrollment) {
    if (enrollment.getClassSessionId() == null || enrollment.getStudentId() == null) {
      return null;
    }

    ClassSession classSession = classSessionRepository.findById(enrollment.getClassSessionId()).orElse(null);

    if (classSession == null) {
      return null;
    }

    Student student = studentRepository.findById(enrollment.getStudentId()).orElse(null);

    if (student == null) {
      return null;
    }

    if (enrollmentRepository.existsByClassSessionIdAndStudentId(
        enrollment.getClassSessionId(),
        enrollment.getStudentId())) {
      return null;
    }

    return enrollmentRepository.save(enrollment);
  }

  public boolean deleteEnrollment(Long classSessionId, Long studentId) {
    Enrollment enrollment = enrollmentRepository
        .findByClassSessionIdAndStudentId(classSessionId, studentId)
        .orElse(null);

    if (enrollment == null) {
      return false;
    }

    enrollmentRepository.delete(enrollment);
    return true;
  }
}

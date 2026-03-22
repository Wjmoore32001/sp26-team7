package edu.UNCG.sp26team7.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.UNCG.sp26team7.entity.Enrollment;
import edu.UNCG.sp26team7.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

  private final EnrollmentRepository enrollmentRepository;

  public EnrollmentService(EnrollmentRepository enrollmentRepository) {
    this.enrollmentRepository = enrollmentRepository;
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

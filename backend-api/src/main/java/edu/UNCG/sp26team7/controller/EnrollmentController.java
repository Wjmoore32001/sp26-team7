package edu.UNCG.sp26team7.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.UNCG.sp26team7.entity.Enrollment;
import edu.UNCG.sp26team7.service.EnrollmentService;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

  private final EnrollmentService enrollmentService;

  public EnrollmentController(EnrollmentService enrollmentService) {
    this.enrollmentService = enrollmentService;
  }

  @GetMapping
  public List<Enrollment> getAllEnrollments() {
    return enrollmentService.getAllEnrollments();
  }

  @GetMapping("/classSession/{classSessionId}")
  public List<Enrollment> getEnrollmentsByClassSessionId(@PathVariable Long classSessionId) {
    return enrollmentService.getEnrollmentsByClassSessionId(classSessionId);
  }

  @GetMapping("/student/{studentId}")
  public List<Enrollment> getEnrollmentsByStudentId(@PathVariable Long studentId) {
    return enrollmentService.getEnrollmentsByStudentId(studentId);
  }

  @PostMapping
  public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
    Enrollment createdEnrollment = enrollmentService.createEnrollment(enrollment);

    if (createdEnrollment == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(createdEnrollment);
  }

  @DeleteMapping("/{enrollmentId}")
  public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollmentId) {
    enrollmentService.deleteEnrollment(enrollmentId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

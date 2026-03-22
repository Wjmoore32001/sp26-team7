package edu.UNCG.sp26team7.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.Enrollment;
import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.repository.ClassSessionRepository;
import edu.UNCG.sp26team7.repository.EnrollmentRepository;
import edu.UNCG.sp26team7.repository.StudentRepository;

class EnrollmentServiceTest {

  @Test
  void getEnrollmentsByClassSessionId_returnsEnrollments() {
    EnrollmentRepository enrollmentRepository = Mockito.mock(EnrollmentRepository.class);
    ClassSessionRepository classSessionRepository = Mockito.mock(ClassSessionRepository.class);
    StudentRepository studentRepository = Mockito.mock(StudentRepository.class);

    EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepository, classSessionRepository,
        studentRepository);

    Enrollment enrollment = new Enrollment(1L, 1L, 2L);

    when(enrollmentRepository.findByClassSessionId(1L)).thenReturn(List.of(enrollment));

    List<Enrollment> result = enrollmentService.getEnrollmentsByClassSessionId(1L);

    assertEquals(1, result.size());
    assertEquals(2L, result.get(0).getStudentId());

    verify(enrollmentRepository).findByClassSessionId(1L);
  }

  @Test
  void createEnrollment_savesWhenSessionAndStudentExistAndNotDuplicate() {
    EnrollmentRepository enrollmentRepository = Mockito.mock(EnrollmentRepository.class);
    ClassSessionRepository classSessionRepository = Mockito.mock(ClassSessionRepository.class);
    StudentRepository studentRepository = Mockito.mock(StudentRepository.class);

    EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepository, classSessionRepository,
        studentRepository);

    Enrollment enrollment = new Enrollment(null, 1L, 2L);

    ClassSession classSession = new ClassSession();
    classSession.setClassSessionId(1L);

    Student student = new Student();
    student.setUserId(2L);

    when(classSessionRepository.findById(1L)).thenReturn(Optional.of(classSession));
    when(studentRepository.findById(2L)).thenReturn(Optional.of(student));
    when(enrollmentRepository.existsByClassSessionIdAndStudentId(1L, 2L)).thenReturn(false);
    when(enrollmentRepository.save(enrollment)).thenReturn(new Enrollment(1L, 1L, 2L));

    Enrollment result = enrollmentService.createEnrollment(enrollment);

    assertNotNull(result);
    assertEquals(1L, result.getEnrollmentId());
    assertEquals(1L, result.getClassSessionId());
    assertEquals(2L, result.getStudentId());

    verify(classSessionRepository).findById(1L);
    verify(studentRepository).findById(2L);
    verify(enrollmentRepository).existsByClassSessionIdAndStudentId(1L, 2L);
    verify(enrollmentRepository).save(enrollment);
  }
}

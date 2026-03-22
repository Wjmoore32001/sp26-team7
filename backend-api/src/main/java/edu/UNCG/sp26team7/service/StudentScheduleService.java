package edu.UNCG.sp26team7.service;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.entity.StudentSchedule;
import edu.UNCG.sp26team7.repository.ClassSessionRepository;
import edu.UNCG.sp26team7.repository.StudentRepository;
import edu.UNCG.sp26team7.repository.StudentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentScheduleService {

  @Autowired
  private StudentScheduleRepository studentScheduleRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private ClassSessionRepository classSessionRepository;

  public StudentSchedule createStudentSchedule(StudentSchedule studentSchedule) {
    if (studentSchedule.getStudent() == null || studentSchedule.getStudent().getUserId() == null) {
      throw new RuntimeException("Student id is required");
    }

    if (studentSchedule.getClassSession() == null || studentSchedule.getClassSession().getClassSessionId() == null) {
      throw new RuntimeException("Class session id is required");
    }

    Student realStudent = studentRepository
        .findById(studentSchedule.getStudent().getUserId())
        .orElseThrow(() -> new RuntimeException("Student not found"));

    ClassSession realClassSession = classSessionRepository
        .findById(studentSchedule.getClassSession().getClassSessionId())
        .orElseThrow(() -> new RuntimeException("Class session not found"));

    if (studentScheduleRepository.existsByStudentAndClassSession(realStudent, realClassSession)) {
      throw new RuntimeException("Student is already enrolled/waitlisted in this class session");
    }

    studentSchedule.setStudent(realStudent);
    studentSchedule.setClassSession(realClassSession);

    return studentScheduleRepository.save(studentSchedule);
  }

  public Optional<StudentSchedule> getStudentScheduleById(Long id) {
    return studentScheduleRepository.findById(id);
  }

  public List<StudentSchedule> getAllStudentSchedules() {
    return studentScheduleRepository.findAll();
  }

  public void deleteStudentSchedule(Long id) {
    studentScheduleRepository.deleteById(id);
  }
}

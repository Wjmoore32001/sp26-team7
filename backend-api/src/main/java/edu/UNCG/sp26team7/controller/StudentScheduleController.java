package edu.UNCG.sp26team7.controller;

import edu.UNCG.sp26team7.entity.StudentSchedule;
import edu.UNCG.sp26team7.service.StudentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student-schedules")
public class StudentScheduleController {

  @Autowired
  private StudentScheduleService studentScheduleService;

  @PostMapping
  public ResponseEntity<StudentSchedule> createStudentSchedule(@RequestBody StudentSchedule studentSchedule) {
    try {
      StudentSchedule created = studentScheduleService.createStudentSchedule(studentSchedule);
      return new ResponseEntity<>(created, HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping
  public ResponseEntity<List<StudentSchedule>> getAllStudentSchedules() {
    return new ResponseEntity<>(studentScheduleService.getAllStudentSchedules(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentSchedule> getStudentScheduleById(@PathVariable Long id) {
    Optional<StudentSchedule> schedule = studentScheduleService.getStudentScheduleById(id);
    return schedule.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudentSchedule(@PathVariable Long id) {
    studentScheduleService.deleteStudentSchedule(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

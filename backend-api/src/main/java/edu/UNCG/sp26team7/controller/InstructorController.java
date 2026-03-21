package edu.UNCG.sp26team7.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.UNCG.sp26team7.entity.Instructor;
import edu.UNCG.sp26team7.service.InstructorService;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

  private final InstructorService instructorService;

  public InstructorController(InstructorService instructorService) {
    this.instructorService = instructorService;
  }

  @GetMapping
  public List<Instructor> getAllInstructors() {
    return instructorService.getAllInstructors();
  }

  @GetMapping("/{userId}")
  public ResponseEntity<Instructor> getInstructorById(@PathVariable Long userId) {
    Instructor instructor = instructorService.getInstructorById(userId);

    if (instructor == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(instructor);
  }

  @PostMapping
  public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {
    Instructor createdInstructor = instructorService.createInstructor(instructor);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdInstructor);
  }

  @PatchMapping("/{userId}")
  public ResponseEntity<Instructor> updateInstructor(
      @PathVariable Long userId,
      @RequestBody Instructor updatedInstructor) {

    Instructor instructor = instructorService.updateInstructor(userId, updatedInstructor);

    if (instructor == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(instructor);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteInstructor(@PathVariable Long userId) {
    boolean deleted = instructorService.deleteInstructor(userId);

    if (!deleted) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.noContent().build();
  }
}

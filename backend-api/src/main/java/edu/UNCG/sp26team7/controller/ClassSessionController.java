package edu.UNCG.sp26team7.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.service.ClassSessionService;

@RestController
@RequestMapping("/classSessions")
public class ClassSessionController {

  private final ClassSessionService classSessionService;

  public ClassSessionController(ClassSessionService classSessionService) {
    this.classSessionService = classSessionService;
  }

  @GetMapping
  public List<ClassSession> getAllClassSessions() {
    return classSessionService.getAllClassSessions();
  }

  @GetMapping("/{classSessionId}")
  public ResponseEntity<ClassSession> getClassSessionById(@PathVariable Long classSessionId) {
    ClassSession classSession = classSessionService.getClassSessionById(classSessionId);

    if (classSession == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(classSession);
  }

  @GetMapping("/classTemplate/{classTemplateId}")
  public List<ClassSession> getSessionsByClassTemplateId(@PathVariable Long classTemplateId) {
    return classSessionService.getSessionsByClassTemplateId(classTemplateId);
  }

  @PostMapping
  public ResponseEntity<ClassSession> createClassSession(@RequestBody ClassSession classSession) {
    ClassSession createdSession = classSessionService.createClassSession(classSession);

    if (createdSession == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
  }

  @PutMapping("/{classSessionId}")
  public ResponseEntity<ClassSession> updateClassSession(
      @PathVariable Long classSessionId,
      @RequestBody ClassSession updatedClassSession) {

    ClassSession classSession = classSessionService.updateClassSession(classSessionId, updatedClassSession);

    if (classSession == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(classSession);
  }

  @DeleteMapping("/{classSessionId}")
  public ResponseEntity<Void> deleteClassSession(@PathVariable Long classSessionId) {
    boolean deleted = classSessionService.deleteClassSession(classSessionId);

    if (!deleted) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.noContent().build();
  }
}

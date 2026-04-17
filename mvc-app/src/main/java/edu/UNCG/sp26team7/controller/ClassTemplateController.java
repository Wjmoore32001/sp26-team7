package edu.UNCG.sp26team7.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.service.ClassTemplateService;

@RestController
@RequestMapping("/classTemplates")
public class ClassTemplateController {

  private final ClassTemplateService classTemplateService;

  public ClassTemplateController(ClassTemplateService classTemplateService) {
    this.classTemplateService = classTemplateService;
  }

  @GetMapping
  public List<ClassTemplate> getAllClassTemplates() {
    return classTemplateService.getAllClassTemplates();
  }

  @GetMapping("/{templateId}")
  public ResponseEntity<ClassTemplate> getClassTemplateById(@PathVariable Long templateId) {
    ClassTemplate classTemplate = classTemplateService.getClassTemplateById(templateId);

    if (classTemplate == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(classTemplate);
  }

  @GetMapping("/instructor/{userId}")
  public List<ClassTemplate> getInstructorTemplates(@PathVariable Long userId) {
    return classTemplateService.getInstructorTemplates(userId);
  }

  @PostMapping
  public ResponseEntity<ClassTemplate> createClassTemplate(@RequestBody ClassTemplate classTemplate) {
    ClassTemplate createdClassTemplate = classTemplateService.createClassTemplate(classTemplate);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdClassTemplate);
  }

  @PutMapping("/{templateId}")
  public ResponseEntity<ClassTemplate> updateClassTemplate(
      @PathVariable Long templateId,
      @RequestBody ClassTemplate updatedClassTemplate) {

    ClassTemplate classTemplate = classTemplateService.updateClassTemplate(templateId, updatedClassTemplate);

    if (classTemplate == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(classTemplate);
  }

  @DeleteMapping("/{templateId}")
  public ResponseEntity<Void> deleteClassTemplate(@PathVariable Long templateId) {
    boolean deleted = classTemplateService.deleteClassTemplate(templateId);

    if (!deleted) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.noContent().build();
  }
}

package edu.UNCG.sp26team7.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.repository.ClassSessionRepository;
import edu.UNCG.sp26team7.repository.ClassTemplateRepository;
import edu.UNCG.sp26team7.repository.EnrollmentRepository;
import edu.UNCG.sp26team7.repository.ReviewRepository;
import edu.UNCG.sp26team7.repository.StudentScheduleRepository;

@Service
public class ClassTemplateService {

  private final ClassTemplateRepository classTemplateRepository;
  private final ClassSessionRepository classSessionRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final StudentScheduleRepository studentScheduleRepository;
  private final ReviewRepository reviewRepository;

  public ClassTemplateService(
      ClassTemplateRepository classTemplateRepository,
      ClassSessionRepository classSessionRepository,
      EnrollmentRepository enrollmentRepository,
      StudentScheduleRepository studentScheduleRepository,
      ReviewRepository reviewRepository) {
    this.classTemplateRepository = classTemplateRepository;
    this.classSessionRepository = classSessionRepository;
    this.enrollmentRepository = enrollmentRepository;
    this.studentScheduleRepository = studentScheduleRepository;
    this.reviewRepository = reviewRepository;
  }

  public List<ClassTemplate> getAllClassTemplates() {
    return classTemplateRepository.findAll();
  }

  public List<ClassTemplate> getPublishedTemplates() {
    return classTemplateRepository.findByPublishedTrue();
  }

  public ClassTemplate getClassTemplateById(Long templateId) {
    return classTemplateRepository.findById(templateId).orElse(null);
  }

  public List<ClassTemplate> getInstructorTemplates(Long userId) {
    return classTemplateRepository.findByInstructorUserId(userId);
  }

  public ClassTemplate createClassTemplate(ClassTemplate classTemplate) {
    return classTemplateRepository.save(classTemplate);
  }

  public ClassTemplate updateClassTemplate(Long templateId, ClassTemplate updatedClassTemplate) {
    ClassTemplate existingClassTemplate = classTemplateRepository.findById(templateId).orElse(null);

    if (existingClassTemplate == null) {
      return null;
    }

    if (updatedClassTemplate.getTitle() != null) {
      existingClassTemplate.setTitle(updatedClassTemplate.getTitle());
    }

    if (updatedClassTemplate.getClassType() != null) {
      existingClassTemplate.setClassType(updatedClassTemplate.getClassType());
    }

    if (updatedClassTemplate.getIntensity() != null) {
      existingClassTemplate.setIntensity(updatedClassTemplate.getIntensity());
    }

    if (updatedClassTemplate.getDuration() != null) {
      existingClassTemplate.setDuration(updatedClassTemplate.getDuration());
    }

    if (updatedClassTemplate.getPrice() != null) {
      existingClassTemplate.setPrice(updatedClassTemplate.getPrice());
    }

    if (updatedClassTemplate.getDescription() != null) {
      existingClassTemplate.setDescription(updatedClassTemplate.getDescription());
    }

    if (updatedClassTemplate.getImageUrl() != null) {
      existingClassTemplate.setImageUrl(updatedClassTemplate.getImageUrl());
    }

    if (updatedClassTemplate.getInstructor() != null) {
      existingClassTemplate.setInstructor(updatedClassTemplate.getInstructor());
    }

    if (updatedClassTemplate.getPublished() != null) {
      existingClassTemplate.setPublished(updatedClassTemplate.getPublished());
    }

    return classTemplateRepository.save(existingClassTemplate);
  }

  @Transactional
  public boolean deleteClassTemplate(Long templateId) {
    if (!classTemplateRepository.existsById(templateId)) {
      return false;
    }

    List<ClassSession> sessions = classSessionRepository.findByClassTemplateClassTemplateId(templateId);

    for (ClassSession session : sessions) {
      Long classSessionId = session.getClassSessionId();
      enrollmentRepository.deleteByClassSessionClassSessionId(classSessionId);
      studentScheduleRepository.deleteByClassSessionClassSessionId(classSessionId);
      classSessionRepository.deleteById(classSessionId);
    }

    reviewRepository.deleteByClassTemplateClassTemplateId(templateId);
    classTemplateRepository.deleteById(templateId);

    return true;
  }
}

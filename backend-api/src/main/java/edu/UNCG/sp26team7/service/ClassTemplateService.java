package edu.UNCG.sp26team7.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.repository.ClassTemplateRepository;

@Service
public class ClassTemplateService {

  private final ClassTemplateRepository classTemplateRepository;

  public ClassTemplateService(ClassTemplateRepository classTemplateRepository) {
    this.classTemplateRepository = classTemplateRepository;
  }

  public List<ClassTemplate> getAllClassTemplates() {
    return classTemplateRepository.findAll();
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

    if (updatedClassTemplate.getDuration() != 0) {
      existingClassTemplate.setDuration(updatedClassTemplate.getDuration());
    }

    if (updatedClassTemplate.getPrice() != null) {
      existingClassTemplate.setPrice(updatedClassTemplate.getPrice());
    }

    if (updatedClassTemplate.getDescription() != null) {
      existingClassTemplate.setDescription(updatedClassTemplate.getDescription());
    }

    if (updatedClassTemplate.getInstructor() != null) {
      existingClassTemplate.setInstructor(updatedClassTemplate.getInstructor());
    }

    return classTemplateRepository.save(existingClassTemplate);
  }

  public boolean deleteClassTemplate(Long templateId) {
    if (!classTemplateRepository.existsById(templateId)) {
      return false;
    }

    classTemplateRepository.deleteById(templateId);
    return true;
  }
}

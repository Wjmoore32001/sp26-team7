package edu.UNCG.sp26team7.service;

import org.springframework.stereotype.Service;

import java.util.List;
import edu.UNCG.sp26team7.repository.ClassTemplateRepository;
import edu.UNCG.sp26team7.entity.ClassTemplate;

@Service
public class ClassTemplateService {

  private final ClassTemplateRepository classTemplateRepository;

  public ClassTemplateService(ClassTemplateRepository classTemplateRepository) {
    this.classTemplateRepository = classTemplateRepository;
  }

  // GET Methods ----------------------------------------------------------------

  public ClassTemplate getClassTemplateById(Long id) {
    return classTemplateRepository.findById(id).orElse(null);
  }

  public List<ClassTemplate> getInstructorTemplates(Long userId) {
    return classTemplateRepository.findByInstructorUserId(userId);
  }

  // POST Methods ----------------------------------------------------------------
  public ClassTemplate createClassTemplate(ClassTemplate classTemplate) {
    return classTemplateRepository.save(classTemplate);
  }
}

package edu.UNCG.sp26team7.service;

import org.springframework.stereotype.Service;
import edu.UNCG.sp26team7.repository.ClassTemplateRepository;

@Service
public class ClassTemplateService {

  private final ClassTemplateRepository classTemplateRepository;

  public ClassTemplateService(ClassTemplateRepository classTemplateRepository) {
    this.classTemplateRepository = classTemplateRepository;
  }
}

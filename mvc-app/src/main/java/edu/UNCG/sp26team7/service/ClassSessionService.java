package edu.UNCG.sp26team7.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.repository.ClassSessionRepository;
import edu.UNCG.sp26team7.repository.ClassTemplateRepository;

@Service
public class ClassSessionService {

  private final ClassSessionRepository classSessionRepository;
  private final ClassTemplateRepository classTemplateRepository;

  public ClassSessionService(
      ClassSessionRepository classSessionRepository,
      ClassTemplateRepository classTemplateRepository) {
    this.classSessionRepository = classSessionRepository;
    this.classTemplateRepository = classTemplateRepository;
  }

  public List<ClassSession> getAllClassSessions() {
    return classSessionRepository.findAll();
  }

  public ClassSession getClassSessionById(Long classSessionId) {
    return classSessionRepository.findById(classSessionId).orElse(null);
  }

  public List<ClassSession> getSessionsByClassTemplateId(Long classTemplateId) {
    return classSessionRepository.findByClassTemplateClassTemplateId(classTemplateId);
  }

  public ClassSession createClassSession(ClassSession classSession) {
    if (classSession.getClassTemplate() == null ||
        classSession.getClassTemplate().getClassTemplateId() == null) {
      return null;
    }

    ClassTemplate realTemplate = classTemplateRepository
        .findById(classSession.getClassTemplate().getClassTemplateId())
        .orElse(null);

    if (realTemplate == null) {
      return null;
    }

    classSession.setClassTemplate(realTemplate);
    return classSessionRepository.save(classSession);
  }

  public ClassSession updateClassSession(Long classSessionId, ClassSession updatedClassSession) {
    ClassSession existingSession = classSessionRepository.findById(classSessionId).orElse(null);

    if (existingSession == null) {
      return null;
    }

    if (updatedClassSession.getScheduledAt() != null) {
      existingSession.setScheduledAt(updatedClassSession.getScheduledAt());
    }

    if (updatedClassSession.getClassTemplate() != null &&
        updatedClassSession.getClassTemplate().getClassTemplateId() != null) {

      ClassTemplate realTemplate = classTemplateRepository
          .findById(updatedClassSession.getClassTemplate().getClassTemplateId())
          .orElse(null);

      if (realTemplate != null) {
        existingSession.setClassTemplate(realTemplate);
      }
    }

    return classSessionRepository.save(existingSession);
  }

  public boolean deleteClassSession(Long classSessionId) {
    if (!classSessionRepository.existsById(classSessionId)) {
      return false;
    }

    classSessionRepository.deleteById(classSessionId);
    return true;
  }
}

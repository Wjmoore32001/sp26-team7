package edu.UNCG.sp26team7.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.repository.ClassSessionRepository;
import edu.UNCG.sp26team7.repository.ClassTemplateRepository;

class ClassSessionServiceTest {

  @Test
  void getSessionsByClassTemplateId_returnsSessions() {
    ClassSessionRepository classSessionRepository = Mockito.mock(ClassSessionRepository.class);
    ClassTemplateRepository classTemplateRepository = Mockito.mock(ClassTemplateRepository.class);

    ClassSessionService classSessionService = new ClassSessionService(classSessionRepository, classTemplateRepository);

    ClassSession classSession = new ClassSession();
    classSession.setClassSessionId(1L);

    when(classSessionRepository.findByClassTemplateClassTemplateId(1L))
        .thenReturn(List.of(classSession));

    List<ClassSession> result = classSessionService.getSessionsByClassTemplateId(1L);

    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getClassSessionId());

    verify(classSessionRepository).findByClassTemplateClassTemplateId(1L);
  }

  @Test
  void createClassSession_savesWhenTemplateExists() {
    ClassSessionRepository classSessionRepository = Mockito.mock(ClassSessionRepository.class);
    ClassTemplateRepository classTemplateRepository = Mockito.mock(ClassTemplateRepository.class);

    ClassSessionService classSessionService = new ClassSessionService(classSessionRepository, classTemplateRepository);

    ClassTemplate requestTemplate = new ClassTemplate();
    requestTemplate.setClassTemplateId(1L);

    ClassTemplate realTemplate = new ClassTemplate();
    realTemplate.setClassTemplateId(1L);

    ClassSession requestSession = new ClassSession();
    requestSession.setScheduledAt(LocalDateTime.of(2026, 4, 20, 10, 0));
    requestSession.setClassTemplate(requestTemplate);

    ClassSession savedSession = new ClassSession();
    savedSession.setClassSessionId(1L);
    savedSession.setScheduledAt(requestSession.getScheduledAt());
    savedSession.setClassTemplate(realTemplate);

    when(classTemplateRepository.findById(1L)).thenReturn(Optional.of(realTemplate));
    when(classSessionRepository.save(requestSession)).thenReturn(savedSession);

    ClassSession result = classSessionService.createClassSession(requestSession);

    assertNotNull(result);
    assertEquals(1L, result.getClassSessionId());

    verify(classTemplateRepository).findById(1L);
    verify(classSessionRepository).save(requestSession);
  }
}

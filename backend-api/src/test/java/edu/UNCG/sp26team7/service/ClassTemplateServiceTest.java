package edu.UNCG.sp26team7.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.repository.ClassTemplateRepository;

class ClassTemplateServiceTest {

  @Test
  void getInstructorTemplates_returnsTemplatesFromRepository() {
    ClassTemplateRepository classTemplateRepository = Mockito.mock(ClassTemplateRepository.class);

    ClassTemplateService classTemplateService = new ClassTemplateService(classTemplateRepository);

    Long userId = 5L;

    ClassTemplate template1 = new ClassTemplate();
    template1.setTitle("Yoga Basics");

    ClassTemplate template2 = new ClassTemplate();
    template2.setTitle("Spin Intro");

    List<ClassTemplate> fakeTemplates = List.of(template1, template2);

    when(classTemplateRepository.findByInstructorUserId(userId))
        .thenReturn(fakeTemplates);

    List<ClassTemplate> result = classTemplateService.getInstructorTemplates(userId);

    assertEquals(2, result.size());
    assertEquals("Yoga Basics", result.get(0).getTitle());
    assertEquals("Spin Intro", result.get(1).getTitle());

    verify(classTemplateRepository).findByInstructorUserId(userId);
  }
}

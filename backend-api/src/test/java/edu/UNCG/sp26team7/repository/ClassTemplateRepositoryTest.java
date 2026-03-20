package edu.UNCG.sp26team7.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.entity.enums.ClassType;
import edu.UNCG.sp26team7.entity.enums.IntensityLevels;

@DataJpaTest
class ClassTemplateRepositoryTest {

  @Autowired
  private ClassTemplateRepository classTemplateRepository;

  @Test
  void repositoryIsCreated() {
    assertNotNull(classTemplateRepository);
  }

  @Test
  void creatingAndSavingAndRetrievingClassTemplate() {

    ClassTemplate classTemplate = new ClassTemplate();

    classTemplate.setTitle("Intro to Yoga");
    classTemplate.setClassType(ClassType.YOGA);
    classTemplate.setIntensity(IntensityLevels.HIGH);
    classTemplate.setDuration(1);
    classTemplate.setPrice(new BigDecimal("10.99"));
    classTemplate.setDescription("Its kinda fun");

    ClassTemplate savedTemplate = classTemplateRepository.save(classTemplate);

    assertNotNull(savedTemplate.getTemplate_id());
    assertNotNull(savedTemplate.getCreatedAt());
    assertNotNull(savedTemplate.getUpdatedAt());

  }
}

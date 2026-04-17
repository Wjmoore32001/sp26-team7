package edu.UNCG.sp26team7.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.entity.enums.ClassType;
import edu.UNCG.sp26team7.entity.enums.IntensityLevels;
import edu.UNCG.sp26team7.service.ClassTemplateService;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ClassTemplateController.class)
class ClassTemplateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private ClassTemplateService classTemplateService;

  @Test
  void getAllClassTemplates_returnsList() throws Exception {
    ClassTemplate template1 = new ClassTemplate();
    template1.setClassTemplateId(1L);
    template1.setTitle("Intro Yoga");
    template1.setClassType(ClassType.YOGA);
    template1.setIntensity(IntensityLevels.MEDIUM);
    template1.setDuration(60);
    template1.setPrice(new BigDecimal("19.99"));
    template1.setDescription("Beginner yoga class");

    ClassTemplate template2 = new ClassTemplate();
    template2.setClassTemplateId(2L);
    template2.setTitle("Spin Intro");
    template2.setClassType(ClassType.CYCLING);
    template2.setIntensity(IntensityLevels.HIGH);
    template2.setDuration(45);
    template2.setPrice(new BigDecimal("24.99"));
    template2.setDescription("Fast-paced cycling class");

    when(classTemplateService.getAllClassTemplates()).thenReturn(List.of(template1, template2));

    mockMvc.perform(get("/classTemplates"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].classTemplateId").value(1))
        .andExpect(jsonPath("$[0].title").value("Intro Yoga"))
        .andExpect(jsonPath("$[1].classTemplateId").value(2))
        .andExpect(jsonPath("$[1].title").value("Spin Intro"));
  }

  @Test
  void getClassTemplateById_returnsTemplateWhenFound() throws Exception {
    ClassTemplate template = new ClassTemplate();
    template.setClassTemplateId(1L);
    template.setTitle("Intro Yoga");
    template.setClassType(ClassType.YOGA);
    template.setIntensity(IntensityLevels.MEDIUM);
    template.setDuration(60);
    template.setPrice(new BigDecimal("19.99"));
    template.setDescription("Beginner yoga class");

    when(classTemplateService.getClassTemplateById(1L)).thenReturn(template);

    mockMvc.perform(get("/classTemplates/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.classTemplateId").value(1))
        .andExpect(jsonPath("$.title").value("Intro Yoga"))
        .andExpect(jsonPath("$.description").value("Beginner yoga class"));
  }

  @Test
  void getClassTemplateById_returns404WhenMissing() throws Exception {
    when(classTemplateService.getClassTemplateById(99L)).thenReturn(null);

    mockMvc.perform(get("/classTemplates/99"))
        .andExpect(status().isNotFound());
  }

  @Test
  void getInstructorTemplates_returnsList() throws Exception {
    ClassTemplate template = new ClassTemplate();
    template.setClassTemplateId(1L);
    template.setTitle("Intro Yoga");
    template.setClassType(ClassType.YOGA);
    template.setIntensity(IntensityLevels.MEDIUM);
    template.setDuration(60);
    template.setPrice(new BigDecimal("19.99"));
    template.setDescription("Beginner yoga class");

    when(classTemplateService.getInstructorTemplates(5L)).thenReturn(List.of(template));

    mockMvc.perform(get("/classTemplates/instructor/5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].classTemplateId").value(1))
        .andExpect(jsonPath("$[0].title").value("Intro Yoga"));
  }

  @Test
  void createClassTemplate_returns201() throws Exception {
    ClassTemplate requestTemplate = new ClassTemplate();
    requestTemplate.setTitle("Intro Yoga");
    requestTemplate.setClassType(ClassType.YOGA);
    requestTemplate.setIntensity(IntensityLevels.MEDIUM);
    requestTemplate.setDuration(60);
    requestTemplate.setPrice(new BigDecimal("19.99"));
    requestTemplate.setDescription("Beginner yoga class");

    ClassTemplate createdTemplate = new ClassTemplate();
    createdTemplate.setClassTemplateId(1L);
    createdTemplate.setTitle("Intro Yoga");
    createdTemplate.setClassType(ClassType.YOGA);
    createdTemplate.setIntensity(IntensityLevels.MEDIUM);
    createdTemplate.setDuration(60);
    createdTemplate.setPrice(new BigDecimal("19.99"));
    createdTemplate.setDescription("Beginner yoga class");

    when(classTemplateService.createClassTemplate(org.mockito.ArgumentMatchers.any(ClassTemplate.class)))
        .thenReturn(createdTemplate);

    mockMvc.perform(post("/classTemplates")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestTemplate)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.classTemplateId").value(1))
        .andExpect(jsonPath("$.title").value("Intro Yoga"));
  }

  @Test
  void updateClassTemplate_returnsUpdatedTemplate() throws Exception {
    ClassTemplate updateRequest = new ClassTemplate();
    updateRequest.setDescription("Updated description");

    ClassTemplate updatedTemplate = new ClassTemplate();
    updatedTemplate.setClassTemplateId(1L);
    updatedTemplate.setTitle("Intro Yoga");
    updatedTemplate.setClassType(ClassType.YOGA);
    updatedTemplate.setIntensity(IntensityLevels.MEDIUM);
    updatedTemplate.setDuration(60);
    updatedTemplate.setPrice(new BigDecimal("19.99"));
    updatedTemplate.setDescription("Updated description");

    when(classTemplateService.updateClassTemplate(
        org.mockito.ArgumentMatchers.eq(1L),
        org.mockito.ArgumentMatchers.any(ClassTemplate.class)))
        .thenReturn(updatedTemplate);

    mockMvc.perform(put("/classTemplates/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.classTemplateId").value(1))
        .andExpect(jsonPath("$.description").value("Updated description"));
  }

  @Test
  void deleteClassTemplate_returns204WhenDeleted() throws Exception {
    when(classTemplateService.deleteClassTemplate(1L)).thenReturn(true);

    mockMvc.perform(delete("/classTemplates/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteClassTemplate_returns404WhenMissing() throws Exception {
    when(classTemplateService.deleteClassTemplate(99L)).thenReturn(false);

    mockMvc.perform(delete("/classTemplates/99"))
        .andExpect(status().isNotFound());
  }
}

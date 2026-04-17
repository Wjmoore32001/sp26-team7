package edu.UNCG.sp26team7.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.service.ClassSessionService;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ClassSessionController.class)
class ClassSessionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private ClassSessionService classSessionService;

  @Test
  void getAllClassSessions_returnsList() throws Exception {
    ClassTemplate classTemplate = new ClassTemplate();
    classTemplate.setClassTemplateId(1L);

    ClassSession classSession = new ClassSession();
    classSession.setClassSessionId(1L);
    classSession.setScheduledAt(LocalDateTime.of(2026, 4, 20, 10, 0));
    classSession.setClassTemplate(classTemplate);

    when(classSessionService.getAllClassSessions()).thenReturn(List.of(classSession));

    mockMvc.perform(get("/classSessions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].classSessionId").value(1));
  }

  @Test
  void createClassSession_returns201() throws Exception {
    ClassTemplate classTemplate = new ClassTemplate();
    classTemplate.setClassTemplateId(1L);

    ClassSession requestSession = new ClassSession();
    requestSession.setScheduledAt(LocalDateTime.of(2026, 4, 20, 10, 0));
    requestSession.setClassTemplate(classTemplate);

    ClassSession createdSession = new ClassSession();
    createdSession.setClassSessionId(1L);
    createdSession.setScheduledAt(LocalDateTime.of(2026, 4, 20, 10, 0));
    createdSession.setClassTemplate(classTemplate);

    when(classSessionService.createClassSession(org.mockito.ArgumentMatchers.any(ClassSession.class)))
        .thenReturn(createdSession);

    mockMvc.perform(post("/classSessions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestSession)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.classSessionId").value(1));
  }

  @Test
  void deleteClassSession_returns204WhenDeleted() throws Exception {
    when(classSessionService.deleteClassSession(1L)).thenReturn(true);

    mockMvc.perform(delete("/classSessions/1"))
        .andExpect(status().isNoContent());
  }
}

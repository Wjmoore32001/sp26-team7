package edu.UNCG.sp26team7.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.UNCG.sp26team7.entity.Enrollment;
import edu.UNCG.sp26team7.service.EnrollmentService;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(EnrollmentController.class)
class EnrollmentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private EnrollmentService enrollmentService;

  @Test
  void getEnrollmentsByClassSessionId_returnsList() throws Exception {
    Enrollment enrollment = new Enrollment(1L, 1L, 2L);

    when(enrollmentService.getEnrollmentsByClassSessionId(1L)).thenReturn(List.of(enrollment));

    mockMvc.perform(get("/enrollments/classSession/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].classSessionId").value(1))
        .andExpect(jsonPath("$[0].studentId").value(2));
  }

  @Test
  void createEnrollment_returns201() throws Exception {
    Enrollment requestEnrollment = new Enrollment(null, 1L, 2L);
    Enrollment createdEnrollment = new Enrollment(1L, 1L, 2L);

    when(enrollmentService.createEnrollment(org.mockito.ArgumentMatchers.any(Enrollment.class)))
        .thenReturn(createdEnrollment);

    mockMvc.perform(post("/enrollments")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestEnrollment)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.enrollmentId").value(1))
        .andExpect(jsonPath("$.classSessionId").value(1))
        .andExpect(jsonPath("$.studentId").value(2));
  }

  @Test
  void deleteEnrollment_returns204WhenDeleted() throws Exception {
    when(enrollmentService.deleteEnrollment(1L, 2L)).thenReturn(true);

    mockMvc.perform(delete("/enrollments/classSession/1/student/2"))
        .andExpect(status().isNoContent());
  }
}

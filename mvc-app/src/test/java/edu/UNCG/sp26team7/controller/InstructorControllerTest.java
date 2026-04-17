package edu.UNCG.sp26team7.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.UNCG.sp26team7.entity.Instructor;
import edu.UNCG.sp26team7.entity.enums.UserRole;
import edu.UNCG.sp26team7.service.InstructorService;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(InstructorController.class)
class InstructorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private InstructorService instructorService;

  @Test
  void getAllInstructors_returnsList() throws Exception {
    Instructor instructor1 = new Instructor();
    instructor1.setUserId(1L);
    instructor1.setName("Alice Smith");
    instructor1.setEmail("alice@uncg.edu");
    instructor1.setPasswordHash("hash1");
    instructor1.setRole(UserRole.INSTRUCTOR);

    Instructor instructor2 = new Instructor();
    instructor2.setUserId(2L);
    instructor2.setName("Bob Jones");
    instructor2.setEmail("bob@uncg.edu");
    instructor2.setPasswordHash("hash2");
    instructor2.setRole(UserRole.INSTRUCTOR);

    when(instructorService.getAllInstructors()).thenReturn(List.of(instructor1, instructor2));

    mockMvc.perform(get("/instructors"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].userId").value(1))
        .andExpect(jsonPath("$[0].name").value("Alice Smith"))
        .andExpect(jsonPath("$[1].userId").value(2))
        .andExpect(jsonPath("$[1].name").value("Bob Jones"));
  }

  @Test
  void getInstructorById_returnsInstructorWhenFound() throws Exception {
    Instructor instructor = new Instructor();
    instructor.setUserId(1L);
    instructor.setName("Alice Smith");
    instructor.setEmail("alice@uncg.edu");
    instructor.setPasswordHash("hash1");
    instructor.setRole(UserRole.INSTRUCTOR);

    when(instructorService.getInstructorById(1L)).thenReturn(instructor);

    mockMvc.perform(get("/instructors/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.name").value("Alice Smith"))
        .andExpect(jsonPath("$.email").value("alice@uncg.edu"));
  }

  @Test
  void getInstructorById_returns404WhenMissing() throws Exception {
    when(instructorService.getInstructorById(99L)).thenReturn(null);

    mockMvc.perform(get("/instructors/99"))
        .andExpect(status().isNotFound());
  }

  @Test
  void createInstructor_returns201() throws Exception {
    Instructor requestInstructor = new Instructor();
    requestInstructor.setName("Alice Smith");
    requestInstructor.setEmail("alice@uncg.edu");
    requestInstructor.setPasswordHash("hash1");

    Instructor createdInstructor = new Instructor();
    createdInstructor.setUserId(1L);
    createdInstructor.setName("Alice Smith");
    createdInstructor.setEmail("alice@uncg.edu");
    createdInstructor.setPasswordHash("hash1");
    createdInstructor.setRole(UserRole.INSTRUCTOR);

    when(instructorService.createInstructor(org.mockito.ArgumentMatchers.any(Instructor.class)))
        .thenReturn(createdInstructor);

    mockMvc.perform(post("/instructors")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestInstructor)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.name").value("Alice Smith"))
        .andExpect(jsonPath("$.email").value("alice@uncg.edu"));
  }

  @Test
  void updateInstructor_returnsUpdatedInstructor() throws Exception {
    Instructor updateRequest = new Instructor();
    updateRequest.setEmail("alice_new@uncg.edu");

    Instructor updatedInstructor = new Instructor();
    updatedInstructor.setUserId(1L);
    updatedInstructor.setName("Alice Smith");
    updatedInstructor.setEmail("alice_new@uncg.edu");
    updatedInstructor.setPasswordHash("hash1");
    updatedInstructor.setRole(UserRole.INSTRUCTOR);

    when(instructorService.updateInstructor(
        org.mockito.ArgumentMatchers.eq(1L),
        org.mockito.ArgumentMatchers.any(Instructor.class)))
        .thenReturn(updatedInstructor);

    mockMvc.perform(put("/instructors/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.email").value("alice_new@uncg.edu"));
  }

  @Test
  void deleteInstructor_returns204WhenDeleted() throws Exception {
    when(instructorService.deleteInstructor(1L)).thenReturn(true);

    mockMvc.perform(delete("/instructors/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteInstructor_returns404WhenMissing() throws Exception {
    when(instructorService.deleteInstructor(99L)).thenReturn(false);

    mockMvc.perform(delete("/instructors/99"))
        .andExpect(status().isNotFound());
  }
}

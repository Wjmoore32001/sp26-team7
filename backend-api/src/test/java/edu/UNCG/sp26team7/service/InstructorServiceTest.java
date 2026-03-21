package edu.UNCG.sp26team7.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.UNCG.sp26team7.entity.Instructor;
import edu.UNCG.sp26team7.entity.enums.UserRole;
import edu.UNCG.sp26team7.repository.InstructorRepository;

class InstructorServiceTest {

  @Test
  void getAllInstructors_returnsAllInstructors() {
    InstructorRepository instructorRepository = Mockito.mock(InstructorRepository.class);
    InstructorService instructorService = new InstructorService(instructorRepository);

    Instructor instructor1 = new Instructor();
    instructor1.setName("Alice Smith");

    Instructor instructor2 = new Instructor();
    instructor2.setName("Bob Jones");

    List<Instructor> fakeInstructors = List.of(instructor1, instructor2);

    when(instructorRepository.findAll()).thenReturn(fakeInstructors);

    List<Instructor> result = instructorService.getAllInstructors();

    assertEquals(2, result.size());
    assertEquals("Alice Smith", result.get(0).getName());
    assertEquals("Bob Jones", result.get(1).getName());

    verify(instructorRepository).findAll();
  }

  @Test
  void getInstructorById_returnsInstructorWhenFound() {
    InstructorRepository instructorRepository = Mockito.mock(InstructorRepository.class);
    InstructorService instructorService = new InstructorService(instructorRepository);

    Instructor instructor = new Instructor();
    instructor.setUserId(1L);
    instructor.setName("Alice Smith");
    instructor.setEmail("alice@uncg.edu");
    instructor.setPasswordHash("hashed-password");
    instructor.setRole(UserRole.INSTRUCTOR);

    when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

    Instructor result = instructorService.getInstructorById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getUserId());
    assertEquals("Alice Smith", result.getName());

    verify(instructorRepository).findById(1L);
  }

  @Test
  void createInstructor_savesAndReturnsInstructor() {
    InstructorRepository instructorRepository = Mockito.mock(InstructorRepository.class);
    InstructorService instructorService = new InstructorService(instructorRepository);

    Instructor instructor = new Instructor();
    instructor.setName("Alice Smith");
    instructor.setEmail("alice@uncg.edu");
    instructor.setPasswordHash("hashed-password");
    instructor.setRole(UserRole.INSTRUCTOR);

    Instructor savedInstructor = new Instructor();
    savedInstructor.setUserId(1L);
    savedInstructor.setName("Alice Smith");
    savedInstructor.setEmail("alice@uncg.edu");
    savedInstructor.setPasswordHash("hashed-password");
    savedInstructor.setRole(UserRole.INSTRUCTOR);

    when(instructorRepository.save(instructor)).thenReturn(savedInstructor);

    Instructor result = instructorService.createInstructor(instructor);

    assertNotNull(result);
    assertEquals(1L, result.getUserId());
    assertEquals("Alice Smith", result.getName());

    verify(instructorRepository).save(instructor);
  }

  @Test
  void updateInstructor_updatesAndReturnsInstructor() {
    InstructorRepository instructorRepository = Mockito.mock(InstructorRepository.class);
    InstructorService instructorService = new InstructorService(instructorRepository);

    Instructor existingInstructor = new Instructor();
    existingInstructor.setUserId(1L);
    existingInstructor.setName("Old Name");
    existingInstructor.setEmail("old@uncg.edu");
    existingInstructor.setPasswordHash("old-hash");
    existingInstructor.setRole(UserRole.INSTRUCTOR);

    Instructor updatedInstructor = new Instructor();
    updatedInstructor.setName("New Name");
    updatedInstructor.setEmail("new@uncg.edu");
    updatedInstructor.setPasswordHash("new-hash");
    updatedInstructor.setRole(UserRole.INSTRUCTOR);

    when(instructorRepository.findById(1L)).thenReturn(Optional.of(existingInstructor));
    when(instructorRepository.save(existingInstructor)).thenReturn(existingInstructor);

    Instructor result = instructorService.updateInstructor(1L, updatedInstructor);

    assertNotNull(result);
    assertEquals("New Name", result.getName());
    assertEquals("new@uncg.edu", result.getEmail());
    assertEquals("new-hash", result.getPasswordHash());

    verify(instructorRepository).findById(1L);
    verify(instructorRepository).save(existingInstructor);
  }

  @Test
  void deleteInstructor_deletesWhenInstructorExists() {
    InstructorRepository instructorRepository = Mockito.mock(InstructorRepository.class);
    InstructorService instructorService = new InstructorService(instructorRepository);

    when(instructorRepository.existsById(1L)).thenReturn(true);

    boolean result = instructorService.deleteInstructor(1L);

    assertTrue(result);

    verify(instructorRepository).existsById(1L);
    verify(instructorRepository).deleteById(1L);
  }

  @Test
  void deleteInstructor_returnsFalseWhenInstructorDoesNotExist() {
    InstructorRepository instructorRepository = Mockito.mock(InstructorRepository.class);
    InstructorService instructorService = new InstructorService(instructorRepository);

    when(instructorRepository.existsById(1L)).thenReturn(false);

    boolean result = instructorService.deleteInstructor(1L);

    assertFalse(result);

    verify(instructorRepository).existsById(1L);
  }
}

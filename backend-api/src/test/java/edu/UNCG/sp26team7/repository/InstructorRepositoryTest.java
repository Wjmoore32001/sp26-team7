package edu.UNCG.sp26team7.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import edu.UNCG.sp26team7.entity.Instructor;
import edu.UNCG.sp26team7.entity.enums.UserRole;

@DataJpaTest
class InstructorRepositoryTest {

  @Autowired
  private InstructorRepository instructorRepository;

  @Test
  void repositoryIsCreated() {
    assertNotNull(instructorRepository);
  }

  @Test
  void saveAndFindById_shouldWork() {
    Instructor instructor = new Instructor();
    instructor.setName("Alice Smith");
    instructor.setEmail("alice@uncg.edu");
    instructor.setPasswordHash("hashed-password");
    instructor.setRole(UserRole.INSTRUCTOR);

    Instructor savedInstructor = instructorRepository.save(instructor);

    assertNotNull(savedInstructor.getUserId());

    Optional<Instructor> foundInstructor = instructorRepository.findById(savedInstructor.getUserId());

    assertTrue(foundInstructor.isPresent());
    assertEquals("Alice Smith", foundInstructor.get().getName());
    assertEquals("alice@uncg.edu", foundInstructor.get().getEmail());
    assertEquals(UserRole.INSTRUCTOR, foundInstructor.get().getRole());
  }
}

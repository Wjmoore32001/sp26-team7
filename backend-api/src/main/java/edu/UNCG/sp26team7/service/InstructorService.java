package edu.UNCG.sp26team7.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.UNCG.sp26team7.entity.Instructor;
import edu.UNCG.sp26team7.repository.InstructorRepository;

@Service
public class InstructorService {

  private final InstructorRepository instructorRepository;

  public InstructorService(InstructorRepository instructorRepository) {
    this.instructorRepository = instructorRepository;
  }

  public List<Instructor> getAllInstructors() {
    return instructorRepository.findAll();
  }

  public Instructor getInstructorById(Long userId) {
    return instructorRepository.findById(userId).orElse(null);
  }

  public Instructor createInstructor(Instructor instructor) {
    return instructorRepository.save(instructor);
  }

  public Instructor updateInstructor(Long userId, Instructor updatedInstructor) {
    Instructor existingInstructor = instructorRepository.findById(userId).orElse(null);

    if (existingInstructor == null) {
      return null;
    }

    if (updatedInstructor.getName() != null) {
      existingInstructor.setName(updatedInstructor.getName());
    }

    if (updatedInstructor.getEmail() != null) {
      existingInstructor.setEmail(updatedInstructor.getEmail());
    }

    if (updatedInstructor.getPasswordHash() != null) {
      existingInstructor.setPasswordHash(updatedInstructor.getPasswordHash());
    }

    return instructorRepository.save(existingInstructor);
  }

  public boolean deleteInstructor(Long userId) {
    if (!instructorRepository.existsById(userId)) {
      return false;
    }

    instructorRepository.deleteById(userId);
    return true;
  }
}

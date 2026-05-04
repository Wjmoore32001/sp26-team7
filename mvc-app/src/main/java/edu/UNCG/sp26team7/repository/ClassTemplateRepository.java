package edu.UNCG.sp26team7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.UNCG.sp26team7.entity.ClassTemplate;

@Repository
public interface ClassTemplateRepository extends JpaRepository<ClassTemplate, Long> {
  List<ClassTemplate> findByInstructorUserId(Long userId);

  List<ClassTemplate> findByPublishedTrue();
}

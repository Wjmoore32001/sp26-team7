package edu.UNCG.sp26team7.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.UNCG.sp26team7.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  List<Enrollment> findByClassSessionId(Long classSessionId);

  List<Enrollment> findByStudentId(Long studentId);

  Optional<Enrollment> findByClassSessionIdAndStudentId(Long classSessionId, Long studentId);

  boolean existsByClassSessionIdAndStudentId(Long classSessionId, Long studentId);
}

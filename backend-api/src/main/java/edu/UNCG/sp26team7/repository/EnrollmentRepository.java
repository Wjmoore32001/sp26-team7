package edu.UNCG.sp26team7.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.UNCG.sp26team7.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  List<Enrollment> findByClassSessionClassSessionId(Long classSessionId);

  List<Enrollment> findByStudentUserId(Long studentId);

  Optional<Enrollment> findByClassSessionClassSessionIdAndStudentUserId(Long classSessionId, Long studentId);

  boolean existsByClassSessionClassSessionIdAndStudentUserId(long l, Long studentId);
}

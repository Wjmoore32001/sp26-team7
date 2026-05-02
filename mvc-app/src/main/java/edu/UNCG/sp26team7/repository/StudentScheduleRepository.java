package edu.UNCG.sp26team7.repository;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.entity.StudentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentScheduleRepository extends JpaRepository<StudentSchedule, Long> {
  boolean existsByStudentAndClassSession(Student student, ClassSession classSession);

  List<StudentSchedule> findByStudentUserId(Long userId);

  void deleteByClassSessionClassSessionId(Long classSessionId);
}

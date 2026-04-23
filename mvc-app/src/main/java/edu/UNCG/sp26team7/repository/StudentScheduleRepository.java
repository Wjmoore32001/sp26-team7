package edu.UNCG.sp26team7.repository;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.entity.StudentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentScheduleRepository extends JpaRepository<StudentSchedule, Long> {
  boolean existsByStudentAndClassSession(Student student, ClassSession classSession);

  @Modifying
  @Query("delete from StudentSchedule ss where ss.classSession.classSessionId = :classSessionId")
  void deleteByClassSessionClassSessionId(Long classSessionId);
}

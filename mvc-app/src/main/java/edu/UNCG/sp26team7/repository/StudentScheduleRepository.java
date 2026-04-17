package edu.UNCG.sp26team7.repository;

import edu.UNCG.sp26team7.entity.StudentSchedule;
import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.entity.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentScheduleRepository extends JpaRepository<StudentSchedule, Long> {

  boolean existsByStudentAndClassSession(Student student, ClassSession classSession);

}

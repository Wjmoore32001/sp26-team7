package edu.UNCG.sp26team7.service;

import edu.UNCG.sp26team7.entity.StudentSchedule;
import edu.UNCG.sp26team7.repository.StudentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentScheduleService {

    @Autowired
    private StudentScheduleRepository studentScheduleRepository;

    public StudentSchedule createStudentSchedule(StudentSchedule studentSchedule) {
        if (studentScheduleRepository.existsByStudentAndClassSession(studentSchedule.getStudent(),
                studentSchedule.getClassSession())) {
            throw new RuntimeException("Student is already enrolled/waitlisted in this class session");
        }
        return studentScheduleRepository.save(studentSchedule);
    }

    public Optional<StudentSchedule> getStudentScheduleById(Long id) {
        return studentScheduleRepository.findById(id);
    }

    public List<StudentSchedule> getAllStudentSchedules() {
        return studentScheduleRepository.findAll();
    }

    public void deleteStudentSchedule(Long id) {
        studentScheduleRepository.deleteById(id);
    }
}

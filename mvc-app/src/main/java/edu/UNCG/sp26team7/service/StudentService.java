package edu.UNCG.sp26team7.service;

import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id).map(student -> {
            if (studentDetails.getName() != null) {
                student.setName(studentDetails.getName());
            }
            if (studentDetails.getEmail() != null) {
                student.setEmail(studentDetails.getEmail());
            }
            if (studentDetails.getRole() != null) {
                student.setRole(studentDetails.getRole());
            }
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

}

package edu.UNCG.sp26team7.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.UNCG.sp26team7.service.ClassSessionService;
import edu.UNCG.sp26team7.service.StudentService;
import edu.UNCG.sp26team7.entity.Student;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentUiController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassSessionService classSessionService;

    @GetMapping("/profile")
    public String getProfile(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");

        if (studentId == null) {
            return "redirect:/signin";
        }

        Student student = studentService.getStudentById(studentId).orElse(null);

        model.addAttribute("student", student);
        return "student/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");

        if (studentId == null) {
            return "redirect:/signin";
        }

        Student student = studentService.getStudentById(studentId).orElse(null);

        model.addAttribute("student", student);
        return "student/edit-profile";
    }

    @PostMapping("/profile")
    public String updateProfile(HttpSession session, @RequestParam String name, @RequestParam String email,
            @RequestParam(required = false) String password) {
        Long studentId = (Long) session.getAttribute("studentId");

        if (studentId == null) {
            return "redirect:/signin";
        }

        Student studentDetails = new Student();
        studentDetails.setName(name);
        studentDetails.setEmail(email);

        if (password != null && !password.isEmpty()) {
            studentDetails.setPasswordHash(password);
        }

        studentService.updateStudent(studentId, studentDetails);
        return "redirect:/student/profile?success";
    }

    @GetMapping("/browse")
    public String browseClasses(Model model) {
        model.addAttribute("sessions", classSessionService.getAllClassSessions());
        return "student/browse";
    }

    @GetMapping("/home")
    public String home() {
        return "student/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

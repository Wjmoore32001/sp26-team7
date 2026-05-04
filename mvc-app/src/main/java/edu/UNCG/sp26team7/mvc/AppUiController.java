package edu.UNCG.sp26team7.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.UNCG.sp26team7.entity.Instructor;
import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.entity.enums.UserRole;
import edu.UNCG.sp26team7.service.InstructorService;
import edu.UNCG.sp26team7.service.StudentService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AppUiController {

  @Autowired
  private StudentService studentService;

  @Autowired
  private InstructorService instructorService;

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/signin")
  public String signin() {
    return "signin";
  }

  @GetMapping("/signup")
  public String signup() {
    return "signup";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/signin";
  }

  @PostMapping("/signin")
  public String signin(@RequestParam String email,
      @RequestParam String password,
      HttpSession session) {

    try {
      Student student = studentService.authenticate(email, password);
      session.setAttribute("studentId", student.getUserId());
      session.setAttribute("role", "STUDENT");
      return "redirect:/student/profile";
    } catch (Exception ignored) {
    }

    try {
      Instructor instructor = instructorService.authenticate(email, password);
      session.setAttribute("instructorId", instructor.getUserId());
      session.setAttribute("role", "INSTRUCTOR");
      return "redirect:/instructor/home.html";
    } catch (Exception ignored) {
    }

    return "redirect:/signin?error";
  }

  @PostMapping("/signup")
  public String signup(@RequestParam String role,
      @RequestParam String name,
      @RequestParam String email,
      @RequestParam String password,
      @RequestParam String confirmPassword,
      Model model) {

    if (!password.equals(confirmPassword)) {
      model.addAttribute("error", "passwords do not match");
      return "signup";
    }

    if (UserRole.STUDENT.name().equals(role)) {
      Student student = new Student();
      student.setName(name);
      student.setEmail(email);
      student.setPasswordHash(password);
      student.setRole(UserRole.STUDENT);
      studentService.createStudent(student);
      return "redirect:/signin";
    }

    if (UserRole.INSTRUCTOR.name().equals(role)) {
      Instructor instructor = new Instructor();
      instructor.setName(name);
      instructor.setEmail(email);
      instructor.setPasswordHash(password);
      instructor.setRole(UserRole.INSTRUCTOR);
      instructorService.createInstructor(instructor);
      return "redirect:/signin";
    }

    model.addAttribute("error", "Invalid account type");
    return "signup";
  }
}

package edu.UNCG.sp26team7.mvc;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.entity.Review;
import edu.UNCG.sp26team7.entity.Student;
import edu.UNCG.sp26team7.entity.StudentSchedule;
import edu.UNCG.sp26team7.entity.enums.BookingStatus;
import edu.UNCG.sp26team7.service.ClassSessionService;
import edu.UNCG.sp26team7.service.ClassTemplateService;
import edu.UNCG.sp26team7.service.ReviewService;
import edu.UNCG.sp26team7.service.StudentScheduleService;
import edu.UNCG.sp26team7.service.StudentService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentUiController {

  @Autowired
  private StudentService studentService;

  @Autowired
  private ClassSessionService classSessionService;

  @Autowired
  private ClassTemplateService classTemplateService;

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private StudentScheduleService studentScheduleService;

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
  public String browseClasses(HttpSession session, Model model) {
    Long studentId = (Long) session.getAttribute("studentId");

    model.addAttribute("studentLoggedIn", studentId != null);
    model.addAttribute("templates", classTemplateService.getPublishedTemplates());
    return "student/browse";
  }

  @GetMapping("/home")
  public String home() {
    return "student/home";
  }

  @PostMapping("/enroll")
  public String enroll(@RequestParam Long classSessionId, HttpSession session) {
    Long studentId = (Long) session.getAttribute("studentId");

    if (studentId == null) {
      return "redirect:/signin";
    }

    StudentSchedule schedule = new StudentSchedule();
    Student student = new Student();
    student.setUserId(studentId);
    ClassSession classSession = new ClassSession();
    classSession.setClassSessionId(classSessionId);

    schedule.setStudent(student);
    schedule.setClassSession(classSession);
    schedule.setEnrolledAt(LocalDateTime.now());
    schedule.setBookingStatus(BookingStatus.ENROLLED);

    studentScheduleService.createStudentSchedule(schedule);
    return "redirect:/student/browse?joined";
  }

  @PostMapping("/cancel")
  public String cancelClass(@RequestParam Long scheduleId, HttpSession session) {
    Long studentId = (Long) session.getAttribute("studentId");

    if (studentId == null) {
      return "redirect:/signin";
    }

    studentScheduleService.deleteStudentSchedule(scheduleId);
    return "redirect:/student/my-classes?cancelled";
  }

  @GetMapping("/my-classes")
  public String myClasses(HttpSession session, Model model) {
    Long studentId = (Long) session.getAttribute("studentId");

    if (studentId == null) {
      return "redirect:/signin";
    }

    List<StudentSchedule> schedules = studentScheduleService.getSchedulesForStudent(studentId);
    model.addAttribute("schedules", schedules);
    return "student/my-classes";
  }

  @GetMapping("/classes/{id}")
  public String classDetails(@PathVariable("id") Long templateId, Model model, HttpSession session) {
    Long studentId = (Long) session.getAttribute("studentId");

    ClassTemplate template = classTemplateService.getClassTemplateById(templateId);
    List<Review> reviews = reviewService.getReviewsByClassTemplateId(templateId);
    List<ClassSession> sessions = classSessionService.getSessionsByClassTemplateId(templateId);

    model.addAttribute("studentLoggedIn", studentId != null);
    model.addAttribute("template", template);
    model.addAttribute("reviews", reviews);
    model.addAttribute("sessions", sessions);
    return "student/class-details";
  }

  @GetMapping("/classes/{id}/reviews/new")
  public String newReview(@PathVariable("id") Long templateId, Model model, HttpSession session) {
    Long studentId = (Long) session.getAttribute("studentId");

    if (studentId == null) {
      return "redirect:/signin";
    }

    ClassTemplate template = classTemplateService.getClassTemplateById(templateId);
    model.addAttribute("template", template);
    return "student/leave-review";
  }

  @PostMapping("/classes/{id}/reviews")
  public String submitReview(@PathVariable("id") Long templateId, @RequestParam Integer rating,
      @RequestParam String comment, HttpSession session) {
    Long studentId = (Long) session.getAttribute("studentId");

    if (studentId == null) {
      return "redirect:/signin";
    }

    Student student = studentService.getStudentById(studentId).orElse(null);
    ClassTemplate template = classTemplateService.getClassTemplateById(templateId);

    Review review = new Review();
    review.setStudent(student);
    review.setClassTemplate(template);
    review.setRating(rating);
    review.setComment(comment);

    reviewService.createReview(review);
    return "redirect:/student/classes/" + templateId;
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/signin";
  }
}

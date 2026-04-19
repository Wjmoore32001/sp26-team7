package edu.UNCG.sp26team7.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.UNCG.sp26team7.service.ClassSessionService;
import edu.UNCG.sp26team7.service.ClassTemplateService;
import edu.UNCG.sp26team7.service.InstructorService;
import edu.UNCG.sp26team7.entity.ClassSession;
import edu.UNCG.sp26team7.entity.ClassTemplate;
import edu.UNCG.sp26team7.entity.Instructor;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/instructor")
public class InstructorUiController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private ClassTemplateService classTemplateService;

    @Autowired
    private ClassSessionService classSessionService;

    @GetMapping("/profile")
    public String getProfile(HttpSession session, Model model) {
        Long instructorId = (Long) session.getAttribute("instructorId");

        if (instructorId == null) {
            return "redirect:/signin";
        }

        Instructor instructor = instructorService.getInstructorById(instructorId);
        model.addAttribute("instructor", instructor);

        return "instructor/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(HttpSession session, Model model) {
        Long instructorId = (Long) session.getAttribute("instructorId");

        if (instructorId == null) {
            return "redirect:/signin";
        }

        Instructor instructor = instructorService.getInstructorById(instructorId);
        model.addAttribute("instructor", instructor);

        return "instructor/edit-profile";
    }

    @PostMapping("/profile")
    public String updateProfile(HttpSession session, @RequestParam String name, @RequestParam String email,
            @RequestParam(required = false) String password, @RequestParam(required = false) String bio) {
        Long instructorId = (Long) session.getAttribute("instructorId");

        if (instructorId == null) {
            return "redirect:/signin";
        }

        Instructor updatedInstructor = new Instructor();
        updatedInstructor.setName(name);
        updatedInstructor.setEmail(email);
        updatedInstructor.setBio(bio);

        if (password != null && !password.isEmpty()) {
            updatedInstructor.setPasswordHash(password);
        }

        instructorService.updateInstructor(instructorId, updatedInstructor);
        return "redirect:/instructor/profile?success";
    }

    @GetMapping("/classes")
    public String getMyClasses(HttpSession session, Model model) {
        Long instructorId = (Long) session.getAttribute("instructorId");
        if (instructorId == null) {
            return "redirect:/signin";
        }

        List<ClassTemplate> templates = classTemplateService.getInstructorTemplates(instructorId);
        model.addAttribute("templates", templates);

        List<ClassSession> sessions = classSessionService.getInstructorSessions(instructorId);
        model.addAttribute("sessions", sessions);
        return "instructor/my-classes";
    }

    @GetMapping("/classes/new")
    public String newClassForm(HttpSession session, Model model) {
        Long instructorId = (Long) session.getAttribute("instructorId");
        if (instructorId == null) {
            return "redirect:/signin";
        }

        model.addAttribute("ClassTemplate", new ClassTemplate());
        return "instructor/create-class";
    }

    @PostMapping("/classes/new")
    public String createClassTemplate(HttpSession session, ClassTemplate classTemplate) {
        Long instructorId = (Long) session.getAttribute("instructorId");
        if (instructorId == null) {
            return "redirect:/signin";
        }

        Instructor instructor = instructorService.getInstructorById(instructorId);
        classTemplate.setInstructor(instructor);

        classTemplateService.createClassTemplate(classTemplate);
        return "redirect:/instructor/classes?created";
    }

    @GetMapping("/classes/{templateId}/schedule")
    public String scheduleClassForm(HttpSession session, @PathVariable Long templateId, Model model) {
        Long instructorId = (Long) session.getAttribute("instructorId");
        if (instructorId == null) {
            return "redirect:/signin";
        }

        ClassTemplate template = classTemplateService.getClassTemplateById(templateId);
        if(template == null || !template.getInstructor().getUserId().equals(instructorId)) {
            return "redirect:/instructor/classes";
        }

        model.addAttribute("template", template);
        model.addAttribute("classSession", new ClassSession());

        return "instructor/schedule-class";
    }

    @PostMapping("/classes/{templateId}/schedule")
    public String scheduleClass(HttpSession session, @PathVariable Long templateId, @RequestParam String date, @RequestParam String time) {
        Long instructorId = (Long) session.getAttribute("instructorId");
        if (instructorId == null) {
            return "redirect:/signin";
        }

        ClassTemplate template = classTemplateService.getClassTemplateById(templateId);
        if(template == null || !template.getInstructor().getUserId().equals(instructorId)) {
            return "redirect:/instructor/classes";
        }

        ClassSession sessionEntity = new ClassSession();
        sessionEntity.setClassTemplate(template);
        sessionEntity.setScheduledAt(java.time.LocalDateTime.parse(date + "T" + time));

        classSessionService.createClassSession(sessionEntity);
        return "redirect:/instructor/classes?scheduled";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

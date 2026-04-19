package edu.UNCG.sp26team7.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.UNCG.sp26team7.service.InstructorService;
import edu.UNCG.sp26team7.entity.Instructor;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/instructor")
public class InstructorUiController {

    @Autowired
    private InstructorService instructorService;

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
}

package edu.UNCG.sp26team7.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.UNCG.sp26team7.entity.Instructor;
import edu.UNCG.sp26team7.service.InstructorService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AppUiController {

    @Autowired
    private InstructorService instructorService;
    
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        try {
            Instructor instructor = instructorService.authenticate(email, password);
            session.setAttribute("instructorId", instructor.getUserId());
            return "redirect:/instructor/profile";
        } catch (Exception e) {
            return "redirect:/signin?error";
        }
    }
}

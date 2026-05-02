package edu.UNCG.sp26team7.mvc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class SessionApiController {

  @GetMapping("/api/session-user")
  public Map<String, Object> getSessionUser(HttpSession session) {
    Map<String, Object> response = new HashMap<>();

    Long studentId = (Long) session.getAttribute("studentId");
    Long instructorId = (Long) session.getAttribute("instructorId");
    String role = (String) session.getAttribute("role");

    response.put("loggedIn", studentId != null || instructorId != null);
    response.put("role", role);
    response.put("studentId", studentId);
    response.put("instructorId", instructorId);

    return response;
  }
}

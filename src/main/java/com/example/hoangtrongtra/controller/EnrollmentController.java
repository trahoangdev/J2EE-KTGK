package com.example.hoangtrongtra.controller;

import com.example.hoangtrongtra.service.EnrollmentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enroll")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{courseId}")
    public String enroll(@PathVariable Long courseId, Authentication authentication) {
        try {
            enrollmentService.enroll(authentication.getName(), courseId);
            return "redirect:/home?enrolled";
        } catch (IllegalArgumentException ex) {
            return "redirect:/home?enrollError";
        }
    }

    @GetMapping("/my-courses")
    public String myCourses(Authentication authentication, Model model) {
        model.addAttribute("enrollments", enrollmentService.getMyEnrollments(authentication.getName()));
        return "my-courses";
    }
}

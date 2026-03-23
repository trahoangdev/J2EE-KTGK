package com.example.hoangtrongtra.controller;

import com.example.hoangtrongtra.model.Course;
import com.example.hoangtrongtra.service.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final CourseService courseService;

    @Value("${app.pagination.page-size:5}")
    private int pageSize;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping({"/", "/home", "/courses"})
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) String keyword,
                       Model model) {

        Page<Course> coursePage = courseService.getCourses(keyword, page, pageSize);

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("keyword", keyword == null ? "" : keyword);

        return "home";
    }
}

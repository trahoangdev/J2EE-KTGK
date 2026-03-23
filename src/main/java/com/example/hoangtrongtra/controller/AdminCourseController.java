package com.example.hoangtrongtra.controller;

import com.example.hoangtrongtra.dto.CourseForm;
import com.example.hoangtrongtra.model.Course;
import com.example.hoangtrongtra.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final CourseService courseService;

    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.getAllCoursesForAdmin());
        return "admin/course-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("courseForm", new CourseForm());
        model.addAttribute("categories", courseService.getAllCategories());
        model.addAttribute("isEdit", false);
        return "admin/course-form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("courseForm") CourseForm courseForm,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", courseService.getAllCategories());
            model.addAttribute("isEdit", false);
            return "admin/course-form";
        }

        courseService.createCourse(courseForm);
        return "redirect:/admin/courses?created";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("courseForm", courseService.mapToForm(course));
        model.addAttribute("categories", courseService.getAllCategories());
        model.addAttribute("isEdit", true);
        model.addAttribute("courseId", id);
        return "admin/course-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("courseForm") CourseForm courseForm,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", courseService.getAllCategories());
            model.addAttribute("isEdit", true);
            model.addAttribute("courseId", id);
            return "admin/course-form";
        }

        courseService.updateCourse(id, courseForm);
        return "redirect:/admin/courses?updated";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses?deleted";
    }
}

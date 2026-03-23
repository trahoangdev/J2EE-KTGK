package com.example.hoangtrongtra.service;

import com.example.hoangtrongtra.dto.CourseForm;
import com.example.hoangtrongtra.model.Category;
import com.example.hoangtrongtra.model.Course;
import com.example.hoangtrongtra.repository.CategoryRepository;
import com.example.hoangtrongtra.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public CourseService(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<Course> getCourses(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        if (keyword == null || keyword.isBlank()) {
            return courseRepository.findAll(pageable);
        }
        return courseRepository.findByNameContainingIgnoreCase(keyword.trim(), pageable);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<Course> getAllCoursesForAdmin() {
        return courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy học phần"));
    }

    @Transactional
    public void createCourse(CourseForm form) {
        Course course = new Course();
        applyForm(course, form);
        courseRepository.save(course);
    }

    @Transactional
    public void updateCourse(Long id, CourseForm form) {
        Course course = findById(id);
        applyForm(course, form);
        courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public CourseForm mapToForm(Course course) {
        CourseForm form = new CourseForm();
        form.setName(course.getName());
        form.setImage(course.getImage());
        form.setCredits(course.getCredits());
        form.setLecturer(course.getLecturer());
        form.setCategoryId(course.getCategory() != null ? course.getCategory().getId() : null);
        return form;
    }

    private void applyForm(Course course, CourseForm form) {
        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));

        course.setName(form.getName());
        course.setImage(form.getImage());
        course.setCredits(form.getCredits());
        course.setLecturer(form.getLecturer());
        course.setCategory(category);
    }
}

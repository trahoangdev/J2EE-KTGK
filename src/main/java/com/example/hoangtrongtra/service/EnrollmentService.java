package com.example.hoangtrongtra.service;

import com.example.hoangtrongtra.model.Course;
import com.example.hoangtrongtra.model.Enrollment;
import com.example.hoangtrongtra.model.Student;
import com.example.hoangtrongtra.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             CourseService courseService,
                             StudentService studentService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Transactional
    public void enroll(String principalName, Long courseId) {
        Student student = studentService.findByPrincipalName(principalName);
        Course course = courseService.findById(courseId);

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("Bạn đã đăng ký học phần này rồi");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDate.now());
        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getMyEnrollments(String principalName) {
        Student student = studentService.findByPrincipalName(principalName);
        return enrollmentRepository.findByStudentOrderByEnrollDateDesc(student);
    }
}

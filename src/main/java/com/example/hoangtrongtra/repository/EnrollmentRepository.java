package com.example.hoangtrongtra.repository;

import com.example.hoangtrongtra.model.Course;
import com.example.hoangtrongtra.model.Enrollment;
import com.example.hoangtrongtra.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndCourse(Student student, Course course);

    long deleteByStudentAndCourse(Student student, Course course);

    List<Enrollment> findByStudentOrderByEnrollDateDesc(Student student);
}

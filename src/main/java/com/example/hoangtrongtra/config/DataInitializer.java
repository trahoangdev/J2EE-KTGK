package com.example.hoangtrongtra.config;

import com.example.hoangtrongtra.model.Category;
import com.example.hoangtrongtra.model.Course;
import com.example.hoangtrongtra.model.Role;
import com.example.hoangtrongtra.model.Student;
import com.example.hoangtrongtra.repository.CategoryRepository;
import com.example.hoangtrongtra.repository.CourseRepository;
import com.example.hoangtrongtra.repository.RoleRepository;
import com.example.hoangtrongtra.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           StudentRepository studentRepository,
                           CategoryRepository categoryRepository,
                           CourseRepository courseRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setName("ADMIN");
            return roleRepository.save(role);
        });

        Role studentRole = roleRepository.findByName("STUDENT").orElseGet(() -> {
            Role role = new Role();
            role.setName("STUDENT");
            return roleRepository.save(role);
        });

        if (!studentRepository.existsByUsername("admin")) {
            Student admin = new Student();
            admin.setUsername("admin");
            admin.setEmail("admin@uniflow.local");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.getRoles().add(adminRole);
            studentRepository.save(admin);
        }

        if (!studentRepository.existsByUsername("student")) {
            Student student = new Student();
            student.setUsername("student");
            student.setEmail("student@uniflow.local");
            student.setPassword(passwordEncoder.encode("student123"));
            student.getRoles().add(studentRole);
            studentRepository.save(student);
        }

        Category software = getOrCreateCategory("Software Engineering");
        Category ai = getOrCreateCategory("Artificial Intelligence");
        Category business = getOrCreateCategory("Business");

        seedCourseIfMissing("Lập trình Spring Boot", 3, "TS. Nguyễn Văn A", "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=900&q=80", software);
        seedCourseIfMissing("Cơ sở dữ liệu nâng cao", 3, "ThS. Trần Minh T", "https://images.unsplash.com/photo-1484417894907-623942c8ee29?auto=format&fit=crop&w=900&q=80", software);
        seedCourseIfMissing("Machine Learning cơ bản", 4, "TS. Lê Kim D", "https://images.unsplash.com/photo-1526378800651-c32d170fe6f8?auto=format&fit=crop&w=900&q=80", ai);
        seedCourseIfMissing("Data Visualization", 2, "ThS. Phạm Hồng N", "https://images.unsplash.com/photo-1551288049-bebda4e38f71?auto=format&fit=crop&w=900&q=80", ai);
        seedCourseIfMissing("Quản trị dự án CNTT", 2, "TS. Võ Anh P", "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?auto=format&fit=crop&w=900&q=80", business);
        seedCourseIfMissing("Digital Marketing", 3, "ThS. Nguyễn Hạnh M", "https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&w=900&q=80", business);
        seedCourseIfMissing("Kiến trúc phần mềm", 3, "TS. Bùi Quang V", "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?auto=format&fit=crop&w=900&q=80", software);

        seedCourseIfMissing("Lập trình Java nâng cao", 3, "TS. Hoàng Minh Q", "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=900&q=80", software);
        seedCourseIfMissing("Thiết kế giao diện người dùng", 2, "ThS. Phạm Thu H", "https://images.unsplash.com/photo-1507238691740-187a5b1d37b8?auto=format&fit=crop&w=900&q=80", software);
        seedCourseIfMissing("Phát triển ứng dụng Web", 3, "TS. Vũ Trọng L", "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?auto=format&fit=crop&w=900&q=80", software);
        seedCourseIfMissing("Deep Learning nhập môn", 4, "TS. Nguyễn Thái K", "https://images.unsplash.com/photo-1507146426996-ef05306b995a?auto=format&fit=crop&w=900&q=80", ai);
        seedCourseIfMissing("Xử lý ngôn ngữ tự nhiên", 3, "ThS. Lê Hữu T", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&w=900&q=80", ai);
        seedCourseIfMissing("Computer Vision", 3, "TS. Trần Quỳnh D", "https://images.unsplash.com/photo-1580894908361-967195033215?auto=format&fit=crop&w=900&q=80", ai);
        seedCourseIfMissing("Phân tích dữ liệu kinh doanh", 3, "ThS. Nguyễn Hà Y", "https://images.unsplash.com/photo-1554224154-26032ffc0d07?auto=format&fit=crop&w=900&q=80", business);
        seedCourseIfMissing("Khởi nghiệp đổi mới sáng tạo", 2, "TS. Đặng Anh S", "https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&w=900&q=80", business);
        seedCourseIfMissing("Tài chính cho nhà quản lý", 2, "ThS. Võ Thanh B", "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?auto=format&fit=crop&w=900&q=80", business);
        seedCourseIfMissing("Chiến lược kinh doanh số", 3, "TS. Phan Gia N", "https://images.unsplash.com/photo-1552581234-26160f608093?auto=format&fit=crop&w=900&q=80", business);
    }

    private Category getOrCreateCategory(String name) {
        return categoryRepository.findByName(name).orElseGet(() -> {
            Category category = new Category();
            category.setName(name);
            return categoryRepository.save(category);
        });
    }

    private void seedCourseIfMissing(String name, Integer credits, String lecturer, String image, Category category) {
        if (courseRepository.existsByNameIgnoreCase(name)) {
            return;
        }
        courseRepository.save(buildCourse(name, credits, lecturer, image, category));
    }

    private Course buildCourse(String name, Integer credits, String lecturer, String image, Category category) {
        Course course = new Course();
        course.setName(name);
        course.setCredits(credits);
        course.setLecturer(lecturer);
        course.setImage(image);
        course.setCategory(category);
        return course;
    }
}

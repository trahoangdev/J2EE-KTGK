package com.example.hoangtrongtra.service;

import com.example.hoangtrongtra.dto.RegisterRequest;
import com.example.hoangtrongtra.model.Role;
import com.example.hoangtrongtra.model.Student;
import com.example.hoangtrongtra.repository.RoleRepository;
import com.example.hoangtrongtra.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerStudent(RegisterRequest request) {
        if (studentRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseThrow(() -> new IllegalStateException("Chưa có role STUDENT"));

        Student student = new Student();
        student.setUsername(request.getUsername());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setEmail(request.getEmail());
        student.getRoles().add(studentRole);

        studentRepository.save(student);
    }

    public Student findByPrincipalName(String principalName) {
        return studentRepository.findByUsername(principalName)
                .or(() -> studentRepository.findByEmail(principalName))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên"));
    }
}

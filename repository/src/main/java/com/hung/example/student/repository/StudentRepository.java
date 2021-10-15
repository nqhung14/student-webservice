package com.hung.example.student.repository;

import com.hung.example.domain.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> getStudentByName(String name);

    void insertStudent(Student student);
}

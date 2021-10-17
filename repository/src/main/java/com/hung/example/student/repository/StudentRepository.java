package com.hung.example.student.repository;

import com.hung.example.domain.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentRepository {
    List<Student> getStudentByName(String name) throws SQLException;

    void insertStudent(Student student) throws SQLException;

    Student getStudentById(String id) throws SQLException;

    void deleteStudent(String id) throws SQLException;

    void updateStudent(Student student) throws SQLException;

    void partialUpdateStudent(Student student) throws SQLException;


}

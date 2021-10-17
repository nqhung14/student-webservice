package com.hung.example.student.repository;

import com.hung.example.domain.Student;

import java.sql.SQLException;
import java.util.List;

public class MongoStudentRepository implements StudentRepository {
    @Override
    public List<Student> getStudentByName(String name) {
        return null;
    }

    @Override
    public void insertStudent(Student student) {

    }

    @Override
    public Student getStudentById(String id) throws SQLException {
        return null;
    }

    @Override
    public void deleteStudent(String id) throws SQLException {

    }

    @Override
    public void updateStudent(Student student) throws SQLException {

    }

    @Override
    public void partialUpdateStudent(Student student) throws SQLException {

    }


}

package com.hung.example.student.repository;

import com.hung.example.domain.Student;

import java.util.List;

public class MongoStudentRepository implements StudentRepository {
    @Override
    public List<Student> getStudentByName(String name) {
        return null;
    }

    @Override
    public void insertStudent(Student student) {

    }
}

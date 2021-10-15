package com.hung.example.webservice.controller;

import com.hung.example.domain.Student;
import com.hung.example.student.repository.MongoStudentRepository;
import com.hung.example.student.repository.StudentRepository;
import com.hung.example.student.repository.SQLStudentRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequestMapping("/student/api")
@RestController
public class StudentController {

    @RequestMapping(
            value = "/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Student> getStudent(@PathVariable String name) throws SQLException {
        StudentRepository repository = new MongoStudentRepository();
        return repository.getStudentByName(name);
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void insertStudent(@RequestBody Student student) throws SQLException {
        SQLStudentRepository.insertStudent(student);
    }
}

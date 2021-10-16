package com.hung.example.webservice.controller;

import com.hung.example.domain.Student;
import com.hung.example.domain.constant.ProjectDefine;
import com.hung.example.student.repository.SQLStudentRepository;
import com.hung.example.student.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.regex.Pattern;

@RequestMapping("/student/api")
@RestController
public class StudentController {

    StudentRepository studentRepository;

    public StudentController() {
        studentRepository = new SQLStudentRepository();
    }

    @RequestMapping(
            value = "/name/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Student> getStudent(@PathVariable String name, @RequestHeader("Token") String token) {
        List<Student> result = null;
        try {
            if (ObjectUtils.isEmpty(token) || !token.equals(ProjectDefine.token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }
            result = studentRepository.getStudentByName(name);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR: " + e.getMessage());
        }
        return result;
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Student getStudentById(@PathVariable String id, @RequestHeader("Token") String token) {
        Student result = null;
        try {
            if (!isValidUUID(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Id");
            }
            if (ObjectUtils.isEmpty(token) || !token.equals(ProjectDefine.token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }
            result = studentRepository.getStudentById(id);
            if (ObjectUtils.isEmpty(result)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR: " + e.getMessage());
        }
        return result;
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void insertStudent(@RequestBody Student student, @RequestHeader("Token") String token) {
        try {
            if (token.isEmpty() || !token.equals(ProjectDefine.token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }
            //validate student information
            if (student.getName().isEmpty() || student.getGender().isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name or Gender is missing");

            studentRepository.insertStudent(student);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR: " + e.getMessage());
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    public void deleteStudent(@PathVariable String id, @RequestHeader("Token") String token) {
        try {
            if (token.isEmpty() || !token.equals(ProjectDefine.token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }
            if (!isValidUUID(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Id");
            }
            studentRepository.deleteStudent(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR: " + e.getMessage());
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateStudent(@PathVariable String id, @RequestBody Student student, @RequestHeader("Token") String token) {
        try {
            if (token.isEmpty() || !token.equals(ProjectDefine.token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }
            if (!isValidUUID(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Id");
            }
            if(!student.getId().isEmpty() &&  !student.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not matched");
            }
            studentRepository.updateStudent(student);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR: " + e.getMessage());
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void partialUpdateStudent(@PathVariable String id, @RequestBody Student student, @RequestHeader("Token") String token) {
        try {
            if (token.isEmpty() || !token.equals(ProjectDefine.token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }
            if (!isValidUUID(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Id");
            }
            if(!student.getId().isEmpty() &&  !student.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not matched");
            }
            studentRepository.partialUpdateStudent(student);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR: " + e.getMessage());
        }
    }

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public static boolean isValidUUID(String str) {
        if (str == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(str).matches();
    }
}

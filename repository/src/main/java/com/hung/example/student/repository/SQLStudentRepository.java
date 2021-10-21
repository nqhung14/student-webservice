package com.hung.example.student.repository;

import com.hung.example.domain.Student;
import com.hung.example.domain.constant.DBDefinition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static com.hung.example.domain.constant.DBDefinition.*;

public class SQLStudentRepository implements StudentRepository {
    Connection conn;

    public SQLStudentRepository() {
        conn = connectToDataBase(DB_URL, DB_NAME, USER, PASSWORD);
    }

    private static Connection connectToDataBase(String url, String dataBase, String username, String pass) {

        Connection conn = null;

        try {
            Class.forName(DBDefinition.SQL_DRIVER);
            String dbURL = String.format("%s;databaseName=%s", url, dataBase);
            String user = username;
            String passWord = pass;
            conn = DriverManager.getConnection(dbURL, user, passWord);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Student inputStudent() {
        Student inputStudent = new Student();
        Scanner input = new Scanner(System.in);
        System.out.println("Input ID: ");
        inputStudent.setId(input.nextLine());
        System.out.println("Input Name: ");
        inputStudent.setName(input.nextLine());
        System.out.println("Input Gender: ");
        inputStudent.setGender(input.nextLine());
        System.out.println("Input BirthYear: ");
        inputStudent.setBirthYear(input.nextLine());
        System.out.println("Input Address: ");
        inputStudent.setAddress(input.nextLine());

        return inputStudent;
    }

    public void updateStudent(Student student, String name) throws SQLException {

        Statement stmt = conn.createStatement();
        String query = "Update dbo.Student set ID = '%s', NameStudent = '%s', Gender = '%s', BirthYear = '%s', HouseAddress = '%s' Where NameStudent = '%s'";
        stmt.execute(String.format(query, student.getId(), student.getName(), student.getGender(), student.getBirthYear(), student.getAddress(), name));
    }

    @Override
    public List<Student> getStudentByName(String name) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM dbo.Student WHERE NameStudent = '%s'";
        ResultSet rs = stmt.executeQuery(String.format(query, name));
        Student student = new Student();
        List<Student> listStudent = new ArrayList<>();
        while (rs.next()) {
            student.setId(rs.getString(1));
            student.setName(rs.getString(2));
            student.setGender(rs.getString(3));
            student.setBirthYear(rs.getString(4));
            student.setAddress(rs.getString(5));

            listStudent.add(student);
        }
        return listStudent;
    }

    public void insertStudent(Student student) throws SQLException {
        Statement stmt = conn.createStatement();
        student.setId(UUID.randomUUID().toString());
        String query = "Insert into dbo.Student (ID, NameStudent, Gender, BirthYear, HouseAddress) Values ('%s', '%s', '%s', '%s', '%s')";
        stmt.execute(String.format(query, student.getId(), student.getName(), student.getGender(), student.getBirthYear(), student.getAddress()));
    }

    @Override
    public Student getStudentById(String id) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM dbo.Student WHERE id = '%s'";
        ResultSet rs = stmt.executeQuery(String.format(query, id));
        Student student = new Student();
        while (rs.next()) {
            student.setId(rs.getString(1));
            student.setName(rs.getString(2));
            student.setGender(rs.getString(3));
            student.setBirthYear(rs.getString(4));
            student.setAddress(rs.getString(5));
        }
        return student;
    }

    @Override
    public void deleteStudent(String id) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "delete * from dbo.Student where id = '%s'";
        stmt.executeQuery(String.format(query, id));
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "Update dbo.Student set  NameStudent = '%s', Gender = '%s', BirthYear = '%s', HouseAddress = '%s' Where id = '%s'";
        stmt.execute(String.format(query, student.getName(), student.getGender(), student.getBirthYear(), student.getAddress(), student.getId()));
    }

    @Override
    public void partialUpdateStudent(Student student) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "UPDATE dbo.Student " +
                "SET" +
                "NameStudent = coalesce (NameStudent,%s)," +
                "Gender = coalesce (Gender,%s)," +
                "BirthYear = coalesce (BirthYear,%s)" +
                "HouseAddress = coalesce (HouseAddress,%s)" +
                "where id = %s " +
                "and " +
                "coalesce(NameStudent, Gender, BirthYear, HouseAddress) is not null";
        stmt.execute(String.format(query, student.getName(), student.getGender(), student.getBirthYear(), student.getAddress(), student.getId()));
    }
}

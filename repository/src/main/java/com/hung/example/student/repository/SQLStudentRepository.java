package com.hung.example.student.repository;

import com.hung.example.domain.Student;
import com.hung.example.domain.constant.DBDefinition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.hung.example.domain.constant.DBDefinition.*;

public class SQLStudentRepository {


    public static Connection connectToDataBase(String url, String dataBase, String username, String pass) {

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
        inputStudent.setID(input.nextLine());
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

    public static List<Student> getStudents(String name) throws SQLException {

        Connection conn = connectToDataBase(DB_URL, DB_NAME, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM dbo.Student WHERE NameStudent = '%s'";
        ResultSet rs = stmt.executeQuery(String.format(query, name));
        Student student = new Student();
        List<Student> listStudent = new ArrayList<>();
        while (rs.next()) {
            student.setID(rs.getString(1));
            student.setName(rs.getString(2));
            student.setGender(rs.getString(3));
            student.setBirthYear(rs.getString(4));
            student.setAddress(rs.getString(5));

            listStudent.add(student);
        }
        return listStudent;
    }

    public static void deleteStudentByName(String name) throws SQLException {

        Connection conn = connectToDataBase(DB_URL, DB_NAME, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "delete * from dbo.Student where NameStudent = '%s'";
        stmt.executeQuery(String.format(query, name));
    }

    public static void updateStudent(Student student, String name) throws SQLException {

        Connection conn = connectToDataBase(DB_URL, DB_NAME, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "Update dbo.Student set ID = '%s', NameStudent = '%s', Gender = '%s', BirthYear = '%s', Adress = '%s' Where NameStudent = '%s'";
        stmt.execute(String.format(query, student.getID(), student.getName(), student.getGender(), student.getBirthYear(), student.getAddress(), name));
    }

    public static void insertStudent(Student student) throws SQLException {

        Connection conn = connectToDataBase(DB_URL, DB_NAME, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "Insert into dbo.Student (ID, NameStudent, Gender, BirthYear, Adress) Values ('%s', '%s', '%s', '%s', '%s')";
        stmt.execute(String.format(query, student.getID(), student.getName(), student.getGender(), student.getBirthYear(), student.getAddress()));
    }
}

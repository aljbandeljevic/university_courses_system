package Validations;

import DatabaseObjects.StudentDatabase;
import DatabaseObjects.JObjects.Student;

import javax.swing.*;
import java.util.List;

public class StudentValidations {
    private StudentDatabase studentDAO;

    public StudentValidations() {
        studentDAO = new StudentDatabase();
    }

    public boolean validate(String username, String password) {
        boolean valid = true;

        if (username.length() == 0) {
            JOptionPane.showMessageDialog(null, "Please enter username");
            valid = false;
        } else if (password.length() == 0) {
            JOptionPane.showMessageDialog(null, "Please enter password");
            valid = false;
        } else if (!validateUsername(username)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid username");
            valid = false;

        }

        // no need to validate pass as it is saved in database

        if (!valid) {
            return false;
        }

        return (studentDAO.validateStudentLogin(username, password));
    }

    private boolean validateUsername(String username) {
        return username.matches("^[a-z0-9 ]{3,}$");

    }

    public boolean validateAndUpdateStudent(Student student) {
        boolean valid = true;
        if (!nameValid(student.getFirstName())) {
            JOptionPane.showMessageDialog(null, "Please enter valid first name");
            valid = false;

        } else if (!nameValid(student.getLastName())) {
            JOptionPane.showMessageDialog(null, "Please enter valid last name");
            valid = false;

        } else if (!ageValid(student.getAge())) {
            JOptionPane.showMessageDialog(null, "Please enter valid age");
            valid = false;

        }

        if (!valid) {
            return false;
        }

        // update student
        return studentDAO.updateStudent(student);

    }

    private boolean nameValid(String name) {
        return name.matches("^[A-Z]{1}[a-z]{2,}$");
    }

    private boolean ageValid(int age) {
        return age >= 18 && age <= 120;
    }

    private boolean validID(int id) {
        return id >= 0;
    }

    public int getStudentIDByUsername(String theUsername) {
        if (validateUsername(theUsername)) {
            return studentDAO.getStudentIDByUsername(theUsername);
        }

        JOptionPane.showMessageDialog(null, "Username invalid");
        return -1;
    }

    public Student getStudentByUsername(String username) {
        if (validateUsername(username)) {
            return studentDAO.getStudentByUsername(username);
        }

        JOptionPane.showMessageDialog(null, "Username invalid");
        return null;
    }

    public Student getStudentByID(Integer studentID) {
        if (validID(studentID)) {
            return studentDAO.getStudentByID(studentID);

        }

        JOptionPane.showMessageDialog(null, "Student ID invalid");
        return null;
    }

    public List<Student> getAllStudents(String theUsername) {
        if (validateUsername(theUsername)) {
            return studentDAO.getAllStudents(theUsername);
        }

        JOptionPane.showMessageDialog(null, "Username invalid");
        return null;

    }

    public boolean addIfNotExists(Student student) {
        // check if username already present
        Student theStudent = studentDAO.getStudentByUsername(student.getUsername());

        if (theStudent == null) {
            return studentDAO.addStudent(student);
        }

        // as already present
        return true;
    }

    public void deleteAllStudents() {
        studentDAO.deleteAllStudents();
    }
}

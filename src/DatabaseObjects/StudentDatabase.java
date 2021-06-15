package DatabaseObjects;

import DatabaseObjects.JObjects.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
    public boolean validateStudentLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from student where username = '" + username + "'" +
                    "and password = '" + password + "'");

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateStudent(Student student) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            return statement.executeUpdate("update student set first_name = '" +
                    student.getFirstName() + "' , last_name = '" + student.getLastName()
                    + "' , age = " + student.getAge() + " where username = '" + student.getUsername() + "'") != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getStudentIDByUsername(String theUsername) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select id from student where username = '" + theUsername + "'");

            if (resultSet.next()) {
                return resultSet.getInt("id");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Student getStudentByID(int studentID) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from student where id = " + studentID);

            if (resultSet.next()) {
                Student student = new Student();
                student.setId(studentID);
                student.setUsername(resultSet.getString("username"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setPassword(resultSet.getString("password"));
                student.setAge(resultSet.getInt("age"));
                student.setProgramID(resultSet.getInt("program_id"));

                return student;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Student getStudentByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from student where username = '" + username + "'");

            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setUsername(resultSet.getString("username"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setPassword(resultSet.getString("password"));
                student.setAge(resultSet.getInt("age"));
                student.setProgramID(resultSet.getInt("program_id"));

                return student;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> getAllStudents(String theUsername) {
        List<Student> students;
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            students = new ArrayList<>();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from student where username != '" + theUsername + "'");

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setUsername(resultSet.getString("username"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setPassword(resultSet.getString("password"));
                student.setAge(resultSet.getInt("age"));
                student.setProgramID(resultSet.getInt("program_id"));

                students.add(student);
            }

            return students;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addStudent(Student student) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "insert into student(first_name, last_name, age, username, password, program_id) values( '" + student.getFirstName() + "', '" + student.getLastName() +
                    "', " + student.getAge() + ", '" + student.getUsername() + "', '" + student.getPassword() + "', " +
                    student.getProgramID() + ")";

            int updates = statement.executeUpdate(sql);

            return updates == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteAllStudents() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "delete from student";

            int deleted = statement.executeUpdate(sql);

            return deleted != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
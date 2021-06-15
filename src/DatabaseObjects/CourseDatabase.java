package DatabaseObjects;

import DatabaseObjects.JObjects.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDatabase {
    public List<Course> getAllCourses() {
        List<Course> courses = null;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select course.id, course_name, course_timings, course_lecture_hall, s.type as semester_type, program_name, t.type as course_type\n" +
                    "from university_courses_system.course\n" +
                    "       inner join program p on course.program_id = p.id\n" +
                    "       inner join semester s on course.semester_id = s.id\n" +
                    "       inner join course_type t on course.course_type_id = t.id;");

            courses = new ArrayList<>();

            while (result.next()) {
                courses.add(new Course(result.getInt("id"), result.getString("course_name"), result.getString("course_timings"),
                        result.getString("course_lecture_hall"), result.getString("semester_type"),
                        result.getString("course_type"), result.getString("program_name")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public Course getCourseByID(int courseID) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select course_name, course_timings, course_lecture_hall, s.type as semester_type, program_name, t.type as course_type\n" +
                    "from university_courses_system.course\n" +
                    "       inner join program p on course.program_id = p.id\n" +
                    "       inner join semester s on course.semester_id = s.id\n" +
                    "       inner join course_type t on course.course_type_id = t.id where course.id = " + courseID);

            Course course = null;

            if (result.next()) {
                course = new Course(courseID, result.getString("course_name"), result.getString("course_timings"),
                        result.getString("course_lecture_hall"), result.getString("semester_type"),
                        result.getString("course_type"), result.getString("program_name"));

            }

            return course;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Course> getStudentCourses(int studentID) {
        List<Course> courses = null;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("\n" +
                    "select course_id from course_list where student_id = " + studentID);

            courses = new ArrayList<>();

            while (result.next()) {
                int courseID = result.getInt("course_id");

                Course course = getCourseByID(courseID);

                courses.add(course);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public Course getCourseByName(String courseName) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select course.id as id, course_name, course_timings, course_lecture_hall, s.type as semester_type, program_name, t.type as course_type\n" +
                    "from university_courses_system.course\n" +
                    "       inner join program p on course.program_id = p.id\n" +
                    "       inner join semester s on course.semester_id = s.id\n" +
                    "       inner join course_type t on course.course_type_id = t.id where course.course_name = '" + courseName + "'");

            Course course = null;

            if (result.next()) {
                course = new Course(result.getInt("id"), result.getString("course_name"), result.getString("course_timings"),
                        result.getString("course_lecture_hall"), result.getString("semester_type"),
                        result.getString("course_type"), result.getString("program_name"));

            }

            return course;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public boolean checkIfCourseAlreadyPresent(String courseName) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "select * from course where course_name = '" + courseName + "'";

            ResultSet results = statement.executeQuery(sql);

            return results.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addCourse(Course course) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "select id from program where program_name = '" + course.getProgramName() + "'";

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                int programID = result.getInt("id");

                sql = "insert into course(course_name, course_timings, course_lecture_hall, semester_id," +
                        "course_type_id, program_id) values( '" + course.getCourseName() + "', '" + course.getCourseTime() +
                        "', '" + course.getCourseLectureHall() + "', " + (course.getSemesterType().equals("Fall") ? "1" : "2")
                        + ", " + (course.getCourseType().equals("Major") ? "1" : "2") + ", " +
                        programID + ")";

                int updates = statement.executeUpdate(sql);

                return updates == 1;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

package DatabaseObjects;

import DatabaseObjects.JObjects.CourseListItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseLItemsDatabase {
    public boolean assignCourseToStudent(CourseListItem item) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "insert into course_list values(" + item.getId() + ", " +
                    item.getStudentID() + ", " + item.getCourseID() + ", '" + item.getComments() + "')";

            int update = statement.executeUpdate(sql);

            return update == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean courseAlreadyAssignedToStudent(CourseListItem item) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select id from course_list where course_id = " + item.getCourseID() + " and " +
                    "student_id = " + item.getStudentID());

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean dropCourse(CourseListItem item) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            int deletedRows = statement.executeUpdate("delete from course_list where student_id = " + item.getStudentID()
                    + " and course_id = " + item.getCourseID());

            return deletedRows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Integer> getAllAttendees(int courseID) {
        List<Integer> students = null;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            students = new ArrayList<>();

            Statement statement = connection.createStatement();

            String sql = "select student_id from course_list where course_id = " + courseID;
            //System.out.println(sql);

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                students.add(resultSet.getInt("student_id"));

            }

            return students;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public boolean addCommentsToACourse(CourseListItem item) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            int updatedRows = statement.executeUpdate("update course_list set comments = '" + item.getComments() +
                    "' where course_id = " + item.getCourseID() + " and student_id = " + item.getStudentID());

            return updatedRows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<CourseListItem> getAllCommentsOfCourse(int courseID) {
        List<CourseListItem> courseListItems = null;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            courseListItems = new ArrayList<>();

            Statement statement = connection.createStatement();

            String sql = "select * from course_list where course_id = " + courseID;
            //System.out.println(sql);

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (resultSet.getString("comments").length() == 0) {
                    continue;
                }

                CourseListItem courseListItem = new CourseListItem();
                courseListItem.setId(resultSet.getInt("id"));
                courseListItem.setStudentID(resultSet.getInt("student_id"));
                courseListItem.setCourseID(resultSet.getInt("course_id"));
                courseListItem.setComments(resultSet.getString("comments"));

                courseListItems.add(courseListItem);

            }

            return courseListItems;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseListItems;
    }

    public boolean dropAllStudents(int courseID) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "delete from course_list where course_id = " + courseID;

            int deletedRows = statement.executeUpdate(sql);

            return deletedRows != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean removeAllComments(int courseID) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "update course_list set comments = '' where course_id = " + courseID;

            int deletedRows = statement.executeUpdate(sql);

            return deletedRows != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }
}

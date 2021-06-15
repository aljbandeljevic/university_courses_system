package Validations;

import DatabaseObjects.CourseDatabase;
import DatabaseObjects.JObjects.Course;

import java.util.List;

public class CoursesValidations {
    private CourseDatabase courseDAO;

    public CoursesValidations() {
        courseDAO = new CourseDatabase();
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    public List<Course> getAllStudentCourses(int userID) {
        if (validateID(userID)) {
            return courseDAO.getStudentCourses(userID);
        } else {
            return null;
        }
    }

    private boolean validateID(int id) {
        return id >= 1;
    }

    private boolean validateCourseName(String courseName) {
        return courseName.matches("^[A-Z][A-Za-z0-9]{1,}$");
    }

    public Course getCourseByID(int courseID) {
        if (validateID(courseID)) {
            return courseDAO.getCourseByID(courseID);
        }

        return null;
    }

    public Course getCourseByCourseName(String courseName) {
        if (validateCourseName(courseName)) {
            return courseDAO.getCourseByName(courseName);
        }
        return null;
    }

    public boolean addAllCourses(List<Course> courses) {
        for (Course course : courses) {
            if (!courseDAO.checkIfCourseAlreadyPresent(course.getCourseName())) {
                if (!courseDAO.addCourse(course)) {
                    return false;
                }

            }
        }

        return true;
    }

    public boolean addCourseIfNotPresent(Course course) {
        if (!courseDAO.checkIfCourseAlreadyPresent(course.getCourseName())) {
            if (!courseDAO.addCourse(course)) {
                return false;
            }

        }
        return true;
    }
}

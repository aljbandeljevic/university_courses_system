package Validations;

import DatabaseObjects.CourseDatabase;
import DatabaseObjects.CourseLItemsDatabase;
import DatabaseObjects.IDOfProgramDatabase;
import DatabaseObjects.JObjects.Course;
import DatabaseObjects.JObjects.CourseListItem;

import javax.swing.*;
import java.util.List;

public class CourseListValidations {
    private CourseLItemsDatabase courseListItemDAO;
    private CourseDatabase courseDAO;
    private IDOfProgramDatabase programDAO;

    public CourseListValidations() {
        courseListItemDAO = new CourseLItemsDatabase();
        courseDAO = new CourseDatabase();
        programDAO = new IDOfProgramDatabase();
    }

    private boolean validateID(int id) {
        return id > 0;
    }

    public boolean checkIfCourseAlreadyAssignedToStudent(CourseListItem item) {
        if (courseListItemDAO.courseAlreadyAssignedToStudent(item)) {
            JOptionPane.showMessageDialog(null, "You have already added this course to " +
                    "your courses");
            return true;
        }

        return false;
    }

    public boolean assignCourseToStudent(CourseListItem item, int studentProgramID) {
        if (!validateID(item.getCourseID())) {
            JOptionPane.showMessageDialog(null, "Course ID invalid");
            return false;
        }

        if (!validateID(item.getStudentID())) {
            JOptionPane.showMessageDialog(null, "Student ID invalid");
            return false;
        }

        if (!validateID(studentProgramID)) {
            JOptionPane.showMessageDialog(null, "Program ID invalid");
            return false;
        }

        if (courseListItemDAO.courseAlreadyAssignedToStudent(item)) {
            JOptionPane.showMessageDialog(null, "You have already added this course to " +
                    "your courses");
            return false;
        }

        String courseProgram = courseDAO.getCourseByID(item.getCourseID()).getProgramName();
        int courseProgramID = programDAO.getProgramIDByProgramName(courseProgram);

        if (studentProgramID != courseProgramID) {
            JOptionPane.showMessageDialog(null, "This course does not belong to your program");
            return false;
        }

        courseListItemDAO.assignCourseToStudent(item);
        JOptionPane.showMessageDialog(null, "The course added to my courses successfully");

        return true;
    }

    public boolean dropACourseForStudent(CourseListItem item) {
        if (validateID(item.getStudentID()) && validateID(item.getCourseID())) {
            return courseListItemDAO.dropCourse(item);
        }

        if (!validateID(item.getStudentID())) {
            JOptionPane.showMessageDialog(null, "Please enter valid student ID");
        }

        if (!validateID(item.getCourseID())) {
            JOptionPane.showMessageDialog(null, "Please enter valid course ID");
        }

        return false;
    }

    public List<Integer> getAllAttendees(int courseID) {
        if (validateID(courseID)) {
            return courseListItemDAO.getAllAttendees(courseID);
        }

        JOptionPane.showMessageDialog(null, "Course ID invalid");
        return null;

    }

    public boolean addCommentsToACourse(CourseListItem item) {
        return courseListItemDAO.addCommentsToACourse(item);

    }

    public List<CourseListItem> getAllCommentsOfCourse(int id) {
        if (validateID(id)) {
            return courseListItemDAO.getAllCommentsOfCourse(id);

        }

        JOptionPane.showMessageDialog(null, "Course ID invalid");
        return null;
    }

    public boolean dropAllStudentsForThisCourse(int courseID) {
        return courseListItemDAO.dropAllStudents(courseID);

    }

    public boolean removeAllComments(int courseID) {
        return courseListItemDAO.removeAllComments(courseID);
    }
}

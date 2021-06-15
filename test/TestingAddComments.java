

import Validations.CourseListValidations;
import Validations.CoursesValidations;
import Validations.StudentValidations;
import DatabaseObjects.JObjects.Course;
import DatabaseObjects.JObjects.CourseListItem;
import DatabaseObjects.JObjects.Student;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TestingAddComments {
    private CourseListValidations courseListValidations;
    private CoursesValidations coursesValidations;
    private StudentValidations studentValidations;

    private void initialize() {
        courseListValidations = new CourseListValidations();
        coursesValidations = new CoursesValidations();
        studentValidations = new StudentValidations();
    }

    @Test
    public void addAndViewCommentsOfACourse() {
        initialize();
        Course course1 = new Course("Java", "1:00 PM", "Room 001", "Fall",
                "Minor", "CS");

        // add a course if not present
        coursesValidations.addCourseIfNotPresent(course1);

        // set it's ID
        course1.setId(coursesValidations.getCourseByCourseName(course1.getCourseName()).getId());

        // remove previous comments of this course
        courseListValidations.removeAllComments(course1.getId());

        // add comments through three different users
        Student student1 = new Student("Aljban", "Deljevic", 20, "aljban", "aljban",
                1);

        Student student2 = new Student("Firstt", "Lastt", 20, "first11", "123",
                1);

        Student student3 = new Student("Firsttt", "Lasttt", 20, "first12", "123",
                1);

        // add students
        studentValidations.addIfNotExists(student1);
        studentValidations.addIfNotExists(student2);
        studentValidations.addIfNotExists(student3);

        student1.setId(studentValidations.getStudentIDByUsername(student1.getUsername()));
        student2.setId(studentValidations.getStudentIDByUsername(student2.getUsername()));
        student3.setId(studentValidations.getStudentIDByUsername(student3.getUsername()));

        CourseListItem item1 = new CourseListItem(student1.getId(), course1.getId(), "Sample Comment");
        CourseListItem item2 = new CourseListItem(student2.getId(), course1.getId(), "Sample Comment");
        CourseListItem item3 = new CourseListItem(student3.getId(), course1.getId(), "Sample Comment");

        List<CourseListItem> allComments = new ArrayList<>();
        allComments.add(item1);
        allComments.add(item2);
        allComments.add(item3);

        courseListValidations.assignCourseToStudent(item1, student1.getProgramID());
        courseListValidations.assignCourseToStudent(item2, student2.getProgramID());
        courseListValidations.assignCourseToStudent(item3, student3.getProgramID());

        courseListValidations.addCommentsToACourse(item1);
        courseListValidations.addCommentsToACourse(item2);
        courseListValidations.addCommentsToACourse(item3);

        List<CourseListItem> allCommentsInDB = courseListValidations.getAllCommentsOfCourse(course1.getId());

        try {
            assertTrue(checkIfListsAreEqual(allComments, allCommentsInDB));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkIfListsAreEqual(List<CourseListItem> courseListItems, List<CourseListItem> courseListItems2) {
        if (courseListItems.size() != courseListItems2.size()) {
            return false;
        }

        courseListItems.sort(new Comparator<CourseListItem>() {
            @Override
            public int compare(CourseListItem o1, CourseListItem o2) {
                return Integer.compare(o1.getStudentID(), o2.getStudentID());
            }
        });

        courseListItems2.sort(new Comparator<CourseListItem>() {
            @Override
            public int compare(CourseListItem o1, CourseListItem o2) {
                return Integer.compare(o1.getStudentID(), o2.getStudentID());
            }
        });

        for (int i = 0; i < courseListItems.size(); i++) {
            if (!courseListItems.get(i).getComments().equals(courseListItems2.get(i).getComments())) {
                return false;

            }

        }

        return true;
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestingAddComments.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        System.out.println("Successful: " + result.wasSuccessful());

    }

}

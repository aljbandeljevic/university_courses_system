

import Validations.CourseListValidations;
import Validations.CoursesValidations;
import Validations.ProgramValidations;
import Validations.StudentValidations;
import DatabaseObjects.JObjects.Course;
import DatabaseObjects.JObjects.CourseListItem;
import DatabaseObjects.JObjects.Student;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class TestAllCourses {
    private CoursesValidations coursesValidations;
    private CourseListValidations courseListValidations;
    private ProgramValidations programValidations;
    private StudentValidations studentValidations;

    private void initialize() {
        courseListValidations = new CourseListValidations();
        coursesValidations = new CoursesValidations();
        programValidations = new ProgramValidations();
        studentValidations = new StudentValidations();

    }

    private void addProgram() {
        initialize();

        try {
            assertTrue(programValidations.addProgramIfNotExists("CS"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Course> addAllCourses() {
        addProgram();

        try {
            List<Course> courses = new ArrayList<>();
            Course course1 = new Course(1, "Java Programming", "12:00 PM", "Room 21", "Fall",
                    "Major", "CS");

            Course course2 = new Course(2, "C# Programming", "10:00 AM", "Room 20", "Spring",
                    "Minor", "CS");

            Course course3 = new Course(3, "US History", "11:30 AM", "Room 8", "Fall",
                    "Major", "CS");


            courses.add(course1);
            courses.add(course2);
            courses.add(course3);

            assertTrue(coursesValidations.addAllCourses(courses));

            return courses;

        } catch (Exception e) {
            e.printStackTrace();
            fail("Adding courses threw an exception");
        }

        return null;

    }

    private boolean equals(List<Course> list1, List<Course> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void getAllCourses() {
        List<Course> courses = addAllCourses();

        try {
            assertTrue(equals(courses, coursesValidations.getAllCourses()));

        } catch (Exception e) {
            e.printStackTrace();
            fail("Could not get all courses");
        }
    }

    private Student getAStudent() {
        Student student = new Student();
        student.setFirstName("Example");
        student.setLastName("User");
        student.setUsername("user1");
        student.setPassword("11111");
        student.setProgramID(1);
        student.setAge(20);

        studentValidations.addIfNotExists(student);

        return student;

    }

    @Test
    public void addStudentToMyCourseList() {
        initialize();

        Student student = studentValidations.getStudentByUsername(getAStudent().getUsername());
        Course course = coursesValidations.getCourseByCourseName("Java Prog");

        try {
            assertNotNull(student);
            assertNotNull(course);

            CourseListItem courseListItem = new CourseListItem();
            courseListItem.setStudentID(student.getId());
            courseListItem.setCourseID(course.getId());

            assertTrue(courseListValidations.checkIfCourseAlreadyAssignedToStudent(courseListItem) ||
                    courseListValidations.assignCourseToStudent(courseListItem, student.getProgramID()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAllAttendees() {
        addAllCourses();

        String courseName1 = "C# prog";
        String courseName2 = "US History";

        Course course1 = coursesValidations.getCourseByCourseName(courseName1);
        Course course2 = coursesValidations.getCourseByCourseName(courseName2);

        try {
            assertNotNull(course1);
            assertNull(course2);

            Student student = studentValidations.getStudentByUsername(getAStudent().getUsername());

            List<Integer> allAttendees1 = courseListValidations.getAllAttendees(course1.getId());
            List<Integer> allAttendees2 = courseListValidations.getAllAttendees(-11);

            assertNotNull(allAttendees1);
            assertNull(allAttendees2);

            List<Integer> expectedAttendees = new ArrayList<>();
            expectedAttendees.add(student.getId());

            assertTrue(equalsAttendees(expectedAttendees, allAttendees1));

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error getting attendees");
        }
    }

    private boolean equalsAttendees(List<Integer> expectedAttendees, List<Integer> allAttendees1) {
        if (expectedAttendees.size() != allAttendees1.size()) {
            return false;
        }

        for (int i = 0; i < expectedAttendees.size(); i++) {
            if (!expectedAttendees.get(i).equals(allAttendees1.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestAllCourses.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        System.out.println("Successful: " + result.wasSuccessful());

    }
}

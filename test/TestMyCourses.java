

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

import static junit.framework.TestCase.*;

public class TestMyCourses {
    private List<Course> myCoursesList;
    private StudentValidations studentValidations;
    private CoursesValidations coursesValidations;
    private CourseListValidations courseListValidations;
    private Student student;

    private void initializeAll() {
        myCoursesList = new ArrayList<>();
        courseListValidations = new CourseListValidations();
        studentValidations = new StudentValidations();
        coursesValidations = new CoursesValidations();

        student = new Student();
        student.setFirstName("Example");
        student.setLastName("User");
        student.setUsername("user1");
        student.setPassword("11111");
        student.setProgramID(1);
        student.setAge(20);

        studentValidations.addIfNotExists(student);

        student.setId(studentValidations.getStudentIDByUsername(student.getUsername()));

    }

    private void assignSomeCoursesToStudent() {
        initializeAll();

        // these courses are already present
        Course course1 = coursesValidations.getCourseByCourseName("PF");

        Course course2 = coursesValidations.getCourseByCourseName("OOP");

        Course course3 = coursesValidations.getCourseByCourseName("Java");

        myCoursesList.add(course1);
        myCoursesList.add(course2);
        myCoursesList.add(course3);

        courseListValidations.assignCourseToStudent(new CourseListItem(student.getId(),
                course1.getId(), ""), student.getProgramID());

        courseListValidations.assignCourseToStudent(new CourseListItem(student.getId(),
                course2.getId(), ""), student.getProgramID());

        courseListValidations.assignCourseToStudent(new CourseListItem(student.getId(),
                course3.getId(), ""), student.getProgramID());

    }

    private boolean checkIfListsEqual(List<Course> courses) {
        if (courses.size() != myCoursesList.size()) {
            return false;
        }

        courses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        myCoursesList.sort(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        for (int i = 0; i < courses.size(); i++) {
            if (!courses.get(i).equals(myCoursesList.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void getAllMyCoursesTest() {
        assignSomeCoursesToStudent();

        student.setId(studentValidations.getStudentIDByUsername(student.getUsername()));

        List<Course> studentCourses = coursesValidations.getAllStudentCourses(student.getId());

        try {
            assertTrue(checkIfListsEqual(studentCourses));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Student's courses are not correct");
        }

    }

    @Test
    public void dropACourse() {
        assignSomeCoursesToStudent();
        try {
            // get a course
            Course courseToDrop = myCoursesList.get(1);
            CourseListItem item = new CourseListItem(student.getId(), courseToDrop.getId(), "");

            // drop that course for student
            assertTrue(courseListValidations.dropACourseForStudent(item));

            // remove the dropped course from current list
            myCoursesList.remove(courseToDrop);

            // now get student's current courses
            List<Course> studentCourses = coursesValidations.getAllStudentCourses(student.getId());

            // check if our list and student's current courses are same
            assertTrue(checkIfListsEqual(studentCourses));

            // add invalid course ID to drop
            courseToDrop.setId(-1);
            item.setCourseID(courseToDrop.getId());

            // this course should not be able to drop as "-1" is not valid courseID
            assertFalse(courseListValidations.dropACourseForStudent(item));

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error in dropping course");
        }

    }

    @Test
    public void viewCourseAttendees() {
        initializeAll();
        try {
            // this course is already present in db
            Course course1 = coursesValidations.getCourseByCourseName("PF");

            courseListValidations.dropAllStudentsForThisCourse(course1.getId());

            // create three sample students
            Student student1 = new Student("First", "Last", 20, "first1", "123",
                    1);

            Student student2 = new Student("Firstt", "Lastt", 20, "first11", "123",
                    1);

            Student student3 = new Student("Firsttt", "Lasttt", 20, "first12", "123",
                    1);

            // the students list

            ArrayList<Student> allStudents = new ArrayList<>();
            allStudents.add(student1);
            allStudents.add(student2);
            allStudents.add(student3);

            // add students
            studentValidations.addIfNotExists(student1);
            studentValidations.addIfNotExists(student2);
            studentValidations.addIfNotExists(student3);

            // set student IDs
            student1.setId(studentValidations.getStudentIDByUsername(student1.getUsername()));
            student2.setId(studentValidations.getStudentIDByUsername(student2.getUsername()));
            student3.setId(studentValidations.getStudentIDByUsername(student3.getUsername()));

            // assign this course to these three students
            courseListValidations.assignCourseToStudent(new CourseListItem(student1.getId(),
                    course1.getId(), ""), student1.getProgramID());

            courseListValidations.assignCourseToStudent(new CourseListItem(student2.getId(),
                    course1.getId(), ""), student2.getProgramID());

            courseListValidations.assignCourseToStudent(new CourseListItem(student3.getId(),
                    course1.getId(), ""), student3.getProgramID());

            List<Integer> allAttendees = courseListValidations.getAllAttendees(course1.getId());

            // check if both lists i.e. ours and the list present in db are same
            assertTrue(checkIfListsEqual(allAttendees, allStudents));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfListsEqual(List<Integer> allAttendees, ArrayList<Student> allStudents) {
        if (allAttendees.size() != allStudents.size()) {
            return false;
        }

        allAttendees.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });

        allStudents.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        for (int i = 0; i < allAttendees.size(); i++) {
            if (!allAttendees.get(i).equals(allStudents.get(i).getId())) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestMyCourses.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        System.out.println("Successful: " + result.wasSuccessful());

    }
}

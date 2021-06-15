

import Validations.StudentValidations;
import DatabaseObjects.JObjects.Student;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

public class TestUpdateProfile {
    private Student student;
    private StudentValidations studentValidations;

    private void initializeStudent() {
        student = new Student();
        student.setFirstName("Example");
        student.setLastName("User");
        student.setUsername("user1");
        student.setPassword("11111");
        student.setProgramID(1);
        student.setAge(20);

    }

    @Test
    public void updateStudentTest() {
        initializeStudent();

        try {
            studentValidations = new StudentValidations();

            student.setAge(-1);
            student.setFirstName("first");
            student.setLastName("last");

            assertFalse(studentValidations.validateAndUpdateStudent(student));

            student.setAge(20);
            student.setFirstName("");
            student.setLastName("last");

            assertFalse(studentValidations.validateAndUpdateStudent(student));

            student.setAge(20);
            student.setFirstName("First");
            student.setLastName("");

            assertFalse(studentValidations.validateAndUpdateStudent(student));

            student.setAge(20);
            student.setFirstName("first");
            student.setLastName("last");

            assertFalse(studentValidations.validateAndUpdateStudent(student));

            student.setAge(20);
            student.setFirstName("First");
            student.setLastName("Last");

            assertTrue(studentValidations.validateAndUpdateStudent(student));

        } catch (Exception e) {
            fail("Error in updating student");
        }

    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestUpdateProfile.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        System.out.println("Successful: " + result.wasSuccessful());

    }
}

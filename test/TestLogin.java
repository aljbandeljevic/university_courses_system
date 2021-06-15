

import Validations.StudentValidations;
import DatabaseObjects.JObjects.Student;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

public class TestLogin {
    private Student student;
    private StudentValidations studentValidations;

    private void initializeStudent() {
        student = new Student();
        student.setFirstName("Aljban");
        student.setLastName("Deljevic");
        student.setUsername("aljban");
        student.setPassword("aljban");
        student.setProgramID(1);
        student.setAge(23);

    }

    public void creatingStudent() {
        initializeStudent();

        try {
            studentValidations = new StudentValidations();
            assertTrue(studentValidations.addIfNotExists(student));

        } catch (Exception e) {
            fail("Creating a student threw an exception!");
            e.printStackTrace();

        }

    }

    @Test
    public void validateStudentLogin() {
        creatingStudent();

        try {
            studentValidations = new StudentValidations();
            // username and password empty
            assertFalse(studentValidations.validate("", ""));
            // username empty
            assertFalse(studentValidations.validate("", "aljban"));
            // password empty
            assertFalse(studentValidations.validate("aljban", ""));
            // correct username but wrong password
            assertFalse(studentValidations.validate(student.getUsername(), "123"));
            // correct username and password
            assertTrue(studentValidations.validate(student.getUsername(), student.getPassword()));

        } catch (Exception e) {
            fail("Validating student threw an exception!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestLogin.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        System.out.println("Successful: " + result.wasSuccessful());

    }

}



import Validations.FriendsValidations;
import Validations.StudentValidations;
import DatabaseObjects.JObjects.Student;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TestFriends {
    private FriendsValidations friendsValidations;
    private StudentValidations studentValidations;

    private void initialize() {
        friendsValidations = new FriendsValidations();
        studentValidations = new StudentValidations();

    }

    @Test
    public void testGetAllFriends() {
        initialize();

        // create three students
        Student student1 = new Student("First", "Last", 20, "first1", "123",
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

        try {
            // make them friend to each other
            friendsValidations.addStudentsAsFriends(student1.getId(), student2.getId());
            friendsValidations.addStudentsAsFriends(student2.getId(), student3.getId());
            friendsValidations.addStudentsAsFriends(student3.getId(), student1.getId());

            // get the friends from db to test
            List<Integer> allFriends = friendsValidations.getAllFriends(student1.getId());
            assertTrue(allFriends.contains(student2.getId()) && allFriends.contains(student3.getId()));

            allFriends = friendsValidations.getAllFriends(student2.getId());
            assertTrue(allFriends.contains(student1.getId()) && allFriends.contains(student3.getId()));

            allFriends = friendsValidations.getAllFriends(student3.getId());
            assertTrue(allFriends.contains(student2.getId()) && allFriends.contains(student1.getId()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestFriends.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        System.out.println("Successful: " + result.wasSuccessful());

    }
}

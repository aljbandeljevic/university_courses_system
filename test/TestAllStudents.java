

import Validations.FriendsValidations;
import Validations.StudentValidations;
import DatabaseObjects.JObjects.Student;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TestAllStudents {
    private StudentValidations studentValidations;
    private FriendsValidations friendsValidations;

    private void initialize() {
        studentValidations = new StudentValidations();
        friendsValidations = new FriendsValidations();
    }

    @Test
    public void getAllStudents() {
        initialize();
        // delete all students
        studentValidations.deleteAllStudents();

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

        // let user be student1
        // the other students except student1 are student2 and student3

        List<Student> allStudents = new ArrayList<>();
        allStudents.add(student2);
        allStudents.add(student3);

        List<Student> allStudentsInDB = studentValidations.getAllStudents(student1.getUsername());

        try {
            assertTrue(checkIfListsEqual(allStudents, allStudentsInDB));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfListsEqual(List<Student> allAttendees, List<Student> allStudents) {
        if (allAttendees.size() != allStudents.size()) {
            return false;
        }

        allAttendees.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        allStudents.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        for (int i = 0; i < allAttendees.size(); i++) {
            if (allAttendees.get(i).getId() != allStudents.get(i).getId()) {
                return false;
            }
        }

        return true;
    }

    private boolean checkIfListsAreEqual(List<Integer> allAttendees, List<Integer> allStudents) {
        if (allAttendees.size() != allStudents.size()) {
            return false;
        }

        allAttendees.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });

        allStudents.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });

        for (int i = 0; i < allAttendees.size(); i++) {
            if (allAttendees.get(i) != allStudents.get(i)) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void addAStudentAsFriend() {
        initialize();

        // create three students
        Student student1 = new Student("First", "Last", 20, "first", "111",
                1);

        Student student2 = new Student("Firstttttttt", "Lasttttttt", 20, "firsttt", "11111",
                1);

        Student student3 = new Student("Firsttt", "Lasttt", 20, "first12", "111111",
                1);

        // add students
        studentValidations.addIfNotExists(student1);
        studentValidations.addIfNotExists(student2);
        studentValidations.addIfNotExists(student3);

        student1.setId(studentValidations.getStudentIDByUsername(student1.getUsername()));
        student2.setId(studentValidations.getStudentIDByUsername(student2.getUsername()));
        student3.setId(studentValidations.getStudentIDByUsername(student3.getUsername()));

        try {
            // make student 2 and 3 friends of student 1
            friendsValidations.addStudentsAsFriends(student1.getId(), student2.getId());
            friendsValidations.addStudentsAsFriends(student1.getId(), student3.getId());

            List<Integer> allFriends = friendsValidations.getAllFriends(student1.getId());

            List<Integer> allFriendsHere = new ArrayList<>();
            allFriendsHere.add(student2.getId());
            allFriendsHere.add(student3.getId());

            assertTrue(checkIfListsAreEqual(allFriends, allFriendsHere));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestAllStudents.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        System.out.println("Successful: " + result.wasSuccessful());

    }
}

package Validations;

import DatabaseObjects.FriendDatabase;

import javax.swing.*;
import java.util.List;

public class FriendsValidations {
    private FriendDatabase friendDAO;

    public FriendsValidations() {
        friendDAO = new FriendDatabase();
    }

    private boolean validateID(int id) {
        return id > 0;
    }

    public boolean addStudentsAsFriends(int studentID1, int studentID2) {
        if (!validateID(studentID1)) {
            JOptionPane.showMessageDialog(null, "Student ID 1 invalid");
            return false;

        }

        if (!validateID(studentID2)) {
            JOptionPane.showMessageDialog(null, "Student ID 2 invalid");
            return false;

        }

        if (friendDAO.checkIfStudentsAlreadyFriends(studentID1, studentID2)) {
            JOptionPane.showMessageDialog(null, "You are already friends with this student");
            return false;

        }

        return friendDAO.addStudentsAsFriends(studentID1, studentID2);

    }

    public List<Integer> getAllFriends(int userID) {
        if (validateID(userID)) {
            return friendDAO.getAllFriends(userID);
        }

        JOptionPane.showMessageDialog(null, "User ID invalid");
        return null;

    }
}

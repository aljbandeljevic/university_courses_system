package DatabaseObjects;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendDatabase {
    public boolean addStudentsAsFriends(int student1, int student2) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "insert into friends(student_id_one, student_id_two) values (" + student1 + ", " + student2 + ")" ;

            //System.out.println(sql);

            int inserts = statement.executeUpdate(sql);

            return inserts == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkIfStudentsAlreadyFriends(int studentID1, int studentID2) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "select id from friends where ( student_id_one = " + studentID1 + " and student_id_two = " +
                    studentID2 + " ) or ( student_id_two = " + studentID1 + " and student_id_one = " + studentID2 + " )";

            ResultSet result = statement.executeQuery(sql);

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Integer> getAllFriends(int userID) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "\n" +
                    "select student_id_two as id from friends where student_id_one = " + userID + "\n" +
                    "union\n" +
                    "select student_id_one as id from friends where student_id_two = " + userID ;

            ResultSet result = statement.executeQuery(sql);

            List<Integer> friendsIds = new ArrayList<>();

            while (result.next()) {
                friendsIds.add(result.getInt("id"));
                //sql = "select * from student where id = " + result.getInt("id");

            }

            return friendsIds;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

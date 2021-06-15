package DatabaseObjects;

import java.sql.*;

public class IDOfProgramDatabase {
    public int getProgramIDByProgramName(String programName) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "select id from university_courses_system.program" +
                    " where program_name = '" + programName + "'";

            //System.out.println(sql);

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                return result.getInt("id");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public String getProgramNameByID(int id) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "select program_name from university_courses_system.program" +
                    " where id = " + id;

            //System.out.println(sql);

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                return result.getString("program_name");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addProgram(String programName) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "insert into program(program_name) values( '" + programName + "')";

            int updates = statement.executeUpdate(sql);

            return updates == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkIfExists(String program) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/university_courses_system?zeroDateTimeBehavior=convertToNull", "sadproject", "12345")) {

            Statement statement = connection.createStatement();

            String sql = "select * from program where program_name = '" + program + "'";

            ResultSet results = statement.executeQuery(sql);

            return results.next();

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }
}

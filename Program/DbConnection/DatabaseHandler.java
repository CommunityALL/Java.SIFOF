package Program.DbConnection;

import Program.DbConnection.Const;

import java.sql.*;

public class DatabaseHandler extends Program.DbConnection.Configs {
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:ucanaccess:/" + dbAddress + "/" + dbName;
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        return DriverManager.getConnection(connectionString);
    }

    public void singUpUser(String firstName, String lastName, String gender, String login, String pass)
            throws SQLException, ClassNotFoundException {

        String insert = "INSERT INTO `" + Const.USER_TABLE + "` (`" + Const.USERS_FIRST_NAME
                + "`, `" + Const.USERS_LAST_NAME + "`, `" + Const.USERS_GENDER + "`, `"
                + Const.USERS_USER_NAME + "`, `" + Const.USERS_PASSWORD + "`)"
                + "VALUES('" + firstName + "', '" + lastName + "', '" + gender + "', '" + login + "', '" + pass + "');";

        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(insert);

        connection.close();
    }

    public void deleteUser(String login)
            throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM `" + Const.USER_TABLE + "` WHERE "
                + Const.USERS_USER_NAME + " = '" + login + "';";
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(delete);
        connection.close();
    }

    public void updateData(String value, String column, String login)
            throws SQLException, ClassNotFoundException {
        String update = "UPDATE `" + Const.USER_TABLE + "` SET " + column + " = \""
                + value + "\" WHERE " + Const.USERS_USER_NAME + " = \"" + login + "\";";
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(update);
        connection.close();
    }

    public void updateBalance(Double value, String login)
            throws SQLException, ClassNotFoundException {
        String update = "UPDATE `" + Const.USER_TABLE + "` SET " + Const.USERS_BALANCE + " = \""
                + value + "\" WHERE " + Const.USERS_USER_NAME + " = \"" + login + "\";";
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(update);
        connection.close();
    }

}

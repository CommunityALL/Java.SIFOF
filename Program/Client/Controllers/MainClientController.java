package Program.Client.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Program.General.OpenWindow;
import Program.DbConnection.Const;
import Program.DbConnection.DatabaseHandler;
import Program.DbConnection.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainClientController {

    public static User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginClientButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        loginClientButton.setOnAction(actionEvent -> {
            String login = loginField.getText();
            String password = passwordField.getText();

            try {
                if(loginUser(login, password)){
                    DatabaseHandler databaseHandler = new DatabaseHandler();
                    try {
                        Connection connection = databaseHandler.getDbConnection();
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + Const.USER_TABLE + "` WHERE " + Const.USERS_USER_NAME + " = \"" + login +"\";");

                        resultSet.next();
                        int id = resultSet.getInt(1);
                        String firstName = resultSet.getString(2);
                        String lastName = resultSet.getString(3);
                        String gender = resultSet.getString(4);
                        double balance = resultSet.getDouble(7);

                        // Это пользователь, в аккаунт которого входит программа
                        user = new User(id, firstName, lastName, gender, login, password, balance);

                        Stage stage = (Stage) loginClientButton.getScene().getWindow();
                        stage.close();
                        Stage stageClient = new Stage();

                        try {
                            OpenWindow openWindow = new OpenWindow();
                            openWindow.openClientWindow(stageClient);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setHeaderText(null);
                    alert.setContentText("Неправильний логін та/або пароль.");

                    alert.showAndWait();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        signUpButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Program/General/SignUp/SignUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent rootSignUp = loader.getRoot();
            Stage stageSignUp = new Stage();
            stageSignUp.setTitle("Client \"Sky is full of fire\"");
            stageSignUp.setMaxHeight(440);
            stageSignUp.setMaxWidth(720);
            stageSignUp.setMinHeight(440);
            stageSignUp.setMinWidth(720);
            stageSignUp.setScene(new Scene(rootSignUp, 700, 400));
            stageSignUp.showAndWait();
        });
    }

    private boolean loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {

        // Здесь нужно раскомментировать код.
        // НЕ УДАЛЯТЬ!!!
        DatabaseHandler databaseHandler = new DatabaseHandler();
        Connection connection = databaseHandler.getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + Const.USER_TABLE + "` WHERE " + Const.USERS_USER_NAME + " = \"" + loginText +"\";");

        if(resultSet.next()){
            String password = resultSet.getString(6);
            return loginPassword.equals(password);
        }
        return false;

        // А вот эту строку удалить.
//        return true;
    }

}

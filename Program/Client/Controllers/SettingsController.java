package Program.Client.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Program.General.Delete.DeleteUserController;
import Program.General.OpenWindow;
import Program.DbConnection.Const;
import Program.DbConnection.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Menu menu;

    @FXML
    private MenuItem normalModeMenuItem;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button firstNameButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private Button lastNameButton;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private Button passwordButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private MenuItem settingModeMenuItem;

    @FXML
    void initialize() {

        firstNameField.setText(MainClientController.user.getFirstName());
        lastNameField.setText(MainClientController.user.getLastName());
        loginField.setText(MainClientController.user.getUserName());
        passwordField.setText(MainClientController.user.getPassword());

        normalModeMenuItem.setOnAction(actionEvent -> {
            Stage stage = (Stage) firstNameButton.getScene().getWindow();
            stage.close();
            Stage stageNormal = new Stage();

            stageNormal.setTitle("Client \"Sky is full of fire\"");

            try {
                OpenWindow openWindow = new OpenWindow();
                openWindow.openClientWindow(stageNormal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        firstNameButton.setOnAction(actionEvent -> {
            String valueToUpdate = firstNameField.getText().trim();
            String columnToUpdate = Const.USERS_FIRST_NAME;

            if (valueToUpdate.length() > 30){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Це ім'я занадто довге (більше 30 знаків).");
                alert.showAndWait();
            }
            else {
                DatabaseHandler databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.updateData(valueToUpdate, columnToUpdate, MainClientController.user.getFirstName());
                    MainClientController.user.setFirstName(valueToUpdate);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Ви змінили ім'я користувача на \"" + valueToUpdate +"\".");
                    alert.showAndWait();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        lastNameButton.setOnAction(actionEvent -> {
            String valueToUpdate = lastNameField.getText().trim();
            String columnToUpdate = Const.USERS_LAST_NAME;
            if (valueToUpdate.length() > 30){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Це прізвище занадто довге (більше 30 знаків).");
                alert.showAndWait();
            }
            else {
                DatabaseHandler databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.updateData(valueToUpdate, columnToUpdate, MainClientController.user.getLastName());
                    MainClientController.user.setLastName(valueToUpdate);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Ви змінили прізвище користувача на \"" + valueToUpdate +"\".");
                    alert.showAndWait();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        loginButton.setOnAction(actionEvent -> {
            String valueToUpdate = loginField.getText().trim();
            String columnToUpdate = Const.USERS_USER_NAME;
            if (valueToUpdate.length() > 30){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Цей логін занадто довгий (більше 30 знаків).");
                alert.showAndWait();
            }
            else {
                DatabaseHandler databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.updateData(valueToUpdate, columnToUpdate, MainClientController.user.getUserName());
                    MainClientController.user.setUserName(valueToUpdate);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Ви змінили логін користувача на \"" + valueToUpdate +"\".");
                    alert.showAndWait();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        passwordButton.setOnAction(actionEvent -> {
            String valueToUpdate = passwordField.getText().trim();
            String columnToUpdate = Const.USERS_PASSWORD;
            DatabaseHandler databaseHandler = new DatabaseHandler();
            try {
                databaseHandler.updateData(valueToUpdate, columnToUpdate, MainClientController.user.getPassword());
                MainClientController.user.setPassword(valueToUpdate);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Ви змінили пароль користувача на \"" + valueToUpdate +"\".");
                alert.showAndWait();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        deleteUserButton.setOnAction(actionEvent -> {
            deleteUserButton.getScene().getWindow().hide();
            Stage stageDelete = new Stage();
            DeleteUserController.deleteUser = MainClientController.user.getUserName();
            DeleteUserController.clientBoolean = true;
            try {
                OpenWindow openWindow = new OpenWindow();
                openWindow.openDeleteWindow(stageDelete);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

}

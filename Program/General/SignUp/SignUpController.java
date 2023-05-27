package Program.General.SignUp;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Program.DbConnection.Const;
import Program.DbConnection.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ToggleGroup genderRadioButtonGroup;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;


    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            try {
                String login = loginField.getText();
                DatabaseHandler databaseHandler = new DatabaseHandler();
                Connection connection = databaseHandler.getDbConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + Const.USER_TABLE + "` WHERE " + Const.USERS_USER_NAME + " = \"" + login +"\";");
                if (resultSet.next()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Цей логін вже використовується. Створіть інший.");
                    alert.showAndWait();
                }
                else {
                    signUpNewUser();
                }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String login = loginField.getText().trim();
        String pass = passwordField.getText().trim();
        String gender;
        if (maleRadioButton.isSelected())
            gender = "чоловік";
        else
            gender = "жінка";
        if (firstName.length() > 30){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Цей логін занадто довгий (більше 30 знаків).");
            alert.showAndWait();
        }
        else if (lastName.length() > 30){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Це прізвище занадто довге (більше 30 знаків).");
            alert.showAndWait();
        }
        else if (login.length() > 30){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Цей логін занадто довгий (більше 30 знаків).");
            alert.showAndWait();
        }
        else {
            try {
                dbHandler.singUpUser(firstName, lastName, gender, login, pass);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            signUpButton.getScene().getWindow().hide();
        }
    }

}


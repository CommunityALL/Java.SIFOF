package Program.Administrator.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainAdminController extends Program.DbConnection.Configs {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginAdministratorButton;


    @FXML
    void initialize() {

        loginAdministratorButton.setOnAction(actionEvent -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passwordField.getText().trim();
            if (loginUser(loginText,loginPassword)){
                loginUser(loginText, loginPassword);

                loginAdministratorButton.getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Program/Administrator/View/Administrator.fxml"));

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Administrator \"Sky is full of fire\"");
                stage.setMaxHeight(540);
                stage.setMaxWidth(720);
                stage.setMinHeight(540);
                stage.setMinWidth(720);
                stage.setScene(new Scene(root, 700, 500));

                stage.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);
                alert.setContentText("Неправильний логін та/або пароль.");

                alert.showAndWait();
            }

        });
    }

    private boolean loginUser(String loginText, String loginPassword) {
        return loginText.equals(dbAdministratorUser) && loginPassword.equals(dbAdministratorPass);
    }

}

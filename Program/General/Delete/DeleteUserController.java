package Program.General.Delete;

import Program.Client.Controllers.MainClientController;
import Program.DbConnection.DatabaseHandler;
import Program.General.OpenWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeleteUserController {

    public static String deleteUser;

    public static boolean clientBoolean;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button noDeleteButton;

    @FXML
    private Button yesDeleteButton;

    @FXML
    private Label deleteUserLabel;

    @FXML
    void initialize() {
        deleteUserLabel.setText(deleteUser);
//        AtomicBoolean deleteUserBoolean = new AtomicBoolean(false);
        yesDeleteButton.setOnAction(actionEvent -> {
//            deleteUserBoolean.set(true);
            DatabaseHandler databaseHandler = new DatabaseHandler();
            try {
                databaseHandler.deleteUser(deleteUser);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

            yesDeleteButton.getScene().getWindow().hide();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Користувач " + deleteUser + " видален.");
            alert.showAndWait();

        });
        noDeleteButton.setOnAction(actionEvent -> {
//            deleteUserBoolean.set(false);
            noDeleteButton.getScene().getWindow().hide();

            if (clientBoolean) {

                Stage stage = (Stage) noDeleteButton.getScene().getWindow();
                stage.close();
                Stage stageSetting = new Stage();
                stageSetting.setTitle("Client (settings) \"Sky is full of fire\"");
                try {
                    OpenWindow openWindow = new OpenWindow();
                    openWindow.openSettingWindow(stageSetting);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
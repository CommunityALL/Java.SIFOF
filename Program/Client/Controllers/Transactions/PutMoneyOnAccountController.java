package Program.Client.Controllers.Transactions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import Program.Client.Controllers.MainClientController;
import Program.Client.Transactions.Transactions;
import Program.DbConnection.Const;
import Program.DbConnection.DatabaseHandler;
import Program.General.OpenWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class PutMoneyOnAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private Button moneyToPutButton;

    @FXML
    private TextField moneyToPutField;

    @FXML
    private Label moneyToPutLabel;

    @FXML
    private Button putMoneyButton;

    @FXML
    void initialize() {

        final double[] moneyToPut = {0.0};
        moneyToPutLabel.setText(Double.toString(moneyToPut[0]));

        cancelButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            Stage stageClient = new Stage();
            try {
                OpenWindow openWindow = new OpenWindow();
                openWindow.openClientWindow(stageClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        moneyToPutButton.setOnAction(actionEvent -> {
            try {
                double moneyToPlusToMoneyToPut = Double.parseDouble(moneyToPutField.getText());
                moneyToPut[0] += Math.round(moneyToPlusToMoneyToPut * 100.0) / 100.0;
                moneyToPutLabel.setText(Double.toString(moneyToPut[0]));
            } catch (Exception err) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Введено некоректне значеня.");
                alert.showAndWait();
            }
        });

        putMoneyButton.setOnAction(actionEvent -> {
            putMoneyButton.getScene().getWindow().hide();
            double balance = Transactions.putMoneyIntoTheAccount(MainClientController.user.getBalance(), moneyToPut[0]);
            DatabaseHandler databaseHandler = new DatabaseHandler();
            try {
                databaseHandler.updateBalance(balance, MainClientController.user.getUserName());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            MainClientController.user.setBalance(balance);

            Stage stageClient = new Stage();
            try {
                OpenWindow openWindow = new OpenWindow();
                openWindow.openClientWindow(stageClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

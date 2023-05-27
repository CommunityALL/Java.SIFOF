package Program.Client.Controllers.Transactions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Program.Client.Controllers.MainClientController;
import Program.Client.Transactions.Transactions;
import Program.DbConnection.DatabaseHandler;
import Program.General.OpenWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WithdrawMoneyFromAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private Button moneyToWithdrawButton;

    @FXML
    private TextField moneyToWithdrawField;

    @FXML
    private Label moneyToWithdrawLabel;

    @FXML
    private Button withdrawMoneyButton;

    @FXML
    void initialize() {

        final double[] moneyToWithdraw = {0.0};
        moneyToWithdrawLabel.setText(Double.toString(moneyToWithdraw[0]));

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

        moneyToWithdrawButton.setOnAction(actionEvent -> {
            try {
                double moneyToPlusToMoneyToPut = Double.parseDouble(moneyToWithdrawField.getText());
                moneyToWithdraw[0] += Math.round(moneyToPlusToMoneyToPut * 100.0) / 100.0;
                if (Transactions.isItPossibleToWithdrawMoneyFromTheAccount(MainClientController.user.getBalance(), moneyToWithdraw[0])) {
                    moneyToWithdrawLabel.setText(Double.toString(moneyToWithdraw[0]));
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Ви не зможете зняти таку суму (" + moneyToWithdraw[0] + "), вона перевищує Ваш баланс.");
                    alert.showAndWait();
                    moneyToWithdraw[0] -= Math.round(moneyToPlusToMoneyToPut * 100.0) / 100.0;
                }
            } catch (Exception err) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Введено некоректне значеня.");
                alert.showAndWait();
            }

        });

        withdrawMoneyButton.setOnAction(actionEvent -> {
            withdrawMoneyButton.getScene().getWindow().hide();
            double balance = Transactions.withdrawMoneyFromTheAccount(MainClientController.user.getBalance(), moneyToWithdraw[0]);
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

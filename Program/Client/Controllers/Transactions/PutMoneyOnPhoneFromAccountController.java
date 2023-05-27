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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PutMoneyOnPhoneFromAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField moneyToPutField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button putMoneyButton;

    @FXML
    void initialize() {


        final double[] moneyToPutOnPhone = {0.0};

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

        putMoneyButton.setOnAction(actionEvent -> {
            try {
                moneyToPutOnPhone[0] = Double.parseDouble(moneyToPutField.getText());
                moneyToPutOnPhone[0] = Math.round(moneyToPutOnPhone[0] * 100.0) / 100.0;
                int phone = Integer.parseInt(phoneField.getText());
                if (Transactions.isItPossibleToWithdrawMoneyFromTheAccount(MainClientController.user.getBalance(), moneyToPutOnPhone[0])) {
                    putMoneyButton.getScene().getWindow().hide();
                    double balance = Transactions.withdrawMoneyFromTheAccount(MainClientController.user.getBalance(), moneyToPutOnPhone[0]);
                    DatabaseHandler databaseHandler = new DatabaseHandler();
                    try {
                        databaseHandler.updateBalance(balance, MainClientController.user.getUserName());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Ви поповнили телефон " + phone + " на " + moneyToPutOnPhone[0] + ".");
                        alert.showAndWait();
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
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Ви не зможете зняти таку суму (" + moneyToPutOnPhone[0] + "), вона перевищує Ваш баланс.");
                    alert.showAndWait();
                    moneyToPutOnPhone[0] = 0.0;
                }
            } catch (Exception err) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Введено некоректне значеня.");
                alert.showAndWait();
            }
        });

    }

}

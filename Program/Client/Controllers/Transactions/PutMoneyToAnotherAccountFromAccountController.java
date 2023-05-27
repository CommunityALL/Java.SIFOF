package Program.Client.Controllers.Transactions;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Program.Client.Controllers.MainClientController;
import Program.Client.Transactions.Transactions;
import Program.DbConnection.Const;
import Program.DbConnection.DatabaseHandler;
import Program.DbConnection.User;
import Program.General.OpenWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PutMoneyToAnotherAccountFromAccountController {

    private User anotherUser;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField accountField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField moneyToPutField;

    @FXML
    private Button putMoneyButton;

    @FXML
    void initialize() {

        final double[] moneyToPutOnAccount = {0.0};

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
                String anotherAccount = accountField.getText();
                if (doesAccountExist(anotherAccount)) {
                    moneyToPutOnAccount[0] = Double.parseDouble(moneyToPutField.getText());
                    moneyToPutOnAccount[0] = Math.round(moneyToPutOnAccount[0] * 100.0) / 100.0;

                    if (Transactions.isItPossibleToWithdrawMoneyFromTheAccount(MainClientController.user.getBalance(), moneyToPutOnAccount[0])) {
                        putMoneyButton.getScene().getWindow().hide();
                        double balance = Transactions.withdrawMoneyFromTheAccount(MainClientController.user.getBalance(), moneyToPutOnAccount[0]);
                        double anotherAccountBalance = Transactions.putMoneyIntoTheAccount(anotherUser.getBalance(), moneyToPutOnAccount[0]);
                        DatabaseHandler databaseHandler = new DatabaseHandler();
                        try {
                            databaseHandler.updateBalance(balance, MainClientController.user.getUserName());
                            databaseHandler.updateBalance(anotherAccountBalance, anotherUser.getUserName());

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Ви поповнили акаунт користувача " + anotherUser.getUserName()
                                    + " (" + anotherUser.getFirstName() + " " + anotherUser.getLastName() + ") на "
                                    + moneyToPutOnAccount[0] + ".");
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
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Ви не зможете зняти таку суму (" + moneyToPutOnAccount[0] + "), вона перевищує Ваш баланс.");
                        alert.showAndWait();
                        moneyToPutOnAccount[0] = 0.0;
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Введений акаунт не існує.");
                    alert.showAndWait();
                }
            } catch (Exception err) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Введено некоректне значеня.");
                alert.showAndWait();
            }
        });

    }

    private boolean doesAccountExist(String anotherAccount) throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        Connection connection = databaseHandler.getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + Const.USER_TABLE + "` WHERE " + Const.USERS_USER_NAME + " = \"" + anotherAccount +"\";");
        if (resultSet.next()) {
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            double balance = resultSet.getDouble(7);
            anotherUser = new User(firstName, lastName, anotherAccount, balance);
            return true;
        }
        return false;
    }

}

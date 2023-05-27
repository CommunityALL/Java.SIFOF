package Program.Client.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;

import Program.General.OpenWindow;
import javafx.stage.Stage;

public class ClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Menu menu;

    @FXML
    private Label balanceLabel;

    @FXML
    private MenuItem normalModeMenuItem;

    @FXML
    private Button putMoneyOnAccountButton;

    @FXML
    private Button putMoneyOnPhoneFromAccountButton;

    @FXML
    private Button putMoneyToAnotherAccountFromAccountButton;

    @FXML
    private MenuItem settingModeMenuItem;

    @FXML
    private Button withdrawMoneyFromAccountButton;

    @FXML
    void initialize() {

        balanceLabel.setText(Double.toString(Math.round(MainClientController.user.getBalance() * 100.0) / 100.0));

        settingModeMenuItem.setOnAction(actionEvent -> {
            Stage stage = (Stage) putMoneyOnAccountButton.getScene().getWindow();
            stage.close();
            Stage stageSetting = new Stage();

            stageSetting.setTitle("Client (settings) \"Sky is full of fire\"");

            try {
                OpenWindow openWindow = new OpenWindow();
                openWindow.openSettingWindow(stageSetting);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        putMoneyOnAccountButton.setOnAction(actionEvent -> {
            putMoneyOnAccountButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Program/Client/View/Transactions/PutMoneyOnAccount.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Client \"Sky is full of fire\"");
            stage.setMaxHeight(440);
            stage.setMaxWidth(720);
            stage.setMinHeight(440);
            stage.setMinWidth(720);
            stage.setScene(new Scene(root, 700, 400));

            stage.showAndWait();
        });

        withdrawMoneyFromAccountButton.setOnAction(actionEvent -> {
            withdrawMoneyFromAccountButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Program/Client/View/Transactions/WithdrawMoneyFromAccount.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Client \"Sky is full of fire\"");
            stage.setMaxHeight(440);
            stage.setMaxWidth(720);
            stage.setMinHeight(440);
            stage.setMinWidth(720);
            stage.setScene(new Scene(root, 700, 400));

            stage.showAndWait();
        });

        putMoneyOnPhoneFromAccountButton.setOnAction(actionEvent -> {
            putMoneyOnPhoneFromAccountButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Program/Client/View/Transactions/PutMoneyOnPhoneFromAccount.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Client \"Sky is full of fire\"");
            stage.setMaxHeight(440);
            stage.setMaxWidth(720);
            stage.setMinHeight(440);
            stage.setMinWidth(720);
            stage.setScene(new Scene(root, 700, 400));

            stage.showAndWait();

        });

        putMoneyToAnotherAccountFromAccountButton.setOnAction(actionEvent -> {
            putMoneyToAnotherAccountFromAccountButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Program/Client/View/Transactions/PutMoneyToAnotherAccountFromAccount.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Client \"Sky is full of fire\"");
            stage.setMaxHeight(440);
            stage.setMaxWidth(720);
            stage.setMinHeight(440);
            stage.setMinWidth(720);
            stage.setScene(new Scene(root, 700, 400));

            stage.showAndWait();

        });

    }

}

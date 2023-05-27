package Program.Administrator.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Program.DbConnection.Const;
import Program.DbConnection.DatabaseHandler;
import Program.DbConnection.User;
import Program.General.Delete.DeleteUserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdministratorController {

    private final ObservableList<User> usersData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private Button registerNewUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button fileButton;

    @FXML
    private Button updateTableButton;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        Connection connection = databaseHandler.getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + Const.USER_TABLE + "`;");
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String gender = resultSet.getString(4);
            String userName = resultSet.getString(5);
            String password = resultSet.getString(6);
            double balance = resultSet.getDouble(7);
            usersData.add(new User(id, firstName, lastName, gender, userName, password, balance));
        }
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableUsers.setItems(usersData);

        registerNewUserButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Program/General/SignUp/SignUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent rootSignUp = loader.getRoot();
            Stage stageSignUp = new Stage();
            stageSignUp.setTitle("Administrator \"Sky is full of fire\"");
            stageSignUp.setMaxHeight(440);
            stageSignUp.setMaxWidth(720);
            stageSignUp.setMinHeight(440);
            stageSignUp.setMinWidth(720);
            stageSignUp.setScene(new Scene(rootSignUp, 700, 400));
            stageSignUp.showAndWait();
        });

        deleteUserButton.setOnAction(actionEvent -> {
            TableView.TableViewSelectionModel<User> selectionModel = tableUsers.getSelectionModel();
            if (!selectionModel.isEmpty()) {
                DeleteUserController.deleteUser = selectionModel.getSelectedItem().getUserName();
                DeleteUserController.clientBoolean = false;

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Program/General/Delete/DeleteUser.fxml"));
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = fxmlLoader.getRoot();
                Stage deleteStage = new Stage();
                deleteStage.setMaxHeight(190);
                deleteStage.setMaxWidth(370);
                deleteStage.setMinHeight(190);
                deleteStage.setMinWidth(370);
                deleteStage.setScene(new Scene(root, 350, 150));
                deleteStage.showAndWait();
            }
            else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Жодний рядок не віділено");
                alert.showAndWait();
            }
        });

        fileButton.setOnAction(actionEvent -> {
            File fileTableUsers = new File("Assets/TableUsers.txt");
            try (FileWriter fileWriterTableUsers = new FileWriter(fileTableUsers, false)){
                fileWriterTableUsers.write("Логін");
                for (int i = 0; i < 25; i++){
                    fileWriterTableUsers.write(" ");
                }
                fileWriterTableUsers.write("Прізвище");
                for (int i = 0; i < 22; i++){
                    fileWriterTableUsers.write(" ");
                }
                fileWriterTableUsers.write("Ім'я");
                for (int i = 0; i < 26; i++){
                    fileWriterTableUsers.write(" ");
                }
                fileWriterTableUsers.write("\n");
                ResultSet resultSetFile = statement.executeQuery("SELECT * FROM `" + Const.USER_TABLE + "`;");
                while (resultSetFile.next()){
                    String userName = resultSetFile.getString(5);
                    String lastName = resultSetFile.getString(3);
                    String firstName = resultSetFile.getString(2);
                    fileWriterTableUsers.write(userName);
                    for(int i = 0; i < 30 - userName.length(); i++){
                        fileWriterTableUsers.write(" ");
                    }
                    fileWriterTableUsers.write(lastName);
                    for(int i = 0; i < 30 - lastName.length(); i++){
                        fileWriterTableUsers.write(" ");
                    }
                    fileWriterTableUsers.write(firstName);
                    for(int i = 0; i < 30 - firstName.length(); i++){
                        fileWriterTableUsers.write(" ");
                    }
                    fileWriterTableUsers.write("\n");
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);
                alert.setContentText("Файл сгенерован.");

                alert.showAndWait();
            }
            catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        });

        updateTableButton.setOnAction(actionEvent -> {

            double x = updateTableButton.getScene().getWindow().getX();
            double y = updateTableButton.getScene().getWindow().getY();

            updateTableButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Program/Administrator/View/Administrator.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Administrator \"Sky is full of fire\"");
            stage.setMaxHeight(540);
            stage.setMaxWidth(720);
            stage.setMinHeight(540);
            stage.setMinWidth(720);
            stage.setScene(new Scene(root, 700, 500));
            stage.setX(x);
            stage.setY(y);
            stage.show();
        });

    }

}

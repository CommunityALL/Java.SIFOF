package Program.Administrator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.ResultSet;

public class MainAdministrator extends Application {
    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Program/Administrator/View/MainAdministrator.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Administrator \"Sky is full of fire\"");
        primaryStage.setMaxHeight(440);
        primaryStage.setMaxWidth(720);
        primaryStage.setMinHeight(440);
        primaryStage.setMinWidth(720);
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }
}
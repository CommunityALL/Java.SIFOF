package Program.General;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class OpenWindow {

    public void openClientWindow(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Program/Client/View/Client.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaxHeight(466);
        stage.setMaxWidth(720);
        stage.setMinHeight(466);
        stage.setMinWidth(720);
        stage.show();
    }

    public void openSettingWindow(Stage stage) throws IOException {


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Program/Client/View/Settings.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaxHeight(466);
        stage.setMaxWidth(715);
        stage.setMinHeight(466);
        stage.setMinWidth(715);

        stage.show();
    }

    public void openDeleteWindow(Stage stage) throws IOException {


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Program/General/Delete/DeleteUser.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaxHeight(190);
        stage.setMaxWidth(370);
        stage.setMinHeight(190);
        stage.setMinWidth(370);
        stage.show();
    }

    public void menu (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Program/Client/View/Settings.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}

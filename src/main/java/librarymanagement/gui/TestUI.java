package librarymanagement.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librarymanagement.gui.controllers.MainSceneController;
import librarymanagement.gui.controllers.StartScreenController;
import librarymanagement.gui.controllers.UIController;

import java.io.IOException;

public class TestUI extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        UIController.setPrimaryStage(primaryStage);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/MainScene.fxml"));
        Scene loginScene = new Scene(loader.load());
        primaryStage.setTitle("Library Management");
        primaryStage.setScene(loginScene);

        primaryStage.setMaximized(false);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

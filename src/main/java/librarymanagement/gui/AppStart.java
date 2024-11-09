package librarymanagement.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import librarymanagement.gui.controllers.LoginController;
import librarymanagement.gui.controllers.StartScreenController;
import librarymanagement.gui.controllers.UIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppStart extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        UIController.setPrimaryStage(primaryStage);

        Scene loginScene = StartScreenController.getStartScreen();
        primaryStage.setTitle("Library Management");
        primaryStage.setScene(loginScene);

        primaryStage.setMaximized(false);
        primaryStage.setResizable(true);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}
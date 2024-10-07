package librarymanagement.gui;

import librarymanagement.gui.controllers.LoginController;
import librarymanagement.gui.controllers.UIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppStart extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(AppStart.class.getResource("FXML/AppStart.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Library Management");
//        stage.setScene(scene);
//        stage.show();
        UIController.setPrimaryStage(primaryStage);

        Scene loginScene = LoginController.getLoginScene();
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
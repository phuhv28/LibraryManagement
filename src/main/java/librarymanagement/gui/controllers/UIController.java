package librarymanagement.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController {
    private static Stage primaryStage = null;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        UIController.primaryStage = primaryStage;
    }

    public static void showScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void setTitle(String title) {
        primaryStage.setTitle(title);
    }

    public static void showMainScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UIController.class.getResource("/FXML/MainScene.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

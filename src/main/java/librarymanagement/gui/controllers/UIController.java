package librarymanagement.gui.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
}

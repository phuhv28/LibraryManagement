package librarymanagement.gui.controllers;

import javafx.stage.Stage;

public class UIController {
    private static Stage primaryStage = null;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        UIController.primaryStage = primaryStage;
    }
}

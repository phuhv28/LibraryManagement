package librarymanagement.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librarymanagement.entity.Document;

import java.io.IOException;

/**
 * The {@code UIController} class serves as a centralized utility for managing
 * and controlling the user interface of the library management application.
 * It handles the primary stage, scene transitions, and application-level UI interactions.
 */
public class UIController {

    private static Stage primaryStage = null;

    /**
     * Retrieves the primary stage of the application.
     *
     * @return the primary {@code Stage}.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the primary stage of the application. This method also adds listeners
     * for scene changes to center the stage on the screen and handles the close request
     * to gracefully exit the application.
     *
     * @param primaryStage the primary {@code Stage} to set.
     */
    public static void setPrimaryStage(Stage primaryStage) {
        UIController.primaryStage = primaryStage;
        primaryStage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                primaryStage.centerOnScreen();
            }
        });
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Displays the given scene on the primary stage.
     *
     * @param scene the {@code Scene} to be displayed.
     */
    public static void showScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    /**
     * Sets the title of the primary stage.
     *
     * @param title the title to set for the stage.
     */
    public static void setTitle(String title) {
        primaryStage.setTitle(title);
    }

    /**
     * Loads and displays the main scene of the application.
     * The main scene is defined in the "MainScene.fxml" file.
     */
    public static void showMainScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UIController.class.getResource("/FXML/MainScene.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the document information window for the specified document.
     * This method delegates the display logic to the {@code DocumentInfoController}.
     *
     * @param document the {@code Document} whose information is to be displayed.
     */
    public static void showDocumentInfo(Document document) {
        DocumentInfoController controller = DocumentInfoController.newInstance(document);
        controller.show();
    }
}

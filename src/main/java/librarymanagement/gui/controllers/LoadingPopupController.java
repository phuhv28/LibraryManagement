package librarymanagement.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for managing the loading popup window.
 * This class is responsible for displaying a modal popup window with a loading message.
 * The popup can be updated with a custom message and can be shown or closed.
 */
public class LoadingPopupController {

    @FXML
    private Label label;

    private Stage stage;

    private static LoadingPopupController instance;

    /**
     * Creates and returns a new instance of the LoadingPopupController.
     * This method loads the FXML file for the loading popup and sets up the stage.
     *
     * @param title the title of the popup window.
     * @return a new instance of LoadingPopupController.
     */
    public static LoadingPopupController newInstance(String title) {
        FXMLLoader loader = new FXMLLoader(LoadingPopupController.class.getResource("/FXML/LoadingPopup.fxml"));
        try {
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);

            instance = loader.getController();
            instance.setStage(stage);

        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }

        return instance;
    }

    /**
     * Sets the text message of the loading popup.
     * This method runs on the JavaFX application thread to update the label with the given message.
     *
     * @param message the message to be displayed on the popup.
     */
    public void setText(Object message) {
        if (stage.isShowing())
            Platform.runLater(() -> label.setText(message.toString()));
    }

    /**
     * Returns the instance of the LoadingPopupController.
     *
     * @return the current instance of LoadingPopupController.
     */
    public static LoadingPopupController getInstance() {
        return instance;
    }

    /**
     * Displays the loading popup window.
     * This method makes the popup window visible to the user.
     */
    public void show() {
        stage.show();
    }

    /**
     * Closes the loading popup window.
     * This method closes the stage and hides the popup.
     */
    public void close() {
        Platform.runLater(() -> {
            if (stage != null) {
                stage.close();
            }
        });
    }

    /**
     * Returns the Stage object representing the loading popup window.
     *
     * @return the Stage object of the loading popup.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Initializes the owner stage of the loading popup.
     * This method sets the owner of the loading popup stage, making the popup modal to the specified owner stage.
     *
     * @param ownerStage the owner stage that the loading popup will be modal to.
     */
    public void initOwnerStage(Stage ownerStage) {
        stage.initOwner(ownerStage);
    }

    /**
     * Sets the Stage object for the loading popup.
     *
     * @param stage the Stage object to be set for the loading popup.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

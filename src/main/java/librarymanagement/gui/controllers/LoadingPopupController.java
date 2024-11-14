package librarymanagement.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.ExecutorService;

public class LoadingPopupController {

    @FXML
    private Label label;

    private Stage stage;

    private static LoadingPopupController instance;

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

    public void setText(Object message) {
        if (stage.isShowing())
            Platform.runLater(() -> label.setText(message.toString()));
    }

    public static LoadingPopupController getInstance() {
        return instance;
    }

    public void show() {
        stage.show();
    }

    public void close() {
        Platform.runLater(() -> {
            if (stage != null) {
                stage.close();
            }
        });
    }

    public Stage getStage() {
        return stage;
    }

    public void initOwnerStage(Stage ownerStage) {
        stage.initOwner(ownerStage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

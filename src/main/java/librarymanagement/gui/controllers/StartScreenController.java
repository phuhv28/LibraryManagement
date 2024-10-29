package librarymanagement.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class StartScreenController {


    private static Scene scene;
    private static StartScreenController startScreenController;

    public static Scene getLoginScene() {
        if (scene == null) {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/FXML/StartScreen.fxml"));
            try {
                Parent parent = loader.load();
                scene = new Scene(parent);
                startScreenController = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return scene;
    }
}

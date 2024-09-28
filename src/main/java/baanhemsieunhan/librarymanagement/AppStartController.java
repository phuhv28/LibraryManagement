package baanhemsieunhan.librarymanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AppStartController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
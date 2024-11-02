package librarymanagement.testcode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class codetest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tải giao diện từ file FXML với đường dẫn tuyệt đối từ thư mục resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainScene.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("JavaFX Image Example");
        primaryStage.setScene(new Scene(root, 1000, 750));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

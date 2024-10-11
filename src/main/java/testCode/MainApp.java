package testCode;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        ImageView imageView = (ImageView) loader.getNamespace().get("imageView");
        ImageView imageView1 = (ImageView) loader.getNamespace().get("imageView1");
        ImageView imageView2 = (ImageView) loader.getNamespace().get("imageView2");

        // Tạo Task để tải hình ảnh trong nền
        Task<Image> loadImageTask = createImageLoadTask("/images/image1.png");
        Task<Image> loadImageUserTask = createImageLoadTask("/images/image_user.png");
        Task<Image> loadImagePasswordTask = createImageLoadTask("/images/image_password.png");

        // Cập nhật ImageView khi tải thành công
        loadImageTask.setOnSucceeded(event -> imageView.setImage(loadImageTask.getValue()));
        loadImageUserTask.setOnSucceeded(event -> imageView1.setImage(loadImageUserTask.getValue()));
        loadImagePasswordTask.setOnSucceeded(event -> imageView2.setImage(loadImagePasswordTask.getValue()));

        // Chạy các Task trong các luồng khác nhau
        new Thread(loadImageTask).start();
        new Thread(loadImageUserTask).start();
        new Thread(loadImagePasswordTask).start();

        primaryStage.setTitle("JavaFX Image Example");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    // Phương thức để tạo Task tải hình ảnh
    private Task<Image> createImageLoadTask(String imagePath) {
        return new Task<>() {
            @Override
            protected Image call() throws Exception {
                return new Image(getClass().getResource(imagePath).toExternalForm());
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}

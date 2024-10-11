package librarymanagement.testCode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class testRegister extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Parent root = loader.load();

        ImageView imageView3 = (ImageView) loader.getNamespace().get("imageView3");


        String imagePath3 = "/images/image2.png";

        Image image3 = new Image(getClass().getResource(imagePath3).toExternalForm());

        imageView3.setImage(image3);

        primaryStage.setTitle("JavaFX Image Example");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

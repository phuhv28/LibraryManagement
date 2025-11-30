package librarymanagement.gui;

import librarymanagement.gui.controllers.StartScreenController;
import librarymanagement.gui.controllers.UIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librarymanagement.gui.models.AccountService;
import librarymanagement.gui.models.BookService;
import librarymanagement.gui.models.BorrowingService;
import librarymanagement.gui.models.MagazineService;
import librarymanagement.gui.models.ReviewService;
import librarymanagement.utils.SQLiteInstance;

import java.io.IOException;

public class AppStart extends Application {
    public static SQLiteInstance sqLiteInstance = new SQLiteInstance();
    @Override
    public void start(Stage primaryStage) throws IOException {

        setUp();

        UIController.setPrimaryStage(primaryStage);

        Scene loginScene = StartScreenController.getStartScreen();
        primaryStage.setTitle("Library Management");
        primaryStage.setScene(loginScene);

        primaryStage.setMaximized(false);
        primaryStage.setResizable(true);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    private void setUp() {
        AccountService.setSqLiteInstance(sqLiteInstance);
        BookService.setSqLiteInstance(sqLiteInstance);
        BorrowingService.setSqLiteInstance(sqLiteInstance);
        MagazineService.setSqLiteInstance(sqLiteInstance);
        ReviewService.setSqLiteInstance(sqLiteInstance);
    }

    public static void main(String[] args) {
        launch();
    }
}
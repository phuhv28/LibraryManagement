module librarymanagement {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports librarymanagement.gui to javafx.fxml, javafx.controls, java.base;
    opens librarymanagement.gui;
    exports librarymanagement.gui.controllers to javafx.fxml, javafx.controls, java.base;
    opens librarymanagement.gui.controllers;
}
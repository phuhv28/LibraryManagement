module librarymanagement {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports librarymanagement.gui to javafx.fxml, javafx.controls, java.base;
    opens librarymanagement.gui;
    exports librarymanagement.gui.controllers to javafx.fxml, javafx.controls, java.base;
    opens librarymanagement.gui.controllers;
    exports librarymanagement.gui.models to java.base, javafx.controls, javafx.fxml;
    opens librarymanagement.gui.models;
    exports librarymanagement.gui.viewmodels to java.base, javafx.controls, javafx.fxml;
    opens librarymanagement.gui.viewmodels;
    exports librarymanagement.testCode;
}
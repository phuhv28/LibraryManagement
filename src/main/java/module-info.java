module librarymanagement {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.json;
    requires annotations;
    requires org.xerial.sqlitejdbc;
    requires org.junit.jupiter.engine;
    requires org.junit.platform.launcher;
    requires org.junit.jupiter.params;

    opens test to org.junit.platform.commons;
    exports librarymanagement.data to javafx.base;
    opens librarymanagement.data;
    exports librarymanagement.gui to javafx.fxml, javafx.controls, java.base ,javafx.graphics;
    opens librarymanagement.gui;
    exports librarymanagement.gui.controllers to javafx.fxml, javafx.controls, java.base ,javafx.graphics;
    opens librarymanagement.gui.controllers;
    exports librarymanagement.gui.models to java.base, javafx.controls, javafx.fxml , javafx.graphics;
    opens librarymanagement.gui.models;
    exports librarymanagement.gui.viewmodels to java.base, javafx.controls, javafx.fxml,  javafx.graphics;
    opens librarymanagement.gui.viewmodels;
    exports librarymanagement.gui.utils to java.base, javafx.controls, javafx.fxml, javafx.graphics;
    opens librarymanagement.gui.utils;
    exports librarymanagement.UserAuth;
}
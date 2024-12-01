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
    requires org.junit.jupiter.api;

    opens librarymanagement.entity;
    exports librarymanagement.gui to javafx.fxml, javafx.controls, java.base ,javafx.graphics;
    opens librarymanagement.gui;
    exports librarymanagement.gui.controllers to javafx.fxml, javafx.controls, java.base ,javafx.graphics;
    opens librarymanagement.gui.controllers;
    opens librarymanagement.gui.models;
    exports librarymanagement.gui.viewmodels to java.base, javafx.controls, javafx.fxml,  javafx.graphics;
    opens librarymanagement.gui.viewmodels;
    opens librarymanagement.utils;
    exports librarymanagement.utils to java.base, javafx.base, javafx.controls, javafx.fxml, javafx.graphics;
    exports librarymanagement.gui.models;
    exports librarymanagement.entity;
}
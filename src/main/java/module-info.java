module librarymanagement {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires java.sql;
    requires org.json;
    requires kotlin.stdlib;

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
    exports librarymanagement.testcode to java.base, javafx.controls, javafx.fxml , javafx.graphics;
    opens librarymanagement.testcode;

}
module baanhemsieunhan.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens baanhemsieunhan.librarymanagement to javafx.fxml;
    exports baanhemsieunhan.librarymanagement;
}
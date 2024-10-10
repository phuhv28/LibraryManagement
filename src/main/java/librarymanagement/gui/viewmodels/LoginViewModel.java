package librarymanagement.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModel {

    private StringProperty userNameProperty = new SimpleStringProperty();
    private final StringProperty passWordProperty = new SimpleStringProperty();

    public StringProperty userNameProperty() {
        return userNameProperty;
    }

    public StringProperty passWordProperty() {
        return passWordProperty;
    }
}

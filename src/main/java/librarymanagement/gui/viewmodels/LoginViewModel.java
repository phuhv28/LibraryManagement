package librarymanagement.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.UserAuth.AccountService;
import librarymanagement.UserAuth.LoginResult;

public class LoginViewModel {

    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final StringProperty errorTextProperty = new SimpleStringProperty();

    public StringProperty usernameProperty() {
        return usernameProperty;
    }

    public StringProperty passwordProperty() {
        return passwordProperty;
    }

    public String getUsername() {
        return usernameProperty.get();
    }

    public String getPassword() {
        return passwordProperty.get();
    }

    public boolean handleLogin() {
        LoginResult loginResult = AccountService.checkLogin(getUsername(), getPassword());
        errorTextProperty.set(loginResult.getMessage());

        return loginResult == LoginResult.SUCCESS;
    }

    public StringProperty errorLabelTextProperty() {
        return errorTextProperty;
    }
}

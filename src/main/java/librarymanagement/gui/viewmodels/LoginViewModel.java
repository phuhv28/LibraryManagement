package librarymanagement.gui.viewmodels;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.gui.models.AccountService;
import librarymanagement.entity.LoginResult;

public class LoginViewModel {

    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final StringProperty errorTextProperty = new SimpleStringProperty();
    private final AccountService accountService = AccountService.getInstance();

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
        LoginResult loginResult = accountService.checkLogin(getUsername(), getPassword());

        Platform.runLater(() -> {
            errorTextProperty.set(loginResult.getMessage());
        });

        return loginResult == LoginResult.SUCCESS;
    }

    public StringProperty errorLabelTextProperty() {
        return errorTextProperty;
    }
}

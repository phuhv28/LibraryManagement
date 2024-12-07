package librarymanagement.gui.viewmodels;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import librarymanagement.entity.RegistrationResult;
import librarymanagement.gui.models.AccountService;

public class AddUserViewModel {
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final StringProperty emailProperty = new SimpleStringProperty();
    private final StringProperty fullNameProperty = new SimpleStringProperty();
    private final StringProperty confirmPasswordProperty = new SimpleStringProperty();
    private final StringProperty menuAccountProperty = new SimpleStringProperty();
    private final StringProperty resultProperty = new SimpleStringProperty();

    private final AccountService accountService = AccountService.getInstance();

    public String getUsername() {
        return usernameProperty.get();
    }

    public StringProperty usernameProperty() {
        return usernameProperty;
    }

    public String getPassword() {
        return passwordProperty.get();
    }

    public StringProperty passwordProperty() {
        return passwordProperty;
    }

    public String getEmail() {
        return emailProperty.get();
    }

    public StringProperty emailProperty() {
        return emailProperty;
    }

    public String getFullName() {
        return fullNameProperty.get();
    }

    public StringProperty fullNameProperty() {
        return fullNameProperty;
    }

    public String getConfirmPassword() {
        return confirmPasswordProperty.get();
    }

    public StringProperty confirmPasswordProperty() {
        return confirmPasswordProperty;
    }

    public String getMenuAccount() {
        return menuAccountProperty.get();
    }

    public StringProperty menuAccountProperty() {
        return menuAccountProperty;
    }

    public String getResult() {
        return resultProperty.get();
    }

    public StringProperty resultProperty() {
        return resultProperty;
    }


    public RegistrationResult addAccount() {
        if (menuAccountProperty.get().equals("Member")) {
            RegistrationResult result = accountService.addMember(getUsername(), getPassword(), getConfirmPassword(), getFullName(), getEmail());
            Platform.runLater(() -> resultProperty.set(result.getMessage()));
            return result;
        } else {
            RegistrationResult result = accountService.addAdmin(getUsername(), getPassword(), getConfirmPassword(), getFullName(), getEmail());
            Platform.runLater(() -> resultProperty.set(result.getMessage()));
            return result;
        }
    }
}

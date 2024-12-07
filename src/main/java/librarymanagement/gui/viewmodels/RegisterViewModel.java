package librarymanagement.gui.viewmodels;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.gui.models.AccountService;
import librarymanagement.entity.RegistrationResult;

public class RegisterViewModel {
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final StringProperty confirmPasswordProperty = new SimpleStringProperty();
    private final StringProperty errorLabelProperty = new SimpleStringProperty();
    private final StringProperty emailProperty = new SimpleStringProperty();
    private final StringProperty fullNameProperty = new SimpleStringProperty();
    private final AccountService accountService = AccountService.getInstance();

    public boolean handleRegister() {
        RegistrationResult registrationResult = accountService.addMember(getUsername(), getPassword(), getConfirmPassword() , getFullName() ,getEmail());

        Platform.runLater(() -> {
            errorLabelProperty.set(registrationResult.getMessage());
        });

        return registrationResult.equals(RegistrationResult.SUCCESS);
    }

    public StringProperty usernameProperty() {
        return usernameProperty;
    }

    public StringProperty passwordProperty() {
        return passwordProperty;
    }

    public StringProperty confirmPasswordProperty() {
        return confirmPasswordProperty;
    }

    public StringProperty emailProperty() {
        return emailProperty;
    }

    public StringProperty fullNameProperty() {
        return fullNameProperty;
    }


    public String getUsername() {
        return usernameProperty.get();
    }

    public String getPassword() {
        return passwordProperty.get();
    }

    public String getConfirmPassword() {
        return confirmPasswordProperty.get();
    }

    public Property<String> errorLabelProperty() {
        return errorLabelProperty;
    }

    public String getEmail() {
        return emailProperty.get();
    }

    public String getFullName() {
        return fullNameProperty.get();
    }

}

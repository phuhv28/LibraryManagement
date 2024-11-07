package librarymanagement.gui.viewmodels;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import librarymanagement.UserAuth.AccountService;
import librarymanagement.UserAuth.RegistrationResult;

public class RegisterViewModel {
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final StringProperty confirmPasswordProperty = new SimpleStringProperty();
    private final StringProperty errorLabelProperty = new SimpleStringProperty();
    private final StringProperty EmailProperty = new SimpleStringProperty();
    private final StringProperty FullNameProperty = new SimpleStringProperty();

    public boolean handleRegister() {
        RegistrationResult registrationResult = AccountService.getInstance().addUser(getUsername(), getPassword(), getConfirmPassword() , getFullName() ,getEmail());
        errorLabelProperty.set(registrationResult.getMessage());
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

    public StringProperty emailPropertyProperty() {
        return EmailProperty;
    }

    public StringProperty fullNamePropertyProperty() {
        return FullNameProperty;
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
        return EmailProperty.get();
    }

    public String getFullName() {
        return FullNameProperty.get();
    }

}

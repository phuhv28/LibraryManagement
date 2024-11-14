package librarymanagement.gui.viewmodels;

import javafx.beans.property.StringProperty;
import librarymanagement.UserAuth.*;
import javafx.beans.property.SimpleStringProperty;

public class AddUserViewModel {
    private StringProperty usernameProperty = new SimpleStringProperty();
    private StringProperty passwordProperty = new SimpleStringProperty();
    private StringProperty emailProperty = new SimpleStringProperty();
    private StringProperty fullNameProperty = new SimpleStringProperty();
    private StringProperty confirmPasswordProperty = new SimpleStringProperty();

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

    public void addUserAccount() {
        AccountService.getInstance().addUser(getUsername(), getPassword(), getConfirmPassword(), getFullName(), getEmail());
    }

    public void createAdminAccount() {
        AccountService.getInstance().addUser(getUsername(), getPassword(), getConfirmPassword(), getFullName(), getEmail());
        AccountService.getInstance().addAdmin(getUsername(), getPassword());
    }

    public void setAsAdmin() {
        AccountService.getInstance().addAdmin(getUsername(), getPassword());
    }

    public boolean checkIfAccountExists() {
        return true;
    }
}

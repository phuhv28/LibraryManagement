package librarymanagement.gui.viewmodels;

import javafx.beans.property.StringProperty;
import librarymanagement.UserAuth.*;
import javafx.beans.property.SimpleStringProperty;

public class AddUserViewModel {
    private StringProperty usernameProperty = new SimpleStringProperty();
    private StringProperty passwordProperty = new SimpleStringProperty();
    private StringProperty emailProperty = new SimpleStringProperty();
    private StringProperty fullnameProperty = new SimpleStringProperty();
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

    public String getFullname() {
        return fullnameProperty.get();
    }

    public StringProperty fullnameProperty() {
        return fullnameProperty;
    }

    public String getConfirmPassword() {
        return confirmPasswordProperty.get();
    }

    public StringProperty confirmPasswordProperty() {
        return confirmPasswordProperty;
    }

    public void addUserAccount() {
        AccountService.getInstance().addUser(getUsername(), getPassword(), getConfirmPassword(), getFullname(), getEmail());
    }

    public void createAdminAccount() {
        AccountService.getInstance().addUser(getUsername(), getPassword(), getConfirmPassword(), getFullname(), getEmail());
        AccountService.getInstance().addAdmin(getUsername(), getPassword());
    }

    public void setAsAdmin() {
        AccountService.getInstance().addAdmin(getUsername(), getPassword());
    }

    public boolean checkIfAccountExists() {
        return true;
    }
}

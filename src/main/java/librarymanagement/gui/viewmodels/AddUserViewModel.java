package librarymanagement.gui.viewmodels;

import javafx.beans.property.StringProperty;
import librarymanagement.UserAuth.*;
import javafx.beans.property.SimpleStringProperty;

public class AddUserViewModel {
    private StringProperty UserNameProperty = new SimpleStringProperty();
    private StringProperty PasswordProperty = new SimpleStringProperty();
    private StringProperty EmailProperty = new SimpleStringProperty();
    private StringProperty FullNameProperty = new SimpleStringProperty();
    private StringProperty ConfirmPasswordProperty = new SimpleStringProperty();

    public String getUserName() {
        return UserNameProperty.get();
    }

    public StringProperty userNamePropertyProperty() {
        return UserNameProperty;
    }

    public String getPassword() {
        return PasswordProperty.get();
    }

    public StringProperty passwordPropertyProperty() {
        return PasswordProperty;
    }

    public String getEmail() {
        return EmailProperty.get();
    }

    public StringProperty emailPropertyProperty() {
        return EmailProperty;
    }

    public String getFullName() {
        return FullNameProperty.get();
    }

    public StringProperty fullNamePropertyProperty() {
        return FullNameProperty;
    }

    public String getConfirmPassword() {
        return ConfirmPasswordProperty.get();
    }

    public StringProperty confirmPasswordPropertyProperty() {
        return ConfirmPasswordProperty;
    }

    public void AddAccountUser() {
         AccountService.getInstance().addUser(getUserName() , getPassword() , getConfirmPassword() ,getFullName() ,getEmail() );
    }

    public void CreatAccountAdmin() {
        AccountService.getInstance().addUser(getUserName() , getPassword() , getConfirmPassword() ,getFullName() ,getEmail() );
        AccountService.getInstance().addAdmin(getUserName() , getPassword());
    }

    public void AddAccountAdmin() {
        AccountService.getInstance().addAdmin(getUserName() , getPassword());
    }

    public boolean checkIfAccountExists() {
        return true;
    }
}

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
    private StringProperty menuAccountProperty = new SimpleStringProperty();
    private StringProperty menuFunctionProperty = new SimpleStringProperty();
    private StringProperty errorTextProperty = new SimpleStringProperty();

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

    public String getMenuFunction() {
        return menuFunctionProperty.get();
    }

    public StringProperty menuFunctionProperty() {
        return menuFunctionProperty;
    }

    public String getErrorText() {
        return errorTextProperty.get();
    }

    public StringProperty errorTextProperty() {
        return errorTextProperty;
    }

    public void addUserOrAdmin() {
        if ("User".equals(getMenuAccount())) {
            if (!getPassword().equals(getConfirmPassword())) {
                loadError(AddUserResult.PASSWORD_NOT_MATCH);
                return;
            }
            if (checkIfAccountExists()) {
                loadError(AddUserResult.ACCOUNT_EXIST);
                return;
            }
            addUserAccount();
            loadError(AddUserResult.SUCCESS_CREAT);
            return;
        } else if ("Admin".equals(getMenuAccount())) {
            if ("Create Admin Account".equals(getMenuFunction())) {
                if (chechAccountIsAdmin()) {
                    loadError(AddUserResult.ACCOUNT_NOT_ADMIN);
                    return;
                }
                if (!getPassword().equals(getConfirmPassword())) {
                    loadError(AddUserResult.PASSWORD_NOT_MATCH);
                    return;
                }
                if (checkIfAccountExists()) {
                    loadError(AddUserResult.ACCOUNT_EXIST);
                    return;
                }
                createAdminAccount();
                loadError(AddUserResult.SUCCESS_CREAT);
                return;
            } else if ("Set As Admin".equals(getMenuFunction())) {
                if (chechAccountIsAdmin()) {
                    loadError(AddUserResult.ACCOUNT_NOT_ADMIN);
                    return;
                }
                else if (checkIfAccountExists()) {
                    loadError(AddUserResult.ACCOUNT_EXIST);
                    return;
                }
                setAsAdmin();
                loadError(AddUserResult.SUCCESS_ADMIN_SET);
                return;
            }
        }
    }
    public void loadError(AddUserResult addUser) {
        System.out.println(addUser.getMessage());
        errorTextProperty.set(addUser.getMessage());
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

    public boolean chechAccountIsAdmin() {
        return AccountService.getInstance().isAdmin(AccountService.getInstance().getCurrentAccount().getUsername());
    }

    public boolean checkIfAccountExists() {
        return true;
    }
}

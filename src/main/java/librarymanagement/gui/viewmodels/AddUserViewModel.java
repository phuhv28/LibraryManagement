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

    public String addUserOrAdmin() {
        if ("User".equals(getMenuAccount())) {
            if (!getPassword().equals(getConfirmPassword())) {
                return "error_password_not_match";
            }
            if (checkIfAccountExists()) {
                return "error_account_exist";
            }
            addUserAccount();
            return "success_user";
        } else if ("Admin".equals(getMenuAccount())) {
            if ("Create Admin Account".equals(getMenuFunction())) {
                if (chechAccountIsAdmin()) {
                    return "error_not_admin";
                }
                if (!getPassword().equals(getConfirmPassword())) {
                    return "error_password_not_match";
                }
                if (checkIfAccountExists()) {
                    return "error_account_exist";
                }
                createAdminAccount();
                return "success_admin_created";
            } else if ("Set As Admin".equals(getMenuFunction())) {
                if (chechAccountIsAdmin()) {
                    return "error_not_admin";
                }
                if (checkIfAccountExists()) {
                    return "error_add_admin";
                }
                setAsAdmin();
                return "success_admin_set";
            }
        }
        return "error_unknown";
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

package librarymanagement.gui.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import librarymanagement.entity.User;
import librarymanagement.gui.models.AccountService;

import java.util.List;

public class ManageUserViewModel {
    private final AccountService accountService = AccountService.getInstance();
    private ObservableList<User> accountsListProperty = null;

    public ManageUserViewModel() {
        List<User> users = accountService.getAllAccounts();
        if (users != null) {
            accountsListProperty = FXCollections.observableArrayList(users);
        }
    }

    public ObservableList<User> accountsListProperty() {
        return accountsListProperty;
    }
}

package librarymanagement.gui.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import librarymanagement.entity.Account;
import librarymanagement.gui.models.AccountService;

import java.util.List;

public class ManageUserViewModel {
    private final AccountService accountService = AccountService.getInstance();
    private ObservableList<Account> accountsListProperty = null;

    public ManageUserViewModel() {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts != null) {
            accountsListProperty = FXCollections.observableArrayList(accounts);
        }
    }

    public ObservableList<Account> accountsListProperty() {
        return accountsListProperty;
    }
}

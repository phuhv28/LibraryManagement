package librarymanagement.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import librarymanagement.entity.User;
import librarymanagement.gui.viewmodels.ManageUserViewModel;
import librarymanagement.utils.SceneHistoryStack;

import java.io.IOException;

public class ManageUserController {

    @FXML
    private TableView<User> tbUserList;

    @FXML
    private TableColumn<User, String> userIdCol;

    @FXML
    private TableColumn<User, String> usernameCol;

    @FXML
    private TableColumn<User, String> fullNameCol;

    @FXML
    private TableColumn<User, Integer> borrowedCountCol;

    @FXML
    private AnchorPane manageUserPane;

    @FXML
    private Button btAddAccount;

    private final ManageUserViewModel viewModel = new ManageUserViewModel();

    @FXML
    public void initialize() {
        btAddAccount.setOnAction(_ -> goToAddUser());
        tbUserList.setItems(viewModel.accountsListProperty());
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        borrowedCountCol.setCellValueFactory(new PropertyValueFactory<>("numberOfBooksIsBorrowing"));

        tbUserList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                User selectedUser = tbUserList.getSelectionModel().getSelectedItem();

                if (selectedUser != null) {
                    UserStatusController.newInstance(selectedUser);
                }
            }
        });
    }

    private void goToAddUser() {
        SceneHistoryStack.listPreviousFxmlFile.push(SceneHistoryStack.previousFxmlFile);
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/AddUser.fxml"));
            manageUserPane.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SceneHistoryStack.previousFxmlFile = "AddUser.fxml";

    }
}

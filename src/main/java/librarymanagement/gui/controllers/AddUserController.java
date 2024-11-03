package librarymanagement.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class AddUserController {

    @FXML
    private AnchorPane AddUserScene;
    @FXML
    private Button btHome;
    @FXML
    private Button btSetting;
    @FXML
    private Button btUser;
    @FXML
    private Button btAddAccount;
    @FXML
    private TextField tfUserName;
    @FXML
    private MenuButton mbFunction;
    @FXML
    private MenuItem mbCreat;
    @FXML
    private MenuItem mbAdd;
    @FXML
    private MenuButton mbAccount;
    @FXML
    private MenuItem mbUser;
    @FXML
    private MenuItem mbAdmin;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfConfirmPassword;
    @FXML
    private TextField tfFullName;
    @FXML
    private Label lbSelectFunction;
    @FXML
    private Label lbEnterFullName;
    @FXML
    private Label lbEnterEmail;
    @FXML
    private Label lbEnterPassword;
    @FXML
    private Label lbConfirmPassword;
    @FXML
    private Label lbEnterUserName;


    @FXML
    public void initialize() {
        btHome.setOnAction(event -> loadHome());
        btSetting.setOnAction(event -> loadSetting());
        btUser.setOnAction(event -> loadUser());
        mbFunction.setOnAction(event -> {
            loadAtStart();
        });
    }

    private void loadScene(String fxmlFile) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource("/FXML/" + fxmlFile));
            AddUserScene.getChildren().setAll(newPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHome(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("HomeScene.fxml");
        previousSceneToReturn.previousFxmlFile2="HomeScene.fxml";}
    private void loadSetting(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("SettingScene.fxml");
        previousSceneToReturn.previousFxmlFile2="SettingScene.fxml";}
    private void loadUser(){
        previousSceneToReturn.previousFxmlFIle1=previousSceneToReturn.previousFxmlFile2;
        loadScene("UserScene.fxml");
        previousSceneToReturn.previousFxmlFile2="UserScene.fxml";}

    private void loadAtStart() {
        mbFunction.setVisible(false);
        mbCreat.setVisible(false);
        mbAdd.setVisible(false);
        tfEmail.setVisible(false);
        tfPassword.setVisible(false);
        tfConfirmPassword.setVisible(false);
        tfFullName.setVisible(false);
        tfUserName.setVisible(false);
        btAddAccount.setVisible(false);
        lbConfirmPassword.setVisible(false);
        lbSelectFunction.setVisible(false);
        lbEnterFullName.setVisible(false);
        lbEnterEmail.setVisible(false);
        lbEnterPassword.setVisible(false);
        lbEnterUserName.setVisible(false);
        lbConfirmPassword.setVisible(false);
    }

    private void loadGraphicWithUserCreat() {
        mbFunction.setVisible(false);
        mbCreat.setVisible(false);
        mbAdd.setVisible(false);
        tfUserName.setDisable(true);
        tfPassword.setDisable(true);
        tfConfirmPassword.setDisable(true);
        tfFullName.setDisable(true);
        tfEmail.setDisable(true);
        btAddAccount.setVisible(true);
        lbConfirmPassword.setVisible(true);
        lbEnterFullName.setVisible(true);
        lbEnterEmail.setVisible(true);
        lbEnterPassword.setVisible(true);
        lbEnterUserName.setVisible(true);
    }

    private void loadGraphicWithAdminCreatr() {
        mbFunction.setVisible(true);
        mbCreat.setVisible(true);
        mbAdd.setVisible(true);
        tfUserName.setDisable(false);
        tfPassword.setDisable(false);
        tfConfirmPassword.setDisable(false);
        tfFullName.setDisable(false);
        tfEmail.setDisable(false);
        btAddAccount.setVisible(true);
        lbConfirmPassword.setVisible(true);
        lbSelectFunction.setVisible(true);
        lbEnterFullName.setVisible(true);
        lbEnterEmail.setVisible(true);
        lbEnterPassword.setVisible(true);
        lbEnterUserName.setVisible(true);
    }

    private void loadGraphicWithAdminAdd() {
        mbFunction.setVisible(true);
        mbCreat.setVisible(true);
        mbAdd.setVisible(true);
        tfUserName.setDisable(true);
        tfEmail.setDisable(true);
        btAddAccount.setVisible(true);
        lbSelectFunction.setVisible(true);
        lbEnterUserName.setVisible(true);
        lbEnterEmail.setVisible(true);
    }

    private void clear() {
        tfUserName.clear();
        tfPassword.clear();
        tfConfirmPassword.clear();
        tfFullName.clear();
        tfEmail.clear();
        mbFunction.setText("function");
        mbAccount.setText("account");
    }


}


package com.example.cards;

import animations.Shaker;
import db.DBhandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label alertLabel;
    @FXML
    private Label answer;
    @FXML
    private Button enterButton;

    @FXML
    private Label howToUseLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label signupButton;

    @FXML
    private TextField usernameField;
    @FXML
    void initialize(){
        DBhandler dBhandler = new DBhandler();

        howToUseLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                answer.setText("- kak hochesh, no luchse zapisivat slova na raznih yazikah");
            }
        });

        enterButton.setOnAction(actionEvent -> {
            if(!(usernameField.getText().equals("") || passwordField.getText().equals("")) &&
                    dBhandler.login(usernameField.getText(),passwordField.getText())){
                try {
                    enterButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("allWordsPage.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            } else {
                Shaker usernameShaker = new Shaker(usernameField);
                Shaker passwordShaker = new Shaker(passwordField);
                usernameShaker.shake();
                passwordShaker.shake();
                usernameField.setText("");
                passwordField.setText("");
                alertLabel.setText("Invalid login or password");
            }
        });

        signupButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    enterButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("signup.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });
    }
}
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignupController {
    @FXML
    private Label alertLabel;

    @FXML
    private Label backLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameField;
    @FXML
    void initialize(){
        backLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            DBhandler dBhandler = new DBhandler();

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    backLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("hello-view.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        signupButton.setOnAction(actionEvent -> {
            DBhandler dBhandler = new DBhandler();

            if(!(usernameField.getText().equals("") || passwordField.getText().equals("") ||
                    dBhandler.checkExistance(usernameField.getText()))){
                dBhandler.signup(usernameField.getText(),passwordField.getText());

                try {

                    signupButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("hello-view.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }

            } else {
                Shaker usernameShaker = new Shaker(usernameField);
                Shaker passwordShaker = new Shaker(passwordField);
                usernameShaker.shake();
                passwordShaker.shake();
                usernameField.setText("");
                passwordField.setText("");
                alertLabel.setText("Invalid login or password (or username is already in use)");
            }
        });
    }
}

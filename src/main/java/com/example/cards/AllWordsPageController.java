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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Card;
import objects.CardsHandler;
import objects.CurrentUser;

import java.util.ArrayList;

public class AllWordsPageController {
    @FXML
    private Button addWordButton;

    @FXML
    private Label alertLabel;

    @FXML
    private Label currentUsernameLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<String> englishWordsList;

    @FXML
    private Label logoutLabel;

    @FXML
    private Button makeEasyButton;

    @FXML
    private Button makeHardButton;

    @FXML
    private TextField translationField;

    @FXML
    private Label translationLabel;

    @FXML
    private TextField wordField;
    @FXML
    void initialize(){
        DBhandler dBhandler = new DBhandler();

        reloadList();
        currentUsernameLabel.setText(CurrentUser.username);

        logoutLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CurrentUser.id = 0;
                CurrentUser.username = "";
                try {
                    logoutLabel.getScene().getWindow().hide();
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

        addWordButton.setOnAction(actionEvent -> {
            if(!(wordField.getText().equals("") || translationField.getText().equals(""))){
                dBhandler.addCard(wordField.getText(),translationField.getText(),CurrentUser.id);
                reloadList();
            } else {
                Shaker wordShaker = new Shaker(wordField);
                Shaker translationShaker = new Shaker(translationField);
                wordShaker.shake();
                translationShaker.shake();
                wordField.setText("");
                translationField.setText("");
                alertLabel.setText("Nuh uh! Fill the gaps pls");
            }
        });

        englishWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = englishWordsList.getSelectionModel().getSelectedIndex();
                ArrayList<Card> cards = dBhandler.getCardsById(CurrentUser.id);
                CardsHandler.card_id = cards.get(index).getId();
                translationLabel.setText(cards.get(index).getTranslation());
                reloadList();
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            dBhandler.deleteCard(CardsHandler.card_id);
            reloadList();
        });

        makeEasyButton.setOnAction(actionEvent -> {
            dBhandler.makeCardEasy(CardsHandler.card_id);
            reloadList();
        });

        makeHardButton.setOnAction(actionEvent -> {
            dBhandler.makeCardHard(CardsHandler.card_id);
            reloadList();
        });
    }

    public void reloadList(){
        englishWordsList.getItems().clear();
        DBhandler dBhandler = new DBhandler();
        ArrayList<Card> cards = dBhandler.getCardsById(CurrentUser.id);
        for (int i = 0; i < cards.size(); i++) {
            if(!cards.get(i).getStatus().equals("DEAD")){
                englishWordsList.getItems().add(cards.get(i).getWord() + " (" +
                        cards.get(i).getStatus() + ")");
            }
        }
    }
}

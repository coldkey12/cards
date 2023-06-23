package db;

import objects.CurrentUser;
import objects.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;

public class DBhandler {
    Connection dbConnection;
    public DBhandler(){};

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/cards";
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(connectionString, "root", "");
        return this.dbConnection;
    }

    public boolean login(String username, String password){
        String insert = "SELECT * FROM " + Const.tables_user + " WHERE " + Const.user_username +
                " =? && " + Const.user_password + " =?";
        boolean flag = false;
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                flag=true;
                CurrentUser.id = resultSet.getInt(1);
                CurrentUser.username = resultSet.getString(2);
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return flag;
    }

    public void signup(String username, String password){
        String insert = "INSERT INTO user(username,password) VALUES(?,?)";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public boolean checkExistance(String username){
        String insert = "SELECT * FROM user WHERE username =?";
        boolean flag = false;
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                flag=true;
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return flag;
    }

    public ArrayList<Card> getCardsById(int id){
        String insert = "SELECT * FROM card WHERE user_id =?";
        ArrayList<Card> cards = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                cards.add(new Card(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),
                        resultSet.getInt(5)));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return cards;
    }

    public void addCard(String word, String translation, int user_id){
        String insert = "INSERT INTO card(word,translation,status,user_id) VALUES(?,?,?,?)";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,word);
            preparedStatement.setString(2,translation);
            preparedStatement.setString(3,"Hard");
            preparedStatement.setInt(4,user_id);
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void deleteCard(int id){
        String insert = "UPDATE " + Const.tables_card + " \n" +
                "SET status =? \n" +
                "WHERE id =?";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"DEAD");
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void makeCardHard(int id){
        String insert = "UPDATE " + Const.tables_card + " \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"Hard");
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void makeCardEasy(int id){
        String insert = "UPDATE " + Const.tables_card + " \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"Easy");
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public ArrayList<Card> allCards(){
        String insert = "SELECT * FROM card";
        ArrayList<Card> cards = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                cards.add(new Card(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),
                        resultSet.getInt(5)));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return cards;
    }
}

package backend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import db.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/frontend/FXML/Login.fxml"));
        Scene newScene = new Scene(root);
        newScene.getStylesheets().add("/frontend/stylesheet.css");
        primaryStage.setTitle("Login");
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Manager = new DBManager("testmate-db");
        Crypto = new Crypto();
        Auth = new Auth(Manager, Crypto);
        Store = new TestStore();

        launch(args);
    }

    public static TestStore Store;
    public static DBManager Manager;
    public static Crypto Crypto;
    public static Auth Auth;
}

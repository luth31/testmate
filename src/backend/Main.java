package backend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import db.*;
import frontend.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../frontend/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Manager = new DBManager("testmate-db");
        Crypto = new Crypto();
        Auth = new Auth(Manager, Crypto);
        String A = Crypto.Hash("Test");
        String B = Crypto.Hash("test");
        String C = Crypto.Hash("XD");
        String D = Crypto.Hash("test");
        boolean isA = Crypto._Engine.verify(A, "test".toCharArray());
        boolean isB = Crypto._Engine.verify(B, "test".toCharArray());
        boolean isC = Crypto._Engine.verify(C, "test".toCharArray());
        boolean isD = Crypto._Engine.verify(D, "test".toCharArray());
        launch(args);
    }

    public static DBManager Manager;
    public static Crypto Crypto;
    public static Auth Auth;
}

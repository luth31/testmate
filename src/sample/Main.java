package sample;

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

import javax.swing.table.TableColumn;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();

        Connection db = null;
        try {
            String url = "jdbc:sqlite:auth.db";
            db = DriverManager.getConnection(url);
            if (db != null) {
                DatabaseMetaData meta = db.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        Table test = new Table("auth");
        test.AddColumn("username", Table.ColumnType.TEXT);
        test.AddColumn("password", Table.ColumnType.TEXT);
        System.out.println(test.CreateString());
    }


    public static void main(String[] args) {
        launch(args);
    }
}

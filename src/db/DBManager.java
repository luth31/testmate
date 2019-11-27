package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DBManager {
    public DBManager(String Path) {
        ConnectionURL = Path;
        Connect();
        InitTables();
        VerifyDB();
    }

    // Connect to the SQLite DB
    public boolean Connect() {
        try {
            _DB = DriverManager.getConnection("jdbc:sqlite:" + ConnectionURL);
            System.out.println("Connected to the DB.");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (_DB != null)
                    _DB.close();
            }
            catch (SQLException e2) {
                System.out.println(e2.getMessage());
                System.exit(1);
            }
            System.exit(1);
        }
        return true;
    }

    // Verify if needed tables exist in DB and create them if not
    public void VerifyDB() {
        Iterator<Table> iter = Tables.iterator();
        while(iter.hasNext()) {
            Table temp = iter.next();
            if (!TableExists(temp.Name))
                CreateTable(temp);
        }
    }

    // Check if given table exists in DB
    public boolean TableExists(String Name) {
        ResultSet res = RawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=" + Name);
        try {
            res.next();
            if (res.getString("name") == Name)
                return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Create table from given Table object
    public void CreateTable(Table TableData) {
        RawQuery(TableData.CreateString());
    }

    // Drop table by given name
    public void DropTable(String Name) {
        String temp = "DROP TABLE IF EXISTS " + Name + ";";
        RawQuery(temp);
    }

    // Helper function; create and recreate all tables; not yet used
    public void InitDB() {
        Iterator<Table> iter = Tables.iterator();
        while(iter.hasNext()) {
            Table temp = iter.next();
            DropTable(temp.Name);
            CreateTable(temp);
        }
    }

    // Init Tables array
    public void InitTables() {
        Tables = new ArrayList<Table>();
        Table Auth = new Table("auth");
        Auth.AddColumn("username", Table.ColumnType.TEXT);
        Auth.AddColumn("salt", Table.ColumnType.TEXT);
        Auth.AddColumn("password", Table.ColumnType.TEXT);
        Tables.add(Auth);
    }

    // Run a query given by Query string
    public ResultSet RawQuery(String Query) {
        ResultSet res = null;
        try {
            Statement stmt = _DB.createStatement();
            res = stmt.executeQuery(Query);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return res;
    }

    private ArrayList<Table> Tables = null; // List of expected tables
    private Connection _DB = null;
    private String ConnectionURL;
}

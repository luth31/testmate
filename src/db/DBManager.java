package db;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DBManager {
    public DBManager(String Path) {
        ConnectionURL = Path;
        Connect();
        InitTables();
    }

    // Connect to the SQLite DB
    private boolean Connect() {
        try {
            _DB = DriverManager.getConnection("jdbc:sqlite:" + ConnectionURL);
            System.out.println("Connected to the DB.");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private void InitTables() {
        _AuthTable = new AuthTable("auth", this);
        _TestTable = new TestTable("tests", this);
        InitTable(_AuthTable);
        InitTable(_TestTable);
    }

    private void InitTable(Table Table) {
        for (HashMap.Entry<String, db.Table.ColumnType> Column : Table.Columns.entrySet()) {
            if (!ColumnExists(Table, Column.getKey())) {
                DropTable(Table);
                break;
            }
        }
        if (!TableExists(Table))
            CreateTable(Table);
    }

    // DB FUNCTIONS

    // Check if given column from a table exists in DB
    public boolean ColumnExists(Table Table, String Column) {
        try {
            ResultSet Res = _DB.getMetaData().getColumns(null, null, Table.Name, Column);
            Res.next();
            return Res.getRow() > 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Check if given table exists in DB
    public boolean TableExists(Table Table) {
        try {
            ResultSet Res = _DB.getMetaData().getTables(null, null, Table.Name, null);
            Res.next();
            return Res.getRow() > 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Create table from given table object
    public void CreateTable(Table Table) {
        RawQuery(Table.CreateString());
    }

    // Drop table by given name
    public void DropTable(Table Table) {
        String temp = "DROP TABLE IF EXISTS " + Table.Name + ";";
        RawQuery(temp);
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

    public PreparedStatement PreparedStatement(String Query) {
        try {
            PreparedStatement stmt = _DB.prepareStatement(Query);
            return stmt;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public AuthTable _AuthTable;
    public TestTable _TestTable;
    private Connection _DB = null;
    private String ConnectionURL;
}

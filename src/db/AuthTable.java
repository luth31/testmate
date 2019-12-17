package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class AuthTable extends Table {
    public AuthTable(String Name, DBManager Manager) {
        super(Name, Manager);
        this.AddColumn("username", ColumnType.TEXT);
        this.AddColumn("password", ColumnType.TEXT);
        this.AddColumn("isAdmin", ColumnType.INTEGER);
    }

    public boolean UsernameExists(String Username) {
        try {
            PreparedStatement stmt = _Manager.PreparedStatement("SELECT * FROM " + this.Name + " WHERE username=?");
            stmt.setString(1, Username);

            ResultSet Res = stmt.executeQuery();
            Res.next();
            return Res.getRow() > 0;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void InsertUser(String Username, String Password) {
        try {
            PreparedStatement stmt = _Manager.PreparedStatement("INSERT INTO " + this.Name + "(username,password,isAdmin) VALUES(?,?,0)");
            stmt.setString(1, Username);
            stmt.setString(2, Password);

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public String GetPassword(String Username) {
        try {
            PreparedStatement stmt = _Manager.PreparedStatement("SELECT password FROM " + this.Name + " WHERE username=?");
            stmt.setString(1, Username);

            ResultSet Res = stmt.executeQuery();
            if (Res.next() != false)
                return Res.getString("password");
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<String> GetAllUsers() {
        ArrayList<String> Usernames = new ArrayList<String>();
        try {
            String Query = "SELECT username FROM " + this.Name;
            ResultSet Res = _Manager.RawQuery(Query);
            while(Res.next() != false) {
                Usernames.add(Res.getString("username"));
            }
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return Usernames;
    }

    public boolean IsPrivileged(String Username) {
        if (!UsernameExists(Username))
            return false;
        try {
            PreparedStatement stmt = _Manager.PreparedStatement("SELECT isAdmin FROM" + this.Name + " WHERE username=?");
            stmt.setString(1, Username);

            ResultSet Res = stmt.executeQuery();
            Res.next();
            return Res.getInt("isAdmin") == 1;
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void MakePrivileged(String Username) {
        if (!UsernameExists(Username))
            return;
        try {
            PreparedStatement stmt = _Manager.PreparedStatement("UPDATE " + this.Name + " SET isAdmin=1 WHERE username=?");
            stmt.setString(1, Username);

            stmt.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }
}

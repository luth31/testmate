package db;

import backend.TestModel;
import backend.TestStore;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestTable extends Table {
    public TestTable(String Name, DBManager Manager) {
        super(Name, Manager);
        this.AddColumn("title", ColumnType.TEXT);
        this.AddColumn("category", ColumnType.TEXT);
    }

    public void DeleteTest(TestModel Test) {
        DeleteTest(Test.id);
    }

    public void DeleteTest(int Id) {
        _Manager.RawExecute("DELETE FROM " + this.Name + " WHERE rowid=" + Id);
    }

    public void UpdateTest(TestModel Test){
        PreparedStatement stmt = _Manager.PreparedStatement("UPDATE " + this.Name + " SET title=?,category=?");
        try {
            stmt.setString(1, Test.title);
            stmt.setString(2, Test.category);
            stmt.execute();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int CreateTest(TestModel Test) {
        PreparedStatement stmt = _Manager.PreparedStatement("INSERT INTO " + this.Name + "(title,category) VALUES(?,?)");
        try {
            stmt.setString(1, Test.title);
            stmt.setString(2, Test.category);
            stmt.execute();
            ResultSet Res = stmt.getGeneratedKeys();
            while(Res.next())
                return Res.getInt(1);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public ArrayList<TestModel> GetAllTests() {
        ArrayList<TestModel> Tests = new ArrayList<TestModel>();
        try {
            ResultSet Res = _Manager.RawQuery("SELECT rowid, title, category FROM " + this.Name);
            while(Res.next()) {
                Tests.add(new TestModel(Res.getInt("rowid"),
                        Res.getString("title"),
                        Res.getString("category")));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        return Tests;
    }
}

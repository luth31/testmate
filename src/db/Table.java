package db;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;

// Table model
public class Table {
    public Table(String TableName, DBManager Manager) {
        _Manager = Manager;
        Name = TableName;
        Columns = new HashMap<String,ColumnType>();
    }

    // Adds a column
    public void AddColumn(String Name, ColumnType Type) {
        if (!Columns.containsKey(Name))
            Columns.put(Name, Type);
        return;
    }

    public String CreateString() {
        if (Columns.isEmpty())
            return "";
        String temp = "CREATE TABLE " + Name + " (";
        Iterator<HashMap.Entry<String, ColumnType>> iter = Columns.entrySet().iterator();
        HashMap.Entry<String, ColumnType> entry = null;
        while (iter.hasNext()) {
            if (entry != null)
                temp += ", ";
            entry = iter.next();
            temp += entry.getKey() + " ";
            switch (entry.getValue()) {
                case INTEGER:
                    temp += "INTEGER";
                    break;
                case REAL:
                    temp += "REAL";
                    break;
                case TEXT:
                    temp += "TEXT";
                    break;
            }
        }
        temp += ");";
        return temp;
    }

    DBManager _Manager;  // Reference to DBManager to use RawQuery
    String Name; // Table name
    public HashMap<String, ColumnType> Columns; // Name and type of the column

    public enum ColumnType {
        INTEGER,
        REAL,
        TEXT
    }
}

package db;

import java.util.HashMap;
import java.util.Iterator;

// ToDo: Include more data types
public class Table {
    public Table(String TableName) {
        Name = TableName;
        Columns = new HashMap<String,ColumnType>();
    }

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

    String Name; // Table name
    HashMap<String, ColumnType> Columns; // Name and type of the column

    public enum ColumnType {
        INTEGER,
        REAL,
        TEXT
    }
}

package db;

public class TestTable extends Table {
    public TestTable(String Name, DBManager Manager) {
        super(Name, Manager);
        this.AddColumn("title", ColumnType.TEXT);
        this.AddColumn("category", ColumnType.TEXT);
    }


}

package db;

public class QuestionTable extends Table {
    public QuestionTable(String Name, DBManager Manager) {
        super(Name, Manager);
        this.AddColumn("testId", ColumnType.INTEGER);
        this.AddColumn("question", ColumnType.TEXT);
        this.AddColumn("answer1", ColumnType.TEXT);
        this.AddColumn("answer2", ColumnType.TEXT);
        this.AddColumn("answer3", ColumnType.TEXT);
        this.AddColumn("answer4", ColumnType.TEXT);
        this.AddColumn("correctAnswer", ColumnType.INTEGER);
    }

}

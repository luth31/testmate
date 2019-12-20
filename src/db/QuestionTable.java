package db;

import backend.QuestionModel;
import backend.TestModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public void DeleteQuestion(QuestionModel Question) {
        DeleteQuestion(Question.id);
    }

    public void DeleteQuestion(int Id) {
        _Manager.RawExecute("DELETE FROM " + this.Name + " WHERE rowid=" + Id);
    }

    public int CreateQuestion(int TestId, QuestionModel Question) {
        PreparedStatement stmt = _Manager.PreparedStatement("INSERT INTO " + this.Name + "(testId,question,answer1,answer2,answer3,answer4,correctAnswer) VALUES(?,?,?,?,?,?,?)");
        try {
            stmt.setInt(1, TestId);
            stmt.setString(2, Question.text);
            stmt.setString(3, Question.answers[0]);
            stmt.setString(4, Question.answers[1]);
            stmt.setString(5, Question.answers[2]);
            stmt.setString(6, Question.answers[3]);
            stmt.setInt(7, Question.answer);
            stmt.execute();
            ResultSet Res = stmt.getGeneratedKeys();
            while (Res.next())
                return Res.getInt(1);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public void UpdateQuestion(int TestId, QuestionModel Question) {
        PreparedStatement stmt = _Manager.PreparedStatement("UPDATE " + this.Name + " SET testId=?,question=?,answer1=?,answer2=?,answer3=?,answer4=?,correctAnswer=?");
        try {
            stmt.setInt(1, TestId);
            stmt.setString(2, Question.text);
            stmt.setString(3, Question.answers[0]);
            stmt.setString(4, Question.answers[1]);
            stmt.setString(5, Question.answers[2]);
            stmt.setString(6, Question.answers[3]);
            stmt.setInt(7, Question.answer);
            stmt.execute();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<QuestionModel> GetQuestionsFor(TestModel Test) {
        ArrayList<QuestionModel> Questions = new ArrayList<QuestionModel>();
        try {
            PreparedStatement stmt = _Manager.PreparedStatement("SELECT rowid,* FROM " + this.Name + " WHERE testId=?");
            stmt.setInt(1, Test.id);
            ResultSet Res = stmt.executeQuery();
            while(Res.next()) {
                Questions.add(new QuestionModel(Res.getInt("rowid"),
                        Res.getString("question"),
                        Res.getString("answer1"),
                        Res.getString("answer2"),
                        Res.getString("answer3"),
                        Res.getString("answer4"),
                        Res.getInt("correctAnswer")));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        return Questions;
    }
}

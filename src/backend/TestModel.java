package backend;

import java.util.ArrayList;

public class TestModel {
    public TestModel(int Id, String Title, String Category) {
        id = Id;
        title = Title;
        category = Category;
        questions = new ArrayList<QuestionModel>();
    }

    public void AddQuestion(int Id, String Text, String FirstAnswer, String SecondAnswer, String ThirdAnswer, String ForthAnswer, int CorrectAnswer) {
        questions.add(new QuestionModel(Id, Text, FirstAnswer, SecondAnswer, ThirdAnswer, ForthAnswer, CorrectAnswer));
    }

    public void AddQuestion(QuestionModel Question) {
        questions.add(Question);
    }
    public int id;
    public String title;
    public String category;
    public ArrayList<QuestionModel> questions;
}
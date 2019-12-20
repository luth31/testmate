package backend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

public class TestStore {
    public TestStore() {
        Tests = new ArrayList<TestModel>(Main.Manager._TestTable.GetAllTests());
        Categories = new ArrayList<String>();
        Categories.add("All");
        for (int i = 0; i < Tests.size(); ++i) {
            if (!Categories.contains(Tests.get(i).category))
                Categories.add(Tests.get(i).category);
            ArrayList<QuestionModel> Questions = Main.Manager._QuestionTable.GetQuestionsFor(Tests.get(i));
            for (int j = 0; j < Questions.size(); ++j) {
                Tests.get(i).AddQuestion(Questions.get(j));
            }
        }
    }

    public void CreateTest(TestModel Test) {
        int Id = Main.Manager._TestTable.CreateTest(Test);
        Test.id = Id;
        Tests.add(Test);
        for (int i = 0; i < Test.questions.size(); ++i) {
            int Qid = Main.Manager._QuestionTable.CreateQuestion(Test.id, Test.questions.get(i));
        }
    }

    public void FlushCategories() {
        Categories.clear();
        Categories.add("All");
        for (int i = 0; i < Tests.size(); ++i) {
            if (!Categories.contains(Tests.get(i).category))
                Categories.add(Tests.get(i).category);
        }
    }

    public ArrayList<TestModel> GetByCategory(String Category) {
        if (Category.equals("All"))
            return Tests;
        ArrayList<TestModel> Filtered = new ArrayList<TestModel>();
        for (int i = 0; i < Tests.size(); ++i) {
            if (Tests.get(i).category.equals(Category))
                Filtered.add(Tests.get(i));
        }
        return Filtered;
    }

    public void DeleteTest(TestModel Test) {
        for (int i = 0; i < Test.questions.size(); ++i) {
            DeleteQuestion(Test.questions.get(i));
        }
        Main.Manager._TestTable.DeleteTest(Test);
        Tests.remove(Test);
    }

    public void DeleteQuestion(QuestionModel Question) {
        Main.Manager._QuestionTable.DeleteQuestion(Question);
    }

    public ArrayList<String> Categories;
    public ArrayList<TestModel> Tests;
}

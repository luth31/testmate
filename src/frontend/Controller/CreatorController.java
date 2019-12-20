package frontend.Controller;

import backend.Main;
import backend.QuestionModel;
import backend.TestModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CreatorController {
    @FXML
    TextField titleText;

    @FXML
    TextField categoryText;

    @FXML
    TextField questionText;

    @FXML
    TextField answer1Text;

    @FXML
    TextField answer2Text;

    @FXML
    TextField answer3Text;

    @FXML
    TextField answer4Text;

    @FXML
    RadioButton a1Radio;

    @FXML
    RadioButton a2Radio;

    @FXML
    RadioButton a3Radio;

    @FXML
    RadioButton a4Radio;

    @FXML
    Button finishButton;

    @FXML
    ListView<String> questionsListView;

    @FXML
    Button addQuestionButton;

    @FXML
    Button exitButton;

    @FXML
    protected void initialize() {
        a1Radio.setToggleGroup(Group);
        a2Radio.setToggleGroup(Group);
        a3Radio.setToggleGroup(Group);
        a4Radio.setToggleGroup(Group);
        a1Radio.setText("1");
        a2Radio.setText("2");
        a3Radio.setText("3");
        a4Radio.setText("4");
        a1Radio.setSelected(true);
        Questions = new ArrayList<QuestionModel>();
    }

    @FXML
    protected void handleAddQuestion() {
        String Q = questionText.getText();
        String A1 = answer1Text.getText();
        String A2 = answer2Text.getText();
        String A3 = answer3Text.getText();
        String A4 = answer4Text.getText();
        int Correct = Integer.parseInt(((RadioButton)Group.getSelectedToggle()).getText());
        if (Q.isEmpty() || A1.isEmpty() || A2.isEmpty() || A3.isEmpty() || A4.isEmpty())
            return;
        QuestionModel Temp = new QuestionModel(0, Q, A1, A2, A3, A4, Correct);
        Questions.add(Temp);
        questionsListView.getItems().add(Q);
    }

    @FXML
    protected void handleFinishButton(ActionEvent Event) {
        String TT = titleText.getText();
        String CT = categoryText.getText();
        if (TT.isEmpty() || CT.isEmpty())
            return;
        TestModel Test = new TestModel(0, TT, CT);
        Test.questions.addAll(Questions);
        Main.Store.CreateTest(Test);
        GoToMenu();
    }

    @FXML
    protected void handleExitButton() {
        GoToMenu();
    }

    protected void GoToMenu() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/frontend/FXML/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("TestMate");
            stage.setScene(new Scene(root));
            stage.show();
            titleText.getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    ToggleGroup Group = new ToggleGroup();
    ArrayList<QuestionModel> Questions;
}

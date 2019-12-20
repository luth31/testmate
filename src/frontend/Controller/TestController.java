package frontend.Controller;

import backend.QuestionModel;
import backend.TestModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class TestController {
    @FXML
    Text  titleText;

    @FXML
    Text questionText;

    @FXML
    RadioButton radio1;

    @FXML
    RadioButton radio2;

    @FXML
    RadioButton radio3;

    @FXML
    RadioButton radio4;

    @FXML
    Text answer1;

    @FXML
    Text answer2;

    @FXML
    Text answer3;

    @FXML
    Text answer4;

    @FXML
    Button exitButton;

    @FXML
    Button nextButton;

    @FXML
    protected void handleExitButton() {
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

    protected void Render() {
        QuestionModel CurrentQuestion = Test.questions.get(QuestionIndex-1);
        titleText.setText("Question " + QuestionIndex + "/" + Test.questions.size());
        questionText.setText(CurrentQuestion.text);
        answer1.setText(CurrentQuestion.answers[0]);
        answer2.setText(CurrentQuestion.answers[1]);
        answer3.setText(CurrentQuestion.answers[2]);
        answer4.setText(CurrentQuestion.answers[3]);
    }

    protected void LoadTest(TestModel Test) {
        this.Test = Test;
    }

    @FXML
    protected void handleNextButton() {
        int Selected = Integer.parseInt(((RadioButton)Group.getSelectedToggle()).getText());
        if (Selected == Test.questions.get(QuestionIndex-1).answer)
            ++Correct;
        ++QuestionIndex;
        if (Test.questions.size() < QuestionIndex) {
            ShowAlert(Alert.AlertType.INFORMATION, "Your score", StageStyle.UTILITY, "You have finished the test. Your score is " + Correct + "/" + Test.questions.size());
            handleExitButton();
            return;
        }
        Render();
    }

    protected void ShowAlert(Alert.AlertType Type, String Title, StageStyle Style, String Context) {
        Alert alert = new Alert(Type);
        alert.setTitle(Title);
        alert.initStyle(Style);
        alert.setContentText(Context);
        alert.showAndWait();
    }

    @FXML
    protected void initialize() {
        Group = new ToggleGroup();
        radio1.setToggleGroup(Group);
        radio2.setToggleGroup(Group);
        radio3.setToggleGroup(Group);
        radio4.setToggleGroup(Group);
        radio1.setSelected(true);
        Render();
    }

    public TestController(TestModel Test) {
        this.Test = Test;
    }

    int Correct = 0;
    ToggleGroup Group;
    int QuestionIndex = 1;
    TestModel Test;
}

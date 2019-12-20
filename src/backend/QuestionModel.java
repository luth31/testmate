package backend;

public class QuestionModel {
    public QuestionModel(int Id, String Text, String FirstAnswer, String SecondAnswer, String ThirdAnswer, String ForthAnswer, int CorrectAnswer) {
        id = Id;
        text = Text;
        answers = new String[]{FirstAnswer, SecondAnswer, ThirdAnswer, ForthAnswer};
        answer = CorrectAnswer;
    }
    public int id;
    public String text;
    public String[] answers;
    public int answer;
}
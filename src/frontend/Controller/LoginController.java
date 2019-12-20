package frontend.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import backend.Main;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Text usernameErrorText;

    @FXML
    private Text passwordErrorText;

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        if (!ValidateFields())
            return;
        boolean success = Main.Auth.Attempt(usernameField.getText(), passwordField.getText());
        if (!success) {
            ShowAlert(Alert.AlertType.ERROR, "Login Failed", StageStyle.UTILITY, "We could not log you in with the provided credentials.");
            return;
        }
        ShowAlert(Alert.AlertType.INFORMATION, "Login Successful", StageStyle.UTILITY, "You have been successfully logged in.");
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/frontend/FXML/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("TestMate");
            stage.setScene(new Scene(root));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleRegisterButtonAction() {
        if (!ValidateFields())
            return;
        boolean success = Main.Auth.CreateUser(usernameField.getText(), passwordField.getText());
        if (!success) {
            ShowAlert(Alert.AlertType.ERROR, "Register Failed", StageStyle.UTILITY, "We could not register you!");
            return;
        }
        ShowAlert(Alert.AlertType.INFORMATION, "Register Successful", StageStyle.UTILITY, "You can now login with the provided credentials.");
    }

    protected boolean ValidateFields() {
        boolean isEmpty = false;
        if (usernameField.getText().isEmpty()) {
            usernameErrorText.setText("Please enter a username!");
            isEmpty = true;
        }
        else {
            usernameErrorText.setText("");
        }
        if (passwordField.getText().isEmpty()) {
            passwordErrorText.setText("Please enter a password!");
            isEmpty = true;
        }
        else {
            passwordErrorText.setText("");
        }
        return !isEmpty;
    }

    protected void ShowAlert(Alert.AlertType Type, String Title, StageStyle Style, String Context) {
        Alert alert = new Alert(Type);
        alert.setTitle(Title);
        alert.initStyle(Style);
        alert.setContentText(Context);
        alert.showAndWait();
    }
}

package frontend.Controller;

import backend.TestModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import backend.Main;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MenuController {
    @FXML
    public ListView<String> testListView;

    @FXML
    public ChoiceBox<String> categoryChoiceBox;

    @FXML
    public Button takeTestButton;

    @FXML
    public Text testText;

    public ContextMenu Menu;

    @FXML
    protected void initialize() {
        categoryChoiceBox.setValue("All");
        categoryChoiceBox.getItems().addAll(Main.Store.Categories);
        Menu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete test");
        deleteMenuItem.setOnAction((event) -> {
            if (CurrentTests.size() == 0)
                return;
            Main.Store.DeleteTest(CurrentTests.get(Selection));
            CurrentTests.remove(Selection);
            RenderListView();
        });
        MenuItem createMenuItem = new MenuItem("Create test");
        createMenuItem.setOnAction((event) -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/frontend/FXML/Creator.fxml"));
                Stage stage = new Stage();
                stage.setTitle("TestMate");
                stage.setScene(new Scene(root));
                stage.show();
                testListView.getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        Menu.getItems().addAll(deleteMenuItem, createMenuItem);
        testListView.setContextMenu(Menu);
    }

    @FXML
    protected void handleCategoryChange(ActionEvent Event) {
        String Category = ((ChoiceBox<String>) Event.getSource()).getValue();
        CurrentTests = new ArrayList<TestModel>(Main.Store.GetByCategory(Category));
        RenderListView();
    }

    @FXML
    protected void handleTakeTest() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/FXML/Test.fxml"));
            TestController Controller = new TestController(CurrentTests.get(Selection));
            loader.setController(Controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("TestMate");
            stage.setScene(new Scene(root));
            stage.show();
            testListView.getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleChangeSelection(MouseEvent Event) {
        Selection = testListView.getSelectionModel().getSelectedIndex();
    }

    protected void RenderListView() {
        testListView.getItems().clear();
        for (TestModel currentTest : CurrentTests) {
            testListView.getItems().add(currentTest.title);
        }
    }

    int Selection = 0;
    ArrayList<String> Categories;
    ArrayList<TestModel> CurrentTests;
}

package todo.htlkaindorf.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import todo.htlkaindorf.application.beans.TodoTask;
import todo.htlkaindorf.application.bl.TodoManagerLogic;

import java.io.File;
import java.time.LocalDate;

public class HelloController {

    @FXML private ListView<TodoTask> listView;
    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> prioComboBox;
    @FXML private ComboBox<String> filterComboBox;

    private TodoManagerLogic logic;

    @FXML
    public void initialize() {
        logic = new TodoManagerLogic();
        listView.setItems(logic.getTasks());

        prioComboBox.getItems().addAll("HOCH", "MITTEL", "NIEDRIG");
        prioComboBox.setValue("MITTEL");

        filterComboBox.getItems().addAll("ALLE", "OFFEN", "IN_BEARBEITUNG", "PAUSIERT", "ERLEDIGT");
        filterComboBox.setValue("ALLE");

        datePicker.setValue(LocalDate.now());
    }

    @FXML
    protected void onAddTodo() {
        logic.addTask(titleField.getText(), descArea.getText(), prioComboBox.getValue(), datePicker.getValue());
        titleField.clear();
        descArea.clear();
    }

    @FXML
    protected void onDeleteTodo() {
        logic.removeTask(listView.getSelectionModel().getSelectedItem());
    }

    @FXML
    protected void onNextState() {
        TodoTask selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.nextState();
            listView.refresh();
        }
    }

    @FXML
    protected void onPauseState() {
        TodoTask selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.pauseState();
            listView.refresh();
        }
    }

    @FXML
    protected void onFilterChanged() {
        String selectedFilter = filterComboBox.getValue();
        logic.filterByStatus(selectedFilter);
    }

    @FXML
    protected void onSave() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(listView.getScene().getWindow());
        if (file != null) {
            try {
                logic.saveToFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onLoad() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(listView.getScene().getWindow());
        if (file != null) {
            try {
                logic.loadFromFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
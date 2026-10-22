package todo.htlkaindorf.application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import todo.htlkaindorf.application.beans.*;
import todo.htlkaindorf.application.bl.TodoManagerLogic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class HelloController {

    @FXML private ListView<TodoTask> listView;
    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> prioComboBox;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private ComboBox<String> stateComboBox;

    private TodoManagerLogic logic;
    private boolean isUpdatingComboBox = false;

    @FXML
    public void initialize() {
        logic = new TodoManagerLogic();
        listView.setItems(logic.getTasks());

        prioComboBox.getItems().addAll("HOCH", "MITTEL", "NIEDRIG");
        prioComboBox.setValue("MITTEL");

        filterComboBox.getItems().addAll("ALLE", "OFFEN", "IN_BEARBEITUNG", "PAUSIERT", "ERLEDIGT");
        filterComboBox.setValue("ALLE");

//        stateComboBox.getItems().addAll("OFFEN", "IN_BEARBEITUNG", "ERLEDIGT");
//        stateComboBox.setValue("OFFEN");

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedTask) -> {
            updateStateComboBox(selectedTask);
        });

        datePicker.setValue(LocalDate.now());

        // Versuche letzten Save zu laden falls vorhanden
        String appDataPath = System.getenv("APPDATA");

        if (appDataPath == null) {
            showCriticalError("The AppData Folder was not found!");
        }

        Path saveDirectory = Paths.get(appDataPath, "TodoManager");
        Path saveFile = saveDirectory.resolve("todos.json");

        try {
            if (Files.notExists(saveDirectory)) {
                Files.createDirectories(saveDirectory);
            }

            if (Files.notExists(saveFile)) {
                Files.createFile(saveFile);

            } else {
                logic.loadFromFile(saveFile.toFile());
            }

        } catch (IOException e) {
            showCriticalError("Error at generating file " + e.getMessage());
        } catch (ClassNotFoundException e) {
            showCriticalError("Error at loading file " + e.getMessage());
        }
    }

    private void showCriticalError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Critical Error");
        alert.setHeaderText("The Program can't be run");
        alert.setContentText(message);
        alert.showAndWait();
        Platform.exit();
        System.exit(1);
    }

    private void updateStateComboBox(TodoTask task) {
        isUpdatingComboBox = true;
        stateComboBox.getItems().clear();
        if (task == null) return;

        TaskState currentState = task.getState();

        stateComboBox.getItems().addAll("OFFEN", "IN_BEARBEITUNG", "ERLEDIGT");

        stateComboBox.setValue(task.getState().getStatusName());

        isUpdatingComboBox = false;
    }

    @FXML
    protected void onChangeState() {
        if (isUpdatingComboBox) return;

        TodoTask selectedTask = listView.getSelectionModel().getSelectedItem();
        String targetState = stateComboBox.getValue();

        if (selectedTask != null && targetState != null) {
            switch (targetState) {
                case "OFFEN":
                    selectedTask.setState(new OffenState());
                    break;
                case "IN_BEARBEITUNG":
                    selectedTask.setState(new InBearbeitungState());
                    break;
                case "PAUSIERT":
                    selectedTask.setState(new PausiertState());
                    break;
                case "ERLEDIGT":
                    selectedTask.setState(new ErledigtState());
                    break;
            }

            listView.refresh();

            updateStateComboBox(selectedTask);
        }
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

//    @FXML
//    protected void onLoad() {
//        FileChooser fileChooser = new FileChooser();
//        File file = fileChooser.showOpenDialog(listView.getScene().getWindow());
//        if (file != null) {
//            try {
//                logic.loadFromFile(file);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
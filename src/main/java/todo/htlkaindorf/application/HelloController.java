package todo.htlkaindorf.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import todo.htlkaindorf.application.beans.TodoItem;
import todo.htlkaindorf.application.bl.TodoManagerLogic;

import java.time.LocalDate;

public class HelloController {

    @FXML
    private ListView<TodoItem> listView;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descArea;
    @FXML
    private DatePicker datePicker;

    private TodoManagerLogic logic;

    @FXML
    public void initialize() {
        logic = new TodoManagerLogic();
        listView.setItems(logic.getTodos());
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    protected void onAddTodo() {
        String title = titleField.getText();
        String desc = descArea.getText();
        LocalDate date = datePicker.getValue();

        logic.addTodo(title, desc, date);

        titleField.clear();
        descArea.clear();
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    protected void onDeleteTodo() {
        TodoItem selected = listView.getSelectionModel().getSelectedItem();
        logic.removeTodo(selected);
    }

    @FXML
    protected void onToggleComplete() {
        TodoItem selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            logic.toggleComplete(selected);
            listView.refresh();
        }
    }
}
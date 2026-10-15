package todo.htlkaindorf.application.bl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import todo.htlkaindorf.application.beans.TodoItem;

import java.time.LocalDate;

public class TodoManagerLogic {
    private final ObservableList<TodoItem> todos;

    public TodoManagerLogic() {
        this.todos = FXCollections.observableArrayList();
        // Beispiel-Einträge
        todos.add(new TodoItem("JavaFX Projekt aufsetzen", "Maven Konfiguration prüfen und Module einrichten", LocalDate.now().plusDays(1)));
        todos.add(new TodoItem("Software Engineering lernen", "Aufgaben für den nächsten Test vorbereiten", LocalDate.now().plusDays(3)));
    }

    public ObservableList<TodoItem> getTodos() {
        return todos;
    }

    public void addTodo(String title, String description, LocalDate dueDate) {
        if (title != null && !title.trim().isEmpty()) {
            todos.add(new TodoItem(title.trim(), description, dueDate));
        }
    }

    public void removeTodo(TodoItem item) {
        if (item != null) {
            todos.remove(item);
        }
    }

    public void toggleComplete(TodoItem item) {
        if (item != null) {
            item.setCompleted(!item.isCompleted());
        }
    }
}

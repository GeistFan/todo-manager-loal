package todo.htlkaindorf.application.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TodoItem {
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;

    public TodoItem(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    @Override
    public String toString() {
        String status = completed ? "[X]" : "[ ]";
        return status + " " + title + " (Fällig: " + (dueDate != null ? dueDate.toString() : "kein Datum") + ")";
    }
}

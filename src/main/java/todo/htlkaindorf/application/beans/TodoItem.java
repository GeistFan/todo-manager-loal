package todo.htlkaindorf.application.beans;

import java.time.LocalDate;

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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        String status = completed ? "[X]" : "[ ]";
        return status + " " + title + " (Fällig: " + (dueDate != null ? dueDate.toString() : "kein Datum") + ")";
    }
}

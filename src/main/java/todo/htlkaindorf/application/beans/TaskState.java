package todo.htlkaindorf.application.beans;

import javafx.concurrent.Task;

import java.io.Serializable;

public interface TaskState extends Serializable {
    void start(TodoTask task);
    void pause(TodoTask task);
    void complete(TodoTask task);
    void reopen(TodoTask task);
    String getStatusName();
}
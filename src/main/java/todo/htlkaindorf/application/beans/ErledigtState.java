package todo.htlkaindorf.application.beans;

import javafx.concurrent.Task;

public class ErledigtState implements TaskState {
    @Override public void start(TodoTask task) { }
    @Override public void pause(TodoTask task) { }
    @Override public void complete(TodoTask task) { }
    @Override public void reopen(TodoTask task) { task.setState(new OffenState()); }
    @Override public String getStatusName() { return "ERLEDIGT"; }
}
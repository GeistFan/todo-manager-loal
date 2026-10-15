package todo.htlkaindorf.application.beans;

import javafx.concurrent.Task;

public class OffenState implements TaskState {
    @Override
    public void start(TodoTask task) {
        task.setState(new InBearbeitungState());
    }
    @Override public void pause(TodoTask task) { /* Nicht erlaubt im Offen-Zustand */ }
    @Override public void complete(TodoTask task) { task.setState(new ErledigtState()); }
    @Override public void reopen(TodoTask task) { }
    @Override public String getStatusName() { return "OFFEN"; }
}
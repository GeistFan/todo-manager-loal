package todo.htlkaindorf.application.beans;

import javafx.concurrent.Task;

public class PausiertState implements TaskState {
    @Override public void start(TodoTask task) { task.setState(new InBearbeitungState()); }
    @Override public void pause(TodoTask task) { }
    @Override public void complete(TodoTask task) { task.setState(new ErledigtState()); }
    @Override public void reopen(TodoTask task) { task.setState(new OffenState()); }
    @Override public String getStatusName() { return "PAUSIERT"; }
}
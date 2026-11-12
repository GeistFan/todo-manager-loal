package todo.htlkaindorf.application.beans;

import java.io.Serial;

public class FinishedState implements TaskState {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override public void start(TodoTask task) { }
    @Override public void pause(TodoTask task) { }
    @Override public void complete(TodoTask task) { }
    @Override public void reopen(TodoTask task) { task.setState(new OpenState()); }
    @Override public String getStatusName() { return "ERLEDIGT"; }
}
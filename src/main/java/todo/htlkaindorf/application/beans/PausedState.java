package todo.htlkaindorf.application.beans;

import java.io.Serial;

public class PausedState implements TaskState {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override public void start(TodoTask task) { task.setState(new InProgressState()); }
    @Override public void pause(TodoTask task) { }
    @Override public void complete(TodoTask task) { task.setState(new FinishedState()); }
    @Override public void reopen(TodoTask task) { task.setState(new OpenState()); }
    @Override public String getStatusName() { return "PAUSIERT"; }
}
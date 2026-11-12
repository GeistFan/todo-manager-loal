package todo.htlkaindorf.application.beans;

import java.io.Serial;

public class OpenState implements TaskState {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override public void start(TodoTask task) {
        task.setState(new InProgressState());
    }
    @Override public void pause(TodoTask task) { /* Nicht erlaubt im Offen-Zustand */ }
    @Override public void complete(TodoTask task) { task.setState(new FinishedState()); }
    @Override public void reopen(TodoTask task) { }
    @Override public String getStatusName() { return "OFFEN"; }
}
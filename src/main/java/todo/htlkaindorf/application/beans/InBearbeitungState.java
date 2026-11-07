package todo.htlkaindorf.application.beans;

import java.io.Serial;

public class InBearbeitungState implements TaskState {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override public void start(TodoTask task) { }
    @Override public void pause(TodoTask task) { task.setState(new PausiertState()); } // Optional: Kann-Anforderung
    @Override public void complete(TodoTask task) { task.setState(new ErledigtState()); }
    @Override public void reopen(TodoTask task) { task.setState(new OffenState()); }
    @Override public String getStatusName() { return "IN_BEARBEITUNG"; }
}
package todo.htlkaindorf.application.beans;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TodoTask implements Serializable {
    private static int idCounter = 1;
    private int id;
    private String titel;
    private String beschreibung;
    private String prioritaet;
    private LocalDate faelligkeit;
    private TaskState state;

    public TodoTask(String titel, String beschreibung, String prioritaet, LocalDate faelligkeit) {
        this.id = idCounter++;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.prioritaet = prioritaet;
        this.faelligkeit = faelligkeit;
        this.state = new OffenState(); // Startzustand
    }

    // Wichtig: Muss public sein, damit die States darauf zugreifen können!
    public void setState(TaskState state) {
        this.state = state;
    }

    // State-Delegation (State Pattern)
    public void nextState() {
        if (state instanceof OffenState) state.start(this);
        else if (state instanceof InBearbeitungState) state.complete(this);
        else if (state instanceof PausiertState) state.start(this);
    }

    public void previousState() {
        state.reopen(this);
    }

    public void pauseState() {
        state.pause(this);
    }
}
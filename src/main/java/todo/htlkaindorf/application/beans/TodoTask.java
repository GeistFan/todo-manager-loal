package todo.htlkaindorf.application.beans;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class TodoTask implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int idCounter = 1;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
        this.state = new OpenState();
    }

    // State-Delegation (State Pattern)
    public void nextState() {
        if (state instanceof OpenState) state.start(this);
        else if (state instanceof InProgressState) state.complete(this);
        else if (state instanceof PausedState) state.start(this);
    }

    public void previousState() {
        state.reopen(this);
    }

    public void pauseState() {
        state.pause(this);
    }

    @Override
    public String toString() {
        String statusName = (state != null) ? state.getStatusName() : "UNBEKANNT";
        String dateStr = (faelligkeit != null) ? faelligkeit.format(dtf) : "Kein Datum";
        return String.format("[%s] %s (Prio: %s, Fällig: %s)", statusName, titel, prioritaet, dateStr);
    }
}
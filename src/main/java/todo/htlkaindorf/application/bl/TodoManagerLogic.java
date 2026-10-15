package todo.htlkaindorf.application.bl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import todo.htlkaindorf.application.beans.TodoTask;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TodoManagerLogic {
    private final ObservableList<TodoTask> allTasks;
    private final ObservableList<TodoTask> filteredTasks;

    public TodoManagerLogic() {
        this.allTasks = FXCollections.observableArrayList();
        this.filteredTasks = FXCollections.observableArrayList();

        // Testdaten
        allTasks.add(new TodoTask("State Pattern implementieren", "Zustände und Übergänge definieren", "HOCH", LocalDate.now().plusDays(2)));
        allTasks.add(new TodoTask("UI anpassen", "FXML Layout erweitern", "MITTEL", LocalDate.now().plusDays(4)));

        filteredTasks.setAll(allTasks);
    }

    public ObservableList<TodoTask> getTasks() {
        return filteredTasks;
    }

    public void addTask(String titel, String beschreibung, String prio, LocalDate faelligkeit) {
        if (titel != null && !titel.trim().isEmpty()) {
            TodoTask newTask = new TodoTask(titel.trim(), beschreibung, prio, faelligkeit);
            allTasks.add(newTask);
            filteredTasks.add(newTask);
        }
    }

    public void removeTask(TodoTask task) {
        if (task != null) {
            allTasks.remove(task);
            filteredTasks.remove(task);
        }
    }

    public void filterByStatus(String status) {
        if (status == null || status.equals("ALLE")) {
            filteredTasks.setAll(allTasks);
        } else {
            filteredTasks.setAll(allTasks.stream()
                    .filter(t -> t.getState().getStatusName().equals(status))
                    .collect(Collectors.toList()));
        }
    }

    // Pflicht: Aufgaben speichern (z.B. serialisiert in Datei)
    public void saveToFile(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(allTasks));
        }
    }

    // Aufgaben laden
    public void loadFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<TodoTask> loaded = (List<TodoTask>) ois.readObject();
            allTasks.setAll(loaded);
            filteredTasks.setAll(loaded);
        }
    }
}
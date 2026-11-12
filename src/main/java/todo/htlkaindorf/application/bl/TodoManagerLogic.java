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

        filteredTasks.setAll(allTasks);
    }

    public ObservableList<TodoTask> getTasks() {
        return filteredTasks;
    }

    public void addTask(String title, String description, String priority, LocalDate dueDate) {
        if (title != null && !title.trim().isEmpty()) {
            TodoTask newTask = new TodoTask(title.trim(), description, priority, dueDate);
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

    public void saveToFile(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(allTasks));
        }
    }

    public void saveAutomatic(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            // 1. "Kommentar" oder Metadaten als String in die Datei schreiben
            oos.writeObject("METADATA: HTL Kaindorf TodoManager Backup - Nicht manuell bearbeiten!");

            // 2. Eigentliche Task-Liste schreiben
            oos.writeObject(new ArrayList<>(allTasks));
        } catch (IOException e) {
            System.err.println("Fehler beim automatischen Speichern:");
            e.printStackTrace();
        }
    }

    public void loadAutomatic(File file) {
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

                // 1. Den Kommentar/Metadaten aus dem Stream lesen und ignorieren (oder anzeigen)
                String fileComment = (String) ois.readObject();
                System.out.println("Gelesener Datei-Hinweis: " + fileComment);

                // 2. Erst jetzt die echte Task-Liste einlesen
                @SuppressWarnings("unchecked")
                List<TodoTask> loaded = (List<TodoTask>) ois.readObject();
                allTasks.setAll(loaded);

                System.out.println("Automatisches Laden erfolgreich: " + loaded.size() + " Tasks geladen.");
            } catch (Exception e) {
                System.err.println("Fehler beim automatischen Laden.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Keine Speicherdatei gefunden. Starte mit leerer Liste.");
        }
    }
}
package todo.htlkaindorf.application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import todo.htlkaindorf.application.beans.TodoItem;
import todo.htlkaindorf.application.bl.TodoManagerLogic;

import java.time.LocalDate;

public class HelloApplication extends Application {

    private TodoManagerLogic logic;
    private ListView<TodoItem> listView;
    private TextField titleField;
    private TextArea descArea;
    private DatePicker datePicker;

    @Override
    public void init() {
        logic = new TodoManagerLogic();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("HTL Kaindorf - ToDo Manager");

        // UI-Elemente initialisieren
        listView = new ListView<>(logic.getTodos());
        listView.setPrefHeight(220);

        titleField = new TextField();
        titleField.setPromptText("Titel des ToDos...");

        descArea = new TextArea();
        descArea.setPromptText("Beschreibung...");
        descArea.setPrefRowCount(3);

        datePicker = new DatePicker(LocalDate.now());

        Button addButton = new Button("Hinzufügen");
        Button deleteButton = new Button("Löschen");
        Button toggleButton = new Button("Erledigt umschalten");

        // Event Handling
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String desc = descArea.getText();
            LocalDate date = datePicker.getValue();

            logic.addTodo(title, desc, date);

            // Eingabefelder zurücksetzen
            titleField.clear();
            descArea.clear();
            datePicker.setValue(LocalDate.now());
        });

        deleteButton.setOnAction(e -> {
            TodoItem selected = listView.getSelectionModel().getSelectedItem();
            logic.removeTodo(selected);
        });

        toggleButton.setOnAction(e -> {
            TodoItem selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                logic.toggleComplete(selected);
                listView.refresh(); // UI aktualisieren
            }
        });

        // Layouts zusammenbauen
        HBox buttonBox = new HBox(10, addButton, deleteButton, toggleButton);
        buttonBox.setPadding(new Insets(5, 0, 5, 0));

        VBox inputForm = new VBox(8, new Label("Neues ToDo erfassen:"), titleField, descArea, datePicker, buttonBox);
        inputForm.setPadding(new Insets(10));

        VBox mainLayout = new VBox(10, new Label("Deine ToDos:"), listView, inputForm);
        mainLayout.setPadding(new Insets(15));

        Scene scene = new Scene(mainLayout, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
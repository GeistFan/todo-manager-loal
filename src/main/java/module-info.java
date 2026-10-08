module todo.htlkaindorf.todo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens todo.htlkaindorf.todo to javafx.fxml;
    exports todo.htlkaindorf.todo;
}
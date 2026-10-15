module todo.htlkaindorf.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens todo.htlkaindorf.application to javafx.fxml;
    exports todo.htlkaindorf.application;
    exports todo.htlkaindorf.application.beans;
    exports todo.htlkaindorf.application.bl;
}
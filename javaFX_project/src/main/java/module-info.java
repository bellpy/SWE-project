module hellofx {
    requires transitive javafx.controls;
    requires javafx.fxml;
 
    opens dtu.example.model to javafx.base; // Allow JavaFX to access Activity properties
    exports dtu.example.ui;
    opens dtu.example.ui to javafx.fxml;
}
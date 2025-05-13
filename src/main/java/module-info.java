module org.example.proyecto3aev {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.xml.bind;


    opens org.example.proyecto3aev to javafx.fxml;
    exports org.example.proyecto3aev;
    exports controller;
    opens controller to javafx.fxml;
}
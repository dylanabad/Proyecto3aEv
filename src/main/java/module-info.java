module org.example.proyecto3aev {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.proyecto3aev to javafx.fxml;
    exports org.example.proyecto3aev;
}
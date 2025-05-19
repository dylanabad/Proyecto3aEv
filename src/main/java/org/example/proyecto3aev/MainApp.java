// Clase principal de la aplicación que inicializa la interfaz gráfica.
// Carga el archivo FXML principal y configura la ventana principal
package org.example.proyecto3aev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/registro.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Gestor de Colecciones - Registro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}

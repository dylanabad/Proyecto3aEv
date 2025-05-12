
package dataBase;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

    public class ConnectionProperties {
        private static final String PROPERTIES_FILE = "db.properties";
        private static String url;
        private static String user;
        private static String password;

        static {
            try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
                Properties properties = new Properties();
                properties.load(fis);
                url = properties.getProperty("db.url");
                user = properties.getProperty("db.user");
                password = properties.getProperty("db.password");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al cargar el archivo de propiedades: " + PROPERTIES_FILE);
            }
        }

        public static String getUrl() {
            return url;
        }

        public static String getUser() {
            return user;
        }

        public static String getPassword() {
            return password;
        }
    }

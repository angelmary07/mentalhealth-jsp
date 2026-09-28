package mentalhealth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {

    private static final String url =
            "jdbc:mysql://localhost:3306/mentalhealthdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // credentials come from environment variables so no password gets pushed to github
    private static final String user = envor("DB_USER", "root");
    private static final String pass = envor("DB_PASS", "your_password");

    private static String envor(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.isEmpty()) ? fallback : value;
    }

    public static Connection getconnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL driver not found in WEB-INF/lib", ex);
        }
        return DriverManager.getConnection(url, user, pass);
    }
}

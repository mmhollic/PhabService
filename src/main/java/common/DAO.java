package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected Connection conn=null;
    protected Connection getConnection() throws SQLException {
        // Common DAO (database access object) code to set up db connection
        // Can be confirgured to use Heroku or Gretty
        boolean heroku=true;

        if (heroku){
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            conn= DriverManager.getConnection(dbUrl);
        }
        else { // gretty
            String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

            try {
                // Registers the driver
                Class.forName("org.postgresql.Driver");
            } catch (Exception e) {
            }
            conn = DriverManager.getConnection(dbUrl, "postgres", "");
        }
        return conn;

    }
}

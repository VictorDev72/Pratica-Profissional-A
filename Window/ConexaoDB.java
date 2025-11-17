import java.sql.*;

public class ConexaoBD
{
        private static final String URL =
                "jdbc:sqlserver://regulus.cotuca.unicamp.br:1433;"+"databaseName=BD25572;"+
                        "integratedSecurity=false;encrypt=true;trustServerCertificate=true";
        private static final String USER = "BD25572";
        private static final String PASSWORD = "BD25572";
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
}

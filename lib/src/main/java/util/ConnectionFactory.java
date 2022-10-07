package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {

	public static final String DRIVER = "com.mysql.jdbcDriver";
	public static String URL = "jdbc:mysql//localhost:3306/toDoApp?characterEncoding=utf8";
	public static final String USER = "root";
	public static final String PASS = "root";

	public static java.sql.Connection getConnection() {
		try {
			Class.forName(DRIVER);//CARREGA O DRIVER DO BANCO.
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Erro na conexão com o banco de dados" + e.getMessage());//ERRO CONSOLE
		}
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar a conexão com o banco de dados");
		}
	}

	public static void closeConnection(Connection connection, PreparedStatement statement) {
        closeConnection(connection);
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados" + e.getMessage());
        }
        
    }
}

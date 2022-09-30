package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionFactory {

	public static final String DRIVER = "com.mysql.jdbcDriver";
	public static String URL = "jdbc:mysql//localhost:3306/toDoApp";
	public static final String USER = "root";
	public static final String PASS = "root";
	
	public static Connection getConnetion() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			throw new RuntimeException("Erro na conexão com o banco de dados, ex");
		}
	}
	
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao fechar a conexão com o banco de dados");
		}
	}

	public static void closeConnection(Connection connection, PreparedStatement statement) {
		try {
			if (connection != null) {
				connection.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao fechar a conexão com o banco de dados");
		}
	}
	
	public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
		try {
			if (connection != null) {
				connection.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if(resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao fechar a conexão com o banco de dados");
		}
	}
}

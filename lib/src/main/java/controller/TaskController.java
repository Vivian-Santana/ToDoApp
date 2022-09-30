package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Task;
import util.ConnectionFactory;

public class TaskController {

	public void save(Task task) {

		String sql = "INSERT INTO tasks (idProject, name, description, completed, notes, deadline, createdAt, updatedAt) "
				+ "VALUES (?,?,?,?,?,?,?,?)";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionFactory.getConnetion();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, task.getIdProject()); // SETA O ID DO PROJETO O QUAL A TAREFA PERTENCE.
			statement.setString(2, task.getName());
			statement.setString(3, task.getDescription());
			statement.setBoolean(4, task.isCompleted());
			statement.setString(5, task.getNotes());
			statement.setDate(6, new Date(task.getDeadLine().getTime()));
			statement.setDate(7, new Date(task.getCreatedAt().getTime()));
			statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
			statement.execute();

		} catch (Exception ex) {
			throw new RuntimeException("Erro ao salvar a tarefa" + ex.getMessage(), ex);
		} finally {
			ConnectionFactory.closeConnection(connection, statement); // QUANDO A CONEXÃO FOR FECHADA O STATEMENT TBM
																		// SERÁ.
		}

	}

	public void update(Task task) {

		String sql = "UPDATE tasks SET, idProject = ?, name = ?, description = ?, notes = ?,"
				+ "completed = ?, deadline = ?, createdAt =?, updatedAt = ?, WHERE id = ?";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionFactory.getConnetion();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, task.getIdProject());
			statement.setString(2, task.getName());
			statement.setString(3, task.getDescription());
			statement.setString(4, task.getNotes());
			statement.setBoolean(5, task.isCompleted());
			statement.setDate(6, new Date(task.getDeadLine().getTime()));
			statement.setDate(7, new Date(task.getCreatedAt().getTime()));
			statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
			statement.execute();

		} catch (Exception e) {
			throw new RuntimeException("Erro ao atualizar tarefa" + e.getMessage());
		}
	}

	public void removeById(int taskId) throws SQLException {

		String sql = "DELETE FROM tasks WHERE ID = ?";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionFactory.getConnetion(); // PREPARA O COMANDO PARA SER EXECUTADO PELA CONEXÃO (EVITA
															// SQL
															// INJECTION)
			statement = connection.prepareStatement(sql);
			statement.setInt(1, taskId); // DELETE FROM TASKS WHERE ID = AO NÚMERO DO ID QUE FOI RECEBIDO COMO
											// PARÂMETRO.
			statement.execute();

		} catch (Exception e) {
			throw new RuntimeException("Erro ao deletar tarefa" + e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
	}

	public List<Task> getAll(int idProject) { // METODO DEVOLVE UMA LISTA DE TAREFAS.

		String sql = "SELECT * FROM TASKS WHERE idProject = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		//LISTA DE TAREFAS QUE SERÁ DEVOLVIDA QUANDO A CHAMADA DO MÉTODO ACONTECER. É UMA ESTRUTURA DE LISTA, COMO SE FOSSE UM CONJUNTO DE VETORES. 
		List<Task> tasks = new ArrayList<Task>();
		                                        
		try {
			connection = ConnectionFactory.getConnetion();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProject);
			resultSet = statement.executeQuery(); // VARIÁVEL Q GUARDA O RETORNO DO BANCO DE DADOS.
			
			while(resultSet.next()) {
				
				Task task = new Task ();
				task.setId(resultSet.getInt("id"));
				task.setIdProject(resultSet.getInt("idProject"));
				task.setName(resultSet.getString("name"));
				task.setDescription(resultSet.getString("notes"));
				task.setCompleted(resultSet.getBoolean("completed"));
				task.setDeadLine(resultSet.getDate("deadline"));
				task.setCreatedAt (resultSet.getDate("createdAt"));
				task.setUpdatedAt (resultSet.getDate("updatedAt"));
				tasks.add(task);
				
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar tarefas" + e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		
		return tasks; //RETORNA A LISTA DE TAREFAS CARREGADA DO BANCO DE DADOS.
	}
}

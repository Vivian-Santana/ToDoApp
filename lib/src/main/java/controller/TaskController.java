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
			// ESTABELECENDO A CONEXÃO COM O BANCO DE DADOS.
			connection = ConnectionFactory.getConnection();
			
			// PREPARANDO A QUERY.
			statement = connection.prepareStatement(sql);
			
			// SETANDO OS VALORES DO STATEMENT.
			statement.setInt(1, task.getIdProject()); // SETA O ID DO PROJETO O QUAL A TAREFA PERTENCE.
			statement.setString(2, task.getName());
			statement.setString(3, task.getDescription());
			statement.setBoolean(4, task.isCompleted());
			statement.setString(5, task.getNotes());
			statement.setDate(6, new Date(task.getDeadLine().getTime()));
			statement.setDate(7, new Date(task.getCreatedAt().getTime()));
			statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
			
			// EXECUTANDO A QUERY.
			statement.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar a tarefa" + e.getMessage(), e);
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
			// ESTABELECENDO A CONEXÃO COM O BANCO DE DADOS.
			connection = ConnectionFactory.getConnection();

			// CRIA UM PREAPARETEDSTATEMENT, CLASSE USADA PARA PREPARAR A QUERY.
			statement = connection.prepareStatement(sql);

			// SETANDO OS VALORES DO STATEMENT.
			statement.setInt(1, task.getIdProject());
			statement.setString(2, task.getName());
			statement.setString(3, task.getDescription());
			statement.setString(4, task.getNotes());
			statement.setBoolean(5, task.isCompleted());
			statement.setDate(6, new Date(task.getDeadLine().getTime()));
			statement.setDate(7, new Date(task.getCreatedAt().getTime()));
			statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
			statement.setInt(9, task.getId());

			// EXECUTANDO A QUERY.
			statement.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar tarefa" + e.getMessage());
		}
	}

	public void removeById(int taskId) throws SQLException {

		String sql = "DELETE FROM tasks WHERE ID = ?";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// CRIAÇÃO DA CONEXÃO COM O BANCO DE DADOS.
			connection = ConnectionFactory.getConnection(); // PREPARA O COMANDO PARA SER EXECUTADO PELA CONEXÃO (EVITA
															// SQL
															// INJECTION)
			// PREPARANDO A QUERY.
			statement = connection.prepareStatement(sql);

			// SETANDO OS VALORES.
			statement.setInt(1, taskId); // DELETE FROM TASKS WHERE ID = AO NÚMERO DO ID QUE FOI RECEBIDO COMO
											// PARÂMETRO.
			// EXECUTANDO A QUERY.
			statement.execute();

		} catch (SQLException e) {
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

		// LISTA DE TAREFAS QUE SERÁ DEVOLVIDA QUANDO A CHAMADA DO MÉTODO ACONTECER. É
		// UMA ESTRUTURA DE LISTA, COMO SE FOSSE UM CONJUNTO DE VETORES.
		List<Task> tasks = new ArrayList<Task>();

		try {
			//CRIÇÃO DA CONEXÃO.
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			
			//SETANDO O VALOR QUE CORRESPONDE AO FILTRO DE BUSCA.
			statement.setInt(1, idProject);
			
			//VALOR RETORNADO PELA EXECUÇÃO DA QUERY
			resultSet = statement.executeQuery(); // VARIÁVEL QUE GUARDA O RETORNO DO BANCO DE DADOS.

			//ENQUANTO HOUVER VALORES A SEREM PERCORRIDOS NO RESULTSET.
			while (resultSet.next()) {

				Task task = new Task();
				
				task.setId(resultSet.getInt("id"));
				task.setIdProject(resultSet.getInt("idProject"));
				task.setName(resultSet.getString("name"));
				task.setDescription(resultSet.getString("notes"));
				task.setCompleted(resultSet.getBoolean("completed"));
				task.setDeadLine(resultSet.getDate("deadline"));
				task.setCreatedAt(resultSet.getDate("createdAt"));
				task.setUpdatedAt(resultSet.getDate("updatedAt"));
				tasks.add(task);

			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao criar tarefa" + e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}

		return tasks; // RETORNA A LISTA DE TAREFAS CARREGADA DO BANCO DE DADOS.
	}
}

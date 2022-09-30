package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Project;
import model.Task;
import util.ConnectionFactory;

public class ProjectController {

	public void save(Project project){

	String sql = "INSERT INTO tasks (idProject, name, description, createdAt, updatedAt, " + "VALUES (?, ?, ?, ?)";

	Connection connection = null;
	PreparedStatement statement = null;

	try
	{
		// ESTABELECENDO A CONEXÃO COM O BANCO DE DADOS.
		connection = ConnectionFactory.getConnection();

		// PREPARANDO A QUERY.
		statement = connection.prepareStatement(sql);

		// SETANDO OS VALORES DO STATEMENT.
		statement.setString(1, project.getName());
		statement.setString(2, project.getDescription());
		statement.setDate(4, new Date(project.getCreatedAt().getTime()));
		statement.setDate(5, new Date(project.getUpdatedAt()));

		// EXECUTANDO A QUERY.
		statement.execute();

	}catch(SQLException e) {
	
		throw new RuntimeException("Erro ao salvar o Projeto" + e.getMessage(), e);
	}finally{
		ConnectionFactory.closeConnection(connection, statement);
	}

  }
	
	public void update(Project project) {
		
		String sql = "INSERT INTO tasks (idProject = ?, name = ?, description = ?, createdAt = ?, updatedAt = ? " 
		+ "WHERE id = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			// ESTABELECENDO A CONEXÃO COM O BANCO DE DADOS.
			connection = ConnectionFactory.getConnection();

			// CRIA UM PREAPARETEDSTATEMENT, CLASSE USADA PARA PREPARAR A QUERY.
			statement = connection.prepareStatement(sql);

			// SETANDO OS VALORES DO STATEMENT.
			statement.setString(1, project.getName());
			statement.setString(2, project.getDescription());
			statement.setDate(3, new Date(project.getCreatedAt().getTime()));
			statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
			statement.setInt(5, project.getId());

			// EXECUTA O SQL PARA INSERÇÃO DOS DAODOS.
			statement.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar o projeto" + e.getMessage());
		}finally{
			ConnectionFactory.closeConnection(connection, statement);
		}
	}
	
	public List<Project> getAll() { 

		String sql = "SELECT * FROM projects = ?"; //BUSCA TODOS OS PROJETOS SEM FILTRAR.
		
		List<Project> tasks = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;//REPRESENTANDO OS DAODOS NO BANCO.

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
		
			//EXECUTAR ENQUANTO HOUVER DADOS NO BANCO.
			while (resultSet.next()) {

				Project project = new Project();
				
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setDescription(resultSet.getString("description"));
				project.setCreatedAt(resultSet.getDate("createdAt"));
				project.setUpdatedAt(resultSet.getDate("updatedAt"));
				tasks.add(project);

			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar projetos" + e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		return projects;
	}
	
	public void removeById(int idProject) {
		
		String sql = "DELETE FROM projects WHERE Id = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProject);
			statement.execute();
		} catch (SQLException e){
			throw new RuntimeException("Erro ao deletar projeto", e);
		} finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
	
	}
	
}
	


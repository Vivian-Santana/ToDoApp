package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

public class ProjectController {

	public void save(Project project){

	String sql = "INSERT INTO projects (name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?)";

	Connection connection = null;
	PreparedStatement statement = null;

	try
	{
		// ESTABELECENDO A CONEXÃO COM O BANCO DE DADOS.
		connection = ConnectionFactory.getConnection();     //ERRO CONSOLE*********!!!!!!!!

		// PREPARANDO A QUERY. 
		statement = connection.prepareStatement(sql);

		// SETANDO OS VALORES DO STATEMENT.
		statement.setString(1, project.getName());
		statement.setString(2, project.getDescription());
		statement.setDate(4, new Date(project.getCreatedAt().getTime()));
		statement.setDate(5, new Date(project.getUpdatedAt().getTime()));

		// EXECUTANDO A QUERY.
		statement.execute();

	} catch(SQLException e) {
	
		throw new RuntimeException("Erro ao salvar o Projeto" + e.getMessage());
	} finally{
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException("Erro ao fechar a conexão" + e.getMessage());
			}
		}
	}

	public void update(Project project) {
		
		String sql = "UPDATE projects SET name = ?, description = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
		
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
		}finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException("Erro ao fechar a conexão" + e.getMessage());
			}
		}
	}
	    //PRIVATE
	    public List<Project> getAll() { //PARAMETROS(List<Project> projects)

		String sql = "SELECT * FROM projects"; //BUSCA TODOS OS PROJETOS SEM FILTRAR.
		
		List<Project> projects = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;//REPRESENTANDO OS DADOS NO BANCO.

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
		
			//EXECUTAR ENQUANTO HOUVER DADOS NO BANCO.
			while (resultSet.next()) {

				Project project = new Project(); //PARAMETROS (0, SQL, SQL, NULL, NULL)
				
				//SETANDO AS INFORMAÇÕES DO PROJETO:
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setDescription(resultSet.getString("description"));
				project.setCreatedAt(resultSet.getDate("createdAt"));
				project.setUpdatedAt(resultSet.getDate("updatedAt"));
				projects.add(project); //ADICIONANDO O PROJETO NA LISTA DE PROJETOS

			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar projetos" + e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException("Erro ao fechar a conexão" + e.getMessage());
			}
			
		}
			
		return projects;
	}
	
	public void removeById(int id) {
		
		String sql = "DELETE FROM projects WHERE Id = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);//SETANDO O PARAMETRO COM O VALOR QUE ESTÁ DENTRO DO ID PROJECT.
			statement.execute();
		} catch (SQLException e){
			throw new RuntimeException("Erro ao deletar o projeto" + e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException("Erro ao fechar a conexão" + e.getMessage());
			}
		}
	
	}
	//OPERAÇÕES FUTURAS: BUSCAR PROJETOS PELO NOME (FAZER UM MÉTODO DE BUSCA POR NOME)
}
	


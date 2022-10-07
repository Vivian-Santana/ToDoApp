package main;


import java.util.List;
import controller.ProjectController;
import model.Project;


public class main {

	public static void main(String[] args) {
		
		ProjectController projectController = new ProjectController();
		
		Project project = new Project();
		project.setName ("Projeto teste");
		project.setDescription("descrição");
		projectController.save(project);
		
//		project.setName("Novo nome do projeto");
//		projectController.update(project);
//		
//		List<Project> projects = projectController.getAll();
//		System.out.println("total de projetos" + projects.size());

		
	}

}

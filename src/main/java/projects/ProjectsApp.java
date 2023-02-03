package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;


public class ProjectsApp {

	private ProjectService projectService = new ProjectService();
	private Scanner scanner = new Scanner(System.in);
	private Project curProject;
	//List to hold our menu options
	// @formatter:off
	private List<String> operations = List.of(
			"1) Add a project",
			"2) List Projects",
			"3) Select a Project",
			"4) Update Project Details");
	// @formatter:on
	
	/**
	 * Entry point for Java Application
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		/**
		 * Only for Debugging Connects to database.
		 * Connection conn = DbConnection.getConnection();
		 */
		new ProjectsApp().processUserSelection();		
	}
	
	/**
	 * This method displays the menu selections, gets a selection 
	 * from the user, and then acts on the selection. It Repeats until the user
	 * Terminates the application
	 */
	private void processUserSelection() {
		boolean done =false;
		
		while(!done) {
			try{
				int selection = getUserSelection();

				switch (selection) {
				case -1:
					done = exitMenu();
					break;
				case 1:
					createProject();
					break;
				case 2:
					listProjects();
					break;
				case 3:
					selectProject();
					break;
				case 4: 
					updateProjectDetails();
					break;
				default:
					System.out.println("\n"+selection + " is invalid. Try again.");
					break;

				}		
			}
			catch(Exception e) {
				System.out.println("\nError: "+e+"\nTry again");
			}
		}
		
	}
	private void updateProjectDetails() {
		if(Objects.isNull(curProject)) {
			System.out.println("Please select a project.");
			return;
		}
		
		String projectName = getStringInput("Enter the project name ["+curProject.getProjectName()+"]");
		BigDecimal estimatedHours = getDecimalInput("Enter the project estimated hours ["+curProject.getEstimatedHours()+"]");
		BigDecimal actualHours = getDecimalInput("Enter the project actual hours ["+curProject.getActualHours()+"]");
		Integer difficulty = getIntInput("Enter the project difficulty ["+curProject.getDifficulty()+"]");
		String notes = getStringInput("Enter the project notes ["+curProject.getNotes()+"]");
		
		Project project = new Project();
		
		project.setProjectId(curProject.getProjectId());
		project.setProjectName(Objects.isNull(projectName)?curProject.getProjectName():projectName);
		project.setEstimatedHours(Objects.isNull(estimatedHours)?curProject.getEstimatedHours():estimatedHours);
		project.setActualHours(Objects.isNull(actualHours)?curProject.getActualHours():actualHours);
		project.setDifficulty(Objects.isNull(difficulty)?curProject.getDifficulty():difficulty);
		project.setNotes(Objects.isNull(notes)?curProject.getNotes():notes);
		
		projectService.modifyProjectDetails(project);
		
		curProject=projectService.fetchProjectById(curProject.getProjectId());
		
	}

	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Please, select a valid project id");
		
		/* Unselect the current project */
		curProject = null;
		
		/*Throw an exception, if project id is ivalid */
		curProject=projectService.fetchProjectById(projectId);
	}

	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		
		System.out.println("\nProjects: ");
		projects.forEach(project -> System.out.println("    "+project.getProjectId()+": "+project.getProjectName()));
		
	}

	/**
	 * Gather user input for a project row then call the project service to create the row.
	 */
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1 - 5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbproject = projectService.addProject(project);
		System.out.println("You have succesfuly created a project "+dbproject);
		
		
	}
	/**
	 * Get user input and convert to a BigDecimal
	 * 
	 * @param prompt The prompt to dispaly on the console.
	 * @return a BigDecimal if succesful
	 * @throws DbException if an error occurs coverting to a BigDecimal
	 */
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input))
			return null;
		
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not valid decimal number.");
		}
		
	}
	/**
	 * Called when user exits the applcation. 
	 */

	private boolean exitMenu() {
		System.out.println("\nExiting system..... Goodbye");
		return true;
	}

	/**
	 * Prints the menu. 
	 * It then gets user input and coverst to an int.
	 * If -1 or blank is entered it exits
	 */
	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter menu selection");
		
		return Objects.isNull(input) ? -1 :input;
	}

	/**
	 * Get user input and convert to a Integer	 
	 */
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input))
			return null;
		
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not valid number.");
		}
		
	}

	/**
	 * Prints a prompt and gets user input
	 */
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null:input.trim();
		
		
	}

	//This method displays the menu selections
	private void printOperations() {
		System.out.println("\nThese are the available menu selections\nPress the Enter key to quit:");
		
		//prints operations list
		operations.forEach(line -> System.out.println("   "+line));
		
		if(Objects.isNull(curProject)) 
			System.out.println("\nYou are not working with a project");
		else
			System.out.println("\nYou are working with project: "+curProject);
	}
	


}

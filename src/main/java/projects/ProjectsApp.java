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
	//List to hold our menu options
	// @formatter:off
	private List<String> operations = List.of(
			"1) Add a project");
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
				default:
					System.out.println("\n"+selection + " is invalid. Try again.");
					break;

				}
				
				
			}catch(Exception e) {
				System.out.println("\nError: "+e+" please try again.");
			}
		}
		
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
		Integer input = getIntInput("Enter menu selection: ");
		
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
			throw new DbException(input + " is not valid number");
		}
		
	}

	/**
	 * PRints a prompt and gets user input
	 */
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null:input.trim();
		
		
	}

	//This method displays the menu selections
	private void printOperations() {
		System.out.println("\nThese are the available menu selections");
		
		//prints operations list
		operations.forEach(line -> System.out.println("   "+line));
	}
	


}

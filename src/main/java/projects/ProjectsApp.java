package projects;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.exception.DbException;


public class ProjectsApp {

	
	private Scanner scanner = new Scanner(System.in);
	//List to hold our menu options
	// @formatter:off
	private List<String> operations = List.of(
			"1) Add a project");
	// @formatter:on
	
	public static void main(String[] args) {
		//**Only for Debugging** Connects to database.
		//Connection conn = DbConnection.getConnection();
		
		new ProjectsApp().processUserSelection();
		
	}
	
	//This method displays the menu selections, gets a selection 
	//from the user, and then acts on the selection. 
	private void processUserSelection() {
		boolean done =false;
		
		while(!done) {
			try{
				int selection = getUserSelection();
				

				switch (selection) {
				case -1:
					done = exitMenu();
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

	private boolean exitMenu() {
		System.out.println("\nExiting system..... Goodbye");
		return true;
	}

	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter menu selection: ");
		
		return Objects.isNull(input) ? -1 :input;
	}

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

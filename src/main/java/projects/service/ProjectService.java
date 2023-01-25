package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import projects.dao.ProjectDao;
import projects.entity.Project;

//The service layer in this small application is implemented by a single file, 
//ProjectService.java. Mostly this file acts as a pass-through between the 
//main application file that runs the menu (ProjectsApp.java) and the 
//DAO file in the data layer (ProjectDao.java).
public class ProjectService {

	private ProjectDao projectDao = new ProjectDao();
	
	
	/**Calls the DAO class to insert a record into the project table.
	*
	* @param project the {@link Project} object.
	* @return The Project object with a generated key value.
	*/
	public Project addProject(Project project) {
		
		return projectDao.insertProject(project); 
		
	}


	public List<Project> fetchAllProjects() {
		
		return projectDao.fetchAllProjects();
	}


	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchByProjectId(projectId).orElseThrow(
			   ()-> new NoSuchElementException(
				"Project with Project ID " + projectId +
				"does not exist."));
	}




}

package projects;

import java.sql.Connection;

import projects.dao.DbConnection;

public class ProjectsApp {

	public static void main(String[] args) {
		//Connect to Database
		Connection conn = DbConnection.getConnection();
		
	}

}

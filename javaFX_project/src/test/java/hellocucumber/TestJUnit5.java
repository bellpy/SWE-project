package hellocucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dtu.example.handler.ActivityHandler;
import dtu.example.handler.ProjectHandler;
import dtu.example.model.Activity;
import dtu.example.model.DbContext;
import dtu.example.model.Project;

public class TestJUnit5 {
	@Test // Tests that activity if activity exists then it adds the user initials
	public void testAssignUserToActivity_addsUserInitials() {
		DbContext dbContext = new DbContext() {
			{
				activities = new ArrayList<>();
			}
		};

		Activity activity = new Activity(42, "Test Activity", 1001);
		dbContext.activities.add(activity);

		ActivityHandler handler = new ActivityHandler(dbContext);

		handler.assignUserToActivity(42, "AB");

		assertTrue(activity.getUserInitials().contains("AB"));
	}

	@Test // Tests that if activity does not exist then it does nothing
	public void testAssignUserToActivity_activityNotFound_doesNothing() {
		DbContext dbContext = new DbContext() {
			{
				activities = new ArrayList<>();
			}
		};

		ActivityHandler handler = new ActivityHandler(dbContext);

		handler.assignUserToActivity(99, "ZZ");

		assertTrue(dbContext.activities.isEmpty());
	}

	@Test
	public void testGetProjectById_found() {
		DbContext dbContext = new DbContext();
		Project project = new Project("Test Project", 1001L);
		dbContext.projects = new ArrayList<>();
		dbContext.projects.add(project);

		ProjectHandler handler = new ProjectHandler(dbContext);
		Project result = handler.getProjectById(1001L);

		assertNotNull(result);
		assertEquals("Test Project", result.getName());
	}

	@Test
	public void testGetProjectById_notFound() {
		DbContext dbContext = new DbContext();
		dbContext.projects = new ArrayList<>(); // empty list

		ProjectHandler handler = new ProjectHandler(dbContext);
		Project result = handler.getProjectById(9999L);

		assertNull(result);
	}
}

Feature: Project Handler

Scenario: Create a new project
    Given the project handler have been initilized
    When I create a new project with name "MyProject"
    Then the dbContext contains the project "MyProject"

Scenario: Add an existing project
    Given the project handler have been initilized
    And I have a project with name "Existing Project"
    When I add the project to the handler
    Then the project exists in the dbContext

Scenario: Remove an existing project
    Given the project handler have been initilized
    And there is a project with ID 12345 in the system
    When I remove the project with ID 12345
    Then the project is successfully removed
    And the project is no longer in the dbContext
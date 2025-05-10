Feature: Project Handler

Scenario: Create a new project
    Given the project handler has been initialized
    When I create a new project with name "MyProject"
    Then the dbContext contains the project "MyProject"

Scenario: Retrieve a project by ID
    Given the project handler has been initialized
    And I create a new project with name "TestProject"
    When I retrieve the project by its ID
    Then the retrieved project has the name "TestProject"

Scenario: Retrieve a project by an invalid ID
    Given the project handler has been initialized
    When I retrieve a project by ID -1
    Then no project is retrieved

Scenario: Retrieve a project by ID
    Given the project handler has been initialized
    And I create a new project with name "TestProject" with manager "TestManager"
    When I retrieve the project by its ID
    Then the project details has "TestManager"

Scenario: Manage projects by adding and removing
    Given the project handler has been initialized
    And I create a new project with name "ProjectToAdd"
    When I manage projects by adding "ProjectToAdd" and removing its ID
    Then the project "ProjectToAdd" is added and removed successfully
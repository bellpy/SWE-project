Feature: Project Handler

Scenario: Create a new project
    Given the project handler has been initialized
    When I create a new project with name "MyProject"
    Then the dbContext contains the project "MyProject"
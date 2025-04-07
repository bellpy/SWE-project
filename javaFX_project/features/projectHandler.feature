Feature: Project Handler

Scenario: Create a new project
    Given the project handler have been initilized
    When I create a new project with name "MyProject"
    Then the dbContext contains the project "MyProject"
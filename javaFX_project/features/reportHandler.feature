
Feature: Report generation for projects

    Scenario: Generate a report for a project
        Given the report handler has been initialized
        And the database contains a project with number 1 and activities and activities
        When I generate a report for project number 1
        Then the report contains details of all activities for project number 1

Feature: Report generation for projects

    Scenario: Generate a report for a project
        Given the report handler has been initialized
        And the database contains a project with number 1 and activities and activities
        When I generate a report for project number 1
        Then the report contains details of all activities for project number 1

    Scenario: Save a report to a file
        Given the report handler has been initialized
        When I save a report with content "Test Report Content" for project number 1
        Then a file is created with the report content
        
    Scenario: Generate and save a report for a project
        Given the report handler has been initialized
        And the database contains a project with number 1 and activities and activities
        When I generate and save a report for project number 1
        Then a file is created with the generated report content
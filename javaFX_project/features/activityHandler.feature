Feature: Activity Handler

Scenario: User creating activity on project
    Given the activity handler have been initilized
    When they create a new activity with "myActivity"
    Then the dbContext activity is created

Scenario: User adds an employee to an activity succesfully
    Given a user with initials "user" exists
    And an employee with initials "abcd" exists
    And an activity with number 25001 exists
    When user adds employee with initials "abcd" to the activity
    Then the activity 25001 has the employee with initials "abcd", assigned to it
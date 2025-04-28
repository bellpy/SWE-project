Feature: Activity Handler

Scenario: User creating acvtivity on project
    Given the activity handler have been initilized
    When they create a new activity with "myActivity"
    Then the dbContext activity is created

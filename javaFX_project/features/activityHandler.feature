Feature: Activity Handler

Scenario: User can retrieve their assigneed activities
    Given the activity handler have been initialized
    And a user with initials "user" exists
    And is assigned an activity with name "myActivity"
    When user retrieves their assigned activities
    Then the activity with name "myActivity" is returned

Scenario: Update an activity
    Given the activity handler have been initialized
    And an activity with name "OldActivity" exists
    When the activity is updated with name "UpdatedActivity"
    Then the activity with name "UpdatedActivity" is returned

Scenario: Retrieve users by activity number
    Given the activity handler have been initialized
    And an activity with name "TeamActivity" exists
    And users "JS, AB" are assigned to the activity
    When users are retrieved by activity number
    Then the users "JS, AB" are returned
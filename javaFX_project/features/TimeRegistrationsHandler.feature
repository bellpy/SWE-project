Feature: Time Registrations Handler

  Background:
    Given the time registrations handler has been initialized

  Scenario: User creating time registration on activity
    Given a user with initials "user" exists and activity with number 25001 exists
    When user creates a new time registration for activity 25001 with hours 8
    Then the dbContext contains the time registration for activity 25001 with hours 8

  Scenario: User can retrieve their time registrations
    Given a user with initials "user" exists and an activity with number 25001 exists
    When user creates a new time registration for activity 25001 from 2 days ago
    Then get time registration returns for user "user" and activity 25001 the current date minus 2 days
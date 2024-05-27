Feature: Company Search
  Scenario: Search for a company
    Given I have searched for an existing company
    Then the status code should be 200

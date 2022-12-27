@UIFeature
Feature: As the Clerk, I should be able to upload a csv file to a portal so
  that I can populate the database from a UI

  Scenario Outline: Upload a csv file to a portal
    Given user navigates to upload csv file
    When csv file has valid details with first row as valid header
      | natid    |
      | name     |
      | gender   |
      | salary   |
      | birthday |
      | tax      |
    When user clicks on browse button and selects the CSV file to upload data
    Then user click on Refresh Tax Relief Table button
    And validate fields natid and tax relief amount populated in table
    And validates natid field must be masked from the 5th character onwards with dollar sign ‘$’
    And computation of the tax relief is using the formula as described
    And calculated tax relief amount after rounding off is more than zero but less than fifty final tax relief amount should be '50.00'
    And calculated tax relief amount before rounding off should have two decimal places
    And the button on the screen must be red-colored
    And The text on the button must be exactly <dispenseNow>
    And click on the button Dispense Now
    Then it should direct me to a page with a text that says <cashDispensed>

    Examples: 
      | dispenseNow  | cashDispensed  |
      | Dispense Now | Cash dispensed |

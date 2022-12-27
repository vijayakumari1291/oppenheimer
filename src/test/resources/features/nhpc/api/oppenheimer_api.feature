@ApiFeature
Feature: As the Clerk, I should be able to insert working class heros into database via an API

  Scenario Outline: To insert new working class hero
    When user inserts a new working class hero
      | natid   | name   | gender   | salary   | birthday   | tax   |
      | <natid> | <name> | <gender> | <salary> | <birthday> | <tax> |
    Then user should get the response code 202
    And user validates the response

    Examples: 
      | natid       | name              | gender | salary | birthday | tax |
      | GBU94XVU3YW | Jaquelyn Espinoza | F      |   9550 | 30082022 | 950 |

  Scenario Outline: To insert new working class hero with wrong dob format
    When user inserts a new working class hero with wrong dob format
      | natid   | name   | gender   | salary   | birthday   | tax   |
      | <natid> | <name> | <gender> | <salary> | <birthday> | <tax> |
    Then user should get the response code 500
    And user validates error message in the response

    Examples: 
      | natid       | name         | gender | salary | birthday | tax |
      | KFU38CIW8NN | Eric Knowles | F      |    100 | 04272022 |   5 |
      | KFU38CIW8NN | Eric Knowles | F      |    100 | YYff     |   5 |
      | KFU38CIW8NN | Eric Knowles | F      |    100 |      123 |   5 |

  Scenario Outline: To insert new working class hero with wrong gender
    When user inserts a new working class hero with wrong gender
      | natid   | name   | gender   | salary   | birthday   | tax   |
      | <natid> | <name> | <gender> | <salary> | <birthday> | <tax> |
    Then user should get the response code 500
    And user validates wrong gender error message in the response

    Examples: 
      | natid       | name         | gender | salary | birthday | tax |
      | KFU38CIW8NN | Eric Knowles | Male   |    100 | 27042022 |   5 |

  Scenario: To insert more than one working class hero
    When user inserts list of new working class heros
      | natid       | name           | gender | salary | birthday | tax |
      | LEJ66YTD8SM | Dolan Sheppard | F      |   9600 | 22082022 | 955 |
      | UIS81FGO8SN | Caryn Lynch    | F      |   9650 | 17012023 | 960 |
      | RLI21FBJ4FH | Callum Oliver  | F      |   9950 | 12082023 | 990 |
    Then user should get the response code 202
    And user validates the response

  Scenario: To get tax releif details with a list consist of natid, tax relief
    amount and name

    When user tries to get tax releif details
    Then user should get the response code 200
    And user validates the tax releif response json

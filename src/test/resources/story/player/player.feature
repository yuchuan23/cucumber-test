Feature: to test player
    fill out description about this feature

    @player @player1
    Scenario: given a basic player
        Given player with name is "hello" and default level
        Then player name should be "hello"
        When I rename player to "world"
        Then player name should be "world"
        Then player level should be 0

    Scenario Outline: given multiple player (run the same Scenario multiple times, with different combinations of values.)
        Given player with name is "<name>" and level is <level>
        Then player name should be "<name>"
        Then player level should be <level>
        When I ask player rank
        Then player rank should be "<rank>"
        When I levelup with 20
        Then player level should be <level2>
        Then player rank should be "<rank>"
        When I ask player rank
        Then player rank should be "<rank2>"

        @player @player2
        Examples:
          | name    | level | rank | level2 | rank2 |
          | red     | 70    | B    | 90     | A     | 
          | Alice   | 10    | E    | 30     | D     |
          | Asuna   | 9     | E    | 29     | E     |

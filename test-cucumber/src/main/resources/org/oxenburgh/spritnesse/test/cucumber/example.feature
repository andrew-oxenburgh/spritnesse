Feature: Example of a feature file
    As some aspiring cuke4duke user
    I want an example of how it works
    So that I can easily setup my project to use it

    Scenario: A simple passing scenario
        Given the letter 'A'
        When I check the letter
        Then the letter should be 'A'

    Scenario: A simple failing scenario
        Given the letter 'A'
        When I check the letter
        Then the letter should be 'B'


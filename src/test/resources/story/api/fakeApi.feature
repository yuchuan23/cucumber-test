Feature: to test a fake api
    this feature is used to demo how to test apis.

    Scenario Outline: to test fake get api
        Given A login user
        When I make a "GET" request to "/photos/<id>"
        Then The status code of last response should be 200
        Then The "/id" of last response should be larger than "0"
        Then The "/id" of last response should be smaller than "3"
        Then The "/id" of last response should be "<id>"
        Then The "/id" of last response should not be "3"
        Then The "/id" of last response should not be "empty"
        Then The "/ids" of last response should be "empty"

        @fakeApi @fakeGet @fakeSingle
        Examples:
          | id    |
          | 1     |
          | 2     |

    Scenario Outline: to test fake list api
        Given A login user
        When I make a "GET" request to "/photos?albumId=<albumId>"
        Then The status code of last response should be 200
        Then The size of "" of last response should be "50"
        Then The size of "" of last response should be larger than "10"

        @fakeApi @fakeGet @fakeList
        Examples:
          | albumId |
          | 1       |

    Scenario Outline: to test fake post api
        Given A non login user
        When I make a "POST" request to "/photos" and payload='{"title": "photo title"}'
        Then The status code of last response should be 201

        @fakeApi @fakePost
        Examples:
          | dummy |
          | _     |

    Scenario Outline: to test fake put api
        Given A non login user
        When I make a "PUT" request to "/photos/<id>" and payload='{"title": "photo title <id>"}'
        Then The status code of last response should be 200

        @fakeApi @fakePut
        Examples:
          | id    |
          | 1     |
          | 2     |

    Scenario Outline: to test fake delete api
        Given A login user
        When I make a "DELETE" request to "/photos/<id>"
        Then The status code of last response should be 200

        @fakeApi @fakeDelete
        Examples:
          | id    |
          | 1     |
          | 2     |

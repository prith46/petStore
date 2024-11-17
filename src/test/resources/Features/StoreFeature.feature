Feature: All Store related API Requests

  Scenario: Successfully view pet inventory with valid request
    Given the user sets up base store URL
    When the user views the pet inventory
    Then the store response returns status code 200
    And the response contains a list of pet statuses and their counts

  Scenario: View pet inventory with invalid parameters
    Given the user provides invalid parameters invalidParam=true
    When the user views the pet inventory
    Then the store response returns status code 200
    And the response contains a list of pet statuses and their counts

  Scenario: Successfully place an order with valid data
    Given the user sets up base store URL
    And the user provides petId 123 valid order data with quantity 5
    When the user places an order for the pet
    Then the store response returns status code 200
    And the response contains the order details

  Scenario: Place an order with missing mandatory fields
    Given the user sets up base store URL
    And the user provides order data missing mandatory fields
    When the user places an order for the pet
    Then the store response returns status code 400
    And the response message indicates "bad input"

  Scenario: Place an order with invalid data types
    Given the user sets up base store URL
    And the user provides petId 123 invalid order data with quantity "abc"
    When the user places an order for the pet
    Then the store response returns status code 400
    And the response message indicates "bad input"

  Scenario: Place an order with an excessively large quantity
    Given the user sets up base store URL
    And the user provides order data with quantity 10000000000
    When the user places an order for the pet
    Then the store response returns status code 500
    And the response message indicates "something bad happened"

  Scenario: Place an order with negative quantity
    Given the user sets up base store URL
    And the user provides order data with quantity -5
    When the user places an order for the pet
    Then the store response returns status code 200

  Scenario: Retrieve an existing order by orderId
    Given the user sets up base store URL
    When user send the GET request for orderId 5
    Then the store response returns status code 200
    And the response body should contain the orderId 5

  Scenario: Delete an existing order by orderId
    Given the user sets up base store URL
    When user sends a DELETE request to orderId 5
    Then the store response returns status code 200
    And the response message indicates "5"

  Scenario: Retrieve a non-existent order
    Given the user sets up base store URL
    When user send the GET request for orderId 5
    Then the store response returns status code 404
    And the response message indicates "Order not found"

  Scenario: Retrieve an order with an invalid orderId
    Given the user sets up base store URL
    When user send the GET request for invalid orderId "12-34"
    Then the store response returns status code 404
    And the response message indicates "java.lang.NumberFormatException: For input string: \"12-34\""

  Scenario: Retrieve order details without providing orderId
    Given the user sets up base store URL
    When user send the GET request for empty orderId
    Then the store response returns status code 405

  Scenario: Delete a non-existent order
    Given the user sets up base store URL
    When user send the GET request for orderId 5
    Then the store response returns status code 404
    And the response message indicates "Order not found"

  Scenario: Delete an order without providing orderId
    Given the user sets up base store URL
    When user send the DELETE request for empty orderId
    Then the store response returns status code 405

  Scenario: Delete an order with invalid characters in orderId
    Given the user sets up base store URL
    When user send the DELETE request for invalid orderId "15-55"
    Then the store response returns status code 404
    And the response message indicates "java.lang.NumberFormatException: For input string: \"15-55\""
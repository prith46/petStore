Feature: All Pet related API Requests

  Scenario: Successfully add a pet with valid data
    Given a valid pet payload
    When the user adds the pet to the store
    Then the pet is successfully added with status code 200

  Scenario: Add a pet with missing mandatory fields
    Given a pet payload with missing mandatory fields
    When the user adds the pet to the store
    Then the response returns status code 400

  Scenario: Add a pet with invalid data types
    Given a pet payload with invalid data types
    When the user adds the pet to the store
    Then the response returns status code 400

  Scenario: Add a pet with an excessively long name
    Given a pet payload with an excessively long name
    When the user adds the pet to the store
    Then the response returns status code 200

  Scenario: Add a pet with invalid characters in petId
    Given a pet payload with invalid characters in petId
    When the user adds the pet to the store
    Then the response returns status code 400

  Scenario: Successfully retrieve an existing pet by petId
    Given the user prepares the get URL
    When the user retrieves the pet by petId
    Then the response returns status code 200
    And the pet details are returned with petId

  Scenario: Retrieve a non-existent pet by petId
    Given the user prepares the get URL
    When the user retrieves the non-existent pet by petId 44323239
    Then the response returns status code 404
    And the response message is "Pet not found"

  Scenario: Retrieve a pet with invalid petId
    Given the user prepares the get URL
    When the user retrieves the pet by invalid petID "abc"
    Then the response returns status code 404
    And the response message is "java.lang.NumberFormatException: For input string: \"abc\""

  Scenario: Retrieve pet details without providing petId
    Given the user prepares the get URL
    When the user attempts to retrieve the pet without petID
    Then the response returns status code 405

  Scenario: Retrieve pet details with a very large petId value
    Given the user prepares the get URL
    When the user retrieves the pet by large petID 99999999999999
    Then the response returns status code 404
    And the response message is "Pet not found"

  Scenario: Successfully update an existing pet with valid data
    Given user gets a valid pet update payload
    When the user updates the pet details
    Then the response returns status code 200
    And the pet details are updated successfully

  Scenario: Update an existing pet with partial data
    Given an existing pet with petId with a partial pet update payload
    When the user updates the pet details
    Then the response returns status code 200
    And the pet details are updated with partial information

  Scenario: Update pet details with an empty request body
    Given an existing pet with petId with an empty update request body
    When the user updates the pet details
    Then the response returns status code 405

  Scenario: Successfully delete an existing pet by petId
    Given the user prepares the get URL
    When the user deletes the pet by petId
    Then the response returns status code 200
    And the response message is deleted petId

  Scenario: Delete a non-existent pet by petId
    Given the user prepares the get URL
    When the user deletes the non-existent pet by petId
    Then the response returns status code 404

  Scenario: Delete a pet without providing petId
    Given the user prepares the get URL
    When the user attempts to delete the pet without petID
    Then the response returns status code 405

  Scenario: Delete a pet with special characters in petId
    Given the user prepares the get URL
    When the user deletes the pet by petId "@!#123"
    Then the response returns status code 404
    And the response message is "java.lang.NumberFormatException: For input string: \"@!#123\""

  Scenario: Find pets with status available
    Given the user prepares the get URL with status "available"
    When the user searches for pets
    Then the response returns status code 200
    And a list of pets with status "available" is returned

  Scenario: Find pets with status pending
    Given the user prepares the get URL with status "pending"
    When the user searches for pets
    Then the response returns status code 200
    And a list of pets with status "pending" is returned

  Scenario: Find pets with status sold
    Given the user prepares the get URL with status "sold"
    When the user searches for pets
    Then the response returns status code 200
    And a list of pets with status "sold" is returned

  Scenario: Find pets with invalid status
    Given the user prepares the get URL with status "invalid_status"
    When the user searches for pets
    Then the response returns status code 200
    And an empty list is returned

  Scenario: Successfully upload an image for an existing pet
    Given the user prepares the URL for existing pet
    And an image file of type jpg
    When the user uploads the image for the pet
    Then the response returns status code 200

  Scenario: Successfully upload an image for an existing pet with txt file
    Given the user prepares the URL for existing pet
    And an image file of type txt
    When the user uploads the image for the pet
    Then the response returns status code 200

  Scenario: Upload an image for a non-existent pet
    Given prepare url for a non-existent pet
    And an image file of type jpg
    When the user uploads the image for the pet
    Then the response returns status code 200

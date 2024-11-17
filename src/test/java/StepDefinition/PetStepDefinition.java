package StepDefinition;

import Utilities.PetAPIResources;
import Utilities.PetPayload;
import Utilities.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

public class PetStepDefinition extends Utils {
    RequestSpecification req;
    Response response;
    String petId = "111";

    @Given("a valid pet payload")
    public void a_valid_pet_payload() throws IOException {
        req = given().spec(getReq())
                .body(PetPayload.getValidAddPet(petId, "2", "German", "Runo Sison", "available"));
    }

    @When("the user adds the pet to the store")
    public void the_user_adds_the_pet_to_the_store(){
        response = req.when().post(PetAPIResources.AddPet.getResource());
    }

    @Then("the pet is successfully added with status code {int}")
    public void the_pet_is_successfully_added_with_status_code(int statusCode){
        assertEquals(response.getStatusCode(), statusCode);
    }

    @Given("a pet payload with missing mandatory fields")
    public void a_pet_payload_with_missing_fields() throws IOException {
        req = given().spec(getReq())
                .body(PetPayload.getMissingPayload());
    }

    @Then("the response returns status code {int}")
    public void the_response_returns_status_code(int statusCode){
        assertEquals(response.getStatusCode(), statusCode);
    }

    @Given("a pet payload with an excessively long name")
    public void a_pet_payload_with_long_name() throws IOException {
        req = given().spec(getReq())
                .body(PetPayload.getLongPetNamePayload());
    }

    @Given("a pet payload with invalid characters in petId")
    public void a_pet_payload_with_invalid_characters() throws IOException {
        req = given().spec(getReq())
                .body(PetPayload.getValidAddPet("!$%134", "2", "German", "Max", "available"));
    }

    @Given("a pet payload with invalid data types")
    public void a_pet_with_invalid_data_types() throws IOException {
        req = given().spec(getReq())
                .body(PetPayload.getInvalidDatatypesPayload());
    }

    @Given("the user prepares the get URL")
    public void get_an_existing_pet() throws IOException {
        req = given().spec(getReq());
    }

    @When("the user retrieves the pet by petId")
    public void retrieves_by_petId(){
        response = req.when().get(PetAPIResources.GetPetById.getResource().replace("{petId}", petId));
    }

    @And("the pet details are returned with petId")
    public void verify_petId(){
        String gotPetId = getJsPath(response, "id");
        assertEquals(gotPetId, petId);
    }

    @When("the user retrieves the non-existent pet by petId {int}")
    public void retrieves_non_existent_pet_id(int invalidPetId){
        String invalidStringPetID = ""+invalidPetId;
        response = req.when().get(PetAPIResources.GetPetById.getResource().replace("{petId}", invalidStringPetID));
    }

    @And("the response message is {string}")
    public void verify_error_message(String message){
        String responseMessage = getJsPath(response, "message");
        assertEquals(responseMessage, message);
    }

    @When("the user retrieves the pet by invalid petID {string}")
    public void retrieve_by_invalid_petID(String invalidPetId){
        response = req.when().get(PetAPIResources.GetPetById.getResource().replace("{petId}", invalidPetId));
    }

    @When("the user attempts to retrieve the pet without petID")
    public void retrieve_without_petId(){
        response = req.when().get(PetAPIResources.GetPetById.getResource().replace("{petId}", ""));
    }

    @When("the user retrieves the pet by large petID {long}")
    public void retrieve_using_large_petId(long tempPetId){
        String longPetId = ""+tempPetId;
        response = req.when().get(PetAPIResources.GetPetById.getResource().replace("{petId}", longPetId));
    }

    @Given("user gets a valid pet update payload")
    public void user_gets_a_valid_pet_update_payload() throws IOException {
        req = given().spec(getReq())
                .body(PetPayload.getValidAddPet(petId, "5", "Indian", "Rick", "available"));
    }

    @When("the user updates the pet details")
    public void user_updates_the_pet_details(){
        response = req.when().put(PetAPIResources.AddPet.getResource());
    }

    @And("the pet details are updated successfully")
    public void verify_pet_details_are_updated(){
        String responseName = getJsPath(response, "name");
        assertEquals(responseName, "Rick");
        response.then()
                .statusCode(200)
                .body("category.id", equalTo(5))
                .body("category.name", equalTo("Indian"));
    }

    @Given("an existing pet with petId with a partial pet update payload")
    public void updating_with_partial_payload() throws IOException {
        req = given().spec(getReq())
                .body(PetPayload.getValidAddPet(petId, "5", "", "Morpheus", ""));
    }

    @And("the pet details are updated with partial information")
    public void verify_partial_update_payload(){
        String responseName = getJsPath(response, "name");
        String responseStatus = getJsPath(response, "status");
        assertEquals(responseName, "Morpheus");
        assertEquals(responseStatus, "");
        response.then()
                .statusCode(200)
                .body("category.id", equalTo(5))
                .body("category.name", equalTo(""));
    }

    @Given("an existing pet with petId with an empty update request body")
    public void update_with_empty_body() throws IOException {
        req = given().spec(getReq())
                .body("");
    }

    @When("the user deletes the pet by petId")
    public void user_deletes_pet(){
        response = req.when().delete(PetAPIResources.DeletePet.getResource().replace("{petId}", petId));
    }

    @And("the response message is deleted petId")
    public void verify_deleted_petID(){
        String responseMessage = getJsPath(response, "message");
        assertEquals(responseMessage, petId);
    }

    @When("the user deletes the non-existent pet by petId")
    public void delete_non_existent_petId(){
        response = req.when().delete(PetAPIResources.DeletePet.getResource().replace("{petId}", petId));
    }

    @When("the user attempts to delete the pet without petID")
    public void delete_without_petID(){
        response = req.when().delete(PetAPIResources.DeletePet.getResource().replace("{petId}", ""));
    }

    @When("the user deletes the pet by petId {string}")
    public void delete_pet_with_invalid_petID(String invalidPetId){
        response = req.when().delete(PetAPIResources.DeletePet.getResource().replace("{petId}", invalidPetId));
    }

    @Given("the user prepares the get URL with status {string}")
    public void get_pets_with_given_status(String status) throws IOException {
        req = given().spec(getReq())
                .queryParam("status", status);
    }

    @When("the user searches for pets")
    public void user_searched_for_pet(){
        response = req.when().get(PetAPIResources.GetPetByStatus.getResource());
    }

    @And("a list of pets with status {string} is returned")
    public void verify_returned_pet_status(String status){
        response.then().statusCode(200)
                .body("status[0]", equalTo(status));
    }

    @And("an empty list is returned")
    public void verify_invalid_status_body(){
        response.then().statusCode(200)
                .body("", hasSize(0));
    }

    @Given("the user prepares the URL for existing pet")
    public void prepares_url_for_imageUpload() throws IOException {
        a_valid_pet_payload();
        the_user_adds_the_pet_to_the_store();
        req = given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.MULTIPART);
    }

    @And("an image file of type jpg")
    public void adding_image_to_formData(){
        req.multiPart("file", new File("C:\\Users\\Dell\\Pictures\\Screenshots\\upload_image.png"));
    }

    @When("the user uploads the image for the pet")
    public void user_upload_image(){
        response = req.when().post(PetAPIResources.UploadPetImage.getResource().replace("{petId}", petId));
    }

    @And("an image file of type txt")
    public void upload_txt_file(){
        req.multiPart("file", new File("C:\\Users\\Dell\\Pictures\\Screenshots\\testDoc.txt"));
    }

    @Given("prepare url for a non-existent pet")
    public void prepare_upload_for_non_existent_pet() throws IOException {
        get_an_existing_pet();
        user_deletes_pet();
        req = given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.MULTIPART);
    }

}

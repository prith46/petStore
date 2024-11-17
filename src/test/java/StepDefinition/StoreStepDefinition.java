package StepDefinition;

import Utilities.StoreAPIResources;
import Utilities.StorePayload;
import Utilities.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class StoreStepDefinition extends Utils {

    RequestSpecification req;
    Response response;
    public String orderId = "5";

    @Given("the user sets up base store URL")
    public void user_setup_base_store_url() throws IOException {
        req = given().spec(getReq());
    }

    @When("the user views the pet inventory")
    public void get_store_inventory(){
        response = req.when().get(StoreAPIResources.GetStoreInventory.getStoreResource());
    }

    @Then("the store response returns status code {int}")
    public void the_response_returns_status_code(int statusCode){
        assertEquals(response.getStatusCode(), statusCode);
    }

    @And("the response contains a list of pet statuses and their counts")
    public void verify_inventory_result(){
        response.then()
                .body("available", greaterThanOrEqualTo(0))
                .body("pending", greaterThanOrEqualTo(0))
                .body("sold", greaterThanOrEqualTo(0));
    }

    @Given("the user provides invalid parameters invalidParam=true")
    public void user_provides_invalid_parameters() throws IOException {
        req = given().spec(getReq())
                .queryParam("invalidParam", "true");
    }

    @And("the user provides petId {int} valid order data with quantity {int}")
    public void user_provides_data(int petId, int quantity){
        req.body(StorePayload.getValidStorePayload(orderId, String.valueOf(petId), String.valueOf(quantity), "placed", "true"));
    }

    @When("the user places an order for the pet")
    public void user_places_an_order(){
        response = req.when().post(StoreAPIResources.PutOrder.getStoreResource());
    }

    @And("the response contains the order details")
    public void validate_response(){
        response.then()
                .body("id", equalTo(5))
                .body("petId", equalTo(123))
                .body("quantity", equalTo(5));
    }

    @And("the user provides order data missing mandatory fields")
    public void user_provides_missing_fields(){
        req.body(StorePayload.getInvalidPayload());
    }

    @And("the response message indicates {string}")
    public void verify_response_message(String message){
        String responseMessage = getJsPath(response, "message");
        assertEquals(responseMessage, message);
    }

    @And("the user provides petId {int} invalid order data with quantity {string}")
    public void user_provides_invalid_order_details(int petId, String quantity){
        req.body(StorePayload.getValidStorePayload(orderId, String.valueOf(petId), quantity, "placed", "true"));
    }

    @And("the user provides order data with quantity {long}")
    public void user_provides_long_quantity(long quantity){
        req.body(StorePayload.getValidStorePayload("1423", "23641", ""+quantity, "placed", "true"));
    }

    @When("user send the GET request for orderId {int}")
    public void user_sends_the_GET_request(int userOrderId){
        response = req.when().get(StoreAPIResources.GetOrderById.getStoreResource().replace("{orderId}", ""+userOrderId));
    }

    @And("the response body should contain the orderId {int}")
    public void verify_orderId_in_response(int userOrderId){
        response.then()
                .body("id", equalTo(userOrderId));
    }

    @When("user send the GET request for empty orderId")
    public void user_send_with_empty_orderId(){
        response = req.when().get(StoreAPIResources.GetOrderById.getStoreResource().replace("{orderId}", ""));
    }

    @When("user send the GET request for invalid orderId {string}")
    public void user_retrieves_invalid_orderID(String userOrderId){
        response = req.when().get(StoreAPIResources.GetOrderById.getStoreResource().replace("{orderId}", userOrderId));
    }

    @When("user sends a DELETE request to orderId {int}")
    public void user_sends_delete_request(int userOrderId){
        response = req.when().delete(StoreAPIResources.DeleteOrder.getStoreResource().replace("{orderId}", ""+userOrderId));
    }

    @When("user send the DELETE request for empty orderId")
    public void user_sends_empty_orderId_for_deletion(){
        response = req.when().delete(StoreAPIResources.DeleteOrder.getStoreResource().replace("{orderId}", ""));
    }

    @When("user send the DELETE request for invalid orderId {string}")
    public void user_sends_invalid_orderId_for_Deletion(String userOrderId){
        response = req.when().delete(StoreAPIResources.DeleteOrder.getStoreResource().replace("{orderId}", userOrderId));
    }
}

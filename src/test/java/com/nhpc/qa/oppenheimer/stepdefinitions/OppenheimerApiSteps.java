package com.nhpc.qa.oppenheimer.stepdefinitions;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

import com.google.gson.Gson;
import com.nhpc.qa.oppenheimer.model.WorkingClassHero;
import com.nhpc.qa.oppenheimer.utils.ConfigReader;
import com.nhpc.qa.oppenheimer.utils.LoggerHelper;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OppenheimerApiSteps {
	private static final String BASE_URL = ConfigReader.getInstance().getApiURL();
	private static final String INSERT_ENDPOINT_URL = "/insert";
	private static final String INSERTMULTIPLE_ENDPOINT_URL = "/insertMultiple";

	private static Response response;
	private static String jsonString;
	private static JSONObject responseObj;
	private static String actualErrorMsg;
	private static WorkingClassHero workingClassHeroData;
	private static final Logger log = LoggerHelper.getLogger(OppenheimerApiSteps.class);

	@DataTableType
	public WorkingClassHero authorEntry(Map<String, String> entry) {
		return new WorkingClassHero(entry.get("natid"), entry.get("name"), entry.get("gender"), entry.get("salary"),
				entry.get("birthday"), entry.get("tax"));
	}

	@When("^user inserts a new working class hero$")
	public void user_inserts_a_new_working_class_hero(List<WorkingClassHero> workingClassHeroDataTable) {
		try {
			workingClassHeroData = workingClassHeroDataTable.get(0);
			String workingClassHeroRequestBody = new Gson().toJson(workingClassHeroData);
			log.info("Input Request: " + workingClassHeroRequestBody);

			RestAssured.baseURI = BASE_URL;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");

			response = request.body(workingClassHeroRequestBody.toString()).post(INSERT_ENDPOINT_URL);
		} catch (Exception e) {
			log.error("Exception occured while inserting a new working class hero");
			assertFalse(true);
		}
	}

	@Then("^user should get the response code (\\d+)$")
	public void user_should_get_the_response_code(int statusCode) throws Throwable {
		Assert.assertEquals(statusCode, response.getStatusCode());
	}

	@Then("^user validates the response$")
	public void user_validates_the_response() throws Throwable {
		String responseBody = response.getBody().asString();
		log.info("responseBody --->" + responseBody);
		Assert.assertEquals("Alright", responseBody);
	}

	@When("user inserts a new working class hero with wrong dob format")
	public void user_inserts_a_new_working_class_hero_with_wrong_dob_format(
			List<WorkingClassHero> workingClassHeroDataTable) {
		try {
			workingClassHeroData = workingClassHeroDataTable.get(0);
			String workingClassHeroRequestBody = new Gson().toJson(workingClassHeroData);
			log.info("Input Request: " + workingClassHeroRequestBody);

			RestAssured.baseURI = BASE_URL;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");

			response = request.body(workingClassHeroRequestBody.toString()).post(INSERT_ENDPOINT_URL);
		} catch (Exception e) {
			log.error("Exception occured while inserting a new working class hero with wrong dob format");
			assertFalse(true);
		}
	}

	@Then("user validates error message in the response")
	public void user_validates_error_message_in_the_response() throws Exception {
		JSONParser parse = new JSONParser();
		responseObj = (JSONObject) parse.parse(response.getBody().asString());
		actualErrorMsg = (String) responseObj.get("message");
		log.info("Error Message: " + actualErrorMsg);
		Assert.assertTrue("Error Message is wrong", actualErrorMsg.contains("could not be parsed"));
	}

	@When("user inserts a new working class hero with wrong gender")
	public void user_inserts_a_new_working_class_hero_with_wrong_gender(
			List<WorkingClassHero> workingClassHeroDataTable) {
		workingClassHeroData = workingClassHeroDataTable.get(0);
		String workingClassHeroRequestBody = new Gson().toJson(workingClassHeroData);
		log.info("Input Request: " + workingClassHeroRequestBody);

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");

		response = request.body(workingClassHeroRequestBody.toString()).post(INSERT_ENDPOINT_URL);
	}

	@Then("user validates wrong gender error message in the response")
	public void user_validates_wrong_gender_error_message_in_the_response() throws Exception {
		JSONParser parse = new JSONParser();
		responseObj = (JSONObject) parse.parse(response.getBody().asString());
		actualErrorMsg = (String) responseObj.get("message");
		log.info("Error Message: " + actualErrorMsg);
		Assert.assertTrue("Error Message is wrong", actualErrorMsg.contains("could not execute statement; SQL"));
	}

	@When("^user inserts list of new working class heros$")
	public void user_inserts_list_of_new_working_class_heros(List<WorkingClassHero> workingClassHerosDataTable)
			throws Throwable {

		List<String> workingClassHerosRequestBody = new ArrayList<String>();
		String workingClassHeroRequestBody = null;
		for (WorkingClassHero workingClassHeroData : workingClassHerosDataTable) {
			workingClassHeroRequestBody = new Gson().toJson(workingClassHeroData);
			workingClassHerosRequestBody.add(workingClassHeroRequestBody);
		}

		log.info("Input Request: " + workingClassHerosRequestBody.toString());

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");

		response = request.body(workingClassHerosRequestBody.toString()).post(INSERTMULTIPLE_ENDPOINT_URL);
	}

	@When("^user tries to get tax releif details$")
	public void user_tries_to_get_tax_releif_details() throws Throwable {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get("taxRelief");
	}

	@Then("^user validates the tax releif response json$")
	public void user_validates_the_tax_releif_response_json() throws Throwable {
		jsonString = response.asString();
		log.info("Tax relief response json: " + jsonString);
		List<Map<String, String>> taxReliefData = JsonPath.from(jsonString).get();
		Assert.assertTrue(taxReliefData.size() > 0);
		Set<String> actualHeaderSet = taxReliefData.get(0).keySet();
		Set<String> expectedHeaderSet = new HashSet<>(Arrays.asList("natid", "name", "relief"));
		Assert.assertEquals(expectedHeaderSet, actualHeaderSet);
		log.info("taxReliefData: " + taxReliefData);
		log.info("taxReliefData Header: " + taxReliefData.get(0).keySet());
		log.info("natid: " + taxReliefData.get(0).get("natid"));
	}

}
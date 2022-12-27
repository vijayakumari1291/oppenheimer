package com.nhpc.qa.oppenheimer.stepdefinitions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.nhpc.qa.oppenheimer.model.WorkingClassHeroBean;
import com.nhpc.qa.oppenheimer.pageObjects.OppenheimerPage;
import com.nhpc.qa.oppenheimer.utils.Baseclass;
import com.nhpc.qa.oppenheimer.utils.CalcTaxRelief;
import com.nhpc.qa.oppenheimer.utils.ConfigReader;
import com.nhpc.qa.oppenheimer.utils.LoggerHelper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OppenheimerUiSteps extends Baseclass {

	OppenheimerPage oppenheimerPageObj = OppenheimerPage.get(driver);
	private static final Logger log = LoggerHelper.getLogger(OppenheimerUiSteps.class);

	String testDataPath = ConfigReader.getInstance().getTestDataPath();
	HashMap<String, String> expectedTaxReliefMap = new HashMap<String, String>();
	HashMap<String, String> actualTaxReliefMap = new HashMap<String, String>();

	@Given("user navigates to upload csv file")
	public void user_navigates_to_upload_csv_file() throws Throwable {
		try {
			seScrollDown();
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(oppenheimerPageObj.uploadCSVFile));
			assertTrue(oppenheimerPageObj.uploadCSVFile.isDisplayed());
			log.info("Upload csv file is available");
		} catch (Exception e) {
			log.error("Exception occured while validating Upload csv file button");
			assertFalse(true);
		}

	}

	@When("csv file has valid details with first row as valid header")
	public void csv_file_has_valid_details_with_first_row_as_valid_header(DataTable expCsvHeaderData) {
		try {
			String expCsvHeader = expCsvHeaderData.asList(String.class).toString();
			log.info("expCsvHeader: " + expCsvHeader.toString());
			String testDataFilePath = new File(testDataPath).getCanonicalPath();
			log.info("testDataPath: " + testDataFilePath);
			FileReader filereader = new FileReader(testDataFilePath);
			CSVReader csvReader = new CSVReaderBuilder(filereader).build();
			List<String[]> csvData = csvReader.readAll();
			String actCsvHeader = Arrays.toString(csvData.get(0));
			log.info("actCsvHeader: " + actCsvHeader);
			Assert.assertEquals("csv has invalid header", expCsvHeader, actCsvHeader);
			log.info("csv has valid header");
		} catch (Exception e) {
			log.error("Exception occured while validating csv file header and details");
			assertFalse(true);
		}
	}

	@When("^user clicks on browse button and selects the CSV file to upload data$")
	public void user_clicks_on_browse_button_and_selects_the_CSV_file_to_upload_data() throws Throwable {
		try {
			WebElement browse = oppenheimerPageObj.browseBtn;
			String testDataFilePath = new File(testDataPath).getCanonicalPath();
			log.info("testDataPath: " + testDataFilePath);
			browse.sendKeys(testDataFilePath);
			log.info("user clicks on browse button and selects the CSV file to upload data successfully");
		} catch (Exception e) {
			log.error("Exception occured while validating csv file file upload");
			assertFalse(true);
		}

	}

	@Then("^user click on Refresh Tax Relief Table button$")
	public void user_click_on_Refresh_Tax_Relief_Table_button() throws Throwable {
		try {
			seWaitForWebElement(20, ExpectedConditions.visibilityOf(oppenheimerPageObj.refreshTaxReliefTableBtn));
			Thread.sleep(500);
			seClick(oppenheimerPageObj.refreshTaxReliefTableBtn, "refreshTaxReliefTableBtn");
			log.info("User clicks on Refresh Tax Relief Table button successfully");
		} catch (Exception e) {
			log.error("Exception occured while validating Refresh Tax Relief Table button");
			assertFalse(true);
		}

	}

	@And("^uploaded records should appear$")
	public void uploaded_records_should_appear() throws Throwable {
		try {
			seWaitForWebElement(10, ExpectedConditions.visibilityOf(oppenheimerPageObj.refreshTaxReliefTableData));
			assertTrue(oppenheimerPageObj.refreshTaxReliefTableData.isDisplayed());
			log.info("Uploaded records are available");
			log.info("Uploaded records are available");
		} catch (Exception e) {
			log.error("Exception occured while validating Uploaded records");
			assertFalse(true);
		}

	}

	@And("^validate fields natid and tax relief amount populated in table$")
	public void validate_fields_natid_and_tax_relief_amount_populated_in_table() throws Throwable {
		try {
			seWaitForWebElement(10, ExpectedConditions.visibilityOf(oppenheimerPageObj.refreshTaxReliefTableData));

			List<String> headersData = seGetWebTableHeader(oppenheimerPageObj.refreshTaxReliefTableData);
			log.info("Headers: " + headersData.toString());

			Assert.assertEquals("NatId column is not matching", "NatId", headersData.get(0));
			Assert.assertEquals("Relief column is not matching", "Relief", headersData.get(1));
			HashMap<String, String> actualTaxReliefMapAll = seGetWebTableDataMap(
					oppenheimerPageObj.refreshTaxReliefTableData);

			List<WorkingClassHeroBean> beans = CalcTaxRelief.getBeansFromCSV();
			expectedTaxReliefMap = CalcTaxRelief.calcTaxRelief(beans);
			expectedTaxReliefMap = (HashMap<String, String>) expectedTaxReliefMap.entrySet().stream()
					.filter(entry -> expectedTaxReliefMap.keySet().contains(entry.getKey()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			log.info("expectedTaxReliefMap: " + expectedTaxReliefMap);

			actualTaxReliefMap = (HashMap<String, String>) actualTaxReliefMapAll.entrySet().stream()
					.filter(entry -> expectedTaxReliefMap.keySet().contains(entry.getKey()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			log.info("actualTaxReliefMap: " + actualTaxReliefMap);

			Assert.assertEquals("Tax Relief Data is not matching", expectedTaxReliefMap, actualTaxReliefMap);
		} catch (Exception e) {
			log.error("Exception occured while validating Tax relief data ");
			assertFalse(true);
		}
	}

	@Then("validates natid field must be masked from the 5th character onwards with dollar sign ‘$’")
	public void validates_natid_field_must_be_masked_from_the_5th_character_onwards_with_dollar_sign_$() {
		try {
			log.info("Expected Nation Ids: " + expectedTaxReliefMap.keySet());
			log.info("Actual Nation Ids: " + actualTaxReliefMap.keySet());

			Assert.assertEquals("natid is not masked as expected", expectedTaxReliefMap.keySet(),
					actualTaxReliefMap.keySet());
			log.info("natid is masked as expected");
		} catch (Exception e) {
			log.error("Exception occured while validating natid  masking");
			assertFalse(true);
		}
	}

	@Then("computation of the tax relief is using the formula as described")
	public void computation_of_the_tax_relief_is_using_the_formula_as_described() {
		try {
			log.info("Expected Tax Relief: " + expectedTaxReliefMap.values());
			log.info("Actual Tax Relief: " + actualTaxReliefMap.values());

			Assert.assertEquals("Tax Relief calculation is not as expected", expectedTaxReliefMap.values().toString(),
					actualTaxReliefMap.values().toString());
			log.info("Tax Relief calculation is as expected");
		} catch (Exception e) {
			log.error("Exception occured while validating Tax Relief calculation");
			assertFalse(true);
		}
	}

	@Then("calculated tax relief amount after rounding off is more than zero but less than fifty final tax relief amount should be {string}")
	public void calculated_tax_relief_amount_after_rounding_off_is_more_than_zero_but_less_than_fifty_final_tax_relief_amount_should_be(
			String fifty) {
		try {
			log.info("Actual Tax Relief amount for ABCD$$$$$$$$: " + actualTaxReliefMap.get("ABCD$$$$$$$$"));

			Assert.assertEquals("Final tax relief amount 50.00 validation failed", fifty,
					actualTaxReliefMap.get("ABCD$$$$$$$$"));

			log.info("Final tax relief amount 50.00 validation is successfull");
		} catch (Exception e) {
			log.error("Exception occured while validating Final tax relief amount 50.00");
			assertFalse(true);
		}
	}

	@Then("calculated tax relief amount before rounding off should have two decimal places")
	public void calculated_tax_relief_amount_before_rounding_off_should_have_two_decimal_places() {
		try {
			log.info("Actual Tax Relief amounts calculated: " + actualTaxReliefMap.values().toString());
			boolean isTaxReliefOnly2Decimal = validateTwoDecimalPlaces(actualTaxReliefMap.values());

			assertTrue("TaxRelief amount is not rounded off 2 decimals", isTaxReliefOnly2Decimal);

			log.info("Tax Relief amount rounding off validation is successfull");
		} catch (Exception e) {
			log.error("Exception occured while validating Tax Relief amount rounding off");
			assertFalse(true);
		}
	}

	@And("^the button on the screen must be red-colored$")
	public void the_button_on_the_screen_must_be_red_colored() throws Throwable {

		try {
			seScrollTillPageEnd();
			Thread.sleep(500);
			WebElement dispenseNowBtn = oppenheimerPageObj.dispenseNowBtn;
			String colour = dispenseNowBtn.getCssValue("color");
			String hexColour = Color.fromString(colour).asHex();
			log.info(colour);
			log.info(hexColour);
			Assert.assertEquals("The button on the screen is red-color", hexColour, "#ffffff");
			log.info("The button on the screen is red-color");
		} catch (InterruptedException e) {
			log.error("Exception occured while validating Dispense Now Btn colour");
			Assert.assertFalse(true);
		}
	}

	@And("^The text on the button must be exactly (.*)$")
	public void the_text_on_the_button_must_be_exactly_Dispense_Now(String expDispenseNow) throws Throwable {
		try {
			String actDispenseNowBtn = oppenheimerPageObj.dispenseNowBtn.getText();
			log.info("Actual Button name is: " + actDispenseNowBtn);
			log.info("Expected Button name is: " + expDispenseNow);
			Assert.assertEquals("The text on the button is not 'Dispense Now' ", actDispenseNowBtn, expDispenseNow);
			log.info("The text on the button is 'Dispense Now'");
		} catch (Exception e) {
			log.error("Exception occured while validating text on Dispense Now Btn");
			Assert.assertFalse(true);
		}

	}

	@And("^click on the button Dispense Now$")
	public void click_on_the_button_Dispense_Now() throws Throwable {
		try {
			seClick(oppenheimerPageObj.dispenseNowBtn, "Clicked on Dispense Now button successfully");
			Assert.assertTrue(true);
			log.info("Clicked on Dispense Now button successfully'");
		} catch (Exception e) {
			log.error("Exception occured while clicking Dispense Now button");
			Assert.assertFalse(true);
		}

	}

	@Then("^it should direct me to a page with a text that says (.*)$")
	public void it_should_direct_me_to_a_page_with_a_text_that_says_Cash_dispensed(String expCashDispensed)
			throws Throwable {

		try {
			seWaitForWebElement(10, ExpectedConditions.visibilityOf(oppenheimerPageObj.cashdispensed));
			String actCashDispensed = oppenheimerPageObj.cashdispensed.getText();
			log.info("Actual Text: " + actCashDispensed);
			log.info("Expected Text: " + expCashDispensed);
			Assert.assertEquals("The text on the screen is not 'Cash dispensed' ", actCashDispensed, expCashDispensed);
			Thread.sleep(500);
		} catch (InterruptedException e) {
			log.error("Exception occured while validating 'Cash dispensed' screen");
			Assert.assertFalse(true);
		}
	}

}

package com.nhpc.qa.oppenheimer.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVReader;

/**
 * @author vijay
 *
 */
/**
 * @author vijay
 *
 */
public class Baseclass {

	private static final Logger log = LoggerHelper.getLogger(Baseclass.class);

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static CSVReader reader;

	/**
	 * This method is used for setting up driver
	 * 
	 */
	public static void setupDriver() {
		String browser = System.getProperty("browser", "chrome");
		log.info("browser type passed from command line: " + browser);
		if (browser.contains("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.contains("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.contains("edge")) {
			driver = new EdgeDriver();
		} else {
			log.error(browser + " Invalid browser type");
			Assert.assertTrue(false);
		}
	}

	/**
	 * This method is used for wait for the element with expected condition
	 * 
	 * @param timeOutInSeconds
	 * @param expectedCondition
	 */
	public static void seWaitForWebElement(long timeOutInSeconds, ExpectedCondition<WebElement> expectedCondition) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
		wait.until(expectedCondition);

	}

	/**
	 * This method is used for clicking on WebElement
	 * 
	 * @param testObject
	 * @param buttonName
	 * @throws Exception
	 */
	public static void seClick(WebElement testObject, String buttonName) throws Exception {
		if (testObject.isDisplayed()) {
			testObject.click();
			log.info(buttonName + "clicked successfully");
		} else {
			log.error(buttonName + "Unable to click");
			Assert.assertTrue(false);
		}

	}

	/**
	 * This method is used to Scroll down the page
	 * 
	 */
	public static void seScrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)", "");
	}

	/**
	 * This method is used to Scroll down the Till Page End
	 * 
	 */
	public static void seScrollTillPageEnd() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * This method returns all values from the Web table
	 * 
	 * @param table
	 * @return
	 */
	public static HashMap<String, String> seGetWebTableDataMap(WebElement table) {
		HashMap<String, String> taxReliefTableData = new HashMap<String, String>();
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		System.out.println(rows_count);
		for (int row = 1; row < rows_count; row++) {
			List<WebElement> columns_row = rows_table.get(row).findElements(By.tagName("td"));
			taxReliefTableData.put(columns_row.get(0).getText(), columns_row.get(1).getText());

		}
		return taxReliefTableData;
	}

	/**
	 * This method returns header from the Web table
	 * 
	 * @param table
	 * @return
	 */
	public static List<String> seGetWebTableHeader(WebElement table) {
		List<WebElement> headers = table.findElements(By.tagName("th"));
		List<String> headerData = new ArrayList<>();
		String columnName = "";
		for (WebElement header : headers) {

			columnName = header.getText();
			headerData.add(columnName);
		}

		return headerData;
	}

	/**
	 * This method checks if decimal has only two places
	 * 
	 * @param taxReliefData
	 * @return
	 */
	public static boolean validateTwoDecimalPlaces(Collection<String> taxReliefData) {
		boolean isTaxReliefOnly2Decimal = true;
		for (String taxRelief : taxReliefData) {
			if (taxRelief.split("\\.").length != 2) {
				isTaxReliefOnly2Decimal = false;
				break;
			}

		}
		return isTaxReliefOnly2Decimal;

	}

	/**
	 * This method is used to compare two Maps
	 * 
	 * @param mapA
	 * @param mapB
	 * @return
	 */
	public static boolean compareMaps(Map<String, String> mapA, Map<String, String> mapB) {
		return mapA.entrySet().equals(mapB.entrySet());
	}

}

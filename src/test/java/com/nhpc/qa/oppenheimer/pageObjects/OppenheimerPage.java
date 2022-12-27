package com.nhpc.qa.oppenheimer.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OppenheimerPage {

	private static OppenheimerPage thisIsTestObj;

	public synchronized static OppenheimerPage get(WebDriver driver) {
		thisIsTestObj = PageFactory.initElements(driver, OppenheimerPage.class);
		return thisIsTestObj;
	}

	@FindBy(xpath = "//span[text()='Upload CSV file']")
	@CacheLookup
	public WebElement uploadCSVFile;

	@FindBy(xpath = "//input[@class='custom-file-input']")
	@CacheLookup
	public WebElement browseBtn;

	@FindBy(xpath = "//button[text()='Refresh Tax Relief Table']")
	@CacheLookup
	public WebElement refreshTaxReliefTableBtn;

	@FindBy(xpath = "//table[@class='table table-hover table-dark']")
	@CacheLookup
	public WebElement refreshTaxReliefTableData;

	@FindBy(xpath = "//a[text()='Dispense Now']")
	@CacheLookup
	public WebElement dispenseNowBtn;

	@FindBy(xpath = "//div[text()='Cash dispensed']")
	@CacheLookup
	public WebElement cashdispensed;

}

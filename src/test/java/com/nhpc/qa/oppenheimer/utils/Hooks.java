package com.nhpc.qa.oppenheimer.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.nhpc.qa.oppenheimer.stepdefinitions.OppenheimerUiSteps;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks extends Baseclass {

	public String BASE_URL = ConfigReader.getInstance().getWebApplicationURL();
	private static final Logger log = LoggerHelper.getLogger(OppenheimerUiSteps.class);

	@Before("@UIFeature")
	public void beforeScenario() {
		setupDriver();
		driver.get(BASE_URL);
		driver.manage().window().maximize();
		log.info("Oppenheimer application launched successfully...");

	}

	@AfterStep("@UIFeature")
	public void afterStep(Scenario scenario) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		byte[] src = ts.getScreenshotAs(OutputType.BYTES);
		scenario.attach(src, "image/png", scenario.getName());
	}

	@After("@UIFeature")
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			byte[] src = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(src, "image/png", scenario.getName());
		}
		driver.quit();
		log.info("Test case is completed and exiting from the browser");

	}

}

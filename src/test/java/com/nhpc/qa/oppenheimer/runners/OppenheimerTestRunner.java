package com.nhpc.qa.oppenheimer.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
features = { "src/test/resources/features/nhpc/api/oppenheimer_api.feature",
		"src/test/resources/features/nhpc/ui/oppenheimer_ui.feature" }, glue = { "com.nhpc.qa.oppenheimer.stepdefinitions",
				"com.nhpc.qa.oppenheimer.utils" },
		 //tags = "@UIFeature",
		publish = true)
public class OppenheimerTestRunner {
}

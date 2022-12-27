package com.nhpc.qa.oppenheimer.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.nhpc.qa.oppenheimer.stepdefinitions.OppenheimerUiSteps;

public class ConfigReader {
	private static final Logger log = LoggerHelper.getLogger(OppenheimerUiSteps.class);
	private static Properties properties;
	private static ConfigReader configReader;
	private static String env = System.getProperty("env", "dev");

	
	public ConfigReader() {
		log.info("Test Case started for env: "+env);
		BufferedReader reader;
		String propertyFilePath = "configs//"+env+".properties";
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	public static ConfigReader getInstance() {
		if (configReader == null) {
			configReader = new ConfigReader();
		}
		return configReader;
	}

	public String getWebApplicationURL() {
		String baseUrl = properties.getProperty("webApplicationURL");
		if (baseUrl != null)
			return baseUrl;
		else
			throw new RuntimeException("webApplicationURL not specified in the config.properties file.");
	}
	public String getApiURL() {
		String baseUrl = properties.getProperty("apiURL");
		if (baseUrl != null)
			return baseUrl;
		else
			throw new RuntimeException("apiURL not specified in the config.properties file.");
	}

	public String getTestDataPath() {

		String testDataPath = properties.getProperty("testDataPath");
		if (testDataPath != null)
			return testDataPath;

		else
			throw new RuntimeException("testDataPath not specified in the config.properties file.");
	}
}
package com.nhpc.qa.oppenheimer.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nhpc.qa.oppenheimer.model.WorkingClassHeroBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CalcTaxRelief {
	static String testDataPath = ConfigReader.getInstance().getTestDataPath();
	private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

	static Map<Integer, Double> ageVariablMap = new HashMap<>();
	static Map<String, Integer> genderBonusMap = new HashMap<>();

	static {
		decimalFormat.setRoundingMode(RoundingMode.UP);

		ageVariablMap.put(18, 1.0);
		ageVariablMap.put(35, 0.8);
		ageVariablMap.put(50, 0.5);
		ageVariablMap.put(75, 0.367);
		ageVariablMap.put(76, 0.05);

		genderBonusMap.put("M", 0);
		genderBonusMap.put("F", 500);
	}

	public static void main(String[] args) throws Exception, FileNotFoundException {

		Double taxRelief = 49.0;
		if (Double.compare(taxRelief, 0.0) > 0 && Double.compare(taxRelief, 50.00) < 0) {
			taxRelief = 50.00;
		}
		System.out.println(taxRelief);

		List<WorkingClassHeroBean> beans = getBeansFromCSV();
		Map<String, String> taxReliefMap = calcTaxRelief(beans);

		System.out.println(taxReliefMap);
	}

	public static HashMap<String, String> calcTaxRelief(List<WorkingClassHeroBean> beans) {
		HashMap<String, String> taxReliefMap = new HashMap<String, String>();

		Double taxRelief = 0.0;
		int age = 0;
		double variable = 0.0;
		int bonus = 0;

		for (WorkingClassHeroBean bean : beans) {
			age = getAge(bean.getBirthday());
			variable = getVariablePerAge(age);
			bonus = genderBonusMap.get(bean.getGender());
			taxRelief = ((bean.getSalary() - bean.getTax()) * variable) + bonus;
			if (Double.compare(taxRelief, 0.0) > 0 && Double.compare(taxRelief, 50.00) < 0) {
				taxRelief = 50.00;
			}
			taxReliefMap.put(getMaskedNatId(bean.getNatid(), 4), decimalFormat.format(taxRelief));
		}
		return taxReliefMap;
	}

	public static List<WorkingClassHeroBean> getBeansFromCSV() throws Exception {
		List<WorkingClassHeroBean> beans = null;
		try {
			beans = new CsvToBeanBuilder<WorkingClassHeroBean>(new FileReader(testDataPath))
					.withType(WorkingClassHeroBean.class).build().parse();
		} catch (IllegalStateException | FileNotFoundException e) {
			throw new Exception(testDataPath + " is not available");
		}

		return beans;
	}

	public static int getAge(String dob) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		// convert String to LocalDate
		LocalDate dobDate = LocalDate.parse(dob, formatter);
		LocalDate today = LocalDate.now();
		Period p = Period.between(dobDate, today);
		return p.getYears();
	}

	public static double getVariablePerAge(int age) {
		double variable = 0.0;
		if (age <= 18)
			variable = ageVariablMap.get(18);
		else if (age <= 35)
			variable = ageVariablMap.get(35);
		else if (age <= 50)
			variable = ageVariablMap.get(50);
		else if (age <= 75)
			variable = ageVariablMap.get(75);
		else if (age >= 76)
			variable = ageVariablMap.get(76);

		return variable;

	}

	public static String getMaskedNatId(String natId, int indexToMask) {
		if ((natId != null) && (natId.length() > 0) && (natId.length() > indexToMask)) {
			char[] tokenChars = natId.toCharArray();
			natId.getChars(0, indexToMask, tokenChars, 0);
			Arrays.fill(tokenChars, indexToMask, tokenChars.length, '$');
			return (new String(tokenChars));
		} else if (natId.length() <= indexToMask) {
			return natId;
		}
		return "";
	}
}

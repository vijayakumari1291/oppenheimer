package com.nhpc.qa.oppenheimer.model;

import com.opencsv.bean.CsvBindByName;

public class WorkingClassHeroBean {
	private WorkingClassHeroBean workingClassHeroBean;

	@CsvBindByName(column = "natid")
	private String natid;
	@CsvBindByName(column = "name")
	private String name;
	@CsvBindByName(column = "gender")
	private String gender;
	@CsvBindByName(column = "salary")
	private Double salary;
	@CsvBindByName(column = "birthday")
	private String birthday;
	@CsvBindByName(column = "tax")
	private Double tax;

	public WorkingClassHeroBean getWorkingClassHero() {
		return workingClassHeroBean;
	}

	public void setPerson(WorkingClassHeroBean workingClassHeroBean) {
		this.workingClassHeroBean = workingClassHeroBean;
	}

	public String getNatid() {
		return natid;
	}

	public void setNatid(String natid) {
		this.natid = natid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

}
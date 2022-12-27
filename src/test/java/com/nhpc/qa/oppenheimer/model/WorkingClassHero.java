package com.nhpc.qa.oppenheimer.model;

public class WorkingClassHero {
	//private WorkingClassHero workingClassHero;


	private String natid;
	private String name;
	private String gender;
	private String salary;
	private String birthday;
	private String tax;
	
	public WorkingClassHero(String natid, String name, String gender, String salary, String birthday, String tax) {
		super();
		this.natid = natid;
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.birthday = birthday;
		this.tax = tax;
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

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}
	
}
### Oppenheimer Automation Framework(selenium-cucumber-java)

This Oppenheimer test project is a test automation framework, which provides structured and standard way of 
creating automated test scripts for GUI and API level tests across projects 

### Installation (pre-requisites)

1. JDK 1.8+ (make sure Java class path is set)
2. Maven (make sure .m2 class path is set)
3. Eclipse
4. Eclipse Plugins for
    - Maven
    - Cucumber

### Framework set up

Fork / Clone repository from github or download zip and set
it up in your local workspace.

Convert project to maven and cucumber under configure option

### Compile Build and Run Tests

Command Line

cd to root to oppenheimer project directory

mvn clean install -DskipTests

mvn clean install  

OR

mvn clean install -Dbrowser=chrome -Denv=dev

### Reporters

Once you ran your tests you can generate the various types of reports. This framework uses
several different types of test reporters to communicate pass/failure.

##### Cucumber Report:

Standard HTML Report  
A report will be generated at 
https://reports.cucumber.io/report-collections/d84913fc-1d4c-48ae-a5b5-a6a89228640c

##### Extent Report:

A report will be generated at 
/ExtentReports/ folder

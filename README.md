# OrangeHRM Automation Framework

Selenium Java Automation Framework built for OrangeHRM.

## Tech Stack
- Selenium 4.18.1
- TestNG 7.9.0
- Maven
- WebDriverManager 5.7.0
- ExtentReports 5.1.1
- Apache POI 5.2.3

## Design Pattern
- Page Object Model (POM)

## Framework Features
- Auto browser setup and teardown
- Screenshot capture on test failure
- Extent HTML Reports auto generated
- Externalized configuration via config.properties
- Explicit waits using WebDriverWait

## Test Cases
### Login Module (4 Tests)
- Valid Login
- Invalid Login
- Empty Login
- Logout

### Dashboard Module (4 Tests)
- Verify Dashboard Title
- Verify Welcome Message
- Verify Dashboard URL
- Verify Admin Menu Navigation

## How to Run
1. Clone the repo
2. Open in Eclipse as Maven project
3. Right-click testng.xml → Run As → TestNG Suite
4. View report at reports/ExtentReport.html

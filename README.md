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
- Data Driven Testing using Apache POI (Excel)
- Parallel Execution using TestNG
- ThreadLocal WebDriver for thread safety

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

### Data Driven Login Tests (4 Tests)
- Valid Login from Excel
- Invalid Login from Excel
- Wrong Password from Excel
- Empty Login from Excel

### PIM Module Tests (4 Tests)
- Verify Employee List Page
- Verify Add Employee Page Loads
- Add New Employee Successfully
- Verify Cancel Button

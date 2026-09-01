# Hybrid QA Spring Boot Automation Framework

## Project Overview

This project is a Java-based hybrid QA automation framework built using **Spring Boot, Selenium WebDriver, TestNG, and REST Assured**.

The framework is designed to demonstrate UI automation, API testing, cross-browser testing, environment-specific configuration, and Maven-based test execution.

The project uses the OrangeHRM demo application as the current UI automation target.

---

## Key Features

* UI automation using Selenium WebDriver
* API automation using REST Assured
* Test execution using TestNG
* Cross-browser testing
* Spring Boot-based configuration
* Environment-specific configuration using Spring Profiles
* Maven-based build and test execution
* Centralized WebDriver configuration
* Positive and negative test scenarios
* Git/GitHub source-code management
GitHub Actions runs the CI TestNG suite on pushes to the main branch.
---

## Technology Stack

| Technology         | Purpose                                 |
| ------------------ | --------------------------------------- |
| Java 21            | Programming language                    |
| Spring Boot        | Application and configuration framework |
| Selenium WebDriver | UI automation                           |
| TestNG             | Test execution and assertions           |
| REST Assured       | API automation                          |
| Maven              | Build and dependency management         |
| Git                | Version control                         |
| GitHub             | Source-code repository                  |
| Eclipse            | Development IDE                         |

---

## Project Structure

```text
hybrid-qa-spring-boot-automation-framework
│
├── src
│   ├── main
│   │   └── java
│   │
│   └── test
│       └── java
│
├── pom.xml
├── testng.xml
├── .gitignore
└── README.md
```

---

## Test Coverage

### UI Testing

The framework uses Selenium WebDriver for browser-based UI automation.

Current UI automation includes:

* Login testing
* Positive and negative login scenarios
* Cross-browser execution
* Chrome
* Firefox
* Microsoft Edge

### API Testing

REST Assured is used for API automation and validation.

The framework supports:

* API requests
* Response validation
* Status-code validation
* API test execution through TestNG

---

## Cross-Browser Testing

The framework supports execution across multiple browsers.

Current supported browsers:

* Chrome
* Firefox
* Microsoft Edge

Example TestNG execution:

```text
Cross Browser Suite

Total tests run: 6
Passes: 6
Failures: 0
Skips: 0
```

---

## Environment Configuration

The framework supports environment-specific configuration using Spring Boot profiles.

Current environments include:

* QA
* DEV
* PROD

Example QA configuration:

```properties
app.environment=QA
app.base-url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
```

Environment profiles can be selected during Maven execution.

Example:

```bash
mvn test -Dspring.profiles.active=qa
```

---

## How to Run

### Prerequisites

Make sure the following are installed:

* Java 21
* Maven
* Git
* Eclipse or another Java IDE
* Supported web browser

### Clone the Repository

```bash
git clone https://github.com/alammohamad/hybrid-qa-spring-boot-automation-framework.git
```

Navigate to the project:

```bash
cd hybrid-qa-spring-boot-automation-framework
```

### Run Tests

Run the Maven test suite:

```bash
mvn test
```

Run using the QA Spring profile:

```bash
mvn test -Dspring.profiles.active=qa
```

---

## Test Execution

Tests can also be executed using the TestNG suite:

```text
testng.xml
```

The TestNG suite provides configuration for browser-based test execution and API tests.

---

## Reporting and Logging

The framework is being developed to provide clear test execution information and professional QA reporting.

Future enhancements will include additional reporting, logging, screenshots, and CI/CD integration.

---

## Future Enhancements

Planned improvements include:

* Allure test reporting
* Enhanced logging
* Automatic screenshots on test failure
* Data-driven testing
* Improved API test coverage
* Database testing
* GitHub Actions CI/CD
* Docker-based test execution
* Additional environment configurations
* Test execution dashboards

---

## Author

**Mohamad Alam**

QA Automation Engineer | Software Test Engineer

---

## Disclaimer

This project is intended as a demonstration and portfolio project for QA automation engineering practices.

The demo application is used as the current UI automation target.

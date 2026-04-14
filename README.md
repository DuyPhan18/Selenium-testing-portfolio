# Selenium Testing Portfolio

Automated Web testing framework built with Java and Selenium WebDriver.

![CI](https://github.com/DuyPhan18/Selenium-testing-portfolio/actions/workflows/ci.yml/badge.svg)

## Tech Stack
- **Java 22**
- **TestNG 7.7.0** - Test framework
- **Selenium WebDriver 4.21.0** - Browser automation
- **Apache POI** - Excel data handling
- **Extent Report** - Test reporting
- **Maven** - Build tool
- **GitHub Actions** - CI/CD
## Prerequisites
- Java 22+
- Maven 3.8+
- Chrome/Firefox/Edge browser installed
## Features
- ✅ Multi-browser support (Chrome, Firefox, Edge)
- ✅ Page Object Model (POM) design pattern
- ✅ Data-driven testing with Excel (Apache POI)
- ✅ Screenshot capture on test failure
- ✅ Auto-retry on flaky tests
- ✅ Automated CI/CD with GitHub Actions
- ✅ Professional Extent Reports with logs

## Project Structure
```
src/
├── main/java/
│   ├── core/
│   │   ├── BasePage.java
│   │   ├── BaseTest.java
│   │   ├── Constants.java
│   │   ├── DriverManager.java
│   │   └── ExtentManager.java
│   ├── pages/
│   │   ├── LoginPage.java
│   │   ├── ProductPage.java
│   │   ├── CartPage.java
│   │   └── CheckoutPage.java
│   ├── helpers/
│   │   └── CaptureHelpers.java
│   └── utils/
│       ├── ConfigReader.java
│       ├── ExcelUtils.java
│       ├── AnnotationTransformer.java
│       ├── RetryAnalyzer.java
│       └── TestListener.java
└── test/java/
    └── tests/
        ├── LoginTest.java
        ├── ProductTest.java
        ├── CartTest.java
        └── CheckoutTest.java
```

## Test Coverage
| Test Class | Test Cases |
|---|---|
| LoginTest | TC01-TC08: Login with valid/invalid credentials, empty fields, locked/problem user |
| ProductTest | View product list, Sort products, View product detail |
| CartTest | Add to cart, Remove from cart, Verify cart badge |
| CheckoutTest | Checkout flow, Complete order, Verify order confirmation |

## How to Run
```bash
# Run all tests
mvn clean test

# Run specific browser (default: chrome)
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge

# View ExtentReport (after running tests)
# Open file: reports/ExtentReport.html
```

## Application Under Test
- **URL:** https://www.saucedemo.com
- **Browser Support:** Chrome, Firefox, Edge
- **Test Data:** Excel-driven (`data/login_data.xlsx`)

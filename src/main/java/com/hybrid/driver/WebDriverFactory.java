package com.hybrid.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

    public static WebDriver createDriver(String browser) {

        System.out.println(
                "WebDriverFactory - Browser: " + browser
        );

        switch (browser.toLowerCase()) {

            case "chrome":

                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();

                if ("true".equalsIgnoreCase(
                        System.getenv("GITHUB_ACTIONS"))) {

                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                }

                return new ChromeDriver(chromeOptions);


            case "firefox":

                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if ("true".equalsIgnoreCase(
                        System.getenv("GITHUB_ACTIONS"))) {

                    firefoxOptions.addArguments("-headless");
                }

                return new FirefoxDriver(firefoxOptions);


//            case "edge":
//
//                WebDriverManager.edgedriver().setup();
//
//                EdgeOptions edgeOptions = new EdgeOptions();
//
//                if ("true".equalsIgnoreCase(
//                        System.getenv("GITHUB_ACTIONS"))) {
//
//                    edgeOptions.addArguments("--headless");
//                    edgeOptions.addArguments("--no-sandbox");
//                    edgeOptions.addArguments("--disable-dev-shm-usage");
//                }
//
//                return new EdgeDriver(edgeOptions);
            case "edge":

                EdgeOptions edgeOptions = new EdgeOptions();

                if ("true".equalsIgnoreCase(
                        System.getenv("GITHUB_ACTIONS"))) {

                    edgeOptions.addArguments("--headless");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                }

                System.setProperty(
                        "webdriver.edge.driver",
                        "C:\\WebDrivers\\msedgedriver.exe"
                );

                return new EdgeDriver(edgeOptions);

            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }
    }
}

//BaseTest
//│
//▼
//WebDriverFactory
//│
//├── Chrome
//├── Firefox
//└── Edge

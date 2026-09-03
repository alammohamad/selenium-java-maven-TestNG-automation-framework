package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // OrangeHRM application URL
    private String baseUrl =
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    public LoginPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    // Locators
    private By usernameField =
            By.xpath("//input[@name='username']");

    private By passwordField =
            By.xpath("//input[@name='password']");

    private By loginButton =
            By.xpath("//button[@type='submit']");

    // Successful login indicator
    private By dashboard =
            By.xpath("//span[text()='Dashboard']");

    // Invalid login error message
    private By invalidLoginMessage =
            By.xpath("//p[contains(@class,'oxd-alert-content-text')]");

    // Open OrangeHRM
    public void openWebsite() {
        driver.get(baseUrl);
    }

    // Verify page title
    public void verifyTitle() {
        Assert.assertTrue(
                driver.getTitle().contains("OrangeHRM"),
                "Title does not contain OrangeHRM"
        );
    }

    // Enter username
    public void enterUsername(String username) {

        WebElement usernameElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        usernameField
                )
        );

        usernameElement.sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {

        WebElement passwordElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        passwordField
                )
        );

        passwordElement.sendKeys(password);
    }

    // Click Login
    public void clickLogin() {

        WebElement loginElement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        loginButton
                )
        );

        loginElement.click();
    }

    // Perform login
    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Verify successful login
    public boolean isLoginSuccessful() {

        try {

            wait.until(
                    ExpectedConditions.urlContains("/dashboard/index")
            );

            System.out.println(
                    "Login successful - Dashboard URL detected: "
                    + driver.getCurrentUrl()
            );

            return true;

        } catch (Exception e) {

            System.out.println(
                    "===== LOGIN SUCCESS CHECK FAILED ====="
            );

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Page Title: "
                    + driver.getTitle()
            );

            System.out.println(
                    "Exception: "
                    + e.getClass().getSimpleName()
            );

            System.out.println(
                    "Exception Message: "
                    + e.getMessage()
            );

            System.out.println(
                    "======================================"
            );

            return false;
        }
    }

    // Verify failed login
    public boolean isLoginFailed() {

        try {

            WebElement errorMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            invalidLoginMessage
                    )
            );

            return errorMessage.isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }
}

// Test execution: Right-click the new project → Run As → Maven test
//bat/cmd: mvn clean test    all browser test and parallel test
// only one browser in QA env: mvn clean test -Denv=qa -Dbrowser=chrome
// use testng-chrome.xml temporarily test only chrome: mvn clean test -Denv=qa -Dsurefire.suiteXmlFiles=testng-chrome.xml

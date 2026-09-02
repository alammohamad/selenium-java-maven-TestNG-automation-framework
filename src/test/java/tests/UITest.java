package tests;

import base.BaseTest;
import pages.LoginPage;
import utils.CSVUtils;

import org.testng.Assert;
import org.testng.annotations.*;

public class UITest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void startBrowser(String browser) {

        System.out.println(
                "Browser received from TestNG: " + browser
        );

        setUp(browser);
    }


    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {

        tearDown();
    }


    @DataProvider(name = "loginData")
    public Object[][] loginData() throws Exception {

        return CSVUtils.getCSVData(
                "src/test/resources/data.csv"
        );
    }


    @Test(
        dataProvider = "loginData",
        description = "Login with test data"
    )
    public void testLogin(
            String userId,
            String password,
            String expectedResult) {

        System.out.println(
                "===== RUNNING LOGIN TEST | Browser: "
                + getDriver().getClass().getSimpleName()
                + " ====="
        );

        LoginPage loginPage =
                new LoginPage(getDriver());

        loginPage.openWebsite();

        loginPage.verifyTitle();

        loginPage.login(userId, password);


        if (expectedResult.equalsIgnoreCase("success")) {

            Assert.assertTrue(
                    loginPage.isLoginSuccessful(),
                    "Expected login to succeed, but login was not successful."
            );

        } else if (expectedResult.equalsIgnoreCase("failure")) {

            Assert.assertTrue(
                    loginPage.isLoginFailed(),
                    "Expected login to fail, but login appears to have succeeded."
            );

        } else {

            Assert.fail(
                    "Invalid expectedResult value: "
                    + expectedResult
            );
        }
    }
}

//TestNG
//↓
//UITest
//↓
//BaseTest
//↓
//WebDriverFactory
//↓
//Selenium WebDriver
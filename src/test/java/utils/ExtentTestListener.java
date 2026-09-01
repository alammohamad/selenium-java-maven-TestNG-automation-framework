package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.BaseTest;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent =
            ExtentManager.getInstance();

    private static ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();


    @Override
    public void onStart(ITestContext context) {

        System.out.println(
                "Extent Report Started: "
                        + context.getName()
        );
    }


    @Override
    public void onTestStart(ITestResult result) {

        String testName = getTestName(result);

        ExtentTest test =
                extent.createTest(testName);

        extentTest.set(test);

        test.info("Test started");

        String browser = getBrowser(result);

        Object[] testNgParameters =
                result.getTestContext()
                        .getCurrentXmlTest() != null
                ? new Object[]{
                        result.getTestContext()
                                .getCurrentXmlTest()
                                .getParameter("browser")
                }
                : null;

        if (testNgParameters != null
                && testNgParameters[0] != null) {

            browser =
                    testNgParameters[0].toString();
        }

        Object[] parameters =
                result.getParameters();

        String userId =
                parameters != null
                        && parameters.length > 0
                        && parameters[0] != null
                ? parameters[0].toString()
                : "";

        String environment =
                System.getProperty(
                        "env",
                        "QA"
                ).toUpperCase();

        ExtentManager.recordStart(
                testName,
                browser,
                environment,
                userId
        );

        test.info(
                "Environment: "
                        + environment
        );

        test.info(
                "Execution Type: "
                        + browser.toUpperCase()
        );

        if (parameters != null) {

            for (Object parameter : parameters) {

                if (parameter != null) {

                    String value =
                            parameter.toString();

                    /*
                     * Do not report passwords.
                     */
                    if (value.equalsIgnoreCase(
                            "admin123")) {

                        value = "********";
                    }

                    test.info(
                            "Test Parameter: "
                                    + value
                    );
                }
            }
        }
    }


    @Override
    public void onTestSuccess(
            ITestResult result) {

        extentTest.get().pass(
                "Test Passed"
        );

        ExtentManager.recordEnd(
                getTestName(result),
                getBrowser(result),
                "PASS"
        );
    }


    @Override
    public void onTestFailure(
            ITestResult result) {

        ExtentTest test =
                extentTest.get();

        test.fail(
                "Test Failed: "
                        + result.getThrowable()
        );

        ExtentManager.recordEnd(
                getTestName(result),
                getBrowser(result),
                "FAIL"
        );

        try {

            Object instance =
                    result.getInstance();

            if (instance instanceof BaseTest) {

                WebDriver driver =
                        ((BaseTest) instance)
                                .getDriver();

                if (driver != null) {

                    String screenshot =
                            ((TakesScreenshot) driver)
                                    .getScreenshotAs(
                                            OutputType.BASE64
                                    );

                    test.fail(
                            "Failure Screenshot",
                            MediaEntityBuilder
                                    .createScreenCaptureFromBase64String(
                                            screenshot
                                    )
                                    .build()
                    );
                }
            }

        } catch (Exception e) {

            test.warning(
                    "Unable to capture screenshot: "
                            + e.getMessage()
            );
        }
    }


    @Override
    public void onTestSkipped(
            ITestResult result) {

        extentTest.get().skip(
                "Test Skipped"
        );

        ExtentManager.recordEnd(
                getTestName(result),
                getBrowser(result),
                "SKIPPED"
        );
    }


    private String getTestName(
            ITestResult result) {

        Object[] parameters =
                result.getParameters();

        if (parameters != null
                && parameters.length >= 3
                && parameters[2] != null) {

            String expectedResult =
                    parameters[2].toString();

            if (expectedResult.equalsIgnoreCase(
                    "success")) {

                return "Login with valid credentials";
            }

            if (expectedResult.equalsIgnoreCase(
                    "failure")) {

                return "Login with invalid credentials";
            }
        }

        return result.getMethod()
                .getMethodName();
    }


    private String getBrowser(
            ITestResult result) {

        if (result.getTestContext()
                .getCurrentXmlTest() != null) {

            String browser =
                    result.getTestContext()
                            .getCurrentXmlTest()
                            .getParameter(
                                    "browser"
                            );

            if (browser != null
                    && !browser.trim().isEmpty()) {

                return browser;
            }
        }

        return "API";
    }


    @Override
    public void onFinish(
            ITestContext context) {

        ExtentManager.flush();

        System.out.println(
                "Extent Report Generated"
        );
    }
}

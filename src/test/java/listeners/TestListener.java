package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utils.DriverFactory;
import utils.ExtentManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite started!");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite is ending!");
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

//    @Override
//    public void onTestSuccess(ITestResult result) {
//        test.get().log(Status.PASS, "Test Passed");
//        try {
//            String screenshotPath = ScreenshotUtil.takeScreenshot(DriverFactory.getDriver(), result.getMethod().getMethodName());
//            test.get().addScreenCaptureFromPath(screenshotPath);
//        } catch (Exception e) {
//            test.get().log(Status.WARNING, "Failed to attach success screenshot: " + e.getMessage());
//        }
//    }
    
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        test.get().log(Status.PASS, "Test Passed");
//        WebDriver driver = DriverFactory.getDriver();
//
//        if (driver == null) {
//            test.get().log(Status.WARNING, "Skipped attaching screenshot: WebDriver is null.");
//            return;
//        }
//
//        try {
//            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getMethod().getMethodName());
//            if (screenshotPath != null) {
//                test.get().addScreenCaptureFromPath(screenshotPath);
//            }
//        } catch (Exception e) {
//            test.get().log(Status.WARNING, "Failed to attach success screenshot: " + e.getMessage());
//        }
//    }

    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getMethod().getMethodName());
            test.get().addScreenCaptureFromPath(screenshotPath);
        } else {
            test.get().log(Status.WARNING, "Driver was null, could not capture screenshot.");
        }

        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        try {
            String screenshotPath = ScreenshotUtil.takeScreenshot(DriverFactory.getDriver(), result.getMethod().getMethodName());
            test.get().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            test.get().log(Status.WARNING, "Failed to attach failure screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optional
    }
}

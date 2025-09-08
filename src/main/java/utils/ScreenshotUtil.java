package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

	public static String takeScreenshot(WebDriver driver, String testName) {
	    if (driver == null) {
	        System.out.println("Screenshot skipped: WebDriver is null.");
	        return null;
	    }

	    TakesScreenshot ts = (TakesScreenshot) driver;
	    File source = ts.getScreenshotAs(OutputType.FILE);

	    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    String dir = System.getProperty("user.dir") + "/reports/screenshots/" + date + "/";
	    String fileName = testName + "_" + System.currentTimeMillis() + ".png";

	    try {
	        Files.createDirectories(Paths.get(dir));
	        File destination = new File(dir + fileName);
	        Files.copy(source.toPath(), destination.toPath());
	        System.out.println("Screenshot saved: " + destination.getAbsolutePath());
	        return destination.getAbsolutePath();
	    } catch (IOException e) {
	        System.out.println("Failed to capture screenshot: " + e.getMessage());
	        return null;
	    }
	}

}

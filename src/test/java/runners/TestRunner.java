package runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features", 
    glue = "stepDefinitions",                
//    plugin = {"pretty", "html:target/cucumber-report.html"},
    		plugin = {
    		        "pretty", 
    		        "html:target/cucumber-report.html",     // ✅ HTML report
    		        "json:target/cucumber-report.json",     // ✅ JSON report
    		        "junit:target/cucumber-report.xml"      // ✅ XML report
    		    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}

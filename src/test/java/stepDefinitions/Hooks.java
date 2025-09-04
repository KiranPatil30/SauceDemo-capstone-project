package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverFactory;

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initDriver(); // ✅ sets up driver before each scenario
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver(); // ✅ quits driver after each scenario
    }
}

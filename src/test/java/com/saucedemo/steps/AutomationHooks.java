package com.saucedemo.steps;

import com.saucedemo.util.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.plugin.event.Result;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AutomationHooks {
    private static final Logger log = LoggerFactory.getLogger(AutomationHooks.class);

    @Before
    public void launchBrowser(Scenario scenario) {
        log.info("*****************************************************************************************\n");
        log.info("\t--{ Scenario: " + scenario.getName().toUpperCase() + " - STARTED }--\n");
        log.info("*****************************************************************************************");
        DriverManager.getInstance().launchBrowser(scenario);
    }

    @After
    public void closeBrowser(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                TakesScreenshot ts = (TakesScreenshot) DriverManager.getInstance().getDriver();
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "screenshot");
                log.info("Completed taking screenshot of failed scenario");
                logError(scenario);
            }
        }finally {
            DriverManager.getInstance().closeDriver();
            log.info("*****************************************************************************************\n");
            log.info("\t--{ Scenario: "+scenario.getName().toUpperCase()+" - "+scenario.getStatus().toString().toUpperCase()+" }--\n");
            log.info("*****************************************************************************************");
        }

    }

    private static void logError(Scenario scenario) {
        Field field = FieldUtils.getField(scenario.getClass(), "stepResults", true);
        field.setAccessible(true);
        try {
            for (Result result : (ArrayList<Result>) field.get(scenario)) {
                if (result.getError() != null)
                    log.error("Scenario: {} => FAILED", scenario.getName(), result.getError());
            }
        } catch (Exception e) {
            log.error("Error while logging error", e);
        }
    }

    /*@AfterAll
    public void quitBrowser(){
        DriverManager.getInstance().closeDriver();
        DriverManager.getInstance().closeDriver();
    }*/

}

package com.saucedemo.util;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class DriverManager {
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static DriverManager driverManager;
    private static final ThreadLocal<WebDriver> tDriver = new ThreadLocal<>();

    private DriverManager() {
    }

    public static DriverManager getInstance() {
        if (driverManager == null) {
            synchronized (DriverManager.class) {
                if (driverManager == null) {
                    driverManager = new DriverManager();
                }
            }
        }
        return driverManager;
    }

    public synchronized WebDriver getDriver() {
        WebDriver driver = tDriver.get();
        if (driver == null) {
            throw new IllegalStateException("Driver should have not been null!!");
        }
        return driver;
    }

    public synchronized WebDriver launchBrowser(Scenario scenario) {
        DriverType driverType = getBrowser();
        WebDriver driver = null;
        log.info("Launching " + driverType + " browser.");
        switch (driverType) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().arch32().setup();
                driver = new FirefoxDriver();
                break;
            case CHROME:
                WebDriverManager.chromedriver().arch32().setup();
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory",
                        System.getProperty("user.dir") + GetConfig.getConfigProperty("downloadFilePath"));
                chromePrefs.put("download.prompt_for_download", false);
                chromePrefs.put("plugins.plugins_disabled", "Chrome PDF Viewer");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.addArguments("disable-infobars");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--no-sandbox");
                options.addArguments("--headless");
                options.setExperimentalOption("prefs", chromePrefs);
                driver = new ChromeDriver(options);
                break;

        }

        tDriver.set(driver);
        driver.manage().timeouts().implicitlyWait(Long.parseLong(GetConfig.getConfigProperty("implicitWaitTime")), TimeUnit.SECONDS);
        return driver;
    }

    private DriverType getBrowser() {
        String browserName = System.getProperty("browser");
        if (browserName == null) {
            browserName = GetConfig.getConfigProperty("browser");
        }
        if (browserName.equalsIgnoreCase("chrome"))
            return DriverType.CHROME;
        else if (browserName.equalsIgnoreCase("firefox"))
            return DriverType.FIREFOX;
        else
            throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
    }

    public synchronized void closeDriver() {
        log.info("Close Browser");
        DriverManager.getInstance().getDriver().quit();
    }
}

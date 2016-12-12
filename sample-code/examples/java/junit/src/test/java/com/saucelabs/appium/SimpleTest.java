package com.saucelabs.appium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

public class SimpleTest implements SauceOnDemandSessionIdProvider {

    private AppiumDriver<WebElement> driver;

    private List<Integer> values;

    private static final int MINIMUM = 0;
    private static final int MAXIMUM = 10;
    private String sessionId;

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */

    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();
    public static final String USERNAME = System.getenv("SAUCE_USERNAME");
    public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
    public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @Before
    public void setUp() throws Exception {

        // The following two lines are needed only when running locally on my machine.
        // When running the test on Saucelab, comment out the following lines.
        //File appDir = new File(System.getProperty("user.dir"), "../../../apps/TestApp/build/release-iphonesimulator");
        //File app = new File(appDir, "TestApp.app");

        //Use https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/ to doublecheck the configs
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appiumVersion", "1.6.2");
        capabilities.setCapability("platformVersion","10.0");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("browserName", "");
        capabilities.setCapability("deviceName","iPhone 7 Plus Simulator");

        // Test against the app I uploaded to Saucelab storage.
        capabilities.setCapability("app", "sauce-storage:TestApp.app.zip");

        driver = new IOSDriver<>(new URL(URL), capabilities);
        values = new ArrayList<Integer>();
        this.sessionId = driver.getSessionId().toString();

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private void populate() {
        //populate text fields with two random number
        Random random = new Random();
        int rndNum1 = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        values.add(rndNum1);
        int rndNum2 = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        values.add(rndNum2);

        driver.findElement(By.name("IntegerA")).sendKeys(String.valueOf(rndNum1));
        driver.findElement(By.name("IntegerB")).sendKeys(String.valueOf(rndNum2));
    }


    @Test
    public void testUIComputation() throws Exception {

        //The following two lines are necessary only on my local machine. On Saucelabs, the alert button does not show.
        //driver.switchTo().alert().accept();
        //Thread.sleep(3000); //Due to the slowness of closing Alerts, I needed to add a sleep here.

        // populate text fields with values
        populate();
        // trigger computation by using the button
        WebElement button = driver.findElement(By.className("XCUIElementTypeButton"));
        button.click();
        // is sum equal ?
        WebElement texts = driver.findElement(By.name("Answer"));
        assertEquals(String.valueOf(values.get(0) + values.get(1)), texts.getText());
    }


    public String getSessionId() {
        return sessionId;
    }
}

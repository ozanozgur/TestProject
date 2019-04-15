package Utilities;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.FlickAction;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BaseTest{

    public WebDriver driver ;
    public WebDriverWait wait;
    long start ;
    //public PageObject page;

    @BeforeTest
    @Parameters(value={"browser"})
    public void setUp(String browser) throws Exception {

        if(browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "./geckodriver.exe/");
           driver = new FirefoxDriver();
        }else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./chromedriver.exe/");
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
/**
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        driver.manage().window().maximize();
*/
        }

    @BeforeMethod
    public void startTime() {
        wait = new WebDriverWait(driver, 30);
        driver.manage().deleteAllCookies();
        start = System.currentTimeMillis();
    }


    @AfterTest(description = "Class Level Teardown!")
    public void tearDown() {
        driver.close();
        driver.quit();
    }


    public void getSomeSleep(int sec){
        try {
            Thread.sleep( sec * 1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void getScreenshot(ITestResult result) throws Exception {


        LogEntries browserLog = driver.manage().logs().get(LogType.BROWSER);
        LogEntries clientLogs = driver.manage().logs().get(LogType.CLIENT);
        //LogEntries performanceLogs = driver.manage().logs().get(LogType.PERFORMANCE);

        //LogEntries performanceLogs = driver.manage().logs().get(LogType.PERFORMANCE);
        //LogEntries profilerLogs = driver.manage().logs().get(LogType.PROFILER);
        //LogEntries serverLogs = driver.manage().logs().get(LogType.SERVER);


        if (ITestResult.FAILURE == result.getStatus()) {

            System.out.println(result.getMethod());

            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy   HH_mm_ss"  );
            Date date = new Date();
            String fileName = sdf.format(date);
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("./Screenshots/" + fileName + ".png" ));


            for (LogEntry entry : browserLog) {
                System.out.println(new Date(entry.getTimestamp()) + " " +
                        entry.getLevel() + " " + entry.getMessage());
            }
            for (LogEntry clientEntry : clientLogs){

                System.out.println(new Date(clientEntry.getTimestamp()) + " " +
                        clientEntry.getLevel() + " " + clientEntry.getMessage());
            }
            /*
            for (LogEntry performanceEntry : performanceLogs){

                System.out.println(new Date(performanceEntry.getTimestamp()) + " " +
                        performanceEntry.getLevel() + " " + performanceEntry.getMessage());
            }
            */

        }

        //ServerInfo.getServerInfo();

        long finish = System.currentTimeMillis();

        long totalTime = finish - start;

        if (totalTime > 30000L){
            System.out.println(totalTime  + "-->" + "It's too long !" );
            System.out.println(result.getInstanceName() + " --> " + result.getMethod());
        }
    }
}

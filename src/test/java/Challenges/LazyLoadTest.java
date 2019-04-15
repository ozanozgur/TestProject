package Challenges;

import Pages.HomePage;
import Utilities.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LazyLoadTest extends BaseTest{


    @Test(description = "Case Study Number:2")
    public void ContiniousScrolling() throws IOException, InterruptedException {

        HomePage homePage = new HomePage(driver , wait);
        homePage.navigateToSite();
        homePage.closeFancyBox();
        scroll();
        homePage.writeResponseTimesToCsvFile();
    }

    public void scroll() throws InterruptedException {
        for (int i = 0; i < 15 ; i++) {
            driver.manage().timeouts().implicitlyWait(5  , TimeUnit.SECONDS);
            driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
            getSomeSleep(3);
        }
    }
}

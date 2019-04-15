package Challenges;

import Pages.HomePage;
import Utilities.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;


public class VerifyBoutiqueLinks extends BaseTest {


    @Test(description = "Case Study Number:1")
    public void linksAndResponseCodes() throws IOException, InterruptedException {

        HomePage homePage = new HomePage(driver , wait);
        homePage.navigateToSite();
        homePage.closeFancyBox();
        homePage.scroll();
        homePage.writeStatusCodesToCsvFile();
    }
}


package Challenges;

import Pages.HomePage;
import Utilities.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

@Feature("Case Study Number : 3")
public class LoginTest extends BaseTest {

    @Test
    //@Step("Valid Email and Valid Password , Expected Results : Successful Login")
    public void validUserNameValidPassword () {
        HomePage homePage = new HomePage(driver , wait);
        homePage.navigateToSite();
        homePage.closeFancyBox();
        homePage.checkHomePageUrl();
        homePage.hoverToUserIcon(homePage.userIcon);
        homePage.isDisplayed(homePage.loginButton);
        homePage.clickToLoginButton();
        homePage.isDisplayed(homePage.loginPopup);
        homePage.enterValidEmail("onlyforatesting2@gmail.com");
        homePage.enterValidPassword("Testing2");
        homePage.clickToSubmitButton();
        homePage.waitInvisibility(homePage.fancyBoxOverlay);
        homePage.checkHomePageUrlAfterLogin();
        homePage.isDisplayed(homePage.loggedInUserIcon);
        homePage.hoverToUserIcon(homePage.loggedInUserIcon);
        homePage.verifyCurrentCustomer("Özgür Ozan Cömert");

    }
    @Test
    @Step("Valid Email and Invalid Password , Expected Results : Show Error Message To User")
    public void validUserNameInvalidPassword(){
        HomePage homePage = new HomePage(driver , wait);
        homePage.navigateToSite();
        homePage.closeFancyBox();
        homePage.checkHomePageUrl();
        homePage.hoverToUserIcon(homePage.userIcon);
        homePage.isDisplayed(homePage.loginButton);
        homePage.clickToLoginButton();
        homePage.isDisplayed(homePage.loginPopup);
        homePage.enterValidEmail("onlyforatesting2@gmail.com");
        homePage.enterInValidPassword("Testing1");
        homePage.clickToSubmitButton();
        homePage.verifyFailedLoginAttempt("Kullanıcıya Hatalı Login Denemesi Yaptığı Söylenmedi !!!"
                , "Hatalı E-Posta / Şifre. Tekrar Deneyin.");
    }
    @Test
    @Step("Valid Email and Empty Password , Expected Results : Show Error Message To User")
    public void validUserNameEmptyPassword(){
        HomePage homePage = new HomePage(driver , wait);
        homePage.navigateToSite();
        homePage.closeFancyBox();
        homePage.checkHomePageUrl();
        homePage.hoverToUserIcon(homePage.userIcon);
        homePage.isDisplayed(homePage.loginButton);
        homePage.clickToLoginButton();
        homePage.isDisplayed(homePage.loginPopup);
        homePage.enterValidEmail("onlyforatesting2@gmail.com");
        homePage.clickToSubmitButton();
        homePage.verifyFailedLoginAttempt("Kullanıcıya Şifre Alanının Boş Geçilmemesi Gerektiği Söylenmedi !!!" ,
                "Lütfen şifre giriniz.");
    }
    @Test(description = "Screenshot için hatalı method")
    @Step("Empty Email and Empty Password , Expected Results : Failed Attempt")
    public void emptyUserNameVaalidPassword() {
        HomePage homePage = new HomePage(driver, wait);
        homePage.navigateToSite();
        homePage.closeFancyBox();
        homePage.checkHomePageUrl();
        homePage.hoverToUserIcon(homePage.userIcon);
        homePage.isDisplayed(homePage.loginButton);
        homePage.clickToLoginButton();
        homePage.isDisplayed(homePage.loginPopup);
        homePage.enterValidEmail("onlyforatesting2@gmail.com");
        homePage.enterValidPassword("Testing2");
        homePage.clickToSubmitButton();
        homePage.waitInvisibility(homePage.fancyBoxOverlay);
        homePage.checkHomePageUrlAfterLogin();
        homePage.isDisplayed(homePage.loggedInUserIcon);
        homePage.hoverToUserIcon(homePage.loggedInUserIcon);
        homePage.verifyCurrentCustomer("Özgür Ozan");
    }

}

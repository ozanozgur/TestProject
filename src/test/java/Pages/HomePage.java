package Pages;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.hasItems;


public class HomePage extends BasePage {

    public String websiteUrl = "https://www.trendyol.com";
    public String loggedInUrl = "https://www.trendyol.com/Butik/Liste/Erkek?e=login";
    public By userIcon = By.cssSelector(".login-register-button-container");
    public By loginButton = By.xpath("//div[@class='account-button login']");
    public By fancyBoxCloseIcon = By.cssSelector(".fancybox-close");
    public By email = By.id("email");
    public By password = By.id("password");
    public By submitButton = By.cssSelector(".login-submit");
    public By currentUserName = By.cssSelector(".user-name");
    public By bigBoutiqueImage = By.xpath("//div[@class='butik-large-image']//img");
    public By littleBoutiqueImage = By.xpath("//div[@class='butik-small-image']//img");
    public By littleBoutiqueLinks = By.xpath("//div[@class='butik-small-image']//a");
    public By loginPopup = By.cssSelector(".popup-form-main");
    public By loggedInPanelContainer = By.cssSelector(".loggedin-panel-container");
    public By loggedInUserIcon = By.cssSelector(".navigation-icon-user");
    public By fancyBoxOverlay = By.className("fancybox-overlay");
    public By errorBox = By.cssSelector(".error-box");


    String resultsForCase1 = "./LinksAndResponseCodes.csv/";
    String resultsForCase2 = "./LinksResponseTimesAndStatusCode.csv/";
    String baseUrl = "https://www.trendyol.com";


    public HomePage(WebDriver driver , WebDriverWait wait) {
        super(driver , wait);
    }

    public void navigateToSite(){
        driver.navigate().to(websiteUrl);
        driver.manage().timeouts().pageLoadTimeout(30 , TimeUnit.SECONDS);
    }
    public void hoverToUserIcon(By element){
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(element)).perform();
    }

    public void clickToLoginButton() {
        click(loginButton);
    }
    public void clickToSubmitButton(){
        click(submitButton);
    }
    public void waitInvisibility(By element){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    public void checkHomePageUrl(){
        driver.getCurrentUrl().equals(websiteUrl);
    }
    public void checkHomePageUrlAfterLogin(){
        driver.getCurrentUrl().equals(loggedInUrl);
    }

    public void closeFancyBox() {
        waitVisibility(fancyBoxCloseIcon);
        click(fancyBoxCloseIcon);
        driver.switchTo().activeElement();
    }

    public void isDisplayed(By element){
        waitVisibility(element);
    }
    public String getErrorMessage(){
        return driver.findElement(errorBox).getText();
    }

    public void enterValidEmail(String validEmail){
        writeText(email , validEmail);
    }
    public void enterValidPassword(String validPassword){
        writeText(password , validPassword);
    }
    public void enterInValidEmail(String invalidEmail){
        writeText(email , invalidEmail);

    }
    public void enterInValidPassword(String invalidPassword){
        writeText(password , invalidPassword);
    }
    public void verifyCurrentCustomer(String expectedTextForCurrentCustomer){

        WebElement actualText = driver.findElement(currentUserName);

        Assertions.assertThat(actualText.getText()).withFailMessage("Kullanıcının İsmi Adresi Yanlıştır !!!")
                .isEqualTo(expectedTextForCurrentCustomer);
    }
    public void verifyFailedLoginAttempt(String customFailMessage , String expectedTextForFailedAttempt){

        WebElement actualText = driver.findElement(errorBox);

        Assertions.assertThat(actualText.getText()).withFailMessage(customFailMessage)
                .isEqualTo(expectedTextForFailedAttempt);
    }

    public void imageResponse(WebElement imgElement) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(imgElement.getAttribute("src"));
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 200 && response.getStatusLine().getStatusCode() != 200 ){
                System.out.println(response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public long getImageResponseTimes(String times){
        long time = given().post(times).timeIn(TimeUnit.MILLISECONDS);
        return time;
    }

    public int getBoutiqueLinksStatusCode(String links){
        int statusCode = given().get(links).getStatusCode();
        return statusCode;
    }

    public void writeResponseTimesToCsvFile() throws IOException {

        BufferedWriter br = new BufferedWriter(new FileWriter(resultsForCase2));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < littleBoutiqueImageUrls().size(); i++) {

            int boutiquesStatusCodes = getBoutiqueLinksStatusCode(getBoutiqueUrls().get(i));
            long littleBoutiqueTime = getImageResponseTimes(littleBoutiqueImageUrls().get(i));

            sb.append("\"" + littleBoutiqueTime + "\"");
            sb.append(",");
            sb.append("\"" + boutiquesStatusCodes + "\"" + "\n");
        }
        String columnNamesList = "Response Times , Status Codes";
        br.append(columnNamesList + "\n");
        br.write(sb.toString());
        br.close();
    }
    public void writeStatusCodesToCsvFile() throws IOException {

        BufferedWriter br = new BufferedWriter(new FileWriter(resultsForCase1));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <getBoutiqueUrls().size(); i++) {
            int boutiquesStatusCodes = getBoutiqueLinksStatusCode(getBoutiqueUrls().get(i));

            sb.append("\"" + getBoutiqueUrls().get(i) + "\"");
            sb.append(",");
            sb.append("\"" + boutiquesStatusCodes + "\"" + "\n");
        }
        String columnNamesList = "Links , Response Codes";
        br.append(columnNamesList + "\n");
        br.write(sb.toString());
        br.close();
    }

    public ArrayList<String> bigBoutiqueImageUrls() {

        return getImageLinks(bigBoutiqueImage);
    }

    public ArrayList<String> littleBoutiqueImageUrls() {

        return getImageLinks(littleBoutiqueImage);
    }

    private ArrayList<String> getImageLinks(By oneOfTheBoutiques) {
        int size = driver.findElements(oneOfTheBoutiques).size();
        List<WebElement> boutiqueItems = driver.findElements(oneOfTheBoutiques);
        ArrayList<String> images = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String image = boutiqueItems.get(i).getAttribute("src");
            images.add(image);
        }
        return images;
    }

    public ArrayList<String> getBoutiqueUrls() {
        int size = driver.findElements(littleBoutiqueLinks).size();
        List<WebElement> boutiqueLinks = driver.findElements(littleBoutiqueLinks);
        ArrayList<String> homePageBoutiqueUrls = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String links = boutiqueLinks.get(i).getAttribute("href");
            homePageBoutiqueUrls.add(links);
        }
        return homePageBoutiqueUrls;
    }

    public void scroll() throws InterruptedException {
        for (int i = 0; i < 15 ; i++) {
            driver.manage().timeouts().implicitlyWait(5  , TimeUnit.SECONDS);
            driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
            Thread.sleep(3000);
        }
    }


}

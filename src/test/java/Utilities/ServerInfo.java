package Utilities;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ServerInfo extends BaseTest {

    public void getServerInfo() {
        WebElement html = driver.findElement(By.cssSelector("head > meta:nth-child(19)"));
        String attribute = html.getTagName();
        System.out.println(attribute);
    }
}

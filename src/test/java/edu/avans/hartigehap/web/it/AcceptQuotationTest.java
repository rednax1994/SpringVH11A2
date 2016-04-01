package edu.avans.hartigehap.web.it;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcceptQuotationTest {
    public static String URL = "http://localhost:8080/hha2";
    public static String URL2 = "http://localhost:8080/hha2/restaurants/HartigeHap/banqueting/quotations/2";
    
    @Test
    public void AcceptQuotationTwo() {
        WebDriver driver = BrowserUtils.getWebDriver();
        driver.get(URL);
        log.info("Congratulations, the home page is available ;-) {}", URL);
        
        // Add javascript support
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Added bootstrap support
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        boolean loggedIn = false;
        try {
            driver.findElement(By.xpath("//*[contains(text(),'Logout')]"));
            loggedIn = true;
        } catch (Exception e) {
            loggedIn = false;
        }
        
        if (loggedIn) {
            js.executeScript("document.getElementById('btn-logout').click();");
        }
        
        log.info("Timeout of 10 seconds");
        
        // Added bootstrap support
        log.info("Timeout of 10 seconds");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        String source = driver.getPageSource();
        log.info("HTML Source of webpage:" + source);
        
        // Dropdown menu is now visible
        log.info("Looking for #login-test div");
        WebElement loginDiv = driver.findElement(By.id("login-test"));
        assertNotNull(loginDiv);
        log.info("Looking for j_username input element");
        js.executeScript("document.getElementById('form-username-j-username').setAttribute('value', 'manager')");
        
        log.info("Looking for j_password input element");
        js.executeScript("document.getElementById('form-username-j-password').setAttribute('value', 'manager')");
        
        String sourceAfterAttributes = driver.getPageSource();
        log.info("HTML Source of webpage after attributes:" + sourceAfterAttributes);
        
        log.info("Submit form");
        js.executeScript("document.getElementsByName('submit')[0].click();");
        driver.get("http://localhost:8080/hha2/restaurants/HartigeHap/banqueting/");
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        driver.get(URL2);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        js.executeScript("document.getElementsByName('accept')[0].click();");
        
        driver.get("http://localhost:8080/hha2/restaurants/HartigeHap/banqueting/");
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

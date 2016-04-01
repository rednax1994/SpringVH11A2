package edu.avans.hartigehap.web.it;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddOrderOptionTest {
    
    public static String URL = "http://localhost:8080/hha2/diningTables/11";
    
    @Test
    public void addOrderOption() {
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
        
        // Added bootstrap support
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        driver.findElement(By.xpath("//*[@id='deleteOrderOptionSelect']")).click();
        
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='mushrooms']")).click();
        
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='removeOrderItemButton']")).click();
        
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        js.executeScript("document.getElementsById('removeOrderItemButton')[0].click();");
        
        String sourceAfter = driver.getPageSource();
        log.info("HTML Source of webpage:" + sourceAfter);
        
        try {
            WebElement errorDiv = driver.findElement(By.className("error"));
            fail("For a succesful login, an error div is not expected: " + errorDiv);
        } catch (NoSuchElementException ex) {
            log.debug("Login succeeded ;-)");
        }
        
        log.info("done waiting");
    }
}
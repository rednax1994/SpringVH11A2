package edu.avans.hartigehap.web.it;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddOrderOptionTest {

    public static String URL = "http://localhost:8080/hha2/diningTables/11";

    @Test
    public void addOrderOption(){
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
        
        String source = driver.getPageSource();
        log.info("HTML Source of webpage:" + source);

        driver.findElement(By.xpath("//*[@id='deleteOrderOptionSelect']")).click();
     // Added bootstrap support
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='mushrooms']")).click();
     // Added bootstrap support
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='removeOrderItemButton']")).click();
     // Added bootstrap support
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        log.info("done waiting");
    }
}
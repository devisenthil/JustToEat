package JustEat;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Arrays;



public class JustToEat_Scenario_RefineTheSearchByDifferentFilters {
	
	public static void main(String[] args) throws Exception{
				
		System.setProperty("webdriver.chrome.driver", 
				 System.getProperty("user.dir")+"/chromedriver");			 
		WebDriver driver = new ChromeDriver();	
		
		Properties prop = new Properties();
		FileInputStream ip = new FileInputStream("/Users/devi/eclipse-workspace/JustEat/config.properties");
		prop.load(ip);
		
		String postalcode = prop.getProperty("PostalCode");
		String url = prop.getProperty("URL");
		String cuisine = prop.getProperty("Cuisine");
		String restaurant = prop.getProperty("Restaurant");
		
		launchJustToEatSite(driver, url);			
		searchPostalCode(driver, postalcode);
		filterByCuisineAndRatings(driver,cuisine);
		selectRestaurant(driver, restaurant);	
		closeBrowser(driver);
		
	}
	
	

	
	
	/* 
	 * Login into "Just To Eat" Website and confirm the landed url
	 */
	
	private static void launchJustToEatSite(WebDriver driver, String url) {
				
		driver.get(url);
		String currentUrl = driver.getCurrentUrl();
		//System.out.println("The URL of the current page is : "+currentUrl);
		driver.manage().window().maximize();
		if(currentUrl.contains(url)) {
			System.out.println("Landed in correct URL : "+currentUrl);
		}else {
			System.out.println("Landed in Incorrect URL : "+currentUrl);
		}
					
	}
	
	/* 
	 * After Landed in the "Just To eat" website, Enter the Postalcode and search for any restaurants nearby
	 */

	private static void searchPostalCode(WebDriver driver, String PostalCode) throws InterruptedException {
	
		driver.findElement(By.xpath("//*[(@name='postcode') and (@aria-label='Enter postcode')]")).sendKeys(PostalCode);
		driver.findElement(By.xpath("//button[(@type='submit') and (@aria-label='Search')]")).click();
		Thread.sleep(2000);
				
	}
	
	/* 
	 * After Landed in the "Just To eat" website and searched with postalcode, now refine the search 
	 * with cuisine selection and some more filters
	 */

	private static void filterByCuisineAndRatings(WebDriver driver, String cuisine) {
		
		driver.findElement(By.xpath("//*[(@id='serp-search') and (@placeholder='Search by cuisine or restaurant')]")).sendKeys(cuisine);
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,350)", "");
		
		driver.findElement(By.xpath("//*[@data-test-id='filter_item_select' and (@for='five_star')]")).click();
		
		
	}
	
	/* 
	 * Select the restaurant and you can provide any restaurant in the config properties
	 */
	
	private static void selectRestaurant(WebDriver driver, String restaurant) throws InterruptedException {
		
		WebElement element = driver.findElement(By.xpath("//*[contains(@data-js-wallpaper-fallback,'"+restaurant+"')]")); 
		Actions actions = new Actions(driver); 
		actions.moveToElement(element).click().perform();
		Thread.sleep(2000);
		
	}	
	
		
	/* 
	 * After testing Close the browser
	 */
	
	public static void closeBrowser(WebDriver driver) {
		
		driver.quit();
		
	}
	
}




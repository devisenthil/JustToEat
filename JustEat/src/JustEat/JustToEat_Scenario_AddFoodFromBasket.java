package JustEat;

import org.testng.annotations.Test;
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
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;



public class JustToEat_Scenario_AddFoodFromBasket {
	
	
	@Test
	public static void JustToEat_AddFoodFromBasket() throws Exception{
				
		System.setProperty("webdriver.chrome.driver", 
				 System.getProperty("user.dir")+"/chromedriver");			 
		WebDriver driver = new ChromeDriver();	
		
		Properties prop = new Properties();
		FileInputStream ip = new FileInputStream("/Users/devi/git/repository/JustEat/config.properties");
		prop.load(ip);
		
		String postalcode = prop.getProperty("PostalCode");
		String url = prop.getProperty("URL");
		String cuisine = prop.getProperty("Cuisine");
		String restaurant = prop.getProperty("Restaurant");
		
		launchJustToEatSite(driver, url);			
		searchPostalCode(driver, postalcode);
		filterByCuisineAndRatings(driver,cuisine);
		selectRestaurant(driver, restaurant);	
		AddFoodToBasket(driver);
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
	 * Add food item to tyhe cart to buy
	 */
	
	private static void AddFoodToBasket(WebDriver driver) throws Exception {
		
		String [] categories = {"Nachos","Salads"};
		String [] foodItems = {"Naked Nachos", "BBQ Pulled Jackfruit Fresh Salad"};
		
		 ArrayList addedItem = new ArrayList();
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,150)", "");
		
		for(int i = 0;i < categories.length; i++) {
			WebElement element1 = driver.findElement(By.xpath("//*[(contains(@href,'/restaurants-demotestddc-25/menu#cat')) and (contains(.,'"+categories[i]+"'))]"));
			if (element1.isDisplayed()) {
					element1.click();
				System.out.println("Category available : "+categories[i]);
				for (int j=0;j< foodItems.length;j++) {
					
					WebElement element2 = driver.findElement(By.xpath("//*[.='"+foodItems[j]+"']/../..//*[@class='addButton ']"));
					if(element2.isDisplayed()) {
					//Add item to the Basket
					
					element2.click();
					System.out.println("Item added to the basket : "+foodItems[j]);
					System.out.println("addedItem in the array : "+addedItem);
					
					addedItem.add(foodItems[j]);
					break;
					}else {
						System.out.println("No Such Item available, Please try some other item");
					}
					
				}
			}else {
				System.out.println("No Such Category available, Please try some other category");
			}
		}
		Thread.sleep(2000);
		
	}
		
	/* 
	 * After testing Close the browser
	 */
	
	public static void closeBrowser(WebDriver driver) {
		
		driver.quit();
		
	}
	
}




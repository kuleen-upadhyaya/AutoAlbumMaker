package cool.auto.imageupload;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import cool.auto.imageupload.properties.AppProperties;
import cool.auto.imageupload.utils.Keyboard;

public class Test2
{
	public static void main(String[] args) throws InterruptedException, IOException, AWTException
	{
		System.setProperty("webdriver.gecko.driver",
				"C:/Users/kulee/Downloads/geckodriver-v0.15.0-win64/geckodriver.exe");
		
		WebDriver driver = new FirefoxDriver();
		AppProperties appProps = new AppProperties();
		
		boolean retry = true;

		while (retry)
		{
			try
			{
				driver.get(appProps.getMyAlbum());
				retry = false;
			}
			catch (WebDriverException e)
			{
				retry = true;
			}
		}
		
		driver.manage().window().maximize();
		
		WebElement loginLink = driver.findElement(By.id("login"));
		loginLink.click();

		Thread.sleep(1000);
		
		WebElement email = driver.findElement(By.id("email"));
		email.click();
		webElementType(email, "kuleen.upadhyaya@gmail.com");
		
		WebElement password = driver.findElement(By.id("password"));
		password.click();
		webElementType(password, appProps.getImageUploadPwd());

		WebElement loginButton = driver.findElement(By.id("usernamePasswordLoginBtn"));
		loginButton.click();
		
		oneRound(driver, "C:\\Users\\kulee\\Downloads\\ForImageUpload\\1", "album0");
		oneRound(driver, "C:\\Users\\kulee\\Downloads\\ForImageUpload\\2", "album1");
		
		//driver.close();
		//driver.quit();
	}

	private static void oneRound(WebDriver driver, String albumPath, String albumNameStr) throws InterruptedException, IOException, AWTException
	{
		WebElement uploadImages = null;
		boolean repeat = true;
		
		while(repeat)
		{
			try
			{
				uploadImages = driver.findElement(By.id("uploadImages"));
				repeat = false;
			}
			catch(Exception e)
			{
				repeat = true;
			}
		}
		
		uploadImages.click();

		Thread.sleep(10000);
		
		WebElement selectPhotoes = driver.findElement(By.id("SWFUpload_0"));
		Actions actions = new Actions(driver);
		actions.moveToElement(selectPhotoes);
		actions.click().build().perform();
		
		String autoITLocation = "C:\\Program Files (x86)\\AutoIt3\\AutoIt3.exe";
		String au3FileLocation = "C:\\Users\\kulee\\autoitScripts\\UploadMultiple.au3";

		String command = autoITLocation + " " + au3FileLocation + " " + albumPath;
		
		Runtime.getRuntime().exec(command);
	    
		Thread.sleep(1000);
		
		double per = 0;
		
		while (per < 100)
		{
			WebElement totalUploadProgress = driver.findElement(By.id("totalUploadProgress"));
			String strPer = totalUploadProgress.getAttribute("aria-valuenow");
			
			per = Double.parseDouble(strPer);
			
			Thread.sleep(2000);
			
			System.out.println(per);
		}

		WebElement albumName = driver.findElement(By.id("albumName"));
		albumName.click();
		webElementType(albumName, albumNameStr);
		
		WebElement createAlbum = driver.findElement(By.cssSelector("button.uploadStats-button-1"));
		createAlbum.click();
		
		System.out.println("Process completed successfully ...");
	}

	private static void webElementType(WebElement we, String str) throws AWTException
	{
		//we.sendKeys(str);
		we.click();
		Keyboard kb = new Keyboard();
		kb.type(str);
	}
}

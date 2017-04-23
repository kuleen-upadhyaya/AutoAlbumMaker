package cool.auto.imageupload;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cool.auto.imageupload.properties.AppProperties;

public class App
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		System.setProperty("webdriver.gecko.driver",
				"C:/Users/kulee/Downloads/geckodriver-v0.15.0-win64/geckodriver.exe");

		AppProperties appProps = new AppProperties();
		
		WebDriver driver = new FirefoxDriver();

		boolean retry = true;

		while (retry)
		{
			try
			{
				driver.get(appProps.getShrinkPhotos());
				retry = false;
			}
			catch (WebDriverException e)
			{
				retry = true;
			}
		}

		processFile(driver, "C:\\Users\\kulee\\Downloads\\test\\DSC_0013.JPG",
				"C:\\Users\\kulee\\Downloads\\test\\DSC_0013_mini.JPG");
		processFile(driver, "C:\\Users\\kulee\\Downloads\\test\\DSC_0549.JPG",
				"C:\\Users\\kulee\\Downloads\\test\\DSC_0549_mini.JPG");
		processFile(driver, "C:\\Users\\kulee\\Downloads\\test\\DSC_0745.JPG",
				"C:\\Users\\kulee\\Downloads\\test\\DSC_0745_mini.JPG");
	}

	private static void processFile(WebDriver driver, String sourceFile, String destFile)
			throws InterruptedException, MalformedURLException, IOException, FileNotFoundException
	{
		WebElement fileUpload = driver.findElement(By.id("fileupload"));
		fileUpload.clear();
		fileUpload.sendKeys(sourceFile);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		boolean flag = true;

		while (flag)
		{
			WebElement status = driver.findElement(By.id("processingStatus"));
			Thread.sleep(1000);
			String strStatus = status.getText();
			System.out.println("Status : " + strStatus);

			if (strStatus.equals(""))
			{
				flag = false;
			}
		}

		driver.switchTo().frame("widgetFrame");

		WebElement downloadLink = driver.findElement(By.id("downloadLink"));
		String downloadUrl = downloadLink.getAttribute("href");
		System.out.println(downloadUrl);

		URL website = new URL(downloadUrl);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(destFile);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();

		String mainWindowHandle = driver.getWindowHandle();
		driver.switchTo().window(mainWindowHandle);
		driver.switchTo().defaultContent();
		
		WebElement tryAgain = driver.findElement(By.id("tryAgain"));
		tryAgain.click();
	}
}

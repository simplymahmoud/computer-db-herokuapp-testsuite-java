package framework;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;

public class FrameworkDriver {
	private WebDriver driver;
	private Properties properties;

	public FrameworkDriver() throws FileNotFoundException, IOException {
		driver = new FirefoxDriver();
		properties = new Properties();
		setUpProperties();
	}

	private void setUpProperties() throws FileNotFoundException, IOException {
		File elements = new File("resources\\Elements.properties");
		File configuration = new File("resources\\Configuration.properties");
		properties.load(new FileReader(elements));
		properties.load(new FileReader(configuration));
	}

	public String returnConfiguration(String config) {
		return properties.getProperty(config);
	}

	public String getURL() {
		return driver.getCurrentUrl();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void openPage(String page) {
		try {
			driver.get(properties.getProperty(page));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void selectDropDownItem(String element, String item) {
		Select dropDown = new Select(this.findElement(element));
		dropDown.selectByVisibleText(item);
	}

	public String getDropDownValue(WebElement element) {
		Select dropDown = new Select(element);
		return dropDown.getFirstSelectedOption().getText();
	}

	public void maximizeBrowser() {
		driver.manage().window().maximize();
	}

	public boolean isTextOnPage(String text) {
		return driver.getPageSource().contains(text);
	}

	public boolean isElementPresent(String element) {
		if (this.findElement(element) != null) {
			return true;
		}
		return false;
	}
	
	public String generateString(int lenght) {
		return RandomStringUtils.randomAlphabetic(lenght);
	}

	public String generateInt(int lenght) {
		return RandomStringUtils.randomNumeric(lenght);
	}

	public void click(String selector) {
		WebElement webElement = this.findElement(selector);
		webElement.click();
	}
	
	public WebElement findElementDynamic(String element , String method){
		try {
			switch (method) {
			case "id":
				return driver.findElement(By.id(element));
			case "css":
				return driver.findElement(By.cssSelector(element));
			case "link":
				return driver.findElement(By.linkText(element));
			case "xpath":
				return driver.findElement(By.xpath(element));
			default:
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public WebElement findElement(String elementProperty) {
		String split[] = elementProperty.split("[.]");
		try {
			switch (split[2]) {
			case "id":
				return driver.findElement(By.id(properties.getProperty(elementProperty)));
			case "css":
				return driver.findElement(By.cssSelector(properties.getProperty(elementProperty)));
			case "link":
				return driver.findElement(By.linkText(properties.getProperty(elementProperty)));
			default:
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public void clearAndFillTextField(String field, String text) {
		try {
			WebElement textfield = this.findElement(field);
			textfield.click();
			textfield.clear();
			textfield.sendKeys(text);
		} catch (org.openqa.selenium.ElementNotVisibleException e) {
			System.out.println(e);
		}
	}

	public void fillTextField(String field, String text) {
		WebElement textfield = this.findElement(field);
		textfield.click();
		textfield.sendKeys(text);
	}

	public void cleanDriver() {
		this.driver.quit();
	}

}


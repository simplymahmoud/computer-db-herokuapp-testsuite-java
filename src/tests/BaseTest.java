package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.FileReader;
import java.io.IOException;

import framework.FrameworkDriver;

public abstract class BaseTest {
	FrameworkDriver driver;
	Properties properties;
	
	@BeforeClass(alwaysRun=true , description = "Description")
	public void setup() throws FileNotFoundException, IOException{
		this.driver = new FrameworkDriver();
		this.properties = new Properties();
		setUpEnvironment();
	}
	
	private void setUpEnvironment() throws FileNotFoundException, IOException{
		 File elements = new File("resources\\Elements.properties");
		 File configuration = new File("resources\\Configuration.properties");
		 properties.load(new FileReader(elements));
		 properties.load(new FileReader(configuration));
	}
	
	
	@AfterClass(alwaysRun=true , description = "Description")
	public void afterclass(){
		this.driver.cleanDriver();
	}
}

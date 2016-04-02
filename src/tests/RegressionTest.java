package tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegressionTest extends BaseTest {
	String testComputer;

	@BeforeClass(alwaysRun = true, description = "Before Class Set Up")
	public void setup() throws FileNotFoundException, IOException {
		super.setup();
		testComputer = driver.generateString(5);
	}

	@BeforeMethod
	public void setupTest() {
		this.driver.openPage("page.url");
	}

	@Test(description = "Test01: Add a Computer ", priority = 0)
	public void testAddComputer() {
		System.out.println("Adding Computer : " + testComputer);
		driver.click("add.button.id");
		driver.fillTextField("name.field.id", testComputer);
		driver.fillTextField("introduced.field.id", "1960-01-01");
		driver.fillTextField("discontinued.field.id", "1965-01-01");
		driver.selectDropDownItem("company.dropdown.id", "IBM");
		driver.click("create.button.css");
		Assert.assertTrue(driver.isElementPresent("done.alert.css"), "The done message is not present in the UI");
		Assert.assertEquals(driver.findElement("done.alert.css").getText(),
				"Done! Computer " + testComputer + " has been created");
	}

	@Test(description = "Test02: Add a Computer , cancel action ", priority = 1)
	public void testAddComputerCancelAction() {
		driver.click("add.button.id");
		driver.fillTextField("name.field.id", testComputer);
		driver.fillTextField("introduced.field.id", "1960/01/01");
		driver.fillTextField("discontinued.field.id", "1965/01/01");
		driver.selectDropDownItem("company.dropdown.id", "IBM");
		driver.click("cancel.button.link");
		Assert.assertFalse(driver.isElementPresent("done.alert.css"), "The done! message is present in the UI");
	}

	@Test(description = "Test03: Add a Computer , check required fields", priority = 2)
	public void testAddComputerWithoutRequiredFields() {
		driver.click("add.button.id");
		driver.fillTextField("name.field.id", "");
		driver.fillTextField("introduced.field.id", "1960-01-01");
		driver.fillTextField("discontinued.field.id", "1965-01-01");
		driver.selectDropDownItem("company.dropdown.id", "IBM");
		driver.click("create.button.css");
		Assert.assertTrue(driver.isTextOnPage("clearfix error"), "The required field was not highlited");
		Assert.assertNull(driver.findElement("done.alert.css"), "The done! message was shown");
	}

	@Test(description = "Test12: Search non existing computer ", priority = 3)
	public void searchInexistentComputer() {
		WebElement result = searchComputer("NotExistingComputer");
		Assert.assertNull(result, "There was a result found");
		Assert.assertTrue(this.driver.isTextOnPage("Nothing to display"), "Nothing to display was not found");
	}

	@Test(description = "Test09: Search a Computer ", priority = 4)
	public void testSearchComputer() {
		WebElement result = searchComputer(testComputer);
		Assert.assertNotNull(result, "The done message is not present in the UI");
		result.click();
		Assert.assertTrue(driver.isElementPresent("delete.button.css"), "The delete computer button was not present");
		Assert.assertTrue(driver.isElementPresent("name.field.id"), "The name field was not found");
		Assert.assertEquals(driver.findElement("name.field.id").getAttribute("value"), this.testComputer);
	}

	@Test(description = "Test05: Edit a Computer ", priority = 5)
	public void testEditComputer() {
		System.out.println("Editing Computer : " + testComputer);
		WebElement result = searchComputer(testComputer);
		result.click();
		driver.clearAndFillTextField("name.field.id", testComputer + "_edited");
		driver.clearAndFillTextField("introduced.field.id", "1960-01-01");
		driver.clearAndFillTextField("discontinued.field.id", "1965-01-01");
		driver.selectDropDownItem("company.dropdown.id", "IBM");
		driver.click("edit.button.css");
		this.testComputer = testComputer + "_edited";
		Assert.assertTrue(driver.isElementPresent("done.alert.css"), "The done message is not present in the UI");
		Assert.assertEquals(driver.findElement("done.alert.css").getText(),
				"Done! Computer " + testComputer + " has been updated");

	}

	@Test(description = "Test06: Edit a Computer , cancel the action", priority = 6)
	public void testEditComputerCancelAction() {
		WebElement result = searchComputer(testComputer);
		result.click();
		driver.clearAndFillTextField("name.field.id", testComputer);
		driver.clearAndFillTextField("introduced.field.id", "1960-01-01");
		driver.clearAndFillTextField("discontinued.field.id", "1965-01-01");
		driver.selectDropDownItem("company.dropdown.id", "IBM");
		driver.click("cancel.button.link");
		Assert.assertFalse(driver.isElementPresent("done.alert.css"), "The done message is  present in the UI");
	}

	@Test(description = "Test07: Edit a Computer without name", priority = 7)
	public void testEditComputerWithoutName() {
		WebElement result = searchComputer(testComputer);
		result.click();
		driver.clearAndFillTextField("name.field.id", "");
		driver.click("edit.button.css");
		Assert.assertFalse(driver.isElementPresent("done.alert.css"), "The done message is  present in the UI");
		Assert.assertTrue(driver.isTextOnPage("clearfix error"), "The required field was not highlited");
	}

	@Test(description = "Test08: Delete a Computer ", priority = 8)
	public void testDeleteComputer() {
		System.out.println("Deleting Computer : " + testComputer);
		WebElement result = searchComputer(testComputer);
		result.click();
		Assert.assertTrue(driver.isElementPresent("delete.button.css"), "The delete computer button was not present");
		driver.click("delete.button.css");
		Assert.assertTrue(driver.isElementPresent("done.alert.css"), "The done message is not present in the UI");
		Assert.assertEquals(driver.findElement("done.alert.css").getText(), "Done! Computer has been deleted");
	}

	private WebElement searchComputer(String computerName) {
		driver.fillTextField("search.field.id", computerName);
		driver.click("search.button.id");
		WebElement result = driver.findElementDinamic("//a[contains(text(),'" + computerName + "')]", "xpath");
		return result;
	}

	@AfterClass(alwaysRun = true, description = "Description")
	public void afterclass() {
		this.driver.cleanDriver();
	}
}

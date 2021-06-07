package com.NRMA.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.NRMA.qa.base.TestBase;
import com.NRMA.qa.pages.ProductSelectionPage;

public class SmokeTestCase extends TestBase {
	
	ProductSelectionPage homepage;
	
	public SmokeTestCase() {
		super();
	}
	
	@BeforeTest
	public void setup() {
		
		initialization();
		homepage = new ProductSelectionPage();
		
	}
	
	@Test(priority=1)
	public void VerifyHomePageTitle() {
		
		String title=homepage.verifyHomePageTitle();
		
		Assert.assertEquals(title,"Villa Availability | NRMA Treasure Island Holiday Resort");
	}
	
	@Test(priority=1)
	public void resortBooking() {
		
		homepage.clickOnDateSelector();
		homepage.bookAHotel();
		
	}
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
	}

}

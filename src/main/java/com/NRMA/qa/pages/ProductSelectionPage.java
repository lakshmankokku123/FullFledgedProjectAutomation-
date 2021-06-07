package com.NRMA.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.NRMA.qa.Utilities.TestUtil;
import com.NRMA.qa.base.TestBase;

public class ProductSelectionPage extends TestBase{
	
	TestUtil util=new TestUtil();
	

	@FindBy(xpath="//*[@id='txtDateRange']")
	@CacheLookup
	WebElement dateSelector;
	

	@FindBy(xpath="//h3[@id='resultTotal']")
	@CacheLookup
	WebElement results;
	
	@FindBy(xpath="//div[@class='accommodationResult']")
	@CacheLookup
	WebElement accomadationResults;
	
	

	@FindBy(xpath="(//button[text()='Apply'])[2]")
	@CacheLookup
	WebElement applyButton;
	
	
	public ProductSelectionPage() {
		
		PageFactory.initElements(driver,this);
	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
	public void clickOnDateSelector() {
		
		util.highlightElements("txtDateRange");
		util.jsClick(dateSelector);
		util.scrollIntoView(applyButton);
		try {
			util.dateUtil();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//util.highlightElements("btnMobileFilterApply");
		
		applyButton.click();
	}
	
	public void bookAHotel() {
		String resultsString=results.getText();
		String str[]=resultsString.split(" ");
		int val=Integer.parseInt(str[0]);
		if(val>=1) {
			util.jsClick(accomadationResults);
			 List<WebElement> columns=accomadationResults.findElements(By.tagName("div"));
			 for (WebElement cell: columns){
				 String text=cell.getAttribute("data-name");
		          if(text != null){
		        	  System.out.println(text);
		          }else {
		        	  
		          }
		        	  
		           
		         }
			
		}
		
	}
	
	
	

}

package com.NRMA.qa.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import com.NRMA.qa.base.TestBase;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 90;
	public static long IMPLICIT_WAIT = 60;

	public static String TESTDATA_SHEET_PATH = "C:\\Users\\lakshman.kokku\\Workspace\\SeleniumLearning\\src\\test\\resources\\Configuration\\TestData.xlsx";

	static Workbook book;
	static Sheet sheet;
	static JavascriptExecutor js;

	public void switchToFrame() {
		driver.switchTo().frame("mainpanel");
	}

	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				// System.out.println(data[i][k]);
			}
		}
		return data;
	}

	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

	public void jsClick(WebElement ele) {

		// WebElement ele = driver.findElement(By.xpath("element_xpath"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", ele);

	}

	public void highlightElements(String idl) {
		WebElement ele = driver.findElement(By.id(idl));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0]. setAttribute('style', 'border:3px solid red; background:white')", ele);
		//jsExecutor.executeScript("document.getElementById('txtDateRange').value='Sat 5 Jun – Fri 18 Jun'");
	}

	public void scrollIntoView(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollBy(0,400)");

	}

	public void dateUtil() throws Throwable {

		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
		String date = formatter.format(d);
		String splitter[] = date.split("-");
		String month_year = splitter[1];
		String day = splitter[0].trim();
		String Year = splitter[2];
		System.out.println(month_year);
		System.out.println(day);
		System.out.println(Year);
		String day2=null;
	if(day.startsWith("0")) {
		
		 day2=day.split("0")[1];
	}
		//String calendarDateDuplicate =day.startsWith("0").split("0")[1];
		String targetDate = getFutureDate();
		selectDate(month_year, day2, Year, targetDate);
	}

	public void selectDate(String month_year, String select_day, String year, String day2) throws InterruptedException {

		Thread.sleep(5000);

		List<WebElement> ele1 = driver.findElements(By.xpath("//div[@class='litepicker']//span"));

		List<WebElement> ele2 = driver.findElements(By.xpath("//div[@class='litepicker']//strong"));

		// System.out.println(ele1.get(2).getText());
		
		System.out.println(ele1.size());// 4
		System.out.println(ele2.size());// 3

		for (int i = 0; i < ele1.size(); i++) {

			Thread.sleep(2000);
			String text1 = ele1.get(i).getText();
			String text2 = ele2.get(i).getText();

			if (text1.equalsIgnoreCase(year) && text2.equalsIgnoreCase(month_year)) {
				// for(int j=Integer.parseInt(select_day);j<=Integer.parseInt(day2);j++) {
				String temp1 = "(//*[@id='navDesktop']//div[text()='";
				String temp2 = "'])[1]";

				String actualXpath1 = temp1 + select_day + temp2;
				String actualXpath2 = temp1 + day2 + temp2;
				By locator1 = By.xpath(actualXpath1);
				By locator2 = By.xpath(actualXpath2);
				click(locator1);
				Thread.sleep(5000);
				click(locator2);

			} else {

			}

		}

	}

	public String getFutureDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");

		Calendar cal = Calendar.getInstance();

		System.out.println("current date: " + cal.getTime());
		cal.add(Calendar.DATE, 7);
		String tempdate = formatter.format(cal.getTime());
		String temp[] = tempdate.split("-");
		return temp[0];
	}

	public void click(By locator) {
		boolean didItSucceed = false;
		int attempts = 1;

		while (!didItSucceed && attempts < 5) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 100);
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				wait.until(ExpectedConditions.elementToBeClickable(locator));
				driver.findElement(locator).click();

				didItSucceed = true;
			} catch (Exception e) {
				System.out.println(e.getMessage());

			}

		}

	}
	
	public void explicitWait(WebElement ele) {
		
		WebDriverWait wait =new WebDriverWait(driver,100);
		wait.until(ExpectedConditions.visibilityOf(ele));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		
		
	}

}

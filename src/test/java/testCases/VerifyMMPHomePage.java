package testCases;

import org.apache.commons.mail.EmailException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import factory.BrowserFactory;
import factory.DataProviderFactory;
import pages.MMPHomePage;
import utility.Mail;

public class VerifyMMPHomePage {
	
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	
	@BeforeMethod
	public void setUp() throws EmailException {
	
	driver = BrowserFactory.getBrowser("Firefox");
	Mail.SendEmail();
	//System.out.println("Email sent");
	report = new ExtentReports(".\\Reports\\MMPHomePageReport.html",true);
	logger = report.startTest("Verify MMPHome Page - Logs and reports added");
	
	driver.get(DataProviderFactory.getConfig().getApplicationUrl());
	logger.log(LogStatus.INFO, "Application up and running");
	}
	
	@Test
	public void verifyTitle() throws InterruptedException{
	
		MMPHomePage mmpHome = PageFactory.initElements(driver, MMPHomePage.class);
		
		String mmpTitle = mmpHome.getTitle();
		Assert.assertEquals(mmpTitle, "NAMTG");
		logger.log(LogStatus.PASS, "HomePage titles are matching");
		System.out.println("MMP Title is: "+ mmpTitle);
		
	}	
		
	@AfterMethod
		public void tearDown() {
		
		BrowserFactory.closeBrowser();
		report.endTest(logger);
		report.flush();
	}
}

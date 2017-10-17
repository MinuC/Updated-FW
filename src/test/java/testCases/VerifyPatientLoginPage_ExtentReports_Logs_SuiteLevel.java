package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import factory.BrowserFactory;
import factory.DataProviderFactory;
import pages.MMPHomePage;
import pages.PatientLoginPage;


public class VerifyPatientLoginPage_ExtentReports_Logs_SuiteLevel {
	
WebDriver driver;
MMPHomePage mmpHome;
ExtentReports report;
ExtentTest logger;

	@BeforeTest
	public void setUp() {
	driver = BrowserFactory.getBrowser("Firefox");
	
	report = new ExtentReports(".\\Reports\\Suite_PatientLoginPage.html", true);
	logger = report.startTest("Verifying MMPHomePage & Patient Login Page - Logs and reports added");
	driver.get(DataProviderFactory.getConfig().getApplicationUrl());	
	logger.log(LogStatus.INFO, "Application up and running");	
	}
	
	@Test(priority=0)
	public void verifyTitle() throws InterruptedException{
	
		mmpHome = PageFactory.initElements(driver, MMPHomePage.class);
		
		String mmpTitle = mmpHome.getTitle();
		Assert.assertEquals(mmpTitle, "NAMTG");
		//System.out.println("MMP Title is: "+ mmpTitle);
		logger.log(LogStatus.PASS, "MMPHomePage - Titles are matching");
	}	
	
	@Test(priority=1,dependsOnMethods={"verifyTitle"})
	public void appLogin() throws InterruptedException{
		
		mmpHome = PageFactory.initElements(driver, MMPHomePage.class);	
		mmpHome.navigateToPatientLoginPage();
		
		logger.log(LogStatus.INFO, "Navigated to PatientLoginPage");
		
		PatientLoginPage pLogin = PageFactory.initElements(driver, PatientLoginPage.class);
		//pLogin.loginToAppln("TomBrady_123", "TomBrady@123");//passing parametre values manually
		
		/*loginToApp method in PatientLoginPage is returning the username. Assigning that value to string user.
		Reading login credentials from Excel sheet*/
		pLogin.loginToAppln(DataProviderFactory.getExcel().getCellData(0, 0, 0), DataProviderFactory.getExcel().getCellData(0, 0, 1));
		
		logger.log(LogStatus.PASS, "Logged in to PatientHomePage");
	}	
		
	@AfterTest
	public void tearDown(){
		
		BrowserFactory.closeBrowser();
		report.endTest(logger);
		report.flush();
	}

}

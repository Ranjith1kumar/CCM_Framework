package contractManagement;


import java.util.Set;
import org.openqa.selenium.*;
import analytics.ReportsLibrary;
import libraries.*;
import prerequisites.PrerequisitesLibrary;
import testPriceWorkFlows.TestPriceScenariosLibrary;

/**
 * @author C16137
 *
 */
public class TestPriceLibrary extends CCMLibrary
{
	
	PrerequisitesLibrary oPrerequisitesLibrary =  new PrerequisitesLibrary(driver, oReport, oParameters);
	
	ExcelData oExcelData = new ExcelData(oParameters);
	
	TestPriceScenariosLibrary oTestPriceScenariosLibrary = new TestPriceScenariosLibrary(driver, oReport, oParameters);
	
	ReportsLibrary oReportsLibrary = new ReportsLibrary(driver, oReport, oParameters);
	
	RateSheetLibrary oRateSheetLibrary = new RateSheetLibrary(driver, oReport, oParameters);
	
	public TestPriceLibrary(WebDriver driver, Report oReport, Parameters oParameters) 
	{
		super(driver, oReport, oParameters);
	}
	
	By CoveredChargesTotal=By.xpath("//div[@id='eor']//div[@id='claimSummary']//td[@class='currency ng-isolate-scope']//span[@class='ng-binding green']");
	
	By expectedReimbursement = By.xpath("//*[@id='totalExpected']/span[contains(.,'Expected Reimbursement:')]");
	
	By expectedReimbursementAmount = By.xpath("//table[@id='lineItems']//tr[@class='footer-title']//td/span[@class='green']");
	
	public void infoEORDisplayed() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if (IsElementDisplayed("Expected Reimbursement",expectedReimbursement))
		{
			scroll("Covered Charge Value", CoveredChargesTotal);
			fixed_wait_time(3);
			
			oReport.AddStepResult("EOR information", "On scrolling down through EOR tab, EOR information is displayed", "PASS");	
		}
		else
			oReport.AddStepResult("EOR information", "On scrolling down through EOR tab, EOR information is not displayed", "FAIL");
		
		driver.switchTo().defaultContent();
	} 
	
	
	By printIconxpath = By.xpath("//*[@id='eor']//a[@icon='print']/i[@class='left fa fa-print']");
	
	By printCancelIconxpath = By.xpath("//*[@id='print-header']/div/button[@class='cancel']");
	
	
	public void printEOR()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		driver.switchTo().frame("testPriceEorView");
		
		click_button("Print Button", printIconxpath);
		fixed_wait_time(3);
		
		driver.switchTo().defaultContent();
		
		fixed_wait_time(2);
	
		String parentWindow = driver.getWindowHandle();
		Set<String> handles =  driver.getWindowHandles();
	
		for(String windowHandle  : handles)
		{
			if(!windowHandle.equals(parentWindow))
		    {
				driver.switchTo().window(windowHandle);
				
				//  <!--Perform your operation here for new window-->
				oReport.AddStepResult("print view", "Upon clicking on Print button, print page gets opened with Eor details","PASS");
				click_button("Print Cancel Button", printCancelIconxpath);
		    }
		}
		driver.switchTo().window(parentWindow);
	}
	

	By testPrice = By.xpath("//div[@id='nav']//a[contains(text(),'Test Price')]");
	
	public void RateSheetCodeValidation()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		oParameters.SetParameters("RateSheetName", get_field_value("RateSheet Code",oReportsLibrary.rateSheetColumn));
		
		click_button("Claim Id", oReportsLibrary.claimIDColumn);
		fixed_wait_time(8);
		waitFor(oTestPriceScenariosLibrary.testPriceButton,"Test Price Button");
		
		click_button("Test Price Button", oTestPriceScenariosLibrary.testPriceButton);
			
		fixed_wait_time(3);
		waitFor(oPrerequisitesLibrary.TestPricerateSheetCode,"Templet RateSheet Code");
		
		if(IsDisplayed("TestPrice Plugin", testPrice))
			oReport.AddStepResult("TestPrice Plugin", "Clicked on Testprice plugin and verified that page navigates to Testprice Plugin", "PASS");
		else
			oReport.AddStepResult("TestPrice Plugin", "Clicked on Testprice plugin and verified that page is not navigates to Testprice Plugin", "FAIL");
		
		By RateSheetCode = By.xpath("//div[@class='ratesheetcodedropdown']//ul[@id='payerContracts-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("RateSheetName")+"']");
	
		if(IsDisplayed("RateSheet Code", RateSheetCode))
			click_button("RateSheet Code", RateSheetCode);
		
		click_button("Test Price Button", oTestPriceScenariosLibrary.testPriceButton);
		
		driver.switchTo().frame("testPriceEorView");
		waitFor(oReportsLibrary.EORtitleBar,"EOR Title Bar");
		
		if(IsElementDisplayed("Explanation of Reimbursement", oReportsLibrary.EORtitleBar))
			oReport.AddStepResult("Explanation of Reimbursement", "Clicked on TestPrice Plugin and verified that page navigate to Explanation of Reimbursement page is displayed ", "PASS");
		else
			oReport.AddStepResult("Explanation of Reimbursement", "Clicked on TestPrice Plugin and verified that page is not navigated to Explanation of Reimbursement page ", "FAIL");
	
		driver.switchTo().defaultContent();
	}
	
	
	public void TestPrice_UB_04_Claim()
	{
		login("EDIT");
		changePricingEngine();
		oReportsLibrary.xpathChange();
		oRateSheetLibrary.navigateToRateSheets();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "TestPriceCMSUBClaim");		
		oRateSheetLibrary.addRateSheetIcon();
		oRateSheetLibrary.AddRateSheetDetails("");
		oRateSheetLibrary.rateSheetSaveButton("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "TestPrice_VR");
		oRateSheetLibrary.AddSectionDetails("","");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","Term_AdditiveTired_VR","AdditiveTiered");
		oRateSheetLibrary.addTermButton("",oParameters.GetParameters("RateSheetName"),oParameters.GetParameters("SectionName"));
		oRateSheetLibrary.selectRateType("");
		oRateSheetLibrary.termSaveButton("");
		
		oPrerequisitesLibrary.navigateToTestPrice();
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "TestPrice_UBClaim", "Additive Tiered");
		oPrerequisitesLibrary.templetBuild_UB04_Claim("");
		oTestPriceScenariosLibrary.TestPriceValidation(oParameters.GetParameters("CoveredCharges"),oParameters.GetParameters("ContractualAdjustments"),oParameters.GetParameters("InsurancePayment"));
		infoEORDisplayed();
		//printEOR();
		oTestPriceScenariosLibrary.navigateBackToUB04Tab_CMS1500();
		oPrerequisitesLibrary.ChangeChargeValue_Validation();
		oReportsLibrary.navigateToClaimRepricingReport();
		oReportsLibrary.claimTypeReports(oParameters.GetParameters("ClaimType"));
		oReportsLibrary.repriceStatusReports("1 - Success");
		RateSheetCodeValidation();
		
		oRateSheetLibrary.navigateToRateSheets();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "TestPriceCMSUBClaim");
		oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"),oParameters.GetParameters("RateSheetName"));
	
		logout();
	}
	
	
	public void TestPrice_CMS_1500_Claim()
	{
		login("EDIT");
		changePricingEngine();
		oReportsLibrary.xpathChange();
		oPrerequisitesLibrary.navigateToTestPrice();
		oPrerequisitesLibrary.navigateCMS1500Claim();
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_CMS1500", "CMS1500Claim");
		oPrerequisitesLibrary.claimProcessingDetails_CMS_1500("");
		oPrerequisitesLibrary.diagnosisCodeDetails("");
		oPrerequisitesLibrary.lineItems_CMS_1500_Claim("");
		oPrerequisitesLibrary.billingProviderDetails_CMS1500_Claim("");
		oTestPriceScenariosLibrary.TestPriceValidation(oParameters.GetParameters("CoveredCharges"),oParameters.GetParameters("ContractualAdjustments"),oParameters.GetParameters("InsurancePayment"));
		infoEORDisplayed();
		//printEOR();
		oTestPriceScenariosLibrary.navigateBackToUB04Tab_CMS1500();
		oPrerequisitesLibrary.ChangeChargeValue_Validation();
		oReportsLibrary.navigate_to_Analytics();
		oReportsLibrary.select_Report("Claim Repricing Report");
		oReportsLibrary.claimTypeReports(oParameters.GetParameters("ClaimType"));
		oReportsLibrary.repriceStatusReports("1 - Success");
		RateSheetCodeValidation();
		logout();
	}
}

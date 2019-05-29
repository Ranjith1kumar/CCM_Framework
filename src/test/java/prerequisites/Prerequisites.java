package prerequisites;

import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;


public class Prerequisites 
{
	Browser oBrowser;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;
	PrerequisitesLibrary oPrerequisitesLibrary;

	String className[] = this.getClass().getName().toUpperCase().split("\\."); // Package Name

	@BeforeMethod()
	public void InitailSetup() 
	{
		oParameters = new Parameters();
		oParameters.SetParameters("CLASSNAME", className[1]);
	}

	@BeforeClass()
	public void InitailSetup1() 
	{
		oClassParameters = new Parameters();
		oClassParameters.SetParameters("CLASSNAME", className[1]);
		oClassReport = new ClassReport(oClassParameters);
	}

	@Test()
	public void Contracts_TestData_Build() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);

		oPrerequisitesLibrary.createContract();
	}

	@Test()
	public void Codes_TestData_Build() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.createCode();
	}

	/*@Test()
	public void Models_TestData_Build() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);

		oPrerequisitesLibrary.createModelJob();
	}*/
	
	
	@Test()
	public void Schedules_TestData_Build() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);

		oPrerequisitesLibrary.addingSchedules();
	}
	
	@Test()
    public void QualificationGroups_TestData_Build() 
    {
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.CreateQualificationGroups();
    }

	
	/*@Test()
	public void CCM_VR_Soarian_Configuration_User_Groups() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);

		oPrerequisitesLibrary.createUserGroups();
	}*/
	
	@Test
	public void TestPriceTemplet_TestData_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.TestPriceTemplet_UB04_Claim_Build();
	}
	
	@Test
	public void TestPriceTemplet_CMS1500_Claim_TestData_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
			
		oPrerequisitesLibrary.TestPriceTemplet_CMS1500_Claim();
	}
	
	@Test()
	public void RateSheet_TestData_CreateRateSheetBuild()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.createratesheetdetails();
	}
	
	
	@Test()
	public void RateSheet_TestData_SectionBuild()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.createSectiondetails();
	}
	
	
	@Test()
	public void RateSheet_TestData_TermBuild()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.createRateTerm();
	}
	
	@Test()
	public void PPS_TestData_DRG_UserRates_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_DRG_User_Rates_Build();

	}
	
	
	@Test()
	public void PPS_TestData_DRG_GrouperOptions_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_DRG_Grouper_Options_Build();
	}
	
	@Test()
	public void PPS_TestData_DRG_ProviderValues_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_DRG_Provider_Values_Build();
	}
	
	@Test()
	public void PPS_TestData_DRG_GrouperDefinitions_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_DRG_GroupingDefinitions();

	}
	
	@Test()
	public void PPS_TestData_DRG_GroupingRuleSet_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_DRG_GroupingRuleSet();
	}
	
	@Test()
	public void PPS_TestData_CMG_UserRates_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_CMG_UserRates();
	}
	
	@Test()
	public void PPS_TestData_CMG_ProviderValues_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_CMG_ProviderRates();
	}
	
	@Test()
	public void PPS_TestData_RUG_UserRates_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_RUG_UserRates();
	}
	
	@Test()
	public void PPS_TestData_APC_APG_ProviderValues_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_APC_APG_ProviderValues();
	}
	
	@Test()
	public void PPS_TestData_APC_APG_GrouperOptions_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_APC_APG_GrouperOptions();
	}
	
	@Test()
	public void PPS_TestData_APC_APG_GrouperDefinitions_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_APC_APG_GroupingDefinitions();
	}
	
	@Test()
	public void PPS_TestData_APC_APG_GrouperRuleSet_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.Create_PPS_APC_APG_GroupingRuleSet();

	}
	
	@Test()
	public void PPS_TestData_Dialysis_Comborbidity_Categories_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.PPS_Dialysis_Comrbidity_Categories_Build();
	}
	
	@Test()
	public void PPS_TestData_Dialysis_Rate_Factors_Build()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase()); 
		oBrowser = new Browser(oParameters);
		oPrerequisitesLibrary = new PrerequisitesLibrary(Browser.driver, Browser.oReport, oParameters);
		
		oPrerequisitesLibrary.create_PPS_Dialysis_Rate_Factors_Build();
	}

	@AfterMethod
	public void FinalSetup() 
	{
		oBrowser.cleanup();
		oClassReport.AddClassResult(oParameters.GetParameters("TESTNAME"), oParameters.GetParameters("TestStatus"),	oParameters.GetParameters("HTML_REPORT_NAME"));
		Browser.oAuraSetup.ALM_JIRA_Update(oParameters);
	}

	@AfterClass
	public void FinalSetup1() 
	{
		oClassReport.cleanup();
	}
}
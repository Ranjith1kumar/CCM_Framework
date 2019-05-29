package contractManagement;

import java.io.IOException;

import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;

public class PPS
{
	Browser oBrowser;
	PPSLibrary oPPSLibrary;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;

	String className[] = this.getClass().getName().toUpperCase().split("\\."); 

	
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
	public void CCM_VR_Soarian_Contract_Management_PPS_PPSDRG_User_Rates_CRUD_Operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.DRG_UserRateSet();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_PPSDRG_Grouper_Option_CRUD_Operation() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oPPSLibrary.DRG_GrouperOptions();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_PPSDRG_Provider_Values_CRUD_Operation() throws IOException 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.DRG_ProviderValues();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_PPSDRG_Grouping_Definitions_CRUD_Operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.DRG_GroupingDefinitions();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_PPSDRG_Grouping_Rule_Set_CRUD_Operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.DRG_GroupingRuleSet();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_APC_APG_Provider_Values_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_APC_APG_ProviderValues();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_APC_APG_Grouper_Options_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_APC_APG_GrouperOptions();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_APC_APG_Grouping_Definitions_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_APC_APG_GroupingDefinitions();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_APC_APG_Grouping_RuleSet_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_APC_APG_GroupingRuleSet();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_CMG_Provider_Values_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_CMG_ProviderValues();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_Dialysis_Rate_Factors_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_Dialysis_RateFactrors();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_Dialysis_Comorbidity_Categories_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_Dialysis_ComorbidityCategories();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_RUG_User_Rates_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_RUG_UserRates();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_Contract_Management_PPS_CMG_User_Rates_CRUD_operation() 
	{	
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			oBrowser = new Browser(oParameters);
			oPPSLibrary = new PPSLibrary(Browser.driver, Browser.oReport, oParameters);
				
			oPPSLibrary.PPS_CMG_UserRates();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
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
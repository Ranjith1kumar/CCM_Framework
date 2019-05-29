package contractManagement;

import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;

public class RateSheets 
{
	Browser oBrowser;
	RateSheetLibrary oRateSheetsLibrary;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;

	String className[] = this.getClass().getName().toUpperCase().split("\\."); // Package Name

	
	@BeforeMethod
	public void InitailSetup() 
	{
		oParameters = new Parameters();
		oParameters.SetParameters("CLASSNAME", className[1]);
	}

	
	@BeforeClass
	public void InitailSetup1() 
	{
		oClassParameters = new Parameters();
		oClassParameters.SetParameters("CLASSNAME", className[1]);
		oClassReport = new ClassReport(oClassParameters);
	}


	@Test
	public void CCM_VR_Soarian_RateSheet_CRUD_Terms() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		oBrowser = new Browser(oParameters);
		oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);

		oRateSheetsLibrary.RateSheet_CRUD_Terms_VR();
	}

	
	@Test
	public void CCM_VR_Soarian_RateSheet_Additive_RateType() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_Additive_RateType_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	
	@Test
	public void CCM_VR_Soarian_RateSheet_Search_and_CRUD_Rate_Sheet() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_Search_and_CRUD_RateSheet_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	

	@Test
	public void CCM_VR_Soarian_RateSheet_Dialysis_PPSRate_DRGUser_and_IRF_CMGPricer_RateType() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_Dialysis_PPSRate_DRGUser_and_IRFCMGPricer_RateType_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test
	public void CCM_VR_Soarian_RateSheet_By_RevenueCode_and_DosageQuantity_RateType() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_By_RevenueCode_and_DosageQuantity_RateType_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test
	public void CCM_VR_Soarian_RateSheet_RevenueCode_PerDay_or_PerCase_and_RUGUser() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_RevenueCode_PerDay_or_PerCase_and_RUGUser_RateType_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	

	@Test
	public void CCM_VR_Soarian_RateSheet_FeeSchedule_and_ProcedureGroup_RateType() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_FeeSchedule_and_ProcedureGroup_RateType_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test
	public void CCM_VR_Soarian_RateSheet_CRUD_StopLoss() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_CRUD_StopLoss_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}	
	
	
	@Test
	public void CCM_VR_Soarian_RateSheet_Tiered_RateType() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_Tiered_RateType_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
		
	
	@Test
	public void CCM_VR_Soarian_RateSheet_CRUD_Exclusion() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.rateSheet_CRUD_Exclusion_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}	
	
	
	@Test
	public void CCM_VR_Soarian_RateSheet_CRUD_Basic_Rate_Types() throws InterruptedException 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_Basic_Rate_Types_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test
	public void CCM_VR_Soarian_RateSheet_ViewOnly_Permission() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.rateSheet_ViewOnly_Permission_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test
	public void CCM_VR_Soarian_RateSheet_Manage_ServiceLine() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_Manage_ServiceLine_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}	
	
	
	@Test
	public void CCM_VR_Soarian_RateSheet_CRUD_Sections() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oRateSheetsLibrary = new RateSheetLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oRateSheetsLibrary.RateSheet_CRUD_Sections_VR();
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
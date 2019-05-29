package analytics;

import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;

public class Reports 
{
	Browser oBrowser;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;
	ReportsLibrary oReportsLibrary;

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
	public void CCM_VR_Soarian_Analytics_120_Day_Report() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oReportsLibrary.Reports120Day_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Analytics_Claim_Repricing_Report1() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oReportsLibrary.claimRepricingReport_VR1();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Analytics_Claim_Repricing_Report2() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oReportsLibrary.claimRepricingReport_VR2();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test
	public void CCM_VR_Soarian_Analytics_Contract_Error_Claim_Report() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oReportsLibrary.contractErrorClaimReport_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Analytics_Contract_Listing_Report() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oReportsLibrary.contractListingReport_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Analytics_RateSheet_Association_Report() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oReportsLibrary.rateSheetAssociationReport_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Analytics_Comparative_Analysis_Report() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oReportsLibrary.comparativeAnalysisReport_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Analytics_Qualification_Group_Usage_Report() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oReportsLibrary.qualificationGroupUsageReport_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Analytics_Rate_Sheet_Terms_Report() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oReportsLibrary = new ReportsLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oReportsLibrary.rateSheetTermsReport_VR();
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

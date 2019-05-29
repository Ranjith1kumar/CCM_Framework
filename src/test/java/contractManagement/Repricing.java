package contractManagement;

import org.testng.annotations.*;
import contractManagement.RepricingLibrary;
import libraries.*;
import libraries.Parameters;

public class Repricing 
{
	Browser oBrowser;
	RepricingLibrary orepricinglibrary;
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

	@Test()
	public void CCM_VR_Soarian_Repricing_Repricing() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
	
			oBrowser = new Browser(oParameters);
			orepricinglibrary = new RepricingLibrary(Browser.driver, Browser.oReport, oParameters);
	
			orepricinglibrary.Repricing_Group();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test()
	public void CCM_VR_Soarian_Repricing_View_Only_Permission() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
	
			oBrowser = new Browser(oParameters);
			orepricinglibrary = new RepricingLibrary(Browser.driver, Browser.oReport, oParameters);
	
			orepricinglibrary.repricing_viewPermission();
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

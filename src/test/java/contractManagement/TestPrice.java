
package contractManagement;

import org.testng.annotations.*;
import libraries.Browser;
import libraries.ClassReport;
import libraries.Parameters;

public class TestPrice 
{
	Browser oBrowser;
	TestPriceLibrary oTestPrice;
	Parameters oParameters;
	ClassReport oClassReport;

	String className[] = this.getClass().getName().toUpperCase().split("\\.");

	@BeforeTest
	public void InitailSetup() 
	{
		oParameters = new Parameters();
	}

	@BeforeClass
	public void InitailSetup1() 
	{
		oParameters.SetParameters("CLASSNAME", className[1]);
		oClassReport = new ClassReport(oParameters);
	}

	@Test()
	public void CCM_VR_Soarian_TestPrice_UB04_Claim_Form() 
	{
		try 
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oTestPrice = new TestPriceLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPrice.TestPrice_UB_04_Claim();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test()
	public void CCM_VR_Soarian_TestPrice_CMS_1500_Claim_Form() 
	{
		try 
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oTestPrice = new TestPriceLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPrice.TestPrice_CMS_1500_Claim();
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

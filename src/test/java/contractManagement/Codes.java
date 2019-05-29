package contractManagement;

import contractManagement.CodesLibrary;
import org.testng.annotations.*;
import libraries.Parameters;
import libraries.*;

public class Codes 
{
	Browser oBrowser;
	CodesLibrary ocodesLibrary;
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
	public void CCM_VR_Soarian_Codes_HCPCS_CPT() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			ocodesLibrary = new CodesLibrary(Browser.driver, Browser.oReport, oParameters);

			ocodesLibrary.CodesHcpcsVR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test()
	public void CCM_VR_Soarian_Codes_ICD() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			ocodesLibrary = new CodesLibrary(Browser.driver, Browser.oReport, oParameters);

			ocodesLibrary.codesIcdVr();
		} 
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test()
	public void CCM_VR_Soarian_Codes_Diagnosis() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			ocodesLibrary = new CodesLibrary(Browser.driver, Browser.oReport, oParameters);

			ocodesLibrary.codesDaignosisVr();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test()
	public void CCM_VR_Soarian_Codes_DRG() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			ocodesLibrary = new CodesLibrary(Browser.driver, Browser.oReport, oParameters);

			ocodesLibrary.codesDrgVr();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_Codes_View_Only_Permission() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			ocodesLibrary = new CodesLibrary(Browser.driver, Browser.oReport, oParameters);

			ocodesLibrary.codesviewOnlyPermission();
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

package contractManagement;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import libraries.Browser;
import libraries.ClassReport;
import libraries.Parameters;

public class FacilitySecurity 
{
	Browser oBrowser;
	FacilitySecurtiyLibrary oFacilitySecurityLibrary;
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
	public void CCM_VR_Soarian_Facility_Security()
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			oFacilitySecurityLibrary = new FacilitySecurtiyLibrary(Browser.driver, Browser.oReport, oParameters);

			
			
			oFacilitySecurityLibrary.();
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

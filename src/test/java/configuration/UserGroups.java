package configuration;

import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;

public class UserGroups 
{
	Browser oBrowser;
	UserGroupsLibrary ouserGroups;
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
	public void CCM_VR_Soarian_Configuration_UserGroups_View_Only_Permission() 
	{
		try 
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			ouserGroups = new UserGroupsLibrary(Browser.driver, Browser.oReport, oParameters);

			ouserGroups.ConfigureViewOnlyPermission();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test()
	public void CCM_VR_Soarian_Configuration_User_Groups() 
	{
		try 
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			ouserGroups = new UserGroupsLibrary(Browser.driver, Browser.oReport, oParameters);

			ouserGroups.configureUserGroups();
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
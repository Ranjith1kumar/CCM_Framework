package contractManagement;

import org.testng.annotations.*;
import contractManagement.ModelsLibrary;
import libraries.*;
import libraries.Parameters;

public class Models
{
	Browser oBrowser;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;
	ModelsLibrary oModels;

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
	public void CCM_VR_Soarian_Models_Search_And_Create_Model() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			oModels = new ModelsLibrary(Browser.driver, Browser.oReport, oParameters);

			oModels.searchAndCreate();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_Models_Creating_Copy_And_Versions_Of_Models() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			oModels = new ModelsLibrary(Browser.driver, Browser.oReport, oParameters);

			oModels.creatingCopy();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_Models_View_Only_Permission() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			oModels = new ModelsLibrary(Browser.driver, Browser.oReport, oParameters);

			oModels.viewOnlyPermission();
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
		oClassReport.AddClassResult(oParameters.GetParameters("TESTNAME"), oParameters.GetParameters("TestStatus"), oParameters.GetParameters("HTML_REPORT_NAME"));
		Browser.oAuraSetup.ALM_JIRA_Update(oParameters);
	}

	@AfterClass
	public void FinalSetup1() 
	{
		oClassReport.cleanup();
	}
}

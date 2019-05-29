package contractManagement;

import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;

public class QG 
{
	Browser oBrowser;
	QGLibrary oQGLibrary;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;

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
	public void CCM_VR_Soarian_QualificationGroups_Search_Qualification_Groups() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.QG_SearchQualificationGroups();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_QualificationGroups_Term_Exclusion_Qualification() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.term_exclusion_qualification();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_QualificationGroups_DRG_Formula_Expression() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.DRG_Formula_Expression();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_QualificationGroups_Section_Qualification() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.Section_Qualification();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_QualificationGroups_Revenue_Code_Qualification() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.Revenue_Code_Qualification();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_QualificationGroups_StopLoss_Formula_Expression() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.StopLoss_Formula_Expression();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_QualificationGroups_StopLoss_Qualification() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.StopLoss_Qualification();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_QualificationGroups_Service_Qualification() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.Service_Qualification();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_QualificationGroups_View_Only_Permission() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oQGLibrary = new QGLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oQGLibrary.QG_ViewOnlyPermission();
		}
		catch(Exception e)
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

package contractManagement;

import org.testng.annotations.*;
import contractManagement.SchedulesLibrary;
import libraries.*;
import libraries.Parameters;

public class Schedules 
{
	Browser oBrowser;
	SchedulesLibrary oSchedulesLibrary;
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
	public void CCM_VR_Soarian_Schedules_Fee_Schedules() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
			oBrowser = new Browser(oParameters);
			oSchedulesLibrary = new SchedulesLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oSchedulesLibrary.feeSchedules_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Schedules_Modifier_Schedules() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oSchedulesLibrary = new SchedulesLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oSchedulesLibrary.modifierSchedules_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Schedules_Group_Code_Rates_Schedules() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oSchedulesLibrary = new SchedulesLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oSchedulesLibrary.groupCodeRatesSchedules_VR();
			//oSchedulesLibrary.groupCodeRateScheduleVR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Schedules_Related_Procedure_Schedules() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oSchedulesLibrary = new SchedulesLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oSchedulesLibrary.relatedProcedureSchedules_VR();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test()
	public void CCM_VR_Soarian_Schedules_View_Only_Permission() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
			
			oBrowser = new Browser(oParameters);
			oSchedulesLibrary = new SchedulesLibrary(Browser.driver, Browser.oReport, oParameters);
	
			oSchedulesLibrary.schedulesViewOnlyVR();
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

package prerequisites;

import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;

public class Jenkins
{

	Browser oBrowser;
	JenkinsLibrary oJenkinsLibrary;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;
	CCMLibrary oCCMLibrary;

	String className[] = this.getClass().getName().toUpperCase().split("\\."); 

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
	public void Jenkins_Job_Creation() 
	{
		
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try
		{
			oBrowser = new Browser(oParameters);
			oJenkinsLibrary = new JenkinsLibrary(Browser.driver, Browser.oReport, oParameters);

			oJenkinsLibrary.create_jenkins_job();
			//oJenkinsLibrary.deleteJenkinsJobs("CCM DC");
			oJenkinsLibrary.trigger_Jenkins_Jobs("CCM");
			//oJenkinsLibrary.noOfJobs("CCM");
			//oJenkinsLibrary.deleteJenkinsBuild("CCM VR");
			//oJenkinsLibrary.updateJenkinsJob("ALL", "30 17 * * *");
			
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void Login() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try
		{
			oBrowser = new Browser(oParameters);
			oJenkinsLibrary = new JenkinsLibrary(Browser.driver, Browser.oReport, oParameters);

			oCCMLibrary.login("EDIT");			
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

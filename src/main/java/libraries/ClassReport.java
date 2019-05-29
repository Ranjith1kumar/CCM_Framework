package libraries;

import com.relevantcodes.extentreports.*;

public class ClassReport 
{
	protected ExtentReports classreport;
	protected ExtentTest classlogger;
	public Parameters oParameters;

	public ClassReport(Parameters oParameters) 
	{
		String ReportPath;

		this.oParameters = oParameters;

		if (oParameters.GetParameters("REPORT_PATH").equalsIgnoreCase("LOCAL"))
			ReportPath = "C://CCM//ExecutionReport//" + oParameters.GetParameters("ENVIRONMENT") + "//"	+ oParameters.GetParameters("CLASSNAME") + ".html";
		else
			ReportPath = "//10.171.246.232//ccm//ExecutionReport//" + oParameters.GetParameters("ENVIRONMENT") + "//" + oParameters.GetParameters("CLASSNAME") + ".html";

		classreport = new ExtentReports(ReportPath, true);
		classlogger = classreport.startTest(oParameters.GetParameters("CLASSNAME"));
	}

	public void cleanup() 
	{
		System.out.println("---------- ----------- ------------- ");
		System.out.println("---------- CLASS CLEAN UP START ------------- ");
		System.out.println("---------- ----------- ------------- ");

		try 
		{
			classreport.flush();
			classreport.endTest(classlogger);

			System.out.println("Class level Report Created");
		}
		catch (Exception exp) 
		{
			System.out.println("Cleanup Exception 1 : " + exp.getMessage());
			System.out.println("Class level report not created");
		}

		System.out.println("---------- ----------- ------------- ");
		System.out.println("---------- CLASS CLEAN UP END ------------- ");
		System.out.println("---------- ----------- ------------- ");
	}

	public void AddClassResult(String VRName, String StepResult, String PathofVRreport) 
	{
		System.out.println(VRName + " - " + StepResult);

		PathofVRreport = "Path of Individual VR : <a href='file:///" + PathofVRreport + "'>Link</a>";

		if (StepResult.toUpperCase().equalsIgnoreCase("Passed")) 
		{
			classlogger.log(LogStatus.PASS, VRName + " :  " + StepResult.toUpperCase(), PathofVRreport);
			classreport.flush();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("Failed")) 
		{
			classlogger.log(LogStatus.FAIL, VRName + " :  " + StepResult.toUpperCase(), PathofVRreport);
			classreport.flush();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("WARNING")) 
		{
			classlogger.log(LogStatus.WARNING, VRName + " :  " + StepResult.toUpperCase(), PathofVRreport);
			classreport.flush();
		}
		else if ("INFO|DONE".contains(StepResult)) 
		{
			classlogger.log(LogStatus.INFO, VRName + " :  " + StepResult.toUpperCase(), PathofVRreport);
			classreport.flush();
		}
	}
}

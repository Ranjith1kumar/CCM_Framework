package libraries;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SuiteReport 
{
	protected ExtentReports suitReport;
	protected ExtentTest suitLogger;
	public Parameters oParameters;

	public SuiteReport(Parameters oParameters, String suiteName) 
	{
		String ReportPath;

		this.oParameters = oParameters;

		if (oParameters.GetParameters("REPORT_PATH").equalsIgnoreCase("LOCAL"))
			ReportPath = "C://CCM//ExecutionReport//" + oParameters.GetParameters("ENVIRONMENT") + "//"	+ suiteName + "//" + suiteName + ".html";
		else
			ReportPath = "//10.171.246.232//ccm//ExecutionReport//" + oParameters.GetParameters("ENVIRONMENT") + "//" + oParameters.GetParameters("SUITNAME") + oParameters.GetParameters("CLASSNAME") + ".html";

		oParameters.SetParameters("CLASS_REPORT_PATH", ReportPath + oParameters.GetParameters("CLASSNAME"));
		suitReport = new ExtentReports(ReportPath, true);
		suitLogger = suitReport.startTest(suiteName);
	}

	public void cleanup() 
	{
		System.out.println("---------- ----------- ------------- ");
		System.out.println("---------- SUIT CLEAN UP START ------------- ");
		System.out.println("---------- ----------- ------------- ");

		try 
		{
			suitReport.flush();
			suitReport.endTest(suitLogger);

			System.out.println("Suit level Report Created");
		}
		catch (Exception exp) 
		{
			System.out.println("Cleanup Exception 1 : " + exp.getMessage());
			System.out.println("Class level report not created");
		}

		System.out.println("---------- ----------- ------------- ");
		System.out.println("---------- SUIT CLEAN UP END ------------- ");
		System.out.println("---------- ----------- ------------- ");
	}

	public void AddSuitResult(String ClassName, String StepResult, String PathofClassReport) 
	{
		System.out.println(ClassName + " - " + StepResult);

		PathofClassReport = "Path of Individual Class : <a href='file:///" + PathofClassReport + "'>Link</a>";

		if (StepResult.toUpperCase().equalsIgnoreCase("Passed")) 
		{
			suitLogger.log(LogStatus.PASS, ClassName + " :  " + StepResult.toUpperCase(), PathofClassReport);
			suitReport.flush();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("Failed")) 
		{
			suitLogger.log(LogStatus.FAIL, ClassName + " :  " + StepResult.toUpperCase(), PathofClassReport);
			suitReport.flush();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("WARNING")) 
		{
			suitLogger.log(LogStatus.WARNING, ClassName + " :  " + StepResult.toUpperCase(), PathofClassReport);
			suitReport.flush();
		}
		else if ("INFO|DONE".contains(StepResult)) 
		{
			suitLogger.log(LogStatus.INFO, ClassName + " :  " + StepResult.toUpperCase(), PathofClassReport);
			suitReport.flush();
		}
	}
}

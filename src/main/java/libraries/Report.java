package libraries;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.*;
import org.openqa.selenium.*;
import com.relevantcodes.extentreports.*;

public class Report 
{
	protected ExtentReports report;
	protected ExtentTest logger;
	protected WebDriver driver;
	protected Parameters oParameters;
	int passed = 0;
	int failed = 0;
	int warning = 0;
	String failedScenarios = "";
	String warningMessages = "";
	
	public Report(WebDriver driver, Parameters oParameters) 
	{
		this.driver = driver;
		this.oParameters = oParameters;

		initialize_reports();
	}

	public void cleanup() 
	{

		System.out.println("---------- ----------- ------------- ");
		System.out.println("---------- TEST CLEAN UP START ------------- ");
		System.out.println("---------- ----------- ------------- ");

		if (oParameters.GetParameters("CONTINUED_EXECUTION").toUpperCase().equalsIgnoreCase("YES"))
			AddStepResult("Completed Execution", "VR executed completely", "INFO");
		else if (oParameters.GetParameters("CONTINUED_EXECUTION").toUpperCase().equalsIgnoreCase("FAIL"))
			AddStepResult("Completed Execution", "VR executed completely and failures were found", "INFO");
		else if (oParameters.GetParameters("CONTINUED_EXECUTION").toUpperCase().equalsIgnoreCase("NO"))
			AddStepResult("Completed Execution", "VR execution aborted as fatal issues were found", "INFO");

		try 
		{
			report.flush();
			report.endTest(logger);

			System.out.println("VR level Report Created");

			oParameters.SetParameters("PASSED_STEPS", String.valueOf(passed));
			oParameters.SetParameters("FAILED_STEPS", String.valueOf(failed));
			oParameters.SetParameters("FAILED_SCENARIO_DISCRIPTION", "Environment : " + oParameters.GetParameters("ENVIRONMENT") + "\n" +  failedScenarios);
			oParameters.SetParameters("Warning", "Environment : " + oParameters.GetParameters("ENVIRONMENT") + "\n" + warningMessages);
			oParameters.SetParameters("JIRADESCRIPTION","Environment : " + oParameters.GetParameters("ENVIRONMENT") + "\n" + failedScenarios);
			
			AddClassStep();
		}
		catch (Exception exp) 
		{
			System.out.println("Cleanup Exception 1 : " + exp.getMessage());
			System.out.println("Vr level Report creation failed");
		}

		System.out.println("---------- ----------- ------------- ");
		System.out.println("---------- TEST CLEAN UP END ------------- ");
		System.out.println("---------- ----------- ------------- ");
	}

	public String getscreenshot() 
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String scrShotPath = oParameters.GetParameters("ScreenShotPath") + "/" + System.currentTimeMillis();

		try 
		{
			FileUtils.copyFile(scrFile, new File(scrShotPath));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return scrShotPath;
	}

	
	public void AddStepResult(String StepName, String StepDescription, String StepResult) 
	{
		System.out.println(StepName + " - " + StepResult + " - " + StepDescription);

		if (StepResult.toUpperCase().equalsIgnoreCase("PASS")) 
		{
			logger.log(LogStatus.PASS, StepDescription + " : " + StepResult, logger.addScreenCapture(getscreenshot()));

			passed++;

			// pushing step result to hash table
			oParameters.SetParameters(StepDescription, StepResult);

			report.flush();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("FAIL")) 
		{
			if (StepDescription.equalsIgnoreCase(oParameters.GetParameters("OLD_JIRA_DISCRIPTION")))
				logger.log(LogStatus.FAIL, StepDescription + " : " + StepResult + " : Failed with Known Issue",	logger.addScreenCapture(getscreenshot()));
			else
				logger.log(LogStatus.FAIL, StepDescription + " : " + StepResult,logger.addScreenCapture(getscreenshot()));

			failed++;

			if (failed == 1)
				failedScenarios = StepDescription;
			else if (failed > 1 && !failedScenarios.isEmpty())
				failedScenarios = failedScenarios + "\n" + StepDescription;

			// pushing step result to hash table
			oParameters.SetParameters(StepDescription, StepResult);

			report.flush();
			oParameters.SetParameters("CONTINUED_EXECUTION", "FAIL");
			
			//All scenarios in test price need to run
			if(!oParameters.GetParameters("CLASSNAME").equalsIgnoreCase("TestPriceScenarios"))
				throw new RuntimeException();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("FATAL")) 
		{
			logger.log(LogStatus.FATAL, StepDescription + " " + StepResult, logger.addScreenCapture(getscreenshot()));

			oParameters.SetParameters("Fatal", "Environment : " + oParameters.GetParameters("ENVIRONMENT") + "\\n" + StepDescription);
						
			// pushing step result to hash table
			oParameters.SetParameters(StepName + "_ " + StepDescription, StepResult);

			report.flush();
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			throw new RuntimeException();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("ERROR")) 
		{
			logger.log(LogStatus.ERROR, StepDescription, StepResult);

			// pushing step result to hash table
			oParameters.SetParameters(StepName + "_ " + StepDescription, StepResult);

			report.flush();
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			throw new RuntimeException();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("WARNING")) 
		{
			logger.log(LogStatus.WARNING, StepDescription, logger.addScreenCapture(getscreenshot()));

			warning++;

			if (warning == 1)
				warningMessages = StepDescription;
			else if (warning > 1 && !warningMessages.isEmpty())
				warningMessages = warningMessages + "\n" + StepDescription;
			
			// pushing step result to hash table
			oParameters.SetParameters(StepName + "_ " + StepDescription, StepResult);

			report.flush();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("UNKNOWN")) 
		{
			logger.log(LogStatus.FAIL, StepDescription, StepResult);
			
			oParameters.SetParameters("Exception", "Environment : " + oParameters.GetParameters("ENVIRONMENT") + "\\n" + StepDescription);
			
			// pushing step result to hash table
			oParameters.SetParameters(StepName + "_ " + StepDescription, StepResult);

			report.flush();
			oParameters.SetParameters("CONTINUED_Execution", "NO");
			throw new RuntimeException();
		}
		else if ("INFO|DONE".contains(StepResult)) 
		{
			logger.log(LogStatus.INFO, StepDescription, StepResult);

			// pushing step result to hash table
			oParameters.SetParameters(StepName + "_ " + StepDescription, StepResult);

			report.flush();
		}
		else if (StepResult.toUpperCase().equalsIgnoreCase("SCREENSHOT")) 
		{
			logger.log(LogStatus.INFO, StepDescription + " : " + StepResult, logger.addScreenCapture(getscreenshot()));

			// pushing step result to hash table
			oParameters.SetParameters(StepName + "_ ", StepResult);

			report.flush();
		}
	}

	private void initialize_reports() 
	{
		String reportPath;
		String vrname = oParameters.GetParameters("TESTNAME");
		String vrid = oParameters.GetParameters("VRID");

		if (oParameters.GetParameters("REPORT_PATH").equalsIgnoreCase("LOCAL"))
			reportPath = "C://CCM//ExecutionReport//" + oParameters.GetParameters("ENVIRONMENT") + "//"	+ oParameters.GetParameters("TENANT") + "//" + oParameters.GetParameters("CLASSNAME") + "//" + vrname + "//" + vrid + "-"+ System.currentTimeMillis();
		else
			reportPath = "//10.171.246.232//ccm//ExecutionReport//" + oParameters.GetParameters("ENVIRONMENT") + "//" + oParameters.GetParameters("TENANT") + "//"+ oParameters.GetParameters("CLASSNAME") + "//" + vrname + "//" + vrid + "-"+ System.currentTimeMillis();
			
		String reportName = reportPath + "//" + vrid + "-" + System.currentTimeMillis() + ".Html";

		System.out.println(reportName);
		
		oParameters.SetParameters("HTML_REPORT_NAME", reportName);

		boolean isFolderCreated = (new File(reportPath)).mkdir();

		if (isFolderCreated)
			System.out.println("Report Folder created");
		else
			System.out.println("Report Folder already exist");

		isFolderCreated = (new File(reportPath + File.separator + "Screenshots")).mkdir();

		if (isFolderCreated)
			System.out.println("Screenshots Folder created");
		else
			System.out.println("Screenshots Folder already exist");

		report = new ExtentReports(reportName, true);

		logger = report.startTest(vrname);

		System.out.println("Report Created");
		
		if (oParameters.GetParameters("HEADLESS").equalsIgnoreCase("YES"))
			AddStepResult("HEADLESS", "Ran in Headless Mode", "INFO");
		else
			System.out.println("VR Ran in normal Chrome");

		oParameters.SetParameters("ScreenShotPath", reportPath + "/Screenshots");
	}

	private void AddClassStep()
    {                   
          String status = logger.getRunStatus().toString().toUpperCase();
          
          if(status.equalsIgnoreCase("FAIL"))
                 status = "Failed";
          else if(status.equalsIgnoreCase("ERROR"))
                 status = "Failed";
          else if(status.equalsIgnoreCase("FATAL"))
                 status = "Failed";
          else if(status.equalsIgnoreCase("UNKNOWN"))
                 status = "Failed";
          else if(status.equalsIgnoreCase("PASS") && oParameters.GetParameters("Continued_Execution").toUpperCase().equalsIgnoreCase("YES") )
                 status = "Passed";
          else if(status.equalsIgnoreCase("WARNING") && oParameters.GetParameters("Continued_Execution").toUpperCase().equalsIgnoreCase("YES"))
                 status = "Passed";
          else if("INFO|DONE".equalsIgnoreCase(status) && oParameters.GetParameters("Continued_Execution").toUpperCase().equalsIgnoreCase("YES"))
                 status = "Passed";
          else
                 status = "Failed";
          
          oParameters.SetParameters("TestStatus", status);
    }

}

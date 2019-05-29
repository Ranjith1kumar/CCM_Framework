package libraries;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.*;

public class Browser
{
	public static WebDriver driver;
	public static Report oReport;
	protected Parameters oParameters;
	protected DBOperation oDBOperation;
	public static AuraSetup oAuraSetup;

	public Browser(Parameters oParameters) 
	{
		this.oParameters = oParameters;

		//DBOperation class object created to get input data from and write output data to DB
		oDBOperation = new DBOperation(oParameters);
		
		//AuraSetup class object created to get previous run data from and update run status of new run to ALM and create/update JIRA in JIRA board
		oAuraSetup = new AuraSetup(oParameters);

		//creating a local driver to launch a browser
		driver = local_createDriver(oParameters.GetParameters("BROWSER_TYPE"));

		//Report class object created to start a new HTML report
		oReport = new Report(driver, oParameters);

		//Created a string variable to store environment link
		String wikiURL = "";
		if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
			wikiURL = "https://revenuecyclecert.cernerworks.com/portal/login.jsp";
		if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT"))
			wikiURL = "https://rwcmdevcert.devcernerrevenuecycle.com/portal/login.jsp";
		if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVTEST"))
			wikiURL = "https://rwcmdevtest.devcernerrevenuecycle.com/portal/login.jsp";
		if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("JENKINS"))
			wikiURL = "http://10.171.246.232:8080";
		
		try 
		{
			//After launching browser it navigates to respective URL
			if(oParameters.GetParameters("ACCESSIBILITY_TEST").equalsIgnoreCase("YES"))
				driver.get("https://accessibility.northamerica.cerner.net:3005/api/registerRunDetail?teamName=REVCYCLE&component=CCM&trigger=Regression&run=start&mode=D&userId=SK054116");
		
			Thread.sleep(1000);
			
			driver.navigate().to(wikiURL);
			
			oReport.AddStepResult("URL Entered", oParameters.GetParameters("ENVIRONMENT") + " Environment URL Entered", "SCREENSHOT");
			
			//Storing wikiURL to hash table
			oParameters.SetParameters("URL", wikiURL);
		}
		catch (Exception e1) 
		{
			System.out.println("Exception caught : " + e1.getMessage());
		}
	}

	//Method to create a local driver based on browser type
	protected WebDriver local_createDriver(String string) 
	{
		
		WebDriver browser = null;

		//Setting up exeDriver path
		String relativePath = "C:/CCM/ExeDriver//";
		
		switch (string) 
		{
		//This option is to run headless chrome
		case "CHROME_HEADLESS":

			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			options.addArguments("window-size=1680x900");
			options.addArguments("disable-infobars");
			options.addArguments("--start-maximized");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			oParameters.SetParameters("downloadFilepath", "C:/CCM/Downloads");
			oParameters.SetParameters("SUPPORTINGFILE_PATH", "C:/CCM/SupportingFiles/");
			oParameters.SetParameters("AUTOIT_FILES_PATH", "C:/CCM/AutoIt/");
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", oParameters.GetParameters("downloadFilepath"));
			options.setExperimentalOption("prefs", chromePrefs);

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			
			System.setProperty("webdriver.chrome.driver", relativePath + "chromedriver.exe");
			browser = new ChromeDriver(cap);
			break;

		//This option is to run normal chrome	
		case "CHROME":
			
			ChromeOptions cOptions = new ChromeOptions();
			cOptions.addArguments("disable-infobars");
			cOptions.addArguments("--start-maximized");
			cOptions.addArguments("disable-gpu");
			
			if(oParameters.GetParameters("ACCESSIBILITY_TEST").equalsIgnoreCase("YES"))
				cOptions.addArguments("load-extension=C:/CCM/a11yChromeExtn-master");
			
			Map<String, Object> prefs1 = new HashMap<String, Object>();
			prefs1.put("credentials_enable_service", false);
			prefs1.put("profile.password_manager_enabled", false);
			cOptions.setExperimentalOption("prefs", prefs1);
			System.setProperty("webdriver.chrome.driver", relativePath + "chromedriver.exe");
			oParameters.SetParameters("downloadFilepath", "C:/CCM/Downloads");
			HashMap<String, Object> chromePrefs1 = new HashMap<String, Object>();
			chromePrefs1.put("profile.default_content_settings.popups", 0);
			chromePrefs1.put("download.default_directory", oParameters.GetParameters("downloadFilepath"));
			oParameters.SetParameters("SUPPORTINGFILE_PATH", "C:/CCM/SupportingFiles/");
			oParameters.SetParameters("AUTOIT_FILES_PATH", "C:/CCM/AutoIt/");
			cOptions.setExperimentalOption("prefs", chromePrefs1);
			
			DesiredCapabilities cap1 = DesiredCapabilities.chrome();
			cap1.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap1.setCapability(ChromeOptions.CAPABILITY, cOptions);
			cap1.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.IGNORE);
			browser = new ChromeDriver(cap1);
			break;

		case "IE":
			System.setProperty("webdriver.ie.driver", relativePath + "IEDriverServer.exe");
			browser = new InternetExplorerDriver();
			break;
		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver", relativePath + "geckodriver.exe");
			browser = new FirefoxDriver();
			break;
		}
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return browser;
	}

	public void cleanup() 
	{
		try 
		{
			oReport.cleanup();
			oDBOperation.writeData();
			driver.quit();
		}
		catch (Exception exp) 
		{
			System.out.println("Cleanup Exception : " + exp.getMessage());
		}
	}
}
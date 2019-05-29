package prerequisites;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import libraries.CCMLibrary;
import libraries.ExcelData;
import libraries.Parameters;
import libraries.Report;

public class JenkinsLibrary extends CCMLibrary
{
	ExcelData oExcelData = new ExcelData(oParameters);
	int count = 0;
	
	public JenkinsLibrary(WebDriver driver, Report oReport, Parameters oParameters) 
	{
		super(driver, oReport, oParameters);
	}
	
	By newItemLink = By.xpath("//a[contains(.,'New Item')]");
	
	By itemNameTextBox = By.xpath("//input[@id='name']");
	
	By freeStyleProjectOption = By.xpath("//span[contains(.,'Freestyle project')]");
	
	By addNewItemOKButton = By.xpath("//button[@id='ok-button']");
	
	By gitRadioButton = By.xpath("//label[contains(.,'Git')]//input[@type='radio']");
	
	By repoURLTextBox = By.xpath("//input[@name='_.url']");
	
	By buildPeriodicallyCheckBox = By.xpath("//input[@name='hudson-triggers-TimerTrigger']");
	
	By scheduleTimeTextArea = By.xpath("//textarea[@name='_.spec']");
	
	By addBuildStepButton = By.xpath("//button[contains(.,'Add build step')]");
	
	By invokeTopLevenMavenProjectOption = By.xpath("//a[contains(.,'Invoke top-level Maven targets')]");
	
	By goalsTextBox = By.xpath("//input[@id='textarea._.targets']");
	
	By saveButton = By.xpath("//button[contains(.,'Save')]");
	
	By duplicateJobNameMsg = By.xpath("//div[@id='itemname-invalid']");
	
	By postBuildActionButton = By.xpath("//button[contains(.,'Add post-build action')]");
	
	By jenkinsTextFinderOption = By.xpath("//a[contains(.,'Jenkins Text Finder')]");
	
	By consoleOutputCheckbox = By.xpath("//input[@name='_.alsoCheckConsoleOutput']");
	
	By regularExpressionTextBox = By.xpath("//input[@name='_.regexp']");
	
	By succeedIfFoundCheckBox = By.xpath("//input[@name='_.succeedIfFound']");
	
	By backToDaskBoardLink = By.xpath("//a[contains(.,'Back to Dashboard')]");
	
	By addTimeStampCheckbox = By.xpath("//input[@name='hudson-plugins-timestamper-TimestamperBuildWrapper']");
	
	public void create_jenkins_job()
	{
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "Jenkins.xlsx", "JENKINS_VBR", "");
		int rowCount = oExcelData.getRowCount1("JENKINS_VBR", "C:\\CCM\\SupportingFiles\\Jenkins.xlsx");
		System.out.println(rowCount);
		
		for(int i=1; i<=rowCount-1; i++)
		{
			waitFor(addNewItemOKButton, "Add New Element");
			//fixed_wait_time(3);
			//To click on Add New item link
			click_button("New Item Link", newItemLink);
			
			//To enter text in Project name text box
			enter_text_value("Project Name", itemNameTextBox, oParameters.GetParameters("JENKINS_JOB_NAME" + i));
			performKeyBoardAction("TAB");
			
			fixed_wait_time(2);
			
			if(IsDisplayed("Duplicate Job Name Message", duplicateJobNameMsg))
				enter_text_value("Project Name", itemNameTextBox, oParameters.GetParameters("JENKINS_JOB_NAME" + i));
			
			//To click on free style project option
			click_button("Free Style Project Option", freeStyleProjectOption);
			//To click on OK button
			click_button("Add New Item OK Button", addNewItemOKButton);
			
			//To select Git checkbox
			click_button("Git Radio Button", gitRadioButton);
			//To enter Git Repo URL in Repo textbox
			enter_text_value("REPO URL", repoURLTextBox, oParameters.GetParameters("REPO_URL" + i));
			//performKeyBoardAction("ENTER");
			//To select Build periodically checkbox
			javaScriptExecutor("Build Periodically CheckBox", buildPeriodicallyCheckBox);
			//To enter schedule time in textbox
			enter_text_value("Schedule Time", scheduleTimeTextArea, oParameters.GetParameters("SCHEDULE_TIME" + i) );
			//To scroll to add build step button
			scroll("", addBuildStepButton);
			//To select add Timestamp checkbox
			click_button("Add Timestamp Checkbox", addTimeStampCheckbox);
			//To click on add build step button
			dclick_button("Add Build Step Button", addBuildStepButton);
			//to select Invoke top level maven project from drop down
			click_button("Invoke Top Leven Maven Project Option", invokeTopLevenMavenProjectOption);
			//To enter Goals command in Goals textbox
			enter_text_value("Goals", goalsTextBox, oParameters.GetParameters("GOALS"+ i));
			//To scroll to post build action button
			scroll("", postBuildActionButton);
			//To click on Post build action button
			dclick_button("Post Build Action Button", postBuildActionButton);
			//To select jenkins text finder option from dropdown
			click_button("Jenkins Text Finder Option", jenkinsTextFinderOption);
			//To select console output checkbox
			click_button("Console Output Checkbox", consoleOutputCheckbox);
			//To enter regular expression in Regular expression text box
			enter_text_value("Regular Expression TextBox", regularExpressionTextBox, oParameters.GetParameters("REGULAR_EXPRESSION" + i));
			scroll("", succeedIfFoundCheckBox);
			//To select succeed if found checkbox
			click_button("Succeed If Found CheckBox", succeedIfFoundCheckBox);
			
			//To click on Save button
			click_button("Save Button", saveButton);
			
			count++;
			
			if(IsDisplayed("Back to Project link", backToDaskBoardLink))
				click_button("Back to dashboard link", backToDaskBoardLink);
		}	
		
		System.out.println(count);
	}
	
	By noOfJenkinsJobs = By.xpath("//table[@id='projectstatus']/tbody/tr");
	
	By deleteProjectLink = By.xpath("//a[contains(.,'Delete Project')]");
	
	public void deleteJenkinsJobs(String viewName)
	{
		By viewNameXpath = By.xpath("//*[@id=\"projectstatus-tabBar\"]//a[contains(.,'" + viewName + "')]");
		
		click_button("View Name", viewNameXpath);
		
		int count = noOfRows(noOfJenkinsJobs);
		System.out.println(count);
		
		for(int i=2; i<=count; i++)
		{
			//To click on View
			click_button("View Name", viewNameXpath);
			
			By xpath = By.xpath("//table[@id='projectstatus']/tbody/tr[2]/td/a");
			
			dclick_button( i+ " job", xpath);
			fixed_wait_time(2);
			click_button("Delete Project Link", deleteProjectLink);
			
			Alert alrt = driver.switchTo().alert();
			alrt.accept();
			
			
			if(IsDisplayed("Back to Project link", backToDaskBoardLink))
				click_button("Back to dashboard link", backToDaskBoardLink);
			
		}
	}
	
	By buildNowLink = By.xpath("//a[contains(.,'Build Now')]");
	
	
	
	public void trigger_Jenkins_Jobs(String viewName)
	{
		By viewNameXpath = By.xpath("//*[@id=\"projectstatus-tabBar\"]//a[contains(.,'" + viewName + "')]");
		
		click_button("View Name", viewNameXpath);
		
		int count = noOfRows(noOfJenkinsJobs);
		System.out.println(count);
		
		for(int i=2; i<=count; i++)
		{
			//To click on View
			click_button("View Name", viewNameXpath);
			
			By xpath = By.xpath("//table[@id='projectstatus']/tbody/tr["+ i +"]/td[3]/a[@class='model-link inside']");
			
			waitFor(xpath, i + " Record");
			
			dclick_button(i +" job", xpath);
			fixed_wait_time(2);
			click_button("Build Now Link", buildNowLink);
			
			count++;
			
			if(IsDisplayed("Back to Project link", backToDaskBoardLink))
				click_button("Back to dashboard link", backToDaskBoardLink);
		}
	}
	
	
	public void noOfJobs(String viewName)
	{
		By viewNameXpath = By.xpath("//*[@id=\"projectstatus-tabBar\"]//a[contains(.,'" + viewName + "')]");
		
		click_button("View Name", viewNameXpath);
		
		int count = noOfRows(noOfJenkinsJobs);
		System.out.println(count);
	}
	
	//
	
	By deleteBuildLink = By.xpath("//a[contains(.,'Delete Build')]");
	
	By deleteBuildYesButton = By.xpath("//button[contains(.,'Yes')]");
	
	public void deleteJenkinsBuild(String viewName) 
	{
		By viewNameXpath = By.xpath("//*[@id=\"projectstatus-tabBar\"]//a[contains(.,'" + viewName + "')]");
		
		click_button("View Name", viewNameXpath);
		
		int count = noOfRows(noOfJenkinsJobs);
		System.out.println(count);
		
		for(int i=2; i<count; i++)
		{
			//To click on View
			click_button("View Name", viewNameXpath);
			
			By jobNameXpath = By.xpath("//table[@id='projectstatus']/tbody/tr["+ i +"]/td[3]/a[@class='model-link inside']");
			
			waitFor(jobNameXpath, i + " Record");
			
			dclick_button(i +" job", jobNameXpath);
			fixed_wait_time(2);
			
			By buildList = By.xpath("//div[@id='buildHistory']//table[@class='pane stripped']/tbody/tr");
			int noOfBuilds = noOfRows(buildList);
			
			if(noOfBuilds > 2)
			{
				for(int j=2; j<noOfBuilds; j++)
				{
					By buildXpath = By.xpath("//div[@id='buildHistory']//table[@class='pane stripped']/tbody/tr[2]//a");
					
					click_button(j + " Build", buildXpath);
					
					click_button("Delete Build Link", deleteBuildLink);
					
					click_button("Delete Build Yes Button", deleteBuildYesButton);
				}
			}
				
			if(IsDisplayed("Back to Project link", backToDaskBoardLink))
				click_button("Back to dashboard link", backToDaskBoardLink);
		}
	}

	By configureLink = By.xpath("//a[contains(.,'Configure')]");
	
	public void updateJenkinsJob(String viewName, String buildTime) 
	{
		//By viewNameXpath = By.xpath("//*[@id=\"projectstatus-tabBar\"]//a[contains(.,'" + viewName + "')]");
	
		//click_button("View Name", viewNameXpath);
		
		int count = noOfRows(noOfJenkinsJobs);
		System.out.println(count);
		
		for(int i=15; i<=count; i++)
		{
			//To click on View
			//click_button("View Name", viewNameXpath);
			
			By jobNameXpath = By.xpath("//table[@id='projectstatus']/tbody/tr["+ i +"]/td[3]/a[@class='model-link inside']");
				
			waitFor(jobNameXpath, i + " Record");
				
			dclick_button(i +" job", jobNameXpath);
			fixed_wait_time(2);
			
			click_button("Configure Link", configureLink);
			
			fixed_wait_time(3);
			
			if(!IsDisplayed("Schedule Textbox", scheduleTimeTextArea))
				//To select Build periodically checkbox
				javaScriptExecutor("Build Periodically CheckBox", buildPeriodicallyCheckBox);
			
			enter_text_value("Schedule Time", scheduleTimeTextArea, buildTime);
					
			//To click on Save button
			click_button("Save Button", saveButton);
			
			if(IsDisplayed("Back to Project link", backToDaskBoardLink))
				click_button("Back to dashboard link", backToDaskBoardLink);
		}
	}
}

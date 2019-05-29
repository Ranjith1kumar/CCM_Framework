package contractManagement;

import java.util.List;
import org.openqa.selenium.*;
import analytics.ReportsLibrary;
import libraries.*;

/**
 * @author C16137
 *
 */

public class RepricingLibrary extends CCMLibrary 
{
	ReportsLibrary oReportsLibrary = new ReportsLibrary(driver, oReport, oParameters);
	
	public RepricingLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}
	
	//First VR(RepricingGroup) starts.

	
	By Analytics=By.xpath("//div[@id='portal-main-nav']//li[@class='ng-scope']//a[text()='Analytics']");
	
	By Report_to_begin=By.xpath("//div[@id='reportsSection']//div[@class='second-line ng-scope ng-binding']");
	
	public void navigateAnalyticsplugin()               
	{
		navigate_to("Analytics_plugin","Select a Report to begin",Analytics,Report_to_begin);
	}

	
	By AllReport=By.xpath("//div[contains(@class, 'margin-b-10')]//a");
	
	By select_report=By.xpath("//div[contains(@class,'document-title-bar margin-b-10')]//span[contains(.,'Select a report')]/..");
	
	By claim_repricing_report=By.xpath("//div[contains(@class, 'margin-b-10')]//a[contains(.,'Claim Repricing Report')]");
	
	By Create_Report=By.xpath("//button[contains(text(),'Create Report')]");
	
	public void selectReport()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("Select a Report", select_report))
		{
			oReport.AddStepResult("Analytics plugin", "Navigated to Analytics suite and verified that 'Select Report' dropdown is displayed", "PASS");
			click_button("Select a report",select_report);
			
			oParameters.SetParameters("ClaimRepricingReport", get_text(claim_repricing_report));
			List<WebElement> Reports = convertToWebElements(AllReport);
			
			for(int i=0;i<=Reports.size();i++)
			{
				if(oParameters.GetParameters("ClaimRepricingReport").equalsIgnoreCase(Reports.get(i).getText()))
				{	
					oReport.AddStepResult("Claim Repricing Report", "Clicked on 'select a report' and verified that claim repricing report is dispalyed", "PASS");
					click_button("Claim Repricing_report",claim_repricing_report);
					
					if(IsElementDisplayed("create report", Create_Report))
						oReport.AddStepResult("Clicked on claim repricing report and verified that", " claim repricing report page is displayed with default filter", "PASS");
					else
						oReport.AddStepResult("Clicked on claim repricing report and verified that", " claim repricing report page is not displayed with default filter", "FAIL");
				break;
				}
			}
		}	
	}

		
	By records=By.xpath("//div[@id='title-bar']//span[@class='small-header ng-binding']/..");
	
	By RepriceClaims_dropdown=By.xpath("//button[@class='btn btn-primary btn-create-report dropdown-toggle']");
	
	By Create_Reprice_Job=By.xpath("//div[contains(text(),'Create Repricing Job')]");
	
	By check_allUserJob=By.xpath("//input[@id='showAllRepriceUserJobs']");
	
	By All_dropdown_button=By.xpath("//div[@id='repricingView']//div[@class='input-group-btn input-group-btn-sm']/a");
	
	
	public void repricingPlugin()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Check all user job",check_allUserJob);
		
		if(IsElementSelected("Show job for All Users", check_allUserJob))
		{
			oReport.AddStepResult("Check Box", "'Show job for All Users' check box is Selected", "PASS");
			
			if(IsElementDisplayed("ALL Users", firstElement))
				oReport.AddStepResult("All Users", " Checked all Users job and verified that all users job is displayed", "PASS");
			else
				oReport.AddStepResult("All Users", " Checked all Users job and verified that  all users job is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Check Box", "'Show job for All Users' check box is not Selected", "FAIL");
	}
	
	
	By In_process=By.xpath("//div[@id='repricingView']//a[contains(.,'In-Process')]");
	
	By In_Process_Status=By.xpath("//button[@id='change-repricing-status']");
	
	By firstElement=By.xpath("//ul[@id='repricing-select']/li[1]");
	
	By completed_successfully_status=By.xpath("//button[@id='repricing-status-completed']");
	
	By completedWithErrors_Status=By.xpath("//button[@id='repricing-status']");
	
	By respectivePage=By.xpath("//div[@class='portal-action-bar']//div[@class='col-lg-12 col-md-12 col-sm-12 xl-header ng-binding']");
	
	By firstElementName=By.xpath("//ul[@id='repricing-select']/li[1]/ng-include/div[contains(@class,'col-lg-11 col-md-11 ng-scope ng-binding bold')]");
	
	
	//This method is used to validate repricing dropdown.
	public void repricingDropDown(By dropDownValue, String dropDownValueName, By repricingStatus, String repricingStatusName)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Repricing dropdown",All_dropdown_button);
		
		if(IsDisplayed("Repricing List", dropDownValue))
		{
			waitFor(dropDownValue, dropDownValueName);
			oReport.AddStepResult("Clicked particular Repricing", "Clicked on repricing Drop down and verified that repricing lists are displayed", "PASS");
			click_button("Respective Repricing",dropDownValue);
			
			if(IsDisplayed("First Repricing", firstElement))
			{
				click_button("First Element",firstElementName);
				
				oReport.AddStepResult("First Repricing status", " Selected "+ get_field_value("DropDown Value", dropDownValue) +" from dropdown and verified that jobs with repricing status is displayed", "PASS");
				oParameters.SetParameters("FirstElementName", get_text(firstElementName));
				
				waitFor(repricingStatus, "Repricing Status");
				oParameters.SetParameters("openedPage", get_text(respectivePage));
		
				if(oParameters.GetParameters("FirstElementName").equals(oParameters.GetParameters("openedPage")))
				{	
					fixed_wait_time(4);
					waitFor(respectivePage, "Respective Page");
					oReport.AddStepResult("Respective Repricing", "Selected '"+dropDownValueName+"' repricing from dropdown and verified that jobs with respective repricing status is displayed", "PASS");
				}
				else
					oReport.AddStepResult("Respective Repricing", "Selected '"+dropDownValueName+"' repricing from dropdown and verified that jobs with respective repricing status is not displayed", "FAIL");
			}	
			else if(IsDisplayed("No Items Were Found", noItemsWereFound))
				oReport.AddStepResult("First Repricing status", " Selected "+get_field_value("DropDown Value", dropDownValue)+" from dropdown and verified that 'No items were found ' message is displayed", "PASS");
				//This method is used to select a completed with errors jobs.
				//repricingDropDown(completedWithErrors, "Completed With Errors",completedWithErrors_Status, "completed With Errors_Status");
		}
		else
			oReport.AddStepResult("Clicked particular Repricing", "Clicked on repricing Drop down and verified that repricing lists are not displayed", "FAIL");
	}
	
	
	By ViewClaimRepricingReport=By.xpath("//div[@class='pull-right inline-flex margin-b-15']/div[1]/input[@ng-click='viewclaimRepricingDetails(r)']");
	
	By TotalSubmitted=By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 field-name pad-l-0 ng-binding'][text()='Total submitted']/..//div[@class='col-lg-4 col-md-4 col-sm-4 bold number ng-binding']");
	
	By ClaimRepricingTableCount=By.xpath("//div[@id='report-table-parent']//table[@class='data-table-container']//table[contains(@class,'table table-condensed table-striped table-responsive')]//tbody//tr");
	
	By NextButtonValidation=By.xpath("//div[@id='report-table-parent']//ul[@class='pull-right rw-pager ng-isolate-scope']//li[text()='Next']");
	
	By PageValidation=By.xpath("//div[@id='report-table-parent']/div[3]/table/tbody/tr/td/ul/li/a");
	
	public void viewClaimRepricingReport()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(2);
		waitFor(ViewClaimRepricingReport, "View Claim Repricing Report");
		oParameters.SetParameters("TotalSubmittedValue", get_text(TotalSubmitted));
		
		javaScriptExecutor("View Claim Repricing Report", ViewClaimRepricingReport);
		
		if(oParameters.GetParameters("TotalSubmittedValue").equals(get_text(records).replaceAll("Records: ", "")))
			oReport.AddStepResult("Report Plugin", "Clicked on View claim repricing report and verified that page navigates to report plugin and Total submitted is "+oParameters.GetParameters("TotalSubmittedValue")+" records is displayed  in 'Claim Repricing Report Page'", "PASS");
		else
			oReport.AddStepResult("Report Plugin", "Clicked on View claim repricing report and verified that page navigates to report plugin and Total submitted is "+oParameters.GetParameters("TotalSubmittedValue")+" records is not displayed  in 'Claim Repricing Report Page'", "FAIL");
	}

	
	By contract_mangement=By.xpath("//div[@id='portal-main-nav']//a[contains(.,'Contract Management')]");
	
	By Repricing_plugin=By.xpath("//a[contains(.,'Repricing')]");
	
	
	public void RepricingPlugin()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Contract Mangement",contract_mangement);
		
		if(IsElementDisplayed("Repricing", Repricing_plugin) && IsElementDisplayed("Repricing Dropdown", allDropDown))
			oReport.AddStepResult("Contract Management", "Clicked on View Claim Repricing report and verified that  page navigates to report plugin and again navigated back to Repricing Plugin and verified that Repricing plugin page is displayed and All the Repricing plugin page Feilds are getting enabled", "PASS");
		else
		{
			oReport.AddStepResult("Contract Management", "Clicked on View Claim Repricing report and verified that  page navigates to report plugin and again navigated back to Repricing Plugin and verified that Repricing plugin page is displayed and All the Repricing plugin page Feilds are not getting enabled", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}	
	}

	
	By dailog_yes_button=By.xpath(".//*[@id='dialog_buttons']//input[contains(@value,'Yes')]");
	
	By Delete=By.xpath("//ul[@id='repricing-select']//li[1]//i[@class='left fa fa-minus-square']");
	
	
	
	public void hoverOverRepriceJob()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		repricingDropDown(completedSuccessfully, "Completed Successfully", completed_successfully_status, "CompletedSuccessfully_status");
		
		click_button("Show Jobs For All Users", showJobsForAllUsers);
		
		if(IsElementDisplayed("First Element", firstElement))
		{
			oReport.AddStepResult("First Element", "Clicked on contract management and verified that first repricing job is displayed", "PASS");
			mouse_hover("First Element", firstElement);
			fixed_wait_time(2);
			dclick_button("Delete", Delete);
			fixed_wait_time(2);
			click_button("Dailog YES Button",dailog_yes_button);
			
			oReport.AddStepResult("Delete Reprice Job", " Clicked on Yes button and verified that respective job is deleted", "PASS");
			/*if(IsElementDisplayed("Respective page", FirstRepricing))
			{	
				waitFor(FirstRepricing, "First Repricing");
				oReport.AddStepResult("Delete Yes Button", " Clicked on Yes button and verified that respective job is deleted", "PASS");
			}
			else
				oReport.AddStepResult("Delete Yes Button", " Clicked on Yes button and verified that respective job is not deleted", "FAIL");
	*/	}
		else
			oReport.AddStepResult("First Element", "Clicked on contract management and verified that first repricing job is not displayed", "PASS");
	}	
	
	
	By Reprising_job_completed=By.xpath("//span[contains(text(),'Repricing Job')]");
	
	By reprisingStatus=By.xpath("//button[@id='repricing-status-completed']");
	
	By reprisingStatusErrors=By.xpath("//button[@id='repricing-status']");
	
	By CreatedDate=By.xpath("//div[@id='repricing-right']//div[@class='row']//div[contains(text(),'Created By:')]");
	
	By CompletedDate=By.xpath("//div[@id='repricing-right']//div[@class='row']//div[contains(text(),'Completed:')]");
	
	By submittedDate=By.xpath("//div[@id='repricing-right']//div[@class='row']//div[contains(text(),'Submitted:')]");
	
	By refresh=By.xpath("//span[contains(text(),'Refresh')]");
	
	By InProcess=By.xpath("//button[@id='change-repricing-status']//div[contains(.,'In Process')]");
	
	public void refreshLink() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		repricingDropDown(In_process, "In_process", In_Process_Status, "In_Process_Status");
		
		for(int i=0;i<=9;i++)
		{
			dclick_button("Refresh",refresh);
			fixed_wait_time(2);
			
			if(IsElementDisplayed("Completed Status", reprisingStatus) || IsElementDisplayed("Completed with Errors", reprisingStatusErrors))
			{
				oReport.AddStepResult("Refresh button", "Clicked on refresh button and verified that job status  changes from In-Process to completed", "PASS");
				
				if(IsDisplayed("Completed Status", reprisingStatus))
				{		
					click_button("Completed Successfully",reprisingStatus);
					fixed_wait_time(3);
					
					if(IsElementDisplayed("Create Date", CreatedDate) && IsElementDisplayed("Submitted", submittedDate) && IsElementDisplayed("Completed Date", CompletedDate))
						oReport.AddStepResult("Created_Date,Submitted_Date,Completed_Date", " Created_Date and Submitted_Date and Completed_Date is displayed ", "PASS");
					else
						oReport.AddStepResult("Created_Date,Submitted_Date,Completed_Date", " Created_Date and Submitted_Date and Completed_Date is not displayed ", "FAIL");
				}
				else
				{
					click_button("Completed with Errors",reprisingStatusErrors);
					fixed_wait_time(3);
					
					if(IsElementDisplayed("Create Date", CreatedDate) && IsElementDisplayed("Submitted", submittedDate) && IsElementDisplayed("Completed Date", CompletedDate))
						oReport.AddStepResult("Created_Date,Submitted_Date,Completed_Date", "Created_Date and Submitted_Date and Completed_Date is displayed ", "PASS");
					else
						oReport.AddStepResult("Created_Date,Submitted_Date,Completed_Date", " Created_Date and Submitted_Date and Completed_Date is not displayed ", "FAIL");
				}
				break;
			}
			else
				System.out.println("Refreshing");
		}
		if(IsElementDisplayed("Completed Status", reprisingStatus) || IsElementDisplayed("Completed with Errors", reprisingStatusErrors))
			System.out.println("Passed");
		else
			oReport.AddStepResult("Refresh button", "Clicked on refresh button and verified that job status not changes from In-Process to completed", "FAIL");
	}
	
	
	
	public void RepriceJobFilter()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Clear Filters Button", oReportsLibrary.clearFiltersButton))
			click_button("Clear Filters Button", oReportsLibrary.clearFiltersButton);
		
		//Claim Histroy
		oReportsLibrary.addFilter("Claim History");
		click_button("History Current Option", oReportsLibrary.historyCurrentElement);
		
		//Repricing Status
		oReportsLibrary.addFilter("Reprice Status");
		oReportsLibrary.selectMultipleCheckBoxes("1 - Success");
		
		/*//Adjustment
		oReportsLibrary.addFilter("Adjustment");
		click_button("Adjustment Operator Button", oReportsLibrary.billTypeOperators);
		click_button("Greater Than or Equal to Button", oReportsLibrary.adjustmentOperatorElementLessthan);		
		enter_text_value("Filters Input Text Box", oReportsLibrary.filtersTextBox,"30");
		performKeyBoardAction("ENTER");		
		*/
		oReportsLibrary.createReport();		
		
		oParameters.SetParameters("TotalRecords", get_field_value("Total No records",records).replaceAll("Records: ",""));
		
		if(Integer.parseInt(oParameters.GetParameters("TotalRecords")) > 0)
			oReport.AddStepResult("Total Numbers of Records", "Clicked on create report and verified that respective records are displayed and repricing claim drop down is enabled", "PASS");
		else
			oReport.AddStepResult("Total Numbers of Records", "Clicked on create report and verified that respective records are not displayed and repricing claim drop down is disabled", "FAIL");
	}
	
	public void createRepriceJob()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Create Report DropDown", oReportsLibrary.createReportDD);
		clickAddButton("Reprice Button", "Create reprice Job Tab", oReportsLibrary.repriceClaimsElement1, Create_Reprice_Job);
		
		enter_text_value("Reprice Job Text Box", oReportsLibrary.repriceJobTexBox, oParameters.GetParameters("Reprice_JOb_Name"));
		clickSaveButton("Reprice Save Button", "'Create reprice Job Tab'", oReportsLibrary.saveReprice, Create_Reprice_Job);
		click_button("Click to View the Repriced Job Now", oReportsLibrary.createdRepriceJobLink);
	}
	
	
	//This method is used to execute Repricing Group VR.
	public void Repricing_Group()
	{
		login("EDIT");
		changePricingEngine();
		
		navigateAnalyticsplugin();
		selectReport();
		RepriceJobFilter();
		createRepriceJob();
		repricingPlugin();
		
		repricingDropDown(completedWithErrors, "Completed With Errors", completedWithErrors_Status, "Completed With Errors_Status");
		repricingDropDown(completedSuccessfully, "Completed Successfully", completed_successfully_status, "CompletedSuccessfully_status");
		viewClaimRepricingReport();
		RepricingPlugin();
		
		refreshLink();
		hoverOverRepriceJob();
		logout();
	}
	
	
	//Repricing 2 VR  -Repricing -View only permission.
	
	
	By SearchForRepricing=By.xpath("//div[@id='repricing-right']//div[@class='second-line ng-scope ng-binding']");
	
	//This Method is use to navigating For Repricing plugin.
	public void navigateRepricing_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		navigate_to("Repricing", "Search for a Reprice job to begin", Repricing_plugin, SearchForRepricing);
	}
	
	
	By showJobsForAllUsers=By.xpath("//input[@id='showAllRepriceUserJobs']");
	
	By FirstRepricing=By.xpath("//div[@id='repricingView']//ul[@id='repricing-select']//li[1]");
	
	//This Method is use to Select a checkbox of All Users.
	public void allUsersCheckBox_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("ALL Users Check Box", showJobsForAllUsers))
		{
			click_button("ALL Users Check Box",showJobsForAllUsers);
			oReport.AddStepResult("Click Search Engine", "Clicked on search Engine when status is 'ALL', ALL Repricing jobs are displayed", "PASS");
				
			if(IsElementDisplayed("Repricing First Element", FirstRepricing))
				oReport.AddStepResult("Search Engine", "When status is ALL, ALL Repricing jobs are displayed", "PASS");
			else
				oReport.AddStepResult("Search Engine", "When status is ALL, ALL Repricing jobs are displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Click Search Engine","No draft Contracts are displayed","FAIL");
	}
	
	
	By noItemsWereFound=By.xpath("//div[@id='repricingView']//div[text()='No items were found']");
	
	//This Method is used to check All Users.
	public void all_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("First Repricing", FirstRepricing) || IsElementDisplayed("No ites Found", noItemsWereFound))
			oReport.AddStepResult("Clicked on ALL", "Clicked on all,When status is ALL, ALL Repricing jobs are displayed", "PASS");
		else
			oReport.AddStepResult("Clicked on ALL", "Clicked on all,When status is ALL, ALL Repricing jobs are not displayed", "FAIL");
	}
	
	
	By allDropDown=By.xpath("//div[@id='repricingView']//span[@ng-show='selectedOption.Text']/..");
	
	By draft=By.xpath("//div[@id='repricingView']//a[contains(.,'Draft')]");
	
	By inProcess=By.xpath("//div[@id='repricingView']//a[contains(.,'In-Process')]");
	
	By completedSuccessfully=By.xpath("//div[@id='repricingView']//a[contains(.,'Completed Successfully')]");
	
	By completedWithErrors=By.xpath("//div[@id='repricingView']//a[contains(.,'Completed With Errors')]");
	
	//This Method is used to check Completed successfully Users.
	public void repricingStatusDropDownValidation(String FeildName,By elemdesc)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Drop Button",allDropDown);
		click_button(FeildName,elemdesc);
		
		if(IsDisplayed("First Repricing", FirstRepricing) || IsDisplayed("No ites Found", noItemsWereFound))
			oReport.AddStepResult(""+FeildName+"", "Clicked on "+FeildName+" from the dropdown, When status is "+FeildName+", ALL Repricing jobs are displayed", "PASS");
		else
			oReport.AddStepResult(""+FeildName+"", "Clicked on "+FeildName+" from the dropdown, When status is "+FeildName+", ALL Repricing jobs are not displayed", "FAIL");
	}
	 
	
	By firstElementText=By.xpath("//ul[@id='repricing-select']//li[1]//div[contains(@class,'col-lg-11 col-md-11 ng-scope')]");
	
	By firstRepriceJob=By.xpath("//div[@id='reprice-search-results']//ul[@id='repricing-select']//li[1]");
	
	By searchBox=By.xpath("//div[@id='repricingView']//input[contains(@class,'search-text-box input-sm form-control')]");
	
	//This Method is used to Search and Select Repricing Jobs.
	public void selectCompletedJob_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Drop Button",allDropDown);
		click_button("Completed Successfully",completedSuccessfully);
		
		oParameters.SetParameters("FirstText", get_text(firstElementText));
		enter_text_value("SearchBox", searchBox, oParameters.GetParameters("FirstText"));
		performKeyBoardAction("ENTER");
		
		if(IsElementDisplayed("FirstElementText", firstRepriceJob))
		{
			oReport.AddStepResult("Completed repricing Job", "Searched and selected completed job and verified that Respective Repricing Jobs are displayed", "PASS");
			click_button("Frist Repricing",firstRepriceJob);
		}
		else
			oReport.AddStepResult("Completed repricing Job", "Searched and selected completed job and verified that Respective Repricing Jobs are not displayed", "FAIL");
	}
	
	
	By viewClaimRepricingReport=By.xpath("//div[@id='repricing-right']//div[@class='section-header']//div[@class='']//input[@class='btn btn-xs btn-default']");
	
	By openedTitleName=By.xpath("//div[@id='repricing-right']//div[contains(@class,'col-lg-12 col-md-12 col-sm-12 xl-header ng-binding')]");
	
	By claimRepricingReport=By.xpath("//div[@id='title-bar']//span[text()='Claim Repricing Report']/..");
	
	By createReport=By.xpath("//div[@id='report-table-parent']//button[text()='Create Report']");
	
	//This Method is used to verfiying page is navigating to report plugin or not.
	public void viewClaimRepricingReport_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(6);
	
		if(IsElementDisplayed("View Claim Repricing Report", viewClaimRepricingReport))
		{
			oReport.AddStepResult("View Claim Repricing Report", "Searched and selected completed job and clicked on 'View Claim Repricing Report' and verified that page is navigated to report plugin ", "PASS");
			
			javaScriptExecutor("View Claim Repricing Report", viewClaimRepricingReport);
			
			fixed_wait_time(2);
			
			if(IsElementDisplayed("Create Report", createReport))
				oReport.AddStepResult("Repricing jobs", "Clicked on Claim repricing report and verified that Repricing Jobs are displayed", "PASS");
			else
				oReport.AddStepResult("Repricing jobs", "Clicked on claim repricing report and verified that Repricing Jobs are not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("View Claim Repricing Report", "Searched and selected completed job and clicked on 'View Claim Repricing Report' and verified that page is not navigated to report plugin ", "FAIL");
	}
	
	
	By contractManagement=By.xpath("//div[@id='portal-main-nav']//a[contains(.,'Contract Management')]");
	
	//This Method is used to navigate back to repricing plugin.
	public void navigateBackRepricing_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Contract Management",contractManagement);
		
		if(IsElementDisplayed("Repricing", Repricing_plugin) && IsElementDisplayed("Repricing Dropdown", allDropDown))
			oReport.AddStepResult("Contract Management", "Navigated back to Repricing Plugin and verified that Repricing plugin page is displayed and All the Repricing plugin page Feilds are getting enabled", "PASS");
		else
		{
			oReport.AddStepResult("Contract Management", "Navigated back to Repricing Plugin and verified that Repricing plugin page is not displayed and All the Repricing plugin page Feilds are not getting enabled", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}
	}
	
	
	By delete=By.xpath("//ul[@id='repricing-select']//li[1]//i[@class='left fa fa-minus-square']");
	
	//This Method is used to hover over on Reprice job and also to check wheather delete icon present or not.
	public void hoverOverRepriceJob_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Show Jobs For All Jobs", showJobsForAllUsers);
		waitFor(FirstRepricing, "First Repricing Name");
		mouse_hover("Hover",FirstRepricing);
		
		if(IsElementDisplayed("Delete icon",delete))
    		oReport.AddStepResult("Delete icon", "Hover over reprice job and verified that delete icon is displayed", "FAIL");
    	else
    		oReport.AddStepResult("Delete icon", "Hover over reprice job and verified that delete icon is not displayed", "PASS");
	}
	
	
	By statistics=By.xpath("//div[@class='portal-content']//div[@class='section-header']//div[contains(.,'Statistics')]");
	
	//This Method is used to check under statistics all the feild values must be there or not.
	public void statistics_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("Statistics", statistics))
			oReport.AddStepResult("Refresh link", " Clicked on Refresh link and verified that all the feild value under statistics is displayed", "PASS");
		else
			oReport.AddStepResult("Refresh link", " Clicked on Refresh link and verified that all the feild value under statistics is not displayed", "FAIL");
	}
	
	
	By claimSelectionCriteria=By.xpath("//div[@class='portal-content']//div[@class='section-header']//div[contains(.,'Claim Selection Criteria')]");
	
	//This Method is used to check under claim Selection Criteria all the feild values must be there or not.
	public void claimSelectionCriteria_ViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("Claim Selection Criteria", claimSelectionCriteria))
			oReport.AddStepResult("Refresh link", " Clicked on Refresh link and verified that all the feild value under Claim Selection is displayed", "PASS");
		else
			oReport.AddStepResult("Refresh link", " Clicked on Refresh link and verified that all the feild value under Claim Selection is NOT displayed", "FAIL");
	}
	
	
	By analytics=By.xpath("//div[@id='portal-main-nav']//a[contains(.,'Analytics')]");
	
	By claimrepricingReport=By.xpath("//div[@id='styledDropdown']//a[contains(.,'Claim Repricing Report')]");
	
	//This Method is used to navigate to Analytics plugin and select Claim Repricing Report.
	public void navigateAnalytics_ViewOnlyPerission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		navigate_to("Analytics", "Create Report", analytics, createReport);
		
		if(IsEnabled("Reprice Claim Dropdown", RepriceClaims_dropdown))
			oReport.AddStepResult("Claim Repricing Report", "Selected on Claim Repricing Report and verified that Claim Repricing report page is not displayed with default filter and reprice claim button is enabled", "FAIL");
		else
			oReport.AddStepResult("Claim Repricing Report", "Selected on Claim Repricing Report and verified that Claim Repricing report page is displayed with default filter and reprice claim button is disabled", "PASS");
	}
	
	
	public void repricing_viewPermission()
	{
		login("VIEW");
		changePricingEngine();
		navigateRepricing_ViewOnlyPermission();
		allUsersCheckBox_ViewOnlyPermission();
		all_ViewOnlyPermission();
		
		repricingStatusDropDownValidation("In-Process",inProcess);
		repricingStatusDropDownValidation("Completed Successfully",completedSuccessfully);
		repricingStatusDropDownValidation("Completed With Errors",completedWithErrors);
		
		repricingDropDown(completedSuccessfully, "Completed Successfully", completed_successfully_status, "Completed Successfully_status");
		viewClaimRepricingReport_ViewOnlyPermission();
		navigateBackRepricing_ViewOnlyPermission();
		hoverOverRepriceJob_ViewOnlyPermission();
		statistics_ViewOnlyPermission();
		claimSelectionCriteria_ViewOnlyPermission();
		navigateAnalytics_ViewOnlyPerission();
		RepriceJobFilter();
		logout();
	}
}
package contractManagement;

import java.lang.ref.Reference;

import org.openqa.selenium.*;
import analytics.ReportsLibrary;
import libraries.*;

/**
 * @author C16137
 *
 */

public class ModelsLibrary extends CCMLibrary
{
	ReportsLibrary oReportsLibrary = new ReportsLibrary(driver, oReport, oParameters);
	
	ExcelData oExcelData = new ExcelData(oParameters);
	
	public ModelsLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}
	
	
	public void xpathChange()
	{
		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
			respectiveJob=respectiveJobDC;
		else
			respectiveJob=respectiveJobDC;
	}
	
	By modelsPlugin=By.xpath("//a[text()='Models']");
	
	By SearchModel=By.xpath("//div[@id='sl-right']//div[@class='second-line ng-scope ng-binding']");
	
	By allUsersCheckBox=By.xpath("//input[@id='showAllModelJobs']");
	
	By firstModelJob=By.xpath("//div[@id='model-search-results']//ul//li[1]");
	
	By ModelJobDropdown=By.xpath("//div[@id='modelView']//span[@ng-show='selectedOption.Text']");
	
	By dropDownStatus = By.xpath("//div[@id='modelView']//div[@class='input-group input-group-sm search-text-box']//li//a");
	
	By version=By.xpath("//div[@id='sl-right']//div[@class='document-title-bar ng-scope']//div[@class='pull-right hand-cursor']//span[@class='small-header ng-binding']");
	
	By respectiveModelJobStatus=By.xpath("//button[@class='pull-left document-status btn-dropdown-popover ng-scope ng-binding']");
	
	By serviceFaciltyFirstNameDC=By.xpath("//div[@id='report-table-parent']//table/tbody/tr[1]/td[9][@class='hide-overflow string ng-scope']");
	
	public void ModelJobValidation(By modelStatus)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button(get_text(modelStatus), modelStatus);
		
		if(IsDisplayed("First Models Job", firstModelJob))
		{
			oReport.AddStepResult("Model Job", " Clicked on 'Model Status DropDown Button'  and selected '"+get_text(ModelJobDropdown)+"' as a status and verified respective '"+get_text(ModelJobDropdown)+" jobs' are displayed", "PASS");
			click_button("First Job",firstModelJob);
		
			/*fixed_wait_time(4);
			
			if(IsDisplayed("Model Job Version", version))
			{		
				click_button("Model Job Version", version);
		
				By newVersion=By.xpath("//div[@id='modelView']//ul[@class='dropdown-menu small-header']/li[1]/a");
				click_button("Version1 dropdown", newVersion);
			}	*/
	
			if(get_text(ModelJobDropdown).replaceAll("-", " ").equals(get_text(respectiveModelJobStatus)))
				oReport.AddStepResult("Model Job", "Clicked on '"+get_text(ModelJobDropdown)+"' model job and verified that respective model job is displayed with '"+get_text(respectiveModelJobStatus)+"' status as expected", "PASS");
			else
				oReport.AddStepResult("Model Job", "Clicked on '"+get_text(ModelJobDropdown)+"' model job and verified that respective model job is not displayed with '"+get_text(respectiveModelJobStatus)+"' status as expected", "FAIL");
		}
		else
			oReport.AddStepResult("No jobs are present", " Clicked on 'Model Status DropDown Button'  and selected '"+get_text(ModelJobDropdown)+"' as a status and verified respective '"+get_text(ModelJobDropdown)+" jobs' are not displayed,its showing message like 'No jobs are present,So no users job message displayed'", "PASS");
	}
	
	public void dropdownStatusValidation(String modelJobStatus)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("All Users Check Box",allUsersCheckBox);
		
		if(modelJobStatus.isEmpty())
		{
			int statusCount = convertToWebElements(dropDownStatus).size();
		
			for(int i=1; i<statusCount; i++)
			{
				click_button("Model job DropDown Button", ModelJobDropdown);
			
				By ModelType = By.xpath("//div[@id='modelView']//div[@class='input-group input-group-sm search-text-box']//li["+(i+1)+"]//a");
				ModelJobValidation(ModelType);
			}
		}
		else
		{
			By ModelType = By.xpath("//div[@id='modelView']//div[@class='input-group input-group-sm search-text-box']//li[2]//a[contains(.,'"+modelJobStatus+"')]");
			
			click_button("Model job DropDown Button", ModelJobDropdown);
			ModelJobValidation(ModelType);
		}
	}	
	
	public By addModelButton=By.xpath("//a[@title='Add Model']");
	
	By modelDetails=By.xpath("//div[contains(@class,'portal-content-right model')]//div[@class='section-header']//div[contains(text(),'Model Details')]");
	
	public By modelNameTextBox=By.xpath("//input[@id='modelname'][@type='text']");
	
	public By manageClaimButton=By.xpath("//div[@type='button'][contains(.,'Manage Claim Criteria')]");
	
	By dateRangeDropdownButton=By.xpath("//div[@id='styledDropdown']//span[contains(text(),'Admit-Discharge')]/..");
	
	By admitDischarge=By.xpath("//li[@id='i[__valueField]']/a[contains(.,' Admit-Discharge')]");
	
	By statementFormThrough=By.xpath("//a[contains(.,'Statement From - Through')]");
	
	//This Method is Used for clicking Addmodel and Verifying model form is displayed or not.  
	public void addModelvalidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		dclick_button("Add Model",addModelButton);
		
		if(IsElementDisplayed("Add Product Model Details", modelDetails))
		{	
			oReport.AddStepResult("Add Model Job Details ", "Clicked on Add Product Model and verified that 'All Product Model feilds' get refreshed and it is displayed", "PASS");
			enter_text_value("Model Name",modelNameTextBox,oParameters.GetParameters("ModelName")+System.currentTimeMillis());
			oParameters.SetParameters("NewModelName", get_field_value("Model Name", modelNameTextBox));
			
			if(IsElementEnabled("Manage Claim Criteria", manageClaimButton) && IsElementDisplayed("click on daterange dropdown button", dateRangeDropdownButton))
			{
				oReport.AddStepResult("Manage Claim Button", "Entered Model Name and verified that Manage claim Button is Enabled", "PASS");
				click_button("Date Range Dropdown",dateRangeDropdownButton);
					
				if(IsElementDisplayed("Admit Discharge", admitDischarge) && IsElementDisplayed("Statement Form -Through", statementFormThrough))
					oReport.AddStepResult("'Admit-Discharge' and 'Statement From'", "Clicked on Select Date Range drop down and verified that 'Admit-Discharge' and 'Statement From'are displayed", "PASS");
				else
					oReport.AddStepResult("'Admit-Discharge' and 'Statement From'", "Clicked on Select Date Range drop down and verified that 'Admit-Discharge' and 'Statement are not displayed", "FAIL");
			}
			else
				oReport.AddStepResult("Manage Claim Button", "Entered Model Name and verified that Manage claim Button is not Enabled", "FAIL");
		}	
		else
			oReport.AddStepResult("Add Model Job Details", "Clicked on Add Product Model and verified that 'All Product Model feilds' are not refreshed and it is not displayed", "FAIL");
	}
	
	By addFilterElement = By.xpath("//div[@id='filter-report']//span[text()='Add Filter']");
	
	By filterDropDown = By.xpath("//div[@id='filter-report']//a[@class='filter-label hand-cursor pull-left ng-scope']");
	
	
	public void AddFilter(String filtername)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add Filter Button", addFilterElement);
		
		click_button("Filters DropDown", filterDropDown);
		
		By selectField = By.xpath("//div[@id='filter-report']//li/a[text()='"+filtername+"']");
		click_button(filtername, selectField);	
	}
	
	//public By manageClaimButton11=By.xpath("//div[@type='button'][contains(.,'Manage Claim Criteria')]");
	
	By manageClaimCriteriaWindow = By.xpath("//div[@id='manageClaimCriteria']//div[@class='modal-header document-title-bar']");
	
	By filterClaimButton = By.xpath("//div[@id='manageClaimCriteria']//input[@value='Filter Claims']");
	
	By doesntContainOperator = By.xpath("//div[@id='filter-report']//a[text()='- Does Not Contain']");
	
	By filterBox=By.xpath("//div[@id='filter-report']//input[1][@title='Enter text to filter and press enter.']");
	
	By saveButton=By.xpath("//div[@id='fullFooter']//input[@value='Save']");
	
	By ClaimId=By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'claimId')\"]");
	
	public void manageClaimCriteria(By CriteriaFilterButton)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Manage claim Criteria",CriteriaFilterButton);
		fixed_wait_time(5);

		
		if(IsDisplayed("'Manage Claim Criteria Window'", manageClaimCriteriaWindow))
		{
			oReport.AddStepResult("Manage claim criteria window", " Clicked on 'Manage claim Criteria' and verified that 'Manage Claim Criteria Window' is displayed", "PASS");
			
			if(IsDisplayed("Clear Filter Button", oReportsLibrary.clearFiltersButton))
			{
				oReportsLibrary.clearFilters();	
				click_button("Filter Claim Button", filterClaimButton);
			}
			
			oReport.AddStepResult("Service Facilty First Name","Clicked on Filter Claim button","SCREENSHOT");
			
			oParameters.SetParameters("FirstServiceFaciltyName", get_text(ClaimId));
			
			AddFilter("Claim Id");
			click_button("Bill Type Operator", oReportsLibrary.billTypeOperators);
			click_button("Does Not Contain", doesntContainOperator);
			enter_text_value("Feild to be Filter", filterBox, oParameters.GetParameters("FirstServiceFaciltyName"));
			performKeyBoardAction("Enter");
			click_button("Filter Claim Button", filterClaimButton);
			
			oReport.AddStepResult("Filter Claim"," Filter Claims using Claim Id ", "SCREENSHOT");
			
			clickSaveButton("'Manage Calim Criteria window Save Button'", "Manage Claim Criteria Window", saveButton, manageClaimCriteriaWindow);
		}
		else
		{
			oReport.AddStepResult("Manage claim criteria window", " Clicked on 'Manage claim Criteria' and verified that 'Manage Claim Criteria Window' is not displayed", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}			
	}
	
	public By productModelName=By.xpath("//input[@id='productmodelname']");
	
	By useCustomCriteriaButton=By.xpath("//div[@class='ng-scope ng-binding']//div[contains(.,'Use Custom Criteria')]");
	
	By customClaim=By.xpath("//div[@id='modelForm.editModelForm']//div[@class='btn btn-default ng-binding']");

	
	
	//This Method is used to verifying Use Custom Criteria button is enabled by Entering product Model Name.
	public void productModelDetails()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Product Model Name", productModelName, oParameters.GetParameters("productModeName")+System.currentTimeMillis());
			
		if(IsDisplayed("Use Custom Criteria", useCustomCriteriaButton) || IsDisplayed("Custom Claim", customClaim))
			oReport.AddStepResult("Use Custom Criteria button", "Entered 'Product Model Name' and verified that 'Use Custom Criteria' button is Enabled", "PASS");
		else
			oReport.AddStepResult("Use Custom Criteria button", "Entered 'Product Model Name' and verified that 'Use Custom Criteria' button is not Enabled", "FAIL");
	}
	
	public By baseRateSheet = By.xpath("//div[@id='modelForm.editModelForm']//input[@id='model-base-baseratesheet']");
	
	public By firstBaseRateSheet = By.xpath("//ul[@id='model-base-baseratesheet-list']//li[1]");
	
	//This Method is used to select a Base Rate Sheet.
	public void selectBaseRateSheet(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
			
		click_button("Base Rate Sheet",baseRateSheet);
		enter_text_value("Base Rate Sheet",baseRateSheet,oParameters.GetParameters("Baseratesheet"+i));
		performKeyBoardAction("ENTER");
		
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
			
		if(IsDisplayed("First BaseRate Sheet", firstBaseRateSheet))
			click_button("First Base Rate Sheet",firstBaseRateSheet);
		
		if(IsElementDisplayed("check Base Rate Sheet is visible", baseRateSheet))
			oReport.AddStepResult("Base Rate Sheet", "Entered Base Rate Sheet and verified that respective Rate sheet is selected", "PASS");
		else
			oReport.AddStepResult("Base Rate Sheet", "Entered Base Rate Sheet and verified that respective Rate sheet is not selected", "FAIL");
	}

	
	public By proposalRateSheet= By.xpath("//div[@id='modelForm.editModelForm']//input[@id='model-prop-ratesheet']");
		
	public By firstProposalRateSheet=By.xpath("//ul[@id='model-prop-ratesheet-list']//li//a[not(text())]//b[text()='Modeling']"); 
	
	public By rateSheetTab=By.xpath("//div[@id='modelView']//ul[@id='model-prop-ratesheet-list']//li");
	
	public By addedProposalRateSheet=By.xpath("//ng-include[@class='ng-scope']//table[contains(@class,'col-lg-12 col-md-12 col-sm-12 table')]//td[@class='hide-overflow col-lg-6 col-md-6 ng-binding']");
	
	//This Method is used to select a Proposal Rate sheet.
	public void selectProposalRateSheet(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
			
		click_button("Proposal Rate Sheet",proposalRateSheet);
		enter_text_value("Proposal Rate Sheet",proposalRateSheet,oParameters.GetParameters("PropsalRateSheet"+i));
		performKeyBoardAction("ENTER");
		performKeyBoardAction("ENTER");
		
		if(IsDisplayed("proposal RateSheet ", rateSheetTab))
			click_button("First Proposal Rate Sheet",firstProposalRateSheet);
		else if(IsDisplayed("Added Proposal Rate Sheet", addedProposalRateSheet))
				oReport.AddStepResult("Proposal Rate Sheet", "Entered Proposal Rate Sheet and verified that respective Rate Sheet is selected", "PASS");
		else
			oReport.AddStepResult("Proposal Rate Sheet", "Entered Proposal Rate Sheet and verified that respective Rate Sheet is not selected", "FAIL");
	}
			
	
	By saveModelJobDetail = By.xpath("//div[@id='sl-right']//div[contains(@class,'portal-content-footer form-button-panel')]//input[@value='Save']");
	
	By cancelModelJobDetail = By.xpath("//div[@id='sl-right']//div[contains(@class,'portal-content-footer form-button-panel')]//input[@value='Cancel']");
	
	//This method is used to click on save opertion.
	public void saveModelJobDetils() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		click_button("Save Job Details",saveModelJobDetail);
		fixed_wait_time(2);	
		
		if(IsDisplayed("Save Model Job Details", saveModelJobDetail))
			click_button("Save Model Job Details",saveModelJobDetail);
		else
			System.out.println("Already Clicked");
		
		fixed_wait_time(10);
		refresh_page();
		
		waitFor(respectiveModelJobStatus,"Model Job Status");
		oParameters.SetParameters("draftsStatus", get_text(respectiveModelJobStatus));	
		
		if(oParameters.GetParameters("draftsStatus").equalsIgnoreCase("Draft"))
			oReport.AddStepResult("Model Form", "Filled all the mandatory feilds in Add Model Form and clicked on save and verified that model job is saved as a draft State", "PASS");
		else
			oReport.AddStepResult("Model Form", "Filled all the mandatory feilds in Add Model Form and clicked on save and verified that model job is saved as a draft State", "FAIL");
	}
	
	
	By saveAddAnotherProductButton=By.xpath("//input[@value='Save and Add Another Product']");
	
	By modelName=By.xpath("//input[@id='modelname']");
	
	//This Method is used to perform Save and Add Another Product and Verify product is added.
	public void saveAndAddAnotherProduct()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Save Add Another Product",saveAddAnotherProductButton);
		fixed_wait_time(5);
		refresh_page();
		waitFor(modelName,"Model Name Feild");
		waitFor(modelName,"Model Name Feild");
		waitFor(modelName,"Model Name Feild");
		
		if(IsDisplayed("Product Model Name Feild", productModelName) && IsDisplayed("Use Custom Criteria", useCustomCriteriaButton))
		{
			oReport.AddStepResult("Save and Add Another Product button", "Clicked on 'Save and Add Another Product' and verified that the product model is added and feilds are displayed to add another product Model", "PASS");
			
			productModelDetails();
			manageClaimCriteria(useCustomCriteriaButton);
			selectBaseRateSheet("");
			selectProposalRateSheet("");
			saveModelJobDetils();
		}
		else
			oReport.AddStepResult("Save and Add Another Product button", "Clicked on 'Save and Add Another Product' and verified that 'One Product Model' is added, however fields are not displayed to add another Product Model", "FAIL");
	}
	
	By editIcon=By.xpath("//div[@id='modelView']//span[text()='Edit']/..");
	
	By addProductModel=By.xpath("//span[text()='Add Product Model']/..");
	
	By UnsavedChangesCancelButton = By.xpath("//div[@id='dialog']//div[@id='dialog_buttons']/input[@value='Cancel']");
	
	By UnsavedChangesDiscardButton = By.xpath("//div[@id='dialog_buttons']/input[@value='Discard']");
	
	public void editProductModelValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		dclick_button("click on Edit Icon",editIcon);
		
		click_button("Add Product Model Link", addProductModel);
		
		productModelDetails();
		manageClaimCriteria(useCustomCriteriaButton);
		selectBaseRateSheet("");
		selectProposalRateSheet("");
		
		cancelScenario("Add Model Job Cancel Button", "'UnSaved Changes PopUp' Cancel Button", "'UnSaved Changes PopUp' Discard Button", cancelModelJobDetail, UnsavedChangesCancelButton, UnsavedChangesDiscardButton);
	}
	
	By submitModelRadioButton=By.xpath("//input[@id='sbmitrev']");
	
	By submitButton=By.xpath("//input[@value='Submit']");
	
	By respectiveJob=By.xpath("//div[@class='pull-left col-lg-12 col-md-12 col-sm-12 xl-header ng-binding']");
	
	//This Method is used change status drop down and submit.
	public void changeStatusDropDown()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Status Button",respectiveModelJobStatus);
		click_button("Submit Model job radio button",submitModelRadioButton);
			
		click_button("Submit Button",submitButton);
		fixed_wait_time(2);
			
		oParameters.SetParameters("InProcessModelStatus", get_text(respectiveModelJobStatus));
		waitFor(respectiveJob,"Respective Opened Model Job");
			
		if(oParameters.GetParameters("InProcessModelStatus").equalsIgnoreCase("In Process"))
			oReport.AddStepResult("Submit job", "Submited the job and Verified that job is submitted and the status changes from Draft to In Process is displayed", "PASS");
		else
			oReport.AddStepResult("Submit job", "Submited the job and Verified that job is submitted and the status changes from Draft to In Process  is not displayed", "FAIL");
	}
		
	By cancelJob=By.xpath("//input[@id='cancelMod']");
		
	//This Method is used to cancel a job.
	public void cancelChangeStatusDropDown()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
			
		click_button("Status Button",respectiveModelJobStatus);
		fixed_wait_time(5);
		
		if(IsDisplayed("Model Job Status Tab",cancelJob))
		{
			oReport.AddStepResult("Model Job Cancel radio Button", "Clicked on satatus button and verified that cancel Model job radio Button is displayed", "PASS");
			click_button("Cancel Model job radio button",cancelJob);
			
			click_button("Submit Button",submitButton);
			oParameters.SetParameters("draftsModelStatus", get_text(respectiveModelJobStatus));	
			
			if(oParameters.GetParameters("draftsModelStatus").equalsIgnoreCase("Draft"))
				oReport.AddStepResult("Canceled submit job", "Canceled the job and Verified that job is canceled and status changes from In Process to Draft is displayed", "PASS");
			else
				oReport.AddStepResult("Canceled submit job", "Canceled the job and Verified that job is canceled and status changes from In Process to Draft is not Displayed", "FAIL");
		}
		else
		{
			oReport.AddStepResult("Model Job Cancel radio Button", "Clicked on satatus button and verified that cancel Model job radio Button is not displayed", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}
	}
	
	
	By statucButtonComp=By.xpath("//button[@id='change-model-status-comp']");
	
	//This Method is used for Model Job is submitted and completed Successfully.
	public void submitModelJob()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		
		for(int i=0;i<=5;i++)
		{
			refresh_page();
			fixed_wait_time(4);
		}
		
		oParameters.SetParameters("CompletedSuccessfullyModelStatus", get_text(statucButtonComp));	
		
		if(oParameters.GetParameters("CompletedSuccessfullyModelStatus").equalsIgnoreCase("Completed Successfully") || oParameters.GetParameters("CompletedSuccessfullyModelStatus").equalsIgnoreCase("Canceled") || oParameters.GetParameters("CompletedSuccessfullyModelStatus").equalsIgnoreCase("Completed With Errors"))
			oReport.AddStepResult("Submit job", "Selected 'Submit Model Job' radio button, clicked on 'Submit' button and model job is submitted successfully and verified that model job status changed to 'Completed Successfully'", "PASS");
		else
		{
			oReport.AddStepResult("Submit job", "Selected 'Submit Model Job' radio button, clicked on 'Submit' button and model job is submitted successfully, however the status of model job is not changed to 'Completed Successfully'", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}
	}
	
	By viewModelResult=By.xpath("//span[text()='View Model Results']/..");
	
	By comparativeAnalysis=By.xpath("//a[contains(.,'Comparative Analysis')]/..");
	
	By analytics=By.xpath("//a[contains(.,'Analytics')]/..");
	
	By pivotTitle=By.xpath("//div[@id='analyticView']//div[@class='col-lg-12 col-md-12 col-sm-12 col-xs-12 pivot-title-section']//div[contains(.,'Comparative Analysis')]");
	
	By respectiveJobDC=By.xpath("//div[@class='pull-left col-lg-12 col-md-12 col-sm-12 large-document-header hide-overflow ng-binding']");
	
	//This Method is used to verifying that page navigates Comparative Analysis plugin.
	public void viewMoreResult()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(isAttribtuePresent(viewModelResult,"disabled"))
			oReport.AddStepResult("View Model Result", " submit the job and verified that All claims are getting errored so that 'View Model Result' button is disabled", "WARNING");
		else
		{	
			click_button("View Model Result",viewModelResult);
			waitFor(comparativeAnalysis,"Comparative Analysis plugin");
			
			if(IsDisplayed("Pivot Title", pivotTitle))
			{
				fixed_wait_time(2);
				oReport.AddStepResult("View Model Result", "Clicked on View more result verified that Page Navigates to Compartive Analysis and Pivot table is Displayed", "PASS");
				click_button("Contract Management",contractManagement);
				
				if(IsElementDisplayed("Model Plugin and Previously opened Job", respectiveJobDC))
					oReport.AddStepResult("Contract Management", "Clicked on contract management suite and verified that Models plugin and previously opened model job is displayed", "PASS");
				else
					oReport.AddStepResult("Contract Management", "Clicked on contract management suite and verified that Models plugin and previously opened model job is not displayed", "FAIL");
			}
			else
				oReport.AddStepResult("View Model Result", "Clicked on View more result verified that Page Navigates to Compartive Analysis and Pivot table is not Displayed", "FAIL");
		}
	}
	
	
	By contractManagement=By.xpath("//a[contains(.,'Contract Management')]/..");
	
	By pivotName=By.xpath("//b[@class='ng-binding']");
	
	By modelOutlier=By.xpath("//span[contains(.,'Model outliers report')]/..");
	
	By claimWithErrors=By.xpath("//span[contains(.,'Claims with errors')]/..");
	
	By claimPricedat$0=By.xpath("//span[contains(.,'Claims priced at $0.00')]/..");
	
	
	//This Method is used to hover a model outlier report.
	public void modelOutlierReport()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Model Outliers Report", modelOutlier))
		{
			oReport.AddStepResult("Model Outliers Report", "Submit the job and verified that 'Model Outliers Report' is displayed", "PASS");
			
			mouse_hover("Mode Ouliet Report", modelOutlier);
			
			if(IsDisplayed("Cliam With Errors", claimWithErrors) && IsDisplayed("Claim priced at $0", claimPricedat$0))
			{	
				oReport.AddStepResult("Cliam With Errors and Claim priced at $0", "Hover on Model Outlier Report and verified that Claim With Errors and claim priced at $0 is displayed", "PASS");
				claimWithErrorsAndCliamPricedAt$0_Validation(errors,claimWithErrors,"Errors");
				claimWithErrorsAndCliamPricedAt$0_Validation(totalPriced,claimPricedat$0,"Total Priced at $0.00");
			}
			else if(IsDisplayed("Cliam With Errors", claimWithErrors))
			{
				oReport.AddStepResult("Mode Ouliet Report", "Hover on Model Outlier Report and verified that Claim With Errors  is displayed", "PASS");
				claimWithErrorsAndCliamPricedAt$0_Validation(errors,claimWithErrors,"Errors");
			}
			else if(IsDisplayed("Claim priced at $0", claimPricedat$0))
			{
				oReport.AddStepResult("Claim priced at $0", "Hover on Model Outlier Report and verified that Claim priced at $0 is not displayed", "PASS");
				claimWithErrorsAndCliamPricedAt$0_Validation(totalPriced,claimPricedat$0,"Total Priced at $0.00");
			}
		}	
		/*else
			oReport.AddStepResult("Model Outliers Report", "Submit the job and verified that 'Model Outliers Report' is not displayed", "FAIL");
*/	}
	
	
	By errors=By.xpath("//div[@id='modelStatistics']//div[@class='errors']//div[1]");
	
	By totalClaims=By.xpath(".//*[@id='modelStatistics']/div[3]/div[1]");
	
	By totalPriced=By.xpath("//div[@id='modelStatistics']/div[2]//div[contains(@class,'col-lg-3 col-md-3 pad-r-0 bold ')]");
	
	
	//This Method is used to click on claim with Errors.
	public void claimWithErrorsAndCliamPricedAt$0_Validation(By elemdesc,By report,String FeildName)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("Statistics", get_text(elemdesc).replace(",", ""));
		mouse_hover("Mode Ouliet Report", modelOutlier);				
		click_button("Statistics",report);			
		
		try 
		{
			fixed_wait_time(3);
		
			//This method is used to convert CSV Format to Excel Format. 
			csvToExcel();
			
	    	@SuppressWarnings("static-access")
			int DownloadedClaimExcelcount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"),"xlsx"));
	    	
	    	if(Integer.parseInt(oParameters.GetParameters("Statistics")) == DownloadedClaimExcelcount) 
	    		oReport.AddStepResult("Claim With Errors and claim priced at $0", " Verified that number of " +FeildName+ " under 'Contract Model Statistics' is "+oParameters.GetParameters("Statistics")+ " is equal to number of records " +DownloadedClaimExcelcount+ " in downloaded excel", "PASS");
	    	else
	    		oReport.AddStepResult("Claim With Errors and claim priced at $0", " Verified that number of " +FeildName+ " under 'Contract Model Statistics' is "+oParameters.GetParameters("Statistics")+ " is not equals to number of records " +DownloadedClaimExcelcount+ " in downloaded excel", "FAIL");
	    }
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	By delete=By.xpath("//a[@title='Delete']");
	
	By deletePopup=By.xpath("//div[contains(text(),'Delete Contract Model')]");
	
	By deleteDailogYesButton=By.xpath("//div[@id='dialog_buttons']/input[@value='Yes']");
	
	By addModelLink=By.xpath("//div[@id='sl-right']//a[contains(.,'Add a Model')]");
	
	
	//This Method is used to perform an delete operation.
	public void deleteIcon()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		dclick_button("Delete Icon",delete);
		
		if(IsElementDisplayed("Delete Contract MOdel", deletePopup))
		{
			oReport.AddStepResult("Delete icon", "clicked on delete icon and Verified that Delete Contract Model Pop Up is displayed", "PASS");
			click_button("Delete Contract Model",deleteDailogYesButton);
			
			if(IsElementDisplayed("Add Model link appears", addModelLink))
				oReport.AddStepResult("Delete Icon", " Verified that Delete Contract Model pop up is displayed and deleted upon clicking yes", "PASS");
			else
				oReport.AddStepResult("Delete Icon", " Verified that Delete Contract Model pop up is not displayed and it is not deleted upon clicking yes", "FAIL");
		}
		else
			oReport.AddStepResult("Delete icon", "clicked on delete and Verified that Delete Contract Model Pop Up is not displayed", "FAIL");
	}
	
	
	By FirstJobDeleteIcon = By.xpath("//div[@id='modelView']//li[1]//a[@class='link-btn hand-cursor ng-isolate-scope']//i[@class='left fa fa-minus-square']");
	
	By searchBox=By.xpath("//input[@placeholder='Search Models']");
	
	public void SearchModelJob()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(firstModelJob,"First Model JOb");
		//oParameters.SetParameters("FirstJobName", get_text(firstModelsJobName));
		
		enter_text_value("Models Search", searchBox, oParameters.GetParameters("NewModelName"));
		performKeyBoardAction("ENTER");
		
		mouse_hover("First Model Job", firstModelJob);
		dclick_button("First Job Delete Icon", FirstJobDeleteIcon);
		
		if(IsElementDisplayed("Delete Contract MOdel", deletePopup))
		{
			oReport.AddStepResult("Delete icon", "clicked on delete icon and Verified that Delete Contract Model Pop Up is displayed", "PASS");
			click_button("Delete Contract Model",deleteDailogYesButton);
			
			if(IsElementDisplayed("Add Model link appears", addModelLink))
				oReport.AddStepResult("Delete Icon", " Verified that Delete Contract Model pop up is displayed and deleted upon clicking yes", "PASS");
			else
				oReport.AddStepResult("Delete Icon", " Verified that Delete Contract Model pop up is not displayed and it is not deleted upon clicking yes", "FAIL");
		}
		else
			oReport.AddStepResult("Delete icon", "clicked on delete and Verified that Delete Contract Model Pop Up is not displayed", "FAIL");
	}
	
	By copylink=By.xpath("//a[@id='copyLink']");
	
	By createNewVersion=By.xpath("//a[contains(.,'Create a New Version')]");
	
	By copyNewModel=By.xpath("//a[contains(.,'Copy to a New Model')]");
	
	By loadingIcon = By.xpath("//div[@name='editModelForm']//ul[@spinner='loadingProductModel']//div[text()='Loading...']");
	
	public void copyLinkIcon(By NewVersion_NewModel)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		dclick_button("Copy Link",copylink);
		
		if(IsDisplayed("Create New Version", createNewVersion) && IsDisplayed("Copy New Modal", copyNewModel)) 
		{
			oReport.AddStepResult("Copy link", " After clicking Copy link verified that Both Create New Version and Copy New Modal is Displayed ", "PASS");
			click_button("Create New Version",NewVersion_NewModel);
			
			if(IsDisplayed("Respective Job", respectiveJob) && IsDisplayed("Model Name", modelNameTextBox))
			{
				oReport.AddStepResult("Create New Version", "After clicking create new version its Respective job is displayed", "PASS");
				
				if(IsDisplayed("Manage Claim Button", manageClaimButton) && IsDisplayed("Product Model", productModelName))
					oReport.AddStepResult("Copy Link", "Clicked on copy link and verified that All the feilds are populated with previous data", "PASS");
				else
					oReport.AddStepResult("Copy Link", "Clicked on copy link and verified that All the feilds Not are populated with previous data", "FAIL");
			}
			else
				oReport.AddStepResult("Create New Version", "After clicking create new version its Respective jobs are not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Copy link", " After clicking Copy link verified that Both Create New Version and Copy New Modal is Displayed ", "FAIL");
	}
	
	By Firstversion = By.xpath("//div[@id='sl-right']//ul[@class='dropdown-menu small-header']/li[1]/a");
	
	//This Method is used to select a version and verifying version job displayed without any error.
	public void selectVersion()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("click on version",version);
		fixed_wait_time(2);
		click_button("select first version",Firstversion);
		
		if(IsElementDisplayed("Respective Job", respectiveJob))
			oReport.AddStepResult("Version1 Job", "Selected Version1 job and verified that version1 job is displayed", "PASS");
		else
			oReport.AddStepResult("Version1 Job", "Selected Version1 job and verified that version1 job is not displayed", "FAIL");
	}
	
	public void searchAndCreate()
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigate_to("Model Plugin", "First Model Job", modelsPlugin, SearchModel);
		dropdownStatusValidation("");
		
		addModelvalidation();
		manageClaimCriteria(manageClaimButton);
		productModelDetails();
		manageClaimCriteria(useCustomCriteriaButton);
		selectBaseRateSheet("");
		selectProposalRateSheet("");
		saveAndAddAnotherProduct();
		editProductModelValidation();
		changeStatusDropDown();
		cancelChangeStatusDropDown();
		changeStatusDropDown();
		
		submitModelJob();
		viewMoreResult();
		modelOutlierReport();
		deleteIcon();
		//SearchModelJob();
		logout();
	}
	
	
	public void creatingCopy()
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigate_to("Model Plugin", "First Model Job", modelsPlugin, SearchModel);
		
		
		//To Create Model Job.
		addModelvalidation();
		manageClaimCriteria(manageClaimButton);
		productModelDetails();
		selectBaseRateSheet("");
		selectProposalRateSheet("");
		saveModelJobDetils();
		waitForIsInvisible(loadingIcon, "Model Job Loading Icon");
		
		copyLinkIcon(createNewVersion);
		productModelDetails();
		saveModelJobDetils();
		waitFor(respectiveJob,"Respective Opened Model Job");
		
		if(IsDisplayed("Version", version))
			oReport.AddStepResult("Modified Data and Check new version of model job is created", "Modified Model job details and clicked on save button and new version of model job is created", "PASS");
		else
			oReport.AddStepResult("Modified Data and Check new version of model job is created", "Modified Model job details and clicked on save button and new version of model job is not created", "FAIL");
	
		
		/*//Save And Another Product Model Scenario.
		fixed_wait_time(2);
		dclick_button("Edit Icon",editIcon);
		selectBaseRateSheet("");
		saveAndAddAnotherProduct();*/
		
		//Cancel Scenario.
		fixed_wait_time(2);
		dclick_button("Edit Icon",editIcon);
		productModelDetails();
		selectBaseRateSheet("");
		cancelScenario("Add Model Job Cancel Button", "'UnSaved Changes PopUp' Cancel Button", "'UnSaved Changes PopUp' Discard Button", cancelModelJobDetail, UnsavedChangesCancelButton, UnsavedChangesDiscardButton);
		
		//Checking Status DropDown and Copy New Model.
		changeStatusDropDown();
		cancelChangeStatusDropDown();
		/*copyLinkIcon(copyNewModel);
		manageClaimCriteria(manageClaimButton);
		saveModelJobDetils();*/
		
		//To Submit Model Job.
		changeStatusDropDown();
		submitModelJob();
		viewMoreResult();
		modelOutlierReport();
		deleteIcon();
		logout();
	}
	
	By All=By.xpath("//div[@id='modelView']//a[contains(.,'All')]");
	
	//This Method is used to verify Add,Edit and Delete icon must not be displayed.
	public void AddEditDeleteValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Status Drop Down",ModelJobDropdown);
		click_button("Select ALL from dropdown",All);
		
		if(IsDisplayed("First Job", firstModelJob))
		{
			oReport.AddStepResult("Status dropdown", "Selected All from status drop down and verified that all Models Jobs are Displayed", "PASS");
			click_button("First Job",firstModelJob);
			waitFor(respectiveJob,"Respective Opened Model Job");
			
			if(IsDisplayed("Edit Icon", editIcon) && IsDisplayed("Delete Icon", delete) && IsDisplayed("Add Model", addModelButton))
				oReport.AddStepResult("Edit icon and Delete icon", "Clicked on First Model job and verified that Edit Icon and Delete Icon and Add Model is Displayed", "FAIL");
			else
				oReport.AddStepResult("Edit icon and Delete icon", "Clicked on First Model job and verified that Edit Icon and Delete Icon and Add Model is not Displayed", "PASS");
		}
		else
			oReport.AddStepResult("Status dropdown", "Selected All from status drop down and verified that all Models Jobs are not Displayed", "FAIL");
	}
	
	
	By firstJobDelete=By.xpath("//ul[@id='model-select']//li[1]//div[@class='pull-right ng-scope']//a/i[1]");
	
	//This Method is used to verify that Delete icon must not be displayed after hover on first job.
	public void hoverOverModelJob()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("Hover on First Job", firstModelJob);
		
		if(!IsDisplayed("click on Delete Button", firstJobDelete))
			oReport.AddStepResult("Hover over model job", "Hover over on Model Job and verified that delete icon is not Displayed", "PASS");
		else
			oReport.AddStepResult("Hover over model job", "Hover over on Model Job and verified that delete icon is Displayed", "FAIL");
	}
	
	
	By searchModels=By.xpath("//div[@id='modelView']//div[contains(@class,'search-text-box no-border border-box-sizing clear-button')]//input");
	
	public void searchAndSelect()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search Models", searchModels, oParameters.GetParameters("Models"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(2);
		
		oParameters.SetParameters("FirstModelJobText", get_field_value("First Model Job", firstModelJob));
		
		if(oParameters.GetParameters("FirstModelJobText").contains(oParameters.GetParameters("Models")))
		{	
			oReport.AddStepResult("Search Model Job", "Searched  particular model job in search feild and verified that model job is displayed", "PASS");
			click_button("First Job",firstModelJob);
		}
		else
		{
			oReport.AddStepResult("Search Model Job", "Searched  particular model job in search feild and verified that model job is not displayed", "FAIL");
			oParameters.SetParameters("Continued_Execution","NO");
		}	
	}
	
	By modelType = By.xpath("//div[@id='modelView']//div[contains(@class,'search-text-box no-border')]//div[contains(@class,'input-group-btn input-group-btn')]//ul//li[2]//a");
	
	By jobStatus=By.xpath("//ul[@id='model-select']//li[1]//div[contains(@class,'col-lg-7 col-md-7 col-sm-7 small-text hidden-xs ng-scope')]");
	
	public void modelsViewOnly(String ModelJobStatus)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("All Users Check Box",allUsersCheckBox);
		
		int count = convertToWebElements(dropDownStatus).size();
		
		for(int i=1;i<count;i++)
		{
			click_button("Model job DropDown Button", ModelJobDropdown);
			
			By ModelType = By.xpath("//div[@id='modelView']//div[contains(@class,'search-text-box no-border')]//div[contains(@class,'input-group-btn input-group-btn')]//ul//li["+(i+1)+"]//a");
			
			click_button(get_text(ModelType), ModelType);
			
			if(IsDisplayed("First Model Job", firstModelJob))
			{
				oReport.AddStepResult("Model Status Drop Down", "Clicked on model status drop down and selected '"+get_text(ModelJobDropdown)+"' as a status and verified that all jobs are displayed with '"+get_text(ModelJobDropdown)+"'" , "PASS");
				
				click_button("First Model Job", firstModelJob);
				
				String[] statusName = get_text(jobStatus).split(" ");
				String[] modelJobStatus = ModelJobStatus.split(",");
				
				if(statusName[0].equals(modelJobStatus[i-1]))
					oReport.AddStepResult("Check all jobs are displayed with status", "Selected '"+modelJobStatus[i-1]+"' status from dropdown and verified that all jobs are displayed with "+statusName[0]+" status ", "PASS");
				else
					oReport.AddStepResult("Check all jobs are displayed with status", "Selected '"+modelJobStatus[i-1]+"' status from dropdown and verified that all jobs are not displayed with "+statusName[0]+" status ", "FAIL");
			}
			else
				oReport.AddStepResult("Status drop down", "Clicked on status drop down and selected '"+get_text(ModelJobDropdown)+"' status and it's showing 'No users job' message has been displayed", "PASS");
		}
	}
	
	public void viewOnlyPermission()
	{
		login("VIEW");
		changePricingEngine();
		xpathChange();
		navigate_to("Model Plugin", "First Model Job", modelsPlugin, SearchModel);
		
		modelsViewOnly("Draft,Processing,Canceled,Completed,Completed With Errors");
		AddEditDeleteValidation();
		hoverOverModelJob();
		searchAndSelect();
		
		viewMoreResult();
		modelOutlierReport();
		logout();
	}
}

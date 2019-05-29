package contractManagement;


import java.io.IOException;
import java.text.*;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import libraries.*;


public class PPSLibrary extends CCMLibrary {
	
	ExcelData oExcelData = new ExcelData(oParameters);
	RateSheetLibrary oRateSheetLibrary = new RateSheetLibrary(driver, oReport, oParameters);

	int count = 0;
	int count1 =0;
	
	public PPSLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}
	
	public By PPSplugin = By.xpath("//div[@id='nav']//a[text()='PPS']");
	public By PluginPage = By.xpath("//div[@options='noGroupTypeSelection']/div/div[contains(.,'Select a PPS Group Type to begin.')]");	
	
	public By SelectGroupDropDown = By.xpath("//div[@id='styledDropdown']//span[contains(.,'Select a PPS Group Type')]");
	public By AddUserRateSetButton = By.xpath("//div[@role='button'][contains(.,' User Rate Set')]");
	public By UserRateSetWindow = By.xpath("//div[@class='workflow  modal-medium']");
	public By OpenPageValidation = By.xpath("//div[@class='msg-blurb']//div[contains(.,' or select one from the list.')]");
	public By saveButton = By.xpath("//div[@id='fullFooter']//input[@value='Save']");
	By ppsDRGPage = By.xpath("//div[@class='msg-blurb']//a[contains(.,'Add a User Rate Set Entry')]");
	By cancelButton = By.xpath("//div[@id='fullFooter']//input[@value='Cancel']");
	By popUpWindow = By.xpath("//div[@id='dialog']//div[@class='msg-area'][contains(.,'You have unsaved changes')]");
	By popUpDiscardButton = By.xpath("//div[@id='dialog_buttons']//input[@value='Discard']");
	By popUpCancelButton = By.xpath("//div[@id='dialog']//div[@id='dialog_buttons']//input[@value='Cancel']");
	By popUpDeleteButton = By.xpath("//div[@id='dialog_buttons']//input[@value='Delete']");
	public By copyCheckBox = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 mar-t-12 form-group ng-scope']//input[@id='copyClosed']");
	public By searchExistingElement = By.xpath("//form[@name='addUserRateSet']//input[@id='copyUserRateSet']");
	By iframeSwitch = By.xpath("//iframe[@id='importUserRateIframe']");
	By chooseButton = By.xpath("//input[@id='userRateEntryIframe']");
	public By addUserRateSetEntry = By.xpath("//a[@ng-click='addUserRateEntry()']");
	By searchResult = By.xpath("//ul[@class='data-list drillable-list scroll-auto']/li[1]");
	By addUserRateSetEntryWindow = By.xpath("//div[@class='workflow  modal-small']//div[@title='Add User Rate Set Entry']");
	By UserRateSetTable = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height scroll-auto']");
	By selectedPPSGroup = By.xpath("//tr[@class='hand-cursor position-relative ng-scope']");
	By editEntryPanel = By.xpath("//div[@id='editEntryPanel']//div[@class='medium-header truncate ng-binding']");
	By editPPSWeight = By.xpath("//div[@id='editEntryPanel']//input[@id='weight']");
	By enterLOS = By.xpath("//div[@id='editEntryPanel']//input[@id='stdLenOfStay']");
	By editSaveButton = By.xpath("//button[@id='button.saveId']");
	By hoverUserEntry = By.xpath("//tr[@ng-repeat='entry in selectedPeriodEntries']");
	By deleteUserRate = By.xpath("//tr[@ng-repeat='entry in selectedPeriodEntries']//i[@class='left fa fa-minus-square']");
	By effectivePeriod = By.xpath("//div[@on-change='selectDrgPeriod']//a[@ng-click='showDropdown = !showDropdown']");
	By editEffectivePeriod = By.xpath("//ul[@class='dropdown-menu period-menu mar-l-15']//li[2]//i[@ng-hide='true']");
	By editEffectiveWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Edit Effective Period']");
	By userRateStatus = By.xpath("//div[@class='workflow  modal-medium']//input[@value='Actv']");
	public By saveEffectivePeriod = By.xpath("//div[@class='workflow  modal-medium']//input[@value='Save']");
	By addPeriod = By.xpath("//div[@on-change='selectDrgPeriod']//a[@ng-click='addPeriod()']");
	By addEffectivePeriodWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add Effective Period']");
	public By addNewEffectivePeriod = By.xpath("//div[@class='workflow  modal-medium']//input[@id='startDate']");
	public By selectExistingUserRate = By.xpath("//div[@class='row mar-l-0 mar-t-10 ng-scope']//input[@id='copyUserRateSet']");
	By entryTable = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height scroll-auto']//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']");
	By deletePeriod = By.xpath("(//i[@ng-click='deletePeriod(p, periods)'])[1]");
	By hoverOverExistingPeriod = By.xpath("(//a[@ng-click='choosePeriod(p)'])[1]");
	By periodCount = By.xpath("//li[@ng-repeat='p in periods']");
	public By selectUserRate = By.xpath("//div[@form-id='masterSetFormModel.formId']//ul[@id='-list']");
	public By selectFirstElement = By.xpath("//div[@form-id='masterSetFormModel.formId']//ul[@id='-list']/li[1]");
	By firstRowDetail = By.xpath("//tr[@ng-repeat='entry in selectedPeriodEntries'][1]");
	By exportButton = By.xpath("//div[@ng-show='selectedUserRateSet && selectedUserRateSet.id']//i[@class='left fa fa-cloud-download']");		
	
	
	By rateSheetSearch = By.xpath("//div[@id='ratesheetSection']//input[@title='Type search criteria and click enter to search'][@placeholder='Search Rate Sheets']");
	By rateSheetsPlugin = By.xpath("//*[@id='nav']//a[text()='Rate Sheets']");
	
	
	// Method to select PPS Group Type,,Add validation for select drop down
	public void selectGroupType(String groupName, By selectGroupDD, By openPageValidation)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		By SelectedDRGGroup = By.xpath("//ul[@class='dropdown-menu']//a[@role='menuitem'][contains(.,'"+groupName+"')]");
		
		click_button(groupName+"Drop Down", selectGroupDD);// click on select group drop down 
		clickAddButton("Selected " + groupName, groupName ,SelectedDRGGroup,openPageValidation);// click on selected group and validating that particular page is opened	
	}
	
	
	//Method for navigating to tab
	public void tabNavigate(String tabName)
	{
		By tabNavigate = By.xpath("//div[@tabs='tabs']//a[contains(.,'"+tabName+"')]");
		
		click_button(tabName+" Tab", tabNavigate);
	}
	
	
	public By DRGUserRateSetName = By.xpath("//div[@model='userRateSet.name']//input[@id='userRateSetName']");
	public By effectiveDate = By.xpath("//div[@model='userRateSet.startDate']//input[@id='startDateDrgUserRate']");
	public By terminationDate = By.xpath("//form[@id='addDrgUserRatePeriod']//input[@id='stopDate']");
	
	
	//Method to add details to create DRG USer Rate Set
	public void addDRGUserRateSetDetails()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
		
		enter_text_value("DRG User Rate Set", DRGUserRateSetName, oParameters.GetParameters("DRGUserRateSetName") + System.currentTimeMillis());// entering DRG User Rate Set Name
		enter_text_value("Effective Date", effectiveDate, oParameters.GetParameters("EffectiveDate"));// entering effective date 	
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		
		facilityName("PPS", "Apollo srn facility");
		oParameters.SetParameters("CreatedUserRateSetName", get_attribute_value("createdUserRateSetValue", DRGUserRateSetName, "value"));
	}		
	
	
	By iFrameDiv = By.xpath("//legend[contains(.,'Import DRG Rate Table')]");
	
	
	//This method is used to iframes
	public void iframeSwitch(String iframeName, String chooseFile, By iframeElement, By chooseFileElement) throws InterruptedException, IOException
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
		
		driver.switchTo().frame(iframeName);
		if(IsDisplayed("Choose Button", chooseButton))
		{
			dclick_button(chooseFile, chooseFileElement);
			//click_button(chooseFile, chooseFileElement);
			fixed_wait_time(10);
			oReport.AddStepResult("choose File button", "Clicking on Choose File button", "SCREENSHOT");
		}
	}

	
	By UserRateInList = By.xpath("//li[@ng-click='selectUserRateSet(set)']");
	By openedUserRateSet = By.xpath("//div[@class='pad-l-10 col-md-6 col-md-5 col-sm-6 large-header ng-binding']");
	
	
	//Method to search PPS Group
	public void searchPPS(String placeholder, String existingUserRateSetName, By openedSetElement, By searchedResult)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
		fixed_wait_time(3);		
		By searchPPSGroup = By.xpath("//input[@placeholder='"+placeholder+"']");
		enter_text_value("Search for existing User Set", searchPPSGroup, existingUserRateSetName);
		fixed_wait_time(6);
		click_button("Clicking on searched USer Rate Set", searchedResult);
		
		if(get_text(searchedResult).equalsIgnoreCase(get_text(openedSetElement)))
			oReport.AddStepResult("Search Result", "Searched user rate"+" "+get_text(searchedResult)+" "+"equal to opened user rate set "+" "+get_text(openedSetElement), "PASS");
		else
			oReport.AddStepResult("Search Result", "Searched user rate"+" "+get_text(searchedResult)+"is not equal to opened user rate set "+" "+get_text(openedSetElement), "FAIL");
	}
	
	
	public By drgCode = By.xpath("//div[@class='workflow-modal-content col-lg-12 col-md-12 col-sm-12']//input[@id='drgCode']");
	public By soi = By.xpath("//div[@class='workflow-modal-content col-lg-12 col-md-12 col-sm-12']//input[@id='soi']");
	public By weight = By.xpath("//div[@class='workflow-modal-content col-lg-12 col-md-12 col-sm-12']//input[@id='weight']");
	By payerPaymentRate1 = By.xpath("//div[@class='workflow-modal-content col-lg-12 col-md-12 col-sm-12']//input[@id='pyrPmtRate1']");
	public By los = By.xpath("//div[@class='workflow-modal-content col-lg-12 col-md-12 col-sm-12']//input[@id='stdLenOfStay']");
	public By highTrimDays = By.xpath("//div[@class='workflow-modal-content col-lg-12 col-md-12 col-sm-12']//input[@id='highTrimDays']");
	public By lowTrimDays = By.xpath("//div[@class='workflow-modal-content col-lg-12 col-md-12 col-sm-12']//input[@id='lowTrimDays']");
	
	
	//Method to add User Rate Set Entry
	public void addRateSetEntry()
	{
		enter_text_value("DRG Code Value", drgCode, oParameters.GetParameters("DRGCode"));// entering DRG Code
		enter_text_value("SOI Value", soi, oParameters.GetParameters("SOI"));// entering SOI value
		enter_text_value("Weight Value", weight, oParameters.GetParameters("Weight"));//entering weight value
		enter_text_value("Payer Payment Rate1 Value", payerPaymentRate1, oParameters.GetParameters("PayerPaymentRate1"));//entering payer payment rate1 value
		enter_text_value("High Trim Days", highTrimDays, oParameters.GetParameters("HighTrimDays"));
		enter_text_value("Low Trim Days", lowTrimDays, oParameters.GetParameters("LowTrimDays"));
		enter_text_value("Length of Stay Value", los, oParameters.GetParameters("LOS"));//entering payer payment rate1 value
	}


	By addFilter = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height scroll-auto']//span[contains(.,'Add Filter')]");
//	By optionFieldFilters = By.xpath("//div[@id='filter-drg']//a[@data-template='availableFieldsSelect']");
//	By textToFilter = By.xpath("//div[@id='filter-drg']//div//input[@class='text'][@title='Enter text to filter and press enter.']");
	By optionFieldFilters = By.xpath("//li[@class='multi-filter-item pull-left']//a[@class='filter-label hand-cursor pull-left ng-scope']");
	By textToFilter = By.xpath("(//input[@class='text'][@title='Enter text to filter and press enter.'])[3]");
	By clearAllFilter = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height scroll-auto']//a[@title='Clear All Filters']");
	
	
	//Method to validate filters
	public void filter(String filterField, String valueToEnter, By filterIcon, String ppsType)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("RuleNameBefore", get_field_value("Rule Name", rule1));
		oParameters.SetParameters("QGNameBefore", get_field_value("QG Name", rule2));
		oParameters.SetParameters("PPSNameBefore", get_field_value("PPS Grouping Definition Name", rule3));		
				
		oParameters.SetParameters("RecordsBeforeFilter", oParameters.GetParameters("noOfRecordsInPortal"));	
		By filtersField = By.xpath("//li[@ng-click='selectFilterField(f)'][contains(.,'"+filterField+"')]");
		fixed_wait_time(4);
		click_button("Add Filter", filterIcon);
		
		if(IsDisplayed("Choose option field", optionFieldFilters))
		{
			click_button("Optional field filter drop down", optionFieldFilters);
			click_button("Filter Field", filtersField);
			
			if(filterField.equalsIgnoreCase("PPS"))			
				enter_text_value("Enter Filter Text", textToFilter, oParameters.GetParameters("PPSNameBefore"));
			else if(filterField.equalsIgnoreCase("Qualification"))			
				enter_text_value("Enter Filter Text", textToFilter, oParameters.GetParameters("QGNameBefore"));
			else			
				enter_text_value("Enter Filter Text", textToFilter, oParameters.GetParameters("RuleNameBefore"));
			
			performKeyBoardAction("ENTER");
		}
				
		oParameters.SetParameters("RuleName", get_field_value("Rule Name", rule1));
		oParameters.SetParameters("QGName", get_field_value("QG Name", rule2));
		oParameters.SetParameters("PPSName", get_field_value("PPS Grouping Definition Name", rule3));	
				
		if(filterField.equalsIgnoreCase("PPS"))
		{
			if(oParameters.GetParameters("PPSNameBefore").equalsIgnoreCase(oParameters.GetParameters("PPSName")))
				oReport.AddStepResult("Filter Validation", "PPS Filter is applied and respective record is displayed", "PASS");
			else
				oReport.AddStepResult("Filter Validation", "PPS Filter is applied but Filtered records are not displayed", "FAIL");				
		}
		else if(filterField.equalsIgnoreCase("Qualification"))
		{
			if(oParameters.GetParameters("QGNameBefore").equalsIgnoreCase(oParameters.GetParameters("QGName")))
				oReport.AddStepResult("Filter Validation", "Qualification Group Name Filter is applied and respective record is displayed", "PASS");
			else
				oReport.AddStepResult("Filter Validation", "Qualification Group Name Filter is applied but Filtered records are not displayed", "FAIL");	
		}
		else
		{
			if(oParameters.GetParameters("RuleNameBefore").equalsIgnoreCase(oParameters.GetParameters("RuleName")))
				oReport.AddStepResult("Filter Validation", "Rule Name Filter is applied and respective record is displayed", "PASS");
			else
				oReport.AddStepResult("Filter Validation", "Rule Name Filter is applied but Filtered records are not displayed", "FAIL");
		}	
		
/*		if(oParameters.GetParameters("noOfRecordsInPortal").equalsIgnoreCase(oParameters.GetParameters("RecordsBeforeFilter")))
			oReport.AddStepResult("Filter Validation", "Filter is applied and respective record is displayed", "PASS");
		else
			oReport.AddStepResult("Filter Validation", "Filter is not applied and Filtered records are not displayed", "FAIL");*/
		
		By clearFilters = By.xpath("//div[@id='filter-"+ppsType+"']//i[@class='left fa-lg fa fa-times-circle']");
		
		click_button("Clear Filters", clearFilters);
	}
	
	
	//Method to check User Rate Entry delete scenario
	public void userEntrydelete()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		int beforeEntries = noOfRows(hoverUserEntry);
		System.out.println(beforeEntries);
		mouse_hover("USer Rate entry", hoverUserEntry);
		click_button("Delete USer Rate Entry", deleteUserRate);
		click_button("Delete button on pop-up", popUpDeleteButton);
		fixed_wait_time(5);
		int afterEntries = noOfRows(hoverUserEntry);
		System.out.println(afterEntries);
		
		if(beforeEntries != afterEntries && IsDisplayed("Add USer Rate Set Entry Page", addUserRateSetEntry))
			oReport.AddStepResult("Delete Rate Entry", "Clicked on delete icon and User Rate Set Entry is deleted successfully", "PASS");	
		else
			oReport.AddStepResult("Delete Rate Entry", "Clicked on delete icon, but expected page is not displayed", "FAIL");
	}
	
	
	By editUserRateIcon = By.xpath("//div[@ng-show='selectedUserRateSet && selectedUserRateSet.id']//i[@class='left fa fa-pencil']");
	By editPanel = By.xpath("//div[@id='editEntryPanel']");
	By editUserRateName = By.xpath("//input[@id='userRateSetName']");
	By userRateName = By.xpath("//div[@class='pad-l-10 col-md-6 col-md-5 col-sm-6 large-header ng-binding']");
	By editUserRateWindow = By.xpath("//div[@id='addUserRateSetModal']//div[@class='modal-content']");
	
	
	//Method to check edit scenario
	public void editScenario()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		if(IsDisplayed("Edit User Rate Window", editUserRateWindow))
		{
			System.out.println(get_field_value("Edit User Rate Name", editUserRateName));
			oParameters.SetParameters("PreviousUserRateName", get_field_value("Edit User Rate Name", editUserRateName));
			enter_text_value("Modify DRG User Rate Set Name", editUserRateName, oParameters.GetParameters("DRGUserRateSetName")+System.currentTimeMillis());
			click_button("Save Button", saveEffectivePeriod);
			oParameters.SetParameters("ModifiedUserName", get_text(userRateName));
			if(oParameters.GetParameters("PreviousUserRateName").equalsIgnoreCase(oParameters.GetParameters("ModifiedUserName")))
				oReport.AddStepResult("Modify User Rate Name", "User Rate Set Name is not modified", "FAIL");
			else
				oReport.AddStepResult("Modify User Rate Name", " User Rate Set Name is modified: Old User Rate Set Name " + oParameters.GetParameters("PreviousUserRateName") +"to" +" "+"' New User Rate Set Name " + get_text(userRateName), "PASS");					
		}
		else
		{	
			String[] existingEntry = get_text(selectedPPSGroup).split("\\s");
			//System.out.println(existingEntry);
			//String beforeModification[] = existingEntry.split("\\s"); 
			
			click_button("DRG Code in Edit mode", selectedPPSGroup);
			enter_text_value("Edit Weight", editPPSWeight, oParameters.GetParameters("EditWeight"));
			enter_text_value("Entering LOS value in edit panel", enterLOS, oParameters.GetParameters("EditLOS"));
			click_button("saveButton", editSaveButton);
			
			String[] modifiedEntry = get_text(selectedPPSGroup).split("\\s");
			//System.out.println(modifiedEntry);
			//String afterModification[] = modifiedEntry.split("\\s");
					
			if(existingEntry[2].equals(modifiedEntry[2]) && existingEntry[6].equals(modifiedEntry[6]))
				oReport.AddStepResult("Edit DRG Code Entry","User Rate Set Entry is not modified successfully","Fail");
			else
				oReport.AddStepResult("Edit DRG Code Entry","User Rate Set Entry is modified successfully","Pass");		
		} 
	}
	
	
	By choosePeriod = By.xpath("//div[@periods='selectedUserRateSet.drgUserRatePeriods']//li[@class='hand-cursor ng-scope']");
	By statusChangeValidation = By.xpath("//div[@periods='selectedUserRateSet.drgUserRatePeriods']//li[@class='hand-cursor ng-scope']//span[contains(.,'Active')]");
	
	
	//Method to change the User Rate Status
	public void userRateStatusChange()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		click_button("Effective Period Drop down", effectivePeriod);
		refresh_page();
		mouse_hover("Period Date", choosePeriod);
		click_button("Edit Period icon", editEffectivePeriod);
		
		if(IsElementDisplayed("Edit Effective Period", editEffectiveWindow) && IsElementEnabled("Save button in edit period window", saveEffectivePeriod))
		{
			enter_text_value("Termination Date", terminationDate, oParameters.GetParameters("TerminationDate"));
			click_button("User Rate Status", userRateStatus);
			click_button("Save Termination Date and change User Rate status", saveEffectivePeriod);
			fixed_wait_time(5);
			click_button("Effective Period Drop down", effectivePeriod);
			
			if(IsDisplayed("User Rate Status Change", statusChangeValidation))
				oReport.AddStepResult("Change User Rate Status", "Clicked on edit period icon, edit effective period window is displayed and User Rate Status is changed from Inactive to Active without any error", "PASS");	
			else 
				oReport.AddStepResult("Change User Rate Status", "Clicked on edit period icon, but User Rate Status cannot be saved since save button is disabled", "FAIL");
		}
	}
	
	
	public By periodCopyCheckBox = By.xpath("//div[@class='row mar-l-0 mar-t-10 ng-scope']//input[@id='copyClosed']");
	public By selectPeriodToCopy = By.xpath("//select[@id='copyPeriod']");
	public By selectPeriodToCopy_EP = By.xpath("//select[@id='copyPeriodSelect']");
	By copyDetailsFromExistingUR = By.xpath("//div[@ng-hide='copyPeriod.copyExistingPeriod']");
	
	
	//Method to add new effective period
	public void newEffectivePeriod()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		//click_button("Effective Period Drop down", effectivePeriod);
		int beforeCount = noOfRows(periodCount);
		click_button("Add Period icon", addPeriod);
		
		if(IsElementDisplayed("Add Effective Period window", addEffectivePeriodWindow))
		{
			oParameters.SetParameters("newEffectivePeriod", get_specified_date(30,"MM/DD/YYYY"));
			enter_text_value("Add new Effective Date", addNewEffectivePeriod,oParameters.GetParameters("newEffectivePeriod"));
			click_button("Copy Details From Existing User Rate Set", copyDetailsFromExistingUR);
			copyFromExisting("CheckBox Button","Search and select existing User Set", "select Period", periodCopyCheckBox, selectExistingUserRate, selectPeriodToCopy_EP,selectUserRate, selectFirstElement, oParameters.GetParameters("SearchUserRates"));
			//fixed_wait_time(3);
			waitForIsClickable(saveButton, "saveButton");
			clickSaveButton("saveButton", "User Rate Set Window", saveButton, UserRateSetWindow);
			int afterCount = noOfRows(periodCount);
			
			if( beforeCount <= afterCount && IsDisplayed(" DRG code entry table", entryTable))
				oReport.AddStepResult("Validation of New period", "Clicked on effective period drop down, clicked on Add Period icon, filled all the details and clicked on save ", "PASS");
			else
				oReport.AddStepResult("Validation of New period", "Clicked on effective period drop down, clicked on Add Period icon, filled all the details but not saved ", "FAIL");	
		}
	}
	
	
	//Method to delete Period
	public void deletePeriod(By periodDropDown, By count)
	{
		click_button("Effective Period Drop down", periodDropDown);
		int countBeforeDelete = noOfRows(count);
		System.out.println(countBeforeDelete);
		mouse_hover("existing period", hoverOverExistingPeriod);
		click_button("Delete Period icon", deletePeriod);
		click_button("Delete button on pop-up", popUpDeleteButton);
		int countAfterDelete = noOfRows(count);
		
		if(countBeforeDelete >= countAfterDelete)
			oReport.AddStepResult("Delete Period", "Clicked on delete icon and period is deleted successfully", "PASS");
		else
			oReport.AddStepResult("Delete Period", "Clicked on delete icon, but period is not deleted", "FAIL");
	}
	

	//Method to create new User Rate Set and to validate the cancel scenario 
	public void validateNewUserRateSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}		
		fixed_wait_time(10);
		if(IsElementDisplayed("Add a User Rate Set Entry", ppsDRGPage))
			oReport.AddStepResult("New User Rate Set", oParameters.GetParameters("CreatedUserRateSetName")+" is created without any error", "PASS");
		else
			oReport.AddStepResult("New User Rate Set", "New User Rate Set is not created", "FAIL");
	}
	
	
	//Method to create new User Rate Set by copying details from existing User Rate Set
	public void validateSetWtihExistingEntry()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}

		//oParameters.SetParameters("CreatedUserRateSetName1", get_attribute_value("createdUserRateSetValue1", DRGUserRateSetName, "value"));

		if(IsElementDisplayed("Entry table", UserRateSetTable))
			oReport.AddStepResult("New User Rate with Existing Entry details", oParameters.GetParameters("CreatedUserRateSetName")+"is created with copy of existing Entries", "PASS");
		else
			oReport.AddStepResult("New User Rate with Existing Entry details", "New User Rate Set is not created", "FAIL");
	}
	
	
	By fileFailuerTab = By.xpath("//div[@id='dialog'][contains(.,'User rate table file uploaded. But there are few validation failures')]");
	By dialogBoxButton = By.xpath("//div[@id='dialog'][contains(.,'User rate table ')]//input");
	//Method to create new User Rate Set by importing the file 
	public void userRateSetByImporting()
	{

		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}				
		try 
		{
			//iframeSwitch("importUserRateIframe","Choose the file to Import", iframeSwitch, chooseButton);
			driver.switchTo().frame("importUserRateIframe");
			fixed_wait_time(2);
			click_button("Browse Button", chooseButton);
			
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\DRGUserRate.exe");
			fixed_wait_time(2);
			oReport.AddStepResult("choose File", "Choose File", "SCREENSHOT");
		}  
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		driver.switchTo().defaultContent();
	}
	
	
	public void validateImportScenaio()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("User Rate File Upload Validation", fileFailuerTab))
		{
			click_button("Close validation tab", dialogBoxButton);
			oReport.AddStepResult("User Rate File Upload Validation", "User Rate table file uploaded. But there are few validation failures ", "INFO");
		}
		else
			oReport.AddStepResult("User Rate File Upload Validation", "User Rate File is uploaded without any validation failures", "INFO");
		
		if(IsElementDisplayed("Entry table", UserRateSetTable))
			oReport.AddStepResult("New User Rate with Existing Entry details", oParameters.GetParameters("CreatedUserRateSetName2")+"is created with copy of existing Entries", "PASS");
		else
			oReport.AddStepResult("New User Rate with Existing Entry details", "New User Rate Set is created but User Rate Entry is not copied from existing User Rate Set", "FAIL");
	}
	
	
	By importButton = By.xpath("//div[@ng-show='selectedUserRateSet && selectedUserRateSet.id']//i[@class='left fa fa-cloud-upload']");
	By importWindow = By.xpath("//div[@class='workflow  modal-small'][contains(.,'Import DRG User Rate Set')]");
	By importDownloadSample = By.xpath("//a[@href='sampleFiles/dowloadFile/sampleDRGTable.xlsx']");
	public By chooseFile = By.xpath("//input[@id='DRGuserRateEntryIframe']");
	
	
	//Method for Importing file
	public void importScenario()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Import Button", importButton);
		
		if(IsDisplayed("Import window", importWindow))
		{
			click_button("Download Sample Link", importDownloadSample);
		
			if(isFileDownloaded("sampleDRGTable.xlsx"))
			{
				LinkedList <String> importcolumns = new LinkedList<String>();
				for (int i=0;i<15;i++)
				{
					String excelData = oExcelData.getExcelData("Rate Table", 1, i, oParameters.GetParameters("downloadFilepath")+"/"+"sampleDRGTable.xlsx");
					importcolumns.add(excelData);
				}
				
				System.out.println(importcolumns.size());
				oReport.AddStepResult("Download Sample File", "Clicked download sample file and file is downloaded", "SCREENSHOT");
				deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+"sampleDRGTable.xlsx");
				String[] Columns = {"DRG code","Severity of Illness","Description","Weight","Payer payment rate1 ($)","Payer payment rate2 ($)","Payer payment rate3 ($)","Total payer payment rate ($)","Low day trim","High day trim","Standard length of stay","Standard total charges ($)","Low dollar trim ($)","High dollar trim ($)","Per diem amount ($)"};		
				LinkedList requiredColumns = new LinkedList(Arrays.asList(Columns));
				
				if(importcolumns.containsAll(requiredColumns))
					oReport.AddStepResult("Download template validation", "Clicked on download sample link and verified that columns present in download file and columns present in valid import file is same ", "PASS");
				else 
					oReport.AddStepResult("Download template validation", "Columns present in downloaded import template doesn't match with columns present in valid import file", "FAIL");
			}
			else
				oReport.AddStepResult("Download Sample File", "Sample file is not downloaded", "FAIL");
		}
		else
			oReport.AddStepResult("Import Window", "Clicked on import icon but import window did not displayed", "FAIL");
		
		click_button("Choose File", chooseFile);
			
		try 
		{
			Runtime.getRuntime().exec("C:/CCM/AutoIt/DRGUserRate.exe");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
		click_button("Save Import File", saveButton);
	}
	
	
	//Method to Add User Rate Set Entry 
	public void addUserRateEntr()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		
	}
	
	
	//Method for validating User Rate Entry
	public void validateUserRateEntry()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		

		String[] enteredEntry = {oParameters.GetParameters("DRGCode"),oParameters.GetParameters("SOI"),oParameters.GetParameters("Weight"),oParameters.GetParameters("PayerPaymentRate1"),oParameters.GetParameters("HighTrimDays"), oParameters.GetParameters("LowTrimDays"), oParameters.GetParameters("LOS")+"000"};
		String[] existingEntry = get_text(selectedPPSGroup).split("\\s");
		
		if(IsDisplayed("Entry table", UserRateSetTable) && Arrays.equals(enteredEntry, existingEntry))
			oReport.AddStepResult("Validation of User Rate Entry", "Entries are added to selected User Rate Set successfully", "PASS");
		else
			oReport.AddStepResult("Validation of User Rate Entry", "In selected User Rate Set,Entered entries are different from Existing entries", "FAIL");
	}
	
	
	//Method for validating pagination
	public void validatePagination(String pageNumber, String firstRowData, By firstRowDetail)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		By selectedPageNumber = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height scroll-auto']//ul[@total-items='totalItems']//li[contains(.,'"+pageNumber+"')]");
		
		//To check if respective button is displayed and enabled to perform click operation
		if(!IsElementDisplayed(pageNumber, selectedPageNumber) && !IsElementEnabled(pageNumber, selectedPageNumber))
		{
			//Adding step result to report
			oReport.AddStepResult(pageNumber, pageNumber + " not Displayed/Enabled", "FATAL");
			return;
		}
		else
		{
			//Getting the first row details before going to other page
			String detailsBeforePagination = get_text(firstRowDetail);
						
			//To click on Page button
			click_button(pageNumber, selectedPageNumber);	
			waitFor(firstRowDetail, "First row detail");
			
			//Getting the first row details after going to other page
			String detailsAfterPagination = get_text(firstRowDetail);	
					
			//To check if particular page is displayed or not
			if(!detailsBeforePagination.equalsIgnoreCase(detailsAfterPagination))
				oReport.AddStepResult("Clicked on ", "Page"+" " + pageNumber + " and verified that first row data is different", "PASS");
			else
				oReport.AddStepResult("Clicked on ", "Page"+" " + pageNumber + " and verified that firt row data is not different", "FAIL");
		  }		
		
	}
	
	
	By recordsInPortal = By.xpath("//div[@ng-if='selectedUserRateSet && selectedUserRateSet.id']//div[@ng-show='selectedPeriod']");
	
	
	//Method for Export
	public void export() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		String exportFileName = oParameters.GetParameters("ModifiedUserName").replaceAll("\\s+", "");
		click_button("Export icon", exportButton);
		fixed_wait_time(6);
		oParameters.SetParameters("ExportedFileName", exportFileName+".xlsx");
		System.out.println("file download validation");
		if(isFileDownloaded(oParameters.GetParameters("ExportedFileName")))
		{
			int fileRowCount = oExcelData.getRowCount(oParameters.GetParameters("downloadFilepath")+"/"+exportFileName+".xlsx")-1;	 
			//String noOfRecordsInPortal = get_text(recordsInPortal).replace("Codes:", "").trim();
			oParameters.SetParameters("noOfRecordsInPortal", get_text(recordsInPortal).replace("Codes:", "").trim());
			
			if(fileRowCount == Integer.parseInt(oParameters.GetParameters("noOfRecordsInPortal")))
				oReport.AddStepResult("File Name", exportFileName+".xlsx"+" "+" file is downloaded without any error and validated that number of records in portal is equal to number of records in downloaded file", "Pass");
			else
				oReport.AddStepResult("File Name", "User Rate file is downloaded without any error and validated that number of records in portal is not equal to number of records in downloaded file", "FAIL");
		}	
		else 
			oReport.AddStepResult("File Export", "File is not exported", "FAIL");
	}
	
	
	// DRG User Rate Set VR
	public void DRG_UserRateSet() 
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);
		selectGroupType("PPS DRG",SelectGroupDropDown,OpenPageValidation);		
		
		//creating new User Rate Set with cancel scenario
		clickAddButton("Add User Rate Set Button", "User Rate Set Window", AddUserRateSetButton, UserRateSetWindow);
		addDRGUserRateSetDetails();
		cancelScenario("Add User Rate Set window cancel button", "Pop-up window cancel button", "Pop Up window discard button", cancelButton, popUpCancelButton, popUpDiscardButton);
		clickAddButton("Add User Rate Set Button", "User Rate Set Window", AddUserRateSetButton, UserRateSetWindow);
		addDRGUserRateSetDetails();
		clickSaveButton("saveButton", "User Rate Set Window", saveButton, UserRateSetWindow);
		validateNewUserRateSet();
		
		//Scenario for add new Rate Entries	
		clickAddButton("Add User Rate Set Entry link", "Add User Rate Set Entry Window", addUserRateSetEntry, addUserRateSetEntryWindow);
		addRateSetEntry();
		click_button("saveButton", saveButton);
		validateUserRateEntry();
				
		//Scenario for Edit PPS User Rate Set and User Rate Entry delete
		editScenario();		
		userEntrydelete();
		
		//creating new User Rate Set by importing existing set
		clickAddButton("Add User Rate Set Button", "User Rate Set Window", AddUserRateSetButton, UserRateSetWindow);
		addDRGUserRateSetDetails();
		userRateSetByImporting();
		By SaveButton2 = By.xpath("//div[@id='addUserRateSetModal']//input[@value='Save']");
		clickSaveButton("saveButton", "User Rate Set Window", SaveButton2, UserRateSetWindow);
		validateImportScenaio();
		
		//creating new User Rate Set by copying Rate Set Entries from existing User Rate Set
		clickAddButton("Add User Rate Set Button", "User Rate Set Window", AddUserRateSetButton, UserRateSetWindow);
		addDRGUserRateSetDetails();
		copyFromExisting("CheckBox Button","Search and select existing User Set", "Select Period", copyCheckBox, searchExistingElement, selectPeriodToCopy,selectUserRate, selectFirstElement, oParameters.GetParameters("selectUserRateSet"));
		clickSaveButton("saveButton", "User Rate Set Window", SaveButton2, UserRateSetWindow);
		validateSetWtihExistingEntry();

		//Scenario for edit User Rate Set Name
		click_button("Edit User Rate icon", editUserRateIcon);
		editScenario();
		
		//Method for  search User Rate Set and Changing User Rate Status in edit effective period window
		searchPPS("Search User Rates",oParameters.GetParameters("ModifiedUserName"), openedUserRateSet, searchResult);// search for existing User Rate Set Entry
		userRateStatusChange();		
		
		//Method to add new Effective Period and copying details from existing User Rate Set
		newEffectivePeriod();
		
		//searchPPS("Search User Rates", "DRG User Rate Set test1533902014622",openedUserRateSet, searchResult);
		//Method for importing the file
		importScenario();

		//Method for validating filter functionality 
		filter("DRG Code",oParameters.GetParameters("FilterDRG"),addFilter,"drg");
		filter("LOS", oParameters.GetParameters("FilterLOS"),addFilter,"drg");
		clear_filter("Clear all Filter", clearAllFilter);
		filter("Weight", oParameters.GetParameters("FilterWeight"),addFilter,"drg");
		clear_filter("Clear all Filter", clearAllFilter);
		validatePagination("5","First Row Detail",firstRowDetail);
		filter("DRG Code", get_random_number(2),addFilter,"drg");
		clear_filter("Clear all Filter", clearAllFilter);
		
		//Method to validate pagination
		validatePagination("2","First Row Detail",firstRowDetail);
		validatePagination("Prev","First Row Detail",firstRowDetail);
		validatePagination("Next","First Row Detail",firstRowDetail);
	
		//Method for Export data
		export();
		deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+oParameters.GetParameters("ExportedFileName"));
		
		//Method to delete the period
		deletePeriod(effectivePeriod,periodCount);
		
		//Method to navigate to Rate Sheet
		navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", rateSheetsPlugin, rateSheetSearch);
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "DRGRateSheetTestData.xlsx", "RateSheet", "");
		int rowcount = oExcelData.getRowCount1("RateSheet", "C:\\CCM\\SupportingFiles\\DRGRateSheetTestData.xlsx");		
		
		// Creating Tiered Rate Type Terms		
		for(int i=1;i<rowcount;i++)
		{
			oRateSheetLibrary.addTermButton(String.valueOf(i),oParameters.GetParameters("RateSheetName"+i),oParameters.GetParameters("SectionName"+i));
			oRateSheetLibrary.selectRateType(String.valueOf(i));
			oRateSheetLibrary.termSaveButton(String.valueOf(i));
		}		
		
		//Method to delete particular section
		oRateSheetLibrary.deleteSection("Inpatient");
		 
		//Method for logout
		logout();
	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public By grouperOption = By.xpath("//div[@tabs='tabs']//a[contains(.,'Grouper Options')]");
	public By addGrouperOptionWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add DRG Grouper Option Set']");
	public By addGrouperOptionLink = By.xpath("//div[@ng-click='addDrgGrouperOptionsSet()']");
	public By checkBox = By.xpath("//form[@id='addDrgGrouperOptionsSet']//input[@id='copyClosed']");
	By selectGrouperOptionSet = By.xpath("//form[@id='addDrgGrouperOptionsSet']//input[@id='copyAddGrouperOptionSet']");
	By choosePeriodToCopy = By.xpath("//form[@id='addDrgGrouperOptionsSet']//select[@id='copyPeriod']");
	public By grouperOptionSetName = By.xpath("//input[@id='grouperOptionsSetName']");
	public By GO_effectiveDate = By.xpath("//div[@model='grouperOptionsSet.startDate']//input[@id='startDateDrgGrouperoptionSet']");
	public By selectDRGGrouperName = By.xpath("//select[@id='drgGrouperName']");
	By grouperOptionSet = By.xpath("//li[@ng-click='selectGrouperOptionSet(set)']");
	By openedGrprOptnName = By.xpath("//div[@class='col-md-6 col-md-5 col-sm-6 xl-header pull-left ng-binding']");
	By searchResult_GO = By.xpath("//ul[@class='data-list drillable-list portal-left-sidebar portal-submenu-left-sidebar']//li[1]");
	
	
	//Method for creating new Grouper options
	public void newGrouperOptions(String grouperName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickAddButton("Add Grouper Option Link", "Add Grouper Option Window", addGrouperOptionLink, addGrouperOptionWindow);
		enter_text_value("Grouper Option Set Name", grouperOptionSetName, oParameters.GetParameters("GrouperOptionSet")+System.currentTimeMillis());
		oParameters.SetParameters("GrouperOptionSetName", get_field_value("Grouper Option Set Name", grouperOptionSetName));
		enter_text_value("Effective Date", GO_effectiveDate, oParameters.GetParameters("EffectiveDate"));
		
		facilityName("PPS", "Apollo srn facility");
		click_button("Grouper Name Drop Down", selectDRGGrouperName);
		
		if(grouperName.equalsIgnoreCase("AP"))
		{
			select_option("DRG Grouper Name", selectDRGGrouperName, "0");
			oReport.AddStepResult("Entering Grouper Option details", "Clicked on add Grouper Option link and entered all the details", "SCREENSHOT");
		}	
		else 
		{
			select_option("DRG Grouper Name", selectDRGGrouperName, "1");
			oReport.AddStepResult("Entering Grouper Option details", "Clicked on add Grouper Option link and entered all the details", "SCREENSHOT");
		}
	}
	
	
	By DRGGrprOptnSetName = By.xpath("//div[@model='grouperOptionsSet.name']//input[@id='grouperOptionsSetName']");
	By firstAttributeValue = By.xpath("//tr[@ng-click='editAtributes(atribute)'][1]//li[@ng-repeat='selectedValue in atribute.values track by $index']");
	
	
	//Method for creating new Grouper Option Set using existing set
	public void newGrprOptnSetwithExisitngSet(String grouperName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		

		if(grouperName.equals("AP"))
			copyFromExisting("CheckBox Button for Copying details from existing set", "Select existing Grouper Option Set", "Select Period to Copy", checkBox, selectGrouperOptionSet, choosePeriodToCopy,selectUserRate, selectFirstElement, oParameters.GetParameters("selectGrouperOptionSet1"));
		else	
			copyFromExisting("CheckBox Button for Copying details from existing set", "Select existing Grouper Option Set", "Select Period to Copy", checkBox, selectGrouperOptionSet, choosePeriodToCopy,selectUserRate, selectFirstElement, oParameters.GetParameters("selectGrouperOptionSet"));

		oParameters.SetParameters("CreatedGrouperOptnSetName", get_attribute_value("CreatedGrouperOptnSetName", DRGGrprOptnSetName, "value"));
		oReport.AddStepResult("Copy from existing details", "Clicked on add Grouper Option link, entered all the details and selected value for copy from existing details field", "SCREENSHOT");
		clickSaveButton("saveButton", "User Rate Set Window", saveEffectivePeriod, UserRateSetWindow);//using same Xpath as is in DRG User Rate 
		
		if(IsDisplayed("Attribute table", attributeTable) && IsDisplayed("First Attribute Value", firstAttributeValue))
			oReport.AddStepResult("New Grouper Option with Existing Entry details", oParameters.GetParameters("CreatedGrouperOptnSetName")+"is created with copy of existing Entries", "PASS");
		else
			oReport.AddStepResult("New Grouper Option with Existing Entry details", "New Grouper Option Set is not created", "FAIL");
	}
	
	
	By openedGrouperOption = By.xpath("//div[@class='col-md-6 col-md-5 col-sm-6 xl-header pull-left ng-binding']");
	By attributeTable = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height']");
	
	
	//Method for validating whether new Grouper Option is created
	public void newGrouperOptionValidation()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickSaveButton("Save button", "Add Grouper Option Window", saveButton, addGrouperOptionWindow);
		
		if(oParameters.GetParameters("GrouperOptionSetName").equalsIgnoreCase(get_text(openedGrouperOption)) && IsDisplayed("Created new Grouper Option set", openedGrouperOption) && IsDisplayed("Attribute Values Table", attributeTable))
			oReport.AddStepResult("New Grouper Option Set", "Clicked on new Grouper Option Set and verified that new Grouper Option Set is created.", "PASS");
		else
			oReport.AddStepResult("New Grouper Option Set", "New Grouper Option Set is created but all elements are not loaded completely", "FAIL");
	}
	
	
	By attributeCount = By.xpath("//tr[@ng-click='editAtributes(atribute)']");
	By checkedValue = By.xpath("//input[@ng-model='drgGrouperValues.value'][@checked='checked']");
	By valueXpath = By.xpath("//input[@ng-model='drgGrouperValues.value']");
	By saveEdit = By.xpath("//button[@id='saveDRGGrouperOptionsEntryId'][@value='Save']");
	
	
	//Method for validating whether new Grouper Option is created
	public void editScenario_GO(String grouperName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(grouperName.equalsIgnoreCase("AP"))
		{
			List<WebElement> attributesCount = convertToWebElements(attributeCount);
			
			for(int i=1; i<=attributesCount.size();i++)
			{
				By attributeValue = By.xpath("//tr[@ng-click='editAtributes(atribute)']["+i+"]");
				By attributeName = By.xpath("//tr[@ng-click='editAtributes(atribute)']["+i+"]//td[@class='ng-binding']");
				By previousValue = By.xpath("//tr[@ng-click='editAtributes(atribute)']["+i+"]//li[@ng-repeat='selectedValue in atribute.values track by $index']");
				
				//condition to check whether value is selected or not
				if(IsDisplayed("Value is selected", previousValue))
					oParameters.SetParameters("PerviousValueChecked", get_field_value("Checked Value", previousValue));
				else
					oParameters.SetParameters("PerviousValueChecked", "No Values Selected");				
				
				click_button("Attribute Value", attributeValue);
				By selectValue = By.xpath("//form[@id='addEditDrgGrouperOptionsEntry']//p[4]//input");
				waitFor(selectValue, "Attribute Value");
				click_button("Attribute Value", selectValue);
				click_button("Save modified value", saveEdit);
				oParameters.SetParameters("CurrentCheckedValue", get_field_value("Checked Value", previousValue));
				
				if(!oParameters.GetParameters("PerviousValueChecked").equalsIgnoreCase(oParameters.GetParameters("CurrentCheckedValue")))
					oReport.AddStepResult("Edit Attribute Value", "Clicked on" +get_field_value("Name of the Attribute", attributeName)+"and changed the value from"+" "+oParameters.GetParameters("PerviousValueChecked")+"to"+" "+oParameters.GetParameters("CurrentCheckedValue"), "PASS");
				else
					oReport.AddStepResult("Edit Attribute Value", "Clicked on" +get_field_value("Name of the Attribute", attributeName)+" but old value is"+" "+oParameters.GetParameters("PerviousValueChecked")+" and new value is"+" "+oParameters.GetParameters("CurrentCheckedValue"), "FAIL");
			}
		}
		else
		{
			List<WebElement> attributesCount = convertToWebElements(attributeCount);
			
			for(int i=1; i<=attributesCount.size();i++)
			{
				By attributeValue = By.xpath("//tr[@ng-click='editAtributes(atribute)']["+i+"]");
				By attributeName = By.xpath("//tr[@ng-click='editAtributes(atribute)']["+i+"]//td[@class='ng-binding']");
				By previousValue = By.xpath("//tr[@ng-click='editAtributes(atribute)']["+i+"]//li[@ng-repeat='selectedValue in atribute.values track by $index']");
				
				//condition to check whether value is selected or not
				if(IsDisplayed("Value is selected", previousValue))
					oParameters.SetParameters("PerviousValueChecked", get_field_value("Checked Value", previousValue));
				else
					oParameters.SetParameters("PerviousValueChecked", "No Values Selected");
				
				if(get_field_value("Discharge Option", attributeName).equalsIgnoreCase("Discharge Option") | get_field_value("Payer Logic Indicator", attributeName).equalsIgnoreCase("Payer Logic Indicator"))
				{
					click_button("Attribute Value", attributeValue);
					By selectValue = By.xpath("//form[@id='addEditDrgGrouperOptionsEntry']//p[2]//input");
					click_button("Attribute Value", selectValue);
				}
				else
				{
					click_button("Attribute Value", attributeValue);
					By selectValue = By.xpath("//form[@id='addEditDrgGrouperOptionsEntry']//p[4]//input");
					fixed_wait_time(3);
					click_button("Attribute Value", selectValue);
				}
				
				click_button("Save modified value", saveEdit);
				oParameters.SetParameters("CurrentCheckedValue", get_field_value("Checked Value", previousValue));
				
				if(!oParameters.GetParameters("PerviousValueChecked").equalsIgnoreCase(oParameters.GetParameters("CurrentCheckedValue")))
					oReport.AddStepResult("Edit Attribute Value", "Clicked on" +" "+get_field_value("Name of the Attribute", attributeName)+" "+"and changed the value from"+" "+oParameters.GetParameters("PerviousValueChecked")+"to"+" "+oParameters.GetParameters("CurrentCheckedValue"), "PASS");
				else
					oReport.AddStepResult("Edit Attribute Value", "Clicked on" +" "+get_field_value("Name of the Attribute", attributeName)+" "+" but old value is"+" "+oParameters.GetParameters("PerviousValueChecked")+" and new value is"+" "+oParameters.GetParameters("CurrentCheckedValue"), "FAIL");
			}
		}
	}
	
	
	By editGOname = By.xpath("//div[@id='addDrgGrouperOptionsSetModal']//div[@class='modal-content']");
	By GprOptName = By.xpath("//input[@id='grouperOptionsSetName']");
	By modifedGrprName = By.xpath("//div[@class='col-md-6 col-md-5 col-sm-6 xl-header pull-left ng-binding']");
	By editIcon = By.xpath("//div[@class='pull-left link-btn hand-cursor']//i[@class='left fa fa-pencil']");
	
	
	//Method for editing the name of Grouper Option Set Name
	public void editGrouperOptionSetName(String grouperName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit Icon",editIcon);
		
		if(IsDisplayed("Edit Grouper Option Set Window", editGOname) && grouperName.equalsIgnoreCase("AP"))
		{
			oParameters.SetParameters("PreviousGrouperOptionName", get_field_value("Edit Grouper Option Set Name", GprOptName));
			enter_text_value("Modify Grouper Option Set Name", GprOptName, oParameters.GetParameters("GrouperOptionSet")+System.currentTimeMillis());
			click_button("Save Button", saveEffectivePeriod);
			fixed_wait_time(5);
			oParameters.SetParameters("ModifiedUserName", get_text(modifedGrprName));
			
			if(oParameters.GetParameters("PreviousGrouperOptionName").equalsIgnoreCase(oParameters.GetParameters("ModifiedUserName")))
				oReport.AddStepResult("Modify Grouper Option Set Name", "Grouper Option Set Name is not modified", "FAIL");
			else
				oReport.AddStepResult("Modify Grouper Option Set Name", "Old Grouper Option Set Name " + oParameters.GetParameters("PreviousGrouperOptionName") + " New Grouper Option Set Name " + get_text(modifedGrprName), "PASS");					
		}
		else if(IsDisplayed("Edit Grouper Option Set Window", editGOname) && grouperName.equalsIgnoreCase("APR"))
		{
			System.out.println(get_field_value("Edit Grouper Option Set Window", editGOname));
			oParameters.SetParameters("PreviousGrouperOptionName", get_field_value("Edit Grouper Option Set Name", GprOptName));
			enter_text_value("Modify Grouper Option Set Name", GprOptName, oParameters.GetParameters("GrouperOptionSet")+System.currentTimeMillis());
			click_button("Save Button", saveEffectivePeriod);
			oParameters.SetParameters("ModifiedUserName", get_text(modifedGrprName));
			if(oParameters.GetParameters("PreviousGrouperOptionName").equalsIgnoreCase(oParameters.GetParameters("ModifiedUserName")))
				oReport.AddStepResult("Modify Grouper Option Set Name", "Grouper Option Set Name is not modified", "FAIL");
			else
				oReport.AddStepResult("Modify Grouper Option Set Name", "Old Grouper Option Set Name '" + oParameters.GetParameters("PreviousGrouperOptionName") + "' New Grouper Option Set Name " + get_text(modifedGrprName), "PASS");					
		}
	}
	
	
	By periodIcon = By.xpath("//div[@periods='selectedGrouperPeriodList']//a[@ng-click='showDropdown = !showDropdown']//span[@ng-show='periods.length > 0']");
	By periodDD = By.xpath("//div[@periods='selectedGrouperPeriodList']//a[@ng-click='showDropdown = !showDropdown']");
	By period = By.xpath("//div[@periods='selectedGrouperPeriodList']//li[@class='hand-cursor ng-scope noStatus']");
	By periodEditIcon = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 ng-binding']//i[@ng-hide='true']");
	By editTerminationDate = By.xpath("//div[@model='groupOptionPeriod.stopDate']//input[@id='stopDate']");
	
	
	//Method for editing Effective Period 
	public void editEffectivePeriod()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Click on Effective Period Drop Down", periodIcon);
		oParameters.SetParameters("PreviousDate", get_field_value("Previous Effective Date", periodIcon));
		
		//Validating Period drop down
		if(IsElementDisplayed("Period drop down", periodDD))
		{
			mouse_hover("Hover over Effective Period", period);
			click_button("Period Edit Icon", periodEditIcon);
			oReport.AddStepResult("Effective Period Drop Down", "Clicked on period drop down and clicked on period edit icon", "INFO");
		}
		else
			oReport.AddStepResult("Effective Period Drop Down", "Clicked on period drop down but period drop down is not displayed", "INFO");
		
		//editing the effective period and validating the same
		if(IsElementDisplayed("Edit Effective Period", editEffectiveWindow) && IsElementEnabled("Save button in edit period window", saveEffectivePeriod))
		{
			enter_text_value("Termination Date", editTerminationDate, oParameters.GetParameters("TerminationDate"));
			click_button("Save Edit Effective Period", saveEffectivePeriod);
			oParameters.SetParameters("modifedDate", get_field_value("Current Effective Date", periodIcon));
			
			if(!oParameters.GetParameters("PreviousDate").equalsIgnoreCase(oParameters.GetParameters("modifedDate")))
				oReport.AddStepResult("Change Effective Date", "Clicked on edit period icon, edit effective period window is displayed and Effective Period is modified", "PASS");	
			else 
				oReport.AddStepResult("Change Effective Date", "Clicked on edit period icon, but Effective Period is not modified", "FAIL");
		}
		else
			oReport.AddStepResult("Edit Effective Period Window", "Clicked on Edit period icon but edit Effective Period window is not displayed", "FAIL");
	}
	
	
	By addPeriodIcon = By.xpath("//div[@on-change='selectedGrouperOptionPeriod']//a[@ng-click='addPeriod()']");
	By periodCheckBox = By.xpath("//div[@class='mar-l-0 mar-t-20 ng-scope']//input[@id='copyClosed']");
	By selectExistingGrouperOption = By.xpath("//div[@form-id='periodFormModel.formId']//input[@id='copyGrouperOptionSet']");
	By selectPeriodCopy = By.xpath("//div[@model='copyPeriod.periodId']//select[@id='copyPeriod']");
	By editEffectiveDate = By.xpath("//div[@model='groupOptionPeriod.startDate']//input[@id='startDate']");
	
	
	//Method for adding new period using existing Grouper Option Set
	public void newPeriod()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Click on Effective Period Drop Down", periodIcon);
		
		//Validating Period drop down
		if(IsElementDisplayed("Period drop down", periodDD))
		{
			click_button("Add Period", addPeriodIcon);
			oReport.AddStepResult("Effective Period Drop Down", "Clicked on period drop down and clicked on Add Period", "INFO");
		}
		else
			oReport.AddStepResult("Effective Period Drop Down", "Clicked on period drop down but period drop down is not displayed", "INFO");
		
		//adding new effective period using existing grouper option set and validating the same
		if(IsElementDisplayed("Add Effective Period window", addEffectivePeriodWindow))
		{
			int beforeCount = noOfRows(periodCount);
			enter_text_value("Effective Date", editEffectiveDate, oParameters.GetParameters("NewEffectiveDate"));
			click_button("Copy Details From Existing User Rate Set", copyDetailsFromExistingUR);
			copyFromExisting("CheckBox Button","Search and select existing User Set", "select Period", periodCheckBox, selectExistingGrouperOption, selectPeriodCopy,selectUserRate, selectFirstElement, oParameters.GetParameters("SearchGrouperOption"));
			clickSaveButton("saveButton", "User Rate Set Window", saveButton, UserRateSetWindow);
			int afterCount = noOfRows(periodCount);
			
			if(beforeCount <= afterCount)
				oReport.AddStepResult("Validation of New period", "Clicked on effective period drop down, clicked on Add Period icon, filled all the details and clicked on save ", "PASS");
			else
				oReport.AddStepResult("Validation of New period", "Clicked on effective period drop down, clicked on Add Period icon, filled all the details but not saved ", "FAIL");	
		}
		else
			oReport.AddStepResult("Edit Effective Period Window", "Clicked on Add period icon but Add Effective Period window is not displayed", "FAIL");
	}
	
	
	By deletePopUp = By.xpath("//div[@id='dialog']//div[@class='medium-header bold ng-binding']");
	By deleteElement = By.xpath("//a[@ng-click='deleteDrgGrouperOptionSet(selectedDrgGrouperoption.id)']//i[@class='left fa fa-minus-square']");
	
	//Method for deleting Grouper Option
	public void deleteSet(By deleteElement, String ppsSet)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Delete Icon", deleteElement);
		
		if(IsDisplayed("Delete Pop Up", deletePopUp))
		{
			click_button("Delete Button", popUpDeleteButton);
			if(IsDisplayed("PPS Set Page", OpenPageValidation))
				oReport.AddStepResult("Delete"+ppsSet, "Clicked on delete button and"+" "+ppsSet+" "+"is deleted", "PASS");
			else
				oReport.AddStepResult("Delete"+ppsSet, "Clicked on delete button but"+" "+ppsSet+" "+"is not deleted", "FAIL");
		}
		else
			oReport.AddStepResult("Delete Pop Up", "Clicked on delete icon but delete pop up is not displayed", "FAIL");
	}
	
	
	// PPS DRG Grouper Options
	public void DRG_GrouperOptions()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);
		selectGroupType("PPS DRG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Grouper Options", "Grouper Option page validation", grouperOption, OpenPageValidation);

		//scenarios for validating AP Grouper Option
		newGrouperOptions("AP");//Method to click on add grouper option link and entering the Grouper Option details
		cancelScenario("Add Grouper Option window cancel button", "Pop-up window cancel button", "Pop Up window discard button", cancelButton, popUpCancelButton, popUpDiscardButton);//Method for checking cancel scenario
		newGrouperOptions("AP");//Method to click on add grouper option link and entering the Grouper Option details
		newGrouperOptionValidation();//Method for validating the created new Grouper Option Set
		deleteSet(deleteElement,"Grouper Options");//Method for deleting Grouper Option Set
		newGrouperOptions("AP");//Method to click on add grouper option link and entering the Grouper Option details
		newGrprOptnSetwithExisitngSet("AP");//Method for creating new set using existing Grouper Option
		searchPPS("Search Grouper Options",oParameters.GetParameters("GrouperOptionSetName"),openedGrprOptnName,searchResult_GO);// search for existing User Rate Set Entry
		editGrouperOptionSetName("AP");//Method for modifying Grouper Option Name
		editScenario_GO("AP");//Method for editing attribute values and validating the modification
		deleteSet(deleteElement,"Grouper Options");//Method for deleting Grouper Option Set
		
		//scenarios for validating APR Grouper Option
		newGrouperOptions("");//Method to click on add grouper option link and entering the Grouper Option details
		newGrouperOptionValidation();//Method for validating the created new Grouper Option Set
		deleteSet(deleteElement,"Grouper Options");//Method for deleting Grouper Option Set
		newGrouperOptions("");//Method to click on add grouper option link and entering the Grouper Option details
		newGrprOptnSetwithExisitngSet("");//Method for creating new set using existing Grouper Option
		searchPPS("Search Grouper Options",oParameters.GetParameters("GrouperOptionSetName"),openedGrprOptnName,searchResult_GO);// search for existing User Rate Set Entry
		editGrouperOptionSetName("APR");//Method for modifying Grouper Option Name
		editScenario_GO("APR");//Method for editing attribute values and validating the modification
		editEffectivePeriod();//Method for modifying Effective Period
		newPeriod();//Method for adding new Effective Period
		deletePeriod(periodDD,periodCount);//Method for deleting period
		deleteSet(deleteElement,"Grouper Options");//Method for deleting Grouper Option Set		
	} 
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public By providerValuesTab =  By.xpath("//div[@tabs='tabs']//a[contains(.,'Provider Values')]"); 
	public By providerValueSetIcon = By.xpath("//div[@ng-click='addProviderValuesSet()']");
	public By addProviderValueWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add Provider Values Set']");
	public By providerValueSetName = By.xpath("//input[@id='providerValuesSetName']");
	public By effectiveDate_PV = By.xpath("//input[@id='startDateProvidervaluesSet']");
	By pricerNamediv = By.xpath("//div[@spec='masterSetFormModel.pricerName.spec']");
	By selectProviderValues = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='pricerName']");
	By pricerVersion = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='pricerVersion']");
	By scheme = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='scheme']");
	By searchedResult = By.xpath("//ul[@class='data-list drillable-list scroll-auto']/li[1]");
	By openedProviderValue = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8 hide-overflow xl-header ng-binding']");
	By deleteProviderValue = By.xpath("//a[@ng-click='deleteDRGProviderValueSet(selectedProviderValuesSet.id)']//i[@class='left fa fa-minus-square']");

	
	//Method for creating new Provider Value Set
	public void newProviderValue(String pricerName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		Date resultdate = new Date(yourmilliseconds);
		fixed_wait_time(3);
		clickAddButton("Provider Value Set", "Add Provider Value Set Window", providerValueSetIcon, addProviderValueWindow);
		enter_text_value("Provider Value Set Name", providerValueSetName, pricerName+sdf.format(resultdate));		
		oParameters.SetParameters("ProviderValueSetName", get_field_value("Provider Values", providerValueSetName));
		enter_text_value("Effective Period", effectiveDate_PV, oParameters.GetParameters("EffectiveDate"));
		
		facilityName("PPS", "Apollo srn facility");
		
		click_button("Pricer Name Label", pricerNamediv);
		
		if(pricerName.equalsIgnoreCase("Medicare"))
		{
			select_option("Pricer Name", selectProviderValues, "3");
			select_option("Pricer Version", pricerVersion, "0");
			oParameters.SetParameters("Medicare", getSelectecText("Selected Pricer Name", selectProviderValues,"value"));
			oReport.AddStepResult("Entering Provider Values details", "Clicked on add Provider Value link and entered all the details", "SCREENSHOT");
		}	
		else if(pricerName.equalsIgnoreCase("CHAMPUS/TRICARE"))
		{
			select_option("Pricer Name", selectProviderValues, "1");
			select_option("Pricer Version", pricerVersion, "0");
			oParameters.SetParameters("CHAMPUS", getSelectecText("Selected Pricer Name", selectProviderValues,"value"));
			oReport.AddStepResult("Entering Provider Values details", "Clicked on add Provider Values link and entered all the details", "SCREENSHOT");
		}
		else if(pricerName.equalsIgnoreCase("NY Medicaid,HMO,Workers Comp,No Fault"))
		{
			select_option("Pricer Name", selectProviderValues, "6");
			select_option("Pricer Version", pricerVersion, "0");
			oParameters.SetParameters("NYMedicaid", getSelectecText("Selected Pricer Name", selectProviderValues,"value"));
			oReport.AddStepResult("Entering Provider Values details", "Clicked on add Provider Values link and entered all the details", "SCREENSHOT");
		}
		else if(pricerName.equalsIgnoreCase("Medicare IP Psychiatric"))
		{
			select_option("Pricer Name", selectProviderValues, "4");
			select_option("Pricer Version", pricerVersion, "0");
			oParameters.SetParameters("MedicareIP", getSelectecText("Provider Values", selectProviderValues,"value"));
			oReport.AddStepResult("Entering Provider Values details", "Clicked on add Provider Values link and entered all the details", "SCREENSHOT");
		}
		else if(pricerName.equalsIgnoreCase("Medicare Long Term Care"))
		{
			select_option("Pricer Name", selectProviderValues, "5");
			select_option("Pricer Version", pricerVersion, "0");
			oParameters.SetParameters("MedicareLongTermCare", getSelectecText("Selected Pricer Name", selectProviderValues,"value"));
			oReport.AddStepResult("Entering Provider Values details", "Clicked on add Provider Values link and entered all the details", "SCREENSHOT");
		}
		else if(pricerName.equalsIgnoreCase("APR Pricing"))
		{
			select_option("Pricer Name", selectProviderValues, "0");
			select_option("Pricer Version", pricerVersion, "0");
			select_option("Scheme",scheme,"0");
			oParameters.SetParameters("APRPricing", getSelectecText("Selected Pricer Name", selectProviderValues,"value"));
			oReport.AddStepResult("Entering Provider Values details", "Clicked on add Provider Values link and entered all the details", "SCREENSHOT");
		}
		else if(pricerName.equalsIgnoreCase("Generic User Rate"))
		{
			select_option("Pricer Name", selectProviderValues, "2");
			oParameters.SetParameters("GenericUserRate", getSelectecText("Selected Pricer Name", selectProviderValues,"value"));
			oReport.AddStepResult("Entering Provider Values details", "Clicked on add Provider Values link and entered all the details", "SCREENSHOT");
		}
	}
	
	
	By medicareFirstLabel = By.xpath("//td[@class='ng-binding'][contains(.,'COLA Multiplier')]");
	By tricareFirstLabel = By.xpath("//td[@class='ng-binding'][contains(.,'Active Duty Cost Share Maximum')]");
	By medicaidFirstLabel = By.xpath("//td[@class='ng-binding'][contains(.,'Additional Public Goods Pool Surcharge Percent')]");
	By aprPricing = By.xpath("//td[@class='ng-binding'][contains(.,'Base')]");
	By genericUserFirstLabel = By.xpath("//td[@class='ng-binding'][text()='1']");
	By medicareLongFirstLabel = By.xpath("//td[@class='ng-binding'][contains(.,'Budget Neutrality Rate')]");
	By medicareIPFistLabel = By.xpath("//td[@class='ng-binding'][contains(.,'Cost Of Living Adjustment')]");
	
	
	//Method for validating the created 
	public void validatingNewProviderValue(String pricerName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
			
		By openedProviderValue = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8 hide-overflow xl-header ng-binding'][contains(.,'"+pricerName+"')]");
		By selectedPricerName = By.xpath("//span[@class='ng-binding'][contains(.,'"+pricerName+"')]");
		
		clickSaveButton("Save button", "Add Provider Values Window", saveButton, addProviderValueWindow);
		
		if(pricerName.equalsIgnoreCase("Medicare"))
		{
			if(get_field_value("Created new Provider Values", openedProviderValue).equalsIgnoreCase(oParameters.GetParameters("ProviderValueSetName")) 
					&& oParameters.GetParameters("Medicare").equalsIgnoreCase(get_field_value("Pricer Name", selectedPricerName)) 
					&& IsDisplayed("First Attribute Presence", medicareFirstLabel))
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"and"+" "+pricerName+" "+"is created.", "PASS");
			else
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"but"+" "+pricerName+" "+"is not created.", "FAIL");
		}	
		else if(pricerName.equalsIgnoreCase("CHAMPUS/TRICARE"))
		{
			if(get_field_value("Created new Provider Values", openedProviderValue).equalsIgnoreCase(oParameters.GetParameters("ProviderValueSetName")) 
					&& oParameters.GetParameters("CHAMPUS").equalsIgnoreCase(get_field_value("Pricer Name", selectedPricerName)) 
					&& IsDisplayed("First Attribute Presence", tricareFirstLabel))
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"and"+" "+pricerName+" "+"is created.", "PASS");
			else
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"but"+" "+pricerName+" "+"is not created.", "FAIL");
		}
		else if(pricerName.equalsIgnoreCase("NY Medicaid,HMO,Workers Comp,No Fault"))
		{
			if(get_field_value("Created new Provider Values", openedProviderValue).equalsIgnoreCase(oParameters.GetParameters("ProviderValueSetName")) 
					&& oParameters.GetParameters("NYMedicaid").equalsIgnoreCase(get_field_value("Pricer Name", selectedPricerName)) 
					&& IsDisplayed("First Attribute Presence", medicaidFirstLabel))
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"and"+" "+pricerName+" "+"is created.", "PASS");
			else
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"but"+" "+pricerName+" "+"is not created.", "FAIL");
		}
		else if(pricerName.equalsIgnoreCase("Medicare IP Psychiatric"))
		{
			if(get_field_value("Created new Provider Values", openedProviderValue).equalsIgnoreCase(oParameters.GetParameters("ProviderValueSetName")) 
					&& oParameters.GetParameters("MedicareIP").equalsIgnoreCase(get_field_value("Pricer Name", selectedPricerName)) 
					&& IsDisplayed("First Attribute Presence", medicareIPFistLabel))
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"and"+" "+pricerName+" "+"is created.", "PASS");
			else
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"but"+" "+pricerName+" "+"is not created.", "FAIL");
		}
		else if(pricerName.equalsIgnoreCase("Medicare Long Term Care"))
		{
			if(get_field_value("Created new Provider Values", openedProviderValue).equalsIgnoreCase(oParameters.GetParameters("ProviderValueSetName")) 
					&& oParameters.GetParameters("MedicareLongTermCare").equalsIgnoreCase(get_field_value("Pricer Name", selectedPricerName)) 
					&& IsDisplayed("First Attribute Presence", medicareLongFirstLabel))
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"and"+" "+pricerName+" "+"is created.", "PASS");
			else
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"but"+" "+pricerName+" "+"is not created.", "FAIL");
		}
		else if(pricerName.equalsIgnoreCase("APR Pricing"))
		{
			if(get_field_value("Created new Provider Values", openedProviderValue).equalsIgnoreCase(oParameters.GetParameters("ProviderValueSetName")) 
					&& oParameters.GetParameters("APRPricing").equalsIgnoreCase(get_field_value("Pricer Name", selectedPricerName)) 
					&& IsDisplayed("First Attribute Presence", aprPricing))
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"and"+" "+pricerName+" "+"is created.", "PASS");
			else
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"but"+" "+pricerName+" "+"is not created.", "FAIL");
		}
		else if(pricerName.equalsIgnoreCase("Generic User Rate"))
		{
			if(get_field_value("Created new Provider Values", openedProviderValue).equalsIgnoreCase(oParameters.GetParameters("ProviderValueSetName")) 
					&& oParameters.GetParameters("GenericUserRate").equalsIgnoreCase(get_field_value("Pricer Name", selectedPricerName)) 
					&& IsDisplayed("First Attribute Presence", genericUserFirstLabel))
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"and"+" "+pricerName+" "+"is created.", "PASS");
			else
				oReport.AddStepResult(pricerName+" "+"page validation", "Filled all the details for"+" "+pricerName+" "+"but"+" "+pricerName+" "+"is not created.", "FAIL");
		}
	}
	
	
	By checkbox_PV = By.xpath("//input[@id='copyClosedAddSet']");
	public By selectProviderValueSet = By.xpath("//input[@id='copyDrgProviderValuesSet']");
	By selectPeriod = By.xpath("//select[@id='copyPeriodInSet']");
	By firstValue = By.xpath("//tr[@class='position-relative ng-scope'][1]//input[@model='kv']");
	By secondValue = By.xpath("//tr[@class='position-relative ng-scope'][2]//input[@model='kv']");
	By firstValue_G = By.xpath("//tr[@class='position-relative ng-scope hand-cursor'][1]//input[@model='kv']");
	By secondValue_G = By.xpath("//tr[@class='position-relative ng-scope hand-cursor'][2]//input[@model='kv']");
	
	//Method for creating new Provider Value Set with existing Set
	public void newProvdrValWithExistngSet(String pricername, String existingSet)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		copyFromExisting("copy details from Existing Set", "Search for Provider Value set", "Select Period to copy",checkbox_PV, selectProviderValueSet, selectPeriod,selectUserRate, selectFirstElement, existingSet);
		clickSaveButton("Save button", "Add Provider Values Window", saveButton, addProviderValueWindow);
		//fixed_wait_time(5);
		if(pricername.equalsIgnoreCase("Generic User Rate"))
		{
			if(!get_field_value("First Attribute Value", firstValue_G).isEmpty() | !get_field_value("First Attribute Value", secondValue_G).isEmpty() )
				oReport.AddStepResult("Copy from existing Provider Value Set", "Clicked on checkbox, searched and selected existing set and verified that value is copied from existing set", "PASS");
			else
				oReport.AddStepResult("Copy from existing Provider Value Set", "Clicked on checkbox, searched and selected existing set and verified that value is not copied from existing set", "FAIL");
		}
		else
		{
			if(!get_field_value("First Attribute Value", firstValue).isEmpty() | !get_field_value("Second Attribute Value", secondValue).isEmpty() )
				oReport.AddStepResult("Copy from existing Provider Value Set", "Clicked on checkbox, searched and selected existing set and verified that value is copied from existing set", "PASS");
			else
				oReport.AddStepResult("Copy from existing Provider Value Set", "Clicked on checkbox, searched and selected existing set and verified that value is not copied from existing set", "FAIL");
		}
		
	}
	
	
	By valueCount = By.xpath("//input[@model='kv']");	
	public By saveDetails = By.xpath("//div[@id='providerValuesActionBar']//input[@value='Save']");	
	
	//Method for adding the values based on selected Pricer
	public void addValues(String pricerName,String sheetName)
	{	
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "ProviderValuesInputDetails.xlsx", sheetName, "");
		
		int rowcount = oExcelData.getRowCount1("DRG_ProviderValues", "C:\\CCM\\SupportingFiles\\ProviderValuesInputDetails.xlsx");	
		
		for (int i=1;i<rowcount;i++)
		{
			if(pricerName.equalsIgnoreCase(oParameters.GetParameters("Key"+i)))
			{
				String PricerName[] = oParameters.GetParameters("Value"+i).split(",");				
				List<WebElement> count = convertToWebElements(valueCount);
				
				for(int k=1;k<=count.size();k++)
				{
					By value_PV = By.xpath("//div[@id='addEditEntryParent']//tr["+k+"]//input[@model='kv']");	
					
					if(k<count.size()-4)
					{
						scroll("Scroll to next field", value_PV);
						enter_text_value("Pricer Value", value_PV, PricerName[k-1]);
					}
					else
						enter_text_value("Pricer Value", value_PV, PricerName[k-1]);
										
				}
				oReport.AddStepResult("Add values", "Adding values for respective Labels", "SCREENSHOT");
				break;
			}
		}
		click_button("Save details", saveDetails);
	}
	
	

	By label = By.xpath("//div[@id='addEditEntryParent']//tr[@ng-repeat='kv in providerValueEntries']/td[1]");
	By value = By.xpath("//div[@id='addEditEntryParent']//input[@model='kv']");
	
	public void writeExcel() throws IOException 
	{
		ArrayList<String> lbl= new ArrayList<String>();
		ArrayList<String> value = new ArrayList<String>();
		
		List<WebElement> labelCount = convertToWebElements(label); 
			
		for (int i=1;i<=labelCount.size();i++) 
		{
			By Label = By.xpath("//div[@id='addEditEntryParent']//tr["+i+"][@ng-repeat='kv in providerValueEntries']/td[1]");
			By value_PV = By.xpath("//div[@id='addEditEntryParent']//tr["+i+"]//input[@model='kv']");
			
			lbl.add(get_field_value("Label", Label));
			value.add(get_field_value("Value", value_PV));
		}
		
		for (int k=1;k<=lbl.size();k++) 
		{
			oExcelData.setExcelData("C:\\CCM\\SupportingFiles\\", "ProviderValues23.xlsx", "NYMedicaid", k, 0, lbl.get(k-1));
			oExcelData.writeExcel("C:\\CCM\\SupportingFiles\\", "ProviderValues23.xlsx","NYMedicaid",k,value.get(k-1));
		}
		System.out.println("Data is written into Excel");
	}
	
	
	By firstElement = By.xpath("//div[@id='addEditEntryParent']//tr[1][@ng-repeat='kv in providerValueEntries']/td[1]");
	public void insertData(String SheetName)
	{
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "ProviderValues23.xlsx", SheetName, "");
		
		int rowcount = oExcelData.getRowCount1(SheetName, "C:\\CCM\\SupportingFiles\\ProviderValues23.xlsx");	
		
		for (int i=1;i<rowcount;i++)
		{
			By Label = By.xpath("//div[@id='addEditEntryParent']//tr["+i+"][@ng-repeat='kv in providerValueEntries']/td[1]");
			
			if(get_field_value("Label", Label).equalsIgnoreCase(oParameters.GetParameters("Key"+i)))
			{
				String Value = oParameters.GetParameters("Value"+i);				
				By value_PV = By.xpath("//div[@id='addEditEntryParent']//tr["+i+"]//input[@model='kv']");
				enter_text_value("Pricer Value", value_PV, Value);	
				scroll("Scroll to next field", value_PV);
			}
		}
		scroll("First element", firstElement);
		oReport.AddStepResult("Add values", "Adding values for respective Labels", "SCREENSHOT");
		click_button("Save details", saveDetails);
	}
	

	public void validateAddValues()
	{
		List<String> PreviousValues = new ArrayList<String>();
		List<String> AfterValues = new ArrayList<>();
		
		for(int k=2;k<=4;k++)
		{
			By value_PV = By.xpath("//div[@id='addEditEntryParent']//tr["+k+"]//input[@model='kv']");	
			oParameters.SetParameters("previousValue", get_field_value("Values", value_PV));
			PreviousValues.add(oParameters.GetParameters("previousValue"));
		}
		
		driver.navigate().refresh();
		fixed_wait_time(10);
		
		for(int i=2;i<=4;i++)
		{
			By value_PV = By.xpath("//div[@id='addEditEntryParent']//tr["+i+"]//input[@model='kv']");	
			oParameters.SetParameters("afterValue", get_field_value("Values", value_PV));
			AfterValues.add(oParameters.GetParameters("afterValue"));
		}
		
		if(PreviousValues.equals(AfterValues))
			oReport.AddStepResult("Add Value Validation", "Values are successfully added to Respective Pricer", "PASS");
		else
			oReport.AddStepResult("Add Value Validation", "Values are not added to Respective Pricer", "FAIL");	
	}
	
	
	By editIcon_PV = By.xpath("//a[@title='Edit Provider Value Set']//i[@class='left fa fa-pencil']");
	By editWindow_PV = By.xpath("//div[@title='Edit Provider Values Set']");
	
	//Method to edit the name of the provider value set name
	public void editPVname(String pricerName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit icon", editIcon_PV);
		
		if(IsDisplayed("Provider Value Set Edit Window", editWindow_PV))
		{
			oParameters.SetParameters("previousSetName", get_field_value("Provider Value Name", providerValueSetName));
			enter_text_value("Provider Value Set Name", providerValueSetName, pricerName+"_"+System.currentTimeMillis());
			oParameters.SetParameters("ModifiedProviderValue", get_field_value("Provider Value Set Name", providerValueSetName));
			click_button("Save Edit Provider Value Set Name", saveEffectivePeriod);
			fixed_wait_time(3);
			if(oParameters.GetParameters("ModifiedProviderValue").equalsIgnoreCase(get_field_value("Opened Provider Value Set Name", openedProviderValue)))
				oReport.AddStepResult("Edit Provider Value Set Name", "Clicked on Edit icon, Modified the Proivder Value Set name from"+" "+oParameters.GetParameters("previousSetName")+" "+"to"+" "+get_field_value("Opened Provider Value Set Name", openedProviderValue), "PASS");
			else 
				oReport.AddStepResult("Edit Provider Value Set Name", "Clicked on Edit Icon but Provider Value name is not modified", "FAIL");
		}
		else
			oReport.AddStepResult("Provider Value Set Edit Window", "Edit Provider Value Set window is not displayed", "FAIL");
	}
	
	
	//Method for modifying the values
	public void modifyValue(String pricername)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		if(pricername.equalsIgnoreCase("Generic User Rate"))
		{
			scroll("First value", firstValue_G);
			oParameters.SetParameters("beforeValue", get_field_value("First value", firstValue_G));
			enter_text_value("Modify Value", firstValue_G, "1.05");
			click_button("Save details", saveDetails);
			waitFor(firstValue_G, "First element");
			oParameters.SetParameters("afterValue", get_field_value("First value", firstValue_G));
		}
		else
		{
			scroll("First value", firstValue);
			oParameters.SetParameters("beforeValue", get_field_value("First value", firstValue));
			enter_text_value("Modify Value", firstValue, "1");
			click_button("Save details", saveDetails);
			waitFor(firstValue, "First element");
			oParameters.SetParameters("afterValue", get_field_value("First value", firstValue));
		}
		
		if(!oParameters.GetParameters("beforeValue").equalsIgnoreCase(oParameters.GetParameters("afterValue")))
			oReport.AddStepResult("Edit attribute value", "Attribute value is modified from"+" "+oParameters.GetParameters("beforeValue")+" "+"to"+" "+oParameters.GetParameters("afterValue"), "PASS");
		else
			oReport.AddStepResult("Edit attribute value", "Attribute is not modified", "FAIL");
	}
	
	
	By periodIcon_PV = By.xpath("//div[@periods='selectedProviderValuesSet.drgProviderValuesPeriods']//a[@ng-click='showDropdown = !showDropdown']//span[@ng-show='periods.length > 0']");
	By periodDD_PV = By.xpath("//div[@periods='selectedProviderValuesSet.drgProviderValuesPeriods']//ul[@class='dropdown-menu period-menu mar-l-15']");
	By period_PV = By.xpath("//div[@periods='selectedProviderValuesSet.drgProviderValuesPeriods']//li[@class='hand-cursor ng-scope']");
	By periodEditIcon_PV = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 ng-binding']//i[@ng-hide='true']");
	By statusIcon = By.xpath("//a[@class='btn btn-light btn-default btn-sm']//i[@class='fa fa-exclamation-circle inactive-circle']");
	
	
	//Method for modifying effective dates
	public void modifyEffectivePeriod()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
		
		waitFor(periodIcon_PV, "Effective Period Dates");
		click_button("Click on Effective Period Drop Down", periodIcon_PV);
		oParameters.SetParameters("PreviousDate", get_field_value("Previous Effective Date", periodIcon_PV));
		
		//Validating Period drop down
		if(IsElementDisplayed("Period drop down", periodDD_PV))
		{
			mouse_hover("Hover over Effective Period", period_PV);
			click_button("Period Edit Icon", periodEditIcon);
			oReport.AddStepResult("Effective Period Drop Down", "Clicked on period drop down and clicked on period edit icon", "INFO");
		}
		else
			oReport.AddStepResult("Effective Period Drop Down", "Clicked on period drop down but period drop down is not displayed", "INFO");
		
		//editing the effective period and validating the same
		if(IsElementDisplayed("Edit Effective Period", editEffectiveWindow) && IsElementEnabled("Save button in edit period window", saveEffectivePeriod))
		{
			click_button("Provider Values Status", userRateStatus);
			//click_button("Save Provider Value status", saveEffectivePeriod);
			//modifyPricer();
		}
	}
	
	
	By previousPricername = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='pricerName']");
	By previousPricerVersion = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='pricerVersion']");
	By previousScheme = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='scheme']");
	By nameDisabledField =By.xpath("//select[@id='pricerName'][@disabled='disabled']");
	By versionDisabledField = By.xpath("//select[@id='pricerVersion'][@disabled='disabled']");
	By schemeDisabledField = By.xpath("//select[@id='scheme'][@disabled='disabled']");
	
	
	//Method for changing Pricer
	public void modifyPricer()
	{
		for (int i=0;i<=5;i++)
		{
			oParameters.SetParameters("beforePricerName", getSelectecText("Pricer Name", previousPricername,"value"));
			if(!IsDisplayed("Pricer Name Field disable", nameDisabledField))
			{
				select_option("Pricer Name in Edit Effective Period", previousPricername, String.valueOf(i));
				oParameters.SetParameters("afterPricerName", getSelectecText("Pricer Name", previousPricername,"value"));
				
				if(!oParameters.GetParameters("beforePricerName").equalsIgnoreCase(oParameters.GetParameters("afterPricerName")))
					oReport.AddStepResult("Pricer Name Modification", "Pricer Name is modified from"+oParameters.GetParameters("beforePricerName")+" "+"to"+" "+oParameters.GetParameters("afterPricerName"), "PASS");
				else
					oReport.AddStepResult("Pricer Name Modification", "Pricer Name is not modified", "FAIL");
			}
			else 
				oReport.AddStepResult("Name Field validation", "Pricer Name field is disabled and pricer name cannot be changed/selected", "FAIL");
			
			if(!oParameters.GetParameters("afterPricerName").contains("Generic"))
			{
				oParameters.SetParameters("beforePricerVersion", getSelectecText("Pricer Version", previousPricerVersion,"value"));
				
				if(!IsDisplayed("Pricer Version Field disable", versionDisabledField))
				{
					select_option("Pricer Version in Edit Effective Period", previousPricerVersion, String.valueOf(i+2));
					oParameters.SetParameters("afterPricerVersion", getSelectecText("Pricer Name", previousPricerVersion,"value"));
					
					if(!oParameters.GetParameters("beforePricerVersion").equalsIgnoreCase(oParameters.GetParameters("afterPricerVersion")))
						oReport.AddStepResult("Pricer Version Modification", "Pricer Version is modified from"+oParameters.GetParameters("beforePricerVersion")+" "+"to"+" "+oParameters.GetParameters("afterPricerVersion"), "PASS");
					else
						oReport.AddStepResult("Pricer Version Modification", "Pricer Version is not modified", "FAIL");
				}
				else
					oReport.AddStepResult("Version Field validation", "Pricer Verion field is disabled and pricer version cannot be changed/selected", "FAIL");
			}
									
			if(oParameters.GetParameters("afterPricerName").contains("APR"))
			{
				oParameters.SetParameters("beforePricerScheme", getSelectecText("Scheme", previousScheme,"value"));
				
				if(IsDisplayed("Scheme Field disable", schemeDisabledField) && !getSelectecText("Scheme Field disable", schemeDisabledField,"value").equalsIgnoreCase("select"))
				{
					select_option("Scheme in Edit Effective Period", previousScheme, String.valueOf(i+5));
					oParameters.SetParameters("afterPricerScheme", getSelectecText("Pricer Name", previousScheme,"value"));
					
					if(!oParameters.GetParameters("beforePricerScheme").equalsIgnoreCase(oParameters.GetParameters("afterPricerScheme")))
						oReport.AddStepResult("Scheme Modification", "Scheme is modified from"+oParameters.GetParameters("beforePricerScheme")+" "+"to"+" "+oParameters.GetParameters("afterPricerScheme"), "PASS");
					else
						oReport.AddStepResult("Scheme Modification", "Scheme is not modified", "FAIL");
				}
				else
					oReport.AddStepResult("Scheme Field validation", "Scheme field is not displaying perviously selected Pricer Name", "FAIL");
			}		
		}
		
		clickSaveButton("Save Pricer Modification", "Edit window", saveEffectivePeriod, editEffectiveWindow);
		
		if(!IsDisplayed("Provider Value Status", statusIcon))
			oReport.AddStepResult("Change Provider Value status", "Provider Value Status is changed from Inactive to Active", "PASS");
		else
			oReport.AddStepResult("Change Provider Value status", "Provider Value Status is not changed from Inactive to Active", "FAIL");
	}
	
	
	By existingPN = By.xpath("//input[@id='selectPricer']");
	By checkbox_PV1 = By.xpath("//input[@id='copyClosedPeriod']");
	By selectProviderValue = By.xpath("//input[@id='providerValuesSearch']");
	By selectPeriod_PV = By.xpath("//select[@id='copyPeriod']");
	By selectVersion = By.xpath("//select[@id='selectVersion']");
	By selectScheme = By.xpath("//select[@id='copyPeriodScheme']");
	
	
	//Method for copying details from existing Provider Value Set
	public void copyFromExisting()
	{
		long number = get_random_number(1, 35);
		By selectPVset = By.xpath("//ul[@id='-list']/li["+number+"]");
		click_button("Copy details from existing PV", checkbox_PV1);
		click_button("search provider value set", selectProviderValue);
		//click_button("select provider value set", selectPVset);
	
		oParameters.SetParameters("CopyPricerName", get_field_value("Pricer Name from copy details", existingPN));
		
		if(!oParameters.GetParameters("CopyPricerName").contains("APR"))
		{
			select_option("Select period", selectPeriod_PV, "0");
			select_option("Select Version", selectVersion, "4");
		}
		else
		{
			select_option("Select period", selectPeriod_PV, "2");
			select_option("Select Version", selectVersion, "4");
			select_option("Select Version", selectScheme, "9");
		}
	}
	
	
	By addPeriod_PV = By.xpath("//div[@periods='selectedProviderValuesSet.drgProviderValuesPeriods']//a[@ng-click='addPeriod()']");
	By newTerminationDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='stopDate']");
	By pricerNameAddPeriod = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='pricerName']");
	By pricerLabel = By.xpath("//div[@form-id='periodFormModel.formId']//label[@for='pricerName']");
	
	
	//Method for adding new effective period
	public void addNewPeriod()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
		
		waitFor(periodIcon_PV, "Effective Period Dates");
		for(int i=0;i<6;i++)
		{
			click_button("Click on Effective Period Drop Down", periodIcon_PV);
			clickAddButton("Add Period icon", "Add Period Window", addPeriod_PV, addEffectivePeriodWindow);
			enter_text_value("Effective Date for medicare", addNewEffectivePeriod, oParameters.GetParameters("NewEffectiveDate"+(i+1)));
			enter_text_value("Termination Date for medicare", newTerminationDate, oParameters.GetParameters("TerminationDate"+(i+1)));
			click_button("Pricer Label", pricerLabel);
			
			if(!IsDisplayed("Pricer Name Field disable", nameDisabledField))
			{
				select_option("Pricer Name in Add Effective Period", pricerNameAddPeriod, String.valueOf(i));
				oParameters.SetParameters("PricerNameAdd", getSelectecText("Pricer Name in add new period", pricerNameAddPeriod,"value"));
			}
			else 
				oReport.AddStepResult("Name Field validation", "Pricer Name field is disabled and pricer name cannot be selected", "FAIL");
		
			if(!oParameters.GetParameters("PricerNameAdd").contains("Generic"))
			{
				if(!IsDisplayed("Pricer Version Field disable", versionDisabledField))
					select_option("Pricer Version in Edit Effective Period", previousPricerVersion, String.valueOf(i+4));
				else
					oReport.AddStepResult("Version Field validation", "Pricer Verion field is disabled and pricer version cannot be selected", "FAIL");
			}
	
			if(oParameters.GetParameters("PricerNameAdd").contains("APR"))
			{
				if(!IsDisplayed("Scheme Field disable", schemeDisabledField))
					select_option("Scheme in Edit Effective Period", previousScheme, String.valueOf(i+6));
				else
					oReport.AddStepResult("Scheme Field validation", "Scheme field is disabled and scheme cannot be changed/selected", "FAIL");		
			}
		
			oReport.AddStepResult("Selecting Pricer Name and versions", "Selected Pricer name and respective pricer version", "SCREENSHOT");
			clickSaveButton("Save new period", "Add Effective Priod window", saveEffectivePeriod, addEffectivePeriodWindow);
		
			count++;
		}
		
		int afterCount = noOfRows(periodCount);
		
		if(afterCount>=7)
			oReport.AddStepResult("New Period", "Clicked on Add Period Icon, Filled all the fields based on selected Pricer Name and new period is created with all Pricer types", "PASS");
		else 
			oReport.AddStepResult("New Period", "Clicked on Add Period Icon, Filled all the fields based on selected Pricer Name but new period is not created for some Pricer types", "FAIL");
	}
	
	
	//Method for adding new period with existing provider value set
	public void newPeriodwithExisitngPV()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
	
		click_button("Click on Effective Period Drop Down", periodIcon_PV);
		clickAddButton("Add Period icon", "Add Period Window", addPeriod_PV, addEffectivePeriodWindow);
		enter_text_value("Effective Date for medicare", addNewEffectivePeriod, "11/01/2006");
		enter_text_value("Termination Date for medicare", newTerminationDate, "11/30/2006");
		copyFromExisting();
		oReport.AddStepResult("Selecting Pricer Name and versions", "Selected Pricer name and respective pricer version", "SCREENSHOT");
		clickSaveButton("Save new period", "Add Effective Priod window", saveEffectivePeriod, addEffectivePeriodWindow);
	}
	
	
	//PPS DRG Provider Values
	public void DRG_ProviderValues() throws IOException
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS DRG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Provider Values", "Provider Values page validation", providerValuesTab, OpenPageValidation);//Method navigates to Provider Values tab
		waitFor(openedProviderValue, "search field");
		//searchPPS("Search Provider Values", "NY Medicaid,HMO,Workers Comp,No Fault_154771198583", openedProviderValue, searchedResult);
		//writeExcel();
		
		String[] pricerName = {"Medicare","CHAMPUS/TRICARE","Generic User Rate","APR Pricing","Medicare IP Psychiatric","Medicare Long Term Care","NY Medicaid,HMO,Workers Comp,No Fault"};
		String[] searchPricerName = {"SearchMedicare","SearchTricare","SearchGeneric","SearchAPR","SearchMEdicareIP","SearchMedicareLongTerm","SearchNYMedicaid"};
		String[] insertPricerName = {"Medicare","CHAMPUS_TRICARE","GenericUserRate","APRPricing","MedicareIPPsychiatric","MedicareLongTermCare","NYMedicaid"};

		for(int i=0;i<=pricerName.length;i++)
		{
			//Scenario's for different Pricer Name
			newProviderValue(pricerName[i]);//Method for entering details for provider value set 
			cancelScenario("Add Provider Values window cancel button", "Pop-up window cancel button", "Pop Up window discard button", cancelButton, popUpCancelButton, popUpDiscardButton);//Method for checking cancel scenario
			newProviderValue(pricerName[i]);//Method for entering details for provider value set
			validatingNewProviderValue(pricerName[i]);//Method for validating new Medicare Pricer created
			deleteSet(deleteProviderValue,"Provider Values");//Method for deleting provider Value set
			fixed_wait_time(5);
			newProviderValue(pricerName[i]);//Method for entering details for provider value set
			newProvdrValWithExistngSet(pricerName[i],oParameters.GetParameters(searchPricerName[i]));//Method for creating new Provider Values with existing set
			searchPPS("Search Provider Values", oParameters.GetParameters("ProviderValueSetName"), openedProviderValue, searchedResult);//Method to select the Provider Value set
			driver.navigate().refresh();
			fixed_wait_time(13);
			insertData(insertPricerName[i]);
			validateAddValues();
			editPVname(pricerName[i]);//Method for editing provider value set name
			modifyValue(pricerName[i]);//Method for validating edit attribute value 
			deleteSet(deleteProviderValue,"Provider Values");//Method for deleting provider Value set	
			fixed_wait_time(10);
		}
		
		searchPPS("Search Provider Values", oParameters.GetParameters("SearchNYMedicaid"), openedProviderValue, searchedResult);//Method to select the Provider Value set
		//searchPPS("Search Provider Values", "NY Medicaid,HMO,Workers Comp,No Fault_154785820946", openedProviderValue, searchedResult);
		modifyEffectivePeriod();//Method for changing Status
		addNewPeriod();//Method for creating new effective periods
		newPeriodwithExisitngPV();//Method for adding new period with existing provider value set
		deletePeriod(periodIcon_PV,periodCount);//Method for deleting period
		logout();
}


//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public By groupingDefinitionsTab = By.xpath("//div[@tabs='tabs']//a[contains(.,'Grouping Definitions')]");
	public By groupingDefinitionSetName =  By.xpath("//input[@id='definitionSetName']");
	public By effectiveDate_GD =By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='startDateDrgGroupingDefinitions']");
	public By terminationDate_GD = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='stopDateDrgGroupingDefintions']");
	By grouperNamee = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='drgGrouperName']//option");
	By grouperName = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='drgGrouperName']");
	By hac_NO = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@name='disableNo']");
	By hac_Yes = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@name='disableYes']");
	public By reassignmentMethod = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='reassignmentMethodCode']");
	public By addGrouperDefinitionWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add DRG Grouping Definition Set']");
	public By addGrouperDefinitionIcon = By.xpath("//div[@ng-click='addGroupingDefinitionSet()']");
	By deleteGrouperDefinition = By.xpath("//a[@ng-click='deleteDrgGroupingDefintionSet(selectedDrgDefinition.id)']//i[@class='left fa fa-minus-square']");
	
	
	public void addGrouperDefinition(String DRGgrouperName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickAddButton("Add Grouper Definition icon", "Add Grouper Definition window", addGrouperDefinitionIcon, addGrouperDefinitionWindow);
		
		facilityName("PPS", "Apollo srn facility");
		
		enter_text_value("Effective Date", effectiveDate_GD, oParameters.GetParameters("EffectiveDate"));
		enter_text_value("Termination Date", terminationDate_GD, oParameters.GetParameters("TerminationDate"));
		performKeyBoardAction("ENTER");
		selectGrouperName(DRGgrouperName);
		enter_text_value("Grouper Definition Set Name", groupingDefinitionSetName, oParameters.GetParameters("SelectedGrouperName")+"_"+System.currentTimeMillis());
		oParameters.SetParameters("newGroupingDefinition", get_field_value("Grouping Definition Name", groupingDefinitionSetName));
		click_button("HAC Radio Button", hac_NO);
	}	
	
	
	By grouperVersion = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='drgGrouperVersion']");
	By mapICDType = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='icdMappingTypeCode']");
	By reOrderIndicator_Yes = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@name='ReorderIndicatorYes']");
	By reOrderIndicator_No = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@name='ReorderIndicatorNo']");
	By mapICDVersionNo = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='icdVersion']");
	
	
	public void selectGrouperName(String DRGgrouperName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		for(int i=0;i<7;i++)
		{
			select_list_value("Grouper Name", grouperNamee, DRGgrouperName);
			
			if(getSelectecText("selected grouper name", grouperName, "value").equalsIgnoreCase(DRGgrouperName))
			{
				oParameters.SetParameters("SelectedGrouperName", getSelectecText("selected grouper name", grouperName, "value"));
				
				if(oParameters.GetParameters("SelectedGrouperName").equalsIgnoreCase("Medicare"))
				{
					select_option("Select Grouper Version", grouperVersion, String.valueOf(i+3));
					select_option("Reassignment Method", reassignmentMethod, String.valueOf(i+5));
					break;
				}
				else if(oParameters.GetParameters("SelectedGrouperName").contains("CHAMPUS"))
				{
					select_option("Select Grouper Version", grouperVersion, String.valueOf(i+2));
					select_option("Select Grouper Version", mapICDVersionNo, String.valueOf(i+2));
					select_option("Select Grouper Version", mapICDType, String.valueOf(i+1));
					break;
				}
				else 
				{
					select_option("Select Grouper Version", grouperVersion, String.valueOf(i+1));
					break;
				}
			}
		}
	}	
	
	
	By icdCodeMappingDiv = By.xpath("//div[@id='editGroupingDefinition']//div[@class='form-group col-md-12 col-sm-12 col-lg-12'][contains(.,'Use the following fields for groupers')]");
	
	
	public void validatingGrouperDefinition(String DRGgrouperName) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickSaveButton("Save button", "Add Provider Values Window", saveButton, addProviderValueWindow);
		List<WebElement> grouperDefinitionList = driver.findElements(By.xpath("//ul[@class='data-list drillable-list scroll-auto']//li"));
		
		for(WebElement option:grouperDefinitionList)
		{
			if(option.getText().equals(DRGgrouperName)) 
			{
				oReport.AddStepResult("Grouper Defintion list", "created new Grouping Definition"+" "+DRGgrouperName+" "+"is displayed", "PASS");	
				break;
			}	
			
		}
			
		By openedGrouperDefinition = By.xpath("//div[@class='col-md-6 col-md-5 col-sm-6 large-header ng-binding'][contains(.,'"+DRGgrouperName+"')]");
		
		if(get_field_value("Create new Grouper Name", openedGrouperDefinition).equalsIgnoreCase(DRGgrouperName) && IsDisplayed("ICD Code Mapping Div", icdCodeMappingDiv))
			oReport.AddStepResult("New Grouper Definition", "Clicked on Add Grouper Definition icon, Filled all the mandatory fields, Clicked on save button and"+" "+DRGgrouperName+" "+"is created successfully", "PASS");
		else
			oReport.AddStepResult("Create new Grouper Definiton", "Clicked on Add Grouper Definition icon, Filled all the mandatory fields but new Grouper Definition is not created", "FAIL");
	}
	
	
	By editGDNameIcon = By.xpath("//a[@ng-click='editDrgGroupingDefinitionSet()']");
	By editGDWindow = By.xpath("//div[@title='Edit DRG Grouping Definition Set']");
	By GD_SetName = By.xpath("//input[@id='definitionSetName']");
	
	
	public void editGrouperOptionName()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit Grouping Definition Icon", editGDNameIcon);
		
		if(IsDisplayed("Edit Grouper Definition Window", editGDWindow))
		{
			oParameters.SetParameters("PreviousGrouperDefinition", get_field_value("Grouper Definition Set Name", GD_SetName));
			enter_text_value("Edit Grouper Definition Set Name", GD_SetName, oParameters.GetParameters("PreviousGrouperDefinition")+"_Edit");
			oParameters.SetParameters("AfterGrouperDefinition", get_field_value("Grouper Definition Set Name", GD_SetName));
			click_button("Save Edit Grouping Definition", saveButton);
			
			if(get_field_value("Create new Grouper Name", openedGrouperDefinition).equalsIgnoreCase(oParameters.GetParameters("AfterGrouperDefinition")))
				oReport.AddStepResult("Edit Grouper Definition Name", "Clicked on edit Grouper Definition icon and modified name from"+" "+oParameters.GetParameters("PreviousGrouperDefinition")+"to"+" "+oParameters.GetParameters("AfterGrouperDefinition"), "PASS");
			else
				oReport.AddStepResult("Edit Grouper Definition Name", "Edit Grouper Definition Set Name is not modified", "FAIL");
		}
	}
	
	
	By DRGgrouperOptionSet = By.xpath("//input[@id='drgGrouperOptionAdd']");
	By DRGproviderValueSet = By.xpath("//input[@id='drgProviderValue']");
	By grouperOptionSetDD = By.xpath("//ul[@id='-list'][@class='dropdown-menu']/li[1]");
	By providerValueSetDD = By.xpath("//ul[@id='-list'][@class='dropdown-menu dropdown-display']/li[1]");
	
	
	public void editGrouperOption()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("previousGrouperOption", get_field_value("Grouper Option", DRGgrouperOptionSet));
		driver.findElement(DRGgrouperOptionSet).clear();
		click_button("DRG Grouper Option Set", DRGgrouperOptionSet);

		performKeyBoardAction("ENTER");
		if(IsDisplayed("Choose Grouper Option Set", grouperOptionSetDD))
		{
			click_button("Selecting first element from Grouper Option dropdown", grouperOptionSetDD);
			oParameters.SetParameters("afterGrouperOption", get_field_value("Grouper Option", DRGgrouperOptionSet));
		}
		else 
			oReport.AddStepResult("Choose Grouper Option Set", "Clicked on Grouper Option search box but Grouper Option list drop down is not displayed", "INFO");
				
		oParameters.SetParameters("previousProviderValue", get_field_value("Provider Value", DRGproviderValueSet));
		driver.findElement(DRGproviderValueSet).clear();
		click_button("DRG Provider Value Set", DRGproviderValueSet);

		performKeyBoardAction("ENTER");
		if(IsDisplayed("Choose Provider Value Set", providerValueSetDD))
		{
			click_button("Selecting first element from Provider Value dropdown", providerValueSetDD);
			oParameters.SetParameters("afterProviderValue", get_field_value("Provider Value", DRGproviderValueSet));				
		}
		else 
			oReport.AddStepResult("Choose Provider Value Set", "Clicked on Provider Value search box but Provider Value list drop down is not displayed", "INFO");
	}
	
	
	By saveGrouperDefinition = By.xpath("//div[@id='fullFooter']//button[@class='btn btn-xs btn-primary']");
	
	
	public void validateEditGD()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Save Edit Grouping Definition", saveGrouperDefinition);
		fixed_wait_time(3);
		
		if(!oParameters.GetParameters("previousGrouperOption").equalsIgnoreCase(oParameters.GetParameters("afterGrouperOption")) && 
				!oParameters.GetParameters("previousProviderValue").equalsIgnoreCase(oParameters.GetParameters("afterProviderValue")))
			oReport.AddStepResult("Edit Grouper Definition", "Grouper Option and Provider Option is modified", "PASS");
	}
	
	
	public By selectExistingGD = By.xpath("(//div[@class='modal-body remove-pad workflow-modal-body ']//input[@id='copyGroupingDefinitionSet'])[2]");
	public By checkbox_GD = By.xpath("//div[@id='editGroupingDefinition']//input[@id='copyClosed']");
	public By selectPeriodToCopy_GD = By.xpath("//select[@id='copyPeriod']");
	
	
	public void GDUsingExistingGroupingDefinition()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickAddButton("Add Grouper Definition icon", "Add Grouper Definition window", addGrouperDefinitionIcon, addGrouperDefinitionWindow);
		enter_text_value("Grouper Definition Set Name", groupingDefinitionSetName, "Test Copy Details from Existing GD Set");
		
		facilityName("PPS", "Apollo srn facility");
		
		enter_text_value("Effective Date", effectiveDate_GD, oParameters.GetParameters("NewEffectiveDate1"));
		enter_text_value("Termination Date", terminationDate_GD, oParameters.GetParameters("TerminationDate1"));
		copyFromExisting("Checkbox for copy details form existing", "Search for Provider Value set", "Select Period to copy", checkbox_GD, selectExistingGD, selectPeriodToCopy_GD,selectUserRate, selectFirstElement, oParameters.GetParameters("CopyFromExistingSet"));
		select_list_value("Grouper Name", grouperNamee, "Medicare");
		select_option("Select Grouper Version", grouperVersion, "3");
		select_option("Reassignment Method", reassignmentMethod, "3");
		clickSaveButton("Save button", "Add Provider Values Window", saveButton, addProviderValueWindow);
	}
	
	
	By openedGrouperDefinition = By.xpath("//div[@class='col-md-6 col-md-5 col-sm-6 large-header ng-binding']");
	By DRGgrouperName = By.xpath("//div[@model='groupingDefinitionSet.drgGrouperMasterID']//select[@id='drgGrouperName']");
	By DRGgrouperVersion = By.xpath("//select[@id='drgGrouperVersion']");
	By effectiveDate_GD1 = By.xpath("//input[@id='startDateDrgGroupingDefinitions']");
	
	
	public void validateCopyFromExisting()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
			
		if(get_field_value("Create new Grouper Name", openedGrouperDefinition).equalsIgnoreCase("Test Copy Details from Existing GD Set"))
		{
			if(!getSelectecText("DRG Grouper Name", DRGgrouperName,"value").isEmpty() && !getSelectecText("DRG Grouper Version", DRGgrouperVersion,"value").isEmpty() 
					&& !get_field_value("Effective Date", effectiveDate_GD1).isEmpty())
				oReport.AddStepResult("New Grouper Definition using copy details from existing", "New Grouper Defintion is created by copying details from existing Grouper Definition set", "PASS");
			else
				oReport.AddStepResult("New Grouper Definition using copy details from existing", "New Grouper Definition is created but field values are empty", "FAIL");
		}
		else
			oReport.AddStepResult("New Grouper Definition using copy details from existing", "New Grouper Definition is created but Grouper Definition name is not displaying ", "FAIL");
	 }
	
	
	public By effectivePeriodDD_GD = By.xpath("//div[@ng-if='selectedDrgDefinition && selectedDrgDefinition.id']//a[@class='btn btn-light btn-default btn-sm']");
	public By addPeriod_GD = By.xpath("//div[@ng-if='selectedDrgDefinition && selectedDrgDefinition.id']//a[@ng-click='addDRGGroupingPeriod()']");
	By periodCount_GD = By.xpath("//li[@ng-repeat='p in definitionPeriodList']");
	By grouperNamePeriod = By.xpath("//div[@model='period.drgGrouperMasterID']//select[@id='drgGrouperName']");
	By grouperVersionPeriod = By.xpath("//div[@model='period.drgGrouperVersionID']//select[@id='drgGrouperVersion']");
	By reassignmentMethodPeriod = By.xpath("//div[@model='period.reassignmentMethodCode']//select[@id='reassignmentMethodCode']");
	
	
	public void addNewPeriodGD()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int beforeCount = noOfRows(periodCount_GD);
		click_button("Add Effective Period Dropdown", effectivePeriodDD_GD);
		clickAddButton("Add Period icon", "Add Period Window", addPeriod_GD, addEffectivePeriodWindow);
		enter_text_value("Effective Date for medicare", addNewEffectivePeriod, oParameters.GetParameters("NewEffectiveDate2"));
		enter_text_value("Termination Date for medicare", newTerminationDate, oParameters.GetParameters("TerminationDate2"));
		performKeyBoardAction("ENTER");
		select_option("DRG Grouper Definition Name", grouperNamePeriod, "0");
		select_option("DRG Grouper Version", grouperVersionPeriod, "2");
		select_option("Reassignment Method", reassignmentMethodPeriod, "4");
		clickSaveButton("Save new period", "Add Effective Priod window", saveEffectivePeriod, addEffectivePeriodWindow);
		 
		int afterCount = noOfRows(periodCount_GD);
		if(afterCount != beforeCount)
			oReport.AddStepResult("New Period", "Clicked on Add Period Icon, Filled all the fields based on selected Grouper Name and new period is created", "PASS");
		else 
			oReport.AddStepResult("New Period", "Clicked on Add Period Icon, Filled all the fields based on selected Grouper Name but new period is not created", "FAIL");
	}
	
	
	By addPeriodCheckBox = By.xpath("//form[@id='addDrgGrouperPeriod']//input[@id='copyClosed']");
	By selectExistingGDSet = By.xpath("(//div[@class='modal-body remove-pad workflow-modal-body ']//input[@id='copyGroupingDefinitionSet'])[1]");
	
	
	public void addPeriodUSingExistingset()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		int beforeCount = noOfRows(periodCount_GD);
		click_button("Add Effective Period Dropdown", effectivePeriodDD_GD);
		clickAddButton("Add Period icon", "Add Period Window", addPeriod_GD, addEffectivePeriodWindow);
		enter_text_value("Effective Date for medicare", addNewEffectivePeriod, oParameters.GetParameters("NewEffectiveDate3"));
		enter_text_value("Termination Date for medicare", newTerminationDate, oParameters.GetParameters("TerminationDate3"));	
		copyFromExisting("Checkbox for copy details from existing set", "Select Grouping Definition Set", "Select Period to copy", addPeriodCheckBox, selectExistingGDSet, selectPeriodToCopy_GD, selectUserRate, selectFirstElement,oParameters.GetParameters("CopyFromExistingSet"));
		fixed_wait_time(5);
		clickSaveButton("Save new period", "Add Effective Priod window", saveEffectivePeriod, addEffectivePeriodWindow); 
		int afterCount = noOfRows(periodCount_GD);
		
		if(afterCount != beforeCount)
			oReport.AddStepResult("New Period", "Clicked on Add Period Icon, Filled all the fields based on selected Grouper Name and new period is created", "PASS");
		else 
			oReport.AddStepResult("New Period", "Clicked on Add Period Icon, Filled all the fields based on selected Grouper Name but new period is not created", "FAIL");
	}
	

	By hoverOverExistingPeriod_GD = By.xpath("(//a[@ng-click='selectPeriod(p)'])[1]");
	By deletePeriod_GD = By.xpath("(//i[@ng-click='deleteDrgGrouperDefinitionPeriod(p, periods)'])[1]");
	//Method to delete Period
	public void deletePeriod_GD(By periodDropDown, By count)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		click_button("Effective Period Drop down", periodDropDown);
		int countBeforeDelete = noOfRows(count);
		System.out.println(countBeforeDelete);
		mouse_hover("existing period", hoverOverExistingPeriod_GD);
		click_button("Delete Period icon", deletePeriod_GD);
		click_button("Delete button on pop-up", popUpDeleteButton);
		int countAfterDelete = noOfRows(count);
			
		if(countBeforeDelete >= countAfterDelete)
			oReport.AddStepResult("Delete Period", "Clicked on delete icon and period is deleted successfully", "PASS");
		else
			oReport.AddStepResult("Delete Period", "Clicked on delete icon, but period is not deleted", "FAIL");
	}

	public void DRG_GroupingDefinitions()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS DRG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Grouping Definition", "Grouping Definition page validation", groupingDefinitionsTab, OpenPageValidation);//Method navigates to Provider Values tab
		waitFor(openedGrouperDefinition, "Opened Set Name");
		
		//Method to create grouping definition for Medicare
		addGrouperDefinition("Medicare");
		validatingGrouperDefinition(oParameters.GetParameters("newGroupingDefinition"));
		deleteSet(deleteGrouperDefinition,"Grouper Definition");
		
		//Method to create grouping definition for CHAMPUS/TRICARE
		addGrouperDefinition("CHAMPUS/TRICARE");
		validatingGrouperDefinition(oParameters.GetParameters("newGroupingDefinition"));
		editGrouperOptionName();
		
		//Method to create grouping definition for AP - All Patient
		addGrouperDefinition("AP - All Patient");
		validatingGrouperDefinition(oParameters.GetParameters("newGroupingDefinition"));
		deleteSet(deleteGrouperDefinition,"Grouper Definition");
		
		//Method to create grouping definition for Medicare IP Psychiatric
		addGrouperDefinition("Medicare IP Psychiatric");
		validatingGrouperDefinition(oParameters.GetParameters("newGroupingDefinition"));
		deleteSet(deleteGrouperDefinition,"Grouper Definition");
		
		//Method to create grouping definition for APR - All Patient Refined
		addGrouperDefinition("APR - All Patient Refined");
		validatingGrouperDefinition(oParameters.GetParameters("newGroupingDefinition"));
		deleteSet(deleteGrouperDefinition,"Grouper Definition");
		
		//Method to create grouping definition for NY APR and Psych
		addGrouperDefinition("NY APR and Psych");
		validatingGrouperDefinition(oParameters.GetParameters("newGroupingDefinition"));
		deleteSet(deleteGrouperDefinition,"Grouper Definition");
		
		
		//Method to create grouping definition by coping details from existing set
		GDUsingExistingGroupingDefinition();
		validateCopyFromExisting();
		deleteSet(deleteGrouperDefinition,"Grouper Definition");
		
		//Method for Editing Grouper Definitions
		//searchPPS("Search Grouping Definitions", oParameters.GetParameters("AfterGrouperDefinition"), openedGrouperDefinition, searchedResult);
		//searchPPS("Search Grouping Definitions", "CHAMPUS/TRICARE_1555031165436_Edit", openedGrouperDefinition, searchedResult);
		editGrouperOption();
		validateEditGD();
		
		//Method for Adding new Period 
		addNewPeriodGD();
		addPeriodUSingExistingset();
		deletePeriod_GD(effectivePeriodDD_GD,periodCount_GD);//Method for deleting period
		deleteSet(deleteGrouperDefinition,"Grouper Definition");//Method for deleting Grouping Definition set
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public By nameTextbox = By.xpath("//div[@class='form-group col-md-12 col-sm-12 col-lg-12 ng-isolate-scope']//input");
	
	public By addPPSSetButton = By.xpath("//div[contains(@class,'list-header drillable-header')]");
	
	public By addProviderValuesStartDate = By.xpath("//input[@id='startDateSet']");
	
	public By addAPCGrouperStartDate = By.xpath("//input[@id='startDateApcGrouperoptionSet']");
	
	public By providerValueStatusDD = By.xpath("//select[@id='statusCode']");
	
	By medicareState = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='state']");
	
	By medicareCarrier = By.xpath("//div[@form-id='masterSetFormModel.formId']//input[@id='carrier']");
	
	By medicareLocality = By.xpath("//div[@form-id='masterSetFormModel.formId']//input[@id='locality']");
	
	By tricareLocality = By.xpath("//div[@form-id='masterSetFormModel.formId']//input[@id='localityTricare']");
	
	By providerValuesTitlebar = By.xpath("//div[@class='col-lg-9 col-md-9 col-sm-9 hide-overflow xl-header ng-binding']");
	
	public By grouperNameDropdown = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='apcGrouperName']");
	
	public By groupingDefinitionStartDate = By.xpath("//div[@id='addGroupingDefinitionSetModal']//input[@id='startDateApcGroupingDefinition']");
	
	public By groupingDefinitionSetGrouperNameDD = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='grouperName']");
	
	public By grouperDefinitionVersionDD = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='grouperVersion']");
	
	public By APCAPGGrouperOptionSetSB = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='grouperOptionSet']");
	
	By APCAPGGrouperOptionSetSBFirstResult = By.xpath("//div[@model='definitionSetSearch.grouperOptionSet']//ul[@class='dropdown-menu']//li[1]");
	
    public By APCAPGProviderValuesSetSB = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='providerValuesSetApc']");
    
    By APCAPGProviderValuesSetSBFirstResult = By.xpath("//div[@model='definitionSetSearch.providerValuesSet']//ul/li[1]");
    
    public By ICDVersionDD = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='icdOverride']");
    
    public By addGroupingDefinitionSetWindow = By.xpath("//div[@title='Add Grouping Definition Set']");
    
    public By addAPCGrouperOptionsSetWindow = By.xpath("//div[@title='Add APC Grouper Options Set']");
	
		
	public void addPPS_APCAPG_Set(String PPSType,String pricerName)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(PPSType.equalsIgnoreCase("Provider Values"))
		{
			clickAddButton("Add Provider Value Set button", "Add Provider Value Set Window", addPPSSetButton, addProviderValueWindow);
			
			enter_text_value("Name box", nameTextbox, pricerName+"_"+System.currentTimeMillis());
			oParameters.SetParameters("PPSSetName", get_field_value("PPS Name", nameTextbox));
			
			facilityName("PPS", "Apollo srn facility");
			
			enter_text_value("Start Date", addProviderValuesStartDate, get_specified_date(0));
			performKeyBoardAction("ENTER");
			select_option("Provider Value Status drop down", providerValueStatusDD, "0");
			
			selectOption(selectProviderValues,"visibletext",pricerName);
			
			if(pricerName.equalsIgnoreCase("EAPG"))
			{
				select_option("Scheme drop down", scheme, get_random_number(1));			
			}
			else if(pricerName.equalsIgnoreCase("Medicare"))
			{
				select_option("State drop down", medicareState, get_random_number(1));
				enter_text_value("Carrier Value", medicareCarrier, get_random_number(5));
				enter_text_value("Locality Value", medicareLocality, get_random_number(2));			
			}	
			else if(pricerName.equalsIgnoreCase("TRICARE APC"))
			{
				select_option("Medicare State", medicareState, get_random_number(1));
				enter_text_value("TRICARE APC Locality", tricareLocality, get_random_number(2));			
			}
			
			select_option("Pricer Version drop down", pricerVersion, get_random_number(1));
		}	
		else if(PPSType.equalsIgnoreCase("Grouper Options"))
		{			
			clickAddButton("Add Grouper Options Set button", "Add APC Grouper Options Set Window", addPPSSetButton, addAPCGrouperOptionsSetWindow);
			
			enter_text_value("Name box", nameTextbox, pricerName+"_"+System.currentTimeMillis());
			oParameters.SetParameters("PPSSetName", get_field_value("PPS Name", nameTextbox));
			
			facilityName("PPS", "Apollo srn facility");
			
			enter_text_value("Start Date", addAPCGrouperStartDate, get_specified_date(0));
			performKeyBoardAction("ENTER");			
			selectOption(grouperNameDropdown,"visibletext",pricerName);
		}
		else if(PPSType.equalsIgnoreCase("Grouping Definitions"))
		{			
			clickAddButton("Add Grouping Definition Set button", "Add Grouping Definition Set window", addPPSSetButton, addGroupingDefinitionSetWindow);
			
			enter_text_value("Name box", nameTextbox, pricerName+"_"+System.currentTimeMillis());
			oParameters.SetParameters("PPSSetName", get_field_value("PPS Name", nameTextbox));
			
			facilityName("PPS", "Apollo srn facility");
				
			enter_text_value("Grouping Definition StartDate", groupingDefinitionStartDate, get_specified_date(0));
			performKeyBoardAction("ENTER");
			
			selectOption(groupingDefinitionSetGrouperNameDD,"visibletext",pricerName);				
			select_option("Gruper Definition Version", grouperDefinitionVersionDD, get_random_number(1));
			
			click_button("APC/APG Grouper Option Set", APCAPGGrouperOptionSetSB);
			performKeyBoardAction("ENTER");
			click_button("APC/APG Grouper Option Set first search value", APCAPGGrouperOptionSetSBFirstResult);
			
			click_button("APC/APG Grouper Option Set", APCAPGProviderValuesSetSB);
			performKeyBoardAction("ENTER");
			click_button("APC/APG Grouper Option Set first search value", APCAPGProviderValuesSetSBFirstResult);
				
			select_option("ICD Version Override drop down", ICDVersionDD, "0");					
		}		
	}
		
	
	By providerValuesPricerName = By.xpath("//div[@class='pad-r-15 pull-right xl-header ng-scope']/span[@class='ng-binding']");
	
	By grouperOptionsGrouperName = By.xpath("//div[@class='pad-r-5 pull-right xl-header']/span[@class='ng-binding']");
	
	By groupingDefinitionsGrouperName = By.xpath("//form[@id='editGroupingDefinitionPeriod']//select[@id='grouperName']");
	
	
	public void PPSSetSaveAndValidation(String PPSType,String pricerName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By PPSpricerName = null;
		
		click_button("Save Button", saveButton);
		
		if(PPSType.equals("Provider Values"))
			PPSpricerName = providerValuesPricerName;
		else if(PPSType.equals("Grouper Options"))
			PPSpricerName = grouperOptionsGrouperName;

		if(PPSType.equals("Grouping Definitions"))
		{
			Select select = new Select(driver.findElement(By.xpath("//form[@id='editGroupingDefinitionPeriod']//select[@id='grouperName']")));
			String grouperName = select.getFirstSelectedOption().getText();
			
			if(grouperName.equals(pricerName))
				oReport.AddStepResult(pricerName+"PPS Set", "In 'Add "+PPSType+" Set' window filled all the mandatory fields then selected Pricer Name as '"+pricerName+"', verified that PPS "+pricerName+" type PPS set added", "PASS");
			else
				oReport.AddStepResult(pricerName+"PPS Set", "In 'Add "+PPSType+" Set' window filled all the mandatory fields then selected Pricer Name as '"+pricerName+"', but that PPS "+pricerName+" type PPS set is not added", "FAIL");
		}	
		else
		{
			waitFor(PPSpricerName,"Pricer Name");
			
			if(get_field_value("PPS Set Pricer Name", PPSpricerName).equalsIgnoreCase(pricerName))
				oReport.AddStepResult(pricerName+"PPS Set", "In 'Add "+PPSType+" Set' window filled all the mandatory fields then selected Pricer Name as '"+pricerName+"', verified that PPS "+pricerName+" type PPS set added", "PASS");
			else
				oReport.AddStepResult(pricerName+"PPS Set", "In 'Add "+PPSType+" Set' window filled all the mandatory fields then selected Pricer Name as '"+pricerName+"', but that PPS "+pricerName+" type PPS set is not added", "FAIL");	
		}
	}
	
	
	// this method will edit the name of the PPS
	public void editPPSsetName(By editIcon,By editWindow,By PPSTitle)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit icon", editIcon);
		
		if(IsDisplayed("PPS Set Edit Window", editWindow))
		{
			oParameters.SetParameters("previousSetName", get_field_value("PPS Set Name", nameTextbox));
			enter_text_value_without_clear("PPS Set Name", nameTextbox, "_Edit");
			oParameters.SetParameters("ModifiedPPSSetValue", get_field_value("PPS Set Name", nameTextbox));
			click_button("PPS Set Name edit window save button", saveEffectivePeriod);
			
			if(oParameters.GetParameters("ModifiedPPSSetValue").equalsIgnoreCase(get_field_value("Opened Provider Value Set Name", PPSTitle)))
				oReport.AddStepResult("Edit PPS Set Name", "Clicked on Edit icon, Modified the PPS Set name from"+" "+oParameters.GetParameters("previousSetName")+" "+"to"+" "+get_field_value("Opened PPS Set Name", PPSTitle), "PASS");
			else 
				oReport.AddStepResult("Edit PPS Set Name", "Clicked on edit icon but PPS set name is not modified", "FAIL");
		}
		else
			oReport.AddStepResult("PPS Set Edit Window", "Edit PPS Set window is not displayed", "FAIL");	
	}	
	
		
	public By periodDropdown = By.xpath("//div[@active-tab='activeTab']//a[@class='btn btn-light btn-default btn-sm']");
	
	By firstPeriodDate = By.xpath("//ul[@class='dropdown-menu period-menu mar-l-15']/li[2]");
	
	By periodDateEditIcon = By.xpath("//i[@class='fa fa-fw fa-pencil show-on-hover'][@style='display: inline-block;']");
	
	
	// this method will open edit effective window
	public void periodEditWindow(By periodEditWindow)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Period drop down", periodDropdown);
		mouse_hover("First Period", firstPeriodDate);
		click_button("Period Date edit icon", periodDateEditIcon);
		
		if(IsDisplayed("Edit Effective Period window", periodEditWindow))
			oReport.AddStepResult("Edit Effective Period window", "Clicked on period edit icon, verified that 'Edit Effective Period' window is dispalyed ", "PASS");
		else
			oReport.AddStepResult("Edit Effective Period window", "Clicked on period edit icon but 'Edit Effective Period' window is not dispalyed ", "FAIL");
	}	
	
	
	By activeRadioButton = By.xpath("//form[@id='addProviderValuesPeriod']//input[@value='Actv']");
	
	By editWindowMedicareDD = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='state']");
	
	By editWindowPricerVersionDD = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='pricerVersion']");
	
	public By editWindowSaveButton = By.xpath("//div[@class='workflow  modal-medium']//div[@id='fullFooter']//input[@value='Save']");
	
	By periodActiveStatus = By.xpath("//a[@class='btn btn-light btn-default btn-sm']//i[@class='fa fa-circle active-circle']");
	
	
	//Editing state,version and status of the Provider Values set
	public void editProviderValueStatus(String PPSType)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Active Radio button", activeRadioButton);
		
		if(PPSType.equalsIgnoreCase("PPS APC/APG"))
			select_option("Pricer Version drop down", editWindowPricerVersionDD, get_random_number(1));
		
		click_button("Save Button", editWindowSaveButton);	
		waitFor(periodActiveStatus, "Period dropdown");
		
		if(IsDisplayed("Active period drop down", periodActiveStatus))
			oReport.AddStepResult("Edit PPS set", "In 'Edit Effective Period' window modified the Provider Values then clicked on save, Verified that Provider Values modified without any error ", "PASS");
		else
			oReport.AddStepResult("Edit PPS set", "In 'Edit Effective Period' window modified the Provider Values then clicked on save but that Provider Values modified ", "FAIL");		
	}
	
	
	public By addPeriodButton = By.xpath("//div[@active-tab='activeTab']//a[contains(.,'Add Period')]");
	
	
	//this method will click on add period 
	public void addPeriodWindow(By periodWindow)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Period drop down", periodDropdown);
		click_button("Add Period button", addPeriodButton);
		
		if(IsDisplayed("Add Effective Period Window", periodWindow))
			oReport.AddStepResult("Add Effective Period Window", "Clicked on 'Add Period', Verified that 'Add Effective Period' window is displayed", "PASS");
		else
			oReport.AddStepResult("Add Effective Period Window", "Clicked on 'Add Period' but 'Add Effective Period' window is not displayed", "FAIL");		
	}
	
	
	public By addEffectivePeriodStartDate = By.xpath("//input[@id='startDateProviderValues']");
	
	By pricerNameDropdown = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='pricerName']");
	
	By addPeriodScheme = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='scheme']");
	
	By addPeriodVersion = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='pricerVersion']");
	
	By addPeriodState = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='state']");
	
	By addPeriodCarrier = By.xpath("//div[@form-id='periodFormModel.formId']//input[@id='carrier']");
	
	By addPeriodLocality = By.xpath("//div[@form-id='periodFormModel.formId']//input[@id='locality']");
	
	By addPeriodTricareLocality = By.xpath("//div[@form-id='periodFormModel.formId']//input[@id='localityTricare']");
	
	public By periodEffectiveDateGD = By.xpath("//form[@id='addPpsApcGrouperPeriod']//input[@id='startDateApcGroupingDefinition']");
	
	public By periodTerminationDateGD = By.xpath("//form[@id='addPpsApcGrouperPeriod']//input[@id='stopDateApcGroupingDefinition']");
	
	public By periodGroupingDefinitionSetGrouperNameDD = By.xpath("//form[@id='addPpsApcGrouperPeriod']//select[@id='grouperName']");
	
	public By periodgrouperDefinitionVersionDD = By.xpath("//form[@id='addPpsApcGrouperPeriod']//select[@id='grouperVersion']");
	
	public By periodAPCAPGGrouperOptionSetSB = By.xpath("//form[@id='addPpsApcGrouperPeriod']//input[@id='grouperOptionSet']");
	
	public By periodAPCAPGGrouperOptionSetSBFirstResult = By.xpath("//form[@id='addPpsApcGrouperPeriod']//div[@model]//ul[@class='dropdown-menu']//li[1]");
	
	public By periodAPCAPGProviderValuesSetSB = By.xpath("//form[@id='addPpsApcGrouperPeriod']//input[@id='providerValuesSetApc']");
	
	public By periodAPCAPGProviderValuesSetSBFirstResult = By.xpath("//form[@id='addPpsApcGrouperPeriod']//div[@model='period.providerValuesSet']//ul/li[1]");
	
	public By periodICDVersionDD = By.xpath("//form[@id='addPpsApcGrouperPeriod']//select[@id='icdOverride']");
	
	
	// this method fill all fields in add effective period window
	public void addPeriodDetails(String PPSType,String pricerName,int num)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(PPSType.equalsIgnoreCase("Provider Values"))
		{
			enter_text_value("Start Date", addEffectivePeriodStartDate, get_specified_date(num));
			performKeyBoardAction("ENTER");
			click_button("Active Radio button", userRateStatus);		
			selectOption(pricerNameDropdown,"visibletext",pricerName);
			
			if(pricerName.equalsIgnoreCase("EAPG"))
			{
				select_option("Scheme drop down", addPeriodScheme, get_random_number(1));			
			}
			else if(pricerName.equalsIgnoreCase("Medicare"))
			{
				select_option("State drop down", addPeriodState, get_random_number(1));
				enter_text_value("Carrier Value", addPeriodCarrier, get_random_number(5));
				enter_text_value("Locality Value", addPeriodLocality, get_random_number(2));			
			}	
			else if(pricerName.equalsIgnoreCase("TRICARE APC"))
			{
				select_option("Medicare State", addPeriodState, get_random_number(1));
				enter_text_value("TRICARE APC Locality", addPeriodTricareLocality, get_random_number(2));			
			}
			
			select_option("Pricer Version drop down", addPeriodVersion, get_random_number(1));	
		}
		else
		{
			enter_text_value("Grouping Definition StartDate", periodEffectiveDateGD, get_specified_date(num));
			performKeyBoardAction("ENTER");
			
			enter_text_value("Grouping Definition StartDate", periodTerminationDateGD, get_specified_date(num+1));
			performKeyBoardAction("ENTER");
			
			selectOption(periodGroupingDefinitionSetGrouperNameDD,"visibletext",pricerName);				
			select_option("Gruper Definition Version", periodgrouperDefinitionVersionDD, get_random_number(1));
			
			click_button("APC/APG Grouper Option Set", periodAPCAPGGrouperOptionSetSB);
			performKeyBoardAction("ENTER");
			click_button("APC/APG Grouper Option Set first search value", periodAPCAPGGrouperOptionSetSBFirstResult);
			
			click_button("APC/APG Grouper Option Set", periodAPCAPGProviderValuesSetSB);
			performKeyBoardAction("ENTER");
			click_button("APC/APG Grouper Option Set first search value", periodAPCAPGProviderValuesSetSBFirstResult);
				
			select_option("ICD Version Override drop down", periodICDVersionDD, "0");
		}		
	}
	
	
	// This method will save and validate period PPS Set type
	public void addPeriodWindowPPSSetValidation(String PPSType,String pricerName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(PPSType.equalsIgnoreCase("Provider Values"))
		{
			click_button("Save Button", saveButton);
			
			if(get_field_value("PPS Set Pricer Name", providerValuesPricerName).equalsIgnoreCase(pricerName))
				oReport.AddStepResult(pricerName+"PPS Set", "In 'Add Effective Period' window filled all the mandatory fields then selected Pricer Name as '"+pricerName+"', verified that PPS "+pricerName+" type PPS set added", "PASS");
			else
				oReport.AddStepResult(pricerName+"PPS Set", "In 'Add Effective Period' window filled all the mandatory fields then selected Pricer Name as '"+pricerName+"', but that PPS "+pricerName+" type PPS set is not added", "FAIL");
		}
		else
		{
			click_button("Save Button", saveEffectivePeriod);
			
			Select select = new Select(driver.findElement(By.xpath("//form[@id='editGroupingDefinitionPeriod']//select[@id='grouperName']")));
			String grouperName = select.getFirstSelectedOption().getText();
			
			if(grouperName.equals(pricerName))
				oReport.AddStepResult(pricerName+"PPS Set", "In 'Add Effective Period' window filled all the mandatory fields then selected Pricer Name as '"+pricerName+"', verified that PPS "+pricerName+" type PPS set added", "PASS");
			else
				oReport.AddStepResult(pricerName+"PPS Set", "In 'Add Effective Period' window filled all the mandatory fields then selected Pricer Name as '"+pricerName+"', but that PPS "+pricerName+" type PPS set is not added", "FAIL");			
		}

	}
	
	
	By inactiveRadioButton = By.xpath("//form[@id='addProviderValuesPeriod']//input[@value='Prelim']");
	
	
	// this method will edit the status of the PPS Set
	public void editPPSSetStatus(By statusEle,String status)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Active Radio button", statusEle);		
		click_button("Save Button", editWindowSaveButton);		
		waitFor(periodActiveStatus, "Period dropdown");
		
		if(status.equalsIgnoreCase("Inactive"))
		{
			if(IsDisplayed("Active period drop down", periodActiveStatus))
				oReport.AddStepResult("Edit PPS set", "In 'Edit Effective Period' window changed the Status then clicked on save but status is not changed from Active to inactive ", "FAIL");		
			else
				oReport.AddStepResult("Edit PPS set", "In 'Edit Effective Period' window changed the Status then clicked on save, Verified that status is changed from Active to inactive without any error ", "PASS");
		}
		else
		{
			if(IsDisplayed("Active period drop down", periodActiveStatus))
				oReport.AddStepResult("Edit PPS set", "In 'Edit Effective Period' window changed the Status then clicked on save, Verified that status is changed from Active to inactive without any error ", "PASS");				
			else
				oReport.AddStepResult("Edit PPS set", "In 'Edit Effective Period' window changed the Status then clicked on save but status is not changed from Active to inactive ", "FAIL");						
		}
	}
	
	
	public By copyFromExistingPPSSetCheckBox = By.xpath("//div[@ng-hide='copyPeriod.copyExistingPeriod || providerPeriod.id']//input[@id='copyClosed']");
	
	public By selectProviderValueSetSB = By.xpath("//div[@model='copyPeriod.providerSet.id']//input[@id='providerValuesSearch']");
	
	public By selectPeriodtoCopyDD = By.xpath("//select[@id='copyPeriod']");
	
	public By pricerVersionDD = By.xpath("//select[@id='copyPeriodSelectVersion']");
	
	
	// Adding period from existing PPS set
	public void addPeriodFromExistingProviderValueSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Start Date", addEffectivePeriodStartDate, get_specified_date(16));
		performKeyBoardAction("ENTER");
		click_button("Copy Details from Existing Provider Value Set check box", copyFromExistingPPSSetCheckBox);
		waitFor(selectProviderValueSetSB, "Select Provider value set search box");
		enter_text_value("Copy Details from Existing Provider Value Set check box", selectProviderValueSetSB, "EAPG_Automation_DND");
		fixed_wait_time(4);
		select_option("Select Period to Copy drop down", selectPeriodtoCopyDD, "0");
		select_option("Pricer Version drop down", pricerVersionDD, get_random_number(1));
		click_button("Add Effective Period save button", saveButton);		
			
		if(get_field_value("PPS Set Pricer Name", providerValuesPricerName).equalsIgnoreCase("EAPG"))
			oReport.AddStepResult("PPS Set", "In 'Add Effective Period' window filled all the mandatory fields then clicked on 'Copy Details from Existing Provider Value Set' then selected Provider value set then clicked on save, verified that new effective period is created with details copied from selected existing Pricer Name ", "PASS");
		else
			oReport.AddStepResult("PPS Set", "In 'Add Effective Period' window filled all the mandatory fields then clicked on 'Copy Details from Existing Provider Value Set' then selected Provider value set then clicked on save but that new effective period is not created with details copied from selected existing Pricer Name ", "FAIL");
	}
	
	
	
	By periodDateDeleteIcon = By.xpath("//ul[@class='dropdown-menu period-menu mar-l-15']//i[contains(@class,'period-minus')][@style='display: inline-block;']");
	
	By periodDropDownElements = By.xpath("//ul[@class='dropdown-menu period-menu mar-l-15']/li");
	
	By deleteProviderValuesSetPeriodWindow = By.xpath("//div[@class='medium-header bold ng-binding']");
		
	By deletePopupwindowDelete = By.xpath("//input[@type='button'][@value='Delete']");
	
	
	// This method will delete first period
	public void deleteFirstPeriod()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Period drop down", periodDropdown);
		oParameters.SetParameters("RateSheetPeriodCountBeforeDelete",String.valueOf(get_table_row_count(periodDropDownElements)));
		mouse_hover("First Period", firstPeriodDate);
		click_button("Period Delete Icon", periodDateDeleteIcon);
		
		if(IsDisplayed("Delete Provider Values Set popup", deleteProviderValuesSetPeriodWindow))
			oReport.AddStepResult("Delete popup window", "Clicked on period delete icon, Verified that delete popup is displayed ", "PASS");
		else
			oReport.AddStepResult("Delete popup window", "Clicked on period delete icon but delete popup is not displayed ", "FAIL");
		
		click_button("Popup Delete button", deletePopupwindowDelete);
		waitFor(periodDropdown, "Period Drop down");
		click_button("Period drop down", periodDropdown);		
		oParameters.SetParameters("RateSheetPeriodCountAfterDelete",String.valueOf(get_table_row_count(periodDropDownElements)));

		if(oParameters.GetParameters("RateSheetPeriodCountBeforeDelete").equals(String.valueOf(get_table_row_count(periodDropDownElements))))
			oReport.AddStepResult("Period Deleted","Clicked on period delete icon but that peroid is not deleted ", "FAIL");
		else
			oReport.AddStepResult("Period Deleted", "Period deleted successfully ", "PASS");
		
		click_button("Period drop down", periodDropdown);
	}
	
	
	By formatValue = By.xpath("//table[contains(@class,'table table-condensed table-striped table-responsive table')]//tr[1]/td[2]");
	
	By attributeValueBox = By.xpath("//table[contains(@class,'table table-condensed table-striped table-responsive table')]//tr[1]/td[3]//input");
	
	By providerValueEntriesSave = By.xpath("//input[@ng-click='saveProviderValueEntries()'][@value='Save']");
	
	
	// This method will add attribute value
	public void addAttributeValue()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("AttributeFormatValue", get_field_value("Format value", formatValue));
		enter_text_value("Attribute Value", attributeValueBox, oParameters.GetParameters("AttributeFormatValue"));
		click_button("Provider Value Entries save button", providerValueEntriesSave);
		
		if(oParameters.GetParameters("AttributeFormatValue").equalsIgnoreCase(get_field_value("Attribute value", attributeValueBox)))
			oReport.AddStepResult("Attribute Value", "Added provider value set attribute value then clicked on save, Verified that attribute value saved ", "PASS");
		else
			oReport.AddStepResult("Attribute Value", "Added provider value set attribute value then clicked on save but that attribute value is not saved ", "FAIL");
	}
	
	
	// This method will modify attribute value
	public void modifyAttributeValue()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("AttributeFormatValue", get_field_value("Format value", formatValue));
		int length = oParameters.GetParameters("AttributeFormatValue").length();
		enter_text_value("Attribute Value", attributeValueBox, get_random_number(length));
		click_button("Provider Value Entries save button", providerValueEntriesSave);
		
		if(oParameters.GetParameters("AttributeFormatValue").equalsIgnoreCase(get_field_value("Attribute value", attributeValueBox)))
			oReport.AddStepResult("Attribute Value", "Added provider value set attribute value then clicked on save but that attribute value is not saved ", "FAIL");
		else
			oReport.AddStepResult("Attribute Value", "Added provider value set attribute value then clicked on save, Verified that attribute value saved ", "PASS");
	}
	
	
	By resetFieldButton = By.xpath("//tr[1]//a[@id='resetfield']/i[@class='left fa fa-times-circle']");
	
	
	// This method will delete attribute value
	public void deleteAttributeValue()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("AttributeFormatValue", get_field_value("Format value", formatValue));
		
		mouse_hover("Attribute Value", attributeValueBox);
		click_button("Reset Field Button", resetFieldButton);		
		click_button("Provider Value Entries save button", providerValueEntriesSave);
		
		if(oParameters.GetParameters("AttributeFormatValue").equalsIgnoreCase(get_field_value("Attribute value", attributeValueBox)))
			oReport.AddStepResult("Attribute Value", "Added attribute value then clicked on save, Verified that attribute value saved ", "FAIL");
		else
			oReport.AddStepResult("Attribute Value", "Added attribute value then clicked on save but that attribute value is not saved ", "PASS");
	}
	
	
	By providerValuesSetDeleteIcon = By.xpath("//a[@title='Delete Provider Value Set']//i[@class='left fa fa-minus-square']");
	
	By grouperOptionsSetDeleteIcon = By.xpath("//div[@class='col-lg-10 col-md-10 col-sm-10 large-height pad-r-0 ng-scope']//i[@class='left fa fa-minus-square']");
	
	By groupingDefinitionsSetDeleteIcon = By.xpath("//div[@class='pad-l-0 col-lg-10 col-md-10 col-sm-10 large-height adjust-height pad-r-0 ng-scope']//i[@class='left fa fa-minus-square']");
	
	
	// to delete PPS set
	public void deletePPS(String placeholder,String existingUserRateSetName,By deleteIcon)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(4);
		By searchPPSGroup = By.xpath("//input[@placeholder='"+placeholder+"']");
		enter_text_value("Search for existing PPS Set", searchPPSGroup, existingUserRateSetName);
		fixed_wait_time(4);
		click_button("Clicking on searched PPS Set", searchedResult);
		fixed_wait_time(4);
		click_button("Delete Icon", deleteIcon);
		fixed_wait_time(2);
		click_button("Popup delete", deletePopupwindowDelete);		
	}
	
	
	// PPS APC/APG_Provider Values CRUD operations VR	
	public void PPS_APC_APG_ProviderValues()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS APC/APG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Provider Values", " Nothing is selected.", providerValuesTab, OpenPageValidation);//Method navigates to Provider Values tab
		
		addPPS_APCAPG_Set("Provider Values","EAPG");
		PPSSetSaveAndValidation("Provider Values","EAPG");
		
		searchPPS("Search Provider Values", oParameters.GetParameters("PPSSetName"), providerValuesTitlebar, searchedResult);
		deletePPS("Search Provider Values",oParameters.GetParameters("PPSSetName"),providerValuesSetDeleteIcon);
		
		addPPS_APCAPG_Set("Provider Values","Medicare");
		PPSSetSaveAndValidation("Provider Values","Medicare");
		deletePPS("Search Provider Values",oParameters.GetParameters("PPSSetName"),providerValuesSetDeleteIcon);
		
		addPPS_APCAPG_Set("Provider Values","TRICARE APC");
		PPSSetSaveAndValidation("Provider Values","TRICARE APC");
		deletePPS("Search Provider Values",oParameters.GetParameters("PPSSetName"),providerValuesSetDeleteIcon);
		
		addPPS_APCAPG_Set("Provider Values","New York EAPG");
		PPSSetSaveAndValidation("Provider Values","New York EAPG");
		
		editPPSsetName(editIcon_PV,editWindow_PV,providerValuesTitlebar);
		
		periodEditWindow(editEffectiveWindow);
		editProviderValueStatus("PPS APC/APG");
		
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodDetails("Provider Values","Medicare",10);
		addPeriodWindowPPSSetValidation("Provider Values","Medicare");
		
		periodEditWindow(editEffectiveWindow);
		editPPSSetStatus(inactiveRadioButton,"Inactive");
		
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodDetails("Provider Values","EAPG",12);
		addPeriodWindowPPSSetValidation("Provider Values","EAPG");
		
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodDetails("Provider Values","New York EAPG",14);
		addPeriodWindowPPSSetValidation("Provider Values","New York EAPG");
		
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodFromExistingProviderValueSet();
		
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodDetails("Provider Values","TRICARE APC",18);
		addPeriodWindowPPSSetValidation("Provider Values","TRICARE APC");
		
		deleteFirstPeriod();
		addAttributeValue();
		modifyAttributeValue();
		deleteAttributeValue();		
		logout();
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------
		
	By grouperOptionsTitlebar = By.xpath("//div[@class='col-md-6 col-md-5 col-sm-6 xl-header ng-binding']");
	
	public By copyFromExistingCheckBox = By.xpath("//label[contains(.,'Copy Details from Existing Grouper Options Set')]//input[@id='copyClosed']");
	
	public By selectGrouperOptionsSB = By.xpath("//input[@id='copyGrouperOptionsSet']");
	
	public By periodtoCopyDD = By.xpath("//select[@id='copyPeriod']");
		
	By grouperOptionsEditWindow = By.xpath("//div[@title='Edit APC Grouper Options Set']");
	
	By openedGrouperOptionsSetTitle = By.xpath("//div[@class='col-md-6 col-md-5 col-sm-6 xl-header ng-binding']");
	
	
	// copying grouper options set from existing set
	public void copyGrouperOptionsSetFromExistingSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Copy Details from Existing Grouper Options Set check box", copyFromExistingCheckBox);
		enter_text_value("Select Grouper Options Set search box", selectGrouperOptionsSB, "Medicare_Automation_DND");
		select_option("Period to copy dropdown", periodtoCopyDD, "0");
		click_button("Save button", saveButton);
		
		if(get_field_value("Values Set", firstAttributeValues).equalsIgnoreCase("1 - Claim line denial or reject"))
			oReport.AddStepResult("", "In 'Add APC Grouper Options Set' window filled all the mandatory fields then clicked on 'Copy Details from Existing Grouper Options Set' then selected Grouper Option Set then clicked on save, Verified that new APC/APG Grouper Options Set is created with values copied from existing Grouper Option Set value ", "PASS");
		else
			oReport.AddStepResult("", "In 'Add APC Grouper Options Set' window filled all the mandatory fields then clicked on 'Copy Details from Existing Grouper Options Set' then selected Grouper Option Set then clicked on save, but that new APC/APG Grouper Options Set is not created with values copied from existing Grouper Option Set value ", "FAIL");		
	}
	
	
	By firstAttribute = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height']//tbody/tr[1]/td[1]");
	
	By attributeEditWindow = By.xpath("//div[@id='editGrouperOptionsEntryPanel']//div[@class='medium-header truncate ng-binding']");
	
	
	// this method will click on attribute value
	public void clickOnAttributeValue()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("FirstAttributeValue", get_field_value("Fitst Attribute", firstAttribute));
		click_button("First Attribute", firstAttribute);
		
		if(oParameters.GetParameters("FirstAttributeValue").equalsIgnoreCase(get_field_value("Attribute Edit Window", attributeEditWindow)))
			oReport.AddStepResult("Attribute Edit Window", "Hovered over and clicked on attribute value, Verified that attribute value tab is displayed ", "PASS");
		else
			oReport.AddStepResult("Attribute Edit Window", "Hovered over and clicked on attribute value but that attribute value tab is not displayed ", "FAIL");
	}
	
	
	By firstValueRadio = By.xpath("//form[@id='addEditApcGrouperOptionsEntry']/div/p[1]");
	
	By firstRadioButton = By.xpath("//form[@id='addEditApcGrouperOptionsEntry']/div/p[1]/input");
	
	public By attributeValueEditWindowSave = By.xpath("//div[@id='fullFooter']//button[@value='Save']");
	
	By firstAttributeValues = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height']//tbody/tr[1]/td[2]");
	
	
	// This method will select value for attribute 
	public void selectValueForAttribute()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("FirstValue", get_field_value("First Value", firstValueRadio));
		click_button("First value Radio button", firstRadioButton);
		click_button("Select Attributes for Values window save", attributeValueEditWindowSave);
		
		if(oParameters.GetParameters("FirstValue").equalsIgnoreCase(get_field_value("Firtst Attribute Value", firstAttributeValues)))
			oReport.AddStepResult("Attribute Value", "In Attribute value tab selected value for attribute then clicked on save, Verified that selected values are displayed for selected attribute", "PASS");
		else
			oReport.AddStepResult("Attribute Value", "In Attribute value tab selected value for attribute then clicked on save but that selected values are not displayed for selected attribute", "FAIL");		
	}
	
	
	//This method will search and open Medicare PPS Set
	public void clickMedicarePPSSetAttribute()
	{ 
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("FirstAttributeValue", get_field_value("Fitst Attribute", firstAttribute));
		click_button("First Attribute", firstAttribute);
		
		if(oParameters.GetParameters("FirstAttributeValue").equalsIgnoreCase(get_field_value("Attribute Edit Window", attributeEditWindow)))
			oReport.AddStepResult("Attribute Edit Window", "Hovered over and clicked on attribute value, Verified that attribute value tab is displayed ", "PASS");
		else
			oReport.AddStepResult("Attribute Edit Window", "Hovered over and clicked on attribute value but that attribute value tab is not displayed ", "FAIL");	
	}
	
	
	By selectAll = By.xpath("//span[text()='Select All']");

	By deSelectAll = By.xpath("//span[text()='Deselect All']");
	
	
	// This method will select all and deselect all values for attribute
	public void attributeValuesModifications()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Selecting all values
		
		click_button("Select All", selectAll);		
		oParameters.SetParameters("FirstValue", get_field_value("First Value", firstValueRadio));		
		click_button("Attribute Value Edit Window Save", attributeValueEditWindowSave);
		
		if(get_field_value("Firtst Attribute Value", firstAttributeValues).contains("1 - Claim line denial or reject"))
			oReport.AddStepResult("Attribute Value", "In Attribute value tab selected value for attribute then clicked on save, Verified that selected values are displayed for selected attribute", "PASS");
		else
			oReport.AddStepResult("Attribute Value", "In Attribute value tab selected value for attribute then clicked on save but that selected values are not displayed for selected attribute", "FAIL");
		
		// Selecting one value
		
		click_button("First Attribute", firstAttribute);
		click_button("Deselect All", deSelectAll);
		oParameters.SetParameters("FirstValue", get_field_value("First Value", firstValueRadio));	
		click_button("First value check box", firstRadioButton);
		click_button("Attribute Value Edit Window Save", attributeValueEditWindowSave);
		
		if(get_field_value("Firtst Attribute Value", firstAttributeValues).equalsIgnoreCase("1 - Claim line denial or reject"))
			oReport.AddStepResult("Attribute Value", "In Attribute value tab selected value for attribute then clicked on save, Verified that selected values are displayed for selected attribute", "PASS");
		else
			oReport.AddStepResult("Attribute Value", "In Attribute value tab selected value for attribute then clicked on save but that selected values are not displayed for selected attribute", "FAIL");			
	}
	
	
	By effectiveDateGO = By.xpath("//form[@id='addGrouperOptionPeriod']//input[@id='startDate']");
	
	By terminationDateGO = By.xpath("//form[@id='addGrouperOptionPeriod']//input[@id='stopDate']");
	
	By periodDateGO = By.xpath("//div[@add-period='addApcGrouperOptionsPeriod']//a[@class='btn btn-light btn-default btn-sm']//span[1]");
	
	
	//This method will edit start and termination dates
	public void editEffectiveAndTerminationDates()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Effective Date", effectiveDateGO, get_specified_date(35));//10
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", terminationDateGO, get_specified_date(36));//14
		performKeyBoardAction("ENTER");
		click_button("Save button", saveButton);		
		oParameters.SetParameters("PeriodDate", get_field_value("period drop down", periodDateGO).substring(0, 10));

		if(get_specified_date(35).equals(oParameters.GetParameters("PeriodDate")))
			oReport.AddStepResult("PPS Period", "In 'Edit Effective Period' window modified Effective and Termination dates, Verified that Effective and Termination date is modified without any error", "PASS");
		else
			oReport.AddStepResult("PPS Period", "In 'Edit Effective Period' window modified Effective and Termination dates but that Effective and Termination date is not modified ", "FAIL");
	}
	
	
	// to fill effective and termination dates
	public void addPeriodDetails(By startDate,By endDate,By save,By periodDD)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Effective Date", startDate, get_specified_date(38));//4
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", endDate, get_specified_date(40));//6
		performKeyBoardAction("ENTER");
		click_button("Save button", save);	//editWindowSaveButton	
		oParameters.SetParameters("PeriodDate", get_field_value("period drop down", periodDD).substring(0, 10));

		if(get_specified_date(38).equals(oParameters.GetParameters("PeriodDate")))
			oReport.AddStepResult("PPS Period", "In 'Add Effective Period' window entered Effective and Termination dates, Verified that new effective period date is created without any error", "PASS");
		else
			oReport.AddStepResult("PPS Period", "In 'Add Effective Period' window entered Effective and Termination dates but new effective period date is not created ", "FAIL");
	}
	
	
	// to add period from existing grouper options set
	public void addPeriodFromExistingSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addPeriodWindow(addEffectivePeriodWindow);
		
		enter_text_value("Effective Date", effectiveDateGO, get_specified_date(42));
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", terminationDateGO, get_specified_date(44));
		performKeyBoardAction("ENTER");
		click_button("Copy Details from Existing Grouper Options Set check box", copyFromExistingCheckBox);
		enter_text_value("Select Grouper Options Set search box", selectGrouperOptionsSB, "Medicare_Automation_DND");
		select_option("Period to copy dropdown", periodtoCopyDD, "0");
		click_button("Save button", saveButton);
		
		if(get_field_value("Values Set", firstAttributeValues).equalsIgnoreCase("1 - Claim line denial or reject"))
			oReport.AddStepResult("", "In 'Add APC Grouper Options Set' window filled all the mandatory fields then clicked on 'Copy Details from Existing Grouper Options Set' then selected Grouper Option Set then clicked on save, Verified that new APC/APG Grouper Options Set is created with values copied from existing Grouper Option Set value ", "PASS");
		else
			oReport.AddStepResult("", "In 'Add APC Grouper Options Set' window filled all the mandatory fields then clicked on 'Copy Details from Existing Grouper Options Set' then selected Grouper Option Set then clicked on save, but that new APC/APG Grouper Options Set is not created with values copied from existing Grouper Option Set value ", "FAIL");		
	}
	
	
	// PPS APC/APG Grouper Options CRUD operations VR	
	public void PPS_APC_APG_GrouperOptions()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS APC/APG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Grouper Options", " Nothing is selected.", grouperOption, OpenPageValidation);//Method navigates to Provider Values tab
		
		addPPS_APCAPG_Set("Grouper Options","EAPG");
		PPSSetSaveAndValidation("Grouper Options","EAPG");
		
		searchPPS("Search Grouper Options", oParameters.GetParameters("PPSSetName"), grouperOptionsTitlebar, searchedResult);
		deletePPS("Search Grouper Options",oParameters.GetParameters("PPSSetName"),grouperOptionsSetDeleteIcon);
		
		addPPS_APCAPG_Set("Grouper Options","New York EAPG");
		PPSSetSaveAndValidation("Grouper Options","New York EAPG");
		
		clickOnAttributeValue();
		selectValueForAttribute();
		deletePPS("Search Grouper Options",oParameters.GetParameters("PPSSetName"),grouperOptionsSetDeleteIcon);
		
		addPPS_APCAPG_Set("Grouper Options","TRICARE APC");
		PPSSetSaveAndValidation("Grouper Options","TRICARE APC");
		deletePPS("Search Grouper Options",oParameters.GetParameters("PPSSetName"),grouperOptionsSetDeleteIcon);
		
		addPPS_APCAPG_Set("Grouper Options","Medicare");		
		copyGrouperOptionsSetFromExistingSet();		
		deletePPS("Search Grouper Options",oParameters.GetParameters("PPSSetName"),grouperOptionsSetDeleteIcon);
		
		addPPS_APCAPG_Set("Grouper Options","Medicare");
		PPSSetSaveAndValidation("Grouper Options","Medicare");
		
		clickMedicarePPSSetAttribute();
		attributeValuesModifications();
		
		editPPSsetName(editIcon,grouperOptionsEditWindow,openedGrouperOptionsSetTitle);
		
		periodEditWindow(editEffectiveWindow);
		editEffectiveAndTerminationDates();
		
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodDetails(effectiveDateGO,terminationDateGO,saveButton,periodDateGO);
		
		deleteFirstPeriod();		
		addPeriodFromExistingSet();		
		logout();
	}
	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	By groupingDefinitionEditWindow = By.xpath("//div[@title='Edit Grouping Definition Set']");
	
	public By copyFromExistedGroupingDefinitionsSet = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='copyClosedApc']");
	
	public By selectGroupingDefinitionSetSB = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='copyGroupingDefinitionSet']");
	
	
	public void copyFromExistingGroupingDefinitionSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
	
		clickAddButton("Grouping Definition Set", "Add Grouping Definition Set window", addPPSSetButton, addGroupingDefinitionSetWindow);
		
		enter_text_value("Name box", nameTextbox, "EAPG_"+System.currentTimeMillis());
		
		facilityName("PPS", "Apollo srn facility");
		
		enter_text_value("Grouping Definition StartDate", groupingDefinitionStartDate, get_specified_date(0));
		performKeyBoardAction("ENTER");		
		
		click_button("Copy Details from Existing Grouping Definition Set check box", copyFromExistedGroupingDefinitionsSet);
		enter_text_value("", selectGroupingDefinitionSetSB, "EAPG_Automation_DND");
		
		click_button("Save Button", saveButton);
		
		Select select = new Select(driver.findElement(By.xpath("//form[@id='editGroupingDefinitionPeriod']//select[@id='grouperName']")));
		String grouperName = select.getFirstSelectedOption().getText();
		
		if(grouperName.equals("EAPG"))			
			oReport.AddStepResult("", "In 'Add Grouping Definition Set' window filled all the mandatory fields then clicked on 'Copy Details from Existing Grouping Definitions Set' then selected Grouping Definition Set then clicked on save, Verified that new APC/APG Grouping Definition Set is created with values copied from existing Grouper Definition Set value ", "PASS");
		else
			oReport.AddStepResult("", "In 'Add Grouping Definition Set' window filled all the mandatory fields then clicked on 'Copy Details from Existing Grouping Definitions Set' then selected Grouping Definition Set then clicked on save but new APC/APG Grouping Definition Set is not created with values copied from existing Grouper Definition Set value ", "FAIL");
	}
	
	
	By titleBarGD = By.xpath("//div[@class='pad-l-10 col-md-6 col-md-5 col-sm-6 large-header ng-binding']");
	
	By searchPPS = By.xpath("//div[contains(@class,'list-header drillable-header')]/../div/../input");
	
	
	// Method to search and select PPS set
	public void searchGroupingDefinitionSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search for existing User Set", searchPPS, oParameters.GetParameters("PPSSetName"));
		fixed_wait_time(6);
		click_button("Clicking on searched USer Rate Set", searchedResult);
		
		if(get_field_value("Grouping Definitions Title bar", titleBarGD).equalsIgnoreCase(oParameters.GetParameters("PPSSetName")))
			oReport.AddStepResult("", "Searched and selected existed Grouping Definition set, Verified that respective Grouper Definition set is displayed", "PASS");
		else
			oReport.AddStepResult("", "Searched and selected existed Grouping Definition set but respective Grouper Definition set is not displayed", "FAIL");		
	}
	
	
	By effectiveDateGD = By.xpath("//input[@id='startDateApcGroupingDefinitions']");
	
	By terminationDateGD = By.xpath("//input[@id='stopDateApcGroupingDefinitions']");
	
	By saveButtonGD = By.xpath("//div[@id='fullFooter']//button[contains(.,'Save')]");
	
	
	// Method to modify Effective and termination dates
	public void editEffectiveTerminationDates()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Effective Date", effectiveDateGD, get_specified_date(35));//7
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", terminationDateGD, get_specified_date(36));//8
		performKeyBoardAction("ENTER");
		click_button("Save button", saveButtonGD);		
		
		if(get_specified_date(35).equals(get_field_value("period drop down", effectiveDateGD)))
			oReport.AddStepResult("PPS Period", "Modified Effective and Termination dates, Verified that Effective and Termination date is modified without any error", "PASS");
		else
			oReport.AddStepResult("PPS Period", "Modified Effective and Termination dates but that Effective and Termination date is not modified ", "FAIL");	
	}
		
	
	// PPS APC/APG Grouper Options CRUD operations VR	
	public void PPS_APC_APG_GroupingDefinitions()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS APC/APG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Grouping Definitions", " Nothing is selected.", groupingDefinitionsTab, OpenPageValidation);//Method navigates to Provider Values tab	
				
		addPPS_APCAPG_Set("Grouping Definitions","EAPG");
		PPSSetSaveAndValidation("Grouping Definitions","EAPG");	
		deletePPS("Search Grouping Definitions",oParameters.GetParameters("PPSSetName"),groupingDefinitionsSetDeleteIcon);
		
		addPPS_APCAPG_Set("Grouping Definitions","Medicare");
		PPSSetSaveAndValidation("Grouping Definitions","Medicare");	
		deletePPS("Search Grouping Definitions",oParameters.GetParameters("PPSSetName"),groupingDefinitionsSetDeleteIcon);
		
		addPPS_APCAPG_Set("Grouping Definitions","New York EAPG");
		PPSSetSaveAndValidation("Grouping Definitions","New York EAPG");	
		deletePPS("Search Grouping Definitions",oParameters.GetParameters("PPSSetName"),groupingDefinitionsSetDeleteIcon);
		
		addPPS_APCAPG_Set("Grouping Definitions","TRICARE APC");
		PPSSetSaveAndValidation("Grouping Definitions","TRICARE APC");	
		
		copyFromExistingGroupingDefinitionSet();
		searchGroupingDefinitionSet();
		editPPSsetName(editIcon,groupingDefinitionEditWindow,titleBarGD);
		editEffectiveTerminationDates();
		
		addPeriodWindow(addEffectivePeriodWindow);		
		addPeriodDetails("Grouping Definitions","Medicare",38);
		addPeriodWindowPPSSetValidation("Grouping Definitions","Medicare");
		
		addPeriodWindow(addEffectivePeriodWindow);		
		addPeriodDetails("Grouping Definitions","EAPG",40);
		addPeriodWindowPPSSetValidation("Grouping Definitions","EAPG");
		
		addPeriodWindow(addEffectivePeriodWindow);		
		addPeriodDetails("Grouping Definitions","New York EAPG",42);
		addPeriodWindowPPSSetValidation("Grouping Definitions","New York EAPG");
		
		addPeriodWindow(addEffectivePeriodWindow);		
		addPeriodDetails("Grouping Definitions","TRICARE APC",44);
		addPeriodWindowPPSSetValidation("Grouping Definitions","TRICARE APC");
		
		deleteFirstPeriod();
		logout();
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------	
	
	
	public By addRuleSetWindow = By.xpath("//div[@title='Add PPS APC/APG Grouping Rule Set']");
	
	public By ruleSetStartDate = By.xpath("//form[@id='addppsAPCGGroupingRuleSet']//input[@id='startDate']");
	
	public By ruleSetStopDate = By.xpath("//form[@id='addppsAPCGGroupingRuleSet']//input[@id='stopDate']");
	
	public By ruleSetStartTime = By.xpath("//form[@id='addppsAPCGGroupingRuleSet']//input[@id='startTimer']");
	
	public By ruleSetStopTime = By.xpath("//form[@id='addppsAPCGGroupingRuleSet']//input[@id='stoptTimer']");
	
	public By ruleSetDescription = By.xpath("//form[@id='addppsAPCGGroupingRuleSet']//input[@id='description']");
	
	By ruleSetComment = By.xpath("//form[@id='addppsAPCGGroupingRuleSet']//textarea[@id='comment']");
	
	public By copyFromExistingGroupingRuleSet = By.xpath("//form[@id='addppsAPCGGroupingRuleSet']//input[@id='copyGroupingRuleSet']");
	
	By periodDateRuleSet = By.xpath("//div[@id='ppView']//a[@class='btn btn-light btn-default btn-sm']//span[1]");
	
	
	// To add APC/APG Grouping rule set
	public void addGroupingRuleSet()
	{
		clickAddButton("Add a APC/APG Grouping Rule Set button", "Add PPS APC/APG Grouping Rule Set window", addPPSSetButton, addRuleSetWindow);
		
		enter_text_value("Start date", ruleSetStartDate, get_specified_date(1));
		enter_text_value("Start Time", ruleSetStartTime, "00:00");
		performKeyBoardAction("ENTER");
		enter_text_value("Stop date", ruleSetStopDate, get_specified_date(2));
		enter_text_value("Start Time", ruleSetStopTime, "11:00");
		performKeyBoardAction("ENTER");		
		clickSaveButton("Save button", "Add APC/APG Grouping Rule Set Window", saveButton, addRuleSetWindow);
		
		if(get_field_value("Period Date", periodDateRuleSet).contains(get_specified_date(1)))
			oReport.AddStepResult("PPS Period", "In 'Add PPS APC/APG Grouping Rule Set' window entered all the mandatory fields Verified that new Grouping Rule Set is created without any error ", "PASS");
		else
			oReport.AddStepResult("PPS Period", "In 'Add PPS APC/APG Grouping Rule Set' window entered all the mandatory fields but ion dates but new Grouping Rule Set is not created", "FAIL");
	}
	
	
	By addRuleWindow = By.xpath("//div[@title='Add APC/APG Grouping Rule']");
	
	By addQualifierButton = By.xpath("//form[@id='addApcGroupingRuleSetRule']//button[@id='qualifierButton']");
	
	By expressionTextArea = By.xpath("//div[@id='addQualificationGroupModal']//textarea[@id='expression']");
		
	
	// to add QG 
	public void addQG()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		QGLibrary oQGLibrary = new QGLibrary(driver, oReport, oParameters);
		
		clickAddButton("Add Qualifier Button", "Add Qualification Group window", addQualifierButton, addQgWindow);
		oQGLibrary.addQGDetails("QG_"+get_random_alphanumeric(3));
		click_button("Expression Text Area", expressionTextArea);
		enter_text_value("Expression Text Area", expressionTextArea, "TRUE");
		click_button("Validate QG Build button", validateButton);
		click_button("Add QG window save button", saveQG);
		
		if(get_field_value("Created QG Expression", createdQG).equalsIgnoreCase("TRUE")&& IsDisplayed("Add APC/APG Grouping Rule window", addRuleWindow))
			oReport.AddStepResult("New Qualification Group Expression", "In 'Add Qualification Group' window filled all the details then clicked on save, Verified that new qualification group is created and previously opened Add Rule window is displayed", "PASS");
		else
			oReport.AddStepResult("New Qualification Group Expression", "In 'Add Qualification Group' window filled all the details then clicked on save but new qualification group is created or previously opened Add Rule window is not displayed", "FAIL");
	}
	
		
	// To add Rule
	public void addRule(String position)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(2);
		click_button("Add Rule button", addRule);
		
		if(IsDisplayed("Add Rule window", addRuleWindow))
		{	
			oReport.AddStepResult("Add Rule Window", "Clicked on 'Add Rule' button, Verified that Add Rule window is displayed", "PASS");
			
			if(position.equalsIgnoreCase("after"))
			{
				oParameters.SetParameters("FirstPositionRuleBefore", get_field_value("First Rule Name", rule1)); 
				click_button("After/Before button", addRuleBeforeAfter);
				click_button("Add Rule After", addRuleAfter);
			}
			
			enter_text_value("Rule Name", groupingRuleName, "Rule_"+get_random_alphanumeric(3));
			oParameters.SetParameters("RuleName", get_field_value("Given Rule Name", groupingRuleName));	
			
			facilityName("PPS", "Apollo srn facility");
			
			addQG();
			click_button("Grouping Definition search field", groupingDefinition);
			performKeyBoardAction("ENTER");
			fixed_wait_time(5);
			
			if(IsDisplayed("Grouping Definition drop down", groupingDefinitionDD))
			{
				click_button("First value from dropdown", firstElementDD);
				oParameters.SetParameters("selectedGroupingDefinition", get_field_value("selected Grouping definition", groupingDefinition));
				oReport.AddStepResult("Add Rule Window", "Screen shot for assuring all details are filled in Add Rule Window", "SCREENSHOT");
			}				
			else
				oReport.AddStepResult("Select Grouping Definition", "Existing Grouping Definition is not found to select grouping defintion", "FAIL");
		}	
		else
			oReport.AddStepResult("Add Rule Window", "Clicked on 'Add Rule' button but that Add Rule window is not displayed", "FAIL");
		
		fixed_wait_time(5);	
	}
	
	
	By firstPeriod = By.xpath("//ul[@class='dropdown-menu mar-l-15']/li[1]");
	
	By ruleSetEditWindow = By.xpath("//div[@title='Edit PPS APC/APG  Grouping Rule Set']");
	
	By description = By.xpath("//input[@id='description']");
	
	
	
	// to edit Rule Set
	public void editRuleSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Period drop down", periodDropdown);
		mouse_hover("First Period", firstPeriod);
		click_button("Period Date edit icon", periodDateEditIcon);
		
		if(IsDisplayed("Edit Effective Period window", ruleSetEditWindow))
		{	
			oReport.AddStepResult("Edit Effective Period window", "Clicked on period edit icon, verified that 'Edit PPS APC/APG Grouping Rule Set' window is dispalyed ", "PASS");
			
			oParameters.SetParameters("RuleEffectiveDate", get_field_value("Start Date", ruleSetStartDate));
			enter_text_value("Start date", ruleSetStartDate, get_next_date(oParameters.GetParameters("RuleEffectiveDate"), 1)); 
			performKeyBoardAction("ENTER");
			enter_text_value("Start Time", ruleSetStartTime,String.valueOf(get_random_number(10, 22))+":"+String.valueOf(get_random_number(10, 22)));
			performKeyBoardAction("ENTER");
		//	enter_text_value("Stop date", ruleSetStopDate, get_specified_date(2));
		//	enter_text_value("Start Time", ruleSetStopTime, String.valueOf(get_random_number(10, 22))+":"+String.valueOf(get_random_number(10, 22)));
		//	performKeyBoardAction("ENTER");		
			enter_text_value("Description", description, "Modified");
			clickSaveButton("Save button", "Add APC/APG Grouping Rule Set Window", saveButton, addRuleSetWindow);
			oParameters.SetParameters("ModifiedPeriodDate", get_field_value("period drop down", periodDropdown).substring(0, 10));
			
			if(oParameters.GetParameters("ModifiedPeriodDate").equals(get_next_date(oParameters.GetParameters("RuleEffectiveDate"), 1)))
				oReport.AddStepResult("", "In 'Edit PPS APC/APG Grouping Rule Set' window modified date and time then clicked on save, Verified that modified changes displayed	", "PASS");
			else
				oReport.AddStepResult("", "In 'Edit PPS APC/APG Grouping Rule Set' window modified date and time then clicked on save but that modified changes not displayed ", "FAIL");
		}	
		else
			oReport.AddStepResult("Edit Effective Period window", "Clicked on period edit icon but 'Edit PPS APC/APG Grouping Rule Set' window is not dispalyed ", "FAIL");		
	}
	
	
	// PPS APC/APG Grouping Rule Set_CRUD_operation VR	
	public void PPS_APC_APG_GroupingRuleSet()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS APC/APG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Grouping Rule Set", "Grouping Rule Set open page validation", groupingRuleSetTab, OpenPageValidation);//Method navigates to Grouping Rule Set tab
	
	//	addGroupingRuleSet();
		
		click_button("First Entry", firstEntryEffectiveDate);
	//	clickAddButton("Add Grouping Rule Set Icon", "Add APC/APG Grouping Rule window", addRule, addRuleWindow);		
		addRule("");
		validateRule();
		
		editRule("");
		validateEditScenario(); 
		
/*		deleteRule();
		copyFromExisting_GRS("addppsAPCGGroupingRuleSet",addRuleSetWindow,saveButton);
		validateNewGRS(rulesWindow,periodDropdown);*/

		addRule("after");
		validateRulePosition();
		
		editRule("after");
		validateEditRulePostion();
		
		editRuleSet();
		
		filter("PPS","000",addFilter_GRS,"apc");
		filter("Qualification","Test_QG",addFilter_GRS,"apc");
		filter("Rule","Rule_Alp",addFilter_GRS,"apc");
		deleteRule();
		logout();
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------	

	// to add CMG Provider Value set
	public void addCMGProviderValuesSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickAddButton("Provider Value Set", "Add Provider Value Set Window", addPPSSetButton, addProviderValueWindow);
		
		enter_text_value("Name box", nameTextbox, "PPS_CMG"+System.currentTimeMillis());
		oParameters.SetParameters("PPSCMG_ProviderValue", get_field_value("PPS_CMG_ProviderValue", nameTextbox));	
		
		facilityName("PPS", "Apollo srn facility");
		
		enter_text_value("Start Date", addProviderValuesStartDate, get_specified_date(0));
		performKeyBoardAction("ENTER");
		select_option("Provider Value Status drop down", providerValueStatusDD, "0");		
		click_button("Save Button", saveButton);
		
		if(get_field_value("PPS Value title bar", providerValuesTitlebar).equalsIgnoreCase(oParameters.GetParameters("PPSCMG_ProviderValue")))
			oReport.AddStepResult("PPS CMG Provider Values", "In 'Add Provider Values Set' window filled all the mandatory fields then clicked on save, Verified that new CMG Provider Values Set is created without any error", "PASS");
		else
			oReport.AddStepResult("PPS CMG Provider Values", "In 'Add Provider Values Set' window filled all the mandatory fields then clicked on save but new CMG Provider Values Set is not created ", "FAIL");		
	}
	
	
	public By copyFromExistingCMGCheckBox = By.xpath("//input[@id='copyClosedAddSet']");
	
	public By selectCMGProvidervaluesetSB = By.xpath("//div[@model='copyPeriodInSet.ppsProviderValue.id']//input[@id='providerValuesSearch']");
	
	
	// to add new CMG Provider Value from existing set
	public void copyFromExistingCMGProviderValueSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickAddButton("Provider Value Set", "Add Provider Value Set Window", addPPSSetButton, addProviderValueWindow);
		
		enter_text_value("Name box", nameTextbox, "PPS_CMG"+System.currentTimeMillis());
		oParameters.SetParameters("PPSCMGProviderValue", get_field_value("PPS_CMG_ProviderValue", nameTextbox));
		
		facilityName("PPS", "Apollo srn facility");
		
		enter_text_value("Start Date", addProviderValuesStartDate, get_specified_date(0));
		performKeyBoardAction("ENTER");
		select_option("Provider Value Status drop down", providerValueStatusDD, "0");	
		click_button("Copy Details from Existing Provider Value Set check box", copyFromExistingCMGCheckBox);
		
		if(IsDisplayed("Select Provider value set search box", selectCMGProvidervaluesetSB))
		{	
			oReport.AddStepResult("", "In 'Add Provider Value Set' window selected Copy details from existing provider value set checkbox, Verified that Select Provider value Set Search box has present and 'No periods exist' message is not displayed ", "PASS");
			enter_text_value("Search box", selectCMGProvidervaluesetSB, "PPS_CMG_Automation");
			fixed_wait_time(5);
			click_button("Save Button", saveButton);
				
			if(get_field_value("PPS Value title bar", providerValuesTitlebar).equalsIgnoreCase(oParameters.GetParameters("PPSCMGProviderValue")))
				oReport.AddStepResult("PPS CMG Provider Values", "In 'Add Provider Values Set' window filled all the mandatory fields then clicked on save, Verified that new CMG Provider Values Set is created without any error", "PASS");
			else
				oReport.AddStepResult("PPS CMG Provider Values", "In 'Add Provider Values Set' window filled all the mandatory fields then clicked on save but new CMG Provider Values Set is not created ", "FAIL");								
		}	
		else
			oReport.AddStepResult("", "In 'Add Provider Value Set' window selected Copy details from existing provider value set checkbox but Select Provider value Set Search box is not displayed ", "FAIL");		
	}
	
	
	// to search with invalid Provider Values Set
	public void invalidProviderValueSearch()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search PPS Group", searchPPS, get_random_alphanumeric(5));
				
	}
	
	
	By noActivePeriodDropdown = By.xpath("//div[@active-tab='activeTab']//a[@class='btn btn-light btn-default btn-sm']//span[text()='No Active Periods']");
	
	
	// this method will select a provider value set which doesn’t have any period.
	public void noPeriodPPSAndCMGProviderValueSetSearch()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search PPS Group", searchPPS, "PPS_CMG_No_Period_DND");
		fixed_wait_time(4);
		click_button("First search result", searchedResult);
		
		if(IsDisplayed("No Active Periods dropdown", noActivePeriodDropdown))
			oReport.AddStepResult("No Active Periods message", "Selected a provider value set which doesn't have any period, Verified that 'No Active Periods' message has displayed ", "PASS");
		else
			oReport.AddStepResult("No Active Periods message", "Selected a provider value set which doesn't have any period but 'No Active Periods' message not displayed ", "FAIL");
		
		if(get_field_value("Grouping Definitions Title bar", providerValuesTitlebar).equalsIgnoreCase("PPS_CMG_No_Period_DND"))
			oReport.AddStepResult("Search PPS", "Searched and selected existed Provider Values set, Verified that respective Provider Values set is displayed", "PASS");
		else
			oReport.AddStepResult("Search PPS", "Searched and selected existed Provider Values set but respective Provider Values set is not displayed", "FAIL");
	}
	
	
	public void searchCMGProviderValueSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search for existing User Set", searchPPS, "PPS_CMG_Automation");
		fixed_wait_time(6);
		click_button("Clicking on searched USer Rate Set", searchedResult);
		
		if(get_field_value("Grouping Definitions Title bar", providerValuesTitlebar).equalsIgnoreCase("PPS_CMG_Automation"))
			oReport.AddStepResult("Search PPS", "Searched and selected existed Provider Values set, Verified that respective Provider Values set is displayed", "PASS");
		else
			oReport.AddStepResult("Search PPS", "Searched and selected existed Provider Values set but respective Provider Values set is not displayed", "FAIL");			
	}
	
	
	public By terminationDate_CMGPV = By.xpath("//input[@id='stopDateProviderValues']");
	
	By periodDateCMGPV = By.xpath("//div[@add-period='addProviderValuesPeriod']//a[@class='btn btn-light btn-default btn-sm']//span[1]");
	
	
	public void addPeriodPPSCMG()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Effective Date", addEffectivePeriodStartDate, get_specified_date(5));//10
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", terminationDate_CMGPV, get_specified_date(6));//12
		performKeyBoardAction("ENTER");
		click_button("Active Radio button", activeRadioButton);
		click_button("Save button", saveEffectivePeriod);		
		oParameters.SetParameters("PeriodDate", get_field_value("period drop down", periodDateCMGPV).substring(0, 10));

		if(get_specified_date(5).equals(oParameters.GetParameters("PeriodDate")))
			oReport.AddStepResult("PPS Period", "In 'Add Effective Period' window entered Effective and Termination dates, Verified that new effective period date is created without any error", "PASS");
		else
			oReport.AddStepResult("PPS Period", "In 'Add Effective Period' window entered Effective and Termination dates but new effective period date is not created ", "FAIL");	
	}
	
	
	public void editPeriodDates()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Effective Date", addEffectivePeriodStartDate, get_specified_date(7));
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", terminationDate_CMGPV, get_specified_date(8));
		performKeyBoardAction("ENTER");
		click_button("Save button", saveButton);		
		oParameters.SetParameters("PeriodDate", get_field_value("period drop down", periodDateCMGPV).substring(0, 10));

		if(get_specified_date(7).equals(oParameters.GetParameters("PeriodDate")))
			oReport.AddStepResult("PPS Period", "In 'Edit Effective Period' window modified Effective and Termination dates, Verified that Effective and Termination date is modified without any error", "PASS");
		else
			oReport.AddStepResult("PPS Period", "In 'Edit Effective Period' window modified Effective and Termination dates but that Effective and Termination date is not modified ", "FAIL");	
	}
	
	
	public By copyFromExistingPVSet = By.xpath("//form[@id='addProviderValuesPeriod']//input[@id='copyClosed']");	
	
	public By selectCMGProvidervaluesetSB1 = By.xpath("//div[@model='copyPeriod.providerSet.id']//input[@id='providerValuesSearch']");
	
	
	public void addPeriodFromExistingCMGPVSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Effective Date", addEffectivePeriodStartDate, get_specified_date(10));
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", terminationDate_CMGPV, get_specified_date(12));
		performKeyBoardAction("ENTER");
		click_button("Copy Details from Existing Provider Value Set check box", copyFromExistingPVSet);
		enter_text_value("Search box", selectCMGProvidervaluesetSB1, "PPS_CMG_Automation");
		click_button("Save button", saveButton);
	}
	
	
	By CMGUserRateSetFirstSearchResult = By.xpath("//div[@spec='rateTypeFormModel.cmgUserRateMaster.spec']//ul/li[1]");
	
	
	public void addCMGPricerRateTerm()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add term button", oRateSheetLibrary.addTerm);		
		enter_text_value("Add Term Name", oRateSheetLibrary.termName,"CMG_"+System.currentTimeMillis());
		oParameters.SetParameters("CMGTermName", get_field_value("CMG Term Name", oRateSheetLibrary.termName));		
		enter_text_value("Qualification Field", oRateSheetLibrary.qualificationGroupField, "0 - TRUE");
		fixed_wait_time(4);
		selectOption(oRateSheetLibrary.rateTypeSearchBox,"visibletext","IRF CMG Pricer");		
		click_button("CMG User Rate Set search box", oRateSheetLibrary.cmgUserRateSet);
		performKeyBoardAction("ENTER");
		click_button("CMG User Rate Set first search result", CMGUserRateSetFirstSearchResult);		
		enter_text_value("CMG Provider Values Set", oRateSheetLibrary.cmgProviderValuesSet, oParameters.GetParameters("PPSCMG_ProviderValue"));
		fixed_wait_time(3);
		click_button("Term Save button", oRateSheetLibrary.addTermSaveButton);
			
		By addedNewTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+ oParameters.GetParameters("CMGTermName")+"')]");
	
		mouse_hover("Added CMG Term", addedNewTerm);

		if(IsDisplayed("Added CMG Term", addedNewTerm))
			oReport.AddStepResult("New Term is ", "In 'Add Term' window filled all the fields then selected Rate Type 'IRF CMG Pricer' then clicked on save, Verified that new term is created with selected CMG User Rate Set and selected CMG Provider Value Set", "PASS");
		else
			oReport.AddStepResult("New Term is ", "In 'Add Term' window filled all the fields then selected Rate Type 'IRF CMG Pricer' then clicked on save but that new term is not created with selected CMG User Rate Set and selected CMG Provider Value Set", "FAIL");		
	}
	
	
	// PPS CMG Provider Values VR
	public void PPS_CMG_ProviderValues()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS CMG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Provider Values", " Nothing is selected.", providerValuesTab, OpenPageValidation);//Method navigates to Provider Values tab
		
		noPeriodPPSAndCMGProviderValueSetSearch();
		
		addCMGProviderValuesSet();		
		addValues("CMG_PV","CMG_ProviderValues");//Method to add values to the Label
		modifyValue("CMG_PV");//Method for validating edit attribute value
		
	//	invalidProviderValueSearch();				
	//	searchCMGProviderValueSet();
		
		copyFromExistingCMGProviderValueSet();
		editPPSsetName(editIcon_PV,editWindow_PV,providerValuesTitlebar);
		periodEditWindow(editEffectiveWindow);
		editProviderValueStatus("PPS CMG");
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodPPSCMG();
		periodEditWindow(editEffectiveWindow);
		editPeriodDates();
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodFromExistingCMGPVSet();
		deleteFirstPeriod();
		oRateSheetLibrary.navigateToRateSheets();
		refresh_page();
		oRateSheetLibrary.searchRateSheet("Production", "AutomationBLR_DND");
		addCMGPricerRateTerm();
		logout();
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------	

	public By rateFactorsTab = By.xpath("//div[@tabs='tabs']//a[contains(.,'Rate Factors')]");
	
	By addRateFactorSetWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add Rate Factor Set']");
	
	By editIcon_RF = By.xpath("//a[@title='Edit Rate Factor Set']//i[@class='left fa fa-pencil']");
	
	By editRateFactorSetWindow = By.xpath("//div[@title='Edit Rate Factor Set']");
	
	By addRateFactorPeriodWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add Rate Factor Period']");
	
	
	// to add rate factor
	public void addRateFactor()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickAddButton("Rate Factors", "Add Rate Factor Set Window", addPPSSetButton, addRateFactorSetWindow);
		
		enter_text_value("Rate Factor Set Name", nameTextbox, "RateFactor_"+System.currentTimeMillis());
		oParameters.SetParameters("RateFactorSetName", get_field_value("Rate Factor Set Name", nameTextbox));
		
		facilityName("PPS", "Apollo srn facility");
		
		click_button("Save Butto", saveButton);
		waitFor(titleBarGD, "Rate Factors Title bar");
		
		if(get_field_value("Rate Factors Title bar", titleBarGD).equalsIgnoreCase(oParameters.GetParameters("RateFactorSetName")))
			oReport.AddStepResult("", "In 'Add Rate Factor Set' window entered name then clicked on save, Verified that new Rate Factor Set is created without any error", "PASS");
		else
			oReport.AddStepResult("", "In 'Add Rate Factor Set' window entered name then clicked on save but new Rate Factor Set is not created ", "FAIL");		
	}
	
	
	// To search and select Rate Factor
	public void searchRateFactor()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search for existing User Set", searchPPS, oParameters.GetParameters("ModifiedPPSSetValue"));
		fixed_wait_time(6);
		click_button("Clicking on searched User Rate Set", searchedResult);
		
		if(get_field_value("Rate Factors Title bar", titleBarGD).equalsIgnoreCase(oParameters.GetParameters("ModifiedPPSSetValue")))
			oReport.AddStepResult("", "Searched and selected existed  Rate Factors set, Verified that respective Rate Factors set is displayed", "PASS");
		else
			oReport.AddStepResult("", "Searched and selected existed Rate Factors set but respective Rate Factors set is not displayed", "FAIL");			
	}
	
	
	By periodDateRF = By.xpath("//div[@add-period='addPeriod']//a[@class='btn btn-light btn-default btn-sm']//span[1]");

	public By effectiveDateRF = By.xpath("//input[@id='rfPartialPeriodStartDate']");
	
	public By terminationDateRF = By.xpath("//input[@id='rfPartialPeriodStopDate']");
	
	By periodWarning = By.xpath("//span[contains(.,' Period will not be created until this form is saved. Please do not navigate away from this page.')]");
	
	
	//To add period for Rate Factor
	public void addRateFactorPeriod(int num)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Effective Date", effectiveDateRF, get_specified_date(num));
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", terminationDateRF, get_specified_date(num+1));
		performKeyBoardAction("ENTER");
		click_button("Save Button", saveEffectivePeriod);
		
		if(IsDisplayed("Period warning message", periodWarning))
			oReport.AddStepResult("Period", "In 'Add Rate Factor Period' window entered Effective and Termination dates then clicked on save,Verified that Rate Factor Period is added without any error", "PASS");
		else
			oReport.AddStepResult("Period", "In 'Add Rate Factor Period' window entered Effective and Termination dates then clicked on save but that Rate Factor Period is not added ", "FAIL");
	}
		
	
	// Method to enter all Common Factors values
	public void enterCommonFactorValues()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		for(int i=1;i<=4;i++)
		{
			By base = By.xpath("//form[@id='addRateFactorPeriod']/div[3]/div["+(i+1)+"]/div[2]//input");
			
			By outlier = By.xpath("//form[@id='addRateFactorPeriod']/div[3]/div["+(i+1)+"]/div[3]//input");
			
			enter_text_value("Base Value", base, "1");
			
			enter_text_value("Outlier Value", outlier, "1");
		
			for(int j=i;j<=i;j++)
			{
				By AgeStart = By.xpath("//form[@id='addRateFactorPeriod']//li[2]/div["+j+"]//input");
				
				enter_text_value("Age Start", AgeStart, "1");
				
				if(i < 4)
				{
					for(int k=j;k<=i;k++)
					{
						By threshold = By.xpath("//form[@id='addRateFactorPeriod']//div[4]/div["+k+"]/div[2]//input");
					
						enter_text_value("Low BMI Threshold", threshold, "1");
					}
				}	
				
				for(int m=j;m<=i;m++)
				{
					By MAPBaseAmount = By.xpath("//form[@id='addRateFactorPeriod']/fieldset//div["+m+"]/div[2]//input");
					
					enter_text_value("Predicted MAP Base Amount ", MAPBaseAmount, "1");
				}				
			}
		}
	}
	
	
	By ageMultiplierAddIcon  = By.xpath("//a[@title='Add']/i[@class='left fa fa-plus-square']");
	
	By ageMultiplierDeleteIcon = By.xpath("//form[@id='addRateFactorPeriod']//li[3]//a[@title='Delete']/i[@class='left fa fa-minus-square']");
	
	By deleteRowPopup = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Delete row?')]");
		
	
	// To add and delete Age Multiplier row
	public void addAgeMultiplierRow()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("AgeMultiplierRows", String.valueOf(get_table_row_count(ageMultiplierAddIcon)));
		click_button("Add Icon", ageMultiplierAddIcon);
		
		if(oParameters.GetParameters("AgeMultiplierRows").equalsIgnoreCase(String.valueOf(get_table_row_count(ageMultiplierAddIcon))))
			oReport.AddStepResult("", "Under Age Multiplier period form entered all the fields and clicked on add icon but new row is not displayed ", "FAIL");
		else
			oReport.AddStepResult("", "Under Age Multiplier period form entered all the fields and clicked on add icon, Verified that new row is displayed ", "PASS");				
	}
	
	
	// To delete age multiplier Row
	public void deleteAgeMultiplierRow()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("AgeMultiplierRowsBefore", String.valueOf(get_table_row_count(ageMultiplierAddIcon)));
		click_button("Delete Icon", ageMultiplierDeleteIcon);
		
		if(IsDisplayed("Delete Pop up", deleteRowPopup))
			oReport.AddStepResult("", "Clicked on Age Multiplier period row delete icon, Verified that Delete Row pop up is displayed", "PASS");
		else
			oReport.AddStepResult("", "Clicked on Age Multiplier period row delete icon but delete Row pop up is not displayed", "FAIL");
		
		click_button("Delete Button", popUpDeleteButton);
		
		if(oParameters.GetParameters("AgeMultiplierRowsBefore").equalsIgnoreCase(String.valueOf(get_table_row_count(ageMultiplierAddIcon))))
			oReport.AddStepResult("", "Age Multiplier period row is deleted but row is not deleted upon clicking on delete button", "FAIL");
		else
			oReport.AddStepResult("", "Age Multiplier period row is deleted ,Verified that row is deleted upon clicking on delete button", "PASS");
	}
		

	public By comorbidityCategorySetSB = By.xpath("//input[@id='ccMasterId']");
	
	public By labFeeScheduleSB = By.xpath("//input[@id='labFeeScheduleId']");
	
	public By labServiceQualificationGroupSB = By.xpath("//input[@id='labQualGroupId']");
	
	public By otherFeeScheduleSB = By.xpath("//input[@id='otherFeeScheduleId']");
	
	public By otherFeeScheduleQGSB = By.xpath("//input[@id='otherQualGroupId']");
	
	By firstComorbidityCategorySetSearchValue = By.xpath("//div[@model='rfPeriodForm.ccMaster.id']//ul/li[1]");
	
	By firstLabFeeScheduleSearchValue = By.xpath("//div[@model='rfPeriodForm.labFeeSchedule.id']//ul/li[1]");
	
	By firstLabServiceQGSearchValue = By.xpath("//div[@model='rfPeriodForm.labQualGroup.id']//ul/li[1]");
	
	By firstFeeScheduleSearchValue = By.xpath("//div[@model='rfPeriodForm.otherFeeSchedule.id']//ul/li[1]");
	
	By firstFeeScheduleQGSearchValue = By.xpath("//div[@model='rfPeriodForm.otherQualGroup.id']//ul/li[1]");
	
	public By rateFactorSaveButton = By.xpath("//button[@id='rfPeriodSave']");
	
	
	//Adding Rate Factor
	public void saveRateFactor(String labServiceQG)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Comorbidity Category Set Search box", comorbidityCategorySetSB);
		performKeyBoardAction("ENTER");
		click_button("Comorbidity Category Set first search value", firstComorbidityCategorySetSearchValue);
		click_button("Lab Fee Schedule Search box", labFeeScheduleSB);
		performKeyBoardAction("ENTER");
		fixed_wait_time(5);
		waitFor(firstLabFeeScheduleSearchValue, "Lab Fee Schedule Search value");
		click_button("Lab Fee Schedule Search value", firstLabFeeScheduleSearchValue);
		
		if(!(labServiceQG.isEmpty()))
		{
			click_button("Lab Service QG search box", labServiceQualificationGroupSB);
			enter_text_value("Lab Service QG search box", labServiceQualificationGroupSB, labServiceQG);
			fixed_wait_time(3);
			/*performKeyBoardAction("ENTER");						
			By searchResultValue = By.xpath("//div[@model='rfPeriodForm.labQualGroup.id']//ul/li[contains(.,'"+labServiceQG+"')]");			
			click_button("Lab Service QG Search Value", searchResultValue);*/
		}
		else
		{
			click_button("Lab Service QG search box", labServiceQualificationGroupSB);
			performKeyBoardAction("ENTER");
			click_button("Lab Service QG Search Value", firstLabServiceQGSearchValue);
		}
		click_button("Rate Factor Save", rateFactorSaveButton);
		
		if(IsDisplayed("Period warning message", periodWarning))
			oReport.AddStepResult("", "Filled all the mandatory fields then clicked on save but Rate Factor period not added ", "FAIL");
		else
			oReport.AddStepResult("", "Filled all the mandatory fields then clicked on save, Verified that Rate Factor period added successfully ", "PASS");
	}
	
	
	public By addProviderEntryButton = By.xpath("//span[text()='Add Provider Entry']");
	
	By addProviderEntryWindow = By.xpath("//div[@title='Add Provider Entry']");
	
	
	// to click on 'Add Provider Entry' button
	public void addProviderEntryWindow()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add Provider Entry Button", addProviderEntryButton);
		
		if(IsDisplayed("Add Provider Entry Window", addProviderEntryWindow))
			oReport.AddStepResult("Add Provider Entry Window", "Clicked on 'Add Provider Entry' button, Verified that 'Add Provider Entry' window is displayed", "PASS");
		else
			oReport.AddStepResult("Add Provider Entry Window", "Clicked on 'Add Provider Entry' button but 'Add Provider Entry' window is not displayed", "FAIL");
	}
	
	
	public By carrier = By.xpath("//div[@id='addProviderEntry']//input[@id='rfProviderCarrier']");
	
	public By locality = By.xpath("//div[@id='addProviderEntry']//input[@id='rfProviderLocality']");
	
	public By lowVolumeIndicator = By.xpath("//div[@id='addProviderEntry']//select[@id='rfProviderLowVol']");
	
	public By PPSCompositeBaseRate = By.xpath("//div[@id='addProviderEntry']//input[@id='rfProviderPPSComp']");
	
	public By trainingAddOn = By.xpath("//div[@id='addProviderEntry']//input[@id='rfProviderTraining']");
	
	
	// To fill all the details in 'Add Provider Entry' window details
	public void addProviderEntryDetails()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Carrier ", carrier, get_random_number(4));
		oParameters.SetParameters("CarrierValue", get_field_value("Carrier", carrier));
		enter_text_value("Locality ", locality, get_random_number(3));
		select_option("Low Volume Indicator", lowVolumeIndicator, "1");
		enter_text_value("PPS Composite Base Rate ", PPSCompositeBaseRate, get_random_number(2));
		click_button("Save Button", saveEffectivePeriod);
		
		By providerEntry = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height']/table//tr[contains(.,'"+oParameters.GetParameters("CarrierValue")+"')]");
		
		if(IsDisplayed("New Provider Value", providerEntry))
			oReport.AddStepResult("", "In 'Add Provider Entry' window Filled all the mandatory fields then click on save, Verified that new Provider Entry is added without any error ", "PASS");
		else
			oReport.AddStepResult("", "In 'Add Provider Entry' window Filled all the mandatory fields then click on save but that new Provider Entry is not added", "FAIL");
	}

	
	By firstProviderEntry = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height']/table/tbody/tr[1]");
	
	By providerEntries = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height']/table/tbody/tr");
	
	By providerEntryDeleteIcon = By.xpath("//a[@ng-click='deleteProviderEntry(entry)'][@style='display: inline;']/i[@class='left fa fa-minus-square']");
	
	By rateFactorDeleteIcon	= By.xpath("//a[@title='Delete Rate Factor Set']/i[@class='left fa fa-minus-square']");
	
	
	// To delete Provider entry
	public void deleteProviderEntry()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("ProviderEntriesCount", String.valueOf(get_table_row_count(providerEntries)));
		mouse_hover("Provider Entry", firstProviderEntry);
		click_button("Delete Button", providerEntryDeleteIcon);
		
		if(IsDisplayed("Delete Provider Entry popup", deleteProviderValuesSetPeriodWindow))
			oReport.AddStepResult("Delete Entry", "Hovered over Provider Entry and clicked on delete icon, Verified that Delete Provider Entry pop up is displayed ", "PASS");
		else
			oReport.AddStepResult("Delete Entry", "Hovered over Provider Entry and clicked on delete icon but Delete Provider Entry pop up is not displayed ", "FAIL");
	
		
		click_button("Popup Delete button", deletePopupwindowDelete);
		fixed_wait_time(4);
		
		if(oParameters.GetParameters("ProviderEntriesCount").equalsIgnoreCase(String.valueOf(get_table_row_count(providerEntries))))
			oReport.AddStepResult("Delete Entry", "Hovered over Provider Entry and clicked on delete icon but that Provider Entry is not deleted ", "FAIL");
		else
			oReport.AddStepResult("Delete Entry", "Hovered over Provider Entry and clicked on delete icon, Verified that Provider Entry is deleted ", "PASS");	
	}
	
	
	// to add term with created PPS rate factor
	public void addPPSRateTerm()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add term button", oRateSheetLibrary.addTerm);		
		enter_text_value("Add Term Name", oRateSheetLibrary.termName,"Dialysis_"+System.currentTimeMillis());
		oParameters.SetParameters("PPSTermName", get_field_value("PPS Term Name", oRateSheetLibrary.termName));		
		enter_text_value("Qualification Field", oRateSheetLibrary.qualificationGroupField, "0 - TRUE");
		fixed_wait_time(4);
		selectOption(oRateSheetLibrary.rateTypeSearchBox,"visibletext","Dialysis PPS Rate");			
		enter_text_value("Dailysis PPS Rate Factor", oRateSheetLibrary.dailysisPPSRateFactor, oParameters.GetParameters("ModifiedPPSSetValue"));
		fixed_wait_time(3);
		click_button("Term Save button", oRateSheetLibrary.addTermSaveButton);
			
		By addedNewTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+ oParameters.GetParameters("PPSTermName")+"')]");
	
		mouse_hover("Added CMG Term", addedNewTerm);

		if(IsDisplayed("Added Dailysis PPS Rate Term", addedNewTerm))
			oReport.AddStepResult("New Term is ", "In 'Add Term' window filled all the fields then selected Rate Type 'Dialysis PPS Rate' then clicked on save, Verified that new term is created with selected Dialysis PPS Rate factor", "PASS");
		else
			oReport.AddStepResult("New Term is ", "In 'Add Term' window filled all the fields then selected Rate Type 'Dialysis PPS Rate' then clicked on save but that new term is not created with selected Dialysis PPS Rate factor", "FAIL");			
	}
	
	
	
	// PPS Dialysis_ Rate Factors CRUD operation VR
	public void PPS_Dialysis_RateFactrors()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS Dialysis",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Rate Factors", " Nothing is selected.", rateFactorsTab, OpenPageValidation);//Method navigates to Provider Values tab
		
		addRateFactor();
		editPPSsetName(editIcon_RF,editRateFactorSetWindow,titleBarGD);// ModifiedPPSSetValue to delete RF set
		searchRateFactor();
		addPeriodWindow(addRateFactorPeriodWindow);
		addRateFactorPeriod(10);
		enterCommonFactorValues();
		addAgeMultiplierRow();
		deleteAgeMultiplierRow();
		saveRateFactor("");
		addPeriodWindow(addRateFactorPeriodWindow);
		addRateFactorPeriod(12);		
		enterCommonFactorValues();
		saveRateFactor("");		
		deleteFirstPeriod();		
		navigate_to("Provider Values", "Add Provider Entry button", providerValuesTab, addProviderEntryButton);		
		addProviderEntryWindow();
		addProviderEntryDetails();
		addProviderEntryWindow();
		addProviderEntryDetails();		
		deleteProviderEntry();		
		oRateSheetLibrary.navigateToRateSheets();
		oRateSheetLibrary.searchRateSheet("Production", "AutomationBLR_DND");
		addPPSRateTerm();		
		navigate_to("PPS Plugin", "Provider Values Tab", PPSplugin, providerValuesTab);//Method for navigating to PPS plugin		
		deletePPS("Search Rate Factors",oParameters.GetParameters("ModifiedPPSSetValue"),rateFactorDeleteIcon);		
		logout();		
	}	
	
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------	

	
	By comorbidityCategoriesTab = By.xpath("//div[@tabs='tabs']//a[contains(.,'Comorbidity Categories')]");
	
	By addComorbidityCategorySetWindow = By.xpath("//div[@title='Add Comorbidity Category Set']");
	
	public By ccStartDate = By.xpath("//input[@id='ccSetStartDate']");
	
	public By ccStopDate = By.xpath("//input[@id='ccSetStopDate']");
	
	By ccEditIcon = By.xpath("//a[@title='Edit Comorbidity Category Set']/i[@class='left fa fa-pencil']");
	
	By ccEditWindow = By.xpath("//div[@title='Edit Comorbidity Category Set']");
		
	
	// To add Comorbidity Category
	public void addComorbidityCategory(String categoryName)
	{		
		clickAddButton("Comorbidity Categories", "Add Comorbidity Category Set Window", addPPSSetButton, addComorbidityCategorySetWindow);
		
		enter_text_value("Comorbidity Category Set Name", nameTextbox, "Comorbidity_"+System.currentTimeMillis());
		oParameters.SetParameters(categoryName, get_field_value("Comorbidity Category Set Name", nameTextbox));
		
		facilityName("PPS", "Apollo srn facility");
		
		enter_text_value("Start Date", ccStartDate, get_specified_date(4));
		performKeyBoardAction("ENTER");
		enter_text_value("Start Date", ccStopDate, get_specified_date(6));
		performKeyBoardAction("ENTER");		
		click_button("Save Butto", saveButton);		
		waitFor(titleBarGD, "Comorbidity Category Title bar");
		
		if(get_field_value("Comorbidity Category Title bar", titleBarGD).equalsIgnoreCase(oParameters.GetParameters(categoryName)))
			oReport.AddStepResult("", "In 'Add Comorbidity Category Set' window entered name,date then clicked on save, Verified that new Comorbidity Category Set is created without any error", "PASS");
		else
			oReport.AddStepResult("", "In 'Add Comorbidity Category Set' window entered name,date then clicked on save but new Comorbidity Category Set is not created ", "FAIL");		
	}
	
	
	public By addCategoryButton = By.xpath("//span[text()='Add Category']");
	
	By addCategoryWindow = By.xpath("//div[@title='Add Category']");
		
	
	// To click on Add Category button
	public void addCategoryButton()
	{
		click_button("Add Category Button", addCategoryButton);
		
		if(IsDisplayed("Add Category Window", addCategoryWindow))
			oReport.AddStepResult("Add Category Window", "Clicked on 'Add Category' button, Verified that 'Add Category' window is displayed", "PASS");
		else
			oReport.AddStepResult("Add Category Window", "Clicked on 'Add Category' button but 'Add Category' window is not displayed", "FAIL");
	}
	
	
	By categoryDD = By.xpath("//select[@id='ccCategory']//option");
    
    public By base = By.xpath("//div[@form-id='ccEntryFormModel.formId']//input[@id='ccBaseValue']");
    
    public By outlier = By.xpath("//div[@form-id='ccEntryFormModel.formId']//input[@id='ccOutlierValue']");
    
    By bacterialPneumoniaTab = By.xpath("//a[text()='Bacterial Pneumonia']");
    
    public By Category = By.xpath("//select[@id='ccCategory']");
    
    public By categorySaveButton = By.xpath("//div[@id='addComorbidityCategoryEntry']//input[@id='button.saveId']");
    
    
    //To add category
    public void addCategory()
    {            
    	addCategoryButton();
        
        List<WebElement> categoryList= convertToWebElements(categoryDD);
        String value = "";
        
        for(int i=1; i<categoryList.size();i++)
        {
        	if(!(i == 1))
        	{
        		click_button("Add Category Button", addCategoryButton);              
        		
        		String categoryValues = get_field_value("Category DropDown", categoryDD);
                     
        		click_button("Category Field", Category);
        		
        		if(!categoryValues.contains(value))
        			oReport.AddStepResult("", "Clicked on Add Category icon, clicked on Select Category dropdown, Verified that previously created "+value+" Category name is not displayed in drop down", "PASS");
        		else
        			oReport.AddStepResult("", "Clicked on Add Category icon, clicked on Select Category dropdown but previously created "+value+" Category name is displayed in drop down ", "FAIL");
        	}
               
        	value = categoryList.get(1).getText();
        	
        	fixed_wait_time(2);
        	selectOption(Category, "visibletext", value);
        	
        	enter_text_value("Base Field", base, String.valueOf(i));
        	enter_text_value("Outlier Field", outlier, String.valueOf(i));
        	
        	fixed_wait_time(2);
        	click_button("Save Button", categorySaveButton);                      
        	
        	By categoryTab = By.xpath("//a[text()='"+value+"']");
               
        	if(IsDisplayed(value+" Tab", categoryTab))
        		oReport.AddStepResult("Category", "In 'Add Category' window filled all the fields then Selected Category as "+value+" clicked on save, Verified that "+value+" category tab is created ", "PASS");
        	else
        		oReport.AddStepResult("Category", "In 'Add Category' window filled all the fields then Selected Category as "+value+" clicked on save but "+value+" category tab is not created ", "FAIL");                              
        } 
    }

		
	public By addICDCodeButton = By.xpath("//span[text()='Add ICD Code']");
	
	public By addICDCodeLink = By.xpath("//a[contains(text(),'Add an ICD Code')]");
	
	By addICDCodeWindow = By.xpath("//div[@title='Add ICD Code']");
	
	
	// this method will click on 'Add ICD Code' link
	public void addICDCodeButton()
	{
		fixed_wait_time(3);
		
		if(IsDisplayed("Add ICD Code", addICDCodeLink))
			click_button("Add ICD Code", addICDCodeLink);
		else
			click_button("Add ICD Code", addICDCodeButton);
		
		if(IsDisplayed("Add ICD Code window", addICDCodeWindow))
			oReport.AddStepResult("Add ICD Code window", "Clicked on Add an ICD Code link, Verified that 'Add ICD Code' window is displayed without any error ", "PASS");
		else
			oReport.AddStepResult("Add ICD Code window", "Clicked on Add an ICD Code link but 'Add ICD Code' window is not displayed", "FAIL");
	}
	
	
	public By diagnosisCodeTextBox = By.xpath("//input[@id='icdDxCode']");
	
	By invalidPopup = By.xpath("//div[@class='message ng-scope ng-binding'][contains(.,'The diagnosis code is invalid')]");
	
	By invalidPopupClose = By.xpath("//div[@id='dialog_buttons']//input[@value='Close']");
	
	By invalidCodeMessage = By.xpath("//span[contains(.,' The diagnosis code does not exist in the master classification.')]");
	
	By invalidCodeMessageClose = By.xpath("//span[contains(.,' The diagnosis code does not exist in the master classification.')]/..//span[@icon='times-circle']");
	
	
	// method to enter ICD Code
	public void addICDCode(String ICDCode)
	{
		enter_text_value("Diagnosis code", diagnosisCodeTextBox, ICDCode);
		click_button("Save Button", saveEffectivePeriod);
		
/*		if(ICDCode.equalsIgnoreCase("Invalid123"))
		{
			if(IsDisplayed("Invalid Popup", invalidPopup))
				oReport.AddStepResult("Invalid Popup", "Click on Add ICD Code icon entered invalid code and clicked on save,Verified that invalid code pop up is displayed without any error", "PASS");
			else
				oReport.AddStepResult("Invalid Popup", "Click on Add ICD Code icon entered invalid code and clicked on save but that invalid code pop up is not displayed ", "FAIL");
						
			click_button("Invalid Popup Close", invalidPopupClose);						
		}*/
		
		if(ICDCode.equalsIgnoreCase("Invalid123"))
		{
			if(IsDisplayed("Invalid code message", invalidCodeMessage))
				oReport.AddStepResult("Invalid code message", "Click on Add ICD Code icon entered invalid code and clicked on save,Verified that invalid code error message is displayed ", "PASS");
			else
				oReport.AddStepResult("Invalid code message", "Click on Add ICD Code icon entered invalid code and clicked on save but invalid code error message is not displayed ", "FAIL");
						
			click_button("Invalid Popup Close", invalidCodeMessageClose);									
		}
		else
		{
			By addedICDCode = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tr[contains(.,'"+ICDCode+"')]");
			
			if(IsDisplayed("ICD Code", addedICDCode))
				oReport.AddStepResult("", "In 'Add ICD Code'window entered Diagnosis Code then clicked on save, Verified that ICD code is added under respective Category", "PASS");
			else
				oReport.AddStepResult("", "In 'Add ICD Code'window entered Diagnosis Code then clicked on save but that ICD code is not added under respective Category", "FAIL");			
		}	
	}
	
	
	// method to add category
	public void categoryadd(String value)
	{
        selectOption(Category, "visibletext", value);
        
        enter_text_value("Base Field", base, "3");
        enter_text_value("Base Field", outlier, "3");
        
        fixed_wait_time(2);
        click_button("Save Button", categorySaveButton); 
        
        By categoryTab = By.xpath("//a[text()='"+value+"']");
        
        if(IsDisplayed(value+" Tab", categoryTab))
     	   oReport.AddStepResult("Category", "In 'Add Category' window filled all the fields then Selected Category as "+value+" clicked on save, Verified that "+value+" category tab is created ", "PASS");
        else
     	   oReport.AddStepResult("Category", "In 'Add Category' window filled all the fields then Selected Category as "+value+" clicked on save but "+value+" category tab is not created ", "FAIL");
	}
	
	
	By ICDCodeDeleteIcon = By.xpath("//a[@ng-click='deleteICDCode(code)'][@style='display: inline;']/i[@class='left fa fa-minus-square']");
	
	By ICDCodes = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tbody/tr");
	
	By firstICDCode = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tbody/tr[1]");
	
	By deleteICDPopup = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Delete')]");
	
	
	// To delete ICD Code
	public void deleteICDCode()
	{
		oParameters.SetParameters("ICDCodes", String.valueOf(get_table_row_count(ICDCodes)));
		mouse_hover("ICD Code", firstICDCode);
		click_button("ICD Code Delete Icon", ICDCodeDeleteIcon);
		
		if(IsDisplayed("Delete ICD Code pop up", deleteICDPopup))
			oReport.AddStepResult("Delete ICD Code pop up", "Hovered over ICD code and clicked on delete icon,Verified that Delete ICD Code pop up is displayed ", "PASS");
		else
			oReport.AddStepResult("Delete ICD Code pop up", "Hovered over ICD code and clicked on delete icon but Delete ICD Code pop up is not displayed ", "FAIL");
		
		click_button("Pop up Delete", deletePopupwindowDelete);
		waitFor(firstICDCode, "ICD Codes");
		
		if(oParameters.GetParameters("ICDCodes").equals(String.valueOf(get_table_row_count(ICDCodes))))
			oReport.AddStepResult("", "Hovered over ICD code and clicked on delete icon but ICD Code is not deleted ", "FAIL");
		else
			oReport.AddStepResult("", "Hovered over ICD code and clicked on delete icon, Verified that ICD Code is deleted ", "PASS");		
	}
	
	
	By deleteCategoryIcon = By.xpath("//span[@class='lnk-btn-txt ng-binding hidden-xs'][text()='Delete Category']");
	
	By editCategoryPeriodWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Edit Comorbidity Category Period']");
	
	
	// To delete Category
	public void deleteCategory(String tabName)
	{
		tabNavigate(tabName);	
		click_button("Category Delete Icon", deleteCategoryIcon);
		
		if(IsDisplayed("Delete Category Popup", deleteICDPopup))
			oReport.AddStepResult("Delete Category Popup", "Clicked on Delete Category icon, Verified that Delete Category pop up is displayed ", "PASS");
		else
			oReport.AddStepResult("Delete Category Popup", "Clicked on Delete Category icon but Delete Category pop up is not displayed ", "FAIL");
				
		click_button("Pop up Delete", deletePopupwindowDelete);
		
		By tab = By.xpath("//div[@tabs='tabs']//a[contains(.,'"+tabName+"')]");
		
		if(IsDisplayed("Category Tab", tab))
			oReport.AddStepResult("", "Clicked on Delete Category icon but that category is not deleted", "FAIL");
		else
			oReport.AddStepResult("", "Clicked on Delete Category icon, Verified that category is deleted", "PASS");		
	}
	
	
	public By ccPeriodStartDate = By.xpath("//input[@id='ccPeriodStartDate']");
	
	public By ccPeriodStopDate = By.xpath("//input[@id='ccPeriodStopDate']");
	
	By addCategoryPeriodWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add Comorbidity Category Period']");
	
	
	//To edit Comorbidity Category Period
	public void editCategoryDate()
	{
		enter_text_value("Category Start date", ccPeriodStartDate, get_specified_date(7));
		performKeyBoardAction("ENTER");
		enter_text_value("Category Stop date", ccPeriodStopDate, get_specified_date(8));
		performKeyBoardAction("ENTER");
		click_button("Edit window save", editWindowSaveButton);		
		fixed_wait_time(5);
		oParameters.SetParameters("CategoryPeriodDate", get_field_value("period drop down", periodDropdown).substring(0, 10));

		if(get_specified_date(7).equals(oParameters.GetParameters("CategoryPeriodDate")))
			oReport.AddStepResult("PPS Period", "In 'Edit Comorbidity Category Period' window modified Effective and Termination dates, Verified that Effective and Termination date is modified without any error", "PASS");
		else
			oReport.AddStepResult("PPS Period", "In 'Edit Comorbidity Category Period' window modified Effective and Termination dates but that Effective and Termination date is not modified ", "FAIL");		
	}
	
	
	By addCategoryLink = By.xpath("//a[@class='bold ng-scope ng-binding'][contains(.,'Add a Category')]");
	
	By periodDateCC = By.xpath("//div[@add-period='addPeriod']//a[@class='btn btn-light btn-default btn-sm']//span[1]");
	
	By ComorbidityCategoriesDeleteIcon = By.xpath("//div[@class='document-action-bar']//a[@title='Delete Comorbidity Category Set']/i[@class='left fa fa-minus-square']");
	
	
	// To add category period 
	public void addCategoryPeriod()
	{
		enter_text_value("Category Start date", ccPeriodStartDate, get_specified_date(9));
		performKeyBoardAction("ENTER");
		enter_text_value("Category Stop date", ccPeriodStopDate, get_specified_date(10));
		performKeyBoardAction("ENTER");
		click_button("Edit window save", editWindowSaveButton);		
		oParameters.SetParameters("CategoryPeriodDate", get_field_value("period drop down", periodDateCC).substring(0, 10));

		if(get_specified_date(9).equals(oParameters.GetParameters("CategoryPeriodDate"))&& IsDisplayed("Add a Category Link", addCategoryLink))
			oReport.AddStepResult("PPS Period", "In 'Add Comorbidity Category Period' window entered Effective and Termination dates then clicked on save, Verified that new period is added and page to add Category is displayed", "PASS");
		else
			oReport.AddStepResult("PPS Period", "In 'Add Comorbidity Category Period' window entered Effective and Termination dates then clicked on save but new period is not added ", "FAIL");			
	}
	
	
	// PPS Dialysis Comorbidity Categories CRUD operation VR
	public void PPS_Dialysis_ComorbidityCategories()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS Dialysis",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Comorbidity Categories", " Nothing is selected.", comorbidityCategoriesTab, OpenPageValidation);//Method navigates to Provider Values tab
		
		addComorbidityCategory("ComorbidityCategorySetName1");
		addComorbidityCategory("ComorbidityCategorySetName2");		
		searchPPS("Search Category Sets", oParameters.GetParameters("ComorbidityCategorySetName1"), titleBarGD, searchedResult);
		editPPSsetName(ccEditIcon,ccEditWindow,titleBarGD);
		addCategory();				
		
		tabNavigate("Bacterial Pneumonia");				
		addICDCodeButton();
		addICDCode("A32.89");
		
		addICDCodeButton();
		addICDCode("Invalid123");
		addICDCode("A32.82");
		
		tabNavigate("Gastrointestinal Tract Bleeding");
		addICDCodeButton();
		addICDCode("A32.81");
		
		tabNavigate("Hereditary Hemolytic/Sickle Cell Anemias");
		addICDCodeButton();
		addICDCode("A27.89");
		
		tabNavigate("Monoclonial Gammopathy");
		addICDCodeButton();
		addICDCode("A27.81");
		
		tabNavigate("Myelodysplastic Syndrome");
		addICDCodeButton();
		addICDCode("A18.89");
		
		tabNavigate("Pericarditis");
		addICDCodeButton();
		addICDCode("A18.84");
		
		tabNavigate("Other");
		addICDCodeButton();
		addICDCode("A18.53");
		
		addICDCodeButton();
		addICDCode("A18.32"); 
		
		deleteICDCode();
		deleteCategory("Other");
		
		periodEditWindow(editCategoryPeriodWindow);
		editCategoryDate();
		
		addPeriodWindow(addCategoryPeriodWindow);
		addCategoryPeriod();
		
		addCategoryButton();
		categoryadd("Pericarditis");
		
		addICDCodeButton();
		addICDCode("A27.89");
		
		deleteFirstPeriod();		
		deletePPS("Search Category Sets",oParameters.GetParameters("ComorbidityCategorySetName2"),ComorbidityCategoriesDeleteIcon);
		deletePPS("Search Category Sets",oParameters.GetParameters("ModifiedPPSSetValue"),ComorbidityCategoriesDeleteIcon);
		logout();		
	}


//--------------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	

	public By addRUGUserRateSetWindow = By.xpath("//div[@title='Add RUG User Rate Set']");

	public By userRateSetName = By.xpath("//input[@id='userRateSetName']");
	
	public By userRateSetEffectiveDate = By.xpath("//input[@id='startDateCodeSet']");
	
	public By userRateSetTerminationDate = By.xpath("//input[@id='stopDateCodeSet']");
	
	public By userRateStatusDD = By.xpath("//select[@id='statusCode']");
	
	public By copyFromExistingUserRateSetCheckbox  = By.xpath("//label[contains(.,'Copy Details from Existing User Rate Set')]//input[@id='copyClosed']");
	
	
	// to click on Add User Rate Set
	public void addUserRateSetWindow()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add User Rate Set", addPPSSetButton);
		
		if(IsDisplayed("Add RUG User Rate Set Window", addRUGUserRateSetWindow))
			oReport.AddStepResult("", "Clicked on 'Add User Rate Set' icon, Verified that Add RUG User Rate window is displayed ", "PASS");
		else
			oReport.AddStepResult("", "Clicked on 'Add User Rate Set' icon but 'Add RUG User Rate' window is displayed ", "FAIL");
	}
	
	
	public By selectCodeSetSB = By.xpath("//input[@id='userRateSetSearch']");
	
	public By chooseFileRUG = By.xpath("//input[@id='userRateEntryIframe'][@name='userRateEntry']");
	
	
	// to fill details in Add User Rate Set window
	public void addUserRateSetDetails(String copyCheckBox,String chooseFile,String PPSType,By checkBox,String frameID,String autoIT)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("User Rate Set Name", userRateSetName, PPSType+System.currentTimeMillis()); //PPSType
		oParameters.SetParameters(PPSType+"UserRateSetName", get_field_value("User Rate Set Name", userRateSetName));
		
		facilityName("PPS", "Apollo srn facility");
		
		enter_text_value("Effective Date", userRateSetEffectiveDate, get_specified_date(0));
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", userRateSetTerminationDate, get_specified_date(2));
		performKeyBoardAction("ENTER");
		selectOption(userRateStatusDD, "visibletext", "Inactive");
		
		if(copyCheckBox.equalsIgnoreCase("CopyFrom"))
		{	
			click_button("Copy Details from Existing User Rate Set", checkBox);// check box Xpath
			enter_text_value("Select Code Set Search box", selectCodeSetSB, "Automation_DND");
			fixed_wait_time(5);
		}	
		
		if(chooseFile.equalsIgnoreCase("Import"))
		{	
			driver.switchTo().frame(frameID);//Frame ID
			click_button("Upload File Button", chooseFileRUG);
			fixed_wait_time(4);

			driver.switchTo().defaultContent();

			try 
			{
				Runtime.getRuntime().exec(autoIT); // AutoIT
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
	By userRateSetTitleBar = By.xpath("//div[@class='pad-l-10 large-header ng-binding']"); 
	
	By RUGCodeValue = By.xpath("//table//tr[@ng-click='editRugUserRateEntry(entry)']/td[text()='123']");
	
	By RUGCodeValue1 = By.xpath("//table//tr[@ng-click='editRugUserRateEntry(entry)']/td[text()='155']");
	
	By editIconRUG = By.xpath("//a[@title='Edit User Rate Set']/i[@class='left fa fa-pencil']");
	
	By editWindowRUG = By.xpath("//div[@title='Edit RUG User Rate Set']");
	
	
	// to save and validate RUG user rate set
	public void saveButtonRUG(String copyCheckBox,String chooseFile)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Save Button", saveButton);	
		waitFor(userRateSetTitleBar, "User Rate Set Title Bar");
		
		if(copyCheckBox.equalsIgnoreCase("CopyFrom"))
		{
			if(IsDisplayed("RUG Code Value", RUGCodeValue))
				oReport.AddStepResult("", "In 'Add RUG User Rate Set' window filled all the mandatory fields then checked copy details from existing User Rate Set and selected previously created User Rate Set then clicked on save, Verified that new User Rate set is created with details copied from searched User Rate Set", "PASS");
			else
				oReport.AddStepResult("", "In 'Add RUG User Rate Set' window filled all the mandatory fields then checked copy details from existing User Rate Set and selected previously created User Rate Set then clicked on save but that new User Rate set is not created with details copied from searched User Rate Set", "FAIL");
		}
		else if(chooseFile.equalsIgnoreCase("Import"))
		{
			if(IsDisplayed("RUG Code Value", RUGCodeValue1))
				oReport.AddStepResult("", "In Add RUG User Rate Set' window filled all the mandatory fields, imported the RUG file and clicked on save, Verified that new User Rate Set is created with data present in the import file", "PASS");
			else
				oReport.AddStepResult("", "In Add RUG User Rate Set' window filled all the mandatory fields, imported the RUG file and clicked on save but that new User Rate Set is not created with data present in the import file", "FAIL");			
		}
		else
		{
			if(oParameters.GetParameters("RUG_"+"UserRateSetName").equalsIgnoreCase(get_field_value("userRateSetTitleBar", userRateSetTitleBar)))
				oReport.AddStepResult("", "In 'Add RUG User Rate Set' window filled all the mandatory fields then clicked on save, Verified that new User Rate set is created ", "PASS");
			else
				oReport.AddStepResult("", "In 'Add RUG User Rate Set' window filled all the mandatory fields then clicked on save but that new User Rate set is not created ", "FAIL");
		}		
	}	
	
	
	public By userRateEntryAddButton = By.xpath("//a[@title='Add User Rate Set Entry']/i[@class='left fa fa-plus-square']");

	
	// to click on 'Add User Rate Set Entry' button
	public void addUserRateSetEntryWindow()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("User Rate Entry Add Button", userRateEntryAddButton);
		
		if(IsDisplayed("Add User Rate Set Entry Window", addUserRateSetEntryWindow))
			oReport.AddStepResult("", "Clicked on 'Add a User Rate Set Entry' button, Verified that 'Add User Rate Set Entry' window is displayed ", "PASS");
		else
			oReport.AddStepResult("", "Clicked on 'Add a User Rate Set Entry' button but 'Add User Rate Set Entry' window is not displayed ", "FAIL");
	}
	
	
	public By RUGCode = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='rugCode']");
	
	public By RUGWeight = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='weight']");
	
	
	// to enter User Rate Set Entry details
	public void userRateSetEntryDetails(String code,String weight)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("RUG Code", RUGCode, code);
		enter_text_value("Weight", RUGWeight, weight);
		
		click_button("Save Button", saveButton);		
		
		By addedUserRateSet = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tr[contains(.,'"+code+"')]");
		
		if(IsDisplayed("User Rate Set Entry", addedUserRateSet))
			oReport.AddStepResult("", "In 'Add User Rate Set Entry' window filled the mandatory fields then clicked on save, Verified that new rate set entry has been added without any error", "PASS");
		else
			oReport.AddStepResult("", "In 'Add User Rate Set Entry' window filled the mandatory fields then clicked on save but new rate set entry not added ", "FAIL");
	}
	
	
	// to click on User Rate set entry 
	public void editUserRateSetEntryWindow(String code)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By addedUserRateSet = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tr[contains(.,'"+code+"')]");
		By userRateSetEditWindow = By.xpath("//div[@class='medium-header truncate ng-binding'][@title='"+code+"']");
		
		mouse_hover("User Rate Set Entry", addedUserRateSet);
		click_button("User Rate Set Entry", addedUserRateSet);
		
		if(IsDisplayed("User Rate Set Edit Window", userRateSetEditWindow))
			oReport.AddStepResult("", "Clicked on user rate set, Verified that Edit User Rate Set window is displayed right side of the screen", "PASS");
		else
			oReport.AddStepResult("", "Clicked on user rate set but edit User Rate Set window is not displayed ", "FAIL");		
	}
	
	
	By payerPaymentRate = By.xpath("(//form[@name='rugUserRateSetEntryForm']//input[@id='pyrPmtRate1'])[1]");
	
	By userRateSetEditWindowSave = By.xpath("//div[@id='editEntryPanel']//button[@value='Save']");
		
	
	// to edit user rate set entries
	public void editUserRateSet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Payer Payment Rate 1", payerPaymentRate, "832");
		
		By modifiedUserRateSet = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tr/td[contains(text(),'832')]");
		
		click_button("Save Button", userRateSetEditWindowSave);		
		
		if(IsDisplayed("Modified User Rate Set", modifiedUserRateSet))
			oReport.AddStepResult("", "Clicked on user rate set then modified the values clicked on save, Verified that Rate Set is modified and Payer Payment Rate is displayed", "PASS");
		else
			oReport.AddStepResult("", "Clicked on user rate set then modified the values clicked on save but that Rate Set is not modified ", "FAIL");				
	}
	
	
	By userRateSetEditWindowCancel = By.xpath("//div[@id='editEntryPanel']//button[@value='Cancel']");
	
	By unsavedWindowDelete = By.xpath("//div[@class='form-button-panel']//input[@class='btn btn-danger discard-red']");
	
	
	// user rate set edit window cancel scenario
	public void userRateSetCancelScenario(String code)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By userRateSetEditWindow = By.xpath("//div[@class='medium-header truncate ng-binding'][@title='"+code+"']");
		
		enter_text_value("Payer Payment Rate 1", payerPaymentRate, "544");
		
		click_button("Cancel Button", userRateSetEditWindowCancel);
		
		if(IsDisplayed("Pop Up Window", popUpWindow))
			oReport.AddStepResult("", "In User Rate Set edit window made some changes clicked on cancel, Verified that 'You have unsaved changes' pop up is dispalyed ", "PASS");
		else
			oReport.AddStepResult("", "In User Rate Set edit window made some changes clicked on cancel but 'You have unsaved changes' pop up is not dispalyed ", "FAIL");
		
		click_button("Discard Button",unsavedWindowDelete);
		
		if(IsDisplayed("User Rate Set edit window", userRateSetEditWindow))
			oReport.AddStepResult("", "In unsaved changes popup window clicked on cancel but User Rate Set edit window is not closed ", "FAIL");			
		else
			oReport.AddStepResult("", "In unsaved changes popup window clicked on cancel, Verified that User Rate Set edit window closed ", "PASS");
	}
	
	
	By importRUG = By.xpath("//div[@id='ppView']//a[@title='Import']/i[@class='left fa fa-cloud-upload']");
	
	By importRUGWindow = By.xpath("//div[@title='Import RUG User Rate Set']");
	
	By downloadSampleRUG = By.xpath("//form[@id='imporRUGtUserRateForm']//a[text()='Download a sample .XLSX']");
	
	By importChooseFile = By.xpath("//input[@id='RUGUserRateEntryIframe'][@name='userRateEntry']");
	
	By activeRadioRUG = By.xpath("//form[@id='addRugUserRatePeriod']//input[@value='Actv']");
	
	By importRUGSave = By.xpath("//div[@id='fullFooter']//input[@value='Save'][@id='importRUGUserRateSetId']");
	
	
	// to import sample RUG User Rate Set
	public void importUserRateSet(String ppsType,By importWindow,By downloadLink,By chooseFile,String autoIT,By importSave,String excelName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Import Button", importRUG);
		
		if(IsDisplayed("Import "+ppsType+" User Rate Set window", importWindow)) // importWindow
		{
			click_button("Download a sample .XLSX", downloadLink); // downloadLink
			fixed_wait_time(10);
			
			click_button("Choose File", chooseFile); // ChooseFile
			fixed_wait_time(4);
			
			try 
			{
				Runtime.getRuntime().exec(autoIT); // autoIT
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			click_button("Save Import File", importSave);	// Save button
			fixed_wait_time(3);
			
			By userRateSetEntry = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tr[contains(.,'123')]");
			
			if(IsDisplayed("Imported User Rate Set date", userRateSetEntry))
				oReport.AddStepResult("", "Clicked on import then download a sample file and choosen the same file and clicked on save, Verified that file is imported without any error ", "PASS");
			else
				oReport.AddStepResult("", "Clicked on import then download a sample file and choosen the same file and clicked on save but that file is not imported ", "FAIL");
			
			deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+excelName);
		}
		else
			oReport.AddStepResult("", "Clicked on 'Import' button but 'Import RUG User Rate Set' window is not displayed", "FAIL");
	}
	
	
	public By selectUserRateSetSBRUG = By.xpath("//input[@id='rugSetSearchPeriod']");
	
	public By selectUserRateSetSBCMG = By.xpath("//input[@id='cmgSetSearchPeriod']");
	
	public By effectiveDateRUG = By.xpath("//input[@id='effectiveDateRugPeriod']");
	
	public By terminationDateRUG = By.xpath("//input[@id='terminationDateRugPeriod']");	
	
	public By userRateSetPeriodDD = By.xpath("//div[@model='copyPeriod.periodId']//select[@id='copyPeriodSelect']");
	
	
	// adding period from existing RUG user rate set
	public void addPeriodFromExistingRateSet(By effectiveDate,By terminationDate,By copySB,By codeValue)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Effective Date", effectiveDate, get_specified_date(32));//4
		performKeyBoardAction("ENTER");
		enter_text_value("Termination Date", terminationDate, get_specified_date(34));//6
		performKeyBoardAction("ENTER");		
		click_button("Copy Details from Existing User Rate Set", copyFromExistingUserRateSetCheckbox);
		enter_text_value("Select Code Set Search box", copySB, "Automation_DND");
		fixed_wait_time(5);
		select_option("Select Period to Copy dropdown", userRateSetPeriodDD, "0");
		click_button("Save button", editWindowSaveButton);
		fixed_wait_time(5);
		waitFor(codeValue, "Imported Data");
		
		if(IsDisplayed("RUG Code Value", codeValue))
			oReport.AddStepResult("", "In 'Add Effective Period' window filled all the mandatory fields then checked copy details from existing User Rate Set and selected previously created User Rate Set then clicked on save, Verified that new User Rate set is created with details copied from searched User Rate Set", "PASS");
		else
			oReport.AddStepResult("", "In 'Add Effective Period' window filled all the mandatory fields then checked copy details from existing User Rate Set and selected previously created User Rate Set then clicked on save but that new User Rate set is not created with details copied from searched User Rate Set", "FAIL");
	}
	
	
	By CMGProviderValuesFirstSet = By.xpath("//input[@id='cmgProviderValuesMaster']/..//ul/li[1]");
	
	
	// to add term with RUG rate type
	public void addPPSUserRateSetTypeTerm(String ppsType,String termRateType)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oRateSheetLibrary.clickAddTerm("Automation_Section");
		enter_text_value("Add Term Name", oRateSheetLibrary.termName,ppsType+System.currentTimeMillis()); // ppsType
		oParameters.SetParameters("PPS_TermName", get_field_value("PPS Term Name", oRateSheetLibrary.termName));		
		enter_text_value("Qualification Field", oRateSheetLibrary.qualificationGroupField, "0 - TRUE");
		fixed_wait_time(4);
		selectOption(oRateSheetLibrary.rateTypeSearchBox,"visibletext",termRateType);	 // Term rate type
		
		if(termRateType.equalsIgnoreCase("IRF CMG Pricer"))
		{
			if(IsDisplayed("CMG User Rate Set", oRateSheetLibrary.cmgUserRateSet) && IsDisplayed("CMG Provider Values Set", oRateSheetLibrary.cmgProviderValuesSet))
			{
				oReport.AddStepResult("CMG User Rate Set and Provider Values Set", "In 'Add Term' window filled all the mandatory fields selected 'IRF CMG Pricer' as Rate Type, verified that CMG User Rate Set and CMG Provider Values Set search box displayed", "PASS");
				
				enter_text_value("CMG User Rate Set", oRateSheetLibrary.cmgUserRateSet, oParameters.GetParameters("ModifiedPPSSetValue"));
				click_button("CMG Provider Values Set", oRateSheetLibrary.cmgProviderValuesSet);
				performKeyBoardAction("ENTER");
				fixed_wait_time(6);
				click_button("CMG Provider Values Set", CMGProviderValuesFirstSet);
				
			}
			else
				oReport.AddStepResult("CMG User Rate Set and Provider Values Set", "In 'Add Term' window filled all the mandatory fields selected 'IRF CMG Pricer' as Rate Type but CMG User Rate Set and CMG Provider Values Set search box is not displayed", "FAIL");		
		}
		else			
			enter_text_value("RUG User Rate Set", oRateSheetLibrary.rugUserRateSet, oParameters.GetParameters("ModifiedPPSSetValue"));
		
		fixed_wait_time(3);
		click_button("Term Save button", oRateSheetLibrary.addTermSaveButton);
			
		By addedNewTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+ oParameters.GetParameters("PPS_TermName")+"')]");
	
		mouse_hover("Added RUG Term", addedNewTerm);

		if(IsDisplayed("Added RUG User Rate Term", addedNewTerm))
			oReport.AddStepResult("New Term is ", "In 'Add Term' window filled all the fields then selected Rate Type 'RUG User' then clicked on save, Verified that new term is created with selected RUG User rate ", "PASS");
		else
			oReport.AddStepResult("New Term is ", "In 'Add Term' window filled all the fields then selected Rate Type 'RUG User' then clicked on save but that new term is not created with selected RUG User rate ", "FAIL");				
	}
	
	
	By rateEntryDeleteIconRUG = By.xpath("//a[@ng-click='deleteRugUserRateEntry(entry)'][@style='display: inline;']/i[@class='left fa fa-minus-square']");
	
	By rateEntryCount = By.xpath("//div[@class='pad-r-5 pull-right xl-header ng-binding']");
	
	
	//To delete RUG User Rate Set Entry
	public void deleteUserRateSet(By deleteIcon)
	{
		oParameters.SetParameters("UserRateSets", get_field_value("No of User Rate Set Entries", rateEntryCount).replace("Codes: ", "")); //String.valueOf(get_table_row_count(ICDCodes)));
		mouse_hover("User Rate Set", firstICDCode);
		click_button("User Rate Set Delete Icon", deleteIcon);
		
		if(IsDisplayed("Delete Rate Entry pop up", deleteICDPopup))
			oReport.AddStepResult("", "Hovered over User Rate Set and clicked on delete icon,Verified that Delete Rate Entry pop up is displayed ", "PASS");
		else
			oReport.AddStepResult("", "Hovered over User Rate Set and clicked on delete icon but Delete Rate Entry pop up is not displayed ", "FAIL");
		
		click_button("Pop up Delete", deletePopupwindowDelete);
		waitFor(firstICDCode, "User Rate Set Entries");
		
		if(oParameters.GetParameters("UserRateSets").equals(get_field_value("No of User Rate Set Entries", rateEntryCount).replace("Codes: ", "")))
			oReport.AddStepResult("", "Hovered over Rate Entry and clicked on delete icon but User Rate Set is not deleted ", "FAIL");
		else
			oReport.AddStepResult("", "Hovered over Rate Entry and clicked on delete icon, Verified that User Rate Set is deleted ", "PASS");			
	}

		
	By RUGColumn = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tbody/tr[1]/td[1]");
	
	By RUGCodeEle = By.xpath("//li[@ng-click='selectFilterField(f)'][contains(.,'RUG Code')]");
	
	
	// To filter reports
	public void filterReportsRUG()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("RUGColumnValue", get_field_value("RUG Code", RUGColumn));	
		fixed_wait_time(4);
		click_button("Add Filter", addFilter);
		
		if(IsDisplayed("Choose option field", optionFieldFilters))
		{
			click_button("Optional field filter drop down", optionFieldFilters);
			click_button("RUG Code Element", RUGCodeEle);
			enter_text_value("Enter Filter Text", textToFilter, oParameters.GetParameters("RUGColumnValue"));
			performKeyBoardAction("ENTER");
			fixed_wait_time(5);
			
			if(oParameters.GetParameters("RUGColumnValue").equalsIgnoreCase(get_field_value("RUG Code", RUGColumn)))
				oReport.AddStepResult("", "Filter is applied and respective RUG Code record is displayed", "PASS");
			else
				oReport.AddStepResult("", "Filter is not applied or Filtered RUG Code records are not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("", "Clicked on 'Add Filter' but filter dropdown is not displayed", "PASS");	
	}
	
	
	By exportRUG = By.xpath("//div[@id='ppView']//a[@title='Export']/i[@class='left fa fa-cloud-download']");
	
	By periodDDRUG = By.xpath("//div[@add-period='addRugUserRatePeriod']//a[@class='btn btn-light btn-default btn-sm']//span[1]");
	
	
	//to export User Rates
	public void exportUserRates()
	{
		oParameters.SetParameters("UserRateSetName", get_field_value("userRateSetTitleBar", userRateSetTitleBar));
		
		click_button("Export RUG", exportRUG);
		fixed_wait_time(5);
			
		if(isFileDownloaded(oParameters.GetParameters("UserRateSetName")+".xlsx"))
			oReport.AddStepResult("", "Clicked on Export button, Verified that User Rates are exported into excel", "PASS");
		else
			oReport.AddStepResult("", "Clicked on Export button but User Rates are not exported into excel", "FAIL");
		
		int columnCount = oExcelData.getColumnCount(0, "C:\\CCM\\Downloads\\"+oParameters.GetParameters("UserRateSetName")+".xlsx", 1);

		String[] columnDataArray = new String[columnCount];

		for (int i = 0; i < columnCount; i++) 
		{
			String cellData = ExcelData.getCellData(0, i, 3, "C:\\CCM\\Downloads\\"+oParameters.GetParameters("UserRateSetName")+".xlsx");
			columnDataArray[i] = cellData;
		}

		String columnDataString1 = String.join(",", columnDataArray).replace(",", " ");

		if(columnDataString1.contains(oParameters.GetParameters("155")))
			oReport.AddStepResult("Downloaded Excel File and Columns", "Downloaded Excel File and Columns Displayed","PASS");
		else
			oReport.AddStepResult("Downloaded Excel File","Clicked on download but that Excel file not Downloaded and that Schedule Columns not Displayed","FAIL");
	}
	
	//PPS RUG User Rates CRUD operation VR
	public void PPS_RUG_UserRates()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS RUG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		searchPPS("Search User Rates", "Automation_DND", userRateSetTitleBar, searchedResult);
		
		addUserRateSetWindow();
		addUserRateSetDetails("","","RUG_",copyFromExistingUserRateSetCheckbox,"","");
		saveButtonRUG("","");

		addUserRateSetWindow();
		addUserRateSetDetails("","Import","RUG_",copyFromExistingUserRateSetCheckbox,"importRugUserRateIframe","C:\\CCM\\AutoIt\\CCM_PPS_RUG_ UserRates_SampleRUGTable.exe");
		saveButtonRUG("","Import");	
					
		addUserRateSetWindow();
		addUserRateSetDetails("CopyFrom","","RUG_",copyFromExistingUserRateSetCheckbox,"","");
		saveButtonRUG("CopyFrom","");
		
		deleteUserRateSet(rateEntryDeleteIconRUG);		
		exportUserRates();		
		editPPSsetName(editIconRUG,editWindowRUG,userRateSetTitleBar);	
		
		addUserRateSetEntryWindow();
		userRateSetEntryDetails("C1","1.5");		
		addUserRateSetEntryWindow();
		userRateSetEntryDetails("CA1","0.5");		
		addUserRateSetEntryWindow();
		userRateSetEntryDetails("CA2","0.6");		
		editUserRateSetEntryWindow("CA2");		
		editUserRateSet();		
		editUserRateSetEntryWindow("CA1");
		userRateSetCancelScenario("CA1");
		
		importUserRateSet("RUG",importRUGWindow,downloadSampleRUG,importChooseFile,"C:\\CCM\\AutoIt\\CCM_PPS_RUG_ UserRates_SampleRUGTable_Import.exe",importRUGSave,"sampleRUGTable.xlsx");
		
		periodEditWindow(editEffectiveWindow);
		editPPSSetStatus(activeRadioRUG,"Active");
		refresh_page();
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodFromExistingRateSet(effectiveDateRUG,terminationDateRUG,selectUserRateSetSBRUG,RUGCodeValue);
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodDetails(effectiveDateRUG,terminationDateRUG,editWindowSaveButton,periodDDRUG);		
		deleteFirstPeriod();
		
		searchPPS("Search User Rates", "RUG_Import", userRateSetTitleBar, searchedResult);
		oRateSheetLibrary.pageNavigations();
		filterReportsRUG();
		oRateSheetLibrary.navigateToRateSheets();
		oRateSheetLibrary.searchRateSheet("Production", "AutomationBLR_DND");
		addPPSUserRateSetTypeTerm("RUG_","RUG User");
		logout();
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------	

	public By addCMGUserRateSetWindow = By.xpath("//div[@title='Add CMG User Rate Set']");
	
	By copyFromExistingCMGRateSet = By.xpath("//div[@ng-hide='copyPeriod.copyExistingPeriod']/input");
	
	By comorbidityTierColumn = By.xpath("//table//tr[@ng-click='editCmgUserRateEntry(entry)']/td[text()='D']");
	
	By comorbidityTierColumn1 = By.xpath("//table//tr[@ng-click='editCmgUserRateEntry(entry)']/td[text()='A']");
	
	By userRateSetTitleBarCMG = By.xpath("//div[@class='pad-l-10 col-md-6 col-md-5 col-sm-6 large-header ng-binding']");
	
	public By effectiveDateCMG = By.xpath("//input[@id='effectiveDateCmgPeriod']");
	
	public By terminationDateCMG = By.xpath("//input[@id='terminationDateCmgPeriod']");
	
	By periodDDCMG = By.xpath("//div[@add-period='addCmgUserRatePeriod']//a[@class='btn btn-light btn-default btn-sm']//span[1]");
	
	By CMGCodeValue = By.xpath("//table//tr[@ng-click='editCmgUserRateEntry(entry)']/td[text()='1321']");
	
		
	// to save and validate RUG user rate set
	public void saveButtonCMG(String copyCheckBox,String chooseFile)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
			
		click_button("Save Button", saveButton);	
		waitFor(userRateSetTitleBar, "User Rate Set Title Bar");
		
		if(copyCheckBox.equalsIgnoreCase("CopyFrom"))
		{
			if(IsDisplayed("CMG Code Value", comorbidityTierColumn))
				oReport.AddStepResult("", "In 'Add CMG User Rate Set' window filled all the mandatory fields then checked copy details from existing User Rate Set and selected previously created User Rate Set then clicked on save, Verified that new User Rate set is created with details copied from searched User Rate Set", "PASS");
			else
				oReport.AddStepResult("", "In 'Add CMG User Rate Set' window filled all the mandatory fields then checked copy details from existing User Rate Set and selected previously created User Rate Set then clicked on save but that new User Rate set is not created with details copied from searched User Rate Set", "FAIL");
		}
		else if(chooseFile.equalsIgnoreCase("Import"))
		{
			if(IsDisplayed("RUG Code Value", comorbidityTierColumn1))
				oReport.AddStepResult("", "In Add CMG User Rate Set' window filled all the mandatory fields, imported the CMG file and clicked on save, Verified that new User Rate Set is created with data present in the import file", "PASS");
			else
				oReport.AddStepResult("", "In Add CMG User Rate Set' window filled all the mandatory fields, imported the CMG file and clicked on save but that new User Rate Set is not created with data present in the import file", "FAIL");			
		}
		else
		{
			if(oParameters.GetParameters("CMG_"+"UserRateSetName").equalsIgnoreCase(get_field_value("userRateSetTitleBar", userRateSetTitleBarCMG)))
					oReport.AddStepResult("", "In 'Add CMG User Rate Set' window filled all the mandatory fields then clicked on save, Verified that new User Rate set is created ", "PASS");
			else
				oReport.AddStepResult("", "In 'Add CMG User Rate Set' window filled all the mandatory fields then clicked on save but that new User Rate set is not created ", "FAIL");
		}		
	}	

	
	By editIconCMG = By.xpath("//a[@title='Edit CMG User Rate Set']/i[@class='left fa fa-pencil']");
	
	By editWindowCMG = By.xpath("//div[@title='Edit CMG User Rate Set']");
		
	public By comorbidityTierDD = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//select[@id='comorbidityTier']");
	
	public By caseMixGroupCode = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//input[@id='caseMixGroupCode']");
	
	public By rehabilitationImpairmentCategory = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//input[@id='rehabilitationImpairment']");
	
	public By lengthOfStay = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//input[@id='slos']");
		
	
	//To fill 'Add User Rate Set Entry' window details
	public void userRateSetEntryDetailsCMG(String comorbidityTier,String cmgCode,String ric,String weightCMG,String los)
	{
		selectOption(comorbidityTierDD, "visibletext", comorbidityTier);
		enter_text_value("Case Mix Group (CMG) Code", caseMixGroupCode, cmgCode);
		enter_text_value("Rehabilitation Impairment Category", rehabilitationImpairmentCategory, ric);
		enter_text_value("Weight", weight, weightCMG);
		enter_text_value("Length of Stay (LOS)", lengthOfStay, los);
		click_button("Save Button", saveButton);	
		
		By addedUserRateSet = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tr[contains(.,'"+los+"')]");
		
		if(IsDisplayed("User Rate Set Entry", addedUserRateSet))
			oReport.AddStepResult("", "In 'Add User Rate Set Entry' window filled the mandatory fields then clicked on save, Verified that new rate set entry has been added without any error", "PASS");
		else
			oReport.AddStepResult("", "In 'Add User Rate Set Entry' window filled the mandatory fields then clicked on save but new rate set entry not added ", "FAIL");		
	}
	
	
	By rateEntryDeleteIconCMG = By.xpath("//a[@ng-click='deleteCmgRateEntry(entry); $event.stopPropagation()'][@style='display: inline;']/i[@class='left fa fa-minus-square']");
	
	By wageAdjustmentBaseRate = By.xpath("//div[@id='editCmgEntryPanel']//input[@id='wageAdjustmentBaseRate']");
	
	By userRateSetEditSave = By.xpath("//div[@id='editCmgEntryPanel']//button[@value='Save']");
	
	By userRateSetEditWindowCancelCMG = By.xpath("//div[@id='editCmgEntryPanel']//button[@value='Cancel']");
	
	By activeRadioCMG = By.xpath("//form[@id='addCmgUserRatePeriod']//input[@value='Actv']");
	
	
	// to edit user rate set entries
	public void editUserRateSetCMG()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Wage Adjustment BaseRate", wageAdjustmentBaseRate, "832");
		
		By modifiedUserRateSet = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tr/td[contains(text(),'832')]");
		
		click_button("Save Button", userRateSetEditSave);		
		
		if(IsDisplayed("Modified User Rate Set", modifiedUserRateSet))
			oReport.AddStepResult("", "Clicked on user rate set then modified the values clicked on save, Verified that Rate Set is modified and Wage Adjustment Base Rate is displayed", "PASS");
		else
			oReport.AddStepResult("", "Clicked on user rate set then modified the values clicked on save but that Rate Set is not modified ", "FAIL");				
	}
	
	
	// user rate set edit window cancel scenario
	public void userRateSetCancelScenarioCMG(String code)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By userRateSetEditWindow = By.xpath("//div[@class='medium-header truncate ng-binding'][@title='"+code+"']");
		
		enter_text_value("Wage Adjustment BaseRate", wageAdjustmentBaseRate, "212");
		
		click_button("Cancel Button", userRateSetEditWindowCancelCMG);
		
		if(IsDisplayed("Pop Up Window", popUpWindow))
			oReport.AddStepResult("", "In User Rate Set edit window made some changes clicked on cancel, Verified that 'You have unsaved changes' pop up is dispalyed ", "PASS");
		else
			oReport.AddStepResult("", "In User Rate Set edit window made some changes clicked on cancel but 'You have unsaved changes' pop up is not dispalyed ", "FAIL");
		
		click_button("Discard Button",unsavedWindowDelete);
		
		if(IsDisplayed("User Rate Set edit window", userRateSetEditWindow))
			oReport.AddStepResult("", "In unsaved changes popup window clicked on cancel but User Rate Set edit window is not closed ", "FAIL");			
		else
			oReport.AddStepResult("", "In unsaved changes popup window clicked on cancel, Verified that User Rate Set edit window closed ", "PASS");
	}
	
	
	By importCMGWindow = By.xpath("//div[@title='Import CMG User Rate Set']");
	
	By downloadSampleCMG = By.xpath("//form[@id='importCmgUserRatePanelForm']//a[text()='Download a sample .XLSX']");
	
	By importChooseFileCMG = By.xpath("//input[@id='CMGuserRateEntryIframe'][@name='userRateEntry']");
	
	By importSaveCMG = By.xpath("//div[@id='fullFooter']//input[@value='Save'][@id='Id']");
	
	By CMGColumn = By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']//tbody/tr[1]/td[3]");
	
	By addFilterCMG = By.xpath("//div[@class='pad-t-15 col-lg-12 col-md-12 col-sm-12 large-height scroll-auto']//span[text()='Add Filter']");
	
	By weightCMG = By.xpath("//li[@ng-click='selectFilterField(f)'][contains(.,'Weight')]");
	
	
	// Filter reports
	public void filterReportsCMG()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("CMGColumnValue", get_field_value("CMG Weight", CMGColumn));	
		fixed_wait_time(4);
		click_button("Add Filter", addFilterCMG);
		
		if(IsDisplayed("Choose option field", optionFieldFilters))
		{
			click_button("Optional field filter drop down", optionFieldFilters);
			click_button("CMG Code Element", weightCMG);
			enter_text_value("Enter Filter Text", textToFilter, oParameters.GetParameters("CMGColumnValue"));
			performKeyBoardAction("ENTER");
			fixed_wait_time(5);
			
			if(oParameters.GetParameters("CMGColumnValue").equalsIgnoreCase(get_field_value("CMG Weight", CMGColumn)))
				oReport.AddStepResult("", "Filter is applied and respective CMG weight record is displayed", "PASS");
			else
				oReport.AddStepResult("", "Filter is not applied or Filtered CMG weight records are not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("", "Clicked on 'Add Filter' but filter dropdown is not displayed", "PASS");	
	
	}
	
	//PPS CMG User Rates CRUD operation VR
	public void PPS_CMG_UserRates()
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS CMG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		searchPPS("Search User Rates", "Automation_DND", userRateSetTitleBarCMG, searchedResult);
		
		clickAddButton("Add a CMG User Rate Set", "Add CMG User Rate Set window", addPPSSetButton, addCMGUserRateSetWindow);
		addUserRateSetDetails("","","CMG_",copyFromExistingCMGRateSet,"","");
		saveButtonCMG("","");

		clickAddButton("Add a CMG User Rate Set", "Add CMG User Rate Set window", addPPSSetButton, addCMGUserRateSetWindow);
		addUserRateSetDetails("","Import","CMG_",copyFromExistingCMGRateSet,"importCMGUserRateIframe","C:\\CCM\\AutoIt\\CCM_PPS_CMG_ UserRates_SampleCMGTable.exe");
		saveButtonCMG("","Import");	
			
		clickAddButton("Add a CMG User Rate Set", "Add CMG User Rate Set window", addPPSSetButton, addCMGUserRateSetWindow);
		addUserRateSetDetails("CopyFrom","","CMG_",copyFromExistingCMGRateSet,"","");
		saveButtonCMG("CopyFrom","");		
		
		deleteUserRateSet(rateEntryDeleteIconCMG);
		
		editPPSsetName(editIconCMG,editWindowCMG,userRateSetTitleBarCMG);		
		
		addUserRateSetEntryWindow();
		userRateSetEntryDetailsCMG("A - Without Comorbidity","23","23","0.3","3");
		
		addUserRateSetEntryWindow();
		userRateSetEntryDetailsCMG("B - Tier 1","24","24","0.4","4");
		
		addUserRateSetEntryWindow();
		userRateSetEntryDetailsCMG("C - Tier 2","21","21","0.11","1");
		
		addUserRateSetEntryWindow();
		userRateSetEntryDetailsCMG("D - Tier 3","22","22","0.2","2");
			
		editUserRateSetEntryWindow("24");		
		editUserRateSetCMG();
		
		editUserRateSetEntryWindow("21");
		userRateSetCancelScenarioCMG("21");
		
		importUserRateSet("CMG",importCMGWindow,downloadSampleCMG,importChooseFileCMG,"C:\\CCM\\AutoIt\\CCM_PPS_CMG_ UserRates_SampleCMGTable_Import",importSaveCMG,"sampleCmgTable.xlsx"); // need to modify
		
		periodEditWindow(editEffectiveWindow);
		editPPSSetStatus(activeRadioCMG,"Active");
		
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodFromExistingRateSet(effectiveDateCMG,terminationDateCMG,selectUserRateSetSBCMG,CMGCodeValue);
		addPeriodWindow(addEffectivePeriodWindow);
		addPeriodDetails(effectiveDateCMG,terminationDateCMG,editWindowSaveButton,periodDDCMG);

		deleteFirstPeriod();
				
		searchPPS("Search User Rates", "CMG_Import", userRateSetTitleBarCMG, searchedResult);
		oRateSheetLibrary.pageNavigations();
		filterReportsCMG(); 
		oRateSheetLibrary.navigateToRateSheets();
		oRateSheetLibrary.searchRateSheet("Production", "AutomationBLR_DND");
		addPPSUserRateSetTypeTerm("CMG_","IRF CMG Pricer"); 
		logout();
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	
	public By groupingRuleSetTab =  By.xpath("//div[@tabs='tabs']//a[text()='Grouping Rule Set']");
	By addGroupingRuleSetIcon = By.xpath("//div[@ng-click='addDrgGroupingRuleSet()']");
	By previousTerminationDate = By.xpath("//ul[@class='data-list drillable-list scroll-auto']/li[1]/span[@ng-if='set.stopDateTime!=null']");
	By firstEntryEffectiveDate = By.xpath("//ul[@class='data-list drillable-list scroll-auto']/li[1]");
	By startTime = By.xpath("//input[@id='startTimer']");
	By addGroupingRuleSetWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add DRG Grouping Rule Set']");
	By addFilter_GRS = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 large-height']//span[contains(.,'Add Filter')]");
	By cancelButton_GRS = By.xpath("//div[@id='fullFooter']//button[@id='button.cancelId']");
	public void addGroupingRuleSet(String ngClick, String formId, By addGroupingRuleSetWindow, By previousTerminationDate, By firstEntryEffectiveDate)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By effectiveDate_GRS = By.xpath("//form[@id="+"'"+formId+"'"+"]//input[@id='startDate']");
		By addGroupingRuleSetIcon = By.xpath("//div[@ng-click='"+ngClick+"']");
		
		if(get_field_value("First entry Terminatin Date", previousTerminationDate).isEmpty())
		{
			
			oParameters.SetParameters("EffectivePeriodDate", get_next_date(String.valueOf(get_field_value("First entry Effective Date", firstEntryEffectiveDate).replaceAll("-", "/")), 15));
			clickAddButton("Add Grouping Rule Set Icon", "Add DRG Grouping Rule Set window", addGroupingRuleSetIcon, addGroupingRuleSetWindow);
			enter_text_value("Grouping Rule Set Effective Date", effectiveDate_GRS, oParameters.GetParameters("EffectivePeriodDate"));
			enter_text_value("Start Time", startTime, "00:00");
			performKeyBoardAction("ENTER");
			clickSaveButton("Save button", "Add Grouping Rule Set Window", saveButton, addGroupingRuleSetWindow);
		}
		else
		{
			oParameters.SetParameters("EffectivePeriodDate", get_next_date(String.valueOf(get_field_value("First entry Terminatin Date", previousTerminationDate).substring(2, 12).replaceAll("-", "/")), 15));
			System.out.println(oParameters.GetParameters("EffectivePeriodDate"));
			clickAddButton("Add Grouping Rule Set Icon", "Add DRG Grouping Rule Set window", addGroupingRuleSetIcon, addGroupingRuleSetWindow);
			enter_text_value("Grouping Rule Set Effective Date", effectiveDate_GRS, oParameters.GetParameters("EffectivePeriodDate"));
			enter_text_value("Start Time", startTime, "00:00");
			performKeyBoardAction("ENTER");
			clickSaveButton("Save button", "Add Provider Values Window", saveButton, addGroupingRuleSetWindow);
		}
			
	}

	
	By rulesWindow = By.xpath("//div[@class='pull-left rule-label'][contains(text(),'Rules')]");
	By periodDropDwon_GRS = By.xpath("//div[@edit-period='editDrgGroupingRuleSetPeriod']//a[@class='btn btn-light btn-default btn-sm']");
	
	public void validateNewGRS(By rulesWindow,By periodDropDwon_GRS)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
	
		oParameters.SetParameters("displayedDate", get_field_value("Effective Period", periodDropDwon_GRS).substring(0, 10));
		if(IsDisplayed("Rules Window", rulesWindow) && oParameters.GetParameters("displayedDate").equalsIgnoreCase(oParameters.GetParameters("EffectivePeriodDate")))
			oReport.AddStepResult("New rule set", "Filled all mandatory fields and new Rule Set is created", "PASS");
		else
 			oReport.AddStepResult("New rule set", "New Rule set is not created", "FAIL");
	}
	
	
	public By addRule = By.xpath("//a[@title='Add Rule']"); 
	By addRuleWindowDRG = By.xpath("//div[@title='Add Rule']");
	By addRuleWindowAPCAPG = By.xpath("//div[@title='Add APC/APG Grouping Rule']");
	public By groupingRuleName = By.xpath("//input[@id='groupingRuleSetRuleName']");
	By groupingDefinition = By.xpath("//div[@form-id='entryFormModel.formId']//input[@id='groupingDefinition']");
	By groupingDefinitionDD = By.xpath("//div[@form-id='entryFormModel.formId']//ul[@id='-list']");
	By firstElementDD = By.xpath("//div[@form-id='entryFormModel.formId']//ul[@id='-list']/li[1]");

	By addRuleBeforeAfter = By.xpath("//div[@class='workflow  modal-medium']//div[@id='styledDropdown']");
	By addRuleAfter = By.xpath("//div[@class='workflow  modal-medium']//div[@id='styledDropdown']//a[contains(.,'Add Rule After')]");
	
//	By addRuleBeforeAfter = By.xpath("//div[@ng-if='(selectedGroupingRuleSet.rules.length > 0)']//div[@on-change='positionChange(rulePlacement)']");
//	By addRuleAfter = By.xpath("//div[@ng-if='(selectedGroupingRuleSet.rules.length > 0)']//li[contains(.,'Rule After')]");
	
	public void addNewRule(String position,By addRuleWindow)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		fixed_wait_time(2);
		click_button("Add Rule button", addRule);
		
		if(IsDisplayed("Add Rule window", addRuleWindow))
		{	
			oReport.AddStepResult("Add Rule Window", "Clicked on 'Add Rule' button, Verified that Add Rule window is displayed", "PASS");
			
			if(position.equalsIgnoreCase("after"))
			{
				oParameters.SetParameters("FirstPositionRuleBefore", get_field_value("Rule Name", rule1));
				click_button("After/Before button", addRuleBeforeAfter);
				click_button("Add Rule After", addRuleAfter);
			}
			
			enter_text_value("Grouping Rule Name", groupingRuleName, "AutomationTest_Rule_"+get_random_alphanumeric(3));
			oParameters.SetParameters("RuleName", get_field_value("Given Rule Name", groupingRuleName));		
			facilityName("PPS", "Apollo srn facility");
			newQgBuild();
			click_button("Grouping Definition search field", groupingDefinition);
			performKeyBoardAction("ENTER");
			fixed_wait_time(5);
			
			if(IsDisplayed("Grouping Definition drop down", groupingDefinitionDD))
			{
				click_button("First value from dropdown", firstElementDD);
				oParameters.SetParameters("selectedGroupingDefinition", get_field_value("selected Grouping definition", groupingDefinition));
				oReport.AddStepResult("Add Rule Window", "Screen shot for assuring all details are filled in Add Rule Window", "SCREENSHOT");
			}
				
			else
				oReport.AddStepResult("Select Grouping Definition", "Existing Grouping Definition is not found to select grouping defintion", "FAIL");
		}	
		else
			oReport.AddStepResult("Add Rule Window", "Clicked on 'Add Rule' button but that Add Rule window is not displayed", "FAIL");
		
		fixed_wait_time(5);
	}
	
	
	
	By addQualificationButton = By.xpath("//div[@class='align-aside col-lg-10 col-md-10 col-sm-10 addQualifierField']//button[@id='qualifierButton']");
	By addQgWindow = By.xpath("//div[@title='Add Qualification Group']");
	By qualificationGroupName = By.xpath("//div[@id='addQualificationGroupModal']//input[@id='expression-name']");
	By environmentlink = By.xpath("//div[@id='addQualificationGroupModal']//p[contains(.,'Environment')]");
	By ppsDRGroupingRuleSet = By.xpath("//div[@id='addQualificationGroupModal']//p[contains(.,'Grouping Rule Set')]");
	By healthPlanAlias = By.xpath("//div[@id='addQualificationGroupModal']//p[contains(.,'Health Plan Alias')]");
	By selectOperator = By.xpath("//div[@id='addQualificationGroupModal']//select[@id='symbols1']");
	By selectMisc = By.xpath("//div[@id='addQualificationGroupModal']//select[@id='symbols2']");
	By qgTextBox = By.xpath("//div[@id='addQualificationGroupModal']//textarea[@id='expression']");
	By saveQG = By.xpath("//div[@id='addQualificationGroupModal']//input[@value='Save']");
	By createdQG = By.xpath("//input[@selected-value='groupingRuleSetRule.id']");
//	By validateButton = By.xpath("//form[@class='ng-isolate-scope ng-dirty ng-valid ng-valid-required']//button[@id='validationButton']");
	
	By validateButton = By.xpath("//div[@id='addQualificationGroupModal']//button[@id='validationButton']");
	
	public void newQgBuild()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickAddButton("Qualificatio group Button", "Add Qualification Group Window", addQualificationButton, addQgWindow);
		enter_text_value("Qualification Group Name", qualificationGroupName, "AutomationTest"+get_random_alphanumeric(3));
		oParameters.SetParameters("QualificationGroupName", get_field_value("Given Qualification Group Name", qualificationGroupName));
		
		if(!IsDisplayed("PPS DRG Grouping Rule Set", ppsDRGroupingRuleSet))
		{
			click_button("Environment Link", environmentlink);
		}
		
		click_button("PPS DRG Grouping Rule Set", ppsDRGroupingRuleSet);
		click_button("Health Plan Alias", healthPlanAlias);
		select_option("Operators", selectOperator, "4");
		select_option("Misc", selectMisc, "5");
		enter_text_value_without_clear("Enter HealthPlan Alias", qgTextBox, "BlrTest"+get_random_alphanumeric(2));
		select_option("Misc", selectMisc, "5");
		click_button("Validate Button", validateButton);
		oParameters.SetParameters("createdQG", get_attribute_value("Qualification Group Expression", qgTextBox,"value").trim());
		
		System.out.println(oParameters.GetParameters("createdQG"));
		click_button("Save Qualification Group", saveQG);
		
		if(get_field_value("Created QG Expression", createdQG).trim().equalsIgnoreCase(oParameters.GetParameters("createdQG")))
			oReport.AddStepResult("New Qualification Group Expression", "Created New Qualification Group Expression", "PASS");
		else
			oReport.AddStepResult("New Qualification Group Expression", "Created Qualification Group Expression is different from displayed expression", "FAIL");
	}
	
	
//	By rule = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)'][1]");
//	By Secondrule = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)'][2]");
//	By rule1 = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)'][1]/td[1]");
//	By rule2 = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)'][1]/td[2]");
//	By rule3 = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)'][1]/td[3]");
//	By saveRule = By.xpath("//div[@class='modal-footer document-action-bar tab-footer ng-scope']//input[@value='Save']");
	
	By saveRule = By.xpath("//div[@class='workflow  modal-medium']//input[@value='Save']");
	
	By rule = By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr[1]");
	By secondRule= By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr[2]");
	By rule1 = By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr[1]/td[1]");
	By rule2 = By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr[1]/td[2]");
	By rule3 = By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr[1]/td[3]");
	
	
	public void validateRule()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Save Rule", saveRule);
		fixed_wait_time(20);
		if(IsDisplayed("First Rule path", rule))
		{
			if(get_field_value("Rule Name", rule1).equalsIgnoreCase(oParameters.GetParameters("RuleName")) && 
					get_field_value("Qualification Group Name", rule2).equalsIgnoreCase(oParameters.GetParameters("QualificationGroupName")) &&
					get_field_value("PPS Grouping Definition", rule3).equalsIgnoreCase(oParameters.GetParameters("selectedGroupingDefinition")))
				oReport.AddStepResult("Validate Rule Created", "Verified that new rule is created with selected values", "PASS");
			else
				oReport.AddStepResult("Validate Rule Created", "Verified the new rule is not created with selected values", "FAIL");
		}
		else
			oReport.AddStepResult("Validate Rule Created", "Verified the new rule is created but not displayed under Rules", "FAIL");
	}
	
	
	By editGroupingRuleName = By.xpath("//input[@id='groupingRuleName']");
	By editQualifierButton = By.xpath("//button[@ng-click='editQualifierModal(qualificationExpression)']");
	By editGDSearchBox = By.xpath("//div[@form-id='editEntryFormModel.formId']//input[@id='groupingDefinition']");
//	By editGroupingDefintion = By.xpath("//div[@model='drgGroupingRuleSetEditEntry.definitionId']//ul[@id='-list']/li[3]");
//	By editAddRuleBeforeAfter = By.xpath("//form[@id='drgGroupingRuleSetEditForm']//a/span[contains(.,'Add Rule')]");
//	By editRulepostionAfter = By.xpath("//form[@id='drgGroupingRuleSetEditForm']//a[contains(.,'Add Rule After')]");
	
	
	By editGroupingDefintion = By.xpath("//div[@form-id='editEntryFormModel.formId']//ul[@id='-list']/li[3]");
	By editAddRuleBeforeAfter = By.xpath("//div[@id='editGroupingRuleSetEntryPanel']//a/span[contains(.,'Add Rule')]");
	By editRulepostionAfter = By.xpath("//div[@id='editGroupingRuleSetEntryPanel']//a[contains(.,'Add Rule After')]");
	
	
	public void editRule(String position)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("PreviousRuleName", get_field_value("Rule Name", rule1));
		oParameters.SetParameters("PreviousQGName", get_field_value("Rule Name", rule2));
		oParameters.SetParameters("PreviousGrpngDefintn", get_field_value("Rule Name", rule3));
		
		
		click_button("Edit Rule", rule);
		if(IsDisplayed("edit grouping definition", editGDSearchBox))
		{
			if(position.equalsIgnoreCase("After"))
			{
				oParameters.SetParameters("Position3RuleBefore", get_field_value("Rule Name", rulePosition3));
				
				click_button("Edit Rule", secondRule);
				click_button("Add Rule Before/After Button", editAddRuleBeforeAfter);
				click_button("Add Rule After", editRulepostionAfter);
			}
				
			enter_text_value_without_clear("Edit Rule Name", editGroupingRuleName, get_random_alphanumeric(3)+"_Edit");
			editQualifier();
			enter_text_value("Grouping Definition", editGDSearchBox, "");
			performKeyBoardAction("ENTER");
			fixed_wait_time(4);
			click_button("select Grouping Definition", editGroupingDefintion);
			click_button("Save edit rule", editSaveButton);
				
			if(!IsDisplayed("RedBoxError", redBoxError))
			System.out.println("Red box error not found");
			else
			{
				oReport.AddStepResult("Red Box Error CLosed", "Upon clicking on " + saveButton + "red box appeared", "FAIL");
				click_button("RedBoxCloseButton", redBoxErrorCloseIcon);
				click_button("Cancel edit Rule Set", cancelButton_GRS);
				click_button("Discard the changes", popUpDiscardButton);
			}
		}	
	}
	
	
	By editQgButton = By.xpath("//button[@ng-click='editQualifierModal(qualificationExpression)']");
	By editQgWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][contains(.,'Edit Qualification Group')]");
	By editQgName = By.xpath("//form[@id='editQualExpressionBuilder']//input[@id='expression-name']");
	By editQGText = By.xpath("//form[@id='editQualExpressionBuilder']//textarea[@id='expression']");
	By editSaveQG = By.xpath("//div[@id='editQualificationGroupModal']//input[@value='Save']");
	
//	By editSaveQG = By.xpath("//input[@id='editQgroupId']");
	
	By redBoxError = By.xpath("//ul[@class='error-items']");		
	By redBoxErrorCloseIcon = By.xpath("//span[@ng-show='canShowCloseBox']");
	
	public void editQualifier()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit Qualifier button", editQgButton);
		if(IsDisplayed("Edit QG Window", editQgWindow))
		{
			enter_text_value_without_clear("Qualification Group Name", editQgName, get_random_alphanumeric(3)+"_Edit");
			enter_text_value("Modify Qualification Group", editQGText, "TRUE");
			click_button("Save QG", editSaveQG);
			
			if(!IsDisplayed("RedBoxError", redBoxError))
				System.out.println("Red box error not found");
			else
			{
				oReport.AddStepResult("Red Box Error CLosed", "Upon clicking on " + saveButton + "red box appeared, hence closed redbox", "FAIL");
				click_button("RedBoxCloseButton", redBoxErrorCloseIcon);
			}
			
		}
		else
			oReport.AddStepResult("Modify Qualification Group", "Edit Qualification Group window is not dispalyed", "FAIL");
	}
	
	
	public void validateEditScenario()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oReport.AddStepResult("Validating postion of Rule", get_field_value("Rule Name", rule1), "INFO");
		
		if(!get_field_value("Rule Name", rule1).equalsIgnoreCase(oParameters.GetParameters("PreviousRuleName")) && 
				!get_field_value("PPS Grouping Definition", rule3).equalsIgnoreCase(oParameters.GetParameters("PreviousGrpngDefintn")) &&
				!get_field_value("Qualification Group Name", rule2).equalsIgnoreCase(oParameters.GetParameters("PreviousQGName")))
			oReport.AddStepResult("Validate Edit Rule", "Verified that rule is modified", "PASS");
		else
			oReport.AddStepResult("Validate Edit Rule", "Verified that rule is not modified", "FAIL");
	}
	
	
//	By deleteRule = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)'][1]/td//i[@class='left fa fa-minus-square']");
//	By noOfRules = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)']");
//	By firstRule = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)'][1]");
	
	By deleteRule = By.xpath("//a[@icon='minus-square'][@style='display: inline;']//i[@class='left fa fa-minus-square']");
	By noOfRules = By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr");
	By firstRule = By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr[1]");
	
	
	public void deleteRule()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int countBeforeDelete = noOfRows(noOfRules);
		mouse_hover("First Rule", firstRule);
		click_button("Delete Rule", deleteRule);
		click_button("Delete button on pop-up", popUpDeleteButton);
		fixed_wait_time(2);
		int countAfterDelete = noOfRows(noOfRules);
		
		if(countBeforeDelete>countAfterDelete && !IsDisplayed("Delete button on pop-up", popUpDeleteButton))
			oReport.AddStepResult("Delete Rule", "Clicked on delete icon and Rule is deleted. ", "PASS");
		else
			oReport.AddStepResult("Delete Rule", "Delete Pop Up is still present on the screen, Hence rule is not deleted", "FAIL");
	}
	
	
	By copyCheckBox_GRS = By.xpath("//input[@id='copyGroupingRuleSet']");
	By selectPeriod_GRS = By.xpath("//form[@id='addDrgGroupingRuleSet']//select[@id='copyPeriod']");
	
	public void copyFromExisting_GRS(String formId,By addGRSWindow,By save)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		By effectiveDate_GRS = By.xpath("//form[@id="+"'"+formId+"'"+"]//input[@id='startDate']");
		
		clickAddButton("Add Grouping Rule Set Icon", "Add Grouping Rule Set window", addPPSSetButton, addGRSWindow);
		oParameters.SetParameters("EffectivePeriodDate", get_next_date(String.valueOf(get_field_value("First entry Effective Date", firstEntryEffectiveDate).replaceAll("-", "/")), 15));
		enter_text_value("Grouping Rule Set Effective Date", effectiveDate_GRS, oParameters.GetParameters("EffectivePeriodDate"));
		enter_text_value("Start Time", startTime, "00:00");
		performKeyBoardAction("ENTER");
		click_button("Copy Details from Existing Grouping Rule Set ", copyCheckBox_GRS);
		select_option("Select Period", selectPeriod_GRS, "0");
		clickSaveButton("Save button", "Add Grouping Rule Set Window", save, addGRSWindow);
	}
	
	
	public void validateRulePosition()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		click_button("Save Rule", saveRule);
		oParameters.SetParameters("FirstPositionRuleAfter", get_field_value("Rule Name", rule1));
		fixed_wait_time(10);
		
		oReport.AddStepResult("Validating postion of Rule", oParameters.GetParameters("FirstPositionRuleBefore"), "INFO");
		oReport.AddStepResult("Validating postion of Rule", oParameters.GetParameters("FirstPositionRuleAfter"), "INFO");
		
		oParameters.SetParameters("SecondPositionRule", get_field_value("Rule Name", rulePosition2));
		
		//if(oParameters.GetParameters("FirstPositionRuleBefore").equalsIgnoreCase(oParameters.GetParameters("FirstPositionRuleAfter")))
		
		if(oParameters.GetParameters("RuleName").equalsIgnoreCase(oParameters.GetParameters("SecondPositionRule")))	
			oReport.AddStepResult("Validating postion of Rule", "Creating new Rule after selected rule and Rule is created after the selected rule successfully", "PASS");
		else
			oReport.AddStepResult("Validating postion of Rule", "New Rule is not created after selected Rule", "FAIL");
		
	}
	
	
//	By rulePosition3 = By.xpath("//tr[@ng-click='editDrgGroupingRuleSetRules(entry)'][3]/td[1]");
	
	By rulePosition2 = By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr[2]/td[1]");
	
	By rulePosition3 = By.xpath("//table[contains(@class,'table table-condensed table-striped table')]//tbody//tr[3]/td[1]");
		
	
	public void validateEditRulePostion()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("Position3RuleBefore").equalsIgnoreCase(get_field_value("Previous Rule at postion 3", rulePosition3)))
			oReport.AddStepResult("Validating rule position in edit window", "Selected Rule is moved to selected position in edit window", "PASS");
		else
			oReport.AddStepResult("Validating rule position in edit window", "Selected Rule is not moved to selected position in edit window", "FAIL");
	}

	
	public void DRG_GroupingRuleSet() 
	{
		login("EDIT");
		changePricingEngine();
		navigate_to("PPS Plugin", "Select PPS Group type to begin.", PPSplugin, PluginPage);//Method for navigating to PPS plugin
		selectGroupType("PPS DRG",SelectGroupDropDown,OpenPageValidation);//Method to select type of PPS 
		navigate_to("Grouping Rule Set", "Grouping Rule Set open page validation", groupingRuleSetTab, OpenPageValidation);//Method navigates to Grouping Rule Set tab
		
	//	addGroupingRuleSet("addDrgGroupingRuleSet()","addDrgGroupingRuleSet",addGroupingRuleSetWindow ,previousTerminationDate,firstEntryEffectiveDate);//Method to create new Grouping Rule Set
	//	validateNewGRS(rulesWindow,periodDropDwon_GRS);//Method for validating newly created rule set
		
		click_button("First Entry", firstEntryEffectiveDate);
		addNewRule("",addRuleWindowDRG);
		validateRule();
		
		editRule("");
		validateEditScenario();
		
		/*deleteRule();
	    copyFromExisting_GRS("addDrgGroupingRuleSet",addGroupingRuleSetWindow,saveButton);
		validateNewGRS(rulesWindow,periodDropDwon_GRS);*/
		
		addNewRule("after",addRuleWindowDRG);
		validateRulePosition();
		
		editRule("after");
		validateEditRulePostion();
		
		filter("PPS","workers",addFilter_GRS,"drg");
		filter("Qualification","blue",addFilter_GRS,"drg");
		filter("Rule","apr",addFilter_GRS,"drg");
		deleteRule();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
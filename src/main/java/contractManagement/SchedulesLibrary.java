package contractManagement;

import java.io.*;
import java.util.List;
import org.openqa.selenium.*;
import libraries.*;

public class SchedulesLibrary extends CCMLibrary 
{
	ExcelData oExcelData = new ExcelData(oParameters);

	public SchedulesLibrary(WebDriver driver, Report oReport, Parameters oParameters) 
	{
		super(driver, oReport, oParameters);
	}

	
	By schedulesButton = By.xpath("//*[@id='nav']//a[text()='Schedules']");

	public By scheduleTypeDropDown = By.xpath("//div[@id='scheduleView']/div[@class='schedules-group-types ng-scope']/div[@class='portal-header']//div[@id='styledDropdown']//a[@class='btn btn-light view-bg-text']/span[1]");

	
	public void xpathChange() 
	{
		if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA")) 
		{
			relatedScheduleTitle = relatedScheduleTitleDC;
			groupCodeRatesScheduleTitle = groupCodeRatesScheduleTitleDC;
			modifierScheduleTitle = modifierScheduleTitleDC;
			schedulesTitle = schedulesTitleDC;
			noOfCodes = noOfCodesDC;
			noOfSchedules = noOfSchedulesDT;
		}
		else if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT")) 
		{
			relatedScheduleTitle = relatedScheduleTitleDC;
			groupCodeRatesScheduleTitle = groupCodeRatesScheduleTitleDC;
			modifierScheduleTitle = modifierScheduleTitleDC;
			schedulesTitle = schedulesTitleDC;
			noOfCodes = noOfCodesDC;
	
		}
		else if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVTEST")) 
		{
			relatedScheduleTitle = relatedScheduleTitleDC;
			noOfSchedules = noOfSchedulesDT;
			groupCodeRatesScheduleTitle = groupCodeRatesScheduleTitleDC;
			modifierScheduleTitle = modifierScheduleTitleDC;
			schedulesTitle = schedulesTitleDC;
			noOfCodes = noOfCodesDC;
		}
	}

	
	// Navigating to Schedules
	public void navigateToSchedules() 
	{
		navigate_to("Schedules Link", "Schedule Type Drop Down", schedulesButton, scheduleTypeDropDown);
	}

	public By feeSchedulesOption = By.xpath("//*[@id='i[__valueField]']//a[contains(.,'Fee Schedules')]");
	public By addAFeeScheduleLink = By.xpath("//a[contains(text(),'Add a Fee Schedule')]");

	
	public void selectSchedule(String scheduleName, String validationOptionName, By scheduleType, By validationOption)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Select a Schedule Type Drop Down", scheduleTypeDropDown);
		click_button( scheduleName+ " Option", scheduleType);
		
		if (IsElementDisplayed(validationOptionName, validationOption))
			oReport.AddStepResult("Add Schedule ","Clicked on ScheduleType drop down and selected " + scheduleName + "type and verified that it has opened respective schedules page","PASS");
		else
			oReport.AddStepResult("Add Schedule ","Failed to navigate to" +  scheduleName + "page","FAIL");
	}	

	
	By schedulesSearchBox = By.xpath("//*[@id='scheduleView']//input[@placeholder='Search Schedules']");

//	By firstSchedule = By.xpath("//*[@id='scheduleView']//ul[@class='data-list drillable-list scroll-auto']/li[1]");

	public By schedulesTitle = By.xpath("//div[@class='pad-l-10 col-md-6 col-md-5 col-sm-6 large-header ng-binding']");

	By schedulesTitleDC = By.xpath("//div[@class='pad-l-10 col-lg-6 col-md-6 col-sm-6 large-document-header hide-overflow ng-binding']");

	
	// Checking clicked Schedule displaying or not
	public void selectFirstSchedule() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(firstSchedule, "First Schedule");
		click_button("First Schedule", firstSchedule);
		fixed_wait_time(3);
		waitFor(schedulesTitle, "Schedule Title Bar");

		if(get_field_value("First Schedule", firstSchedule).equalsIgnoreCase(get_field_value("Opened Schedule", schedulesTitle)))
			oReport.AddStepResult("Clicked Schedule ","On 'Fee Schedules' page Clicked on first Schedule and verified that it has opened successfully","PASS");
		else
			oReport.AddStepResult("Clicked Schedule ","On 'Fee Schedules' page Clicked on first Schedule however it has not opened ", "FAIL");
	}

	
	public By addFeeScheduleButton = By.xpath("//div[@class='list-header drillable-header ng-binding'][contains(.,'Fee Schedule')]");

	By addFeeScheduleCodeWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Add Fee Schedule Code']");

	By addScheduleWindow = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add Schedule']");

	public By selectAScheduleDD = By.xpath("//div[@class='workflow  modal-medium']//select[@name='scheduleTypeSet']");

	public By scheduleNameTextBox = By.xpath("//div[@class='workflow  modal-medium']//input[@name='scheduleName']");

	public By addSchedulestartDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='startDateSchedules']");

	public By addScheduleEndDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='stopDateSchedules']");

	By downloadXLSXLink = By.xpath("//form[@id='testFileIframe']//a[text()='Download a sample .XLSX']");

	public By uploadFileButton = By.xpath("//input[@id='feeScheduleEntryIframe'][@name='feeScheduleEntry']");

	public By scheduleSaveButton = By.xpath("//div[@class='workflow  modal-medium']//input[@id='button.saveId']");

	public By tableRow = By.xpath("//*[@id='schedulesTable']/table/thead/tr");

	By tableHCPCSRow = By.xpath("//*[@id='schedulesTable']//th[text()='HCPCS/CPT']");

	By addScheduleEntryLink = By.xpath("//a[@title='Add Schedule Entry']//span[contains(text(),'Add')]");

	By HCPCSTextBox = By.xpath("//div[@id='addScheduleEntry']//input[@id='scheduleCode']");

	By modifiersTextBox = By.xpath("//div[@id='addScheduleEntry']//input[@id='modifier']");

	By amountTextBox = By.xpath("//div[@id='addScheduleEntry']//input[@id='amount']");

	By maxQuantityTextBox = By.xpath("//div[@id='addScheduleEntry']//input[@id='maxQuantity']");

	By maxBasisDD = By.xpath("//div[@class='workflow  modal-medium']//select[@id='maxBasisCode']");

	By carrierTextBox = By.xpath("//div[@id='addScheduleEntry']//input[@id='carrier']");

	By localityTextBox = By.xpath("//div[@id='addScheduleEntry']//input[@id='locality']");

	By disabledSaveButton = By.xpath("//div[@id='addScheduleEntry']//input[@id='button.saveId'][@disabled='disabled']");

	By enabledSaveButton = By.xpath("//div[@id='addScheduleEntry']//input[@id='button.saveId']");

	public By errorWindow = By.xpath("//div[@class='message ng-scope ng-binding'][contains(.,'Please refer to the error.xlsx file downloaded on your machine.')]");

	public By errorWindowOKButton = By.xpath("//input[@class='btn  btn-default'][@value='OK']");

	
	// Add Schedule Window
	public void addFeeScheduleButton() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFeeScheduleButton, "Add Fee Schedule Button");
		click_button("Add Fee Schedule", addFeeScheduleButton);
		waitFor(addScheduleWindow, "Add Schedule Window");

		if(IsElementDisplayed("Add Schedule Window", addScheduleWindow))
			oReport.AddStepResult("Add Schedule Window", "Add Schedule Window Displayed", "PASS");
		else
			oReport.AddStepResult("Add Schedule Window ","Clicked on Add Fee Schedule but Add Fee Schedule window not displayed ", "FAIL");
	}

	
	By noEntriesMessage = By.xpath("//div[text()='No Schedule Entries exist.']");

	
	// Adding Schedule
	public void addANewSchedule(String dropDownValue, String stepName, String excelPath, String excelFileColumns,String autoitPath) 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//waitFor(selectAScheduleDD, "Select a Schedule Type Dropdown");
		select_option("Select A Schedule Drop Down", selectAScheduleDD, dropDownValue); // Drop down Value			

		oReport.AddStepResult(stepName, stepName+" Selected", "PASS"); // Step Name

		enter_text_value("Schedule Name Text Box", scheduleNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("FeeScheduleName", get_field_value("Schedule Name", scheduleNameTextBox));
		facilityName("SCHEDULES", "Apollo srn facility");
		enter_text_value_without_clear("Schedules Start Date", addSchedulestartDate, get_specified_date(-1000));
		performKeyBoardAction("ENTER");
		enter_text_value("Schedule End Date", addScheduleEndDate, get_specified_date(1));
		performKeyBoardAction("ENTER");
		driver.switchTo().frame("ScheduleimportIframe");
		click_button("Download XLSX Link", downloadXLSXLink);
		fixed_wait_time(4);

		int columnCount = oExcelData.getColumnCount(0, excelPath, 1); // excel path

		String[] columnDataArray = new String[columnCount];

		for (int i = 0; i < columnCount; i++) 
		{
			String cellData = ExcelData.getCellData(0, i, 2, excelPath);
			columnDataArray[i] = cellData;
		}

		String columnDataString = String.join(",", columnDataArray).replace(",", " ");

		if(columnDataString.equalsIgnoreCase(excelFileColumns)) // need to pass excel returned data															
			oReport.AddStepResult("Downloaded Excel file ","On 'Add Schedule' window clicked on 'Download a sample .XLSX' link and verified that Excel file contains "+ excelFileColumns + " column name","PASS");
		else
			oReport.AddStepResult("Downloaded Excel File","On 'Add Schedule' window clicked on 'Download a sample .XLSX' link and verified that Excel file contains doesn't contain "+ excelFileColumns + " column name ","FAIL");

		click_button("Upload File Button", uploadFileButton);
		fixed_wait_time(5);

		driver.switchTo().defaultContent();

		try 
		{
			Runtime.getRuntime().exec(autoitPath); // AutoIT Path
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		click_button("Schedule Save Button", scheduleSaveButton);
		fixed_wait_time(6);

		if(IsDisplayed("New Schedule Value", tableHCPCSRow)) 
		{
			waitFor(addFeeScheduleButton, "Add Fee Schedule Button");
			waitFor(tableRow, "Schedule Table");
			oParameters.SetParameters("TableColumns", get_field_value("Table Columns", tableRow));

			if(IsDisplayed("New Schedule Value", tableHCPCSRow))																		
				oReport.AddStepResult("New Schedule","On 'Add Schedule' window entered all mandatory details, clicked on save button and verified that Schedule is added ","PASS");
			else
				oReport.AddStepResult("New Schedule ","On 'Add Schedule' window entered all mandatory details, Selected "+ stepName.replace("Option", "")+ " as a Schedule type and clicked on save button, However new schedule is not added with same values present in imported file","FAIL");
		}
		else if(IsDisplayed("Error Window", errorWindow)) 
		{
			performKeyBoardAction("ESC");
			click_button("Error Window Ok", errorWindowOKButton);
			oReport.AddStepResult("New Schedule","On 'Add Schedule' window entered all mandatory details, Selected " + stepName.replace("Option", "")+ " as a Schedule type and clicked on save button, However new schedule is not added with same values present in imported file","FAIL");
		}
		else if(IsDisplayed("No Entries Message", noEntriesMessage))
			oReport.AddStepResult("New Schedule","On 'Add Schedule' window entered all mandatory details, Selected " + stepName.replace("Option", "")+ " as a Schedule type and clicked on save button, However new schedule is not added with same values present in imported file","FAIL");

		File xlsxFile = new File(excelPath); // Excel Path
		xlsxFile.delete();

		// Adding Schedule Entry with Invalid Values

		click_button("Add Schedule Entry Button", addScheduleEntryLink);
		fixed_wait_time(4);
		waitFor(HCPCSTextBox, "Add Fee Schedule Code window");
		enter_text_value("HCPCS Text Box", HCPCSTextBox, get_random_alphanumeric(3));
		
	//	enter_text_value("Modifiers Text Box", modifiersTextBox, get_random_alphanumeric(3));
		if(IsDisplayed("Modifiers Text Box", modifiersTextBox))
			enter_text_value("Modifiers Text Box", modifiersTextBox, get_random_alphanumeric(3));
		
		enter_text_value("Amount Text Box", amountTextBox, get_random_alphanumeric(3));
		enter_text_value("Max Quantity Text Box", maxQuantityTextBox, get_random_alphanumeric(3));
		select_option("Max Basis Drop Down", maxBasisDD, "0");

		if(IsDisplayed("Carrier Text Box", carrierTextBox))
			enter_text_value("Carrier Text Box", carrierTextBox, get_random_alphanumeric(3));
		else
			System.out.println("No Carrier for This Type Schedule");

		if(IsDisplayed("Locality Text Box", localityTextBox))
			enter_text_value("Locality Text Box", localityTextBox, get_random_alphanumeric(3));
		else
			System.out.println("No Locality for This Type Schedule");

		if(IsDisplayed("Disabled Save Button", disabledSaveButton))
			oReport.AddStepResult("Save Button","On 'Add Fee Schedule Code' window we have entered all invalid data and verified that Save Button is Disabled","PASS");
		else
			oReport.AddStepResult("Save Button ","On 'Add Fee Schedule Code' window we have entered all invalid data and verified that Save Button is Enabled ","FAIL");

		// Adding Schedule Entry with Valid Values

		enter_text_value("HCPCS Text Box", HCPCSTextBox, oParameters.GetParameters("HCPCS/CPT"));
	//	enter_text_value("Modifiers Text Box", modifiersTextBox, oParameters.GetParameters("Modifiers"));
		
		if(IsDisplayed("Modifiers Text Box", modifiersTextBox))
			enter_text_value("Modifiers Text Box", modifiersTextBox, oParameters.GetParameters("Modifiers"));
		
		enter_text_value("Amount Text Box", amountTextBox, oParameters.GetParameters("Amount"));
		enter_text_value("Max Quantity Text Box", maxQuantityTextBox, oParameters.GetParameters("MaxQuantity"));
		select_option("Max Basis Drop Down", maxBasisDD, "0");

		if(IsDisplayed("Carrier Text Box", carrierTextBox))
			enter_text_value("Carrier Text Box", carrierTextBox, oParameters.GetParameters("Carrier"));
		else
			System.out.println("No Carrier for This Type Schedule");

		if(IsDisplayed("Locality Text Box", localityTextBox))
			enter_text_value("Locality Text Box", localityTextBox, oParameters.GetParameters("Locality"));
		else
			System.out.println("No Locality for This Type Schedule");

		click_button("Save Button", enabledSaveButton);

		By HCPCSValue = By.xpath("//td[@ng-click='editCodeEntry(item)'][text()='" + oParameters.GetParameters("HCPCS/CPT") + "']");

		waitFor(HCPCSValue, "Schedule Table");
		oParameters.SetParameters("NewScheduleHCPCSValue", get_field_value("HCPCS Value", HCPCSValue));
		mouse_hover("New Schedule", HCPCSValue);

		if(oParameters.GetParameters("NewScheduleHCPCSValue").equals(oParameters.GetParameters("HCPCS/CPT")))
		{
			waitFor(HCPCSValue, "Schedule Table");
			oReport.AddStepResult("New Schedule", "New Schedule code is added to that specific Schedule ", "PASS");
		}
		else
			oReport.AddStepResult("New Schedule ","Clicked on Add Schedule entry link, entered valid data in all fields and clicked on save, however new schedule code is not added ","FAIL");
	}

	
	By unsavedMessageWindow = By.xpath("//div[contains(text(),'You have unsaved changes')]");

	public By scheduleCancelButton = By.xpath("//div[@class='workflow  modal-medium']//input[@id='button.cancelId']");

	By unsavedWindowCancelButton = By.xpath("//div[@class='dialog ng-scope']//input[@class='btn btn-default']");

	public By unsavedWindowDiscard = By.xpath("//div[@class='form-button-panel']//input[@class='btn btn-danger discard-red']");

	By editScheduleLink = By.xpath("//a[@title='Edit Schedules']//span[contains(text(),'Edit')]");

	By editScheduleWindow = By.xpath("//workflow-modal[@id='addScheduleModal']//div[@title='Edit Schedule']");

	By editScheduleWindowSave = By.xpath("//workflow-modal[@id='addScheduleModal']//input[@id='button.saveId']");
	
	By editScheduleWindowCancel = By.xpath("//workflow-modal[@id='addScheduleModal']//input[@id='button.cancelId']");

	By periodDropDown = By.xpath("//div[@id='scheduleView']//div[@id='period-selector']/a");

	By addPeriodLink = By.xpath("//div[@class='portal-action-bar']//div[@id='period-selector']//li[1]");

	By periodDate = By.xpath("//div[@class='portal-action-bar']//div[@id='period-selector']//li[2]");

	By periodDateDeleteIcon = By.xpath("//div[@class='portal-action-bar']//div[@id='period-selector']//li[2]//i[@class='fa fa-fw fa-minus-square pull-right show-on-hover period-minus']");

	By periodDateEditIcon = By.xpath("//div[@class='portal-action-bar']//div[@id='period-selector']//li[2]//i[@class='fa fa-fw fa-pencil show-on-hover']");

	By periodDateEditWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Edit Effective Period']");

	By periodEditStartDate = By.xpath("//input[@id='startDateSchedulesPeriod']");

	By periodEditEndDate = By.xpath("//*[@id='stopDateSchedulesPeriod']");

	By periodEditSaveButton = By.xpath("//div[@class='workflow  modal-medium']//input[@id='button.saveId']");

	By addEffectivePeriodWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Add Effective Period']");

	By deletePeriodWindow = By.xpath("//div[@class='medium-header bold ng-binding'][contains(text(),'Delete Period')]");

	By deletePeriod = By.xpath("//input[@class='btn btn-danger'][@value='Delete']");

	By invalidCodePopUp = By.xpath("//div[@class='msg-area']/div[@class='medium-header bold ng-binding']");

	By invalidCodeCancel = By.xpath("//div[@id='dialog_buttons']/input[@class='btn btn-primary'][@value='Cancel']");

	
	// Add Fee Schedule Window Validation
	public void addScheduleCancelScenario() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Schedule Name Text Box", scheduleNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		click_button("Cancel Button", scheduleCancelButton);

		if(IsDisplayed("Unsaved Changes Message Window", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved Changes pop up","On 'Add Schedule' window entered data, Clicked on cancel button verified that Unsaved Changes pop up is Displayed","PASS");
		else
			oReport.AddStepResult("Unsaved Changes pop up","On 'Add Schedule' window entered data, Clicked on cancel button verified that Unsaved Changes pop up is not Displayed","FAIL");

		// Clicking on Cancel

		click_button("Unsaved Changes Window Cancel Button", unsavedWindowCancelButton);

		if(IsDisplayed("Add Schedule Window", addScheduleWindow))
			oReport.AddStepResult("Pop Up Cancel ","On 'You have unsaved changes' popup clicked on cancel button and verified that popup is closed and Add Schedule Window Displayed","PASS");
		else
			oReport.AddStepResult("Pop Up Cancel ","On 'You have unsaved changes' popup clicked on cancel button and verified that popup is not closed and Add Schedule Window not Displayed","FAIL");

		// Entering Date

		enter_text_value_without_clear("Schedules Start Date", addSchedulestartDate, get_specified_date(-10));
		performKeyBoardAction("ENTER");
		click_button("Cancel Button", scheduleCancelButton);

		if(IsDisplayed("Unsaved Changes Message Window", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved Changes pop up","On 'Add Schedule' window entered data, Clicked on cancel button verified that Unsaved Changes pop up is Displayed","PASS");
		else
			oReport.AddStepResult("Unsaved Changes pop up ","On 'Add Schedule' window entered data, Clicked on cancel button verified that Unsaved Changes pop up is not Displayed","FAIL");

		// Discard Schedule

		click_button("Discard Button", unsavedWindowDiscard);

		if(IsDisplayed("Add Schedule Entry", addScheduleEntryLink))
			oReport.AddStepResult("Add Schedule Window","On 'You have unsaved changes' popup clicked on discard button and verified that popup is closed and Add Schedule Window is Closed","PASS");
		else
			oReport.AddStepResult("Add Schedule Window","On 'You have unsaved changes' popup clicked on discard button and verified that popup is not closed and Add Schedule Window is not Closed","FAIL");
	}

	
	// Editing Schedule
	public void editFeeScheduleName() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit Schedule Button", editScheduleLink);

		if(IsDisplayed("Edit Schedule Window", editScheduleWindow))
		{	
			oReport.AddStepResult("Edit Schedules Window","On 'Fee Schedule' page clicked on edit button, Verified that Edit Schedule Window Displayed","PASS");

			// Editing Schedule Name

			enter_text_value_without_clear("Schedule Name Text Box", scheduleNameTextBox,oParameters.GetParameters("EditScheduleName"));
			oParameters.SetParameters("EditedSchedule", get_field_value("Edited Schedule Name", scheduleNameTextBox));
			click_button("Edit Schedule Save Button", editScheduleWindowSave);
			waitFor(schedulesTitle, "Schedule Title Bar");
			oParameters.SetParameters("EditedScheduleTitle", get_field_value("Edited Schedule Title", schedulesTitle));

			if(oParameters.GetParameters("EditedSchedule").equals(oParameters.GetParameters("EditedScheduleTitle")))
			{
				waitFor(schedulesTitle, "Schedule Title Bar");
				oReport.AddStepResult("Schedule Name","Modified Schedule name and verified that changes are appeared on portal ", "PASS");
			}
			else 
			{
				waitFor(schedulesTitle, "Schedule Title Bar");
				oReport.AddStepResult("Schedule Name","Modified Schedule name, However changes are not appeared on portal ", "FAIL");
			}
		}
		else
		{	
			oReport.AddStepResult("Edit Schedules Window","On 'Fee Schedule' page clicked on edit button, Verified that Edit Schedule Window is not Displayed","FAIL");
			
			click_button("Edit Schedule Window Cancel", editScheduleWindowCancel);
		}	
	}

	
	By addPeriodScheduleDD = By.xpath("//select[@id='scheduleType']");

	
	// Period Drop down validation
	public void editFeeSchedulesPeriod() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(4);
		click_button("Period Drop Down", periodDropDown);

		if(IsDisplayed("Add Period Option", addPeriodLink) && IsDisplayed("Given Effective Date", periodDate))
			oReport.AddStepResult("Add Period Option and Date",	"On 'Fee Schedules' page clicked on period dropdown and verified Add Period Option is Displayed","PASS");
		else
			oReport.AddStepResult("Period Drop Down ","On 'Fee Schedules' page clicked on period dropdown, However given effective date and Add Period option is not Displayed ","FAIL");

		// Period Date Edit and Delete Icon

		mouse_hover("Period Date", periodDate);

		if (IsDisplayed("Edit Icon", periodDateEditIcon)&& IsDisplayed("Delete Icon", periodDateDeleteIcon))
			oReport.AddStepResult("Period Date Edit and Delete Icon","On 'Fee Schedules' page clicked on period dropdown, mouse hovered over existing period and verified that Period Edit and Delete icons are Displayed","PASS");
		else
			oReport.AddStepResult("Edit and Delete Icon ","On 'Fee Schedules' page clicked on period dropdown, mouse hovered over existing period and verified that Period Edit and Delete icons are not Displayed","FAIL");

		// Edit Effective Date Window

		click_button("Period Edit Icon", periodDateEditIcon);

		if(IsDisplayed("Period Edit Window", periodDateEditWindow))
			oReport.AddStepResult("Edit Effective Period window","Clicked on period edit button and verified that 'Edit Effective Period' window is displayed","PASS");
		else
			oReport.AddStepResult("Edit Effective Period window ","Clicked on period edit button and verified that 'Edit Effective Period' window is not displayed","FAIL");

		// Editing Period Date

		oParameters.SetParameters("PeriodEndDate", get_field_value("Period Termination Date", periodEditEndDate));
		enter_text_value("Edit Period Start Date", periodEditStartDate, get_specified_date(-110));// oParameters.GetParameters("PeriodEndDate"));
		performKeyBoardAction("ENTER");
		click_button("Edit Window Save Button", periodEditSaveButton);
		
		if(IsDisplayed("Edit Effective Period Window", periodDateEditWindow))
		{
			fixed_wait_time(4);
			waitFor(periodDropDown, "Schedule period dropdown");
		}	
		else if(!IsDisplayed("Edit Effective Period Window", periodDateEditWindow))
			if (IsDisplayed("Modified Date", periodDropDown))
				oReport.AddStepResult("Modified Period Date","On 'Edit Effective Period' window entered new Date, clicked on save and verified that new date reflected on portal without any error ","PASS");
			else
				oReport.AddStepResult("Modified Period Date ","On 'Edit Effective Period' window entered new Date, clicked on save and verified that new date is not reflected on portal ","FAIL");
	}

	
	// Add new period Window
	public void feeSchedulesNewPeriod() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Period Drop Down", periodDropDown);
		click_button("Add Period Option", addPeriodLink);

		if(IsDisplayed("Add Effective Period Window", addEffectivePeriodWindow))
			oReport.AddStepResult("Add Effective Period Window","On 'Fee Schedules' page clicked on period dropdown and clicked on 'Add Period' verified that 'Add Effective Period' Window is displayed","PASS");
		else
			oReport.AddStepResult("Add Effective Period Window ","On 'Fee Schedules' page clicked on period dropdown and clicked on 'Add Period' verified that 'Add Effective Period' Window is not displayed","FAIL");

		// Adding New Period

		enter_text_value("Edit Period Start Date", periodEditStartDate, get_specified_date(2));// get_next_date(oParameters.GetParameters("PeriodEndDate"),2));
		enter_text_value("Edit Period Start Date", periodEditEndDate, get_specified_date(3));// get_next_date(oParameters.GetParameters("PeriodEndDate"),3));
		performKeyBoardAction("ENTER");
		select_option("Select A Schedule Drop Down", addPeriodScheduleDD, "2");
		click_button("Add Period Window Save", periodEditSaveButton);
		fixed_wait_time(5);
		click_button("Period Drop Down", periodDropDown);
		mouse_hover("New Period Date", periodDate);

		if(IsDisplayed("Period Date", periodDate))
			oReport.AddStepResult("New Period to Respective schedule ","Clicked on 'Add Effective Period' Window save button, verified that New Period is added to Respective schedule ","PASS");
		else
			oReport.AddStepResult("New Period to Respective schedule ","Filled all the mandatory fields and clicked on save but that new period is not added for the respective schedule type","FAIL");

		// Deleting existing Period

		mouse_hover("Period Date", periodDate);
		click_button("Period Date Delete Icon", periodDateDeleteIcon);

		if(IsDisplayed("Delete Period Notification Window", deletePeriodWindow))
			oReport.AddStepResult("Delete Period Pop up", "Delete Period Pop up Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Period Pop up","Clicked on period drop down, hovered over existing period and click on delete icon but that delete period pop up is not displayed ","FAIL");

		click_button("Delete Period", deletePeriod);
		fixed_wait_time(5);
		click_button("Period Drop Down", periodDropDown);

		if(IsDisplayed("Period Date", periodDate))
			oReport.AddStepResult("Existing Period ", "Existing Period Deleted", "PASS");
		else
			oReport.AddStepResult("Deleting Period ","Clicked on Delete Existing Period but that period Is not Deleted", "FAIL");
	}

	
	// Clicking on Add a schedule entry link
	public void creatingNewFeeSchedule() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add a Schedule Entry Link", addScheduleEntryLink);

		if(IsDisplayed("Add Fee Schedule Window", addFeeScheduleCodeWindow))
			oReport.AddStepResult("Add Fee Schedule Code window", "Add Fee Schedule Code window Displayed", "PASS");
		else
			oReport.AddStepResult("Add Fee Schedule Code window","Clicked on Add a schedule entry link but that Add Fee Schedule Code window is not displayed ","FAIL");

		enter_text_value("HCPCS Text Box", HCPCSTextBox, get_random_alphanumeric(3));

		oReport.AddStepResult("Alpha numeric characters for HCPCS field","Alpha numeric characters for HCPCS field Accepted", "PASS");

		enter_text_value("Amount Text Box", amountTextBox, oParameters.GetParameters("Amount"));
		click_button("Save Fee Schedule", enabledSaveButton);

		if(IsDisplayed("Invalid Code Pop up message", invalidCodePopUp))
			oReport.AddStepResult("Invalid Code Pop up Message", "Invalid Code Pop up Message Displayed", "PASS");
		else
			oReport.AddStepResult("Invalid Code Pop up Message ","Given random values under HCPCS Code and clicked on save but that Invalid code pop up message is not displayed","FAIL");

		click_button("Cancel Invalid Code Pop up", invalidCodeCancel);
		enter_text_value("HCPCS Text Box", HCPCSTextBox, oParameters.GetParameters("HCPCS/CPT1"));
		enter_text_value("Amount Text Box", amountTextBox, oParameters.GetParameters("Amount"));
		click_button("Save Fee Schedule", enabledSaveButton);

		By HCPCSValue = By.xpath("//td[@ng-click='editCodeEntry(item)'][text()='" + oParameters.GetParameters("HCPCS/CPT1") + "']");

		waitFor(HCPCSValue, "Schedule Table");
		oParameters.SetParameters("NewScheduleHCPCSValue", get_field_value("HCPCS Value", HCPCSValue));
		mouse_hover("New Schedule", HCPCSValue);

		if(oParameters.GetParameters("NewScheduleHCPCSValue").equals(oParameters.GetParameters("HCPCS/CPT1")))
			oReport.AddStepResult("New Schedule Code", "New Schedule Code Added", "PASS");
		else
			oReport.AddStepResult("New Schedule Code ","Filled all the mandatory fields to add schedule entry and clicked on save but that new schedule code is not added","FAIL");
	}

	
	By addFilterElement = By.xpath("//*[@id='scheduleView']//span[text()='Add Filter']");

	By filtersDropDown = By.xpath("//*[@id='scheduleView']//a[@class='filter-label hand-cursor pull-left ng-scope']");

	By filtersTextBox = By.xpath("//*[@id='scheduleView']//input[@class='text']");

	By filtersCloseButton = By.xpath("//*[@id='filter-schedules']//a[@title='Clear All Filters']/i[@class='left fa-lg fa fa-times-circle']");

	By amountColumn = By.xpath("//div[@id='schedulesTable']//tr[1]/td[4]");

	By amountElement = By.xpath("//li/a[contains(text(),'Amount')]");
	
	
	public void addFilter(By filtersElement,String elementName)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add Filter", addFilterElement);
		fixed_wait_time(2);
		click_button("Filters Drop Down", filtersDropDown);
		fixed_wait_time(2);
		click_button(elementName, filtersElement);	
	}

	// Filter Reports
	// Amount Filter Reports
	public void amountReports() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Schedule Search", scheduleSearch, oParameters.GetParameters("ScheduleForFilters"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		click_button("First Schedule", firstSchedule);
		waitFor(amountColumn, "Schedule Table");
		oParameters.SetParameters("AmountBeforeFilter", get_field_value("Amount Column", amountColumn));
		fixed_wait_time(2);
		addFilter(amountElement,"Amount");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("AmountBeforeFilter"));
		performKeyBoardAction("ENTER");
		waitFor(amountColumn, "Schedule Table");
		oParameters.SetParameters("AmountAfterFilter", get_field_value("Amount Column", amountColumn));

		if(oParameters.GetParameters("AmountBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("AmountAfterFilter")))
			oReport.AddStepResult("Amount Filter Reports","Schedule Code Filtered Based on Amount those Reports are Displayed", "PASS");
		else
			oReport.AddStepResult("Amount Filter Reports","Schedule Code Filtered Based on Amount but those Reports are Not Displayed", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By baseUnitElement = By.xpath("//li/a[contains(text(),'Base Unit')]");

	By baseUnitColumn = By.xpath("//div[@id='schedulesTable']//tr[4]/td[6]");

	
	// Base Unit Filter Reports
	public void baseUnitReports() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("BaseUnitBeforeFilter", get_field_value("Base Unit Column", baseUnitColumn));
		addFilter(baseUnitElement,"Base Unit");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("BaseUnitBeforeFilter"));
		performKeyBoardAction("ENTER");

		By baseUnitColumn = By.xpath("//div[@id='schedulesTable']//tr[1]/td[6]");

		waitFor(baseUnitColumn, "Schedule Table");
		oParameters.SetParameters("BaseUnitAfterFilter", get_field_value("Base Unit Column", baseUnitColumn));

		if(oParameters.GetParameters("BaseUnitBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("BaseUnitAfterFilter")))
			oReport.AddStepResult("Base Unit Filter Reports ","Schedule Code Filtered Based on Base Unit those Reports are Displayed", "PASS");
		else
			oReport.AddStepResult("Base Unit Filter Reports","Schedule Code Filtered Based on Base Unit but those Reports are Not Displayed ", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By hcpcsColumn = By.xpath("//div[@id='schedulesTable']//tr[1]/td[2]");

	By hcpcsElement = By.xpath("//li/a[contains(text(),'HCPCS/CPT')]");

	
	// HCPCS/CPT Filter Reports
	public void HCPCSReports() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("HCPCSBeforeFilter", get_field_value("HCPCS Column", hcpcsColumn));
		addFilter(hcpcsElement,"HCPCS/CPT");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("HCPCSBeforeFilter"));
		performKeyBoardAction("ENTER");
		waitFor(hcpcsColumn, "Schedule Table");
		oParameters.SetParameters("HCPCSAfterFilter", get_field_value("HCPCS Column", hcpcsColumn));

		if(oParameters.GetParameters("HCPCSBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("HCPCSAfterFilter")))
			oReport.AddStepResult("HCPCS/CPT Filter Reports","Schedule Code Based on HCPCS/CPT Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("HCPCS/CPT Filter Reports","Schedule Code Filtered Based on HCPCS/CPT but those Reports are Not Displayed ", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}
	

	By maxBasisColumn = By.xpath("//div[@id='schedulesTable']//tr[1]/td[8]");

	By maxBasisElement = By.xpath("//li/a[contains(text(),'Max Basis')]");

	
	// Max Basis Filter Reports
	public void maxBasisReports() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("MaxBasisBeforeFilter", get_field_value("Max Basis Column", maxBasisColumn));
		addFilter(maxBasisElement,"Max Basis");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("MaxBasisBeforeFilter"));
		performKeyBoardAction("ENTER");
		waitFor(maxBasisColumn, "Schedule Table");
		oParameters.SetParameters("MaxBasisAfterFilter", get_field_value("Max Basis Column", maxBasisColumn));

		if(oParameters.GetParameters("MaxBasisBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("MaxBasisAfterFilter")))
			oReport.AddStepResult("Max Basis Filter Reports ","Schedule Code Based on Max Basis Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Max Basis Filter Reports ","Schedule Code Filtered Based on Max Basis but those Reports are Not Displayed ", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By maxQuantityColumn = By.xpath("//div[@id='schedulesTable']//tr[1]/td[7]");

	By maxQuantityElement = By.xpath("//li/a[contains(text(),'Max Quantity')]");

	
	// Max Quantity Filter Reports
	public void maxQuantityReports() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("MaxQuantityBeforeFilter", get_field_value("Max Quantity Column", maxQuantityColumn));
		addFilter(maxQuantityElement,"Max Quantity");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("MaxQuantityBeforeFilter"));
		performKeyBoardAction("ENTER");
		waitFor(maxQuantityColumn, "Schedule Table");
		oParameters.SetParameters("MaxQuantityAfterFilter", get_field_value("Max Quantity Column", maxQuantityColumn));

		if(oParameters.GetParameters("MaxQuantityBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("MaxQuantityAfterFilter")))
			oReport.AddStepResult("Max Quantity Filter Reports","Schedule Code Based on Max Quantity Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Max Quantity Filter Reports","Schedule Code Filtered Based on Max Quantity but those Reports are Not Displayed ", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By modifierColumn = By.xpath("//div[@id='schedulesTable']//tr[1]/td[3]");

	By modifierElement = By.xpath("//li/a[contains(text(),'Modifier')]");

	
	// Modifier Filter Reports
	public void modifierReports() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("ModifierBeforeFilter", get_field_value("Modifier Column", modifierColumn));
		addFilter(modifierElement,"Modifier");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("ModifierBeforeFilter"));
		performKeyBoardAction("ENTER");
		waitFor(modifierColumn, "Schedule Table");
		oParameters.SetParameters("ModifierAfterFilter", get_field_value("Modifier Column", modifierColumn));

		if(oParameters.GetParameters("ModifierBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ModifierAfterFilter")))
			oReport.AddStepResult("Modifier Filter Reports ","Schedule Code Based on Modifier Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Modifier Filter Reports ","Schedule Code Filtered Based on Modifier but those Reports are Not Displayed ", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By rvuColumn = By.xpath("//div[@id='schedulesTable']//tr[2]/td[5]");

	By rvuElement = By.xpath("//li/a[contains(text(),'RVU')]");

	
	// RVU Filter Reports
	public void RVUReports() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("RVUBeforeFilter", get_field_value("RVU Column", rvuColumn));
		addFilter(rvuElement,"RVU");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("RVUBeforeFilter"));
		performKeyBoardAction("ENTER");

		By rvuColumn = By.xpath("//div[@id='schedulesTable']//tr[1]/td[5]");

		waitFor(rvuColumn, "Schedule Table");
		oParameters.SetParameters("RVUAfterFilter", get_field_value("RVU Column", rvuColumn));

		if(oParameters.GetParameters("RVUBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("RVUAfterFilter")))
			oReport.AddStepResult("RVU Filter Reports ", "Schedule Code Based on RVU Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("RVU Filter Reports ","Schedule Code Filtered Based on RVU but those Reports are Not Displayed ", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}
	

	By scheduleFirstRow = By.xpath("//tr[1]//td[2][@ng-click='editCodeEntry(item)']");

	By scheduleEditTab = By.xpath("//*[@id='addEditSingleCode']//div[@class='medium-header truncate ng-binding']");

	By scheduleEditTabMaxQuantity = By.xpath("//*[@id='addEditSingleCode']//input[@id='maxQuantity']");

	By scheduleEditMaxBasis = By.xpath("//*[@id='addEditSingleCode']//select[@id='maxBasisCode']");

	By scheduleEditTabSave = By.xpath("//*[@id='addEditSingleCode']//button[@id='button.saveId']");

	By scheduleEditTabDelete = By.xpath("//span[@class='lnk-btn-txt ng-binding'][text()='Delete Code']");

	By schedulePopupDelete = By.xpath("//div[@id='dialog']//div[@id='dialog_buttons']/input[@value='Delete']");

	By scheduleDeleteIcon = By.xpath("//div[@id='schedulesTable']//tr[1]//a[@class='position-absolute link-btn hand-cursor ng-isolate-scope']/i[1]");

	By scheduleCheckBox = By.xpath("//tr[1]//td[1]//input[@type='checkbox']");

	By deleteEntriesButton = By.xpath("//*[@id='multipleSchedulesActionBar']//span[text()='Delete Entries']");

	By deselectAllButton = By.xpath("//*[@id='multipleSchedulesActionBar']//input[@value='Deselect all']");

	By deleteScheduleCodePopup = By.xpath("//div[@class='medium-header bold ng-binding']");

	By deleteScheduleCodeCancel = By.xpath("//div[@id='dialog']//div[@class='form-button-panel']//input[@value='Cancel']");

	By deleteScheduleCodeDelete = By.xpath("//div[@id='dialog']//div[@class='form-button-panel']//input[@value='Delete']");

	
	// Created Schedule validation
	public void scheduleValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Schedule Search", scheduleSearch, oParameters.GetParameters("FeeScheduleName"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(4);
		click_button("First Schedule", firstSchedule);
		
		waitFor(scheduleFirstRow, "Schedule Table");
		oParameters.SetParameters("FirstRowScheduleCode", get_field_value("HCPCS Value", scheduleFirstRow));
		fixed_wait_time(2);
		
		click_button("Schedule Code", scheduleFirstRow);
		
		if(IsDisplayed("Schedule Edit Tab", scheduleEditTab))
			oReport.AddStepResult("Schedule Edit Tab Window", "Schedule Edit Tab Window Displayed", "PASS");
		else
			oReport.AddStepResult("Schedule Edit Tab Window","Clicked on created fee schedule code but edit tab is not displayed on right side of the screen","FAIL");

		// Changing Max Quantity and Max Basis

		enter_text_value("Schedule Edit Tab Max Quantity Text Box", scheduleEditTabMaxQuantity,oParameters.GetParameters("MaxQuantity"));
		select_option("Max Basis Drop Down", scheduleEditMaxBasis, "1");
		click_button("Schedule Edit Tab Save", scheduleEditTabSave);

		By HCPCSValue = By.xpath("//td[@ng-click='editCodeEntry(item)'][text()='"+ oParameters.GetParameters("FirstRowScheduleCode") + "']");

		if(IsDisplayed("Edited Schedule Code", HCPCSValue))
			oReport.AddStepResult("Edited Schedule Code", "Edited Schedule Code Displayed", "PASS");
		else
			oReport.AddStepResult("Edited Schedule Code","Modified the Max Quantity/Max Basis and clicked on save but that schedule code is not modified","FAIL");

		// Again Clicking on Fee Schedule Code

		click_button("Schedule Code", scheduleFirstRow);
		//waitFor(scheduleEditTab, "Schedule code Edit Window");

		if(IsDisplayed("Schedule Edit Tab", scheduleEditTab))
			oReport.AddStepResult("Schedule Edit Tab Window", "Schedule Edit Tab Window Displayed", "PASS");
		else
			oReport.AddStepResult("Schedule Edit Tab Window","Clicked on created fee schedule code but edit tab is not displayed on right side of the screen","FAIL");

		// Deleting Schedule

		click_button("Delete Schedule Option", scheduleEditTabDelete);
		click_button("Schedule Delete", schedulePopupDelete);

		if(IsDisplayed("Schedule Table", scheduleFirstRow))
			oReport.AddStepResult("Fee Schedule Code", "Fee Schedule Code Deleted", "PASS");
		else
			oReport.AddStepResult("Delete Schedule Code ","Clicked on delete code but that delete fee schedule code is not displayed ", "FAIL");

		// Hovering to Fee Schedule Code

		mouse_hover("Schedule Code", scheduleFirstRow);

		if(IsDisplayed("Schedule Delete Icon", scheduleDeleteIcon))
			oReport.AddStepResult("Schedule Code Delete Icon", "Schedule Code Delete Icon Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Icon ", "Mouse Hovered To Schedule Code But Delete Icon is Not Displayed","FAIL");

		// Clicking on Schedule Code CheckBox

		click_button("Schedule Code Check Box", scheduleCheckBox);

		if(IsDisplayed("Delete Entries Button", deleteEntriesButton) && IsDisplayed("Deleselect All Option", deselectAllButton))
			oReport.AddStepResult("Deselect All and Delete Entries Buttons","Deselect All and Delete Entries Buttons Displayed", "PASS");
		else
			oReport.AddStepResult("Deselect All and Delete Entries Buttons","Hovered over the code and clicked on checkbox but that Delete Entries and Deselect all buttons are not displayed","FAIL");

		// Clicking on Deselect All

		click_button("Deselect All Option", deselectAllButton);

		if(IsDisplayed("Checked Check Box", scheduleCheckBox))
			oReport.AddStepResult("Deselect All ","Clicked on Deselect all button but still that all selected codes are checked", "FAIL");
		else
			oReport.AddStepResult("Selected Code CheckBox", "Selected Code CheckBox is Unchecked", "PASS");

		// Again Hovering and Deleting Schedule Code

		mouse_hover("Schedule Code", scheduleFirstRow);
		click_button("Schedule Code Check Box", scheduleCheckBox);
		click_button("Schedule Delete Entries", deleteEntriesButton);

		if(IsDisplayed("Delete Schedule Code Pop up", deleteScheduleCodePopup))
			oReport.AddStepResult("Delete Fee Schedule Code pop up is", "Delete Fee Schedule Code pop up is Displayed","PASS");
		else
			oReport.AddStepResult("Delete Fee Schedule Code pop up ","Hovered over the code, clicked on checkbox and Clicked on Delete Entries but that Delete Fee Schedule Code pop up is not displayed ","FAIL");

		// Clicking On Cancel

		click_button("Delete Schedule Code Cancel Button", deleteScheduleCodeCancel);

		if(IsDisplayed("Delete Schedule Popup", deleteScheduleCodePopup))
			oReport.AddStepResult("Delete Schedule Popup ","Clicked on Delete Fee Schedule Code pop up cancel button but that delete pop up is not closed","FAIL");
		else
			oReport.AddStepResult("Delete Schedule Popup is", "Delete Schedule Popup is Closed", "PASS");

		// Again clicking on Delete Entries

		click_button("Schedule Delete Entries", deleteEntriesButton);
		click_button("Schedule Delete", deleteScheduleCodeDelete);

		if(IsDisplayed("Schedule Codes", scheduleFirstRow))
			oReport.AddStepResult("Selected Schedule Codes are", "Selected Schedule Codes are Deleted", "PASS");
		else
			oReport.AddStepResult("Deleting Entries ","Clicked on Delete Entries and clicked on delete but that selected codes are not deleted ", "FAIL");
	}
	

	By exportSchedule = By.xpath("//div[@class='col-lg-10 col-md-10 col-sm-10 col-xs-8 pad-l-0 pad-r-0 large-height']//span[contains(text(),'Export')]");

	By importSchedule = By.xpath("//div[@class='col-lg-10 col-md-10 col-sm-10 col-xs-8 pad-l-0 pad-r-0 large-height']//span[contains(text(),'Import')]");

	By importScheduleWindow = By.xpath("//div[@title='Import Fee Schedule Entries']");

	By downloadSampleXLSX = By.xpath("//*[@id='sampleLink']");

	By importScheduleSave = By.xpath("//input[@id='importScheduleId']");

	By chooseFileButton = By.xpath("//div[@class='scrollable-content']//input[@id='feeScheduleEntryIframe'][@name='feeScheduleEntry']");

	
	// Schedule export and importing
	public void scheduleExportingImporting() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("ScheduleTitle", get_field_value("Schedule Name", schedulesTitle));
		oParameters.SetParameters("TableColumns", get_field_value("Table Columns", tableRow));
		click_button("Schedule Export", exportSchedule);
		fixed_wait_time(4);

		int columnCount = oExcelData.getColumnCount(0,"C:\\CCM\\Downloads\\" + oParameters.GetParameters("ScheduleTitle") + ".xlsx", 1);

		String[] columnDataArray = new String[columnCount];

		for (int i = 0; i < columnCount; i++) 
		{
			String cellData = ExcelData.getCellData(0, i, 2,"C:\\CCM\\Downloads\\" + oParameters.GetParameters("ScheduleTitle") + ".xlsx");
			columnDataArray[i] = cellData;
		}

		String columnDataString = String.join(",", columnDataArray).replace(",", " ");

		if(columnDataString.contains(oParameters.GetParameters("HCPCS/CPTValue")))
			oReport.AddStepResult("Export File ","Excel File Downloaded and All the Codes Under Fee Schedule are Displayed", "PASS");
		else
			oReport.AddStepResult("Export File ","Clicked on Export link but that all the codes displayed under fee schedule those codes are not downloaded","FAIL");

		// Clicking on Import Link

		click_button("Import Schedule", importSchedule);
		
		if(IsDisplayed("Import Schedule Window", importScheduleWindow))
			oReport.AddStepResult("Import Fee Schedule Entries window ","Import Fee Schedule Entries window is Displayed", "PASS");
		else
			oReport.AddStepResult("Import Fee Schedule Entries window ","Clicked on Import link but that Import Fee Schedule Entries window is not displayed", "FAIL");

		click_button("Download Sample XLSX", downloadSampleXLSX);
		fixed_wait_time(4);

		int columnCount1 = oExcelData.getColumnCount(0, "C:\\CCM\\Downloads\\sampleSchedule.xlsx", 1);

		String[] columnDataArray1 = new String[columnCount1];

		for (int i = 0; i < columnCount1; i++) 
		{
			String cellData = ExcelData.getCellData(0, i, 2, "C:\\CCM\\Downloads\\sampleSchedule.xlsx");
			columnDataArray1[i] = cellData;
		}

		String columnDataString1 = String.join(",", columnDataArray).replace(",", " ");

		if(columnDataString1.contains(oParameters.GetParameters("HCPCS/CPTValue")))
			oReport.AddStepResult("Downloaded Excel File and Columns", "Downloaded Excel File and Columns Displayed","PASS");
		else
			oReport.AddStepResult("Downloaded Excel File","Clicked on download but that Excel file not Downloaded and that Schedule Columns not Displayed","FAIL");

		click_button("Choose File Button", chooseFileButton);
		fixed_wait_time(4);

		try 
		{
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\CCM-VR-Soarian – Schedules – Fee Schedules-sampleSchedule.exe");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		click_button("Schedule Save Button", importScheduleSave);
		fixed_wait_time(5);

		if(IsDisplayed("Error Window", errorWindow)) 
		{
			performKeyBoardAction("ESC");
			click_button("Error Window Ok", errorWindowOKButton);
			oReport.AddStepResult("New Schedule ","Clicked on choose file button and selected appropriate file then clicked on save button but that new schedule is not added with same values present in imported file","FAIL");
		}
		else if(IsDisplayed("No Entries Message", noEntriesMessage))
			oReport.AddStepResult("New Schedule","Clicked on choose file button and selected appropriate file then clicked on save button but that new schedule is not added with same values present in imported file","FAIL");
		else 
		{
			waitFor(addFeeScheduleButton, "Add Fee Schedule Button");
			oParameters.SetParameters("TableColumns", get_field_value("Table Columns", tableRow));

			if(columnDataString1.contains(oParameters.GetParameters("sampleSchedule")))
				oReport.AddStepResult("New Schedule", "New Schedule With Same Values Displayed", "PASS");
			else
				oReport.AddStepResult("New Schedule","Clicked on choose file button and selected appropriate file then clicked on save button but that new schedule is not added with same values present in imported file","FAIL");
		}
		File xlsxFile = new File("C:\\CCM\\Downloads\\sampleSchedule.xlsx");
		xlsxFile.delete();
	}

	
	// Importing Invalid File
	public void invalidFileImporting() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Import Schedule", importSchedule);

		click_button("Choose File Button", chooseFileButton);
		fixed_wait_time(4);

		try 
		{
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Schedules_FeeSchedules_CCMLibrary.exe");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		click_button("Schedule Save Button", importScheduleSave);

		if(IsDisplayed("Error Window", errorWindow))
			oReport.AddStepResult("Unable to upload pop message is ", "Unable to upload pop message is  Displayed","PASS");
		else
			oReport.AddStepResult("Unable to upload pop ","Clicked on Import link, Clicked on Choose file button and selected invalid file but unable to upload pop message is not displayed and error file is not downloaded","FAIL");

		click_button("Error Window Ok", errorWindowOKButton);
	}
	

	By copyDetailsCheckBox = By.xpath("//workflow-modal[@id='addScheduleModal']//input[@id='copyClosed_addEditSchedule']");

	By scheduleSearchBox = By.xpath("//input[@id='scheduleSearchSet']");

	By searchSchedulePeriodDD = By.xpath("//select[@id='copyPeriodSelectSet']");

	
	// Add Fee Schedule Window validation
	public void addFeeScheduleWindow() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFeeScheduleButton();
		select_option("Select A Schedule Drop Down", selectAScheduleDD,oParameters.GetParameters("ScheduleDropDownOneValue"));

		enter_text_value("Schedule Name Text Box", scheduleNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		enter_text_value_without_clear("Schedules Start Date", addSchedulestartDate, get_specified_date(0));
		performKeyBoardAction("ENTER");

		click_button("Copy Details Check Box", copyDetailsCheckBox);
		fixed_wait_time(2);

		if(IsDisplayed("Schedule Search Box", scheduleSearchBox))
			oReport.AddStepResult("Schedule Search box", "Existing Selected Fee Schedule search box is Displayed","PASS");
		else
			oReport.AddStepResult("Schedule Search box","Copy Details Check Box Clicked But existing Selected Fee Schedule search box is Not Displayed","FAIL");

		enter_text_value("Schedule Search Box", scheduleSearchBox, oParameters.GetParameters("ScheduleForFilters"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);

		if(IsDisplayed("Search Schedule Period DropDown", searchSchedulePeriodDD))
			oReport.AddStepResult("Period Drop Down ", "Search Schedule Period Drop Down Is Displayed", "PASS");
		else
			oReport.AddStepResult("Period Drop Down ","Selected fee schedule but that Schedule period drop down is not displayed", "FAIL");

		// Selecting Period

		select_option("Period Drop down Value", searchSchedulePeriodDD,
		oParameters.GetParameters("ScheduleDropDownZeroValue"));
		click_button("Schedule Save Button", scheduleSaveButton);
		fixed_wait_time(6);
		waitFor(scheduleFirstRow, "Schedule Table");

		if(IsDisplayed("New Fee Schedule", scheduleFirstRow)) 
		{
			waitFor(scheduleFirstRow, "Schedule Table");
			oReport.AddStepResult("New fee schedule ", "New fee schedule with existed fee schedule codes is Added","PASS");
		} 
		else 
		{
			waitFor(scheduleFirstRow, "Schedule Table");
			oReport.AddStepResult("New fee schedule ","Trying to add  new Schedule from copying Details from Existing Fee Schedule, That existed Fee Schedule is  selected and clicked on save but that new fee schedule is not added with fee schedule codes existed in selected Search Schedule","FAIL");
		}
	}
	

	By addPeriodCopyDetailsCheckBox = By.xpath("//input[@id='copyClosed_addSchedulePeriod']");

	By addPeriodSearchBox = By.xpath("//input[@id='scheduleSearch']");

	By addPeriodDD = By.xpath("//select[@id='copyPeriodSelect']");

	By schedulePeriodDD = By.xpath("//div[@class='workflow  modal-medium']//select[@name='scheduleType']");

	
	// Period Drop down Validation
	public void addPeriodValidation() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(5);
		click_button("Period Drop Down", periodDropDown);
		click_button("Add Period Option", addPeriodLink);
		
		if(IsElementDisplayed("Add Effective Period Window", addEffectivePeriodWindow))
			oReport.AddStepResult("Add Effective Period Window", "Add Effective Period Window Displayed", "PASS");
		else
			oReport.AddStepResult("Add Effective Period Window","Clicked on period drop down but that Add Effective Period window is not displayed ", "FAIL");

		enter_text_value("Add Period Start Date", periodEditStartDate, get_specified_date(1));
		enter_text_value("Add Period Start Date", periodEditEndDate, get_specified_date(2));
		performKeyBoardAction("ENTER");
		select_option("Select A Schedule Drop Down", schedulePeriodDD, "2");
		click_button("Add Period Copy Details Check Box", addPeriodCopyDetailsCheckBox);

		if(IsElementDisplayed("Add Period Search Box", addPeriodSearchBox))
			oReport.AddStepResult("Fee Schedule search box ", "Existing selected Fee Schedule search box is Displayed","PASS");
		else
			oReport.AddStepResult("Fee Schedule search box ","Filled mandatory fields and checked the copy details from existing Fee Schedule but that existing selected Fee Schedule search box is not displayed","FAIL");

		enter_text_value("Schedule Search Box", addPeriodSearchBox, oParameters.GetParameters("ScheduleForFilters"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);

		if(IsDisplayed("Add Period DropDown", addPeriodDD))
			oReport.AddStepResult("Add Period Drop Down ", "Add Period Drop Down Is Displayed", "PASS");
		else
			oReport.AddStepResult("Add Period Drop Down ","Searched with existing fee schedule but that period drop down is not displayed", "FAIL");

		// Selecting Period

		select_option("Period Drop down Value", addPeriodDD, oParameters.GetParameters("ScheduleDropDownZeroValue"));
		click_button("Schedule Save Button", scheduleSaveButton);
		fixed_wait_time(5);


		if(IsDisplayed("New Fee Schedule", scheduleFirstRow))
		{
			waitFor(scheduleFirstRow, "Schedulet Table");
			oReport.AddStepResult("New fee schedule ", "New fee schedule with existed fee schedule codes is Added","PASS");
		} 
		else
		{
			waitFor(scheduleFirstRow, "Schedulet Table");
			oReport.AddStepResult("New fee schedule ", "new fee schedule code is not added as existing period", "FAIL");
		}
	}

	
	By scheduleDelete = By.xpath("//a[@title='Delete']//span[@class='lnk-btn-txt ng-binding hidden-xs'][text()='Delete']");

	By deleteSchedulePopUp = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Delete Fee Schedule?')]");

	By scheduleSearch = By.xpath("//input[@title='Type search criteria and click enter to search'][@placeholder='Search Schedules']");

	By noOfSchedules = By.xpath("//div[@class='pad-r-5 pull-right xl-header ng-binding']");

	By noOfSchedulesDT = By.xpath("//div[@class='pull-right col-lg-1 col-md-1 col-sm-1 large-document-header ng-binding']");

	By firstPageButton = By.xpath("//li[@class='num first']");

	By secondPageButton = By.xpath("//li[@class='num']/a[contains(text(),'2')]");

	By lastPageButton = By.xpath("//li[@class='num last']");

	By selectedSecondPage = By.xpath("//li[@class='num selected']/a[contains(text(),'2')]");

	By nextPageButton = By.xpath("//li[@class='next ng-scope ng-binding'][contains(text(),'Next')]");

	By prevPageButton = By.xpath("//li[@class='prev ng-scope ng-binding'][contains(text(),'Prev')]");

	By disabledPrevPageButton = By.xpath("//li[@class='prev disabled ng-scope ng-binding'][contains(text(),'Prev')]");

	By deleteSchedulePopUpCancel = By.xpath("//div[@ng-show='DialogButtons!==undefined']/div[@id='dialog_buttons']/input[@value='Cancel']");

	
	// Deleting Schedule
	public void scheduleDeletePopup() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Delete Schedule", scheduleDelete);

		if(IsDisplayed("Delete Schedule Window", deleteSchedulePopUp))
			oReport.AddStepResult("Delete Fee Schedule pop up ", "Delete Fee Schedule pop up is Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Fee Schedule pop up ","Clicked on delete button but that delete Fee Schedule pop up is not displayed", "FAIL");

		click_button("Delete Cancel", deleteSchedulePopUpCancel);
	}

	// Schedules Page Navigations
	public void feeSchedulePageNavigatoins() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Schedule Search", scheduleSearch, oParameters.GetParameters("ScheduleForPageNavigations"));
		fixed_wait_time(4);
		click_button("First Schedule", firstSchedule);
		waitFor(addFeeScheduleButton, "Add Fee Schedule Button");
		oParameters.SetParameters("NumOfSchedules",
		get_field_value("No.Of Schedules", noOfSchedules).replaceAll("[Codes:: , Codes:]", ""));
		oParameters.SetParameters("LastPageNumber", get_field_value("Last Page No.", lastPageButton));
		int schedulesCount = Integer.parseInt(oParameters.GetParameters("NumOfSchedules"));
		int lastPageNumber = Integer.parseInt(oParameters.GetParameters("LastPageNumber"));
		int lastPageNum = lastPageNumber - 1;

		if(schedulesCount / lastPageNum >= 20)
			oReport.AddStepResult("20 Fee Schedules ", "20 Fee Schedules in each page Displayed", "PASS");
		else
			oReport.AddStepResult("Fee Schedules ","Selected the period which has more than 50 Fee Schedules but in that 20 Fee Schedules are not displayed in each page","FAIL");

		// Second page

		if(IsDisplayed("Second Page Button", secondPageButton))
		{
			click_button("Second Page Button", secondPageButton);

			if(IsDisplayed("Selected Second Page", selectedSecondPage))
				oReport.AddStepResult("Clicked Page Results", "Clicked Page Results Displayed", "PASS");
			else
				oReport.AddStepResult("Page Results ","Clicked on Second page but that respective records not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Page Button", "No Next Pages", "INFO");

		// Previous Page

		if(IsDisplayed("Prev Page Button", prevPageButton)) 
		{
			click_button("Prev Page Button", prevPageButton);

			if(IsDisplayed("Next Page Button", nextPageButton))
				oReport.AddStepResult("Prev Page Results", "Prev Page Results Displayed", "PASS");
			else
				oReport.AddStepResult("Page Results ", "Clicked on Prev page but that respective records not displayed","FAIL");
		}
		else
			oReport.AddStepResult("Prev Page Button", "No Prev Page", "INFO");

		// Next Page

		if(IsDisplayed("Next Page Button", nextPageButton)) 
		{
			click_button("Next Page Button", nextPageButton);

			if(IsDisplayed("Prev Page Button", prevPageButton))
				oReport.AddStepResult("Next Page Results", "Next Page Results Displayed", "PASS");
			else
				oReport.AddStepResult("Page Results", "Clicked on Next page but that respective records not displayed","FAIL");
		}
		else
			oReport.AddStepResult("Next Page Button", "No Next Page", "INFO");

		// Choosing schedule from the list

		click_button("First Schedule", firstSchedule);
		waitFor(schedulesTitle, "Schedules Title bar");

		if(get_field_value("First Schedule", firstSchedule).equalsIgnoreCase(get_field_value("Opened Schedule", schedulesTitle)))
			oReport.AddStepResult("Schedule ", "Schedule opened successfully", "PASS");
		else
			oReport.AddStepResult("Schedule ","Clicked on Schedule from the list but that respective schedule is not displayed", "FAIL");
	}

	
	By periodDate1 = By.xpath("//div[@class='portal-action-bar']//div[@id='period-selector']//li[3]");

	By rateSheetsPlugin = By.xpath("//a[text()='Rate Sheets']");

	By rateSheetSearch = By.xpath("//div[@id='ratesheetSection']//input[@class='search-text-box input-sm form-control pad-l-25 search-icon'][@placeholder='Search Rate Sheets']");

	By existingRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'"+ oParameters.GetParameters("RateSheetName") + "')]");

	By rateSheetTitle = By.xpath("//div[@class='col-lg-6 col-md-6 col-sm-6 xl-header ng-binding'][text()='"+ oParameters.GetParameters("RateSheetName") + "']");
	
	By addTermsButton = By.xpath("//div[@id='iPSlidePanelParent']//li[@class='ng-scope'][1]//div[@class='pull-right icon-container']//a[1]//i[1]");

	By addTermWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Add Term']");

	By qualificationGroupSearch = By.xpath("//form[@id='addEditTermForm']//input[@id='qualificationGroup']");

//	By firstResultQG = By.xpath("//ul[@id='-list'][@class='dropdown-menu']/li[1]");
	
	By firstResultQG = By.xpath("//div[@model='qualificationGroupModelTerm']//ul[@id='-list']//li[1]//a[1]");

	By advancedQualifierOption = By.xpath("//span[contains(text(),'Show Advanced Qualifier Options')]");

	By optionsTab = By.xpath("//*[@id='qualgroupdetails123']");

	By addTermCancel = By.xpath(".//*[@id='showTermAddModal']//input[@id='button.cancelId']");

	By addTermDiscard = By.xpath("//input[@class='btn btn-danger discard-red'][@value='Discard']");

	
	// Navigating to Rate Sheet
	public void rateSheetPlugIn() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Rate Sheet Plugin", rateSheetsPlugin);

		if(IsDisplayed("Rate Sheet Search Box", rateSheetSearch))
			oReport.AddStepResult("Rate Sheet Plugin", "Rate Sheet Plugin Displayed Without Any Error", "PASS");
		else
			oReport.AddStepResult("Rate Sheet Plugin ", "Clicked on Rate Sheet but Rate Sheet Plugin Is Not Displayed","FAIL");
	}

	// Searching with Existing Rate Sheet
	public void existingRateSheetSearch() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Rate Sheet Search", rateSheetSearch, oParameters.GetParameters("RateSheetName"));
		performKeyBoardAction("ENTER");

		if(IsDisplayed("Existed Rate Sheet", existingRateSheet)) 
		{
			click_button("Existed Rate Sheet", existingRateSheet);

			if(IsDisplayed("Selected Rate Sheet", rateSheetTitle))
				oReport.AddStepResult("Respective Rate sheet is ", "Respective Rate sheet is Displayed", "PASS");
			else
				oReport.AddStepResult("Respective Rate sheet ","Searched and selected existing rate sheet but that respective rate sheet is not displayed","FAIL");
		}
		else
			oReport.AddStepResult("Existed Rate Sheet","Searched With Existed Rate Sheet but that specified search results are not Displayed ", "FAIL");
	}

	// Getting Tool tip
	public void addingNewTerm() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("Add Terms", addTermsButton);
		fixed_wait_time(3);
		oParameters.SetParameters("AddTermsToolTip", getToolTipValue(addTermsButton));

		if(oParameters.GetParameters("AddTermsToolTip").equalsIgnoreCase("Add Terms"))
			oReport.AddStepResult("Add Terms Tool Tip", "Add Terms tool tip displayed", "PASS");
		else
			oReport.AddStepResult("Add Terms Tool Tip ","Mouse Hovered to Add Terms icon but Add Terms tool tip is not displayed", "WARNING");

		click_button("Add Terms", addTermsButton);

		if(IsElementDisplayed("Add Term Window", addTermWindow))
			oReport.AddStepResult("Add Term Window is ", "Add Term Window is displayed", "PASS");
		else
			oReport.AddStepResult("Add Term Window ","Clicked on Add Term icon but that Add Term window is not displayed", "FAIL");
	}

	
	// Searching with QG
	public void qualificationGroupSearch() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Qualification Group Search", qualificationGroupSearch);
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		click_button("QG First Result", firstResultQG);
		
		waitFor(qualificationGroupSearch, "QG Search box");

		oReport.AddStepResult("Respective Qualifier group ", "Respective Qualification Group is selected", "PASS");
	}

	// Show Advance QG Check Box
	public void showAdvanceQGCheckBox() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Show Advance QG", advancedQualifierOption);

		if(IsDisplayed("Options Tab", optionsTab))
			oReport.AddStepResult("Options Tab", "Options Tab Displayed", "PASS");
		else
			oReport.AddStepResult("Options Tab ","Checked Show Advance Qualifier Group checkbox but that options tab is not displayed", "FAIL");
	}

	// adding term with Rate Type as Fee Schedule
	public void addiTermWithFeeScheduleRateType() 
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		select_option("Rate Type Drop down", rateTypeDropDown, "7");// oParameters.GetParameters("FeeScheduleValueOption"));
		fixed_wait_time(3);

		enter_text_value("Choosing Fee Schedule", feeScheduleSearch,
		oParameters.GetParameters("FeeScheduleName") + oParameters.GetParameters("EditScheduleName"));

		enter_text_value("Add Term Name", termNameTextBox,oParameters.GetParameters("ScheduleName")+System.currentTimeMillis());
		oParameters.SetParameters("NewTermName", get_field_value("New Term Name", termNameTextBox));
		fixed_wait_time(2);

		if(IsDisplayed("Disabled Save", addTermWindowDisabledSave)) 
		{
			oReport.AddStepResult("Disabled Save","Filled all the mandatory fields to add the Term under Rate Sheet Section but that Add Term window Save Button is Disabled  ","FAIL");

			click_button("Add Term Window Cancel", addTermWindowCancel);
			click_button("Unsaved Window Discard", unsavedWindowDiscard);
		}
		else 
		{
			click_button("Save", addTermWindowSave);

			By addedNewTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+ oParameters.GetParameters("NewTermName") + "')]");

			mouse_hover("Added Term", addedNewTerm);

			if(IsDisplayed("Added New Term", addedNewTerm))
				oReport.AddStepResult("New Term is ", "New Term is Added", "PASS");
			else
				oReport.AddStepResult("New Term is ","Filled all mandatory fields and clicked on save but that new term is not added", "FAIL");
		}
	}
	
	
	By searchSchedule = By.xpath("//div[contains(@class,'list-header drillable-header')]/../input[@title='Type search criteria and click enter to search']");
	
	By firstSchedule = By.xpath("//*[@id='scheduleView']//ul[contains(@class,'data-list drillable-list')]/li[1]");
	
	By deletePopupwindowDelete = By.xpath("//input[@type='button'][@value='Delete']");
	
	
	// to delete Schedule
	public void deleteSchedule(String existingSchedule,By deleteIcon)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(4);
		enter_text_value("Search for existing Schedule", searchSchedule, existingSchedule);
		fixed_wait_time(4);
		click_button("Clicking on searched Schedule", firstSchedule);
		fixed_wait_time(4);
		click_button("Delete Icon", deleteIcon);
		fixed_wait_time(2);
		click_button("Popup delete", deletePopupwindowDelete);		
	}

	
	// Fee Schedules VR
	public void feeSchedules_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigateToSchedules();
		selectSchedule("Fee Schedules", "Add a Fee Schedule link", feeSchedulesOption, addAFeeScheduleLink);
		selectFirstSchedule();
		addFeeScheduleButton();
		addANewSchedule("1", "Carrier/Locality/Procedure/Modifier Code schedule Option","C:\\CCM\\Downloads\\CarrierLocalityProcedureCodesModifier.xlsx",oParameters.GetParameters("CarrierLocalityProcedureCodesModifier"),"C:\\CCM\\AutoIt\\Schedules_FeeSchedules_CarrierLocalityProcedureCodesModifier.exe");
		addFeeScheduleButton();
		addANewSchedule("0", "Carrier/Locality/Procedure Code Only Option","C:\\CCM\\Downloads\\CarrierLocalityProcedureCodes.xlsx",oParameters.GetParameters("CarrierLocalityProcedureCodes"),"C:\\CCM\\AutoIt\\Schedules_FeeSchedules_CarrierLocalityProcedureCodes.exe");
		addFeeScheduleButton();
		addANewSchedule("3", "Procedure Code Only Option", "C:\\CCM\\Downloads\\ProcedureCodeTemplate.xlsx",oParameters.GetParameters("ProcedureCodeTemplate"),"C:\\CCM\\AutoIt\\Schedules_FeeSchedules_ProcedureCodeTemplate.exe");
		addFeeScheduleButton();
		addANewSchedule("4", "Procedure Code/Modifier Optional Option","C:\\CCM\\Downloads\\FeeScheduleProcedureModifier.xlsx",oParameters.GetParameters("FeeScheduleProcedureModifier"),"C:\\CCM\\AutoIt\\Schedules_FeeSchedules_FeeScheduleProcedureModifier.exe");
		addFeeScheduleButton();
		addANewSchedule("2", "Procedure/Modifier Code Option", "C:\\CCM\\Downloads\\sampleSchedule.xlsx",oParameters.GetParameters("sampleSchedule"),"C:\\CCM\\AutoIt\\Schedules_FeeSchedules_sampleSchedule.exe");
		addFeeScheduleButton();
		addScheduleCancelScenario();
		editFeeScheduleName();
		editFeeSchedulesPeriod();
		feeSchedulesNewPeriod();
		creatingNewFeeSchedule();
		amountReports();
		baseUnitReports();
		HCPCSReports();
		maxBasisReports();
		maxQuantityReports();
		modifierReports();
		RVUReports();
		scheduleValidation();
		scheduleExportingImporting();
		invalidFileImporting();
		addFeeScheduleWindow();
		addPeriodValidation();
		scheduleDeletePopup();
		feeSchedulePageNavigatoins();
		rateSheetPlugIn();
		existingRateSheetSearch();
		addingNewTerm();
		qualificationGroupSearch();
		showAdvanceQGCheckBox();
		addiTermWithFeeScheduleRateType();
		logout();
	}
	

	// Modifier Schedules VR

	public By modifierSchedulesOption = By.xpath("//*[@id='i[__valueField]']//a[contains(.,'Modifier Schedules')]");

	public By addAModifierScheduleLink = By.xpath("//a[contains(text(),'Add a Modifier Schedule')]");

	public By addModifierScheduleButton = By.xpath("//div[@class='list-header drillable-header ng-binding'][contains(.,'Modifier Schedule')]");
	
	By modifierScheduleTitle = By.xpath("//div[@class='pad-l-10 col-md-6 col-md-6 col-sm-6 large-header ng-binding']");

	public By modifierScheduleTitleDC = By.xpath("//div[@class='pad-l-10 col-lg-12 col-md-12 col-sm-12 large-document-header hide-overflow ng-binding']");

	By modifierScheduleTableBody = By.xpath("//div[@class='pps-group-content portal-tabs large-height scroll-y-auto pad-r-15 pad-l-15 ']");

	
	// Search Modifier Schedule
	public void searchModifierSchedule() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(firstSchedule, "First Modifier Schedule");
		click_button("First Schedule", firstSchedule);
		waitFor(modifierScheduleTitle, "Modifier Schedule Title bar");

		if(get_field_value("First Schedule", firstSchedule).equalsIgnoreCase(get_field_value("Opened Schedule", modifierScheduleTitle))) 
		{
			waitFor(modifierScheduleTableBody, "Modifier Schedule Table");
			oReport.AddStepResult("Schedule ", "Schedule opened successfully", "PASS");
		}
		else 
		{
			waitFor(modifierScheduleTableBody, "Modifier Schedule Table");
			oReport.AddStepResult("Search result","Clicked on first Schedule but that particular schedule is not opened", "FAIL");
		}
	}
	

	public By modifierScheduleStartDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='addModifierStartDate']");

	public By modifierScheduleEndDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='addModifierStopDate']");

	
	// Adding Modifier Schedule
	public void addingModifierSchedule() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		click_button("Add Modifier Schedule Button", addModifierScheduleButton);
		waitFor(scheduleNameTextBox, "Modifier Schedule Name textbox");
		enter_text_value("New Modifier Schedule Name Textbox", scheduleNameTextBox,
		oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("ModifierScheduleName", get_field_value("New Schedule Name", scheduleNameTextBox));
		facilityName("SCHEDULES", "Apollo srn facility");	
		enter_text_value("Schedule Start Date", modifierScheduleStartDate, get_specified_date(0));
		performKeyBoardAction("ENTER");
		enter_text_value("Schedule Start Date", modifierScheduleEndDate, get_specified_date(1));
		performKeyBoardAction("ENTER");
		click_button("Schedule Save Button", scheduleSaveButton);
		waitFor(modifierScheduleTitle, "Modifier Schedule Title bar");
		waitFor(modifierScheduleTableBody, "Modifier Schedule Table");
		oParameters.SetParameters("CreatedScheduleName",get_field_value("Created Schedule Name", modifierScheduleTitle));

		if(oParameters.GetParameters("ModifierScheduleName").equalsIgnoreCase(oParameters.GetParameters("CreatedScheduleName")))
			oReport.AddStepResult("New Modifier Schedule ","New Modifier Schedule is  added successfully and displayed ", "PASS");
		else
			oReport.AddStepResult("New Modifier Schedule ","Clicked on Add Modifier Schedule, filled all the mandatory fields and clicked on save but new Modifier Schedule is not added","FAIL");
	}

	By editModifierSchedule = By.xpath("//a[@title='Edit Modifier Schedule']//span[contains(text(),'Edit')]");

	By editModifierScheduleWindow = By.xpath("//div[@title='Edit Modifier Schedule']");

	
	// Add Modifier Schedule Window Validation
	public void addModifierWindowValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		click_button("Add Modifier Schedule Button", addModifierScheduleButton);
		waitFor(scheduleNameTextBox, "Modifier Schedule Name textbox");
		enter_text_value("New Modifier Schedule Name Textbox", scheduleNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		click_button("Cancel Button", scheduleCancelButton);
		waitFor(unsavedMessageWindow, "You have unsaved changes popup");

		if(IsDisplayed("Unsaved Changes Popup", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved changes pop up ", "Unsaved changes pop up is Displayed", "PASS");
		else
			oReport.AddStepResult("Unsaved changes pop up ","Clicked on Add Modifier schedule and filled the mandatory fields and clicked on cancel but unsaved changes pop up is not displayed","FAIL");

		// Clicking on Cancel

		click_button("Unsaved Changes Window Cancel Button", unsavedWindowCancelButton);

		if(IsDisplayed("Add Schedule Window", scheduleNameTextBox))
			oReport.AddStepResult("Pop Up Cancel", "Pop Up Closed and Add Schedule Window Displayed", "PASS");
		else
			oReport.AddStepResult("Pop Up Cancel ","Clicked on cancel button but that pop up is not closed and add schedule window is not displayed","FAIL");

		// Entering Date

		enter_text_value_without_clear("Schedules Start Date", modifierScheduleEndDate, get_specified_date(10));
		performKeyBoardAction("ENTER");
		click_button("Cancel Button", scheduleCancelButton);

		if(IsDisplayed("Unsaved Changes Message Window", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved Changes pop up", "Unsaved Changes pop up Displayed", "PASS");
		else
			oReport.AddStepResult("Unsaved Changes pop up","Termination date added and again clicked on cancel but that unsaved changes pop up is not displayed ","FAIL");

		// Discard Schedule

		click_button("Discard Button", unsavedWindowDiscard);

		if(IsDisplayed("Added Schedule Title", modifierScheduleTitle))
			oReport.AddStepResult("Add Schedule Window", "Add Schedule Window Closed", "PASS");
		else
			oReport.AddStepResult("Add Schedule Window","Clicked on discard button but that Add Modifier schedule window is not closed", "FAIL");

		// Editing Schedule

		click_button("Edit Schedule Button", editModifierSchedule);

		if(IsElementDisplayed("Edit Schedule Window", editModifierScheduleWindow))
			oReport.AddStepResult("Edit Schedules Window", "Edit Schedules Window Displayed", "PASS");
		else
			oReport.AddStepResult("Edit Schedules Window","Clicked on Edit icon but that Edit Modifier Schedule window is not opened", "FAIL");

		// Editing Schedule Name

		enter_text_value_without_clear("Schedule Name Text Box", scheduleNameTextBox,oParameters.GetParameters("EditModifierScheduleName"));
		oParameters.SetParameters("EditedModifierSchedule",get_field_value("Edited Modifier Schedule Name", scheduleNameTextBox));
		click_button("Edit Schedule Save Button", scheduleSaveButton);
		waitFor(modifierScheduleTitle, "Modifier Schedule Title bar");
		oParameters.SetParameters("EditedModifierScheduleTitle",get_field_value("Edited Schedule Title", modifierScheduleTitle));

		if(oParameters.GetParameters("EditedModifierSchedule").equals(oParameters.GetParameters("EditedModifierScheduleTitle"))) 
		{
			waitFor(modifierScheduleTableBody, "Modifier Schedule Table");
			oReport.AddStepResult("Schedule Name", "Schedule Name Modified", "PASS");
		}
		else 
		{
			waitFor(modifierScheduleTableBody, "Modifier Schedule Table");
			oReport.AddStepResult("Schedule Name", "Schedule name edited but that schedule name is doesn't changed","FAIL");
		}
	}

	
	// Modifier Schedules Period drop down validation
	public void editingModifierSchedulesPeriod() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		click_button("Period Drop Down", periodDropDown);
		waitFor(addPeriodLink, "Add Period Button");

		if(IsDisplayed("Add Period Option", addPeriodLink) && IsDisplayed("Given Effective Date", periodDate))
			oReport.AddStepResult("Add Period dropdown", "Add Period Option and Date Displayed", "PASS");
		else
			oReport.AddStepResult("Add Period Dropdown ","Clicked on Period Drop Down but given effective date is not displayed along with Add Period option","FAIL");

		// Period Date Edit Icon

		mouse_hover("Period Date", periodDate);
		fixed_wait_time(1);

		if(IsDisplayed("Edit Icon", periodDateEditIcon))
			oReport.AddStepResult("Period Date Edit Icon", "Period Date Edit Icon Displayed", "PASS");
		else
			oReport.AddStepResult("Period date Edit icon ","Mouse hovered to existing Period Date But Edit Icon Not Displayed", "FAIL");

		// Edit Effective Date Window

		click_button("Period Edit Icon", periodDateEditIcon);
		waitFor(periodDateEditWindow, "Edit Effective Period Window");

		if(IsElementDisplayed("Period Edit Window", periodDateEditWindow))
			oReport.AddStepResult("Edit Effective Period window", "Edit Effective Period window Displayed", "PASS");
		else
			oReport.AddStepResult("Edit Effective period window ","Clicked on Edit Period icon but Edit Effective Period window Is Not Displayed", "FAIL");

		// Editing Period Date

		oParameters.SetParameters("PeriodEndDate", get_field_value("Period Termination Date", modifierScheduleEndDate));
		enter_text_value("Edit Period Start Date", modifierScheduleStartDate,oParameters.GetParameters("PeriodEndDate"));
	//	performKeyBoardAction("ENTER");
		click_button("Edit Window Save Button", periodEditSaveButton);
		fixed_wait_time(3);
		waitFor(periodDropDown, "Schedule Period Dropdown");
		click_button("Period Drop Down", periodDropDown);
		mouse_hover("Edited Period Date", periodDate);
		waitFor(addPeriodLink, "Add Period button");

		if(IsDisplayed("Modified Period Date", periodDate))
			oReport.AddStepResult("Modified Period Date", "Modified Period Date Saved without any error", "PASS");
		else
			oReport.AddStepResult("Modified Period Date ","Dates modified and clicked on save but that respective modification is not saved", "FAIL");
	}
	

	// Add new period Window	
	public void addingModifierSchedulesNewPeriod() 
	{
		click_button("Add Period Option", addPeriodLink);
		waitFor(addEffectivePeriodWindow, "Add Effective Period Window");

		if(IsElementDisplayed("Add Effective Period Window", addEffectivePeriodWindow))
			oReport.AddStepResult("Add Effective Period Window", "Add Effective Period Window Displayed", "PASS");
		else
			oReport.AddStepResult("Add Effective Period Window ","Clicked on period drop down and clicked on Add Period but that Add Effective Period window is not displayed","FAIL");

		// Adding New Period

		enter_text_value("Edit Period Start Date", modifierScheduleStartDate, get_specified_date(10));//2
		enter_text_value("Edit Period Start Date", modifierScheduleEndDate, get_specified_date(12));//3
		performKeyBoardAction("ENTER");
		click_button("Add Period Window Save", periodEditSaveButton);
		waitFor(periodDropDown, "Schedule Period Dropdown");
		fixed_wait_time(2);
		click_button("Period Drop Down", periodDropDown);
		mouse_hover("New Period Date", periodDate);

		if(IsDisplayed("Period Date", periodDate))
			oReport.AddStepResult("New Period to Respective schedule Is", "New Period to Respective schedule Is Added","PASS");
		else
			oReport.AddStepResult("New Period ","Filled all the mandatory fields and clicked on save but that new period is not added", "FAIL");

		// Adding New period again

		click_button("Add Period Option", addPeriodLink);
		waitFor(addEffectivePeriodWindow, "Add Effective Period Window");
		enter_text_value("Edit Period Start Date", modifierScheduleStartDate, get_specified_date(14));
		enter_text_value("Edit Period Start Date", modifierScheduleEndDate, get_specified_date(15));
		performKeyBoardAction("ENTER");
		click_button("Add Period Window Save", periodEditSaveButton);
		waitFor(periodDropDown, "Schedule Period dropdown");
		fixed_wait_time(2);
		click_button("Period Drop Down", periodDropDown);
		mouse_hover("New Period Date", periodDate);

		if(IsDisplayed("Period Date", periodDate))
			oReport.AddStepResult("New Period to Respective schedule Is", "New Period to Respective schedule Is Added","PASS");
		else
			oReport.AddStepResult("New Period ","Filled all the mandatory fields and clicked on save but that new period is not added", "FAIL");
	}

	
	public By multiplierReductionButton = By.xpath("//span[@class='lnk-btn-txt ng-binding'][text()='Multiplier/Reduction']");

	By addModifierWindow = By.xpath("//div[@id='addEditModifierValueId']//div[@title='Add Modifiers']");

	By addModifierCodeTextBox = By.xpath("//div[@id='addEditModifierValueId']//input[@id='modifierCode']");

	By addModifierPercentageTextBox = By.xpath("//div[@id='addEditModifierValueId']//input[@id='modifierPercent']");

	public By addModifierWindowSave = By.xpath("//div[@id='addEditModifierValueId']//input[@id='button.saveId']");

	By addModifierWindowCancel = By.xpath("//div[@id='addEditModifierValueId']//input[@id='button.cancelId']");

	By valueOutOfRangeAlert = By.xpath("//span[@class='text-up ng-binding'][contains(.,'Value out of range')]");
	
	By percentageErrorInfo = By.xpath("//span[@class='text-up ng-binding'][contains(.,'The percentage value should be positive')]");
	
	By percentageErrorInfoCloseIcon = By.xpath("//span[@class='text-up ng-binding'][contains(.,'The percentage value should be positive')]/../span[@icon='times-circle']");

	By modifierAddIcon = By.xpath("//*[@id='discountModifierId']//tr//a[@title='Add']");

	By modifierFirstAddIcon = By.xpath("//*[@id='discountModifierId']//tr[1]//a[@title='Add']");

	By modifierDeleteIcon = By.xpath("//*[@id='discountModifierId']//tr//a[@title='Delete']");

	By modifierFirstDeleteIcon = By.xpath("//*[@id='discountModifierId']//tr[1]//a[@title='Delete']");

	By modifierCodeFirstRow = By.xpath("//*[@id='addEditMpmEntryParent']//tbody/tr[1]/td[1]");

	By modifierCodeFirstRowDeleteIcon = By.xpath("//*[@id='addEditMpmEntryParent']//tr[1]//a[@icon='minus-square']/i[@class='left fa fa-minus-square']");

	By modifierCodeEditWindow = By.xpath("//*[@id='addEditModifierValueId']//div[@class='medium-header truncate ng-binding']");

	By editModifierWindowSave = By.xpath("//button[@id='button.saveId']");

	By editModifierWindowCancel = By.xpath("//button[@id='button.cancelId']");

	By modifierDeleteCode = By.xpath("//span[text()='Delete Code']");

	By deleteRateEntryPopup = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Delete Rate Entry')]");

	By rateEntryDelete = By.xpath("//div[@id='dialog_buttons']//input[@class='btn btn-danger']");

	
	// Multiplier Reduction Add Modifier Window Validation
	public void multiplierReductionModifierValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Multiplier Reduction Button", multiplierReductionButton);
		waitFor(addModifierWindow, "Add Modifiers Window");

		if(IsDisplayed("Add Modifier Window", addModifierWindow))
			oReport.AddStepResult("Add Modifier edit Window", "Add Modifier edit Window Displayed", "PASS");
		else
			oReport.AddStepResult("Add Modifier edit Window ","Clicked on Add Multiplier/Reduction icon but that Add Modifiers window is not displayed", "FAIL");

		// Value out of range Alert

		enter_text_value("Modifier Code", addModifierCodeTextBox, get_random_number(2));
		enter_text_value("Modifier Percentage", addModifierPercentageTextBox, get_random_number(5));
		click_button("Add Modifier Save Button", addModifierWindowSave);
		
		if(IsDisplayed("Value Out of Range Alert", valueOutOfRangeAlert) || IsDisplayed("The percentage value should be positive", percentageErrorInfo))
			oReport.AddStepResult("Value Out of Range ", "Value Out of Range Alert is Displayed", "PASS");
		else
			oReport.AddStepResult("Value Out of Range ","Entered many values but Value Out of Range Alert is not Displayed", "FAIL");

		click_button("Percentage Error Info Close Icon", percentageErrorInfoCloseIcon);
		
		// Adding new Multiplier/Reduction Code

		enter_text_value("Modifier Code", addModifierCodeTextBox, get_random_number(2));
		oParameters.SetParameters("ModifierCode", get_field_value("Modifier Code", addModifierCodeTextBox));
		enter_text_value("Modifier Percentage", addModifierPercentageTextBox, get_random_number(2));
		click_button("Add Modifier Save Button", addModifierWindowSave);

		By newModifierCode = By.xpath("//*[@id='addEditMpmEntryParent']//tr/td[contains(.,'"+ oParameters.GetParameters("ModifierCode") + "')]");

		waitFor(newModifierCode, "Modifier Schedule codes Table");
		mouse_hover("New Modifier Code", newModifierCode);

		if(IsDisplayed("Created Modifier Code", newModifierCode))
			oReport.AddStepResult("New Multiplier/Reduction code ", "New code under Multiplier/Reduction is  Added","PASS");
		else
			oReport.AddStepResult("New Multiplier/Reduction code ","Given valid Code and Percentage and clicked on save but New code under Multiplier/Reduction is not Added","FAIL");

		// Add Modifier Window

		click_button("Multiplier Reduction Button", multiplierReductionButton);
		waitFor(addModifierWindow, "Add Modifiers Window");

		if(IsElementDisplayed("Add Modifier Window", addModifierWindow))
			oReport.AddStepResult("Add Modifier edit Window", "Add Modifier edit Window Displayed", "PASS");
		else
			oReport.AddStepResult("Add Modifier edit Window ","Clicked on Add Multiplier/Reduction icon but that Add Modifiers window is not displayed", "FAIL");

		// Adding Code and Percentage Row

		oParameters.SetParameters("ModifierCodeRowCountBefore", String.valueOf(get_table_row_count(modifierAddIcon)));
		click_button("Add New Code Row", modifierAddIcon);
		oParameters.SetParameters("ModifierCodeRowCountAfter", String.valueOf(get_table_row_count(modifierAddIcon)));

		if(oParameters.GetParameters("ModifierCodeRowCountBefore").equals(oParameters.GetParameters("ModifierCodeRowCountAfter")))
			oReport.AddStepResult("New Code and Percentage Row ","Clicked on Add icon but new row is not displayed to add new code with percentage", "FAIL");
		else
			oReport.AddStepResult("New Code and Percentage Row ", "New Code and Percentage Row is  Added", "PASS");

		// Deleting Row

		oParameters.SetParameters("ModifierCodeRowCountBefore",String.valueOf(get_table_row_count(modifierDeleteIcon)));
		click_button("Delete Code Row", modifierFirstDeleteIcon);
		oParameters.SetParameters("ModifierCodeRowCountAfter", String.valueOf(get_table_row_count(modifierDeleteIcon)));

		if(oParameters.GetParameters("ModifierCodeRowCountBefore").equals(oParameters.GetParameters("ModifierCodeRowCountAfter")))
			oReport.AddStepResult("New Code and Percentage Row ","Clicked on delete button but that row is not deleted", "FAIL");
		else
			oReport.AddStepResult("New Code and Percentage Row ", "New Code and Percentage Row is Deleted", "PASS");

		// Adding new Multiplier/Reduction Code

		enter_text_value("Modifier Code", addModifierCodeTextBox, get_random_number(2));
		oParameters.SetParameters("NewModifierCode", get_field_value("Modifier Code", addModifierCodeTextBox));
		enter_text_value("Modifier Percentage", addModifierPercentageTextBox, get_random_number(2));
		click_button("Add Modifier Save Button", addModifierWindowSave);

		By newModifierCode1 = By.xpath("//*[@id='addEditMpmEntryParent']//tr/td[contains(.,'"+ oParameters.GetParameters("NewModifierCode") + "')]");

		waitFor(newModifierCode1, "Modifier Schedule Codes table");
		mouse_hover("New Modifier Code", newModifierCode1);

		if(IsDisplayed("Created Modifier Code", newModifierCode1))
			oReport.AddStepResult("New Multiplier/Reduction code ", "New code under Multiplier/Reduction is Added","PASS");
		else
			oReport.AddStepResult("New Multiplier/Reduction code ","Given valid Code and Percentage and clicked on save but New code under Multiplier/Reduction is not Added","FAIL");

		// Hovering to Modifier Schedule Code

		mouse_hover("Modifier First Row", modifierCodeFirstRow);
		fixed_wait_time(2);

		if(IsDisplayed("Modifier Code Delete Icon", modifierCodeFirstRowDeleteIcon))
			oReport.AddStepResult("Delete Icon", "Modifier Code Delete Icon Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Icon ","Mouse Hovered to Modifier Schedule Code but Delete Icon is Not Displayed", "FAIL");

		// Modifier Code Edit Window

		click_button("Modifier Code First Row", newModifierCode1);
		waitFor(modifierCodeEditWindow, "Modifier Schedule Code Edit Window");

		if(IsElementDisplayed("Modifier Code Edit Window", modifierCodeEditWindow))
			oReport.AddStepResult("Modifier Edit Window ", "Modifier Edit Code Window is Displayed", "PASS");
		else
			oReport.AddStepResult("Modifier Edit Window ","Clicked on modifier schedule code but that edit tab is not displayed on right side of the screen ","FAIL");

		// Modifying Code and Percentage

		enter_text_value("Modifier Code", addModifierCodeTextBox, get_random_alphanumeric(2));
		oParameters.SetParameters("ModifierEditedCode", get_field_value("Modifier Code", addModifierCodeTextBox));
		enter_text_value("Modifier Percentage", addModifierPercentageTextBox, get_random_number(2));
		click_button("Edited Modifier Code Window Save Button", editModifierWindowSave);
		fixed_wait_time(3);

		if(oParameters.GetParameters("ModifierCode").equals(oParameters.GetParameters("ModifierEditedCode")))
			oReport.AddStepResult("Modifier Code and Percentage ","Modified the code/percentage and clicked on save but that respective field is not modified ","FAIL");
		else
			oReport.AddStepResult("Modifier Code and Percentage", "Modifier Code and Percentage Modfied", "PASS");

		// Multiplier Reduction Window validation and deleting Modifier
		// Unsaved Changes Window validation

		click_button("Modifier Code First Row", newModifierCode);
		waitFor(modifierCodeEditWindow, "Modifier Schedule Code Edit Window");
		enter_text_value("Modifier Code", addModifierCodeTextBox, get_random_string(2));
		click_button("Edit Window Cancel", editModifierWindowCancel);
		waitFor(unsavedMessageWindow, "unsaved Changes popup");

		if(IsDisplayed("Unsaved Changes Window", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved Changes Popup", "Unsaved Changes Popup Displayed", "PASS");
		else
			oReport.AddStepResult("Unsaved Changes Popup ","Clicked on modifier schedule code, made some changes and clicked on cancel that unsaved changes pop up is not displayed ","FAIL");

		// Clicking on Cancel

		click_button("Unsaved Changes Window Cancel Button", unsavedWindowCancelButton);

		if(IsDisplayed("Modifier Edit Window", modifierCodeEditWindow))
			oReport.AddStepResult("Pop Up Cancel", "Pop Up Closed and Edit Schedule Window Displayed", "PASS");
		else
			oReport.AddStepResult("Pop Up Cancel ", "Clicked on cancel button but that edit tab is not persisting ","FAIL");

		// Discard Schedule

		click_button("Modifier Edit Window Cancel", editModifierWindowCancel);
		waitFor(unsavedWindowDiscard, "Unsaved Changes popup Discard button");
		click_button("Discard Button", unsavedWindowDiscard);
		waitFor(newModifierCode, "Modifier Schedule Codes Table");

		if(IsDisplayed("Modifier Code", newModifierCode))
			oReport.AddStepResult("Edit tab ", "Edit tab is closed and changes are Not Saved", "PASS");
		else
			oReport.AddStepResult("Edit tab ","Clicked on discard button but that edit tab is not closed and changes are saved", "FAIL");

		// Clicking on Delete Code in Edit Window

		click_button("Modifier Code", newModifierCode);
		waitFor(modifierCodeEditWindow, "Modifier Schedule Code Edit Window");
		click_button("Delete Code", modifierDeleteCode);
		waitFor(deleteRateEntryPopup, "Delete Rate Entry Popup");

		if(IsDisplayed("Delete Rate Entry Popup", deleteRateEntryPopup))
			oReport.AddStepResult("Delete pop up ", "Delete Rate Entry pop up is Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Pop up ","Clicked on Delete Code but Delete Rate Entry pop up is Not Displayed", "FAIL");

		// Deleting Rate Entry

		click_button("Delete Rate Entry", rateEntryDelete);
		fixed_wait_time(5);

		if(IsDisplayed("Deleted Modifier Code", newModifierCode))
			oReport.AddStepResult("Code deleting "," Clicked on delete button that code is not deleted and displayed under section ", "FAIL");
		else
			oReport.AddStepResult("Code deleting ", "Code is deleted and under section Not displayed ", "PASS");

		// Clicking on Delete Icon

		mouse_hover("Modifier Code", modifierCodeFirstRow);
		fixed_wait_time(2);
		click_button("Modifier Delete Icon", modifierCodeFirstRowDeleteIcon);
		waitFor(deleteRateEntryPopup, "Delete Rate Entry Popup");

		if(IsDisplayed("Delete Rate Entry Popup", deleteRateEntryPopup))
			oReport.AddStepResult("Delete Rate Entry pop up ", "Delete Rate Entry pop up is Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Rate Entry pop up ","Hovered over modifier code and clicked on delete icon but that Delete Rate Entry pop up is not displayed","FAIL");

		// Deleting the rate Entry

		click_button("Delete Rate Entry", rateEntryDelete);
		fixed_wait_time(5);

		if(IsDisplayed("Deleted Modifier Code", newModifierCode))
			oReport.AddStepResult("Code deleting "," Clicked on delete button that code is not deleted and displayed under section ", "FAIL");
		else
			oReport.AddStepResult("Code deleting ", "Code is deleted and under section Not displayed ", "PASS");

		// choosing another period from period drop down

		fixed_wait_time(2);
		click_button("Period Drop Down", periodDropDown);
		click_button("Another period Date", periodDate1);
		waitFor(multiplierReductionButton, "Multiplier/Reduction Button");

		if(IsDisplayed("Multiplier Reduction Button", multiplierReductionButton))
			oReport.AddStepResult("Respective period ", "Respective period is Displayed", "PASS");
		else
			oReport.AddStepResult("Respective period ","Clicked on period drop down and choosed another period but that respective periods data is not displayed ","FAIL");
	}

	
	By multipleProceduresButton = By.xpath("//span[@class='lnk-btn-txt ng-binding'][text()='Multiple Procedures']");

	By addMultipleProcedureModifierWindow = By.xpath("//div[@id='addEditMpmValueId']//div[@title='Add Modifiers']");

	By multipleProceduresCode = By.xpath("//div[@id='addMpmvalue']//input[@id='modifierCode']");

	By multipleProceduresThrough = By.xpath("//div[@id='addMpmvalue']//input[@id='mpmStopValue']");

	By multipleProceduresFirstThrough = By.xpath("//div[@id='addMpmvalue']//tr[2]//input[@id='mpmStopValue']");

	By multipleProceduresPercentage = By.xpath("//div[@id='addMpmvalue']//input[@id='mpmPercentValue']");

	By multipleProceduresFirstPercentage = By.xpath("//div[@id='addMpmvalue']//tr[2]//input[@id='mpmPercentValue']");

	By multipleProceduresWindowSave = By.xpath("//div[@id='fullFooter']//input[@id='saveMpmValueId']");

	By multipleProceduresWindowSaveandAdd = By.xpath("//*[@id='saveNAddMpmValueId']");

	By multipleProceduresValueOutofRange = By.xpath("//div[@id='addMpmvalue']//span[contains(.,'Value out of range')]");

	By multipleProcedureAddIcon = By.xpath("//div[@id='addMpmvalue']//tr//a[@title='Add']");

	By multipleProcedureFirstAddIcon = By.xpath("//div[@id='addMpmvalue']//tr[2]//a[@title='Add']");

	By multipleProcedureDeleteIcon = By.xpath("//div[@id='addMpmvalue']//tr//a[@title='Delete Modifier']");

	By multipleProcedureFirstDeleteIcon = By.xpath("//div[@id='addMpmvalue']//tr[3]//a[@title='Delete Modifier']");

	By multipleProceduresEditWindow = By.xpath("//*[@id='addEditMpmValueId']//div[@class='medium-header truncate ng-binding']");

	By multipleProceduresEditWindowCode = By.xpath("//*[@id='addEditMpmValueId']//tr[1]//input[@id='modifierCode']");

	By multipleProceduresEditWindowPercentage = By.xpath("//*[@id='addEditMpmValueId']//tr[1]//input[@id='mpmPercentValue']");

	
	// Multiple Procedures Add Modifier Window Validation
	public void multipleProceduresModifierValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Multiple Procedures Button", multipleProceduresButton);
		waitFor(addMultipleProcedureModifierWindow, "Add Modifiers Window");

		if(IsElementDisplayed("Multiple Procedures Add Modifier Window", addMultipleProcedureModifierWindow))
			oReport.AddStepResult("Add Modifier edit Window ", "Multiple Procedures Add Modifier Window is Displayed","PASS");
		else
			oReport.AddStepResult("Add Modifier edit Window ","Clicked on Multiple Procedures icon but that Add Modifiers window is not displayed", "FAIL");

		// Value out of Range Alert

		enter_text_value("Multiple Procedures Code", multipleProceduresCode, get_random_number(2));
		enter_text_value("Multiple Procedures Through", multipleProceduresThrough, get_random_number(2));
		enter_text_value("Multiple Procedures Percentage", multipleProceduresPercentage, get_random_number(5));
		click_button("Multiple Procedures Save", multipleProceduresWindowSave);

		if(IsDisplayed("Value Out of Range Alert", multipleProceduresValueOutofRange)|| IsDisplayed("The percentage value should be positive", percentageErrorInfo))
			oReport.AddStepResult("Value Out of Range ", "Value Out of Range Alert is Displayed", "PASS");
		else
			oReport.AddStepResult("Value Out of Range ","Entered many values but Value Out of Range Alert is not Displayed", "FAIL");

		click_button("Percentage Error Info Close Icon", percentageErrorInfoCloseIcon);
		
		// Adding Multiple Procedures modifier

		enter_text_value("Multiple Procedures Code", multipleProceduresCode,oParameters.GetParameters("MultipleProceduresCode"));
		oParameters.SetParameters("MultipleProceduresCode",get_field_value("Multiple Procedures Code", multipleProceduresCode));
		enter_text_value("Multiple Procedures Through", multipleProceduresThrough, get_random_number(2));
		enter_text_value("Multiple Procedures Percentage", multipleProceduresPercentage, get_random_number(2));
		click_button("Multiple Procedures Save", multipleProceduresWindowSave);

		By multipleProcedureCode = By.xpath("//*[@id='addEditMpmEntryParent']//tr/td[contains(.,'"+ oParameters.GetParameters("MultipleProceduresCode") + "')]");

		waitFor(multipleProcedureCode, "Modifier Schedule Codes Table");
		mouse_hover("New Procedure Modifier", multipleProcedureCode);

		if(IsDisplayed("New Procedure Modifier", multipleProcedureCode))
			oReport.AddStepResult("New Procedure Modifier ", "New Procedure Modifier is Added", "PASS");
		else
			oReport.AddStepResult("New Procedure Modifier ","Given valid input Code, Through and Percentage and clicked on save but that new Procedure Modifier is not added","FAIL");

		// Multiple Procedure add Modifier window

		click_button("Multiple Procedures Button", multipleProceduresButton);
		waitFor(addMultipleProcedureModifierWindow, "Add Modifiers Window");

		if(IsElementDisplayed("Multiple Procedures Add Modifier Window", addMultipleProcedureModifierWindow))
			oReport.AddStepResult("Add Modifier edit Window ", "Multiple Procedures Add Modifier Window is Displayed","PASS");
		else
			oReport.AddStepResult("Add Modifier edit Window ","Clicked on Multiple Procedures icon but that Add Modifiers window is not displayed", "FAIL");

		// Adding New Row

		enter_text_value("Multiple Procedures Code", multipleProceduresCode, get_random_number(2));
		oParameters.SetParameters("MultipleProceduresCode",get_field_value("Multiple Procedures Code", multipleProceduresCode));
		enter_text_value("Multiple Procedures Through", multipleProceduresThrough,String.valueOf(get_random_number(2, 4)));
		enter_text_value("Multiple Procedures Percentage", multipleProceduresPercentage, get_random_number(2));
		oParameters.SetParameters("ModifierCodeRowCountBefore",String.valueOf(get_table_row_count(multipleProcedureAddIcon)));
		click_button("Multiple Procedure Add Icon", multipleProcedureAddIcon);
		oParameters.SetParameters("ModifierCodeRowCountAfter",String.valueOf(get_table_row_count(multipleProcedureAddIcon)));

		if(oParameters.GetParameters("ModifierCodeRowCountBefore").equals(oParameters.GetParameters("ModifierCodeRowCountAfter")))
			oReport.AddStepResult("New Code and Percentage Row ","Given Input Code, Through and Percentage and clicked on add but new row is not displayed with next From Value along with allowing user to input Through and percentage ","FAIL");
		else
			oReport.AddStepResult("New Code and Percentage Row ", "New Code and Percentage Row is Added", "PASS");

		// Again adding new Row

		oParameters.SetParameters("ModifierCodeRowCountBefore",String.valueOf(get_table_row_count(multipleProcedureAddIcon)));
		click_button("Add New Code Row", multipleProcedureFirstAddIcon);
		enter_text_value("Multiple Procedures Through", multipleProceduresFirstThrough,String.valueOf(get_random_number(5, 9)));
		enter_text_value("Multiple Procedures Percentage", multipleProceduresFirstPercentage, get_random_number(2));
		oParameters.SetParameters("ModifierCodeRowCountAfter",String.valueOf(get_table_row_count(multipleProcedureAddIcon)));

		if(oParameters.GetParameters("ModifierCodeRowCountBefore").equals(oParameters.GetParameters("ModifierCodeRowCountAfter")))
			oReport.AddStepResult("New Code and Percentage Row ","Clicked on add icon but that new row is not displayed with next From Value along with allowing user to input Through and percentage ","FAIL");
		else
			oReport.AddStepResult("New Code and Percentage Row ", "New Code and Percentage Row is Added", "PASS");

		// Deleting Row

		oParameters.SetParameters("ModifierCodeRowCountBefore",String.valueOf(get_table_row_count(multipleProcedureDeleteIcon)));
		click_button("Delete Code Row", multipleProcedureFirstDeleteIcon);
		oParameters.SetParameters("ModifierCodeRowCountAfter",String.valueOf(get_table_row_count(multipleProcedureDeleteIcon)));

		if(oParameters.GetParameters("ModifierCodeRowCountBefore").equals(oParameters.GetParameters("ModifierCodeRowCountAfter")))
			oReport.AddStepResult("New Code and Percentage Row ","Clicked on delete button but that row is not deleted", "FAIL");
		else
			oReport.AddStepResult("New Code and Percentage Row ", "New Code and Percentage Row is Deleted", "PASS");

		// Save and Add Another button

		click_button("Save and Add Another", multipleProceduresWindowSaveandAdd);

		By savedProcedureModifier = By.xpath("//div[@class='dialog-bg adjust-height']//tr/td[contains(.,'"+ oParameters.GetParameters("MultipleProceduresCode") + "')]");

		if(IsDisplayed("Saved Procedure Modifier", savedProcedureModifier))
			oReport.AddStepResult("saved and another Procedure modifier ","Procedure modifier is saved and another Procedure modifier Added", "PASS");
		else
			oReport.AddStepResult("Save and Add Another ","Clicked on Save and Add Another button but Procedure modifier is not saved ", "FAIL");

		// Entering values

		enter_text_value("Multiple Procedures Code", multipleProceduresCode, get_random_number(2));
		oParameters.SetParameters("MultipleProceduresCode",get_field_value("Multiple Procedures Code", multipleProceduresCode));
		enter_text_value("Multiple Procedures Through", multipleProceduresThrough,String.valueOf(get_random_number(2, 4)));
		enter_text_value("Multiple Procedures Percentage", multipleProceduresPercentage, get_random_number(2));
		click_button("Multiple Procedures Save", multipleProceduresWindowSave);

		By procedureModifierAddedCode = By.xpath("//*[@id='addEditMpmEntryParent']//tr/td[contains(.,'"+ oParameters.GetParameters("MultipleProceduresCode") + "')]");

		if(IsDisplayed("Added Procedure Modifiers", procedureModifierAddedCode))
			oReport.AddStepResult("Procedure modifiers ", "Procedure modifiers are Added", "PASS");
		else
			oReport.AddStepResult("Procedure modifiers ","Filled all the fields and clicked on save button but that all the procedure modifiers are not added","FAIL");

		// Hover over a Multiple Procedures Code

		mouse_hover("Multiple Procedure Code", multipleProcedureCode);
		fixed_wait_time(2);

		if(IsDisplayed("Multiple Procedures Code Delete Icon", modifierCodeFirstRowDeleteIcon))
			oReport.AddStepResult("Delete Icon", "Modifier Code Delete Icon Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Icon ","Hovered over a Multiple Procedures code but that delete icon is not displayed ", "FAIL");

		// Modifier Code Edit Window

		click_button("Multiple Procedure Code First Row", multipleProcedureCode);
		waitFor(multipleProceduresEditWindow, "Modifier Schedule code Edit window");

		if(IsElementDisplayed("Modifier Code Edit Window", multipleProceduresEditWindow))
			oReport.AddStepResult("Modifier Edit Code Window ", "Modifier Edit Code Window is Displayed", "PASS");
		else
			oReport.AddStepResult("Modifier Edit Window ","Clicked on Multiple Procedures code but that edit tab is not displayed on right side of the screen ","FAIL");

		// Modifying Code and Percentage

		enter_text_value("Modifier Code", multipleProceduresEditWindowCode, get_random_alphanumeric(2));
		oParameters.SetParameters("MultipleProcedureEditedCode",get_field_value("Modifier Code", multipleProceduresEditWindowCode));
		enter_text_value("Modifier Percentage", multipleProceduresEditWindowPercentage, get_random_number(2));
		click_button("Edited Modifier Code Window Save Button", editModifierWindowSave);
		fixed_wait_time(3);

		if(oParameters.GetParameters("ModifierCode").equals(oParameters.GetParameters("MultipleProcedureEditedCode")))
			oReport.AddStepResult("Modifier Code and Percentage ","Modified the code/percentage and clicked on save but that respective field is not modified ","FAIL");
		else
			oReport.AddStepResult("Modifier Code and Percentage", "Modifier Code and Percentage Modfied", "PASS");

		By multipleProcedureCode1 = By.xpath("//*[@id='addEditMpmEntryParent']//tr/td[contains(.,'"+ oParameters.GetParameters("MultipleProcedureEditedCode") + "')]");

		// Unsaved Changes Window validation

		click_button("Modifier Code First Row", multipleProcedureCode1);
		waitFor(multipleProceduresEditWindow, "Modifier Schedule code Edit window");
		enter_text_value("Modifier Code", multipleProceduresEditWindowCode, get_random_string(2));
		click_button("Edit Window Cancel", editModifierWindowCancel);
		waitFor(unsavedMessageWindow, "Unsaved Changes notification");

		if(IsDisplayed("Unsaved Changes Window", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved Changes Popup", "Unsaved Changes Popup Displayed", "PASS");
		else
			oReport.AddStepResult("Unsaved Changes Popup ","Clicked on Multiple Procedures code, made some changes and clicked on cancel but that unsaved changes pop up is not displayed ","FAIL");

		// Clicking on Cancel

		click_button("Unsaved Changes Window Cancel Button", unsavedWindowCancelButton);

		if(IsDisplayed("Modifier Edit Window", multipleProceduresEditWindow))
			oReport.AddStepResult("Edit Schedule Window ", "Pop Up Closed and Edit Schedule Window Displayed", "PASS");
		else
			oReport.AddStepResult("Edit Schedule Window ","Clicked on Unsaved Changes Window cancel button but that edit tab is not persisting ", "FAIL");

		// Discard Schedule

		click_button("Modifier Edit Window Cancel", editModifierWindowCancel);
		waitFor(unsavedWindowDiscard, "Unsaved Changes popup window Discard");
		click_button("Discard Button", unsavedWindowDiscard);
		waitFor(multipleProcedureCode1, "Modifier Schedule Codes Table");

		if(IsDisplayed("Modifier Code", multipleProcedureCode1))
			oReport.AddStepResult("Discard Schedule ","Clicked on Unsaved Changes Window discard button that edit tab is closed ", "PASS");
		else
			oReport.AddStepResult("Edit tab is Not closed and changes are","Clicked on Unsaved Changes Window discard button but that edit tab is not closed ", "FAIL");

		// Clicking on Delete Code in Edit Window

		click_button("Modifier Code", multipleProcedureCode1);
		waitFor(multipleProceduresEditWindow, "Modifier Schedules Code Edit window");
		click_button("Delete Code", modifierDeleteCode);
		waitFor(deleteRateEntryPopup, "Delete Rate Entry Popup");

		if(IsDisplayed("Delete Rate Entry Popup", deleteRateEntryPopup))
			oReport.AddStepResult("Delete Rate Entry pop up ", "Delete Rate Entry pop up is Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Rate Entry Popup ","Clicked on Delete Code but Delete Rate Entry pop up is  Not Displayed", "FAIL");

		// Deleting Rate Entry

		click_button("Delete Rate Entry", rateEntryDelete);
		fixed_wait_time(3);

		if(IsDisplayed("Deleted Modifier Code", multipleProcedureCode1))
			oReport.AddStepResult("Code is Not deleted and under section","Clicked on delete button but that code is not deleted and displayed under section", "FAIL");
		else
			oReport.AddStepResult("Code is deleted and under section","Code is deleted and under section Not displayed ", "PASS");

		// Clicking on Delete Icon

		mouse_hover("Modifier Code", modifierCodeFirstRow);
		fixed_wait_time(2);
		click_button("Modifier Delete Icon", modifierCodeFirstRowDeleteIcon);
		waitFor(deleteRateEntryPopup, "Delete Rate Entry Popup");

		if(IsDisplayed("Delete Rate Entry Popup", deleteRateEntryPopup))
			oReport.AddStepResult("Delete Rate Entry pop up is ", "Delete Rate Entry pop up is Displayed", "PASS");
		else
			oReport.AddStepResult("Clicked on Delete Code but Delete Rate Entry pop up is ","Hovered over modifier code and clicked on delete icon but that Delete Rate Entry pop up is not displayed","FAIL");

		// Deleting the rate Entry

		click_button("Delete Rate Entry", rateEntryDelete);
		fixed_wait_time(5);

		if(IsDisplayed("Deleted Modifier Code", multipleProcedureCode1))
			oReport.AddStepResult("Code is Not deleted and under section"," Clicked on delete button but that code is not deleted and displayed under section", "FAIL");
		else
			oReport.AddStepResult("Code is deleted and under section","Code is deleted and under section Not displayed ", "PASS");

		// choosing another period from period drop down

		click_button("Period Drop Down", periodDropDown);
		click_button("Another period Date", periodDate1);
		waitFor(multiplierReductionButton, "Multiplier/Reduction Button");

		if(IsDisplayed("Multiplier Reduction Button", multiplierReductionButton))
			oReport.AddStepResult("Respective period is ", "Respective period is Displayed", "PASS");
		else
			oReport.AddStepResult("Respective period is ","Clicked on period drop down and choosed another period but that respective periods data is not displayed ","FAIL");
	}

	
	By rateTypeDropDown = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8 ng-isolate-scope']//select");

	By modifierScheduleSearch = By.xpath("//input[@id='fsModDiscount']");

	By feeScheduleSearch = By.xpath("//input[@id='fsMaster']");

	By termNameTextBox = By.xpath("//input[@id='termName']");

	By addTermWindowSave = By.xpath("//div[@id='ratesheetView']//div[@id='fullFooter']//input[@id='button.saveId']");

	By addTermWindowCancel = By.xpath("//div[@id='ratesheetView']//div[@id='fullFooter']//input[@id='button.cancelId']");

	By addTermWindowDisabledSave = By.xpath("//div[@id='ratesheetView']//div[@id='fullFooter']//input[@id='button.saveId'][@disabled='disabled']");

	
	// Searching Rate Type with Fee Schedule
	public void rateTypeFeeSchedule() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(rateTypeDropDown, "Rate Type DropDown");
		select_option("Rate Type Drop down", rateTypeDropDown, oParameters.GetParameters("FeeScheduleValueOption"));
		fixed_wait_time(3);

		enter_text_value("Modifier Schedule Search", modifierScheduleSearch,oParameters.GetParameters("EditedModifierSchedule"));
		fixed_wait_time(3);

		enter_text_value("Choosing Fee Schedule", feeScheduleSearch,oParameters.GetParameters("ExistedFeeScheduleName"));

		enter_text_value("Add Term Name", termNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("NewTermName", get_field_value("New Term Name", termNameTextBox));
		fixed_wait_time(2);

		if(IsDisplayed("Disabled Save", addTermWindowDisabledSave)) 
		{
			oReport.AddStepResult("Disabled Save","Filled all the mandatory fields to add the Term under Rate Sheet Section but that Add Term window Save Button is Disabled  ","FAIL");

			click_button("Add Term Window Cancel", addTermWindowCancel);
			click_button("Unsaved Window Discard", unsavedWindowDiscard);
		}
		else 
		{
			click_button("Save", addTermWindowSave);

			By addedNewTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+ oParameters.GetParameters("NewTermName") + "')]");

			waitFor(addedNewTerm, "Added Term");
			fixed_wait_time(4);
			mouse_hover("Added Term", addedNewTerm);

			if(IsDisplayed("Added New Term", addedNewTerm))
				oReport.AddStepResult("New Term is ", "New Term is Added", "PASS");
			else
				oReport.AddStepResult("New Term is ","Filled all mandatory fields and clicked on save but that new term is not added", "FAIL");
		}
	}

	
	// Modifier Schedules VR
	public void modifierSchedules_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigateToSchedules();
		selectSchedule("Modifier Schedule", "Add a Modifier Schedule link", modifierSchedulesOption, addAModifierScheduleLink);
		searchModifierSchedule();
		addingModifierSchedule();
		addModifierWindowValidation();
		editingModifierSchedulesPeriod();
		addingModifierSchedulesNewPeriod();
		multiplierReductionModifierValidation();
		multipleProceduresModifierValidation();
		rateSheetPlugIn();
		existingRateSheetSearch();
		addingNewTerm();
		qualificationGroupSearch();
		rateTypeFeeSchedule();
		logout();
	}

	
	// Group Code Rates Schedules VR

	
	public By groupCodeRatesSchedulesOption = By.xpath("//*[@id='i[__valueField]']//a[contains(.,'Group Code Rates')]");

	public By addAGroupCodeRateScheduleLink = By.xpath("//a[contains(text(),'Add a Group Code Rate')]");

	public By addGroupCodeRatesScheduleButton = By.xpath("//div[@class='list-header drillable-header ng-binding'][contains(.,'Group Code Rate Schedule')]");

	By groupCodeRatesScheduleTitle = By.xpath("//*[@id='scheduleView']//div[@class='pad-l-10 col-sm-6 col-md-6 col-lg-6 large-header ng-binding']");

	public By groupCodeRatesScheduleTitleDC = By.xpath("//div[@class='pad-l-10 col-lg-9 col-md-9 col-sm-9 col-xs-12 large-document-header hide-overflow ng-binding']");

	
	// Search Modifier Schedule
	public void selectFirstGroupCodeRatesSchedule() 
	{
		click_button("First Schedule", firstSchedule);
		//waitFor(groupCodeRatesScheduleTitle, "Group Code Rates Schedule Title Bar");

		if(get_field_value("First Schedule", firstSchedule).equalsIgnoreCase(get_field_value("Opened Schedule", groupCodeRatesScheduleTitle)))
			oReport.AddStepResult("Schedule ", "Schedule opened successfully", "PASS");
		else
			oReport.AddStepResult("Search result","Clicked on first Schedule but that particular schedule is not opened", "FAIL");
	}

	By addGroupCodeRatesScheduleWindowTitle = By.xpath("//div[@class='workflow  modal-medium']//div[@title='Add Group Code']");

	// Add Group Code Rates Schedule Window
	public void addGroupCodeRatesScheduleButton() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		click_button("Add Group Code Rates Schedule", addGroupCodeRatesScheduleButton);
		waitFor(addGroupCodeRatesScheduleWindowTitle, "Add Group Code Rates Schedule Button");

		if(IsElementDisplayed("Add Group Code Rates Schedule Window", addGroupCodeRatesScheduleWindowTitle))
			oReport.AddStepResult("Schedule Window", "Add Group Code Rates Schedule Window Displayed", "PASS");
		else
			oReport.AddStepResult("Schedule Window ","Clicked on Add Group Code Rates but Add Group Code window not displayed", "FAIL");
	}

	public By groupCodeNameTextBox = By.xpath("//form[@id='addGroupCodeRateModal']//input[@id='groupCodeName']");

	public By groupCodeEffectiveDate = By.xpath("//form[@id='addGroupCodeRateModal']//input[@id='setStartDate']");

	public By groupCodeTerminationDate = By.xpath("//form[@id='addGroupCodeRateModal']//input[@id='setStopDate']");

	public By groupCodeWindowSave = By.xpath("//div[@id='addGroupCodeModal']//input[@id='button.saveId']");

	By groupCodeWindowCancel = By.xpath("//div[@id='addGroupCodeModal']//input[@id='button.cancelId']");

	
	// Adding New Group Code Rates Schedule
	public void groupCodeRatesScheduleDetails() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Group Code Name", groupCodeNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("GroupCodeName", get_field_value("Group Code Name", groupCodeNameTextBox));
		enter_text_value("Group Code Start Date", groupCodeEffectiveDate, get_specified_date(0));
		performKeyBoardAction("ENTER");
		enter_text_value("Group Code End Date", groupCodeTerminationDate, get_specified_date(2));
		performKeyBoardAction("ENTER");
		facilityName("SCHEDULES", "Apollo srn facility");	
		click_button("Group Code Save", groupCodeWindowSave);
		fixed_wait_time(3);
		waitFor(groupCodeRatesScheduleTitle, "Group Code Rates Schedule Title Bar");

		if(oParameters.GetParameters("GroupCodeName").equalsIgnoreCase(get_field_value("Group Name Title", groupCodeRatesScheduleTitle))) 
		{
			waitFor(groupCodeRatesScheduleTitle, "Group Code Rates Schedule Title Bar");
			oReport.AddStepResult("New Group Code ", "New Group Code is Added successfully and displayed ", "PASS");
		}
		else 
		{
			waitFor(groupCodeRatesScheduleTitle, "Group Code Rates Schedule Title Bar");
			oReport.AddStepResult("New Group Code ","Filled all the mandatory fields and clicked on save but that new Group Code is not added and displayed ","FAIL");
		}
	}

	By groupCodeEditButton = By.xpath("//div[@ng-show='selectedPeriod.id']//a[@title='Edit Group Code Rate']/i[@class='left fa fa-pencil']");

	By groupCodeEditWindow = By.xpath("//div[@id='addGroupCodeModal']//div[@title='Edit Group Code']");

	
	// New Group Code window Validation
	public void groupCodeCancelScenario() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Group Code Name", groupCodeNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		enter_text_value("Group Code Start Date", groupCodeEffectiveDate, get_specified_date(0));
		performKeyBoardAction("ENTER");
		click_button("Group Code Window Cancel", groupCodeWindowCancel);

		if(IsDisplayed("Unsaved changes pop up", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved changes pop up ", "Unsaved changes pop up is Displayed", "PASS");
		else
			oReport.AddStepResult("Unsaved changes pop up ","Filled the mandatory fields and clicked on cancel but that unsaved changes pop up not displayed","FAIL");

		// Clicking on Cancel

		click_button("Unsaved Changes Window Cancel", unsavedWindowCancelButton);

		if(IsDisplayed("Add Group Code Rates Schedule Window", addGroupCodeRatesScheduleWindowTitle))
			oReport.AddStepResult("Unsaved Changes window cancel ","Pop Up Closed and Add Group Code Rates Schedule Window Displayed", "PASS");
		else
			oReport.AddStepResult("Unsaved Changes window cancel ","Clicked on Unsaved Changes Window cancel but that pop up is not closed and add Group Code window is not displayed","FAIL");

		// Entering Termination Date and Clicking on Cancel

		enter_text_value("Group Code End Date", groupCodeTerminationDate, get_specified_date(7));
		performKeyBoardAction("ENTER");
		click_button("Group Code Window Cancel", groupCodeWindowCancel);

		if(IsDisplayed("Unsaved changes pop up", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved changes pop up ", "Unsaved changes pop up is Displayed", "PASS");
		else
			oReport.AddStepResult("Unsaved changes pop up","Added termination date and clicked on cancel but that unsaved changes pop up is not displayed","FAIL");

		// Discard Schedule

		click_button("Discard Button", unsavedWindowDiscard);

		if(get_field_value("Group Name Title", groupCodeRatesScheduleTitle).equalsIgnoreCase(oParameters.GetParameters("GroupCodeName")))
			oReport.AddStepResult("Add Schedule Window", "Add Schedule Window Closed", "PASS");
		else
			oReport.AddStepResult("Discard Schedule ","Click on Unsaved Changes Window discard but that Add Group Code window is not closed ", "FAIL");
	}

	// Editing Schedule Name
	public void editGroupCodeSchedule() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit Group Code Rates Schedule Button", groupCodeEditButton);
		fixed_wait_time(2);

		if(IsDisplayed("Group Code Rates Schedule Edit Window", groupCodeEditWindow))
			oReport.AddStepResult("Edit Group Code Rates Schedules Window","Edit Group Code Rates Schedules Window Displayed", "PASS");
		else
			oReport.AddStepResult("Edit Group Code Rates Schedules Window","Clicked on group code Schedule name Edit icon but that Edit Group Code window is not opened","FAIL");

		// Editing Schedule Name

		enter_text_value_without_clear("Group Code Rates Schedule Name Text Box", groupCodeNameTextBox,oParameters.GetParameters("EditGroupCodeScheduleName"));
		oParameters.SetParameters("EditedGroupCodeSchedule",get_field_value("Edited Group Code Schedule Name", groupCodeNameTextBox));
		click_button("Edit Group Code Schedule Save Button", groupCodeWindowSave);
		
		fixed_wait_time(3);
		waitFor(groupCodeRatesScheduleTitle, "Group Code Rates Schedule Title Bar");
		
		oParameters.SetParameters("EditedGroupCodeScheduleTitle",get_field_value("Edited Group Code Schedule Title", groupCodeRatesScheduleTitle));

		if(oParameters.GetParameters("EditedGroupCodeSchedule").equals(oParameters.GetParameters("EditedGroupCodeScheduleTitle")))
			oReport.AddStepResult("Schedule Name", "Schedule Name Modified", "PASS");
		else
			oReport.AddStepResult("Schedule Name editing ","Modified the Group Code name and clicked on save but that Group Code name is not modified","FAIL");
	}

	By groupCodeAddPeriodLink = By.xpath("//div[@id='scheduleView']//li[@class='hand-cursor border-b-1 noStatus']/a");

	By groupCodePeriodDate = By.xpath("//div[@id='scheduleView']//ul[@class='dropdown-menu period-menu mar-l-15']/li[2]");

	By groupCodePeriodEditIcon = By.xpath("//div[@id='scheduleView']//ul[@class='dropdown-menu period-menu mar-l-15']/li[2]//i[@class='fa fa-fw fa-pencil show-on-hover']");

	By groupCodePeriodDeleteIcon = By.xpath("//div[@id='scheduleView']//ul[@class='dropdown-menu period-menu mar-l-15']/li[2]//i[@class='fa fa-fw fa-minus-square pull-right show-on-hover period-minus']");

	By groupCodePeriodEditEndDate = By.xpath("//form[@id='addGroupCodeRatePeriod']//input[@id='setStopDate']");

	By groupCodePeriodEditStartDate = By.xpath("//form[@id='addGroupCodeRatePeriod']//input[@id='setStartDate']");

	
	// Period Drop down validation
	public void editGroupCodeRatesSchedulesPeriod() 
	{
		fixed_wait_time(3);
		click_button("Period Drop Down", periodDropDown);

		if(IsDisplayed("Add Period Option", groupCodeAddPeriodLink)	&& IsDisplayed("Given Effective Date", groupCodePeriodDate))
			oReport.AddStepResult("Add Period Option and Date", "Add Period Option and Date Displayed", "PASS");
		else
			oReport.AddStepResult("Group code Rates Schedule Period Drop down ","Clicked on period drop down but that given effective date is not displayed along with Add Period option","FAIL");

		// Period Date Edit and Delete Icon

		mouse_hover("Period Date", groupCodePeriodDate);

		if(IsDisplayed("Edit Icon", groupCodePeriodEditIcon)&& IsDisplayed("Delete Icon", groupCodePeriodDeleteIcon))
			oReport.AddStepResult("Period Date Edit and Delete Icon", "Period Date Edit and Delete Icon Displayed",	"PASS");
		else
			oReport.AddStepResult("Edit and Delete icon ","Mouse hovered to Period Date But Edit and Delete Icon Not Displayed", "FAIL");

		// Edit Effective Date Window

		click_button("Period Edit Icon", groupCodePeriodEditIcon);

		if(IsDisplayed("Period Edit Window", periodDateEditWindow))
			oReport.AddStepResult("Edit Effective Period window", "Edit Effective Period window Displayed", "PASS");
		else
			oReport.AddStepResult("Schedule edit window ","Clicked on period edit icon but that Edit Effective Period window is not displayed", "FAIL");

		// Editing Period Date

		oParameters.SetParameters("GroupCodePeriodEndDate",	get_field_value("Period Termination Date", groupCodePeriodEditEndDate));
		enter_text_value("Edit Period Start Date", groupCodePeriodEditStartDate,oParameters.GetParameters("GroupCodePeriodEndDate"));
		performKeyBoardAction("ENTER");
		click_button("Edit Window Save Button", periodEditSaveButton);
		fixed_wait_time(3);
		click_button("Period Drop down", periodDropDown);
		mouse_hover("Edited Period", groupCodePeriodDate);

		if(IsDisplayed("Modified Date", groupCodePeriodDate))
			oReport.AddStepResult("Modified Period Date", "Modified Period Date Saved without any error", "PASS");
		else
			oReport.AddStepResult("Editing period date ","Modified the period dates and clicked on save but that respective modification is not done ","FAIL");
	}

	By groupCodeEditStartDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='setStartDate']");

	By groupCodeEditEndDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='setStopDate']");

	
	// Add new period Window
	public void groupCodeRatesSchedulesNewPeriodValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add Period Option", groupCodeAddPeriodLink);

		if(IsElementDisplayed("Add Effective Period Window", addEffectivePeriodWindow))
			oReport.AddStepResult("Add Effective Period Window ", "Add Effective Period Window Displayed", "PASS");
		else
			oReport.AddStepResult("Add period window ","Clicked on period drop down and clicked on Add Period but that Add Effective Period window is not displayed","FAIL");

		// Adding New Period

		enter_text_value("Edit Period Start Date", groupCodeEditStartDate, get_specified_date(3));
		enter_text_value("Edit Period Start Date", groupCodeEditEndDate, get_specified_date(4));
		performKeyBoardAction("ENTER");
		click_button("Add Period Window Save", periodEditSaveButton);
		fixed_wait_time(3);
		click_button("Period Drop Down", periodDropDown);
		mouse_hover("New Period Date", groupCodePeriodDate);

		if(IsDisplayed("Period Date", groupCodePeriodDate))
			oReport.AddStepResult("New Period ", "New Period to Respective schedule Is Added", "PASS");
		else
			oReport.AddStepResult("Adding New period ","Filled all the date fields and clicked on save but that new period is not added for the respective Group Code Rate ","FAIL");

		// Deleting existing Period

		mouse_hover("Period Date", groupCodePeriodDate);
		click_button("Period Date Delete Icon", groupCodePeriodDeleteIcon);

		if(IsDisplayed("Delete Period Notification Window", deletePeriodWindow))
			oReport.AddStepResult("Delete Period Pop up", "Delete Period Pop up Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Period Pop up ","Clicked on period drop down, hovered over existing period and clicked on delete icon but that delete period pop up is not displayed ","FAIL");

		click_button("Delete Period", deletePeriod);
		fixed_wait_time(3);
		waitFor(periodDropDown, "Group Code Rates Schedules period dropdown");
		click_button("Period Drop Down", periodDropDown);
		mouse_hover("Period Date", groupCodePeriodDate);

		if(IsDisplayed("Period Date", groupCodePeriodDate))
			oReport.AddStepResult("Existing Period", "Existing Period Deleted", "PASS");
		else
			oReport.AddStepResult("Deleting period ","After hovered over existing period clicked on delete icon and clicked on delete but that period is not deleted ","FAIL");
	}
	

	By addGroupCodeScheduleEntryLink = By.xpath("//a[@title='Add a Group Code Entry']/i[@class='left fa fa-plus-square']");

	By addGroupCodeScheduleWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Add a Group Code Entry']");

	By groupCodeTextBox = By.xpath("//div[@class='workflow  modal-medium']//input[@id='groupCode']");

	By groupCodeAmountTextBox = By.xpath("//div[@class='workflow  modal-medium']//input[@id='amount']");

	By groupCodeEntryWindowSave = By.xpath("//div[@class='workflow  modal-medium']//input[@id='button.saveId']");

	By createdGroupCodeEditWindow = By.xpath("//div[@id='editEntryPanel']//div[@class='medium-header truncate ng-binding']");

	By groupCodeEditWindowAmount = By.xpath("//div[@id='editEntryPanel']//input[@id='amount']");

	By groupCodeEditWindowSave = By.xpath("//div[@id='editEntryPanel']//button[@id='button.saveId']");

	By groupCodeEditWindowCancel = By.xpath("//div[@id='editEntryPanel']//button[@id='button.cancelId']");

	By groupCodeDiscardButton = By.xpath("//div[@class='form-button-panel']//input[@class='btn btn-danger discard-red']");

	By groupCodeRateEntryErrorWindow = By.xpath("//div[@class='medium-header bold ng-binding'][contains(text(),'Error Saving Group Code Rate Entry')]");

	By groupCodeRateEntryErrorWindowOk = By.xpath("//div[@class='form-button-panel']//input[@class='btn  btn-default']");

	
	// Clicking on Add a Group Code entry link
	public void createNewGroupCodeRatesSchedule() 
	{
		fixed_wait_time(3);
		click_button("Add a Schedule Entry Link", addGroupCodeScheduleEntryLink);
		fixed_wait_time(2);

		if(IsElementDisplayed("Add Group Code Schedule Window", addGroupCodeScheduleWindow))
			oReport.AddStepResult("Add Group Code Rates Schedule Code window","Add Group Code Rates Schedule Code window Displayed", "PASS");
		else
			oReport.AddStepResult("that Add Group Code window ","Clicked on Add a Group Code entry link but that Add Group Code window is not displayed ", "FAIL");

		enter_text_value("Group Code Text Box", groupCodeTextBox, get_random_alphanumeric(3));
		oParameters.SetParameters("GroupCodeValue", get_field_value("Group Code Value", groupCodeTextBox));
		enter_text_value("Amount Text Box", groupCodeAmountTextBox, get_random_number(2));
		oParameters.SetParameters("GroupCodeAmount", get_field_value("Group Code Amount", groupCodeAmountTextBox));
		click_button("Save Group Code Entry Schedule", groupCodeEntryWindowSave);

		By groupCodeColumn = By.xpath("//tr[@class='hand-cursor position-relative ng-scope']/td[text()='"+ oParameters.GetParameters("GroupCodeValue") + "']");

		waitFor(groupCodeColumn, "Group Code Rate Schedules codes table");
		oParameters.SetParameters("NewGroupCodeValue", get_field_value("Group Code Value", groupCodeColumn));
		mouse_hover("New Schedule", groupCodeColumn);

		if(oParameters.GetParameters("GroupCodeValue").equals(oParameters.GetParameters("NewGroupCodeValue"))) 
		{
			waitFor(groupCodeColumn, "Group Code Rate Schedules codes table");
			oReport.AddStepResult("New Schedule Code", "New Schedule Code Added", "PASS");
		}
		else 
		{
			fixed_wait_time(3);
			oReport.AddStepResult("Adding new group code ","Filled all the mandatory fields for adding group code and clicked on save but that new Group Code is not added","FAIL");
		}
		
		// Clicking on Created Group Code

		click_button("Created Group Code", groupCodeColumn);

		if(IsDisplayed("Group Code Edit Window", createdGroupCodeEditWindow))
			oReport.AddStepResult("Edit tab ", "Edit tab is Displayed on right side of the screen", "PASS");
		else
			oReport.AddStepResult("Edit tab ","Clicked on Created Group code but Edit tab is Not Displayed on right side of the screen", "FAIL");

		// Editing the Group Code

		enter_text_value("Group Code Amount", groupCodeEditWindowAmount, get_random_number(3));
		oParameters.SetParameters("GroupCodeEditedAmount",get_field_value("Group Code Amount", groupCodeEditWindowAmount));
		click_button("Group Code Edit Window Save", groupCodeEditWindowSave);

		if(IsDisplayed("Group Code Error Window", groupCodeRateEntryErrorWindow)) 
		{
			oReport.AddStepResult("Editing group code amount ","Tried to Modify the group code amount but group code amount is not Modified", "FAIL");

			click_button("Group code Error Window Ok", groupCodeRateEntryErrorWindowOk);
			click_button("Group Code Edit Window Cancel", groupCodeEditWindowCancel);
			fixed_wait_time(2);
			click_button("Group Code Discard Button", groupCodeDiscardButton);
		}
		else 
		{
			waitFor(groupCodeColumn, "Group Code Rate Schedules codes table");

			By groupCodeAmountColumn = By.xpath("//tr[@class='hand-cursor position-relative ng-scope']/td[contains(.,'"+ oParameters.GetParameters("GroupCodeEditedAmount")+"')]");

			oParameters.SetParameters("GroupCodeAmountAfterEdit",get_field_value("Group Code Amount", groupCodeAmountColumn));

			if(oParameters.GetParameters("GroupCodeAmount").equals(oParameters.GetParameters("GroupCodeAmountAfterEdit")))
				oReport.AddStepResult("Editing group code amount ","Tried to Modify the group code amount but group code amount is not Modified", "FAIL");
			else
				oReport.AddStepResult("Group Code is ", "Group Code is Edited", "PASS");
		}

		// Again clicking on Created Group Code

		click_button("Created Group Code", groupCodeColumn);

		if(IsDisplayed("Group Code Edit Window", createdGroupCodeEditWindow))
			oReport.AddStepResult("Edit tab is ", "Edit tab is Displayed on right side of the screen", "PASS");
		else
			oReport.AddStepResult("Group code Edit tab ","Clicked on Created Group code but Edit tab is Not Displayed on right side of the screen", "FAIL");

		// Deleting Group Code Schedule

		click_button("Group Code Delete", scheduleEditTabDelete);
		waitFor(deleteScheduleCodeDelete, "Delete Group Code Entry popup");
		click_button("Delete Button", deleteScheduleCodeDelete);
		fixed_wait_time(10);
		waitFor(groupCodeRatesScheduleTitle, "Group Code Rates Schedule Title bar");

		if(IsDisplayed("Created Group Code", groupCodeColumn))
			oReport.AddStepResult("Deleting Group Code ","Clicked on delete group code but that respective group code is not deleted ", "FAIL");
		else
			oReport.AddStepResult("Group Code is ", "Group Code is Deleted", "PASS");
	}

	By createdFirstGroupCodeDeleteIcon = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']//a[@icon='minus-square']/i[@class='left fa fa-minus-square']");

	By deleteGroupCodePopup = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Delete Group Code Entry')]");

	By deleteGroupCodePopupDelete = By.xpath("//div[@class='form-button-panel']//input[@value='Delete']");

	
	// Again Creating Group Codes
	public void againCreatingGroupCodes() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(2);
		click_button("Add a Schedule Entry Link", addGroupCodeScheduleEntryLink);
		fixed_wait_time(2);
		waitFor(addGroupCodeScheduleWindow, "Add a Group Code Entry window");
		enter_text_value("Group Code Text Box", groupCodeTextBox, get_random_alphanumeric(3));
		oParameters.SetParameters("GroupCodeValue", get_field_value("Group Code Value", groupCodeTextBox));
		enter_text_value("Amount Text Box", groupCodeAmountTextBox, get_random_number(2));
		oParameters.SetParameters("GroupCodeAmount", get_field_value("Group Code Amount", groupCodeAmountTextBox));
		click_button("Save Group Code Entry Schedule", groupCodeEntryWindowSave);

		By groupCodeRatesColumn = By.xpath("//tr[@class='hand-cursor position-relative ng-scope']/td[text()='"+ oParameters.GetParameters("GroupCodeValue") + "']");

		waitFor(groupCodeRatesColumn, "Group code rates schedules Codes table");
		fixed_wait_time(3);
		oParameters.SetParameters("NewGroupCodeValue", get_field_value("Group Code Value", groupCodeRatesColumn));
		mouse_hover("New Schedule", groupCodeRatesColumn);

		if(IsDisplayed("Delete Icon", createdFirstGroupCodeDeleteIcon))
			oReport.AddStepResult("Group Code Delete Icon ", "Group Code Delete Icon is Displayed", "PASS");
		else
			oReport.AddStepResult("Group code Delete icon ","Mouse Hovered to Created Group Code but Delete Icon is Not Displayed", "FAIL");

		// Clicking on Delete Group Code Icon

		click_button("Group Code Delete Icon", createdFirstGroupCodeDeleteIcon);
		waitFor(deleteGroupCodePopup, "Delete Group Code Popup");

		if(IsDisplayed("Delete Group Code Popup", deleteGroupCodePopup))
			oReport.AddStepResult("Delete Group Code Entry pop up ", "Delete Group Code Entry pop up is Displayed","PASS");
		else
			oReport.AddStepResult("Delete Group Code Entry pop up ","Clicked on group code delete icon but Delete Group Code Entry pop up is not displayed ", "FAIL");

		// Deleting Group Code

		click_button("Delete Group Code", deleteGroupCodePopupDelete);
		fixed_wait_time(4);
		waitFor(addGroupCodeScheduleEntryLink, "Add a Group Code Schedule Entry button");

		if(IsDisplayed("Deleted Group Code", groupCodeRatesColumn))
			oReport.AddStepResult("Deleting group code ","Clicked on group code delete but that respective Group Code is not Deleted ", "FAIL");
		else
			oReport.AddStepResult("Created Group Code ", "Created Group Code is  Deleted", "PASS");

		fixed_wait_time(2);
		click_button("Add a Schedule Entry Link", addGroupCodeScheduleEntryLink);
		fixed_wait_time(2);
		waitFor(addGroupCodeScheduleWindow, "Add a Group Code Entry window");
		enter_text_value("Group Code Text Box", groupCodeTextBox, get_random_alphanumeric(3));
		oParameters.SetParameters("GroupCodeValue", get_field_value("Group Code Value", groupCodeTextBox));
		enter_text_value("Amount Text Box", groupCodeAmountTextBox, get_random_number(2));
		oParameters.SetParameters("GroupCodeAmount", get_field_value("Group Code Amount", groupCodeAmountTextBox));
		click_button("Save Group Code Entry Schedule", groupCodeEntryWindowSave);
		By groupCodeColumn1 = By.xpath("//tr[@class='hand-cursor position-relative ng-scope']/td[text()='"+oParameters.GetParameters("GroupCodeValue")+"']");
		waitFor(groupCodeColumn1, "Group code Rates Schedule codes table");
	}

	
	By groupCodeRatesCodeRows = By.xpath("//tr[@class='hand-cursor position-relative ng-scope']");

	By groupCodeScheduleExport = By.xpath("//div[@ng-show='selectedPeriod.id']//a[@title='Export']/i[@class='left fa fa-cloud-download']");

	By groupCodeScheduleImport = By.xpath("//div[@ng-show='selectedPeriod.id']//a[@title='Import']/i[@class='left fa fa-cloud-upload']");

	By groupCodeEntriesWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Import Group Code Entries']");

	By downloadSampleGroupCodeXLSX = By.xpath("//div[@id='importGroupCodeFormId']//a[2][text()='Download a sample .XLSX']");

	By groupCodeScheduleChooseFile = By.xpath("//div[@id='importGroupCodeFormId']//input[@id='groupCodeEntryIframe']");

	By importGroupCodeScheduleSave = By.xpath("//input[@id='importGroupCodeRateEntryId'][@value='Save']");

	By groupCodeRatesFirstRow = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']");

	
	// Group Code Rates Schedule Exporting
	public void groupCodeSchedulesExport() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("GroupCodeScheduleName",get_field_value("Opened Schedule", groupCodeRatesScheduleTitle));
		oParameters.SetParameters("AllGroupCodeRates", String.valueOf(get_table_row_count(groupCodeRatesCodeRows)));
		click_button("Export Schedule", groupCodeScheduleExport);
		fixed_wait_time(4);

		@SuppressWarnings("static-access")
		int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
		oParameters.SetParameters("RecordsInExcel", String.valueOf(recordsCount - 1));
		fixed_wait_time(4);

		if(oParameters.GetParameters("AllGroupCodeRates").equals(oParameters.GetParameters("RecordsInExcel")))
			oReport.AddStepResult("Exporting Group code ","All the codes displayed under Group Code Rates Downloaded in Excel", "PASS");
		else
			oReport.AddStepResult("Exporting Group code ","Clicked on export group code but all the codes displayed under Group Code Rates not downloaded in Excel file","FAIL");
	}
	
	
	//Group Code Rates Schedule Import
	public void groupCodeSchedulesImport()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Schedule Importing

		click_button("Schedule Import", groupCodeScheduleImport);
		waitFor(groupCodeEntriesWindow, "Import Group Code Entries window");

		if(IsElementDisplayed("Group Code Entries Window", groupCodeEntriesWindow))
			oReport.AddStepResult("Import Group Code Entries window ", "Import Group Code Entries window is Displayed","PASS");
		else
			oReport.AddStepResult("Import Group Code Entries window ","Clicked on Import but that Import Group Code Entries window is not displayed", "FAIL");

		// downloading a sample XLSX file

		click_button("Download a sample XLSX", downloadSampleGroupCodeXLSX);
		fixed_wait_time(4);

		click_button("Choose File Button", groupCodeScheduleChooseFile);
		fixed_wait_time(4);

		try 
		{
		//	Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Schedules_GroupCodeRates_sampleGroupCodeForUpload.exe");
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Schedules_GroupCodeRates_sampleGroupCodeWithoutLsCode.exe");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		waitFor(importGroupCodeScheduleSave, "Import Group Code Entries window save");
		click_button("Schedule Save Button", importGroupCodeScheduleSave);
		waitFor(groupCodeRatesFirstRow, "Group Code Rate Schedules Codes Table");

		if(IsDisplayed("Group Code Schedule Imported", groupCodeRatesFirstRow))
			oReport.AddStepResult("Imported Group Code is uploaded ", "Imported Group Code is uploaded Successfully","PASS");
		else
			oReport.AddStepResult("Uploading group code rates file ","Clicked on Choose file button and choosed the appropriate file but that respective Group Code is not uploaded successfully ","FAIL");

		File xlsxFile = new File("C:\\CCM\\Downloads\\sampleGroupCodeWithoutLsCode.xlsx");
		xlsxFile.delete();

		File importedFile = new File("C:\\CCM\\Downloads\\" + oParameters.GetParameters("GroupCodeScheduleName") + ".xlsx");
		importedFile.delete();
	}

	public By LSCodeCheckBox = By.xpath("//div[@class='form-group pad-l-0 mar-t-10 ng-isolate-scope'][@model='groupCode.lsCode']//input");

	By downloadGroupCodeXLSXFile = By.xpath("//body[@style='overflow: hidden']//a[@id='downloadGroupCodeSampleFile']");

	public By ChooseGroupCodeScheduleFile = By.xpath("//body[@style='overflow: hidden']//input[@id='groupCodeEntryIframe']");

	By groupCodeScheduleTableRow = By.xpath("//*[@id='addEditGroupCodeEntryParent']//thead/tr");

	
	// Again adding Group Code Rates Schedule with LS Code
	public void addingGroupCodeRatesScheduleWithLSCode() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Group Code Name", groupCodeNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("GroupCodeName", get_field_value("Group Code Name", groupCodeNameTextBox));
		enter_text_value("Group Code Start Date", groupCodeEffectiveDate, get_specified_date(0));
		performKeyBoardAction("ENTER");
		enter_text_value("Group Code End Date", groupCodeTerminationDate, get_specified_date(2));
		performKeyBoardAction("ENTER");
		facilityName("SCHEDULES", "Apollo srn facility");	
		click_button("LS Code Check Box", LSCodeCheckBox);

		driver.switchTo().frame("importGroupCodeRateIframe");
		
/*		click_button("Download XLSX Link", downloadGroupCodeXLSXFile);
		fixed_wait_time(4);
		
		int columnCount = oExcelData.getColumnCount(0, "C:\\CCM\\sampleGroupCode.xlsx", 1);
		
		String [] columnDataArray = new String[columnCount];
		
		for(int i=0; i<columnCount;i++)
		{
			String cellData = ExcelData.getCellData(0, i, 2, "C:\\CCM\\sampleGroupCode.xlsx");
			columnDataArray[i]= cellData;
		}
		  
		String columnDataString = String.join(",", columnDataArray).replace(",", " ");
		
		if(columnDataString.contains("LS Code"))
			oReport.AddStepResult("File is downloaded ", "with LS Code", "PASS");
		else
			oReport.AddStepResult("Checked Use LS Code for this Period file but file is downloaded ", "Without LS Code", "FAIL");*/  
		
		click_button("Upload File Button", ChooseGroupCodeScheduleFile);
		fixed_wait_time(5);

		driver.switchTo().defaultContent();

		try 
		{
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Schedules_GroupCodeRates_sampleGroupCodeForUpload.exe");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		waitFor(groupCodeWindowSave, "Group Code Window Save button");
		click_button("Group Code Save", groupCodeWindowSave);
		fixed_wait_time(3);
		waitFor(groupCodeScheduleTableRow, "Group code Rate Schedule Codes Table");

		oParameters.SetParameters("GroupCodeScheduleTableColumns",get_field_value("Table Rows", groupCodeScheduleTableRow));

		if(oParameters.GetParameters("GroupCodeScheduleTableColumns").contains(oParameters.GetParameters("LSCode")))
			oReport.AddStepResult("New Group Code ", "New Group Code is added with LS Code", "PASS");
		else
			oReport.AddStepResult("Uploading new schedule ","Schedule downloaded with LS Code but while Importing that schedule New Group Code is Not added with LS Code","FAIL");

		File xlsxFile = new File("C:\\CCM\\Downloads\\sampleGroupCode.xlsx");
		xlsxFile.delete();
	}

	// Clicking on Add a Group Code entry link verifying amount field drop down
	public void addaGroupCodeEntryWindowAmountDropDown() 
	{

	}

	// Importing file
	public void importingGroupCodeScheduleFile() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Group Code Name", groupCodeNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("GroupCodeName", get_field_value("Group Code Name", groupCodeNameTextBox));
		enter_text_value("Group Code Start Date", groupCodeEffectiveDate, get_specified_date(0));
		performKeyBoardAction("ENTER");
		enter_text_value("Group Code End Date", groupCodeTerminationDate, get_specified_date(2));
		performKeyBoardAction("ENTER");
		facilityName("SCHEDULES", "Apollo srn facility");
		driver.switchTo().frame("importGroupCodeRateIframe");
		click_button("Upload File Button", ChooseGroupCodeScheduleFile);
		fixed_wait_time(4);

		driver.switchTo().defaultContent();

		try 
		{
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Schedules_GroupCodeRates_sampleGroupCodeWithoutLsCode.exe");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		click_button("Group Code Save", groupCodeWindowSave);
		waitFor(groupCodeScheduleTableRow, "Group code Rate Schedule Codes Table");

		oParameters.SetParameters("GroupCodeScheduleTableColumns",get_field_value("Table Rows", groupCodeScheduleTableRow));

		if(oParameters.GetParameters("GroupCodeScheduleTableColumns").contains(oParameters.GetParameters("GroupCode")))
			oReport.AddStepResult("New Group Code ", "New Group Code is added ", "PASS");
		else
			oReport.AddStepResult("New Group Code ","Downloaded Schedule with LS Code is Imported but that New Group Code is Not added with LS Code","FAIL");
	}

	
	By groupCodeScheduleSearch = By.xpath("//input[@title='Type search criteria and click enter to search'][@placeholder='Search Group Codes']");

	By groupCodeColumn = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']/td[1]");

	By groupCodeElement = By.xpath("//ul[@class='dropdown-menu ng-scope am-fade bottom-left']//li/a[contains(.,'Group code')]");

	
	// Filter Reports with LS Code Schedule
	// Group code Filter Reports
	public void groupCodeReports(String scheduleName, By groupCodeColumnEle, By groupCodeEle) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Group Code Schedule Search", groupCodeScheduleSearch, scheduleName);// Schedule Name
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		waitFor(firstSchedule, "Group Code Rates first Schedules");
		click_button("First Schedule", firstSchedule);
		waitFor(groupCodeColumnEle, "Group Code rates Schedule Codes Table");
		oParameters.SetParameters("GroupCodeBeforeFilter",get_field_value("Group Code Before Filter", groupCodeColumnEle));// Group Code Column
		fixed_wait_time(3);
		click_button("Add Filter", addFilterElement);
		click_button("Filters Drop Down", filtersDropDown);
		click_button("Group Code Element", groupCodeEle); // Group code Element
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("GroupCodeBeforeFilter"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		waitFor(groupCodeColumnEle, "Group Code rates Schedule Codes Table"); // Group Code column
		oParameters.SetParameters("GroupCodeAfterFilter",get_field_value("Group Code After Filter", groupCodeColumnEle)); // Group Code column

		if(oParameters.GetParameters("GroupCodeBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("GroupCodeAfterFilter")))
			oReport.AddStepResult("Schedule Code Based on Group Code Filter Reports","Schedule Code Based on Group Code Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Group Code reports ","Schedule Code Filtered based on Group Code but those reports are not Displayed", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By groupCodeScheduleAmountColumn = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']/td[2]");

	By groupCodeScheduleAmountColumnWithLS = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']/td[3]");

	By groupCodeScheduleAmountElement = By.xpath("//ul[@class='dropdown-menu ng-scope am-fade bottom-left']//li/a[contains(.,'Amount')]");

	
	// Amount Filter Reports
	public void groupCodeScheduleAmountReports(By groupCodeAmountColumnEle, By groupCodeAmountEle) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("AmounteBeforeFilter",get_field_value("Amount Before Filter", groupCodeAmountColumnEle).replace("$", ""));// Group Code Column
		fixed_wait_time(3);
		click_button("Add Filter", addFilterElement);
		click_button("Filters Drop Down", filtersDropDown);
		click_button("Amount Element", groupCodeAmountEle); // Group Code Element 
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("AmounteBeforeFilter"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		waitFor(groupCodeAmountColumnEle, "Group Code Rates Schedule Codes Table");// Group Code Column
		oParameters.SetParameters("AmountAfterFilter",get_field_value("Amount After Filter", groupCodeAmountColumnEle).replace("$", ""));// Group Code Column

		if(oParameters.GetParameters("AmounteBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("AmountAfterFilter")))
			oReport.AddStepResult("Amount Reports", "Schedule Code Based on Amount Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Amount Reports","Schedule Code Filtered based on Amount but those reports are not Displayed", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By groupCodeScheduleNameColumn = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']/td[4]");

	By groupCodeScheduleNameColumnWithLS = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']/td[5]");

	By groupCodeScheduleNameElement = By.xpath("//ul[@class='dropdown-menu ng-scope am-fade bottom-left']//li/a[contains(.,'Name')]");

	
	// Name Filter Reports
	public void NameReports(By groupCodeNameEle, By groupCodeEle) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("NameBeforeFilter", get_field_value("Name Before Filter", groupCodeNameEle)); // Name Column
		fixed_wait_time(3);
		click_button("Add Filter", addFilterElement);
		click_button("Filters Drop Down", filtersDropDown);
		click_button("Name Element", groupCodeEle);// Name Element
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("NameBeforeFilter"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		waitFor(groupCodeNameEle, "Group Code Rates Schedule Codes Table");// Name Column
		oParameters.SetParameters("NameAfterFilter", get_field_value("Name After Filter", groupCodeNameEle));// Name Column

		if(oParameters.GetParameters("NameBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("NameAfterFilter")))
			oReport.AddStepResult("Name Reports", "Schedule Code Based on Name Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Name Reports","Schedule Code Filtered based on Name but those reports are not Displayed", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	By LSCodeColumn = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']/td[2]");

	By LSCodeElement = By.xpath("//ul[@class='dropdown-menu ng-scope am-fade bottom-left']//li/a[contains(.,'LS Code')]");

	
	// LS Code Filter Reports()
	public void LSCodeReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("LSCodeBeforeFilter", get_field_value("LS Code Before Filter", LSCodeColumn));
		fixed_wait_time(3);
		click_button("Add Filter", addFilterElement);
		click_button("Filters Drop Down", filtersDropDown);
		click_button("LS Code Element", LSCodeElement);
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("LSCodeBeforeFilter"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		waitFor(LSCodeColumn, "Group Code Rates Schedule Codes Table");
		oParameters.SetParameters("LSCodeAfterFilter", get_field_value("LS Code After Filter", LSCodeColumn));

		if(oParameters.GetParameters("LSCodeBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("LSCodeAfterFilter")))
			oReport.AddStepResult("LS Code Reports", "Schedule Code Based on LS Code Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("LS Code Reports","Schedule Code Filtered based on LS Code but those reports are not Displayed", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By percentColumn = By.xpath("//tr[3][@class='hand-cursor position-relative ng-scope']/td[3]");

	By percentElement = By.xpath("//ul[@class='dropdown-menu ng-scope am-fade bottom-left']//li/a[contains(.,'Percent')]");

	
	// Percent Filter Reports
	public void percentReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("PercentBeforeFilter",get_field_value("Percent Before Filter", percentColumn).replace("%", ""));
		fixed_wait_time(3);
		click_button("Add Filter", addFilterElement);
		click_button("Filters Drop Down", filtersDropDown);
		click_button("Percent Element", percentElement);
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("PercentBeforeFilter"));
		performKeyBoardAction("ENTER");

		By percentColumn1 = By.xpath("//tr[1][@class='hand-cursor position-relative ng-scope']/td[3]");

		fixed_wait_time(3);
		waitFor(percentColumn, "Group Code Rates Schedule Codes Table");
		oParameters.SetParameters("PercentAfterFilter",get_field_value("Percent After Filter", percentColumn1).replace("%", ""));

		if(oParameters.GetParameters("PercentBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("PercentAfterFilter")))
			oReport.AddStepResult("Percent Reports", "Schedule Code Based on Percent Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Percent Reports","Schedule Code Filtered based on Percent but those reports are not Displayed", "FAIL");

		click_button("Clear Filters", filtersCloseButton);
	}

	
	By noOfCodes = By.xpath("//div[@class='pad-r-5 col-sm-6 col-md-6 col-lg-6 text-right xl-header ng-binding']");

	By noOfCodesDC = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-12 text-right large-document-header ng-binding']");

	
	// Group Code Rates Schedule Page Navigations
	public void groupCodeSchedulePageNavigations() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Schedule Search", groupCodeScheduleSearch,oParameters.GetParameters("ScheduleForPageNavigations"));
		fixed_wait_time(3);
		waitFor(firstSchedule, "Group Code Rates First Schedule");
		click_button("First Schedule", firstSchedule);
		fixed_wait_time(3);
		waitFor(addGroupCodeRatesScheduleButton, "Add Group Code Rates Schedule Button");
		oParameters.SetParameters("NumOfSchedules",get_field_value("No.Of Schedules", noOfCodes).replaceAll("[Codes::, Codes:]", ""));
		oParameters.SetParameters("LastPageNumber", get_field_value("Last Page No.", lastPageButton));
		int schedulesCount = Integer.parseInt(oParameters.GetParameters("NumOfSchedules"));
		int lastPageNumber = Integer.parseInt(oParameters.GetParameters("LastPageNumber"));

		int lastPageNum = lastPageNumber - 1;

		if(schedulesCount / lastPageNum >= 20)
			oReport.AddStepResult("20 Fee Schedules in each page", "20 Fee Schedules in each page Displayed", "PASS");
		else
			oReport.AddStepResult("20 Fee Schedules in each page","Selected the period which has more than 50 Fee Schedules but in that 20 Fee Schedules are not displayed in each page","FAIL");

		// Second page

		if(IsDisplayed("Second Page Button", secondPageButton)) 
		{
			click_button("Second Page Button", secondPageButton);
			waitFor(selectedSecondPage, "selected Second Page button");

			if(IsDisplayed("Selected Second Page", selectedSecondPage))
				oReport.AddStepResult("Clicked Page Results", "Clicked Page Results Displayed", "PASS");
			else
				oReport.AddStepResult("Clicked Page Results","Clicked on Second page but that respective records not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Page Button", "No Next Pages", "INFO");

		// Previous Page

		if(IsDisplayed("Prev Page Button", prevPageButton)) 
		{
			click_button("Prev Page Button", prevPageButton);
			waitFor(secondPageButton, "second Page Button");

			if(IsDisplayed("Next Page Button", nextPageButton))
				oReport.AddStepResult("Prev Page Results", "Prev Page Results Displayed", "PASS");
			else
				oReport.AddStepResult("Prev Page Results","Clicked on Prev page but that respective records not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Prev Page Button", "No Prev Page", "INFO");

		// Next Page

		if(IsDisplayed("Next Page Button", nextPageButton)) 
		{
			click_button("Next Page Button", nextPageButton);
			waitFor(prevPageButton, "prev Page Button");

			if(IsDisplayed("Prev Page Button", prevPageButton))
				oReport.AddStepResult("Next Page Results", "Next Page Results Displayed", "PASS");
			else
				oReport.AddStepResult("Next Page Results","Clicked on Next page but that respective records not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Next Page Button", "No Next Page", "INFO");
	}

	By procedureToUseDropDown = By.xpath("//select[@id='proceduresToUse']");

	By HCPCSCodeSetSearchBox = By.xpath("//input[@id='codeSet']");

	By groupCodeRateSet = By.xpath("//input[@id='groupCodeRateMaster']");

	By HCPCSGroupCodeFirstSearchValue = By.xpath("//div[@model='term.rate.procedureGroupMasterID']//ul/li[1]");
	

	// Procedure Group Rate Type Rate Sheet
	public void procedureGroupRateTypeGroupCode() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(rateTypeDropDown, "Rate Type DropDown");
		select_option("Rate Type Drop down", rateTypeDropDown, oParameters.GetParameters("ProcedureCodeValueOption"));
		waitFor(procedureToUseDropDown, "Procedures to Use dropdown");
		select_option("Rate Type Drop down", procedureToUseDropDown,oParameters.GetParameters("ProcedureToUseValueOption"));		
		click_button("HCPCS/CPT Code Set search box", HCPCSCodeSetSearchBox);
		performKeyBoardAction("ENTER");
		click_button("HCPCS Code Set first search value", HCPCSGroupCodeFirstSearchValue);		
		//enter_text_value("HCPCS Code Set", HCPCSCodeSetSearchBox, oParameters.GetParameters("ExistedFeeScheduleName"));
		enter_text_value("Group Code Rate Set", groupCodeRateSet, oParameters.GetParameters("GroupCodeName"));// oParameters.GetParameters("ExistedFeeScheduleName"));
		enter_text_value("Add Term Name", termNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("NewTermName", get_field_value("New Term Name", termNameTextBox));
		fixed_wait_time(2);

		if(IsDisplayed("Disabled Save", addTermWindowDisabledSave)) 
		{
			oReport.AddStepResult("Disabled Save","Filled all the mandatory fields to add the Term under Rate Sheet Section but that Add Term window Save Button is Disabled  ","FAIL");

			click_button("Add Term Window Cancel", addTermWindowCancel);
			click_button("Unsaved Window Discard", unsavedWindowDiscard);
		}
		else 
		{
			click_button("Save", addTermWindowSave);

			By addedNewTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+ oParameters.GetParameters("NewTermName") + "')]");

			fixed_wait_time(4);
			waitFor(addedNewTerm, "Added Term");
			mouse_hover("Added Term", addedNewTerm);

			if(IsDisplayed("Added New Term", addedNewTerm))
				oReport.AddStepResult("New Term is ", "New Term is Added", "PASS");
			else
				oReport.AddStepResult("New Term is ","Filled all mandatory fields and clicked on save but that new term is not added", "FAIL");
		}
	}

	
	// Group Code Rates Schedules VR
	public void groupCodeRatesSchedules_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigateToSchedules();
		selectSchedule("Group Code Rate Schedule", "Add a Group Code Rate Schedule Button", groupCodeRatesSchedulesOption, addGroupCodeRatesScheduleButton);
		selectFirstGroupCodeRatesSchedule();
		addGroupCodeRatesScheduleButton();
		groupCodeRatesScheduleDetails();
		addGroupCodeRatesScheduleButton();
		groupCodeCancelScenario();
		editGroupCodeSchedule();
		editGroupCodeRatesSchedulesPeriod();
		groupCodeRatesSchedulesNewPeriodValidation();
		createNewGroupCodeRatesSchedule();
		againCreatingGroupCodes();
		groupCodeSchedulesExport();
		groupCodeSchedulesImport();
		addGroupCodeRatesScheduleButton();
		addingGroupCodeRatesScheduleWithLSCode();
		addGroupCodeRatesScheduleButton();
		importingGroupCodeScheduleFile();
		groupCodeReports("GroupCode With LS(Do Not Delete)", groupCodeColumn, groupCodeElement);
		groupCodeScheduleAmountReports(groupCodeScheduleAmountColumnWithLS, groupCodeScheduleAmountElement);
		NameReports(groupCodeScheduleNameColumnWithLS, groupCodeScheduleNameElement);
		LSCodeReports();
		groupCodeReports("GroupCode without LS(Do Not Delete)", groupCodeColumn, groupCodeElement);
		groupCodeScheduleAmountReports(groupCodeScheduleAmountColumn, groupCodeScheduleAmountElement);
		NameReports(groupCodeScheduleNameColumn, groupCodeScheduleNameElement);
		percentReports();
		groupCodeSchedulePageNavigations();
		rateSheetPlugIn();
		existingRateSheetSearch();
		addingNewTerm();
		qualificationGroupSearch();
		procedureGroupRateTypeGroupCode();
		logout();
	}

	
	// Related Procedure Schedules VR

	public By relatedProcedureSchedulesOption = By.xpath("//li[@id='i[__valueField]']/a[contains(.,'Related Procedure Schedules')]");

	By addARelatedProcedureScheduleLink = By.xpath("//a[contains(text(),'Add a Related Procedure Discount Schedule')]");

	By relatedProcedureFirstSchedule = By.xpath("//div[contains(@class,'col-lg-2 col-md-2 col-sm-2 col-xs-2 pad-r-0 large-height adjust-height')]//ul//li[1]");

	By relatedScheduleTitle = By.xpath("//div[@class='col-lg-6 col-md-6 col-sm-6 xl-header no-wrap ng-binding']");

	By relatedScheduleTitleDC = By.xpath("//div[@class='col-lg-9 col-md-9 col-sm-9 col-xs-10 large-document-header hide-overflow no-wrap ng-binding']");

	
	// Selecting Related Procedure Schedule
	public void searchRelatedProcedureSchedule() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button(" Related Procedure Schedule First Schedules", relatedProcedureFirstSchedule);
		waitFor(relatedScheduleTitle, "Related Procedure Schedule Title bar");

		if(get_field_value("First Schedule", relatedProcedureFirstSchedule).equalsIgnoreCase(get_field_value("Opened Schedule", relatedScheduleTitle))) 
		{
			waitFor(relatedScheduleTitle, "Related Procedure Schedule Title bar");
			oReport.AddStepResult("Schedule ", "Schedule opened successfully", "PASS");
		}
		else 
		{
			waitFor(relatedScheduleTitle, "Related Procedure Schedule Title bar");
			oReport.AddStepResult("Search result","Clicked on first Schedule but that particular schedule is not opened", "FAIL");
		}
	}
	

	public By addRelatedProcedureScheduleButton = By.xpath("//div[@class='list-header drillable-header ng-scope ng-binding'][contains(.,'Related Procedure Schedule')]");

	public By relatedScheduleNameBox = By.xpath("//input[@id='scheduleName'][@name='scheduleName']");

	public By relatedScheduleStartDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='effectiveDateRelatedSchedules']");

	public By relatedScheduleEndDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='terminationDateRelatedSchedules']");

	public By relatedScheduleDiscount = By.xpath("//input[@id='discountRelatedSchedule']");

	By relatedScheduleAddPeriodDiscount = By.xpath("//form[@id='relatedSchedulePeriodFrom']//input[@id='OPLDiscount']");

	
	// Adding related Procedure Schedule
	public void addingRelatedProcedureSchedule() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add Related Procedure Schedule button", addRelatedProcedureScheduleButton);
		waitFor(relatedScheduleNameBox, "Related Procedure Schedule name text box");
		enter_text_value("Related Schedule Name", relatedScheduleNameBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("RelatedScheduleName",get_field_value("Related Schedule Name", relatedScheduleNameBox));
		facilityName("SCHEDULES", "Apollo srn facility");
		enter_text_value("Related Schedule Start Date", relatedScheduleStartDate, get_specified_date(0));
		enter_text_value("Related Schedule End Date", relatedScheduleEndDate, get_specified_date(2));
		performKeyBoardAction("ENTER");
		enter_text_value("Related Schedule Discount", relatedScheduleDiscount,oParameters.GetParameters("RelatedScheduleDiscount"));
		click_button("Related Schedule Save", scheduleSaveButton);
		waitFor(relatedScheduleTitle, "Related Procedure Schedule Title bar");

		if(oParameters.GetParameters("RelatedScheduleName").equalsIgnoreCase(get_field_value("Related Schedule Title", relatedScheduleTitle))) 
		{
			waitFor(relatedScheduleTitle, "Related Procedure Schedule Title bar");
			oReport.AddStepResult("New Related Schedule ","New Related Procedure Schedule is Added successfully and displayed ", "PASS");
		}
		else 
		{
			waitFor(relatedScheduleTitle, "Related Procedure Schedule Title bar");
			oReport.AddStepResult("New Related Schedule ","Filled all the mandatory fields Related Procedure Schedule and clicked on save but that new Related Schedule is not added and displayed ","FAIL");
		}
	}

	
	By relatedScheduleEditIcon = By.xpath("//a[@title='Edit Related Schedule']/i[@class='left fa fa-pencil']");

	
	// Editing Related Schedule Name and Discount
	public void editingRelatedScheduleNameAndDiscount() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Related Schedule Edit", relatedScheduleEditIcon);
		enter_text_value_without_clear("Related Schedule Name Editing", relatedScheduleNameBox,oParameters.GetParameters("EditRelatedScheduleName"));
		enter_text_value("Related Schedule Discount", relatedScheduleDiscount,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
		click_button("Related Schedule Save", scheduleSaveButton);
		waitFor(relatedScheduleTitle, "Related Procedure Schedule Title bar");

		if(oParameters.GetParameters("RelatedScheduleName").equalsIgnoreCase(get_field_value("Related Schedule Title", relatedScheduleTitle)))
			oReport.AddStepResult("Editing Name and Discount","Clicked on Edit then modified schedule name, Discount and clicked on save but that schedule name not modified ","FAIL");
		else
			oReport.AddStepResult("Editing Name and Discount", "Related Schedule Name and Discount Modified", "PASS");
	}

	
	// Editing Related Schedule Period Dated
	public void relatedSchedulePeriodDatesValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Period Drop Down", periodDropDown);
		waitFor(addPeriodLink, "Add Period Button");

		if(IsDisplayed("Add Period Option", addPeriodLink) && IsDisplayed("Given Effective Date", periodDate))
			oReport.AddStepResult("Add Period Option and Date", "Add Period Option and Date Displayed", "PASS");
		else
			oReport.AddStepResult("Period Drop Down ","Clicked on period drop down but that given effective date is not displayed along with Add Period option","FAIL");

		// Period Date Edit and Delete Icon

		mouse_hover("Period Date", periodDate);

		if(IsDisplayed("Edit Icon", periodDateEditIcon) && IsDisplayed("Delete Icon", periodDateDeleteIcon))
			oReport.AddStepResult("Period Date Edit and Delete Icon", "Period Date Edit and Delete Icon Displayed","PASS");
		else
			oReport.AddStepResult("Edit and Delete Icon ","Mouse hovered to Period Date but Edit and Delete Icon not Displayed", "FAIL");

		// Editing Period Date

		click_button("Period Edit Icon", periodDateEditIcon);
		waitFor(periodDateEditWindow, "Edit Effective Period window");
		oParameters.SetParameters("PeriodEndDate", get_field_value("Period Termination Date", relatedScheduleEndDate));
		enter_text_value("Edit Period Start Date", relatedScheduleStartDate,oParameters.GetParameters("PeriodEndDate"));
		performKeyBoardAction("ENTER");
		click_button("Edit Window Save Button", periodEditSaveButton);
		waitFor(periodDropDown, "Related Procedure Schedules Period Dropdown");

		if(IsDisplayed("Modified Date", periodDropDown))
			oReport.AddStepResult("Modified Period Date", "Modified Period Date Saved without any error", "PASS");
		else
			oReport.AddStepResult("Modified Period Date ","Modified the dates and clicked on save but that modified dates not saved without any error","FAIL");
	}

	
	// Adding new Related Schedule Period
	public void addingRelatedSchedulePeriod() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Period Drop Down", periodDropDown);
		click_button("Add Period Option", addPeriodLink);
		waitFor(addEffectivePeriodWindow, "Add Effective Period window");
		enter_text_value("Edit Period Start Date", relatedScheduleStartDate, get_specified_date(10));
		enter_text_value("Edit Period Start Date", relatedScheduleEndDate, get_specified_date(12));
		performKeyBoardAction("ENTER");
		enter_text_value("Related Schedule Discount", relatedScheduleAddPeriodDiscount,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
		click_button("Add Period Window Save", periodEditSaveButton);
		waitFor(periodDropDown, "Related Procedure Schedules Period Dropdown");
		click_button("Period Drop Down", periodDropDown);
		mouse_hover("New Period Date", periodDate);

		if(IsDisplayed("Period Date", periodDate))
			oReport.AddStepResult("New Period to Respective schedule ", "New Period to Respective schedule Is Added","PASS");
		else
			oReport.AddStepResult("New Period to Respective schedule ","Filled all the mandatory fields and clicked on save but that new period is not added for the respective schedule type","FAIL");
	}

	
	// Deleting Related Schedule Period
	public void deletingRelatedSchedulesPeriod() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("Period Date", periodDate);
		click_button("Period Date Delete Icon", periodDateDeleteIcon);
		waitFor(deletePeriodWindow, "Delete period popup");

		if(IsDisplayed("Delete Period Notification Window", deletePeriodWindow))
			oReport.AddStepResult("Delete Period Pop up", "Delete Period Pop up Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Period Pop up","Clicked on period drop down, hovered over existing period and click on delete icon but that delete period pop up is not displayed ","FAIL");

		click_button("Pop up Delete", deletePeriod);
		fixed_wait_time(10);
		waitFor(periodDropDown, "Related Procedure Schedules Period Dropdown");
		click_button("Period Drop Down", periodDropDown);

		if(IsDisplayed("Period Date", periodDate))
			oReport.AddStepResult("Existing Period ", "Existing Period Deleted", "PASS");
		else
			oReport.AddStepResult("Deleting Period ","Clicked on Delete Existing Period but that period Is not Deleted", "FAIL");
	}
	

	public By categoryAddIcon = By.xpath("//i[@class='fa fa-plus-square pull-right pad-l-5 pad-t-2 ng-scope'][@title='Add Category']");

	public By categoryNameBox = By.xpath("//input[@id='categoryName']");

	public By categoryDiscount = By.xpath("//div[@class='form-group mar-t-10  pad-l-0 ng-isolate-scope']//input[@id='discount']");

	public By categorySave = By.xpath("//div[@class='workflow  modal-medium']//input[@id='button.saveId']");

	By categoryCancel = By.xpath("//div[@class='workflow  modal-medium']//input[@id='button.cancelId']");

	By addCategorySearchBox = By.xpath("//input[@id='categoryPlacingTerm']");

	By categoryList = By.xpath("//ul[@id='categoryPlacingTerm-list']/li[1]/a");

	By categoryListSecondOption = By.xpath("//ul[@id='categoryPlacingTerm-list']/li[2]/a");

	By firstCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[1]");

	By secondCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[2]");

	By addCategoryDropDown = By.xpath("//form[@id='addEditCategoryForm']//div[@id='styledDropdown']/a[1]");

	By addCategoryBeforeOption = By.xpath("//form[@id='addEditCategoryForm']//ul//a[contains(.,'Add Category Before')]");

	By addCategoryAfterOption = By.xpath("//form[@id='addEditCategoryForm']//ul//a[contains(.,'Add Category After')]");

//	By categoryEditIcon = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[1]//i[@class='left fa fa-pencil']");
	
	By categoryEditIcon = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li//a[@style='display: inline;']//i[@class='left fa fa-pencil']");

	By categoryDeleteIcon = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[1]//i[@class='left fa fa-minus-square']");

	By discountTitle = By.xpath("//div[@class='portal-action-bar']//div[@class='pad-r-15 pull-right xl-header ng-scope']");

	By categoryPercentageRadio = By.xpath("//input[@value='percentage'][@name='discountOptions']");
	
	By categoryPercentage = By.xpath("//input[@id='OPLDiscount']");
	
	
	
	// Category Validation
	public void categoryValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Adding Category

		click_button("Add Category Button ", categoryAddIcon);
		waitFor(categoryNameBox, "Category Name textbox");
		enter_text_value("Category Name TextBox", categoryNameBox, get_random_string(3));
		oParameters.SetParameters("CategoryName", get_field_value("Category Name", categoryNameBox));
		click_button("Category Percentage Radio", categoryPercentageRadio);
		enter_text_value("Category Percentage", categoryPercentage,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
	//	enter_text_value("Category Discount", categoryDiscount,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
		click_button("Category Save", categorySave);

		By createdCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0 pad')]//span[contains(.,'"+ oParameters.GetParameters("CategoryName") + "')]");

		waitFor(createdCategory, "Created Category");

		if(IsDisplayed("Created Category", createdCategory))
			oReport.AddStepResult("Category ", "New Category Added ", "PASS");
		else
			oReport.AddStepResult("Category ","Clicked on Add category, filled all the mandatory fields and clicked on save but New Category is not Added ","FAIL");

		// Checking the List of Categories displaying or not

		click_button("Add Category Button ", categoryAddIcon);
		waitFor(categoryNameBox, "Category Name textbox");
		enter_text_value("Category Name TextBox", categoryNameBox, get_random_string(4));
		oParameters.SetParameters("CategoryNameAfter", get_field_value("Category Name", categoryNameBox));
		click_button("Category Percentage Radio", categoryPercentageRadio);
		enter_text_value("Category Percentage", categoryPercentage,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
	//	enter_text_value("Category Discount", categoryDiscount,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
		click_button("Add Category search Box", addCategorySearchBox);
		performKeyBoardAction("ENTER");
		waitFor(categoryList, "Category Search results list ");

		if(IsDisplayed("Category List", categoryList))
			oReport.AddStepResult("Category List ", "List of categories displayed", "PASS");
		else
			oReport.AddStepResult("Category List ","Clicked on add category, filled the mandatory fields with valid values and selected Add Category After and clicked enter in text field but that list of categories is not displayed","FAIL");

		// Adding Category after previously created Category

		click_button("Previous Category ", categoryList);
		click_button("Category Save", categorySave);

		By createdAfterCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[2]//span[contains(.,'"+ oParameters.GetParameters("CategoryNameAfter") + "')]");

		waitFor(createdAfterCategory, "Created Category");

		if(IsDisplayed("New Category after Selected Category", createdAfterCategory))
			oReport.AddStepResult("Created Category", "New category added after selected category", "PASS");
		else
			oReport.AddStepResult("Created Category","Clicked on add category, filled the mandatory fields with valid values and selected Add Category After and clicked enter in text field, Selected one category and clicked on save but that new category not added after selected category ","FAIL");

		// Checking the List of Categories displaying or not

		click_button("Add Category Button ", categoryAddIcon);
		waitFor(categoryNameBox, "Category Name text box");
		enter_text_value("Category Name TextBox", categoryNameBox, get_random_string(5));
		oParameters.SetParameters("CategoryNameBefore", get_field_value("Category Name", categoryNameBox));
		click_button("Category Percentage Radio", categoryPercentageRadio);
		enter_text_value("Category Percentage", categoryPercentage,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
	//	enter_text_value("Category Discount", categoryDiscount,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
		click_button("Add Category Drop Down", addCategoryDropDown);
		click_button("Add Category Before", addCategoryBeforeOption);
		click_button("Add Category search Box", addCategorySearchBox);
		performKeyBoardAction("ENTER");
		waitFor(categoryList, "Category Search results list ");

		if(IsDisplayed("Category List", categoryList))
			oReport.AddStepResult("Category List ", "List of categories displayed", "PASS");
		else
			oReport.AddStepResult("Category List ","Clicked on add category, filled the mandatory fields with valid values and selected Add Category After and clicked enter in text field but that list of categories is not displayed","FAIL");

		// Adding Category Before previously created Category

		click_button("Previous Category ", categoryList);
		click_button("Category Save", categorySave);

		By createdBeforeCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[1]//span[contains(.,'"+ oParameters.GetParameters("CategoryNameBefore") + "')]");

		waitFor(createdBeforeCategory, "Created Category");

		if(IsDisplayed("New Category before Selected Category", createdBeforeCategory))
			oReport.AddStepResult("Created Category", "New category added before selected category", "PASS");
		else
			oReport.AddStepResult("Created Category","Clicked on add category, filled the mandatory fields with valid values and selected Add Category Before and clicked enter in text field, Selected one category and clicked on save but that new category not added after selected category ","FAIL");

		// Unsaved Changes window pop up

		click_button("Add Category Button ", categoryAddIcon);
		waitFor(categoryNameBox, "Category name tex box");
		enter_text_value("Category Name TextBox", categoryNameBox, get_random_string(3));
		click_button("Category Cancel", categoryCancel);
		waitFor(unsavedMessageWindow, "Unsaved Changes popup");

		if(IsDisplayed("Unsaved Changes Window", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved Window", "Unsaved changes pop up is displayed ", "PASS");
		else
			oReport.AddStepResult("Unsaved Window","Clicked on Add category and filled all fields. Clicked on cancel but Unsaved changes pop up is not displayed ","FAIL");

		// clicking on discard

		click_button("Discard Button", unsavedWindowDiscard);

		if(IsDisplayed("Category", categoryAddIcon))
			oReport.AddStepResult("Unsaved Window ", "Unsaved changes pop up is closed ", "PASS");
		else
			oReport.AddStepResult("Unsaved Window ","Clicked on Add category and filled all fields then Clicked on cancel button but Unsaved changes pop up is not closed ","FAIL");

		// Category Edit and Delete Icon

		mouse_hover("Created Category", firstCategory);

		if(IsDisplayed("Edit Icon ", categoryEditIcon) && IsDisplayed("Delete Icon", categoryDeleteIcon))
			oReport.AddStepResult("Edit and Delete Icon", "Edit and Delete icon is displayed ", "PASS");
		else
			oReport.AddStepResult("Edit and Delete Icon ","Hovered over a created category but Edit and Delete icon is not displayed ", "FAIL");

		// Editing Category Schedule Discount

		oParameters.SetParameters("DiscountBefore",get_field_value("Discount", discountTitle).replaceAll("[Discount : %]", ""));
		click_button("Edit Icon", categoryEditIcon);
		click_button("Category Percentage Radio", categoryPercentageRadio);
		enter_text_value("Category Percentage", categoryPercentage,get_random_number(1));
	//	enter_text_value("Discount Edit", categoryDiscount, get_random_number(1));
		oParameters.SetParameters("EditedDiscount", get_field_value("Category Discount", categoryPercentage));
		click_button("Category Save", categorySave);
		waitFor(discountTitle, "Discount Title bar");

		if(oParameters.GetParameters("DiscountBefore").equals(oParameters.GetParameters("EditedDiscount")))
			oReport.AddStepResult("Discount ","Clicked on edit category, modified Discount value and clicked on save but that modification is not saved","FAIL");
		else
			oReport.AddStepResult("Discount ", "Discount Modification is saved and displayed the same", "PASS");

		// Checking the List of Categories displaying or not

		mouse_hover("Created Category", firstCategory);
		click_button("Edit Icon", categoryEditIcon);
		waitFor(categoryNameBox, "Category Name text box");
		oParameters.SetParameters("ExistedCategoryNameforAfter", get_field_value("Category Name", categoryNameBox));
		click_button("Add Category Drop Down", addCategoryDropDown);
		click_button("Add Category Before", addCategoryAfterOption);
		enter_text_value("Category Search box", addCategorySearchBox, "");
		performKeyBoardAction("ENTER");
		waitFor(categoryList, "Category Search results list ");

		if(IsDisplayed("Category List", categoryList))
			oReport.AddStepResult("Category List ", "List of categories displayed", "PASS");
		else
			oReport.AddStepResult("Category List ","Clicked Edit category, selected Add Category After and clicked enter in text field but that list of categories is not displayed","FAIL");

		// Moving Existing category after previously created category

		click_button("Previously created Category", categoryListSecondOption);
		click_button("Category Save", categorySave);

		By movedAfterCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[2]//span[contains(.,'"+ oParameters.GetParameters("ExistedCategoryNameforAfter") + "')]");

		waitFor(movedAfterCategory, "Category list");

		if(IsDisplayed("New Category After Selected Category", movedAfterCategory))
			oReport.AddStepResult("Created Category", "Category Displayed After selected category", "PASS");
		else
			oReport.AddStepResult("Created Category","Clicked on Edit category, selected Add Category After and clicked enter in text field, Selected one category and clicked on save but that category not Displayed after selected category ","FAIL");

		// Checking the list of categories displayed or not

		mouse_hover("Created Category", firstCategory);
		click_button("Edit Icon", categoryEditIcon);
		oParameters.SetParameters("ExistedCategoryNameforBefore", get_field_value("Category Name", categoryNameBox));
		click_button("Add Category Drop Down", addCategoryDropDown);
		click_button("Add Category Before", addCategoryBeforeOption);
		enter_text_value("Category Search box", addCategorySearchBox, "");
		performKeyBoardAction("ENTER");
		waitFor(categoryList, "Category Search results list ");

		if(IsDisplayed("Category List", categoryList))
			oReport.AddStepResult("Category List ", "List of categories displayed", "PASS");
		else
			oReport.AddStepResult("Category List ","Clicked Edit category, selected Add Category Before and clicked enter in text field but that list of categories is not displayed","FAIL");

		// Moving Existing category after previously created category

		click_button("Previously created Category", categoryListSecondOption);
		click_button("Category Save", categorySave);

		By movedBeoforeCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[1]//span[contains(.,'"+ oParameters.GetParameters("ExistedCategoryNameforBefore") + "')]");

		waitFor(movedBeoforeCategory, "Category List");

		if(IsDisplayed("New Category before Selected Category", movedBeoforeCategory))
			oReport.AddStepResult("Created Category", "Category Displayed After selected category", "PASS");
		else
			oReport.AddStepResult("Created Category","Clicked on Edit category, selected Add Category Before and clicked enter in text field, Selected one category and clicked on save but that category not Displayed before selected category ","FAIL");

		// Clicking on edit category then closing that window

		mouse_hover("Created Category", firstCategory);
		click_button("Edit Icon", categoryEditIcon);
		waitFor(categoryCancel, "Edit Category window Cancel ");
		click_button("Category Cancel", categoryCancel);

		if(IsDisplayed("Category Cancel", categoryNameBox))
			oReport.AddStepResult("Category Edit window","Clicked on edit category and clicked on cancel but edit category window is not closed", "FAIL");
		else
			oReport.AddStepResult("Category Edit Window", "Edit category window is closed", "PASS");
	}

	
	By categorySearchBox = By.xpath("//input[@placeholder='Search Category']");

	
	// Searching with existing category
	public void categorySearch() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Category Searh", categorySearchBox,oParameters.GetParameters("ExistedCategoryNameforBefore"));
		performKeyBoardAction("ENTER");
		waitFor(firstCategory, "Category list");

		By movedBeoforeCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-l-0')]//li[1]//span[contains(.,'"+ oParameters.GetParameters("ExistedCategoryNameforBefore") + "')]");

		waitFor(movedBeoforeCategory, "Category list");

		if(IsDisplayed("Category Search Result", movedBeoforeCategory)) 
		{
			waitFor(movedBeoforeCategory, "Category list");
			oReport.AddStepResult("Category Searh",	"Searched with Existing category that respective category is displayed", "PASS");
		}
		else 
		{
			fixed_wait_time(2);
			oReport.AddStepResult("Category Searh","Searched with Existing category but the respective category is not displayed", "FAIL");
		}
		
		enter_text_value("category Search", categorySearchBox, "");
		performKeyBoardAction("ENTER");
		waitFor(firstCategory, "Category List");
	}

	
	public By subCategoryAddIcon = By.xpath("//i[@class='pull-right pad-t-2 pad-r-5 fa fa-plus-square ng-scope'][@title='Add Subcategory']");

	public By subCategoryNameBox = By.xpath("//input[@id='subcategoryName']");

	By addSubcategorySearchBox = By.xpath("//input[@id='subCategoryPlacingTerm']");

	By firstSubcategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0')]//li[1]");

	By secondSubcategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0')]//li[2]");

	By subCategoryList = By.xpath("//ul[@id='subCategoryPlacingTerm-list']/li[1]/a");

	By subCategoryListSecondOption = By.xpath("//ul[@id='subCategoryPlacingTerm-list']/li[2]/a");

	By addSubcategoryButton = By.xpath("//form[@id='subCategoryForm']//div[@id='styledDropdown']/a[1]");

	By addSubcategoryBeforeOption = By.xpath("//form[@id='subCategoryForm']//ul//a[contains(.,'Add Subcategory Before')]");

	By addSubcategoryAfterOption = By.xpath("//form[@id='subCategoryForm']//ul//a[contains(.,'Add Subcategory After')]");

	By subCategoryEditIcon = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0')]//li[1]//i[@class='left fa fa-pencil']");

	By subCategoryDeleteIcon = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0')]//li[1]//i[@class='left fa fa-minus-square']");

	
	// Subcategory Validation
	public void subCategoryValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Adding Subcategory

		click_button("Add Subcategory Button ", subCategoryAddIcon);
		waitFor(subCategoryNameBox, "SubCategory Name textbox");
		enter_text_value("Subcategory Name TextBox", subCategoryNameBox, get_random_string(3));
		oParameters.SetParameters("SubCategoryName", get_field_value("Subcategory Name", subCategoryNameBox));
		click_button("Subcategory Save", categorySave);

		By createdSubcategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0 border')]//span[contains(.,'"+ oParameters.GetParameters("SubCategoryName") + "')]");

		waitFor(createdSubcategory, "Subcategory list");

		if(IsDisplayed("Created Subcategory", createdSubcategory))
			oReport.AddStepResult("Subcategory ", "New Subcategory is added", "PASS");
		else
			oReport.AddStepResult("Subcategory ","Clicked on Add Subcategory, filled all the mandatory fields and clicked on save but New Subcategory is not Added ","FAIL");

		// Checking the List of Sub categories displaying or not

		click_button("Add Subcategory Button ", subCategoryAddIcon);
		waitFor(subCategoryNameBox, "SubCategory Name textbox");
		enter_text_value("Subcategory Name TextBox", subCategoryNameBox, get_random_string(4));
		oParameters.SetParameters("SubcategoryNameAfter", get_field_value("Subcategory Name", subCategoryNameBox));
		click_button("Add Subcategory search Box", addSubcategorySearchBox);
		performKeyBoardAction("ENTER");
		waitFor(subCategoryList, "Sub Category search result list");

		if(IsDisplayed("Subcategory List", subCategoryList))
			oReport.AddStepResult("Subcategory List ", "List of Subcategories displayed", "PASS");
		else
			oReport.AddStepResult("Subcategory List ","Clicked on add Subcategory, filled the mandatory fields with valid values and selected Add Subcategory After and clicked enter in text field but that list of Subcategories is not displayed","FAIL");

		// Adding Sub category after previously created Sub category

		click_button("Previous Subcategory ", subCategoryList);
		click_button("Subcategory Save", categorySave);

		By createdAfterSubcategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0 border')]//li[2]//span[contains(.,'"+ oParameters.GetParameters("SubcategoryNameAfter") + "')]");

		waitFor(createdAfterSubcategory, "Sub category list");

		if(IsDisplayed("New Subcategory after Selected Subcategory", createdAfterSubcategory))
			oReport.AddStepResult("Created Subcategory", "New Subcategory added after selected Subcategory", "PASS");
		else
			oReport.AddStepResult("Created Subcategory","Clicked on add Subcategory, filled the mandatory fields with valid values and selected Add Subcategory After and clicked enter in text field, Selected one Subcategory and clicked on save but that new Subcategory not added after selected Subcategory ","FAIL");

		// Checking the List of Sub categories displaying or not

		click_button("Add Subcategory Button ", subCategoryAddIcon);
		waitFor(subCategoryNameBox, "Sub category name text box");
		enter_text_value("SubCategory Name TextBox", subCategoryNameBox, get_random_string(5));
		oParameters.SetParameters("SubCategoryNameBefore", get_field_value("SubCategory Name", subCategoryNameBox));
		click_button("Add SubCategory Drop Down", addSubcategoryButton);
		click_button("Add SubCategory Before", addSubcategoryBeforeOption);
		click_button("Add SubCategory search Box", addSubcategorySearchBox);
		performKeyBoardAction("ENTER");
		waitFor(subCategoryList, "Sub Category search result list");

		if(IsDisplayed("Subcategory List", subCategoryList))
			oReport.AddStepResult("Subcategory List ", "List of Subcategories displayed", "PASS");
		else
			oReport.AddStepResult("Subcategory List ","Clicked on add Subcategory, filled the mandatory fields with valid values and selected Add Subcategory Before and clicked enter in text field but that list of Subcategories is not displayed","FAIL");

		// Adding Sub category Before previously created Sub category

		click_button("Previous Subcategory ", subCategoryList);
		click_button("SubCategory Save", categorySave);

		By createdBeforeSubcategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0 border')]//li[1]//span[contains(.,'"+ oParameters.GetParameters("SubCategoryNameBefore") + "')]");

		waitFor(createdBeforeSubcategory, "Sub category list");

		if(IsDisplayed("New Subcategory before Selected Subcategory", createdBeforeSubcategory))
			oReport.AddStepResult("Created Subcategory", "New Subcategory added before selected Subcategory", "PASS");
		else
			oReport.AddStepResult("Created Subcategory","Clicked on add Subcategory, filled the mandatory fields with valid values and selected Add Subcategory Before and clicked enter in text field, Selected one Subcategory and clicked on save but that new Subcategory not added before selected Subcategory ","FAIL");

		// Unsaved Changes window pop up

		click_button("Add Subcategory Button ", subCategoryAddIcon);
		waitFor(subCategoryNameBox, "Sub category name text box");
		enter_text_value("SubCategory Name TextBox", subCategoryNameBox, get_random_string(3));
		click_button("Subcategory Cancel", categoryCancel);
		waitFor(unsavedMessageWindow, "Unsaved Changes popup");

		if(IsDisplayed("Unsaved Changes Window", unsavedMessageWindow))
			oReport.AddStepResult("Unsaved Window", "Unsaved changes pop up is displayed ", "PASS");
		else
			oReport.AddStepResult("Unsaved Window","Clicked on Add Subcategory and filled all fields. Clicked on cancel but Unsaved changes pop up is not displayed ","FAIL");

		// clicking on discard

		click_button("Discard Button", unsavedWindowDiscard);

		if(IsDisplayed("Subcategory", subCategoryAddIcon))
			oReport.AddStepResult("Unsaved Window ", "Unsaved changes pop up is closed ", "PASS");
		else
			oReport.AddStepResult("Unsaved Window ","Clicked on Add Subcategory and filled all fields then Clicked on cancel button but Unsaved changes pop up is not closed ","FAIL");

		// Category Edit and Delete Icon

		oParameters.SetParameters("SubcategoryNameBeforeEdit", get_field_value("Subcategory Name", firstSubcategory));
		mouse_hover("Created SubCategory", firstSubcategory);

		if(IsDisplayed("Edit Icon ", subCategoryEditIcon) && IsDisplayed("Delete Icon", subCategoryDeleteIcon))
			oReport.AddStepResult("Edit and Delete Icon", "Edit and Delete icon is displayed ", "PASS");
		else
			oReport.AddStepResult("Edit and Delete Icon ","Hovered over a created Subcategory but Edit and Delete icon is not displayed ", "FAIL");

		// Editing Subcategory Name

		click_button("Subcategory Edit icon", subCategoryEditIcon);
		enter_text_value("Subcategory Name Box", subCategoryNameBox, get_random_string(6));
		click_button("Subcategory Save", categorySave);
		waitFor(firstSubcategory, "Subcategory list");
		oParameters.SetParameters("SubcategoryNameAfterEdit",get_field_value("Subcategory Edited Name", firstSubcategory));

		if(oParameters.GetParameters("SubcategoryNameBeforeEdit").equalsIgnoreCase(oParameters.GetParameters("SubcategoryNameAfterEdit")))
			oReport.AddStepResult("Subcategory Name Editing","Clicked on edit Subcategory modified the name and clicked on save but Subcategory modification is not saved ","FAIL");
		else
			oReport.AddStepResult("Subcategory Name Editing","Subcategory modification is saved and displayed the same", "PASS");

		// Checking the List of Categories displaying or not

		mouse_hover("Created Subcategory", firstSubcategory);
		click_button("Edit Icon", subCategoryEditIcon);
		waitFor(subCategoryNameBox, "Sub category name text box");
		oParameters.SetParameters("ExistedSubCategoryNameforAfter",get_field_value("SubCategory Name", subCategoryNameBox));
		click_button("Add Subcategory Drop Down", addSubcategoryButton);
		click_button("Add subcategory After", addSubcategoryAfterOption);
		enter_text_value("Subcategory Search box", addSubcategorySearchBox, "");
		performKeyBoardAction("ENTER");
		waitFor(subCategoryList, "Sub Category search result list");

		if(IsDisplayed("Subcategory List", subCategoryList))
			oReport.AddStepResult("Subcategory List ", "List of Subcategories displayed", "PASS");
		else
			oReport.AddStepResult("Subcategory List ","Clicked Edit Subcategory, selected Add Subcategory After and clicked enter in text field but that list of Subcategories is not displayed","FAIL");

		// Moving Existing category after previously created category

		click_button("Previously created Subcategory", subCategoryListSecondOption);
		click_button("Subcategory Save", categorySave);

		By movedAfterSubCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0 border')]//li[2]//span[contains(.,'"+ oParameters.GetParameters("ExistedSubCategoryNameforAfter") + "')]");

		waitFor(movedAfterSubCategory, "Sub category list");

		if(IsDisplayed("New Subcategory After Selected Subcategory", movedAfterSubCategory))
			oReport.AddStepResult("Created Subcategory", "Subcategory Displayed After selected Subcategory", "PASS");
		else
			oReport.AddStepResult("Created Subcategory","Clicked on Edit Subcategory, selected Add Subcategory After and clicked enter in text field, Selected one Subcategory and clicked on save but that Subcategory not Displayed after selected Subcategory ","FAIL");

		// Checking the list of categories displayed or not

		mouse_hover("Created Subcategory", firstSubcategory);
		click_button("Edit Icon", subCategoryEditIcon);
		oParameters.SetParameters("ExistedSubCategoryNameforBefore",get_field_value("SubCategory Name", subCategoryNameBox));
		click_button("Add Category Drop Down", addSubcategoryButton);
		click_button("Add Category Before", addSubcategoryBeforeOption);
		enter_text_value("Category Search box", addSubcategorySearchBox, "");
		performKeyBoardAction("ENTER");
		waitFor(subCategoryList, "Sub Category search result list");

		if(IsDisplayed("Subcategory List", subCategoryList))
			oReport.AddStepResult("Subcategory List ", "List of Subcategory displayed", "PASS");
		else
			oReport.AddStepResult("Subcategory List ","Clicked Edit Subcategory, selected Add Subcategory Before and clicked enter in text field but that list of Subcategories is not displayed","FAIL");

		// Moving Existing category after previously created category

		click_button("Previously created Subcategory", subCategoryListSecondOption);
		click_button("Subcategory Save", categorySave);

		By movedBeoforeSubCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0 border')]//li[1]//span[contains(.,'"+ oParameters.GetParameters("ExistedSubCategoryNameforBefore") + "')]");

		waitFor(movedBeoforeSubCategory, "Sub category list");

		if(IsDisplayed("New Subcategory before Selected Subcategory", movedBeoforeSubCategory))
			oReport.AddStepResult("Created Subcategory", "Subcategory Displayed After selected Subcategory", "PASS");
		else
			oReport.AddStepResult("Created Subcategory","Clicked on Edit Subcategory, selected Add Subcategory Before and clicked enter in text field, Selected one Subcategory and clicked on save but that Subcategory not Displayed before selected Subcategory ","FAIL");

		// Clicking on edit category then closing that window

		mouse_hover("Created Subcategory", firstSubcategory);
		click_button("Edit Icon", subCategoryEditIcon);
		waitFor(categoryCancel, "Edit Sub category window cancel");
		click_button("Subcategory Cancel", categoryCancel);

		if(IsDisplayed("Subcategory Cancel", subCategoryNameBox))
			oReport.AddStepResult("Subcategory Edit window","Clicked on edit Subcategory and clicked on cancel but edit Subcategory window is not closed","FAIL");
		else
			oReport.AddStepResult("Subcategory Edit Window", "Edit Subcategory window is closed", "PASS");
	}

	
	By subCategorySearchBox = By.xpath("//input[@placeholder='Search Subcategory']");

	
	// Searching with existing Sub category
	public void subCategorySearch() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Subcategory Searh", subCategorySearchBox,oParameters.GetParameters("ExistedSubCategoryNameforBefore"));
		performKeyBoardAction("ENTER");
		waitFor(firstSubcategory, "Subcategory list");

		By movedBeoforeSubCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3 pad-r-0 border')]//li[1]//span[contains(.,'"+ oParameters.GetParameters("ExistedSubCategoryNameforBefore") + "')]");

		waitFor(movedBeoforeSubCategory, "Sub category list");

		if(IsDisplayed("Subcategory Search Result", movedBeoforeSubCategory)) 
		{
			waitFor(movedBeoforeSubCategory, "Sub category list");
			oReport.AddStepResult("Subcategory Searh","Searched with Existing Subcategory that respective Subcategory is displayed", "PASS");
		}
		else 
		{
			fixed_wait_time(2);
			oReport.AddStepResult("Subcategory Searh","Searched with Existing Subcategory but the respective Subcategory is not displayed", "FAIL");
		}
		
		enter_text_value("Subcategory Search", subCategorySearchBox, "");
		performKeyBoardAction("ENTER");
		waitFor(firstSubcategory, "Sub category list");
	}

	
	public By addProcedureLink = By.xpath("//a[@title='Add Procedure']/i[@class='left fa fa-plus']");

	public By procedureCodeBox = By.xpath("//input[@id='procedureCodesName']");

	By procedureCodeErrorWindow = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Error occurred while saving')]");
	
	By invalidProcedureCodeNotification = By.xpath("//span[@class='text-up ng-binding'][contains(.,'The procedure code does not exist.')]");
	
	By errorNotificationCloseIcon = By.xpath("//span[@class='text-up ng-binding']/..//span[@icon='times-circle']");
	
	By addProcedureWindowCancel = By.xpath("//div[@id='showProcedureCodeModal']//input[@id='button.cancelId']");

	By procedureCodeerrorWindowOKButton = By.xpath("//div[@id='dialog']//input[@value='OK']");

	
	// Procedure Code with invalid input
	public void invalidProcedureCode() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("First Category", firstCategory);
		click_button("First Subcategory", firstSubcategory);
		waitFor(addProcedureLink, "Add Procedure button");
		click_button("Add Procedure Code", addProcedureLink);
		waitFor(procedureCodeBox, "Add Procedure code text box");
		enter_text_value("Procedure Code Text Box", procedureCodeBox, get_random_alphanumeric(5));
		click_button("Procedure Code Save ", categorySave);
		
		if(IsDisplayed("Invalid Procedure Code Error Notification", invalidProcedureCodeNotification))
		{	
			oReport.AddStepResult("Error Window", "Procedure Code Error message is displayed", "PASS");
			click_button("Error Notification Close Icon", errorNotificationCloseIcon);
/*			click_button("Add Procedure Window Cancel", addProcedureWindowCancel);
			click_button("Unsaved popup Discard", addTermDiscard);*/
		}	
		else if(IsDisplayed("Error Window", procedureCodeErrorWindow))
		{
			oReport.AddStepResult("Error Window", "Procedure Code Error message is displayed", "PASS");
			click_button("Error Window OK ", procedureCodeerrorWindowOKButton);
		}
		else
			oReport.AddStepResult("Error Window","Clicked on Add Procedure gave Invalid Input Procedure Code and clicked on save but that error message is not displayed","FAIL");		
	}
	

	By addedProcedureCode = By.xpath("//div[@class='large-height scroll-auto mar-r-15 border-gray']//tr//td[contains(text(),'G0030')]");

	
	// Procedure Code with valid input
	public void validProcedureCode() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Procedure Code Text Box", procedureCodeBox, oParameters.GetParameters("ProcedureCode"));
		click_button("Procedure Code Save ", categorySave);
		waitFor(addedProcedureCode, "Added Procedure Code");

		if (IsDisplayed("Added Procedure Code", addedProcedureCode))
			oReport.AddStepResult("Procedure Code", "Procedure code is added without any error", "PASS");
		else
			oReport.AddStepResult("Procedure Code","Clicked on Add Procedure then gave Valid Input Procedure Code and clicked on save but that Procedure code is not added ","FAIL");
	}

	
	// Creating Procedure Codes for Category and Sub categories
	public void creatingProceduresCodes(By category, By subCategory, String procedureCode) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Category ", category);
		click_button("Subcategory", subCategory);
		waitFor(addProcedureLink, "Add Procedure button");
		click_button("Add Procedure Code", addProcedureLink);
		waitFor(procedureCodeBox, "Add Procedure code text box");
		enter_text_value("Procedure Code Text Box", procedureCodeBox, procedureCode);
		click_button("Procedure Code Save ", categorySave);
		waitFor(addedProcedureCode, "Added Procedure Code");
	}

	
	// Selecting Category and Sub category to see the Procedure Code
	public void procedureCodeValidation(By category, By subCategory, String categoryNumber, String SubcategoryNumber) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// selecting Category and Sub category

		click_button("Category", category);
		click_button("Subcategory", subCategory);
		waitFor(addedProcedureCode, "Added Procedure Code");

		if(IsDisplayed("Respective Procedure Codes", addedProcedureCode))
			oReport.AddStepResult("Procedure Codes",categoryNumber + SubcategoryNumber + "Selected that respective Procedure Codes are displayed ","PASS");
		else
			oReport.AddStepResult("Procedure Codes", categoryNumber + SubcategoryNumber+ "Selected but that respective Procedure Codes are not displayed ", "FAIL");
	}

	
	By procedureCodeDeleteIcon = By.xpath("//a[@title='Delete Procedure Code']/i[@class='left fa fa-minus-square']");

	
	// Deleting Procedure Code
	public void deletingProcedureCode() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Procedure Code Delete Icon

		mouse_hover("Procedure Code", addedProcedureCode);

		if (IsDisplayed("Delete Icon ", procedureCodeDeleteIcon))
			oReport.AddStepResult("Delete Icon","Hovered over the Procedure Code that Procedure Code Delete icon is displayed ", "PASS");
		else
			oReport.AddStepResult("Delete Icon","Hovered over the Procedure Code but that Procedure Code Delete icon is not displayed ", "FAIL");

		// Deleting Procedure Code

		click_button("Delete Procedure Code", procedureCodeDeleteIcon);
		click_button("Procedure Code Delete", deletePeriod);
		waitFor(addProcedureLink, "Add Procedure button");

		if (IsDisplayed("Procedure Code", addedProcedureCode))
			oReport.AddStepResult("Procedure Code","Clicked on Delete Procedure Code but that Procedure Code is Not deleted ", "FAIL");
		else
			oReport.AddStepResult("Procedure Code", "Clicked on Delete Procedure Code that Procedure Code is deleted ","PASS");
	}

	
	By importLink = By.xpath("//a[@title='import']/i[@class='left fa fa-cloud-upload']");

	By chooseFileButtonforProcedureCodes = By.xpath("//input[@id='RelatedProcedureCodeEntryIframe']");

	By errorWindowOK = By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");

	
	// Importing Procedure Code file
	public void importingProcedureCode() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Import Icon", importLink);
		waitFor(chooseFileButtonforProcedureCodes, "Import Procedure Codes window");
		click_button("Choose file", chooseFileButtonforProcedureCodes);
		fixed_wait_time(5);

		try 
		{
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Schedules_RelatedProcedureSchedules_RelatedProcedureDiscountEntries.exe");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		fixed_wait_time(5);
		click_button("Import Related Schedule Save Button", scheduleSaveButton);
		waitFor(addProcedureLink, "Add Procedure button");

		if(IsDisplayed("Procedure Code", addedProcedureCode)) 
		{
			waitFor(addedProcedureCode, "Added Procedure Code");
			oReport.AddStepResult("Procedure Code Import","Imported Procedure code file that new Procedure Code is added ", "PASS");
		}
		else 
		{
			fixed_wait_time(2);
			oReport.AddStepResult("Procedure Code Import","Clicked on Import Procedure Code then Choosed the proper file but that new Procedure Code is not added ","FAIL");
		}
	}

	
	 By addPeriodStartDate = By.xpath("//div[@class='workflow modal-medium']//input[@id='effectiveDateRelatedSchedules']");

	 By addPeriodEndDate = By.xpath("//div[@class='workflow modal-medium']//input[@id='terminationDateRelatedSchedules']");
	 
	 By periodSaveButton = By.xpath("//div[@class='workflow modal-medium']//input[@id='button.saveId']");
	
	
	// Selecting another period
	public void selectingAnotherPeriod() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Period Drop Down", periodDropDown);
		click_button("Add Period Option", addPeriodLink);
		waitFor(addEffectivePeriodWindow, "Add Effective Period window");
		enter_text_value("Edit Period Start Date", addPeriodStartDate, get_specified_date(14));
		enter_text_value("Edit Period Start Date", addPeriodEndDate, get_specified_date(16));
		performKeyBoardAction("ENTER");
		enter_text_value("Related Schedule Discount", relatedScheduleAddPeriodDiscount,oParameters.GetParameters("RelatedScheduleDiscountForEdit"));
		click_button("Add Period Window Save", periodSaveButton);
		waitFor(periodDropDown, "Related Procedure Schedules Period Dropdown");
		click_button("Period Drop Down", periodDropDown);
		click_button("New Period Date", periodDate);
		waitFor(addProcedureLink, "Add Procedure button");

		if(IsDisplayed("Procedure Code", addProcedureLink))
			oReport.AddStepResult("Procedure Code", "Respective period procedure code is displayed", "PASS");
		else
			oReport.AddStepResult("Procedure Code","Clicked on Period drop down and choosed another period but that respective period procedure code is not displayed ","FAIL");
	}

	
	By useRelatedProcedureDiscountingCheckBox = By.xpath("//div[@model='term.rate.checkedRsDiscounting']/label/input");

	By relatedProcedureScheduleSearchBox = By.xpath("//input[@id='rsMaster']");

	By addTermSaveButton = By.xpath("//div[@id='showTermAddModal']//div[@id='fullFooter']//input[@id='button.saveId']");

	
	// Rate Type as Fee Schedule
	public void addingFeeScheduleRate() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(rateTypeDropDown, "Rate Type DropDown");
		select_option("Rate Type Drop down", rateTypeDropDown, oParameters.GetParameters("FeeScheduleValueOption"));
		fixed_wait_time(3);

		enter_text_value("Choosing Fee Schedule", feeScheduleSearch,oParameters.GetParameters("ExistedFeeScheduleName"));

		enter_text_value("Add Term Name", termNameTextBox,oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
		oParameters.SetParameters("NewTermName", get_field_value("New Term Name", termNameTextBox));

		click_button("Use Related Procedure Discounting Check Box", useRelatedProcedureDiscountingCheckBox);
		waitFor(relatedProcedureScheduleSearchBox, "Related Procedure Schedule Search Box");
		scroll_to_element(relatedProcedureScheduleSearchBox, useRelatedProcedureDiscountingCheckBox);

		if(IsDisplayed("Related Procedure Schedule Search", relatedProcedureScheduleSearchBox))
			oReport.AddStepResult("Related Procedure Schedule Search","Related Procedure schedule search field displayed", "PASS");
		else
			oReport.AddStepResult("Related Procedure Schedule Search","Selected Rate Type as Fee Schedule and checked Use Related Procedure Discounting checkbox but Related Procedure schedule search field is not displayed","FAIL");

		enter_text_value("Related Procedure Schedule Search", relatedProcedureScheduleSearchBox,oParameters.GetParameters("RelatedScheduleName")+ oParameters.GetParameters("EditRelatedScheduleName"));
		fixed_wait_time(3);

		if(IsDisplayed("Disabled Save", addTermWindowDisabledSave)) 
		{
			oReport.AddStepResult("Disabled Save","Filled all the mandatory fields to add the Term under Rate Sheet Section but that Add Term window Save Button is Disabled  ","FAIL");

			click_button("Add Term Window Cancel", addTermWindowCancel);
			click_button("Unsaved Window Discard", unsavedWindowDiscard);
		}
		else 
		{
			click_button("Save", addTermSaveButton);

			By addedNewTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+ oParameters.GetParameters("NewTermName") + "')]");

			fixed_wait_time(4);
			waitFor(addedNewTerm, "Added Term");

			mouse_hover("Added Term", addedNewTerm);

			if(IsDisplayed("Added New Term", addedNewTerm))
				oReport.AddStepResult("New Term is ", "New Term is Added", "PASS");
			else
				oReport.AddStepResult("New Term is ","Filled all mandatory fields and clicked on save but that new term is not added", "FAIL");
		}
	}
	
	
	By relatedSchedule = By.xpath("//div[@id='schedulesTemplate']//div[@class='delete-parent-position']//span[contains(.,'"+oParameters.GetParameters("RelatedScheduleName")+"')]");
	
	By relatedScheduleDeleteIcon = By.xpath("//div[@id='schedulesTemplate']//div[@class='delete-parent-position']//span[contains(.,'"+oParameters.GetParameters("RelatedScheduleName")+"')]/..//i[@class='left fa fa-minus-square']");
	
	By relatedScheduleDeleteButton = By.xpath("//div[@id='dialog_buttons']/input[@value='Delete']");
		
	
	// Deleting created Schedules
	public void relatedProcedureScheduleDelete()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("Existed Related Schedule", relatedSchedule);
		click_button("Related Schedule Delete Icon", relatedScheduleDeleteIcon);
		waitFor(relatedScheduleDeleteButton, "relatedScheduleDeleteButton");
		click_button("Related Schedule Delete Button", relatedScheduleDeleteButton);	 	
	}
	
	
	// Related Procedure Schedules VR
	public void relatedProcedureSchedules_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigateToSchedules();
		selectSchedule("Related Procedure Schedule", "Add a Related Procedure Schedule Link", relatedProcedureSchedulesOption, addARelatedProcedureScheduleLink);
		searchRelatedProcedureSchedule();
		addingRelatedProcedureSchedule();
		editingRelatedScheduleNameAndDiscount();
		relatedSchedulePeriodDatesValidation();
		addingRelatedSchedulePeriod();
		deletingRelatedSchedulesPeriod();
		categoryValidation();
		categorySearch();
		subCategoryValidation();
		subCategorySearch();
		invalidProcedureCode();
		validProcedureCode();
		creatingProceduresCodes(firstCategory, secondSubcategory, "G0030");
		creatingProceduresCodes(secondCategory, firstSubcategory, "G0030");
		creatingProceduresCodes(secondCategory, secondSubcategory, "G0030");
		procedureCodeValidation(firstCategory, firstSubcategory, "First Category ", "First Subcategory ");
		procedureCodeValidation(firstCategory, secondSubcategory, "First Category ", "Second Subcategory ");
		procedureCodeValidation(secondCategory, firstSubcategory, "Second Category ", "First Subcategory ");
		procedureCodeValidation(secondCategory, secondSubcategory, "Second Category ", "Second Subcategory ");
		deletingProcedureCode();
		importingProcedureCode();
		selectingAnotherPeriod();
		rateSheetPlugIn();
		existingRateSheetSearch();
		addingNewTerm();
		qualificationGroupSearch();
		addingFeeScheduleRate();
	//	relatedProcedureScheduleDelete();	
		logout();
	}

	
	// Schedules View Only Permission.
    
    
    By scheduledropdown=By.xpath("//div[@id='scheduleView']//a[contains(@class,'btn btn-light view-bg-text')]");
            
    By scheduleTitle=By.xpath("//div[@id='scheduleView']//div[@class='document-title-bar ng-scope']//span[@class='pad-l-10 xl-header ng-binding']");
            
    By scheduleDropdownName=By.xpath("//div[@id='scheduleView']//a[@class='btn btn-light view-bg-text']//span[@class='dropdown-text hide-overflow ng-binding'][1]");
            
    By relatedProcedureSchedulesFirstGroup= By.xpath("//div[@id='schedulesTemplate']//div[contains(@class,'col-lg-2 col-md-2 col-sm-2 col-xs-2 pad-r-0 large-height adjust-height')]//ul//li[1]");
            
    By FeeScheduleSearch=By.xpath("//div[@id='scheduleView']//input[contains(@class,'search-text-box input-sm do-not-scroll form-control')]");
            
    By ModifierSearch=By.xpath("//div[@id='scheduleView']//input[contains(@class,'search-text-box do-not-scroll input-sm form-control search-icon')]");
            
    By GroupCodeSearch=By.xpath("//div[@id='scheduleView']//input[contains(@class,'search-text-box input-sm form-control do-not-scroll search-icon')]");
            
    By relatedProcedureSearch=By.xpath("//div[@id='schedulesTemplate']//div[@class='row pad-l-0 pad-r-0 large-height']//input[contains(@class,'search-text-box pad-l-20 form-control input-sm')]");
            
    By respectiveSchedule=By.xpath("//div[@id='scheduleView']//div[contains(@class,'pad-l-10 col')]");
            
    By respectiveScheduleForRelated=By.xpath("//div[@id='scheduleView']//div[contains(@class,'col-lg-9 col-md-9 col-sm-9 col-xs-10')]");
            
    By respectiveRelatedSchedules=By.xpath("//div[@id='schedulesTemplate']//div[contains(@class,'col-lg-6 col-md-6 col-sm-6 xl-header')]");
            
    By respectiveRelatedSchedulesDC=By.xpath("//div[@id='schedulesTemplate']//div[@class='col-lg-9 col-md-9 col-sm-9 col-xs-10 large-document-header hide-overflow no-wrap ng-binding']");
            
    By feeSchedules = By.xpath("//li[@id='i[__valueField]']/a[contains(.,'Fee Schedules')]");
            
    By modifierSchedules = By.xpath("//li[@id='i[__valueField]']/a[contains(.,'Modifier Schedules')]");
            
    By groupCodeRatesSchedule = By.xpath("//li[@id='i[__valueField]']/a[contains(.,'Group Code Rates')]");
            

    public void selectScheduledropdown(By elemdesc,By search,By firstSchedule,By respectivepage)
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
    	{			
    		oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	click_button("select schedule", scheduledropdown);
    	click_button("Fee Schedules",elemdesc);
                   
    	if(get_text(scheduleDropdownName).equalsIgnoreCase(get_text(scheduleTitle)))
    	{
    		waitFor(firstSchedule,"");
    		oReport.AddStepResult("Respective Schedule", "Clicked on Select Schedule type dropdown and selected particular schedule type and verified that respective schedule is displayed", "PASS");
                         
    		if(IsDisplayed("First Schedule", firstSchedule))
    		{
    			oReport.AddStepResult("First Schedule", "Clicked on particular schedule type and verified that first schedule from the list is displayed", "PASS");
            	
            	oParameters.SetParameters("Schedule",get_text(firstSchedule));
            	enter_text_value("Search Box", search, oParameters.GetParameters("Schedule"));
            	performKeyBoardAction("ENTER");
            	waitFor(firstSchedule, "");
            	fixed_wait_time(2);
            	
            	
            	click_button("First Schedules",firstSchedule);
            	waitFor(respectivepage, "");
            	oParameters.SetParameters("RespectiveSchedule",get_text(respectivepage));
            
            	if(oParameters.GetParameters("Schedule").equalsIgnoreCase(oParameters.GetParameters("RespectiveSchedule")))
            	{      
            		waitFor(respectivepage,"");
            		oReport.AddStepResult("Respective schedule", "Clicked on First schedule and verified that respective schedule type is Displayed", "PASS");
            		enter_text_value("Search Box", search,"");
            		performKeyBoardAction("ENTER");
            		waitFor(firstSchedule,"");
            	}
            	else
            		oReport.AddStepResult("Respective schedule", "Clicked on First schedule and verified that respective schedule type is not Displayed", "FAIL");
    		}
    		else
    			oReport.AddStepResult("First Schedule", "Clicked on particular schedule type and verified that first schedule from the list is not displayed", "FAIL");
    	}
    	else
    		oReport.AddStepResult("Respective Schedule", "Clicked on Select Schedule type dropdown and selected particular schedule type and verified that respective schedule is not displayed", "FAIL");
    }
    
    
    By addIcon=By.xpath("//div[@id='scheduleView']//a[@title='Add Schedule Entry']");
    
    By addScheduleEntryLink1=By.xpath("//div[@id='scheduleView']//a[contains(text(),'Add a Schedule Entry')]");
    
    By secondSchedule=By.xpath("//div[@id='scheduleView']//ul[@class='data-list drillable-list scroll-auto']//li[2]");
    
    
    //This is method is used for add icon validation.
    public void addIconValidation()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
       if(IsDisplayed("Add Icon",addIcon) && IsDisplayed("Add Schedule Link",addScheduleEntryLink1))
    	   oReport.AddStepResult("Add Icon and Add schedule Link", "Clicked on First schedule and verified that Add icon is displayed", "FAIL");
       else
           oReport.AddStepResult("Add Icon and Add schedule Link", "Clicked on First schedule and verified that Add icon is not displayed", "PASS");
    }
            
            
    By editIcon=By.xpath("//div[@id='scheduleView']//a[@title='Edit Schedules']");
            
    By importFile= By.xpath("//div[@id='scheduleView']//a[@title='Import']");

    
    //This is method is used for Edit icon validation.
    public void editIconValidation()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
       if(IsDisplayed("Edit Icon",editIcon) && IsDisplayed("Import",importFile))
    	   oReport.AddStepResult("Edit Icon and Import Button", "Clicked on First schedule and verified that Edit icon is displayed", "FAIL");
       else
    	   oReport.AddStepResult("Edit Icon and Import Button", "Clicked on First schedule and verified that Edit icon is not displayed", "PASS");
    }
            
            
    By deleteIcon= By.xpath("//div[@id='schedulesTable']//i[@class='left fa fa-minus-square']");
            
    By schedulescode=By.xpath("//tr[@class='hand-cursor position-relative ng-scope'][1]//td[2]");
    
    By GroupCodeSchedule=By.xpath("//tr[@class='hand-cursor position-relative ng-scope'][1]//td[1]");
            
    By TotalScheduleCode=By.xpath("//div[@id='scheduleView']//ul[@class='data-list drillable-list scroll-auto']/li");
           
    
    //This is method is used for Delete icon validation.
    public void deleteIconValidation()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
       List<WebElement> totalScheduleCode=convertToWebElements(TotalScheduleCode);
       
       for(int i=1;i<=totalScheduleCode.size();i++)
       {
    	   By Totalshedulecodeset=By.xpath("//div[@id='scheduleView']//ul[@class='data-list drillable-list scroll-auto']/li["+i+"]");
            
    	   if(i != 1)
    		   click_button("procedure code",Totalshedulecodeset);
                   
    	   if(IsDisplayed("First Schedule code set", schedulescode))
    	   {      
    		   waitFor(schedulescode,"");
    		   oReport.AddStepResult("First Schedule Code Set", "Clicked on First schedule code set and verified that procedure code is displayed", "PASS");
            	 
    		   mouse_hover("Schedules Code",schedulescode);
            	 
    		   if(IsDisplayed("Delete Icon",deleteIcon))
    			   oReport.AddStepResult("Delete Icon", "Clicked on First schedule and verified that Delete icon is displayed", "FAIL");
    		   else
    			   oReport.AddStepResult("Delete Icon", "Clicked on First schedule and verified that Delete icon is not displayed", "PASS");
    		   break;
    	   }
    	   else
    		   oReport.AddStepResult("First Schedule Code Set", "Clicked on First schedule code set and verified that procedure code is not displayed", "PASS");
       }     
    }           
             
            
    By relatedProcedureCategory=By.xpath("//div[contains(@style,'position: relative; padding: 5px 0 0 0;height: 16px;')]");
            
    By relatedProcedureDelete=By.xpath("//div[contains(@style,'position: relative; padding: 5px 0 0 0;height: 16px;')]//i[@class='left fa fa-minus-square']");
                  
    By relatedProcedureEdit=By.xpath("//div[contains(@style,'position: relative; padding: 5px 0 0 0;height: 16px;')]//i[@class='left fa fa-pencil']");
                  
    By TotalRelatedProcedureSchedules=By.xpath("//div[@id='schedulesTemplate']//div[contains(@class,'col-lg-2 col-md-2 col-sm-2 col-xs-2 pad-r-0 large-height adjust-height')]//ul//li");
            
    
    //This method is used Hover over on Related procedure Schedules.
    public void relatedProcedureDeleteValidation()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
    		oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	List<WebElement> totalRelatedSchedulesSet=convertToWebElements(TotalRelatedProcedureSchedules);
       
    	for(int i=2;i<=totalRelatedSchedulesSet.size();i++)
    	{
    		By TotalRelatedSchedulsCodeset=By.xpath("//div[@id='schedulesTemplate']//div[contains(@class,'col-lg-2 col-md-2 col-sm-2 col-xs-2 pad-r-0 large-height adjust-height')]//ul//li["+i+"]");
    		click_button("procedure code",TotalRelatedSchedulsCodeset);
    		fixed_wait_time(3);
             
    		if(IsDisplayed("Related Schedules Code Set", relatedProcedureCategory) && IsDisplayed("SubCategoryCode", subCategoryCode))
    		{
    			oReport.AddStepResult("First related procedure schedules code set", "Clicked on First  related procedure schedules code set and verified that procedure code is displayed", "PASS");
    			mouse_hover("related Procedure Delete icon", relatedProcedureCategory);
                    
    			if(IsDisplayed("Related Procedure Delete Button", relatedProcedureDelete) && IsDisplayed("Related Procedure Edit Button", relatedProcedureEdit))
    				oReport.AddStepResult("Hover over Related Procedure and verified that", " Edit icon and delete icon is displayed", "FAIL");
    			else
    				oReport.AddStepResult("Hover over Related Procedure and verified that", " Edit icon and delete icon is not displayed", "PASS");
    			break;
    		}
    		else
    			oReport.AddStepResult("First related procedure schedules code set", "Clicked on First  related procedure schedules code set and verified that procedure code is not displayed", "PASS");
    	}
    }      
            
            
    By multipleProcedureTab = By.xpath("//div[@id='addEditMpmEntryParent']//div[@class='ng-isolate-scope']//ul[@class='cm-nav-tabs']/li[2]/a[contains(text(),'Multiple Procedures')]");
            
    By schedulesDetailstab=By.xpath("//div[@id='scheduleView']//div[@class='medium-header truncate ng-binding']");
    
    By schedulesDetailstab1 = By.xpath("//div[@id='scheduleView']//div[@class='medium-header truncate ng-binding']");
    
    By scheduleDetailsTabSaveButton = By.xpath("//button[@id='button.saveId']");
    
    By modifierDetailsTab=By.xpath("//div[@id='addEditModifierValueId']//div[@class='medium-header truncate ng-binding']");
    
    By scheduleSaveButtonCloseButton = By.xpath("//div[@id='slidePanelParent']//button[contains(@class,'close fa fa-times-circle')]");
    
    By scheduleSaveButtonCloseButton1 = By.xpath("//div[@id='slidePanelParent']//button[contains(@class,'close fa fa-times-circle')]");
    
    By modifierDetailsTabCloseButton = By.xpath("//div[@id='addEditModifierValueId']//button[contains(@class,'close fa fa-times-circle')]");
    
    
    public void scheduleDetailsCode1()
    {
    	if(get_text(scheduleTitle).equalsIgnoreCase(oParameters.GetParameters("FeeSchedule")) 
    			|| get_text(scheduleTitle).equalsIgnoreCase(oParameters.GetParameters("GroupCodeRate"))
    				|| get_text(scheduleTitle).equalsIgnoreCase(oParameters.GetParameters("ModifierSchedules")))
    	{
    		if(get_text(scheduleTitle).equalsIgnoreCase(oParameters.GetParameters("GroupCodeRate")))
    		{	
    			schedulescode = GroupCodeSchedule;
    			schedulesDetailstab = schedulesDetailstab1;
    			scheduleSaveButtonCloseButton = scheduleSaveButtonCloseButton1;
    		}
    		else if(get_text(scheduleTitle).equalsIgnoreCase(oParameters.GetParameters("ModifierSchedules")))
    		{
    			schedulescode = GroupCodeSchedule;
    			schedulesDetailstab = modifierDetailsTab;
    			scheduleSaveButtonCloseButton = modifierDetailsTabCloseButton;
    		}	
    			
    		oParameters.SetParameters("ScheduleDetailsCode",get_text(schedulescode));
    		click_button("Schedule Details Code",schedulescode);
    		
    		if(get_text(schedulesDetailstab).equalsIgnoreCase(oParameters.GetParameters("ScheduleDetailsCode")))
    		{
    			oReport.AddStepResult("Respective Schedule Details tab", "Clicked on particular schedule code and verified that schedule details tab is displayed in right side of the screen", "PASS");
    		
    			if(IsDisplayed("Schedules Tab Save Button", scheduleDetailsTabSaveButton))
    			{	
    				oReport.AddStepResult("Save Button Displayed", "In '"+get_text(scheduleTitle)+"' clicked on first 'Schedule Type' and selected first 'group code', Verified that save button is displayed", "FAIL");
    				dclick_button("'Schedules Details Tab' Close Icon", scheduleSaveButtonCloseButton);
    			}
    			else
    			{
    				oReport.AddStepResult("Save Button Displayed", "In '"+get_text(scheduleTitle)+"' clicked on first 'Schedule Type' and selected first 'group code', Verified that save button is not displayed", "PASS");
    				dclick_button("'Schedules Details Tab' Close Icon", scheduleSaveButtonCloseButton);
    			}
    		}
    		else
    			oReport.AddStepResult("Respective Schedule Details tab", "Clicked on particular schedule code and verified that schedule details tab are not displayed in right side of the screen", "FAIL");
    	}
    }
    

    By multipleProcedureCodeDetails = By.xpath("//div[@id='addEditMpmEntryParent']//tr[1][@class='hand-cursor position-relative ng-scope']/td[@class='col-sm-2 ng-binding']");
                                                                                    
    By multipleProcedureDeleteIcon1 = By.xpath("//div[@id='addEditMpmEntryParent']//i[@class='left fa fa-minus-square'][1]");
    
    By multipleProcedureCloseIcon = By.xpath("//div[@id='addEditMpmValueId']//div[@class='secondary-title-bar panel-header col-lg-12 col-md-12 col-sm-12']/button");
            
    By multipleProcedureDetailsTab = By.xpath("//div[@id='addEditMpmValueId']//div[@class='secondary-title-bar panel-header col-lg-12 col-md-12 col-sm-12']//div[@class='medium-header truncate ng-binding']");
    
    
    public void multipleProcedureButton()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
       dclick_button("Multiple Procedures",multipleProcedureTab);
       
       if(IsDisplayed("Multiple procedure code", multipleProcedureCodeDetails))
       {
    	   oReport.AddStepResult("Multiple procedure code", "Clicked on Multiple procedure and verified that First mutiple procedure code is displayed", "PASS");
    	   
    	   mouse_hover("Modifier schedule", multipleProcedureCodeDetails);
    	   
    	   if(IsDisplayed("Multiple procedure Delete icon", multipleProcedureDeleteIcon1))
    		   oReport.AddStepResult("modifier schedule code delete icon", "Hover over modifier schedule code and verified that delete icon is displayed", "FAIL");
           else
        	   oReport.AddStepResult("modifier schedule code delete icon", "Hover over modifier schedule code and verified that delete icon is not displayed", "PASS");
             
    	   oParameters.SetParameters("MultipleCodeDetails",get_text(multipleProcedureCodeDetails));
             
    	   click_button("Schedule Details Tab",multipleProcedureCodeDetails);
    	   oParameters.SetParameters("MultipleProcedureDetailsTab",get_text(multipleProcedureDetailsTab));
    	   
    	   if(oParameters.GetParameters("MultipleCodeDetails").equalsIgnoreCase(oParameters.GetParameters("MultipleProcedureDetailsTab")))
           {
    		   oReport.AddStepResult("multiple procedure code", "Clicked on multiple procedures code and verified that multiple procedure code is displayed in details tab", "PASS");
    		   dclick_button("Multiple Procedure CloseIcon",multipleProcedureCloseIcon);
           }
    	   else
    		   oReport.AddStepResult("multiple procedure code", "Clicked on multiple procedures code and verified that multiple procedure code is not displayed in details tab", "FAIL");
       }	
       else
             oReport.AddStepResult("Multiple procedure code", "Clicked on Multiple procedure and verified that First mutiple procedure code is not displayed", "PASS");
    }      
            
            
    By addFilter= By.xpath("//div[@id='filter-schedules']//span[text()='Add Filter']");
    
    By filterTextBox= By.xpath("//div[@id='filter-schedules']//input[@class='text']");
    
    By FiltercloseIcon=By.xpath("//div[@id='filter-schedules']//a[@class='inverse-min link-btn hand-cursor ng-isolate-scope']//i[@class='left fa fa-times-circle']");
    
    By FirstFeeSchedule=By.xpath("//tr[@class='hand-cursor position-relative ng-scope'][1]//td[2]");
    
    
    public void addFilterValidation1()
    {
    	if(oParameters.GetParameters("FeeSchedule").equalsIgnoreCase(get_text(scheduleTitle)) 
    		|| oParameters.GetParameters("GroupCodeRate").equalsIgnoreCase(get_text(scheduleTitle)))
    	{
    		
    		if(oParameters.GetParameters("GroupCodeRate").equalsIgnoreCase(get_text(scheduleTitle)))
    			FirstFeeSchedule = GroupCodeSchedule;
    		
    		oParameters.SetParameters("FirstScheduleType", get_text(FirstFeeSchedule));
    		dclick_button("Add Filter Icon", addFilter);
    		
    		enter_text_value("Filter Text Box", filterTextBox, oParameters.GetParameters("FirstScheduleType"));
    		performKeyBoardAction("ENTER");
    		
    		if(get_text(FirstFeeSchedule).equals(oParameters.GetParameters("FirstScheduleType")))
    		{
    			oReport.AddStepResult("Add Filter", "Filtered this ' "+oParameters.GetParameters("FirstScheduleType")+" ' code set and Excpected ' "+get_text(FirstFeeSchedule)+" ' code is displayed", "PASS");
    			click_button("Add Filter Close Icon", FiltercloseIcon);
    		}
    		else
    			oReport.AddStepResult("Add Filter", "Filtered this ' "+oParameters.GetParameters("FirstScheduleType")+" ' code set and Excpected ' "+get_text(FirstFeeSchedule)+" ' code is not displayed", "FAIL");
    	}
    }
            
   
    By periodDropDownButton = By.xpath("//div[@id='period-selector']/a//span[@class='ng-binding']");
    
    By firstPeriod = By.xpath("//div[@id='period-selector']//ul[@class='dropdown-menu period-menu mar-l-15']//li[2]/a");
    
    By periodDeleteIcon = By.xpath("//div[@id='period-selector']//ul//li[2]//div[2]/span[3]//i");
    
    By periodEditIcon =By.xpath("//div[@id='period-selector']/ul/li[2]//div[1]/span//i");
    
    
    //This method is used for period drop down validation.
    public void periodDropDownValidation()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
       if(IsDisplayed("Period Dropdown Button", periodDropDownButton))
       {
           oReport.AddStepResult("Period Drop Down", "Clicked on particular schedule type and verified that respective schedule and period dropdown is displayed ", "PASS");
           click_button("Effective Period Drop Down", periodDropDownButton);
           
           mouse_hover("First Period", firstPeriod);
           	
           if(IsDisplayed("Delete Icon",periodDeleteIcon ) && IsDisplayed("Edit Icon",periodEditIcon))
        	   oReport.AddStepResult("Edit and Delete icon", "Clicked on period drop down and hovered over period and verified that 'Edit icon' and 'Delete icon' are displayed", "FAIL");
           else
        	   oReport.AddStepResult("Edit and Delete icon", "Clicked on period drop down and hovered over period and verified that 'Edit icon' and 'Delete icon' are not displayed", "PASS");
       }
       else
    	   oReport.AddStepResult("Period Drop Down", "Clicked on particular schedule type and verified that respective schedule and period dropdown is not displayed ", "FAIL");
    }
            
             
    By categoryCode = By.xpath("//div[@id='addEditEntryParent']//div[@class='mar-l-15']//ul[@spinner='schedule.loadCategoryList']/li/div[@style='position: relative; padding: 5px 0 0 0;height: 16px;']");
            
    By subCategoryCode =By.xpath("//div[@id='addEditEntryParent']//ul[@spinner='schedule.loadSubcategoryList']/li[@ng-repeat='item in schedule.subCategoryList']/div");
            
    By procedureAddIcon = By.xpath("//div[@id='addEditEntryParent']//a[@hide-text='true']/i[@class='left fa fa-plus']");
    
    By procedureImportIcon = By.xpath("//div[@id='addEditEntryParent']//div[@spinner='schedule.loadingProcedureList']//a[@ng-show='isEditable' ]/i[@class='left fa fa-cloud-upload']");
    
    By procedureCode = By.xpath("//div[@id='addEditEntryParent']//div[@class='mar-l-15']//div[contains(@class,'col-lg-6 col-md-6 col-sm-6 col-xs-6 columns-height')]//table//tbody//tr[2]//td[1]");
    
    
    public void categoryCodeDetails()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
       click_button("Category Code",categoryCode);
       click_button("Sub Category Code",subCategoryCode);
       waitFor(procedureCode,"");
       
       if(IsDisplayed("Procedure Add Icon",procedureAddIcon) && IsDisplayed("Procedure Import Icon", procedureImportIcon))
    	   oReport.AddStepResult("Add and Import Icon", "Clicked on Subcategory and verified that 'Add icon' and 'Import icon' Is displayed", "FAIL");
       else
    	   oReport.AddStepResult("Add and Import Icon","Clicked on Subcategory and verified that 'Add icon' and 'Import icon' Is not displayed","PASS");
    }
    
             
    By exportIconButton = By.xpath("//div[@id='scheduleView']//div[@class='pull-right pad-r-15']//a[@title='Export']"); 
             
    By Codes=By.xpath("//div[@id='scheduleView']//div[@class='document-title-bar']//div[@class='pad-r-5 pull-right xl-header ng-binding']");
            
            
    @SuppressWarnings("static-access")
    public void exportValidation()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	oParameters.SetParameters("ScheduleTitle", get_field_value("Schedule Name", schedulesTitle));
      
    	dclick_button("Export Button", exportIconButton);
    	fixed_wait_time(4);
       
    	File fileName = getTheNewestFile("C:\\CCM\\Downloads", "xlsx");
       
    	int columnCount = oExcelData.getColumnCount(0,fileName.toString(),1);
       
    	String [] columnDataArray = new String[columnCount];
       
    	for(int i=0; i<columnCount;i++)
    	{                                      
    		String cellData = ExcelData.getCellData(0, i, 2, fileName.toString());
    		columnDataArray[i]= cellData;
    	}
       
    	String columnDataString = String.join(",", columnDataArray).replace(",", " ");           
       
    	if(columnDataString.contains(oParameters.GetParameters("HCPCS/CPTValue")))
    		oReport.AddStepResult("Export File ", "Excel File Downloaded and All the Codes Under Fee Schedule are Displayed", "PASS");
    	else
    		oReport.AddStepResult("Export File ", "Clicked on Export link but that all the codes displayed under fee schedule those codes are not downloaded", "FAIL");
    }            
         
    
    By schedulesType = By.xpath("//div[@id='scheduleView']//li//a");
    
    
    public void SchedulesViewOnly()
    {
    	int count = convertToWebElements(schedulesType).size();
    	
    	for(int i=1;i<count;i++)
    	{	
    		click_button("select schedule", scheduledropdown);
    		
    		By SchedulesType = By.xpath("//div[@id='scheduleView']//ul[@class='dropdown-menu']//li["+(i+1)+"]");
    		
    		click_button(get_text(SchedulesType), SchedulesType);
    		
    		if(get_text(scheduleDropdownName).equals(get_field_value(get_text(scheduleDropdownName), scheduleTitle)))
    		{
    			oReport.AddStepResult("Schedule Type", "Clicked on 'Schedule type dropdown' and selected '"+get_text(scheduleDropdownName)+"' and verified that 'Respective Schedule' is displayed", "PASS");
    			
    			By FirstSchedule = By.xpath("//div[contains(@id,'schedule')]//ul[contains(@class,'data-list drillable-list')]/li[1]");
    			oParameters.SetParameters("FirstSchedule", get_text(FirstSchedule));
    			click_button("First Schedule", FirstSchedule);
    		
    			if(get_text(scheduleDropdownName).equals("Related Procedure Schedules"))
    				respectiveSchedule=respectiveScheduleForRelated;
    				
    			if(get_text(respectiveSchedule).equalsIgnoreCase(oParameters.GetParameters("FirstSchedule")))	
    				oReport.AddStepResult("Respective schedule", "Clicked on '"+get_text(FirstSchedule)+"' schedule type and verified that '"+get_text(respectiveSchedule)+"' as expected schedule type is Displayed", "PASS");
    			else
    				oReport.AddStepResult("Respective schedule", "Clicked on '"+get_text(FirstSchedule)+"' schedule type and verified that '"+get_text(respectiveSchedule)+"' as expected schedule,but it's not displayed ", "FAIL");
    		
    			addIconValidation();
    			editIconValidation();
    		    
    			if(get_text(scheduleDropdownName).equals("Related Procedure Schedules"))
    				relatedProcedureDeleteValidation();
    			else
    				deleteIconValidation();
    				scheduleDetailsCode1();
    				
    			if(get_text(scheduleDropdownName).equalsIgnoreCase("Fee Schedules") || get_text(scheduleDropdownName).equalsIgnoreCase("Group Code Rates"))
    			{
    				addFilterValidation1();
    				periodDropDownValidation();
    				exportValidation();
    			}	
    			else if(get_text(scheduleDropdownName).equalsIgnoreCase("Modifier Schedules"))
    			{
    				multipleProcedureButton();
    			    periodDropDownValidation();
    			}
    			else if(get_text(scheduleDropdownName).equals("Related Procedure Schedules"))
    			{
    				categoryCodeDetails();
    				periodDropDownValidation();
    			}
    		}
    		else
    			oReport.AddStepResult("Schedule Type", "Clicked on 'Schedule type dropdown' and selected '"+get_text(scheduleDropdownName)+"' and verified that 'Respective Schedule' is not displayed", "FAIL");
    	}
    }
    
        
    public void schedulesViewOnlyVR()
    {
       login("VIEW");
       changePricingEngine();
       xpathChange();
       navigateToSchedules();
       
       SchedulesViewOnly();
       logout();
    }

     
    
    
    
    
    
    
    
    
    
    By addGroupCodeRatesScheduleLink = By.xpath("//a[contains(.,'Add a Group Code Rate')]");
    
    
    public void addGroupCodeRateScheduleButton(String buttonLink)
    {
    	if(buttonLink.equalsIgnoreCase("BUTTON"))
    	{
	    	//To check add group code rate schedule button is displayed and enabled to perform click operation
	    	if(IsElementDisplayed("Add Group Code Schedule Button", addGroupCodeRatesScheduleButton) && IsElementEnabled("Add Group Code Schedule Button", addGroupCodeRatesScheduleButton))
	    		//To click on Add Group Code Schedule Button
	    		click_button("Add Group Code Schedule Button", addGroupCodeRatesScheduleButton);
	    	
	    	//To validate if Add Group Code Schedule window is displayed or not
	    	if (IsElementDisplayed("Add Group Code Rates Schedule Window Title", addGroupCodeRatesScheduleWindowTitle))
				oReport.AddStepResult("Add Group Code Rates Schedule Window ", "Clicked on 'Add Group Code Rates Schedule' button and verified that Add a group code window is displayed", "PASS");
			else
				oReport.AddStepResult("Add Group Code Rates Schedule Window ","Clicked on 'Add Group Code Rates Schedule' button however Add Group Code window is not displayed", "FAIL");
    	}
    	else
    	{
    		//To To check add group code rate schedule link is displayed and enabled to perform click operation
        	if(IsElementDisplayed("Add Group Code Schedule Link", addGroupCodeRatesScheduleLink) && IsElementEnabled("Add Group Code Schedule Link", addGroupCodeRatesScheduleLink))
        		//To click on Add Group Code Schedule Button
        		click_button("Add Group Code Schedule Link", addGroupCodeRatesScheduleButton);
        	
        	//To validate if Add Group Code Schedule window is displayed or not
        	if (IsElementDisplayed("Add Group Code Rates Schedule Window Title", addGroupCodeRatesScheduleWindowTitle))
    			oReport.AddStepResult("Add Group Code Rates Schedule Window ", "Clicked on 'Add Group Code Rates Schedule' link and verified that Add a group code window is displayed", "PASS");
    		else
    			oReport.AddStepResult("Add Group Code Rates Schedule Window ","Clicked on 'Add Group Code Rates Schedule' link however Add Group Code window is not displayed", "FAIL");
    	}
    }
    
    
    public void groupCodeRateScheduleDetails()
    {
    	//Setting Group Code Rate Schedule Name parameter to hash table
    	oParameters.SetParameters("GroupCodeName", oParameters.GetParameters("ScheduleName") + System.currentTimeMillis());
    	//To enter Group Code Rate Schedule Name
    	enter_text_value("Group Code Name", groupCodeNameTextBox,oParameters.GetParameters("GroupCodeName"));
		//To Enter Effective Date
    	enter_text_value("Group Code Effective Date", groupCodeEffectiveDate, get_specified_date(0));
    	performKeyBoardAction("ENTER");
		//To Enter Termination Date
    	enter_text_value("Group Code Termination Date", groupCodeTerminationDate, get_specified_date(2));
    	performKeyBoardAction("ENTER");
	}
    
    
    public void groupCodeRateScheduleSaveButton()
    {
    	//To check if save button is displayed and enabled to perform click operation
    	if(IsElementDisplayed("Add Group Code Rate Schedule Window Save Button", groupCodeWindowSave) && IsElementEnabled("Add Group Code Rate Schedule Window Save Button", groupCodeWindowSave))
    		//To click on save button 
    		click_button("Add Group Code Rate Schedule Window Save Button", groupCodeWindowSave);
    	
    	//To check if it has clicked on save botton and window has closed
    	if (IsElementDisplayed("Add Group Code Rates Schedule Button", addGroupCodeRatesScheduleButton))
			oReport.AddStepResult("Add Group Code Rates Schedule Window Save Button", "Clicked on 'Add Group Code Rates Schedule' window save button and verified that Add a group code window is closed", "PASS");
		else
			oReport.AddStepResult("Add Group Code Rates Schedule Window ","Unable to click on 'Add Group Code Rates Schedule' window save button and verified that window is still displayed", "FAIL");
    }
    
    
    By noGroupCodeEntriesExist = By.xpath("//div[@options='noGroupCodesForPeriod']//div[@class='first-line ng-scope ng-binding']");
    
    
    public void addGroupCodeRateScheduleValidation()
    {
    	//To wait for 'No Group Code Entries Exist' Label
    	waitFor(noGroupCodeEntriesExist, "No Group Code Entries Exist Label");
    	
    	//To check if it has created new group code with entered data 
    	if (oParameters.GetParameters("GroupCodeName").equalsIgnoreCase(get_field_value("Group Name Title", groupCodeRatesScheduleTitle))) 
			oReport.AddStepResult("New Group Code ", "New Group Code is Added successfully and displayed ", "PASS");
		else 
			oReport.AddStepResult("New Group Code ","Filled all the mandatory fields and clicked on save but that new Group Code is not added and displayed ","FAIL");
	}
        
 
    public void groupCodeRateScheduleVR()
    {
    	login("EDIT");
		changePricingEngine();
		xpathChange();
		navigateToSchedules();
		selectSchedule("Group Code Rate Schedule", "Add a Group Code Rate Schedule Button", groupCodeRatesSchedulesOption, addGroupCodeRatesScheduleButton);
		clickAddButton("Add Group Code Schedule Button", "Add Group Code Rates Schedule Window Title", addGroupCodeRatesScheduleButton, addGroupCodeRatesScheduleWindowTitle);
		//addGroupCodeRateScheduleButton("BUTTON");

		groupCodeRateScheduleDetails();
		//groupCodeRateScheduleSaveButton();
		clickSaveButton("Add Group Code Rate Schedule Window Save Button", "Add Group Code Rates Schedule Button", groupCodeWindowSave, addGroupCodeRatesScheduleButton);
		logout();
    }
}	
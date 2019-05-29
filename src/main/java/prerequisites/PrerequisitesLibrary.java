package prerequisites;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import contractManagement.*;
import libraries.*;
import libraries.Parameters;


/**
 * @author C16137
 *
 */


public class PrerequisitesLibrary extends CCMLibrary
{
	int count = 0;
	
	public PrerequisitesLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}
	
	
	RateSheetLibrary oRateSheetLibrary = new RateSheetLibrary(driver, oReport, oParameters);
	
	ExcelData oExcelData = new ExcelData(oParameters);
	
	ContractLibrary oContractLibrary = new ContractLibrary(driver, oReport, oParameters);
	
	CodesLibrary oCodesLibrary = new CodesLibrary(driver, oReport, oParameters);
	
	ModelsLibrary oModelsLibrary = new ModelsLibrary(driver, oReport, oParameters);
	
	SchedulesLibrary oSchedulesLibrary = new SchedulesLibrary(driver, oReport, oParameters);
	
	QualificationGroupLibrary oQualificationGroupLibrary = new QualificationGroupLibrary(driver, oReport, oParameters);
	
	PPSLibrary oPPSLibrary = new PPSLibrary(driver, oReport, oParameters);

	
	//Create Contract Details.
	public void createContractDetails()
	{
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "Contracts", "");
		int rowCount = oExcelData.getRowCount1("Contracts", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
		System.out.println(rowCount);
		
		
		for(int i=1; i<=rowCount-1; i++)
		{
			oContractLibrary.addContractButton();
			
			if(oParameters.GetParameters("TypeOfContract"+i).equalsIgnoreCase("NonContracted"))
				click_button("Contracted Checkbox", oContractLibrary.contractedCheckBoxElement);
				
			waitFor(oContractLibrary.contractNameBy,"Contract Name Label");
			
			//To enter Contract Name
			enter_text_value("contractName", oContractLibrary.contractNameBy, oParameters.GetParameters("ContractName"+i));
		
			//To Enter Negotaitor Name
			enter_text_value("Negotiator", oContractLibrary.negotiatorElement,oParameters.GetParameters("Negotiator"+i));
		
			//To Enter Negotiator Email
			enter_text_value("Negotiator Email", oContractLibrary.negotiatorEmailElement, oParameters.GetParameters("NegotiatorEmail"+i));
			
			//To Enter Contract Manager Name
			enter_text_value("Contract Manager", oContractLibrary.contractManagerElement, oParameters.GetParameters("ContractManager"+i));
			
			//To Enter Contract Manager Email
			enter_text_value("Contract Manager EMail", oContractLibrary.contractManagerEmailElement, oParameters.GetParameters("ContractManagerEmail"+i));
			
			//To Enter Region
			enter_text_value("Region", oContractLibrary.regionElement, oParameters.GetParameters("Region"+i));
			
			//To Enter Effective  Date
			enter_text_value("Effective Date", oContractLibrary.effectiveDateElement, oParameters.GetParameters("EffectiveDate"+i));
			performKeyBoardAction("ENTER");
			
			//To Enter Renewal  Date
			enter_text_value("Renewal Date", oContractLibrary.renewalDateElement, oParameters.GetParameters("RenewalDate"+i));
			performKeyBoardAction("ENTER");
			
			//To Enter Termination Date
			enter_text_value("Termination Date", oContractLibrary.terminationDateElement, oParameters.GetParameters("TerminationDate"+i));
			performKeyBoardAction("ENTER");
			
			//To Enter Payer
			enter_text_value("Payer", oContractLibrary.payerElement, "");
			performKeyBoardAction("ENTER");
		
			waitFor(oContractLibrary.firstElement,"Payer Drop Down First Element");
			click_button("First Payer", oContractLibrary.firstElement);
			
			oParameters.SetParameters("Entered_Payer_Name1", convertToWebElement(oContractLibrary.payerElement).getAttribute("value"));
			
			oContractLibrary.saveButton();
			
					
		}	
	}
	
	//To create Contract
	public void createContract()
	{
		login("EDIT");
		changePricingEngine();
		createContractDetails();
	}
	
	
	//Fetching data from excel creating Codes Details in New Tenant.
	public void createCodeDetails()
	{
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "Codes", "");
		int rowCount = oExcelData.getRowCount1("Codes", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
		
		for(int i=1; i<rowCount; i++)
		{
			oCodesLibrary.codesDropdown();
			
			if(oParameters.GetParameters("TypeOfCode"+i).equalsIgnoreCase("HCPCS/CPT"))
			{
				oCodesLibrary.SelectCodeSetType(oCodesLibrary.HCPCS_CPT,oCodesLibrary.addHcpcsCpt);
				
				fixed_wait_time(2);
				
				if(IsElementDisplayed("Respective opened Code Set page",oCodesLibrary.openedpage))
				{	
					click_button("", oCodesLibrary.openedpage);
					fixed_wait_time(1);
					click_button("Add code Set Icon ",oCodesLibrary.Add_Hcpcs_cpt);
				}
				else
					click_button("Add HCPCS/CPT", oCodesLibrary.addHcpcsCpt);
				
				oCodesLibrary.addCodeSetButtonIcon();
				addCodeSetTabDetails(i);
				
			}
			else if(oParameters.GetParameters("TypeOfCode"+i).equalsIgnoreCase("ICD"))
			{	
				oCodesLibrary.SelectCodeSetType(oCodesLibrary.ICD,oCodesLibrary.add_ICD);
				fixed_wait_time(2);
				
				if(IsElementDisplayed("Respective opened Code Set page",oCodesLibrary.openedpage))
				{
					click_button("", oCodesLibrary.openedpage);
					fixed_wait_time(1);
					click_button("Add code Set Icon ",oCodesLibrary.Add_Hcpcs_cpt);
				}
				else
					click_button("ADD ICD", oCodesLibrary.add_ICD);
				
				oCodesLibrary.addCodeSetButtonIcon();
				addCodeSetTabDetails(i);
			}
			else if(oParameters.GetParameters("TypeOfCode"+i).equalsIgnoreCase("Diagnosis"))
			{
				oCodesLibrary.SelectCodeSetType(oCodesLibrary.diagnosis,oCodesLibrary.add_diagnosis_Code_set);
				fixed_wait_time(2);
				
				if(IsElementDisplayed("Respective opened Code Set page",oCodesLibrary.openedpage))
				{
					click_button("", oCodesLibrary.openedpage);
					fixed_wait_time(1);
					click_button("Add code Set Icon ",oCodesLibrary.Add_Hcpcs_cpt);
				}
				else
					click_button("Add code set Icon", oCodesLibrary.add_diagnosis_Code_set);
				
				oCodesLibrary.addCodeSetButtonIcon();
				addCodeSetTabDetails(i);
			}
			else if(oParameters.GetParameters("TypeOfCode"+i).equalsIgnoreCase("DRG"))
			{
				oCodesLibrary.SelectCodeSetType(oCodesLibrary.DRG,oCodesLibrary.addDRGCodeSet);
				fixed_wait_time(2);
				
				if(IsElementDisplayed("Respective opened Code Set page",oCodesLibrary.openedpage))
				{	
					click_button("", oCodesLibrary.openedpage);
					fixed_wait_time(1);
					click_button("Add code Set Icon ",oCodesLibrary.Add_Hcpcs_cpt);
				}
				else
					click_button("Add code set Icon", oCodesLibrary.addDRGCodeSet);
				
				oCodesLibrary.addCodeSetButtonIcon();
				addCodeSetTabDetails(i);
			}
		}
	}
	
	
	public void addCodeSetTabDetails(int i)
	{
		enter_text_value("Code set Name",oCodesLibrary.codesetname,oParameters.GetParameters("CodeName"+i));
		
		enter_text_value("Effective date",oCodesLibrary.add_effective,oParameters.GetParameters("New_effectivedate"+i));
		performKeyBoardAction("ENTER");
		
		if(!oParameters.GetParameters("Status"+i).isEmpty())
		{
			By status = By.xpath("//input[@id='period"+oParameters.GetParameters("Status"+i)+"AddCode']");
			
			click_button("Status Icon", status);
		}

		driver.switchTo().frame("codeSetImportIframe");
		
		click_button("browse button",oCodesLibrary.browsebutton);
		
		String path = oParameters.GetParameters("AUTOIT_FILES_PATH"+i);
		fixed_wait_time(3);
		
		try 
		{
			Runtime.getRuntime().exec(path);
			fixed_wait_time(1);
			driver.switchTo().defaultContent();
    		click_button("Save button",oCodesLibrary.addCodeSet_SaveButton);
    		refresh_page();
    		fixed_wait_time(2);
    		
    		if(IsDisplayed("Error Adding Code Set", oCodesLibrary.ErrorCodeSet))
    		{
    			System.out.println("FAIL");
    			click_button("Error Adding Code Set", oCodesLibrary.ErrorAddingCodeSetOKButton);
    			
    			fixed_wait_time(1);
    			click_button("Add Code Set Cancel Button", oCodesLibrary.addCodeSet_CancelButton);
    			
    			fixed_wait_time(1);
    			click_button("Discard", oCodesLibrary.discard);
    		}
    		else
    			System.out.println("PASS");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	//Codes Build
	public void createCode()
	{
		login("EDIT");
		changePricingEngine();
		oCodesLibrary.xpathChange();
		oCodesLibrary.navigateCodesPlugin();
		createCodeDetails();
		logout();
	}
	
	/*//Fetching data from excel creating Model Jobs Details in new tenant.
	public void createModelJobDetails()
	{
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "Models", "");
		int rowCount = oExcelData.getRowCount1("Models", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
		
		for(int i=1; i<rowCount; i++)
		{
			dclick_button("Add Model",oModelsLibrary.addModelButton);
			
			waitFor(oModelsLibrary.modelNameTextBox,"Model Name Feild");
			enter_text_value("Model Name",oModelsLibrary.modelNameTextBox,oParameters.GetParameters("ModelName"+i));
			
			click_button("Manage claim Criteria",oModelsLibrary.manageClaimButton);
			
			oModelsLibrary.filterField();
			
			enter_text_value("Product Model Name", oModelsLibrary.productModelName, oParameters.GetParameters("ProductModelName"+i));
			
			oModelsLibrary.useCustomCriteria();
			
			oModelsLibrary.filterField();
			
			oModelsLibrary.selectBaseRateSheet(String.valueOf(i));
			
			oModelsLibrary.selectProposalRateSheet(String.valueOf(i));
			
			oModelsLibrary.saveDetails();
			
		}
	}
	
	//Model Job Build.
	public void createModelJob()
	{
		login("EDIT");
		changePricingEngine();
		oModelsLibrary.xpathChange();
		oModelsLibrary.navigateModelsPlugin();
		createModelJobDetails();
	}*/
	
	By feeScheduleDuplicateNotification = By.xpath("//div[@class='message ng-scope ng-binding'][contains(.,'Duplicate Fee Schedule Name.')]");
    
    By duplicatePopupCancel = By.xpath("//div[@id='dialog']//div[@id='dialog_buttons']//input[@value='Cancel']");
    
    By modifierScheduleDuplicateNotification = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Error adding Modifier Schedule')]");
    
    By duplicatePopupOK = By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");
    
    By groupCodeDuplicateNotification = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Error Saving Group Code Rate')]");
    
    By relatedScheduleDuplicateNotification = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'There was a problem saving')]");
    
    By duplicatePopupClose = By.xpath("//div[@id='dialog']//div[@id='dialog_buttons']//input[@value='Close']");

	
	
	public void duplicateScheduleHandling(int i)
	{
		if(oParameters.GetParameters("TypeOfSchedule"+i).equalsIgnoreCase("Fee Schedules"))
		{
			if(IsDisplayed("Fee Schedule Duplicate Notification", feeScheduleDuplicateNotification))
			{
				click_button("Duplicate Popup Cancel", duplicatePopupCancel);	
				fixed_wait_time(2);
				click_button("Add Schedule window cancel", oSchedulesLibrary.scheduleCancelButton);	
				fixed_wait_time(2);
				click_button("Add Schedule window discard", oSchedulesLibrary.unsavedWindowDiscard);
			}			
		}
		else if(oParameters.GetParameters("TypeOfSchedule"+i).equalsIgnoreCase("Modifier Schedules")||oParameters.GetParameters("TypeOfSchedule"+i).equalsIgnoreCase("Group Code Rates"))
		{
			if(IsDisplayed("Modifier Schedule Duplicate Notification", modifierScheduleDuplicateNotification) || IsDisplayed("Group Code Rates Schedule Duplicate Notification", groupCodeDuplicateNotification))
			{
				click_button("Duplicate Popup OK", duplicatePopupOK);		
				fixed_wait_time(2);
				click_button("Add Schedule window cancel", oSchedulesLibrary.scheduleCancelButton);
				fixed_wait_time(2);
				click_button("Add Schedule window discard", oSchedulesLibrary.unsavedWindowDiscard);
				oParameters.SetParameters("True", "YES");
			}
		}
		else if(oParameters.GetParameters("TypeOfSchedule"+i).equalsIgnoreCase("Related Procedure Schedules"))
		{
			if(IsDisplayed("Related Procedure Schedule Duplicate Notification", relatedScheduleDuplicateNotification))
			{
				click_button("Duplicate Popup Close", duplicatePopupClose);
				fixed_wait_time(2);
				click_button("Add Schedule window cancel", oSchedulesLibrary.scheduleCancelButton);
				fixed_wait_time(2);
				click_button("Add Schedule window discard", oSchedulesLibrary.unsavedWindowDiscard);	
				oParameters.SetParameters("True", "YES");
			}
		}
	}
	
	
	public void addSchedules()
	{
	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "Schedules", "");
    int rowCount = oExcelData.getRowCount1("Schedules", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    
    for(int i=1;i<rowCount;i++)
    {                 
    	if(get_field_value("Schedule Type Dropdown", oSchedulesLibrary.scheduleTypeDropDown).contains(oParameters.GetParameters("TypeOfSchedule"+i)))
    		System.out.println("Already "+oParameters.GetParameters("TypeOfSchedule"+i)+" Selected");
    	else
    	{
			click_button("Select a Schedule Type Drop Down", oSchedulesLibrary.scheduleTypeDropDown);
			By dropDownOption = By.xpath("//*[@id='i[__valueField]']//a[contains(.,'"+ oParameters.GetParameters("TypeOfSchedule"+i) +"')]");
			click_button("Schedules Option", dropDownOption);
		}  
    	if(oParameters.GetParameters("TypeOfSchedule"+i).equalsIgnoreCase("Fee Schedules"))
    	{
    		fixed_wait_time(5);
    		waitFor(oSchedulesLibrary.addFeeScheduleButton, "Add Fee Schedule Button");
    		
    		click_button("Add Fee Schedule button", oSchedulesLibrary.addFeeScheduleButton);
                
    		addScheduleDetails(i,oSchedulesLibrary.scheduleNameTextBox,oSchedulesLibrary.addSchedulestartDate,oSchedulesLibrary.addScheduleEndDate);
                
    		selectOption(oSchedulesLibrary.selectAScheduleDD, "visibletext",oParameters.GetParameters("ScheduleType"+i));
                
    		importSchedule(i,"ScheduleimportIframe",oSchedulesLibrary.uploadFileButton);
                
    		click_button("Schedule Save button", oSchedulesLibrary.scheduleSaveButton);
    		fixed_wait_time(4);
    		
    		
    		if (IsDisplayed("Error Window", oSchedulesLibrary.errorWindow)) 
    		{
    			performKeyBoardAction("ESC");
    			click_button("Error Window Ok", oSchedulesLibrary.errorWindowOKButton);
            }
    		else
    			duplicateScheduleHandling(i);
                
                waitFor(oSchedulesLibrary.addFeeScheduleButton,"Add Fee Schedule button");   
                waitFor(oSchedulesLibrary.tableRow,"Schedule Table Row");            
        }
    	else if(oParameters.GetParameters("TypeOfSchedule"+i).equalsIgnoreCase("Modifier Schedules"))
        {
              oParameters.SetParameters("TRUE", "NO");              
              
              //String[] percentage = oParameters.GetParameters("Percentage"+i).split(","); 
            
              waitFor(oSchedulesLibrary.addModifierScheduleButton, "Add Modifier Schedule Button");
              click_button("Add Modifier Schedule button", oSchedulesLibrary.addModifierScheduleButton);
                    
              addScheduleDetails(i,oSchedulesLibrary.scheduleNameTextBox,oSchedulesLibrary.modifierScheduleStartDate,oSchedulesLibrary.modifierScheduleEndDate);
              
              click_button("Schedule Save button", oSchedulesLibrary.scheduleSaveButton);
              duplicateScheduleHandling(i);
              
              String[] Codes_Percentage = oParameters.GetParameters("Code_Percentage"+i).split(",");
              
              if(!oParameters.GetParameters("TRUE").equalsIgnoreCase("YES"))
              {              
            	  waitFor(oSchedulesLibrary.addModifierScheduleButton, "Add Modifier Schedule button");
            	  waitFor(oSchedulesLibrary.scheduleTypeDropDown, "Select a Schedule Type Drop Down");
              
            	  click_button("Multiplier Reduction Button", oSchedulesLibrary.multiplierReductionButton);
              
            	  for(int k=0;k<Codes_Percentage.length;k++)
            	  {
            		  String[] codes_per = Codes_Percentage[k].split("-");
            		  
            		  By CodeTextBox = By.xpath("//div[@id='addEditModifierValueId']//tr["+(k+1)+"]//input[@id='modifierCode']");
            		  By percentageTextBox = By.xpath("//div[@id='addEditModifierValueId']//tr["+(k+1)+"]//input[@id='modifierPercent']");
            		  
            		  enter_text_value("Code Field", CodeTextBox, codes_per[0]);
                    
            		  enter_text_value("Percentage Field", percentageTextBox, codes_per[1]);
            		  
           		   /*for(int l=k;l<=k;l++)
            		  {
                          By percentageField = By.xpath("//div[@id='addEditModifierValueId']//tr["+(l+1)+"]//input[@id='modifierPercent']");
                          
                          try
                          {
                                enter_text_value("Percentage Field", percentageField, percentage[l]);
                          }
                          catch(Exception e)
                          {
                                System.out.println(e.getMessage());
                          }
                         
                          if(k < codeValues.length-1)
                          {
                                By addIcon = By.xpath("//*[@id='discountModifierId']//tr["+(k+1)+"]//a[@title='Add']");
                                click_button("Add Icon ", addIcon);
                          }
                    }*/
            		  
            		  if(k < Codes_Percentage.length-1)
                      {
                            By addIcon = By.xpath("//*[@id='discountModifierId']//tr["+(k+1)+"]//a[@title='Add']");
                            click_button("Add Icon ", addIcon);
                      }
              }
              click_button("Multiplier Reduction Save Button", oSchedulesLibrary.addModifierWindowSave);
              }
        }
        else if(oParameters.GetParameters("TypeOfSchedule"+i).equalsIgnoreCase("Group Code Rates"))
        {
              waitFor(oSchedulesLibrary.addGroupCodeRatesScheduleButton, "Add Group Code Rates Schedule Button");
              
              click_button("Add Group Code Rates Schedule button", oSchedulesLibrary.addGroupCodeRatesScheduleButton);
                                      
         //     oSchedulesLibrary.groupCodeRatesScheduleDetails();
              
              addScheduleDetails(i,oSchedulesLibrary.groupCodeNameTextBox,oSchedulesLibrary.groupCodeEffectiveDate,oSchedulesLibrary.groupCodeTerminationDate);
              
              importSchedule(i,"importGroupCodeRateIframe",oSchedulesLibrary.ChooseGroupCodeScheduleFile);
              
              click_button("Schedule Save button",oSchedulesLibrary.groupCodeWindowSave);
              duplicateScheduleHandling(i);
              waitFor(oSchedulesLibrary.scheduleTypeDropDown, "Select a Schedule Type Drop Down");
        }
        else if(oParameters.GetParameters("TypeOfSchedule"+i).equalsIgnoreCase("Related Procedure Schedules"))
        {
        	oParameters.SetParameters("TRUE", "NO");
              
        	waitFor(oSchedulesLibrary.addRelatedProcedureScheduleButton, "Add Related Procedure Schedule Button");
              
            click_button("Add Related Schedules button", oSchedulesLibrary.addRelatedProcedureScheduleButton);
              
              addScheduleDetails(i,oSchedulesLibrary.relatedScheduleNameBox,oSchedulesLibrary.relatedScheduleStartDate,oSchedulesLibrary.relatedScheduleEndDate);
        
              enter_text_value("Related Schedule Discount text box",oSchedulesLibrary.relatedScheduleDiscount ,oParameters.GetParameters("Discount"+i));
              
              click_button("Schedule Save button", oSchedulesLibrary.scheduleSaveButton);
              duplicateScheduleHandling(i);
              
              if(!oParameters.GetParameters("TRUE").equalsIgnoreCase("YES"))
              {
              waitFor(oSchedulesLibrary.scheduleTypeDropDown, "Select a Schedule Type Drop Down");                
              
              fixed_wait_time(3);
              
              String[] category = oParameters.GetParameters("Category"+i).split(",");
              
              for(int k=0;k<category.length;k++)
              {
            	  click_button("Category Add Icon", oSchedulesLibrary.categoryAddIcon);
            	  
            	  String[] categoryNameDiscount = category[k].split("-");
            	  
            	  enter_text_value("Category Name", oSchedulesLibrary.categoryNameBox, categoryNameDiscount[0]);
            	  enter_text_value("Category Discount", oSchedulesLibrary.categoryDiscount, categoryNameDiscount[1]); 
            	  click_button("Save Button", oSchedulesLibrary.categorySave);
            	  waitFor(oSchedulesLibrary.categoryAddIcon, "Category Add Button");
               }
              
              String[] subcategory = oParameters.GetParameters("SubCategory"+i).split("-");
              
              for(int n=0;n<subcategory.length;n++)
              {
            	  click_button("Sub Category Add Icon", oSchedulesLibrary.subCategoryAddIcon);
            	  
                  enter_text_value("Sub Category Name", oSchedulesLibrary.subCategoryNameBox, subcategory[n]);
                    
                  click_button("Save Button", oSchedulesLibrary.categorySave);
                  waitFor(oSchedulesLibrary.subCategoryAddIcon, "SubCategory Add Icon");
              }
              
              
              String[] procedureCode = oParameters.GetParameters("ProcedureCode"+i).split(",");
              
              for(int p=0;p<procedureCode.length;p++)
              {
                    String[] procedureCodeToCreate = procedureCode[p].split("-");
                    
                    By click_Category = By.xpath("//div[contains(@class,'col-lg-3 col-md-3')]//span[contains(.,'"+procedureCodeToCreate[0]+"')]");
                    
                    By click_SubCategory = By.xpath("//div[contains(@class,'col-lg-3 col-md-3')]//span[contains(.,'"+procedureCodeToCreate[1]+"')]");
                    
                    click_button("Category", click_Category);
                    
                    waitFor(click_SubCategory, "Sub Category");
                    click_button("Sub Category", click_SubCategory);
                    
                    waitFor(oSchedulesLibrary.addProcedureLink, "Add Procedure Link");
                    click_button("Procedure Link", oSchedulesLibrary.addProcedureLink);
                    
                    enter_text_value("Procedure Name", oSchedulesLibrary.procedureCodeBox, procedureCodeToCreate[2]);
                    
                    click_button("Save Button", oSchedulesLibrary.categorySave);
              }
              
        	}
        } 
    	}
	}
	
	public void addScheduleDetails(int i,By scheduleName,By Effective ,By Termination)
	{
		enter_text_value("Schedule Name text box", scheduleName, oParameters.GetParameters("ScheduleName"+i));
		
		enter_text_value("Effective Date", Effective, oParameters.GetParameters("Effective"+i));
		performKeyBoardAction("ENTER");
		
		if(oParameters.GetParameters("Termination"+i).isEmpty())
			System.out.println("Termination Date is empty");
		else
		{
			enter_text_value("Termination Date", Termination, oParameters.GetParameters("Termination"+i));
			performKeyBoardAction("ENTER");
		}
		
		if(oParameters.GetParameters("ScheduleName"+i).equals("GroupCode With LS(Do Not Delete)"))
			click_button("LS Code Check Box", oSchedulesLibrary.LSCodeCheckBox);
		else{}
	}
	
	
	public void importSchedule(int i,String frame,By uploadButton)
	{
		driver.switchTo().frame(frame);
		
		click_button("Upload File Button",uploadButton);
		
		String path = oParameters.GetParameters("FilePath"+i);
		fixed_wait_time(3);
	
		try
		{
			Runtime.getRuntime().exec(path);
			fixed_wait_time(1);
			driver.switchTo().defaultContent();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
		
	
	public void addingSchedules()
	{
		login("EDIT");
		changePricingEngine();
		oSchedulesLibrary.xpathChange();
		oSchedulesLibrary.navigateToSchedules();
		addSchedules();
	}
	
	By addRateSheet = By.xpath("//div[@id='ratesheetSection']//a[@title='Add Rate Sheet']/i[@class='left fa fa-plus-square']");
	
	By addRateSheetWindow = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//div[@id='addRateSheetModal']//div[@title='Add Rate Sheet']");
	
	//Method to click on RateSheet Icon.
	public void addRateSheetIcon()
	{
		if(IsElementDisplayed("Add RateSheet Button", addRateSheet))
		{	
			oReport.AddStepResult("Add Rate Sheet Button", "Navigated to RateSheet plugin and verified that Add Rate Sheet button is displayed", "PASS");
			click_button("Add Rate Sheet Link", addRateSheet);
			
			if(IsElementDisplayed("Add Rate Sheet Window", addRateSheetWindow))
				oReport.AddStepResult("Add Rate Sheet Window", "Clicked on add rate sheet link and verifed that add sheet sheet window is displayed", "PASS");
			else
				oReport.AddStepResult("Add Rate Sheet Window", "Clicked on add rate sheet link and verifed that add sheet sheet window is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Add Rate Sheet Button", "Navigated to RateSheet plugin and verified that Add Rate Sheet button is not displayed", "FAIL");
	}
	
	
	By rateSheetName = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='ratesheetName']");
	
	By rateSheetCode = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='ratesheetCode']");
	
	By rateSheetType = By.xpath("//form[@id='addEditRatesheetForm']//input[@value='Model']");
	
	By rateSheetEffectiveDate = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='startDate']");
	
	By rateSheetTerminationDate = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='endDate']");
	
	By DRGGrouping = By.xpath("//workflow-modal[@class='ng-isolate-scope']//select[@id='claimDrg']");
	
	By qualifyInpatientClaims = By.xpath("//div[@model='ratesheet.qualInpatient']//select[@id='qualInpatient']");
	
	By qualifyOutpatientClaims = By.xpath("//div[@model='ratesheet.qualOutpatient']//select[@id='qualOutpatient']");
	
	By copyExistingRateSheet = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='copyClosed']");
	
	By selectRateSheet = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='copyRateSheetSet']");
	
	By includeTermNotes = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='includeNotes']");
	
	By addANote = By.xpath("//div[@note-value='ratesheet.ratesheetNote']//textarea[@placeholder='Add a note']");
	
	By production = By.xpath("//form[@id='addEditRatesheetForm']//input[@value='Prod']");
	
	By modeling = By.xpath("//form[@id='addEditRatesheetForm']//input[@value='Model']");
	
	By firstRateSheet = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//ul[@id='-list']//li[1]");
	
	//Method to enter a RateSheet Details.
	public void AddRateSheetDetails(String i)
	{
		/*long SystemTime;
		
		if(i.isEmpty())
			SystemTime = System.currentTimeMillis();
		else
			SystemTime = Long.parseLong("");*/
		
		enter_text_value("Rate Sheet name", rateSheetName, oParameters.GetParameters("RateSheetName"+i));//+Long.toString(SystemTime));
		
		enter_text_value("Rate Sheet code", rateSheetCode, oParameters.GetParameters("RateSheetCode"+i));//+Long.toString(SystemTime));			
		
		facilityName("RATESHEET", "Apollo srn facility");
		
		if(oParameters.GetParameters("RateSheetType"+i).equals("Production"))
			click_button("Production Radio Box", production);
		else
			click_button("Modeling Radio Box", modeling);
		
		if(!oParameters.GetParameters("SelectDRGGrouping"+i).isEmpty())
			selectOption(DRGGrouping,"visibletext",oParameters.GetParameters("SelectDRGGrouping"+i));
		
		selectOption(qualifyInpatientClaims,"visibletext",oParameters.GetParameters("QualifyInpatientClaimsOn"+i));
		selectOption(qualifyOutpatientClaims,"visibletext",oParameters.GetParameters("QualifyOutpatientClaimsOn"+i));
		
		click_button("Rate Sheet Effective Date", rateSheetEffectiveDate);
		enter_text_value("Rate Sheat Effective Date", rateSheetEffectiveDate,oParameters.GetParameters("EffectiveDate"+i));
		performKeyBoardAction("ENTER");
		
		if(oParameters.GetParameters("TerminationDate"+i).isEmpty())
			System.out.println("Termination Date is Blank");
		else
		{
			click_button("Rate Sheet Termination Date", rateSheetTerminationDate);
			enter_text_value("Rate Sheat Termination Date", rateSheetTerminationDate,oParameters.GetParameters("TerminationDate"+i));
			performKeyBoardAction("ENTER");
		}
		
		if(oParameters.GetParameters("CopyExistingRateSheet"+i).equals("YES"))
		{	
			click_button("Copy Existing Rate Sheet", copyExistingRateSheet);
			
			if(oParameters.GetParameters("SelectRateSheet"+i).isEmpty())
			{
				enter_text_value("Select Rate Sheet", selectRateSheet,"");
				performKeyBoardAction("ENTER");
				
				waitFor(firstRateSheet,"First RateSheet");
				click_button("First Rate Sheet", firstRateSheet);
			}
			else
			{
				enter_text_value("Select Rate Sheet", selectRateSheet, oParameters.GetParameters("SelectRateSheet"+i));
				performKeyBoardAction("ENTER");
			
				By RateSheet = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("SelectRateSheet"+i)+"']");
				click_button("Select RateSheet", RateSheet);
			}
			
			fixed_wait_time(2);
			waitFor(includeTermNotes,"Include Term Notes CheckBox");
			click_button("Include Term Notes", includeTermNotes);
		}
		
		if(oParameters.GetParameters("IncludeTermsNotes"+i).equals("YES"))
		{
			fixed_wait_time(1);
			click_button("Add A Note", addANote);
			enter_text_value_jscript("Add A Note", addANote, oParameters.GetParameters("AddANote"+i));
		}
	}
	
	
	By rateSheetCodeSaveButton = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='button.saveId']");
	
	By rateSheetCodeCancelButton = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='button.cancelId']");
	
	By sectionCancelButton = By.xpath("//div[@id='addEditRateSheetSection']//input[@id='button.cancelId']");
	
	By sectionDiscardButton = By.xpath("//div[@class='form-button-panel']//input[@class='btn btn-danger discard-red']");
	
	By rateSheetRespectivePage = By.xpath("//div[@id='ratesheetSection']//div[@class='col-lg-6 col-md-6 col-sm-6 xl-header ng-binding']");

	By ErrorAddingRateSheet = By.xpath("//div[contains(text(),'Error Adding Rate Sheet')]");
	
	By ErrorAddingOKButton = By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");
	
	//Method to click on 'RateSheet Save Button'.
	public void rateSheetSaveButton(String i)
	{
		if(IsElementEnabled("RateSheet save Button", rateSheetCodeSaveButton))
		{	
			oReport.AddStepResult("RateSheet Save Button", "In 'Add RateSheet Tab' filled all the details and clicked on save button", "PASS");
			 
			click_button("Rate Sheet Save Button", rateSheetCodeSaveButton);
				
			if(IsDisplayed("Error Adding Rate Sheet", ErrorAddingRateSheet))
			{
				oReport.AddStepResult("Error Adding Rate Sheet", "In 'Add Rate Sheet' Window filled all mandatory fields and clicked on save button and verified that '"+oParameters.GetParameters("RateSheetName"+i)+"' RateSheet is already exist", "WARNING");
					
				click_button("Error Adding Ok Button", ErrorAddingOKButton);
					
				click_button("RateSheet Cancel Button", rateSheetCodeCancelButton);
					
				click_button("Discard Button", sectionDiscardButton);
			}
			else
			{
				waitFor(rateSheetRespectivePage,"Respective RateSheet");
				fixed_wait_time(3);
			
				if(oParameters.GetParameters("RateSheetName"+i).equals(get_text(rateSheetRespectivePage)))
				{
					oReport.AddStepResult("Add Rate Sheet Save Button", "Clicked on 'Add Rate Sheet Icon' and filled all mandatory fileds and clicked on save button and verified that "+oParameters.GetParameters("RateSheetName"+i)+" Ratesheet is displayed", "PASS");
					count++;
				}
				else
					oReport.AddStepResult("Add Rate Sheet Save Button", "Clicked on 'Add Rate Sheet Icon' and filled all mandatory fileds and clicked on save button and verified that "+oParameters.GetParameters("RateSheetName"+i)+" Ratesheet is not displayed", "FAIL");
			}
			
			System.out.println("Total Number of " +oParameters.GetParameters("RateSheetName"+i)+ "RateSheet Uploaded:" + count);
		}	
		else
			oReport.AddStepResult("RateSheet Save Button", "In 'Add RateSheet Tab' filled all the details and clicked on save button", "FAIL");
	}
	
	
	By stopLossTab = By.xpath("//a[@class='ng-binding' and contains(text(),'Stop Loss')]");
	
	By addStopLossSectionLink = By.xpath("//a[@class='bold ng-scope ng-binding' and contains(text(),'Add a Stop Loss Section.')]");
	
	By addStopLossSectionbutton = By.xpath("//input[@ng-click='addStopLoss()']");
	
	By stopLossSectionWimdow = By.xpath("//div[@class='xl-header truncate ng-binding' and @title='Add Stop Loss Section']");
	
	//Method to click on 'Add Section Icon'.
	public void addSectionIcon(String i)
	{
		
		By SectionLink = null;
		By SectionButton = null;
		By SectionWindow = null;
		
		
		if(oParameters.GetParameters("SectionType"+i).equalsIgnoreCase("Section"))
		{
			SectionLink = addTermSectionLink;
			SectionButton = AddSectionButton;
			SectionWindow = addSectionWindow;
			
		}	
		else if(oParameters.GetParameters("SectionType"+i).equalsIgnoreCase("StopLossSection"))
		{
			SectionLink = addStopLossSectionLink;
			SectionButton = addStopLossSectionbutton;
			SectionWindow = stopLossSectionWimdow;
			
			navigate("StopLoss Tab", stopLossTab);
		}	
	
		if(IsElementDisplayed("Add Term Section Link", SectionLink))
			click_button("Add Term Section link", SectionLink);
		else
			click_button("Add Section Button", SectionButton);
		
		if(IsElementDisplayed("Add Section Window", SectionWindow))
			oReport.AddStepResult("Add Section Window", "Clicked on add Section Icon and verified that 'Add Section' Window is displayed", "PASS");
		else
			oReport.AddStepResult("Add Section Window", "Clicked on add Section Icon and verified that 'Add Section' Window is not displayed", "FAIL");
	}
	
	
	//Method to handle 'Advanced Qualifier Option'.
	public void advancedQualifierOption(String i)
	{
		if(oParameters.GetParameters("AdvancedQualifierOptions"+i).equals("YES"))
		{
			if(IsDisplayed("Advanced Qualifier Options For Section", advancedQualifierOptions_Section))
				click_button("Advanced Qualifier Options For Section", advancedQualifierOptions_Section);
			else
				click_button("Advanced Qualifier Options For Term", advancedQualifierOptions_Term);
			
			if(!oParameters.GetParameters("ProcedureGroupCodeSetDD"+i).isEmpty())
			{
				selectOption(procedureGroupCodeSet,"visibletext",oParameters.GetParameters("ProcedureGroupCodeSetDD"+i));
				enter_text_value("Procedure Group Code Set Search Box", procedureGroupCodeSetSearchBox, oParameters.GetParameters("ProcedureGroupCodeSetSB"+i));
				performKeyBoardAction("ENTER");
				fixed_wait_time(2);
				selectOption(procedureGropuCodeSetEffectivePeriod,"value",oParameters.GetParameters("ProcedureGroupCodeSetEPDD"+i));
			}
			
			if(!oParameters.GetParameters("DiagnosisCodeSetSB"+i).isEmpty())
			{
				enter_text_value("Diagnosis Code Set", diagnosisCodeSetSearchBox, oParameters.GetParameters("DiagnosisCodeSetSB"+i));
				performKeyBoardAction("ENTER");
				fixed_wait_time(2);
				selectOption(diagnosisCodeSetEffectivePeriod,"value",oParameters.GetParameters("DiagnosisCodeSetEPDD"+i));
			}
			
			if(!oParameters.GetParameters("DRGCodeSetSB"+i).isEmpty())
			{
				enter_text_value("DRG Code Set", drgCodeSetSearchBox, oParameters.GetParameters("DRGCodeSetSB"+i));
				performKeyBoardAction("ENTER");
				fixed_wait_time(2);
				selectOption(drgEffectivePeriod,"value",oParameters.GetParameters("DRGCodeSetEPDD"+i));
			}
			
			if(!oParameters.GetParameters("FeeScheduleSB"+i).isEmpty())
			{
				enter_text_value("Fee Schedule Set", feeScheduleSearchBox, oParameters.GetParameters("FeeScheduleSB"+i));
				performKeyBoardAction("ENTER");
				fixed_wait_time(2);
				
				By FeeSchedule = By.xpath("//form[@id='qualificationGroupOptions']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("FeeScheduleSB"+i)+"']");
				
				if(IsDisplayed("Fee Schedule Search Box", FeeSchedule))
					click_button("Fee Schedule Search Value", FeeSchedule);	
				
				selectOption(feeScheduleEffectivePeriod,"value",oParameters.GetParameters("FeeScheduleEP"+i));
			}
		}
	}
	
	/*By addStopLossSectionLink = By.xpath("//a[@class='bold ng-scope ng-binding' and contains(text(),'Add a Stop Loss Section.')]");
    
    By addStopLossSectionbutton = By.xpath("//input[@ng-click='addStopLoss()']");*/
    
   // By stopLossSectionWimdow = By.xpath("//div[@class='xl-header truncate ng-binding' and @title='Add Stop Loss Section']");
    
   // By stopLossTab = By.xpath("//a[@class='ng-binding' and contains(text(),'Stop Loss')]");
    
    By stopLossSectionName = By.xpath("//input[@id='slSectionName']");
    
    By includeExclusionsInStoploss = By.xpath("//label[contains(., 'Include Exclusions in stop loss calculations')]//input");
    
    By useDRGUserDataInStopLossCalculations = By.xpath("//label[contains(.,'Use DRG user rate data in stop loss calculations and formulas')]//input");
    
    By drgUserRateSet = By.xpath("//input[@id='drgUserRateMaster']");
           
    By stoplossSectionMinimumRateType = By.xpath("//select[@id='minRateType']");
    
    By stoplossSectionMinimumRateAmount = By.xpath("//input[@id='maxRateAmount']");
    
    By ShowStopLossOptions = By.xpath("//div[@class='scrollable-content']//i[@class='fa  fa-caret-right']");

	By StopLossAddedSection = By.xpath("//li[@class=' list-header light toolbar data-row sections ']//span[@class='bold ng-binding']");
	
	public void AddStopLossSectionDetails(String i,String rateSheetName)
    {
		String[] sectionName = oParameters.GetParameters("SectionName"+i).split(",");
		
		By addedSection =null;
           
           for(int k=0;k<sectionName.length;k++)
           {
                  
              enter_text_value("Stop Loss Section Name", stopLossSectionName, sectionName[k]);
                  
               if(oParameters.GetParameters("IncludeExclusionInStopLoss"+i).equals("YES"))
            	   check_checkbox(includeExclusionsInStoploss);
                  
               if(oParameters.GetParameters("UseDRGUserDataInStopLossCalculations"+i).equals("YES"))
               {
            	   check_checkbox(useDRGUserDataInStopLossCalculations);
            	   enter_text_value("DRG User Rate Set", drgUserRateSet, "DRGUserRateSet"+i);
               }
                  
               
               if(!oParameters.GetParameters("MaxRateType"+i).isEmpty())
               {
            	   selectOption(maximumRateType_Section,"visibletext",oParameters.GetParameters("MaxRateType"+i));
               
            	   if(oParameters.GetParameters("MaxRateType"+i).equalsIgnoreCase("Per Case"))
            		   enter_text_value("Maximum Amount", maximumAmount, oParameters.GetParameters("MaxAmount/Percentage"+i));
            	   else if(oParameters.GetParameters("MaxRateType"+i).equalsIgnoreCase("Per Length of Stay"))
            		   enter_text_value("Maximum Amount", maximumAmount, oParameters.GetParameters("MaxAmount/Percentage"+i));
            	   else if(oParameters.GetParameters("MaxRateType"+i).equalsIgnoreCase("Percentage"))
            		   enter_text_value("Maximum Amount", maximumAmount, oParameters.GetParameters("MaxAmount/Percentage"+i));
            	   else
            		   System.out.println(" "+oParameters.GetParameters("MaxRateType")+"");
              } 
               
              
               if(!oParameters.GetParameters("MinRateType"+i).isEmpty())
               {
            	   selectOption(stoplossSectionMinimumRateType,"visibletext",oParameters.GetParameters("MinRateType"+i));
            	  
            	   if(oParameters.GetParameters("MinRateType"+i).equalsIgnoreCase("Per Case"))
            		   enter_text_value("Minimum Amount", stoplossSectionMinimumRateAmount, oParameters.GetParameters("MinAmount/Percentage"+i));
            	   else if(oParameters.GetParameters("MaxRateType"+i).equalsIgnoreCase("Per Length of Stay"))
            		   enter_text_value("Minimum Amount", stoplossSectionMinimumRateAmount, oParameters.GetParameters("MinAmount/Percentage"+i));
            	   else if(oParameters.GetParameters("MaxRateType"+i).equalsIgnoreCase("Percentage"))
            		   enter_text_value("Minimum Amount", stoplossSectionMinimumRateAmount, oParameters.GetParameters("MinAmount/Percentage"+i));
            	   else
                       System.out.println(" "+oParameters.GetParameters("MinRateType")+"");
               }
               
               if(oParameters.GetParameters("ShowStoplossOptions"+i).equals("yes"))
               {
            	   click_button("Show Stop loss options", ShowStopLossOptions);
            	   
            	   By stoplossOptions = By.xpath("//form[@id='addStopLossModel']//label[contains(.,'"+oParameters.GetParameters("ShowStoplossOptionsValue"+i)+"')]//input");

            	   click_button("StopLoss", stoplossOptions);
               }      
                  
               int j;
                  
               if(IsDisplayed("Section Save Button", rateSheetCodeSaveButton) && IsDisplayed("Add StopLoss Window", stopLossSectionWimdow))
               {
            	   oReport.AddStepResult("Add Section", "In 'ADD Section Tab' filled all details and verified that save button is enabled", "PASS");
            	   click_button("Rate Sheet Save Button", rateSheetCodeSaveButton);
                        
            	   fixed_wait_time(2);
            	   
            	   addedSection = StopLossAddedSection;
            	   
                   waitFor(addedSection,"Added Sections");
                        
                   List<WebElement> Sections = convertToWebElements(addedSection);
                  
                   for(j=0;j<Sections.size();j++)
                   {
                	   if(Sections.get(j).getText().contains(sectionName[k]))
                	   {
                		   oReport.AddStepResult("Added Section", "Filled all mandatory feilds in Add Section Window and clicked on save button and verified that new '" +sectionName[k]+ "' Section is added  ", "PASS");
                                                           
                		   break;
                	   }
                	   else
                		   System.out.println("Searching for created Section");
                   	}
                        
                   if(j>=Sections.size())
                	   oReport.AddStepResult("Added Section", "Filled all mandatory feilds in Add Section Window and clicked on save button and verified that new section is not added", "FAIL");
                  
                   fixed_wait_time(2);
               	}
           }
    }

	
	
	By AddSectionButton = By.xpath("//div[@id='ratesheetView']//input[@class='btn-default btn-xs mar-l-5']");
	
	By addSectionWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Add Section']");
	
	By sectionNameFeild = By.xpath("//input[@id='sectionName']");
	
	By sectionQualificationGroupFeild = By.xpath("//form[@id='addEditRateSheetSectionForm']//input[@id='qualificationGroup']");
	
	By addedSection = By.xpath("//div[@id='iPSlidePanelParent']//li[@class='ng-scope']//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4']//span");
	
	By addTermSectionLink = By.xpath("//div[@id='iPSlidePanelParent']//a[contains(text(),'Add a Term Section.')]");
	
	By maximumRateType_Section = By.xpath("//select[@id='maxRateType']");
	
	By maximumAmount = By.xpath("//input[@id='maxRateAmount']");
	
	By respectiveRateSheet = By.xpath("//div[@id='ratesheetSection']//header-component//div[contains(@class,'col-lg-6 col-md-6 col-sm-6')]");
	
	By copySection = By.xpath("//form[@id='addEditRateSheetSectionForm']//input[@id='selectSection']");
	
	By rateSheetSection = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='searchfortermaction']");
	
	By effectiveRateSheet = By.xpath("//select[@id='ratesheetPeriod']");
	
	By section = By.xpath("//select[@id='moveSec']");
	
	By includeTermNoteSection = By.xpath("//form[@id='addEditRateSheetSectionForm']//input[contains(@id,'cnCheckbox')]");
	
	By addSectionAfter_Before = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//span[@class='dropdown-text hide-overflow ng-binding']");
	
	By sectionPriority = By.xpath("//input[@id='sectionPriority']");
	
	By advancedQualifierOptions_Term = By.xpath("//form[@id='addEditTermForm']//span[@toggle-target='qualificationGroupOptionsType']");
	
	By advancedQualifierOptions_Section = By.xpath("//form[@id='addEditRateSheetSectionForm']//span[@toggle-target='qualificationGroupOptionsType']");
	
	By procedureGroupCodeSet = By.xpath("//select[@id='procedureGroup']");
	
	By procedureGroupCodeSetSearchBox = By.xpath("//input[@id='pGrpCSSearch']");
	
	By procedureGropuCodeSetEffectivePeriod = By.xpath("//select[@id='procedureGroupPeriod']");
	
	By diagnosisCodeSetSearchBox = By.xpath("//input[@id='DiagCSSearch']");
	
	By diagnosisCodeSetEffectivePeriod = By.xpath("//select[@id='DiagCSPeriod']");
	
	By drgCodeSetSearchBox = By.xpath("//input[@id='DRGCSSearch']");
	
	By drgEffectivePeriod = By.xpath("//select[@id='DrgPeriod']");
	
	By feeScheduleSearchBox = By.xpath("//input[@id='feeScheduleSearch']");
	
	By feeScheduleEffectivePeriod = By.xpath("//select[@id='feeSchedPeriod']");
	
	By RateSheetResults = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 col-mdlg-7 mar-r-0']//ul[@class='dropdown-menu']");
	
	By EmptyQG = By.xpath("//div[@class='clearBoth col-lg-7 col-md-7 col-sm-7 mar-r-13']//div[text()='You must select an option from the search results.']");
	
	//Method to enter a data in 'Add Section window'.
	public void AddSectionDetails(String i,String rateSheetName)
	{
		int j;
		
		String[] sectionName = oParameters.GetParameters("SectionName"+i).split(",");
		
		String[] qualificationName = oParameters.GetParameters("QualificationGroup"+i).split(",");
		
		
		for(int k=0;k<sectionName.length;k++)
		{
			if(rateSheetName.isEmpty())
			{
				waitFor(respectiveRateSheet,"RateSheet Title Bar");
				addSectionIcon(String.valueOf(i));
			}	
			else if(rateSheetName.contains("Edit"))
				System.out.println("");
			else 
			{
				oRateSheetLibrary.searchRateSheet("Production",rateSheetName);
				addSectionIcon(String.valueOf(i));
			}
			
		
			if(oParameters.GetParameters("SectionType"+i).equalsIgnoreCase("Section"))
			{
			
				enter_text_value("Section Name", sectionNameFeild, sectionName[k]);
	
				if(oParameters.GetParameters("AddSectionAfterBefore"+i).isEmpty())
					System.out.println(" Adding section ");
				else
				{
					click_button("Add Section After/Before", addSectionAfter_Before);
			
					By addSection = By.xpath("//li[@id='i[__valueField]']/a[contains(.,'"+oParameters.GetParameters("AddSectionAfterBefore"+i)+"')]");
			
					click_button("Add Section After/Before", addSection);
			
					enter_text_value("Search Section", sectionPriority, oParameters.GetParameters("AddSectionAfterBeforeSearch"+i));
					performKeyBoardAction("ENTER");
			
					By SectionPosition = By.xpath("//div[@class='mar-b-0 form-group  form-group ']//ul/li[contains(.,'"+oParameters.GetParameters("AddSectionAfterBeforeSearch"+i)+"')]");
			
					waitFor(SectionPosition,"Section List");
					click_button("Select section position", SectionPosition);
				}
		
				if(oParameters.GetParameters("CopySection"+i).equals("YES"))
				{
					click_button("Copy Section", copySection);
			
					if(oParameters.GetParameters("SearchRateSheetName"+i).isEmpty())
					{
						enter_text_value("Search RateSheet Name", rateSheetSection,"");
						performKeyBoardAction("ENTER");
				
						waitFor(firstRateSheet,"First RateSheet");
						click_button("First Rate Sheet", firstRateSheet);
					}
					else
					{
						enter_text_value("Search RateSheet Name", rateSheetSection, oParameters.GetParameters("SearchRateSheetName"+i));
						fixed_wait_time(3);
				
						if(IsElementDisplayed("RateSheet Results", RateSheetResults))
						{
							By RateSheetName = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 col-mdlg-7 mar-r-0']//ul[@class='dropdown-menu']/li/a[contains(.,'"+oParameters.GetParameters("SearchRateSheetName"+i)+"')]");
							waitFor(RateSheetName,"RateSheet Details");
							click_button("RateSheetName",RateSheetName);
						}
				
					}
		
					selectOption(effectiveRateSheet,"value",oParameters.GetParameters("EffectivePeriod"+i));
		
					selectOption(section,"value",oParameters.GetParameters("Section"+i));
			
					fixed_wait_time(1);
					click_button("Include Term Note Section", includeTermNoteSection);
				}
		
				for(int l=k;l<=k;l++)
				{
					enter_text_value("Qualification Group", sectionQualificationGroupFeild,qualificationName[l]);
					performKeyBoardAction("ENTER");
					fixed_wait_time(2);
		
					By Qualification = By.xpath("//form[@id='addEditRateSheetSectionForm']//ul[@id='-list']//li//a[not(text())]//b[text()='"+qualificationName[l]+"']");
		
					if(IsDisplayed("Qualification Group", Qualification))
						click_button("Qualification Group", Qualification);
		
		
					advancedQualifierOption(i);
		
					if(oParameters.GetParameters("MaxRateType"+i).equalsIgnoreCase("Per Case"))
					{
						selectOption(maximumRateType_Section,"visibletext",oParameters.GetParameters("MaxRateType"+i));
						enter_text_value("Maximum Amount", maximumAmount, oParameters.GetParameters("MaxAmount/Percentage"+i));
					}
					else if(oParameters.GetParameters("MaxRateType"+i).equalsIgnoreCase("Per Length of Stay"))
					{
						selectOption(maximumRateType_Section,"visibletext",oParameters.GetParameters("MaxRateType"+i));
						enter_text_value("Maximum Amount", maximumAmount, oParameters.GetParameters("MaxAmount/Percentage"+i));
					}
					else if(oParameters.GetParameters("MaxRateType"+i).equalsIgnoreCase("Percentage"))
					{
						selectOption(maximumRateType_Section,"visibletext",oParameters.GetParameters("MaxRateType"+i));
						enter_text_value("Maximum Amount", maximumAmount, oParameters.GetParameters("MaxAmount/Percentage"+i));
					}
					else
						System.out.println(" "+oParameters.GetParameters("MaxRateType")+"");
			
			
					if(IsDisplayed("Section Save Button", rateSheetCodeSaveButton))
					{
						oReport.AddStepResult("Add Section", "In 'ADD Section Tab' filled all details and verified that save button is enabled", "PASS");
						click_button("Rate Sheet Save Button", rateSheetCodeSaveButton);
				
						fixed_wait_time(2);
				
						if(IsDisplayed("Empty QG", EmptyQG))
						{
							oReport.AddStepResult("Section Tab", "While creating '"+sectionName[k]+"' section, passing '"+qualificationName[l]+"' as a QG, but this QG is not selecting", "WARNING");
					
							click_button("Section Cancel Button", sectionCancelButton);
					
							fixed_wait_time(2);
					
							click_button("Discard Button", sectionDiscardButton);
						}
						else
						{
							waitFor(addedSection,"Added Sections");
			
							List<WebElement> Sections = convertToWebElements(addedSection);
			
							for(j=0;j<Sections.size();j++)
							{
								if(Sections.get(j).getText().contains(sectionName[k]))
								{
									//if(oParameters.GetParameters("AddSectionAfterBefore").equalsIgnoreCase("Add Section Before"))
									oReport.AddStepResult("Added Section", "Filled all mandatory feilds in Add Section Window and clicked on save button and verified that new '" +sectionName[k]+ "' Section is added  ", "PASS");
									
									break;
								}
								else
									System.out.println("Searching for created Section");
							}
				
							if(j>=Sections.size())
								oReport.AddStepResult("Added Section", "Filled all mandatory feilds in Add Section Window and clicked on save button and verified that new section is not added", "FAIL");
			
							fixed_wait_time(2);
						}
					}
				}
	
			}	
			else if(oParameters.GetParameters("SectionType"+i).equalsIgnoreCase("StopLossSection"))
				AddStopLossSectionDetails(String.valueOf(i),oParameters.GetParameters("RateSheetName"+i));
	}
}	
	
	//Method to click Section Save Button.
	public void sectionSaveButton(String i)
	{
		int j;
		
		if(IsDisplayed("Empty QG", EmptyQG))
		{
			oReport.AddStepResult("Section Tab", "While creating "+oParameters.GetParameters("SectionName"+i)+" section, passing "+oParameters.GetParameters("QualificationGroup"+i)+"  QG, but this QG is not selecting", "WARNING");
			
			click_button("Section Cancel Button", sectionCancelButton);
			
			fixed_wait_time(2);
			
			click_button("Discard Button", sectionDiscardButton);
		
		}
		else if(IsElementEnabled("Section Save Button", rateSheetCodeSaveButton))
		{
			oReport.AddStepResult("Add Section", "In 'ADD Section Tab' filled all details and verified that save button is enabled", "PASS");
			click_button("Rate Sheet Save Button", rateSheetCodeSaveButton);
		
			waitFor(addedSection,"Added Sections");
		
			List<WebElement> Sections = convertToWebElements(addedSection);
		
			for(j=0;j<Sections.size();j++)
			{
				if(oParameters.GetParameters("SectionName"+i).contains(Sections.get(j).getText()))
				{
					if(oParameters.GetParameters("AddSectionAfterBefore").equalsIgnoreCase("Add Section Before"))
						oReport.AddStepResult("Added Section", "Filled all mandatory feilds in Add Section Window and clicked on save button and verified that new section is added Before " + oParameters.GetParameters("AdddSectionAfterBeforeSearch"), "PASS");
								
					break;
				}
				else
					System.out.println("Searching for created Section");
			}
			
			if(j>=Sections.size())
				oReport.AddStepResult("Added Section", "Filled all mandatory feilds in Add Section Window and clicked on save button and verified that new section is not added", "FAIL");
		
			fixed_wait_time(2);
		}
	}
	
	
	
	By addTermWindow = By.xpath("//div[@id='showTermAddModal']//div[@title='Add Term']");
	
	By addTerm = By.xpath("//div[@id='iPSlidePanelParent']//li[@class='ng-scope'][1]//div[@class='pull-right icon-container']//a[1]//i[1]");
	
	
	//Method to click add term icon.
	public void clickAddTerm(String sectionName)
	{
		if(sectionName.isEmpty())
			click_button("Add Term Button", addTerm);
		else
		{
			By AddTermIcon = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4'][contains(.,'"+sectionName+"')]/..//div[@class='pull-right icon-container']//a[1]//i[1]");
			click_button("Add Term Button", AddTermIcon);
		}
		
		if(IsElementDisplayed("Add Term Window", addTermWindow))
			oReport.AddStepResult("Add Term Window", "Clicked on 'Add Term Icon' and verified that 'Add Term Window' is displayed", "PASS");
		else
			oReport.AddStepResult("Add Term Window", "Clicked on 'Add Term Icon' and verified that 'Add Term Window' is not displayed", "FAIL");

	}
	
	
	By addedSections = By.xpath("//div[@id='iPSlidePanelParent']//li[@class='ng-scope']//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4']//span");

	
	//Method to identitfy section.
	public void checkForSections(String sectionName)
	{
		int j;
		
		try
		{
			if(!IsElementDisplayed("Sections List", addedSections))
			{
				AddSectionDetails("","");
				sectionSaveButton("");
				oReport.AddStepResult("Section in Ratesheet", "Opened ratesheet doesnt contain any sections, hence created new section "+oParameters.GetParameters("SectionName"), "INFO");
			}
			else
			{		
				List<WebElement> Sections = convertToWebElements(addedSections);
		
				for( j=0;j<Sections.size();j++)
				{
					if(Sections.get(j).getText().contains(sectionName))
						break;
				}
				if(j >= Sections.size())
				{
					AddSectionDetails("","");
					sectionSaveButton("");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public By termName = By.xpath("//input[@id='termName']");
	
	By qualificationGroupFeild = By.xpath("//workflow-modal[@id='addIpTerm']//input[@id='qualificationGroup']");
	
	By firstQualification = By.xpath("//workflow-modal[@id='addIpTerm']//ul[@id='-list']//li[1]");
	
	By selectRateType = By.xpath("//form[@id='addEditTermForm']//select[contains(@ng-options,'item as spec.objectList[item][spec.optionText]')]");
	
	By selectTierBasis = By.xpath("//select[@id='tierBasis']");
	
	By Form_ThroughFeilds = By.xpath("//form[@id='addEditTermForm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li[@class='data-row ng-scope']");
	
	By termnumbers = By.xpath("//div[@id='iPSlidePanelParent']//ul[@class='data-list']//span[3]");
	
	public By tierBasisRateType = By.xpath("//select[@id='tierRateType']"); 
	
	By addTermAfter_Before = By.xpath("//workflow-modal[@id='addIpTerm']//span[@class='dropdown-text hide-overflow ng-binding']");
			
	By addTermAfterBeforeSearchFeild = By.xpath("//input[@id='placingTerm']");
	
	By advancedQualifiersOptions_Term = By.xpath("//form[@id='addEditTermForm']//span[@toggle-target='qualificationGroupOptionsType']");
	
	 
	//Method to enter a Term Details.
	public void addTermButton(String i,String rateSheetName,String sectionName)
	{
		oRateSheetLibrary.searchRateSheet(oParameters.GetParameters("TypeOfRateSheet"+i),rateSheetName);
		
		if(oParameters.GetParameters("TermType"+i).equals("Terms"))
			checkForSections(sectionName);
		
		if(oParameters.GetParameters("SectionName"+i).isEmpty() && oParameters.GetParameters("TermType"+i).equals("Exclusions"))
			ExclusionTab(String.valueOf(i));
		else if(!oParameters.GetParameters("SectionName"+i).isEmpty() && oParameters.GetParameters("TermType"+i).equals("StopLossTerm"))
		{
			navigate("StopLoss Tab", stopLossTab);
			oRateSheetLibrary.clickAddTerm(sectionName);
			oRateSheetLibrary.stopLossTab(i);
		}
		else
		{
			clickAddTerm(sectionName);
		
			enter_text_value("Term Name", termName, oParameters.GetParameters("TermName"+i));
		
			if(oParameters.GetParameters("AddTermAfterBefore"+i).isEmpty())
				System.out.println("Adding Term");
			else
			{		
				click_button("Add Term After/Before DropDown", addTermAfter_Before);
			
				By AddTerm = By.xpath("//li[@id='i[__valueField]']/a[contains(.,'"+oParameters.GetParameters("AddTermAfterBefore"+i)+"')]");
			
				click_button("Add Term After/Before", AddTerm);	
			
				enter_text_value("Search TermName", addTermAfterBeforeSearchFeild, oParameters.GetParameters("SearchTermName"+i));
				performKeyBoardAction("ENTER");
			
				By TermPosition = By.xpath("//div[@class='form-group mar-0']//ul/li[contains(.,'"+oParameters.GetParameters("SearchTermName"+i)+"')]");
				waitFor(TermPosition,"Terms List");
				click_button("Select Particular Term", TermPosition);
			}
		
			enter_text_value("Qualification Feild", qualificationGroupFeild, oParameters.GetParameters("QualificationGroup"+i));
			performKeyBoardAction("ENTER");
			fixed_wait_time(2);
		
			By Qualification_Term = By.xpath("//form[@id='addEditTermForm']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("QualificationGroup"+i)+"']");
		
			if(IsDisplayed("Qualification Term", Qualification_Term))
				click_button("Qualification Term", Qualification_Term);
		
			advancedQualifierOption(i);
		}
	}
	
	By AddExclusionTermTab = By.xpath("//div[@id='showExclusionModal']//div[@title='Add Exclusion Term']");
	
	By addStopLossTermWindow = By.xpath("//div[@title='Add Stop Loss Term']");
	
	//Method to enter a Form/Through and Amount Values.
	public void formThroughValue(String i)
	{
		String[] fromValues = oParameters.GetParameters("From"+i).split(",");
		
		String[] throughValues = oParameters.GetParameters("Through"+i).split(",");
			
		String[] amountValues = oParameters.GetParameters("Amount/Percentage"+i).split(",");
		
		for(int j=0;j<fromValues.length;j++)
		{
			By fromValue = null;
			
			if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Term Window", addTermWindow))
				 fromValue = By.xpath("//form[@id='addEditTermForm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li[" + (j+2) +"]//div[@spec='formTierModel.start.spec']//input[@id='']");
			else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Exclusion Term", AddExclusionTermTab))
				fromValue = By.xpath("//form[@id='addEditExclusionTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li[" + (j+2) +"]//div[@spec='formTierModel.start.spec']//input[@id='']");
			else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow))
				fromValue = By.xpath("//form[@id='addStopLossTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li[" + (j+2) +"]//div[@spec='formTierModel.start.spec']//input[@id='']");
			else if(oParameters.GetParameters("RateType"+i).equals("Procedure Group") || oParameters.GetParameters("RateType"+i).equals("Tiered"))
				 fromValue = By.xpath("//li[" + (j+2) + "]//div[@spec='formTierModel.start.spec']//input[@id='']");
			
			
			enter_text_value("Form Values", fromValue, fromValues[j]);
				
			for(int k=j;k<=j;k++) 
			{
				By throughValue = null;
				
				if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Term Window", addTermWindow))
					throughValue = By.xpath("//form[@id='addEditTermForm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//div[@spec='formTierModel.stop.spec']//input[@id='']");
				else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Exclusion Term", AddExclusionTermTab))
					throughValue = By.xpath("//form[@id='addEditExclusionTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//div[@spec='formTierModel.stop.spec']//input[@id='']");
				else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow))
					throughValue = By.xpath("//form[@id='addStopLossTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//div[@spec='formTierModel.stop.spec']//input[@id='']");
				else if(oParameters.GetParameters("RateType"+i).equals("Procedure Group") || oParameters.GetParameters("RateType"+i).equals("Tiered"))
					throughValue = By.xpath("//li[" + (j+2) + "]//div[@spec='formTierModel.stop.spec']//input[@id='']");
				
				try 
				{
					enter_text_value("Through Values", throughValue, throughValues[k]);
				}
				catch(Exception e) 
				{
					System.out.println(e.getMessage());
				}
				
				if(oParameters.GetParameters("RateType"+i).equals("Tiered"))
				{
					String[] rateType = oParameters.GetParameters("From/ThroughRateType"+i).split(",");
					
					for(int l=0;l<=k;l++)
					{
						By TieredRateType = By.xpath("//li["+(l+2)+"]//div[@options='tierRateTypes']//select[@id='002']");
						
						selectOption(TieredRateType,"visibletext",rateType[l]);
						
						if(rateType[l].equals("Percentage") && oParameters.GetParameters("TireBasis"+i).equals("Day") 
								||rateType[l].equals("Percentage") && oParameters.GetParameters("TireBasis"+i).equals("Age in Years") 
									||rateType[l].equals("Percentage") && oParameters.GetParameters("TireBasis"+i).equals("Total Covered Amount")) 
						{
							if(oParameters.GetParameters("TermType"+i).equals("Terms"))
							{
								String[] Percentage = oParameters.GetParameters("if_Percentage"+i).split(",");
							
								for(int b=l;b<=l;b++)
								{
									By percenatge = By.xpath("//form[@id='addEditTermForm']//li["+(b+2)+"]//div[@spec='formTierModel.percentageBasis.spec']//select[@id='']");
							
									selectOption(percenatge,"visibletext",Percentage[b]);
								}
							}
						}
					}
				}
				for(int l=k;l<=k;l++)
				{
					By amountValue = null;
					
					if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Term Window", addTermWindow))
						amountValue = By.xpath("//form[@id='addEditTermForm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//input[@id='002']");
					else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Exclusion Term", AddExclusionTermTab))
						amountValue = By.xpath("//form[@id='addEditExclusionTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//input[@id='002']");
					else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow))
						amountValue = By.xpath("//form[@id='addStopLossTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//input[@id='002']");
					else if(oParameters.GetParameters("RateType"+i).equals("Procedure Group") || oParameters.GetParameters("RateType"+i).equals("Tiered"))
						amountValue = By.xpath("//li[" + (j+2) + "]//input[@id='002']");
					try 
					{
						enter_text_value("Through Values", amountValue, amountValues[l]);
					}
					catch(Exception e) 
					{
						System.out.println(e.getMessage());
		        	}
				}
	        }	
	        		
			if(j < fromValues.length-1)
			{	
				By addIcon = null;
				
				if(IsDisplayed("Add Term Window", addTermWindow) && oParameters.GetParameters("RateType"+i).equals("Additive Tiered") || IsDisplayed("Add Term Window", addTermWindow) && oParameters.GetParameters("RateType"+i).equals("Tiered"))
					addIcon = By.xpath("//form[@id='addEditTermForm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//i[@class='left fa fa-plus-square']");
				else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Exclusion Term", AddExclusionTermTab) || oParameters.GetParameters("RateType"+i).equals("Tiered") && IsDisplayed("Add Exclusion Term", AddExclusionTermTab))
					addIcon = By.xpath("//form[@id='addEditExclusionTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//i[@class='left fa fa-plus-square']");
				else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow) || oParameters.GetParameters("RateType"+i).equals("Tiered") && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow))
					addIcon = By.xpath("//form[@id='addStopLossTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//i[@class='left fa fa-plus-square']");
				else if(IsDisplayed("Add Term Window", addTermWindow) && oParameters.GetParameters("RateType"+i).equals("Procedure Group"))
					addIcon = By.xpath("//fieldset [@id='multipleProcedureRules']//li["+ (j+2) +"]//i[@class='left fa fa-plus-square']");
					
				click_button("Add Icon", addIcon);
			}
		}
	}
	
	
	
	By percentageBasis = By.xpath("//select[@id='maxRatePercentageBasis']");
	
	//Method to enter a Maximum,Minimum and TermNote.
	public void MaxMinRateType(String i,String sheetName,String excelName)
	{
		if(oParameters.GetParameters("TermType"+i).equals("Terms"))
		{
			int columnCount = oExcelData.getColumnCount(sheetName,"C:\\CCM\\"+excelName+"");
		
			String [] columnDataArray = new String[columnCount];
		
			for(int k=0; k<columnCount;k++)
			{	                                   
				String cellData = oExcelData.getCellData(sheetName, k, 1, "C:\\CCM\\"+excelName+"");
				columnDataArray[k]= cellData;
			}
		
			String columnDataString = String.join(",", columnDataArray).replace(",", " ");
		
			if(columnDataString.contains("MaxRateType"))
			{
				selectOption(maximumRateType_Term,"visibletext",oParameters.GetParameters("MaxRateType"+i));
		
				if(!oParameters.GetParameters("MaxRateType"+i).isEmpty())
					enter_text_value("MaxRate Amount/Percentage", maxRateAmount, oParameters.GetParameters("MaxRateAmount/Percentage"+i));
		
				if(oParameters.GetParameters("RateType"+i).equals("Revenue Code Per Day or Per Case") || oParameters.GetParameters("RateType"+i).equals("Per Service") || oParameters.GetParameters("RateType"+i).equals("Per Diem"))
				{
					if(oParameters.GetParameters("MaxRateType"+i).equals("Percentage"))
					{
						if(IsDisplayed("Percentage Basis", percentageBasis))
							selectOption(percentageBasis,"visibletext",oParameters.GetParameters("PercentageBasis"+i));
					}
				}	
			}	
		
			if(columnDataString.contains("MinRateType"))
			{
				selectOption(minimumRateType,"visibletext",oParameters.GetParameters("MinRateType"+i));
	
				if(!oParameters.GetParameters("MinRateType"+i).isEmpty())
					enter_text_value("MinRate Amount/Percentage", minRateAmount, oParameters.GetParameters("MinRateAmount/Percentage"+i));
			}
			
			if(!oParameters.GetParameters("TermNote"+i).isEmpty())
				enter_text_value("Term Note", termNote, oParameters.GetParameters("TermNote"+i));
		}
	}
	
	By rateTypeSearchBox = By.xpath("//form[@id='addEditTermForm']//div[@class='col-lg-8 col-md-8 col-sm-8 ng-isolate-scope']//select[@ng-model='model']");
	
	By rateTypeSearchBox_ExclusionTerm = By.xpath("//form[@id='addEditExclusionTerm']//select[@id='']");
	
	public By tierBasis = By.xpath("//select[@id='tierBasis']");
	
	By maximumRateType_Term = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//select[@id='maxRateType']");
	
	By minimumRateType = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//select[@id='minRateType']");
	
	By percentage = By.xpath("//input[@id='rateAmount']");
	
	By maxRateAmount = By.xpath("//input[@id='maxRateAmount']");
	
	By minRateAmount = By.xpath("//input[@id='minRateAmount']");
	
	By termNote = By.xpath("//textarea[@id='002']");
	
	By tableBasis = By.xpath("//fieldset[@class='bg-light-gray']//div[@class='col-lg-4 col-md-4 col-sm-4 ng-scope']//div[@options='rateBasis']//select[@ng-model='model']");
	
	By rateAmount = By.xpath("//input[@id='rateAmount']");
	
	By QuantityRoundingMethod = By.xpath("//form[@id='addEditTermForm']//div[@model='term.rate.roundingMethodCode']//select[@ng-model='model']");
	
	By DRG_percentage = By.xpath("//input[@id='percentage']");
	
	By DRG_additionalFlatAmount = By.xpath("//input[@id='additionalFlatAmount']");
	
	By dailysisPPSRateFactor = By.xpath("//input[@id='dialysisFactor']");
	
	By DRGUserrateSet = By.xpath("//input[@id='drgUserRateMaster']");
	
	By DRGUserSetPeriod = By.xpath("//select[@id='drgUserRatePeriod']");
	
	By groupCodeOverride = By.xpath("//select[@id='overrideDRGType']");
	
	By calculationMethod = By.xpath("//select[@id='drgUserCalculationMethodCode']");
	
	By formulaName = By.xpath("//input[@id='drgFormulaName']");
	
	By feeScheduleSearchFeild = By.xpath("//input[@id='fsMaster']");
	
	By feeSchedulePeriod = By.xpath("//select[@id='fsPeriod']");
	
	By modifierSchedule = By.xpath("//input[@id='fsModDiscount']");
	
	By useRelatedProcedureDiscountingCheckBox = By.xpath("//label[contains(.,'Use Related Procedure Discounting')]//input");
	
	By useRelatedProcedureDiscountingCheckBox_ExclusionTerm = By.xpath("//label[contains(.,'Use Related Procedure Discounting')]//input");
	
	By relatedProcedureSchedule = By.xpath("//input[@id='rsMaster']");
	
	By cmgUserRateSet = By.xpath("//input[@id='cmgUserRateMaster']");
	
	By cmgUserRateSetPeriod = By.xpath("//select[@id='cmgUserRatePeriod']");
	
	By cmgProviderValuesSet = By.xpath("//input[@id='cmgProviderValuesMaster']");
	
	By cmgProviderValuesSetPeriod = By.xpath("//select[@id='cmgProviderValuesPeriod']");
	
	By procedureToUse = By.xpath("//select[@id='proceduresToUse']");
	
	By icdProcedureVersionOverride = By.xpath("//form[@id='addEditTermForm']//div[@class='form-group row ng-scope']//select[@id='']");
	
	By HCPCS_icdCodeSet = By.xpath("//input[@id='codeSet']");
	
	By HCPCS_IcdCodeSetPeriod = By.xpath("//select[@id='codeSetPeriod']");
	
	By groupCodeRateSet = By.xpath("//input[@id='groupCodeRateMaster']");
	
	By gropuCodeRateSetPeriod = By.xpath("//select[@id='groupCodeRatePeriod']");
	
	By bilateralProcedure = By.xpath("//label[contains(.,'Separate Bilateral Procedures (modifier 50)')]//input[@type='checkbox']");
	
	By discountPriorityBasis = By.xpath("//select[@id='discountPriorityBasis']");
	
	By ungroupedProcedureRate = By.xpath("//select[@id='ungroupedRateType']");
	
	By groupCodeRateAmount = By.xpath("//rate-amount[@model='term.rate.ungroupedRateAmount']//input[@id='rateAmount']");
	
	By includeUngroupedProcedureinMultipleProcedure = By.xpath("//select[@id='includeUngroupedProcedures']");
	
	By revenueCode = By.xpath("//input[@id='revCdAmount']");
	
	By revenueCodeRateBasis = By.xpath("//form[@id='addEditTermForm']//div[@options='rateBasis']//select[@id='']");
	
	By percentageLimit = By.xpath("//input[@id='revCdPctLimit']");
	
	By rugUserRateSet = By.xpath("//input[@id='rugUserRateMaster']");
	
	By rugUserRatePeriod = By.xpath("//select[@id='rugUserRatePeriod']");
	
	By sectionForThisExclusion = By.xpath("//form[@id='addEditExclusionTerm']//span[@toggle-target='selectTermSection']");
	
	By percentageBasisFeild = By.xpath("//select[@id='percentageBasis']");
	
	By rateTypeSearchBox_Stoploss = By.xpath("//form[@id='addStopLossTerm']//div[@class='col-lg-8 col-md-8 col-sm-8 ng-isolate-scope']//select[@ng-model='model']");
	
	By formulaNameSB = By.xpath("//input[@id='formulaName']");
	
	By groupCodeOverrideDD = By.xpath("//select[@id='grouperCodeOverride']");
	
	
	//Method to enter a data in add term window.
	public void selectRateType(String i)
	{
		if(oParameters.GetParameters("TermType"+i).equals("Exclusions"))
			selectOption(rateTypeSearchBox_ExclusionTerm,"visibletext",oParameters.GetParameters("RateType"+i));
		else if(oParameters.GetParameters("TermType"+i).equals("StopLossTerm"))
			selectOption(rateTypeSearchBox_Stoploss,"visibletext",oParameters.GetParameters("RateType"+i));
		else if(oParameters.GetParameters("TermType"+i).equals("Terms"))
			selectOption(rateTypeSearchBox,"visibletext",oParameters.GetParameters("RateType"+i));
		
		if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered"))
		{
			if(IsDisplayed("Add Term", addTermWindow))
			{
				selectOption(tierBasis,"visibletext",oParameters.GetParameters("TireBasis"+i));
			
				if(oParameters.GetParameters("TireBasis"+i).equals("Total Covered Charge Amount") || oParameters.GetParameters("TireBasis"+i).equals("Daily covered charge amount") || oParameters.GetParameters("TireBasis"+i).equals("Total Covered Amount"))
					selectOption(tierBasisRateType,"visibletext",oParameters.GetParameters("TierBasisRateType"+i));
				
				formThroughValue(i);
			}
			else if(IsDisplayed("StopLoss Window",addStopLossTermWindow))
			{	
				selectOption(tierBasisRateType,"visibletext",oParameters.GetParameters("TierBasisRateType"+i));
				formThroughValue(i);
			}
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Formula"))
		{
			if(IsElementDisplayed("Formula Name Search Box", formulaNameSB))
				enter_text_value("Formula Name Search Box", formulaNameSB, oParameters.GetParameters("FormulaNameSB"+i));
			else
				oReport.AddStepResult("Formula Rate Type", "In 'Add Stop Loss Term' window filled mandatory fields then selected 'Formula' as Rate Type but Formula name search box is not displayed ", "FAIL");
			
			if(!oParameters.GetParameters("GroupCodeOverride"+i).isEmpty())
				selectOption(groupCodeOverrideDD,"visibletext",oParameters.GetParameters("TableBasis"+i));		
		}
		else if(oParameters.GetParameters("RateType"+i).equals("APC/APG Pricer"))
			enter_text_value("Percentage",percentage , oParameters.GetParameters("Percentage"+i));
		else if(oParameters.GetParameters("RateType"+i).equals("By Revenue Code"))
		{
			selectOption(tableBasis,"visibletext",oParameters.GetParameters("TableBasis"+i));
			
			String[] revenueCode = oParameters.GetParameters("RevenueCodeExpression"+i).split(",");
			
			String[] Amount_Percentage = oParameters.GetParameters("Amount/Percentage"+i).split(",");
			
			for(int j=0;j<revenueCode.length;j++)
			{
				By ByRevenueCode_revenueCodeExpresstion = By.xpath("//input[@id='revCodeRateEntry"+j+"']");
				
				enter_text_value("Revenue Code Expresstion", ByRevenueCode_revenueCodeExpresstion, revenueCode[j]);
				
				By ByRevenueCode_Amount = By.xpath("//input[@id='rateEntries"+j+"']");
				
				enter_text_value("Amount", ByRevenueCode_Amount, Amount_Percentage[j]);
				
				if(j < Amount_Percentage.length-1)
				{
					if(oParameters.GetParameters("TermType"+i).equals("Exclusions"))
					{
						By addIcon = By.xpath("//form[@id='addEditExclusionTerm']//ul[@class='data-list overflow-visible']//li["+(j+2)+"]//i[@class='left fa fa-plus-square']");
						click_button("Add Icon", addIcon);
					}
					else
					{
						By addIcon = By.xpath("//form[@id='addEditTermForm']//ul[@class='data-list overflow-visible']//li["+(j+2)+"]//i[@class='left fa fa-plus-square']");
						click_button("Add Icon", addIcon);
					}
					
				}
			}
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Dosage Quantity"))
		{
			enter_text_value("Rate Amount", rateAmount, oParameters.GetParameters("RateAmount"+i));
			
			selectOption(QuantityRoundingMethod,"visibletext",oParameters.GetParameters("QuantityRoundingMethod"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("DRG Pricer"))
		{
			enter_text_value("DRG Pricer Percentage", DRG_percentage, oParameters.GetParameters("Percentage"+i));
			
			enter_text_value("Additional Flat Amount", DRG_additionalFlatAmount, oParameters.GetParameters("AdditionalFlatAmount"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Dialysis PPS Rate"))
		{
			enter_text_value("Dailysis PPS Rate", percentage, oParameters.GetParameters("Percentage"+i));
			
			enter_text_value("Dailysis PPS Rate Factor", dailysisPPSRateFactor, oParameters.GetParameters("DialysisPPSRateFactors"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("DRG User"))
		{
			enter_text_value("DRG User Rate Set", DRGUserrateSet, oParameters.GetParameters("DRGUserRateSet"+i));
			
			if(IsElementDisplayed("DRG User Set Period", DRGUserSetPeriod))
				selectOption(DRGUserSetPeriod,"visibletext",oParameters.GetParameters("DRGUserRateSetPeriod"+i));
			
			if(IsElementDisplayed("Group Code Override", groupCodeOverride))
				selectOption(groupCodeOverride,"visibletext",oParameters.GetParameters("GroupCodeOverride"+i));
			
			if(IsElementDisplayed("Calculation Method", calculationMethod))
				selectOption(calculationMethod,"visibletext",oParameters.GetParameters("CalculationMethod"+i));
			
			if(oParameters.GetParameters("CalculationMethod"+i).equals("DRG Formula Method"))
			{	
				enter_text_value("Formula Name", formulaName, oParameters.GetParameters("FormulaName"+i));
				
				By FormulaList = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("FormulaName"+i)+"']");
			
				if(IsDisplayed("Formula List", FormulaList))
					click_button("Formula List Name", FormulaList);
			}
			
			enter_text_value("DRG User Percentage", DRG_percentage, oParameters.GetParameters("Percentage"+i));
			
			enter_text_value("DRG Additional flat Amount", DRG_additionalFlatAmount, oParameters.GetParameters("AdditionalFlatAmount"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Fee Schedule"))
		{
			enter_text_value("Fee Schedule Search Feild", feeScheduleSearchFeild, oParameters.GetParameters("FeeSchedule"+i));
			performKeyBoardAction("ENTER");
			fixed_wait_time(2);
			
			By FeeSchedule = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("FeeSchedule"+i)+"']");

			if(IsDisplayed("Fee Schedule", FeeSchedule))
				click_button("Fee Schedule", FeeSchedule);
			
			enter_text_value("Percent of Fee Schedule", rateAmount, oParameters.GetParameters("PercentOfFeeSchedule"+i));
		
			if(!oParameters.GetParameters("FeeSchedulePeriod"+i).isEmpty())
				selectOption(feeSchedulePeriod,"value",oParameters.GetParameters("FeeSchedulePeriod"+i));
			
			if(!oParameters.GetParameters("ModifierSchedule"+i).isEmpty())
			{
				enter_text_value("Modifier Schedule", modifierSchedule, oParameters.GetParameters("ModifierSchedule"+i));
			
				By modifierSchedule = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("ModifierSchedule"+i)+"']");
				
				if(IsDisplayed("Modifier Schedule", modifierSchedule))
					click_button("Modifier Schedule", modifierSchedule);
			}
			
			if(!oParameters.GetParameters("UseRelatedProcedureSchedule"+i).isEmpty())
			{
				if(oParameters.GetParameters("TermType"+i).equals("Exclusions") && oParameters.GetParameters("UseRelatedProcedureSchedule"+i).equals("YES"))
					click_button("Use Related Procedure Discounting Checkbox Exclusion Term", useRelatedProcedureDiscountingCheckBox_ExclusionTerm);
				else if(oParameters.GetParameters("TermType"+i).equals("Terms") && oParameters.GetParameters("UseRelatedProcedureSchedule"+i).equals("YES"))
					click_button("Use Related Procedure Discounting Checkbox", useRelatedProcedureDiscountingCheckBox);
			}
			
			if(!oParameters.GetParameters("SearchRelatedProcedureSchedule"+i).isEmpty())
				enter_text_value("Related Procedure Schedule", relatedProcedureSchedule, oParameters.GetParameters("SearchRelatedProcedureSchedule"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("IRF CMG Pricer"))
		{
			enter_text_value("CMG User Rate Set", cmgUserRateSet, oParameters.GetParameters("CMGUserRateSet"+i));
			
			selectOption(cmgUserRateSetPeriod,"value",oParameters.GetParameters("CMGUserRateSetPeriod"+i));
			
			enter_text_value("CMG Provider Values Set", cmgProviderValuesSet, oParameters.GetParameters("CMGProviderValuesSet"+i));
			
			selectOption(cmgProviderValuesSetPeriod,"value",oParameters.GetParameters("CMGProviderValuesSetPeriod"+i));
			
			enter_text_value("Percentage", DRG_percentage, oParameters.GetParameters("Percentage"+i));
			
			enter_text_value("Additional Flat Amount", DRG_additionalFlatAmount, oParameters.GetParameters("AdditionalFlatAmount"+i));
			
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Per Case") 
					|| oParameters.GetParameters("RateType"+i).equals("Per Diem") 
						|| oParameters.GetParameters("RateType"+i).equals("Per Hour") 
							|| oParameters.GetParameters("RateType"+i).equals("Per Length of Stay")
								|| oParameters.GetParameters("RateType"+i).equals("Per Minute")
									|| oParameters.GetParameters("RateType"+i).equals("Per Service")
										|| oParameters.GetParameters("RateType"+i).equals("PPS Professional Rate"))
			enter_text_value("Rate Amount", rateAmount, oParameters.GetParameters("RateAmount/Percentage"+i));
		
		else if(oParameters.GetParameters("RateType"+i).equals("Percentage"))
		{
			enter_text_value("Rate Amount", rateAmount, oParameters.GetParameters("RateAmount/Percentage"+i));
			
			if(oParameters.GetParameters("TermType"+i).equals("Exclusions") || oParameters.GetParameters("TermType"+i).equals("StopLossTerm"))
				selectOption(percentageBasisFeild,"visibletext",oParameters.GetParameters("PercentageBasis"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Procedure Group"))
		{
			enter_text_value("Percentage", rateAmount, oParameters.GetParameters("Percentage"+i));
			
			selectOption(procedureToUse,"visibletext",oParameters.GetParameters("ProcedureToUser"+i));
			
			if(oParameters.GetParameters("ProcedureToUser"+i).equals("ICD Claim Level Only"))
			{	
				selectOption(icdProcedureVersionOverride,"visibletext",oParameters.GetParameters("ICDProceduresVersionOverride"+i));
				
				enter_text_value("ICD Code Set", HCPCS_icdCodeSet, oParameters.GetParameters("ICDCodeSet"+i));
				performKeyBoardAction("ENTER");
				
				By ICdCode = By.xpath("//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("ICDCodeSet"+i)+"']");
				 
				if(IsDisplayed("ICD Code Set", ICdCode))
					click_button("ICD Code Set", ICdCode);
				
				selectOption(HCPCS_IcdCodeSetPeriod,"value",oParameters.GetParameters("ICDCodeSetPeriod"+i));
			}
			else
			{
				enter_text_value("HCPCS/CPT Code Set", HCPCS_icdCodeSet, oParameters.GetParameters("HCPCS/CPTCodeSet"+i));
				performKeyBoardAction("ENTER");
				fixed_wait_time(1);
				
				By HPCS_CPT = By.xpath("//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("HCPCS/CPTCodeSet"+i)+"']");
				
				if(IsDisplayed("HCPCS/CPT Code Set", HPCS_CPT))
					click_button("HCPCS/CPT", HPCS_CPT);
				
				selectOption(HCPCS_IcdCodeSetPeriod,"value",oParameters.GetParameters("HCPCS/CPTCodeSetPeriod"+i));
			}
			
			enter_text_value("Group Code Rate Set", groupCodeRateSet, oParameters.GetParameters("GroupCodeRateSet"+i));
			fixed_wait_time(3);
			
			if(!oParameters.GetParameters("GroupCodeRateSet"+i).isEmpty())
				selectOption(gropuCodeRateSetPeriod,"value",oParameters.GetParameters("GroupCodeRateSetPeriod"+i));
			
			enter_text_value("Modifier Schedule", modifierSchedule, oParameters.GetParameters("ModifierSchedule"+i));
			
			if(oParameters.GetParameters("SeperateBilateralProcedures"+i).equals("YES"))
				click_button("Bilateral Procedure CheckBox", bilateralProcedure);
			
			if(!oParameters.GetParameters("MultipleProcedureRules"+i).isEmpty())
				selectOption(discountPriorityBasis,"visibletext",oParameters.GetParameters("MultipleProcedureRules"+i));
			
			if(!oParameters.GetParameters("From"+i).isEmpty())
				formThroughValue(i);
			
			if(!oParameters.GetParameters("UngroupedProcedureRate"+i).isEmpty())
			{
				selectOption(ungroupedProcedureRate,"visibletext",oParameters.GetParameters("UngroupedProcedureRate"+i));
				enter_text_value("Group Code Rate Amount", groupCodeRateAmount, oParameters.GetParameters("RateAmount/Percentage"+i));
			}
			
			if(!oParameters.GetParameters("IncludeUngroupedProceduresInMultipleProcedureRules"+i).isEmpty())
				selectOption(includeUngroupedProcedureinMultipleProcedure,"visibletext",oParameters.GetParameters("IncludeUngroupedProceduresInMultipleProcedureRules"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Revenue Code Per Day or Per Case"))
		{
			enter_text_value("Revenue Code Amount Feild", revenueCode, oParameters.GetParameters("Amount"+i));
			
			selectOption(revenueCodeRateBasis,"visibletext",oParameters.GetParameters("Basis"+i));
			
			enter_text_value("Percentage limit", percentageLimit, oParameters.GetParameters("PercentageLimit"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("RUG User"))
		{
			enter_text_value("RUG User Rate Set ", rugUserRateSet, oParameters.GetParameters("RUG_UserRateSet"+i));
		
			selectOption(rugUserRatePeriod,"value",oParameters.GetParameters("RUG_UserRateSetPeriod"+i));
			
			enter_text_value("RUG User Percentage", DRG_percentage, oParameters.GetParameters("Percentage"+i));
			
			enter_text_value("RUG Additional Flat Amount", DRG_additionalFlatAmount, oParameters.GetParameters("AdditionalFlatAmount"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Tiered"))
		{
			selectOption(tierBasis,"visibletext",oParameters.GetParameters("TireBasis"+i));
			
			formThroughValue(i);
		}
		
		if(oParameters.GetParameters("SectionForThisExclusion"+i).equals("yes"))
		{
			click_button("Show Section For This Exclusion", sectionForThisExclusion);
		
			By Section = By.xpath("//form[@id='addEditExclusionTerm']//label[contains(.,'"+oParameters.GetParameters("SectionForThisExclusionValue"+i)+"')]//input");
		
			click_button("Particular Exclusion Section", Section);
		}	
	}
	
	
	By addTermSaveButton = By.xpath("//div[@id='showTermAddModal']//input[@id='button.saveId']");
	
	By addTermCancelButton = By.xpath("//div[@id='showTermAddModal']//input[@id='button.cancelId']");
	
	By termsListCount = By.xpath("//ul[@class='ratesheet-terms terms-hide-overflow']//li//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding']");
	
	By ExclusionSaveButton = By.xpath("//div[@id='showExclusionModal']//input[@id='button.saveId']");
	
	By TermEmptyQG = By.xpath("//div[@class='clearBoth']//div[text()='You must select an option from the search results.']");
	
	By EmptyRevenueCodeExpresstion = By.xpath("//div[@class='col-lg-6 col-md-6 col-sm-6 col-xs-6']//div[text()='You must select an option from the search results.']");
	
	By stopLossSaveButton = By.xpath("//div[@id='addSectionModal']//input[@value='Save']");
	
	//This Method is used to click on 'Term Save Button' and verifing wheather term is added or not.
	public void termSaveButton(String i)
	{
		int j;
		
		if(IsDisplayed("Add Term Save Button", addTermSaveButton))
			click_button("Term Save Button", addTermSaveButton);
		else if(IsDisplayed("Exclusion Save Button", ExclusionSaveButton))
			click_button("Exclusion Term Save Button", ExclusionSaveButton);
		else
			click_button("StopLoss Save Button", stopLossSaveButton);
		
		
		if(IsDisplayed("Term QG", TermEmptyQG))
		{
			oReport.AddStepResult("Term Tab", "While creating '"+oParameters.GetParameters("TermName"+i)+"' this term, passing '"+oParameters.GetParameters("QualificationGroup"+i)+"' as a QG, but this QG is not selecting", "WARNING");
			
			click_button("Add Term Cancel Button", addTermCancelButton);
			
			fixed_wait_time(2);
			
			click_button("Add Term Discard Button", sectionDiscardButton);
		}
		else if(IsDisplayed("Empty Revenue Code Expresstion", EmptyRevenueCodeExpresstion))
		{
			oReport.AddStepResult("Term Tab", "While creating '"+oParameters.GetParameters("TermName"+i)+"' this term, passing '"+oParameters.GetParameters("RevenueCodeExpression"+i)+"' as a Revenue Code Expression, but this Revenue Code Expression is not selecting", "WARNING");
			
			click_button("Add Term Cancel Button", addTermCancelButton);
			
			fixed_wait_time(2);
			
			click_button("Add Term Discard Button", sectionDiscardButton);
		}
		else
		{
			List<WebElement> termList= convertToWebElements(termsListCount);
		
			for(j=0;j<termList.size();j++)
			{
				if(termList.get(j).getText().contains(oParameters.GetParameters("TermName"+i)))
				{	
					oReport.AddStepResult("Term Name", "Filled all mandatory filled in 'Add term' window and clicked on save button and verified that ' "+oParameters.GetParameters("TermName"+i)+" ' is added susccefully", "PASS");
					break;
				}	
			}
			if(j>termList.size())
				oReport.AddStepResult("Term Name", "Filled all mandatory filled in 'Add term' window and clicked on save button and verified that ' "+oParameters.GetParameters("TermName"+i)+" ' is not added susccefully", "FAIL");
		
			fixed_wait_time(2);
		}
	}
	
	
	By ExclusionTab = By.xpath("//div[@id='ratesheetView']//a[text()='Exclusions']");
    
    By addExclusionTermButton = By.xpath("//li[@id='sec-term-list']//a[text()='Add Exclusion Term']");
    
    By ExclusionTerm = By.xpath("//div[@id='exSlidePanelParent']//a[contains(text(),'Add an Exclusion Term.')]");
    
    By ExclusionQualificationFeild = By.xpath("//input[@id='qualificationGroup']");
    
    By ExclusionRateType = By.xpath("//form[@id='addEditExclusionTerm']//div[@form-id='rateTypeFormModel.formId']//select[@id='']");
	
    
    public void ExclusionTab(String i)
    {
    	click_button("Exclusion Tab", ExclusionTab);
		
    	if(IsDisplayed("Exclusion Term", ExclusionTerm) || IsDisplayed("Exclusion Term Button", addExclusionTermButton))
		{
			oReport.AddStepResult("Exclusion Term", "Clicked on Exclusion Tab and verified that exclusion Term icon is displayed", "PASS");
			
			if(IsDisplayed("Exclusion Term Icon", ExclusionTerm))
				click_button("Exclusion Term", ExclusionTerm);
			else
				click_button("Exclusion Term Button", addExclusionTermButton);
			
			if(IsElementDisplayed("Add Exclusion Term Tab", AddExclusionTermTab))
			{
				oReport.AddStepResult("Add Exclusion Term ", "Clicked on 'Add an Exclusion Term icon' and verified that 'Add Exclusion Term' Tab is displayed", "PASS");
				
				enter_text_value("Term Name", termName, oParameters.GetParameters("TermName"+i));
				
				enter_text_value("Qualification Group", ExclusionQualificationFeild, oParameters.GetParameters("QualificationGroup"+i));
				performKeyBoardAction("ENTER");
				fixed_wait_time(2);
				
				By qualification = By.xpath("//div[@model='qualificationGroupModelTerm']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("QualificationGroup"+i)+"']");
				
				if(IsDisplayed("Exclusion Qualification", qualification))
					click_button("Exclusion Qualification", qualification);
				
				//advancedQualifierOption(i);
			}
			else
				oReport.AddStepResult("Add Exclusion Term ", "Clicked on 'Add an Exclusion Term icon' and verified that 'Add Exclusion Term' Tab is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Exclusion Term", "Clicked on Exclusion Tab and verified that exclusion Term icon is not displayed", "FAIL");
    }
    
    
   
	
	
	//RateSheet Build
	public void createratesheetdetails()
	{
		login("EDIT");
		changePricingEngine();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "RateSheet", "");
				int rowCount = oExcelData.getRowCount1("RateSheet", "C:\\CCM\\SupportingFiles\\TestData.xlsx");

		oRateSheetLibrary.navigateToRateSheets();
		
		for(int i=1;i<=rowCount-1;i++)
		{
			addRateSheetIcon();
			AddRateSheetDetails(String.valueOf(i));
			rateSheetSaveButton(String.valueOf(i));
		}
	}
	
	//Section Build
	public void createSectiondetails()
	{
		login("EDIT");
		changePricingEngine();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "RateSheetSection", "");
		int rowCount = oExcelData.getRowCount1("RateSheetSection", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
		
		oRateSheetLibrary.navigateToRateSheets();
		
		for(int i=1;i<=rowCount-1;i++)
		{
			AddSectionDetails(String.valueOf(i),oParameters.GetParameters("RateSheetName"+i));
		}
	}
	
	//Term Build
	public void createRateTerm()
	{
		login("EDIT");
		changePricingEngine();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "Term_Tired", "");
		int rowCount = oExcelData.getRowCount1("Term_Tired", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
		
		oRateSheetLibrary.navigateToRateSheets();
		
		for(int i=46;i<=rowCount-1;i++)
		{
			addTermButton(String.valueOf(i),oParameters.GetParameters("RateSheetName"+i),oParameters.GetParameters("SectionName"+i));
			selectRateType(String.valueOf(i));
			MaxMinRateType(String.valueOf(i),"Term_Tired","TestData.xlsx");
			termSaveButton(String.valueOf(i));
		}
	}
	
	public By RateSheetCode = By.xpath("//form[@id='testPriceUB04']//input[@id='payerContracts']");
	
	//This Method is used for navigating to Test Price plugin. 
	public void navigateToTestPrice()
	{
		navigate_to_plugin("TESTPRICE");
		//waitFor(RateSheetCode,"Rate Sheet Code Feild");
		fixed_wait_time(2);
	}
	
	
	public By TestPricerateSheetCode = By.xpath("//div[@id='testpriceView']//input[@id='payerContracts']");
	
	public By selectPeriod = By.xpath("//select[@id='selectedPeriod']");
	
	By ICDVersion = By.xpath("//select[@id='ICDVersionUB04']");
	
	By billType = By.xpath("//input[@id='typeOfBill']");
	
	public By healthPlanAlias = By.xpath("//input[@id='hpa']");
	
	By providerGroup = By.xpath("//div[@id='testpriceView']//input[@id='serviceProviderName']");
	
	By fedralTaxId = By.xpath("//input[@id='taxId']");
	
	By statementFromDate = By.xpath("//input[@id='fromDate']");
	
	By statementToDate = By.xpath("//input[@id='toDate']");
	
	public void claimProcessingDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Rate Sheet Code", TestPricerateSheetCode, oParameters.GetParameters("RateSheetCode"+i));
		performKeyBoardAction("ENTER");
		
		By rateSheetCode = By.xpath("//ul[@id='payerContracts-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("RateSheetCode"+i)+"']");
		
		if(IsDisplayed("Rate Sheet Code", rateSheetCode))
			click_button("RateSheet Code", rateSheetCode);
		
		selectOption(selectPeriod,"value",oParameters.GetParameters("Period"+i));
		
		selectOption(ICDVersion,"visibletext",oParameters.GetParameters("ICDVersion"+i));
		
		enter_text_value("Bill Type", billType, oParameters.GetParameters("BillType"+i));
		
		if(!oParameters.GetParameters("HPA"+i).isEmpty())
			enter_text_value("Health Plan Alias", healthPlanAlias, oParameters.GetParameters("HPA"+i));
		
		if(!oParameters.GetParameters("providerGroup"+i).isEmpty())
		{
			enter_text_value("Provider Group", providerGroup, oParameters.GetParameters("providerGroup"+i));
			performKeyBoardAction("ENTER");
		
			By firstProviderGropuing = By.xpath("//ul[@id='npiContainer']//li//span[contains(.,'"+oParameters.GetParameters("providerGroup"+i)+"')]");
			click_button("First Provider Grouping", firstProviderGropuing);
		}
		
		enter_text_value("Ferdral Tax ID", fedralTaxId, oParameters.GetParameters("TaxID"+i));
		
		fixed_wait_time(1);
		enter_text_value("Statement From Date", statementFromDate, oParameters.GetParameters("StatementFrom"+i));
		performKeyBoardAction("ENTER");
		
		fixed_wait_time(1);
		enter_text_value("Statement To Date", statementToDate, oParameters.GetParameters("StatementTo"+i));
		performKeyBoardAction("ENTER");
		
		oReport.AddStepResult("Claim Processing Details", "Filled all claim Processing details infromation", "SCREENSHOT");
		
	}
	
	
	By birthdate = By.xpath("//input[@id='patientDob']");
	
	By gender = By.xpath("//select[@id='gender']");
	
	public void patientDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("BirthDate"+i).isEmpty())
		{	
			enter_text_value("Birth Date", birthdate, oParameters.GetParameters("BirthDate"+i));
			performKeyBoardAction("ENTER");
		}
		
		if(!oParameters.GetParameters("Gender"+i).isEmpty())
			selectOption(gender,"visibletext",oParameters.GetParameters("Gender"+i));
	}
	
	
	By admissionDate = By.xpath("//input[@id='admissionDate']");
	
	By admissionType = By.xpath("//input[@id='admissionType']");
	
	By admissionSRC = By.xpath("//input[@id='admissionSource']");
	
	By dischargeStatus = By.xpath("//input[@id='dischargeStatus']");
	
	public void admissionDischargeDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("AdmissionDate"+i).isEmpty())
		{
			enter_text_value("Admission Date", admissionDate, oParameters.GetParameters("AdmissionDate"+i));
			performKeyBoardAction("ENTER");
		}
		if(!oParameters.GetParameters("AdmissionType"+i).isEmpty())
			enter_text_value("Admission Type ", admissionType, oParameters.GetParameters("AdmissionType"+i));
		
		if(!oParameters.GetParameters("AdmissionSRC"+i).isEmpty())
			enter_text_value("Admission Source", admissionSRC, oParameters.GetParameters("AdmissionSRC"+i));
		
		if(!oParameters.GetParameters("DischargeStatus"+i).isEmpty())
			enter_text_value("Discharge Status", dischargeStatus, oParameters.GetParameters("DischargeStatus"+i));
	}
	
	
	public void conditionCodesDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("ConditionCode"+i).isEmpty())
		{	
			String[] conditionCode = oParameters.GetParameters("ConditionCode"+i).split(",");
		
			for(int j=0;j<conditionCode.length;j++)
			{
				By ConditionCode = By.xpath("//input[@id='conditionCodes"+j+"']");
				enter_text_value("Conditions Code", ConditionCode, conditionCode[j]);
			
				if(j < conditionCode.length-1)
				{
					By addIcon = By.xpath("//div[@id='conditionCodesSec']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
					click_button("Add ICon", addIcon);
				}
			}
		}	
	}
	
	public void occurrenceDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("OccurenceCode"+i).isEmpty() && !oParameters.GetParameters("OccurenceDate"+i).isEmpty())
		{	
			String[] occurrenceCode = oParameters.GetParameters("OccurenceCode"+i).split(",");
		
			String[] occurrenceDate = oParameters.GetParameters("OccurenceDate"+i).split(",");
		
			for(int j=0;j<occurrenceCode.length;j++)
			{
				By OccurrenceCode = By.xpath("//input[@id='occCode"+j+"']");
				enter_text_value("Occurrence Code ", OccurrenceCode, occurrenceCode[j]);
			
				By OccurenceDate = By.xpath("//input[@id='occDate"+j+"']");
			
				if(!occurrenceDate[j].isEmpty())
				{
					enter_text_value("Occurrence Date ", OccurenceDate, occurrenceDate[j]);
					performKeyBoardAction("ENTER");
				}
				if(j < occurrenceCode.length-1)
				{
					By addIcon = By.xpath("//div[@id='occurrenceCodes']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
					click_button("Add Icon", addIcon);
				}
			}
		}	
	}
	
	
	public void OccurrenSpan(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("OccSpanCode"+i).isEmpty() && !oParameters.GetParameters("OccSpanFromValue"+i).isEmpty() && !oParameters.GetParameters("OccSpanThroughValue"+i).isEmpty())
		{
			String[] occurrenceSpanCode = oParameters.GetParameters("OccSpanCode"+i).split(",");
		
			String[] occurrenceFromValue = oParameters.GetParameters("OccSpanFromValue"+i).split(",");
		
			String[] occurrenceThroughValue = oParameters.GetParameters("OccSpanThroughValue"+i).split(",");
		
			for(int j=0 ;j<occurrenceSpanCode.length;j++)
			{
				By OccurrenceSpanCode = By.xpath("//input[@id='occCode1"+j+"']");
				enter_text_value("Occurrence Code", OccurrenceSpanCode, occurrenceSpanCode[j]);
			
				if(!occurrenceFromValue[j].isEmpty() && !occurrenceThroughValue[j].isEmpty())
				{
					By OccurrenceFrom = By.xpath("//input[@id='occFromDate"+j+"']");
					enter_text_value("Occurrence From Value", OccurrenceFrom, occurrenceFromValue[j]);
					performKeyBoardAction("ENTER");
			
					By OccurrenceThrough = By.xpath("//input[@id='occThroughDate"+j+"']");
					enter_text_value("Occurrence Through Value", OccurrenceThrough, occurrenceThroughValue[j]);
					performKeyBoardAction("ENTER");
				}
				if(j < occurrenceSpanCode.length-1)
				{
					By addIcon = By.xpath("//div[@id='occurrenceSpanSec']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
					click_button("Add Icon", addIcon);
				}	
			}
		}	
	}
	
	public void valueCodeDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("ValueCode"+i).isEmpty() && !oParameters.GetParameters("ValueAmount"+i).isEmpty())
		{
			String[] valueCode = oParameters.GetParameters("ValueCode"+i).split(",");
		
			String[] valueAmount = oParameters.GetParameters("ValueAmount"+i).split(",");
		
			for(int j=0;j<valueAmount.length;j++)
			{
				By ValueCode = By.xpath("//input[@id='valueCode"+j+"']");
				enter_text_value("Value Code", ValueCode, valueCode[j]);
			
				By AmountValue = By.xpath("//input[@id='amount"+j+"']");
				enter_text_value("Amount Value", AmountValue, valueAmount[j]);
			
				if(j < valueCode.length-1)
				{
					By addIcon = By.xpath("//div[@id='valueCodesSec']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
					click_button("Add Icon", addIcon);
				}
			}
		}	
	}
	
	public void lineItems(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] revCode = oParameters.GetParameters("RevCode"+i).split(",");
		
		String[] description = oParameters.GetParameters("Description"+i).split(",");
		
		String[] hcpcs = oParameters.GetParameters("HCPCS"+i).split(",");
		
		String[] modifiers1 = oParameters.GetParameters("Modifiers1"+i).split(",");
		
		String[] modifiers2 = oParameters.GetParameters("Modifiers2"+i).split(",");
		
		String[] modifiers3 = oParameters.GetParameters("Modifiers3"+i).split(",");
		
		String[] modifiers4 = oParameters.GetParameters("Modifiers4"+i).split(",");
		
		String[] serivceUnit = oParameters.GetParameters("ServiceUnits"+i).split(",");
		
		String[] charge = oParameters.GetParameters("Charges"+i).split(",");
		
		String[] nonCoveredCharge = oParameters.GetParameters("NonCoveredCharges"+i).split(",");
		
		
		for(int j=0;j<revCode.length;j++)
		{
			By RevCode = By.xpath("//input[@id='revCode"+j+"']");
			enter_text_value("RevCode", RevCode, revCode[j]);
			
			for(int k=j;k<=j;k++)
			{
				By Description = By.xpath("//input[@id='description"+k+"']");
				
				try
				{
					if(!description[k].isEmpty())
						enter_text_value("Description", Description, description[k]);
				}
				catch(Exception e) 
				{
					System.out.println(e.getMessage());
				}
				
				for(int l=k;l<=k;l++)
				{
					By HCPCS = By.xpath("//input[@id='hcpcs"+l+"']");
					
					try
					{
						if(!hcpcs[l].isEmpty())
							enter_text_value("HCPCS", HCPCS, hcpcs[l]);
					}
					catch(Exception e) 
					{
						System.out.println(e.getMessage());
					}
					
					for(int m=l;m<=l;m++)
					{
						By Modifier1 = By.xpath("//input[@id='modifiers0"+m+"']");
						
						try
						{
							if(!modifiers1[m].isEmpty())
								enter_text_value("Modifiers1", Modifier1, modifiers1[m]);
						}
						catch(Exception e)
						{
							System.out.println(e.getMessage());
						}
						
						for(int n=m;n<=m;n++)
						{
							By Modifier2 = By.xpath("//input[@id='modifiers1"+n+"']");
							
							try
							{
								if(!modifiers2[n].isEmpty())
									enter_text_value("Modifiers2", Modifier2, modifiers2[n]);
							}
							catch(Exception e)
							{
								System.out.println(e.getMessage());
							}
							
							for(int p=n;p<=n;p++)
							{
								By Modifier3 = By.xpath("//input[@id='modifiers2"+p+"']");
								
								try
								{
									if(!modifiers3[p].isEmpty())
										enter_text_value("Modifiers3", Modifier3, modifiers3[p]);
								}
								catch(Exception e)
								{
									System.out.println(e.getMessage());
								}
								
								for(int q=p;q<=p;q++)
								{
									By Modifier4 = By.xpath("//input[@id='modifiers3"+q+"']");
								
									try
									{
										if(!modifiers4[q].isEmpty())
											enter_text_value("Modifiers4", Modifier4, modifiers4[q]);
									}
									catch(Exception e)
									{
										System.out.println(e.getMessage());
									}
									
									String	servicedate [] = oParameters.GetParameters("ServiceDate"+i).split(",");
									
									for(int r=q;r<=q;r++)
									{
										By ServiceDate = By.xpath("//input[@id='serviceDate"+r+"']");
											
										try
										{
											if(!servicedate[r].isEmpty())
											{	
												enter_text_value("Service Date", ServiceDate, servicedate[r]);
												performKeyBoardAction("ENTER");
											}	
										}
										catch(Exception e)
										{
											System.out.println(e.getMessage());
										}
									}
									for(int s=q;s<=q;s++)
									{
										By ServiceUnit = By.xpath("//input[@id='units"+s+"']");
										
										try
										{
											if(!serivceUnit[s].isEmpty())
												enter_text_value("Service Unit", ServiceUnit, serivceUnit[s]);
										}
										catch(Exception e)
										{
											System.out.println(e.getMessage());
										}
											
										for(int t=s;t<=s;t++)
										{
											By Charges = By.xpath("//input[@id='charges"+t+"']");
												
											try
											{
												if(!charge[t].isEmpty())
													enter_text_value("Charges", Charges, charge[t]);
												
												oReport.AddStepResult("Line Items", "Entered LineItems Details", "SCREENSHOT");
											}
											catch(Exception e)
											{
												System.out.println(e.getMessage());
											}
												
											for(int u=t;u<=t;u++)
											{
												By NonCoveredCharges = By.xpath("//input[@id='nonCoveredCharges"+u+"']");
													
												try
												{
													if(!nonCoveredCharge[u].isEmpty())
														enter_text_value("Non Covered Charges", NonCoveredCharges, nonCoveredCharge[u]);
												}
												catch(Exception e)
												{
													System.out.println(e.getMessage());
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		
			if(j < revCode.length-1)
			{
				By addIcon = By.xpath("//div[@id='testpriceView']//div[@id='lineItemsSec']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
				click_button("Add Icon", addIcon);
			}
		}
		
		oReport.AddStepResult("Line Items", "Enetered All Line Items ", "SCREENSHOT");
	}
	
	
	By principleDX = By.xpath("//input[@id='reqDiagnosis']");
	
	By admitDX = By.xpath("//input[@id='admittingDiagnosis']");
	
	By DRGCode = By.xpath("//input[@id='submittedDrgCode']");
	
	
	public void diagnosisDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] diagnosiscode = oParameters.GetParameters("DiagnosisCode"+i).split(",");
		
		String[] Poa = oParameters.GetParameters("POA"+i).split(",");
		
		String[] patientReasonDX = oParameters.GetParameters("PatientReasonDX"+i).split(",");
		
		
		if(!oParameters.GetParameters("PrincipleDX"+i).isEmpty())
			enter_text_value("Principle DX", principleDX, oParameters.GetParameters("PrincipleDX"+i));
		
		if(!oParameters.GetParameters("AdmitDX"+i).isEmpty())
			enter_text_value("Admit DX", admitDX, oParameters.GetParameters("AdmitDX"+i));
		
		if(!oParameters.GetParameters("DRGCode"+i).isEmpty())
			enter_text_value("DRG Code", DRGCode, oParameters.GetParameters("DRGCode"+i));
		
		if(!oParameters.GetParameters("PatientReasonDX"+i).isEmpty())
		{	
			for(int j=0;j<patientReasonDX.length;j++)
			{
				By PatientReasonDX = By.xpath("//input[@id='reasonForVisit"+(j+1)+"']");
			
				if(!patientReasonDX[j].isEmpty())
					enter_text_value("Patient Reason DX", PatientReasonDX,patientReasonDX[j]);
			}
		}	
		
		for(int j=0;j<diagnosiscode.length;j++)
		{
			By DiagnosisCode = By.xpath("//input[@id='nonreqDiagnosis"+j+"']");
			
			if(!diagnosiscode[j].isEmpty())
			{	
				enter_text_value("Daignosis Code", DiagnosisCode, diagnosiscode[j]);
			
				By POA = By.xpath("//select[@id='nonReqPOA"+j+"']");
				
				if(!Poa[j].isEmpty())
					selectOption(POA,"visibletext",Poa[j]);
			
				if(j < diagnosiscode.length-1)
				{
					By addIcon = By.xpath("//fieldset[@id='diagnosisSec']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
					click_button("Add Icon", addIcon);
				}
			}	
		}
		
	}
	
	public void procedureCodeDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("ProcedureCode"+i).isEmpty())
		{
			String[] procedureCode = oParameters.GetParameters("ProcedureCode"+i).split(",");
		
			String[] procedureDate = oParameters.GetParameters("ProcedureDate"+i).split(",");
		
			for(int j=0 ;j<procedureCode.length;j++)
			{
				By ProcedureCode = By.xpath("//input[@id='procedureCode"+j+"']");
				enter_text_value("Procedure Code", ProcedureCode, procedureCode[j]);

				if(!procedureCode[j].isEmpty())
				{
					By ProcedureDate = By.xpath("//input[@id='procedureDate"+j+"']");
					enter_text_value("Procedure Date", ProcedureDate, procedureDate[j]);
					performKeyBoardAction("ENTER");
				}
				if(j < procedureCode.length-1)
				{
					By addIcon = By.xpath("//fieldset[@id='procedureSec']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
					click_button("Add Icon", addIcon);
				}
			}
		}	
	}
	
	
	By billingProviderNPI = By.xpath("//input[@id='npi']");
	
	By billingName = By.xpath("//input[@id='name']");
	
	By billingState = By.xpath("//input[@id='state']");
	
	By billingZipCode = By.xpath("//input[@id='zipcode']");
	
	By billingTaxonomy = By.xpath("//input[@id='taxonomy']");
	
	By ICUDays = By.xpath("//input[@id='icuDays']");
	
	public void billingProviderDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("BillingProviderNPI"+i).isEmpty())
			enter_text_value("Billing Provider NPI", billingProviderNPI, oParameters.GetParameters("BillingProviderNPI"+i));
	
		if(!oParameters.GetParameters("BillingProviderName"+i).isEmpty())
			enter_text_value("Billing Name", billingName, oParameters.GetParameters("BillingProviderName"+i));
		
		if(!oParameters.GetParameters("State"+i).isEmpty())
			enter_text_value("Billing State", billingState, oParameters.GetParameters("State"+i));
		
		if(!oParameters.GetParameters("Zipcode"+i).isEmpty())
			enter_text_value("Billing Zip Code", billingZipCode, oParameters.GetParameters("Zipcode"+i));
		
		if(!oParameters.GetParameters("Taxonomy"+i).isEmpty())
			enter_text_value("Billing Taxonomy", billingTaxonomy, oParameters.GetParameters("Taxonomy"+i));
		
		if(!oParameters.GetParameters("ICUDays"+i).isEmpty())
			enter_text_value("ICU Days", ICUDays, oParameters.GetParameters("ICUDays"+i));
		
		oReport.AddStepResult("Template Details", "Filled all Template details", "SCREENSHOT");
	}
	
	By SaveTemplate = By.xpath("//div[@id='searchClaim1']//button[contains(.,'Save as Template')]");
	
	By SaveTestPriceTemplate = By.xpath("//form[@id='TestPriceTemplateSaveConfirmDialog']//div[text()='Save Test Price Template']");
	
	By TemplateNameFeild = By.xpath("//input[@id='saveAsTemplateName']");
	
	By TemplateSaveButton = By.xpath("//form[@id='TestPriceTemplateSaveConfirmDialog']//input[@value='Save']");
	
	By clearAllFeilds = By.xpath("//input[@value='Clear All Fields']");
	
	public void SaveAsTemplate(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(oParameters.GetParameters("TemplateSave_Yes_No"+i).equals("YES"))
		{
			click_button("Template Save Button", SaveTemplate);
			
			enter_text_value("Template Name", TemplateNameFeild, oParameters.GetParameters("SavedTempletName"+i));
			fixed_wait_time(2);
			
			facilityName("TESTPRICE", "Apollo srn facility");
		
			click_button("Template Save Button", TemplateSaveButton);
			
			fixed_wait_time(3);
			click_button("Clear All Feilds", clearAllFeilds);
		}	
	}
	
	public By CMS_1500Claim = By.xpath("//ul[@class='cm-nav-tabs']//a[text()='CMS-1500 Claim']");
	
	public void navigateCMS1500Claim()
	{
		click_button("CMS 1500 Claim", CMS_1500Claim);
		waitFor(RateSheetCode,"Rate Sheet Code Feild");
	}
	
	
	By dischargeDate = By.xpath("//input[@id='dischargeDate']");
	
	By ICDVersion_CMS1500_Claim = By.xpath("//select[@id='ICDVersion']");
	
	public void claimProcessingDetails_CMS_1500(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Rate Sheet Code", TestPricerateSheetCode, oParameters.GetParameters("RateSheetCode"+i));
		performKeyBoardAction("ENTER");
		
		By rateSheetCode = By.xpath("//ul[@id='payerContracts-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("RateSheetCode"+i)+"']");
		
		if(IsDisplayed("Rate Sheet Code", rateSheetCode))
			click_button("RateSheet Code", rateSheetCode);
		
		selectOption(selectPeriod,"value",oParameters.GetParameters("Period"+i));
		
		if(!oParameters.GetParameters("HPA"+i).isEmpty())
			enter_text_value("Health Plan Alias", healthPlanAlias, oParameters.GetParameters("HPA"+i));
		
		patientDetails("");
		
		if(!oParameters.GetParameters("Admission"+i).isEmpty())
		{
			enter_text_value("Admission Date", admissionDate, oParameters.GetParameters("Admission"+i));
			performKeyBoardAction("ENTER");
		}
		
		if(!oParameters.GetParameters("Discharge"+i).isEmpty())
		{
			enter_text_value("Discharge Date", dischargeDate, oParameters.GetParameters("Discharge"+i));
			performKeyBoardAction("ENTER");
		}
		
		selectOption(ICDVersion_CMS1500_Claim,"visibletext",oParameters.GetParameters("ICDVersion"+i));
	}
	
	public void diagnosisCodeDetails(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("DiagnosisCode"+i).isEmpty())
		{	
			String[] daignosisCode = oParameters.GetParameters("DiagnosisCode"+i).split(",");
		
			for(int j=0;j<daignosisCode.length;j++)
			{
				By DaignosisCode = By.xpath("//input[@id='diagnosis"+j+"']");
				enter_text_value("Conditions Code", DaignosisCode, daignosisCode[j]);
			
				if(j < daignosisCode.length-1)
				{
					By addIcon = By.xpath("//div[@id='diagnosisSec']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
					click_button("Add ICon", addIcon);
				}
			}
		}
	}
	
	
	public void lineItems_CMS_1500_Claim(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] serviceDateForm = oParameters.GetParameters("ServiceDateFrom"+i).split(","); 
		
		String[] serviceDateTo = oParameters.GetParameters("ServiceDateTo"+i).split(",");
		
		String[] Po = oParameters.GetParameters("24BPO"+i).split(",");
		
		String[] HCPCSCode = oParameters.GetParameters("24DCPT/HCPCS"+i).split(",");
		
		String[] modifiers = oParameters.GetParameters("Modifiers"+i).split("-");
		
		String[] pointers = oParameters.GetParameters("24EPointers"+i).split("-");
		
		String[] charges = oParameters.GetParameters("24FCharges"+i).split(",");
		
		String[] serviceUnits = oParameters.GetParameters("24GServiceUnits"+i).split(",");
		
		String[] RenderingProviderNPI = oParameters.GetParameters("24JRenderingProviderNPI"+i).split(",");
		
		String[] RenderingProviderTaxonomy = oParameters.GetParameters("24JRenderingProviderTaxonomy"+i).split(",");
		
		String[] AnesthesiaHours = oParameters.GetParameters("AnesthesiaHours"+i).split(",");
		
		String[] AnesthesiaMinutes = oParameters.GetParameters("AnesthesiaMinutes"+i).split(",");
	
		
		for(int j=0;j<HCPCSCode.length;j++)
		{
			By hcpcsCode = By.xpath("//input[@id='hcpcs"+j+"']");
			enter_text_value("HCPCS Code", hcpcsCode, HCPCSCode[j]);
			
			for(int k=j;k<=j;k++)
			{
				By serviceDateform = By.xpath("//input[@id='fromDate"+k+"']");
				
				By serviceDateto = By.xpath("//input[@id='toDate"+k+"']");
				
				try
				{
					if(!serviceDateForm[k].isEmpty())
					{	
						enter_text_value("Service Form Date", serviceDateform, serviceDateForm[k]);
						performKeyBoardAction("ENTER");
						
						enter_text_value("Service To Date", serviceDateto, serviceDateTo[k]);
						performKeyBoardAction("ENTER");
					}	
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				
				for(int l=k;l<=k;l++)
				{
					By PO = By.xpath("//input[@id='pos"+l+"']");
					
					try
					{
						if(!Po[l].isEmpty())
							enter_text_value("24B PO",PO , Po[l]);
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					
					for(int m=l;m<=l;m++)
					{
						By HcpcsCode = By.xpath("//input[@id='hcpcs"+m+"']");
						
						try
						{
							if(!HCPCSCode[m].isEmpty())
								enter_text_value("HCPCS Code", HcpcsCode, HCPCSCode[m]);
						}
						catch(Exception e)
						{
							System.out.println(e.getMessage());
						}
						
						for(int n=m;n<=m;n++)
						{
							try
							{
								for(int p=n;p<=modifiers.length;p++)
								{	
									String ModifierValue[] = modifiers[p].split(",");
									
									for(int b=0;b<=ModifierValue.length;b++)
									{
										By modifier = By.xpath("//div[@id=\"hcpcCol"+j+"\"]/div/input["+(b+1)+"]");
									
										if(!ModifierValue[b].isEmpty())
											enter_text_value("Modifiers", modifier, ModifierValue[b]);
									}
								}	
							}
							catch(Exception e)
							{
								System.out.println(e.getMessage());
							}
							
							for(int q=n;q<=n;q++)
							{
								try
								{
									for(int r=q;r<=pointers.length;r++)
									{
										String PointersValue[] = pointers[r].split(",");
										
										for(int c=0;c<=PointersValue.length;c++)
										{
											By pointer = By.xpath("//div[@id=\"pointersCol"+j+"\"]/input["+(c+1)+"]");
										
											if(!PointersValue[c].isEmpty())
												enter_text_value("Pointers",pointer, PointersValue[c]);
										}
									}
								}
								catch(Exception e)
								{
									System.out.println(e.getMessage());
								}
								
								for(int s=q;s<=q;s++)
								{
									By charge = By.xpath("//input[@id='charges"+s+"']");
									
									try
									{
										if(!charges[s].isEmpty())
											enter_text_value("Charges Value",charge,charges[s]);
									}
									catch(Exception e)
									{
										System.out.println(e.getMessage());
									}
									
									for(int t=s;t<=s;t++)
									{
										By serviceUnit = By.xpath("//input[@id='unit"+t+"']");
										
										try
										{
											if(!serviceUnits[t].isEmpty())
												enter_text_value("Service Units",serviceUnit, serviceUnits[t]);
										}
										catch(Exception e)
										{
											System.out.println(e.getMessage());
										}
										
										for(int u=t;u<=t;u++)
										{
											By renderingProviderNPI = By.xpath("//input[@id='renderingProviderNpi"+u+"']");
											
											try
											{
												if(!RenderingProviderNPI[u].isEmpty())
													enter_text_value("Rendering Provider Value NPI", renderingProviderNPI, RenderingProviderNPI[u]);
											}
											catch(Exception e)
											{
												System.out.println(e.getMessage());
											}
											
											for(int v=u;v<=u;v++)
											{
												By renderingProvidertaxonomy = By.xpath("//input[@id='taxonomyCode"+v+"']");
												
												try
												{
													if(!RenderingProviderTaxonomy[v].isEmpty())
														enter_text_value("Rendering Provider Taxonomy", renderingProvidertaxonomy, RenderingProviderTaxonomy[v]);
												}
												catch(Exception e)
												{
													System.out.println(e.getMessage());
												}
												
												for(int w=v;w<=v;w++)
												{
													By anesthesiaHours = By.xpath("//input[@id='anesthesiaHrs"+w+"']");
													
													try
													{
														if(!AnesthesiaHours[w].isEmpty())
															enter_text_value("Anesthesia Hours", anesthesiaHours, AnesthesiaHours[w]);
													}
													catch(Exception e)
													{
														System.out.println(e.getMessage());
													}
													
													for(int x=w;x<=w;x++)
													{
														By anesthesiaMinutes = By.xpath("//input[@id='anesthesiaMins"+x+"']");
														
														try
														{
															if(!AnesthesiaMinutes[x].isEmpty())
																enter_text_value("Anesthesia Minutes", anesthesiaMinutes, AnesthesiaMinutes[x]);
														}
														catch(Exception e)
														{
															System.out.println(e.getMessage());
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		
			if(j < HCPCSCode.length-1)
			{
				By addIcon = By.xpath("//table[@id='proClaimLineItems']//tr["+(j+1)+"]//i[@class='left fa fa-plus-square']");
				click_button("ADD ICON", addIcon);
			}
		}
	}
	
	
	By ClaimError = By.xpath(".//*[@id='errors']//tr/td[@style='text-align: left' and contains(.,'Claim Errors')]");
	
	By totalCharges = By.xpath("//div[@id='testpriceView']//label[text()='Total Charges']/following-sibling::span[1]");
	
	By AddingCharges = By.xpath("//div[@id='testpriceView']//input[@name='charges']");
	
	By CoveredCharges = By.xpath("//div[@id='claimSummary']//td[@class='currency ng-isolate-scope']//span[@class='ng-binding green']");
	
	public By testPriceButton = By.xpath("//input[@value='Test Price']");
	
	public void ChangeChargeValue_Validation()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
	
		for(int i=0;i<2;i++)
		{	
			By Charges = By.xpath("//input[@id='charges"+i+"']");
			enter_text_value("New revenueCode",Charges, oParameters.GetParameters("Charges"+i+""));
		}
		
		oReport.AddStepResult("Claim Details","Entered Rate Sheet Code and All line items Detils,Daignosis Details successfully ","PASS");
		
		scroll("Covered Charges", totalCharges);
		String TotalChargesValues = get_text(totalCharges).replaceAll("[,n/a $,()]","");

		double TotalchargeValues = Double.parseDouble(TotalChargesValues);

		NumberFormat numberformat = NumberFormat.getInstance();
		numberformat.setMaximumFractionDigits(1);
		String charges = numberformat.format(TotalchargeValues);

		int noOfCharges = get_table_row_count(AddingCharges);
		double  ChargesValues = 0.00 ;

		for(int i=0;i<=noOfCharges-1;i++)
		{
			By ChargeValue = By.xpath("//input[@id='charges"+i+"']");

			if(isTextBoxBlank("Charges Value", ChargeValue))
				System.out.println("Charges value is empty:" + i);
			else
			{	
				Float valueOf = Float.valueOf(get_field_value("Reimbursement Value",ChargeValue).replaceAll("[,n/a $,()]",""));
				ChargesValues = valueOf+ChargesValues;      
			}  
		}

		scroll("Total Charge", totalCharges);
		oReport.AddStepResult("Total Charge", "Total Charge Value", "SCREENSHOT");
		
		try
		{
			numberformat.setMaximumFractionDigits(1);
			String myString = numberformat.format(ChargesValues);

			if(charges.replaceAll("^0*|\\.0*$", "").equals(myString.replaceAll("\\.0*$","")))
				oReport.AddStepResult("Total Charges", "Entered all the "+myString+" charges value and verified that  it is equal to total "+charges+" charge value ", "PASS");
			else
				oReport.AddStepResult("Total Charges", "Entered all the "+myString+" charges value and verified that  it is not equal to total "+charges+" charge value ", "FAIL");			
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e.getMessage());
		}

		
		click_button("test price button", testPriceButton);
		fixed_wait_time(3);
		
		driver.switchTo().frame("testPriceEorView");
		
		if(get_field_value("Covered Charges", CoveredCharges).replaceAll("[,n/a $,()]", "").equals(TotalChargesValues))
			oReport.AddStepResult("Covered Charges Validation", "In "+oParameters.GetParameters("TESTNAME")+" VR, Clicked on Test price and verified that Covered Charges value is "+get_text(CoveredCharges)+" displayed as Expected", "PASS");
		else
			oReport.AddStepResult("Covered Charges Validation", "In "+oParameters.GetParameters("TESTNAME")+" VR, Clicked on Test price and verified that Covered Charges value is "+get_text(CoveredCharges)+" not displayed as Expected", "FAIL");

		driver.switchTo().defaultContent();
	}
	
	
	
	By serviceProviderState = By.xpath("//input[@id='spState']");
	
	By serviceProviderZipCode = By.xpath("//input[@id='spzipCode']");
	
	By federalTaxID = By.xpath("//input[@id='taxId']");
	
	By billingProviderName = By.xpath("//input[@id='name']");
	
	By billingProviderState = By.xpath("//input[@id='state']");
	
	By billingProviderZipCode = By.xpath("//input[@id='zipcode']");
	
	By billingProviderNPI_CMS1500_Claim = By.xpath("//input[@id='NPI']");
	
	By billingProviderTaxonomy = By.xpath("//input[@id='providerTaxonomy']");
	
	By billingProviderProviderGroup = By.xpath("//input[@id='providerGroup']");
	
	
	public void billingProviderDetails_CMS1500_Claim(String i)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("ServiceProvider_State"+i).isEmpty())
			enter_text_value("Service Provider State", serviceProviderState, oParameters.GetParameters("ServiceProvider_State"+i));
		
		if(!oParameters.GetParameters("ServiceProvider_ZipCode"+i).isEmpty())
			enter_text_value("Service Provider Zip Code", serviceProviderZipCode, oParameters.GetParameters("ServiceProvider_ZipCode"+i));
		
		if(!oParameters.GetParameters("FederalTaxID"+i).isEmpty())
			enter_text_value("Federal Tax ID", federalTaxID, oParameters.GetParameters("FederalTaxID"+i));
		
		if(!oParameters.GetParameters("Name"+i).isEmpty())
			enter_text_value("Billing Provider Name", billingProviderName, oParameters.GetParameters("Name"+i));
		
		if(!oParameters.GetParameters("BillingProvider_State"+i).isEmpty())
			enter_text_value("Billing Provider State", billingProviderState, oParameters.GetParameters("BillingProvider_State"+i));
		
		if(!oParameters.GetParameters("ZipCode"+i).isEmpty())
			enter_text_value("Billing Provider zip Code", billingProviderZipCode, oParameters.GetParameters("ZipCode"+i));
		
		if(!oParameters.GetParameters("NPI"+i).isEmpty())
			enter_text_value("Billing Provider NPI", billingProviderNPI_CMS1500_Claim, oParameters.GetParameters("NPI"+i));
		
		if(!oParameters.GetParameters("Taxonomy"+i).isEmpty())
			enter_text_value("Billing Provider Taxonomy", billingProviderTaxonomy, oParameters.GetParameters("Taxonomy"+i));
		
		if(!oParameters.GetParameters("ProviderGroup"+i).isEmpty())
			enter_text_value("Billing Provider Group",billingProviderProviderGroup , oParameters.GetParameters("ProviderGroup"+i));
	}
	
	
	//Template Save Build for UB-04 Claim.
	public void templetBuild_UB04_Claim(String i)
	{
		claimProcessingDetails(String.valueOf(i));
		patientDetails(String.valueOf(i));
		admissionDischargeDetails(String.valueOf(i));
		conditionCodesDetails(String.valueOf(i));
		occurrenceDetails(String.valueOf(i));
		OccurrenSpan(String.valueOf(i));
		valueCodeDetails(String.valueOf(i));
		lineItems(String.valueOf(i));
		diagnosisDetails(String.valueOf(i));
		procedureCodeDetails(String.valueOf(i));
		billingProviderDetails(String.valueOf(i));
		//SaveAsTemplate(String.valueOf(i));
	}
	
	//Template Save Build for UB-04 Claim.
	public void TestPriceTemplet_UB04_Claim_Build()
	{
		login("EDIT");
		changePricingEngine();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceEndToEndScenario", "");
		int rowCount = oExcelData.getRowCount1("TestPriceEndToEndScenario", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");
		
		navigateToTestPrice();
		
		for(int i=1;i<=rowCount-1;i++)
		{
			templetBuild_UB04_Claim(String.valueOf(i));
		}
	}
	
	
	public void templetBuild_CMS1500_Claim(String i)
	{
		claimProcessingDetails_CMS_1500(String.valueOf(i));
		diagnosisCodeDetails(String.valueOf(i));
		lineItems_CMS_1500_Claim(String.valueOf(i));
		billingProviderDetails(String.valueOf(i));
		SaveAsTemplate(String.valueOf(i));
	}
	
	//Template Save Build for CMS-1500 Claim.
	public void TestPriceTemplet_CMS1500_Claim()
	{
		login("EDIT");
		changePricingEngine();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_CMS1500", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_CMS1500", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		navigateToTestPrice();
		navigateCMS1500Claim();
		
		for(int i=1;i<=rowCount-1;i++)
		{
			templetBuild_CMS1500_Claim(String.valueOf(i));
		}
	}
	
	
	public void CreateQualificationGroups()
    {
		login("EDIT");
    	changePricingEngine(); 
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "QG", "");
    	int rowCount = oExcelData.getRowCount1("QG", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oQualificationGroupLibrary.navigateQualificationPlugin();
    	
        for(int i=1; i<=rowCount; i++)
        {
            oQualificationGroupLibrary.addQualificationGroup();
            oQualificationGroupLibrary.QualificationDetails(String.valueOf(i));
        	oQualificationGroupLibrary.saveButton();
                  
        }
    }
	
	
	
	//PPS DRG Build---------------------------------------
	
	By UserRateStatus = By.xpath("//select[@id='statusCode']");
	
	By AddDRGUserRateSetWindow = By.xpath("//div[@title='Add DRG User Rate Set']");
	
	By AddCMGUserRateSetWindow = By.xpath("//div[@title='Add CMG User Rate Set']");
	
	By DRGUserRate_TerminationDate = By.xpath("//input[@id='stopDateDrgUserRate']");
	
	By CMGFirstUserRate = By.xpath("//div[@form-id='cmgUserRateSetFormModel.formId']//ul[@id='-list']/li[1]");
	
	By CMGSelectPeriodToCopy = By.xpath("//div[@form-id='cmgUserRateSetFormModel.formId']//select[@id='copyPeriodSelect']");
	
	By RUGFirstUserRate = By.xpath("//div[@form-id='userRateSetFormModel.formId']//ul[@id='-list']/li[1]");
	
	By RUGSelectPeriodToCopy = By.xpath("//div[@form-id='userRateSetFormModel.formId']//select[@id='copyPeriodSelect']");
	
	By NameMustUnique = By.xpath("//ul[@class='error-items']//span[contains(text(),'The name must be unique.')]");
	
	By NameMustUnique_RedBoxErrorCloseIcon = By.xpath("//ul[@class='error-items']//span[@ng-show='canShowCloseBox']");
	
	By DRG_CMGUserRateSetWindow_CloseIcon = By.xpath("//div[@id='addUserRateSetModal']//button[@title='Close']");
	
	By RUGUserRateSetWindow_CloseIcon = By.xpath("//div[@id='addRugUserRateSetModal']//button[@title='Close']");
	
	By UnSavedChanges_DiscardButton = By.xpath("//input[@value='Discard']");
	
	By SearchUserRates = By.xpath("//input[@placeholder='Search User Rates']");
	
	By DRG_CMG_UserRateSaveButton = By.xpath("//div[@id='addUserRateSetModal']//input[@value='Save']");
	
	By RUG_UserRateSaveButton = By.xpath("//div[@id='addRugUserRateSetModal']//input[@value='Save']");
	
	public void addUserRateSetDetails(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
		
		By UserRateSetName = null;
		By EffectiveDate = null;
		By TerminationDate =null;
		By UserRateStatus_DropDown = null;
		By ChooseFile_Button = null;
		By CopyExisiting_UserRate_CheckBox = null;
		By SelectUserRate_SearchBox = null;
		By FirstUserRates = null;
		By PeriodToCopy = null;
		By AddUserRateSetWindow_CloseButton = null;
		By SaveButton = null;
		
		String Frame_ID = null;
		String UserRateSetName_Details = null;
		String EffectiveDate_Details = null;
		String TerminationDate_Details = null;
		String UserRateStatus_Details = null;
		String ImportFile_Details = null;
		String AutoitScript_Details = null;
		String CopyExisitingUserRate_Details = null;
		String SelectUserRate_Details = null;
		String SelectPeriod_Details = null;
		
		
		if(IsDisplayed("Add DRG User Rate Set Window", AddDRGUserRateSetWindow))
		{
			UserRateSetName = oPPSLibrary.DRGUserRateSetName;
			EffectiveDate = oPPSLibrary.effectiveDate;
			TerminationDate = DRGUserRate_TerminationDate;
			UserRateStatus_DropDown = UserRateStatus;
			ChooseFile_Button = oPPSLibrary.chooseFile;
			CopyExisiting_UserRate_CheckBox = oPPSLibrary.copyCheckBox;
			SelectUserRate_SearchBox = oPPSLibrary.searchExistingElement;
			FirstUserRates = oPPSLibrary.selectFirstElement;
			PeriodToCopy = oPPSLibrary.selectPeriodToCopy;
			AddUserRateSetWindow_CloseButton = DRG_CMGUserRateSetWindow_CloseIcon;
			SaveButton = DRG_CMG_UserRateSaveButton;
			
			Frame_ID = "importUserRateIframe";
			UserRateSetName_Details = "DRG_USER_RATE_SET_NAME";
			EffectiveDate_Details = "EFFECTIVE_DATE";
			TerminationDate_Details = "TERMINATION_DATE";
			UserRateStatus_Details = "USER_RATE_STATUS";
			ImportFile_Details = "IMPORT_DRG_RATE_TABLE";
			AutoitScript_Details = "AUTOIT_SCRIPT_PATH";
			CopyExisitingUserRate_Details = "COPY_FROM_EXISTING_USER_RATE_SET";
			SelectUserRate_Details = "SELECT_USER_RATE_SET";
			SelectPeriod_Details = "SELECT_PERIOD_TO_COPY";
		}
		else if(IsDisplayed("Add CMG User Rate Set Window", AddCMGUserRateSetWindow))
		{
			UserRateSetName = oPPSLibrary.userRateSetName;
			EffectiveDate = oPPSLibrary.userRateSetEffectiveDate;
			TerminationDate = oPPSLibrary.userRateSetTerminationDate;
			UserRateStatus_DropDown = oPPSLibrary.userRateStatusDD;
			ChooseFile_Button = oPPSLibrary.chooseFileRUG;
			CopyExisiting_UserRate_CheckBox = oPPSLibrary.checkBox;
			SelectUserRate_SearchBox = oPPSLibrary.selectCodeSetSB;
			FirstUserRates = CMGFirstUserRate;
			PeriodToCopy = CMGSelectPeriodToCopy;
			AddUserRateSetWindow_CloseButton = DRG_CMGUserRateSetWindow_CloseIcon;
			SaveButton = DRG_CMG_UserRateSaveButton;
			
			Frame_ID = "importCMGUserRateIframe";
			UserRateSetName_Details = "CMG_USER_RATE_SET_NAME";
			EffectiveDate_Details = "EFFECTIVE_DATE";
			TerminationDate_Details = "TERMINATION_DATE";
			UserRateStatus_Details = "USER_RATE_STATUS";
			ImportFile_Details = "IMPORT_CMG_RATE_TABLE";
			AutoitScript_Details = "AUTOIT_SCRIPT_PATH";
			CopyExisitingUserRate_Details = "COPY_DETAILS_FROM_EXISTING_USER_RATE_SET";
			SelectUserRate_Details = "SELECT_USER_RATE_SET";
			SelectPeriod_Details = "SELECT_PERIOD_TO_COPY";
			
		}
		else if(IsDisplayed("Add RUG User Rate Set Window", oPPSLibrary.addRUGUserRateSetWindow))
		{
			UserRateSetName = oPPSLibrary.userRateSetName;
			EffectiveDate = oPPSLibrary.userRateSetEffectiveDate;
			TerminationDate = oPPSLibrary.userRateSetTerminationDate;
			UserRateStatus_DropDown = oPPSLibrary.userRateStatusDD;
			ChooseFile_Button = oPPSLibrary.chooseFileRUG;
			CopyExisiting_UserRate_CheckBox = oPPSLibrary.copyFromExistingUserRateSetCheckbox;
			SelectUserRate_SearchBox = oPPSLibrary.selectCodeSetSB;
			FirstUserRates = RUGFirstUserRate;
			PeriodToCopy = RUGSelectPeriodToCopy;
			AddUserRateSetWindow_CloseButton = RUGUserRateSetWindow_CloseIcon;
			SaveButton = RUG_UserRateSaveButton;
			
			Frame_ID = "importRugUserRateIframe";
			UserRateSetName_Details = "USER_RATE_SET_NAME";
			EffectiveDate_Details = "EFFECTIVE_DATE";
			TerminationDate_Details = "TERMINATION_DATE";
			UserRateStatus_Details = "USER_RATE_STATUS";
			ImportFile_Details = "IMPORT_RUG_RATE_TABLE";
			AutoitScript_Details = "AUTOIT_SCRIPT_PATH";
			CopyExisitingUserRate_Details = "COPY_DETAILS_FROM_EXISTING_USER_RATE_SET";
			SelectUserRate_Details = "SELECT_CODE_SET";
			SelectPeriod_Details = "SELECT_PERIOD_TO_COPY";
			
		}
		
		enter_text_value("DRG User Rate Set", UserRateSetName, oParameters.GetParameters(""+UserRateSetName_Details+""+i)); 
		enter_text_value("Effective Date", EffectiveDate, oParameters.GetParameters(""+EffectiveDate_Details+""+i));
		performKeyBoardAction("ENTER");
		
		if(!oParameters.GetParameters(""+TerminationDate_Details+""+i).isEmpty())
		{
			enter_text_value("Termination Date", TerminationDate, oParameters.GetParameters(""+TerminationDate_Details+""+i));
			performKeyBoardAction("ENTER");
		}
		
		fixed_wait_time(2);
		
		if(oParameters.GetParameters(""+UserRateStatus_Details+""+i).equals("Active"))
			selectOption(UserRateStatus_DropDown,"visibletext",oParameters.GetParameters(""+UserRateStatus_Details+""+i));
		
		facilityName("PPS", "Apollo srn facility");
		
		if(!oParameters.GetParameters(""+ImportFile_Details+""+i).isEmpty())		
		{
			driver.switchTo().frame(Frame_ID);
			click_button("Choose File button", ChooseFile_Button);
			
			try
			{
				Runtime.getRuntime().exec(oParameters.GetParameters(""+AutoitScript_Details+"")+i);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}	
		
		if(!oParameters.GetParameters(""+CopyExisitingUserRate_Details+""+i).isEmpty())
		{
			click_button("Copy Details from existing User Rate Set", CopyExisiting_UserRate_CheckBox);
			
			enter_text_value("Select User Rate Set", SelectUserRate_SearchBox, oParameters.GetParameters(""+SelectUserRate_Details+""+i));
			performKeyBoardAction("ENTER");
			
			if(IsDisplayed("Select User Rate Set", FirstUserRates))
				click_button("Select first element", FirstUserRates);
			
			fixed_wait_time(2);
			
			if(!oParameters.GetParameters(""+SelectPeriod_Details+""+i).isEmpty())
				selectOption(PeriodToCopy, "value", oParameters.GetParameters(""+SelectPeriod_Details+""+i));
		}
		
		fixed_wait_time(2);
		
		click_button("Add User Rate Set Save Button", SaveButton);
		
		if(IsDisplayed("Error. The name must be unique", NameMustUnique))
		{
			oReport.AddStepResult("Error. The name must be Unique","Clicked on Add User Rate Set Button,Filled User Rate Set Name as '"+oParameters.GetParameters(""+UserRateSetName_Details+""+i)+"'  and clicked on save button and verified that 'Error.The name must be unique message is displayed',So "+oParameters.GetParameters(""+UserRateSetName_Details+""+i)+" User Name already Exist", "PASS");
			
			click_button("RedBox Error Close icon", NameMustUnique_RedBoxErrorCloseIcon);
		
			click_button("Add User Rate Set Window Close Icon", AddUserRateSetWindow_CloseButton);
			
			click_button("You Have UnSaved Changes Window Discard Button", UnSavedChanges_DiscardButton);
			
			fixed_wait_time(1);
			
			enter_text_value("Search User Rates", SearchUserRates, oParameters.GetParameters(""+UserRateSetName_Details+""+i));
			performKeyBoardAction("ENTER");
			
			fixed_wait_time(1);
			
			By FirstUserRateSet = By.xpath("//div[@id='ppView']//ul//li[contains(.,'"+oParameters.GetParameters(""+UserRateSetName_Details+""+i)+"')]");
			click_button("First User Rate Set", FirstUserRateSet);
		}
		
		fixed_wait_time(1);
		oParameters.SetParameters("PPSTYPE", get_field_value("PPS Type Value", PPSType));
	}
	
By Description = By.xpath("//div[@id='addEditUserRateForm']//input[@id='userRateDescription']");
	
	By RUG_Description = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='userRateDescription']");
	
	By TotalPayerPaymentRate = By.xpath("//div[@id='addEditUserRateForm']//input[@id='totalPyrPmtRate']");
	
	By RUG_TotalPayerPaymentRate = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='totalPyrPmtRate']");
	
	By StandardTotalCharges = By.xpath("//div[@id='addEditUserRateForm']//input[@id='stdTotCharges']");
	
	By RUG_StandardTotalCharges = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='stdTotCharges']");
	
	By LowTrimCharge = By.xpath("//div[@id='addEditUserRateForm']//input[@id='lowTrimCharge']");
	
	By RUG_LowTrigCharge = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='lowTrimCharge']");
	
	By RUG_LoWTrimDays = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='lowTrimDays']");
	
	By RUG_HighTrimDays = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='highTrimDays']");
	
	By RUG_LOS = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='stdLenOfStay']");
	
	By HighTrimCharge = By.xpath("//div[@id='addEditUserRateForm']//input[@id='highTrimCharge']");
	
	By RUG_HighTrimCharge = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='highTrimCharge']");
	
	By PerDiemAmount = By.xpath("//div[@id='addEditUserRateForm']//input[@id='perDiemAmt']");
		
	By RUG_PerDeimAmount = By.xpath("//div[@id='addEditRugUserRateSetEntry']//input[@id='perDiemAmt']");
	
	By SaveButton = By.xpath("//div[@id='addUserRateSetModal']//input[@value='Save']");
	
	By PPSType = By.xpath("//div[@id='ppView']//div[contains(@class,'document-title-bar')]//span[contains(@class,'pad-l-10 xl-header')]");
	
	By addRUGUserRateSet = By.xpath("//a[@ng-click='addRugUserRateEntry()']");
	
	public void Add_DRG_UserRateSetEntry(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] Code = null;
		String[] soi = null;
		
		By Code_Field = null;
		By Soi_Field = null;
		By Description_Feild = null;
		By weight_Field = null;
		By totalPayermentRate_Field = null;
		By lowTrimDays_Field = null;
		By highTrimDays_Field = null;
		By LOS_Feild = null;
		By StandardTotalCharges_Feild = null;
		By LowTrimCharge_Feild = null;
		By HighTrimCharge_Feild = null;
		By PerDiemAmount_Feild = null;
		
		
		
		if(oParameters.GetParameters("PPSTYPE").equals("PPS DRG"))
		{	
			click_button("Add Icon", oPPSLibrary.addUserRateSetEntry);
			fixed_wait_time(2);
			Code = oParameters.GetParameters("DRG_CODE"+i).split(",");
			soi = oParameters.GetParameters("SOI"+i).split(",");
			
			Code_Field = oPPSLibrary.drgCode;
			Soi_Field = oPPSLibrary.soi;
			Description_Feild = Description;
			weight_Field = oPPSLibrary.weight;
			totalPayermentRate_Field = TotalPayerPaymentRate;
			lowTrimDays_Field = oPPSLibrary.lowTrimDays;
			highTrimDays_Field = oPPSLibrary.highTrimDays;
			LOS_Feild = oPPSLibrary.los;
			StandardTotalCharges_Feild = StandardTotalCharges;
			LowTrimCharge_Feild = LowTrimCharge;
			HighTrimCharge_Feild = HighTrimCharge;
			PerDiemAmount_Feild = PerDiemAmount;
			
		}
		else if(oParameters.GetParameters("PPSTYPE").equals("PPS RUG"))
		{
			click_button("Add Icon", addRUGUserRateSet);
			fixed_wait_time(2);
			Code = oParameters.GetParameters("RUG_CODE"+i).split(",");
			
			Code_Field = oPPSLibrary.RUGCode;
			Description_Feild = RUG_Description;
			weight_Field = oPPSLibrary.RUGWeight;
			totalPayermentRate_Field = RUG_TotalPayerPaymentRate;
			lowTrimDays_Field = RUG_LoWTrimDays;
			highTrimDays_Field = RUG_HighTrimDays;
			LOS_Feild = RUG_LOS;
			StandardTotalCharges_Feild = RUG_StandardTotalCharges;
			LowTrimCharge_Feild = RUG_LowTrigCharge;
			HighTrimCharge_Feild = RUG_HighTrimCharge;
			PerDiemAmount_Feild = RUG_PerDeimAmount;
			
		}
		
		String[] description = oParameters.GetParameters("DESCRIPTION"+i).split(",");
		
		String[] weight = oParameters.GetParameters("WEIGHT"+i).split(",");
		
		String[] payerPayment = oParameters.GetParameters("PAYER_PAYMENT_RATE_1_2_3"+i).split("-");
		
		String[] totalPayerPaymentRate = oParameters.GetParameters("TOTAL_PAYER_PAYMENT_RATE"+i).split(",");
		
		String[] lowtrimdays = oParameters.GetParameters("LOW_TRIM_DAYS"+i).split(",");
		
		String[] hightrimdays = oParameters.GetParameters("HIGH_TRIM_DAYS"+i).split(",");
		
		String[] los = oParameters.GetParameters("LOS"+i).split(",");
		
		String[] standardtotalcharges = oParameters.GetParameters("STANDARD_TOTAL_CHARGES"+i).split(",");
		
		String[] lowtrimcharge = oParameters.GetParameters("LOW_TRIM_CHARGE"+i).split(",");
		
		String[] hightrimcharge = oParameters.GetParameters("HIGH_TRIM_CHARGE"+i).split(",");
		
		String[] perdiemamount = oParameters.GetParameters("PER_DIEM_AMOUNT"+i).split(",");
		
		for(int j=0;j<Code.length;j++)
		{	
			enter_text_value(" Code Value", Code_Field,Code[j]);		
		
			if(oParameters.GetParameters("PPSTYPE").equals("PPS DRG"))
			{	
				if(!soi[j].isEmpty())	
					enter_text_value("SOI Value", Soi_Field, soi[j]);
			}
			
			if(!description[j].isEmpty())
				enter_text_value("Description", Description_Feild, description[j]);
		
			if(!weight[j].isEmpty())
				enter_text_value("Weight Value", weight_Field, weight[j]);
		
			try
			{
				String[] Payerpayment = payerPayment[j].split(",");
					
				for(int l=0;l<Payerpayment.length;l++)
				{
					By PayerPaymentRate = By.xpath("//div[@class='workflow-modal-content col-lg-12 col-md-12 col-sm-12']//input[@id='pyrPmtRate"+(l+1)+"']");
				
					if(!Payerpayment[l].isEmpty())
						enter_text_value("Payer Payment Rate1 Value", PayerPaymentRate, Payerpayment[l]);
				}	
				
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			
			
			if(!totalPayerPaymentRate[j].isEmpty())
				enter_text_value("Total Payer Payment Rate", totalPayermentRate_Field, totalPayerPaymentRate[j]);
		
			if(!lowtrimdays[j].isEmpty())
				enter_text_value("Low Trim Days", lowTrimDays_Field, lowtrimdays[j]);
		
			if(!hightrimdays[j].isEmpty())
				enter_text_value("High Trim Days", highTrimDays_Field, hightrimdays[j]);
		
			if(!los[j].isEmpty())
				enter_text_value("Length of Stay Value", LOS_Feild, los[j]);
		
			if(!standardtotalcharges[j].isEmpty())
				enter_text_value("Standard Total Charges", StandardTotalCharges_Feild, standardtotalcharges[j]);
		
			if(!lowtrimcharge[j].isEmpty())
				enter_text_value("Low Trim Charge", LowTrimCharge_Feild, lowtrimcharge[j]);
		
			if(!hightrimcharge[j].isEmpty())
				enter_text_value("High Trim Charge", HighTrimCharge_Feild, hightrimcharge[j]);
		
			if(!perdiemamount[j].isEmpty())
				enter_text_value("Per Diem Amount", PerDiemAmount_Feild, perdiemamount[j]);
		
			click_button("Add User Rate Set Entry 'Save Button'", oPPSLibrary.saveButton);
			
			if(j < Code.length-1)
			{
				if(get_field_value("PPS Type Value", PPSType).equals("PPS DRG"))
					click_button("Add Icon ", oPPSLibrary.addUserRateSetEntry);
				else if(get_field_value("PPS Type Value", PPSType).equals("PPS RUG"))
					click_button("Add Icon ", addRUGUserRateSet);
			}	
		}
	}
	
	By periodDropDown = By.xpath("//div[@id='ppView']//span[@ng-show='periods.length > 0']");
	
	By addPeriod = By.xpath("//div[@id='ppView']//div[contains(@on-change,'select')]//a[@ng-click='addPeriod()']");
	
	By terminationDate = By.xpath("//div[@class='workflow  modal-medium']//input[@id='stopDate']");
	
	By FirstDRGUserRateSetDetails = By.xpath("//div[@form-id='periodFormModel.formId']//ul[@id='-list']//li[1]");
	
	By FirstCMGUserRateSetDetails = By.xpath("//div[@form-id='cmgPeriodFormModel.formId']//ul[@id='-list']//li[1]");
	
	By FirstRUGUserRateSetDetails = By.xpath("//div[@form-id='rugPeriodFormModel.formId']//ul[@id='-list']//li[1]");
	
	By periodSaveButton = By.xpath("//workflow-modal[@show='showPeriodModal']//input[@value='Save']");
	
	By PPS_DRG_AddEffectivePeriod = By.xpath("//div[@id='addDrgUserRatePeriod']//div[@title='Add Effective Period']");
	
	By PPS_CMG_AddEffectivePeriod = By.xpath("//div[@id='addEditCmgUserRatePeriodModal']//div[@title='Add Effective Period']");
	
	By PPS_RUG_AddEffectivePeriod = By.xpath("//div[@id='addRugUserRatePeriod']//div[@title='Add Effective Period']");
	
	By PPS_RUG_CopyExisitingUserRateSet = By.xpath("//form[@id='addRugUserRatePeriod']//input[@id='copyClosed']");
	
	public void addPeriod(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		 }
		
		By EffectiveDate_Period = null;
		By Termination_Period = null;
		By ExistingUserRateSetCheckBox = null;
		By SelectUserToCopy_Period = null;
		By SelectPeriodToCopy_Period = null;
		
		if(get_field_value("PPS Type Value", PPSType).equals("PPS DRG"))
		{
			EffectiveDate_Period = oPPSLibrary.addNewEffectivePeriod;
			Termination_Period = terminationDate;
			ExistingUserRateSetCheckBox = oPPSLibrary.periodCopyCheckBox;
			SelectUserToCopy_Period = oPPSLibrary.selectExistingUserRate;
			SelectPeriodToCopy_Period = oPPSLibrary.selectPeriodToCopy_EP;
		}
		else if(get_field_value("PPS Type Value", PPSType).equals("PPS CMG"))
		{
			EffectiveDate_Period = oPPSLibrary.effectiveDateCMG;
			Termination_Period = oPPSLibrary.terminationDateCMG;
			ExistingUserRateSetCheckBox = oPPSLibrary.copyFromExistingUserRateSetCheckbox;
			SelectUserToCopy_Period = oPPSLibrary.selectUserRateSetSBCMG;
			SelectPeriodToCopy_Period = oPPSLibrary.userRateSetPeriodDD;
		}
		else if(get_field_value("PPS Type Value", PPSType).equals("PPS RUG"))
		{
			EffectiveDate_Period = oPPSLibrary.effectiveDateRUG;
			Termination_Period = oPPSLibrary.terminationDateRUG;
			ExistingUserRateSetCheckBox = PPS_RUG_CopyExisitingUserRateSet;
			SelectUserToCopy_Period = oPPSLibrary.selectUserRateSetSBRUG;
			SelectPeriodToCopy_Period = oPPSLibrary.userRateSetPeriodDD;
		}
		
		
		
		if(!oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i).isEmpty())
		{
			click_button("Period Drop Down", periodDropDown);
			
			click_button("Add Perion", addPeriod);
			
			enter_text_value("Effective Date", EffectiveDate_Period, oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i));
			
			if(!oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i).isEmpty())
				enter_text_value("Termination Date", Termination_Period, oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i));
			
			if(!oParameters.GetParameters("PERIOD_USER_RATE_STATUS"+i).isEmpty())
			{
				By UserRateStatus = null;
				
				if(IsDisplayed("Add DRG_Effective Period", PPS_DRG_AddEffectivePeriod))
					UserRateStatus = By.xpath("//div[@form-id='periodFormModel.formId']//label[contains(.,'"+oParameters.GetParameters("PERIOD_USER_RATE_STATUS"+i)+"')]//input");
				else if(IsDisplayed("Add CMG_Efective Period", PPS_CMG_AddEffectivePeriod))
					UserRateStatus = By.xpath("//div[@form-id='cmgPeriodFormModel.formId']//label[contains(.,'"+oParameters.GetParameters("PERIOD_USER_RATE_STATUS"+i)+"')]//input");
				else if(IsDisplayed("PPS RUG_Effective Period", PPS_RUG_AddEffectivePeriod))
					UserRateStatus = By.xpath("//form[@id='addRugUserRatePeriod']//label[contains(.,'"+oParameters.GetParameters("PERIOD_USER_RATE_STATUS"+i)+"')]//input");
					
				click_button("User Rate Status Radio Button", UserRateStatus);
			}
			
			if(!oParameters.GetParameters("PERIOD_COPY_DETAILS_FROM_EXISTING_USER_RATE_SET"+i).isEmpty())
			{
				click_button("Copy Details from Existing User Rate Set CheckBox", ExistingUserRateSetCheckBox);
				
				enter_text_value("Select Period to Copy Text Box", SelectUserToCopy_Period, oParameters.GetParameters("PERIOD_USER_RATE_SET_NAME"+i));
				performKeyBoardAction("ENTER");
				
				if(IsDisplayed("Add DRG User Rate Set Window", PPS_DRG_AddEffectivePeriod))
				{	
					if(IsDisplayed("User Rate Set Details", FirstDRGUserRateSetDetails))
						click_button("Frist User Rate Set Details", FirstDRGUserRateSetDetails);
				}
				else if(IsDisplayed("Add CMG User Rate Set Window", PPS_CMG_AddEffectivePeriod))
				{
					if(IsDisplayed("User Rate Set Details", FirstCMGUserRateSetDetails))
						click_button("Frist User Rate Set Details", FirstCMGUserRateSetDetails);
				}
				else if(IsDisplayed("PPS RUG Add Effective Period", PPS_RUG_AddEffectivePeriod))
				{
					if(IsDisplayed("User Rate Set Details",FirstRUGUserRateSetDetails ))
						click_button("Frist User Rate Set Details", FirstRUGUserRateSetDetails);
				}
				
				selectOption(SelectPeriodToCopy_Period, "value", oParameters.GetParameters("PERIOD_SELECT_PERIOD_TO_COPY"+i));
			}
			
			click_button("Period Save Button", periodSaveButton);
		}
	}
	
	
	
	//PPS DRG User Rates Build.
	public void Create_PPS_DRG_User_Rates_Build()
	{
		login("EDIT");
    	changePricingEngine(); 

    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_DRG_USER_RATES", "");
    	int rowCount = oExcelData.getRowCount1("PPS_DRG_USER_RATES", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS DRG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		clickAddButton("Add User Rate Set Button", "User Rate Set Window", oPPSLibrary.AddUserRateSetButton, oPPSLibrary.UserRateSetWindow);
    		addUserRateSetDetails(String.valueOf(i));
    		
    		Add_DRG_UserRateSetEntry(String.valueOf(i));
    		
    		addPeriod(String.valueOf(i));
    	}
    }
    
	
	By GroupingOptions_TerminationDate = By.xpath("//div[@model='grouperOptionsSet.stopDate']//input[@id='stopDateDrgGrouperoptionSet']");
	
	By SelectGrouperOptionSet = By.xpath("//input[@id='copyAddGrouperOptionSet']");
	
	By SelectPeriodCopy = By.xpath("//select[@id='copyPeriod']");
	
	By AddDRGGrouperOptionSet = By.xpath("//div[@title='Add DRG Grouper Option Set']");
	
	By AddAPCGrouperOptionsSet = By.xpath("//div[@title='Add APC Grouper Options Set']");
	
	By APCGrouperOptionTerminationSet = By.xpath("//input[@id='stopDateApcGrouperoptionSet']");
	
	
	public void addDRGGrouperOptionSetDetails(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By GrouperOptionName = null;
		By Effective = null;
		By Termination = null;
		By GrouperName = null;
		By CopyExistingGrouperOptionSet = null;
		By SelectGrouperOptionValue = null;
		By SelectPeriodValue = null;
		
		String GrouperOption = null;
		String EffectiveDate = null;
		String TerminationDate = null;
		String GrouperNameFeild = null;
		String ExistingGrouperOptionSet = null;
		String GrouperOptionSetFeild = null;
		String periodToCopy = null;
		
		
		
		if(IsDisplayed("Add Grouper Option Set", AddDRGGrouperOptionSet))
		{
			 GrouperOptionName = oPPSLibrary.grouperOptionSetName;
			 Effective = oPPSLibrary.GO_effectiveDate;
			 Termination = GroupingOptions_TerminationDate;
			 GrouperName = oPPSLibrary.selectDRGGrouperName;
			 CopyExistingGrouperOptionSet = oPPSLibrary.checkBox;
			 SelectGrouperOptionValue = SelectGrouperOptionSet;
			 SelectPeriodValue = SelectPeriodCopy;
			 
			 GrouperOption = "GROUPER_OPTION_SET_NAME";
			 EffectiveDate = "EFFECTIVE_DATE";
			 TerminationDate = "TERMINATION_DATE";
			 GrouperNameFeild = "DRG_GROUPER_NAME";
			 ExistingGrouperOptionSet = "COPY_DETAILS_FROM_EXISTING_GROUPER_OPTION_SET";
			 GrouperOptionSetFeild = "SELECT_GROUPER_OPTION_SET";
			 periodToCopy = "SELECT_PERIOD_TO_COPY";
		}
		else if(IsDisplayed("Add APC Grouper Options Set", AddAPCGrouperOptionsSet))
		{
			GrouperOptionName = oPPSLibrary.grouperOptionSetName;
			Effective = oPPSLibrary.addAPCGrouperStartDate;
			Termination = APCGrouperOptionTerminationSet;
			GrouperName = oPPSLibrary.grouperNameDropdown;
			CopyExistingGrouperOptionSet = oPPSLibrary.copyFromExistingCheckBox;
			SelectGrouperOptionValue = oPPSLibrary.selectGrouperOptionsSB;
			SelectPeriodValue = oPPSLibrary.periodtoCopyDD;
			
			GrouperOption = "GROUPER_OPTION_SET_NAME";
			EffectiveDate = "EFFECTIVE_DATE";
			TerminationDate = "TERMINATION_DATE";
			GrouperNameFeild = "APC_APG_GROUPER_NAME";
			ExistingGrouperOptionSet = "COPY_DETAILS_FROM_EXISTINGGROUPER_OPTION_SET";
			GrouperOptionSetFeild = "SELECT_GROUPER_OPTION_SET";
			periodToCopy = "SELECT_PERIOD_TO_COPY";
		}
		
		enter_text_value("Grouper Option Set Name", GrouperOptionName, oParameters.GetParameters(""+GrouperOption+""+i));
		
		facilityName("PPS", "Apollo srn facility");
		
		enter_text_value("Effective Date", Effective, oParameters.GetParameters(""+EffectiveDate+""+i));
		performKeyBoardAction("ENTER");

		if(!oParameters.GetParameters(""+TerminationDate+""+i).isEmpty())
			enter_text_value("Termination Date", Termination, oParameters.GetParameters(""+TerminationDate+""+i));
		
		selectOption(GrouperName, "visibletext", oParameters.GetParameters(""+GrouperNameFeild+""+i));
		
		if(!oParameters.GetParameters(""+ExistingGrouperOptionSet+""+i).isEmpty())
		{
			click_button("Copy Details form Existing Grouper Option Set", CopyExistingGrouperOptionSet);
			
			enter_text_value("Select Grouper Option Set", SelectGrouperOptionValue, oParameters.GetParameters(""+GrouperOptionSetFeild+""+i));
			performKeyBoardAction("ENTER");
			
			selectOption(SelectPeriodValue, "value", oParameters.GetParameters(""+periodToCopy+""+i));
		}
			
		click_button("Add Grouper Option Window Save Button", oPPSLibrary.saveButton);
	}
	
	
	By agencyIndicator = By.xpath("//td[text()='Agency Indicator']");
	
	By BirthWeightOption = By.xpath("//td[text()='Birth Weight Option']");
	
	By HACVersion = By.xpath("//td[text()='HAC Version']");
	
	By SuppressHACCateegories = By.xpath("//td[text()='Suppress HAC Categories']");
	
	By saveButton = By.xpath("//div[@id='editDrgOptionsEntry']//button[@value='Save']");
	
	public void attributesDetails(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] Attributes = { "AGENCY_INDICATOR","BIRTH_WEIGHT_OPTION","HAC_VERSION","SUPPRESS_HAC_CATEGORIES" };
		
		ArrayList<By> oarraylist1 = new ArrayList<>();
		
		oarraylist1.add(agencyIndicator);
		oarraylist1.add(BirthWeightOption);
		oarraylist1.add(HACVersion);
		oarraylist1.add(SuppressHACCateegories);
		
		
		for(int j=0;j<Attributes.length;j++)
		{	
			if(!oParameters.GetParameters(""+Attributes[j]+""+i).isEmpty())
			{	
				fixed_wait_time(2);
				click_button(""+Attributes[j]+"", oarraylist1.get(j));
			
				By Values = By.xpath("//p[contains(.,'"+oParameters.GetParameters(""+Attributes[j]+""+i)+"')]//input");
			
				click_button("Selecting Values for Attributes", Values);
				
				click_button("Attributes Details Save button", saveButton);
			}
		}
	}
	
	By CopyDetailsfromExistingGrouperOptions = By.xpath("//form[@id='addDrgGrouperOptionPeriodForm']//input[@id='copyClosed']");
	
	By SelectGrouperOptionsSet = By.xpath("//input[@id='copyGrouperOptionSet']");
	
	public void GroupingOptions_addPeriod(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i).isEmpty())
		{
			click_button("Period Drop Down", periodDropDown);
			
			click_button("Add Perion", addPeriod);
			
			enter_text_value("Effective Date", oPPSLibrary.addNewEffectivePeriod, oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i));
			
			if(!oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i).isEmpty())
				enter_text_value("Termination Date", terminationDate, oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i));
			
			if(!oParameters.GetParameters("PERIOD_COPY_DETAILS_FROM_EXISTING_GROUPER_OPTION_SET"+i).isEmpty())
			{	
				click_button("Copy Details fromExisting Grouper Option Set", CopyDetailsfromExistingGrouperOptions);
			
				enter_text_value("Select Grouper Option Set", SelectGrouperOptionsSet, oParameters.GetParameters("PERIOD_SELECT_GROUPER_OPTION_SET"+i));
				performKeyBoardAction("ENTER");
				
				if(IsDisplayed("User Rate Set Details", FirstDRGUserRateSetDetails))
					click_button("Frist User Rate Set Details", FirstDRGUserRateSetDetails);
				
				selectOption(SelectPeriodCopy, "value", oParameters.GetParameters("PERIOD_SELECT_PERIOD_TO_COPY"+i));
			}	
			
			click_button("Period Save Button", periodSaveButton);
		}
	}
	
	//PPS DRG Grouper Options Build.
	public void Create_PPS_DRG_Grouper_Options_Build()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_DRG_GROUPER_OPTIONS", "");
    	int rowCount = oExcelData.getRowCount1("PPS_DRG_GROUPER_OPTIONS", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS DRG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	navigate_to("Grouper Options", "Grouper Option page validation", oPPSLibrary.grouperOption, oPPSLibrary.OpenPageValidation);
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		clickAddButton("Add Grouper Option Set Button", "Add DRG Grouper OPtion Set Window", oPPSLibrary.addGrouperOptionLink, oPPSLibrary.addGrouperOptionWindow);
    		
    		addDRGGrouperOptionSetDetails(String.valueOf(i));
    		attributesDetails(String.valueOf(i));
    		GroupingOptions_addPeriod(String.valueOf(i));
    	}
	}
	
	By ProviderValue_TerminationDate = By.xpath("//div[@model='providerValuesSet.stopDate']//input[@id='stopDateProvidervaluesSet']");
	
	By APCAPG_TerminationDate = By.xpath("//div[@model='providerValuesSet.stopDate']//input[@id='stopDateSet']");
	
	By APCAPG_ProviderValueSetSB = By.xpath("//div[@form-id='masterSetFormModel.formId']//input[@id='providerValuesSearch']");
	
	By ProviderValue_CopyExisting = By.xpath("//input[@id='copyClosedAddSet']");
	
	By SelectPeriodToCopy = By.xpath("//select[@id='copyPeriodInSet']");
	
	By CopyExistingProviderValue_PricerVersion = By.xpath("//select[@id='copyPeriodSelectVersion']");
	
	By ProviderSaveButton = By.xpath("//div[@id='addProviderValuesSetModal']//input[@value='Save']");
	
	public void PPS_DRG_addProviderValuesSetDetails(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By ProviderValuesSet = null;
		By EffectiveDate = null;
		By TerminationDate = null;
		By UserRate = null;
		By CopyExistingProviderValueSet = null;
		By ProviderValueSet_SearchBox = null;
		By FristElement = null;
		By PeriodCopy = null;
		By CopyExistingPricerVersion = null;
		
		String ProviderValueSetName = null;
		String Effective = null;
		String Termination = null;
		String ProviderValueStatus = null;
		String CopyExistingProviderValue = null;
		String ProviderValueName = null;
		String SelectPeriod  = null;
		String PricerVersion = null;
		
		if(oParameters.GetParameters("PPSTYPE").equals("PPS DRG"))
		{
			ProviderValuesSet = oPPSLibrary.providerValueSetName;
			EffectiveDate = oPPSLibrary.effectiveDate_PV;
			TerminationDate = ProviderValue_TerminationDate;
			UserRate = UserRateStatus;
			CopyExistingProviderValueSet = ProviderValue_CopyExisting;
			ProviderValueSet_SearchBox = oPPSLibrary.selectProviderValueSet;
			FristElement = oPPSLibrary.selectFirstElement;
			PeriodCopy = SelectPeriodToCopy;
			CopyExistingPricerVersion = CopyExistingProviderValue_PricerVersion;
			
			 ProviderValueSetName = "DRG_PROVIDER_VALUES_SET_NAME";
			 Effective = "EFFECTIVE_DATE";
			 Termination = "TERMINATION_DATE";
			 ProviderValueStatus = "PROVIDER_VALUE_STATUS";
			 CopyExistingProviderValue = "COPY_DETAILS_FROM_EXISTING_PROVIDER_VALUE_SET";
			 ProviderValueName = "SELECT_PROVIDER_VALUE_SET";
			 SelectPeriod  = "SELECT_PERIOD_TO_COPY";
			 PricerVersion = "PRICER_VERSION";
		}
		else if(oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
		{
			ProviderValuesSet = oPPSLibrary.nameTextbox;
			EffectiveDate = oPPSLibrary.addProviderValuesStartDate;
			TerminationDate = APCAPG_TerminationDate;
			UserRate = oPPSLibrary.providerValueStatusDD;
			CopyExistingProviderValueSet = ProviderValue_CopyExisting;
			ProviderValueSet_SearchBox = APCAPG_ProviderValueSetSB;
			FristElement = oPPSLibrary.selectFirstElement;
			PeriodCopy = SelectPeriodToCopy;
			CopyExistingPricerVersion = CopyExistingProviderValue_PricerVersion;
			
			 ProviderValueSetName = "PROVIDER_VALUES_NAME";
			 Effective = "EFFECTIVE_DATE";
			 Termination = "TERMINATION_DATE";
			 ProviderValueStatus = "PROVIDER_VALUE_SET";
			 CopyExistingProviderValue = "COPY_DETAILS_FROM_EXISTING_PROVIDER_VALUE_SET";
			 ProviderValueName = "SELECT_PROVIDER_VALUE_SET";
			 SelectPeriod  = "SELECT_PERIOD_TO_COPY";
			 PricerVersion = "PRICER_VERSION";
		}
		
		
		enter_text_value("Provider Values Set Name",ProviderValuesSet, oParameters.GetParameters(""+ProviderValueSetName+""+i));
		
		enter_text_value("Effective Date", EffectiveDate, oParameters.GetParameters(""+Effective+""+i));
		performKeyBoardAction("ENTER");
		
		facilityName("PPS", "Apollo srn facility");
		
		if(!oParameters.GetParameters(""+Termination+""+i).isEmpty())
		{
			enter_text_value("Termination Date", TerminationDate, oParameters.GetParameters(""+Termination+""+i));
			performKeyBoardAction("ENTER");
		}
		
		if(!oParameters.GetParameters(""+ProviderValueStatus+""+i).isEmpty())
			selectOption(UserRate,"visibletext",oParameters.GetParameters(""+ProviderValueStatus+""+i));
		
		if(!oParameters.GetParameters(""+CopyExistingProviderValue+""+i).isEmpty())
		{	
			click_button("Copy Details From Existing Provider Value Set", CopyExistingProviderValueSet);
		
			fixed_wait_time(2);
			
			enter_text_value("Select Provider Value Set", ProviderValueSet_SearchBox, oParameters.GetParameters(""+ProviderValueName+""+i));
			performKeyBoardAction("ENTER");
			
			if(IsDisplayed("Select User Rate Set", FristElement))
				click_button("Select first element", FristElement);
			
			if(!oParameters.GetParameters(""+SelectPeriod+""+i).isEmpty())
				selectOption(PeriodCopy,"value",oParameters.GetParameters(""+SelectPeriod+""+i));
			
			if(!oParameters.GetParameters(""+PricerVersion+""+i).isEmpty())
				selectOption(CopyExistingPricerVersion,"visibletext",oParameters.GetParameters(""+PricerVersion+""+i));
				
			click_button("Add Provider Value Set Save Button", ProviderSaveButton);
		}
		
		ProviderValues_PricerNameDetails(String.valueOf(i));
		
		click_button("Add Provider Value Set Save Button", ProviderSaveButton);
	}
	
	
	By addproviderValuesSet = By.xpath("//div[@title='Add Provider Values Set']");
	
	By addEffectivePeriod = By.xpath("//div[@title='Add Effective Period']");
	
	public void ProviderValues_PricerNameDetails(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By PricerName = null;
		By PricerVersion = null;
		By Scheme = null;
		By State = null;
		By carrier = null;
		By locality = null;
		By locality1 = null;
		
		String PRICER_N = null;
		String PRICER_V = null;
		String PRICER_S = null;
		String PRICER_State = null;
		String Pricer_Carrier = null;
		String Pricer_Locality = null;
		
		if(oParameters.GetParameters("PPSTYPE").equals("PPS DRG"))
		{
			if(!oParameters.GetParameters("PRICER_NAME"+i).isEmpty())
			{	
				if(IsDisplayed("Add Provider Values Set", addproviderValuesSet))
				{
					PricerName = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='pricerName']");
					PricerVersion = By.xpath("//form[@id='addProviderValuesSet']//select[@id='pricerVersion']");
					Scheme = By.xpath("//form[@id='addProviderValuesSet']//select[@id='scheme']");
				 
					PRICER_N = "PRICER_NAME";
					PRICER_V = "PRICER_VERSION";
					PRICER_S = "PRICER_SCHEME";
				}
				else if(IsDisplayed("Add Effective Period", addEffectivePeriod))
				{
					PricerName = By.xpath("//select[@id='pricerName']");
					PricerVersion = By.xpath("//select[@id='pricerVersion']");
					Scheme = By.xpath("//select[@id='scheme']");
				
					PRICER_N = "PERIOD_PRICER_NAME";
					PRICER_V = "PERIOD_PRICER_VERSION";
					PRICER_S = "PERIOD_PRICER_SCHEME";
				}
			}	
		}
		else if(oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
		{
			if(!oParameters.GetParameters("PRICER_NAME"+i).isEmpty())
			{
				if(IsDisplayed("Add Provider Values Set", addproviderValuesSet))
				{
					PricerName = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='pricerName']");
					PricerVersion = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='pricerVersion']");
					Scheme = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='scheme']");
					State = By.xpath("//div[@form-id='masterSetFormModel.formId']//select[@id='state']");
					carrier = By.xpath("//div[@form-id='masterSetFormModel.formId']//input[@id='carrier']");
					locality = By.xpath("//div[@form-id='masterSetFormModel.formId']//input[@id='locality']");
					
					
					PRICER_N = "PRICER_NAME";
					PRICER_V = "PRICER_VERSION";
					PRICER_S = "PRICER_SCHEME";
					PRICER_State = "STATE";
					Pricer_Carrier = "CARRIER";
					Pricer_Locality = "LOCALITY";
				}
				else if(IsDisplayed("Add Effective Period", addEffectivePeriod))
				{
					PricerName = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='pricerName']");
					PricerVersion = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='pricerVersion']");		
					Scheme = By.xpath("//div[@form-id='periodFormModel.formId']//select[@id='scheme']");
					
					PRICER_N = "PERIOD_PRICER_NAME";
					PRICER_V = "PERIOD_PRICER_VERSION";
					PRICER_S = "PERIOD_SCHEME";
					PRICER_State = "PERIOD_STATE";
					Pricer_Carrier = "PERIOD_CARRIER";
					Pricer_Locality = "PERIOD_LOCALITY";
				}
			}
		}
		
		selectOption(PricerName,"visibletext",oParameters.GetParameters(""+PRICER_N+""+i));
			
		if(oParameters.GetParameters(""+PRICER_N+""+i).equals("APR Pricing") || 	
				oParameters.GetParameters(""+PRICER_N+""+i).equals("CHAMPUS/TRICARE") ||
					oParameters.GetParameters(""+PRICER_N+""+i).equals("Medicare") || 
						oParameters.GetParameters(""+PRICER_N+""+i).equals("Medicare IP Psychiatric") ||
							oParameters.GetParameters(""+PRICER_N+""+i).equals("Medicare Long Term Care") ||
								oParameters.GetParameters(""+PRICER_N+""+i).equals("NY Medicaid,HMO,Workers Comp,No Fault") ||
									oParameters.GetParameters(""+PRICER_N+""+i).equals("EAPG") || 
										oParameters.GetParameters(""+PRICER_N+""+i).equals("New York EAPG") ||
											oParameters.GetParameters(""+PRICER_N+""+i).equals("TRICARE APC") ||
												oParameters.GetParameters(""+PRICER_N+""+i).equals("Generic User Rate"))
		{
			selectOption(PricerVersion,"visibletext",oParameters.GetParameters(""+PRICER_V+""+i));
			
			fixed_wait_time(2);
			
			if(oParameters.GetParameters(""+PRICER_N+""+i).equals("APR Pricing") || oParameters.GetParameters(""+PRICER_N+""+i).equals("EAPG"))
			{
				scroll("Scroll to next field", Scheme);
				selectOption(Scheme,"visibletext",oParameters.GetParameters(""+PRICER_S+""+i));
			}
			
			if(oParameters.GetParameters(""+PRICER_N+""+i).equals("Medicare") && oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
			{
				if(!oParameters.GetParameters(""+Pricer_Carrier+""+i).isEmpty())
					enter_text_value("Carrier Value", carrier, oParameters.GetParameters(""+Pricer_Carrier+""+i));
			}
		
			if(oParameters.GetParameters(""+PRICER_N+""+i).equals("Medicare") && oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG") || 
					oParameters.GetParameters(""+PRICER_N+""+i).equals("TRICARE APC") && oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
			{	
				selectOption(State,"visibletext",oParameters.GetParameters(""+PRICER_State+""+i));
				
				if(oParameters.GetParameters(""+PRICER_N+""+i).equals("Medicare") && oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
					locality1 = locality;
				else if(oParameters.GetParameters(""+PRICER_N+""+i).equals("TRICARE APC") && oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
					locality1 = By.xpath("//div[@form-id='masterSetFormModel.formId']//input[@id='localityTricare']");
				
				
				enter_text_value("Locality Value", locality1, oParameters.GetParameters(""+Pricer_Locality+""+i));
			}
		}
	}
	
	
	public void providerValues_Attributes(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] attributesValues = null;
		String attributeName = null;
		
		if(oParameters.GetParameters("PPSTYPE").equals("PPS DRG") || oParameters.GetParameters("PPSTYPE").equals("PPS CMG"))
		{
			attributesValues = oParameters.GetParameters("LABEL_VALUES"+i).split(",");
			attributeName = "LABEL_VALUES";
		}
		else if(oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
		{
			attributesValues = oParameters.GetParameters("SCHEME_VALUES"+i).split(",");
			attributeName = "SCHEME_VALUES";
		}
		
		if(!oParameters.GetParameters(""+attributeName+""+i).isEmpty())
		{
			for(int j=0;j<attributesValues.length;j++)
			{
				By attributesValue = By.xpath("//div[@id='addEditEntryParent']//tr["+(j+1)+"]//input[@model='kv']");
				
				fixed_wait_time(2);
			
				scroll("Scroll to next field", attributesValue);
				
				if(!attributesValues[j].isEmpty())
					enter_text_value("Pricer Value", attributesValue, attributesValues[j]);
			}
		
			fixed_wait_time(2);
		
			click_button("Pricer Value Save Button", oPPSLibrary.saveDetails);
		}		
	}
	
	By ProviderValue_CopyExisting_ProviderValueSet = By.xpath("//input[@id='copyClosedPeriod']");
	
	By SelectProviderValueSet = By.xpath("//input[@id='providerValuesSearch']");
	
	By AddEffectivePeriod_PricerVersion = By.xpath("//select[@id='selectVersion']");
	
	By AddPeriod_PPS_APC_APG = By.xpath("//div[@id='ppView']//a[@ng-click='addPeriod()']");
	
	By AddPeriod_PPS_APC_APG_TerminationDate = By.xpath("//input[@id='stopDateProviderValues']");
	
	public void providerValues_addPeriod(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By addperiodButton = null;
		By EffectiveDate = null;
		By TerminationDate = null;
		By CopyExistingProvider = null;
		By SelectProviderValueSetSB = null;
		By selectPeriod = null;
		By SelectPricerVersion = null;
		
		String EffectiveDateValue = null;
		String TerminationDateValue = null;
		String providerValueStatus = null;
		String CopyExsitingProivderValueSet = null;
		String ProviderValueSetSB = null;
		String SelectPeriodValue = null;
		String PricerVersionValue = null;
		
		
		if(oParameters.GetParameters("PPSTYPE").equals("PPS DRG"))
		{
			addperiodButton = addPeriod;
			EffectiveDate = oPPSLibrary.addNewEffectivePeriod;
			TerminationDate = terminationDate;
			CopyExistingProvider = ProviderValue_CopyExisting_ProviderValueSet;
			SelectProviderValueSetSB = SelectProviderValueSet;
			selectPeriod = SelectPeriodCopy;
			SelectPricerVersion = AddEffectivePeriod_PricerVersion;
			
			EffectiveDateValue = "PERIOD_EFFECTIVE_DATE";
			TerminationDateValue = "PERIOD_TERMINATION_DATE";
			providerValueStatus = "PERIOD_PROVIDER_VALUE_STATUS";
			CopyExsitingProivderValueSet = "PERIOD_COPY_DETAILS_FROM_EXISTING_PROVIDER_VALUE_SET";
			ProviderValueSetSB = "PERIOD_SELECT_PROVIDER_VALUE_SET";
			SelectPeriodValue = "PERIOD_SELECT_PERIOD_TO_COPY";
			PricerVersionValue = "PERIOD_PRICER_VERSION";
		}
		else if(oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
		{
			addperiodButton = AddPeriod_PPS_APC_APG;
			EffectiveDate = oPPSLibrary.addEffectivePeriodStartDate;
			TerminationDate = AddPeriod_PPS_APC_APG_TerminationDate;
			CopyExistingProvider = oPPSLibrary.copyFromExistingPPSSetCheckBox;
			SelectProviderValueSetSB = oPPSLibrary.selectProviderValueSetSB;
			selectPeriod = oPPSLibrary.selectPeriodtoCopyDD;
			SelectPricerVersion = oPPSLibrary.pricerVersionDD;
			
			EffectiveDateValue = "PERIOD_EFFECTIVE_DATE";
			TerminationDateValue = "PERIOD_TERMINATION_DATE";
			providerValueStatus = "PROVIDER_VALUE_STATUS";
			CopyExsitingProivderValueSet = "PERIOD_COPY_DETAILS_FROM_EXISTING_PROVIDER_VALUE_SET";
			ProviderValueSetSB = "PERIOD_SELECT_PROVIDER_SET";
			SelectPeriodValue = "PERIOD_SELECT_PERIOD_TO_COPY";
			PricerVersionValue = "PERIOD_PRICER_VERSION";
		}
		
		if(!oParameters.GetParameters(""+EffectiveDateValue+""+i).isEmpty())
		{
			click_button("Period Drop Down", periodDropDown);
			
			click_button("Add Period", addperiodButton);
			
			enter_text_value("Effective Date", EffectiveDate, oParameters.GetParameters(""+EffectiveDateValue+""+i));
			
			if(!oParameters.GetParameters(""+TerminationDateValue+""+i).isEmpty())
				enter_text_value("Termination Date", TerminationDate, oParameters.GetParameters(""+TerminationDateValue+""+i));
			
			if(!oParameters.GetParameters(""+providerValueStatus+""+i).isEmpty())
			{
				By UserRateStatus = By.xpath("//div[@form-id='periodFormModel.formId']//label[contains(.,'"+oParameters.GetParameters(""+providerValueStatus+""+i)+"')]//input");
				
				click_button("User Rate Status Radio Button", UserRateStatus);
			}
			
			if(!oParameters.GetParameters(""+CopyExsitingProivderValueSet+""+i).isEmpty())
			{
				click_button("Copy Details From Existing Provider Value Set", CopyExistingProvider);
				
				fixed_wait_time(2);
				
				if(!oParameters.GetParameters(""+ProviderValueSetSB+""+i).isEmpty())
				{	
					enter_text_value("Select Provider Value Set", SelectProviderValueSetSB, oParameters.GetParameters(""+ProviderValueSetSB+""+i)); 
					performKeyBoardAction("ENTER");
					
					if(IsDisplayed("User Rate Set Details", FirstDRGUserRateSetDetails))
						click_button("Frist User Rate Set Details", FirstDRGUserRateSetDetails);
					
					selectOption(selectPeriod, "value", oParameters.GetParameters(""+SelectPeriodValue+""+i));
					
					selectOption(SelectPricerVersion,"visibletext",oParameters.GetParameters(""+PricerVersionValue+""+i));
				}
				
				click_button("Period Save Button", periodSaveButton);
			}
			
			ProviderValues_PricerNameDetails(String.valueOf(i));
			
			click_button("Period Save Button", periodSaveButton);
			
			providerValues_AddPeriod_Attributes(String.valueOf(i));
		}	
	}
	
	
	//PPS DRG Provider Values Build.
	public void Create_PPS_DRG_Provider_Values_Build()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_DRG_PROVIDER_VALUES", "");
    	int rowCount = oExcelData.getRowCount1("PPS_DRG_PROVIDER_VALUES", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS DRG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	navigate_to("Provider Values", "Provider Values page validation", oPPSLibrary.providerValuesTab, oPPSLibrary.OpenPageValidation);
    	
    	waitFor(PPSType,"PPS TYPE");
    	
    	oParameters.SetParameters("PPSTYPE", get_field_value("PPS Type", PPSType));
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		clickAddButton("Provider Value Set", "Add Provider Value Set Window", oPPSLibrary.providerValueSetIcon, oPPSLibrary.addProviderValueWindow);
    		//oPPSLibrary.searchPPS("Search Provider Values", oParameters.GetParameters("DRG_PROVIDER_VALUES_SET_NAME"+i), oPPSLibrary.openedProviderValue, oPPSLibrary.searchedResult);
    		
    		PPS_DRG_addProviderValuesSetDetails(String.valueOf(i));
    		providerValues_Attributes(String.valueOf(i));
    		providerValues_addPeriod(String.valueOf(i));
    	}
	}
	
	
	By PPS_APC_AddGrouping_Termination = By.xpath("//div[@id='addGroupingDefinitionSetModal']//input[@id='stopDateApcGroupingDefinition']");
	
	By PPS_APC_AddGrouping_SelectPeriod = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='copyPeriod']");
	
	By PPS_APC_AddGrouping_FirstGrouingDefinition = By.xpath("//div[@form-id='masterSetFormModel.formId']//ul[@id='-list']/li[1]");
	
	public void addGroupingDefinitionSetDetails(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By GroupingDefinition = null;
		By EffectiveDate = null;
		By Temrination = null;
		By CopyExisitingGroupingDefinitionSet = null;
		By SelectGroupingDefinitionSet = null;
		By SelectPeriodCopy = null;
		By FirstGrouping = null;
		
		String GroupingDefintionNameDetails = null;
		String Effective = null;
		String Termination = null;
		String CopyExistingGroupingDefintion = null;
		String SelectGroupingDefinitionName = null;
		String SelectPeriodToCopy = null;
		
		
		if(oParameters.GetParameters("PPSTYPE").equals("PPS DRG"))
		{
			GroupingDefinition = oPPSLibrary.groupingDefinitionSetName;
			EffectiveDate = oPPSLibrary.effectiveDate_GD;
			Temrination = oPPSLibrary.terminationDate_GD;
			CopyExisitingGroupingDefinitionSet = oPPSLibrary.checkbox_GD;
			SelectGroupingDefinitionSet = oPPSLibrary.selectExistingGD;
			SelectPeriodCopy = oPPSLibrary.selectPeriodToCopy_GD;
			FirstGrouping = oPPSLibrary.selectFirstElement;
			
			GroupingDefintionNameDetails = "GROUPING_DEFINITION_SET_NAME";
			Effective = "EFFECTIVE_DATE";
			Termination = "TERMINATION_DATE";
			CopyExistingGroupingDefintion = "COPY_DETAILS_FROM_EXISTING_GROUPING_DEFINITION_SET";
			SelectGroupingDefinitionName = "SELECT_GROUPING_DEFINITION_SET";
			SelectPeriodToCopy = "SELECT_PERIOD_TO_COPY";
		}
		else if(oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
		{
			GroupingDefinition = oPPSLibrary.groupingDefinitionSetName;
			EffectiveDate = oPPSLibrary.groupingDefinitionStartDate;
			Temrination = PPS_APC_AddGrouping_Termination;
			CopyExisitingGroupingDefinitionSet = oPPSLibrary.copyFromExistedGroupingDefinitionsSet;
			SelectGroupingDefinitionSet = oPPSLibrary.selectGroupingDefinitionSetSB;
			SelectPeriodCopy = PPS_APC_AddGrouping_SelectPeriod;
			FirstGrouping = PPS_APC_AddGrouping_FirstGrouingDefinition;
			
			GroupingDefintionNameDetails = "GROUPING_DEFINITION_SET_NAME";
			Effective = "EFFECTIVE_DATE";
			Termination = "TERMINATION_DATE";
			CopyExistingGroupingDefintion = "COPY_DETAILS_FROM_EXISTING_GROUPING_DEFINITION_SET";
			SelectGroupingDefinitionName = "SELECT_GROUPING_DEFINITION_SET";
			SelectPeriodToCopy = "SELECT_PERIOD_TO_COPY";
		}
		
		
		enter_text_value("Grouping Definition Set Name", GroupingDefinition, oParameters.GetParameters(""+GroupingDefintionNameDetails+""+i));
		

		facilityName("PPS", "Apollo srn facility");
	
		enter_text_value("Effective Date", EffectiveDate, oParameters.GetParameters(""+Effective+""+i));
		performKeyBoardAction("ENTER");
		
		if(!oParameters.GetParameters(""+Termination+""+i).isEmpty())
		{
			enter_text_value("Termination Date", Temrination, oParameters.GetParameters(""+Termination+""+i));
			performKeyBoardAction("ENTER");
		}
		
		if(oParameters.GetParameters(""+CopyExistingGroupingDefintion+""+i).equals("Yes"))
		{
			click_button("Copy Details from Existing Grouping Definition Set", CopyExisitingGroupingDefinitionSet);
			
			enter_text_value("Select Grouping Deifinition Set", SelectGroupingDefinitionSet, oParameters.GetParameters(""+SelectGroupingDefinitionName+""+i));
			performKeyBoardAction("ENTER");
			
			if(IsDisplayed("First Grouping Definition Set", FirstGrouping))
				click_button("Clicked First Grouping Definition Set", FirstGrouping);
			
			selectOption(SelectPeriodCopy, "value", oParameters.GetParameters(""+SelectPeriodToCopy+""+i));
		}
		
		if(oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG"))
		{
			selectOption(oPPSLibrary.groupingDefinitionSetGrouperNameDD,"visibletext",oParameters.GetParameters("GROUPER_NAME"+i));				
			
			selectOption(oPPSLibrary.grouperDefinitionVersionDD,"visibletext",oParameters.GetParameters("GROUPER_VERSION"+i));
			
			if(!oParameters.GetParameters("APC_APG_GROUPER_OPTION_SET"+i).isEmpty())
				enter_text_value("APC/APG Grouper Option Set", oPPSLibrary.APCAPGGrouperOptionSetSB, oParameters.GetParameters("APC_APG_GROUPER_OPTION_SET"+i));
			
			if(!oParameters.GetParameters("APC_APG_PROVIDER_VALUES_SET"+i).isEmpty())
				enter_text_value("APC/APG Provider Values Set", oPPSLibrary.APCAPGProviderValuesSetSB, oParameters.GetParameters("APC_APG_PROVIDER_VALUES_SET"+i));
			
			if(!oParameters.GetParameters("ICD_VERSION_OVERRIDE"+i).isEmpty())
				selectOption(oPPSLibrary.ICDVersionDD,"visibletext",oParameters.GetParameters("ICD_VERSION_OVERRIDE"+i));
			
			click_button("Add Grouping Definition Set Save Button", oPPSLibrary.saveButton);
		}
	}
	
	
	By addDRGGroupingDefinitionSetWindow = By.xpath("//div[@title='Add DRG Grouping Definition Set']");
	
	By GroupingDefinition_addEffectivePeriod = By.xpath("//div[@title='Add Effective Period']");
	
	public void selctDRGGrouperName(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By DRGGrouperName = null;
		By DRGGrouperVersion = null;
		By DRGGrouperOptionSet = null;
		By DRGProviderValueSet =null;
		By MapICDCodesToVersionNumber = null;	
		By MapICDType = null;
		By DRGGrouperFirstOptionSet =null;
		By DRGProviderFirstValueSet = null;
		By saveButton = null;
		
		String GrouperName = null;
		String GrouperVarsion = null;
		String GrouperOptionSet = null;
		String ProviderValueSet = null;
		String ReassignmentMethod = null;
		String MapICDCodesVersionNumber = null;
		String ICDMapType = null;
		String HospitalAcquiredConditionalProcessing = null;
		String ReOrderImportance = null;
		
		
		//form[@id='addDrgGrouperPeriod']//input[@name='ReorderIndicatorNo']
		
		if(IsDisplayed("Add DRG Grouping Deifinition Set", addDRGGroupingDefinitionSetWindow))
		{	
			DRGGrouperName = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='drgGrouperName']");
			DRGGrouperVersion = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='drgGrouperVersion']");
			DRGGrouperOptionSet = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='drgGrouperOptionAdd']");
			DRGProviderValueSet = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='drgProviderValue']");
			MapICDCodesToVersionNumber = By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='icdVersion']");
			MapICDType= By.xpath("//form[@id='addGroupingDefinitionSet']//select[@id='icdMappingTypeCode']");
			DRGGrouperFirstOptionSet = By.xpath("//input[@id='drgGrouperOptionAdd']/..//ul[@id='-list']/li[1]");
			DRGProviderFirstValueSet = By.xpath("//input[@id='drgProviderValue']/..//ul[@id='-list']/li[1]");
			
			saveButton = oPPSLibrary.saveButton;
			GrouperName = "DRG_GROUPER_NAME";
			GrouperVarsion = "DRG_GROUPER_VERSION";		
			GrouperOptionSet = "DRG_GROUPER_OPTION_SET";
			ProviderValueSet = "DRG_PROVIDER_VALUE_SET";
			ReassignmentMethod = "REASSIGNMENT_METHOD";
			MapICDCodesVersionNumber = "MAP_ICD_CODES_TO_VERSION_NUMBER";
			ICDMapType = "ICD_MAP_TYPE";
			HospitalAcquiredConditionalProcessing = "USE_HOSPITAL_ACQUIRED_CONDITIONAL_PROCESSING";
			ReOrderImportance = "REORDER_BY_IMPORTANCE";
			
		}
		else if(IsDisplayed("Add Effective Period Window", GroupingDefinition_addEffectivePeriod))
		{
			DRGGrouperName = By.xpath("//form[@id='addDrgGrouperPeriod']//select[@id='drgGrouperName']");
			DRGGrouperVersion = By.xpath("//form[@id='addDrgGrouperPeriod']//select[@id='drgGrouperVersion']");
			DRGGrouperOptionSet = By.xpath("//form[@id='addDrgGrouperPeriod']//input[@id='drgGrouperOptionAdd']");
			DRGProviderValueSet = By.xpath("//form[@id='addDrgGrouperPeriod']//input[@id='drgProviderValue']");
			MapICDCodesToVersionNumber = By.xpath("//form[@id='addDrgGrouperPeriod']//select[@id='icdVersion']");
			MapICDType = By.xpath("//form[@id='addDrgGrouperPeriod']//select[@id='icdMappingTypeCode']");
			DRGGrouperFirstOptionSet = By.xpath("//div[@spec='periodFormModel.grouperOptionSetSearch.spec']//input[@id='drgGrouperOptionAdd']/..//ul[@id='-list']/li[1]");
			DRGProviderFirstValueSet = By.xpath("//div[@spec='periodFormModel.providerValuesSetSearch.spec']//input[@id='drgProviderValue']/..//ul[@id='-list']/li[1]");
			
			saveButton = oPPSLibrary.saveEffectivePeriod;
			GrouperName = "PERIOD_DRG_GROUPER_NAME";
			GrouperVarsion = "PERIOD_PERIOD_DRG_GROUPER_VERSION";		
			GrouperOptionSet = "PERIOD_DRG_GROUPER_OPTION_SET";
			ProviderValueSet = "PERIOD_DRG_PROVIDER_VALUE_SET";
			ReassignmentMethod = "PERIOD_REASSIGNMENT_METHOD";
			MapICDCodesVersionNumber = "PERIOD_MAP_ICD_CODES_TO_VERSION_NUMBER";
			ICDMapType = "PERIOD_ICD_MAP_TYPE";
			HospitalAcquiredConditionalProcessing = "PERIOD_USE_HOSPITAL_ACQUIRED_CONDITIONAL_PROCESSING";
			ReOrderImportance = "PERIOD_REORDER_BY_IMPORTANCE";
			
		}
		
		selectOption(DRGGrouperName,"visibletext",oParameters.GetParameters(""+GrouperName+""+i));
		
		selectOption(DRGGrouperVersion,"visibletext",oParameters.GetParameters(""+GrouperVarsion+""+i));
		
		if(!oParameters.GetParameters(""+GrouperOptionSet+""+i).isEmpty())
		{
			enter_text_value("DRG Grouper Option Set", DRGGrouperOptionSet, oParameters.GetParameters(""+GrouperOptionSet+""+i));
			performKeyBoardAction("ENTER");
			
			if(IsDisplayed("First Grouper Option Set", DRGGrouperFirstOptionSet))
				click_button("DRG First Grouper Option Set", DRGGrouperFirstOptionSet);
		}
		
		if(!oParameters.GetParameters(""+ProviderValueSet+""+i).isEmpty())
		{
			enter_text_value("DRG Provider Value Set", DRGProviderValueSet, oParameters.GetParameters(""+ProviderValueSet+""+i));
			performKeyBoardAction("ENTER");
			
			if(IsDisplayed("Firsrt Provider Value Set", DRGProviderFirstValueSet) )
				click_button("DRG First Provider Value Set", DRGProviderFirstValueSet);
		}
		
		if(oParameters.GetParameters(""+GrouperName+""+i).equals("Medicare") && !oParameters.GetParameters(""+ReassignmentMethod+""+i).isEmpty())
			selectOption(oPPSLibrary.reassignmentMethod,"visibletext",oParameters.GetParameters(""+ReassignmentMethod+""+i));
	
		if(!oParameters.GetParameters(""+HospitalAcquiredConditionalProcessing+""+i).isEmpty())
		{
			//By	AcquiredConditionalProcessing = By.xpath("//div[@id='addGroupingDefinitionSetModal']//label[contains(.,'Use Hospital Acquired Conditional Processing :')]/..//div[contains(text(),'"+oParameters.GetParameters(""+HospitalAcquiredConditionalProcessing+""+i)+"')]//input");
			By AcquiredConditionalProcessing = By.xpath("//div[@id='addGroupingDefinitionSetModal']//label[contains(.,'Use Hospital Acquired Conditional Processing :')]/..//div[contains(.,'"+oParameters.GetParameters(""+HospitalAcquiredConditionalProcessing+""+i)+"')]//input");
			click_button("Use Hospital Acquired Conditional Processing", AcquiredConditionalProcessing);
		}
			
		if(!oParameters.GetParameters(""+MapICDCodesVersionNumber+""+i).isEmpty())
			selectOption(MapICDCodesToVersionNumber,"visibletext",oParameters.GetParameters(""+MapICDCodesVersionNumber+""+i));
		
		if(!oParameters.GetParameters(""+ICDMapType+""+i).isEmpty())
			selectOption(MapICDType,"visibletext",oParameters.GetParameters(""+ICDMapType+""+i));
			
		if(!oParameters.GetParameters(""+ReOrderImportance+""+i).isEmpty())
		{
			By ReOrderByImportance = null;
			
			if(IsDisplayed("Add DRG Grouping Deifinition Set", addDRGGroupingDefinitionSetWindow))
				ReOrderByImportance = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@name='ReorderIndicator"+oParameters.GetParameters(""+ReOrderImportance+""+i)+"']");
			else if(IsDisplayed("Add Effective Period Window", GroupingDefinition_addEffectivePeriod)) 
				ReOrderByImportance = By.xpath("//form[@id='addDrgGrouperPeriod']//input[@name='ReorderIndicatorNo']");
				
			click_button("Re-Order By Importance", ReOrderByImportance);
		}
		
		click_button("Add DRG Grouping Definition Save Button", saveButton);
		
		fixed_wait_time(3);
	}
	
	By TerminationDate = By.xpath("//input[@id='stopDate']");
	
	By ExistingGroupingDefinitionSet_AddEffectivePeriod = By.xpath("//form[@id='addDrgGrouperPeriod']//input[@id='copyClosed']");
	
	By selectGrouingDefinitionSet_AddEffectivePeriod = By.xpath("//form[@id='addDrgGrouperPeriod']//input[@id='copyGroupingDefinitionSet']");
	
	By firstGroupingSet_AddEffectivePeriod = By.xpath("//form[@id='addDrgGrouperPeriod']//input[@id='copyGroupingDefinitionSet']/..//ul[@id='-list']/li[1]");
	
	By SelectPeriodToCopy_AddEffectivePeriod = By.xpath("//form[@id='addDrgGrouperPeriod']//select[@id='copyPeriod']");
	
	public void GroupingDefinition_addPeriod(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		if(!oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i).isEmpty())
		{
			click_button("Period DropDown", oPPSLibrary.effectivePeriodDD_GD);
		
			click_button("Add Period Button", oPPSLibrary.addPeriod_GD);
			fixed_wait_time(2);
		
			enter_text_value("Effective Date", oPPSLibrary.addNewEffectivePeriod, oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i));
			performKeyBoardAction("ENTER");
		
			if(!oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i).isEmpty())
			{
				enter_text_value("Termination Date", TerminationDate, oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i));
				performKeyBoardAction("ENTER");
			}
		
			if(!oParameters.GetParameters("PERIOD_COPY_DETAILS_FROM_EXISTING_GROUPING_DEFINITION_SET"+i).isEmpty())
			{
				click_button("Copy Detials Existing Grouping Definition Set", ExistingGroupingDefinitionSet_AddEffectivePeriod);
			
				enter_text_value("Select Grouping Definition Set", selectGrouingDefinitionSet_AddEffectivePeriod, oParameters.GetParameters("PERIOD_SELECT_GROUPING_DEFINITION_SET"+i));
				performKeyBoardAction("ENTER");
			
				if(IsDisplayed("First Grouping Definition Set", firstGroupingSet_AddEffectivePeriod))
					click_button("Selected Grouping Definition Set", firstGroupingSet_AddEffectivePeriod);
			
				selectOption(SelectPeriodToCopy_AddEffectivePeriod, "value", oParameters.GetParameters("PERIOD_SELECT_PERIOD_TO_COPY"+i));
			}
		
			selctDRGGrouperName(String.valueOf(i));
		}	
	}
	
	//PPS DRG Grouping Definition Build.
	public void Create_PPS_DRG_GroupingDefinitions()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_DRG_GROUPING_DEFINITIONS", "");
    	int rowCount = oExcelData.getRowCount1("PPS_DRG_GROUPING_DEFINITIONS", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS DRG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	navigate_to("Grouping Definition", "Grouping Definition page validation", oPPSLibrary.groupingDefinitionsTab, oPPSLibrary.OpenPageValidation);
    	
    	oParameters.SetParameters("PPSTYPE", get_field_value("PPS Type", PPSType));
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		clickAddButton("Add Grouper Definition icon", "Add Grouper Definition window", oPPSLibrary.addGrouperDefinitionIcon, oPPSLibrary.addGrouperDefinitionWindow);
    		
    		addGroupingDefinitionSetDetails(String.valueOf(i));
    		
    		selctDRGGrouperName(String.valueOf(i));
    		
    		GroupingDefinition_addPeriod(String.valueOf(i));
    	}
	}
	
	By EffectiveDate_DRGGroupingRuleSet = By.xpath("//div[@id='addGroupingRuleSetModal']//input[@id='startDate']");
	
	By StartTimer = By.xpath("//div[@id='addGroupingRuleSetModal']//input[@id='startTimer']");
	
	By  StopTimer = By.xpath("//div[@id='addGroupingRuleSetModal']//input[@id='stoptTimer']");
	
	By TerminationDate_DRGGroupingRuleSet = By.xpath("//div[@id='addGroupingRuleSetModal']//input[@id='stopDate']");
	
	By Description_DRGGroupingRuleSet = By.xpath("//div[@id='addGroupingRuleSetModal']//input[@id='description']");
	
	By SelectPeriodCopy_DRGGroupingRuleSet = By.xpath("//div[@id='addGroupingRuleSetModal']//select[@id='copyPeriod']");
	
	By CopyExistingGroupingRoleSet = By.xpath("//form[@id='addDrgGroupingRuleSet']//input[@name='copyGroupingRuleSet']");
	
	By SaveButton_DRGGroupingRuleSet = By.xpath("//div[@id='addGroupingRuleSetModal']//input[@value='Save']");
	
	By addDRGGroupingRuleSetWindow = By.xpath("//div[@title='Add DRG Grouping Rule Set']");
	
	By addPPSAPCAPGGroupingRuleSet = By.xpath("//div[@title='Add PPS APC/APG Grouping Rule Set']");
	
	By selectPeriod_APCAPGGroupingRuleSet = By.xpath("//form[@id='addppsAPCGGroupingRuleSet']//select[@id='copyPeriod']");
	
	public void addDRGGroupingRuleSet(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By EffectiveDateFeild = null;
		By TerminationDateFeild = null;
		By StartTimerFeild = null;
		By StopTimerFeild = null;
		By DescriptionFeild = null;
		By CopyExistingRuleSetCheckBox = null;
		By SelectPeriodFeild = null;
		By SaveButtonFeild = null;
		
		String EffectiveDateValue = null;
		String TerminationDateValue = null;
		String StartTimerValue  = null;
		String StopTimerValue = null;
		String DescriptionValue = null;
		String ExistingRuleSet = null;
		String PeriodCopy = null;
		
		
		if(IsDisplayed("Add DRG Grouping Rule Set", addDRGGroupingRuleSetWindow))
		{
			EffectiveDateFeild = EffectiveDate_DRGGroupingRuleSet;
			TerminationDateFeild = TerminationDate_DRGGroupingRuleSet;
			StartTimerFeild = StartTimer;
			StopTimerFeild = StopTimer;
			DescriptionFeild = Description_DRGGroupingRuleSet;
			CopyExistingRuleSetCheckBox = CopyExistingGroupingRoleSet;
			SelectPeriodFeild = SelectPeriodCopy_DRGGroupingRuleSet;
			SaveButtonFeild = SaveButton_DRGGroupingRuleSet;
			
			 EffectiveDateValue = "EFFECTIVE_DATE";
			 TerminationDateValue = "TERMINATION_DATE";
			 StartTimerValue  = "START_TIME";
			 StopTimerValue = "STOP_TIME";
			 DescriptionValue = "DESCRIPTION";
			 ExistingRuleSet = "COPY_DETAILS_FROM_EXISTING_GROUPING_RULE_SET";
			 PeriodCopy = "SELECT_PERIOD_TO_COPY";
		}
		else if(IsDisplayed("Add APC/APG Grouping Rule Set", addPPSAPCAPGGroupingRuleSet))
		{
			EffectiveDateFeild = oPPSLibrary.ruleSetStartDate;
			TerminationDateFeild = oPPSLibrary.ruleSetStopDate;
			StartTimerFeild = oPPSLibrary.ruleSetStartTime;
			DescriptionFeild = oPPSLibrary.ruleSetDescription;
			CopyExistingRuleSetCheckBox = oPPSLibrary.copyFromExistingGroupingRuleSet;
			SelectPeriodFeild = selectPeriod_APCAPGGroupingRuleSet;
			SaveButtonFeild = saveButton;
			
			 EffectiveDateValue = "EFFECTIVE_DATE";
			 TerminationDateValue = "TERMINATION_DATE";
			 StartTimerValue  = "START_TIME";
			 DescriptionValue = "DESCRIPTION";
			 ExistingRuleSet = "COPY_DETAILS_FROM_EXISTING_RULE_SET";
			 PeriodCopy = "SELECT_PERIOD_TO_COPY";
		}
		
		
		if(!oParameters.GetParameters(""+EffectiveDateValue+""+i).isEmpty())
		{
			enter_text_value("Add DRG Grouping Rule Set Effective Date", EffectiveDateFeild, oParameters.GetParameters(""+EffectiveDateValue+""+i));
			performKeyBoardAction("ENTER");
		
			if(!oParameters.GetParameters(""+StartTimerValue+""+i).isEmpty())
			{	
				enter_text_value("Start Timer", StartTimerFeild, oParameters.GetParameters(""+StartTimerValue+""+i));
				performKeyBoardAction("ENTER");
			}
		}
		
		
		if(!oParameters.GetParameters(""+TerminationDateValue+""+i).isEmpty())
		{	
			enter_text_value("Add DRG Grouping Rule Set Termination Date", TerminationDateFeild, oParameters.GetParameters(""+TerminationDateValue+""+i));
			performKeyBoardAction("ENTER");
			
			if(!oParameters.GetParameters(""+StopTimerValue+""+i).isEmpty())
			{	
				enter_text_value("Stop Timer", StopTimerFeild, oParameters.GetParameters(""+StopTimerValue+""+i));
				performKeyBoardAction("ENTER");
			}
		}
		
		if(!oParameters.GetParameters(""+DescriptionValue+""+i).isEmpty())
			enter_text_value("Description", DescriptionFeild, oParameters.GetParameters(""+DescriptionValue+""+i));
			
		if(!oParameters.GetParameters(""+ExistingRuleSet+""+i).isEmpty())
		{	
			click_button("Copy Details from Existing Grouping Rule Set", CopyExistingRuleSetCheckBox);
			
			selectOption(SelectPeriodFeild,"value",oParameters.GetParameters(""+PeriodCopy+""+i));
		}
		
		click_button("Add DRG Grouping Rule Set", SaveButtonFeild);
	}
	
	By addingRuleAfterBefore = By.xpath("//form[@id='addDrgGroupingRuleSetRules']//span[contains(@class,'dropdown-text hide-overflow ng-binding')]/..");
	
	By addingRuleAfterBefore_Value = By.xpath("//form[@id='addDrgGroupingRuleSetRules']//input[@id='rulePlacement']");
	
	By addQualifierButton = By.xpath("//button[contains(.,'Add Qualifier')]");
	
	By QualificationGroupName = By.xpath("//div[@id='ppView']//form[@id='addQualExpressionBuilder']//input[@id='expression-name']");
	
	By QualificationGroupBuildResult = By.xpath("//div[@id='ppView']//form[@id='addQualExpressionBuilder']//textarea[@id='expression']");
	
	By ValidateQualificationGroupBuild = By.xpath("//div[@id='ppView']//form[@id='addQualExpressionBuilder']//button[@id='validationButton']");
			
	By AddQualificationGroup_SaveButton = By.xpath("//div[@id='ppView']//div[@id='fullFooter']//input[@id='saveQgroupId']");
	
	By GroupingDefinition = By.xpath("//div[@id='ppView']//div[@form-id='entryFormModel.formId']//input[@id='groupingDefinition']");
	
	By FirstGroupingDefinition = By.xpath("//div[@id='ppView']//div[@form-id='entryFormModel.formId']//ul[@id='-list']/li[1]");
	
	By AddRule_SaveButton = By.xpath("//div[@id='addDrgGroupingRuleSetRulesModal']//input[@value='Save']");
	
	public void addRule(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] GroupingRule = oParameters.GetParameters("GROUPING_RULE_NAME"+i).split(",");
		
		String[] AddingRuleAfterBefore = oParameters.GetParameters("ADDING_RULE_AFTER_BEFORE"+i).split(",");
		
		String[] AddingRuleAfterBefore_Value = oParameters.GetParameters("ADDING_RULE_AFTER_BEFORE_VALUE"+i).split(",");
		
		String[] QualificationGroupName_Value = oParameters.GetParameters("QUALIFICATION_GROUP_NAME"+i).split(",");
		
		String[] Expression_Value = oParameters.GetParameters("EXPRESSION"+i).split(",");
		
		String[] GroupingDefinition_Value = oParameters.GetParameters("GROUPING_DEFINITION"+i).split(",");
		
		
		for(int j=0;j<GroupingRule.length;j++)
		{
			fixed_wait_time(2);

			click_button("Add Rule Button", oPPSLibrary.addRule);
			
			enter_text_value("Grouping Rule Name", oPPSLibrary.groupingRuleName, GroupingRule[j]);
			
			facilityName("PPS", "Apollo srn facility");
			
			if(oParameters.GetParameters("ADDING_RULE_AFTER_BEFORE"+i).isEmpty())
				System.out.println("Adding Rule");
			else
			{
				click_button("CLicked Add Rule After/Before Button", addingRuleAfterBefore);
				
				By AddRuleAfter_Before = By.xpath("//form[@id='addDrgGroupingRuleSetRules']//li[@id='i[__valueField]']/a[contains(.,'Add Rule "+AddingRuleAfterBefore[j]+"')]");
			
				click_button("Adding Rule", AddRuleAfter_Before);
				
				enter_text_value("Adding Rule After Before Value", addingRuleAfterBefore_Value, AddingRuleAfterBefore_Value[j]);
				performKeyBoardAction("ENTER");
				
			}
			
			click_button("Add Qualifier Button", addQualifierButton);
			
			enter_text_value("Qualification Group Name", QualificationGroupName, QualificationGroupName_Value[j]);
			
			if(!oParameters.GetParameters("EXPRESSION"+i).isEmpty())
				enter_text_value("Qualification Group Build Results", QualificationGroupBuildResult, Expression_Value[j]);
			
			fixed_wait_time(2);
			click_button("Validate Qualification Group Build", ValidateQualificationGroupBuild);	
			
			fixed_wait_time(1);
			click_button("Add Qaulification Group Save Button", AddQualificationGroup_SaveButton);
			
			fixed_wait_time(5);
			
			enter_text_value(" Add Rule Grouping Definition", GroupingDefinition, GroupingDefinition_Value[j]);
			performKeyBoardAction("ENTER");
			
			if(IsDisplayed("Grouping Definition", FirstGroupingDefinition))
				click_button("Frist Grouping Definition", FirstGroupingDefinition);
			
			fixed_wait_time(1);
			click_button("Add Rule Save Button", AddRule_SaveButton);
			
			fixed_wait_time(2);
		}	
	}
	
	By addGroupingRuleSet = By.xpath("//div[@title='Add DRG Grouping Rule Set']");
	
	By groupingRule = By.xpath("//li[contains(.,'01-25-2027')]");
	
	//PPS DRG Grouping Rule Set
	public void Create_PPS_DRG_GroupingRuleSet()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_DRG_GROUPING_RULE_SET", "");
    	int rowCount = oExcelData.getRowCount1("PPS_DRG_GROUPING_RULE_SET", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS DRG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	navigate_to("Grouping Rule Set", "Grouping Rule Set open page validation", oPPSLibrary.groupingRuleSetTab, oPPSLibrary.OpenPageValidation);
    	
    	click_button("Selected Grouping Rule", groupingRule);
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		/*clickAddButton("Add a APC/APG Grouping Rule Set button", "Add PPS APC/APG Grouping Rule Set window", oPPSLibrary.addPPSSetButton, addGroupingRuleSet);
    		
    		addDRGGroupingRuleSet(String.valueOf(i));*/
    		
    		addRule(String.valueOf(i));
    	}
    }
	
	
	By AddCMG_UserRateSetEntry = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//input[@id='cmgUserRateDescription']");
	
	By WageAdjustmentBaseRate = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//input[@id='wageAdjustmentBaseRate']");
	
	By LowIncomePatientAmount = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//input[@id='lowIncomePatientAmount']");
	
	By TeachingAdjustmentAmount = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//input[@id='teachingAdjustmentAmount']");
	
	By TotalPaymentAmount = By.xpath("//div[@id='addCmgUserRateSetEntryForm']//input[@id='totalPayAmount']");
	
	
	public void Add_CMG_UserRateSetEntry(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] comorbidity = oParameters.GetParameters("COMORBIDITY_TIER"+i).split(",");
		
		String[] CMGCODE = oParameters.GetParameters("CMG_CODE"+i).split(",");
		
		String[] description = oParameters.GetParameters("DESCRIPTION"+i).split(",");
		
		String[] Rehabilitation = oParameters.GetParameters("REHABILITATION_IMPAIREMENT_CATEGORY"+i).split(",");
		
		String[] weight = oParameters.GetParameters("WEIGHT"+i).split(",");
		
		String[] LengthOfStay = oParameters.GetParameters("LENGTH_OF_STAY"+i).split(",");
		
		String[] wageAdjustmentBaseRate = oParameters.GetParameters("WAGE_ADJUSTEMENT_BASE_RATE"+i).split(",");
		
		String[] lowIncomePatient = oParameters.GetParameters("LOW_INCOME_PATIENT_AMOUNT"+i).split(",");
		
		String[] teachingAdjustment = oParameters.GetParameters("TEACHING_ADJUSTEMENT_AMOUNT"+i).split(",");
		
		String[] totalPaymentAmount = oParameters.GetParameters("TOTAL_PAYMENT_AMOUNT"+i).split(","); 
		
		
		click_button("User Rate Entry Add Button", oPPSLibrary.userRateEntryAddButton);
		
		for(int j=0;j<comorbidity.length;j++)
		{
			selectOption(oPPSLibrary.comorbidityTierDD, "visibletext", comorbidity[j]);
			
			enter_text_value("Case Mix Group (CMG) Code", oPPSLibrary.caseMixGroupCode, CMGCODE[j]);
			
			if(!description[j].isEmpty())
				enter_text_value("Description", AddCMG_UserRateSetEntry, description[j]);
			
			if(!Rehabilitation[j].isEmpty())
				enter_text_value("Rehabilitation Impairment Category", oPPSLibrary.rehabilitationImpairmentCategory, Rehabilitation[j]);
			
			if(!weight[j].isEmpty())
				enter_text_value("Weight", oPPSLibrary.weight, weight[j]);
			
			if(!LengthOfStay[j].isEmpty())
				enter_text_value("Length of Stay (LOS)", oPPSLibrary.lengthOfStay, LengthOfStay[j]);
			
			if(!wageAdjustmentBaseRate[j].isEmpty())
				enter_text_value("Wage Adjustment Base Rate", WageAdjustmentBaseRate, wageAdjustmentBaseRate[j]);
			
			if(!lowIncomePatient[j].isEmpty())
				enter_text_value("Low Income Patient", LowIncomePatientAmount, lowIncomePatient[j]);
			
			if(!teachingAdjustment[j].isEmpty())
				enter_text_value("Teaching Adjustment Amount", TeachingAdjustmentAmount, teachingAdjustment[j]);

			if(!totalPaymentAmount[j].isEmpty())
				enter_text_value("Total Payment Amount", TotalPaymentAmount, totalPaymentAmount[j]);
			
			fixed_wait_time(2);
			click_button("Save Button", oPPSLibrary.saveButton);
			
			if(j < comorbidity.length-1)
			{
				fixed_wait_time(3);
				click_button("User Rate Entry Add Button", oPPSLibrary.userRateEntryAddButton);
			}
		}
	}
	
	//PPS CMG Build----------------------
	
	//PPS CMG User Rates Build
	public void Create_PPS_CMG_UserRates()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_CMG_USER_RATES", "");
    	int rowCount = oExcelData.getRowCount1("PPS_CMG_USER_RATES", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS CMG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		clickAddButton("Add a CMG User Rate Set", "Add CMG User Rate Set window", oPPSLibrary.addPPSSetButton, oPPSLibrary.addCMGUserRateSetWindow);
    		
    		addUserRateSetDetails(String.valueOf(i));
    		
    		Add_CMG_UserRateSetEntry(String.valueOf(i));
    		
    		addPeriod(String.valueOf(i));
    	}
    }
	
	
	By terminationDate_addProvider = By.xpath("//input[@id='stopDateSet']");
	
	By selectPeriodToCopy_AddProviderValueSet = By.xpath("//select[@id='copyPeriodInSet']");
	
	By selectPeriodToCopy_AddEffectivePeriod = By.xpath("//select[@id='copyPeriod']");
	
	By addCMG_ProviderValueSetSaveButton = By.xpath("//div[@id='addProviderValuesSetModal']//input[@value='Save']");
	
	By AddCMG_ProviderValuesSetCloseIcon = By.xpath("//div[@id='addProviderValuesSetModal']//button[@title='Close']");
	
	By SearchProviderValues = By.xpath("//input[@placeholder='Search Provider Values']");
	
	public void PPS_CMG_addProviderValuesSetDetails(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Name box", oPPSLibrary.nameTextbox,oParameters.GetParameters("PROVIDER_VALUES_NAME"+i));
				
		enter_text_value("Start Date", oPPSLibrary.addProviderValuesStartDate,oParameters.GetParameters("EFFECTIVE_DATE"+i));
		performKeyBoardAction("ENTER");
		
		if(!oParameters.GetParameters("TERMINATION_DATE"+i).isEmpty())
		{	
			enter_text_value("Termination Date", terminationDate_addProvider, oParameters.GetParameters("TERMINATION_DATE"+i));
			performKeyBoardAction("ENTER");
		}
		
		selectOption(oPPSLibrary.providerValueStatusDD,"visibletext",oParameters.GetParameters("PROVIDER_VALUE_STATUS"+i));	
		
		if(!oParameters.GetParameters("COPY_DETAILS_FROM_EXISTING_PROVIDER_VALUE_SET"+i).isEmpty())
		{
			click_button("Copy Details from Existing Provider Value Set check box", oPPSLibrary.copyFromExistingCMGCheckBox);
			
			enter_text_value("Search box", oPPSLibrary.selectCMGProvidervaluesetSB,oParameters.GetParameters("SELECT_PROVIDER_VALUE_SET"+i));
			performKeyBoardAction("ENTER");
			
			if(IsDisplayed("Select First Provider Value Set", oPPSLibrary.selectFirstElement))
				click_button("First Provider Value Set", oPPSLibrary.selectFirstElement);
			
			selectOption(selectPeriodToCopy_AddProviderValueSet,"value",oParameters.GetParameters("SELECT_PERIOD_TO_COPY"+i));
		}
			
		click_button("Save Button", addCMG_ProviderValueSetSaveButton);
		
		if(IsDisplayed("Error. The name must be unique", NameMustUnique))
		{
			oReport.AddStepResult("Error. The name must be Unique","Clicked on Add User Rate Set Button,Filled User Rate Set Name as '"+oParameters.GetParameters("PROVIDER_VALUES_NAME"+i)+"'  and clicked on save button and verified that 'Error.The name must be unique message is displayed',So "+oParameters.GetParameters("PROVIDER_VALUES_NAME"+i)+" User Name already Exist", "PASS");
			
			click_button("RedBox Error Close icon", NameMustUnique_RedBoxErrorCloseIcon);
		
			click_button("Add CMG Provider Values Set Close Icon", AddCMG_ProviderValuesSetCloseIcon);
			
			click_button("You Have UnSaved Changes Window Discard Button", UnSavedChanges_DiscardButton);
			
			fixed_wait_time(1);
			
			enter_text_value("Search User Rates", SearchProviderValues, oParameters.GetParameters("PROVIDER_VALUES_NAME"+i));
			performKeyBoardAction("ENTER");
			
			fixed_wait_time(1);
			
			By FirstUserRateSet = By.xpath("//div[@id='ppView']//ul//li[contains(.,'"+oParameters.GetParameters("PROVIDER_VALUES_NAME"+i)+"')]");
			click_button("First User Rate Set", FirstUserRateSet);
		}
		
		fixed_wait_time(1);
		oParameters.SetParameters("PPSTYPE", get_field_value("PPS Type Value", PPSType));
	}
	
	
	By FirstProviderValue_addEffectivePeriod = By.xpath("//div[@form-id='periodFormModel.formId']//ul[@id='-list']/li[1]");
	
	public void PPS_CMG_addEffectivePeriod(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i).isEmpty())
		{
			click_button("Period Drop Down", periodDropDown);
			
			click_button("Add Perion", addPeriod);
		
			enter_text_value("Effective Date", oPPSLibrary.addEffectivePeriodStartDate, oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i));
			performKeyBoardAction("ENTER");
			
			if(!oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i).isEmpty())
			{	
				enter_text_value("Termination Date", oPPSLibrary.terminationDate_CMGPV, oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i));
				performKeyBoardAction("ENTER");
			}
		
			if(!oParameters.GetParameters("PERIOD_PROVIDER_VALUE_STATUS"+i).isEmpty())
			{
				By ProviderValueStatus = By.xpath("//div[@form-id='periodFormModel.formId']//label[contains(.,'"+oParameters.GetParameters("PERIOD_PROVIDER_VALUE_STATUS"+i)+"')]//input");
				click_button("Provider Value Status", ProviderValueStatus);
			}
			
			if(oParameters.GetParameters("PERIOD_COPY_DETAILS_FROM_EXISTING_PROVIDER_VALUE_SET"+i).equals("Yes"))
			{
				click_button("Copy Details from Existing Provider Value Set", oPPSLibrary.copyFromExistingPVSet);
				
				enter_text_value("Select Provider Value Set", oPPSLibrary.selectCMGProvidervaluesetSB1, oParameters.GetParameters("PERIOD_SELECT_PROVIDER_VALUE_SET"+i));
				performKeyBoardAction("ENTER");
				
				if(IsDisplayed("First Provider Value Set", FirstProviderValue_addEffectivePeriod))
					click_button("Select Provider Value Set", FirstProviderValue_addEffectivePeriod);
				
				selectOption(selectPeriodToCopy_AddEffectivePeriod,"value",oParameters.GetParameters("PERIOD_SELECT_PERIOD_TO_COPY"+i));
				
			}
			
			click_button("Save button", oPPSLibrary.saveEffectivePeriod);		
		}
	}
	
	
	//PPS CMG Provider Rates Build
	public void Create_PPS_CMG_ProviderRates()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_CMG_PROVIDER_VALUES", "");
    	int rowCount = oExcelData.getRowCount1("PPS_CMG_PROVIDER_VALUES", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS CMG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	navigate_to("Provider Values", " Nothing is selected.", oPPSLibrary.providerValuesTab, oPPSLibrary.OpenPageValidation);
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		clickAddButton("Provider Value Set", "Add Provider Value Set Window", oPPSLibrary.addPPSSetButton, oPPSLibrary.addProviderValueWindow);
    		
    		PPS_CMG_addProviderValuesSetDetails(String.valueOf(i));
    		providerValues_Attributes(String.valueOf(i));
    		PPS_CMG_addEffectivePeriod(String.valueOf(i));
    	}
	}
	
	
	//PPS RUG UserRates Build
	public void Create_PPS_RUG_UserRates()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_RUG_USER_RATES", "");
    	int rowCount = oExcelData.getRowCount1("PPS_RUG_USER_RATES", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS RUG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		clickAddButton("User Rate Set", "Add RUG User Rate Set", oPPSLibrary.addPPSSetButton, oPPSLibrary.addRUGUserRateSetWindow);
    		
    		addUserRateSetDetails(String.valueOf(i));
    		
    		Add_DRG_UserRateSetEntry(String.valueOf(i));
    		
    		addPeriod(String.valueOf(i));
    	}
	}
	
	
	public void providerValues_AddPeriod_Attributes(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] attributesValues = null;
		
		if(oParameters.GetParameters("PPSTYPE").equals("PPS APC/APG") && !oParameters.GetParameters("PERIOD_SCHEME_VALUES").isEmpty())
			attributesValues = oParameters.GetParameters("PERIOD_SCHEME_VALUES"+i).split(",");
			
			
		for(int j=0;j<=attributesValues.length;j++)
		{
			By attributesValue = By.xpath("//div[@id='addEditEntryParent']//tr["+(j+1)+"]//input[@model='kv']");
			
			enter_text_value("Pricer Value", attributesValue, attributesValues[j]);
		}
		
		click_button("Pricer Value Save Button", oPPSLibrary.saveDetails);
	}
	
	
	//PPS APC/APG ProviderValue Build
	public void Create_PPS_APC_APG_ProviderValues()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_APC_APG_PROVIDER_VALUES", "");
    	int rowCount = oExcelData.getRowCount1("PPS_APC_APG_PROVIDER_VALUES", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	
    	oPPSLibrary.selectGroupType("PPS APC/APG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	oParameters.SetParameters("PPSTYPE", get_field_value("PPS Type", PPSType));
    	
    	for(int i=1;i<rowCount;i++)
    	{	
    		clickAddButton("Add Provider Value Set button", "Add Provider Value Set Window", oPPSLibrary.addPPSSetButton, oPPSLibrary.addProviderValueWindow);
    		
    		PPS_DRG_addProviderValuesSetDetails(String.valueOf(i));
    		
    		providerValues_Attributes(String.valueOf(i));
    		
    		providerValues_addPeriod(String.valueOf(i));
    	}
	}
	
	
	public void PPS_APC_GrouperOptions_AttributesValues(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		
		String[] attributeDetails = oParameters.GetParameters("ATTRIBUTES"+i).split("/");
		
		String[] attributesValueDetails = oParameters.GetParameters("ATTRIBUTES_VALUES"+i).split("/");
		
		for(int j=0;j<attributeDetails.length;j++)
		{
			if(!attributeDetails[j].isEmpty())
			{
				By attribute = By.xpath("//td[text()='"+attributeDetails[j]+"']");
			
				click_button("Selected Attribute", attribute);
			
				fixed_wait_time(2);
			
				if(!attributesValueDetails[j].isEmpty())
				{
					By attributeValue = By.xpath("//form[@id='addEditApcGrouperOptionsEntry']/div/p[contains(.,'"+attributesValueDetails[j]+"')][1]/input");
					click_button("Selected Attribute Value", attributeValue);
				}
			
				click_button("Attribute Value Window Save Button", oPPSLibrary.attributeValueEditWindowSave);
				
				fixed_wait_time(3);
			}
		}	
	}
	
	
	//PPS APC/APG GrouperOptions Build
	public void Create_PPS_APC_APG_GrouperOptions()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_APC_APG_GROUPER_OPTIONS", "");
    	int rowCount = oExcelData.getRowCount1("PPS_APC_APG_GROUPER_OPTIONS", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	oPPSLibrary.selectGroupType("PPS APC/APG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
		navigate_to("Grouper Options", " Nothing is selected.", oPPSLibrary.grouperOption, oPPSLibrary.OpenPageValidation);	
		
		for(int i=1;i<rowCount;i++)
		{
			clickAddButton("Add Grouper Options Set button", "Add APC Grouper Options Set Window", oPPSLibrary.addPPSSetButton, oPPSLibrary.addAPCGrouperOptionsSetWindow);
			
			addDRGGrouperOptionSetDetails(String.valueOf(i));
			
			PPS_APC_GrouperOptions_AttributesValues(String.valueOf(i));
		}
	}
	
	
	 By APC_APG_PeriodDropDown_GroupingDefinition = By.xpath("//div[@id='ppView']//span[@ng-show='hasPeriods']");
	
	 By APC_APG_AddPeriod_GroupingDefinition = By.xpath("//div[@id='ppView']//a[@ng-click='addApcGroupingPeriod()']");
	
	 By APC_APG_CopyExistingGroupingdefinitionCheckBox = By.xpath("//div[@id='ppView']//input[@id='copyClosed']");
	
	 By APC_APG_SelectGroupingDefinitionSet = By.xpath("//div[@id='ppView']//div[@form-id='periodFormModel.formId']//input[@id='copyGroupingDefinitionSet']");
	
	 By APC_APG_SelectPeriodToCopy = By.xpath("//div[@id='ppView']//div[@form-id='periodFormModel.formId']//select[@id='copyPeriod']");
	 
	 By FristGroupingDefinitionSet = By.xpath("//div[@id='ppView']//div[@form-id='periodFormModel.formId']//input[@id='copyGroupingDefinitionSet']/..//ul[@id='-list']//li[1]");
	 
	 
	public void APC_APG_AddEffectivePeriod(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i).isEmpty())
		{
			click_button("APC/APG Grouping Definition Period DropDown", APC_APG_PeriodDropDown_GroupingDefinition);
			
			click_button("APC/APG Grouping Definition Add Period", APC_APG_AddPeriod_GroupingDefinition);
			
			fixed_wait_time(2);
		
			enter_text_value("Effective Date", oPPSLibrary.periodEffectiveDateGD, oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i));
			performKeyBoardAction("ENTER");
			
			if(!oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i).isEmpty())
			{
				enter_text_value("Termination Date ", oPPSLibrary.periodTerminationDateGD, oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i));
				performKeyBoardAction("ENTER");
			}
			
			if(!oParameters.GetParameters("PERIOD_COPY_DETAILS_FROM_EXISTING_GROUPING_DEFINITION_SET"+i).isEmpty())
			{
				click_button("Copy Details From Existing Grouping Definition Set", APC_APG_CopyExistingGroupingdefinitionCheckBox);
				
				enter_text_value("Select Grouping Definition Set", APC_APG_SelectGroupingDefinitionSet, oParameters.GetParameters("PERIOD_SELECT_GROUPING_DEFINITION_SET"+i));
				performKeyBoardAction("ENTER");
				
				if(IsDisplayed("Select Frist Grouping Definition Set", FristGroupingDefinitionSet))
					click_button("Grouping Definition Set", FristGroupingDefinitionSet);

				selectOption(APC_APG_SelectPeriodToCopy,"value",oParameters.GetParameters("PERIOD_SELECT_PERIOD_TO_COPY"+i));
			
				click_button("Add Effective Period Save Button", oPPSLibrary.saveEffectivePeriod);
			}
			
			
			selectOption(oPPSLibrary.periodGroupingDefinitionSetGrouperNameDD,"visibletext",oParameters.GetParameters("PERIOD_GROUPER_NAME"+i));				
			
			select_option("Gruper Definition Version", oPPSLibrary.periodgrouperDefinitionVersionDD, oParameters.GetParameters("PERIOD_GROUPER_VERSION"+i));
			
			if(!oParameters.GetParameters("PERIOD_APC_APG_GROUPER_OPTION_SET"+i).isEmpty())
			{	
				enter_text_value("APC/APG Grouper Option Set", oPPSLibrary.periodAPCAPGGrouperOptionSetSB, oParameters.GetParameters("PERIOD_APC_APG_GROUPER_OPTION_SET"+i));
				performKeyBoardAction("ENTER");
				
				if(IsDisplayed("APC/APG Grouper Option Set first search value", oPPSLibrary.periodAPCAPGGrouperOptionSetSBFirstResult))
					click_button("APC/APG Grouper Option Set first search value", oPPSLibrary.periodAPCAPGGrouperOptionSetSBFirstResult);
			}
			
			if(!oParameters.GetParameters("PERIOD_APC_APG_PROVIDER_VALUES_SET"+i).isEmpty())
			{
				enter_text_value("APC/APG Provider Values Set", oPPSLibrary.periodAPCAPGProviderValuesSetSB, oParameters.GetParameters("PERIOD_APC_APG_PROVIDER_VALUES_SET"+i));
				performKeyBoardAction("ENTER");
				
				if(IsDisplayed("APC/APG Grouper Option Set first search value", oPPSLibrary.periodAPCAPGProviderValuesSetSBFirstResult))
					click_button("APC/APG Grouper Option Set first search value", oPPSLibrary.periodAPCAPGProviderValuesSetSBFirstResult);
			}	
			
			selectOption(oPPSLibrary.periodICDVersionDD,"visibletext",oParameters.GetParameters("PERIOD_ICD_VERSION_OVERRIDE"+i));
			
			click_button("Add Effective Period Save Button", oPPSLibrary.saveEffectivePeriod);
		}
		
	}
	
	
	//PPS APC/APG Grouping Definition Build
	public void Create_PPS_APC_APG_GroupingDefinitions()
	{
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_APC_APG_GROUPING_DEFINITION", "");
    	int rowCount = oExcelData.getRowCount1("PPS_APC_APG_GROUPING_DEFINITION", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	oPPSLibrary.selectGroupType("PPS APC/APG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	navigate_to("Grouping Definitions", " Nothing is selected.", oPPSLibrary.groupingDefinitionsTab, oPPSLibrary.OpenPageValidation);
    	

    	oParameters.SetParameters("PPSTYPE", get_field_value("PPS Type", PPSType));

    	
    	for(int i=1;i<rowCount;i++)
    	{
    		clickAddButton("Add Grouping Definition Set button", "Add Grouping Definition Set window", oPPSLibrary.addPPSSetButton, oPPSLibrary.addGroupingDefinitionSetWindow);
    		
    		addGroupingDefinitionSetDetails(String.valueOf(i));
    		
    		APC_APG_AddEffectivePeriod(String.valueOf(i));
    	}
	}
	
	By AddRuleAfterBefore_APC = By.xpath("//form[@id='addApcGroupingRuleSetRule']//span[contains(@class,'dropdown-text hide-overflow ng-binding')]/..");
	
	By AddRuleAfterBeforeValue_APC = By.xpath("//form[@id='addApcGroupingRuleSetRule']//input[@id='rulePlacement']");
	
	By AddQualificationGroupSaveButton = By.xpath("//div[@id='ppView']//div[@id='fullFooter']//input[@id='saveRuleSetQualifierGroupId']");
	
	By AddAPCAPGGroupingRuleSet_SaveButton = By.xpath("//div[@id='addGroupingRuleSetRuleModal']//input[@value='Save']");
	
	public void addAPCAPGGroupingRule(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] GroupingRule = oParameters.GetParameters("GROUPING_RULE_NAME"+i).split(",");
		
		String[] AddRuleAfterBefore = oParameters.GetParameters("ADDING_RULE_AFTER_BEFORE"+i).split(",");
		
		String[] AddRuleAfterBeforeValue = oParameters.GetParameters("ADDING_RULE_AFTER_BEFORE_VALUE"+i).split(",");
		
		String[] QualificationGroup_Value = oParameters.GetParameters("QUALIFICATION_GROUP_NAME"+i).split(",");
		
		String[] Expression_Value = oParameters.GetParameters("EXPRESSION"+i).split(",");
		
		String[] GroupingDefinition_Value = oParameters.GetParameters("GROUPING_DEFINITION"+i).split(",");
		
		for(int j=0;j<GroupingRule.length;j++)
		{
			fixed_wait_time(2);
			
			click_button("APC APG Add Rule button", oPPSLibrary.addRule);
			
			enter_text_value("Grouping Rule Name", oPPSLibrary.groupingRuleName, GroupingRule[j]);
			
			facilityName("PPS", "Apollo srn facility");
			
			if(oParameters.GetParameters("ADDING_RULE_AFTER_BEFORE"+i).isEmpty())
				System.out.println("Adding Rule");
			else
			{
				click_button("Add Rule After/Before Button", AddRuleAfterBefore_APC);
				
				By addRuleBeforeAfter = By.xpath("//form[@id='addApcGroupingRuleSetRule']//li[@id='i[__valueField]']/a[contains(.,'Add Rule "+AddRuleAfterBefore[j]+"')]");
				
				click_button("Add Rule Status", addRuleBeforeAfter);
				
				enter_text_value("Add Rule After Before Value", AddRuleAfterBeforeValue_APC, AddRuleAfterBeforeValue[j]);
				performKeyBoardAction("ENTER");
			}
			
			click_button("Add Qualifier Button", addQualifierButton);
			
			enter_text_value("Qualification Group Name", QualificationGroupName, QualificationGroup_Value[j]);
			
			if(!oParameters.GetParameters("EXPRESSION"+i).isEmpty())
				enter_text_value("Qualification Group Build Results", QualificationGroupBuildResult, Expression_Value[j]);
			
			fixed_wait_time(2);
			click_button("Validate Qualification Group Build", ValidateQualificationGroupBuild);
			
			fixed_wait_time(1);
			click_button("Add Qualification Group Save Button ", AddQualificationGroupSaveButton);
			
			fixed_wait_time(5);
			
			enter_text_value(" Add Rule Grouping Definition", GroupingDefinition, GroupingDefinition_Value[j]);
			performKeyBoardAction("ENTER");
			
			if(IsDisplayed("Grouping Definition", FirstGroupingDefinition))
				click_button("Frist Grouping Definition", FirstGroupingDefinition);
			
			fixed_wait_time(1);
			click_button("Add APC/APG Grouping Rule Save Button", AddAPCAPGGroupingRuleSet_SaveButton);
			
			fixed_wait_time(2);
		}
	}
	
	By APC_APG_RuleSet = By.xpath("//li[contains(.,'01-01-2011')]");
	
	//PPS APC/APG Grouping Rule Set Build.
	public void Create_PPS_APC_APG_GroupingRuleSet()
	{
		
		login("EDIT");
    	changePricingEngine();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData.xlsx", "PPS_APC_APG_GROUPING_RULE_SET", "");
    	int rowCount = oExcelData.getRowCount1("PPS_APC_APG_GROUPING_RULE_SET", "C:\\CCM\\SupportingFiles\\TestData.xlsx");
    	
    	navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
    	oPPSLibrary.selectGroupType("PPS APC/APG",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	navigate_to("Grouping Rule Set", "Grouping Rule Set open page validation", oPPSLibrary.groupingRuleSetTab, oPPSLibrary.OpenPageValidation);
    	
    	click_button("APC / APG Grouping Rule Set", APC_APG_RuleSet);
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		/*clickAddButton("Add a APC/APG Grouping Rule Set button", "Add PPS APC/APG Grouping Rule Set window", oPPSLibrary.addPPSSetButton, oPPSLibrary.addRuleSetWindow);
    		
    		addDRGGroupingRuleSet(String.valueOf(i));*/
    		
    		addAPCAPGGroupingRule(String.valueOf(i));
    	}
	}
	
	
	
    //PPS Dialysis Comorbidity Categories Build-------------------------------
    
    
    // To add Comorbidity Category Set
    public void addComorbidityCategoriesSet(String i)
    {
    	//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
    	{                 
    		oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
    		return ;
    	}
          
    	click_button("Add a Comorbidity Category Set button", oPPSLibrary.addPPSSetButton);
    	
    	enter_text_value("Comorbidity Category Set Name", oPPSLibrary.nameTextbox, oParameters.GetParameters("COMORBIDITY_CATEGORY_SET_NAME"+i));
    	
    	facilityName("PPS",oParameters.GetParameters("FACILITY_SB"+i));
    	
    	enter_text_value("Start Date", oPPSLibrary.ccStartDate, oParameters.GetParameters("EFFECTIVE_DATE"+i));
    	performKeyBoardAction("ENTER");
    	
    	enter_text_value("Start Date", oPPSLibrary.ccStopDate, oParameters.GetParameters("TERMINATION_DATE"+i));
    	performKeyBoardAction("ENTER");
          
    	fixed_wait_time(3);
    	click_button("Save Button", oPPSLibrary.saveButton);              
    }
    
    
    // To add Category 
    public void addCategory(String i)
    {
    	String[] category = oParameters.GetParameters("CATEGORY"+i).split(",");
    	
    	String[] base = oParameters.GetParameters("BASE"+i).split(",");
    	
    	String[] outlier = oParameters.GetParameters("OUTLIER"+i).split(",");
                      
    	for(int j=0;j<category.length;j++)
    	{                             
    		click_button("Add Category Button", oPPSLibrary.addCategoryButton);
    		
    		selectOption(oPPSLibrary.Category, "visibletext", category[j]);     
    		
    		enter_text_value("Base Field", oPPSLibrary.base, base[j]);
    		
    		if(!oParameters.GetParameters("OUTLIER"+i).isEmpty())
    			enter_text_value("Base Field", oPPSLibrary.outlier, outlier[j]);               
                	
            click_button("Category Save Button", oPPSLibrary.categorySaveButton);
                            
            for(int l=j;l<=j;l++)
            {     
            	String[] diagnosisCode = oParameters.GetParameters("DIAGNOSIS_CODE"+i).split("-");
                      
            	String[] diagnosis = diagnosisCode[j].split(",");
                      
            	for(int k=0;k<diagnosis.length;k++)
            	{     
            		if(IsDisplayed("Add ICD Code", oPPSLibrary.addICDCodeLink))
            			click_button("Add ICD Code", oPPSLibrary.addICDCodeLink);
            		else
            			click_button("Add ICD Code", oPPSLibrary.addICDCodeButton);            
                
            		enter_text_value("Diagnosis code", oPPSLibrary.diagnosisCodeTextBox, diagnosis[k]);
            		
            		click_button("Save Button", oPPSLibrary.saveEffectivePeriod);
            	}
            }
    	} 	    
    }
    
    
    //To add Period
    public void addComorbidityCategoryPeriod(String i)
    {
        if(!oParameters.GetParameters("PERIOD_EFFECTIVE_DATE"+i).isEmpty())
        {     
        	click_button("Period drop down", oPPSLibrary.periodDropdown);
        	
        	click_button("Add Period button", oPPSLibrary.addPeriodButton);
        	
        	enter_text_value("Category Start date", oPPSLibrary.ccPeriodStartDate, oParameters.GetParameters("PERRIOD_EFFECTIVE_DATE"+i));
        	performKeyBoardAction("ENTER");
        	
            if(!oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i).isEmpty())
            {
            	enter_text_value("Category Stop date", oPPSLibrary.ccPeriodStopDate, oParameters.GetParameters("PERIOD_TERMINATION_DATE"+i));
            	performKeyBoardAction("ENTER");
            }         
            
            click_button("Edit window save", oPPSLibrary.editWindowSaveButton);
        }     
    }
    
    
    // PPS Dialysis Comorbidity Categories Set Build
    public void PPS_Dialysis_Comrbidity_Categories_Build()
    {
    	login("EDIT");
          
    	changePricingEngine(); 

    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "Build_TestData.xlsx", "PPS_DIALYSIS_COMORBIDITY_CAT", "");
    	int rowCount = oExcelData.getRowCount1("PPS_DIALYSIS_COMORBIDITY_CAT", "C:\\CCM\\SupportingFiles\\Build_TestData.xlsx");
    
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);   
    
    	oPPSLibrary.selectGroupType("PPS Dialysis",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    	
    	for(int i=1;i<rowCount;i++)
    	{
    		addComorbidityCategoriesSet(String.valueOf(i));
    		
    		addCategory(String.valueOf(i));
    		
    		addComorbidityCategoryPeriod(String.valueOf(i));
    	}     	
    }
    
    
    //PPS Dialysis Rate Factors Build--------------------
    
    public void addDialysisRateFactor(String i)
    {
    	click_button("Add Rate Factors button", oPPSLibrary.addPPSSetButton);
          
    	enter_text_value("Rate Factor Set Name", oPPSLibrary.nameTextbox, oParameters.GetParameters("RATEFACTOR_SET_NAME"+i));
    	
    	facilityName("PPS",oParameters.GetParameters("FACILITY_SB"+i));
    	
    	click_button("Save Button", oPPSLibrary.saveButton);        
    }
    
    
    //To add Rate Factors Period
    public void addRateFactorsPeriod(String i)
    {
    	if(!oParameters.GetParameters("EFFECTIVE_DATE"+i).isEmpty())
    	{     
    		click_button("Period drop down", oPPSLibrary.periodDropdown);
    		
    		click_button("Add Period button", oPPSLibrary.addPeriodButton);
    		
    		enter_text_value("Rate Factors Start date", oPPSLibrary.effectiveDateRF, oParameters.GetParameters("EFFECTIVE_DATE"+i));             
    		performKeyBoardAction("ENTER");
    		
    		if(!oParameters.GetParameters("TERMINATION_DATE"+i).isEmpty())
    		{     
    			enter_text_value("Rate Factors Stop date", oPPSLibrary.terminationDateRF, oParameters.GetParameters("TERMINATION_DATE"+i));
    			performKeyBoardAction("ENTER");
    		}     
    		
    		click_button("Edit window save", oPPSLibrary.editWindowSaveButton);
    	}     
    }
    
    
    // Method to enter all Common Factors values
    public void enterCommonFactorValues(String i)
    {           
        if(!oParameters.GetParameters("BSA_MULTIPLIER_BASE_OUTLIER"+i).isEmpty())
        {     
        	String[] baseValue = oParameters.GetParameters("BASE"+i).split(",");
        	
        	String[] OutlierValue = oParameters.GetParameters("OUTLIER"+i).split(",");
        	
        	for(int j=1;j<=baseValue.length;j++)
        	{
        		By base = By.xpath("//form[@id='addRateFactorPeriod']/div[3]/div["+(j+1)+"]/div[2]//input");
        		
        		By outlier = By.xpath("//form[@id='addRateFactorPeriod']/div[3]/div["+(j+1)+"]/div[3]//input");
        		
        		enter_text_value("BASE Value", base, baseValue[j-1]);
        		
        		enter_text_value("Outlier Value", outlier, OutlierValue[j-1]);
        	}
        }
          
        if(!oParameters.GetParameters("AGE_START"+i).isEmpty())
        {
        	String[] ageDetails = oParameters.GetParameters("AGE_DETAILS"+i).split("-");
        	
        	for(int k=0;k<ageDetails.length;k++)
        	{
        		String[] ageValues = ageDetails[k].split(",");
        		
        		for(int l=0;l<ageValues.length;l++)
        		{
        			By AgeFields = By.xpath("//form[@id='addRateFactorPeriod']//li[2]/div["+(l+1)+"]//input");
        			
        			enter_text_value("Age Fields", AgeFields, ageValues[l]);
        		}
                      
        		if(k < ageDetails.length-1)
        		{
        			By addIcon = By.xpath("//ul[@model='rfPeriodForm.ageMultiplier']/li["+(k+2)+"]//a[@title='Add']//i[@class='left fa fa-plus-square']");
        			
        			click_button("Add Icon", addIcon);
        		}
        	}
        }
          
          
        if(!oParameters.GetParameters("THRESHOLD_VALUES"+i).isEmpty())
        {
        	String[] thresholdValues = oParameters.GetParameters("THRESHOLD_VALUES"+i).split(",");
        	
        	for(int j=1;j<=thresholdValues.length;j++)
        	{
        		By threshold = By.xpath("//form[@id='addRateFactorPeriod']//div[4]/div["+j+"]/div[2]//input");
        		
        		enter_text_value("Low BMI Threshold", threshold, thresholdValues[j]);
        	}
        }	
          	
          	
        if(!oParameters.GetParameters("OUTLIER_CALCULATIONS"+i).isEmpty())
        {
        	String[] outlierCalculations = oParameters.GetParameters("THRESHOLD_VALUES"+i).split(",");
        	
        	for(int n=1;n<=outlierCalculations.length;n++)
        	{
        		By outlierCalculation = By.xpath("//form[@id='addRateFactorPeriod']/fieldset//div["+n+"]/div[2]//input");
        		
        		enter_text_value("Low BMI Threshold", outlierCalculation, outlierCalculations[n]);
        	}
        }
                      
        enter_text_value("Comorbidity Category Set Search box", oPPSLibrary.comorbidityCategorySetSB,oParameters.GetParameters("COMBORIDITY_CATEGORY_SET_SB"+i));
        
        if(!oParameters.GetParameters("LAB_FEE_SCHEDULE"+i).isEmpty())
        	enter_text_value("Lab Fee Schedule Search box", oPPSLibrary.labFeeScheduleSB,oParameters.GetParameters("LAB_FEE_SCHEDULE"+i));
        
        if(!oParameters.GetParameters("LAB_SERVICE_QUALIFICATION_GROUP"+i).isEmpty())
        	enter_text_value("Lab Service Qualification Group", oPPSLibrary.labServiceQualificationGroupSB,oParameters.GetParameters("LAB_SERVICE_QUALIFICATION_GROUP"+i));
          
        if(!oParameters.GetParameters("OTHER_FEE_SCHELDULE"+i).isEmpty())
        	enter_text_value("Other Fee Schedule search", oPPSLibrary.otherFeeScheduleSB,oParameters.GetParameters("OTHER_FEE_SCHELDULE"+i));
          
        if(!oParameters.GetParameters("OTHER_FEE_SCHEDULE_QUALIFICATION_GROUP"+i).isEmpty())
        	enter_text_value("Other Fee Schedule Qualification Group search", oPPSLibrary.otherFeeScheduleQGSB,oParameters.GetParameters("OTHER_FEE_SCHEDULE_QUALIFICATION_GROUP"+i));
                      
          	click_button("Rate Factor Save", oPPSLibrary.rateFactorSaveButton);        
    }
    
    
    // To add details in Provider Values Tab
    public void providerValuesDetails(String i)
    {
    	if(!oParameters.GetParameters("CARRIER"+i).isEmpty())
    	{
    		navigate_to("Provider Values", "Add Provider Entry button", oPPSLibrary.providerValuesTab, oPPSLibrary.addProviderEntryButton);      
    		
    		String[] carrier = oParameters.GetParameters("CARRIER"+i).split(",");
    		
    		String[] locality = oParameters.GetParameters("LOCALITY"+i).split(",");
          
    		String[] lowVolumeIndicator = oParameters.GetParameters("LOW_VOLUME_INDICATOR"+i).split(",");
          
    		String[] compositeBaseRate = oParameters.GetParameters("PPS_COMPOSITE_BASE_RATE"+i).split(",");
          
    		String[] trainingAddOn = oParameters.GetParameters("TRAINING_ADD_ON"+i).split(",");
          
    		for(int j=0;j<carrier.length;j++)
            {           
    			click_button("Add Provider Entry Button", oPPSLibrary.addProviderEntryButton);
          
    			enter_text_value("Carrier ", oPPSLibrary.carrier, carrier[j]);
          
    			enter_text_value("Locality ", oPPSLibrary.locality, locality[j]);
          
                selectOption(oPPSLibrary.lowVolumeIndicator,"value",lowVolumeIndicator[j]);
          
                enter_text_value("PPS Composite Base Rate ", oPPSLibrary.PPSCompositeBaseRate, compositeBaseRate[j]);
          
                if(!oParameters.GetParameters("TRAINING_ADD_ON"+i).isEmpty())
                	enter_text_value("Training Add-On", oPPSLibrary.trainingAddOn, trainingAddOn[j]);
                
                click_button("Save Button", oPPSLibrary.saveEffectivePeriod);
            }
    	}
    }
          
    
    // PPS Dialysis Comorbidity Categories Set Build
    public void create_PPS_Dialysis_Rate_Factors_Build()
    {
    	login("EDIT");
          
    	changePricingEngine(); 

    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "Build_TestData.xlsx", "PPS_DIALYSIS_RATE_FACTORS", "");
    	int rowCount = oExcelData.getRowCount1("PPS_DIALYSIS_RATE_FACTORS", "C:\\CCM\\SupportingFiles\\Build_TestData.xlsx");
    
    	oPPSLibrary.navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);   
    
    	oPPSLibrary.selectGroupType("PPS Dialysis",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
    
    	navigate_to("Rate Factors", " Nothing is selected.", oPPSLibrary.rateFactorsTab, oPPSLibrary.OpenPageValidation);//Method navigates to Provider Values tab
          
 //   	addDialysisRateFactor("");
    
    	for(int i=1;i<rowCount;i++)
    	{
    		addDialysisRateFactor(String.valueOf(i));
    		addRateFactorsPeriod(String.valueOf(i));
    		enterCommonFactorValues(String.valueOf(i));
    		providerValuesDetails(String.valueOf(i));
    	}     
    }
}



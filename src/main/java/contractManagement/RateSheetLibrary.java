package contractManagement;

import java.text.*;
import java.util.*;
import org.openqa.selenium.*;
import libraries.*;

public class RateSheetLibrary extends CCMLibrary
{
	ExcelData oExcelData = new ExcelData(oParameters);
	
	ModelsLibrary oModelsLibrary = new ModelsLibrary(driver, oReport, oParameters);
	
	int count = 0;
	
	public RateSheetLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}

	By rateSheetsPlugin = By.xpath("//*[@id='nav']//a[text()='Rate Sheets']");

	By contractsPlugin = By.xpath("//*[@id='nav']//a[text()='Contracts']");

	
	// Navigating to Rate Sheets
	public void navigateToRateSheets()
	{
		navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", rateSheetsPlugin, rateSheetSearch);
	}
		
	
	By addRateSheet = By.xpath("//div[@id='ratesheetSection']//a[@title='Add Rate Sheet']/i[@class='left fa fa-plus-square']");
    
    By addRateSheetWindow = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//div[@id='addRateSheetModal']//div[@title='Add Rate Sheet']");
    
    
	//Method to click on RateSheet Icon.
	public void addRateSheetIcon()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("Add RateSheet Button", addRateSheet))
		{	
			oReport.AddStepResult("Add Rate Sheet Button", "Navigated to RateSheet plugin and verified that Add Rate Sheet button is displayed", "PASS");
			click_button("Add Rate Sheet Link", addRateSheet);
			
			if(IsElementDisplayed("Add Rate Sheet Window", addRateSheetWindow))
				oReport.AddStepResult("Add Rate Sheet Window", "Clicked on add rate sheet link and verifed that add Ratesheet window is displayed", "PASS");
			else
				oReport.AddStepResult("Add Rate Sheet Window", "Clicked on add rate sheet link and verifed that add Ratesheet window is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Add Rate Sheet Button", "Navigated to RateSheet plugin and verified that Add Rate Sheet button is not displayed", "FAIL");
	}                                
	
    
	//Method to enter a RateSheet Details.
	public void AddRateSheetDetails(String i)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
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
		fixed_wait_time(1);
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
			
				if(IsDisplayed("Select Search result RateSheet", RateSheet))				
					click_button("Select Search result RateSheet", RateSheet);
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
    
    
    By rateSheetName = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='ratesheetName']");
    
    By rateSheetCode = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='ratesheetCode']");
    
    By rateSheetType = By.xpath("//form[@id='addEditRatesheetForm']//input[@value='Model']");
    
    By rateSheetEffectiveDate = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='startDate']");
    
    By rateSheetTerminationDate = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='endDate']");
    
    By DRGGrouping = By.xpath("//form[@id='addEditRatesheetForm']//select[@id='claimDrg']");
    
    By qualifyInpatientClaims = By.xpath("//select[@id='qualInpatient']");
    
    By qualifyOutpatientClaims = By.xpath("//*[@id='qualOutpatient']");
    
    By copyExistingRateSheet = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='copyClosed']");
    
    By selectRateSheet = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='copyRateSheetSet']");
    
    By includeTermNotes = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='includeNotes']");
    
    By addANote = By.xpath("//div[@note-value='ratesheet.ratesheetNote']//textarea[@placeholder='Add a note']");
    
    By production = By.xpath("//form[@id='addEditRatesheetForm']//input[@value='Prod']");
    
    By modeling = By.xpath("//form[@id='addEditRatesheetForm']//input[@value='Model']");
    
    By searchFirstRateSheet = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//ul[@id='-list']//li[1]");
    
    By advancedQualifierOptions = By.xpath("//form[@id='addEditRateSheetSectionForm']//span[@toggle-target='qualificationGroupOptionsType']");
    
    By advancedQualifierOptions_Section = By.xpath("//form[@id='addEditRateSheetSectionForm']//span[@toggle-target='qualificationGroupOptionsType']");

	By advancedQualifierOptions_Term = By.xpath("//form[@id='addEditTermForm']//span[@toggle-target='qualificationGroupOptionsType']");
    
	By feeScheduleEffectivePeriod = By.xpath("//select[@id='feeSchedPeriod']");
	
	By feeScheduleSearchBox = By.xpath("//input[@id='feeScheduleSearch']");
	
	By maximumRateType_Section = By.xpath("//div[@form-id='formBasicSectionModel.formId']//select[@id='maxRateType']");
	
    By procedureGroupCodeSet = By.xpath("//select[@id='procedureGroup']");
    
    By procedureGroupCodeSetSearchBox = By.xpath("//input[@id='pGrpCSSearch']");
    
    By procedureGropuCodeSetEffectivePeriod = By.xpath("//select[@id='procedureGroupPeriod']");
    
    By diagnosisCodeSetSearchBox = By.xpath("//input[@id='DiagCSSearch']");
    
    By diagnosisCodeSetEffectivePeriod = By.xpath("//select[@id='DiagCSPeriod']");
    
    By drgCodeSetSearchBox = By.xpath("//input[@id='DRGCSSearch']");
    
    By drgEffectivePeriod = By.xpath("//select[@id='DrgPeriod']");
    
    By RateSheetResults = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 col-mdlg-7 mar-r-0']//ul[@class='dropdown-menu']"); 
    
    By errorAddingRateSheet = By.xpath("//div[contains(text(),'Error Adding Rate Sheet')]");
	
	By errorAddingOKButton = By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");
	
	By rateSheetCodeCancelButton = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='button.cancelId']"); 
	
	By errorAddingRateSheet1= By.xpath("//span[contains(.,' The contract and reporting names must both be unique for a given payer.')]");
	
	By errorCloseButton = By.xpath("//div[@class='message-center ng-isolate-scope global']//span[@icon='times-circle']");
    
    
	//Method to click on 'RateSheet Save Button'.
	public void rateSheetSaveButton(String i)
	{          
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementEnabled("RateSheet save Button", rateSheetCodeSaveButton))
		{      
			oReport.AddStepResult("RateSheet Save Button", "In 'Add RateSheet Tab' filled all the details, verified that save button is enabled", "PASS");
        
			click_button("Rate Sheet Save Button", rateSheetCodeSaveButton);
			fixed_wait_time(3);
              
			if(IsDisplayed("Error Adding Rate Sheet", errorAddingRateSheet))
			{
				oReport.AddStepResult("Error Adding Rate Sheet", "In 'Add Rate Sheet' Window filled all mandatory fields and clicked on save button and verified that '"+oParameters.GetParameters("RateSheetName"+i)+"' RateSheet is already exist", "WARNING");
                     				
				click_button("Error Popup OK", errorPopupOK);

				// Validating Unsaved changes pop up is displayed or not

				click_button("Add RateSheet Window Cancel", rateSheetCodeCancelButton);
				
				if(IsDisplayed("Unsaved Changes popup", unsavedMessageWindow))
				{
					click_button("Unsaved Changes popup Delete", unsavedWindowDelete);

					if(IsDisplayed("Add Rate Sheet Window", addRateSheetWindow))
						oReport.AddStepResult("Unsaved Changes popup","Clicked on Add Rate Sheet then filled all the mandatory fields then clicked on cancel but unsaved changes pop up is not displayed and Add Rate Sheet window is not closed upon clicking on Discard ","FAIL");
					else
						oReport.AddStepResult("Unsaved Changes popup","Unsaved changes pop up is displayed and Add Rate Sheet window is closed upon clicking on Discard ","PASS");
				} 
				else
					oReport.AddStepResult("Unsaved Changes popup","Clicked on Add Rate Sheet then filled all the mandatory fields then clicked on cancel but unsaved changes pop up is not displayed ","FAIL");

				searchRateSheet("Production", oParameters.GetParameters("RateSheetName"+i)); 
				
				oParameters.SetParameters("rateSheetStatus", "Existed");
			}
			else if(IsDisplayed("Error Adding Rate Sheet", errorAddingRateSheet1))
			{
				oReport.AddStepResult("Error Adding Rate Sheet", "In 'Add Rate Sheet' Window filled all mandatory fields and clicked on save button and verified that '"+oParameters.GetParameters("RateSheetName"+i)+"' RateSheet is already exist", "WARNING");
			
				click_button("Error notification close icon", errorCloseButton);
				
				// Validating Unsaved changes pop up is displayed or not

				click_button("Add RateSheet Window Cancel", rateSheetCodeCancelButton);
				
				if(IsDisplayed("Unsaved Changes popup", unsavedMessageWindow))
				{
					click_button("Unsaved Changes popup Delete", unsavedWindowDelete);

					if(IsDisplayed("Add Rate Sheet Window", addRateSheetWindow))
						oReport.AddStepResult("Unsaved Changes popup","Clicked on Add Rate Sheet then filled all the mandatory fields then clicked on cancel but unsaved changes pop up is not displayed and Add Rate Sheet window is not closed upon clicking on Discard ","FAIL");
					else
						oReport.AddStepResult("Unsaved Changes popup","Unsaved changes pop up is displayed and Add Rate Sheet window is closed upon clicking on Discard ","PASS");
				} 
				else
					oReport.AddStepResult("Unsaved Changes popup","Clicked on Add Rate Sheet then filled all the mandatory fields then clicked on cancel but unsaved changes pop up is not displayed ","FAIL");

				searchRateSheet("Production", oParameters.GetParameters("RateSheetName"+i)); 
				
				oParameters.SetParameters("rateSheetStatus", "Existed");
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
			System.out.println("Total Number of RateSheet Uploaded:" + count);
		}      
		else
			oReport.AddStepResult("RateSheet Save Button", "In 'Add RateSheet Tab' filled all the details but ratesheet save button not enabled", "FAIL");
	}

	
	//Method to click on 'Add Section Icon'.
	public void addSectionIcon()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Add Term Section Link", addTermSectionLink))
			click_button("Add Term Section link", addTermSectionLink);
		else if(IsDisplayed("Add Section Button", AddSectionButton))
			click_button("Add Section Button", AddSectionButton);
		else 
			addStopLossSectionLink();
		
		if(IsDisplayed("Add Section Window", addSectionWindow)||IsDisplayed("Add Stop Loss Section Window", addStopLossSectionWindow))
			oReport.AddStepResult("Add Section Window", "Clicked on add Section Icon and verified that 'Add Section' Window is displayed", "PASS");
		else
			oReport.AddStepResult("Add Section Window", "Clicked on add Section Icon and verified that 'Add Section' Window is not displayed", "FAIL");
	}
	
    
	//Method to handle 'Advanced Qualifier Option'.
	public void advancedQualifierOption(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(oParameters.GetParameters("AdvancedQualifierOptions"+i).equals("YES"))
		{
			if(IsDisplayed("Advanced Qualifier Options For Section", advancedQualifierOptions_Section))
			{	
				click_button("Advanced Qualifier Options For Section", advancedQualifierOptions_Section);
				
				if(IsElementDisplayed("Qualification Group Options", qualificationGroupOptions))
					oReport.AddStepResult("Qualification Group Options", "Another Qualifier Group options is displayed","PASS");
				else
					oReport.AddStepResult("Qualification Group Options","Section opened in the edit mode and checked 'Show Advanced Qualifier Options' but Another Qualifier Group options is not displayed","FAIL");
			}	
			else
				click_button("Advanced Qualifier Options For Term", advancedQualifierOptions_Term);
			
			if(!oParameters.GetParameters("ProcedureGroupCodeSetDD"+i).isEmpty())
			{
				selectOption(procedureGroupCodeSet,"visibletext",oParameters.GetParameters("ProcedureGroupCodeSetDD"+i));
				enter_text_value("Procedure Group Code Set Search Box", procedureGroupCodeSetSearchBox, oParameters.GetParameters("ProcedureGroupCodeSetSB"+i));
				fixed_wait_time(3);
				performKeyBoardAction("ENTER");
				fixed_wait_time(3);
				selectOption(procedureGropuCodeSetEffectivePeriod,"value",oParameters.GetParameters("ProcedureGroupCodeSetEPDD"+i));
			}
			
			if(!oParameters.GetParameters("DiagnosisCodeSetSB"+i).isEmpty())
			{
				enter_text_value("Diagnosis Code Set", diagnosisCodeSetSearchBox, oParameters.GetParameters("DiagnosisCodeSetSB"+i));
				fixed_wait_time(3);
				performKeyBoardAction("ENTER");
				fixed_wait_time(3);
				selectOption(diagnosisCodeSetEffectivePeriod,"value",oParameters.GetParameters("DiagnosisCodeSetEPDD"+i));
			}
			
			if(!oParameters.GetParameters("DRGCodeSetSB"+i).isEmpty())
			{
				enter_text_value("DRG Code Set", drgCodeSetSearchBox, oParameters.GetParameters("DRGCodeSetSB"+i));
				fixed_wait_time(3);
				performKeyBoardAction("ENTER");
				fixed_wait_time(3);
				selectOption(drgEffectivePeriod,"value",oParameters.GetParameters("DRGCodeSetEPDD"+i));
			}
			
			if(!oParameters.GetParameters("FeeScheduleSB"+i).isEmpty())
			{
				enter_text_value("Fee Schedule Set", feeScheduleSearchBox, oParameters.GetParameters("FeeScheduleSB"+i));
				fixed_wait_time(3);
				performKeyBoardAction("ENTER");
				fixed_wait_time(3);
				
				By FeeSchedule = By.xpath("//form[@id='qualificationGroupOptions']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("FeeScheduleSB"+i)+"']");
				
				if(IsDisplayed("Fee Schedule Search Box", FeeSchedule))
					click_button("Fee Schedule Search Value", FeeSchedule);
				
				selectOption(feeScheduleEffectivePeriod,"value",oParameters.GetParameters("FeeScheduleEP"+i));
			}
		}
	}
	
	
	By EmptyQG = By.xpath("//div[@class='clearBoth col-lg-7 col-md-7 col-sm-7 mar-r-13']//div[text()='You must select an option from the search results.']");
    
	By sectionNameFeild = By.xpath("//input[@id='sectionName']");
		
	By sectionCancelButton = By.xpath("//div[@id='addEditRateSheetSection']//input[@id='button.cancelId']");
	
	By sectionQualificationGroupFeild = By.xpath("//form[@id='addEditRateSheetSectionForm']//input[@id='qualificationGroup']");
	
	
	//Method to enter a data in 'Add Section window'.
	public void AddSectionDetails(String i,String rateSheetName)
	{        
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
        String[] sectionName = oParameters.GetParameters("SectionName"+i).split(",");
        
        String[] qualificationName = oParameters.GetParameters("QualificationGroup"+i).split(",");
                
        for(int k=0;k<sectionName.length;k++)
        {
        	if(rateSheetName.isEmpty())
        	{
        		waitFor(respectiveRateSheet,"RateSheet Title Bar");
        		addSectionIcon();
            }      
        	else if(rateSheetName.contains("Edit"))
        		System.out.println("");
            else 
            {
            	searchRateSheet("Production",rateSheetName);
            	addSectionIcon();
            }	
           
        	if(!sectionName[k].isEmpty())
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
               
            	click_button("Select section position", SectionPosition);
            }
        
        	if(oParameters.GetParameters("CopySection"+i).equals("YES"))
        	{
        		if(IsDisplayed("Copy Section check box", copySection))
        		{	
        			click_button("Copy Section check box", copySection);
        			
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
                         
            			if(IsDisplayed("RateSheet Results", RateSheetResults))
            			{
            				By RateSheetName = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 col-mdlg-7 mar-r-0']//ul[@class='dropdown-menu']/li/a[contains(.,'"+oParameters.GetParameters("SearchRateSheetName"+i)+"')]");
            				waitFor(RateSheetName,"RateSheet Details");
            				click_button("RateSheetName",RateSheetName);
            			}                     
            		}
            		
            		if(!oParameters.GetParameters("EffectivePeriod"+i).isEmpty())
            			selectOption(effectiveRateSheet,"value",oParameters.GetParameters("EffectivePeriod"+i));
            
            		if(!oParameters.GetParameters("Section"+i).isEmpty())
            			selectOption(section,"value",oParameters.GetParameters("Section"+i));
                   
            		fixed_wait_time(1);
            		click_button("Include Term Note Section", includeTermNoteSection);
        		}	
        		else
        		{	
        			oReport.AddStepResult("", "Copy Section check box is not found", "FAIL");
        			oParameters.SetParameters("Continued_Execution", "No");
        		}	
        		
        	}
        
        	for(int l=k;l<=k;l++)
        	{
            	if(!oParameters.GetParameters("CopySection"+i).equals("YES"))
            	{
            		if(!qualificationName[l].isEmpty())
            			enter_text_value("Qualification Group", sectionQualificationGroupFeild,qualificationName[l]);
            			fixed_wait_time(2);
            			performKeyBoardAction("ENTER");
            			fixed_wait_time(2);
            		
            		By Qualification = By.xpath("//form[@id='addEditRateSheetSectionForm']//ul[@id='-list']//li//a[not(text())]//b[text()='"+qualificationName[l]+"']");
            		
            		if(IsDisplayed("Qualification Group", Qualification))
            			click_button("Qualification Group", Qualification);            		
            	}
        
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

        		
        		if(rateSheetName.equalsIgnoreCase("Edit"))
        		{}
        		else
        		{
            		if(IsDisplayed("Section Save Button", rateSheetSectionSaveButton))
            		{
            			oReport.AddStepResult("Add Section", "In 'ADD Section Tab' filled all details and verified that save button is enabled", "PASS");
            			click_button("Rate Sheet Save Button", rateSheetSectionSaveButton);
                         
            			fixed_wait_time(4);
            			
            			if(IsDisplayed("Sections List", addedSection))
            			{
            				waitFor(addedSection,"Added Sections");
            	               
            				By SectionName = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[contains(.,'"+sectionName[k]+"')]");
            				
            				if(IsDisplayed("Section Name", SectionName))
            					oReport.AddStepResult("Added Section", "Filled all mandatory fields in Add Section Window and clicked on save button and verified that new '" +sectionName[k]+ "' Section is added  ", "PASS");
            				else
            					oReport.AddStepResult("Added Section", "Filled all mandatory fields in Add Section Window and clicked on save button and verified that new section is not added", "FAIL");
            			}
            			else if(IsDisplayed("Empty QG", EmptyQG))
            			{
            				oReport.AddStepResult("Section Tab", "While creating '"+sectionName[k]+"' section, passing '"+qualificationName[l]+"' as a QG, but this QG is not selecting", "WARNING");
                                
            				click_button("Section Cancel Button", sectionCancelButton);
            				
            				fixed_wait_time(2);
                                
            				click_button("Discard Button", sectionDiscardButton);
            			}
            		}
            		else
            			oReport.AddStepResult("Add Section", "In 'ADD Section Window' filled all details but save button is not enabled", "FAIL");
        		}

        	}        	
        }
	}
	
  
    By rateSheetCodeSaveButton = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//input[@id='button.saveId']");
    
    By rateSheetSectionSaveButton = By.xpath("//div[@class='workflow  modal-medium-tall']//input[@id='button.saveId']");
    
	By rateSheetRespectivePage = By.xpath("//div[@id='ratesheetSection']//div[@class='col-lg-6 col-md-6 col-sm-6 xl-header ng-binding']");
	
	
    //Method to click Section Save Button.
	public void sectionSaveButton(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int j;
		
		if(IsElementEnabled("Section Save Button", rateSheetSectionSaveButton))
		{
			oReport.AddStepResult("Add Section", "In 'ADD Section Tab' filled all details and verified that save button is enabled", "PASS");
			click_button("Rate Sheet Save Button", rateSheetSectionSaveButton);
		
			waitFor(addedSections,"Added Sections");
		
			List<WebElement> Sections = convertToWebElements(addedSections);
		
			for(j=0;j<Sections.size();j++)
			{
				if(Sections.get(j).getText().contains(oParameters.GetParameters("SectionName"+i)))
				{
					oReport.AddStepResult("Added Section", "Filled all mandatory fields in Add Section Window and clicked on save button and verified that new section is added Before " + oParameters.GetParameters("AdddSectionAfterBeforeSearch"), "PASS");
					break;
				}
				else
					System.out.println("Searching for created Section");
			}
			
			if(j>=Sections.size())
				oReport.AddStepResult("Added Section", "Filled all mandatory fields in Add Section Window and clicked on save button and verified that new section is not added", "FAIL");			
		}
		else
			oReport.AddStepResult("Add Section", "In 'ADD Section Tab' filled all details and verified that save button is not enabled", "FAIL");
	}

	
	By addTermWindow1 = By.xpath("//div[@id='addSectionModal']//div[@title='Add Stop Loss Term']");
	
	
	//Method to click add term icon.
	public void clickAddTerm(String sectionName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(sectionName.isEmpty())
		{	
			if(IsDisplayed("Add Term Button", addTerm))
				click_button("Add Term Button", addTerm);
			else if(IsDisplayed("Add Stop Loss Term", stoplossAddTermButton))
				click_button("Add Stop Loss Term Button", stoplossAddTermButton);
		}
		else
		{
			By AddTermIcon = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4'][contains(.,'"+sectionName+"')]/..//div[@class='pull-right icon-container']//a[1]//i[1]");
			
			if(IsDisplayed("Add Term Icon Button", AddTermIcon))
				click_button("Add Term Button", AddTermIcon);			
			
			By AddTermIcon1 = By.xpath("//div[@class='large-height scroll-auto col-lg-12 col-md-12 col-sm-12'][contains(.,'"+sectionName+"')]/..//div[@class='pull-right icon-container']//a[1]//i[1]");
					
			if(IsDisplayed("Add Term Icon Button", AddTermIcon1))	
				click_button("Add Term Button", AddTermIcon1);		
		}
		
		if(IsDisplayed("Add Term Window", addTermWindow) || IsDisplayed("Add StopLoss Term Window", addTermWindow1))
			oReport.AddStepResult("Add Term Window", "Clicked on 'Add Term Icon' and verified that 'Add Term Window' is displayed", "PASS");
		else
			oReport.AddStepResult("Add Term Window", "Clicked on 'Add Term Icon' and verified that 'Add Term Window' is not displayed", "FAIL");
	}
	
	
	//Method to identify section.
	public void checkForSections(String sectionName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int j;
		if(!IsDisplayed("Sections List", addedSections))
		{
			AddSectionDetails("","");
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
			if(j>Sections.size())
			{
				AddSectionDetails("","");
			}
		}	
	}
	
	
	By qualificationGroupField = By.xpath("//workflow-modal[@id='addIpTerm']//input[@id='qualificationGroup']");
	
	By qualificationGroupFeildExclusions = By.xpath("//workflow-modal[@id='addExTerm']//input[@id='qualificationGroup']");
	
	By addTermAfter_Before = By.xpath("//workflow-modal[@id='addIpTerm']//span[@class='dropdown-text hide-overflow ng-binding']");
	
	By addTermAfterBeforeSearchFeild = By.xpath("//input[@id='placingTerm']");
	
	By stopLossQG = By.xpath("//workflow-modal[@id='showAddSectionTermModal']//input[@id='qualifierExp1']");
	
	
	//Method to enter a Term Details.
	public void addTermButton(String i,String rateSheetName,String sectionName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		searchRateSheet(oParameters.GetParameters("TypeOfRateSheet"+i),rateSheetName);
		
		if(oParameters.GetParameters("TermType"+i).equals("Terms"))
			checkForSections(sectionName);
		
		if(oParameters.GetParameters("TermType"+i).equals("StopLoss"))
		{
			clickAddTerm(sectionName);
			stopLossTab(i);
		}
						
		else if(oParameters.GetParameters("SectionName"+i).isEmpty() && oParameters.GetParameters("TermType"+i).equals("Exclusions"))
			ExclusionTab(String.valueOf(i)); 
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
			enter_text_value("Qualification Field", qualificationGroupField, oParameters.GetParameters("QualificationGroup"+i));
			fixed_wait_time(2);
			performKeyBoardAction("ENTER");
			fixed_wait_time(2);
		
			By Qualification_Term = By.xpath("//form[@id='addEditTermForm']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("QualificationGroup"+i)+"']");
		
			if(IsDisplayed("Qualification Term", Qualification_Term))
				click_button("Qualification Term", Qualification_Term);
		
			advancedQualifierOption(i);		
		}	
	}
	
	
	By AddExclusionTermTab = By.xpath("//div[@id='showExclusionModal']//div[@title='Add Exclusion Term']");
	
	
	//Method to enter a Form/Through and Amount Values.
	public void formThroughValue(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
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
        	else if((oParameters.GetParameters("RateType"+i).equals("Additive Tiered") || oParameters.GetParameters("RateType"+i).equals("Tiered")) && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow))		
        		fromValue = By.xpath("//form[@id='addStopLossTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li[" + (j+2) +"]//div[@spec='formTierModel.start.spec']//input[@id='']");
        	else if(oParameters.GetParameters("RateType"+i).equals("Procedure Group") || oParameters.GetParameters("RateType"+i).equals("Tiered"))
        		fromValue = By.xpath("//li[" + (j+2) + "]//div[@spec='formTierModel.start.spec']//input[@id='']");

        	if(j > 0)
        	{
        		click_button("From Value", fromValue);
        		
        		if(Float.parseFloat(oParameters.GetParameters("ThroughValue")) < Float.parseFloat(get_field_value("From Value",fromValue)))
        			oReport.AddStepResult("From/Through value", "Entered From and Through values then clicked on add icon button verified that new row is added and From value started with next value", "PASS");
        		else
        			oReport.AddStepResult("From/Through value", "Entered From and Through values then clicked on add icon button verified that new row is added and From value is not started with next value", "FAIL");
        	}        	
        	
        	enter_text_value("From Values", fromValue, fromValues[j]);
        	oParameters.SetParameters("FromValue", get_field_value("From Value", fromValue));        	
       	
        	for(int k=j;k<=j;k++) 
        	{
        		By throughValue = null;
                     
        		if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Term Window", addTermWindow))
        			throughValue = By.xpath("//form[@id='addEditTermForm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//div[@spec='formTierModel.stop.spec']//input[@id='']");
        		else if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered") && IsDisplayed("Add Exclusion Term", AddExclusionTermTab))
        			throughValue = By.xpath("//form[@id='addEditExclusionTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//div[@spec='formTierModel.stop.spec']//input[@id='']");
        		else if((oParameters.GetParameters("RateType"+i).equals("Additive Tiered") || oParameters.GetParameters("RateType"+i).equals("Tiered")) && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow))
        			throughValue = By.xpath("//form[@id='addStopLossTerm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li["+ (j+2) +"]//div[@spec='formTierModel.stop.spec']//input[@id='']");
        		else if(oParameters.GetParameters("RateType"+i).equals("Procedure Group") || oParameters.GetParameters("RateType"+i).equals("Tiered"))
        			throughValue = By.xpath("//li[" + (j+2) + "]//div[@spec='formTierModel.stop.spec']//input[@id='']");
                     
        		try 
        		{
        			enter_text_value("Through Values", throughValue, throughValues[k]);
        			oParameters.SetParameters("ThroughValue", get_field_value("Through Value", throughValue)); 
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
        			else if((oParameters.GetParameters("RateType"+i).equals("Additive Tiered") || oParameters.GetParameters("RateType"+i).equals("Tiered")) && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow))
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
        		else if((oParameters.GetParameters("RateType"+i).equals("Additive Tiered") || oParameters.GetParameters("RateType"+i).equals("Tiered")) && IsDisplayed("Add Stop Loss Term", addStopLossTermWindow))
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
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int columnCount = oExcelData.getColumnCount(sheetName,"C:\\CCM\\SupportingFiles\\"+excelName+"");
		
		String [] columnDataArray = new String[columnCount];
		
		for(int k=0; k<columnCount;k++)
        {                                   
              String cellData = oExcelData.getCellData(sheetName, k, 2, "C:\\CCM\\SupportingFiles\\"+excelName+"");
              columnDataArray[k]= cellData;
        }
		
		String columnDataString = String.join(",", columnDataArray).replace(",", " ");
		
		if(columnDataString.contains("MaxRateType"))
		{		
			selectOption(maximumRateType_Term,"visibletext",oParameters.GetParameters("MaxRateType"+i));
		
			if(!oParameters.GetParameters("MaxRateType"+i).isEmpty())
				enter_text_value("MaxRate Amount/Percentage", maxRateAmount, oParameters.GetParameters("MaxRateAmount/Percentage"+i));
		
			if(oParameters.GetParameters("MaxRateType"+i).equals("Percentage"))
			{
				if(IsElementDisplayed("Percentage Basis", percentageBasis))
					selectOption(percentageBasis,"visibletext",oParameters.GetParameters("PercentageBasis"+i));
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
	
	
	By stoplossAddTermButton = By.xpath("//div[@id='ratesheetStoploss']//li[@class='ng-scope'][1]//div[@class='pull-right icon-container']//a[1]//i[1]");
	
	By TermType = By.xpath("//select[@id='typeName']");
	
	By threshold = By.xpath("//input[@id='thresholdAmount']");
	
	
	public void stopLossTab(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
/*		click_button("Stop Loss", stopLossLink);		
		
		click_button("Stop Loss Add Term", stoplossAddTermButton);*/
		
		enter_text_value("Term Name", termName, oParameters.GetParameters("TermName"+i));
		
		selectOption(TermType,"visibletext",oParameters.GetParameters("TypeDD"+i));
		
		enter_text_value("Threshold", threshold, oParameters.GetParameters("Threshold"+i));
		
		if(oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_QualificationGroups_StopLoss_Formula_Expression"))
			enter_text_value("StopLoss Qualification", stopLossQG,"BCBS" /*oParameters.GetParameters("QualificationGroupName")*/);
		else
			enter_text_value("StopLoss Qualification", stopLossQG, oParameters.GetParameters("QualificationGroup"+i));
		
		
		performKeyBoardAction("ENTER");
		
		fixed_wait_time(2);

	  By Qualification = By.xpath("//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("QualificationGroup"+i)+"']");
		
		if(IsDisplayed("Qualification List", Qualification))
			click_button("Qualification Group", Qualification);

	}
		
	
	By rateTypeSearchBox = By.xpath("//form[@id='addEditTermForm']//div[@class='col-lg-8 col-md-8 col-sm-8 ng-isolate-scope']//select[@ng-model='model']");
	
	By rateTypeSearchBox_Stoploss = By.xpath("//form[@id='addStopLossTerm']//div[@class='col-lg-8 col-md-8 col-sm-8 ng-isolate-scope']//select[@ng-model='model']");
	
	By rateTypeSearchBox_ExclusionTerm = By.xpath("//form[@id='addEditExclusionTerm']//select[@id='']");
	
	By tierBasis = By.xpath("//select[@id='tierBasis']");
	
	By maximumRateType_Term = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//select[@id='maxRateType']");
	
	By minimumRateType = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//select[@id='minRateType']");
	
	By percentage = By.xpath("//input[@id='rateAmount']");
	
	By percentageBasisDD = By.xpath("//select[@id='percentageBasis']");
	
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
	
	By feeScheduleSearchField = By.xpath("//input[@id='fsMaster']");
	
	By feeSchedulePeriod = By.xpath("//select[@id='fsPeriod']");
	
	By modifierSchedule = By.xpath("//input[@id='fsModDiscount']");
	
	By useRelatedProcedureDiscountingCheckBox = By.xpath("//input[@id='cnCheckbox_0VK']");
	
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
	
	By formulaNameSB = By.xpath("//input[@id='formulaName']");
	
	By groupCodeOverrideDD = By.xpath("//select[@id='grouperCodeOverride']");
	
	By percentageBasisField = By.xpath("//select[@id='percentageBasis']");
	
	By useRelatedProcedureDiscountingCheckBox_ExclusionTerm = By.xpath("//label[contains(.,'Use Related Procedure Discounting')]//input");
	
	By sectionForThisExclusion = By.xpath("//form[@id='addEditExclusionTerm']//span[@toggle-target='selectTermSection']");
	
	By firstFeeScheduleSearchValue = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//input[@id='fsMaster']//../ul[@id='-list']/li[1]");
	
	By firstModifierScheduleSearchValue = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//input[@id='fsModDiscount']//../ul[@id='-list']/li[1]");
	
	By HCPCSGroupCodeFirstSearchValue = By.xpath("//div[@model='term.rate.procedureGroupMasterID']//ul/li[1]");
	
	
	//Method to enter a data in add term window.
	public void selectRateType(String i)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(oParameters.GetParameters("TermType"+i).equals("Terms"))
			selectOption(rateTypeSearchBox,"visibletext",oParameters.GetParameters("RateType"+i));
		else if(oParameters.GetParameters("TermType"+i).equals("StopLoss"))
			selectOption(rateTypeSearchBox_Stoploss,"visibletext",oParameters.GetParameters("RateType"+i));
		else if(oParameters.GetParameters("TermType"+i).equals("Exclusions"))
			selectOption(rateTypeSearchBox_ExclusionTerm,"visibletext",oParameters.GetParameters("RateType"+i));
		
		if(oParameters.GetParameters("RateType"+i).equals("Additive Tiered"))
		{
			if(IsDisplayed("Add Term", addTermWindow))
			{
				if(IsDisplayed("Tier Basis Drop down", tierBasis))
				{
					oReport.AddStepResult("Additive Tiered Rate Type", "In 'Add Term' window filled madatory fields then selected 'Additive Tiered' as Rate Type, verified that Table Basis drop down displayed", "PASS");
				
					selectOption(tierBasis,"visibletext",oParameters.GetParameters("TireBasis"+i));
					
					if(!(oParameters.GetParameters("TireBasis"+i).equals("Hours") || oParameters.GetParameters("TireBasis"+i).equals("Minutes")))
					{
						if(oParameters.GetParameters("TireBasis"+i).equals("Total Covered Charge Amount") || oParameters.GetParameters("TireBasis"+i).equals("Daily covered charge amount"))
						{
							oReport.AddStepResult("Additive Tiered Rate Type", "In 'Add Term' window filled madatory fields then selected 'Additive Tiered' as Rate Type and Tier Basis as "+oParameters.GetParameters("TireBasis"+i)+", verified that Table Basis Rate Type drop down displayed", "PASS");
							
							selectOption(tierBasisRateType,"visibletext",oParameters.GetParameters("TierBasisRateType"+i));
						}
						else
							oReport.AddStepResult("Additive Tiered Rate Type", "In 'Add Term' window filled madatory fields then selected 'Additive Tiered' as Rate Type and Tier Basis as "+oParameters.GetParameters("TireBasis"+i)+" but that Table Basis Rate Type drop down is not displayed", "FAIL");
					}				
				}
				else
					oReport.AddStepResult("Additive Tiered Rate Type", "In 'Add Term' window filled madatory fields then selected 'Additive Tiered' as Rate Type but table Basis drop down is not displayed", "FAIL");			
			}
			else if(IsDisplayed("StopLoss Window",addStopLossTermWindow ))
			{
					selectOption(tierBasisRateType,"visibletext",oParameters.GetParameters("TierBasisRateType"+i));
			}
			else if(IsDisplayed("Add Exclusion Term", AddExclusionTermTab))
			{
				if(IsDisplayed("Tier Basis Drop down", tierBasis))
				{
					oReport.AddStepResult("Additive Tiered Rate Type", "In 'Add Exclusion Term' window filled madatory fields then selected 'Additive Tiered' as Rate Type, verified that Table Basis drop down displayed", "PASS");
				
					selectOption(tierBasis,"visibletext",oParameters.GetParameters("TireBasis"+i));
					selectOption(tierBasisRateType,"visibletext",oParameters.GetParameters("TierBasisRateType"+i));
				}
				else
					oReport.AddStepResult("Additive Tiered Rate Type", "In 'Add Exclusion Term' window filled madatory fields then selected 'Additive Tiered' as Rate Type but table Basis drop down is not displayed", "FAIL");
			}
			formThroughValue(i);
		}	
		else if(oParameters.GetParameters("RateType"+i).equals("Formula"))
		{
			
			if(IsDisplayed("Formula Name Search Box", formulaNameSB))	
			{	
				//stop loss formula expression QG vr
				if(oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_QualificationGroups_StopLoss_Formula_Expression"))
					enter_text_value("Formula Name Search Box", formulaNameSB,oParameters.GetParameters("QualificationGroupName"));
				else
					enter_text_value("Formula Name Search Box", formulaNameSB, oParameters.GetParameters("FormulaNameSB"+i));
			}
			else
				oReport.AddStepResult("Formula Rate Type", "In 'Add Stop Loss Term' window filled mandatory fields then selected 'Formula' as Rate Type but Formula name search box is not displayed ", "FAIL");
			
			if(!oParameters.GetParameters("GroupCodeOverride"+i).isEmpty())
				selectOption(groupCodeOverrideDD,"visibletext",oParameters.GetParameters("TableBasis"+i));		
		}
		else if(oParameters.GetParameters("RateType"+i).equals("APC/APG Pricer"))
		{
			if(IsDisplayed("Percentage text box", percentage))
			{
				oReport.AddStepResult("Percentage text box", "In 'Add Term' window filled madatory fields then selected 'APC/APG Price' as Rate Type, verified that percentage text box displayed", "PASS");
				
				enter_text_value("Percentage",percentage , oParameters.GetParameters("Percentage"+i));
			}	
			else
				oReport.AddStepResult("Percentage text box", "In 'Add Term' window filled madatory fields then selected 'APC/APG Price' as Rate Type but that percentage text box is not displayed", "FAIL");
		}		
		else if(oParameters.GetParameters("RateType"+i).equals("By Revenue Code"))
		{
			if(IsElementDisplayed("Table Basis Drop down", tableBasis))
			{
				oReport.AddStepResult("Table Basis", "In 'Add Term' window filled madatory fields then selected 'By Revenue Code' as Rate Type, verified that Table Basis drop down displayed", "PASS");
				
				selectOption(tableBasis,"visibletext",oParameters.GetParameters("TableBasis"+i));
			
				String[] revenueCode = oParameters.GetParameters("RevenueCodeExpression"+i).split(",");
			
				String[] Amount_Percentage = oParameters.GetParameters("Amount/Percentage"+i).split(",");
			
				for(int j=0;j<revenueCode.length;j++)
				{
					By ByRevenueCode_revenueCodeExpresstion = By.xpath("//input[@id='revCodeRateEntry"+j+"']");
				
					enter_text_value("Revenue Code Expresstion", ByRevenueCode_revenueCodeExpresstion, revenueCode[j]);
					
					By Revenue = By.xpath("//div[@id='revCodeRateEntry0']//ul[@id='-list']//li//a[not(text())]//b[text()='"+revenueCode[j]+"']");
					
					if(IsDisplayed("Revenue Expresstion", Revenue))
						click_button("Revenue Expression", Revenue);
				
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
			else
			{
				oReport.AddStepResult("Table Basis", "In 'Add Term' window filled madatory fields then selected 'By Revenue Code' as Rate Type but that Table Basis drop down is not displayed", "FAIL");
				oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			}
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Dosage Quantity"))
		{
			if(IsDisplayed("Rate Amount", rateAmount)&& IsDisplayed("Quantity Rounding Dropdown", QuantityRoundingMethod))
			{
				oReport.AddStepResult("Quantity Rounding Dropdown", "In 'Add Term' window filled all the mandatory fields selected 'Dosage Quantity' as Rate Type, verified that Quantity Rounding method drop down is displayed", "PASS");
				
				enter_text_value("Rate Amount", rateAmount, oParameters.GetParameters("RateAmount"+i));
				
				selectOption(QuantityRoundingMethod,"visibletext",oParameters.GetParameters("QuantityRoundingMethod"+i));				
			}
			else
				oReport.AddStepResult("Quantity Rounding Dropdown", "In 'Add Term' window filled all the mandatory fields selected 'Dosage Quantity' as Rate Type but Quantity Rounding method drop down is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("DRG Pricer"))
		{
			if(IsDisplayed("DRG Pricer Percentage", DRG_percentage))
			{
				oReport.AddStepResult("DRG Pricer Percentage", "In 'Add Term' window filled all the mandatory fields selected 'DRG Price' as Rate Type, verified that Percentage text box displayed", "PASS");
				
				enter_text_value("DRG Pricer Percentage", DRG_percentage, oParameters.GetParameters("Percentage"+i));
				
				enter_text_value("Additional Flat Amount", DRG_additionalFlatAmount, oParameters.GetParameters("AdditionalFlatAmount"+i));		
			}
			else
				oReport.AddStepResult("DRG Pricer Percentage", "In 'Add Term' window filled all the mandatory fields selected 'DRG Price' as Rate Type but Percentage text box is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Dialysis PPS Rate"))
		{
			if(IsDisplayed("Dailysis PPS Rate", percentage) && IsDisplayed("Dailysis PPS Rate Factor", dailysisPPSRateFactor))
			{
				oReport.AddStepResult("Dailysis PPS Rate and Rate Factor", "In 'Add Term' window filled all the mandatory fields selected 'Dailysis PPS Rate' as Rate Type, verified that Percentage text box and Dialysis PPS Rate Factors search box displayed", "PASS");
				
				enter_text_value("Dailysis PPS Rate", percentage, oParameters.GetParameters("Percentage"+i));
				
				enter_text_value("Dailysis PPS Rate Factor", dailysisPPSRateFactor, oParameters.GetParameters("DialysisPPSRateFactors"+i));				
			}
			else
				oReport.AddStepResult("Dailysis PPS Rate and Rate Factor", "In 'Add Term' window filled all the mandatory fields selected 'Dailysis PPS Rate' as Rate Type but Percentage text box and Dialysis PPS Rate Factors search box is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("DRG User"))
		{
			if(IsDisplayed("DRG User Rate Set", DRGUserrateSet))
			{
				oReport.AddStepResult("DRG User Rate Set", "In 'Add Term' window filled all the mandatory fields selected 'DRG User' as Rate Type, verified that DRG user rate set search box displayed", "PASS");				
				
				enter_text_value("DRG User Rate Set", DRGUserrateSet, oParameters.GetParameters("DRGUserRateSet"+i));
				
				if(IsDisplayed("DRG User Set Period", DRGUserSetPeriod))
					selectOption(DRGUserSetPeriod,"visibletext",oParameters.GetParameters("DRGUserRateSetPeriod"+i));
				
				if(IsDisplayed("Group Code Override", groupCodeOverride))
					selectOption(groupCodeOverride,"visibletext",oParameters.GetParameters("GroupCodeOverride"+i));
				
				if(IsDisplayed("Calculation Method", calculationMethod))
					selectOption(calculationMethod,"visibletext",oParameters.GetParameters("CalculationMethod"+i));
				
				if(oParameters.GetParameters("CalculationMethod"+i).equals("DRG Formula Method"))
				{	
					enter_text_value("Formula Name", formulaName, oParameters.GetParameters("FormulaName"+i));
					
					By FormulaList = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("FormulaName"+i)+"']");
				
					if(IsDisplayed("Formula List", FormulaList))
						click_button("Formula List Name", FormulaList);
				}
				
				if(!oParameters.GetParameters("Percentage"+i).isEmpty())
					enter_text_value("DRG User Percentage", DRG_percentage, oParameters.GetParameters("Percentage"+i));
				
				if(!oParameters.GetParameters("AdditionalFlatAmount"+i).isEmpty())
					enter_text_value("DRG Additional flat Amount", DRG_additionalFlatAmount, oParameters.GetParameters("AdditionalFlatAmount"+i));
			}
			else
				oReport.AddStepResult("DRG User Rate Set", "In 'Add Term' window filled all the mandatory fields selected 'DRG User' as Rate Type but DRG user rate set search box is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Fee Schedule"))
		{
			if(IsDisplayed("Fee Schedule Search Field", feeScheduleSearchField))
			{
				oReport.AddStepResult("Fee Schedule Search Field", "In 'Add Term' window filled all the mandatory fields selected 'Fee Schedule' as Rate Type, verified that Fee Schedule search box displayed", "PASS");
				
				if(!oParameters.GetParameters("FeeSchedule"+i).isEmpty())
				{
					enter_text_value("Fee Schedule Search Field", feeScheduleSearchField, oParameters.GetParameters("FeeSchedule"+i));
					fixed_wait_time(2);
					performKeyBoardAction("ENTER");
					fixed_wait_time(3);	
					
					By FeeSchedule = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("FeeSchedule"+i)+"']");

					if(IsDisplayed("Fee Schedule", FeeSchedule))
						click_button("Fee Schedule", FeeSchedule);
				}
				else 
				{
					click_button("Fee Schedule Search Field", feeScheduleSearchField);
					performKeyBoardAction("ENTER");
					click_button("First fee schedule search result value", firstFeeScheduleSearchValue);
				}
								
				oReport.AddStepResult("Fee Schedule Search field", "Fee Schedule selected", "SCREENSHOT");
				fixed_wait_time(2);
				
				enter_text_value("Percent of Fee Schedule", rateAmount, oParameters.GetParameters("PercentOfFeeSchedule"+i));
				fixed_wait_time(3);
			
				if(!oParameters.GetParameters("FeeSchedulePeriod"+i).isEmpty())
					selectOption(feeSchedulePeriod,"value",oParameters.GetParameters("FeeSchedulePeriod"+i));
				
				if(!oParameters.GetParameters("ModifierSchedule"+i).isEmpty())
				{
					enter_text_value("Modifier Schedule", modifierSchedule, oParameters.GetParameters("ModifierSchedule"+i));
				
					By modifierSchedule = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("ModifierSchedule"+i)+"']");
					
					if(IsDisplayed("Modifier Schedule", modifierSchedule))
						click_button("Modifier Schedule", modifierSchedule);
				}
/*				else
				{
					click_button("Modifier Schedule", modifierSchedule);
					performKeyBoardAction("ENTER");
					click_button("First modifier search result value", firstModifierScheduleSearchValue);					
				}*/
				
				if(oParameters.GetParameters("UseRelatedProcedureSchedule"+i).equals("YES"))
				{
					if(oParameters.GetParameters("TermType"+i).equals("Exclusions") && oParameters.GetParameters("UseRelatedProcedureSchedule"+i).equals("YES"))
						click_button("Use Related Procedure Discounting Checkbox Exclusion Term", useRelatedProcedureDiscountingCheckBox_ExclusionTerm);
					else if(oParameters.GetParameters("TermType"+i).equals("Terms") && oParameters.GetParameters("UseRelatedProcedureSchedule"+i).equals("YES"))
						click_button("Use Related Procedure Discounting Checkbox", useRelatedProcedureDiscountingCheckBox);
					
					if(IsDisplayed("Related Procedure Schedule", relatedProcedureSchedule))
					{
						oReport.AddStepResult("Related Procedure Schedule", "In 'Add Term' window clicked on Use Related Procedure Discounting check box, verified that Related Procedure Schedule search box displayed", "PASS");
						
						if(!oParameters.GetParameters("SearchRelatedProcedureSchedule"+i).isEmpty())
							enter_text_value("Related Procedure Schedule", relatedProcedureSchedule, oParameters.GetParameters("SearchRelatedProcedureSchedule"+i));						
					}
					else
						oReport.AddStepResult("Related Procedure Schedule", "In 'Add Term' window clicked on Use Related Procedure Discounting check box but Related Procedure Schedule search box is not displayed", "FAIL");
				}				
			}
			else
				oReport.AddStepResult("Fee Schedule Search Field", "In 'Add Term' window filled all the mandatory fields selected 'Fee Schedule' as Rate Type but Fee Schedule search box is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("IRF CMG Pricer"))
		{
			if(IsDisplayed("CMG User Rate Set", cmgUserRateSet) && IsDisplayed("CMG Provider Values Set", cmgProviderValuesSet))
			{
				oReport.AddStepResult("CMG User Rate Set and Provider Values Set", "In 'Add Term' window filled all the mandatory fields selected 'IRF CMG Pricer' as Rate Type, verified that CMG User Rate Set and CMG Provider Values Set search box displayed", "PASS");
				
				enter_text_value("CMG User Rate Set", cmgUserRateSet, oParameters.GetParameters("CMGUserRateSet"+i));
				
				if(!oParameters.GetParameters("CMGUserRateSetPeriod"+i).isEmpty())
					selectOption(cmgUserRateSetPeriod,"value",oParameters.GetParameters("CMGUserRateSetPeriod"+i));
				
				enter_text_value("CMG Provider Values Set", cmgProviderValuesSet, oParameters.GetParameters("CMGProviderValuesSet"+i));
				
				if(!oParameters.GetParameters("CMGProviderValuesSetPeriod"+i).isEmpty())
					selectOption(cmgProviderValuesSetPeriod,"value",oParameters.GetParameters("CMGProviderValuesSetPeriod"+i));
				
				if(!oParameters.GetParameters("Percentage"+i).isEmpty())
					enter_text_value("Percentage", DRG_percentage, oParameters.GetParameters("Percentage"+i));
				
				if(!oParameters.GetParameters("AdditionalFlatAmount"+i).isEmpty())
					enter_text_value("Additional Flat Amount", DRG_additionalFlatAmount, oParameters.GetParameters("AdditionalFlatAmount"+i));	
			}
			else
				oReport.AddStepResult("CMG User Rate Set and Provider Values Set", "In 'Add Term' window filled all the mandatory fields selected 'IRF CMG Pricer' as Rate Type but CMG User Rate Set and CMG Provider Values Set search box is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Per Case") 
					|| oParameters.GetParameters("RateType"+i).equals("Per Diem") 
						|| oParameters.GetParameters("RateType"+i).equals("Per Hour") 
							|| oParameters.GetParameters("RateType"+i).equals("Per Length of Stay")
								|| oParameters.GetParameters("RateType"+i).equals("Per Minute")
									|| oParameters.GetParameters("RateType"+i).equals("Per Service")
										|| oParameters.GetParameters("RateType"+i).equals("PPS Professional Rate"))
		{
			if(IsElementDisplayed("Rate Amount", rateAmount))
			{
				oReport.AddStepResult("Rate Amount", "In 'Add Term' window filled all the mandatory fields selected '"+oParameters.GetParameters("RateType"+i)+"' as Rate Type, verified that Rate Amount text box displayed", "PASS");
				
				enter_text_value("Rate Amount", rateAmount, oParameters.GetParameters("RateAmount/Percentage"+i));
			}
			else
				oReport.AddStepResult("Rate Amount", "In 'Add Term' window filled all the mandatory fields selected '"+oParameters.GetParameters("RateType"+i)+"' as Rate Type but Rate Amount text box is not displayed", "FAIL");
		}	
		else if(oParameters.GetParameters("RateType"+i).equals("Percentage"))
		{
			enter_text_value("Rate Amount", rateAmount, oParameters.GetParameters("RateAmount/Percentage"+i));
			
			if(oParameters.GetParameters("TermType"+i).equals("StopLoss"))
				selectOption(percentageBasisDD,"visibletext",oParameters.GetParameters("PercentageBasis"+i));			
			
			if(oParameters.GetParameters("TermType"+i).equals("Exclusions"))
				selectOption(percentageBasisField,"visibletext",oParameters.GetParameters("PercentageBasis"+i));
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Procedure Group"))
		{
			if(IsDisplayed("Procedure to use drop down", procedureToUse) && IsDisplayed("Group Code Rate Set search box", groupCodeRateSet))
			{
				oReport.AddStepResult("", "In 'Add Term' window filled all the mandatory fields selected 'Procedure Group' as Rate Type, verified that Procedures to Use drop down and Group Code Rate Set search box displayed", "PASS");
				
				enter_text_value("Percentage", rateAmount, oParameters.GetParameters("Percentage"+i));
				
				selectOption(procedureToUse,"visibletext",oParameters.GetParameters("ProcedureToUser"+i));
				
				if(oParameters.GetParameters("ProcedureToUser"+i).equals("ICD Claim Level Only"))
				{	
					if(IsDisplayed("ICD Code Set", HCPCS_icdCodeSet))
					{
						oReport.AddStepResult("ICD Code Set", "In 'Add Term' window selected 'Procedure Group' as Rate Type then selected 'ICD Claim Level Only' as Procedures to Use, verified that ICD code Set search box displayed", "PASS");
						
						selectOption(icdProcedureVersionOverride,"visibletext",oParameters.GetParameters("ICDProceduresVersionOverride"+i));
						
						enter_text_value("ICD Code Set", HCPCS_icdCodeSet, oParameters.GetParameters("ICDCodeSet"+i));
						fixed_wait_time(3);
						
						By ICdCode = By.xpath("//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("ICDCodeSet"+i)+"']");
						 
						if(IsDisplayed("ICD Code Set", ICdCode))
							click_button("ICD Code Set", ICdCode);
					
						if(oParameters.GetParameters("ICDCodeSetPeriod"+i).isEmpty())
							selectOption(HCPCS_IcdCodeSetPeriod,"value",oParameters.GetParameters("ICDCodeSetPeriod"+i));
							fixed_wait_time(2);
					}
					else
						oReport.AddStepResult("ICD Code Set", "In 'Add Term' window selected 'Procedure Group' as Rate Type then selected 'ICD Claim Level Only' as Procedures to Use but ICD code Set search box is not displayed", "FAIL");
				}
				else
				{
					if(IsDisplayed("HCPCS/CPT Code Set", HCPCS_icdCodeSet))
					{
						oReport.AddStepResult("HCPCS/CPT Code Set", "In 'Add Term' window selected 'Procedure Group' as Rate Type then selected 'HCPCS/CPT Claim Line Level Procedures Only' as Procedures to Use, verified that HCPCS/CPT Code Set search box displayed", "PASS");						
						
						if(!oParameters.GetParameters("HCPCS/CPTCodeSet"+i).isEmpty())
						{
							enter_text_value("HCPCS/CPT Code Set", HCPCS_icdCodeSet, oParameters.GetParameters("HCPCS/CPTCodeSet"+i));
							fixed_wait_time(3);
							
							By HPCS_CPT = By.xpath("//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("HCPCS/CPTCodeSet"+i)+"']");
							
							if(IsDisplayed("HCPCS/CPT Code Set", HPCS_CPT))
								click_button("HCPCS/CPT", HPCS_CPT);
						}
						else
						{
							click_button("HCPCS/CPT Code Set search box", HCPCS_icdCodeSet);
							performKeyBoardAction("ENTER");
							click_button("HCPCS Code Set first search value", HCPCSGroupCodeFirstSearchValue);	
						}
						
						if(!oParameters.GetParameters("HCPCS/CPTCodeSetPeriod"+i).isEmpty())
							selectOption(HCPCS_IcdCodeSetPeriod,"value",oParameters.GetParameters("HCPCS/CPTCodeSetPeriod"+i));	
							fixed_wait_time(2);
					}
					else
						oReport.AddStepResult("ICD Code Set", "In 'Add Term' window selected 'Procedure Group' as Rate Type then selected 'HCPCS/CPT Claim Line Level Procedures Only' as Procedures to Use but HCPCS/CPT Code Set search box is not displayed", "FAIL");
				}
				
				enter_text_value("Group Code Rate Set", groupCodeRateSet, oParameters.GetParameters("GroupCodeRateSet"+i));
				
				if(!oParameters.GetParameters("GroupCodeRateSet"+i).isEmpty())
					selectOption(gropuCodeRateSetPeriod,"value",oParameters.GetParameters("GroupCodeRateSetPeriod"+i));
				
				if(!oParameters.GetParameters("ModifierSchedule"+i).isEmpty())
					enter_text_value("Modifier Schedule", modifierSchedule, oParameters.GetParameters("ModifierSchedule"+i));
				
				if(oParameters.GetParameters("SeperateBilateralProcedures"+i).equals("YES"))
					click_button("Bilateral Procedure CheckBox", bilateralProcedure);
				
				if(!oParameters.GetParameters("MultipleProcedureRules"+i).isEmpty())
				{	
					selectOption(discountPriorityBasis,"visibletext",oParameters.GetParameters("MultipleProcedureRules"+i));
					formThroughValue(i);
				}				
				
				if(!oParameters.GetParameters("UngroupedProcedureRate"+i).isEmpty())
				{
					selectOption(ungroupedProcedureRate,"visibletext",oParameters.GetParameters("UngroupedProcedureRate"+i));
					enter_text_value("Group Code Rate Amount", groupCodeRateAmount, oParameters.GetParameters("RateAmount/Percentage"+i));
				}
				
				if(!oParameters.GetParameters("IncludeUngroupedProceduresInMultipleProcedureRules"+i).isEmpty())
					selectOption(includeUngroupedProcedureinMultipleProcedure,"visibletext",oParameters.GetParameters("IncludeUngroupedProceduresInMultipleProcedureRules"+i));	
			}
			else
				oReport.AddStepResult("", "In 'Add Term' window filled all the mandatory fields selected 'Procedure Group' as Rate Type but Procedures to Use drop down and Group Code Rate Set search box is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Revenue Code Per Day or Per Case"))
		{
			if(IsDisplayed("Revenue Code Amount Field", revenueCode) && IsDisplayed("Basis Drop down", revenueCodeRateBasis))
			{
				oReport.AddStepResult("Revenue Code Amount Field", "In 'Add Term' window filled all the mandatory fields selected 'Revenue Code Per Day or Per Case' as Rate Type, verified that Amount text box and Basis drop down displayed", "PASS");
				
				enter_text_value("Revenue Code Amount Field", revenueCode, oParameters.GetParameters("Amount"+i));
				
				selectOption(revenueCodeRateBasis,"visibletext",oParameters.GetParameters("Basis"+i));
				
				enter_text_value("Percentage limit", percentageLimit, oParameters.GetParameters("PercentageLimit"+i));				
			}
			else
				oReport.AddStepResult("Revenue Code Amount Field", "In 'Add Term' window filled all the mandatory fields selected 'Revenue Code Per Day or Per Case' as Rate Type but Amount text box and Basis drop down is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("RUG User"))
		{
			if(IsDisplayed("RUG User Rate Set ", rugUserRateSet))
			{
				oReport.AddStepResult("RUG User Rate Set ", "In 'Add Term' window filled all the mandatory fields selected 'RUG User' as Rate Type, verified that RUG User Rate Set search box displayed", "PASS");
				
				enter_text_value("RUG User Rate Set ", rugUserRateSet, oParameters.GetParameters("RUG_UserRateSet"+i));
				
				selectOption(rugUserRatePeriod,"value",oParameters.GetParameters("RUG_UserRateSetPeriod"+i));
				
				enter_text_value("RUG User Percentage", DRG_percentage, oParameters.GetParameters("Percentage"+i));
				
				enter_text_value("RUG Additional Flat Amount", DRG_additionalFlatAmount, oParameters.GetParameters("AdditionalFlatAmount"+i));				
			}
			else
				oReport.AddStepResult("RUG User Rate Set ", "In 'Add Term' window filled all the mandatory fields selected 'RUG User' as Rate Type but RUG User Rate Set search box is not displayed", "FAIL");
		}
		else if(oParameters.GetParameters("RateType"+i).equals("Tiered"))
		{
			if(IsDisplayed("Tier Basis Drop down", tierBasis))
			{
				oReport.AddStepResult("Tiered", "In 'Add Term' window filled madatory fields then selected 'Tiered' as Rate Type, verified that Table Basis drop down displayed", "PASS");
				
				selectOption(tierBasis,"visibletext",oParameters.GetParameters("TireBasis"+i));
				
				formThroughValue(i);
			}
			else
				oReport.AddStepResult("Tiered", "In 'Add Term' window filled madatory fields then selected 'Tiered' as Rate Type but Table Basis drop down is not displayed", "FAIL");
		}
		
		if(oParameters.GetParameters("SectionForThisExclusion"+i).equals("yes"))
		{
			click_button("Show Section For This Exclusion", sectionForThisExclusion);
		
			By Section = By.xpath("//form[@id='addEditExclusionTerm']//label[contains(.,'"+oParameters.GetParameters("SectionForThisExclusionValue"+i)+"')]//input");
		
			click_button("Particular Exclusion Section", Section);
		}
	}
	
	
	By addTermCancelButton = By.xpath("//div[@id='showTermAddModal']//input[@id='button.cancelId']");
	
	By addExclusionTermCancelButton = By.xpath("//div[@id='showExclusionModal']//input[@id='button.cancelId']");
	
	By termsListCount = By.xpath("//ul[@class='ratesheet-terms terms-hide-overflow']//li//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding']");
	
	By ExclusionSaveButton = By.xpath("//div[@id='showExclusionModal']//input[@id='button.saveId']");
	
	By ExclusionDisabledSaveButton  = By.xpath("//div[@id='showExclusionModal']//input[@id='button.saveId'][@disabled='disabled']");
	
	By TermEmptyQG = By.xpath("//div[@class='clearBoth']//div[text()='You must select an option from the search results.']");
	
	By EmptyRevenueCodeExpresstion = By.xpath("//div[@class='col-lg-6 col-md-6 col-sm-6 col-xs-6']//div[text()='You must select an option from the search results.']");
	
	By sectionDiscardButton = By.xpath("//div[@class='form-button-panel']//input[@class='btn btn-danger discard-red']");	
	
	By stopLossTermSaveButton = By.xpath("//workflow-modal[@id='showAddSectionTermModal']//div[@id='addSectionModal']//input[@id='button.saveId']");
	
	By stopLossTermDisabledSaveButton = By.xpath("//workflow-modal[@id='showAddSectionTermModal']//div[@id='addSectionModal']//input[@id='button.saveId'][@disabled='disabled']");

	By redBoxError = By.xpath("//li[1]//span[@class='notification-icon icon fa fa-minus-circle notification-error-icon']");
	
	By QGListCount = By.xpath("//ul[@class='ratesheet-terms terms-hide-overflow']//li//div[contains(@class,'col-lg-5 col-md-5 col-sm-5 col-xs-5')]");
	
	
	//This Method is used to click on 'Term Save Button' and verifying whether term is added or not.
    public void termSaveButton(String i)
    {
    	//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
    	{                   
    		//Adding step result to report
    		oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
    		return ;
    	}
     
    	int j;
    	
    	if(IsDisplayed("Add Term Save Button", addTermSaveButton))
    	{
    		if(IsDisplayed("Add Term disabled Save Button", addTermDisabledSaveButton))
    		{      
    			oReport.AddStepResult("Save button", "In 'Add "+oParameters.GetParameters("TermType"+i)+" Window' filled all the details but save button is not enabled", "FAIL");
    			
    			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
    			
    			return;
    		}
    		else   
    		{
    			oReport.AddStepResult("Save button", "In 'Add "+oParameters.GetParameters("TermType"+i)+" Window' filled all the details, verified that save button is enabled", "PASS");
    			click_button("Term Save Button", addTermSaveButton);
    			fixed_wait_time(5);
    		}      
    	}
    	else if(IsDisplayed("Add Stop Loss Term Save Button", stopLossTermSaveButton))
    	{
    		if(IsDisplayed("Add Stop Loss Term Disabled Save Button", stopLossTermDisabledSaveButton))
    		{
    			oReport.AddStepResult("Save button", "In 'Add "+oParameters.GetParameters("TermType"+i)+" Window' filled all the details but save button is not enabled", "FAIL");
    			
    			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
    			
    			return;
    		}
    		else
    		{
    			oReport.AddStepResult("Save button", "In 'Add "+oParameters.GetParameters("TermType"+i)+" Window' filled all the details, verified that save button is enabled", "PASS");
    			click_button("Add Stop Loss Term Save Button", stopLossTermSaveButton);
    		}
    	}
    	else if(IsDisplayed("Add Exculsions Term Save Button", ExclusionSaveButton))
    	{      
    		if(IsDisplayed("Exclusion Term Disabled Save Button", ExclusionDisabledSaveButton))
    		{
    			oReport.AddStepResult("Save button", "In 'Add "+oParameters.GetParameters("TermType"+i)+" Window' filled all the details but save button is not enabled", "FAIL");
    			
    			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
    			
    			return;
    		}
    		else
    		{
    			oReport.AddStepResult("Save button", "In 'Add "+oParameters.GetParameters("TermType"+i)+" Window' filled all the details, verified that save button is enabled", "PASS");
    			click_button("Exclusion Term Save Button", ExclusionSaveButton);
    			fixed_wait_time(3);
    		}      
    	}
    	fixed_wait_time(2);
    
    	if(oParameters.GetParameters("TermType"+i).equalsIgnoreCase("Exclusions"))
    		addTermCancelButton=addExclusionTermCancelButton;
    
    	if(IsDisplayed("Term List Count", termsListCount))
    	{
    		List<WebElement> termList= convertToWebElements(termsListCount);
    		
    		for(j=0;j<termList.size();j++)
    		{
    			if(termList.get(j).getText().contains(oParameters.GetParameters("TermName"+i)))
    			{      
    				List<WebElement> QGList= convertToWebElements(QGListCount);
    				
    				for(int k=0;k<=termList.size();k++)
    				{
    					if(QGList.get(j).getText().contains(oParameters.GetParameters("QualificationGroup"+i)))
    					{
    						oReport.AddStepResult("Term Name", "Filled all mandatory filled in 'Add term' window and clicked on save button and verified that ' "+oParameters.GetParameters("TermName"+i)+" ' and ' "+oParameters.GetParameters("QualificationGroup")+" '  is added successfully", "PASS");
    						break;
    					}
    				}
    			}      
    		}
    		if(j>termList.size())
    			oReport.AddStepResult("Term Name", "Filled all mandatory filled in 'Add term' window and clicked on save button and verified that ' "+oParameters.GetParameters("TermName"+i)+" ' and ' "+oParameters.GetParameters("QualificationGroup")+" ' QG  is not added ", "FAIL");
    		
    		fixed_wait_time(2);
    	}	
    	else if(IsDisplayed("Red box Error", redBoxError))
    	{
    		oReport.AddStepResult("Red Box Error", "While creating "+oParameters.GetParameters("RateType"+i)+" type term filled all the mandatory fields then clicked on save but term not added red box error displayed ", "FAIL");
                 
    		click_button("Add Term Cancel Button", addTermCancelButton);            
    		fixed_wait_time(2);               
    		click_button("Add Term Discard Button", sectionDiscardButton);
    	} 	               
    	else if(IsDisplayed("Term QG", TermEmptyQG))
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
    }
	
	
	By ExclusionTab = By.xpath("//a[text()='Exclusions']");
	
	By selectedExclusionTab = By.xpath("//li[@class='portal-tab-pane ng-scope active']/a[contains(text(),'Exclusions')]");
    
    By addExclusionTermButton = By.xpath("//li[@id='sec-term-list']//a[text()='Add Exclusion Term']");
    
    By ExclusionTerm = By.xpath("//div[@id='exSlidePanelParent']//a[contains(text(),'Add an Exclusion Term.')]");
    
    By ExclusionQualificationFeild = By.xpath("//workflow-modal[@id='addExTerm']//input[@id='qualificationGroup']");
    
    By ExclusionRateType = By.xpath("//form[@id='addEditExclusionTerm']//div[@form-id='rateTypeFormModel.formId']//select[@id='']");
	
	
	public void ExclusionTab(String i)
    {
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
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
    		   oReport.AddStepResult("Add Exclusion Term ", "Clicked on 'Add an Exclusion Term icon' and verified that 'Add Exclusion Term' window is displayed", "PASS");
    		   
    		   enter_text_value("Term Name", termName, oParameters.GetParameters("TermName"+i));
    		   
    		   enter_text_value("Qualification Group", ExclusionQualificationFeild, oParameters.GetParameters("QualificationGroup"+i));
    		   performKeyBoardAction("ENTER");
    		   fixed_wait_time(2);
    		   
    		   By qualification = By.xpath("//div[@model='qualificationGroupModelTerm']//ul[@id='-list']//li//a[not(text())]//b[text()='"+oParameters.GetParameters("QualificationGroup"+i)+"']");
    		   
    		   if(IsDisplayed("Exclusion Qualification", qualification))
    			   click_button("Exclusion Qualification", qualification);
    	   }
    	   else
    		   oReport.AddStepResult("Add Exclusion Term ", "Clicked on 'Add an Exclusion Term icon' and verified that 'Add Exclusion Term' Tab is not displayed", "FAIL");
       }	
       else
    	   oReport.AddStepResult("Exclusion Term", "Clicked on Exclusion Tab and verified that exclusion Term icon is not displayed", "FAIL");       	
    }

    
    By AddSectionButton = By.xpath("//div[@id='iPSlidePanelParent']//input[@value='Add Section']");
        
    By sectionNameField = By.xpath("//input[@id='sectionName']");
    
    By sectionQualificationGroupField = By.xpath("//form[@id='addEditRateSheetSectionForm']//input[@id='qualificationGroup']");
    
    By addedSections = By.xpath("//div[@id='iPSlidePanelParent']//li[@class='ng-scope']//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4']//span");
    
    By addedSection =  By.xpath("//div[@id='iPSlidePanelParent']//li[2]//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4']//span");
    
    By rateSheetSections = By.xpath("//div[@id='iPSlidePanelParent']//li//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4']//span");
    
    By addTermSectionLink = By.xpath("//div[@id='iPSlidePanelParent']//a[contains(text(),'Add a Term Section.')]");
    
    By maximumRateType = By.xpath("//select[@id='maxRateType']");
    
    By maximumAmount = By.xpath("//input[@id='maxRateAmount']");
    
    By respectiveRateSheet = By.xpath("//div[@id='ratesheetSection']//header-component//div[contains(@class,'col-lg-6 col-md-6 col-sm-6')]");
    
    By copySection = By.xpath("//form[@id='addEditRateSheetSectionForm']//input[@id='selectSection']");
    
    By rateSheetSection = By.xpath("//div[@class='form-group col-lg-12 col-md-12 col-sm-12 ng-scope']//input[@id='searchfortermaction']");
    
    By effectiveRateSheet = By.xpath("//select[@id='ratesheetPeriod']");
    
    By section = By.xpath("//select[@id='moveSec']");
    
    By includeTermNoteSection = By.xpath("//form[@id='addEditRateSheetSectionForm']//input[contains(@id,'cnCheckbox')]");
    
    By addSectionAfter_Before = By.xpath("//workflow-modal[@class='ng-scope ng-isolate-scope']//span[@class='dropdown-text hide-overflow ng-binding']");
    
    By sectionPriority = By.xpath("//input[@id='sectionPriority']");	

	By rateSheetSearch = By.xpath("//div[@id='ratesheetSection']//input[@title='Type search criteria and click enter to search'][@placeholder='Search Rate Sheets']");

	By addTermsButton = By.xpath("//a[@class='link-btn hand-cursor ng-isolate-scope'][@title='Add Terms']");

	By addATermSectionLink = By.xpath("//a[@class='bold ng-scope ng-binding'][contains(text(),'Add a Term Section.')]");

	By addSectionButton = By.xpath("//input[@class='btn-default btn-xs mar-l-5'][@value='Add Section']");

	By addSectionWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Add Section']");
	
	By addStopLossSectionWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][@title='Add Stop Loss Section']");

	By sectionNameSearchBox = By.xpath("//input[@id='sectionName']");

	By QGsearchBox = By.xpath("//form[@id='addEditRateSheetSectionForm']//input[@id='qualificationGroup']");

	By firstResultQG = By.xpath("//ul[@id='-list'][@class='dropdown-menu']/li[1]");

	By maxRateTypeDropdown = By.xpath("//select[@id='maxRateType'][@name='maxRateType']");

	By maxAmountTextBox = By.xpath("//input[@id='maxRateAmount'][@name='maxRateAmount']");

	By windowSave = By.xpath("//div[@id='fullFooter']//input[@id='button.saveId']");

	By addedSectionsNumber = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li");

	
	// Searching and Creating new terms
	public void addingSectionWithMaxRateTypeasPerCase()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section1");		
		AddSectionDetails("", "");
	}

	
	By sectionDeleteIcon = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[2]//a[@class='link-btn hand-cursor ng-isolate-scope'][@title='Delete Section']");

	By SectionDelete = By.xpath("//input[@class='btn btn-danger'][@value='Delete']");

	
	// Deleting Sections
	public void deleteAllSections()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int count = 0;
		if(IsDisplayed("Section Delete Icon ", sectionDeleteIcon))
		{
			do
			{
				fixed_wait_time(2);
				click_button("Section Delete Icon", sectionDeleteIcon);
				fixed_wait_time(2);
				click_button("popup delete button", SectionDelete);
				fixed_wait_time(2);
				count++;
			}
			while(IsDisplayed("Section Delete Icon", sectionDeleteIcon));
			
			oReport.AddStepResult("Delete Section", count+" Sections deleted", "INFO");
		} 
		else
			oReport.AddStepResult("Delete Section", "No Sections for this Rate Sheet", "INFO");
	}
	

	By addSectionDropdown = By.xpath("//form[@id='addEditRateSheetSectionForm']//div[@id='styledDropdown']/a[1]");

	By addSectionBeforeOption = By.xpath("//form[@id='addEditRateSheetSectionForm']//div[@id='styledDropdown']//ul//a[contains(.,'Add Section Before')]");
	

	// Adding Section Before previously created Section
	public void addingSectionBeforeSection()
	{	
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section2");
		AddSectionDetails("", "");
		
		By firstSection = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[2][contains(.,'"+oParameters.GetParameters("SectionName")+"')]");
	
		waitFor(addedSection, "Added first section");                                                 
		mouse_hover("First Section", firstSection);
		
		if(IsDisplayed("Added Section", firstSection))
			oReport.AddStepResult("Added Section", "'" + oParameters.GetParameters("SectionName") + " '" + " Section is added before " + "'" + oParameters.GetParameters("AddSectionAfterBeforeSearch") + "'" + " Section", "PASS");
		else
			oReport.AddStepResult("Added Section","Clicked on Add Section, selected 'Add Section Before' from the drop down, selected Maximum Rate Type as 'Per Length of stay', filled all the mandatory fields and clicked on save but Section is not added before the selected section ","FAIL");
	} 

	
	// Adding Section After previously created Section
	public void addingSectionAfterSection()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section3");
		AddSectionDetails("", "");

		By addedSection = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[4][contains(.,'"+oParameters.GetParameters("SectionName")+"')]");

		waitFor(addedSection, "added new section");

		if(IsDisplayed("Added Section", addedSection))
			oReport.AddStepResult("Added Section","'" + oParameters.GetParameters("SectionName") + " '" + " Section is added after" + "'" + oParameters.GetParameters("AddSectionAfterBeforeSearch") + "'" + " Section", "PASS");
		else
			oReport.AddStepResult("Added Section","Clicked on Add Section, selected 'Add Section After' from the drop down, selected Maximum Rate Type as 'Per Length of stay', filled all the mandatory fields and clicked on save but Section is not added after the selected section ","FAIL");
	}

	
	By advancedQualifierOption = By.xpath("//span[contains(text(),'Show Advanced Qualifier Options')]");

	By qualificationGroupOptions = By.xpath("//form[@id='addEditRateSheetSectionForm']//form[@id='qualificationGroupOptions']");

	By procedureGroupDropDown = By.xpath("//div[@class=' col-lg-4 col-md-4 col-sm-4']//select[@id='procedureGroup']");

	By procedureGroupSearch = By.xpath("//div[@class='col-lg-4 col-md-4 col-sm-4']//input[@id='pGrpCSSearch']");

	By procedureGroupPeriodDD = By.xpath("//select[@id='procedureGroupPeriod']");

	By diagnosisSearch = By.xpath("//input[@id='DiagCSSearch']");

	By diagnosisPeriodDD = By.xpath("//select[@id='DiagCSPeriod']");

	By DRGSearch = By.xpath("//input[@id='DRGCSSearch']");

	By DRGPeriodDD = By.xpath("//select[@id='DrgPeriod']");

	
	// Adding section with advanced qualifier options
	public void addingSectionWithAdvanceQualifierOptions()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section4");
		AddSectionDetails("", "");
		
		By addedSection = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[contains(.,'"+oParameters.GetParameters("SectionName")+"')]");

		mouse_hover("Added Section", addedSection);

		if(IsDisplayed("Added Section", addedSection))
			oReport.AddStepResult("Add Section","Procedure Code, Diagnosis code and DRG Code selected and Section created", "PASS");
		else
			oReport.AddStepResult("Add Section","Clicked on Add section, filled mandatory fields and checked 'Show Advanced Qualifier Options' and Selected Procedure Code, Diagnosis code and DRG Code but new Section is not Created","FAIL");
	}

	
	By copyExistingRateSheetCheckbox = By.xpath("//div[@class='form-group  col-lg-12 col-md-12 col-sm-12 mar-t-10  ng-scope']//input[@id='selectSection']");

	By rateSheetSearchBox = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 col-mdlg-7 mar-r-0']//input[@id='searchfortermaction']");

	By rateSheetPeriodDD = By.xpath("//select[@id='ratesheetPeriod']");

	By rateSheetSectionDD = By.xpath("//select[@id='moveSec']");

	By includeTermsCheckBox = By.xpath("//label[contains(.,'Include Term Notes when Copying')]/input");

	By firstRateSheet = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 col-mdlg-7 mar-r-0']//ul[@class='dropdown-menu']/li/a[contains(.,'RATESHEETAUTOMATION_DND')]");
	

	// Adding Section with copying section form Existed rate sheet
	public void addingSectionWithExistingRateSheetSection()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section5");
		AddSectionDetails("", "");	

		By addedSection = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[contains(.,'"+oParameters.GetParameters("SectionName")+"')]");

		mouse_hover("Added Section", addedSection);

		if(IsDisplayed("Added Section", addedSection))
			oReport.AddStepResult("Added Section","New section is added which is copied from another Rate Sheet including Term Notes, Qualification Group Stop Loss and Exclusion","PASS");
		else
			oReport.AddStepResult("Added Section","Clicked on Add Section, filled section name, checked Copy section from existing Rate Sheet, selected Rate Sheet, Effective Period, Section and checked include term notes then Clicked on save but new term is not added","FAIL");
	}

	
	By showStopLossOption = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 form-group']//span[text()='Show Stop Loss Options']");

	By showExclusionOption = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 form-group']//span[text()='Show Exclusion Options']");

	By exclusionSelectAllCheckBox = By.xpath("//input[@id='selectAllExclusions']");

	
	// Adding Section with Stop loss and exclusion
	public void addingSectionWithStopLossAndExclusion(String rateSheetName)
	{	
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickStopLossTab();
		addStopLossSectionLink();
		addStopLossSection();
		
		// adding exclusion Term		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Exclusion_PerService_VR", "ExclusionPerServiceTerm");
		addTermButton("",rateSheetName,"");
		selectRateType("");
		termSaveButton("");
		
		clickTermsTab();
		
		click_button("Add Section", addSectionButton);
		enter_text_value("Section Name", sectionNameSearchBox,oParameters.GetParameters("RateSheetSectionName") + System.currentTimeMillis());
		oParameters.SetParameters("StopLossAndExclusionSectionName",get_field_value("Section Name", sectionNameSearchBox));
		enter_text_value("QG Search Box", QGsearchBox, "0 - TRUE");// oParameters.GetParameters("ExistedQGName"));
		click_button("Select a Stop Loss check box", showStopLossOption);
		click_button("Select an Exclusion check box", showExclusionOption);
		click_button("Exclusion Select All", exclusionSelectAllCheckBox);
		click_button("Add Section Save", rateSheetSectionSaveButton);

		By addedSection = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[contains(.,'"+oParameters.GetParameters("SectionName")+"')]");

		waitFor(addedSection, "");
		mouse_hover("Added Section", addedSection);

		if(IsDisplayed("Added Section", addedSection))
			oReport.AddStepResult("Added Section", "New section is added with selected Stop Loss and Exclusion","PASS");
		else
			oReport.AddStepResult("Added Section","Clicked on Add Section, filled all mandatory fields, checked 'Select a stop loss' and choosen the stop, checked 'Select an Exclusion' and choosen exclusion and clicked on save but New section is not added with selected Stop Loss and Exclusion","FAIL");
	}


	By sectionEditWindow = By.xpath("//div[@id='EditSectionPanel']//div[@class='medium-header truncate ng-binding']");

	By editWindowSave = By.xpath("//button[@id='button.saveId']");

	By editWindowCancel = By.xpath("//button[@id='button.cancelId']");

	
	// Editing Section
	public void editingSection()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Added Section", addedSection);

		if(IsElementDisplayed("Section Edit Window", sectionEditWindow))
			oReport.AddStepResult("Edit Window", "Section edit mode window is displayed", "PASS");
		else
			oReport.AddStepResult("Edit Window", "Clicked on Section but section edit window is not displayed", "FAIL");

		// Editing Section Name,QG and Max Rate Type		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section8");
		AddSectionDetails("", "Edit");
		
		click_button("Edit Window Save", editWindowSave);
		waitFor(addedSection, "added new section");
		oParameters.SetParameters("SectionNameAfterEdit", get_field_value("Section Name", addedSection));

		if(oParameters.GetParameters("SectionName").equals(oParameters.GetParameters("SectionNameAfterEdit")))
			oReport.AddStepResult("Section Name","Clicked on Section, Modified Section name, Qualification Group, Maximum Rate Type and clicked on save but Section name not modified","FAIL");
		else
			oReport.AddStepResult("Section Name","Clicked on Section, Modified Section name, Qualification Group, Maximum Rate Type and clicked on save Section name modified","PASS");

		// Checking Show Advanced Qualifier Options
		click_button("First Section", addedSection);
		fixed_wait_time(4);
		waitFor(advancedQualifierOption, "Show Advanced Qualifier Options");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section9");
		AddSectionDetails("", "Edit");

		oReport.AddStepResult("Diagnosis and DRG","Procedure Group, Diagnosis Code and DRG Code is selected and respective effective period is selected","PASS");

		click_button("Edit Window Section Save", editWindowSave);

		By addedSection = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[2][contains(.,'"+oParameters.GetParameters("SectionNameAfterEdit")+"')]");

		waitFor(addedSection, "First Section");
		mouse_hover("First Section", addedSection);

		if(IsDisplayed("First Section", addedSection))
		{
			waitFor(addedSection, "First Section");
			oReport.AddStepResult("Add Section","Procedure Code, Diagnosis code and DRG Code selected and Section created", "PASS");
		}
		else
		{
			waitFor(addedSection, "First Section");
			oReport.AddStepResult("Add Section","Clicked on existed section, Then filled mandatory fields and checked 'Show Advanced Qualifier Options' and Selected Procedure Code, Diagnosis code and DRG Code but new Section is not Created","FAIL");
		}
		
/*		// Adding Section with copying form Existed rate sheet
		click_button("Added Section", addedSection);
		waitFor(copyExistingRateSheetCheckbox, "Copy Section from Existing Rate Sheet check box");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section11");
		AddSectionDetails("", "Edit");
		
		if(!oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))		
		{
			waitFor(editWindowSave, "Edit window save");
			click_button("Edit Window Section Save", editWindowSave);

			By addedSection1 = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[2][contains(.,'"+ oParameters.GetParameters("SectionNameAfterEdit") + "')]");

			mouse_hover("Added Section", addedSection1);

			if(IsDisplayed("Added Section", addedSection1))
				oReport.AddStepResult("Added Section","New section is added which is copied from another Rate Sheet including Term Notes, Qualification Group Stop Loss and Exclusion","PASS");
			else
				oReport.AddStepResult("Added Section","Clicked on Add Section, filled section name, checked Copy section from existing Rate Sheet, selected Rate Sheet, Effective Period, Section and checked include term notes then Click on save but new term not added","FAIL");
		}*/
	}
	
	
	public void editWindowCancelScenario()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("First Section", addedSection);
		waitFor(sectionEditWindow, "");
		click_button("Edit Window Cancel", editWindowCancel);

		if(IsDisplayed("Section Edit Window", sectionEditWindow))
			oReport.AddStepResult("Edit Window","Section opened in the Edit mode and clicked on cancel button but that edit window is not closed","FAIL");
		else
			oReport.AddStepResult("Edit Window","Opened the section in Edit mode and clicked cancel button that edit window is closed", "PASS");		
	}
	
	
	// Deleting Section
	public void deleteSection(String sectionName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By section = By.xpath("//li[@class='list-header light toolbar data-row sections hand-cursor'][contains(.,'"+sectionName+"')]//i[@class='left fa fa-minus-square']");
		
		fixed_wait_time(3);
		waitFor(section, sectionName+"Section Delete Icon");
		oParameters.SetParameters("SectionCountBeforeDelete", String.valueOf(get_table_row_count(rateSheetSections)));
		click_button(sectionName+" section delete Icon", section);
		fixed_wait_time(3);
		click_button("Delete Section", SectionDelete);
		fixed_wait_time(3);
		waitFor(addedSection, "First Section");

		if(oParameters.GetParameters("SectionCountBeforeDelete").equals(String.valueOf(get_table_row_count(rateSheetSections))))
			oReport.AddStepResult("Section Delete","Clicked on Section Delete icon but that section is not deleted ", "FAIL");
		else
			oReport.AddStepResult("Section Delete","Clicked on Section Delete icon, verified that section is deleted ", "PASS");
	}
	

	By addTermButton = By.xpath("//ul[@class='data-list']//li[2]//a[@class='link-btn hand-cursor ng-isolate-scope']/i[@class='left fa fa-plus-square']");

	By termNameTextBox = By.xpath("//input[@id='termName']");

	By qualificationGroupSearch = By.xpath("//form[@id='addEditTermForm']//input[@id='qualificationGroup']");

	By rateTypeDropDown = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8 ng-isolate-scope']//select");

	By tierBasisDropdown = By.xpath("//select[@id='tierBasis']");

	By additiveTieredFromTextBox = By.xpath("//input[@class='form-control ng-pristine ng-valid no-change ng-valid-required']");

	By additiveTieredFromTextBox2 = By.xpath("//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li[3]//input[@class='form-control ng-pristine ng-valid no-change ng-valid-required']");

	By additiveTierdThroughTextBox = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-3 pad-r-0']//input[@class='form-control ng-pristine no-change ng-valid ng-valid-required']");

	By additiveTieredAmountTextBox = By.xpath("//div[@class='col-lg-4 col-md-4 col-sm-4 col-xs-4 pad-r-0']//input");

	By additiveTieredAmountTextBox2 = By.xpath("//input[@class='form-control ng-isolate-scope ng-pristine bad-type ng-invalid ng-invalid-required']");

	By tierBasisAddRowIcon = By.xpath("//a[@class='hand-cursor link-btn hand-cursor ng-isolate-scope']/i[@class='left fa fa-plus-square']");

	By maximumRateTypeDD = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//select[@id='maxRateType'][@class='form-control ng-pristine no-change ng-valid ng-valid-required']");

	By minimumRateTypeDD = By.xpath("//select[@id='minRateType'][@class='form-control ng-pristine no-change ng-valid ng-valid-required']");

	By maximumRateAmountTextBox = By.xpath("//input[@id='maxRateAmount']");

	By minimumRateAmountTextBox = By.xpath("//input[@id='minRateAmount']");

	By addTermWindowSave = By.xpath("//div[@id='showTermAddModal']//input[@id='button.saveId']");

	
	// Adding terms for created Sections
	public void addTerms()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//Creating Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_AdditiveTired_VR", "CrudTermsTerm");
		addTermButton("", "CRUD_Terms_RateSheet_DND", "");
		selectRateType("");
		MaxMinRateType("", "Term_AdditiveTired_VR", "RateSheets_TestData.xlsx");
		termSaveButton("");
		
		//Creating Dosage quantity rate type Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DosageQuantity_VR", "Term1");
		addTermButton("", "CRUD_Terms_RateSheet_DND", "");
		selectRateType("");
		termSaveButton("");	
		
		//Creating IRF CMG pricer rate type term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_IRF_CMG_Pricer_VR", "IRFTerm");
		addTermButton("", "CRUD_Terms_RateSheet_DND", "");
		selectRateType("");
		termSaveButton("");	

		fixed_wait_time(5);
	}
	
	
	By editWindowTermNameTextBox = By.xpath("//div[@class='panel large-height panel-noborder']//input[@id='termName']");
	
	By termEditWindow = By.xpath("//div[@id='showTermEditModal']//div[@class='medium-header truncate ng-binding']");

	
	// Editing Existed Term
	public void editTerm(String termName, String editText)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By addedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]");

		oParameters.SetParameters("TermNameBeforeEdit", get_field_value("Term Name before edit", addedTerm));
		click_button("Added Term", addedTerm);
		waitFor(editWindowTermNameTextBox, "Edit window term name text box");
		enter_text_value_without_clear("Term Name", editWindowTermNameTextBox, editText);
		fixed_wait_time(3);
		
		clickSaveButton("Save button", "Term edit window", editWindowSave, termEditWindow);
		
	//	click_button("Edit Window Save", editWindowSave);
		fixed_wait_time(3);
		waitFor(addedTerm, "Existing term");
		oParameters.SetParameters("TermNameAfterEdit", get_field_value("Term Name before edit", addedTerm));

		if(oParameters.GetParameters("TermNameBeforeEdit").equals(oParameters.GetParameters("TermNameAfterEdit")))
			oReport.AddStepResult("Term Editing","Clicked on existed Term, in edit window, made changes and clicked on save but that modifications is not saved ","FAIL");
		else
			oReport.AddStepResult("Term Editing","Clicked on existed Term, in edit window, made some changes and clicked on save, verified that modification is saved without any error","PASS");
	}

	
	By termToggleDown = By.xpath("//a[@icon='toggle-down'][@style='display: inline;']/i[@class='left fa fa-toggle-down']");

	By sectionDD = By.xpath("//div[@class=' row col-lg-5 col-md-5 col-sm-5 col-mdlg-5 mar-r-0']//select[@id='currentSec']");

	By moveTermWindowSave = By.xpath("//input[@id='singleTermSaveId']");
	
	By moveTermWindowSaveSL = By.xpath("//input[@id='multipleTermSaveId']");

	By moveMultipleTermsWindowSave = By.xpath("//input[@id='multipleTermSaveId']");

	By firstSectionTermCount = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[2]//span[3][@class='pull-right small ng-binding']");
	
	By firstSectionTermCountSL = By.xpath("//ul[@class='data-list']/li[2]//span[2][@class='pull-right small ng-binding']");

	By secondSectionTermCount = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[3]//span[3][@class='pull-right small ng-binding']");
	
	By secondSectionTermCountSL = By.xpath("//ul[@class='data-list']/li[3]//span[2][@class='pull-right small ng-binding']");

	By toAnotherRateSheetRadio = By.xpath("//input[@id='actionToOther'][@type='radio']");
	
	By toAnotherRateSheetRadioExclusion = By.xpath("//*[@id='actionToOtherTerm']");

	By moveTermWinowRateSheetSearchBox = By.xpath("//div[@class='form-group col-lg-12 col-md-12 col-sm-12 col-mdlg-12 mar-r-0']//input[@id='searchfortermaction']");

	By moveTermRateSheetFirstSearchResult = By.xpath("//div[@class='row col-lg-7 col-md-7 col-sm-7 col-mdlg-7 mar-r-0']//ul[@class='dropdown-menu']/li/a[contains(.,'AUTOMATIONRATESHEET_DND')]");

	By moveTermRateSheetPeriodDD = By.xpath("//div[@class='form-group col-lg-12 col-md-12 col-sm-12 col-mdlg-12 mar-r-0']//select[@id='otherPrd']");

	By moveTermRateSheetSectionDD = By.xpath("//div[@class=' row col-lg-5 col-md-5 col-sm-5 col-mdlg-5 mar-r-0']//select[@id='otherSec']");

	By termDeleteIcon = By.xpath("//a[@class='link-btn hand-cursor ng-isolate-scope'][@style='display: inline;']/i[@class='left fa fa-minus-square']");

	By sectionTermCount = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[2]//span[3][@class='pull-right small ng-binding']");

	By multipleTermsActionDD = By.xpath("//span[text()='Take action on multiple terms']");

	By multipleTermsDDMoveTerms = By.xpath("//*[@id='multipleTermsActionBar']//a[@ng-click='moveMultipleTerm()']");
	
	By multipleTermsDDMoveTermsSL = By.xpath("//*[@id='multipleTermsActionBar']//a[@ng-click='moveMultipleStoplossTerm()']");

	By multipleTermsDDCopyTerms = By.xpath("//*[@id='multipleTermsActionBar']//a[@ng-click='copyMultipleTerm()']");
	
	By multipleTermsDDCopyTermsSL = By.xpath("//*[@id='multipleTermsActionBar']//a[@ng-click='copyMultipleStoplossTerm()']");

	By multipleTermsDDDeleteTerms = By.xpath("//*[@id='multipleTermsActionBar']//a[@ng-click='deleteMultipleTerm(trm, sec.Item)']");
	
	By periodDropdown = By.xpath("//*[@id='ratesheetSection']//button[@class='btn btn-light btn-default btn-sm']");

	By addPeriod = By.xpath("//ul[@class='dropdown-menu period-menu']//a[@ng-click='addPeriod()']");

	By firstPeriodDate = By.xpath("//ul[@class='dropdown-menu period-menu']/li[2]");
	
	By secondPeriodDate = By.xpath("//ul[@class='dropdown-menu period-menu']/li[3]");

	By periodDate = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 ng-binding']");

	By periodDateEditIcon = By.xpath("//div[@class='col-lg-7 col-md-7 col-sm-7 ng-binding']//i[@class='fa fa-fw fa-pencil show-on-hover'][@style='display: inline-block;']");

	By peiodDateDeleteIcon = By.xpath("//i[@class='fa fa-fw fa-minus-square pull-right show-on-hover period-minus'][@style='display: inline-block;']");

	By rateSheetPeriodStartDate = By.xpath("//input[@id='startDateRatesheetPeriod']");

	By rateSheetPeriodEndDate = By.xpath("//input[@id='stopDateRatesheetPeriod']");

	By editPeriodActiveRadio = By.xpath("//form[@id='addEditPeriod']//input[@class='ng-pristine ng-valid no-change'][@value='Actv']");

	By editPeriodActiveRadioChanged = By.xpath("//form[@id='addEditPeriod']//input[@class='ng-valid ng-dirty changed'][@value='Actv']");

	By editPeriodInactiveRadio = By.xpath("//form[@id='addEditPeriod']//input[@class='ng-pristine ng-valid no-change'][@value='Prelim']");

	By editPeriodInactiveRadioChanged = By.xpath("//form[@id='addEditPeriod']//input[@class='ng-valid ng-dirty changed'][@value='Prelim']");

	By copyOfExistingRateSheetCheckBox = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 mar-t-12 row form-group mar-l-0']//input[@id='copyClosed']");

	By selectRateSheetSearchBox = By.xpath("//input[@id='copyRateSheet']");

	By copyingRateSheetPeriodDD = By.xpath("//select[@id='copyPeriod']");

	By copyingRateSheetFirstSearchResult = By.xpath("//div[@class='col-md-9 col-sm-9 col-lg-9 form-group  form-group ']//ul[@class='dropdown-menu dropdown-display']/li[1]/a");

	By editPeriodWindowSave = By.xpath("//div[@id='addPeriodModal']//input[@id='button.saveId']");
	
	By firstValue = By.xpath("//ul[@class='dropdown-menu dropdown-display']/li[contains(.,'BLR_AUTOMATION(DO NOT DELETE')]");

	
	// Editing Period Dates and Status
	public void editingandAddingPeriod()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Editing period Date

		click_button("Rate Sheet Period Dropdown", periodDropdown);
		mouse_hover("Period Date", periodDate);
		click_button("Period Edit Icon", periodDateEditIcon);
		waitFor(rateSheetPeriodEndDate, "");
		click_button("Active Radio", editPeriodActiveRadio);

		if (IsDisplayed("Edit Period Active Radio Changed", editPeriodActiveRadioChanged)|| IsDisplayed("Edit Period Inactive Radio Changed", editPeriodInactiveRadioChanged))
		{} 
		else
			click_button("Inactive Radio", editPeriodInactiveRadio);

		oParameters.SetParameters("PeriodEndDate", get_field_value("Period Termination Date", rateSheetPeriodEndDate));
		enter_text_value("Edit Period End Date", rateSheetPeriodEndDate,get_next_date(oParameters.GetParameters("PeriodEndDate"), 1));
		click_button("Edit Period Window Save", editPeriodWindowSave);
		waitFor(periodDropdown, "");
		click_button("Period Drop down", periodDropdown);
		mouse_hover("Period Date", periodDate);

		if(IsDisplayed("Period Date", periodDate))
			oReport.AddStepResult("Period Date", "Rate Sheet period date and Status modified ", "PASS");
		else
			oReport.AddStepResult("Period Date","Clicked on Rate Sheet Periods drop down, hovered over period, clicked on edit icon changed the status and termination date then Click on save but period date is not modified","FAIL");

		// Adding period with copying details from Existed Rate Sheet

		click_button("Add Period", addPeriod);
		enter_text_value("Period Start Date", rateSheetPeriodStartDate,get_next_date(oParameters.GetParameters("PeriodEndDate"), 2));
		performKeyBoardAction("ENTER");
		enter_text_value("Period Start Date", rateSheetPeriodEndDate,get_next_date(oParameters.GetParameters("PeriodEndDate"), 30));
		performKeyBoardAction("ENTER");
		click_button("Copy Details from	Existing Rate Sheet", copyOfExistingRateSheetCheckBox);
		enter_text_value("Copy Rate Sheet Search Box", selectRateSheetSearchBox, "BLR_AUTOMATION(DO NOT DELETE)");// oParameters.GetParameters("ExistedRateSheet");
		performKeyBoardAction("ENTER");
		fixed_wait_time(2);		
		waitFor(copyingRateSheetPeriodDD, "");
		click_button("Copying Rate Sheet Window Save", editPeriodWindowSave);
		fixed_wait_time(3);

		By existingTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'IRF_CMG_pricer_rate_Term')]");
		
		if(IsDisplayed("Copied Ratesheet Term", existingTerm))
			oReport.AddStepResult("Existed Ratesheet period", "Copied Rate Sheet Terms added for respective period ","PASS");
		else
			oReport.AddStepResult("Existed Ratesheet period","Clicked on Rate Sheet Period, clicked on add period, selected dates, checked copy details from existing rate sheet, Searched and selected the Rate Sheet and then clicked on save but that Rate Sheet terms for respective period is not added ","FAIL");

		// Deleting that added period

		fixed_wait_time(3);
		click_button("Rate Sheet period drop down", periodDropdown);
		mouse_hover("First Period Date", firstPeriodDate);
		click_button("Period Delete Icon", peiodDateDeleteIcon);
		fixed_wait_time(3);
		click_button("Peroid Delete", SectionDelete);
		fixed_wait_time(4);
		waitFor(periodDropdown, "Period Dropdown");
	}

	
	By addFilterElement = By.xpath("//div[@id='ratesheetSection']//span[text()='Add Filter']");
	
	By filtersDropDown = By.xpath("//div[@id='ratesheetView']//a[@class='filter-label hand-cursor pull-left ng-scope']");			
	
	By filtersDropDownSL = By.xpath("//div[@id='filter-stoplossSections']//a[@class='filter-label hand-cursor pull-left ng-scope']");

	By calculationElement = By.xpath("//li//a[text()='Calculation']");

	By QGElement = By.xpath("//li//a[text()='Qualification Group']");

	By termElement = By.xpath("//li//a[text()='Term']");
	
	By stopLossElement = By.xpath("//div[@id='filter-stoplossSections']//li//a[text()='Stop Loss']");
	
	By exclusionElement = By.xpath("//li//a[text()='Exclusion']");
	
	By calculationColumn = By.xpath("//*[@id='iPSlidePanel']/div/ratesheet-sections-terms/div/div[1]/ul/li[2]/ul/li[2]/ul/li[1]/div/div[2]/div[3]");
	
	By calculationColumnSL = By.xpath("//div[@id='sPSlidePanelParent']//ratesheet-stoploss-sections/div/ul/li/ul//li[1]//div[@class='calculation']");

	By calculationColumnExclusion = By.xpath("//li[@ng-repeat='(key, trm) in getExclusionTerms()'][1]//div[@class='calculation ng-scope ng-binding']");
	
	By filtersTextBox = By.xpath("//div[@class='doc-details-main rate-sheet-content ng-scope']//input[@title='Enter text to filter and press enter.'][@class='text']");

	By clearFiltersButton = By.xpath("//div[@id='iPSlidePanelParent']//i[@class='left fa-lg fa fa-times-circle']");
	
	By clearFiltersButtonSL = By.xpath("//div[@id='filter-stoplossSections']//i[@class='left fa-lg fa fa-times-circle']");
	
	By clearFiltersButtonExclusion = By.xpath("//div[@id='exSlidePanelParent']//i[@class='left fa-lg fa fa-times-circle']");
	
	By qualificationGroupColumn = By.xpath("//*[@id='iPSlidePanel']/div/ratesheet-sections-terms/div/div[1]/ul/li[2]/ul/li[2]/ul/li[1]/div/div[2]/div[2]");

	By qualificationGroupColumnSL = By.xpath("//div[@id='sPSlidePanelParent']//ratesheet-stoploss-sections/div/ul/li/ul//li[1]//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-5 ng-binding']");

	By qualificationGroupColumnExclusion = By.xpath("//li[@ng-repeat='(key, trm) in getExclusionTerms()'][1]//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-5 pad-l-30 ng-binding']");
	
	By termColumn = By.xpath("//*[@id='iPSlidePanel']/div/ratesheet-sections-terms/div/div[1]/ul/li[2]/ul/li[2]/ul/li[1]/div/div[2]/div[1]");
	
	By termColumnSL = By.xpath("//div[@id='sPSlidePanelParent']//ratesheet-stoploss-sections/div/ul/li/ul//li[1]//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding']");
	
	By exclusionColumn = By.xpath("//li[@ng-repeat='(key, trm) in getExclusionTerms()'][1][1]//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding']");
	
	
	// Filter Reports
	public void filterReports(String tabName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(tabName.equalsIgnoreCase("Stop Loss"))
		{
			qualificationGroupColumn = qualificationGroupColumnSL;
			calculationColumn = calculationColumnSL;
			termColumn = termColumnSL;
			//filtersDropDown = filtersDropDownSL;
			termElement = stopLossElement;
			clearFiltersButton = clearFiltersButtonSL;
		}		
		else if(tabName.equalsIgnoreCase("Exclusions"))
		{
			qualificationGroupColumn = qualificationGroupColumnExclusion;
			calculationColumn = calculationColumnExclusion;
			termColumn = exclusionColumn;			
			termElement = exclusionElement;
			clearFiltersButton = clearFiltersButtonExclusion;
		}
		
		// Qualification Group Filter Reports
		waitFor(addFilterElement, "Add filter Element");
		oParameters.SetParameters("QGBeforeFilter", get_field_value("QG Name", qualificationGroupColumn));
		fixed_wait_time(3);
		click_button("Add Filter Button", addFilterElement);
		click_button("Filters DropDown", filtersDropDown);
		click_button("Qualification Group Element", QGElement);
		enter_text_value("Filters Input Text Box", filtersTextBox, oParameters.GetParameters("QGBeforeFilter"));
		performKeyBoardAction("ENTER");
		waitFor(qualificationGroupColumn, "Ratesheet Term");
		oParameters.SetParameters("QGAfterFilter", get_field_value("QG Name", qualificationGroupColumn));

		if(oParameters.GetParameters("QGBeforeFilter").equals(oParameters.GetParameters("QGAfterFilter")))
			oReport.AddStepResult("Qualification Group Reports","Rate Sheet Terms are Filtered based on Qualification Group those reports are displayed", "PASS");
		else
			oReport.AddStepResult("Qualification Group Reports","Rate Sheet Terms are Filtered based on Qualification Group but those reports are not displayed","FAIL");

		click_button("Clear Filters Button", clearFiltersButton);

		// Calculation Filter Reports
		oParameters.SetParameters("TermCalculationBeforeFilter",get_field_value("Calculation Term Name", calculationColumn));
		click_button("Add Filter Button", addFilterElement);
		click_button("Filters DropDown", filtersDropDown);
		click_button("Calculation Element", calculationElement);
		enter_text_value("Filters Input Text Box", filtersTextBox,oParameters.GetParameters("TermCalculationBeforeFilter"));
		performKeyBoardAction("ENTER");
		waitFor(calculationColumn, "Ratesheet Term");
		oParameters.SetParameters("TermCalculationAfterFilter",	get_field_value("Calculation Term Name", calculationColumn));

		if(oParameters.GetParameters("TermCalculationBeforeFilter").equals(oParameters.GetParameters("TermCalculationAfterFilter")))
			oReport.AddStepResult("Calculation Reports","Rate Sheet Terms are Filtered based on Calculation those reports are displayed", "PASS");
		else
			oReport.AddStepResult("Calculation Reports","Rate Sheet Terms are Filtered based on Calculation but those reports are not displayed", "FAIL");

		click_button("Clear Filters Button", clearFiltersButton);

		// Term Filter Reports
		oParameters.SetParameters("TermBeforeFilter", get_field_value( tabName+" Name", termColumn).replace("1.1 ", ""));
		click_button("Add Filter Button", addFilterElement);
		click_button("Filters DropDown", filtersDropDown);
		click_button(tabName+" Element", termElement);
		enter_text_value("Filters Input Text Box", filtersTextBox, oParameters.GetParameters("TermBeforeFilter"));
		performKeyBoardAction("ENTER");
		waitFor(termColumn, "Ratesheet Term");
		oParameters.SetParameters("TermAfterFilter", get_field_value(tabName+" Name", termColumn).replace("1.1 ", ""));

		if(oParameters.GetParameters("TermBeforeFilter").equals(oParameters.GetParameters("TermAfterFilter")))
			oReport.AddStepResult(tabName+" Reports","Rate Sheet Terms are Filtered based on "+tabName+" those reports are displayed", "PASS");
		else
			oReport.AddStepResult(tabName+" Reports","Rate Sheet Terms are Filtered based on "+tabName+" but those reports are not displayed", "FAIL");

		click_button("Clear Filters Button", clearFiltersButton);
	}
	
	
	//This method will click on Terms tab
	public void clickTermsTab()
	{
		navigate_to("Terms Tab", "Selected terms Tab", termsTab, selectedTermsTab);
	}
	

	// Rate Sheet-CRUD Terms VR
	public void RateSheet_CRUD_Terms_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();
		
		addRateSheet("CRUDTermsRatesheet","CRUD_Terms_RateSheet_DND");
		
		addingSectionWithMaxRateTypeasPerCase();
		addingSectionBeforeSection();
		addingSectionAfterSection();
		addingSectionWithAdvanceQualifierOptions();
		addingSectionWithExistingRateSheetSection();
		addingSectionWithStopLossAndExclusion("CRUD_Terms_RateSheet_DND");
		editingSection();
		editWindowCancelScenario();
		deleteSection("");		
		addTerms();	
		editTerm("CRUDTerms_AdditiveTiered_Term", "_Edited");		
		
		//Copying and moving terms to same ratesheet section and another ratesheet section
		movingSingleTerm("CRUDTerms_AdditiveTiered_Term_Edited", "Terms");
		movingSingleTermToOtherRS("CRUDTerms_AdditiveTiered_Term_Edited", "Terms");	
		deleteSingleTerm("CRUDTerms_AdditiveTiered_Term_Edited");
		searchRateSheet("Production", "CRUD_Terms_RateSheet_DND");		
		copingSingleTerm("DosageQuantity_NoRounding_Term", "Terms");		
		copingSingleTermToOtherRS("DosageQuantity_NoRounding_Term", "Terms");		
		deleteSingleTerm("DosageQuantity_NoRounding_Term");		
		searchRateSheet("Production", "CRUD_Terms_RateSheet_DND");		
		copyingMultipleTerm("DosageQuantity_NoRounding_Term", "IRF_CMG_pricer_rate_Term", "Terms");		
		deleteTerm("IRF_CMG_pricer_rate_Term", "DosageQuantity_NoRounding_Term");			
		editingandAddingPeriod();
		deleteSection("Automation_Section");
		filterReports("Terms");
		deleteRatesheet("Production","CRUD_Terms_RateSheet_DND");	
		logout();
	}

	
	By addTermWindow = By.xpath("//div[@id='showTermAddModal']//div[@title='Add Term']");

	By addTerm = By.xpath("//div[@id='iPSlidePanelParent']//li[@class='ng-scope'][1]//div[@class='pull-right icon-container']//a[1]//i[1]");

	By termName = By.xpath("//input[@id='termName']");

	By firstQualification = By.xpath("//workflow-modal[@id='addIpTerm']//ul[@id='-list']//li[1]");

	By selectRateType = By.xpath("//form[@id='addEditTermForm']//select[contains(@ng-options,'item as spec.objectList[item][spec.optionText]')]");

	By selectTierBasis = By.xpath("//select[@id='tierBasis']");

//	By Form_ThroughFields = By.xpath("//form[@id='addEditTermForm']//ul[@class='data-list pad-l-15 pad-r-15 ng-scope']//li[@class='data-row ng-scope']");

	By termnumbers = By.xpath("//div[@id='iPSlidePanelParent']//ul[@class='data-list']//span[3]");
	
	By tierBasisRateType = By.xpath("//select[@id='tierRateType']");

	By FirstEmptythroughField = By.xpath("//form[@id='addEditTermForm']//input[@class='form-control ng-pristine no-change ng-valid ng-valid-required']");

	By getFirstthroughvalue = By.xpath("//form[@id='addEditTermForm']//input[@class='form-control ng-valid ng-valid-required ng-dirty changed']");

	By secondamountField = By.xpath("//form[@id='addEditTermForm']//input[@class='form-control ng-isolate-scope ng-pristine bad-type ng-invalid ng-invalid-required']");

	By getFirstAmountField = By.xpath("//input[@class='form-control ng-isolate-scope ng-dirty ng-valid ng-valid-required changed']");

	By FirstaddIcon = By.xpath("//form[@id='addEditTermForm']//li[@class='data-row ng-scope']//i[@class='left fa fa-plus-square']");

	By secondFromValue = By.xpath("//form[@id='addEditTermForm']//li[3]/div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-3 pad-r-0']//input[@class='form-control ng-pristine ng-valid no-change ng-valid-required']");

	By SecondaddIcon = By.xpath("//form[@id='addEditTermForm']//li[@class='data-row ng-scope'][2]//i[@class='left fa fa-plus-square']");

	By addTermSaveButton = By.xpath("//workflow-modal[@id='addIpTerm']//div[@id='showTermAddModal']//input[@id='button.saveId']");
	
	By addTermDisabledSaveButton = By.xpath("//workflow-modal[@id='addIpTerm']//div[@id='showTermAddModal']//input[@id='button.saveId'][@disabled='disabled']");

	By ThridaddIcon = By.xpath("//form[@id='addEditTermForm']//li[@class='data-row ng-scope'][3]//i[@class='left fa fa-plus-square']");

	By AddingCharges = By.xpath("//div[@id='lineItemsSec']//td[@class='charges']//input[contains(@id,'charges')]");

	By ErrorSavingRateSheet = By.xpath("//div[@id='dialog']//div[contains(text(),'Error saving rate sheet')]");

	By ErrorSavingRateSheetOKButton = By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");

	By CancelButton = By.xpath("//workflow-modal[@id='addIpTerm']//div[@id='showTermAddModal']//input[@id='button.cancelId']");

	By DiscardButton = By.xpath("//div[@id='dialog_buttons']/input[@value='Discard']");

	By UnSavedChangesDailogBox = By.xpath("//div[@id='dialog']//div[contains(text(),'You have unsaved changes')]");

	By FromFirstField = By.xpath("//form[@id='addEditTermForm']//li[3]//input[contains(@class,'form-control ng-pristine ng-valid no-change ng-valid-required')]");

	By secondFromField = By.xpath("//form[@id='addEditTermForm']//li[4]//input[contains(@class,'form-control ng-pristine ng-valid no-change ng-valid-required')]");

	By AdditiveTired = By.xpath("//select[@class='form-control ng-valid ng-dirty changed']");

	By TierBasis = By.xpath("//select[@name='tierBasis']");

	By TierBasisRateType = By.xpath("//select[@name='tierRateType']");

	By ThirdDeleteIcon = By.xpath("//form[@id='addEditTermForm']//li[@class='data-row ng-scope'][3]//i[@class='left fa fa-minus-square']");

	
	public void addingAdditiveTieredTerms()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_AdditiveTired_VR", "");
		
		//Creating Additive Tiered Rate Type Terms		
		for(int i=1;i<=7;i++)
		{
			addTermButton(String.valueOf(i),"AdditiveTiered_RateSheet_DND","RateSheet_Section");
			selectRateType(String.valueOf(i));
			termSaveButton(String.valueOf(i));
		}
	}
		
	
	// Rate Sheet Additive Rate Type VR
	public void RateSheet_Additive_RateType_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();
		
		addRateSheet("AdditiveTiered_RateType","AdditiveTiered_RateSheet_DND");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");
		AddSectionDetails("", "");
		
		addingAdditiveTieredTerms();

		// Editing Term name
		editTerm("DailyCovered_Term_PerDiem", "_Edited");
		
		// Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section7");
		AddSectionDetails("", "");		
		
		//Copying and moving terms to same rate sheet section and another rate sheet section		
		movingSingleTerm("Hours_Term", "Terms");
		movingSingleTermToOtherRS("Hours_Term", "Terms");		
		deleteSingleTerm("Hours_Term");
		searchRateSheet("Production", "AdditiveTiered_RateSheet_DND");		
		copingSingleTerm("Minute_Term", "Terms");		
		copingSingleTermToOtherRS("Minute_Term", "Terms");		
		deleteSingleTerm("Minute_Term");		
		searchRateSheet("Production", "AdditiveTiered_RateSheet_DND");
		copyingMultipleTerm("Minute_Term", "TotalCovered_Term_PerCase", "Terms");
		deleteTerm("TotalCovered_Term_PerCase", "Minute_Term");				
		editingandAddingPeriod();
		filterReports("Terms");		
		deleteRatesheet("Production","AdditiveTiered_RateSheet_DND");		
		logout();
	}
	

	// Rate Sheet-Search and CRUD Rate Sheet VR

	By rateSheetShowDD = By.xpath("//div[@id='ratesheetSection']//a[@class='btn btn-light btn-default btn-sm  ng-scope']");

	By modelingOption = By.xpath("//li[@class='hand-cursor ng-scope']/a[contains(.,'Modeling')]");

	By productionOption = By.xpath("//li[@class='hand-cursor ng-scope']/a[contains(.,'Production')]");

	By carotUp = By.xpath("//div[@id='ratesheetSection']//i[@class='left fa fa-chevron-up']");

	By carotDown = By.xpath("//div[@id='ratesheetSection']//i[@class='left fa fa-chevron-down']");

	By rateSheetTitleBar = By.xpath("//div[@class='document-title-bar ng-scope']/div[@class='pull-right xl-header pad-r-5 ng-binding']");

	By rateSheetFirtstSearchResult = By.xpath("//ul[@class='data-list pad-t-20 ng-scope']/li[1]/div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div/div");
	
	By openedRateSheet = By.xpath("//div[@id='ratesheetSection']//div[@class='col-lg-6 col-md-6 col-sm-6 xl-header ng-binding']"); 
	
	By rateSheetDeletedErrorNotification = By.xpath("//ul[@class='error-items']/li[1]//span[contains(.,' The item you selected is unavailable or has been deleted. Refresh the page and try again.')]");
	
	
	public void searchRateSheet(String rateSheetType, String rateSheetName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Opened RateSheet", openedRateSheet))
		{
			if(rateSheetName.equals(get_text(openedRateSheet)))
				System.out.println("Already RateSheet Opened");
			else
				selectRateSheet(rateSheetType,rateSheetName);
		}
		else
			selectRateSheet(rateSheetType,rateSheetName);
	} 
	
	
	// Search and Select Rate Sheet
	public void selectRateSheet(String rateSheetType, String rateSheetName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(rateSheetType.equalsIgnoreCase("VIEW"))
		{}
		else
		{
			if(get_field_value("Rate Sheet show Dropdown",rateSheetShowDD).equals(rateSheetType))
			{}		
			else
			{	
				click_button("Rate Sheet show Dropdown", rateSheetShowDD);
				
				if(rateSheetType.equals("Production"))
					click_button("Production Option", productionOption);
				else
					click_button("Modeling Option", modelingOption);
			}			
		}
			
		enter_text_value("Rate Sheet Search", rateSheetSearch, rateSheetName);
		performKeyBoardAction("ENTER");	
		fixed_wait_time(3);		
			
		if(rateSheetName.isEmpty())
		{
			waitFor(rateSheetFirtstSearchResult, "Ratesheet search result");
			oParameters.SetParameters("FirtstRateSheetCode", get_field_value("First RateSheet Name", rateSheetFirtstSearchResult));
			click_button("First Rate Sheet", rateSheetFirtstSearchResult);
			waitFor(rateSheetTitleBar, "Ratesheet title");
			
			if(oParameters.GetParameters("FirtstRateSheetCode").equalsIgnoreCase(get_field_value("Rate Sheet Code", rateSheetTitleBar).replace("Code ", "")))
				oReport.AddStepResult("", "Clicked On "+oParameters.GetParameters("FirtstRateSheetCode")+" Rate sheet, Verified that particular Rate sheet is opened", "PASS");
			else
				oReport.AddStepResult("", "Clicked On "+oParameters.GetParameters("FirtstRateSheetCode")+" Rate sheet, But that particular Rate sheet is not opened", "FAIL");		
		}
		else
		{				
		//	By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'"+rateSheetName+"')]");
			By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[.//text()='"+rateSheetName+"']");
			
			if(IsDisplayed("Searched RateSheet", existedRateSheet))
			{
				click_button("Searched Rate Sheet", existedRateSheet);
				
				if(IsDisplayed("RateSheet Deleted Error Notification", rateSheetDeletedErrorNotification))
					oReport.AddStepResult("", "Searched with "+rateSheetName+" Ratesheet was already deleted but still displaying in search result list ", "FAIL");
				else
				{
					waitFor(rateSheetTitleBar, "Ratesheet title");
					
					if(rateSheetName.equalsIgnoreCase(get_field_value("Rate Sheet Name", openedRateSheet)))
						oReport.AddStepResult("", "Clicked On "+rateSheetName+" Rate sheet, Verified that particular Rate sheet is opened", "PASS");
					else
						oReport.AddStepResult("", "Clicked On "+rateSheetName+" Rate sheet, but that particular Rate sheet is not opened", "FAIL");
				}
			}	
			else if(IsDisplayed("No Items Found Message", noItemsFoundInfo))
			{
				System.out.println("There is no Ratesheet with "+rateSheetName+" name");
				oParameters.SetParameters("RateSheetPresent", "NO");
				return;
			}
			else
			{			
				oReport.AddStepResult("", "Searched with "+rateSheetName+" RateSheet but couldn't find that Ratesheet in search result and 'No Items Found Message' also not displayed  ", "FAIL");
				oParameters.SetParameters("RateSheetPresent", "NO");
			}			
		}
	}

	
	// Rate Sheet search
	public void rateSheetSearch(By rateSheetTypeDD,String rateSheetName) 
	{
		click_button("Rate Sheet show DD", rateSheetShowDD);
		click_button("Rate Sheet show Dropdown", rateSheetTypeDD);
		enter_text_value("Rate Sheet Search", rateSheetSearch, rateSheetName);
		performKeyBoardAction("ENTER");

		By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'"+rateSheetName+"')]");

		if(IsDisplayed("Carot Down", carotDown) && IsDisplayed("Searched Rate Sheet", existedRateSheet))
			oReport.AddStepResult("Rate Sheet Search","Searched with '"+rateSheetName+"' Rate Sheet, Verified that Ratesheet is Displayed and carot facing down words ", "PASS");
		else
			oReport.AddStepResult("Rate Sheet Search","Searched with '"+rateSheetName+"' Rate Sheet but that Ratesheet is not Displayed or carot not facing down words ", "FAIL");
		
		if(IsDisplayed("Carot up", carotUp))
			click_button("Carot up", carotUp);
	}

	
	By searchBoxClearButton = By.xpath("//div[@class='fa fa-times-circle close-icon'][@ng-click='cancelSearch()']");
	
	By noItemsFoundInfo = By.xpath("//div[@class='no-results margin-top ng-scope ng-binding'][contains(text(),'No items were found')]");

	
	// Clicking on carot icon
	public void clickCarotIcon()
	{
		click_button("Carot Button", carotDown);

		if(IsDisplayed("Carot Up", carotUp))
			oReport.AddStepResult("Carot Button","Clicked on Carot Button and Verified that displayed search list is collapsed ", "PASS");
		else
			oReport.AddStepResult("Carot Button","Clicked on Carot Button but displayed search list is not collapsed ", "FAIL");
	}
	
	
	// No Result found message validation
	public void noResultsFoundMessage()
	{
		enter_text_value("Rate Sheet Search", rateSheetSearch, get_random_alphanumeric(8));
		performKeyBoardAction("ENTER");
		fixed_wait_time(4);
		 
		if(IsDisplayed("No Items Found Message", noItemsFoundInfo))
			oReport.AddStepResult("No Items Found Message", "Searched with invalid Rate Sheet name, Verified that No Result found message is displayed", "PASS");
		else
			oReport.AddStepResult("No Items Found Message", "Searched with invalid Rate Sheet name, But No Result found message is not displayed", "FAIL");
	}
	
	
	By addRateSheetButton = By.xpath("//a[@class='button link-btn hand-cursor ng-isolate-scope'][@title='Add Rate Sheet']/i[@class='left fa fa-plus-square']");

	By rateSheetNameTextBox = By.xpath("//div[@class='workflow modal-medium']//input[@id='ratesheetName']");

	By rateSheetCodeTextBox = By.xpath("//div[@class='workflow modal-medium']//input[@id='ratesheetCode']");

	By modelingRadio = By.xpath("//div[@class='workflow modal-medium']//label[contains(.,'Modeling')]/input");

	By productionRadio = By.xpath("//div[@class='workflow modal-medium']//label[contains(.,'Production')]/input");

	By DRGGroupingDD = By.xpath("//div[@class='workflow modal-medium']//select[@id='claimDrg']");
	
	By qualifyInpatientDD = By.xpath("//div[@class='workflow modal-medium']//select[@id='qualInpatient']");
	
	By qualifyOutpatientDD = By.xpath("//div[@class='workflow modal-medium']//select[@id='qualOutpatient']");

	By rateSheetStartDate = By.xpath("//div[@class='workflow modal-medium']//input[@id='startDate']");

	By rateSheetEndDate = By.xpath("//div[@class='workflow modal-medium']//input[@id='endDate']");

	By addANoteBox = By.xpath("//div[@class='workflow modal-medium']//textarea[@placeholder='Add a note']");

	By addRateSheetWindowSave = By.xpath("//div[@class='workflow modal-medium']//input[@id='button.saveId']");

	By addRateSheetWindowCancel = By.xpath("//div[@class='workflow modal-medium']//input[@id='button.cancelId']");

	By rateSheetDelete = By.xpath("//div[@id='ratesheetView']//span[@class='lnk-btn-txt ng-binding'][text()='Delete']");

	By unsavedWindowDelete = By.xpath("//div[@class='form-button-panel']//input[@class='btn btn-danger discard-red']");
	
	By copyDetailsfromExistingRateSheetCheckBox = By.xpath("//div[@class='workflow modal-medium']//div[@class='col-lg-12 col-md-12 col-sm-12 ng-scope']//input[@id='copyClosed']");

	By copyRateSheetSearchBox = By.xpath("//div[@class='workflow modal-medium']//input[@id='copyRateSheetSet']");

	By copyRateSheetSearchResult = By.xpath("//div[@class='workflow modal-medium']//li/a[contains(.,'RATESHEETADDITIVERATETYPE_DND')]");

	By includeTermNotesCheckBox = By.xpath("//div[@class='workflow modal-medium']//div[@class='form-group col-lg-12 col-md-12 col-sm-12 pad-l-0']/input[@id='includeNotes']");

	
	// Adding Rate Sheet From Existing Rate Sheet
	public void addingRateSheetFromExistingRateSheet() 
	{
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "AddingRatesheetFromExisting");
		addRateSheetIcon();
		AddRateSheetDetails("");
		rateSheetSaveButton(""); 
		
	//	By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'aWWW')]");
	//	By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'Test Karthik')]");
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'TotalCovered_ExclusionTerm_PerCase')]");

		if(IsDisplayed("Added Rate Sheet", existedTerm))
			oReport.AddStepResult("New Rate Sheet",	"New Rate Sheet is created with all the terms and notes copied from existed Rate Sheet ", "PASS");
		else
			oReport.AddStepResult("New Rate Sheet","While adding Rate Sheet filled all the mandatory fields then clicked on Copy Details from Existing Rate Sheet check box then selected existed Rate Sheet but new Rate Sheet is not created with all the terms and notes copied from existed Rate Sheet ","FAIL");
	}

	
	By duplicateCodeError = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Error Adding Rate Sheet')]");

	By errorPopupOK = By.xpath("//div[@id='dialog']//input[@value='OK']");

	By unsavedMessageWindow = By.xpath("//div[contains(text(),'You have unsaved changes')]");
	
	
	public void addRatesheetWindowCancelScenario()
	{
		click_button("Add RateSheet Window Cancel", rateSheetCodeCancelButton);
		
		if(IsDisplayed("Unsaved Changes popup", unsavedMessageWindow))
		{
			click_button("Unsaved Changes popup Delete", unsavedWindowDelete);

			if(IsDisplayed("Add Rate Sheet Window", addRateSheetWindow))
				oReport.AddStepResult("Unsaved Changes popup","Clicked on Add Rate Sheet then filled all the mandatory fields then clicked on cancel but unsaved changes pop up is not displayed and Add Rate Sheet window is not closed upon clicking on Discard ","FAIL");
			else
				oReport.AddStepResult("Unsaved Changes popup","Unsaved changes pop up is displayed and Add Rate Sheet window is closed upon clicking on Discard ","PASS");
		} 
		else
			oReport.AddStepResult("Unsaved Changes popup","Clicked on Add Rate Sheet then filled all the mandatory fields then clicked on cancel but unsaved changes pop up is not displayed ","FAIL");	
	}

	
	// Validating duplicate Rate Sheet code Error message and Unsaved changes pop up
	public void duplicateRatesheetCodePopup(String key) 
	{		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","RateSheets_TestData.xlsx","RateSheet_VR", key);    
		addRateSheetIcon();
		AddRateSheetDetails("");
		
		click_button("Add Rate Sheet Window Save", rateSheetCodeSaveButton);

		if(IsDisplayed("Duplicate Code Error Notification", duplicateCodeError))
			oReport.AddStepResult("Duplicate Ratesheet popup",	"Clicked on Add Rate Sheet icon and filled existed rate sheet details then clicked on save button then Verified that Error notification displayed with message as 'Duplicate rate sheet code. Cannot create rate sheet with the same code' ",	"PASS");
		else
			oReport.AddStepResult("Duplicate Ratesheet popup","Clicked on Add Rate Sheet icon and filled existed rate sheet details then clicked on save button but Error notification is not displayed with message as 'Duplicate rate sheet code. Cannot create rate sheet with the same code' ","FAIL");

		click_button("Error Popup OK", errorPopupOK);
	}

	By rateSheetEditIcon = By.xpath("//div[@id='ratesheetSection']//span[@class='lnk-btn-txt ng-binding'][text()='Edit']");

	By editRateSheetWindow = By.xpath("//div[@class='workflow modal-medium']//div[@title='Edit Rate Sheet']");

	
	// Rate Sheet editing
	public void editRateSheet(By rateSheetType, String rateSheetName, String DRGGroup) 
	{
		waitFor(rateSheetSearch, "RateSheet Search box");
		click_button("Rate Sheet show DD", rateSheetShowDD);
		click_button("Rate Sheet show DD Value", rateSheetType);
		enter_text_value("Rate Sheet Name", rateSheetSearch, rateSheetName);
		performKeyBoardAction("ENTER");

		By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'"+rateSheetName+"')]");

		click_button("Existing Rate Sheet", existedRateSheet);
		waitFor(rateSheetTitleBar, "RateSheet Title Bar");
		oParameters.SetParameters("RateSheetCodeBeforeEdit", get_field_value("Rate Sheet Code", rateSheetTitleBar));
		click_button("Rate Sheet Edit Icon", rateSheetEditIcon);

		if(IsDisplayed("Edit Rate Sheet Window", editRateSheetWindow))
			oReport.AddStepResult("Edit RateSheet Window","Clicked on existed Rate Sheet Edit icon Verified that Edit Rate Sheet window is displayed ",	"PASS");
		else
			oReport.AddStepResult("Edit RateSheet Window","Clicked on existed Rate Sheet Edit icon but Edit Rate Sheet window is not displayed ",	"FAIL");

		// Modifying Rate Sheet name,code and DRG Grouping

		enter_text_value_without_clear("Rate Sheet Name", rateSheetNameTextBox, "0");
		enter_text_value_without_clear("Rate Sheet Code", rateSheetCodeTextBox, "0");
		select_option("DRG Grouping", DRGGroupingDD, DRGGroup);
		click_button("Edit RateSheet Window Save", addRateSheetWindowSave);
		waitFor(rateSheetTitleBar, "RateSheet Title Bar");
		oParameters.SetParameters("RateSheetCodeAfterEdit", get_field_value("Rate Sheet Code", rateSheetTitleBar));

		if(oParameters.GetParameters("RateSheetCodeBeforeEdit").equals(oParameters.GetParameters("RateSheetCodeAfterEdit")))
			oReport.AddStepResult("Edited Rate Sheet","Existed Rate Sheet name, Code and DRG are Modified then clicked on save but those changes are not saved ","FAIL");
		else
			oReport.AddStepResult("Edited Rate Sheet","Existed Rate Sheet name, Code and DRG are Modified then clicked on save those changes are saved ","PASS");
	}

	
	By rateSheetHistoryTab = By.xpath("//a[text()='History']");
	
	By selectedHistoryTab = By.xpath("//li[@class='portal-tab-pane ng-scope active']/a[contains(text(),'History')]");
	
	// This method will click on History Tab
	public void clickHistoryTab()
	{
		navigate_to("History Tab", "Selected History Tab", rateSheetHistoryTab, selectedHistoryTab);
	}
	
	
	// Checking Rate Sheet History tab log added or not
	public void rateSheetHistoryTabLog() 
	{
		waitFor(rateSheetHistoryTab, "Ratesheet History Tab");
		clickHistoryTab();
		waitFor(noOfRecordsElement, "No. of Records");
		oParameters.SetParameters("NoOfHistoryRecords",get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));

		if(Integer.parseInt(oParameters.GetParameters("NoOfHistoryRecords")) > 1)
			oReport.AddStepResult("History Log","Clicked on History Tab Verified that history tab displayed all the changes made ", "PASS");
		else
			oReport.AddStepResult("History Log","Clicked on History Tab but history tab not displayed all the changes made ", "FAIL");
	}

	By rateSheetSummaryTab = By.xpath("//a[text()='Rate Sheet Summary']");

	By summaryTabNote = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12']//textarea[@placeholder='Add a note']");

	By summaryTabNoteSave = By.xpath("//div[@ratesheet-id='ratesheetId']//input[@class='btn btn-default margin-t-2'][@value='Save']");

	
	public void rateSheetNote(String note)
	{
		click_button("RateSheet Summary Tab", rateSheetSummaryTab);
		waitFor(summaryTabNote, "Summary Tab Note");
		click_button("RateSheet Summary Note", summaryTabNote);
		oParameters.SetParameters("SummaryTabNote", get_field_value("RateSheet Summary Tab", summaryTabNote));

		if(note.equals(oParameters.GetParameters("SummaryTabNote")))
			oReport.AddStepResult("RateSheet Summary Tab","Clicked on Rate Sheet Summary Tab, Verified that added Rate Sheet Note displayed ", "PASS");
		else
			oReport.AddStepResult("RateSheet Summary Tab","Clicked on Rate Sheet Summary Tab but added Rate Sheet Note not displayed ", "FAIL");		
	}
	
	
	// Editing Rate sheet summary note
	public void editNote()
	{
		click_button("RateSheet Summary Note", summaryTabNote);
		enter_text_value_without_clear("Summary Tab Note", summaryTabNote, " Edited");
		click_button("Summary Tab Note Save", summaryTabNoteSave);
		fixed_wait_time(4);
		click_button("RateSheet Summary Note", summaryTabNote);
		oParameters.SetParameters("SummaryTabNoteAfterEdit", get_field_value("RateSheet Summary Tab", summaryTabNote));

		if(oParameters.GetParameters("SummaryTabNoteAfterEdit").equals(oParameters.GetParameters("SummaryTabNote")))
			oReport.AddStepResult("Summary Note","Navigated to Rate Sheet Summary Tab then Edited the Summary Note but Modified Rate Sheet Note is not displayed ","FAIL");
		else
			oReport.AddStepResult("Summary Note","Navigated to Rate Sheet Summary Tab then Edited the Summary Note, Verified that Modified Rate Sheet Note is displayed","PASS");
		
	}
	
	By rateSheetDeleteIcon = By.xpath("//div[@id='ratesheetSection']//span[@class='lnk-btn-txt ng-binding'][text()='Delete']");

	By nothingSelectdMessage = By.xpath("//div[@id='ratesheetView']//div[@class='first-line ng-scope ng-binding'][text()='Nothing is selected.']");

	By addSectionWindowSave = By.xpath("//div[@id='addEditRateSheetSection']//div[@id='fullFooter']//input[@id='button.saveId']");

	
	// Deleting and Creating new Rate Sheet with deleted rate sheet Details
	public void deletingandCreatingRateSheet(String rateSheetName, String rateSheetCode, By rateSheetTypeRadio)
	{
		click_button("RateSheet Delete Icon", rateSheetDeleteIcon);
		waitFor(unsavedWindowDelete, "");

		if(IsDisplayed("Rate Sheet Delete pop up", unsavedWindowDelete))
			oReport.AddStepResult("Delete Pop up","Clicked on Rate Sheet Delete icon, Verified that Delete Rate Sheet pop up displayed ", "PASS");
		else
			oReport.AddStepResult("Delete Pop up","Clicked on Rate Sheet Delete icon but Delete Rate Sheet pop up is not displayed ","FAIL");

		// Deleting Rate Sheet

		click_button("Rate Sheet Delete", unsavedWindowDelete);
		waitFor(nothingSelectdMessage, "");

		if(IsDisplayed("Nothing Selectd Message", nothingSelectdMessage))
			oReport.AddStepResult("Rate Sheet Delete", "Clicked on Rate Sheet delete that Rate Sheet is Deleted","PASS");
		else
			oReport.AddStepResult("Rate Sheet Delete","Clicked on Rate Sheet delete but that Rate Sheet is not Deleted", "FAIL");

		// Adding Rate Sheet with Deleted Rate Sheet Details

		click_button("Add Rate Sheet Button", addRateSheetButton);
		waitFor(rateSheetNameTextBox, "");
		enter_text_value("Rate Sheet Name", rateSheetNameTextBox, rateSheetName);
		enter_text_value("Rate Sheet Code", rateSheetCodeTextBox, rateSheetCode);
		select_option("Qualify Inpatient Claims On Dropdown", qualifyInpatientDD, "0");
		select_option("Qualify Outpatient Claims On Dropdown", qualifyOutpatientDD, "2");
		enter_text_value("Rate Sheet Start Date", rateSheetStartDate, get_specified_date(-3000));
		performKeyBoardAction("ENTER");
		click_button("Rate Sheet Type", rateSheetTypeRadio);
		click_button("Add Rate Sheet Window Save", addRateSheetWindowSave);

		By addedRateSheet = By.xpath("//div[@class='document-title-bar ng-scope']/div[@class='pull-right xl-header pad-r-5 ng-binding'][contains(.,'"+ rateSheetCode + "')]");

		waitFor(addedRateSheet, "");

		if(IsDisplayed("Added Rate Sheet", addedRateSheet))
			oReport.AddStepResult("New Rate Sheet","Clicked on Add Rate Sheet icon and filled mandatory fields, Selected Effective date then clicked on save button that rate sheet is created","PASS");
		else
			oReport.AddStepResult("New Rate Sheet",	"Trying to create a Rate Sheet with deleted Rate Sheet details, Clicked on Add Rate Sheet then filled mandatory fields, Selected Effective date then clicked on save button but that rate sheet is not Created","FAIL");
	}

	By rateSheetPeriodDropDown = By.xpath("//div[@source='ratesheets']//button[@class='btn btn-light btn-default btn-sm']");

	By addPeriodElement = By.xpath("//div[@source='ratesheets']//ul/li[@class='hand-cursor border-b-1']");

	By existedPeriod = By.xpath("//div[@source='ratesheets']//ul/li[2]");
	
	By existedPeriodSecond = By.xpath("//div[@source='ratesheets']//ul/li[3]");

	By periodEditIcon = By.xpath("//i[@class='fa fa-fw fa-pencil show-on-hover'][@style='display: inline-block;']");

	By periodDeleteIcon = By.xpath("//i[@class='fa fa-fw fa-minus-square pull-right show-on-hover period-minus'][@style='display: inline-block;']");

	By periodActiveStatus = By.xpath("//div[@source='ratesheets']//ul/li[2]//span/i[@class='fa fa-circle active-circle']");

	By periodInactiveStatus = By.xpath("//div[@source='ratesheets']//ul/li[2]//span/i[@class='fa fa-exclamation-circle inactive-circle']");

	By periodDropDownElements = By.xpath("//div[@source='ratesheets']//ul/li");

	By noEndDateNotification = By.xpath("//h5[text()='There is currently a period with no end date']");

	By rateSheetPeriodEffectiveDate = By.xpath("//input[@id='startDateRatesheetPeriod']");

	By rateSheetPeriodTerminationDate = By.xpath("//input[@id='stopDateRatesheetPeriod']");

	By rateSheetPeriodWindowSave = By.xpath("//div[@id='addPeriodModal']//input[@id='button.saveId']");

	By cannotDeletePopUp = By.xpath("//div[@class='medium-header bold ng-binding'][contains(.,'Cannot Delete Active Period')]");

	By periodDeleteButton = By.xpath("//div[@class='form-button-panel']//input[@class='btn btn-danger']");

	
	// Rate Sheet period functionality Validation
	public void rateSheetPeriodValidation() 
	{
		// Checking There is currently a period with no end date notification displaying or not

		click_button("RateSheet Period DropDown", rateSheetPeriodDropDown);
		oParameters.SetParameters("RateSheetPeriodCountBeforeAdding",String.valueOf(get_table_row_count(periodDropDownElements)));
		click_button("Add Period Element", addPeriodElement);
		fixed_wait_time(3);

		if(IsDisplayed("No End Date Notification", noEndDateNotification))
			oReport.AddStepResult("No End Date Info","Clicked on Add Period and Verified that Add Effective Period window displaying 'There is currently a period with no end date' ","PASS");
		else
			oReport.AddStepResult("No End Date Info","Clicked on Rate Sheet Period Drop Down then clicked on Add Period but Add Effective Period window not displaying 'There is currently a period with no end date' notification ","FAIL");

		// Entering dates and saving the period

		enter_text_value("RateSheet Effective Date", rateSheetPeriodEffectiveDate, get_specified_date(2001));
		enter_text_value("RateSheet Termination Date", rateSheetPeriodTerminationDate, get_specified_date(2002));
		click_button("RateSheet Period Window Save", rateSheetPeriodWindowSave);
		fixed_wait_time(4);
		click_button("RateSheet Period DropDown", rateSheetPeriodDropDown);
		oParameters.SetParameters("RateSheetPeriodCountAfterAdding",String.valueOf(get_table_row_count(periodDropDownElements)));

		if(oParameters.GetParameters("RateSheetPeriodCountBeforeAdding").equals(oParameters.GetParameters("RateSheetPeriodCountAfterAdding")))
			oReport.AddStepResult("Adding Period","Tried to add another period, Entered Effective and Termination Date then clicked on save but new peroid is not added ","FAIL");
		else
			oReport.AddStepResult("Adding Period", "New Period added successfully ", "PASS");

		// Period Edit and Delete icon checking

		mouse_hover("Rate sheet Period", existedPeriodSecond);

		if(IsDisplayed("Period Edit Icon", periodEditIcon)&& IsDisplayed("Period Delete Icon", periodDeleteIcon))
			oReport.AddStepResult("Edit and Delete Icon","Mouse Hovered to period date Verified that Edit and Delete icon is Displayed", "PASS");
		else
			oReport.AddStepResult("Edit and Delete Icon","Mouse Hovered to period date but Edit and Delete icon is not Displayed", "FAIL");

		// Editing period status from Inactive to Active

		click_button("Period Edit Icon", periodEditIcon);
		click_button("Period Active Radio", editPeriodActiveRadio);
		click_button("RateSheet Period Window Save", rateSheetPeriodWindowSave);
		click_button("RateSheet Period DropDown", rateSheetPeriodDropDown);

		if(IsDisplayed("Period Active Status", periodActiveStatus))
			oReport.AddStepResult("Active Period", "Verified that selected period is in active state ", "PASS");
		else
			oReport.AddStepResult("Active Period","Clicked on period edit icon then status changed from Inactive to Active then clicked on Save but that selected period is not in Active state ","FAIL");

		// Cannot Delete Active Period pop up Validation

		mouse_hover("Rate sheet Period", existedPeriod);
		click_button("Period Delete Icon", periodDeleteIcon);
		fixed_wait_time(2);

		if(IsDisplayed("Cannot Delete PopUp", cannotDeletePopUp))
			oReport.AddStepResult("Pop up","Tried to delete Active state period, Verified that Cannot Delete Active Period pop up is displayed ","PASS");
		else
			oReport.AddStepResult("Pop up","Tried to delete Active state period but Cannot Delete Active Period pop up is not displayed ","FAIL");

		click_button("Cannot Delete Active Period pop up OK Button", ErrorSavingRateSheetOKButton);

		// Editing period status from Active to inactive

		click_button("RateSheet Period DropDown", rateSheetPeriodDropDown);
		mouse_hover("Rate sheet Period", existedPeriod);
		click_button("Period Edit Icon", periodEditIcon);
		click_button("Period Inactive Radio", editPeriodInactiveRadio);
		click_button("RateSheet Period Window Save", rateSheetPeriodWindowSave);
		click_button("RateSheet Period DropDown", rateSheetPeriodDropDown);

		if(IsDisplayed("Period Inactive Status", periodInactiveStatus))
			oReport.AddStepResult("Inactive Period", "Verified that selected period is in Inactive state ", "PASS");
		else
			oReport.AddStepResult("Inactive Period","Clicked on period edit icon then status changed from Active to Inactive then clicked on Save but that selected period is not in Inactive state ","FAIL");

		// Deleting period

		mouse_hover("Rate sheet Period", existedPeriod);
		click_button("Period Delete Icon", periodDeleteIcon);
		click_button("Period Delete Button", periodDeleteButton);
		click_button("RateSheet Period DropDown", rateSheetPeriodDropDown);
		oParameters.SetParameters("RateSheetPeriodCountAfterRemoving",String.valueOf(get_table_row_count(periodDropDownElements)));

		if(oParameters.GetParameters("RateSheetPeriodCountAfterAdding").equals(oParameters.GetParameters("RateSheetPeriodCountAfterRemoving")))
			oReport.AddStepResult("Period Deleted","Tried to delete existed period,clicked on delete Period but that peroid is not deleted ", "FAIL");
		else
			oReport.AddStepResult("Period Deleted", "Existed Period Deleted successfully ", "PASS");
	}

	
	By copyDetailsCheckBoxForPeriod = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 mar-t-12 row form-group mar-l-0']//input[@id='copyClosed']");

	By selectRateSheetSearchResult = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 mar-t-12 row form-group mar-l-0']//div[@class='col-md-9 col-sm-9 col-lg-9 form-group  form-group ']//li/a[contains(.,'AUTOMATIONRATESHEET_DND')]");

	By searchRateSheetincludeTermNotesCheckBox = By.xpath("//fieldset[@class='bg-light-gray ng-scope']//div[@class='form-group col-lg-12 col-md-12 col-sm-12 pad-l-0']/input[@id='includeNotes']");

	By addPeriodStartDate = By.xpath("//input[@id='startDateRatesheetPeriod']");

	By addPeriodWindowSave = By	.xpath("//workflow-modal[@show='showPeriodModal']//div[@id='fullFooter']//input[@id='button.saveId']");

	
	// Adding period with copying details from existing Rate sheet
	public void addingPeriodFromExistingRateSheet() 
	{
		click_button("Add Period Element", addPeriodElement);
		enter_text_value("Add Period Start Date", addPeriodStartDate, get_specified_date(2003));
		performKeyBoardAction("ENTER");
		click_button("Copy Details from Existing Rate Sheet Check Box", copyDetailsCheckBoxForPeriod);
		waitFor(selectRateSheetSearchBox, "");

		if(IsDisplayed("Select RateSheet SearchBox", selectRateSheetSearchBox))
			oReport.AddStepResult("Search Box", "Verified that option to select Rate Sheet and Period is displayed ","PASS");
		else
			oReport.AddStepResult("Search Box","Clicked on Add Period, Checked copy details from existing Rate Sheet box but option to select Rate Sheet and Period is not displayed","FAIL");

		// Searching and selecting existed Rate Sheet

		enter_text_value("Copy Rate Sheet Search Box", selectRateSheetSearchBox, "AutomationRateSheet_DND");
		performKeyBoardAction("ENTER");
		fixed_wait_time(4);
		click_button("Copy Rate Sheet Search Result", selectRateSheetSearchResult);
		click_button("Include Term Notes Check Box", searchRateSheetincludeTermNotesCheckBox);
		click_button("Add Rate Sheet Window Save", addPeriodWindowSave);

		By existedTem = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'TotalCovered_ExclusionTerm_PerCase')]");

		waitFor(existedTem, "");

		if(IsDisplayed("Added Rate Sheet", existedTem))
			oReport.AddStepResult("New Rate Sheet","New Rate Sheet is created with all the terms and notes copied from existed Rate Sheet ", "PASS");
		else
			oReport.AddStepResult("New Rate Sheet","While adding period searched and selected existed Rate Sheet filled all the mandatory fields then clicked on Copy Details from Existing Rate Sheet check box then selected existed Rate Sheet but new Rate Sheet is not created with all the terms and notes copied from existed Rate Sheet ","FAIL");
	}

	
	By addFilterButton = By.xpath("//div[@id='ratesheetSection']//span[text()='Add Filter']");
	
	By filtersSearchBox  = By.xpath("//li[@class='multi-filter-item pull-left']//input[@class='date dateModel ng-valid is-dateEntry is-datepick ng-dirty']");

	By showOperatorsButton = By.xpath("//li[@class='pull-left multi-filter-item ng-scope']//a[@class='pull-left operand ng-scope ng-binding']");
	
	By dateAfterElement = By.xpath("//a[contains(text(),'After')]");
	
	By dateBeforeElement = By.xpath("//a[contains(text(),'Before')]");

	By dateBetweenElement = By.xpath("//a[contains(text(),'Between')]");
	
	By dateRangeElement = By.xpath("//div[@class='filter-value pull-left ng-scope ng-binding']");
	
	By startDate = By.xpath("//input[@id='\"dateFrom\"+af.id']");
	
	By filterButton = By.xpath("//input[@value='Filter']");

	By dateColumn = By.xpath("//*[@id='table-data']/table/tbody/tr[1]//span[@ng-switch-when='date']");

	By userColumn = By.xpath("//*[@id='table-data']/table/tbody/tr[1]//td[2]/span");

	By auditDetailColumn = By.xpath("//*[@id='table-data']/table/tbody/tr[1]//td[3]//div[@class='ng-scope']//span[@class='ng-binding']");

	By typeOperator = By.xpath("//a[@class='pull-left operand ng-scope ng-binding'][@data-trigger='click']");

	By clearFilters = By.xpath("//div[@id='ratesheetView']//div[@id='filter-ratesheets']//i[@class='left fa-lg fa fa-times-circle']");

	
	// Rate Sheet History Date filter reports
	public void dateFilterReports()
	{
		// Date Filter Reports
		// Equals Date filters Reports
		
		waitFor(noOfRecordsElement, "No. of Records");
		click_button("Add Filter Button", addFilterButton);
		fixed_wait_time(2);
		click_button("Filters Search Box", filtersSearchBox);
		fixed_wait_time(2);
		enter_text_value("Date Equals", filtersSearchBox, "04/30/2019");
		performKeyBoardAction("ENTER");
		fixed_wait_time(2);
		click_button("Filters Button", filterButton);
		waitFor(dateColumn, "Date Column");
		oParameters.SetParameters("RateSheetDate", get_field_value("Date Column", dateColumn).substring(0, 10));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		try
		{
		  Date date1 = sdf.parse("04/30/2019");
		  Date date2 = sdf.parse(oParameters.GetParameters("RateSheetDate"));
		  
		  if(date1.equals(date2))
			  oReport.AddStepResult("RateSheet Reports ","Rate Sheets are filtered with Date Equals Operator those reports are displayed" , "PASS"); 
		  else
			  oReport.AddStepResult("RateSheet Reports ","Rate Sheet history log is filtered with Equals Date but those reports are Not Displayed", "FAIL");
		}
		catch (ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}

		// Greater than filter reports
		
		click_button("Show All Operators Button", showOperatorsButton);
		click_button("Date After Element", dateAfterElement);
		click_button("Filters Button", filterButton);
		waitFor(dateColumn, "Date Column");
		oParameters.SetParameters("RateSheetDate", get_field_value("Date Column", dateColumn).substring(0, 10));

		try
		{
		  Date date3 = sdf.parse("07/19/2018");
		  Date date4 = sdf.parse(oParameters.GetParameters("RateSheetDate"));
		  
		  if(date4.after(date3))
			  oReport.AddStepResult("RateSheet Reports ","Rate Sheets are filtered with Date After Operator those reports are displayed" , "PASS"); 
		  else
			  oReport.AddStepResult("RateSheet Reports ","Rate Sheet history log is filtered with Date After but those reports are Not Displayed", "FAIL");
		}
		catch (ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		 
		// Less than filter reports
		
		click_button("Show All Operators Button", showOperatorsButton);
		click_button("Date Before Element", dateBeforeElement);
		click_button("Filters Button", filterButton);
		waitFor(dateColumn, "Date Column");
		oParameters.SetParameters("RateSheetDate", get_field_value("Date Column", dateColumn).substring(0, 10));

		try
		{
		  Date date3 = sdf.parse("07/19/2018");
		  Date date4 = sdf.parse(oParameters.GetParameters("RateSheetDate"));
		  
		  if(date3.after(date4))
			  oReport.AddStepResult("RateSheet Reports ","Rate Sheets are filtered with Date Before Operator those reports are displayed" , "PASS"); 
		  else
			  oReport.AddStepResult("RateSheet Reports ","Rate Sheet history log is filtered with Date Before but those reports are Not Displayed", "FAIL");
		}
		catch (ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		
		// Between filter reports
		
		click_button("Show All Operators Button", showOperatorsButton);
		click_button("Date Between Element", dateBetweenElement);
		click_button("Date Range Element", dateRangeElement);
		fixed_wait_time(2);
		enter_text_value_without_clear("Start Date", startDate, "05/15/2019");
		performKeyBoardAction("ENTER");
		click_button("Filters Button", filterButton);
		waitFor(dateColumn, "Date Column");
		oParameters.SetParameters("RateSheetDate", get_field_value("Date Column", dateColumn).substring(0, 10));

		try
		{
		  Date date3 = sdf.parse("05/15/2019");
		  Date date4 = sdf.parse(oParameters.GetParameters("RateSheetDate"));
		  
		  if(date4.after(date3))
			  oReport.AddStepResult("RateSheet Reports ","Rate Sheets are filtered with Date Between Operator those reports are displayed" , "PASS"); 
		  else
			  oReport.AddStepResult("RateSheet Reports ","Rate Sheet history log is filtered with Date Between but those reports are Not Displayed", "FAIL");
		}
		catch (ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		
		click_button("Clear Filters", clearFilters);		
	}
	

	By rateSheetfiltersDropDown = By.xpath("//div[@id='ratesheetSection']//a[@class='filter-label hand-cursor pull-left ng-scope']");

	By auditDetailElement = By.xpath("//li/a[contains(text(),'Audit Detail')]");

	By filterSearchBox = By.xpath("//div[@id='ratesheetSection']//input[@title='Enter text to filter and press enter.'][@class='text']");

	By userElement = By.xpath("//li/a[contains(text(),'User')]");

	
	// Audit Detail Filter Reports
	public void auditDetailFilterReports() 
	{
		oParameters.SetParameters("AuditDetailValueBefore", get_field_value("Audit Detail", auditDetailColumn));
		click_button("Add Filter", addFilterButton);
		click_button("RateSheet filters DropDown", rateSheetfiltersDropDown);
		click_button("Audit Detail Element", auditDetailElement);
		enter_text_value("Audit Detail Search Box", filterSearchBox,oParameters.GetParameters("AuditDetailValueBefore"));
		performKeyBoardAction("ENTER");
		click_button("Filter Button", filterButton);
		waitFor(auditDetailColumn, "Audit Detail Column");
		oParameters.SetParameters("AuditDetailValueAfter", get_field_value("Audit Detail", auditDetailColumn));

		if(oParameters.GetParameters("AuditDetailValueBefore").equalsIgnoreCase(oParameters.GetParameters("AuditDetailValueAfter")))
			oReport.AddStepResult("Audit Detail","Reports are filtered based on Audit Details those reports are Displayed ", "PASS");
		else
			oReport.AddStepResult("Audit Detail","Reports are filtered based on Audit Details but those reports are not Displayed ", "FAIL");

		oParameters.SetParameters("UserFilteredRecords", get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));
	}

	
	// User Filter Reports
	public void userFilterReports()
	{
		oParameters.SetParameters("UserValueBefore", get_field_value("User Column", userColumn));
		click_button("Add Filter", addFilterButton);
		click_button("RateSheet filters DropDown", rateSheetfiltersDropDown);
		click_button("User Element", userElement);
		enter_text_value("Filters Search Box", filterSearchBox, oParameters.GetParameters("UserValueBefore"));
		performKeyBoardAction("ENTER");
		click_button("Filter Button", filterButton);
		waitFor(userColumn, "User Column");
		oParameters.SetParameters("UserValueAfter", get_field_value("User Column", userColumn));

		if(oParameters.GetParameters("UserValueBefore").equalsIgnoreCase(oParameters.GetParameters("UserValueAfter")))
			oReport.AddStepResult("User", "Reports are filtered based on User those reports are Displayed ", "PASS");
		else
			oReport.AddStepResult("User", "Reports are filtered based on User those reports are not Displayed ","FAIL");

		click_button("Clear Filters", clearFilters);
	}
	
	
	// Clearing Filters
	public void clearFilters() 
	{
		click_button("Clear Filters Button", clearFilters);
	}
	
	
	By noOfRecordsElement = By.xpath("//div[@id='ratesheetSection']//div[@class='pull-left ng-binding'][contains(text(),'Records')]");
	
	By downloadCSVButton = By.xpath("//a[@title='Download CSV']//span[contains(text(),'Download')]");
	
	
	// Downloading Rate sheet history records
	public void downloadRateSheetHistory()
	{
		click_button("Download CSV Button", downloadCSVButton);
		fixed_wait_time(5);
		
		if(isFileDownloaded("rateSheetHistory.csv"))
			oReport.AddStepResult("", "Clicked on Download CSV icon, Verified that filtered results are downloaded", "PASS");
		else
			oReport.AddStepResult("", "Clicked on Download CSV icon, but that filtered results are not downloaded", "FAIL");
		
		csvToExcel();
		@SuppressWarnings("static-access")
		int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
		oParameters.SetParameters("recordsInExcel", String.valueOf(recordsCount));
		
		deleteFile("C:/CCM/Downloads/rateSheetHistory.csv");
		
		deleteFile("C:/CCM/Downloads/rateSheetHistory.xlsx");
		
/*		File csvFile = new File(oParameters.GetParameters("downloadFilepath")+"/rateSheetHistory.csv");
		csvFile.delete();
		
		File xlsxFile = new File(oParameters.GetParameters("downloadFilepath")+"/rateSheetHistory.xlsx");
		xlsxFile.delete();*/
		
		if(oParameters.GetParameters("UserFilteredRecords").equals(oParameters.GetParameters("recordsInExcel")))
			oReport.AddStepResult("", "Clicked on Download CSV icon, Verified that filtered results are displayed in excel sheet ", "PASS");
		else
			oReport.AddStepResult("", "Clicked on Download CSV icon, But that filtered results are not displayed in excel sheet ", "FAIL");		
	}
		

	By recordsPerPageDD = By.xpath("//div[@id='action-bar']//select[@style='border-radius: 5px']");

	By firstPageButton = By.xpath("//li[@class='num first']");

	By secondPageButton = By.xpath("//li[@class='num']/a[contains(text(),'2')]");

	By lastPageButton = By.xpath("//li[@class='num last']");

	By selctedSecondPage = By.xpath("//li[@class='num selected']/a[contains(text(),'2')]");

	By nextPageButton = By.xpath("//li[@class='next ng-scope ng-binding'][contains(text(),'Next')]");

	By prevPageButton = By.xpath("//li[@class='prev ng-scope ng-binding'][contains(text(),'Prev')]");

	By disabledPrevPageButton = By.xpath("//li[@class='prev disabled ng-scope ng-binding'][contains(text(),'Prev')]");

	
	// Number of records per page
	public void recordsPerPage(int NoOfRecords,String recordsPerPage)
	{
		click_button("RateSheet History Tab", rateSheetHistoryTab);
		waitFor(noOfRecordsElement, "");
		click_button("Filter Button", filterButton);
		waitFor(noOfRecordsElement, "");
		select_option("Records per page", recordsPerPageDD, String.valueOf(NoOfRecords));
		waitFor(recordsPerPageDD, "Records per page Dropdown");
		oParameters.SetParameters("NumOfRecords",get_field_value("No.Of Records", noOfRecordsElement).replace(" Records", ""));
		oParameters.SetParameters("LastPageNumber", get_field_value("Last Page No.", lastPageButton));
		int recordsCount = Integer.parseInt(oParameters.GetParameters("NumOfRecords"));
		int lastPageNumber = Integer.parseInt(oParameters.GetParameters("LastPageNumber"));
		int lastPageNum = lastPageNumber - 1;

		if(recordsCount / lastPageNum >= NoOfRecords)
			oReport.AddStepResult("Records Per page", "Clicked on Records per page drop down and choosed "+recordsPerPage+" results per page, verified that same number of records are displayed ", "PASS");
		else
			oReport.AddStepResult("Records Per page","Clicked on Records per page drop down and choosed "+recordsPerPage+" results per page but that same number of records are not displayed ","FAIL");
	}

	// Page Navigations
	public void pageNavigations()
	{
		// Second page

		if(IsDisplayed("Second Page Button", secondPageButton))
		{
			click_button("Second Page Button", secondPageButton);
			waitFor(selctedSecondPage, "");

			if(IsDisplayed("Selected Second Page", selctedSecondPage))
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
			waitFor(secondPageButton, "");

			if(!(IsDisplayed("Selected Second Page", selctedSecondPage)))//IsDisplayed("Next Page Button", nextPageButton))
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
			waitFor(prevPageButton, "");

			if(IsDisplayed("Prev Page Button", prevPageButton))
				oReport.AddStepResult("Next Page Results", "Next Page Results Displayed", "PASS");
			else
				oReport.AddStepResult("Page Results", "Clicked on Next page but that respective records not displayed","FAIL");
		} 
		else
			oReport.AddStepResult("Next Page Button", "No Next Page", "INFO");
	}

	By rateSheetPrintIcon = By.xpath("//a[@class='button link-btn hand-cursor ng-isolate-scope'][@title='Print']/i[@class='left fa fa-print']");

	By specificPagePrintRadio = By.xpath("//input[@type='radio'][@class='page-settings-custom-radio']");

	By printPageCancel = By.xpath("//button[@class='cancel'][contains(text(),'Cancel')]");

	
	// Print preview
	public void printPreview()
	{
		click_button("Rate Sheet Print Icon", rateSheetPrintIcon);
		fixed_wait_time(4);
		switchToNewWindow(2);
		waitFor(specificPagePrintRadio, "");

		if(IsDisplayed("Specific Page Print Radio", specificPagePrintRadio))
		{	
			fixed_wait_time(2);
			oReport.AddStepResult("Print Page", "Verified that all the data in Rate Sheet is displayed in print view ",	"PASS");
		}	
		else
			oReport.AddStepResult("Print Page",	"All the data in Rate Sheet is not displayed in print view ", "FAIL");

		click_button("Print page cancel button", printPageCancel);
		fixed_wait_time(3);
		switchToNewWindow(1);
	//	windowHandle("parent");
	}
	
	public void deleteRateSheetValidation(String rateSheetName)
	{
		click_button("RateSheet Delete Icon", rateSheetDeleteIcon);
		waitFor(unsavedWindowDelete, "Delete RateSheet pop up");
		click_button("Delete Ratesheet popup window delete button", unsavedWindowDelete);
		fixed_wait_time(10);
		
		waitFor(rateSheetSearch, "Rate Sheet Search box");
		enter_text_value("Rate Sheet Search", rateSheetSearch, rateSheetName);
		performKeyBoardAction("ENTER");	
		fixed_wait_time(3);
		
		if(IsDisplayed("No Items Found Message", noItemsFoundInfo))
			oReport.AddStepResult("Delete RateSheet", ""+rateSheetName+" Ratesheet is successfully deleted", "PASS");
		else
		{
			By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'"+rateSheetName+"')]");
			
			if(IsDisplayed("Searched RateSheet", existedRateSheet))
				oReport.AddStepResult("", "Searched with "+rateSheetName+" RateSheet but couldn't find that Ratesheet in search result and 'No Items Found Message' also not displayed  ", "FAIL");
			else
				oReport.AddStepResult("", "Searched with "+rateSheetName+" RateSheet verified that Ratesheet is deleted and not displayed in search result", "PASS");
		}
	}
	
		
	public void deleteRatesheet(String rateSheetType, String rateSheetName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(rateSheetShowDD,"RateSheet Type Dropdown");	
		
		if(IsDisplayed("Opened RateSheet", openedRateSheet))
		{
			if(!rateSheetName.equals(get_text(openedRateSheet)))
			{
				searchRateSheet(rateSheetType, rateSheetName);
				deleteRateSheetValidation(rateSheetName);			
			}
			else
				deleteRateSheetValidation(rateSheetName);			
		}
		else
		{
			searchRateSheet(rateSheetType, rateSheetName);
			if(!oParameters.GetParameters("RateSheetPresent").equalsIgnoreCase("NO"))
				deleteRatesheet(rateSheetType, rateSheetName);
		}		
	}		
	
	
	public void rateSheetDelete()
	{
		for(int i=1;i<=4;i++)
		{
			deleteRatesheet("Production","RateSheet"+i+"2");
		}
		for(int i=5; i<=8;i++)
		{
			deleteRatesheet("Modeling","RateSheet"+i+"2");
		}
		
		deleteRatesheet("Production", "RateSheetCopiedFromExistingRateSheet");
	}
	
	public void addRateSheet()
	{
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "");
		
		for(int i=1;i<=8;i++)
		{
			addRateSheetIcon();
			AddRateSheetDetails(String.valueOf(i));
			rateSheetSaveButton(String.valueOf(i)); 
		}
	}
	

	// Rate Sheet-Search and CRUD Rate Sheet VR
	public void RateSheet_Search_and_CRUD_RateSheet_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();
		rateSheetSearch(modelingOption,"Test");
		rateSheetSearch(productionOption,"BLR_Automation(Do Not Delete)");
		clickCarotIcon();
		noResultsFoundMessage();
		addRateSheet();		
		rateSheetSearch(modelingOption,"RateSheet52");
		rateSheetSearch(productionOption,"RateSheet42");		
		addingRateSheetFromExistingRateSheet();				
		duplicateRatesheetCodePopup("RateSheet32"); 
		addRatesheetWindowCancelScenario();			
		editRateSheet(productionOption, "RateSheet32", "1");
		rateSheetHistoryTabLog();		
		rateSheetNote("Production and 3M DRG Grouper");
		editNote();			
		deleteRatesheet("Production", "RateSheet320");		
		
		// Adding Rate sheet
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","RateSheets_TestData.xlsx","RateSheet_VR", "RateSheet32");    
		addRateSheetIcon();
		AddRateSheetDetails("");
		rateSheetSaveButton("");		
				
		//adding Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");		
		AddSectionDetails("", "");
				
		rateSheetPeriodValidation();
		addingPeriodFromExistingRateSheet();
		rateSheetHistoryTabLog();		
		rateSheetSearch(modelingOption,"RateSheet52");
		duplicateRatesheetCodePopup("RateSheet62");
		addRatesheetWindowCancelScenario();
		editRateSheet(modelingOption, "RateSheet62", "0");
		rateSheetHistoryTabLog();		
		rateSheetNote("Modeling and Claim DRG");
		editNote();				
		deleteRatesheet("Modeling", "RateSheet620");	
		
		// Adding Rate sheet
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","RateSheets_TestData.xlsx","RateSheet_VR", "RateSheet62");    
		addRateSheetIcon();
		AddRateSheetDetails("");
		rateSheetSaveButton("");
		
		// adding section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");		
		AddSectionDetails("", "");
		
		rateSheetPeriodValidation();
		addingPeriodFromExistingRateSheet();
		rateSheetHistoryTabLog();
		searchRateSheet("Production", "BLR_View_Ratesheet_DND");
		clickHistoryTab();
		userFilterReports();
		dateFilterReports();					
		auditDetailFilterReports();
		downloadRateSheetHistory();
		clearFilters();
		recordsPerPage(1,"50");
		recordsPerPage(2,"100");
		recordsPerPage(3,"200");
		pageNavigations();
		downloadRateSheetHistory();
	//	printPreview();
		rateSheetDelete();		
		logout();
	}
	

	// Rate Sheet Dialysis PPS Rate, DRG User and IRF CMG Pricer Rate Type

	By dialysisPPSRateFactorsSearchBox = By.xpath("//input[@id='dialysisFactor']");

	By DRGUserRateSetSearchBox = By.xpath("//input[@id='drgUserRateMaster']");

	By CMGUserRateSetSearchBox = By.xpath("//input[@id='cmgUserRateMaster']");

	By CMGProviderValuesSetSearchBox = By.xpath("//input[@id='cmgProviderValuesMaster']");

	By PPSRateFactorFirstSearchResult = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8 form-group  form-group ']//ul[@id='-list']/li[1]/a");

	By DRGUserFirstSearchResult = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//ul[@id='-list']/li[1]/a");

	By CMGUserRateSetFirstSearchResult = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//div[@spec='rateTypeFormModel.cmgUserRateMaster.spec']//ul[@id='-list']/li[1]/a");

	By CMGProviderValuesSetFirstSearchResult = By.xpath("//div[@class='col-lg-8 col-md-8 col-sm-8']//div[@spec='rateTypeFormModel.cmgProviderValuesMaster.spec']//ul[@id='-list']/li[1]/a");

	By calculationMethodDD = By.xpath("//select[@id='drgUserCalculationMethodCode']");
	
	
	// this method will create Dialysis PPS Rate, DRG User and IRF CMG Price Rate type Terms
	public void dialysisPPSRateDRGUSerIRFCMGPricerTerms()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Creating Dialysis PPS Rate Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DialysisPPSRate_VR", "DialysisTerm");		
		addTermButton("", "DialysisPPSRate_DRGUser_IRF_CMGPricer_DND", "RateSheet_Section"); 		
		selectRateType("");
		termSaveButton("");	
		
		// Creating DRG User Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DRGUser_VR", "DRGUserTerm");		
		addTermButton("", "DialysisPPSRate_DRGUser_IRF_CMGPricer_DND", "RateSheet_Section"); 		
		selectRateType("");
		termSaveButton("");	
		
		//Creating IRF CMG Pricer Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_IRF_CMG_Pricer_VR", "IRFTerm");		
		addTermButton("", "DialysisPPSRate_DRGUser_IRF_CMGPricer_DND", "RateSheet_Section");		
		selectRateType("");
		termSaveButton("");		
	}

	
	// Rate Sheet- Dialysis PPS Rate, DRG User and IRF CMG Pricer Rate Type
	public void RateSheet_Dialysis_PPSRate_DRGUser_and_IRFCMGPricer_RateType_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();
		
		addRateSheet("DialysisPPSRateDRGIRFCMGRateType","DialysisPPSRate_DRGUser_IRF_CMGPricer_DND");
		
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");
		AddSectionDetails("", "");
		
		dialysisPPSRateDRGUSerIRFCMGPricerTerms();
		
		editTerm("IRF_CMG_pricer_rate_Term", "_Edited");
		
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section7");
		AddSectionDetails("", "");
		
		//Creating Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DosageQuantity_VR", "Term1");
		addTermButton("", "DialysisPPSRate_DRGUser_IRF_CMGPricer_DND", "Section1515138728");
		selectRateType("");
		termSaveButton("");       
		
		//Copying and moving terms to same ratesheet section and another ratesheet section
		movingSingleTerm("IRF_CMG_pricer_rate_Term_Edited", "Terms");
		movingSingleTermToOtherRS("IRF_CMG_pricer_rate_Term_Edited", "Terms");	
		deleteSingleTerm("IRF_CMG_pricer_rate_Term_Edited");
		searchRateSheet("Production", "DialysisPPSRate_DRGUser_IRF_CMGPricer_DND");		
		copingSingleTerm("Dialysis_PPSRate_Term", "Terms");		
		copingSingleTermToOtherRS("Dialysis_PPSRate_Term", "Terms");		
		deleteSingleTerm("Dialysis_PPSRate_Term");		
		searchRateSheet("Production", "DialysisPPSRate_DRGUser_IRF_CMGPricer_DND");		
		copyingMultipleTerm("Dialysis_PPSRate_Term", "DRG_User_Term", "Terms");		
		deleteTerm("DRG_User_Term", "Dialysis_PPSRate_Term");		
		editingandAddingPeriod();
		deleteSection("RateSheet_Section");
		filterReports("Terms");		
		deleteRatesheet("Production","DialysisPPSRate_DRGUser_IRF_CMGPricer_DND");		
		logout();
	}
	
	
	// Creating Revenue Code and Dosage Quantity terms
	public void revenueCodeDosageQuantityTerms()
	{	
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Creating Revenue Code per case Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_ByRevenueCode_VR", "PerCaseTerm");		
		addTermButton("", "RevenueCodeDosageQuantity_DND", "RateSheet_Section"); 		
		selectRateType("");
		termSaveButton("");
		
		// Creating Revenue Code per diem Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_ByRevenueCode_VR", "PerDiemTerm");		
		addTermButton("", "RevenueCodeDosageQuantity_DND", "RateSheet_Section"); 		
		selectRateType("");
		termSaveButton("");
		
		// Creating Revenue Code per service Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_ByRevenueCode_VR", "PerServiceTerm");		
		addTermButton("", "RevenueCodeDosageQuantity_DND", "RateSheet_Section"); 		
		selectRateType("");
		termSaveButton("");
		
		// Creating Revenue Code percentage Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_ByRevenueCode_VR", "PercentageTerm");		
		addTermButton("", "RevenueCodeDosageQuantity_DND", "RateSheet_Section"); 		
		selectRateType("");
		termSaveButton("");
		
		//Creating Dosage Quantity No Rounding Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DosageQuantity_VR", "Term1");
		addTermButton("", "RevenueCodeDosageQuantity_DND", "RateSheet_Section");
		selectRateType("");
		termSaveButton("");
		
		//Creating Dosage Quantity Round to nearest hundred Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DosageQuantity_VR", "Term2");
		addTermButton("", "RevenueCodeDosageQuantity_DND", "RateSheet_Section");
		selectRateType("");
		termSaveButton("");		
	}
			
	
	//Rate Sheet- By Revenue Code and Dosage Quantity Rate Type VR		
	public void RateSheet_By_RevenueCode_and_DosageQuantity_RateType_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();
		
		addRateSheet("RevCodeDosageQuantity_RateSheet","RevenueCodeDosageQuantity_DND");
				
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");
		AddSectionDetails("", "");
	       
		revenueCodeDosageQuantityTerms();
		
		editTerm("RevenueCode_PerCase_Term", "_Edited");
		
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section7");
		AddSectionDetails("", "");
				
		//Creating Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DosageQuantity_VR", "Term1");
		addTermButton("", "RevenueCodeDosageQuantity_DND", "Section1515138728");
		selectRateType("");
		termSaveButton("");		
		
		//Copying and moving terms to same ratesheet section and another ratesheet section
		movingSingleTerm("RevenueCode_PerCase_Term_Edited", "Terms");
		movingSingleTermToOtherRS("RevenueCode_PerCase_Term_Edited", "Terms");
		deleteSingleTerm("RevenueCode_PerCase_Term_Edited");
		searchRateSheet("Production", "RevenueCodeDosageQuantity_DND");		
		copingSingleTerm("RevenueCode_PerDiem_Term", "Terms");		
		copingSingleTermToOtherRS("RevenueCode_PerDiem_Term", "Terms");		
		deleteSingleTerm("RevenueCode_PerDiem_Term");		
		searchRateSheet("Production", "RevenueCodeDosageQuantity_DND");		
		copyingMultipleTerm("RevenueCode_PerDiem_Term", "RevenueCode_Percentage_Term", "Terms");		
		deleteTerm("RevenueCode_Percentage_Term", "RevenueCode_PerDiem_Term");		
		editingandAddingPeriod();
		filterReports("Terms");		
		deleteRatesheet("Production","RevenueCodeDosageQuantity_DND");		
		logout();
	}
	
	
	//Creating Revenue Code Per Day or Per Care and RUG User terms
	public void RevenueCode_PerDay_or_PerCare_and_RUGUser_Terms()
	{	
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//Creating Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_RevCodePerDayorPerCase_VR", "Term1");
		addTermButton("", "RevenueCodePerDayorPerCaseandRUGUser_DND", "RateSheet_Section");
		selectRateType("");
		termSaveButton("");	
		
		//Creating Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_RevCodePerDayorPerCase_VR", "Term2");
		addTermButton("", "RevenueCodePerDayorPerCaseandRUGUser_DND", "RateSheet_Section");
		selectRateType("");
		termSaveButton("");	
		
		//Creating Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_RUGUSer_VR", "Term1");
		addTermButton("", "RevenueCodePerDayorPerCaseandRUGUser_DND", "RateSheet_Section");
		selectRateType("");
		termSaveButton("");	
	}
		
	
	// Rate Sheet- Revenue Code Per Day or Per Care and RUG User Rate Type VR
	public void RateSheet_RevenueCode_PerDay_or_PerCase_and_RUGUser_RateType_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();
				
		addRateSheet("RevCDPerDayPerCase_RUGUser_RateType","RevenueCodePerDayorPerCaseandRUGUser_DND");
		
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");
		AddSectionDetails("", "");
		
		RevenueCode_PerDay_or_PerCare_and_RUGUser_Terms(); 
		
		editTerm("RUGUser_Term", "_Edited");
		
	    //Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section7");
		AddSectionDetails("", "");
				
		//Creating Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DosageQuantity_VR", "Term1");
		addTermButton("", "RevenueCodePerDayorPerCaseandRUGUser_DND", "Section1515138728");
		selectRateType("");
		termSaveButton("");		
		
		//Copying and moving terms to same ratesheet section and another ratesheet section
		movingSingleTerm("RUGUser_Term_Edited", "Terms");
		movingSingleTermToOtherRS("RUGUser_Term_Edited", "Terms");	
		deleteSingleTerm("RUGUser_Term_Edited");
		searchRateSheet("Production", "RevenueCodePerDayorPerCaseandRUGUser_DND");		
		copingSingleTerm("RevenueCodePerDayorPerCase_PerCase_Term", "Terms");		
		copingSingleTermToOtherRS("RevenueCodePerDayorPerCase_PerCase_Term", "Terms");		
		deleteSingleTerm("RevenueCodePerDayorPerCase_PerCase_Term");		
		searchRateSheet("Production", "RevenueCodePerDayorPerCaseandRUGUser_DND");		
		copyingMultipleTerm("RevenueCodePerDayorPerCase_PerCase_Term", "RevenueCodePerDayorPerCase_PerDay_Term", "Terms");		
		deleteTerm("RevenueCodePerDayorPerCase_PerDay_Term", "RevenueCodePerDayorPerCase_PerCase_Term");				
		editingandAddingPeriod();
		deleteSection("RateSheet_Section");
		filterReports("Terms");		
		deleteRatesheet("Production","RevenueCodePerDayorPerCaseandRUGUser_DND");		
		logout();    
	}
		
	
	//Creating Fee Schedule and Procedure Group Rate Type Terms
	public void feeScheduleAndProcedureGroupRateTypeTerms(String rateSheetName)
	{	    
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//Creating Fee Schedule Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_FeeSchedule_VR", "FeeScheduleTerm");
		addTermButton("", rateSheetName, "RateSheet_Section");
		selectRateType("");
		termSaveButton("");	
	    
		//Creating Fee Schedule Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_ProcedureGroup_VR", "ProcedureHCPCSTerm");
		addTermButton("", rateSheetName, "RateSheet_Section");
		selectRateType("");
		termSaveButton("");
		
		//Creating Fee Schedule Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_ProcedureGroup_VR", "ProcedureICDTerm");
		addTermButton("", rateSheetName, "RateSheet_Section");
		selectRateType("");
		termSaveButton("");	    
	}
		
	
	// Rate Sheet- Fee Schedule Rate Type and Procedure Group Rate Type Terms VR
	public void RateSheet_FeeSchedule_and_ProcedureGroup_RateType_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();	
		
		addRateSheet("FeeScheduleProcedureGroup_RateSheet","FeeSchedule_and_ProcedureGroup_DND");
		
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");
		AddSectionDetails("", "");
		
		feeScheduleAndProcedureGroupRateTypeTerms("FeeSchedule_and_ProcedureGroup_DND");
		
		editTerm("FeeScheuleRateType_Term", "_Edited");
		
	    //Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section7");
		AddSectionDetails("", "");
		
		//Creating Term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_DosageQuantity_VR", "Term1");
		addTermButton("", "FeeSchedule_and_ProcedureGroup_DND", "Section1515138728");
		selectRateType("");
		termSaveButton("");		
		
		//Copying and moving terms to same ratesheet section and another ratesheet section
		movingSingleTerm("FeeScheuleRateType_Term_Edited", "Terms");
		movingSingleTermToOtherRS("FeeScheuleRateType_Term_Edited", "Terms");
		deleteSingleTerm("FeeScheuleRateType_Term_Edited");
		searchRateSheet("Production", "FeeSchedule_and_ProcedureGroup_DND");		
		copingSingleTerm("ProcedureGroup_HCPCS/CPT_Claim_Term", "Terms");		
		copingSingleTermToOtherRS("ProcedureGroup_HCPCS/CPT_Claim_Term", "Terms");		
		deleteSingleTerm("ProcedureGroup_HCPCS/CPT_Claim_Term");
		searchRateSheet("Production", "FeeSchedule_and_ProcedureGroup_DND");
		copyingMultipleTerm("ProcedureGroup_HCPCS/CPT_Claim_Term", "ProcedureGroup_ICD_Claim_Term", "Terms");
		deleteTerm("ProcedureGroup_ICD_Claim_Term", "ProcedureGroup_HCPCS/CPT_Claim_Term");		
		editingandAddingPeriod();
		deleteSection("RateSheet_Section");
		filterReports("Terms");				
		deleteRatesheet("Production","FeeSchedule_and_ProcedureGroup_DND");		
		logout(); 			
	}
	
		
	// This method will click on Stop Loss tab
	public void clickStopLossTab()
	{
		navigate_to("Stop Loss Tab", "Selected Stop Loss Tab", stopLossLink, selectedStopLoss);
	}
	
	
	By stopLossLink = By.xpath("//a[text()='Stop Loss']");
	
	By selectedStopLoss = By.xpath("//li[@class='portal-tab-pane ng-scope active']/a[contains(text(),'Stop Loss')]");
	
	By addStopLossSectionLink = By.xpath("//div[@id='sPSlidePanelParent']//a[contains(text(),'Add a Stop Loss Section.')]");
	
	By addStopLossSectionButton = By.xpath("//input[@class='btn btn-xs btn-default pull-right'][@value='Add Section']");
	
	By addStopLossSetctionWindow = By.xpath("//div[@title='Add Stop Loss Section']");
	
	
	// this method will click on 'Add a Stop Loss Section' and validate add stop loss section window opened or not
	public void addStopLossSectionLink()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Add Stop Loss Section Link", addStopLossSectionLink))
			click_button("Add Stop Loss Section link", addStopLossSectionLink);
		else
			click_button("Add Stop Loss Section Button", addStopLossSectionButton);
		
		waitFor(addStopLossSetctionWindow, "Add Stop Loss Section Window");
	}
	
	
	By stopLossSectionNameField = By.xpath("//input[@id='slSectionName']");
	
	By maxRateTypeDD = By.xpath("//select[@id='maxRateType']");
	
	By minRateTypeDD = By.xpath("//select[@id='minRateType']");
	
	By minimumAmount = By.xpath("//input[@id='minRateAmount']");
	
	By stopLossSectionWindowSave = By.xpath("//workflow-modal[@id='stoplossSectionAdd']//input[@id='button.saveId']");
	
	By addedStopLossSections = By.xpath("//div[@id='sPSlidePanelParent']//li[@class=' list-header light toolbar data-row sections ']//span[@class='bold ng-binding']");
	
	
	// Adding stop loss Section
	public void addStopLossMinMaxRateTypeSectionDetails()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Stop Loss Section Name Field", stopLossSectionNameField, "Section"+System.currentTimeMillis());//oParameters.GetParameters("SectionName"+i));	
		oParameters.SetParameters("StopLossSectionName", get_field_value("Stop Loss Section Name", stopLossSectionNameField));
		selectOption(maxRateTypeDD, "visibletext", "Per Case");
		enter_text_value("Maximum Amount Field", maximumAmount, "22");
		selectOption(minRateTypeDD, "visibletext", "Per Length of Stay");
		enter_text_value("Minimum Amount Field", minimumAmount, "12");
		
	//	clickSaveButton("Stop Loss Section Window Save", "Added Stop Loss Section", stopLossSectionWindowSave, addedSLSection);		
		
		click_button("Stop Loss Section Window Save", stopLossSectionWindowSave);
		fixed_wait_time(3);
		
		By addedSLSection = By.xpath("//span[@class='bold ng-binding'][contains(.,'"+oParameters.GetParameters("StopLossSectionName")+"')]");
		
		waitFor(addedSLSection, "Added Stop Loss Section");
		
		if(IsDisplayed("Added Stop Loss Section", addedSLSection))
			oReport.AddStepResult("Stop Loss Section", "In 'Add Stop Loss Section' window filled mandatory fields then clicked on save, verified that new Stop Loss section is created", "PASS");
		else
			oReport.AddStepResult("Stop Loss Section", "In 'Add Stop Loss Section' window filled mandatory fields then clicked on save but that new Stop Loss section is not created", "FAIL");
	}
	
	
	By includeExclusionsCheckBox = By.xpath("//label[contains(.,'Include Exclusions in stop loss calculations.')]//input[@type='checkbox']");
	
	By useDRGUserRateDataCheckBox = By.xpath("//label[contains(.,'Use DRG user rate data in stop loss')]//input[@type='checkbox']");
	
	By DRGUserRateSetFirstSearchValue = By.xpath("//input[@id='drgUserRateMaster']/..//ul/li[1]");
	
	
	// Adding Stop Loss Section
	public void addStopLossSection()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Stop Loss Section Name Field", stopLossSectionNameField, "StopLossSection"+ System.currentTimeMillis());	
		oParameters.SetParameters("StopLossSectionName", get_field_value("Stop Loss Section Name", stopLossSectionNameField));
		click_button("Include Exclusions in stop loss calculations Checkbox", includeExclusionsCheckBox);
		click_button("Use DRG user rate data in stop loss calculations and formulas Checkbox", useDRGUserRateDataCheckBox);
		//enter_text_value("DRG user rate set Search box", DRGUserRateSetSearchBox, "test error");
		click_button("DRG user rate set Search box", DRGUserRateSetSearchBox);
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		click_button("DRG User rate set first search value", DRGUserRateSetFirstSearchValue);		
		waitFor(stopLossSectionWindowSave,"Stop Loss Section Window Save");
		click_button("Stop Loss Section Window Save", stopLossSectionWindowSave);
		fixed_wait_time(3);
		
		By addedSLSection = By.xpath("//span[@class='bold ng-binding'][contains(.,'"+oParameters.GetParameters("StopLossSectionName")+"')]");
		
		waitFor(addedSLSection, "Added Stop Loss Section");
		
		if(IsDisplayed("Added Stop Loss Section", addedSLSection))
			oReport.AddStepResult("Stop Loss Section", "In 'Add Stop Loss Section' window filled mandatory fields then clicked on save, verified that new Stop Loss section is created", "PASS");
		else
			oReport.AddStepResult("Stop Loss Section", "In 'Add Stop Loss Section' window filled mandatory fields then clicked on save but that new Stop Loss section is not created", "FAIL");		
	}
		
	
	By addStopLossTermWindow = By.xpath("//div[@title='Add Stop Loss Term']");
	
	
	public void addStopLossSectionDetails()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
	//	enter_text_value("Stop Loss Section Name Field", stopLossSectionNameField, oParameters.GetParameters("SectionName"+i));	
		//oParameters.SetParameters("StopLossSectionName", get_field_value("Stop Loss Section Name", stopLossSectionNameField));
		selectOption(maxRateTypeDD, "visibletext", "Per Case");
		enter_text_value("Maximum Amount Field", maximumAmount, "22");
		selectOption(minRateTypeDD, "visibletext", "Per Length of Stay");
		enter_text_value("Minimum Amount Field", minimumAmount, "12");
		
		enter_text_value("Stop Loss Section Name Field", stopLossSectionNameField, "StopLossSection"+ System.currentTimeMillis());	
		oParameters.SetParameters("StopLossSectionName", get_field_value("Stop Loss Section Name", stopLossSectionNameField));
		click_button("Include Exclusions in stop loss calculations Checkbox", includeExclusionsCheckBox);
		click_button("Use DRG user rate data in stop loss calculations and formulas Checkbox", useDRGUserRateDataCheckBox);
		enter_text_value("DRG user rate set Search box", DRGUserRateSetSearchBox, "test error");
		waitFor(stopLossSectionWindowSave,"Stop Loss Section Window Save");
		click_button("Stop Loss Section Window Save", stopLossSectionWindowSave);	
	}
	
	
	// Creating Stop Loss Additive Tiered,Formula,Per Case,Per Length of Stay,Percentage and Tiered terms
	public void stopLossTerms()
	{	
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "StopLoss_AdditiveTired_VR", "");
	
		String[] rateType = oParameters.GetParameters("SheetName1").split(",");
		int rateTypeLength = rateType.length;
	
		for(int i=1;i<=rateTypeLength;i++)
		{
			oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", rateType[i-1], "");
			int rowcount = oExcelData.getRowCount1(rateType[i-1], "C:\\CCM\\SupportingFiles\\RateSheets_TestData.xlsx");
			
			for(int j=1;j<=rowcount-1;j++)
			{
				addTermButton(String.valueOf(j),"StopLossRateSheet_DND","");
				selectRateType(String.valueOf(j));
				termSaveButton(String.valueOf(j));
			}
		}		
	}		
	
	
	// To add Stop loss Section
	public void createStopLossSection()
	{
		addStopLossSectionLink();
		
		enter_text_value("Stop Loss Section Name Field", stopLossSectionNameField, oParameters.GetParameters("SectionName"));
		click_button("Stop Loss Section Window Save", stopLossSectionWindowSave);
		
		fixed_wait_time(3);
		
		By addedSLSection = By.xpath("//span[@class='bold ng-binding'][contains(.,'"+oParameters.GetParameters("SectionName")+"')]");
		
		waitFor(addedSLSection, "Added Stop Loss Section");
		
		if(IsDisplayed("Added Stop Loss Section", addedSLSection))
			oReport.AddStepResult("Stop Loss Section", "In 'Add Stop Loss Section' window filled mandatory fields then clicked on save, verified that new Stop Loss section is created", "PASS");
		else
			oReport.AddStepResult("Stop Loss Section", "In 'Add Stop Loss Section' window filled mandatory fields then clicked on save but that new Stop Loss section is not created", "FAIL");
	}
	
	
	
	//Rate Sheet-CRUD Stop Loss VR
	public void RateSheet_CRUD_StopLoss_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();		
		addRateSheet("StopLossRateSheet","StopLossRateSheet_DND");		
		
		clickStopLossTab();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetStopLossSection", "StopLossSection");
		addSectionIcon();
		StopLossSection("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetStopLossSection", "SLSection");
		addSectionIcon();
		StopLossSection("");		
		
/*		addSectionIcon();
		addStopLossMinMaxRateTypeSectionDetails();
		addSectionIcon();
		addStopLossSection();*/
		
		stopLossTerms();		
		editTerm("Tiered_Day_Term", "_Edited");		
		
		//Copying and moving terms to same ratesheet section and another ratesheet section
		copingSingleTerm("Tiered_AgeInYears_Term","Stop Loss");
		copingSingleTermToOtherRS("Tiered_AgeInYears_Term","Stop Loss");
		deleteSingleTerm("Tiered_AgeInYears_Term");
		searchRateSheet("Production", "StopLossRateSheet_DND");
		clickStopLossTab();		
		copyingMultipleTerm("Tiered_AgeInYears_Term","Formula_term","Stop Loss");	
		filterReports("Stop Loss");		
		deleteRatesheet("Production", "StopLossRateSheet_DND");
		logout();   			
	}
		
	
	//adding Tiered Rate Type Terms
	public void addTieredTerms()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Term_Tiered_VR", "");
		int rowcount = oExcelData.getRowCount1("Term_Tiered_VR", "C:\\CCM\\SupportingFiles\\RateSheets_TestData.xlsx");		
		
		// Creating Tiered Rate Type Terms		
		for(int i=1;i<rowcount;i++)
		{
			addTermButton(String.valueOf(i),"RateSheet_Tiered_RateType","RateSheet_Section");
			selectRateType(String.valueOf(i));
			termSaveButton(String.valueOf(i));
		}
	}
	
	
	// Rate Sheet Tiered Rate Type VR
	public void RateSheet_Tiered_RateType_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();
			
		addRateSheet("TieredRateTypeRateSheet","RateSheet_Tiered_RateType");
		
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");
		AddSectionDetails("", "");
		
		addTieredTerms();
		
		editTerm("Tiered_Day_PerCase_Term", "_Edited");	
		
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section7");
		AddSectionDetails("", "");
		
		//Copying and moving terms to same ratesheet section and another ratesheet section
		movingSingleTerm("Tiered_AgeInYears_PerCase_Term", "Terms");
		movingSingleTermToOtherRS("Tiered_AgeInYears_PerCase_Term", "Terms");
		deleteSingleTerm("Tiered_AgeInYears_PerCase_Term");;
		searchRateSheet("Production", "RateSheet_Tiered_RateType");	
		copingSingleTerm("Tiered_Day_PerService_Term", "Terms");		
		copingSingleTermToOtherRS("Tiered_Day_PerService_Term", "Terms");		
		deleteSingleTerm("Tiered_Day_PerService_Term");		
		searchRateSheet("Production", "RateSheet_Tiered_RateType");		
		copyingMultipleTerm("Tiered_Day_PerService_Term", "Tiered_AgeInYears_PerService_Term", "Terms");		
		deleteTerm("Tiered_AgeInYears_PerService_Term", "Tiered_Day_PerService_Term");		
		editingandAddingPeriod();
		filterReports("Terms");
		deleteRatesheet("Production", "RateSheet_Tiered_RateType");
		logout();   
	}
	
	
	public void addRateSheet(String key,String rateSheetName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","RateSheets_TestData.xlsx","RateSheet_VR", key);    
		addRateSheetIcon();
		AddRateSheetDetails("");
		rateSheetSaveButton("");    
		
		if(oParameters.GetParameters("rateSheetStatus").equalsIgnoreCase("Existed"))
		{
			deleteRatesheet("Production", rateSheetName);
			
			oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","RateSheets_TestData.xlsx","RateSheet_VR", key);    
			addRateSheetIcon();
			AddRateSheetDetails("");
			rateSheetSaveButton(""); 
		}
		
		if(IsDisplayed("Hide search results button", carotDown))
			click_button("Hide search results button", carotDown);
		
		searchRateSheet("Production", rateSheetName);
	}
	
	
	public void exclusionTerms()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "Exclusions_AdditiveTired_VR", "");	
		
		String[] rateType = oParameters.GetParameters("SheetName1").split(",");
		int rateTypeLength = rateType.length;
		
		for(int i=1;i<=rateTypeLength;i++)
		{
			oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", rateType[i-1], "");
			int rowcount = oExcelData.getRowCount1(rateType[i-1], "C:\\CCM\\SupportingFiles\\RateSheets_TestData.xlsx");
						
			for(int j=1;j<=rowcount-1;j++)
			{
				addTermButton(String.valueOf(j),"ExclusionTerms_RateSheet","");
				selectRateType(String.valueOf(j));
				termSaveButton(String.valueOf(j));
			}
		}
	}
	
	
	By termsTab = By.xpath("//a[@class='ng-binding'][text()='Terms']");
	
	By selectedTermsTab = By.xpath("//li[@class='portal-tab-pane ng-scope active']/a[contains(text(),'Terms')]");
	
	By copyTosameRateSheetTermsTabRadio = By.xpath("//*[@id='actionToCurrentTerm']");
	
	By copyToAnotherRateSheetTermsTabRadio = By.xpath("//input[@id='actionToOtherTerm']");
	
	
	public void copyingSingleTermToSameRateSheetTermsTab(String termName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]");
		By copyTermExclusion = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Copy Term...']");
		
		waitFor(existedTerm, "");
		mouse_hover("Existing Term", existedTerm);
		fixed_wait_time(2);
		click_button("Toggle Down", termToggleDown);
		click_button("Copy Term..", copyTermExclusion);
		click_button("Copy to current rate sheet's terms tab radio button", copyTosameRateSheetTermsTabRadio);
		select_option("Select Section DD", sectionDD, "0");
		click_button("Copy Term Window Save", moveTermWindowSaveSL);
		fixed_wait_time(3);		
		waitFor(termsTab, "Terms Tab");
		click_button("Terms Tab", termsTab);		

		if(IsDisplayed("Copied Term", existedTerm))
			oReport.AddStepResult("copying to Terms tab", ""+termName+" is copied from exclusions tab to same Rate Sheet terms tab, verified that term copied to terms tab Successfully","PASS");
		else
			oReport.AddStepResult("copying to Terms tab", "Tried to copy "+termName+" from exclusions tab to same Rate Sheet terms tab but that term is not copied to terms tab","FAIL");			
	}
	
	
	By moveTermRateSheetPeriodDDExclusion = By.xpath("//div[@class='form-group col-lg-12 col-md-12 col-sm-12 col-mdlg-12 mar-r-0']//select[@id='copyOtherPrd']");
	
	
	public void copyingSingleTermtoAnotherRateSheetTermsTab(String termName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]");
		By copyTermExclusion = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Copy Term...']");		
		By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'AutomationRateSheet_DND')]");
		
		waitFor(existedTerm, "");
		mouse_hover("Existed Term", existedTerm);
		click_button("Toggle Down", termToggleDown);
		click_button("Copy Term..", copyTermExclusion);
		click_button("To Another RateSheet Radio", toAnotherRateSheetRadioExclusion);
		enter_text_value("Rate Sheet Search", moveTermWinowRateSheetSearchBox, "AutomationRateSheet_DND");
		fixed_wait_time(3);
		click_button("First Rate Sheet", moveTermRateSheetFirstSearchResult);
		select_option("Rate Sheet Period DD", moveTermRateSheetPeriodDDExclusion, "0");
		select_option("Rate Sheet Section DD ", moveTermRateSheetSectionDD, "0");		
		click_button("Copy Term Window Save", moveTermWindowSaveSL);
		fixed_wait_time(3);
		waitFor(rateSheetSearch, "Rate Sheet Search Box");
		enter_text_value("Rate Sheet Search Box", rateSheetSearch, "AutomationRateSheet_DND");// oParameters.GetParameters("ExistedRateSheet");
		performKeyBoardAction("ENTER");
		waitFor(existedRateSheet, "");
		mouse_hover("Rate Sheet", existedRateSheet);
		click_button("Existed Rate", existedRateSheet);		
		
		waitFor(existedTerm, "");
		mouse_hover("Moved Term", existedTerm);

		if(IsDisplayed("Copied Term", existedTerm))
			oReport.AddStepResult("Copied Term",""+termName+" is copied from exclusions tab to another Rate Sheet terms tab, verified that term copied to terms tab Successfully", "PASS");
		else
			oReport.AddStepResult("Copied Term","Tried to copy "+termName+" from exclusions tab to another Rate Sheet terms tab but that term is not copied to terms tab","FAIL");
	}
	
	
	//This method will click on Exclusions Tab
	public void clickExclusionsTab()
	{
		navigate_to("Exclusions Tab", "Selected Exclusions Tab", ExclusionTab, selectedExclusionTab);
	}
	
	
	// Rate Sheet-CRUD Exclusion VR
	public void rateSheet_CRUD_Exclusion_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();		
		addRateSheet("ExclusionsRateSheet","ExclusionTerms_RateSheet");		
		exclusionTerms();		
		deleteSingleTerm("Percentage_CoveredChargeAmount_ExclusionTerm");
		editTerm("TotalCovered_ExclusionTerm_PerCase", "_Edited");	
		
		copingSingleTermToOtherRS("DailyCovered_ExclusionTerm_PerDiem","Exclusions");
		
		deleteSingleTerm("DailyCovered_ExclusionTerm_PerDiem");
		
		searchRateSheet("Production", "ExclusionTerms_RateSheet");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");
		AddSectionDetails("", "");
		
		clickExclusionsTab();		
		copyingSingleTermToSameRateSheetTermsTab("DailyCovered_ExclusionTerm_PerDiem");
		deleteSingleTerm("DailyCovered_ExclusionTerm_PerDiem");
		clickExclusionsTab();		
		copyingSingleTermtoAnotherRateSheetTermsTab("DailyCovered_ExclusionTerm_PerDiem");
		
		deleteSingleTerm("DailyCovered_ExclusionTerm_PerDiem");
		
		searchRateSheet("Production", "ExclusionTerms_RateSheet");
		clickExclusionsTab();
		filterReports("Exclusions");
		deleteRatesheet("Production", "ExclusionTerms_RateSheet");
		logout();		
	}
	
	
	public void addBasicRateTypesRateSheet()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","TestData-BasicRateTpes.xlsx","RateSheet", "BasicRateTypesRateSheet");    
		addRateSheetIcon();
		AddRateSheetDetails("");
		rateSheetSaveButton("");    
		
		if(oParameters.GetParameters("rateSheetStatus").equalsIgnoreCase("Existed"))
		{
			deleteRatesheet("Production", "Basic_FlatRates_Ratesheet_DND");
			
			oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","TestData-BasicRateTpes.xlsx","RateSheet", "BasicRateTypesRateSheet");    
			addRateSheetIcon();
			AddRateSheetDetails("");
			rateSheetSaveButton(""); 
		}
		
		if(IsDisplayed("Hide search results button", carotDown))
			click_button("Hide search results button", carotDown);
		
		searchRateSheet("Production", "Basic_FlatRates_Ratesheet_DND");
	}
			
	
	public void basicRateTypeTerms()
	{	
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData-BasicRateTpes.xlsx", "Term_APCAPG_Pricer", "");
	
		String[] rateType = oParameters.GetParameters("SheetName1").split(",");
		int rateTypeLength = rateType.length;
	
		for(int i=1;i<=rateTypeLength;i++)
		{
			oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TestData-BasicRateTpes.xlsx", rateType[i-1], "");
			int rowcount = oExcelData.getRowCount1(rateType[i-1], "C:\\CCM\\SupportingFiles\\TestData-BasicRateTpes.xlsx");
			
			for(int j=1;j<=rowcount-1;j++)
			{
				addTermButton(String.valueOf(j),"Basic_FlatRates_Ratesheet_DND","");
				selectRateType(String.valueOf(j));
				termSaveButton(String.valueOf(j));
			}
		}		
	}
	
	
	//Creating In patient and Out patient Sections
	public void addingInAndOutpatientSections()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
    	//creating new section in the rate sheet 
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","TestData-BasicRateTpes.xlsx", "RateSheetSection", "");
    	int rowCount1 = oExcelData.getRowCount1("RateSheetSection", "C:\\CCM\\SupportingFiles\\TestData-BasicRateTpes.xlsx");
    	
    	for(int i=1;i<=rowCount1-1;i++)
    	{
    		AddSectionDetails(String.valueOf(i),"");
    	}
	}
	

    //Rate Sheet-Flat Rate Types
    public void RateSheet_Basic_Rate_Types_VR() //throws InterruptedException
    {         
    	login("EDIT");
    	changePricingEngine();
    	navigateToRateSheets();    	
    	addBasicRateTypesRateSheet();    
    	addingInAndOutpatientSections();    	
    	basicRateTypeTerms();

    	//creating Per Minute Term in Out patient Section 
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles","TestData-BasicRateTpes.xlsx", "Term_PerMinute", "Term1");
    	addTermButton("", "Basic_FlatRates_Ratesheet_DND", "Outpatient Section");
    	selectRateType("");
    	termSaveButton("");
          
    	//Moving term from one section to another section
    	movingSingleTerm("APC/APG_Pricer","Terms");
    	copingSingleTerm("DRG_Pricer","Terms");
    	movingMultipleTerm("DRG_Pricer","Per_Diem","Terms");
    	copyingMultipleTerm("Per_Case","Per_Hour","Terms");
          
    	// Moving term from one Rate Sheet to another Rate Sheet
    	movingSingleTermToOtherRS("Per_Hour","Terms");
    	deleteSingleTerm("Per_Hour");    	
    	searchRateSheet("Production", "Basic_FlatRates_Ratesheet_DND");    	
    	copingSingleTermToOtherRS("PPS_Professional_Rate","Terms");
    	deleteSingleTerm("PPS_Professional_Rate");    	
    	searchRateSheet("Production", "Basic_FlatRates_Ratesheet_DND");       	
    	movingMultipleTermToOtherRS("Per_Length_of_Stay","PPS_Professional_Rate","Terms");   
    	deleteTerm("Per_Length_of_Stay","PPS_Professional_Rate");
    	searchRateSheet("Production", "Basic_FlatRates_Ratesheet_DND");	    	
    	copingMultipleTermToOtherRS("PerServiceRate","PercentageRate","Terms");    	
    	deleteTerm("PerServiceRate","PercentageRate");
    	searchRateSheet("Production", "Basic_FlatRates_Ratesheet_DND");	
          
    	// CRUD Operation on Effective Period dates
    	editingandAddingPeriod();
          
    	//Method for Filters
    	filterReports("Terms");
                 
    	//Deleting Rate Sheet
    	deleteRatesheet("Production","Basic_FlatRates_Ratesheet_DND");//Automation_DND_BasicFlatRates
          
    	//Method for Logout
    	logout();
    }
	
	
	//----------------------------------------------
    
    
    By moveTermWindow = By.xpath("//div[@title='Move Term']");
    
    By moveBefore = By.xpath("//select[@id='placement']");
    
    By termPriority = By.xpath("//select[@id='currentTrm']");
    
    By termPriorityDisabled = By.xpath("//select[@id='currentTrm']//option[@value='0']");
	

    // To move term from one section to another section
	public void movingSingleTerm(String termName, String tabName)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]");		
		By moveTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Move Term...']");
		
		if(tabName.equals("Stop Loss"))
		{
			secondSectionTermCount = secondSectionTermCountSL;
			moveTermWindowSave = moveTermWindowSaveSL;
		}		
		
		waitFor(existedTerm, "");
		oParameters.SetParameters("SecondSectionTermCountBeforeMoving",get_field_value("term Count", secondSectionTermCount).replace(" Terms", ""));
		mouse_hover("Existed Term", existedTerm);
		click_button("Toggle Down", termToggleDown);
		click_button("Move Term..", moveTerm);
		select_option("Select Section DD", sectionDD, "1");
		select_option("Add Before", moveBefore, "B");
		
		if(IsDisplayed("Disabled Term Priority", termPriorityDisabled))
			select_option("Term Priority", termPriority, "0");

		
		clickSaveButton("Save Button", "Move Term", moveTermWindowSave, moveTermWindow);		
	//	click_button("Move Term Window Save", moveTermWindowSave);
		
		fixed_wait_time(3);
		waitFor(secondSectionTermCount, "");
		click_button("Rate Sheet title bar", openedRateSheet);
		oParameters.SetParameters("SecondSectionTermCountAfterMoving",get_field_value("term Count", secondSectionTermCount).replace(" Terms", ""));

		if(oParameters.GetParameters("SecondSectionTermCountBeforeMoving").equals(oParameters.GetParameters("SecondSectionTermCountAfterMoving")))
		{
			waitFor(secondSectionTermCount, "");
			oReport.AddStepResult("Term Moving","Hovered over Term, selected move term option from drop down, but term is not moved to respective section ", "FAIL");
		}
		else
		{
			waitFor(secondSectionTermCount, "");
			oReport.AddStepResult("Term Moving", "Term is moved to respective section ", "PASS");
		}	
	}

	
    // To move term from one Rate Sheet to another Rate Sheet
	public void movingSingleTermToOtherRS(String termName, String tabName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]");		
		By moveTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Move Term...']");		
		By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'AutomationRateSheet_DND')]");
		
		if(tabName.equals("Stop Loss"))
		{
			moveTermWindowSave = moveTermWindowSaveSL;
		}		

		waitFor(existedTerm, "");
		mouse_hover("Existed Term", existedTerm);
		click_button("Toggle Down", termToggleDown);
		click_button("Move Term..", moveTerm);
		click_button("To Another RateSheet Radio", toAnotherRateSheetRadio);
		enter_text_value("Rate Sheet Search", moveTermWinowRateSheetSearchBox, "AutomationRateSheet_DND");
		
		if(IsDisplayed("RateSheet Search Result", moveTermRateSheetFirstSearchResult))
			click_button("First Rate Sheet", moveTermRateSheetFirstSearchResult);
		
		select_option("Rate Sheet Period DD", moveTermRateSheetPeriodDD, "0");
		select_option("Rate Sheet Section DD ", moveTermRateSheetSectionDD, "0");
		click_button("Move Term Window Save", moveTermWindowSave);
		fixed_wait_time(3);
		waitFor(rateSheetSearch, "RateSheet Search box");
		enter_text_value("Rate Sheet Search Box", rateSheetSearch, "AutomationRateSheet_DND");// oParameters.GetParameters("ExistedRateSheet");
		performKeyBoardAction("ENTER");
		waitFor(existedRateSheet, "");
		mouse_hover("Rate Sheet", existedRateSheet);
		fixed_wait_time(3);
		click_button("Existed Rate", existedRateSheet);
		
		if(tabName.equals("Stop Loss"))
			click_button("Stop Loss tab", stopLossLink);
		
		waitFor(existedTerm, "");
		mouse_hover("Moved Term", existedTerm);

		if(IsDisplayed("Moved Term", existedTerm))
			oReport.AddStepResult("Moved Term", "Term is moved form one Rate Sheet to another Rate Sheet Successfully","PASS");
		else
			oReport.AddStepResult("Moved Term",	"Tried to move term  form one Rate Sheet to another Rate Sheet but that respective term is not added to selected Rate Sheet","FAIL");		
	}
	
	
	// To copy term from one section to another section
	public void copingSingleTerm(String termName, String tabName)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]");
		By copyTerm = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[2]//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Copy Term...']");
		
		By copyTermSL = By.xpath("//ul[@class='data-list']/li[2]//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Copy Term...']");
		
		if(tabName.equals("Stop Loss"))
		{
			copyTerm = copyTermSL;
			moveTermWindowSave = moveTermWindowSaveSL;
			secondSectionTermCount = secondSectionTermCountSL;
		}
		
		waitFor(existedTerm, "");
		oParameters.SetParameters("SecondSectionTermCountBeforeCopying",get_field_value("term Count", secondSectionTermCount).replace(" Terms", ""));
		mouse_hover("Existed Term", existedTerm);
		fixed_wait_time(2);
		click_button("Toggle Down", termToggleDown);
		click_button("Copy Term..", copyTerm);
		select_option("Select Section DD", sectionDD, "1");
		select_option("Add Before", moveBefore, "B");
		
		if(IsDisplayed("Disabled Term Priority", termPriorityDisabled))
			select_option("Term Priority", termPriority, "0");

		click_button("Copy Term Window Save", moveTermWindowSave);
		fixed_wait_time(3);
		waitFor(secondSectionTermCount, "");
		mouse_hover("Second Section Term Count", secondSectionTermCount);
		oParameters.SetParameters("SecondSectionTermCountAfterCopied",get_field_value("term Count", secondSectionTermCount).replace(" Terms", ""));

		if(oParameters.GetParameters("SecondSectionTermCountBeforeCopying").equals(oParameters.GetParameters("SecondSectionTermCountAfterCopied")))
		{
			waitFor(secondSectionTermCount, "");
			oReport.AddStepResult("Term Copying","Hovered over Term, selected copy term option from drop down, but term is not copied to respective section ","FAIL");
		}
		else
		{
			waitFor(secondSectionTermCount, "");
			oReport.AddStepResult("Term Copying", "Term is copied to respective section ", "PASS");
		}
	}
	
	
	// Coping term from one Rate Sheet to another Rate Sheet
	public void copingSingleTermToOtherRS(String termName, String tabName)
	{	
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]");
		By copyTerm = By.xpath("//ul[@class='data-list'][@style='overflow: visible;']/li[2]//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Copy Term...']");
		
		By copyTermSL = By.xpath("//ul[@class='data-list']/li[2]//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Copy Term...']");
		By copyTermExclusion = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]/../..//a[text() = 'Copy Term...']");
		
		By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'AutomationRateSheet_DND')]");
				
		if(tabName.equals("Stop Loss"))
		{
			copyTerm = copyTermSL;
			moveTermWindowSave = moveTermWindowSaveSL;
			secondSectionTermCount = secondSectionTermCountSL;
		}
		else if(tabName.equals("Exclusions"))
		{	
			moveTermWindowSave = moveTermWindowSaveSL;
		//	toAnotherRateSheetRadio = toAnotherRateSheetRadioExclusion;
			copyTerm = copyTermExclusion;
		}	
		
		waitFor(existedTerm, "");
		mouse_hover("Existed Term", existedTerm);
		click_button("Toggle Down", termToggleDown);
		click_button("Copy Term..", copyTerm);
		click_button("To Another RateSheet Radio", toAnotherRateSheetRadio);
		enter_text_value("Rate Sheet Search", moveTermWinowRateSheetSearchBox, "AutomationRateSheet_DND");
		fixed_wait_time(3);
		
		if(IsDisplayed("RateSheet Search Result", moveTermRateSheetFirstSearchResult))
			click_button("First Rate Sheet", moveTermRateSheetFirstSearchResult);
		
		select_option("Rate Sheet Period DD", moveTermRateSheetPeriodDD, "0");
		
		if(!(tabName.equals("Exclusions")))
			select_option("Rate Sheet Section DD ", moveTermRateSheetSectionDD, "0");
		
		click_button("Copy Term Window Save", moveTermWindowSave);
		fixed_wait_time(3);
		waitFor(rateSheetSearch, "Rate Sheet Search Box");
		enter_text_value("Rate Sheet Search Box", rateSheetSearch, "AutomationRateSheet_DND");// oParameters.GetParameters("ExistedRateSheet");
		performKeyBoardAction("ENTER");
		waitFor(existedRateSheet, "");
		mouse_hover("Rate Sheet", existedRateSheet);
		click_button("Existed Rate", existedRateSheet);
		
		if(tabName.equals("Stop Loss"))
			click_button("Stop Loss tab", stopLossLink);
		else if(tabName.equals("Exclusions"))
			click_button("Exclusions Tab", ExclusionTab);		
		
		waitFor(existedTerm, "Copied term");

		if(IsDisplayed("Copied Term", existedTerm))
			oReport.AddStepResult("Copied Term","Term is Copied form one Rate Sheet to another Rate Sheet Successfully", "PASS");
		else
			oReport.AddStepResult("Copied Term","Tried to Copy term  form one Rate Sheet to another Rate Sheet but that respective term is not added to selected Rate Sheet","FAIL");
	}

	
	// To move multiple terms from one section to another section
	public void movingMultipleTerm(String termName1, String termName2, String tabName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]");
		By existedTerm1 = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]");
		By existedSecondSectionFirstTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]/../..//input[@type='checkbox']");
		By existedSecondSectionSecondTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]/../..//*[@id='GroupDetails']");
		oParameters.SetParameters("SecondSectionTermCountBeforeMovingMultipleTerms",get_field_value("term Count", secondSectionTermCount).replace(" Terms", ""));
		
		if(tabName.equals("Stop Loss"))
		{
			moveTermWindowSave = moveTermWindowSaveSL;
			secondSectionTermCount = secondSectionTermCountSL;
			multipleTermsDDMoveTerms = multipleTermsDDMoveTermsSL;
		}
		
		mouse_hover("Existed Term", existedTerm);
		click_button("Select First term Check box", existedSecondSectionFirstTerm);

		mouse_hover("Existed Term", existedTerm1);
		click_button("Select Second term Check box", existedSecondSectionSecondTerm);
		
		click_button("Multiple Terms Action DD", multipleTermsActionDD);
		click_button("Multiple Terms Dropdown Move Terms", multipleTermsDDMoveTerms);
		select_option("Select Section DD", sectionDD, "1");
		select_option("Add Before", moveBefore, "B");
		select_option("Term Priority", termPriority, "0");
		
		click_button("Move Term Window Save", moveMultipleTermsWindowSave);
		fixed_wait_time(3);
		waitFor(secondSectionTermCount, "");
		mouse_hover("Second Section Terms", secondSectionTermCount);
		oParameters.SetParameters("SecondSectionTermCountAfterMovingMultipleTerms",get_field_value("term Count", secondSectionTermCount).replace(" Terms", ""));

		if(oParameters.GetParameters("SecondSectionTermCountBeforeMovingMultipleTerms").equals(oParameters.GetParameters("SecondSectionTermCountAfterMovingMultipleTerms")))
		{
			waitFor(secondSectionTermCount, "");
			oReport.AddStepResult("Term Moving","Hovered over Terms, selected multiple Terms and then clicked on move term option, but term is not moved to respective section ","FAIL");
		}
		else
		{
			waitFor(secondSectionTermCount, "");
			oReport.AddStepResult("Term Moving", "Multiple Terms are moved to respective section ","PASS");
		}
	}
	
	
	// To move multiple terms from one Rate Sheet to another Rate Sheet
	public void movingMultipleTermToOtherRS(String termName1, String termName2, String tabName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'AutomationRateSheet_DND')]");
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]");
		By existedTerm1 = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]");
		By existedSecondSectionFirstTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]/../..//input[@type='checkbox']");
		By existedSecondSectionSecondTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]/../..//*[@id='GroupDetails']");
	
		if(tabName.equals("Stop Loss"))
		{	
			multipleTermsDDMoveTerms = multipleTermsDDMoveTermsSL;
			secondSectionTermCount = secondSectionTermCountSL;
		}
		
		waitFor(existedTerm, "");
		mouse_hover("Existed Term", existedTerm);
		click_button("Select First term Check box", existedSecondSectionFirstTerm);

		mouse_hover("Existed Term", existedTerm1);
		click_button("Select Second term Check box", existedSecondSectionSecondTerm);

		click_button("Multiple Terms Action DD", multipleTermsActionDD);
		click_button("Multiple Terms Dropdown Move Terms", multipleTermsDDMoveTerms);

		select_option("Select Section DD", sectionDD, "1");
		
		click_button("To Another RateSheet Radio", toAnotherRateSheetRadio);
		enter_text_value("Rate Sheet Search", moveTermWinowRateSheetSearchBox, "AutomationRateSheet_DND");
		
		if(IsDisplayed("RateSheet Search Result", moveTermRateSheetFirstSearchResult))
			click_button("First Rate Sheet", moveTermRateSheetFirstSearchResult);
		
		select_option("Rate Sheet Period DD", moveTermRateSheetPeriodDD, "0");
		select_option("Rate Sheet Section DD ", moveTermRateSheetSectionDD, "0");
		click_button("Move Term Window Save", moveMultipleTermsWindowSave);

		fixed_wait_time(3);
		waitFor(secondSectionTermCount, "");
		mouse_hover("Second Section Terms", secondSectionTermCount);

		waitFor(rateSheetSearch, "Rate Sheet Search Box");
		enter_text_value("Rate Sheet Search Box", rateSheetSearch, "AutomationRateSheet_DND");// oParameters.GetParameters("ExistedRateSheet");
		performKeyBoardAction("ENTER");
		waitFor(existedRateSheet, "");
		mouse_hover("Rate Sheet", existedRateSheet);
		click_button("Existed Rate", existedRateSheet);
		
		if(tabName.equals("Stop Loss"))
			click_button("Stop Loss tab", stopLossLink);
		
		if(IsDisplayed("Moved Term", existedTerm)&&IsDisplayed("Moved Term", existedTerm1))
			oReport.AddStepResult("Term Moving", "Multiple Terms are moved to respective section ","PASS");			
		else
			oReport.AddStepResult("Term Moving","Hovered over Terms, selected multiple Terms and then clicked on move term option, but terms are not moved to respective section ","FAIL");
	}
	
	
	// To copy multiple terms from one Section to another Section
	public void copyingMultipleTerm(String termName1, String termName2, String tabName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]");
		By existedTerm1 = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]");
		By existedSecondSectionFirstTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]/../..//input[@type='checkbox']");
		By existedSecondSectionSecondTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]/../..//*[@id='GroupDetails']");
		By multipleTermsDDCopyTerms = By.xpath("//*[@id='multipleTermsActionBar']//a[@ng-click='copyMultipleTerm()']");
		
		By multipleTermsDDCopyTermsSL = By.xpath("//*[@id='multipleTermsActionBar']//a[@ng-click='copyMultipleStoplossTerm()']");
		
		if(tabName.equals("Stop Loss"))
		{
			multipleTermsDDCopyTerms = multipleTermsDDCopyTermsSL;
			firstSectionTermCount = firstSectionTermCountSL;
		}
		
		waitFor(secondSectionTermCount, "Section Term count");
		oParameters.SetParameters("SecondSectionTermCountBeforeCopying",get_field_value("term Count", secondSectionTermCount).replace(" Terms", ""));
		mouse_hover("Existed Term", existedTerm);
		click_button("Select First term Check box", existedSecondSectionFirstTerm);

		mouse_hover("Existed Term", existedTerm1);
		click_button("Select Second term Check box", existedSecondSectionSecondTerm);
		
		click_button("Multiple Terms Action DD", multipleTermsActionDD);
		click_button("Multiple Terms copy Terms", multipleTermsDDCopyTerms);		
		
		select_option("Select Section DD", sectionDD, "1");
		select_option("Add Before", moveBefore, "B");
		select_option("Term Priority", termPriority, "0");
		
		click_button("copy Term Window Save", moveMultipleTermsWindowSave);
		fixed_wait_time(3);
		waitFor(firstSectionTermCount, "");
		mouse_hover("First Section Term Count", firstSectionTermCount);
		click_button("Rate Sheet title bar", openedRateSheet);
		oParameters.SetParameters("SecondSectionTermCountAfterCopied",get_field_value("term Count", secondSectionTermCount).replace(" Terms", ""));

		if(oParameters.GetParameters("SecondSectionTermCountBeforeCopying").equals(oParameters.GetParameters("SecondSectionTermCountAfterCopied")))
		{
			waitFor(secondSectionTermCount, "");
			oReport.AddStepResult("Term Copying","Hovered over Terms, selected multiple Terms and then clicked on copy term from 'Take action on multiple terms' drop down, changed the section, placement and term then Clicked on save button but term is not copied to respective section ","FAIL");
		}
		else
		{
			waitFor(secondSectionTermCount, "");
			oReport.AddStepResult("Term Copying","Multiple Terms are copied to respective section at selected position ", "PASS");
		}
	}
	
	
	// To copy multiple terms from one Rate Sheet to another Rate Sheet
    public void copingMultipleTermToOtherRS(String termName1, String termName2, String tabName)
    {   
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
    	By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]");
    	By existedTerm1 = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]");
    	By existedRateSheet = By.xpath("//div[@class='col-lg-5 col-md-5 col-sm-5 col-xs-6 hide-overflow']/div[contains(.,'AutomationRateSheet_DND')]");
    	By existedSecondSectionFirstTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]/../..//input[@type='checkbox']");
    	By existedSecondSectionSecondTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]/../..//*[@id='GroupDetails']");
    	By multipleTermsDDCopyTerms = By.xpath("//*[@id='multipleTermsActionBar']//a[@ng-click='copyMultipleTerm()']");
    	
		if(tabName.equals("Stop Loss"))
		{
			multipleTermsDDCopyTerms = multipleTermsDDCopyTermsSL;
			firstSectionTermCount = firstSectionTermCountSL;
		}    	
    	
    	mouse_hover("Existed Term", existedTerm);    
    	click_button("Select First term Check box", existedSecondSectionFirstTerm);

    	mouse_hover("Existed Term", existedTerm1);
    	click_button("Select Second term Check box", existedSecondSectionSecondTerm);
          
    	click_button("Multiple Terms Action DD", multipleTermsActionDD);
    	click_button("Multiple Terms Dropdown Move Terms", multipleTermsDDCopyTerms);       
    	click_button("To Another RateSheet Radio", toAnotherRateSheetRadio);
    	enter_text_value("Rate Sheet Search", moveTermWinowRateSheetSearchBox, "AUTOMATIONRATESHEET_DND");
    	fixed_wait_time(3);
    	
    	if(IsDisplayed("RateSheet Search Result", moveTermRateSheetFirstSearchResult))
    		click_button("First Rate Sheet", moveTermRateSheetFirstSearchResult);
    	
    	select_option("Rate Sheet Period DD", moveTermRateSheetPeriodDD, "0");
    	select_option("Rate Sheet Section DD ", moveTermRateSheetSectionDD, "0");
    	click_button("Move Term Window Save", moveMultipleTermsWindowSave);
    	fixed_wait_time(3);
    	waitFor(rateSheetSearch, "Rate Sheet Search Box");
    	enter_text_value("Rate Sheet Search Box", rateSheetSearch, "AUTOMATIONRATESHEET_DND");// oParameters.GetParameters("ExistedRateSheet");
    	performKeyBoardAction("ENTER");
    	waitFor(existedRateSheet, "");
    	mouse_hover("Rate Sheet", existedRateSheet);
    	click_button("Existed Rate", existedRateSheet);

    	if(IsDisplayed("Moved Term", existedTerm)&&IsDisplayed("Moved Term", existedTerm1))
    		oReport.AddStepResult("Term Copying","Multiple Terms are copied to respective section at selected position ", "PASS");
    	else
    		oReport.AddStepResult("Term Copying","Hovered over Terms, selected multiple Terms and then clicked on copy term from Take action on multiple terms drop down, changed the section, placement and term then Clicked on save button but term is not copied to respective section ","FAIL");
    }
    
     
    // To Delete multiple Terms
    public void deleteTerm(String termName1, String termName2)
    {         
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
    	By selectingFirstTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]");
    //	By selectingSecondTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]");
          
    	if(!termName1.isEmpty() && termName2.isEmpty()) 
    	{       
    		oParameters.SetParameters("SectionTermCountBeforeDelete",get_field_value("term Count", sectionTermCount).replace(" Terms", ""));
    		mouse_hover("First Terms", selectingFirstTerm);
    		click_button("Delete Term", termDeleteIcon);
    		fixed_wait_time(2);
    		click_button("Delete Term", SectionDelete);
    		waitFor(addTermButton, "");
    		waitFor(sectionTermCount, "");
    		mouse_hover("Section Terms", sectionTermCount);
    		oParameters.SetParameters("SectionTermCountAfterDelete",get_field_value("term Count", sectionTermCount).replace(" Terms", ""));

    		if(oParameters.GetParameters("SectionTermCountBeforeDelete").equals(oParameters.GetParameters("SectionTermCountAfterDelete")))
    			oReport.AddStepResult("Term Deleting","Hovered over existed Term,clicked on Delete Term but term is not Deleted from respective section ","FAIL");
    		else
    			oReport.AddStepResult("Term Deleting", "Term is Deleted form respective section ", "PASS");                 
    	}
    	else 
    	{
    		By existedSecondSectionFirstTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]/../..//input[@type='checkbox']");
    		By existedSecondSectionSecondTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]/../..//*[@id='GroupDetails']");
    		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName1+"')]");
    		By existedTerm1 = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName2+"')]");
    		
    		oParameters.SetParameters("FirstSectionTermCountBeforeDelete",get_field_value("term Count", firstSectionTermCount).replace(" Terms", ""));
    		mouse_hover("Existed Term", existedTerm);
    		click_button("Select First term Check box", existedSecondSectionFirstTerm);
    		mouse_hover("Existed Term", existedTerm1);
    		click_button("Select Second term Check box", existedSecondSectionSecondTerm);
    		waitFor(multipleTermsActionDD, "");
    		click_button("Multiple Terms Action DD", multipleTermsActionDD);
    		click_button("Multiple Terms Dropdown Delete Terms", multipleTermsDDDeleteTerms);
    		fixed_wait_time(2);
    		click_button("Delete multiple terms", SectionDelete);
    		fixed_wait_time(3);
    		waitFor(firstSectionTermCount, "");
/*    		mouse_hover("Section Terms", firstSectionTermCount);
    		oParameters.SetParameters("FirstSectionTermCountAfterDelete",get_field_value("term Count", firstSectionTermCount).replace(" Terms", ""));

    		if(oParameters.GetParameters("FirstSectionTermCountBeforeDelete").equals(oParameters.GetParameters("FirstSectionTermCountAfterDelete"))) 
    		{
    			waitFor(secondSectionTermCount, "");
    			oReport.AddStepResult("Term Deleting","Hovered over Terms, selected multiple Terms, but those terms are not deleted upon clicking on delete in respective Section","FAIL");
    		}	
    		else
    		{
    			waitFor(secondSectionTermCount, "");
    			oReport.AddStepResult("Term Deleting", "Multiple Terms are Deleted from respective section ", "PASS");
    		}*/
    	}
    }	
	
	
	By deleteTermPopup = By.xpath("//div[@class='medium-header bold ng-binding']");//[contains(.,'Delete rate sheet term')]");
	
	
	// To delete single Term
	public void deleteSingleTerm(String termName)
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By term = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'"+termName+"')]");
		
		mouse_hover("Term", term);		
		click_button("Delete Term", termDeleteIcon);
		
		if(IsDisplayed("Delete Term Popup", deleteTermPopup))
			oReport.AddStepResult("Delete rate sheet term popup", "Clicked on term delete icon, verified that 'Delete rate sheet term' popup is displayed ", "PASS");
		else
			oReport.AddStepResult("Delete rate sheet term popup", "Clicked on term delete icon but 'Delete rate sheet term' popup is not displayed ", "FAIL");
		
		click_button("Delete Term", SectionDelete);
		fixed_wait_time(3);
		waitFor(openedRateSheet, "Rate Sheet Title bar");
		
		if(IsDisplayed(termName+" Term", term))
			oReport.AddStepResult("Term Delete", "Clicked on "+termName+" delete icon then clicked on delete term but that term not deleted", "FAIL");
		else
			oReport.AddStepResult("Term Delete", "Clicked on "+termName+" delete icon then clicked on delete term, Verified that term deleted", "PASS");
	}
	
	
	public void viewRateSheetPeriodDropdown()
	{
		click_button("Period Dropdown", periodDropdown);
		mouse_hover("Ratesheet Period", firstPeriodDate);
		
		if(IsDisplayed("Period Edit Icon", periodDateEditIcon) && IsDisplayed("Period Delete Icon", peiodDateDeleteIcon))
			oReport.AddStepResult("Edit and delete Icon", "In view permission Rate Sheet mouse hovered over period but edit and delete icon is displayed", "FAIL");
		else
			oReport.AddStepResult("Edit and delete Icon", "In view permission Rate Sheet mouse hovered over period, verified that edit and delete icon is not displayed", "PASS");

		// Clicking on rate sheet period
		
		click_button("Ratesheet period", secondPeriodDate);
		
		By existedTerm = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-4 ng-binding'][contains(.,'Section_Term')]");
		
		if(IsDisplayed("Ratesheet Term", existedTerm))
			oReport.AddStepResult("Period Term", "Clicked on period, verified that particular period term is diplayed", "PASS");
		else
			oReport.AddStepResult("Period Term", "Clicked on period but that particular period term is not diplayed", "FAIL");
		
		//clicking on first period
		
		click_button("Period Dropdown", periodDropdown);
		click_button("Ratesheet Period", firstPeriodDate);		
	}
	
	
	By firstTerm = By.xpath("//ul[@class='ratesheet-terms terms-hide-overflow']/li[1]/div[@class='data-row col-lg-12 col-md-12 col-sm-12']");
	
	
	public void termDeleteIcon()
	{
		mouse_hover("RateSheet Term", firstTerm);
		
		if(!IsDisplayed("Term Delete Icon", termDeleteIcon))
			oReport.AddStepResult("Term delete icon", "Hovered over term, verified that edit and delete icon is not displayed", "PASS");
		else
			oReport.AddStepResult("Term delete icon", "In view permission Rate Sheet Hovered over term that term delete icon is displayed", "FAIL");		
	}
	
	public void termEditWindowSaveButton(String termType)
	{
		click_button("Term", firstTerm);
		waitFor(termEditWindow, "Edit term window");
		
		if(IsDisplayed("Edit term window save", editWindowSave))
			oReport.AddStepResult("Edit term window save", "Logged in with view only permission, Clicked on Rate Sheet "+termType+" term and verified that save button is displayed on edit term window", "FAIL");
		else
			oReport.AddStepResult("Edit term window save", "Clicked on term, verified that edit term window is displayed without save button", "PASS");
		
		click_button("Edit term window cancel", editWindowCancel);
	}
	
	
	//RateSheet View Only Permission VR
	public void rateSheet_ViewOnly_Permission_VR()
	{
		login("VIEW");
		changePricingEngine();
		navigateToRateSheets();	
		
		searchRateSheet("VIEW", "BLR_View_Ratesheet_DND");//BLR_Ratesheet_DND
		
		//Terms
		viewRateSheetPeriodDropdown();
		termDeleteIcon();
		termEditWindowSaveButton("");
		
		//Stop Loss
		clickStopLossTab();
		termDeleteIcon();
		termEditWindowSaveButton("Stop Loss");
		
		//Exclusions
		clickExclusionsTab();
		termDeleteIcon();
		termEditWindowSaveButton("Exclusion");
		
		//Terms tab
		clickTermsTab();
		filterReports("Terms");
		
		//History filter reports
		clickHistoryTab();			
		userFilterReports();
		dateFilterReports();
		auditDetailFilterReports();
		
		//downloading history filter reports
		downloadRateSheetHistory();
		
		//clear filters
		clearFilters();
		
		//page navigations
		recordsPerPage(1,"50");
		recordsPerPage(2,"100");
		recordsPerPage(3,"200");
		pageNavigations();
		
		//downloading history reports
		downloadRateSheetHistory();
		
		//clicking on print icon
		//printPreview();
		
		//To logout of the portal
		logout();	
	}
	
	
	By manageServiceLinesTab  = By.xpath("//a[text()='Manage Service Lines']");
	
	By selectedManageServiceLines = By.xpath("//li[@class='portal-tab-pane ng-scope active']/a[contains(text(),'Manage Service Lines')]");
	
	
	// This method will click on Stop Loss tab
	public void clickManageServiceLinesTab()
	{
		navigate_to("Manage Service Lines Tab", "Selected Manage Service Lines Tab", manageServiceLinesTab, selectedManageServiceLines);
	}
	
	
	By useExistingServiceLineGroupChecked = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-6 col-xs-6 pad-l-0']//input[@checked='checked']");
	
	By useExistingServiceLineGroupRadio  = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-6 col-xs-6 pad-l-0']//label[text()='Use existing Service Line Group ']/../input");
	
	By createNewServiceLineGroupRadio  = By.xpath("//div[@class='col-lg-3 col-md-3 col-sm-6 col-xs-6 pad-l-0']//label[text()='Create a new Service Line Group ']/../input");
	
	By serviceLineGroupNameDD  = By.xpath("//div[@class='col-lg-9 col-md-9 col-sm-6 col-xs-6 pad-10 bg-light-gray']//a[@class='btn btn-light view-bg-text']");
	
	By serviceLineGroupNamesDDList = By.xpath("//div[@class='col-lg-9 col-md-9 col-sm-6 col-xs-6 pad-10 bg-light-gray']//ul/li/a[contains(.,'Test EB')]");
	
	By serviceLineGroupNamesDDList1 = By.xpath("//div[@class='col-lg-9 col-md-9 col-sm-6 col-xs-6 pad-10 bg-light-gray']//ul/li["+String.valueOf(get_random_number(1))+"]/a");
	
	By serviceLineGroupNamesCount = By.xpath("//div[@class='col-lg-9 col-md-9 col-sm-6 col-xs-6 pad-10 bg-light-gray']//ul/li/a");
	
	By goToServiceLinesButton = By.xpath("//div[@class='col-lg-9 col-md-9 col-sm-6 col-xs-6 pad-10 bg-light-gray']//input[@value='Go to Service Lines']");
	
	By addServiceLineTextBox = By.xpath("//input[@id='addserviceLines']");
	
	By saveAndNextButton = By.xpath("//input[@value='Save & Next']");
	
	By serviceLinesListDD = By.xpath("//a[@class='btn btn-light view-bg-text btn-xs']/../../../div[contains(.,'FeeScheuleRateType_Term')]/..//span[@class='dropdown-text hide-overflow ng-binding']");
	
	
	
	By refreshButton = By.xpath("//div[@class='manage-service-lines-content col-lg-12 col-md-12']//input[@value='Refresh']");
	
	By disabledRefresh = By.xpath("//div[@class='manage-service-lines-content col-lg-12 col-md-12']//input[@value='Refresh'][@disabled]");
	
	By mapButton = By.xpath("//div[@class='manage-service-lines-content col-lg-12 col-md-12']//input[@value='Map']");
	
	By terms = By.xpath("//li[@class='data-row hand-cursor sort-item overflow-visible ng-scope']");
	
	By selectAllLink = By.xpath("//div[@id='ratesheetSection']//span[text()=' Select All ']");
	
	By selectServiceLineDD = By.xpath("//div[@id='styledDropdown']//a//span[contains(.,'Select Service Line')]");
	
	By saveButton = By.xpath("//div[@id='serviceLine']//input[@value='Save']");
	
	By serviceLineSummary = By.xpath("//div[text()='Service Line Summary']");
	
	By showOtherRatesheetLink = By.xpath("//div[@id='otherRateSheets'][contains(.,'show other rate sheets')]");
	
	By otherRatesheets = By.xpath("//div[@class='panel-body scroll-auto large-height']//div[@ng-show='showHideOtherRateSheets']");
	
	By serviceLinesInactiveLink = By.xpath("//li[@class='ng-scope ng-isolate-scope inactive']//a[text()='Service Lines']");
	
	By serviceLinesActiveLink = By.xpath("//li[@class='ng-scope ng-isolate-scope active']//a[text()='Service Lines']");
	
	By selectServiceLineGroupInactiveLink = By.xpath("//li[@class='ng-scope ng-isolate-scope inactive']//a[text()='Select Service Line Group']");
	
	By serviceLineGroupNameTextbox = By.xpath("//input[@id='serviceLineGroupName']");
	
	By addServiceLinesButton = By.xpath("//input[@value='Add Service Lines']");
	
	
	
	By serviceLineName1 = By.xpath("//div[@ng-if='showMappingTab']//label[text()='Map Selected Terms:']/..//div[@id='styledDropdown']//ul/li[contains(.,'"+oParameters.GetParameters("ServiceLineName")+"')]");
	
	By serviceLines = By.xpath("//ul[@id='iContainerServiceLines']//div[@class='col-lg-10 col-md-10 col-sm-10 col-xs-9']");
	
	By firstServiceLine = By.xpath("//ul[@id='iContainerServiceLines']/li[1]//div[@class='col-lg-10 col-md-10 col-sm-10 col-xs-9']/input");
	
	By serviceLineDeleteIcon = By.xpath("//ul[@id='iContainerServiceLines']/li/div/a[@style='display: inline;']/i[@class='left fa fa-minus-square']");
	
	By showAllTermsCheckbox = By.xpath("//label[text()='Show all Terms']/..//input[@ng-model='displayUnmapped']");
	
	
	
	public void mapServiceLineToTerm()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Use existing Service Line Group checked Radio", useExistingServiceLineGroupChecked))
			oReport.AddStepResult("", "Clicked on 'Manage Service Lines' tab, Verified that Use Existing Service Group Radio button is selected by default", "PASS");
		else
			oReport.AddStepResult("", "Clicked on 'Manage Service Lines' tab but Use Existing Service Group Radio button is not selected by default", "FAIL");
		
		// Selecting Service Line group
		
		click_button("Service Line Group Name Dropdown", serviceLineGroupNameDD);		
		click_button("Service Line Group Names", serviceLineGroupNamesDDList1);		
		navigate_to("Go To Service Lines Button", "Add Service Line textbox", goToServiceLinesButton, addServiceLineTextBox);				
		enter_text_value("Add Service Line textbox", addServiceLineTextBox, "Test"+System.currentTimeMillis());
		oParameters.SetParameters("ServiceLineName", get_field_value("Service Line name", addServiceLineTextBox));
		click_button("Save and Next Button", saveAndNextButton);
		
		// Mapping to service line to term
		
		By serviceLineName = By.xpath("//div[@class='panel-body scroll-auto large-height']//div[@class='map-content o-auto']//li[@ng-repeat='s in sections'][1]//ul[@class='ratesheet-terms']//li[@class='data-row hand-cursor sort-item overflow-visible ng-scope'][1]//a[@role='menuitem' and contains(.,'"+oParameters.GetParameters("ServiceLineName")+"')]");
		
	//	click_button("Show all Terms checkbox", showAllTermsCheckbox);
		click_button("Service Lines List DD", serviceLinesListDD);
		click_button("Service Line name", serviceLineName);
		
		oParameters.SetParameters("TermCountBeforeMap",String.valueOf(get_table_row_count(terms)));	
		
		if(IsDisplayed("Disabled Refresh button", disabledRefresh))
		{
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			return;
		}	
		else
			click_button("Refresh button", refreshButton);
		
		if(oParameters.GetParameters("TermCountBeforeMap").equals(String.valueOf(get_table_row_count(terms))))
			oReport.AddStepResult("Term Mapping", "Mapped the service lines to respective term and clicked on refresh but that respective term is not mapped and hidden ", "FAIL");
		else
			oReport.AddStepResult("Term Mapping", "Mapped the service lines to respective term and clicked on refresh, Verified that respective term is mapped and hidden ", "PASS");
	}
	
	
	public void mapAllTermsToServiceLine()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Mapping Terms from Map Selected Terms Dropdown
		
		oParameters.SetParameters("TermCountBeforeMap",String.valueOf(get_table_row_count(terms)));	
		
		click_button("Select All", selectAllLink);
		
		if(IsDisplayed("Select Service Line Dropdown", selectServiceLineDD))
			oReport.AddStepResult("Map Selected Terms dropdown", "Clicked on select All terms, Verified that all the terms are selected and Map Selected Terms drop down is enabled ", "PASS");
		else
			oReport.AddStepResult("Map Selected Terms dropdown", "Clicked on select All terms but that all the terms are not selected and Map Selected Terms drop down is not enabled ", "FAIL");
		
		// Mapping service lines to those selected Terms
		
		click_button("Select Service Line Dropdown", selectServiceLineDD);
		click_button("Service Line Name", serviceLineName1);
		click_button("Map Button", mapButton);
		click_button("Refresh button", refreshButton);
		
		if(oParameters.GetParameters("TermCountBeforeMap").equals(String.valueOf(get_table_row_count(terms))))
			oReport.AddStepResult("", "Selected all the term and mapped service lines to all the term but that all selected lines are not mapped ", "FAIL");
		else
			oReport.AddStepResult("", "Selected all the term and mapped service lines to all the term, verified that all selected lines are mapped ", "PASS");
		
		// Saving mapped service lines
		
		click_button("Save Button", saveButton);
		fixed_wait_time(5);
		
		if(IsDisplayed("Service Line Summary", serviceLineSummary))
			oReport.AddStepResult("Service Line Summary", "All terms are mapped and clicked on save, verified that mapped service lines are saved and service line summary is displayed", "PASS");
		else
			oReport.AddStepResult("Service Line Summary", "All terms are mapped and clicked on save but that mapped service lines are not saved and service line summary is not displayed", "FAIL");
	}
	
	
	// Clicking on 'show other rate sheets'
	public void showOtherRateSheetsLink()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Show other Ratesheets Link", showOtherRatesheetLink);
		
		if(IsDisplayed("Other Ratesheets", otherRatesheets))
			oReport.AddStepResult("", "Clicked on 'show other ratesheets' link, Verified that Group used by other rate sheets is displayed ", "PASS");
		else
			oReport.AddStepResult("", "Clicked on 'show other ratesheets' link but that Group used by other rate sheets is not displayed ", "FAIL");
	}
	
	
	// Clicking on Service Lines tab
	public void navigateToServiceLineTab()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Service Lines tab", serviceLinesInactiveLink);
		
		if(IsDisplayed("Service Lines tab", serviceLinesActiveLink)&& IsDisplayed("Add Service Line TextBox", addServiceLineTextBox))
			oReport.AddStepResult("Service Lines Tab", "Clicked service line tab, Verified that respective tab is displayed", "PASS");
		else
			oReport.AddStepResult("Service Lines Tab", "Clicked service line tab but that respective tab is not displayed", "FAIL");
	}
	
	
	// Creating a new Service Line Group
	public void createNewServiceLineGroup()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Select Service Line Group tab", selectServiceLineGroupInactiveLink);
		click_button("Create a New Service Line Group Radio button", createNewServiceLineGroupRadio);
		enter_text_value("Service line group name text box", serviceLineGroupNameTextbox, "New Service line group"+get_random_number(3));
		oParameters.SetParameters("NewServiceLineGroupName", get_field_value("New Service Line Group Name", serviceLineGroupNameTextbox));
		click_button("Add Service Lines Button", addServiceLinesButton);
			
		By addedServiceLine = By.xpath("//b[text()='"+oParameters.GetParameters("NewServiceLineGroupName")+"']");
		
		if(IsDisplayed("Added Service Line", addedServiceLine) && IsDisplayed("Service Lines Tab", serviceLinesActiveLink))
			oReport.AddStepResult("New Service Line", "created new service line is added and navigated to 'Service Lines' tab successfully ", "PASS");
		else
			oReport.AddStepResult("New Service Line", "Tried to created new service line group, entered Service line group name then clicked on 'Go to Service Lines' button but new service line is not added and not navigated to 'Service Lines' tab", "FAIL");
	}
	
	
	// Adding new service lines 
	public void addNewServiceLines()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Add Service Line TextBox", addServiceLineTextBox, "Test"+System.currentTimeMillis());
		oParameters.SetParameters("ServiceLineName", get_field_value("Service Line name", addServiceLineTextBox));
		performKeyBoardAction("ENTER");
		enter_text_value("Add Service Line TextBox", addServiceLineTextBox, "Test"+System.currentTimeMillis());
		oParameters.SetParameters("ServiceLineName2", get_field_value("Service Line name", addServiceLineTextBox));
		performKeyBoardAction("ENTER");
		click_button("Save and Next Button", saveAndNextButton);
		 
		if(IsDisplayed("Service Lines List Dropdown", serviceLinesListDD))
			oReport.AddStepResult("", "New Service Line is added and navigated to 'Map Service Lines' tab successfully ", "PASS");
		else
			oReport.AddStepResult("", "Added few Service Lines under service Line tab and clicked on save and Next button but New Service Line is not added and not navigated to 'Map Service Lines' tab ", "FAIL");
	}
	
	
	// Mapping the new Service Line to terms
	public void mapServiceLines()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By serviceLineName = By.xpath("//div[@class='panel-body scroll-auto large-height']//div[@class='map-content o-auto']//li[@ng-repeat='s in sections'][1]//ul[@class='ratesheet-terms']//li[@class='data-row hand-cursor sort-item overflow-visible ng-scope'][1]//a[@role='menuitem' and contains(.,'"+oParameters.GetParameters("ServiceLineName")+"')]");
		
		click_button("Service Lines List DD", serviceLinesListDD);
		click_button("Service Line name", serviceLineName);
		click_button("Save and Next button", saveAndNextButton);
		
		if(IsDisplayed("Service Line Summary", serviceLineSummary))
			oReport.AddStepResult("Service Line Summary", "Term is mapped against new service line is added and displayed under service line summary ", "PASS");
		else
			oReport.AddStepResult("Service Line Summary", "All terms are mapped and clicked on save but that mapped service lines are not saved and service line summary is not displayed", "FAIL");
	}
	
	
	// Deleting Service line
	public void deleteServiceLine()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Service Lines tab", serviceLinesInactiveLink);		
		oParameters.SetParameters("ServiceLinesBeforeDelete", String.valueOf(get_table_row_count(serviceLines)));		
		mouse_hover("Service Line", firstServiceLine);
		click_button("Service Line Delete Icon", serviceLineDeleteIcon);
		
		if(oParameters.GetParameters("ServiceLinesBeforeDelete").equals(String.valueOf(get_table_row_count(serviceLines))))
			oReport.AddStepResult("Service line delete", "clicked on Service line delete icon but that service line is not deleted ", "FAIL");
		else
			oReport.AddStepResult("Service line delete", "clicked on Service line delete icon, Verified that service line is deleted ", "PASS");
	}
	
	
	// Editing Service line name
	public void editServiceLine()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("First Service Line", firstServiceLine);
		oParameters.SetParameters("ServiceLineNameBeforeEdit", get_field_value("Service Line", firstServiceLine));
		enter_text_value_without_clear("Service Line", firstServiceLine, "Edited");
		click_button("Service Lines", serviceLinesActiveLink);
		
		if(oParameters.GetParameters("ServiceLineNameBeforeEdit").equalsIgnoreCase(get_field_value("Service Line", firstServiceLine)))
			oReport.AddStepResult("", "Clicked on service line and modified the line but that service line is not modified ", "FAIL");
		else
			oReport.AddStepResult("", "Clicked on service line and modified the line, verified that service line is modified ", "PASS");		
	}
	
	
	By modelsPlugin=By.xpath("//a[text()='Models']");
	
	By SearchModel=By.xpath("//div[@id='sl-right']//div[@class='second-line ng-scope ng-binding']");
	
	
	// Rate Sheet-Manage Service Line VR	
	public void RateSheet_Manage_ServiceLine_VR()
	{	
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();
		
		addRateSheet("ManageServiceLines","Manage_ServiceLines_RateSheet");
		
		//Creating Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "Section10");
		AddSectionDetails("", "");
		
		feeScheduleAndProcedureGroupRateTypeTerms("Manage_ServiceLines_RateSheet");
		
		clickManageServiceLinesTab();
		mapServiceLineToTerm();
		mapAllTermsToServiceLine();
		showOtherRateSheetsLink();
		navigateToServiceLineTab();
		createNewServiceLineGroup();
		addNewServiceLines();
		mapServiceLines();
		deleteServiceLine();
		editServiceLine();		
		navigate_to("Model Plugin", "First Model Job", modelsPlugin, SearchModel);
		oModelsLibrary.addModelvalidation();
		oModelsLibrary.manageClaimCriteria(oModelsLibrary.manageClaimButton);
		oModelsLibrary.productModelDetails();
		oModelsLibrary.manageClaimCriteria(oModelsLibrary.useCustomCriteriaButton);
		oModelsLibrary.selectBaseRateSheet("");
		oModelsLibrary.selectProposalRateSheet("");
		oModelsLibrary.saveModelJobDetils();
		oModelsLibrary.changeStatusDropDown();
		oModelsLibrary.submitModelJob();
		oModelsLibrary.viewMoreResult();				
		navigateToRateSheets();
		deleteRatesheet("Production","Manage_ServiceLines_RateSheet");
		logout();	
	}
		
	
	// RateSheet CRUD Sections VR
	public void RateSheet_CRUD_Sections_VR()
	{
		login("EDIT");
		changePricingEngine();
		navigateToRateSheets();		
		addRateSheet("CRUDSectionsRateSheet","CRUD_Sections_RateSheet_DND");		
		addingSectionWithMaxRateTypeasPerCase();
		addingSectionBeforeSection();
		addingSectionAfterSection();
		addingSectionWithAdvanceQualifierOptions();
		addingSectionWithExistingRateSheetSection();
		addingSectionWithStopLossAndExclusion("CRUD_Sections_RateSheet_DND");
		editingSection();
		editWindowCancelScenario();
		deleteSection("");	
		logout();	
	}
	
	
	By DRGUserRateSetPeriod = By.xpath("//select[@id='drgUserRatePeriod']");
    
	
    public void StopLossSection(String i)
    {   
    	if(oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_QualificationGroups_StopLoss_Formula_Expression"))
    		oParameters.SetParameters("StopLossSectionName"+i,oParameters.GetParameters("StopLossSectionName"+i)+get_random_alphanumeric(4));
    	
    	enter_text_value("Stop Loss Section Name Feild", stopLossSectionNameField, oParameters.GetParameters("StopLossSectionName"+i));
          
    	if(!oParameters.GetParameters("IncludeExclusionsCheckbox"+i).isEmpty())
    		click_button("Include Exclusions Checkbox", includeExclusionsCheckBox);
          
    	if(!oParameters.GetParameters("UseDRGUserRateData"+i).isEmpty())       
    	{      
    		click_button("Use DRG user rate data in stop loss calculations and formulas CheckBox", useDRGUserRateDataCheckBox);
                       
    		enter_text_value("DRG user rate set Search Box", DRGUserRateSetSearchBox, oParameters.GetParameters("DRGUserRateSetSB"+i));
                       
    		selectOption(DRGUserRateSetPeriod,"value",oParameters.GetParameters("DRGuserratesetperiod"+i));
    	}      
          
    	if(!oParameters.GetParameters("MaxRateType"+i).isEmpty())
    	{      
    		selectOption(maxRateTypeDD,"visibletext",oParameters.GetParameters("MaxRateType"+i));
                 
    		enter_text_value("Maximum Amount ", maximumAmount, oParameters.GetParameters("MaxRateAmount/Percentage"+i));
    	}
                 
    	if(!oParameters.GetParameters("MinRatetype"+i).isEmpty())
    	{
    		selectOption(minRateTypeDD,"visibletext",oParameters.GetParameters("MinRatetype"+i));
                       
    		enter_text_value("Maximum Amount ", minimumAmount, oParameters.GetParameters("MaxRateAmount/Percentage"+i));
    	}
                 

    	if(oParameters.GetParameters("AdvancedQualifierOptions"+i).equals("YES"))
    	{      
    		By CheckBox = By.xpath("//label[contains(.,'"+oParameters.GetParameters("SelectStopLoss"+i)+"')]//input"); 
    		
    		click_button("Select Check Box", CheckBox);
    	}  
    	
    	clickSaveButton("StopLoss Save Button","Add Stop Loss Section" , stopLossSectionWindowSave, addStopLossSectionWindow); 
    	
		By SectionName = By.xpath("//span[@class='bold ng-binding'][contains(.,'"+oParameters.GetParameters("StopLossSectionName"+i)+"')]");
		
		if(IsDisplayed("Section Name", SectionName))
			oReport.AddStepResult("Added Section", "Filled all mandatory fields in Add Section Window and clicked on save button and verified that new '" +oParameters.GetParameters("StopLossSectionName"+i)+ "' Section is added  ", "PASS");
		else
			oReport.AddStepResult("Added Section", "Filled all mandatory fields in Add Section Window and clicked on save button and verified that new section is not added", "FAIL");
    } 

}
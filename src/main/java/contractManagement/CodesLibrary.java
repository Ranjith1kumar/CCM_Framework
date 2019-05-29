package contractManagement;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.*;
import org.openqa.selenium.*;
import libraries.*;

/**
 * @author C16137
 *
 */

public class CodesLibrary extends CCMLibrary
{
	ExcelData oExcelData = new ExcelData(oParameters);
	
	public CodesLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}
	
	
	public void xpathChange()
	{
		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
		{	
			openedpage=openedpageDC;
			OpenedCodeSet=OpenedCodeSetDC;
		}
		else
		{
			openedpage=openedpageDC;
			OpenedCodeSet=OpenedCodeSetDC;
		}
	}
	
	
	By codes=By.xpath("//a[text()='Codes']");
	
	By SelectCodeSetType=By.xpath("//div[@id='codeView']//div[@class='msg-blurb']//div[@class='second-line ng-scope ng-binding']");
	
	By contracts=By.xpath("//div[@id='nav']//a[text()='Contracts']");
	
	
	//This Method is used to navigate to codes plugin.
	public void navigateCodesPlugin() 
	{
		navigate_to("Codes_plugin","Select a Report to begin",codes,SelectCodeSetType);
	}
	
	
	By dropdown =By.xpath("//div[@id='codeView']/div[@class='codes ng-scope']//div[@class='portal-header']//div[@id='styledDropdown']//a[@class='btn btn-light view-bg-text']/span[1]");
	
	public By dropdownName = By.xpath("//div[@id='codeView']//div[@id='styledDropdown']/a[1]//span[@class='dropdown-text hide-overflow ng-binding']");
	
	//This Method is used to click on the dropdown button.
	public void codesDropdown()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("Dropdown Button", dropdown))
		{
			oReport.AddStepResult("Codes Plugin", "Navigated to codes plugin and verified that 'Select a code set type' dropdown is displayed", "PASS");
			click_button("Dropdown",dropdown);
			
			if(IsElementDisplayed("HCPCS/CPT", HCPCS_CPT) && IsElementDisplayed("Codes_ICD", ICD) 
					&& IsElementDisplayed("Codes_Diagnosis", diagnosis) && IsElementDisplayed("Codes_DRG", DRG))
				oReport.AddStepResult("Select a Code Set Type", "clicked on 'select a code set type' and verified that all code set types are displayed", "PASS");
			else
				oReport.AddStepResult("Select a Code Set Type", "clicked on 'select a code set type' and found that all code set types are not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Codes Plugin", "Navigated to codes plugin and verified that 'Select a code set type' dropdown is not displayed", "FAIL");
	}
	
	 
	public By HCPCS_CPT=By.xpath("//li[@id='i[__valueField]']/a[contains(.,'HCPCS/CPT')]");
	
	public By addHcpcsCpt=By.xpath("//a[contains(text(),'Add a HCPCS/CPT')]");
	
	//This Method is used to select a HCPCS/CPT
	public void SelectCodeSetType(By elemdesc,By addelement)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Select a Code Set Type",elemdesc);
		
		if(IsDisplayed("ADD an Code Set",addelement) || IsDisplayed("Respective opened Code Set page",openedpage))
			oReport.AddStepResult("Clicked on Code set", " clicked on code set type from dropdown and Verified that respective Code Set page is displayed", "PASS");
		else
			oReport.AddStepResult("Clicked on Code set", " clicked on code set type from dropdown and Verified that respective Code Set page is not displayed", "FAIL");
	}
	
	
	//Always selecting first code set only so used indexing. 
	public By firstElement=By.xpath("//ul[contains(@class,'data-list drillable-list')]//li[2]");
		
	public By SearchBox = By.xpath("//div[@id='codeView']//input[contains(@class,'search-text-box input-sm form-control')]");
	
	By OpenedCodeSet=By.xpath("//div[@class='col-lg-6 col-md-6 col-sm-6 col-xs-12 large-header pad-l-0 ng-binding']");
	
	By OpenedCodeSetDC=By.xpath("//div[@id='codeView']//div[@class='col-lg-9 col-md-9 col-sm-9 col-xs-12 large-document-header hide-overflow pad-l-0 ng-binding']");
	
	By Searchedcodeset=By.xpath("//ul[contains(@class,'data-list drillable-list')]//li[contains(@class,'data-row ng-scope ng-binding')]");
	
	
	//This Method is used to select a First Code Set.
	public void selectCode()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		oParameters.SetParameters("SecondCodeSetName", get_field_value("First Code Set", firstElement));
		
		enter_text_value("Search Code Set", SearchCodeSet,oParameters.GetParameters("SecondCodeSetName"));
    	performKeyBoardAction("ENTER");
		
    	By searchedCodeSet = By.xpath("//li[contains(.,'"+oParameters.GetParameters("SecondCodeSetName")+"')]");
    	
    	click_button("Selected particular Code Set",searchedCodeSet);
		
		if(get_text(searchedCodeSet).equalsIgnoreCase(get_text(OpenedCodeSet)))
		{	
			fixed_wait_time(3);
			oReport.AddStepResult("Select first Codeset", " Selected first Codeset from the list and verified that repective code set is displayed", "PASS");
			enter_text_value("Search Code Set", SearchCodeSet, "");
			performKeyBoardAction("ENTER");
		}
		else
			oReport.AddStepResult("Select first Codeset", " Selected first Codeset from the list and verified that repective code set is not displayed", "FAIL");
	}
	
	
	By AddCodeSetValue=By.xpath("//div[@id='codeView']//a[@title='Add Code']");
	
	//This Method is used to click on Add Code
	public void addCodeSetButton()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		refresh_page();
		oParameters.SetParameters("ProcedureCountBeforeAdding", String.valueOf(get_table_row_count(ProcedureCountBeforeadding)));
		oParameters.SetParameters("CodeSetDropDownName", get_text(dropdown));
		
		clickAddButton("'Add Code Set Button'","'Add Code Set Value' Tab",AddCodeSetValue,addcodesettab);
	}
	
	
	By addcodesettab=By.xpath("//div[@title='Add Code Set Value']");
	
	public By procedure=By.xpath("//div[@class='input-wrapper ']//input[@id='procedure']");
	
	public By addcodesetsave_button=By.xpath("//workflow-modal[@show='showCreateCodeValueModal']//input[@id='button.saveId']");
	
	public By addCodeSet_CancelButton = By.xpath("//workflow-modal[@show='showCreateEntryModal']//input[@id='button.cancelId']");
	
	By cancel=By.xpath("//input[@id='button.cancelId']");
	
	public By discard=By.xpath("//div[@id='dialog']//div[@id='dialog_buttons']/input[@value='Discard']");
	
	public By ProcedureCountBeforeadding=By.xpath("//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']/tbody/tr");
	
	//This Method is used to fill the Details of Add Code Set Value Tab.
	public void addCodeSetTabSaveScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Procedure",procedure,"00"+get_random_number(2)+"T");
		click_button("Save Button",addcodesetsave_button);
		
		if(IsDisplayed("Error Adding Code Set Tab", ErrorAddingCodeSet))
   	   	{
   	   		click_button("Error Adding Code Set Tab Ok button", ErrorAddingCodeSetOKButton);
   	   		enter_text_value("Procedure",procedure,"00"+get_random_number(2)+"T");
   	   		clickSaveButton("Add Code Set Window Save Button", "Add Code Set Window", addcodesetsave_button, addcodesettab);
   	   	}
		
		if(get_table_row_count(ProcedureCountBeforeadding) > Integer.parseInt(oParameters.GetParameters("ProcedureCountBeforeAdding")))
			oReport.AddStepResult("Add code set save Button", " In 'Add Code Set Value' window entered Procedure code and clicked on save button and verified that new  procedure code is added", "PASS");
		else
			oReport.AddStepResult("Add code set save Button", " In 'Add Code Set Value' window entered Procedure code and clicked on save button and verified that new  procedure code is not added", "FAIL");
	}	
	
	
	By Editbutton =By.xpath("//div[@class='pull-right pad-r-15']//div[@class='pull-left link-btn hand-cursor']");
	
	By EditCodeSettab=By.xpath("//div[@title='Edit a Code Set']");
	
	//This Method is used to click on Edit Code.
	public void editCodeSetButton()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clickAddButton("'Edit Code Set Button'", "'Edit Code Set Value Tab'", Editbutton, EditCodeSettab);
	}
	
	
	public By addCodeSet_SaveButton=By.xpath("//workflow-modal[@show='showCreateEntryModal']//input[@id='button.saveId']");
	
	By editcodesetname=By.xpath("//input[@id='codeSetName']");
	
	By allCodeSet=By.xpath("//ul[contains(@class,'data-list drillable-list')]//li");
	
	
	//This Method is used to Edit Code Set Name.
	public void editCodeSetTab()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Code Set Name",editcodesetname,oParameters.GetParameters("EditCodeSetName")+System.currentTimeMillis());
		oParameters.SetParameters("EditingFirstCodeSetName", convertToWebElement(editcodesetname).getAttribute("value"));
		
		clickSaveButton("'Edit Code Set Value' Save Button", "'Edit Code Set Value Tab'", addCodeSet_SaveButton, EditCodeSettab);
		
		//This Method is used select First Code Set.
		selectEditedCodeSet();
		
		waitFor(firstElement,"First Code Set");
			
		List<WebElement> list =convertToWebElements(allCodeSet);
		
		for(int i=0;i<=list.size();i++)
		{
			System.out.println(list.get(i).getText());
							
			if(oParameters.GetParameters("EditingFirstCodeSetName").equals(list.get(i).getText()))
			{
				oReport.AddStepResult("Edit code set save Button", " Edited existing codeset name with new name and verified that changes are reflected on code set name", "PASS");
				break;
			}
		}
	}
	
	
	//By secondelement=By.xpath("//ul[contains(@class,'data-list drillable-list')]//li[3]");
	
	
	//This Method is used select First Code Set.
	public void selectEditedCodeSet()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search Code Set", SearchCodeSet,oParameters.GetParameters("EditingFirstCodeSetName"));
    	performKeyBoardAction("ENTER");
		
    	By editedCodeSet = By.xpath("//li[contains(.,'"+oParameters.GetParameters("EditingFirstCodeSetName")+"')]");
    	
    	click_button("Selected Edited Code Set", editedCodeSet);
    	
    	enter_text_value("Seacrh Code Set", SearchCodeSet, " ");
    	performKeyBoardAction("ENTER");
    			
    	if(IsElementDisplayed("Respective page", openedpage))
			oReport.AddStepResult("First code set", " Clicked on first code set and verifyed that respective code set is opened", "PASS");
		else
			oReport.AddStepResult("First code set"," Clicked on first code set and verifyed that respective code set is not opened", "FAIL");
	}
	
	
	//This Method is used to Edit Code Set Name.
	public void editCodeSetCancelScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//This Method is used to click on Edit Icon.
		editCodeSetButton();
		
		enter_text_value("Code Set Name",editcodesetname,oParameters.GetParameters("CodeName")+System.currentTimeMillis());
		
		//This method is used perform cancel scenario.
		cancelScenario("Cancel Button","popUp Cancel Button","popUp Discard Button",Edit_code_cancelbutton,dailogbox_cancelbutton,discard);
	}
	
	
	By Edit_code_cancelbutton=By.xpath("//workflow-modal[@show='showCreateEntryModal']//input[@id='button.cancelId']");
	
	By unsavedchangespopup=By.xpath("//div[@class='msg-area']//div[contains(.,'You have unsaved')]");
	
	By dailogbox_cancelbutton=By.xpath("//div[@id='dialog']//div[@id='dialog_buttons']/input[@value='Cancel']");
	
	By Edit_code_cancelbutton1=By.xpath("//div[@id='editCodeSetEntryPanel']//button[@id='button.cancelId']");
	
	public By addcodesetcancelbutton=By.xpath("//workflow-modal[@show='showCreateCodeValueModal']//input[@id='button.cancelId']");
	
	
	//This Method is used to do Cancel Scenario for Edit Code Tab.
	public void CodeSetCancelScenario(By elemdesc)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Cancel Button",elemdesc);
			
		if(IsElementDisplayed("UnSavedChanges PopUp", unsavedchangespopup))
		{
			oReport.AddStepResult("Edit code set window cancel button", " Clicked on 'Edit code set window cancel button' and verified that Unsaved Changes pop up is appeared", "PASS");
			click_button("Dailogbox cancel button",dailogbox_cancelbutton);
			
			if(IsDisplayed("UnSavedChanges PopUp", unsavedchangespopup))
				oReport.AddStepResult("You have UnSaved Changes", "On 'You have unsaved changes' window, clicked on cancel button and verified that 'You have unsaved changes' window is not closed", "FAIL");
			else
			{	
				oReport.AddStepResult("You have UnSaved Changes", "On 'You have unsaved changes' window, clicked on cancel button and verified that 'You have unsaved changes' window is closed", "PASS");
				click_button("Cancel Button",elemdesc);
			
				click_button("Discard button",discard);
			}
		}	
		else
			oReport.AddStepResult("Edit code set window cancel button", " Clicked on 'Edit code set window cancel button' and verified that Unsaved Changes pop up is not appeared", "FAIL");
	}
	
	
	//This Method is used to add procedure name.
	public void addCodeSetSaveScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//This method is used to click on add icon.
		addCodeSetButton();
	    
		//This method is used to add a code set value. 
		addCodeSetTabSaveScenario();
	 }
	  
	
	By procedureFirstelement=By.xpath("//div[@id='addEditEntryParent']//table[contains(@class,'table table-condensed')]//tr[contains(@class,'hand-cursor position')][1]/td[1]");
	
	public By procedureFirstelementDelete=By.xpath("//div[@id='addEditEntryParent']//tbody//tr[1]//td//span[@class='lnk-btn-txt ng-binding']/..");
	
	//This method is used to Hover over on Procedure first element and verifing that delete icon.
	public void hoverOverCodePeriod()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		/*List<WebElement> totalCodeSet=convertToWebElements(TotalCodeset);
    	
    	for(int i=2;i<=totalCodeSet.size();i++)
    	{
    		By TotalCodeset=By.xpath("//ul[contains(@class,'data-list drillable-list')]//li["+i+"]");
    		click_button("procedure code",TotalCodeset);*/
    		
    		if(IsDisplayed("Procedure First", procedureFirstelement))
    		{
    			waitFor(procedureFirstelement,"First Procedure Code set");
    			oReport.AddStepResult("First Code Set", "Clicked on First code set and verified that procedure code is displayed", "PASS");
    			dclick_button("Procedure First Element is Displayed", procedureFirstelement);
    			
    			if(IsElementEnabled("Procedure First element Delete icon", procedureFirstelementDelete))
    				oReport.AddStepResult("Hover over procedure code", " Hovered over procedure code and verified that delete icon is displayed", "PASS");
    			else
    				oReport.AddStepResult("Hover over procedure code", " Hovered over procedure code and verified that delete icon is not displayed", "FAIL");
    			//break;
    		}
    		/*else
    			oReport.AddStepResult("First Code Set", "Clicked on First code set and verified that procedure code is not displayed", "FAIL");
   */ 	}
	//}	
	
	
	By EditCodeSetValuetab=By.xpath("//div[contains(text(),'Edit Code Set Value')]");
	
	By FirstProcedureName=By.xpath("//form[@id='addEditCodeSetValueForm']//div[@class='form-group col-lg-12 col-md-12 col-sm-12 ng-isolate-scope']//input");
	
	//This method is used to click on procedure first element and verifing that edit code set value tab is opened with procedure name. 
	public void EditCodeSetValueWindowValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(procedureFirstelement,"First procedure Code Set");
		
		oParameters.SetParameters("FirstProcedureName", get_text(procedureFirstelement));
		
		click_button("Procedure",procedureFirstelement);
		
		if(IsElementDisplayed("Edit Code Set Value Tab", EditCodeSetValuetab) && oParameters.GetParameters("FirstProcedureName").equalsIgnoreCase(convertToWebElement(FirstProcedureName).getAttribute("value")))
			oReport.AddStepResult("First Procedure"," clicked on first procedure code and verified that 'Edit Code Set Value' window is displayed with respective 'procedure code'" , "PASS");
		else
			oReport.AddStepResult("First Procedure"," clicked on first procedure code and verified that 'Edit Code Set Value' window is not displayed" , "FAIL");
	}
	
	
	By GroupValueFeild=By.xpath("//div[@id='codeValuescodeSetValueEntries']//input[@id='groupValue']");
	
	By Edit_savebutton=By.xpath("//div[@id='fullFooter']//button[@id='button.saveId']");
	
	//This method is used to edit group value and click on save operation.
	public void editCodeSetValue()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Edit Group value",GroupValueFeild,oParameters.GetParameters("Edit_Group_value"));
		
		clickSaveButton("Edit Code Set Value Save Button","'Edit Code Set Value' Tab",Edit_savebutton,EditCodeSetValuetab);
		
		By GorupValue = By.xpath("//div[@id='addEditEntryParent']//td[contains(.,'"+oParameters.GetParameters("FirstProcedureName")+"')]/following-sibling::td[1]");
		
		if(get_field_value("Group Value", GorupValue).equals(oParameters.GetParameters("Edit_Group_value")))
			oReport.AddStepResult("Edit code set value Save Button", " Clicked on first procedure and Entered group value and perform save operation and verified that new group value is changing","PASS");
		else
	   		oReport.AddStepResult("Edit code set value Save Button", " Clicked on first procedure and Entered group value and perform save operation and verified that new group value is not changing", "FAIL");
	}
	
	
	By popup_discardbutton=By.xpath("//div[@id='dialog_buttons']//input[@class='btn btn-danger discard-red']");
	
	
	//This method is used to edit group value and click on cancel operation.
	public void EditCodeSetValueCancelScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//This method is used to click on procedure first element.
		EditCodeSetValueWindowValidation();
		
		enter_text_value("Edit Group value",GroupValueFeild,oParameters.GetParameters("Edit_Group_value2"));
		
		cancelScenario("'Edit Code Set Value' Cancel Button", "'UnSaved Changes popUp' Cancel Button", "'UnSaved Changes popUP' Discard Button", Edit_code_cancelbutton1, dailogbox_cancelbutton, popup_discardbutton);
	}
   
	
	//This method is used to add a code set and performing cancel operation.
	public void AddCodeCancelScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//This method is used to click on add icon.
		addCodeSetButton();
		
		enter_text_value("procedure",procedure,"00"+get_random_number(2)+"T");
	    
		//This method is used to perform cancel scenario.
		cancelScenario("'Add Code Set Value' Cancel Button", "'UnSaved Changes popUp Cancel Button'", "'UnSaved Changes popUp Discard Button'", addcodesetcancelbutton,dailogbox_cancelbutton , discard);
	}
	
	
	By period_dropdown=By.xpath("//div[@id='period-selector']/a");
	
	
	//This Method is used to click on period dropdown.
	public void periodDropdownElement()
    {
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(2);
		clickAddButton("'Period Dropdown Button'", "'Add Period link'", period_dropdown, addperiod);
	}
	
	
	By add_period_deletebutton=By.xpath("//div[@id='period-selector']//li[2]//i[@ng-click='deletePeriod(p, periods)']");
	
	By effectiveDate=By.xpath("//ul[contains(@class,'dropdown-menu period-menu')]//li[2]//a");
	
	By addperiod=By.xpath("//ul[contains(@class,'dropdown-menu period-menu')]//li[1]/a");
	
	By hover_AddPeriod=By.xpath("//ul[contains(@class,'dropdown-menu period-menu')]//li[2]/a");
	
	By edit_period_editbutton=By.xpath("//div[@id='period-selector']//li[2]//i[@ng-click='editPeriod(p)']");
	
	
	//This method is used to perform hover operation and click on edit period.
	public void periodEditButton()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("Effective Date", effectiveDate) && IsElementDisplayed("Add Period", addperiod))
    	{
    		oReport.AddStepResult("Period drop down", " Clicked on period dropdown and verified that 'EffectiveDate' and 'Add period' is displayed", "PASS");
    		mouse_hover("Hover Add Period",hover_AddPeriod);
    	    
    		if(IsElementDisplayed("Add_period_EditButton", edit_period_editbutton) && IsElementDisplayed("Add_period_deletebutton", add_period_deletebutton))
    	   	{
    	    	oReport.AddStepResult("Hovered over on Period", " Hovered over first period and verified that 'Edit' and 'delete' icons are displayed", "PASS");
    	   		
    	    	clickAddButton("'Period Edit Button'","'Edit Effective Period Tab'", edit_period_editbutton, editEffectiveperiod);
    	   	}
    	    else
    	    	oReport.AddStepResult("Hovered over on Period", "Hovered over first period and verified that 'Edit' and 'delete' icons are not displayed", "FAIL");
    	}
    	else
    		oReport.AddStepResult("Period drop down", " Clicked on period dropdown and verified that 'EffectiveDate' and 'Add period' is not displayed", "FAIL");
    }
	
	
	By editEffectiveperiod=By.xpath("//div[@id='addCodeSetPeriodModal']//div[@title='Edit Effective Period']");
	
	By terminationDate=By.xpath("//input[@id='terminationDateCodeSet']");
	
	By EditEffective_Savebutton=By.xpath("//workflow-modal[@show='showPeriodModal']//input[@id='button.saveId']");
	
	By EditEffective_Cancelbutton=By.xpath("//div[@id='codeView']//input[@id='button.cancelId']");
	
	
	//This method is used to add termination date and performing save operation.
	public void periodEditSaveScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("EffectiveDate",get_field_value("", add_effective_date));
		oParameters.SetParameters("AddedTerminationDate", get_next_date(oParameters.GetParameters("EffectiveDate"), 2));
		
		click_button("Termination Date",terminationDate);
		enter_text_value("Termination Date",terminationDate,oParameters.GetParameters("AddedTerminationDate"));
		
		click_button("Edit Effective Save Buttton",EditEffective_Savebutton);
		
		if(IsElementDisplayed("period dropdown", period_dropdown))
		{
			waitFor(period_dropdown,"Period DropDown");
			oReport.AddStepResult("Edit Effective Period Save Button", "Clicked on 'Edit period icon',entered 'termination date' clicked on save button and verified that 'Edit Effective Period' window is closed ", "PASS");
		}
		else
			oReport.AddStepResult("Edit Effective Period Save Button", "Clicked on 'Edit period icon',entered 'termination date' clicked on save button and verified that 'Edit Effective Period' window is not closed", "FAIL");
	}
	
	
	public By DeleteCodeSet=By.xpath("//div[@id='dialog_buttons']/input[@value='Delete']");
	
	By DeleteCodeSetTab=By.xpath("//div[contains(text(),'Delete Code Set Value?')]");
	
	
	//This method is used to delete first procedure.
	public void deleteProcedureScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("Hover on procedure First Element", procedureFirstelement);
			
		if(IsElementEnabled("Procedure FirstElement Delete", procedureFirstelementDelete))
		{
			oReport.AddStepResult("First procedure code", "Hovered over  first procedure code and verified that delete icon is Displayed", "PASS");
			click_button("first procedure delete",procedureFirstelementDelete);
			fixed_wait_time(1);
			
			if(IsDisplayed("Delete Code set Value?", DeleteCodeSetTab))
			{
				oReport.AddStepResult("Delete Code Set Tab", " Clicked on first Procedure code delete icon and verified that 'Delete Code Set Value?' popup is displayed", "PASS");
				click_button("Delete code set value",DeleteCodeSet);
			
				if(IsElementDisplayed("Respective opened page", openedpage))
					oReport.AddStepResult("Delete Procedure Code", " On 'Delete Code Set Value?' popup,Clicked on Delete button and verified that respective code set is deleted", "PASS");
				else
					oReport.AddStepResult("Delete Procedure Code", " On 'Delete Code Set Value?' popup,Clicked on Delete button and verified that respective code set is not deleted", "FAIL");
			}
			else
				oReport.AddStepResult("Delete Code Set Tab", " Clicked on first Procedure code delete icon and verified that 'Delete Code Set Value?' popup is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("First procedure code", " Hovered over first procedure code and verified that delete icon is not Displayed", "FAIL");
	}
	
	
	By cancelButton=By.xpath("//workflow-modal[@show='showPeriodModal']//input[@class='btn btn-default']");
	
	By saveButton=By.xpath("//workflow-modal[@show='showPeriodModal']//input[@class='btn btn-primary']");
	
	
	//This method gets termination date of first period,if termination feild is blank then it adds 2 days to effective date and enters it to termination date feild if not it will click in cancel button.
	public void terminationDateValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//This method is used to click on period drop down.
		periodDropdownElement();
		
		if(IsDisplayed("First period date", hover_field))
		{	
			mouse_hover("Hover on First period", hover_AddPeriod);
			fixed_wait_time(2);
			
			click_button("Edit Period Button", edit_period_editbutton);
			
			if(IsDisplayed("Edit effective period tab", editEffectiveperiod))
			{
				if(isTextBoxBlank("termination date", terminationDate))
				{
					waitFor(add_effective_date,"");
					oParameters.SetParameters("EffectiveDate", get_field_value("get Effective Date", add_effective_date));
			    	oParameters.SetParameters("NewEffectiveDatePeriod", get_next_date(oParameters.GetParameters("EffectiveDate"), 2));
			    	enter_text_value("Termination Date",terminationDate , oParameters.GetParameters("NewEffectiveDatePeriod"));
			    	performKeyBoardAction("ENTER");
			    	waitFor(terminationDate,"Termination Date");
			    	oParameters.SetParameters("TerminationDate",get_field_value("", terminationDate));
			    	click_button("Save Button", saveButton);
				}
				else
				{	
					oReport.AddStepResult("Termination Date", "Termination date is present", "SCREENSHOT");
					waitFor(terminationDate,"Termination Date");
					oParameters.SetParameters("TerminationDate",get_field_value("", terminationDate));
					click_button("Cancel Button", cancelButton);
				}
			}
		}
	}
	
	
	By inactive=By.xpath("//div[@model='period.statusCode']/label/input[@value='Prelim']");
    
    By add_period=By.xpath("//li[@class='hand-cursor border-b-1 noStatus']/a");
    
    By add_effective_Period=By.xpath("//div[@title='Add Effective Period']");
    
    By add_effective_date=By.xpath("//input[@id='effectiveDateCodeSet']");
    
    By terminationPeriodDate=By.xpath("//input[@id='terminationDateCodeSet']");
    
    By active=By.xpath("//div[@model='period.statusCode']/label/input[@value='Actv']");
    
    By addedPeriod=By.xpath("//div[@id='period-selector']/a/span[1]");
    
    By addPeriod=By.xpath("//div[@id='period-selector']//a[contains(.,'Add Period')]");
    
    
    //This method is used to 'Creating a New Period'. 
    public void addNewPeriod()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//This method is used to click on period drop down.
    	periodDropdownElement();
    	click_button("Add Period",addPeriod);
    	
    	if(IsDisplayed("Add Effective Period", add_effective_Period))
    	{
    		oReport.AddStepResult("Add Period icon", " Clicked on 'Add period' button and verified that 'Add Effective period window' is displayed", "PASS");
    		
    		oParameters.SetParameters("AddedEffectiveDate", get_next_date(oParameters.GetParameters("TerminationDate"), 2));
    		enter_text_value("ADD Effective Date",add_effective_date,oParameters.GetParameters("AddedEffectiveDate"));
    		
    		click_button("Add Effective save button",EditEffective_Savebutton);
    		waitFor(openedpage,"Respective opened code set");
    		
    		if(get_text(addedPeriod).contains(oParameters.GetParameters("AddedEffectiveDate")))
    		{
    			waitFor(addedPeriod,"");
    			oReport.AddStepResult("Add Effective Period Save Button", " In 'ADD Effective Period' Window clicked on save button and verified that new period is added", "PASS");
    		}
    		else
    			oReport.AddStepResult("Add Effective Period Save Button", " In 'ADD Effective Period' Window clicked on save button and verified that new period is not added", "FAIL");
    	}
    	else
    		oReport.AddStepResult("Add Period icon", " Clicked on 'Add period' button and verified that 'Add Effective period window' is not displayed", "FAIL");
    }
    
    
    By hover_field=By.xpath("//ul[@class='dropdown-menu period-menu mar-l-15']/li[2]/a");
    
    By delete_period=By.xpath("//div[contains(text(),'Delete Period')]");
    
    By delete=By.xpath("//input[@value='Delete']");
    
    By TotalPeriod=By.xpath("//ul[@class='dropdown-menu period-menu mar-l-15']/li/a");
    
    By CannotDeleteActivePeriod = By.xpath("//div[@id='dialog']//div[contains(text(),'Cannot Delete Active Period')]");
    
    By CannotDeleteActivePeriodOKButton = By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");
    
    
    //This Method is used to delete a period.
    public void deletePeriod()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//This method is used to click on period drop down.
    	periodDropdownElement();
    	
    	oParameters.SetParameters("TotalPeriodBeforeDeleting", String.valueOf(get_table_row_count(TotalPeriod)));
    	mouse_hover("HoverOver Feild", hover_field);
    	dclick_button("Hover on delete icon", add_period_deletebutton);
    	
    	if(IsDisplayed("Delete Period", delete_period))
    	{	
    		oReport.AddStepResult("Delete Period Icon", "Clicked on period delete icon and verified that 'Delete Period pop up' is displayed", "PASS");
    		click_button("Delete period",delete);
    		
    		periodDropdownElement();
    		oParameters.SetParameters("TotalPeriodAfterDeleting", String.valueOf(get_table_row_count(TotalPeriod)));
    		
    		if(Integer.valueOf(oParameters.GetParameters("TotalPeriodBeforeDeleting")) > Integer.valueOf((oParameters.GetParameters("TotalPeriodAfterDeleting"))))
    			oReport.AddStepResult("Delete Period Button", "On 'Delete Period' popup clicked on Delete icon and verified that respective period is deleted", "PASS");
    		else
    			oReport.AddStepResult("Delete Period Button", "On 'Delete Period' popup clicked on Delete icon and verified that respective period is not deleted", "FAIL");
    	}
    	else if(IsDisplayed("Cannot Delete Active Period", CannotDeleteActivePeriod))
    	{
    		oReport.AddStepResult("Delete Period Icon", "Clicked on period delete icon and 'Cannot Delete Active Period' window is displayed, Since Active and Inactive status functionality is removed we cant change status to inactive, hence failed", "FAIL");
    		
    		click_button("Cannot Delete Active Period Ok Button", CannotDeleteActivePeriodOKButton);
    	
    		/*periodDropdownElement();
    		mouse_hover("HoverOver Feild", hover_field);
    		click_button("Period Edit icon", edit_period_editbutton);
    		click_button("Status Changing icon in 'Edit Effective Period' ", inactive);
    		
    		click_button("Edit Effective Period Save Button", EditEffective_Savebutton);*/
    			
    	}
    	else
    		oReport.AddStepResult("Delete Period Icon", "Clicked on period delete icon and verified that 'Delete Period pop up' is not displayed", "FAIL");
    }
    
    
    public By Add_Hcpcs_cpt=By.xpath("//ul[contains(@class,'data-list drillable-list portal')]//li[@class='data-row ng-binding']");
    
    By addCodeSetTab=By.xpath("//div[@title='Add a Code Set']");
    
    public By codesetname=By.xpath("//input[@id='codeSetName']");
    
    By active_radio_button=By.xpath("//input[@id='periodActiveAddCode']");
    
    By addCostSetCheckbox=By.xpath("//div[@class='row mar-l-0 ng-scope']//input[@id='copyClosed']");
    
    By SearchFeildCodeSet=By.xpath("//div[@model='copyPeriod.codeSet.name']//input[@id='codeSetSearch']");
    
    //This Method is used to click on Add Hcpcs icon.
    public void addCodeSetButtonIcon()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	click_button("Add ICD code set",Add_Hcpcs_cpt);
   	 
    	if(IsElementDisplayed("Add CodeSet Tab is Displayed", addCodeSetTab))
    		oReport.AddStepResult("Add HCPCS_CPT link", " Clicked on 'Add Code Set Icon' and verified that 'Add a code Set window' is displayed", "PASS");
    	else
    		oReport.AddStepResult("Add HCPCS_CPT link", " Clicked on 'Add Code Set Icon' and verified that 'Add a code Set window' is not displayed", "FAIL");
    }
    
    
    By terminationDateCodeSet=By.xpath("//div[@form-id='codeSetFormModel.formId']//input[@id='terminationDateCodeSet']");
    
    
    //This Method is used to Add a HCPCS/CPT Code Set.
    public void addNewCodeSet()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	enter_text_value("Code set Name",codesetname,oParameters.GetParameters("CodeName")+System.currentTimeMillis());
    	oParameters.SetParameters("FirstCodeSetName", convertToWebElement(codesetname).getAttribute("value"));
    	
    	enter_text_value("Effective date",add_effective,oParameters.GetParameters("New_effectivedate"));
    	performKeyBoardAction("ENTER");
    	
    	facilityName("CODES", "Apollo srn facility");
    	
    	if(IsElementDisplayed("ADD a code set check box", addCostSetCheckbox))
    	{
    		oReport.AddStepResult("ADD a code set window check box", "On 'ADD a Code Set' window Filled all the mandatory feilds ", "INFO");
    		click_button("Check all user job",addCostSetCheckbox);
    		
    		if(IsElementEnabled("SearchFeildCodeSet", SearchFeildCodeSet))
    		{
    			oReport.AddStepResult("Add code set window check box", "On 'ADD a Code Set' window Selected 'Copy details from existing code set' checkbox and verified that 'Select search bar'is displayed", "PASS");
    			click_button("Select Code Set Feild",SearchFeildCodeSet);
    			performKeyBoardAction("ENTER");
    			click_button("First code",SelectCodeSet);
    			click_button("Save button",addCodeSet_SaveButton);
    			fixed_wait_time(2);
    			
    			if(oParameters.GetParameters("FirstCodeSetName").equalsIgnoreCase(get_text(openedpage)))
    			{
    				fixed_wait_time(3);
    				oReport.AddStepResult("Add code set window Save button", "On 'ADD a Code Set' window Filled all the mandatory Filled and Clicked on save button and verified that new code set is added and displayed with same code set name", "PASS");
    			}
    			else
	   				oReport.AddStepResult("Add code set window Save button", "On 'ADD a Code Set' window Filled all the mandatory Filled and Clicked on save button and verified that new code set is not added ", "FAIL");
			}
    		else
    			oReport.AddStepResult("Add code set window check box", "On 'ADD a Code Set' window Selected 'Copy details from existing code set' checkbox and verified that 'Select search bar'is not displayed", "FAIL");
    	}
    	else
    		oReport.AddStepResult("ADD a code set check box", "On 'ADD a Code Set' window Filled all the mandatory feilds ", "INFO");
    }
    	  
    
    By checkbox=By.xpath("//form[@id='addCodeSetPeriod']//input[@id='copyClosed']");
    
    By select_code=By.xpath("//input[@id='codeSetSearch']");
    
    By cardiac_Cath=By.xpath("//ul[@id='-list']//li//a[not(text())]//b[contains(.,'Cardiac')]");
    
    By ErrorAddingPeriod=By.xpath("//div[@id='dialog']//div[contains(text(),'Error Adding Period')]");
   
   	By ErrorAddingPeriodOkButton=By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");
   
   	By AddEffectivePeriodCancelButton=By.xpath("//div[@id='addCodeSetPeriodModal']//input[@value='Cancel']");
    
   	
   	//This Method is to click on period dropdown and to ceate a period.
    public void addNewEffectivePeriodValidation()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	click_button("procedure dropdown",period_dropdown);
   		click_button("Add period",add_period);
   		
   		oParameters.SetParameters("Add_NewEffectiveDate", get_next_date(oParameters.GetParameters("add_effectivedate"),2));
   		
   		//javaScriptExecutor("Add Effective Date",add_effective_date);
   		//click_button("Add Effective Date",add_effective_date);
   		enter_text_value("Effective date",add_effective_date,oParameters.GetParameters("Add_NewEffectiveDate"));
   		performKeyBoardAction("ENTER");
   		
   		oParameters.SetParameters("effectiveDatePeriod", get_field_value("get Effective Date", add_effective_date));
    	oParameters.SetParameters("newEffectiveDatePeriod", get_next_date(oParameters.GetParameters("effectiveDatePeriod"), 2));
    	
    	enter_text_value("Termination Date Code Set", terminationPeriodDate, oParameters.GetParameters("newEffectiveDatePeriod"));
    	performKeyBoardAction("ENTER");
   		
    	click_button("Check box",checkbox);
   		waitFor(select_code,"Select Code Set in 'Add Effective Period'");
   		
   		oParameters.SetParameters("selectCodeSet",driver.findElement(select_code).getAttribute("value"));
   			
   		if(oParameters.GetParameters("selectCodeSet").isEmpty())
   			oReport.AddStepResult("Add period check box", " On 'Add Effective period' window filled all mandatory feilds and selected 'Copy Details from Existing Code Set'check box and verified that code set name is not displayed in 'select Code Set' feild ", "FAIL");
   		else
   		{
   			oReport.AddStepResult("Add period check box", " On 'Add Effective period' window filled all mandatory feilds and selected 'Copy Details from Existing Code Set'check box and verified that code set name is displayed in 'select Code Set' feild ", "PASS");
   			fixed_wait_time(5);
   			click_button("Add Effective Save_button ",EditEffective_Savebutton);
   			
   			
   			if(IsDisplayed("Error Adding Period", ErrorAddingPeriod))
   			{	
   				oReport.AddStepResult("Error Adding Period", "On 'Add Effective period' window verified that 'select code set' search box contains code set value hence clicked on save button and also verified that 'Error Adding Period' Message is displayed ", "PASS");
   				click_button("Error Adding period Ok Button", ErrorAddingPeriodOkButton);	
   				
   				dclick_button("Edit Effective Cancel button", AddEffectivePeriodCancelButton);
   				click_button("Discard Button", discard);
   			}
   		}
   	}
    
    
    public By add_effective=By.xpath("//div[@form-id='codeSetFormModel.formId']//input[@id='effectiveDateCodeSet']");
    
    By downloadlink=By.xpath("//form[@id='testFileIframe']//a[text()='Download a sample .XLSX']");
    
    public By browsebutton=By.xpath("//input[@id='codeSetEntryIframe']");
    
   
    public void addNewCodeSet1(String i,String Filename)
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	addCodeSetButtonIcon();
    	
    	enter_text_value("Code set Name",codesetname,oParameters.GetParameters("CodeName"+i)+System.currentTimeMillis());
    	oParameters.SetParameters("SecondCodeSetName", convertToWebElement(codesetname).getAttribute("value"));	
    	
    	facilityName("CODES", "Apollo srn facility");
    	
    	click_button("Add Effective",add_effective);
    	
    	enter_text_value("Effective date",add_effective,oParameters.GetParameters("New_effectivedate"));
    	performKeyBoardAction("ENTER");
    	fixed_wait_time(1);
    		
    	driver.switchTo().frame("codeSetImportIframe");
    	click_button("download a sampe",downloadlink);
    	click_button("browse button",browsebutton);
    	
    	
    	oReport.AddStepResult("Browse Button","Clicked on Browse Button", "SCREENSHOT");
    	String path = oParameters.GetParameters("AUTOIT_FILES_PATH"+i)+Filename;
    	
    	try 
    	{
    		fixed_wait_time(5);
    		Runtime.getRuntime().exec(path);
    		
    		/*//Robert Class
    		StringSelection sel = new StringSelection("C:\\CCM\\SupportingFiles\\Valid_Code_Set.xlsx");
   		 
 		   	// Copy to clipboard
    		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel,null);
    		
    		FileHandled();
    	*/	
    		driver.switchTo().defaultContent();
    		
    		oReport.AddStepResult("Add Code Set","Add Code Set Window details entered", "SCREENSHOT");
    		
    		click_button("Save button",addCodeSet_SaveButton);
    		fixed_wait_time(2);
    		refresh_page();
    		
    		fixed_wait_time(3);
    	
    		waitFor(procedureFirstelement, "procedureFirstelement");
    		
    		oReport.AddStepResult("Add Code Set Icon", " Clicked on 'Add Code Set Icon' and Filled all mandatory fields", "SCREENSHOT");
    		
    		fixed_wait_time(10);
    		if(IsDisplayed("Procedure First Element Uploaded", procedureFirstelement))
    			oReport.AddStepResult("Procedure First Element Uploaded", "File gets uploaded and code set added Sucessfully", "PASS");
		    else
		    	oReport.AddStepResult("Procedure First Element Uploaded", "File Not Uploaded", "FAIL");
	    }
    	catch (Exception e) 
    	{
    		e.printStackTrace();
		}
    }
    		
    By SelectCodeSet=By.xpath("//div[@form-id='codeSetFormModel.formId']//div[@style='position: relative;']//ul[@id='-list']/li[1]");
    
    public By openedpage=By.xpath("//div[contains(@class,'col-lg-6 col-md-6 col-sm-6 col-xs-12 large-header pad-l-0 ng-binding')]");
   
    public By openedpageDC=By.xpath("//div[@id='codeView']//div[@class='col-lg-9 col-md-9 col-sm-9 col-xs-12 large-document-header hide-overflow pad-l-0 ng-binding']");
    
    By addfilter=By.xpath("//div[@id='filter-ratesheets']//div[@class='multi-filter-container form-control']//li[@class='pull-left add-filter']");
    
    By addfilter_cancel=By.xpath("//ul[@class='pull-left']//li//a[contains(@class,'inverse-min link-btn hand-cursor ng-isolate-scope')]//i[@class='left fa fa-times-circle']");
    												
    By deletecodeset=By.xpath("//div[@id='codeView']//a[@text='Delete']//i[@class='left fa fa-minus-square']");
    
    By procedurefeild=By.xpath("//div[@id='filter-ratesheets']//li[@class='multi-filter-item pull-left']//input[@class='text']");
    
   
    //This Method is used to check a filter.
    public void filterCodeSet()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//click_button("First Code Set", firstElement);
    	waitFor(procedureFirstelement,"First Procedure Code Set");
    	oParameters.SetParameters("filter_code", get_text(procedureFirstelement));	
 		
 		dclick_button("AddFilter", addfilter);
    	
    	enter_text_value("Add Filter",procedurefeild,oParameters.GetParameters("filter_code"));
 		performKeyBoardAction("ENTER");
 		fixed_wait_time(2);
 			
 		if(IsDisplayed("Procedure First element", procedureFirstelement))
 		{	
 			waitFor(openedpage,"Respective opened Code Set page");
 			oReport.AddStepResult("Filtered procedure"," Filtered procedure based on first procedure name and verified that only procedure is displayed", "PASS");
 			dclick_button("AddFilter_Cancel", addfilter_cancel);
 				
   			if(IsElementDisplayed("Respective Code Set Page", openedpage))
   				oReport.AddStepResult("Add filter cancel icon", "Clicked on filter close icon and verified that all procedure is displayed", "PASS");
    		else
    			oReport.AddStepResult("Add filter cancel icon", "Clicked on filtered close icon and verified that all procedure is not displayed", "FAIL");
    	}
 		else
 			oReport.AddStepResult("Filtered procedure"," Filtered procedure based on first procedure name and verified that only procedure is not displayed", "FAIL");
 	}
   
	
    By import_link=By.xpath("//div[@class='pull-right pad-r-15']//a[@title='Import']");
    
    By importcodeset=By.xpath("//div[@title='Import Code Set']");
    
    By browse=By.xpath("//input[@id='codeSetEntryIframepanel']");
    
    By Save=By.xpath(".//*[@id='importCodeSetEntriesId']");
    
    By Upload_code_set_ok=By.xpath("//div[@id='dialog_buttons']//input[@value='OK']");
    
    By downloadlinkimport=By.xpath("//a[@id='hcpsCodeSetSampleFile']");
    
    By HcpcsNoOfCodes=By.xpath("//div[@id='codeView']//div[@class='document-title-bar pad-l-10']//div[contains(@class,'col-lg-6 col-md-6 col-sm-6 col-xs-12 large-header text-right ng-binding')]");
    
    
    //This method is used to execute Codes(HCPCS/CPT) VR.
    public void CodesHcpcsVR()
    {
    	login("EDIT");
    	changePricingEngine();
    	xpathChange();
    	navigateCodesPlugin();
		codesDropdown();
		SelectCodeSetType(HCPCS_CPT,addHcpcsCpt);
		
		addNewCodeSet1("","Valid_Code_Set.exe");
		ImportIconValidation("C:\\CCM\\AutoIt\\Invalid_Code_Set.exe",4,"hcps");
		
		//selectCode();
		addCodeSetSaveScenario();
		AddCodeCancelScenario();
		
		editCodeSetButton();
		editCodeSetTab();
		editCodeSetCancelScenario();
		
		hoverOverCodePeriod();
		EditCodeSetValueWindowValidation();
		editCodeSetValue();
		EditCodeSetValueCancelScenario();
		deleteProcedureScenario();
		terminationDateValidation();
		addNewPeriod();
		periodDropdownElement();
		periodEditButton();
		periodEditSaveScenario();
		deletePeriod();
		
		addCodeSetButtonIcon();
		addNewCodeSet();
		addNewEffectivePeriodValidation();
		
		filterCodeSet();
		deleteCodeSet(oParameters.GetParameters("FirstCodeSetName"));
		deleteCodeSet(oParameters.GetParameters("EditingFirstCodeSetName"));
		logout();
    }
    
    
    
   //CCM_VR_Soarian_Codes_ICD
    
    public By ICD=By.xpath("//li[@id='i[__valueField]']/a[contains(.,'ICD')]");
    
    public By add_ICD=By.xpath("//a[contains(text(),'Add an ICD Code Set')]");
    
    
    //This method is used to enter a procedure code and performing a save operation.
    public void addCodeSaveScenarioICD()
   	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	enter_text_value("Procedure",procedure,get_random_number(2));
  	   	click_button("Save Button",addcodesetsave_button);
  	   
  	   	if(IsDisplayed("Error Adding Code Set Tab", ErrorAddingCodeSet))
  	   	{
  	   		click_button("Error Adding Code Set Tab Ok button", ErrorAddingCodeSetOKButton);
  	   		enter_text_value("Procedure",procedure,get_random_number(2));
  	   		click_button("Save Button",addcodesetsave_button);
    	}
  	   		
  	   	if(IsDisplayed("Respective page",openedpage))
  	   		oReport.AddStepResult("Add code ICD", "On 'Add Code Set Value' popup,clicked on save Button and verified that new code is added", "PASS");
  	   	else
  			oReport.AddStepResult("Add code ICD", "On 'Add Code Set Value' popup,clicked on save Button verified that new code is not added", "FAIL");
   	}
    
   
    //This method is used to perform cancel scenario
    public void addCodeCancelScenarioICD()
	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//This Method is used to click on Add Code
    	addCodeSetButton();
		
    	enter_text_value("procedure",procedure,get_random_number(2));
		
		//This Method is used to do Cancel Scenario for Add Code Tab.
		CodeSetCancelScenario(addcodesetcancelbutton);
	}
    
   
    By downloadlinkICD=By.xpath("//a[@id='icdCodeSetSampleFile']");
    
    //This method is used to click on import icon and validating respective file. 
    @SuppressWarnings("static-access")
	public void ImportIconValidation(String UploadFilePath ,int i2, String codes)
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method :" + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	dclick_button("Import Link", import_link);
    		
    	if(IsElementDisplayed("Import Code Set", importcodeset))
    	{
	    	oReport.AddStepResult("Import icon", " Clicked on import icon and verified that 'import code set window' is displayed", "PASS");
	    	
	    	By downloadlinkimport = By.xpath("//a[@id='"+codes+"CodeSetSampleFile']");
	    	click_button("Download link",downloadlinkimport);
    		
	    	int rowCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"),"xlsx"));
			
	    	if(rowCount >= 0)
				oReport.AddStepResult("'Import code set window' download link", "On 'Import Code Set' popup,clicked on downloadlink icon verified that code set sample file is downloaded successfully", "PASS");
			else
				oReport.AddStepResult("'Import code set window' download link", "On 'Import Code Set' popup,clicked on downloadlink icon verified that code set sample file is not downloaded successfully", "FAIL");
			
    		click_button("browse ",browse);
		   	fixed_wait_time(3);
		   	
		   	try 
    		{
    			Runtime.getRuntime().exec(UploadFilePath);
				click_button("File upload_save_button",Save);
	    		fixed_wait_time(2);
	    		
	    		if(IsDisplayed("unable to upload all code set", Upload_code_set_ok)) 
	    		{	
	    			oReport.AddStepResult("Import section", "On 'Import Code Set' popup,imported appropriate file, clicked on save button and verified that 'Unable to upload all the code set Codes' Tab is displayed", "PASS");
	    			click_button("click Ok Button",Upload_code_set_ok);
	    			 
	    			File f =  getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx");
	    			
	                int columnCount = oExcelData.getColumnCount(0, "C:\\CCM\\Downloads\\"+f.getName()+"", 1);   
	                
	                String [] columnDataArray = new String[columnCount];
	                
	                for(int i=0; i<columnCount;i++)
	                {                                   
	                      String cellData = ExcelData.getCellData(0, i, 2, "C:\\CCM\\Downloads\\"+f.getName()+"");
	                      columnDataArray[i]= cellData;
	                }
	                
	                String columnDataString = String.join(",", columnDataArray).replace(",", " ");

	    			if(columnDataString.contains("Error Message"))
	    				oReport.AddStepResult("Error File", "Downloaded Error file contains 'Error Message Column' ", "PASS");
	    			else
	    				oReport.AddStepResult("Error File", "Downloaded Error file doesn't contains 'Error Message Column' ", "WARNING");
	    		}
    		}
    		catch (Exception e) 
		   	{
    			e.printStackTrace();
		   	}
    	}
    	else
    		oReport.AddStepResult("Import icon", "Clicked on import icon and verified that 'import code set window' is not displayed", "FAIL");
   	}
    
   
    //This method is used to execute Codes(ICD) VR.
    public void codesIcdVr()
    {
    	login("EDIT");
    	changePricingEngine();
    	xpathChange();
    	navigateCodesPlugin();
		codesDropdown();
		SelectCodeSetType(ICD,add_ICD);
		
		addNewCodeSet1("","Valid_ICD_Code_Set.exe");
		ImportIconValidation("C:\\CCM\\AutoIt\\Invalid_ICD_Code_Set.exe",4,"icd");
		
		selectCode();
		addCodeSetButton();
		addCodeSaveScenarioICD();
		addCodeCancelScenarioICD();
		
		editCodeSetButton();
		editCodeSetTab();
		editCodeSetCancelScenario();
		
		hoverOverCodePeriod();
		EditCodeSetValueWindowValidation();
		editCodeSetValue();
		EditCodeSetValueCancelScenario();
		deleteProcedureScenario();
		terminationDateValidation();
		
		addNewPeriod();
		periodDropdownElement();
		periodEditButton();
		periodEditSaveScenario();
		deletePeriod();
		addCodeSetButtonIcon();
		addNewCodeSet();
		addNewEffectivePeriodValidation();
		
		filterCodeSet();
		deleteCodeSet(oParameters.GetParameters("FirstCodeSetName"));
		deleteCodeSet(oParameters.GetParameters("EditingFirstCodeSetName"));
		logout();
    }
    
      
    //CCM_VR_Soarian_Codes_Diagnosis.
    
    public By diagnosis=By.xpath("//li[@id='i[__valueField]']/a[contains(.,'Diagnosis')]");
    
    public By add_diagnosis_Code_set=By.xpath("//a[contains(text(),'Add a Diagnosis Code Set')]");
    
    public By code_diagnosis=By.xpath("//div[contains(@class,'form-group col-lg-12 col-md-12')]//input[@id='codeValue']");
    
    public By ErrorAddingCodeSet=By.xpath("//div[@id='dialog']//div[contains(text(),'Error Adding Code Set')]");
    
    public By ErrorAddingCodeSetOKButton=By.xpath("//div[@id='dialog_buttons']//input[@value='OK']");
    
    
    //This method is used to enter a procedure code and perform save operation.
    public void addCodeSetDiagnosisDRG()
   	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	click_button("procedure",code_diagnosis);
    	
    	if(oParameters.GetParameters("CodeSetDropDownName").equals("Diagnosis"))
   	   		enter_text_value("Procedure",code_diagnosis,"A0"+get_random_number(2)+".0");
    	else
    		enter_text_value("Procedure",code_diagnosis,get_random_number(2));
    		
    	click_button("Save Button",addcodesetsave_button);
   	   	
   	   	if(IsDisplayed("Error Adding Code Set Tab", ErrorAddingCodeSet))
   	   	{
   	   		click_button("Error Adding Code Set Tab Ok button", ErrorAddingCodeSetOKButton);
   	   		
   	   		if(oParameters.GetParameters("CodeSetDropDownName").equals("Diagnosis"))
   	   			enter_text_value("Procedure",code_diagnosis,"A0"+get_random_number(2)+".0");
   	   		else
	   			enter_text_value("Procedure",code_diagnosis,get_random_number(2));
	   			
   	   		click_button("Save Button",addcodesetsave_button);
   	   	}
   	   	
   	   	waitFor(openedpage,"Respective opened Code Set Page");
   	    
   	   	if(get_table_row_count(ProcedureCountBeforeadding) >= Integer.parseInt(oParameters.GetParameters("ProcedureCountBeforeAdding")))
			oReport.AddStepResult("Add 'daignosis Code set' save Button", "On 'Add Code Set Value' popup,clicked on save Button and verified that new code is added", "PASS");
		else
			oReport.AddStepResult("Add 'daignosis Code set' save Button", "On 'Add Code Set Value' popup,clicked on save Button and verified that new code is not added", "FAIL");
   	}
    
    
    By EditCodeSettab_termination=By.xpath("//div[@id='editContainer']//div[@form-id='codeEntriesFormModel.formId']//input[@id='codeEntriesTerminationDate']");
    
    By EditCodeSettab_savebutton=By.xpath("//div[@id='editCodeSetEntryPanel']//button[text()='Save']");
    
    By EditCodeSettab_effective=By.xpath("//div[@id='editContainer']//div[@form-id='codeEntriesFormModel.formId']//input[@id='codeEntriesEffectiveDate']");
    
    public  By ErrorCodeSet = By.xpath("//div[@id='dialog']//div[contains(text(),'Error adding code set')]");
    
    By EditCodeSetValue = By.xpath("//div[@id='editCodeSetEntryPanel']//div[@title='Edit Code Set Value']");
    
    
    //This method is used to Edit code set name and perform save operation.
    public void editCodeSetValueDiagnosisDRG()
	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	enter_text_value("Effective date",EditCodeSettab_effective,oParameters.GetParameters("New_effectivedate"));
		performKeyBoardAction("ENTER");

		enter_text_value("Termination Date",EditCodeSettab_termination,oParameters.GetParameters("EditTerminationDate_Diagnosis"));
		performKeyBoardAction("ENTER");
		clickSaveButton("Edit Code Set Value Save Button", "Edit Code Set Value", EditCodeSettab_savebutton, EditCodeSetValue);
		
		if(IsElementDisplayed("Respective page",openedpage))
		{
			waitFor(openedpage,"Respective Opened Code Set Page");
			oReport.AddStepResult("'Edit effective period window'save button", "On 'Edit a Code Set' popup,modified termination date, clicked on save button and verified that termination date is modified","PASS");
		}
		else
			oReport.AddStepResult("'Edit effective period window'save button", "On 'Edit a Code Set' popup,modified termination date, clicked on save button and verified that termination date is not modified","FAIL");
	}
    
    
    public void addCodeSetSaveScenarioDiagnosisDRG()
   	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//This method is used to click on add icon.
    	addCodeSetButton();
    	
    	//This method is used to add a code set value.
    	addCodeSetDiagnosisDRG();
   	}
    
    
    //This method is used to perform a cancel scenario.
    public void addCodeSetCancelScenarioDiagnosisDRG()
   	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//This method is used to click on add icon.
   		addCodeSetButton();
   		
   		enter_text_value("procedure",code_diagnosis,get_random_number(2));
   	    
   	    //This method is used to perfrom cancel scenario.
   	    CodeSetCancelScenario(addcodesetcancelbutton);
   	}
    
    
    By DeleteCodeSet_tab=By.xpath("//div[contains(text(),'Delete Code Set?')]");
    
    By TotalCodeset=By.xpath("//ul[contains(@class,'data-list drillable-list')]//li");
    
    By Reimbursement_DeleteCodeSetTab = By.xpath("//div[@id='dialog']//div[contains(text(),'Delete Code Set?')]");
    
    By DeleteCodeSetOkButton = By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");
    
    //This method is used to delete code set.
    public void deleteCodeSet(String DeleteCode)
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	oParameters.SetParameters("TotalCodeSetBeforDelete", String.valueOf(get_table_row_count(TotalCodeset)));
    	
    	enter_text_value("Search Code Set", SearchCodeSet,DeleteCode);
    	performKeyBoardAction("ENTER");
    	oReport.AddStepResult("Search Code Set", "Search Particular code set", "SCREENSHOT");
    	
    	click_button("Code Set", firstElement);
    	
    	if(get_text(firstElement).equalsIgnoreCase(get_text(OpenedCodeSet)))
    	{
    		dclick_button("DeleteCodeSet", deletecodeset);
    		
    		if(IsElementDisplayed("DeleteCodeSet Tab", DeleteCodeSet_tab))
        	{
        		oReport.AddStepResult("'Delete icon'"," Clicked on Delete icon and verified that delete Code Set? Tab is displayed", "PASS");
        		click_button("Cancel Button",dailogbox_cancelbutton);
        		
        		dclick_button("Delete Code Set", deletecodeset);
        		
        		fixed_wait_time(2);
        		click_button("Delete Button",delete);
        		
        		if(IsDisplayed("Delete Code Set Tab", Reimbursement_DeleteCodeSetTab))
        		{
        			oReport.AddStepResult("Reimbursement Delete Code Set Tab", "Clicked on delete icon and verified that Code Set is being used in reimbursement calculations and cannot be deleted is displayed", "WARNING");
        			click_button("Delete Code Set OK Button", DeleteCodeSetOkButton);
        		}
        		else
        		{
        			refresh_page();
            		fixed_wait_time(3);
            		waitFor(openedpage,"Respective opened Code Set page");
            		
            		fixed_wait_time(2);
            		
            		oParameters.SetParameters("TotalCodeSetAfterDelete", String.valueOf(get_table_row_count(TotalCodeset)));
            		
            		if(Integer.parseInt(oParameters.GetParameters("TotalCodeSetAfterDelete")) < Integer.parseInt(oParameters.GetParameters("TotalCodeSetBeforDelete")))
            		{
            			waitFor(openedpage,"Respective opened Code Set Page");
            			oReport.AddStepResult("Delete Icon", "Clicked on delete icon and verified that respective code set is deleted", "PASS");
            		}
            		else
            			oReport.AddStepResult("Delete Icon", "Clicked on delete icon and verified that respective code set is not deleted", "FAIL");
        		}
        	}	
        	else
            	oReport.AddStepResult("'Delete icon'"," Clicked on Delete icon and verified that delete Code Set? Tab is not displayed", "FAIL");
    	}
     }
    
    
    
    By SearchCodeSet=By.xpath(".//*[@id='codeView']//input[contains(@class,'search-text-box input-sm')]");
    
    By export=By.xpath("//div[@class='pull-right pad-r-15']//a[@title='Export']");
    
    
    @SuppressWarnings("static-access")
	public void clearSearchBoxValidation()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	click_button("Search Code Set",SearchCodeSet);
    	enter_text_value("Seacrh Code Set", SearchCodeSet, " ");
    	performKeyBoardAction("ENTER");
    	
    	if(IsElementDisplayed("FirstElement", firstElement))
    		oReport.AddStepResult("Search Button", "Clicked on search button and verified that code set is displayed", "PASS");
    	else
    		oReport.AddStepResult("Search Button", "Clicked on search button and verified that code set is not displayed", "FAIL");
    }
    
    
    By downlodlinkdiagnosis=By.xpath("//a[@id='drgdxCodeSetSampleFile']");
    
    
    //This method is used to execute Codes(Daignosis) VR.
    public void codesDaignosisVr() 
    {
    	login("EDIT");
    	changePricingEngine();
    	xpathChange();
    	navigateCodesPlugin();
		codesDropdown();
		SelectCodeSetType(diagnosis,add_diagnosis_Code_set);
		
		addNewCodeSet1("","Valid_Daignosis_Code_Set.exe");
		ImportIconValidation("C:\\CCM\\AutoIt\\Invalid_Diagnosis_Code_Set.exe", 3,"drgdx");
		
		selectCode();
		addCodeSetSaveScenarioDiagnosisDRG();
		addCodeSetCancelScenarioDiagnosisDRG();
		
		editCodeSetButton();
		editCodeSetTab();
		editCodeSetCancelScenario();
	
		EditCodeSetValueWindowValidation();
		editCodeSetValueDiagnosisDRG();
		deleteProcedureScenario();
		terminationDateValidation();
		
		addNewPeriod();
		periodDropdownElement();
		periodEditButton();
		periodEditSaveScenario();
		//daignosisPeriodStatusChange(active,"Active");
		//daignosisPeriodStatusChange(inactive,"In-Active");
		deletePeriod();
		addCodeSetButtonIcon();
		addNewCodeSet();
		addNewEffectivePeriodValidation();
		
		SearchCodesSet();
		paginationValidation(nextbutton,lastpage,prevpage,procedureFirstelement);
		
		filterCodeSet();
		filterDiagnosisProcedureCodes(first_effective_date,effective_filter,datefilter_feild);
		filterDiagnosisProcedureCodes(first_terminationdate,termination_filter,datefilter_termination);
		clearSearchBoxValidation();
		deleteCodeSet(oParameters.GetParameters("FirstCodeSetName"));
		deleteCodeSet(oParameters.GetParameters("EditingFirstCodeSetName"));
		logout();
    }
    
    
    //CCM_VR_Soarian_Codes_DRG.
    
    
    public By DRG=By.xpath("//div[@id='codeView']//li[@id='i[__valueField]']//a[contains(.,'DRG')]");
    
    public By addDRGCodeSet=By.xpath("//a[contains(text(),'Add a DRG Code Set')]");
    
    
    //This method is used to export DRG File and validating.
    @SuppressWarnings("static-access")
	public void ExportIconValidation() 
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	dclick_button("Export Button", export);
    	fixed_wait_time(2);
    	
    	try 
    	{
    		Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Export_CancelButton.exe");
			int rowCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"),"xlsx"));
	    	
			if(rowCount >=0)
				oReport.AddStepResult("'Export icon'", " clicked on export icon and verified that procedure codes are downloaded into excel", "PASS");
			else
				oReport.AddStepResult("'Export icon'", " clicked on export icon and verified that procedure codes are not downloaded into excel", "FAIL");
    	}	
		catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    
    //This method is used to click on period dropdown and Edit period and click on inactive.
    public void daignosisPeriodStatusChange(By elemdesc,String status)
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//This Method is used to click on period dropdown.
    	periodDropdownElement();
    	
    	mouse_hover("Hover Add Period", hover_AddPeriod);
    	
    	if(IsElementDisplayed("Add_period_EditButton", edit_period_editbutton) && IsElementDisplayed("Add_period_deletebutton", add_period_deletebutton))
	    {
	    	oReport.AddStepResult("Hover over period"," Hovered over first period and verified that edit and delete periods buttons are displayed", "PASS");
	    	click_button("Period edit button",edit_period_editbutton);
	    	waitFor(elemdesc,"Active Radio Button in 'Edit Effective Period Tab'");
	    	
	    	//oReport.AddStepResult("Status Change", "Period status changed to "+status+"", "INFO");
	    	//click_button("Period Status Icon in 'Edit Effective Period' ",elemdesc);
	    	//oReport.AddStepResult("Status Change", "Period status changed to "+status+"", "INFO");
	    	
	    	click_button("Edit Effective Save Buttton",EditEffective_Savebutton);
	    	
			if(IsElementDisplayed("period dropdown", period_dropdown))
				oReport.AddStepResult("'Edit period value tab' save button", "On 'Edit period value' window, Clicked on save button and verified that period status changed to "+status+" ", "PASS");
			else
				oReport.AddStepResult("'Edit period value tab' save button", "On 'Edit period value' window, Clicked on save button and verified that period status not changed to "+status+" ", "FAIL");
		}
	    else
	    	oReport.AddStepResult("Hover over period"," Hovered over period and verified that edit period button and delete period button is not displayed", "FAIL");
	}
    
    
    //This method is used to search particular code set.
    public void SearchCodesSet()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	enter_text_value("Effective date",SearchCodeSet,oParameters.GetParameters("CodeSet_50codes"));
    	fixed_wait_time(2);
    	
    	click_button("First Element",firstElement);
    	
    	if(IsElementDisplayed("Procedure First Element", procedureFirstelement))
    	{	
    		fixed_wait_time(2);
    		waitFor(openedpage,"Respective opened Code Set page");
    		oReport.AddStepResult("20 daignosis codes in each page"," Clicked on specific daignosis code set and selected the period which has more than 50 daignosis codes and verified 20 daignosis codes are displayed in each page", "PASS");
    	}
    	else
    		oReport.AddStepResult("20 daignosis codes in each page"," Clicked on specific daignosis code set  and selected the period which has more than 50 daignosis codes and verified 20 daignosis codes are not displayed in each page", "FAIL");
    }
    
     
    By nextbutton=By.xpath("//div[@id='codeValuescodeSetValueEntries']//ul[contains(@class,'pull-right rw-pager')]//li[text()='Next']");
    
    By lastpage=By.xpath("//div[@id='addEditEntryParent']//ul[@class='pull-right rw-pager ng-isolate-scope']//li[8]/a");
    
    By prevpage=By.xpath("//div[@id='addEditEntryParent']//ul[@class='pull-right rw-pager ng-isolate-scope']//li[1]");
 
    
    //This Method is used to page Navigation.
    public void paginationValidation(By oNextPage,By oLastpage,By oPrevpage,By elemdesc)
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
   	 	if(IsElementDisplayed("Next Button", oNextPage))
    	{
    		oReport.AddStepResult("Verified that next button"," Verified that on Codes set page, 'Next button' is present at the bottom of the page","PASS");
    		dclick_button("Next Button", oNextPage);
    		fixed_wait_time(2);
    		
    		if(IsElementDisplayed("Procedure First Element", elemdesc))
    		{
    			waitFor(oLastpage,"LAST Button");
    			oReport.AddStepResult("clicked on next button", "Clicked on 'Next button' and verified that diagnosis codes are displayed in that respective page", "PASS");
    			dclick_button("Last Page",oLastpage);
    			fixed_wait_time(2);
    		
    			if(IsElementDisplayed("Procedure First Element", elemdesc))
    			{
    				waitFor(oPrevpage,"Previous Page");
    				oReport.AddStepResult("clicked on last button", " Clicked on 'Last button' and verified that diagnosis codes are displayed in that respective page", "PASS");
    				dclick_button("Previous Page",oPrevpage);
    				fixed_wait_time(2);
    	       	
    				if(IsElementDisplayed("Procedure First Element", elemdesc))
    				{	
    					waitFor(elemdesc,"First Procedure Code Set Type");
    					oReport.AddStepResult("clicked on prev page button", " Clicked on 'Previous button' and verified that diagnosis codes are displayed in that respective page", "PASS");
    				}
    				else
    					oReport.AddStepResult("clicked on prev page button", "Clicked on 'Previous button' and verified that diagnosis codes are not displayed in that respective page", "FAIL");
    			}
    			else
    				oReport.AddStepResult("clicked on last button", "Clicked on 'Last button' and verified that diagnosis codes are not displayed in that respective page", "FAIL");
    		}
    		else
    			oReport.AddStepResult("clicked on next button", "Clicked on 'Next button' and verified that diagnosis codes are not displayed in that respective page", "FAIL");
        }
    	else
   			oReport.AddStepResult("Verified that next button"," Verified that on Codes set page, 'Next button' is not present at the bottom of the page","FAIL");
   	}
          
    
    By procedure_filter=By.xpath("//div[@id='filter-ratesheets']//ul/li//a[contains(@class,'filter-label hand-cursor')]");
    
    By effective_filter=By.xpath("//ul[@class='dropdown-menu ng-scope am-fade bottom-left']//a[text()='Effective Date']");
    
    By first_effective_date=By.xpath("//td[contains(.,'A00.0')]/following-sibling::td[1]");
    
    By datefilter_feild=By.xpath("//div[@id='filter-ratesheets']//li[@class='multi-filter-item pull-left']//input[@class='date dateModel ng-pristine ng-valid is-dateEntry is-datepick']"); 
    
    By termination_filter=By.xpath("//ul[@class='dropdown-menu ng-scope am-fade bottom-left']//a[text()='Termination Date']");
    
    By first_terminationdate=By.xpath("//td[contains(.,'A00.0')]/following-sibling::td[2]");
    
    By datefilter_termination=By.xpath("//div[@id='filter-ratesheets']//li[@class='multi-filter-item pull-left']//input[@class='date dateModel ng-valid is-dateEntry is-datepick ng-dirty']");
    
    
    //This method is used to filter effective date.
    public void filterDiagnosisProcedureCodes(By elemdesc, By optionalFilter,By dateFilterFeild)
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	waitFor(elemdesc,"First Effective");
    	oParameters.SetParameters("effectiveDateFilterCode", get_text(elemdesc));
    	fixed_wait_time(2);
    	
    	dclick_button("AddFilter", addfilter);
    	click_button("Procedure Filter",procedure_filter);
    	
    	click_button("Effective Date",optionalFilter);
    	enter_text_value("Date Filter", dateFilterFeild, oParameters.GetParameters("effectiveDateFilterCode"));
    	performKeyBoardAction("ENTER");
    	waitFor(procedureFirstelement,"First Procedure Code Set");
    	
       	if(oParameters.GetParameters("effectiveDateFilterCode").equals(get_text(elemdesc)))
       	{	
       		waitFor(openedpage,"Respective opened Code Set page");
       		oReport.AddStepResult("Filter based on the effective date"," Filtered based on effective date and verified that diagnosis procedure codes are displayed", "PASS");
       	}
    	else
    		oReport.AddStepResult("Filter based on the effective date"," Filtered based on effective date and verified that diagnosis procedure codes are not displayed", "FAIL");
    		
    	dclick_button("AddFilter_Cancel", addfilter_cancel);
    		
    	if(IsElementDisplayed("Respective page", openedpage))
    		oReport.AddStepResult("Click on addfilter cancel button", " Clicked on filter close button and verified that all procedure code are displayed", "PASS");
    	else
    		oReport.AddStepResult("Click on addfilter cancel button", " Clicked on filter close button and verified that all procedure code are not displayed", "FAIL");
    }
 		    
    
    //This method is used to execute Codes(Daignosis) VR.
    public void codesDrgVr()
    {
    	login("EDIT");
    	changePricingEngine();
    	xpathChange();
    	navigateCodesPlugin();
		codesDropdown();
		SelectCodeSetType(DRG,addDRGCodeSet);
		
		addNewCodeSet1("","Valid_DRG_Code_Set.exe");
		ImportIconValidation("C:\\CCM\\AutoIt\\Invalid_Diagnosis_Code_Set.exe", 3,"drgdx");
		
		selectCode();
		addCodeSetSaveScenarioDiagnosisDRG();
		addCodeSetCancelScenarioDiagnosisDRG();
		
		editCodeSetButton();
		editCodeSetTab();
		editCodeSetCancelScenario();
		EditCodeSetValueWindowValidation();
		editCodeSetValueDiagnosisDRG();
		
		deleteProcedureScenario();
		terminationDateValidation();
		addNewPeriod();
		periodDropdownElement();
		periodEditButton();
		periodEditSaveScenario();
		deletePeriod();
		addCodeSetButtonIcon();
		addNewCodeSet();
		addNewEffectivePeriodValidation();
		
		filterCodeSet();
		ExportIconValidation();
		deleteCodeSet(oParameters.GetParameters("FirstCodeSetName"));
		deleteCodeSet(oParameters.GetParameters("EditingFirstCodeSetName"));
		logout();
    }
    
    
    //CCM_VR_Soarian_Codes_View_Only_Permission
    
    
    By selectCodeSet = By.xpath("//div[@id='codeView']//div[@class='col-lg-10 col-md-10 col-sm-10 large-height pad-r-0']//div[@class='msg-blurb']//div[contains(@class,'second-line')]");

    //Checking For  Add-icon and Add-HCPCS/CPT link should be not there.
    public void addIconViewOnlyPermission()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	if(IsDisplayed("Click on Add icon",AddCodeSetValue))
    		oReport.AddStepResult("First Code set-HCPCS/CPT"," Clicked on first code set,code set page is displayed with 'Add'icon' or 'Add link' is displayed","FAIL");
  		else
  			oReport.AddStepResult("First Code set-HCPCS/CPT"," Clicked on first code set,code set page is not displayed with 'Add'icon' or 'Add link' is displayed","PASS");
    }
    
   
    //Checking for User must not be able to Edit or Save.
    public void editIconViewOnlyPermission()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	if(IsDisplayed("Edit button", Editbutton) && IsDisplayed("Save Button", addCodeSet_SaveButton))
    		oReport.AddStepResult("First Code set-HCPCS/CPT"," Clicked on first code set,code set page is displayed with 'Edit' icon", "FAIL");
    	else
    		oReport.AddStepResult("First Code set-HCPCS/CPT"," Clicked on first code set,code set page is displayed without 'Edit' icon", "PASS");
    }
    
    
    //Checking For Delete icon should not be present.
    public void hoverOverCodeViewOnlyPermission()
    {	
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	List<WebElement> totalCodeSet=convertToWebElements(TotalCodeset);
    	
    	for(int i=2;i<=totalCodeSet.size();i++)
    	{
    		By TotalCodeset=By.xpath("//ul[contains(@class,'data-list drillable-list')]//li["+i+"]");
    		click_button("procedure code",TotalCodeset);
    		
    		if(IsDisplayed("Procedure First", procedureFirstelement))
    		{
    			waitFor(procedureFirstelement,"First Procedure Code Set");
    			oReport.AddStepResult("First Code Set", "Clicked on First code set and verified that procedure code is displayed", "PASS");
    			dclick_button("Procedure First Element", procedureFirstelement);
    			
    			if(IsDisplayed("Procedure First Element_Delete", procedureFirstelementDelete))
    				oReport.AddStepResult("Hover over First procedure"," Hovered over procedure first element and verified that delete icon is displayed","FAIL");
    			else
    				oReport.AddStepResult("Hover over First procedure"," Hovered over procedure first element and verified that delete icon is not displayed","PASS");
    			break;
    		}
    		else
    			oReport.AddStepResult("First Code Set", "Clicked on First code set and verified that procedure code is not displayed", "FAIL");
    	}
    }	
    
    
    By Edit_code_Close_button=By.xpath("//div[@id='editCodeSetEntryPanel']//div[contains(@class,'secondary-title-bar panel-header')]//button");
   
    By editProcedureName=By.xpath("//div[@id='editContainer']//input[@id='procedure']");
    
    By editCodeSetTab=By.xpath("//div[@id='editCodeSetEntryPanel']//div[@title='Edit Code Set Value']");
    
    By CodesCancelIcon=By.xpath("//div[@id='codeValuescodeSetValueEntries']//a[@title='Clear All Filters']/i[@class='left fa-lg fa fa-times-circle']");
    
    
    //Checking procedure code tab details.
    public void editCodeViewOnlyPermission()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	oParameters.SetParameters("procedure", get_text(procedureFirstelement));
    	click_button("First procedure",procedureFirstelement);
    	
    	if(IsElementDisplayed("Edit Code Set Tab", editCodeSetTab))
    	{
	    	oReport.AddStepResult("First Procedure code", " Clicked on procedure first element and verified that 'Edit Code Set Value' window is displayed", "PASS");
	    	dclick_button("EditCode Close Button",Edit_code_Close_button);
	    		
	    	if(IsElementDisplayed("Respective page", openedpage))
	    		oReport.AddStepResult("'Edit code set value Tab' close icon"," On 'Edit code set value ' window, Clicked on close icon and verified that edit code set window is closed", "PASS");
	    	else
	    		oReport.AddStepResult("'Edit code set value Tab' close icon"," On 'Edit code set value ' window, Clicked on close icon and verified that edit code set window is not closed", "FAIL");
	    	waitFor(period_dropdown,"Period Drop Down");
    	}
    	else
    		oReport.AddStepResult("First Procedure code", " Clicked on procedure first element and verified that 'Edit Code Set Value' window is not displayed", "FAIL");
    }
    
    public void periodDropdown_ViewOnly()
    {
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(1);
		click_button("period drop down",period_dropdown);
	}
    
    By hoverPeriodViewOnlyPermission=By.xpath("//div[@id='period-selector']//ul/li[2]/a");
    
    //Checking for Edit and Delete icon is not displayed
    public void hoverOverPeriodViewOnlyPermission()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	mouse_hover("First Period", hoverPeriodViewOnlyPermission);
    	
    	if(IsDisplayed("Procedure First Element_Delete", procedureFirstelementDelete))
    		oReport.AddStepResult("Hover over period"," Hovered over first period and verified that 'Delete' and 'Edit' icon is displayed", "FAIL");
		else
			oReport.AddStepResult("Hover over period"," Hovered over first period and verified that 'Delete' and 'Edit' icon is not displayed", "PASS");
	}
    
    
    //This method is used to click on Period.
    public void selectPeriodViewOnlyPermission()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	click_button("First period",hoverPeriodViewOnlyPermission);
    	
    	if(IsElementDisplayed("Opened Page", openedpage))
    		oReport.AddStepResult("Selected first period" ," Clicked on first period and verified that respective period code page is displayed", "PASS");
    	else
    		oReport.AddStepResult("Selected first period" ," Clicked on first period and verified that respective period code page is not displayed", "FAIL");
    }
    
   
    By codeSetTypeDropdown = By.xpath("//div[@id='codeView']//div[@id='styledDropdown']/a[@class='btn btn-light view-bg-text']");
    
  
    //This Method is used to select a particular code set type from dropdown.
    public void selectCodeSetTypeFromDropdown(By elemdesc,String CodeSetType)
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	click_button("Select a Code Set Dropdown",codeSetTypeDropdown);
    	click_button(" "+CodeSetType+" ",elemdesc);
    	
    	if(IsDisplayed("select a code set", selectCodeSet))
			oReport.AddStepResult("Selected ICD from Dropdown", "Clicked on "+CodeSetType+" from the 'Select Code Set type' Drop down and verified that respective code set page is displayed", "PASS");
		else
			oReport.AddStepResult("Selected ICD from Dropdown", "Clicked on "+CodeSetType+" from the 'Select Code Set type' Drop down and verified that respective code set page is not displayed", "FAIL");
	}
    
    
    public void viewOnlyPermission()
    {
    	addIconViewOnlyPermission();
		editIconViewOnlyPermission();
		hoverOverCodeViewOnlyPermission();
		EditCodeSetValueWindowValidation();
		editCodeViewOnlyPermission();
		periodDropdown_ViewOnly();
		hoverOverPeriodViewOnlyPermission();
		selectPeriodViewOnlyPermission();
		filterCodeSet();
    }
    
    public void codesviewOnlyPermission()
    {
    	login("VIEW");
    	changePricingEngine();
    	xpathChange();
    	navigate_to("Codes Plugin", "Select a Report to begin", codes, SelectCodeSetType);
    	
    	SelectCodeSetType();
    	
    }
    
    By CodeSetTypes = By.xpath("//div[@id='codeView']//div[@id='styledDropdown']//ul[@class='dropdown-menu']//li//a");
    
    public void SelectCodeSetType()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	int codesTypeCount = convertToWebElements(CodeSetTypes).size();
    	
    	for(int i=1; i<codesTypeCount; i++)
    	{
    		click_button("Codes SetType DropDown", codeSetTypeDropdown);
    			
    		By CodeType = By.xpath("//div[@id='codeView']//div[@id='styledDropdown']//ul[@class='dropdown-menu']//li["+(i+1)+"]//a");
    		click_button(get_text(CodeType), CodeType);
    		
    		selectCode();
    		viewOnlyPermission();
    		ExportIconValidation();
    	}
    	
    	logout();
    }


	public void codesviewOnlyPermission1() 
	{
		
	}
}

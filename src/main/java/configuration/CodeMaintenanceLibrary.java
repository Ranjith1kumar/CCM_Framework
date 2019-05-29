package configuration;

import java.io.*;
import java.util.List;
import org.openqa.selenium.*;
import contractManagement.CodesLibrary;
import libraries.*;

/**
 * @author C16137
 *
 */

public class CodeMaintenanceLibrary extends CCMLibrary
{
	ExcelData oExcelData = new ExcelData(oParameters);
	
	CodesLibrary ocodesLibrary = new CodesLibrary(Browser.driver, Browser.oReport, oParameters);
	
	UserGroupsLibrary ouserGroups = new UserGroupsLibrary(Browser.driver, Browser.oReport, oParameters);
	
	public CodeMaintenanceLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}

	By codeMaintenance=By.xpath("//div[@id='portal-main']//a[text()='Code Maintenance']");
	
	By codeMaintenanceSelectCode=By.xpath("//div[@id='codeMaintenanceView']//div[@class='second-line ng-scope ng-binding']");
	
	//This method is used to select a code Maintenance under configuration.
	public void navigateToCodeMaintenance()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		navigate_to("Code Maintenance", "Select a code or Schedule Type", codeMaintenance, codeMaintenanceSelectCode);
	}
	
	By SelectCodeDropDown=By.xpath("//div[@id='codeMaintenanceView']//div[@class='input-group input-group-sm search-text-box']//a//span[1]");
	
	By DropdownList=By.xpath("//div[@id='codeMaintenanceView']//div[@class='col-lg-6 col-md-8 col-sm-10 col-xs-10 pad-l-0 pad-r-0']//a");
	
	public By CodeMaintenance_HCPCS_CPT=By.xpath("//div[@id='codeMaintenanceView']//a[contains(.,'HCPCS/CPT')]");
	
	public By CodeMaintenance_ICD=By.xpath("//div[@id='codeMaintenanceView']//a[contains(.,'ICD')]");
	
	public By CodeMaintenance_Modifiers=By.xpath("//div[@id='codeMaintenanceView']//a[contains(.,'Modifiers')]");
	
	By dropDown_HCPCS_CPT = By.xpath("//div[@id='codeMaintenanceView']//a[contains(.,'HCPCS/CPT')]//span[1]");
	
	By dropDown_ICD = By.xpath("//div[@id='codeMaintenanceView']//a[contains(.,'ICD')]//span[1]");
	
	By dropDown_Modifiers = By.xpath("//div[@id='codeMaintenanceView']//a[contains(.,'Modifiers')]//span[1]");
	
	By importCodes=By.xpath("//div[@id='codeMaintenanceView']//a[text()='Import Codes']");
	
	
	//This method is used to click on Select Code or Schedule Type Dropdown().
	public void selectCodeScheduleTypeDropDown(By elemdesc)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Select Code/Schedule Type", SelectCodeDropDown);
		oParameters.SetParameters("ScheduleType", get_text(elemdesc));
		
		List<WebElement> DropdownValidation=convertToWebElements(DropdownList);
		
		for(int i=0;i<=DropdownValidation.size();i++)
		{
			if(get_text(elemdesc).equals(oParameters.GetParameters("ScheduleType")))
			{
				click_button("Select Schedule Type", elemdesc);
				break;
			}
			else
				System.out.println("Searching");
		}
		if(IsElementDisplayed("Import Codes link", importCodes))
			oReport.AddStepResult("Import Codes Link", "Clicked on 'Select code' dropdown and selected schedule type and verified that respective schedule type is displayed ", "PASS");
		else
			oReport.AddStepResult("Import Codes Link", "Clicked on 'Select code' dropdown and selected schedule type and verified that respective schedule type is not displayed ", "FAIL");
	}

	
	By searchBox=By.xpath("//div[@id='codeMaintenanceView']//input[@class='search-text-box input-sm form-control pad-l-25 search-icon']");
	
	By respectiveCodes=By.xpath("//div[@id='codeMaintenanceView']/div/div[2]/div/div/div[2]/table/tbody/tr/td[1]");
	
	By descriptionPage=By.xpath("//div[@id='codeMaintenanceView']//div[@class='pad-l-10 col-md-6 col-md-5 col-sm-6 large-header ng-binding']");
	
	By NoResultsFound = By.xpath("//div[@id='codeMaintenanceView']//div[contains(text(),'No results found')]");
	
	
	//This method is used to search and select a code.
	public void searchAndSelect(String Codes,String feildtype,String classficationtype)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search Box", searchBox, Codes);
		oParameters.SetParameters("respectiveCodes", convertToWebElement(searchBox).getAttribute("value"));
		performKeyBoardAction("ENTER");
		
		fixed_wait_time(2);
		if(IsDisplayed("No Result Found", NoResultsFound))
		{
			if(get_text(dropDown_HCPCS_CPT).equals("HCPCS/CPT") || get_text(dropDown_ICD).equals("ICD"))
			{
				addCode(feildtype,classficationtype,Codes);
				SaveScenarioValidation();
			}
			else
				modifierAddCode(oParameters.GetParameters("MODIFIERS"),Codes);
			
		}
		else
		{
			List<WebElement> listCodes=convertToWebElements(respectiveCodes);
		
			for(int i=0;i<=listCodes.size();i++)
			{
				if(listCodes.get(i).getText().equals(oParameters.GetParameters("respectiveCodes")))
				{
					oReport.AddStepResult("Search and Select", "Searched particular code in a search box and verified that respective code id displayed ", "PASS");
					oParameters.SetParameters("MatchedCodes", listCodes.get(i).getText());
				
					By particularCode=By.xpath("//div[@id='codeMaintenanceView']//td[text()='"+ oParameters.GetParameters("MatchedCodes")+"']");
					click_button("respective codes",particularCode);
				break;
				}
			}
			if(IsElementDisplayed("Respective codes", respectiveCodes))
				oReport.AddStepResult("Respective Description Page", "Searched and selected particular code and verified that respective code is displayed in the list with description", "PASS");
			else
				oReport.AddStepResult("Respective Description Page", "Searched and selected particular code and verified that respective code is not displayed in the list with description", "FAIL");
		}
	}	
	
	By addCodes=By.xpath("//div[@id='codeMaintenanceView']//div[@class='list-header drillable-header ng-binding']");
	
	By addCodesTab=By.xpath("//div[@id='addCodeFormModal']//div[@title='Add Codes']");
	
	By AddCodesTabCancelButton=By.xpath("//input[@id='button.cancelId']");
	
	By Classficationtype=By.xpath("//select[@id='icdClassificationType']");
	
	By UnsavedChangesPopUP=By.xpath("//div[@id='dialog']//div[contains(text(),'You have unsaved changes')]");
	
	By Discard=By.xpath("//div[@class='form-button-panel']//input[@value='Discard']");
	
	By DiagnosisCategory=By.xpath("//select[@id='icdDiagnosisCategory']");
	
	
	//This method is used to click on add codes and fill all mandatory feilds and to perform cancel scenario.
	public void addCode(String scheduleType,String item,String codeset)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		dclick_button("Add codes link", addCodes);
		
		if(IsElementDisplayed("Add Codes Tab", addCodesTab))
		{
			oReport.AddStepResult("Add codes Tab", "Clicked on 'Add codes link' and verified that Add codes window is displayed ", "PASS");
			
			By codeFeild=By.xpath("//input[@id='"+scheduleType+"CodeSetName']");
			enter_text_value("Code Feild", codeFeild, codeset+System.currentTimeMillis());
			oParameters.SetParameters("NewProcedureCodeName", convertToWebElement(codeFeild).getAttribute("value"));
			
			By effectiveDate=By.xpath("//input[@id='"+scheduleType+"EffectiveDate']");
			enter_text_value("Effective Date", effectiveDate, oParameters.GetParameters("Effectivedate"));
			performKeyBoardAction("ENTER");
			
			By description=By.xpath("//input[@id='"+scheduleType+"Description']");
			enter_text_value("Description Feild", description, oParameters.GetParameters("DescriptionFeild"));
			
			if(IsDisplayed("Classification type", Classficationtype))
			{
				oReport.AddStepResult("classification type", "Selected ICD from drop down and clicked on add icd and verified that classification type feild is displayed", "PASS");
				selectOption(Classficationtype, "value", item);
			}
			else
				oReport.AddStepResult("classification type", "Selected HCPCS from drop down and clicked on add Hcpcs and verified that classification type feild is not displayed", "PASS");
		}
		else
			oReport.AddStepResult("Add codes Tab", "Clicked on 'Add codes link' and verified that Add codes window is not displayed ", "FAIL");
	}
		
	
	//This method is used to click on cancel buttton.	
	public void CancelButtonValidation()
	{	
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Cancel Button", AddCodesTabCancelButton);
			
		if(IsElementDisplayed("Unsaved Changes PopUp", UnsavedChangesPopUP))
		{
			oReport.AddStepResult("Unsaved Changes PopUp", "Clicked on Cancel Button and verified that Unsaved Changes PopUp is displayed", "PASS");
			click_button("Discard Button", Discard);
		}
		else
			oReport.AddStepResult("Unsaved Changes PopUp", "Clicked on Cancel Button and verified that Unsaved Changes PopUp is not displayed", "FAIL");
	}
	
	
	By editIcon=By.xpath("//div[@id='codeMaintenanceView']//a[@title='Edit Codes']");
	
	By editCodesTab=By.xpath("//div[@id='addCodeFormModal']//div[@title='Edit Codes']");
	
	By terminationDate=By.xpath("//input[@id='hcpcsTerminationDate']");
	
	By effectiveDate=By.xpath("//input[@id='hcpcsEffectiveDate']");
	
	By EditCodesSaveButton=By.xpath("//input[@id='button.saveId']");
	
	
	//This method is used perform edit operation.
	public void SearchHCPCSCode()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(isTextBoxBlank("Search Box",searchBox))
			System.out.println("PASS");
		else
			System.out.println("FAIL");
			
		dclick_button("Edit Icon", editIcon);
		fixed_wait_time(3);
		
		if(IsElementDisplayed("Edit Codes Tab", editCodesTab))
		{
			oReport.AddStepResult("Edit icon", "Clicked on edit icon and verified that 'Edit Codes Tab' is displayed", "PASS");
			
			if(isTextBoxBlank("Termination Date", terminationDate))
			{
				oParameters.SetParameters("EffectiveDate", convertToWebElement(effectiveDate).getAttribute("value"));
				enter_text_value("Termination Date", terminationDate, get_next_date(oParameters.GetParameters("EffectiveDate"), 2));
				performKeyBoardAction("ENTER");
			}
			else
			{
				oParameters.SetParameters("TerminationDate", convertToWebElement(terminationDate).getAttribute("value"));
				enter_text_value("Termination Date", terminationDate, get_next_date(oParameters.GetParameters("TerminationDate"), 2));
				performKeyBoardAction("ENTER");
			}
		}
		else
			oReport.AddStepResult("Edit icon", "Clicked on edit icon and verified that 'Edit Codes Tab' is not displayed", "FAIL");
	}
	
	
	//This method is used to perform save operation.
	public void savaButtonValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit Codes Tab", EditCodesSaveButton);
		
		if(IsElementDisplayed("Respective page", descriptionPage))
			oReport.AddStepResult("Save Button", "Clicked on Edit Icon and modified thetermination date and verified that termination date is displayed", "PASS");
		else
			oReport.AddStepResult("Save Button", "Clicked on Edit Icon and modified thetermination date and verified that termination date is not displayed", "FAIL");
	}
	
	
	By AddCodesTabSaveButton=By.xpath("//input[@id='button.saveId']");
	
	
	//This method is used to click on save Button.
	public void SaveScenarioValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Save Button", AddCodesTabSaveButton);
		
		if(IsElementDisplayed("Add Codes", addCodes))
			oReport.AddStepResult("Added code", "Clicked on Add Code Button and verified that new code set is added successfully", "PASS");
		else
			oReport.AddStepResult("Added code", "Clicked on Add Code Button and verified that new code set is not added successfully", "FAIL");
	}

	
	By filterCancelIcon=By.xpath("//div[@id='codeMaintenanceView']//div[@class='fa fa-times-circle close-icon']");
	
	//By importIcon=By.xpath("//div[@id='codeMaintenanceView']//span[text()='Import']/..");

	By importIcon = By.xpath("//div[@id='codeMaintenanceView']//i[@class='left fa fa-cloud-upload']/..");
	
	By importCodeMaintenance=By.xpath("//div[@id='importCodeSetModal']//div[@title='Import Code Maintenance']");
	
	By downloadSampleXLSXLink=By.xpath("//form[@id='testImportCodeMaintenanceId']//a[text()='Download a sample .XLSX']");
	
	
	//This method is used to download a sample.XLSX File.
	public void importIcon()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		dclick_button("Filter cancel Icon", filterCancelIcon);
		
		if(isTextBoxBlank("Search Box", searchBox))
		{
			oReport.AddStepResult("Search Box", "Clicked on Filtered close icon and verified that search box is cleared", "PASS");
			fixed_wait_time(2);
			dclick_button("Import Icon", importIcon);
			
			if(IsElementDisplayed("Import Code Maintenance", importCodeMaintenance))
			{
				oReport.AddStepResult("Import Code Maintenance", "Clicked on 'import icon' and verified that 'Import Code Maintenance tab' is displayed", "PASS");
				
				click_button("Download Link", downloadSampleXLSXLink);
				int rowCount = ExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"),"xlsx"));
				
				if(rowCount > 0)
					oReport.AddStepResult("Import download link", "Clicked on 'Download Sample XLSX' link and verified that file contains all necessary coloumns are displayed ", "PASS");
				else
					oReport.AddStepResult("Import download link", "Clicked on 'Download Sample XLSX' link and verified that file contains all necessary coloumns are not displayed ", "FAIL");
			}
			else
				oReport.AddStepResult("Import Code Maintenance", "Clicked on 'import icon' and verified that 'Import Code Maintenance tab' is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Search Box", "Clicked on Filtered close icon and verified that search box is not cleared", "FAIL");
	}
	
	
	By BrowseButton=By.xpath("//form[@id='testImportCodeMaintenanceId']//input[@id='CodeMaintenanceId']");
	
	By SaveButton=By.xpath("//div[@class='workflow  modal-medium']//div[@id='fullFooter']//input[@value='Save']");
	
	By CancelButton=By.xpath("//workflow-modal[@class='ng-isolate-scope']//input[@id='button.cancelId']");
	
	
	//This method is used to import the valid file.
	public void ImportValidFile()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Clicked on Browse Button", BrowseButton);
		fixed_wait_time(5);
		
		try
		{
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Valid_CodeMaintenance.exe");
			fixed_wait_time(5);
			
			oReport.AddStepResult("Valid Code Maintenance", "Uploaded Valid code Maintenance File", "SCREENSHOT");
			click_button("Save Button", SaveButton);
			fixed_wait_time(3);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	By UnableToUploadAllCode = By.xpath("//div[@id='dialog']//div[contains(text(),'Unable to upload all the Code Maintenance')]");
	
	By UnableToUploadTabOkButton=By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");
	
	
	//This method is used to import the invalid file.
	public void ImportInValidFile()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(2);
		dclick_button("Import Icon", importIcon);
		fixed_wait_time(2);
		
		if(IsElementDisplayed("Import Code Maintenance", importCodeMaintenance))
		{
			oReport.AddStepResult("Import Code Maintenance", "Clicked on 'import icon' and verified that 'Import Code Maintenance tab' is displayed", "PASS");
			click_button("Clicked on Browse Button", BrowseButton);
			
			try 
			{
				fixed_wait_time(2);
				Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\Invalid_CodeMaintenance.exe");
				fixed_wait_time(5);
				
				oReport.AddStepResult("Invalid Code Maintenance", "Uploaded Invalid code Maintenance File", "SCREENSHOT");
				click_button("Save Button", SaveButton);
				fixed_wait_time(2);
				
				if(IsDisplayed("Unable To Upload All Code Maintenance", UnableToUploadAllCode))
				{
					oReport.AddStepResult("Unable To Upload all the Code Maintenance ", "Clicked on Browse Button and uploaded Invalid File and verified that 'Unable to upload all the code Maintenance' Tab is displayed", "PASS");
					click_button("Unable Upload Ok Button", UnableToUploadTabOkButton);
					
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
	    				oReport.AddStepResult("Invalid File", "Uploaded Invalid File and verified that 'Unable to upload' Tab is displayed and also 'Error File' is downloaded with 'Error Message' coloumn", "PASS");
	    			else
	    				oReport.AddStepResult("Invalid File", "Uploaded Invalid File and verified that 'Unable to upload' Tab is displayed and also 'Error File' is not downloaded with 'Error Message' coloumn", "WARNING");
				}
				else
					oReport.AddStepResult("Unable To Upload all the Code Maintenance ", "Clicked on Browse Button and uploaded Invalid File and verified that 'Unable to upload all the code Maintenance' Tab is not displayed", "WARNING");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
			oReport.AddStepResult("Import Code Maintenance", "Clicked on 'import icon' and verified that 'Import Code Maintenance tab' is not displayed", "FAIL");
	}
	
	
	
	By ContractManagement = By.xpath("//div[@id='portal-main-nav']//a[contains(.,'Contract Management')]");
	
	//This method is used to navigate Contract management.
	public void navigateContractManagement()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Contract Management", ContractManagement);
		
		//This method is used to navigate Codes plugin.
		ocodesLibrary.navigateCodesPlugin();
		
		//This method is used to click on select code set type.
		ocodesLibrary.codesDropdown();
	}
	
	
	By SearchBox=By.xpath("//div[@id='contract-management']//div[@id='codeView']//input[@class='search-text-box input-sm form-control search-icon ng-pristine ng-valid']");
	
	By procedureList=By.xpath("//div[@id='addEditEntryParent']//table[contains(@class,'table table-condensed')]//tr[contains(@class,'hand-cursor position')]/td[1]");
	
	By ErrorAddingCodeSet=By.xpath("//div[@id='dialog']//div[contains(text(),'Error Adding Code Set')]");
	
	By ErrorAddingCodeSetOkButton=By.xpath("//div[@id='dialog_buttons']/input[@value='OK']");
	
	By proceudreDelete=By.xpath("//div[@id='addEditEntryParent']//tbody//tr//td//span[@class='lnk-btn-txt ng-binding']/..");
	
	
	//This method is used to select particular code set 
	public void SearchCodeSet()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search Box", SearchBox, oParameters.GetParameters("codeset"));
		fixed_wait_time(3);
		
		
		if(IsDisplayed("First Procedure Code Set", ocodesLibrary.firstElement))
			click_button("First Code Set ", ocodesLibrary.firstElement);
		else 
		{
			click_button("Add HCPCS/CPT Code Set", ocodesLibrary.Add_Hcpcs_cpt);
			waitFor(ocodesLibrary.codesetname, "Code Set Name Feild");
			
			enter_text_value("Code set Name",ocodesLibrary.codesetname,oParameters.GetParameters("codeset"));
	    		
	    	click_button("Add Effective",ocodesLibrary.add_effective);
	    	
	    	enter_text_value("Effective date",ocodesLibrary.add_effective,get_specified_date(0));
	    	performKeyBoardAction("ENTER");
	    	
	    	click_button("Save button",ocodesLibrary.addCodeSet_SaveButton);
		}
		
		//This method is used to select procedure first element.
		//ocodesLibrary.selectCode();
		
		//This Method is used to click on Add Code.
		ocodesLibrary.addCodeSetButton();
		
		if(IsDisplayed("Procedure feild", ocodesLibrary.procedure))
		{
			enter_text_value("Procedure",ocodesLibrary.procedure,oParameters.GetParameters("ProcedureCode"));
			oParameters.SetParameters("NewProcedure",convertToWebElement(ocodesLibrary.procedure).getAttribute("value"));
		
			click_button("Save Button",ocodesLibrary.addcodesetsave_button);
			waitFor(ocodesLibrary.openedpage, "Respective Code Set");
		}
		else
		{
			enter_text_value("Procedure",ocodesLibrary.code_diagnosis,oParameters.GetParameters("ProcedureCode"));
			oParameters.SetParameters("NewProcedure",convertToWebElement(ocodesLibrary.procedure).getAttribute("value"));
		
			click_button("Save Button",ocodesLibrary.addcodesetsave_button);
			waitFor(ocodesLibrary.openedpage, "Respective Code Set");
		}
	
		if(get_table_row_count(ocodesLibrary.ProcedureCountBeforeadding) > Integer.parseInt(oParameters.GetParameters("ProcedureCountBeforeAdding")))
		{	
			oReport.AddStepResult("Add code set save Button", " Entered Procedure code and clicked on add code set save button and verifyed that new  procedure code is added", "PASS");
			
			fixed_wait_time(2);
			List<WebElement> TotalCount=convertToWebElements(procedureList);
			
			for(int i=0;i<TotalCount.size();i++)
			{
				if(String.valueOf(TotalCount.get(i).getText()).equals(oParameters.GetParameters("NewProcedure")))
				{
					
					By count=By.xpath("//div[@id='addEditEntryParent']//td[text()='"+TotalCount.get(i).getText()+"']");
					mouse_hover("Procedure", count);
					 
					By proceudreDelete=By.xpath("//div[@id='addEditEntryParent']//td[text()='"+TotalCount.get(i).getText()+"']/..//i[@class='left fa fa-minus-square']");
					dclick_button("first procedure delete",proceudreDelete);
					fixed_wait_time(2);
					click_button("Delete Code Set", ocodesLibrary.DeleteCodeSet);
						
				break;	
				}
			}
		}
	}
	
	
	//This method is used to execute Code Maintenance HCPCS.
	public void CodeMaintenanceHCPCS()
	{
		login("EDIT");
		changePricingEngine();
		ocodesLibrary.xpathChange();
		ouserGroups.navigateToConfigurationSuite();
		navigateToCodeMaintenance();
		selectCodeScheduleTypeDropDown(CodeMaintenance_HCPCS_CPT);
		searchAndSelect(oParameters.GetParameters("Codes_HCPCS"),oParameters.GetParameters("Hcpcs_Cpt"),"0");
		addCode(oParameters.GetParameters("Hcpcs_Cpt"),"0",oParameters.GetParameters("ADDCodeFeild"));
		CancelButtonValidation();
		SearchHCPCSCode();
		SaveScenarioValidation();
		SearchHCPCSCode();
		CancelButtonValidation();
		importIcon();
		ImportValidFile();
		ImportInValidFile();
		navigateContractManagement();
		ocodesLibrary.SelectCodeSetType(ocodesLibrary.HCPCS_CPT,ocodesLibrary.addHcpcsCpt);
		SearchCodeSet();
		logout();
	}
	
	
	By ageSpecificationDropdown=By.xpath("//select[@id='icdAgeSpec']");
	
	By gender=By.xpath("//select[@id='icdGender']");
	
	By procedureClass=By.xpath("//select[@id='icdProcedureClass']");
	
	By addCodeSaveButton=By.xpath("//workflow-modal[@id='addCodeModal']//input[@id='button.saveId']");
	
	
	public void addCodeTabDetails()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int ageSpecificationCount = getOptions("Age Specification DropDown",ageSpecificationDropdown);
		
		for(int i=1;i<=ageSpecificationCount;i++)
		{
			selectOptionByIndex(ageSpecificationDropdown, "Age Specfication", i);
			
			int AgeSpecificationindex = i-1;
			By AgeSpecification = By.xpath("//select[@id='icdAgeSpec']//option[@value='"+AgeSpecificationindex+"']");
			
			int genderCount = getOptions("Gender DropDown",gender);
			
			for(int j=1;j<genderCount;j++)
			{
				selectOptionByIndex(gender, "Gender", j);
				fixed_wait_time(2);
				
				int Genderindex = j-1;
				By Gender = By.xpath("//select[@id='icdGender']//option[@value='"+Genderindex+"']");
				
				if(IsDisplayed("Procedure class", procedureClass))
				{
					int procedureClassCount = getOptions("Procedure Class",procedureClass);
					
					for(int k=j;k<procedureClassCount;k++)
					{
						selectOptionByIndex(procedureClass, "Procedure Class", k);
						
						int procedureindex = k-1;
						By Procedure = By.xpath("//select[@id='icdProcedureClass']//option[@value='"+procedureindex+"']");
						
						//waitFor(addCodeSaveButton,"");
						//click_button("Add Code Save Button", addCodeSaveButton);
						//fixed_wait_time(3);
						
						oReport.AddStepResult("Add Codes Tab","Selected Age Specification as "+get_text(AgeSpecification)+ ", Selected Gender as "+get_text(Gender)+ ", Selected Procedure Class as "+get_text(Procedure)+ " and clicked on Save Button ", "PASS");
						waitFor(addCodes,"");
						addCode(oParameters.GetParameters("ICD"),"0",oParameters.GetParameters("ADDCodeFeild"));
					
						if(k < genderCount-1 || k == 5)
							break;
					}
				}
				else
				{
					int diagnosisCategoryCount = getOptions("Daignosis Category",DiagnosisCategory);
					
					for(int k=j;k<=diagnosisCategoryCount;k++)
					{
						selectOptionByIndex(DiagnosisCategory, "Procedure Class", k);
						
						int Diagnosisindex = k-1;
						By Diagnosis = By.xpath("//select[@id='icdDiagnosisCategory']//option[@value='"+Diagnosisindex+"']");
						
						//waitFor(addCodeSaveButton,"");
						//click_button("Add Code Save Button", addCodeSaveButton);
						fixed_wait_time(3);
						oReport.AddStepResult("Add Codes Tab","Selected Age Specification as "+get_text(AgeSpecification)+ ", Selected Gender as "+get_text(Gender)+ ", Selected Diagnosis Category as "+get_text(Diagnosis)+ " and clicked on Save Button ", "PASS");
						waitFor(addCodes,"");
						addCode(oParameters.GetParameters("ICD"),"1",oParameters.GetParameters("ADDCodeFeild"));
					
						if(k < genderCount-1 || k == 5)
							break;
					}
				}
			}
		}
	}
	
	
	public void CodeMaintenanceICD()
	{
		login("EDIT");
		changePricingEngine();
		ocodesLibrary.xpathChange();
		ouserGroups.navigateToConfigurationSuite();
		navigateToCodeMaintenance();
		selectCodeScheduleTypeDropDown(CodeMaintenance_ICD);
		searchAndSelect(oParameters.GetParameters("Codes_ICD"),oParameters.GetParameters("ICD"),"0");
		addCode(oParameters.GetParameters("ICD"),"0",oParameters.GetParameters("ADDCodeFeild"));
		CancelButtonValidation();
		
		//addCode(oParameters.GetParameters("ICD"),"0");
		
		addCode(oParameters.GetParameters("ICD"),"1",oParameters.GetParameters("ADDCodeFeild"));
		addCodeTabDetails();
		importIcon();
		ImportValidFile();
		ImportInValidFile();
		navigateContractManagement();
		ocodesLibrary.SelectCodeSetType(ocodesLibrary.ICD,ocodesLibrary.add_ICD);
		SearchCodeSet();
		ocodesLibrary.codesDropdown();
		ocodesLibrary.SelectCodeSetType(ocodesLibrary.diagnosis,ocodesLibrary.add_diagnosis_Code_set);
		SearchCodeSet();
		logout();
	}
	
	
	//CCM - VR - Soarian - Configuration - Code Maintenance- Modifiers starts.
	
	
	By levelCode=By.xpath("//select[@id='levelCode']");
	
	By addCodeCancelButton=By.xpath("//workflow-modal[@id='addCodeModal']//input[@id='button.cancelId']");
		
	By UnSavedChangesPopUp=By.xpath("//div[@id='dialog']//div[contains(text(),'You have unsaved changes')]");
		
	By UnSavedChangesDiscard=By.xpath("//div[@id='dialog_buttons']/input[@value='Discard']");
		
	By EditCode=By.xpath("//div[@id='codeMaintenanceView']//a[@title='Edit Codes']");
	
	By EditCodeTab=By.xpath("//div[@id='addCodeFormModal']//div[@title='Edit Codes']");
	
	
	//This method is used to select level code and perform cancel scenario.
	public void levelCodeCancelScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		dclick_button("Edit Code", EditCode);
		
		if(IsElementDisplayed("Edit Code Tab", EditCodeTab))
		{
			oReport.AddStepResult("Edit Code Tab", "Selected particular code in Modifiers and clicked on edit icon and verified that 'Edit Code Set Tab' is displayed ", "Pass");
			selectOption(levelCode, "value", "1");
			click_button("Add Codes Cancel button", addCodeCancelButton);
		
			if(IsElementDisplayed("Unsaved Changes PopUp", UnSavedChangesPopUp))
			{
				oReport.AddStepResult("UnSaved Changes PopUP", "Clicked on 'Add Code Cancel Button'and verified that Un saved changes PopUp is displayed ", "PASS");
				click_button("UnSaved Changes Discard Button", UnSavedChangesDiscard);
			}
			else
				oReport.AddStepResult("UnSaved Changes PopUP", "Clicked on 'Add Code Cancel Button'and verified that Un saved changes PopUp is not displayed ", "FAIL");
		}
		else
		{
			oReport.AddStepResult("Edit Code Tab", "Selected particular code in Modifiers and clicked on edit icon and verified that 'Edit Code Set Tab' is not displayed ", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}		
	}		
		
		
	By CodeTextBox=By.xpath("//input[@id='modifiersName']");
	
	By levelCodeOptions=By.xpath("//select[@id='levelCode']/option");
	
	By respectivepage = By.xpath("//div[@id='codeMaintenanceView']//div[contains(@class,'pad-l-10 col-md-6 col-md-5 col-sm-6')]");
		
	
	public void modifierAddCode(String scheduleType,String modifierfeild)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		for(int i=1;i<=3;i++)
		{	
			fixed_wait_time(1);
			dclick_button("Add codes link", addCodes);
			
			if(IsElementDisplayed("Add Codes Tab", addCodesTab))
			{
				oReport.AddStepResult("Add codes Tab", "Clicked on 'Add codes link' and verified that Add codes window is displayed ", "PASS");
					
				enter_text_value("Code Set Name Feild", CodeTextBox,modifierfeild + System.currentTimeMillis());
				
				By effectiveDate=By.xpath("//input[@id='"+scheduleType+"EffectiveDate']");
				enter_text_value("Effective Date", effectiveDate, oParameters.GetParameters("Effectivedate"));
				performKeyBoardAction("ENTER");
			
				By description=By.xpath("//input[@id='"+scheduleType+"Description']");
				enter_text_value("Description Feild", description, oParameters.GetParameters("DescriptionFeild"));
				
				if(IsElementDisplayed("Level Code Feild", levelCode))
				{		
					oReport.AddStepResult("Level Code Feild", "Selected Modifier from drop down and clicked on add modifier and verified that level code feild is displayed", "PASS");
					
					click_button("Level Code", levelCode);
					selectOptionByIndex(levelCode,"Level Code",i);
					
					click_button("Add Codes Save Button", addCodeSaveButton);
					fixed_wait_time(3);
					
					int index = i-1;
					By LevelCode = By.xpath("//select[@id='levelCode']//option[@value='"+index+"']");
						
					oReport.AddStepResult("Add Codes Tab", "Selected Level Code '"+get_text(LevelCode)+"' and clicked on Save Button", "PASS");
				}
				else
					oReport.AddStepResult("Level Code Feild", "Selected Modifier from drop down and clicked on add modifier and verified that level code feild is not displayed", "FAIL");
			}
			else
				oReport.AddStepResult("Add codes Tab", "Clicked on 'Add codes link' and verified that Add codes window is not displayed ", "FAIL");
		}
	}	
		
	By SearchBoxCloseIcon=By.xpath("//div[@id='codeMaintenanceView']//div[@class='fa fa-times-circle close-icon']");
		
		
	public void SearchBoxValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(isTextBoxBlank("Search Box", searchBox))
			oReport.AddStepResult("Search Feild", "Navigated to Code maintenance and selected Code Set and verified that Search Feild is Blank ", "PASS");
		else
		{
			fixed_wait_time(3);
			click_button("Search Box Close Icon", SearchBoxCloseIcon);
		}
	}
	
	
	public void CodeMaintenanceModifiers()
	{
		login("EDIT");
		changePricingEngine();
		ocodesLibrary.xpathChange();
		ouserGroups.navigateToConfigurationSuite();
		navigateToCodeMaintenance();
		selectCodeScheduleTypeDropDown(CodeMaintenance_Modifiers);
		searchAndSelect(oParameters.GetParameters("Codes_Modifiers"),"","");
		modifierAddCode(oParameters.GetParameters("MODIFIERS"),oParameters.GetParameters("ADDCodeFeild"));
		SearchBoxValidation();
		searchAndSelect(oParameters.GetParameters("Codes_Modifiers1"),"","");
		levelCodeCancelScenario();
		importIcon();
		ImportValidFile();
		ImportInValidFile();
		logout();
	}
}

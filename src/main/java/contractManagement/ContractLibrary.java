package contractManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import libraries.*;

public class ContractLibrary extends CCMLibrary
{
	public ContractLibrary(WebDriver driver, Report oReport, Parameters oParameters) 
	{
		super(driver, oReport, oParameters);
	}
	
		
	By addContractButtonElement = By.xpath("//div/a[@title='Add Contract']");
	
	By AddContractWindowTitle=By.xpath("//div[@title='Add Contract']");
	
	By addContractLink = By.xpath("//div/a[@title='Add Contract']");
	
	//Method to click on Add Contract Button	
	public void addContractButton()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add Contract Button", addContractButtonElement);
		
		if(IsElementDisplayed("Add Contract Window", AddContractWindowTitle))
			oReport.AddStepResult("Add Contract window", "Clicked on Add Contract icon and verified that Add Contract window is displayed", "PASS");
		else
			oReport.AddStepResult("Add Contract window", "Clicked on Add Contract icon and verified that Add Contract window is not displayed", "FAIL");
	}
	
	
	By mandatoryRenewalDate = By.xpath("//label[@for='renewalTermDateId']/span[contains(.,'*')]");
	
	By mandatoryTerminationDate = By.xpath("//label[@for='endTermDateId']/span[contains(.,'*')]");
	
	By mandatoryPayer = By.xpath("//label[@for='payerId']/span[contains(.,'*')]");
	
	By mandatoryEffectiveDate = By.xpath("//label[@for='beginTermDateId']/span[contains(.,'*')]");
	
	public By contractedCheckBoxElement = By.xpath(".//*[@id='contracted']");
	
	
	//Method to validate all mandatory feilds
	public void checkMandatoryFeilds()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsFieldDisplayed("Effective Date Star Mark", mandatoryEffectiveDate) && !IsFieldDisplayed("Renewal Date star Mark", mandatoryRenewalDate) && !IsFieldDisplayed("Termination Date star Mark", mandatoryTerminationDate))
			oReport.AddStepResult("Renewal Date star Mark", "Effective Date feild is marked as mandatory and Renewal and Termination date feilds are not marked as mandatory", "INFO");
		else
			oReport.AddStepResult("Renewal Date star Mark", "Effective Date feild is not marked as mandatory or Renewal date and Termination date feilds are marked as mandatory", "INFO");
		
		if(IsElementSelected("Contracted checkbox", contractedCheckBoxElement))
		{
			//oReport.AddStepResult("Contracted Checkbox", "By default contracted checkbox is selected", "INFO");
			if(IsFieldDisplayed("Mandatory Payer", mandatoryPayer))
				oReport.AddStepResult("Payer Field Mandatory", "Contracted checkbox is selected hence Payer feild is marked as mandatory", "PASS");
			else
				oReport.AddStepResult("Payer Field Mandatory", "Contracted checkbox is selected however Payer feild is not marked as mandatory", "FAIL");
		}
		else
			oReport.AddStepResult("Contracted Checkbox", "Contracted checkbox is not selected, hence Payer feild is not marked as mandatory", "PASS");
		
	}
	
	
	public By contractNameBy=By.xpath(".//*[@id='contractNameId']");
	
	public By negotiatorElement = By.xpath(".//*[@id='negotiatorId']");
	
	public By negotiatorEmailElement = By.xpath(".//*[@id='negotiatorEmailId']");
	
	public By contractManagerElement = By.xpath(".//*[@id='contractManagerId']");
	
	public By contractManagerEmailElement = By.xpath(".//*[@id='contractManagerEmailId']");
	
	public By regionElement = By.xpath(".//*[@id='regionId']");
	
	public By effectiveDateElement = By.xpath(".//*[@id='beginTermDateId']");
	
	public By renewalDateElement = By.xpath(".//*[@id='renewalTermDateId']");
	
	public By terminationDateElement = By.xpath(".//*[@id='endTermDateId']");
	
	public By payerElement = By.xpath(".//*[@id='payerId']");
		
	
	//Method to enter data in data in Add Contract window
	public void contractDetails()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//To enter Contract Name
		enter_text_value_jscript("contractName", contractNameBy, oParameters.GetParameters("Contract Name") + System.currentTimeMillis());
		oParameters.SetParameters("Entered_Contract_Name", convertToWebElement(contractNameBy).getAttribute("value"));
		
		//To Enter Negotaitor Name
		enter_text_value_jscript("Negotiator", negotiatorElement,oParameters.GetParameters("Negotiator Name"));
		
		//To Enter Negotiator Email
		enter_text_value_jscript("Negotiator Email", negotiatorEmailElement, oParameters.GetParameters("Negotiator Email"));
		
		//To Enter Contract Manager Name
		enter_text_value_jscript("Contract Manager", contractManagerElement, oParameters.GetParameters("Contract Manager"));
		
		//To Enter Contract Manager Email
		enter_text_value_jscript("Contract Manager EMail", contractManagerEmailElement, oParameters.GetParameters("Contract Manager Email"));
		
		//To Enter Region
		enter_text_value_jscript("Region", regionElement, oParameters.GetParameters("Region"));
		
		//To Enter Effective  Date
		oParameters.SetParameters("effectiveDate", get_specified_date(-200));
		enter_text_value("effectiveDate", effectiveDateElement, oParameters.GetParameters("effectiveDate"));
		performKeyBoardAction("ENTER");
		
		//To Enter Renewal  Date
		oParameters.SetParameters("renewalDate", get_specified_date(200));
		enter_text_value("Renewal Date", renewalDateElement, oParameters.GetParameters("renewalDate"));
		performKeyBoardAction("ENTER");
		
		//To Enter Termination Date
		oParameters.SetParameters("terminationDate", get_specified_date(210));
		enter_text_value("Termination Date", terminationDateElement, oParameters.GetParameters("terminationDate"));
		performKeyBoardAction("ENTER");
		
		//To Enter Payer
		enter_text_value("Payer", payerElement, "");
		performKeyBoardAction("ENTER");
		
		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT"))
		{
			fixed_wait_time(5);
			waitFor(firstElement,"Payer Drop Down First Element");
		}
		
		longWaitFor(firstElement, "First Payer");
		click_button("First Payer", firstElement);
		
		oParameters.SetParameters("Entered_Payer_Name1", convertToWebElement(payerElement).getAttribute("value"));
	}
	
	
	By cancelButtonElement = By.xpath(".//*[@id='button.cancelId']");
	
	By popupCancelButtonElement = By.xpath("//div[@id='dialog']//input[@value='Cancel']");
	
	By popupDiscardButtonElement = By.xpath("//div[@id='dialog']//input[@value='Discard']");
	
	
	//Method to check cancel scenario
	public void cancelScenario()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//To click on cancel Button
		click_button("Cancel Button", cancelButtonElement);
		oReport.AddStepResult("Cancel Button", "Clicked on cancel button and Popup appeared", "PASS");
		
		//To click on Popup cancle Button
		List<WebElement>  we1 = convertToWebElements(popupCancelButtonElement); 
		we1.get(1).click();
		oReport.AddStepResult("Cancel Button", "Clicked on popup cancel button and popup disappeared", "PASS");
		
		//To click again on add contract cancel button
		click_button("Cancel Button", cancelButtonElement);
		
		//To click on Popup Discard button
		List<WebElement>  we2 = convertToWebElements(popupDiscardButtonElement); 
		we2.get(1).click();
		oReport.AddStepResult("Discard Button", "Clicked on popup discard button and add contrtact window is closed", "PASS");
	}
	
	
	By noContractTermsDocumentsLabel = By.xpath("//div[contains(text(),'No Contract Terms Documents found for this contract')]");
	
	By saveButtonElement = By.xpath(".//*[@id='button.saveId']");
	
	By effectiveDateValueElement = By.xpath(".//*[@id='contractView']//tr[8]//td[@class='field-value ng-binding']");
	
	By rateSheetAssiciationElement = By.xpath("//span[contains(.,'Associate Rate Sheet')]");
	
	By redBoxError = By.xpath("//ul[@class='error-items']");
	
	By redBoxErrorCloseIcon = By.xpath("//span[@ng-show='canShowCloseBox']");
	
	
	//Method to click on Ad contract window save button
	public void saveButton()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!IsElementEnabled("Save Button", saveButtonElement))
			oReport.AddStepResult("Save Button", "Save button on 'Add New Contract' Window is not Enabled", "FATAL");
			
		click_button("Save Button", saveButtonElement);
		
		if(!IsFieldDisplayed("RedBoxError", redBoxError))
			System.out.println("Red box error not found");
		else
		{
			click_button("RedBoxCloseButton", redBoxErrorCloseIcon);
			return;
		}
		
		oParameters.SetParameters("statusDropDownValue", get_text(statusDropDown));
		
		if(IsElementDisplayed("rateSheetAssiciationElement", rateSheetAssiciationElement) && oParameters.GetParameters("statusDropDownValue").equalsIgnoreCase("Draft"))
			oReport.AddStepResult("Contract", "Contract created successfully and its status is Draft", "PASS");
		else
			oReport.AddStepResult("New Contract", "Unable to click on 'Add new Contract' window save button", "FATAL");
	}
	
	
	By createdContractName = By.xpath(".//*[@id='contractView']/div/div[1]/ng-include[2]/div/div[1]");
	
	
	//Method to set contract name to hash table	
	public void saveContractName()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(createdContractName,"Contract Name Label");
		oParameters.SetParameters("createdContractName", get_text(contractTitle));
	}
	
	
	By negotiator_contractManagerElement = By.xpath("//*[@id='contractView']//tr[3]//a[@class='ng-binding']");
	
	By contractManagerElementVer = By.xpath("//*[@id='contractView']//tr[4]//a[@class='ng-binding']");
	
	By payerDetails = By.xpath(".//*[@id='contractView']//li[@class='doc-details-content collapsable']/div[@class='bold ng-binding']");
	
	
	//Method to validate details of newly created contract
	public void contractValidation()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("statusDropDownValue", get_text(statusDropDown));
		
		if(IsElementDisplayed("rateSheetAssiciationElement", rateSheetAssiciationElement) && oParameters.GetParameters("statusDropDownValue").equalsIgnoreCase("Draft"))
			oReport.AddStepResult("Contract", "Contract created successfully and its status is Draft", "PASS");
		else
			oReport.AddStepResult("New Contract", "Unable to click on 'Add new Contract' window save button", "FATAL");
		
		waitFor(noContractTermsDocumentsLabel, "No Contrtact Term Label");
		oParameters.SetParameters("payerName", get_text(payerDetails));
		oParameters.SetParameters("negotiatorManagerName", get_text(negotiator_contractManagerElement));
		oParameters.SetParameters("contractManagerName", get_text(contractManagerElementVer));
		oParameters.SetParameters("effectiveDateString", get_text(effectiveDateValueElement));
		
		if(oParameters.GetParameters("Negotiator Name").equals(oParameters.GetParameters("negotiatorManagerName"))) 
			oReport.AddStepResult("Negotiator name", "Negotiator name validation successfull", "PASS");
		else
			oReport.AddStepResult("Negotiator Name", "Negotiator Name validation unsuccessfull : " + oParameters.GetParameters("Negotiator Name") + "!=" + oParameters.GetParameters("negotiatorManagerName"), "FAIL");
		
	
		if(oParameters.GetParameters("Contract Manager").equals(oParameters.GetParameters("contractManagerName")))
			oReport.AddStepResult("Contract Manager", "Contract Manager name validation successfull", "PASS");
		else
			oReport.AddStepResult("Contract Manager", "Contract Manager Name validation unsuccessfull : " + oParameters.GetParameters("Contract Manager") + "!=" + oParameters.GetParameters("contractManagerName"), "FAIL");
	
		
		if(oParameters.GetParameters("effectiveDate").equals(oParameters.GetParameters("effectiveDateString")))
			oReport.AddStepResult("Effective Date", "Effective Date validation successfull", "PASS");
		else
			oReport.AddStepResult("Effective Date", "Effective Date validation unsuccessfull : " + oParameters.GetParameters("Effective Date") + "!=" + oParameters.GetParameters("effectiveDateString"), "FAIL");
						
		
		if(oParameters.GetParameters("Entered_Payer_Name1").equals(oParameters.GetParameters("payerName")))
			oReport.AddStepResult("Payer Name", "Payer Name validation successfull", "PASS");
		else
			oReport.AddStepResult("Payer Name", "Payer Name validation unsuccessfull : " + oParameters.GetParameters("Entered_Payer_Name1") + "!=" + oParameters.GetParameters("payerName"), "FAIL");
	}
	
	
	//Method to uncheck Contracted checkbox on Add new contract window
	public void nonContracted()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementSelected("Contraced CheckBox", contractedCheckBoxElement))
		{
			oParameters.SetParameters("contractedContract", "YES");
			click_button("Contracted Checkbox", contractedCheckBoxElement);
			oReport.AddStepResult("Contracted COntract", "Selected Contracted checkbox, hence creating Contracted Contract", "PASS");
		}
		else
		{
			oParameters.SetParameters("contractedContract", "NO");
			oReport.AddStepResult("Contracted Checkbox", "Contracted checkbox is not selected hence Creating NonContracted Contract", "INFO");
		}
			
	}
	
	
	By contractName = By.xpath(".//*[@id='contractNameId']");
	
	By contractEditButtonElement = By.xpath(".//*[@id='contractView']//span[@class='lnk-btn-txt hidden-xs ng-binding']");
	
	By editContractWindowTitle = By.xpath("//div[@title='Edit Contract']");
	
	//Method to click on Edit contract button
	public void editContractButton()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Edit Button", contractEditButtonElement);
		
		waitFor(contractName, "Contract Name Label");
		oReport.AddStepResult("Edit Button", "Clicked on Edit button", "PASS");
	}
	
	
	By editContractSaveButtonElement = By.xpath("//div[@id='addContractModal']//input[@id='button.saveId']");
	
	
	//Method to click on Edit contract window save button
	public void editContractSaveButton()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementEnabled("Save button", editContractSaveButtonElement))
			dclick_button("EditContract Save Button", editContractSaveButtonElement);
		else
			System.out.println("Element not enabled");
		
		waitFor(noContractTermsDocumentsLabel,"No Contract Term Label");
		oReport.AddStepResult("Edit Contract Save Button", "Clicked on edit contract window save button", "PASS");
	}
	
	
	public By firstElement = By.xpath("//ul[@id='-list']//li[1]");
	
	
	// Method to edit Payer name 
	public void editContractDetails()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Payer", payerElement, oParameters.GetParameters("Payer1"));
		click_button("First Element", firstElement);
		fixed_wait_time(2);
		oParameters.SetParameters("Entered_Payer_Name", convertToWebElement(payerElement).getAttribute("value"));
		fixed_wait_time(3);
		oReport.AddStepResult("Edit Contract : ", "Payer name is changed in Edit contract window", "SCREENSHOT");
	}
	
	
	By contractModifierName = By.xpath(".//*[@id='contractView']/div/div[3]/div[2]/ng-include[1]/ul/li[2]/table/tbody/tr[2]/td[2]");
	
	By modifiedBy = By.xpath(".//*[@id='contractView']//tr[2]/td[@class='field-value ng-binding']");
	
	By createdBy = By.xpath(".//*[@id='contractView']//tr[1]/td[@class='field-value ng-binding']");
	
	
	//Method to validate edited contract details
	public void editContractValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(contractModifierName, "Contract Modifier Name");
		
		oParameters.SetParameters("createdByText", get_text(createdBy));
		oParameters.SetParameters("modifiedByText", get_text(modifiedBy));
		oParameters.SetParameters("payerNameChanged", get_text(payerDetails));
		
		if(oParameters.GetParameters("modifiedByText").equalsIgnoreCase(oParameters.GetParameters("createdByText")) 
				&& oParameters.GetParameters("payerNameChanged").equalsIgnoreCase(oParameters.GetParameters("Entered_Payer_Name")))
			oReport.AddStepResult("Payer details Validation", "Payer name is changed and validation is successfull", "Pass");
		else
			oReport.AddStepResult("Payer Details Validation", "Payer name is changed in Edit contract window and its not reflected in portal so validation is unSuccessfull : " + oParameters.GetParameters("payerNameChanged") + "!=" + oParameters.GetParameters("Entered_Payer_Name"), "FAIL");
	}
	
	
	By statusDropDown = By.xpath(".//*[@id='contractView']//button[@class='pull-right btn-dropdown-popover ng-scope' and @data-trigger='click']");
	
	By active = By.xpath(".//*[@id='accept']");
	
	By inactive = By.xpath(".//*[@id='Delete']");
	
	By draft = By.xpath(".//*[@id='Reopen']");
	
	By changeButton = By.xpath("//input[@value='Change']");
	
	
	//Method to change Contract status	
	public void contractStatusChange()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//To change status from Draft to Active
		click_button("Draft", statusDropDown);
		click_button("To Active", active);
		click_button("Change Button", changeButton);
		//fixed_wait_time(3);
		
		waitFor(statusDropDown, "Status DropDown");
		oParameters.SetParameters("statusDropDownName", get_text(statusDropDown));
		System.out.println(oParameters.GetParameters("statusDropDownName"));
		
		if(oParameters.GetParameters("statusDropDownName").equalsIgnoreCase("Active"))
			oReport.AddStepResult("Change status to Acitve", "Contract status is changed to Active", "PASS");
		else
			oReport.AddStepResult("Change status to Acitve", "Contract status is not changed to Active", "FAIL");
			
		//To change status from Active to Inactive
		click_button("Active", statusDropDown);
		click_button("To Inactive", inactive);
		click_button("Change Button", changeButton);
		//fixed_wait_time(3);
		
		waitFor(statusDropDown, "Status DropDown");
		oParameters.SetParameters("statusDropDownName", get_text(statusDropDown));
		System.out.println(oParameters.GetParameters("statusDropDownName"));
		
		if(oParameters.GetParameters("statusDropDownName").equalsIgnoreCase("Inactive") && !IsElementDisplayed("contractEditButtonElement", contractEditButtonElement))
			oReport.AddStepResult("Changed status to Inacitve", "Contract status is changed to Inctive and verified that contract is not editable", "PASS");
		else
			oReport.AddStepResult("Change status to Inacitve", "Contract status is not changed to inactive", "FAIL");
				

		//To change status from Inactive to Draft
		click_button("Inactive", statusDropDown);
		click_button("To Draft", draft);
		click_button("Change Button", changeButton);
		//fixed_wait_time(3);
		
		waitFor(statusDropDown, "Status DropDown");
		oParameters.SetParameters("statusDropDownName", get_text(statusDropDown));
		System.out.println(oParameters.GetParameters("statusDropDownName"));
		
		if(oParameters.GetParameters("statusDropDownName").equalsIgnoreCase("Draft"))
			oReport.AddStepResult("Change status to Draft", "Contract status is changed to Draft", "PASS");
		else
			oReport.AddStepResult("Change status to Draft", "Contract status is not changed to Draft", "FAIL");
	}
	
	//This contains all methods with respect to contracted and non contracted VR 
	public void Contracted_NonContracted()
	{
		login("EDIT");
		changePricingEngine();
		
		//Non Contracted Contract
		clickAddButton("Add Contract Button", "Add Contract Window",  addContractButtonElement,  AddContractWindowTitle);
		nonContracted();
		contractDetails();
		cancelScenario("Add Contract window cancel button", "Add contract window popup cancel button", "Add Contract window discard button", cancelButtonElement, popupCancelButtonElement, popupDiscardButtonElement);
		
		clickAddButton("Add Contract Button", "Add Contract Window",  addContractButtonElement,  AddContractWindowTitle);
		checkMandatoryFeilds();
		nonContracted();
		contractDetails();
		clickSaveButton("Add contract window save button", "Add contract window", saveButtonElement, AddContractWindowTitle);
		
		saveContractName();
		
		contractValidation();
		
		clickAddButton("Edit contract button", "Edit contract window", contractEditButtonElement, editContractWindowTitle);
		editContractDetails();
		clickSaveButton("Edit contract window save button", "Edit contract window", editContractSaveButtonElement, editContractWindowTitle);
		
		editContractValidation();
		
		contractStatusChange();
		
		//Contracted Contract
		clickAddButton("Add Contract Button", "Add Contract Window",  addContractButtonElement,  AddContractWindowTitle);
		contractDetails();
		cancelScenario("Add Contract window cancel button", "Add contract window popup cancel button", "Add Contract window discard button", cancelButtonElement, popupCancelButtonElement, popupDiscardButtonElement);
		
		clickAddButton("Add Contract Button", "Add Contract Window",  addContractButtonElement,  AddContractWindowTitle);
		checkMandatoryFeilds();
		contractDetails();
		clickSaveButton("Add contract window save button", "Add contract window", saveButtonElement, AddContractWindowTitle);
		saveContractName();
		
		contractValidation();
		
		clickAddButton("Edit contract button", "Edit contract window", contractEditButtonElement, editContractWindowTitle);
		editContractDetails();
		clickSaveButton("Edit contract window save button", "Edit contract window", editContractSaveButtonElement, editContractWindowTitle);
		
		editContractValidation();
		
		contractStatusChange();
		
		logout();
	}
	
	
	
	//CRUD RateSheetAssociation VR
	
	
	By searchTextBox = By.xpath("//input[@placeholder='Search Contracts']");
	
	By contractHeading = By.xpath(".//*[@id='contractView']/div/div[1]/ng-include[2]/div/div[1]");
	
	String ContractName="Automation_DND_Contract";
	
	By addRateSheetLink = By.xpath("//span[contains(text(),'Add a Rate Sheet')]");
	
	By associateRateSheet=By.xpath("//span[contains(.,'Associate Rate Sheet')]");
	
	
	public void addRateSheetAssociationLink()
 	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(2);

		click_button("Rate Sheet Association Link", rateSheetAssiciationElement);
 		
 		if(IsElementDisplayed("RSA Page", By.xpath("//div[@title='Add Rate Sheet Association']")))
 			oReport.AddStepResult("Add Rate Sheet Association Page", "Add Rate Sheet Association Page is displayed", "PASS");
 		else
 			oReport.AddStepResult("Add Rate Sheet Association Page", "Upon clicking on Add Rate Sheet Association link the Add Rate Sheet Association Page is not displayed", "FAIL");
 	}
	
	
	By serviceProviderTextBoxElement = By.xpath(".//*[@id='serviceProviderName']");
	
	By healthPlanNameElement = By.xpath(".//*[@id='healthPlanDesc']");
	
	By healthPlanAliasElement = By.xpath(".//*[@id='healthPlanAlias']");
	
	By rateSheetElement = By.xpath(".//*[@id='ratesheetCode']");
	
	By fromDateElement = By.xpath(".//*[@id='fromDate']");
	
	By toDateElement = By.xpath(".//*[@id='toDate']");
	
	By claimTypeElement = By.xpath(".//*[@id='claimType']");
	
	By qualifyClaimsOnElement = By.xpath("//*[@id='effectiveDateType']");
	
	By RSASaveButtonElement = By.xpath("//input[@id='associateRateSheetId'][@value='Save']");
	
	By saveAndAddButton = By.xpath(".//*[@id='saveNAddRateSheetAssocId']");
	
	By serviceProviderNameBy=By.xpath(".//*[@id='serviceProviderName']");
	
	By toDateErrorMessgae=By.xpath("//form[@id='associatedRateSheetFrm']//div[@class='col-lg-4 col-md-6 col-sm-6 ng-isolate-scope']//span[@class='error pull-left ng-binding']");
	
	public By rateSheetDropDownFirstElement = By.xpath("//ul[@class='dropdown-menu dropdown-display'][@id='-list']/li[1]");
	
	By firstNPIName = By.xpath("//ul[@id='npiContainer']//li[1]//a[1]");
	
	public void rateSheetAssociationDetails()
    {
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
        if(IsElementDisplayed("RSA Page", serviceProviderNameBy))
        {
        	facilityName("CONTRACTS", "Apollo srn facility");
        	
        	click_button("Service Provider Name Search Box", serviceProviderTextBoxElement);
        	performKeyBoardAction("ENTER");

        	
        	if(IsDisplayed("First NPI Name", firstNPIName))
        	{
        		click_button("First NPI Name", firstNPIName);
        		oReport.AddStepResult("Click on First NPI", "Clicked on First NPI", "SCREENSHOT");
        	}
        	else
        		oReport.AddStepResult("Click on First NPI", "NPI drop down not displayed", "SCREENSHOT");
        	
        	
        	/*enter_text_value_jscript("Service Provider Text Box", serviceProviderTextBoxElement, oParameters.GetParameters("serviceProviderName"));
            performKeyBoardAction("ENTER");
            By dropDownXpath = By.xpath("//span[contains(.,'" + oParameters.GetParameters("serviceProviderName") + "')]");

            click_button("Service Provider NAme", dropDownXpath);
            */
        	
            enter_text_value_jscript("Health Plan Name", healthPlanNameElement, oParameters.GetParameters("healthPlanName") + System.currentTimeMillis());
            
            enter_text_value_jscript("Health Plan Alias", healthPlanAliasElement, oParameters.GetParameters("healthPlanAlias") + System.currentTimeMillis());
            oParameters.SetParameters("healthPlanAlias", get_attribute_value("Health Plan Alias", healthPlanAliasElement, "value"));
                       
            click_button("Rate Sheet Search Box", rateSheetElement);
            performKeyBoardAction("ENTER");
            waitFor(rateSheetDropDownFirstElement, "Rate Sheet DropDown First Element");
            click_button("Rate Sheet DropDown First Element", rateSheetDropDownFirstElement);
            
            oParameters.SetParameters("RateSheetName", get_attribute_value("Rate Sheet Name", rateSheetElement, "value"));
            
            selectOption(By.xpath("//*[@id='effectiveDateType']"), "visibletext", "Discharge Date");
                 
            selectOption(By.xpath(".//*[@id='claimType']"), "visibletext", "Both");
            
            /*oParameters.SetParameters("effectiveFromDate",get_specified_date(2, get_attribute_value("effectiveFromDate", fromDateElement, "value"), "MM/dd/YYYY"));
            enter_text_value("Effective From Date", fromDateElement, oParameters.GetParameters("effectiveFromDate"));
            performKeyBoardAction("ENTER");
            fixed_wait_time(2);
                 
            oParameters.SetParameters("effectiveToDate",get_specified_date(-2, get_attribute_value("effectiveToDate", toDateElement, "value"), "MM/dd/YYYY"));
            enter_text_value("Effective To Date", toDateElement, oParameters.GetParameters("effectiveToDate"));
            performKeyBoardAction("ENTER");
            fixed_wait_time(2);*/
            
            oReport.AddStepResult("RSA Details ", "Required details are entered in Add Rate Sheet Association Page", "PASS");
        }
        else
        	oReport.AddStepResult("RSA Details ", "Add Rate Sheet Association Page is not displayed", "FAIL");
    }	
    
    
    By RSASaveButton = By.xpath("//input[@id='associateRateSheetId'][@value='Save']");
    
    public void RSASaveButton()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
        fixed_wait_time(1);
        if(IsElementEnabled("RSA Save Button", RSASaveButton))
        {
        	click_button("Save Button", RSASaveButton);
        	
        	if(IsDisplayed("to Date Error Message", toDateErrorMessgae))
        	{
        		oReport.AddStepResult("To Date Error Message", " To date Error Message is displayed", "INFO");
        		oParameters.SetParameters("Continued_Execution", "NO");
        	}
        	else
        		System.out.println("To Date Error Message is not Displayed");
        	              
        	click_button("ALL Tab", AllTabXpath);
            
            fixed_wait_time(2);
            
        	if(IsElementDisplayed("Associated Rate Sheet", By.xpath("//span[contains(text(),'" + oParameters.GetParameters("healthPlanAlias") + "')]")))
        		oReport.AddStepResult("RSA ", "Clicked on RSA save button and verified that RSA is successfull", "PASS");
        	else
        		oReport.AddStepResult("RSA ", "Clicked on RSA save button howevere Rate Sheet Association is failed", "FAIL");
        }	
        else
        	oReport.AddStepResult("Save Button", "Save Button is not enabled", "FAIL");

    }
    
	
    By RSACancelButtonElement = By.xpath("//div[@id='contentFooter']//input[@value='Cancel']");
	
	
    public void RSACancelButton()
    {
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	if(IsElementEnabled("RSA Cancel Button", RSACancelButtonElement))
    		click_button("RSA Cancel Button", RSACancelButtonElement);
    	else
    		oReport.AddStepResult("Cancel Button", "Cancel Button is not enabled", "INFO");
    	
    	if(IsFieldDisplayed("ADD Filter Link", AddFilter))
    		oReport.AddStepResult("On Contract Page", "After clicking on 'ADD RSA Cancel' button 'ADD RSA' window is closed ", "PASS");
    	else
    		oReport.AddStepResult("On Contract Page", "After clicking on 'ADD RSA Cancel' button 'ADD RSA' window is not closed ", "FAIL");
    }
    
    
	public void RSACancelScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Cancel Button", RSACancelButtonElement);
		
		oReport.AddStepResult("Cancel Button", "Clicked on clancel button and verified that popup appeared with cancel and discard buttons on it", "PASS");
		List<WebElement> we3 = convertToWebElements(popupCancelButtonElement);
		we3.get(1).click();
		oReport.AddStepResult("Cancel Button", "Clicked popup cancel button and verified that popup is disappeared", "PASS");
		click_button("Cancel Button", RSACancelButtonElement);
		List<WebElement> we4 = convertToWebElements(popupDiscardButtonElement);
		we4.get(1).click();
		oReport.AddStepResult("Discard Button", "Clicked on discard butoon on popup and verified that Add Contrtact popup is closed", "PASS");
	}
	
	
	By facilityNameElement = By.xpath("//a[contains(.,'Facility Name')]");
	
	By facilityNPIElement = By.xpath("//a[contains(.,'Facility NPI')]");
	
	By providerNameElement = By.xpath("//a[contains(.,'Provider Name')]");
	
	By providerNPIElement = By.xpath("//a[contains(.,'Provider NPI')]");
	
	By providerGroupElement = By.xpath("//a[contains(.,'Provider Group')]");
	
	By serviceProviderSearch = By.xpath("//input[@id='serviceProviderName']");
	
	By qualifyClaimDropDown = By.xpath(".//*[@id='effectiveDateType'][@ng-disabled='spec.disabled']");
	
	By claimTypeDropDown = By.xpath(".//*[@id='claimType']");
	
	By errorBoxElement = By.xpath("//span[@icon='minus-circle']");
	
	
	public void rateSheetAssociationDeatils1()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		for(int i=0; i<=4; i++)
		{
			System.out.println("By default Facility name is selected");			
			for(int j=0; j<=3; j++)
			{
				System.out.println("By Default Select is selected");
				for(int k=0; k<=2; k++)
				{
					System.out.println("By default Select is selected");
					
					if(i==0)
					{
						try
						{
							System.out.println("Facility Name selected");
							enter_text_value_jscript("Service Provider", serviceProviderSearch, oParameters.GetParameters("facilityName"));
							performKeyBoardAction("ENTER");
							By serviceProviderNameOptionXpath=By.xpath("//span[contains(.,'" + oParameters.GetParameters("facilityName") + "')]");

							waitFor(serviceProviderNameOptionXpath,"Service Provider Name Dropdown");

							click_button("Service Provider Name", serviceProviderNameOptionXpath);
						}
						catch(Exception e)
						{
							System.out.println("Exception catched" + e.getMessage());
						}
					}
					else if(i==1)
					{
						try
						{
							click_button("Facility Name", facilityNameElement);
							click_button("Facility NPI", facilityNPIElement);
							
							enter_text_value_jscript("Service Provider Search", serviceProviderSearch, oParameters.GetParameters("facilityNPI"));
							performKeyBoardAction("ENTER");
							By serviceProviderNameOptionXpath=By.xpath(".//*[@id='npiContainer']/li[1]/a[1]");

							waitFor(serviceProviderNameOptionXpath,"Service Provider Name DropDown");

							click_button("Service Provider NAme", serviceProviderNameOptionXpath);
						}
						catch(Exception e)
						{
							System.out.println("Exception catched" + e.getMessage());
						}
					}
					else if(i==2)
					{
						try
						{
							click_button("Facility Name", facilityNameElement);
							click_button("Provider Name", providerNameElement);
							
							enter_text_value_jscript("Service Provider Search", serviceProviderSearch, oParameters.GetParameters("providerName"));
							performKeyBoardAction("ENTER");
							By serviceProviderNameOptionXpath=By.xpath("//span[contains(.,'" + oParameters.GetParameters("providerName") + "')]");

							waitFor(serviceProviderNameOptionXpath,"Service Provider Name DropDown");

							click_button("Provider Name", serviceProviderNameOptionXpath);
						}
						catch(Exception e)
						{
							System.out.println("Exception catched" + e.getMessage());
						}
					}
					else if(i==3)
					{
						try
						{
							click_button("Facility Name", facilityNameElement);
							click_button("provider Name", providerNPIElement);
							
							enter_text_value_jscript("erviceProviderSearch", serviceProviderSearch, oParameters.GetParameters("providerNPI"));
							performKeyBoardAction("ENTER");
							By serviceProviderNameOptionXpath=By.xpath(".//*[@id='npiContainer']/li[1]/a[1]");

							waitFor(serviceProviderNameOptionXpath,"Service Provider Name DropDown");

							click_button("Service Provider Name", serviceProviderNameOptionXpath);
						}
						catch(Exception e)
						{
							System.out.println("Exception catched" + e.getMessage());
						}
					}
					else
					{
						click_button("facility Name", facilityNameElement);
						click_button("Provider Group", providerGroupElement);
						
						enter_text_value_jscript("Service Provider Search", serviceProviderSearch, oParameters.GetParameters("providerGroup"));
						performKeyBoardAction("ENTER");
						By serviceProviderNameOptionXpath=By.xpath("//span[contains(.,'" + oParameters.GetParameters("providerGroup") + "')]");
						waitFor(serviceProviderNameOptionXpath,"Service Provider Name");
						click_button("Service Provider Name Option", serviceProviderNameOptionXpath);
					}
					
					if(j==0)
					{
						//To enter qualify claim on
						By qualifyClaimsDropDown = By.xpath("//select[@id='effectiveDateType']");
						selectOption(qualifyClaimsDropDown, "visibletext", "Admission Date");
					}
					else if(j==1)
					{
						//To enter qualify claim on
						By qualifyClaimsDropDown = By.xpath("//select[@id='effectiveDateType']");
						selectOption(qualifyClaimsDropDown, "visibletext", "Discharge Date");
						
					}
					else if(j==2)
					{
						//To enter qualify claim on
						By qualifyClaimsDropDown = By.xpath("//select[@id='effectiveDateType']");
						selectOption(qualifyClaimsDropDown, "visibletext", "Statement/Service From");
						
					}
					else
					{
						//To enter qualify claim on
						By qualifyClaimsDropDown = By.xpath("//select[@id='effectiveDateType']");
						selectOption(qualifyClaimsDropDown, "visibletext", "Statement/Service To");
					}
					
					if(k==0)
					{
						//To enter cliam type
						By claimTypeDropDown = By.xpath(".//*[@id='claimType']");
						selectOption(claimTypeDropDown, "visibletext", "Both");
					}
					else if(k==1)
					{
						//To enter cliam type
						By claimTypeDropDown = By.xpath(".//*[@id='claimType']");
						selectOption(claimTypeDropDown, "visibletext", "Institutional");
					}
					else
					{
						By claimTypeDropDown = By.xpath(".//*[@id='claimType']");
						selectOption(claimTypeDropDown, "visibletext", "Professional");
					}
								
					enter_text_value_jscript("Health Plan Name", healthPlanNameElement, oParameters.GetParameters("healthPlanName") + System.currentTimeMillis());
					
					enter_text_value_jscript("Health Plan ALias", healthPlanAliasElement, oParameters.GetParameters("healthPlanAlias") + System.currentTimeMillis());
					
					enter_text_value_jscript("Rate Sheet Name", rateSheetElement, oParameters.GetParameters("rateSheet"));
					By xpath =  By.xpath("//ul[@id='-list']/li/a/b[contains(.,'" + oParameters.GetParameters("rateSheet") + "')]");
					
					fixed_wait_time(4);
					if(IsElementDisplayed("xpath", xpath))
						click_button("Rate Sheet", xpath);
					else
						System.out.println("No Drop Down List");
					
					enter_text_value("Effective Date", fromDateElement, oParameters.GetParameters("effectiveFromDate"));
					performKeyBoardAction("ENTER");
					
					enter_text_value("Effective To Date", toDateElement, oParameters.GetParameters("effectiveToDate"));
					performKeyBoardAction("ENTER");
					
					fixed_wait_time(2);
					if(i==4 && j==3 && k==2)
					{

						waitFor(RSASaveButtonElement,"RSA Window save button");

						click_button("SAve Button", RSASaveButtonElement);
						By warningMessage = By.xpath("//ul[@class='warning-items ng-hide']");
						if(IsElementDisplayed("ErrorBox", By.xpath("//span[@icon='minus-circle']")))
							oReport.AddStepResult("Error Message", "Upon clicking on save button Red box error is displayed with error message and RSA is unsuccessfull", "FAIL");
						else if(IsElementDisplayed("Warning Message", warningMessage))
							oReport.AddStepResult("Warning message ", "Upon clicking on save button Red Box error id displayed with warning message and RSA is unsuccessfull", "FAIL");
						else
							oReport.AddStepResult("RSA", "Clicked on save button and verified that Rate Sheet Association is successfull", "PASS");
					}
					else
					{
						click_button("Save And Add", saveAndAddButton);
						
						if(IsElementDisplayed("ErrorBox", By.xpath("//span[@icon='minus-circle']")))
							oReport.AddStepResult("Error Message", "Upon clicking on save and add button Red box error is displayed with error message and RSA is unsuccessfull", "FAIL");
						else
							oReport.AddStepResult("RSA", "Clicked on save and add button and verified that Rate Sheet Association is successfull", "PASS");
					}
				}
			}
		}
	}
	
	
	By firstRSA = By.xpath("//ul[@class='data-list']/li[@class='data-row position-relative ng-scope'][1]//div[@class='small-header bold hide-overflow ng-binding']");
	
	By RSAFirstElement = By.xpath("//ul[@class='data-list']//li[contains(@class,'data-row position-relative ng-scope') and not(contains(@class, 'ng-hide'))][1]");
	
	By RSAEditIcon = By.xpath("//div[@title='Click to edit this associated ratesheet']");
	
	By serviceProviderTitle = By.xpath("//div[@class='title ng-binding'][contains(.,'Service Provider')]");
	
	@Test
	public void RSAEditButton()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("First RSA", RSAFirstElement))
		{	
			mouse_hover("First RSA ", RSAFirstElement);
			
			click_button("First RSA ", firstRSA);
			
			if(IsElementDisplayed("RateSheet Association Link", serviceProviderTitle))
			{
				waitFor(serviceProviderTitle,"Service Provider Title");
				oReport.AddStepResult("Edit RSA ", "RSA edit page is displayed", "PASS");
			}
			else
				oReport.AddStepResult("Edit RSA ", "RSA edit page is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("First RSA", "No Rate Sheets Associated with this contract", "FATAL");
	}
	
	
	public void RSAEditDetails(String whatUpdate, String pastPresentFuture)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("healthPlanAliasName_EditWindow", get_field_value("Health Plan Alias", healthPlanAliasElement));
		
		if(whatUpdate.equalsIgnoreCase("effectiveDate"))
		{
			if(pastPresentFuture.equalsIgnoreCase("PAST"))
			{
				//enter_text_value("Effective From Date", fromDateElement, get_attribute_value("Effective From Date", fromDateElement, "value"));
				enter_text_value("Effective From Date", fromDateElement, get_specified_date(-20));
				performKeyBoardAction("ENTER");
				
				oParameters.SetParameters("pastDate", get_specified_date(-10));
				enter_text_value("Effective To Date", toDateElement, oParameters.GetParameters("pastDate"));
				fixed_wait_time(1);
				oParameters.SetParameters("RSAEditDetails", "PastDate "+oParameters.GetParameters("pastDate"));
				performKeyBoardAction("ENTER");
				
				
			}
			else if(pastPresentFuture.equalsIgnoreCase("PRESENT"))
			{
				oParameters.SetParameters("presentDate", get_specified_date(-2));
				enter_text_value("Effective From Date", fromDateElement, oParameters.GetParameters("presentDate"));
				fixed_wait_time(1);
				oParameters.SetParameters("RSAEditDetails", "PresentDate " +oParameters.GetParameters("presentDate"));
				performKeyBoardAction("ENTER");
				
				//enter_text_value("Effective To Date", toDateElement, get_attribute_value("Effective To Date", toDateElement, "value"));
				enter_text_value("Effective To Date", toDateElement, get_specified_date(2));
			}	
			else
			{
				oParameters.SetParameters("futureDate", get_specified_date(2));
				enter_text_value("Effective From Date", fromDateElement, oParameters.GetParameters("futureDate"));
				fixed_wait_time(1);
				oParameters.SetParameters("RSAEditDetails", "FutureDate " +oParameters.GetParameters("futureDate"));
				performKeyBoardAction("ENTER");
				
				//enter_text_value("Effective To Date", toDateElement, get_attribute_value("Effective To Date", toDateElement, "value"));
				enter_text_value("Effective To Date", toDateElement, get_specified_date(20));
				performKeyBoardAction("ENTER");
			}
		}
		else
		{
			enter_text_value("Health Plan ALias", healthPlanAliasElement, oParameters.GetParameters("healthPlanAlias"));
			oParameters.SetParameters("RSAEditDetails", "healthPlanAlias " +oParameters.GetParameters("healthPlanAlias"));
			oParameters.SetParameters("healthPlanAliasName_EditWindow", get_field_value("Health Plan Alias", healthPlanAliasElement));
			performKeyBoardAction("ENTER");
			
		}
		fixed_wait_time(2);
		oReport.AddStepResult("RSA details ", oParameters.GetParameters("RSAEditDetails")+ "RSA Edit details are entered", "SCREENSHOT");
	}
	
	
	By RSAEditSaveButton = By.xpath("//input[@id='editAsscoiateRateSheetId']");
		
	By searchBoxElement = By.xpath("//input[@placeholder='Search Contracts']");
	
	By validProviderNameErrorMSG = By.xpath("//ul[@type='associatedRateSheetFrm.serviceProviderId']//li[@class='error ng-scope ng-binding']");
	
	
	public void RSAEditSave()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("RSA EDIT Save Button", RSAEditSaveButton);
		
		if(IsDisplayed("to Date Error Message", toDateErrorMessgae))
		{
			oReport.AddStepResult("To Date Error Message", " To date Error Message is displayed", "SCREENSHOT");
			
			if(IsDisplayed("Provider name error message", validProviderNameErrorMSG))
			{
				oReport.AddStepResult("Provider name error message", "Error message displayed at provider name as well as To date", "SCREENSHOT");
			
				enter_text_value_jscript("Service Provider Text Box", serviceProviderTextBoxElement, oParameters.GetParameters("serviceProviderName"));
                performKeyBoardAction("ENTER");
                By dropDownXpath = By.xpath("//span[contains(.,'" + oParameters.GetParameters("serviceProviderName") + "')]");
                
                click_button("Service Provider NAme", dropDownXpath);
                
        		click_button("RSA EDIT Save Button", RSAEditSaveButton);
        		
        		if(IsDisplayed("to Date Error Message", toDateErrorMessgae))
        			oReport.AddStepResult("To Date Error Message", " Even after entering different provider name, 'To date' Error Message is displayed", "SCREENSHOT");
        		else
        			oReport.AddStepResult("To Date Error Message", " After entering different provider name, 'To date' Error Message went off", "PASS");
        	}
			else
			{
				System.out.println("Valid provider name error message is not displayed");
				oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			}
		}	
		else if(IsElementDisplayed("Search Box", searchBoxElement))
		{
			oReport.AddStepResult("Edit RSA ", "Clicked on RSA save button and verified that edit RSA is successfull", "PASS");
			oParameters.SetParameters("CONTINUED_EXECUTION", "YES");
		}
		else
		{
			oReport.AddStepResult("Edit RSA ", "Clicked on RSA save button but it has not saved so edit RSA is unsuccessfull", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}
	}
	
	
	By RSAEditCancelButton = By.xpath("//div[@id='contentFooter']//input[@value='Cancel']");
	
	By RSAPopupCancel = By.xpath("//div[@id='dialog']/div[@class='form-button-panel']//div[@id='dialog_buttons']//input[2]");
	
	By RSAPopupDiscard = By.xpath("//div[@id='dialog']/div[@class='form-button-panel']//div[@id='dialog_buttons']//input[1]");
	
	
	public void RSAEditCancelScenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Cancel Button", RSAEditCancelButton);
		
		waitFor(RSAPopupCancel,"RSA Cancel Popup Cancel Button");
		oReport.AddStepResult("Clicked on cancel ", "Clicked on cancel button and verified that popup is displayed with cancel and discard buttons on it", "SCREENSHOT");
		
		click_button("Popup Cancel Button", RSAPopupCancel);
		
		waitFor(RSAEditCancelButton,"RSA Popup Cancel Button");
		oReport.AddStepResult("Clicked on popup cancel ", " Clicked on popup cancel button and verified that popup is disappeared", "SCREENSHOT");
		
		click_button("Cancel Button", RSAEditCancelButton);
		
		click_button("Discard Button", RSAPopupDiscard);
		
		oReport.AddStepResult("Clicked on discard ", "Clicked on popup discard button and verified that Edit RSA window is disappeared", "PASS");
	}
	
	
	By RSADeleteIcon = By.xpath("//ul[@class='data-list']//li[contains(@class,'data-row position-relative ng-scope') and not(contains(@class, 'ng-hide'))][1]//a[@title='Click to delete this associated ratesheet']//i[@class='left fa fa-times-circle']");
	
	By RSADeletePopupYes = By.xpath("//div[@id='dialog']/div[@class='form-button-panel']//div[@id='dialog_buttons']//input[1]");
	
	By RSADeletePopupNo = By.xpath("//div[@id='dialog']/div[@class='form-button-panel']//div[@id='dialog_buttons']//input[2]");
	
	By DeleteAssociatedRateSheetWindow=By.xpath("//div[@id='dialog']//div[contains(text(),'Delete Associated Rate Sheet')]");
	
	
	public void deleteRSA(By elemdesc)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(1);
		
		//To Hower over first element
		mouse_hover("First RSA ", RSAFirstElement);
		fixed_wait_time(5);
		/*mouse_hover("RSADeleteIcon", RSADeleteIcon);
		click_button("RSADeleteIcon", RSADeleteIcon);
		*/
		dclick_button("RSADeleteIcon", RSADeleteIcon);
				
		if(IsElementDisplayed("Delete Associated Rate Sheet", DeleteAssociatedRateSheetWindow))
		{
			oReport.AddStepResult("Delete Popup ", "Clicked on delete RSA icon and verified that popup is displayed", "PASS");
			if(elemdesc.toString().equalsIgnoreCase("RSADeletePopupNo"))
			{	
				click_button("click on "+ elemdesc, elemdesc);
				oReport.AddStepResult("Delete Popup", "Clicked on popup No button and verified that delete popup is disappeared", "PASS");
			}
			else
			{
				click_button("click on "+ elemdesc, elemdesc);
				oReport.AddStepResult("Delete Popup", "Clicked on popup yes button and verified that RSA is deleted", "PASS");
			}
		}
		else
			oReport.AddStepResult("Delete Popup", "Delete RSA popup is not displayed", "FAIL");
	}	
	
	
	By rateSheetLink = By.xpath("//a[@ng-click='getRateSheetlink(r.ratesheetId)']");
	
	By RateSheetsPlugin = By.xpath("//a[contains(.,'Rate Sheets')]");
	
	By filterButtonElement = By.xpath("//input[@value='Cancel'][@ng-click='closeSaveConfirmDialog()']");
	
	By allColumns = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[1]/a/div[1]");
	
	By claimTypeButtonElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[1]/ul/li[3]/a");
			
	By bothButtonElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[3]/div[2]/ul[2]/li[4]/a");
	
	By clearFilterButtonElement = By.xpath("//div[@id='filter-asscRateSheets']//ul[@class='pull-left']//a[@class='inverse-min link-btn hand-cursor ng-isolate-scope']//i[@class='left fa fa-times-circle'] ");
	
	By effectiveButtonElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[1]/ul/li[4]/a");
	
	By effectiveDateTextBoxElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[3]/input[2]");
	
	By operatorElement = By.xpath(".//*[@id='af1']/div/a[4]");
	
	By afterElement = By.xpath(".//*[@id='af1']/div/ul/li[2]/a");
	
	By beforeElement = By.xpath(".//*[@id='af1']/div/ul/li[3]/a");
	
	By betweenElement = By.xpath(".//*[@id='af1']/div/ul/li[4]/a");
	
	By healthPlanNameXpathElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[1]/ul/li[6]/a");
	
	By healthPlanNameTextBoxElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[3]/input[1]");
	
	By healthPlanAliasxpathElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[1]/ul/li[6]/a");
	
	By rateSheetXpathElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[1]/ul/li[7]/a");
	
	By rateSheetTextBoxElement = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[3]/input[1]");
	
	By filterButton = By.xpath("//div[@id='contractView']//span[contains(.,'Add Filter')]");
	
	By healthPlanName = By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[3]/input[1]");
	
	
	public void RSAFilter()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("RSA Filter Button", filterButton))
		{
			mouse_hover("Filter Button", filterButton);
			click_button("Filter Button", filterButton);
			
			click_button("All columns", allColumns);
			
			click_button("Claim Type", claimTypeButtonElement);
			
			click_button("Both Value", bothButtonElement);
			
			click_button("Clear Filter", clearFilterButtonElement);
			
			mouse_hover("Filter Button", filterButton);
			click_button("Filter Button", filterButton);
			
			click_button("All Columns", allColumns);
			
			click_button("Effective Date", effectiveButtonElement);
			
			enter_text_value("Effective Date", effectiveDateTextBoxElement, "07/05/2016");
			performKeyBoardAction("ENTER");
			
			oReport.AddStepResult("Effective Date", "In RSA Filter Effective date with == operator is selected and verified that rate sheets are displayed accordingly", "PASS");
			
			//Effective date with >After operator
			click_button("Operator Element", operatorElement);
			
			click_button("After Element", afterElement);
			
			oReport.AddStepResult("Effective Date", "In RSA filter Effective date with > operator is selected and Rate sheets are displayed accordingly", "PASS");
			
			//Effective date with <Before operator
			click_button("Operator Button", operatorElement);
			click_button("Before Element", beforeElement);
			
			oReport.AddStepResult("Effective DAte ", "In RSA filter Effective date with < operator is selected and Rate sheets are displayed accordingly", "PASS");
			
			//Effective date with Between operator
			click_button("Operator Button", operatorElement);
			click_button("Between Element", betweenElement);
			
			oReport.AddStepResult("Effective Date", "In RSA filter Effective date with Between option is selected and Rate sheets are displayed accordingly", "PASS");
			
			
			//Filter based on Health Plan name
			click_button("Clear Filter", clearFilterButtonElement);
			mouse_hover("Filter Button", filterButton);
			click_button("Filter Button", filterButton);
			
			click_button("All Column", allColumns);
			
			click_button("Health Plan Name", healthPlanNameXpathElement);
			
			enter_text_value("Health Plan Name", healthPlanName, "test");
			performKeyBoardAction("ENTER");
			
			oReport.AddStepResult("Health Plan Name", "In RSA filter dropdown Health Plan Name is selected and Rate sheets are displayed accordingly", "PASS");
			
			
			//Filter based on Health Plan Alias
			click_button("Clear Filter", clearFilterButtonElement);
			mouse_hover("Filter Button", filterButton);
			click_button("Filter Button", filterButton);
			
			click_button("All Column", allColumns);
			
			click_button("Health Plan Alias", healthPlanAliasxpathElement);

			enter_text_value("Health Plan Alias", By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[3]/input[1]"), "test");
			performKeyBoardAction("ENTER");
			
			oReport.AddStepResult("Health Plan Alias ", "In RSA filter drop down Health Plan Alias is selected and Rate sheets are displayed accordingly", "PASS");
			
			
			//Filter based on Rate Sheet
			click_button("Clear Filter", clearFilterButtonElement);
			mouse_hover("Filter Button", filterButton);
			click_button("Filter Button", filterButton);
			
			click_button("All Column", allColumns);
			
			click_button("Rate Sheet", rateSheetXpathElement);
			
			enter_text_value("Ratesheet", By.xpath(".//*[@id='filter-asscRateSheets']/div/ul/li[1]/div/div[3]/input[1]"), "test");
			performKeyBoardAction("ENTER");
			
			oReport.AddStepResult("Rate Sheet ", "Rate Sheet", "PASS");
		}
		else
			System.out.println("Element not displayed");
	}
		
		
	
	public void CRUD_RatesheetAssociation()
	{
		login("EDIT");
		changePricingEngine();
		
		
		SearchContract("CRUD RSA");
		
		addRateSheetAssociationLink();
		RSACancelButton();
		
		addRateSheetAssociationLink();
		rateSheetAssociationDetails();
		cancelScenario("RSA window cancel button", "Popup cancel button", "Popup Discard button", RSACancelButtonElement, popupCancelButtonElement, popupDiscardButtonElement);
		
		addRateSheetAssociationLink();
		rateSheetAssociationDetails();
		RSASaveButton();
		
		RSAEditButton();
		RSAEditDetails("HealthPlanAlias", "PAST");
		cancelScenario("RSA Edit window cancel button", "Popup cancel button", "Popup Discard button", RSAEditCancelButton, popupCancelButtonElement, popupDiscardButtonElement);
		
		RSAEditButton();
		RSAEditDetails("effectiveDate", "FUTURE");
		RSAEditSave();
		contractFutureTabSelection();
		
		RSAEditButton();
		RSAEditDetails("effectiveDate", "PAST");
		RSAEditSave();
		contractPastTabSelection();
		
		RSAEditButton();
		RSAEditDetails("effectiveDate", "PRESENT");
		RSAEditSave();
		contractCurrentTabSelection();
		
		contractAllTabSelection();
		deleteRSA(RSADeletePopupNo);
		deleteRSA(RSADeletePopupYes);
		
		ClickRateSheetLink();
		navigateContractPlugin();
		RSAFilter();
		logout();
	}
	
	
	//Search Contract VR
	By contractsDropDownList = By.xpath("//div[@id='contract-search-results'][@class='search-results']");
	
	public void ListAllContract()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Search Contract ", ContractSearchBox);
		performKeyBoardAction("ENTER");
		
		if(IsElementDisplayed("List All Contract", contractsDropDownList))
			oReport.AddStepResult("Contracts", "Pressed enter in contracts search box and verified that all contracts are displayed", "PASS");
		else
			oReport.AddStepResult("Contracts", "Pressed enter in contracts search box but no contracts were displayed", "FAIL");
	}
	
	
	By ContractNamePageTitle = By.xpath("//*[@id='contractView']//div[@class='document-title-bar ng-scope']/div[@class[contains(.,'col-lg-12 col-md-12 col-sm-12 col-xs-12 xl-header ng-binding')]]");
	
	By ContractSearchBox = By.xpath("//input[@placeholder='Search Contracts']");
	
	By associatedRateSheetHeading = By.xpath("//div[@class='title no-border ng-binding'][contains(.,'Associated Rate Sheets')]");
	
	By contractTitle = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 col-xs-12 xl-header ng-binding']");
	
	By contractDropDownFirstContract = By.xpath("//ul[@id='contract-select']//li[@class='data-row hand-cursor ng-scope'][1]//div[@class='col-lg-3 col-md-3 col-sm-4 contract-label']//div[@class='field-value ng-binding']");
	
	By contractDropDownSecondContract = By.xpath("//ul[@id='contract-select']//li[@class='data-row hand-cursor ng-scope'][2]//div[@class='col-lg-3 col-md-3 col-sm-4 contract-label']//div[@class='field-value ng-binding']");
	
	By errorMsg = By.xpath("//li[@class='nopadding notification-item ng-scope ng-isolate-scope notification-error']//span[contains(.,' The item you selected is unavailable or has been deleted. Refresh the page and try again.')]");
	
	By errorMsgCloseIcon = By.xpath("//span[@icon='times-circle']");
	
	public void SearchContract(String searchData)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		waitFor(ContractSearchBox,"Contract Search Box");
		
		if(searchData.equalsIgnoreCase(""))
		{
			click_button("Contract search Box", ContractSearchBox);
			performKeyBoardAction("ENTER");
			
			waitFor(contractDropDownFirstContract, "First Contract Name");
			oParameters.SetParameters("FirstContractName", get_text(contractDropDownFirstContract));
			
			click_button("click on particular Contract ", contractDropDownFirstContract);
			
			if(!IsFieldDisplayed("Error Message", errorMsg))
				System.out.println("Contract not found error msg not displayed");
			else
			{
				click_button("Red Box Error CLose Icon", errorMsgCloseIcon);
				oReport.AddStepResult("RedBox Error", "Upon clicking on first contract Redbox error displayed", "INFO");
				click_button("Contract search Box", ContractSearchBox);
				performKeyBoardAction("ENTER");
			
				waitFor(contractDropDownSecondContract, "Second Contract Name");
				oParameters.SetParameters("FirstContractName", get_text(contractDropDownSecondContract));
			
				click_button("click on particular Contract ", contractDropDownSecondContract);
			}	
		}
		else
		{
			click_button("Contract search Box", ContractSearchBox);
			enter_text_value("Contractc name", ContractSearchBox, searchData);
			performKeyBoardAction("ENTER");
			
			By searchedContractXpath=By.xpath("//div[@class='contract-name' and contains(.,'" + searchData + "')]");
			waitFor(searchedContractXpath, "Searched Contract name");
			
			oParameters.SetParameters("FirstContractName", searchData);
			
			click_button("click on particular Contract ", searchedContractXpath);
		}
		
		
		
		if(!IsDisplayed("Error Message", errorMsg))
			oReport.AddStepResult("Contract Not found Error Message", "Contract not found error message not displayed", "INFO");
		else
		{
			oReport.AddStepResult("Contract Not found Error Message", "Contract not found error message displayed", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			click_button("Error Message Close Icon", errorMsgCloseIcon);
		}
		
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
			return;
		
		waitFor(noContractTermsDocumentsLabel, "Opened contract name");
		if(get_field_value("Contract Page Title", contractTitle).equalsIgnoreCase(oParameters.GetParameters("FirstContractName")) || 
					get_field_value("Contract Page Title", contractTitle).equalsIgnoreCase(oParameters.GetParameters("ContractName")))
			oReport.AddStepResult("Searched Contract", "Clicked on respective contract name and verified that contract opened successfully","PASS");
		else
			oReport.AddStepResult("Searched Contract", "Clicked on respective contract and verified that contract has not opened","FAIL");
	}
	
	
	By noSearchElement = By.xpath("//div[@class='scroll-auto search-contracts']//div[@class='no-results margin-top ng-binding']");
	
	
	public void invalidContractNameSearch()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Contract search Box", ContractSearchBox);
		enter_text_value("Invalid Contract Name", ContractSearchBox, "yyyyyyyyyyyyyyyyyyyyyyyyyyy");
		performKeyBoardAction("ENTER");
		
		if(IsFieldDisplayed("No contracts message", noSearchElement))
			oReport.AddStepResult("No search Element", "Entered invalid name in contracts search box and verified that 'No items were found' message is displayed", "PASS");
		else
			oReport.AddStepResult("No search Element", "Entered invalid name in contracts search box and verified that 'No items were found' message is not displayed", "FAIL");
	}
	
	By firstContractName = By.xpath("//ul[@id='contract-select']//li[1]//div[@class='contract-name']");
	
	public void validContractNameSearch()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Contract search Box", ContractSearchBox);
		enter_text_value("Clear Text Box", ContractSearchBox, "");
		performKeyBoardAction("ENTER");
		String FirstContractName = get_field_value("First Contract Name", firstContractName);
		enter_text_value("Valid Contract Name", ContractSearchBox, FirstContractName);
		performKeyBoardAction("ENTER");
		
		By specificContract = By.xpath("//ul[@id='contract-select']//div[@class='field-value ng-binding'][contains(.,'"+ FirstContractName+"')]");
		
		click_button(FirstContractName + " contract name", specificContract);
		
		if(get_field_value("Contract Page Title", contractTitle).equalsIgnoreCase(FirstContractName))
			oReport.AddStepResult("Searched Contract", FirstContractName + " contract got opened","PASS");
		else
			oReport.AddStepResult("Searched Contract", FirstContractName + " contract failed to open","FAIL");
	}
	
	
	By StatusDropdownSelectionXpath=By.xpath("//*[@id='contractView']//a[@role='button']");
	
	By StatusDropdownSelectionDraftXpath=By.xpath("//*[@id='contractView']//a[@role='menuitem' and contains(.,'Draft')]");
	
	By SearchEngineStatusChangedxpath=By.xpath("//*[@id='contractView']//input[@title='Type search criteria and click enter to search']");
	
	By StatusDropdownSelectionInactiveXpath=By.xpath("//*[@id='contractView']//a[@role='menuitem' and contains(.,'Inactive')]");
	
	By StatusDropdownSelectionActiveXpath=By.xpath("//*[@id='contractView']//a[@role='menuitem' and contains(.,'Active')]");
	
	By StatusDropdownSelectionAllXpath=By.xpath("//*[@id='contractView']//a[@role='menuitem' and contains(.,'All')]");
	
	public void contractStatusDropdown()
	{		
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//To click on Status button
		click_button("click on Status Drop down", StatusDropdownSelectionXpath);
	
		// to click on Draft
		click_button("click on Status Drop down- choose Draft", StatusDropdownSelectionDraftXpath);
		
		SearchContract("");
			
		if(get_field_value("Contract status drop down", statusDropDown).equalsIgnoreCase("Draft"))
			oReport.AddStepResult("Click Search Engine", "Clicked on First contract when searched with status as Draft and verified that opened contract is in draft state", "PASS");
		else
			oReport.AddStepResult("Click Search Engine","Clicked on First contract when searched with status as Draft and verified that opened contract is not in draft state","FAIL");
			
		// to click on inactive
		click_button("click on Status Drop down", StatusDropdownSelectionXpath);
		
		click_button("click on Status Drop down- choose Inactive", StatusDropdownSelectionInactiveXpath);
		
		SearchContract("");
		
		if(get_field_value("Contract status drop down", statusDropDown).equalsIgnoreCase("Inactive"))
			oReport.AddStepResult("Click Search Engine", "Clicked on First contract when searched with status as 'Inactive' and verified that opened contract is in 'Inactive' state", "PASS");
		else
			oReport.AddStepResult("Click Search Engine","Clicked on First contract when searched with status as 'Inactive' and verified that opened contract is not in 'Inactive' state","FAIL");
		
		
		// to click on Active
		click_button("click on Status Drop down", StatusDropdownSelectionXpath);
		
		click_button("click on Status Drop down- choose Active", StatusDropdownSelectionActiveXpath);
		
		SearchContract("");
		
		if(get_field_value("Contract status drop down", statusDropDown).equalsIgnoreCase("Active"))
			oReport.AddStepResult("Click Search Engine", "Clicked on First contract when searched with status as 'Active' and verified that opened contract is in 'Active' state", "PASS");
		else
			oReport.AddStepResult("Click Search Engine","Clicked on First contract when searched with status as 'Inactive' and verified that opened contract is not in 'Inactive' state","FAIL");
		
		// to click on all Tab	
		click_button("click on Status Drop down", StatusDropdownSelectionXpath);
		
		click_button("click on Status Drop down- choose All", StatusDropdownSelectionAllXpath);
		
		SearchContract("");
		
		oParameters.SetParameters("ContractStatus", get_field_value("Contract status drop down", statusDropDown));
		
		if(oParameters.GetParameters("ContractStatus").equalsIgnoreCase("Draft") || 
				oParameters.GetParameters("ContractStatus").equalsIgnoreCase("Active") ||
					oParameters.GetParameters("ContractStatus").equalsIgnoreCase("Inactive"))
			oReport.AddStepResult("Click Search Engine", "Clicked on First contract when searched with status as 'ALL' and verified that opened contract has status " + oParameters.GetParameters("ContractStatus"), "PASS");
		else
			oReport.AddStepResult("Click Search Engine","Clicked on First contract when searched with status as 'ALL' and verified that contract has not opened","FAIL");
	}
	
	
	By addRateSheetAssociationLinkXpath=By.xpath("//span[contains(.,'Associate Rate Sheet')]");
	
	
	public void addRSALink_View()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(!IsDisplayed(" Add RSA Link is displayed or not", addRateSheetAssociationLinkXpath))
        	oReport.AddStepResult("RSA Link", "In ViewOnly mode Add RSA link not displayed, so non-editable", "PASS");
        else
        	oReport.AddStepResult("RSA Link", "In ViewOnly mode Add RSA link is displayed, so its Editable", "FAIL");
    }	
	
	By pastTabXpath=By.xpath("//*[@id='segmented-button--0']/span[2]");
	
	By FutureTabXpath=By.xpath("//*[@id='segmented-button--2']/span[2]");
	
	public By AllTabXpath=By.xpath("//*[@id='segmented-button--3']/span[2]");
	
	By CurrentTabXpath=By.xpath("//*[@id='segmented-button--1']/span[2]");
		
	
	//To select Past Tab
	public void contractPastTabSelection()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("click on Past Tab", pastTabXpath);
		
		By editedRSA = By.xpath("//li[@ng-repeat='r in relatedRateSheets  | orderBy:predicate:reverse']//span[contains(.,'" +oParameters.GetParameters("healthPlanAliasName_EditWindow") + "')]");
		
		if(IsFieldDisplayed("Edited RSA", editedRSA))
			oReport.AddStepResult("Clicked on past Tab "," Clicked on Past tab and verified that Edited RSA is present","PASS");
		else
			oReport.AddStepResult("Clicked on past Tab "," Clicked on Past tab and verified that Edited RSA is not present","FAIL");
	}
	
	//To select Future Tab
	public void contractFutureTabSelection()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("click on Future Tab", FutureTabXpath);
		
		By editedRSA = By.xpath("//li[@ng-repeat='r in relatedRateSheets  | orderBy:predicate:reverse']//span[contains(.,'" +oParameters.GetParameters("healthPlanAliasName_EditWindow") + "')]");
		
		if(IsFieldDisplayed("Edited RSA", editedRSA))
			oReport.AddStepResult("Clicked on Future Tab "," Clicked on Future tab and verified that Edited RSA is present","PASS");
		else
			oReport.AddStepResult("Clicked on Future Tab "," Clicked on Future tab and verified that Edited RSA is not present","FAIL");
	}
	
	
	// to select All tab
	public void contractAllTabSelection()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("click on all Tab", AllTabXpath);
		
		By editedRSA = By.xpath("//li[@ng-repeat='r in relatedRateSheets  | orderBy:predicate:reverse']//span[contains(.,'" +oParameters.GetParameters("healthPlanAliasName_EditWindow") + "')]");
		
		if(IsFieldDisplayed("Edited RSA", editedRSA))
			oReport.AddStepResult("Clicked on all Tab "," Clicked on All tab and verified that Edited RSA is present","PASS");
		else
			oReport.AddStepResult("Clicked on all Tab "," Clicked on All tab and verified that Edited RSA is not present","FAIL");
	}
		
	
	public void contractCurrentTabSelection()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// to select current tab
		click_button("click on current Tab", CurrentTabXpath);
		
		By editedRSA = By.xpath("//li[@ng-repeat='r in relatedRateSheets  | orderBy:predicate:reverse']//span[contains(.,'" +oParameters.GetParameters("healthPlanAliasName_EditWindow") + "')]");
		
		if(IsFieldDisplayed("Edited RSA", editedRSA))
			oReport.AddStepResult("Clicked on current Tab ", " Clicked on Current tab and verified that Edited RSA is present","PASS");
		else
			oReport.AddStepResult("Clicked on current Tab ", " Clicked on Current tab and verified that Edited RSA is not present","FAIL");
	}
	
	
	By RSAEditButton = By.xpath("//*[@id='contractView']//li[@class='data-row position-relative ng-scope last'][1]//a[contains(@class,'hand-cursor') and contains(@title,'edit')]");
	
	By RSADeleteButton = By.xpath("//*[@id='contractView']//li[@class='data-row position-relative ng-scope last'][1]//a[contains(@class,'hand-cursor') and contains(@title,'delete')]");
	
	
	public void RSAEditDeleteIcon ()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("AssociatedRatesheet", AssociatedRatesheet);
	
		if(IsDisplayed("RSAEditButton", RSAEditButton))
			oReport.AddStepResult("RSA Edit Button", "In ViewOnly mode howered over RSA and Edit icon is visible so its Editable", "FAIL");
		else
			oReport.AddStepResult("RSA Edit Button", "In View Only mode howered over RSA and verified that Edit icon is not present so its not Editable", "PASS");
		
		if(IsDisplayed("RSADeleteButton", RSADeleteButton))
			oReport.AddStepResult("RSA Delete Button", "In View Only mode howered over RSA and could see delete icon is present, so RSA is editable", "FAIL");		
		else
			oReport.AddStepResult("RSA Delete Button", "In View Only mode howered over RSA and verified that Delete icon is not present so its not editable", "PASS");	
	}
	
	
	By addDocumentLinkXpath = By.xpath("//div[@id='contractView']//span[text()='Add Document']");
	
	By NoContractsTermsDocument=By.xpath("//div[@id='contractView']//div[contains(text(),'No Contract Terms Documents found for this contract')]");
	
	By DocumentCount=By.xpath("//div[@id='contractView']//ul[@class='data-list']/li");
	

	public void addDocument()
	{	
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(addDocumentLinkXpath,"Add Document Link");
		
		oParameters.SetParameters("BeforeAddingDocument", String.valueOf(get_table_row_count(DocumentCount)));
		
		if(IsElementDisplayed("Add doc link displayed or not", addDocumentLinkXpath))
		{
			oReport.AddStepResult("Add doc Link"," Add Document Link is displayed, ","PASS");
			fixed_wait_time(2);
			click_button("Click on Add document link", addDocumentLinkXpath);
		}
		else
			oReport.AddStepResult("Add doc Link", "Add Document link is not displayed","FAIL");
	    
		try 
		{
			fixed_wait_time(6);
			Runtime.getRuntime().exec("C:\\CCM\\AutoIt\\FileUpload_AutoIt.exe");
			fixed_wait_time(3);
			waitFor(FirstElement,"First Element");
			oParameters.SetParameters("AfterAddingDocument", String.valueOf(get_table_row_count(DocumentCount)));
			
			if(Integer.parseInt(oParameters.GetParameters("AfterAddingDocument")) > Integer.parseInt(oParameters.GetParameters("BeforeAddingDocument")))
				oReport.AddStepResult("Add doc Link","File successfully gets uploaded","PASS");
			else
				oReport.AddStepResult("Add doc Link","File Upload Failed","FAIL");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			oReport.AddStepResult("Add doc Link","File Upload Failed","FAIL");
		}
		fixed_wait_time(6);
	}

	
	By FirstElementxpath = By.xpath("//*[@id='contractView']//ul[@class='data-list']/li[@class='data-row position-relative ng-scope last' and @ng-repeat='d in contractDocuments'][1]");
	
	By deleteButtonxpath= By.xpath("//*[@id='contractView']//ul[@class='data-list']/li[@class='data-row position-relative ng-scope last' and @ng-repeat='d in contractDocuments'][1]//a[@title='Click to delete this document']");
	
	By FirstElement = By.xpath("//ul[@class='data-list']//li[@class='data-row position-relative ng-scope last' and @ng-repeat='d in contractDocuments']");
		
	By DeleteIconXpath = By.xpath("//*[@id='contractView']//ul[@class='data-list']/li[@class='data-row position-relative ng-scope last' and @ng-repeat='d in contractDocuments'][1]//a[@title='Click to delete this document']");
	
	By noOfdocCount = By.xpath("//ul[@class='data-list']//li[@ng-repeat='d in contractDocuments']");
	
	
	//Actions hover = new Actions(driver);
	
	
	public void deleteDocument()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int noOfDocs = noOfRows(noOfdocCount);
		
		int noOfDocsPostDelete;
		if(IsElementDisplayed("First Element", FirstElement))
		{	
			mouse_hover("First Element", FirstElement);
			dclick_button("DeleteIconXpath", DeleteIconXpath);
			
			if(IsElementDisplayed("Delete Document Popup", noButtonXpath))
			{
				oReport.AddStepResult("Delete Icon", "Clicked on Delete document icon and Popup gets opened", "SCREENSHOT");
				click_button("Click on No", noButtonXpath);
				oReport.AddStepResult("Delete Icon", "Clicked on popup No button and Popup gets closed", "SCREENSHOT"); 
			}	
			else
				oReport.AddStepResult("Delete Icon", "Clicked on Delete document icon, however Popup didnt gets opened", "FAIL");
			
			mouse_hover("First Element", FirstElement);
			dclick_button("DeleteIconXpath", DeleteIconXpath);
			
			if(IsElementDisplayed("Delete Document Popup", noButtonXpath))
			{
				oReport.AddStepResult("Delete Icon", "Clicked on Delete document icon and Popup gets opened", "SCREENSHOT");
				click_button("Click on No", yesButtonXpath);
				oReport.AddStepResult("Delete Icon", "Clicked on popup Yes button and Popup gets closed", "SCREENSHOT"); 
				
				noOfDocsPostDelete = noOfRows(noOfdocCount);
				
				if(noOfDocsPostDelete < noOfDocs)
					oReport.AddStepResult("Delete Doc", "Clicked on Delete document icon and verified that document deleted successfully", "PASS");
				else
					oReport.AddStepResult("Delete Doc", "Clicked on Delete document icon and verified that document not deleted", "FAIL");
			}	
			else
				oReport.AddStepResult("Delete Icon", "Clicked on Delete document icon, however Popup didnt gets opened", "FAIL");
			
			
		}
		else
			oReport.AddStepResult("Delete Icon", "Not Clicked and Popup doesnt gets displayed", "FAIL");	
	}
		
	
	By noButtonXpath = By.xpath("//input[@value = 'No']");
	
	By yesButtonXpath = By.xpath("//input[@value = 'Yes']");
	
	
	public void deleteDocumentconfirmation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		deleteDocument();
	    click_button("Click on No", noButtonXpath);
	    fixed_wait_time(3);
	    oReport.AddStepResult("Click No"," Clicked on delete document popup No button, Document not deleted","PASS");
	            
	    deleteDocument();                
	    click_button("Click on Yes", yesButtonXpath);
	    fixed_wait_time(3);
	    oReport.AddStepResult("Click Yes"," Clicked on delete document Yes button, Document deleted successfully","PASS");
	}        

	
	public void Contracts_SearchContracts()
	{
		login("EDIT");
		changePricingEngine();
	     
		ListAllContract();	
		invalidContractNameSearch();
		validContractNameSearch();
		
		clickAddButton("Add Contract Button", "Add Contract Window",  addContractButtonElement,  AddContractWindowTitle);
		contractDetails();
		clickSaveButton("Add contract window save button", "Add contract window", saveButtonElement, AddContractWindowTitle);
		
		contractStatusDropdown();
		
		SearchContract("");
		
		
		contractPastTabSelection();
		contractFutureTabSelection();
		contractCurrentTabSelection();
		contractAllTabSelection();	
		
		addDocument();
		deleteDocument();
		logout();
	}
	
	
	//View Only VR
	
	By ContractsTab = By.xpath("//*[@id='nav']/ul/li[1]/a");		
    
	By ContractsPageCheck = By.xpath("//li[contains(.,'Contract Information')]");
    
	By LockIconXpath=By.xpath("//*[@id='contractView']//span[@title='Contract is not editable']");
	
	
    public void EditOption()
	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//click_button("Click on LockIcon Button",LockIconXpath );
	    
	    if(IsDisplayed("Checking Lock Icon is displayed or not",  LockIconXpath))
			oReport.AddStepResult("Lock Icon", "In View only mode Lock Icon is displayed, hence its not editable", "PASS");
		else
			oReport.AddStepResult("Lock Icon", "In View only mode Lock Icon not displayed, hence its editable", "FAIL");
	 }
	
 
    By Popoverxpath=By.xpath("//*[@id='contractView']//div[@class='popover-content']");
    
    
    public void ChangeStatus()
	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	if(IsDisplayed("Checking Popover is displayed or not", Popoverxpath))
        	oReport.AddStepResult("Popver displayed", "In View Only mode popover is displayed, hence contract status is editable", "FAIL");
        else
        	oReport.AddStepResult("Popver Not displayed", "In View Only mode poover is displayed, hence contract status is not editable", "PASS");
     }
	
	
	By RateSheetCode = By.xpath("//*[@id='ratesheetSection']//div[@class='pull-right xl-header pad-r-5 ng-binding']");

	By AssociatedRatesheet = By.xpath("//*[@id='contractView']//li[@class='data-row position-relative ng-scope last'][1]");

	By RSARatesheetLink = By.xpath("//*[@id='contractView']//li[@class='data-row position-relative ng-scope last'][1]//a[@class='link ng-binding']");
    
	By headerPath = By.xpath("//ul[@class='data-list']/li[@class='list-header sort']//div[@class='heading ng-scope']");
	
	By contentPath = By.xpath("//ul[@class='data-list']//li[@class='data-row position-relative ng-scope']");
	
	
	public void ClickRateSheetLink()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String str = get_table_row_value(headerPath, contentPath, "Rate Sheet", 0);
		System.out.println(str);
		click_on_link_in_table_row(headerPath, contentPath, "Rate Sheet", 0);
		
		waitFor(RateSheetCode, "RateSheetCode");
		
		String str1 = get_text(RateSheetCode).replace("Code ", "");
    	if(str1.equalsIgnoreCase(str))
    		oReport.AddStepResult("RateSheet Tab","Clicked on Rate Sheet link " + str + " and verified that its navigated to " + str1 + " Ratesheet tab successfully","PASS");
		else
			oReport.AddStepResult("RateSheet Tab","Clicked on Rate Sheet link " + str  + ", however its not navigated to Ratesheet " + str1 + " tab","FAIL");
	}
	
	
    public void navigateContractPlugin() 
	{
    	if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
    	
    	//waitFor(By.xpath("//*[@id='nav']/ul/li[1]/a"),"Contract Plugin");
		navigate_to("Contracts","Add a Qualification Group", ContractsTab,ContractsPageCheck);
	
		if(IsElementDisplayed("Checking Contracts Tab displayed or not", ContractsTab))
			oReport.AddStepResult("Contracts Tab", "Clicked on contracts link and verified that it navigated to contracts plugin", "PASS");
		else
			oReport.AddStepResult("Contracts Tab", "Clicked on contracts link, however its not navigated to Contracts plugin", "FAIL");
	}
		
    
	By AddFilterxpath = By.xpath("//*[@id='filter-asscRateSheets']//span[contains(text(),'Add Filter')]");
	 
	By AddFilter = By.xpath("//*[@id='filter-asscRateSheets']//span[contains(text(),'Add Filter')]");
	 
	 
	public void RSAFilter_View()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("AddFilter", AddFilter))
		{
			 click_button("Add Filter", AddFilterxpath);
			 click_button("All columns", allColumns);
			 click_button("Claim Type", claimTypeButtonElement);
			 click_button("Both Value", bothButtonElement);
			 oReport.AddStepResult("Claim Type", "In RSA filter claim type is selected and Rate sheets are displayed accordingly", "PASS");
			
			click_button("Clear Filter", clearFilterButtonElement);
    	}
    	else
    		oReport.AddStepResult("Claim Type", "In RSA filter claim type is not selected and Rate sheets are not displayed", "FAIL");
    }
    
    
	By viewAddDocumentIconxpath=By.xpath("//*[@id='addContractTermsDoc']/span");
	
	
	public void viewAddDocumentIcon()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Add Document Icon", viewAddDocumentIconxpath))
			oReport.AddStepResult("Add Document Icon","In View only mode Add document link is displayed", "FAIL");
		else
			oReport.AddStepResult("Add Document Icon","In View only mode Add document link is not displayed", "PASS");
	}
		
		
	By documentDownloadIconxpath=By.xpath("//*[@id='contractView']//li[@class='data-row position-relative ng-scope']/a/span[@class='upload-doc-icon doc-icon docx']");
	
	By DocumentDownloadIcon = By.xpath("//*[@id='contractView']//li[@class='data-row position-relative ng-scope']/a/span[@class='upload-doc-icon doc-icon docx'");
	
	By DownloadIcon = By.xpath("//*[@id='contractView']//ul[@class='data-list']/li[@ng-repeat='d in contractDocuments'][1]//span[@class='upload-doc-icon doc-icon java']");
	
	
	public void downloadDocument()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(FirstElementxpath,"First Element");
		if(IsElementDisplayed("First Element", FirstElement))
		{
			mouse_hover("First Element", FirstElement);
			if(IsElementDisplayed("DownloadIcon", DownloadIcon))
			{
				dclick_button("DownloadIcon", DownloadIcon);
				fixed_wait_time(2);
				oReport.AddStepResult("Download Icon", "Clicked and document gets downloaded", "PASS");
			}
			else
			{
				oReport.AddStepResult("Download Icon", "Not Clicked and document not downloaded", "FAIL");
			}
		}
		else
			oReport.AddStepResult("Download Icon", "Not Clicked and document not downloaded", "FAIL");	
	}
	
	
	public void Contracts_ViewOnlyPermission()
	{
		login("VIEW");
		changePricingEngine();
		SearchContract("");
		EditOption();
		ChangeStatus();
		addRSALink_View();
		ClickRateSheetLink();
		//navigateContractPlugin();
		navigate_to("Contracts","Add a Qualification Group", ContractsTab,ContractsPageCheck);
		contractAllTabSelection();
		contractPastTabSelection();
		contractFutureTabSelection();
		contractCurrentTabSelection();
		contractAllTabSelection();
		RSAEditDeleteIcon();
		RSAFilter_View();
		viewAddDocumentIcon();
		logout();
	}
}
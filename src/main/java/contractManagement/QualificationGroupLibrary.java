package contractManagement;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import libraries.*;

/**
 * @author C16137
 *
 */
public class QualificationGroupLibrary  extends CCMLibrary
{
	CommonPages oCommonPages=new CommonPages(driver, oReport,oParameters);
	PPSLibrary oPPSLibrary = new PPSLibrary(driver, oReport,oParameters);
	ExcelData oExcelData = new ExcelData(oParameters);
	
	public QualificationGroupLibrary(WebDriver driver, Report oReport,Parameters oParameters) 
	{
		super(driver, oReport,oParameters);
	}
	
	public void xpathChange()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
		{	
			firstQualifierRespectivePage = firstQualifierRespectivePageDC;
			SearchRatesheetNameXpath = SearchRatesheetNameXpath;	
		}
		else
		{
			firstQualifierRespectivePage = firstQualifierRespectivePageDC;
			SearchRatesheetNameXpath = SearchRatesheetNameXpathDC;
		}
	}
		
	//Ranjith 
	By QualificationGroupTab = By.xpath("//div[@id='nav']//a[contains(text(),'Qualification Groups')]");
		
	By TermExclusionsQualification = By.xpath("//li[@id=\"i[__valueField]\"]/a[contains(.,'Term/E')]");
	
	By firstElementName = By.xpath("//ul[contains(@class,'data-list drillable-list scroll-y-auto')]//li[1]//div[@class='col-lg-11 col-md-11 col-sm-11 ng-binding']");
	
	By searchExpression = By.xpath("//div[@id='qualificationgroupView']//input[contains(@title,'Search Qualifier Expression')]");
	
	By firstQualifierRespectivePage = By.xpath("//div[@id='qualificationgroupView']//div[@class='col-lg-6 col-md-6 col-sm-6 col-xs-12 large-header pad-l-0 ng-binding']");
	
	By firstQualifierRespectivePageDC = By.xpath("//div[@id='qualificationgroupView']//div[@class='col-lg-12 col-md-12 col-sm-12 col-xs-12 large-document-header pad-l-0 hide-overflow ng-binding']");
	
	By firstElement = By.xpath("//ul[contains(@class,'data-list drillable-list scroll-y-auto')]/li[1]");
	
	By QualificationCount = By.xpath("//div[@id='qualificationgroupView']//ul[@class='dropdown-menu']//li");
	
	By DRG_qualificationGroup = By.xpath("//li[@id=\"i[__valueField]\"]/a[contains(.,' DRG Formula Expression')]");
	
	By sectionQualifierGroup = By.xpath("//li[@id=\"i[__valueField]\"]/a[contains(.,'Section Qualification')]");
	
	By Revenue_code_qualificationgroup = By.xpath("//li[@id=\"i[__valueField]\"]/a[contains(.,'Revenue Code Qualification')]");
	
	By StopLossFormula_QualificationGroup = By.xpath("//li[@id=\"i[__valueField]\"]/a[contains(.,'Stop Loss Formula Expression')]");
	
	By StopLoss_QualificationGroup = By.xpath("//li[@id=\"i[__valueField]\"]/a[contains(.,'Stop Loss Qualification')]");
	
	By Service_QualificationGroup = By.xpath("//li[@id=\"i[__valueField]\"]/a[contains(.,' Service Qualification')]");
	
	By Qualificationdropdown = By.xpath("//div[@id='qualificationgroupView']//div[@id='styledDropdown']//a[@class='btn btn-light view-bg-text']//span[@class='dropdown-text hide-overflow ng-binding']");
	
	//This Method is used to Click on select Qualification Drop down and to select a particulat qualification.
	public void QualificationGroup(By QualificationName)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Qualificaion DropDown", Qualificationdropdown);
		
		oParameters.SetParameters("QualificationGroup", get_text(QualificationName));
		
		List<WebElement> TotalQualificationGroup = convertToWebElements(QualificationCount);
		
		for(int i=0; i<=TotalQualificationGroup.size(); i++)
		{
			if(oParameters.GetParameters("QualificationGroup").equalsIgnoreCase(TotalQualificationGroup.get(i).getText()))
			{
				oReport.AddStepResult("Select Qualification dropdown", "Select a Qualification group dropdown and verified that particular Qualification is displayed", "PASS");
				click_button("Qualification Group", QualificationName);
				break;
			}
		}
	}	
	
	//This method is used to validating a search box.
	public void searchBoxValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(isTextBoxBlank("Search Box",searchExpression))
			oReport.AddStepResult("Qualification Group", "selected Qualification Group and verified that searchBox is empty", "PASS");
		else
			oReport.AddStepResult("Qualification Group", "selected Qualification Group and verified that searchBox is not empty", "FAIL");
	}
				
	//This method is used to click on First Qualifier Group.
	public void FirstQualificationGroup(By FirstQualifier)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		
		if(IsElementDisplayed("First Qualifier Gorup", firstElement))
		{	
			oReport.AddStepResult("Qualification Group", "Clicked on qualification group from drop down and verified that all qualifier group is displayed", "PASS");
			
			oParameters.SetParameters("firstElementName", get_text(FirstQualifier));
			click_button("First Qualifier_group", firstElement);
			
			waitFor(firstQualifierRespectivePage, "Respective First Qualifier Group");
					
			if(oParameters.GetParameters("firstElementName").equalsIgnoreCase(get_text(firstQualifierRespectivePage)))
				oReport.AddStepResult("first qualification group", "Clicked on first qualifier group and verified that respective qualifier group is displayed", "PASS");
			else
				oReport.AddStepResult("first qualification group","Clicked on first qualifier group and verified that respective qualifier group is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Qualification Group", "Clicked on qualification group from drop down and verified that all qualifier group is not displayed", "FAIL");
	}
			
	By openedQG= By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 col-xs-12 large-document-header pad-l-0 hide-overflow ng-binding']");
	
	//This Method is used to pass particular Qualifier group into search box.
	public void SearchQualifier(String particularQualifier)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
			
		enter_text_value("SearchExpression",searchExpression,oParameters.GetParameters(particularQualifier));
		performKeyBoardAction("ENTER");
	
		click_button("First Qualifier_group",firstElement);
		
		if(isTextBoxBlank("Search Box",searchExpression) && get_text(firstElement).equalsIgnoreCase(get_text(openedQG)))
			oReport.AddStepResult("Qualification Group", "Passed particular qualifier group to search box and selected respective qualifier group and verified that searchBox is suddenly getting empty", "FAIL");
		else
			oReport.AddStepResult("Qualification Group", "Passed particular qualifier group and selected respective qualifier group and verified that searchBox is not suddenly getting empty", "PASS");
	}
	
	//QG_Search Qualification Group VR methods
	public void QG_SearchQualificationGroups()
	{
		login("EDIT");
		changePricingEngine();
		navigateQualificationPlugin();
		xpathChange();
		
		//Term/Exclusion Expression Qualification.
		QualificationGroup(TermExclusionsQualification);
		searchBoxValidation();
		FirstQualificationGroup(firstElementName);
		SearchQualifier("termExclusions");
		
		//DrgFormula Expression Qualification.
		QualificationGroup(DRG_qualificationGroup);
		searchBoxValidation();
		FirstQualificationGroup(firstElementName);
		SearchQualifier("DRG");
		
		//Section Expression Qualification 
		QualificationGroup(sectionQualifierGroup);
		searchBoxValidation();
		FirstQualificationGroup(firstElementName);
		SearchQualifier("sectionQualification");
		
		//RevenueCode Expression Qualification
		QualificationGroup(Revenue_code_qualificationgroup);
		searchBoxValidation();
		FirstQualificationGroup(firstElementName);
		SearchQualifier("revenueCode");
		
		//stopLossFormula Expression Qualification
		QualificationGroup(StopLossFormula_QualificationGroup);
		searchBoxValidation();
		FirstQualificationGroup(firstElementName);
		SearchQualifier("stopLossFormula");
		
		//StopLoss Qualification
		QualificationGroup(StopLoss_QualificationGroup);
		searchBoxValidation();
		FirstQualificationGroup(firstElementName);
		SearchQualifier("stopLossQualification");
		
		//Service Qualification
		QualificationGroup(Service_QualificationGroup);
		searchBoxValidation();
		FirstQualificationGroup(firstElementName);
		SearchQualifier("serviceQualification");
		logout();
	}
		
	
	
	By QualificationGroupType = By.xpath("//*[@id='expression-group-type']");

	By SaveButton = By.xpath("//*[@id='Id' and @value='Save']");
	
	By AddQGCancelButton = By.xpath("//*[@id='Id' and @value='Cancel']");
	
	By SaveAs = By.xpath("//*[@id='fullFooter']/span/div/button[2]");
	
	By SaveAsButton = By.xpath("//*[@id='fullFooter']/span/div/ul/li/a");
	
	By PopupSaveButton = By.xpath("//div[@id ='dialog']//*[@value='Save']");
	By popupSaveButton = By.xpath("//form[@id='qualifierSaveAsTemplate']//input[@value='Save']");
	By PopupCancelButton = By.xpath("//*[@id='qualifierSaveAsTemplate']/div[4]/input[2]");

	By RatesheetTab = By.xpath("//*[@id='nav']/ul/li[2]/a");
	
	By RatesheetPageCheck = By.xpath("//*[@id='ratesheetView']/div[2]/div/div[2]/div[2]");
	
	By RSSectionCancelButton = By.xpath("//*[@id='button.cancelId']");
	
	By ExclusionsTab = By.xpath("//*[@id='ratesheetSection']/div[2]/div[2]/div/rate-sheet-tabs/div/div/div[1]/ul/li[4]/a");
	
	By saveAsPopUp4 = By.xpath("//*[@name='qualifierSaveAsTemplate']//*[@value='Save']");

	
	public void navigateQualificationPlugin() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
				if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
				{			
					//Adding step result to report
					oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
					return ;
				}
		//waitFor(QualificationGroupTab, "");
		navigate_to("Qualification Groups","Add a Qualification Group", QualificationGroupTab,addQualificationGroup);
		
		if(IsElementDisplayed("addQualificationGroup button", addQualificationGroup))
			oReport.AddStepResult("QG plugin Navigation", "Upon clicking on Qualification group Tab, its Navigated to Qaulification group Plugin successfully", "PASS");	
		else
			oReport.AddStepResult("QG plugin Navigation", "Upon clicking on Qualification group Tab, its not Navigated to Qaulification group Plugin successfully", "FAIL");
	}
	
	
	By addQualificationGroup = By.xpath("//*[@id='qualificationgroupView']//a[@title='Add Qualification Group']");
	
	By addQualificationGroupModalTitleXpath=By.xpath("//*[@id='addQualificationGroupModal']//div[@title='Add Qualification Group']");
	
	
	public void addQualificationGroup()
	{
		click_button("Add Qualification Group Button", addQualificationGroup);
		
		waitFor(addQualificationGroupModalTitleXpath, "");	
	   	oParameters.SetParameters("AddQGWindowTitleValidation", get_field_value("QG Modal Title", addQualificationGroupModalTitleXpath));
		System.out.println("Page Title Validation"+oParameters.GetParameters("AddQGWindowTitleValidation"));
		
		oParameters.SetParameters("AddQGWindowTitle", "Add Qualification Group");
		System.out.println("oParameters.GetParameters(\"AddQGWindowTitle\")"+oParameters.GetParameters("AddQGWindowTitle"));
	
		if(oParameters.GetParameters("AddQGWindowTitle").equals(oParameters.GetParameters("AddQGWindowTitleValidation")))
	   		oReport.AddStepResult("Add Qualification Group", "Upon clicking on Add Qualification group Icon,Add Qualification Group Window gets opened","PASS");	
	   	else
	   		oReport.AddStepResult("Add Qualification Group", "Upon clicking on Add Qualification group Icon,Add Qualification Group Window not opened", "FAIL");		
	}
	
	
	public By QualifierGroupNameXpath=By.xpath(".//*[@id='addQualificationGroupModal']//*[@id='expression-name']");
	
	By OperatorsXpath=By.xpath("//*[@id='addQualificationGroupModal']//*[@id='symbols1']");
	
	By MiscXpath=By.xpath(".//*[@id='addQualificationGroupModal']//*[@id='symbols2']");
	
	public By QualificationGroupBuildResultsXpath=By.xpath(".//*[@id='addQualificationGroupModal']//*[@id='expression']");
	
	public By QualificationGroupTypeXpath=By.xpath("//div[@id='addQualificationGroupModal']//select[@id='expression-group-type']");
	 
	public void QualificationGroupDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, oParameters.GetParameters("QGTermName")+System.currentTimeMillis());
		
		oParameters.SetParameters("QualifierGroupName", get_field_value("Get text value from  Qualification Group Name Field",QualifierGroupNameXpath));
		System.out.println("QualifierGroupName:"+oParameters.GetParameters("QualifierGroupName"));	
		
		
		
		waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"205");
		
		By TermEnvironmentXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Environment')]");
		
		waitFor(TermEnvironmentXpath, "");
		click_button("click Environment", TermEnvironmentXpath);
		
		By ClaimLevel_AllLinesXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Claim Level - All Lines')]");
		
		waitFor(ClaimLevel_AllLinesXpath, "");
		click_button("click Claim Level_All Lines ", ClaimLevel_AllLinesXpath);
	
		By LengthOfStayXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Length of Stay')]");
		
		waitFor(LengthOfStayXpath, "");
		click_button("click Length Of stay", LengthOfStayXpath);
		
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");
	
		click_button("Add QualificationGroup", QualificationGroupBuildResultsXpath);
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("LengthOfStay"));
	}
	
	
	By ClaimLevelCharges_AllLinesXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Claim Level Charges - All Lines')]");
	
	By ChargesXpath=By.xpath("//*[@id='addQualificationGroupModal']//*[@id='environment']//p[text()='Charges']");
	
	By CPT_HCPCS_Xpath=By.xpath(".//*[@id='addQualificationGroupModal']//*[@id='environment']/ul/li[2]/div/ul/li/div/ul/li[4]/div/p");
	
	
	public void ClaimLevelCharges()
	{
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
    
		//waitFor(ClaimLevelCharges_AllLinesXpath, "");
		click_button("click Claim Level_All Lines ", ClaimLevelCharges_AllLinesXpath);
		
		//waitFor(ChargesXpath, "");
		click_button("click charges", ChargesXpath);
		select_option("Select Operator EQ", MiscXpath,"5");
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("Number"));
		
		select_option("Select Operator EQ", MiscXpath,"5");
	
		select_option("Select Operator EQ", OperatorsXpath,"2");
	
		select_option("Select Operator EQ", MiscXpath,"3");
	
		click_button("click HCPCS code", CPT_HCPCS_Xpath);
	
		select_option("Select Operator EQ", MiscXpath,"4");	
	}
	
	
	By Billing_CodesXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Billing Codes']");
	
	By AmountForValueCodeXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Amount for Value Code 54']");
	
	
	public void BillingCode()
	{
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
	
		click_button("click Billing_Codes", Billing_CodesXpath);
		
		waitFor(AmountForValueCodeXpath, "");
		click_button("click Amount For Value in Code", AmountForValueCodeXpath);
		
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("BillingCodeNumber"));
	}
	
	
	By CurrentChargeXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Current Charge-Line Level']");
	
	By ChargeAmountXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Charge Amount']");
	
	By EncounterXpath=By.xpath("//*[@id='environment']//p[text()='Encounter']");
	
	By SpecimenOnlyIndicatorXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Specimen Only Indicator']");

	By ProcedureModifiersXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Procedure Modifiers']");
	
	By ProcedureModifiers_unitsXpath=By.xpath("//*[@id='environment']//li[2]//div/p[@class='ng-scope ng-binding' and text()='Units']");
	
	public void CurrentCharges()
	{
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");

		click_button("click Current Charge", CurrentChargeXpath);
		
		waitFor(ChargeAmountXpath, "");
		click_button("click Amount For Value in Code", ChargeAmountXpath);
	
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("Current charges name"));
	
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
	
		click_button("click Encounter", EncounterXpath);
		
		waitFor(SpecimenOnlyIndicatorXpath, "");
		click_button("click Amount For Value in Code", SpecimenOnlyIndicatorXpath);
	
		select_option("Select Operator EQ", OperatorsXpath,"4");
	
		select_option("Select Operator TRUE", MiscXpath,"6");

		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
	
		click_button("click Procedure modifier", ProcedureModifiersXpath);
		
		waitFor(ProcedureModifiers_unitsXpath, "");
		click_button("click Amount For Value in Code", ProcedureModifiers_unitsXpath);
		
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("Procedure modifier name"));		
	}
		
	
	By CurrentPolicyXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Current Policy']");
	
	By CurrentPolicy_DRGcodeXpath=By.xpath(".//*[@id='addQualificationGroupModal']//*[@id='environment']/ul/li[5]/div/ul/li[2]/div/p");
	
	By PolicyXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Policy']");
	
	By PolicyHealthPlanXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Health Plan']");
	
	By HPAXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Health Plan Alias - HPA']");
	
	
	public void CurrentPolicy()
	{
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
	
		click_button("click Current Policy", CurrentPolicyXpath);
		
		waitFor(PolicyXpath, "");
		click_button("click Policy", PolicyXpath);
		
		waitFor(PolicyHealthPlanXpath, "");
		click_button("click Policy Health Plan", PolicyHealthPlanXpath);
		
		waitFor(HPAXpath, "");
		click_button("click HPA", HPAXpath);
		
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		select_option("Select Misc Quotes ", MiscXpath,"5");
	
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("Current Policy text"));
	
		select_option("Select Misc Quotes ", MiscXpath,"5");
	}
	
	
	By ProcedureGroupDataXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Procedure Group Data']");
	
	By CPT_AllInCodeSetxpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='CPT/HCPCS Procedure-All in Code Set']");
	
	
	public void ProcedureGroupData()
	{
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
		
		click_button("click Procedure Group Data", ProcedureGroupDataXpath);
		
		waitFor(CPT_AllInCodeSetxpath, "");
		click_button("click CPT_AllInCodeSet", CPT_AllInCodeSetxpath);
	
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		select_option("Select Operator TRUE", MiscXpath,"6");
	}
	
	
	By DiagnosisCodeXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Diagnosis Data']");
	
	By Diagnosis_prinicpalCodeSetXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Diagnosis - Principal in Code Set']");
	
	
	public void DiagnosisCode()
	{
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
	
		click_button("click DiagnosisCode", DiagnosisCodeXpath);
		
		waitFor(Diagnosis_prinicpalCodeSetXpath, "");
		click_button("click Diagnosis - prinicpal Code Set", Diagnosis_prinicpalCodeSetXpath);
	
		select_option("Select Operator EQ", OperatorsXpath,"4");
	
		select_option("Select Operator TRUE", MiscXpath,"6");
	}
	
	
	By DRGDataXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='DRG Data']");
	
	By DRGCodeXpath=By.xpath(".//*[@id='addQualificationGroupModal']//*[@id='environment']/ul/li[8]/div/ul/li/div/p");	
	
	
	public void DRGCode()
	{
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
	
		click_button("click DRG Data", DRGDataXpath);
		
		waitFor(DRGCodeXpath, "");
		click_button("click DRG code", DRGCodeXpath);
		
		select_option("Select Operator EQ", OperatorsXpath,"4");
	
		select_option("Select Operator TRUE", MiscXpath,"6");
	}
	
	
	By FeeScheduleDataXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Fee Schedule Data']");
	
	By FeeScheduleXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Fee Schedule']");
	
	
	public void FeeScheduleData()
	{
		enter_text_value("QualificationGroup Build Results", QualificationGroupBuildResultsXpath,"");
	
		click_button("click Fee Schedule Data", FeeScheduleDataXpath);
	
		waitFor(FeeScheduleXpath, "");
		click_button("click Fee schedule", FeeScheduleXpath);
	
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		select_option("Select Misc TRUE", MiscXpath,"6");
	}
	
	
	By ValidationButtonXpath=By.xpath("//*[@id='addQualificationGroupModal']/div//*[@id='validationButton']");
	
	By EditPageValidationButtonXpath = By.xpath("//form[@id='editQualExpressionBuilder']//button[@id='validationButton']");
	
	
	public void ValidationButton()
	{
		//waitFor(ValidationButtonXpath, "");
		click_button("click  Validate Button", ValidationButtonXpath);
		//fixed_wait_time(3);
	}
	
	
	public void EditPageValidationButton()
	{
		//waitFor(EditPageValidationButtonXpath, "");
		click_button("click  Validate Button", EditPageValidationButtonXpath);
		//fixed_wait_time(3);
	}
	
	By ErrorQualificationExpression = By.xpath("//div[@id='dialog']//div[contains(text(),'Error saving qualification Expression')]");
	
	By OKxpath = By.xpath("//div[@id ='dialog']//input[@value='OK']");
	
	public void saveButton()
	{
		click_button("Save Button", SaveButton);
	
		//waitFor(QualifierGroupName_PageTitle);	
		fixed_wait_time(8);
		
		oParameters.SetParameters("QGName_TermPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		waitFor(QualifierGroupName_PageTitle,"");
	
		if(IsDisplayed("Error QG Expression", ErrorQualificationExpression))
		{
			System.out.println("Already QG is  present");
			click_button("Enter OK", OKxpath);
			CancelButton();
		}
		else if(oParameters.GetParameters("QualifierGroupName").equals(oParameters.GetParameters("QGName_TermPageTitle")))
			oReport.AddStepResult(" Term/Exclusion QG Created", "Upon clicking on Save button,respective Terms/Exclusion Qualification group page gets created and displayed", "PASS");	
		else
			oReport.AddStepResult(" Term/Exclusion QG Created", "Upon clicking on Save button,respective Terms/Exclusion Qualification group page not created and displayed", "FAIL");		
	}
			                                                                                                                                                                                                                                                                                                                                                                                                                              

	
	By SearchEngingeXpath=By.xpath("//*[@id='qualificationgroupView']//input[contains(@title,'Search Qualifier Expression ')]");
	
	
	public void SearchQualificationGroup()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QualifierGroupName"));
		fixed_wait_time(2);
		performKeyBoardAction("ENTER");
		
		
		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QualifierGroupName") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);

		oParameters.SetParameters("QGName_TermPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_TermPageTitle"));
	
		if(oParameters.GetParameters("QualifierGroupName").equals(oParameters.GetParameters("QGName_TermPageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
	
	
	public void ModifyQualificationGroup()
	{
		By QualificationGroupBuildResultsXpath=By.xpath("//*[@id='qualificationgroupView']//*[@id='expression']");
	
		enter_text_value_without_clear("Modifying a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, "");
	}
	
	
	By addQualificationGroupxpath=By.xpath("//*[@id='qualificationgroupView']/div/div/div[1]/div/div[2]/a/i[1]");
	
	By QGSaveAsNameXpath = By.xpath("//*[@id='qualifierSaveAsTemplateName']");
	
	//String QGSaveAsName;
	
	By youHaveUnsavedChangesPopup = By.xpath("//div[contains(.,'You have unsaved changes.')][@class='medium-header bold ng-binding']");
	By youHaveUnsavedChangesWindowSaveButton = By.xpath("//div[@id=\"dialog\"]//input[@value='Save']");
	
	public void SaveAsQG() 
	{
		waitFor(SaveAs, "");
		click_button("click Save As caret Button", SaveAs);

		click_button("click Save As Button", SaveAsButton);
	
		if(IsDisplayed("Unsaved changes Popup", youHaveUnsavedChangesPopup))
		{
			click_button("Save Button", youHaveUnsavedChangesWindowSaveButton);
			oReport.AddStepResult("Save As", "You have unsaved changes popup is displayed hence clicked on save button", "SCREENSHOT");
		}
		else
			oReport.AddStepResult("Save As", "You have unsaved changes popup is not displayed", "SCREENSHOT");
				
		if(IsDisplayed("popup", popupSaveButton))	
		{
			oParameters.SetParameters("QGSaveAsName",get_field_value("Get text field value of SaveAs name", QGSaveAsNameXpath));
			
			click_button("click Save As popup Button", popupSaveButton);
			oReport.AddStepResult("Save As", "Save As popup displayed hence clicked on Save button", "PASS");
		}
		else
			oReport.AddStepResult("Save As", "Save As popup not displayed", "FAIL");
		//enter_text_value("SaveAs Name", QGSaveAsNameXpath, oParameters.GetParameters("SaveAsName") + oParameters.GetParameters("QualifierGroupName"));
	
		//oParameters.SetParameters("QGSaveAsName",get_field_value("Get text field value of SaveAs name", QGSaveAsNameXpath));
		//System.out.println(oParameters.GetParameters("QGSaveAsName"));
	
		//click_button("4th save button", saveAsPopUp4);
		
		waitFor(ValidationButtonXpath, "");
		waitFor(FirstElement, "");
		
		oParameters.SetParameters("QGName_TermPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_TermPageTitle"));
	
		if(oParameters.GetParameters("QGSaveAsName").equals(oParameters.GetParameters("QGName_TermPageTitle")))
			oReport.AddStepResult("Terms QG SaveAs Name", "Upon on clicking on SaveAs button in Qualification group, Copy of Terms/Exclusion Qualification group Page not created ", "PASS");	
		else
			oReport.AddStepResult("Terms QG SaveAs Name", "Upon on clicking on SaveAs button in Qualification group, Copy of Terms/Exclusion Qualification group Page not created ", "FAIL");		
	}
	
	
	public void SearchCopyOfQualificationGroup()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("clear text box", SearchEngingeXpath, "");

		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QGSaveAsName"));
		fixed_wait_time(3);
		performKeyBoardAction("ENTER");
		
		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QGSaveAsName") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);
		
		oParameters.SetParameters("QGName_TermPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_TermPageTitle"));
	
		if(oParameters.GetParameters("QGSaveAsName").equals(oParameters.GetParameters("QGName_TermPageTitle")))
	    	oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
	    else
	    	oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
	
	
	public void CancelQualificationGroupDetails()
	{
		waitFor(QualifierGroupNameXpath, "");
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath,oParameters.GetParameters("QGTermName")+System.currentTimeMillis());
	
		By ClaimLevel_AllLinesXpath=By.xpath(".//*[@id='addQualificationGroupModal']//*[@id='environment']/ul/li[1]/div/p");
		waitFor(ClaimLevel_AllLinesXpath, "");
		click_button("click Claim Level_All Lines ", ClaimLevel_AllLinesXpath);
		
		By LengthOfStayXpath=By.xpath("//*[@id='addQualificationGroupModal']//*[@id='environment']/ul/li[1]/div/ul/li[11]/div/p");
		
		waitFor(LengthOfStayXpath, "");
		click_button("click Length Of stay", LengthOfStayXpath);
		
		By OperatorsXpath=By.xpath("//*[@id='addQualificationGroupModal']//*[@id='symbols1']");
		
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");
				
		By QualificationGroupBuildResultsXpath=By.xpath("//*[@id='addQualificationGroupModal']//*[@id='expression']");
		
		click_button("Add QualificationGroup", QualificationGroupBuildResultsXpath);
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("LengthOfStay"));
	}
	
	
	 By AddQGPopupCancelButton = By.xpath("//*[@id='dialog_buttons']/input[2]");
	
	 By AddQGpopupDiscardButton = By.xpath("//*[@id='dialog_buttons']/input[1]");
	
	
	public void CancelButton()
	{
		click_button("Cancel Button", AddQGCancelButton);
        oReport.AddStepResult("Cancel Button", "Upon clicking on Cancel button,a Popup appears with cancel and discard options", "PASS");
        
        //To click on Popup cancel Button
        List<WebElement>  we1 = convertToWebElements(AddQGPopupCancelButton); 
        we1.get(1).click();
        oReport.AddStepResult("Cancel Button", "Upon clicking on Cancel button within popup box,popup gets disappered without cancelling the Qualification group", "PASS");
        
        //To click again on add contract cancel button
        click_button("Cancel Button", AddQGCancelButton);
        
        //To click on Popup Discard button
        List<WebElement>  we2 = convertToWebElements(AddQGpopupDiscardButton); 
        we2.get(1).click();
        oReport.AddStepResult("Discard Button", "Upon clicking on discard button within popup box,popup gets disappered without saving the Qualification group", "PASS");
	}
	

	By AddRateSheet = By.xpath("//*[@id='ratesheetSection']//a[@title='Add Rate Sheet']");
	
	
	public void navigateRatesheetPlugin() 
	{
		waitFor(RatesheetTab, "");
		navigate_to("RateSheet","RateSheet page displayed", RatesheetTab,AddRateSheet);
		
		if(IsElementDisplayed("AddRateSheet button", AddRateSheet) == true)
			oReport.AddStepResult("Ratesheet plugin Navigation", "Upon clicking on Ratesheet Tab, its Navigated to  Ratesheet plugin successfully", "PASS");	
		else
			oReport.AddStepResult("Ratesheet plugin Navigation", "Upon clicking on Ratesheet Tab,Not Navigated to Ratesheet Plugin ", "FAIL");
	}	
	
	
	
	By RatesheetSearchEngine = By.xpath("//*[@id='ratesheetSection']//input[@placeholder='Search Rate Sheets']");

	By RatesheetNamePageTitle = By.xpath("//*[@id='ratesheetSection']//div[@class='document-title-bar ng-scope']/div[1]");
	
	By SearchRatesheetNameXpath=By.xpath("//div[@class='ng-binding'][contains(.,'"+oParameters.GetParameters("RatesheetName")+"')]");
	
	By SearchRatesheetNameXpathDC=By.xpath("//div[@class='ng-binding' and contains(.,'"+oParameters.GetParameters("RatesheetName")+"')]");
	
	By RatesheetSearchEngineXpath=By.xpath("//*[@id='ratesheetSection']//input[@placeholder='Search Rate Sheets']");
	
	
	public void SearchRateSheet()
	{
		
		System.out.println(oParameters.GetParameters("RatesheetName"));
	
		enter_text_value("Enter text on Search Text Box", RatesheetSearchEngineXpath, oParameters.GetParameters("RatesheetName"));
		performKeyBoardAction("ENTER");
		System.out.println(SearchRatesheetNameXpath);
		click_button("click on particular Ratesheet ", SearchRatesheetNameXpath);
		
		waitFor(RatesheetNamePageTitle, "");
		oParameters.SetParameters("RatesheetPageTitle",get_field_value("Ratesheet Name Page Title", RatesheetNamePageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("RatesheetPageTitle"));
		
	    if(oParameters.GetParameters("RatesheetName").equals(oParameters.GetParameters("RatesheetPageTitle")))
	    	oReport.AddStepResult("Search RateSheet", "Once Ratesheet plugin gets opened, a particular ratesheet name is Searched and clicked on that particular Ratesheet where respective page gets opened","PASS");	
	    else
	    	oReport.AddStepResult("Search RateSheet", "Once Ratesheet plugin gets opened, a particular ratesheet name is Searched and clicked on that particular Ratesheet where respective page not opened","FAIL");		
	}
	
	
	By TermsCount = By.xpath("//ratesheet-sections-terms//ul[@class='data-list']/li[2]//span[@class='pull-right small ng-binding' and contains(text(),'Terms')]");
	
	
	public void TermsBeforeCount()
	{
		oParameters.SetParameters("RSTermsBeforeCount", get_field_value("Terms Count", TermsCount).replace(" Terms",""));
		System.out.println("RSTermsBeforeCount:"+oParameters.GetParameters("RSTermsBeforeCount"));
	}
	

	By RSAddTermButtonForExistXpath=By.xpath("//ratesheet-sections-terms//ul[@class='data-list']/li[2]//a[@title='Add Terms']");
	
	Actions hover = new Actions(driver);
	
	public void AddTerm()
	{
		waitFor(RSAddTermButtonForExistXpath, "");
		
		if(IsElementDisplayed("Checking Add term button for existing terms is displayed or not",  RSAddTermButtonForExistXpath) == true)
		{
			dclick_button("Add Term Button", RSAddTermButtonForExistXpath);
			oReport.AddStepResult("Add Term", "Upon clicking on Add Term Icon in Ratesheet,Add term window gets opened","PASS");	
		}
		else
		{
			System.out.println("No add term icon present");
			oReport.AddStepResult("Add Term", "Upon clicking on Add Term Icon in Ratesheet,Add term window not opened","FAIL");
		}
	}

	
	By TermNameinTermSectionxpath = By.xpath("//*[@id='addEditTermForm']//input[@title='']");
	
	
	public void AddTermSectionDetails()
	{
		//waitFor(TermNameinTermSectionxpath, "");
		enter_text_value_jscript("Enter a Term name", TermNameinTermSectionxpath, oParameters.GetParameters("Term name")+System.currentTimeMillis());	
	}
	

	By TermQualifierGroupNameXpath=By.xpath("//*[@id='addEditTermForm']//input[@id='qualificationGroup' and  @trigger-search-event='keypress']");
	
	
	public void SearchQualificationGroupInTerm()
	{
		enter_text_value_jscript("Enter text on Search Text Box", TermQualifierGroupNameXpath, oParameters.GetParameters("QGSaveAsName"));
		fixed_wait_time(2);
		performKeyBoardAction("ENTER");	
		//fixed_wait_time(3);
		//performKeyBoardAction("ENTER");
	}
	
	
	By TermRateTypeXpath=By.xpath("//*[@id='addEditTermForm']//select[@id='' and @ng-model='model']");
	
	By TermRateAmountXpath=By.xpath("//*[@id='rateAmount' and @name='rateAmount']");
	
	
	public void RateTypeInTerm()
	{
		waitFor(TermRateTypeXpath, "");
		select_option("Choose a Rate Type", TermRateTypeXpath, "12");
		
		waitFor(TermRateAmountXpath, "");
		enter_text_value("Enter a value for Rate amount", TermRateAmountXpath, "50");
		fixed_wait_time(3);
	}
	
	
	By RSTermSaveButton = By.xpath("//*[@id='button.saveId']");
	
	
	public void RSTermSaveButton()
	{
		waitFor(RSTermSaveButton, "");
		System.out.println("save button enabled:"+IsElementEnabled("Term save button", RSTermSaveButton));
		
		click_button("click on Term Save button ", RSTermSaveButton);
		fixed_wait_time(5);	
		
		
		//oReport.AddStepResult("Term Created", "Upon clicking on Save button,Term gets added successfully", "PASS");	
	}
	
	
	public void TermsAfterCount()
	{
		waitFor(TermsCount, "");
		oParameters.SetParameters("RSTermsAfterCount", get_field_value("Terms Count", TermsCount).replace(" Terms",""));
		System.out.println("RSTermsAfterCount:"+oParameters.GetParameters("RSTermsAfterCount"));
		
		if (Integer.parseInt(oParameters.GetParameters("RSTermsAfterCount")) == Integer.parseInt(oParameters.GetParameters("RSTermsBeforeCount")))
			waitFor(TermsCount, "");
		
		oParameters.SetParameters("RSTermsAfterCount", get_field_value("Terms Count", TermsCount).replace(" Terms",""));
			System.out.println("RSTermsAfterCount:"+oParameters.GetParameters("RSTermsAfterCount"));
		
			if (Integer.parseInt(oParameters.GetParameters("RSTermsAfterCount")) > Integer.parseInt(oParameters.GetParameters("RSTermsBeforeCount")))
				oReport.AddStepResult("Term Created", "Upon clicking on Save button for adding Ratesheet Term, Term gets added successfully", "PASS");
			else
				oReport.AddStepResult("Term not Created", "Upon clicking on Save button for adding Ratesheet Term, Term not added successfully", "FAIL");
	}
	
	public void ExclusionTabNavigation() 
	{
		waitFor(ExclusionsTab, "");
		navigate_to("Exclusions Tab","Add Exclusion button", ExclusionsTab,RSAddExclusionTermButtonForExistXpath);
		
		waitFor(RSAddExclusionTermButtonForExistXpath, "");
	    if(IsElementDisplayed("RS Add ExclusionTerm Button", RSAddExclusionTermButtonForExistXpath)== true)
	    	oReport.AddStepResult("Exclusion Tab", "Upon clicking on Exclusion Tab,its Navigated to Exclusion tab successfully", "PASS");	
	    else
	    	oReport.AddStepResult("Exclusion Tab", "Upon clicking on Exclusion Tab, Not Navigated to Exclusion tab ", "FAIL");		
	}
	
	
	By RSAddExclusionTermButtonForExistXpath=By.xpath("//*[@id='sec-term-list']//a[(text()='Add Exclusion Term')]");
		
	//remove Try catch block
	public void  AddExclusion()
	{
		if(IsElementDisplayed("Checking Add term button for existing terms is displayed or not",  RSAddExclusionTermButtonForExistXpath) == true)
		{
				waitFor(RSAddExclusionTermButtonForExistXpath, "");
				click_button("Click on Add Exclusion button", RSAddExclusionTermButtonForExistXpath);
				fixed_wait_time(3);
				oReport.AddStepResult("Add Exclusion", "Upon clicking on Add Exclsuion icon,Add Exclusion window gets opened","PASS");
		}
		else	
		{
			oReport.AddStepResult("Add Exclusion", "Upon clicking on Add Exclsuion icon,Add Exclusion window not opened","FAIL");
		}
	}
	
	
	By ExclusionTermNameForExist = By.xpath("//*[@id='showExclusionModal']//input[@id='termName']");
	
	
	public void AddExclusionDetails()
	{
		enter_text_value("Enter a Exclusion TermName", ExclusionTermNameForExist,oParameters.GetParameters("ExclusionTermName")+System.currentTimeMillis());	
	}
	
	
	public void SearchQualificationGroupInExlusions()
	{
		By ExclusinQualifierGroupNameXpath=By.xpath("//*[@id='showExclusionModal']//input[@id='qualificationGroup']"); 
		
		enter_text_value("Enter text on Search Text Box", ExclusinQualifierGroupNameXpath,oParameters.GetParameters("QGSaveAsName"));
		fixed_wait_time(3);
		performKeyBoardAction("ENTER");	
		fixed_wait_time(1);
		performKeyBoardAction("ENTER");
	}
	
	
	By ExclusionRateTypeXpath=By.xpath("//*[@id='addEditExclusionTerm']//select[@id='']");
	
	By ExclusionPercentageXpath=By.xpath("//*[@id='rateAmount']");
	
	By ExclusionPercentageBasisXpath=By.xpath("//select[@id='percentageBasis']");
	
	
	public void RateTypeInExclusion()
	{
		waitFor(ExclusionRateTypeXpath, "");
		select_option("Choose a Rate Type", ExclusionRateTypeXpath, "4");
		
		waitFor(ExclusionPercentageXpath, "");
		enter_text_value("Enter a value for Rate amount", ExclusionPercentageXpath, "50");
		
		waitFor(ExclusionPercentageBasisXpath, "");
		select_option("Choose a Percentage Basis Type", ExclusionPercentageBasisXpath, "0");
	}
	
	
	By RSExclusionTermSaveButtonXpath=By.xpath("//*[@id='showExclusionModal']//input[@id='button.saveId']");
	
	
    public void RSExclusionTermSaveButton()
	{
    	if(IsElementEnabled("RSA Exclusion Term Save Button", RSExclusionTermSaveButtonXpath))
    	{
    		click_button("click on save button ", RSExclusionTermSaveButtonXpath);
    		oReport.AddStepResult("Exclusion Created", "Upon clicking on save button, Exclusion gets added successfully", "PASS");
    	}
    	else
    		oReport.AddStepResult("Exclusion Created", "Upon clicking on save button, Exclusion gets not added", "FAIL");
    	//Report is not loaded properly
		fixed_wait_time(3);
	    
		oReport.AddStepResult("Exclusion Created", "Upon clicking on save button, Exclusion gets added successfully", "PASS");
	}
    
    
    By UsageTableName=By.xpath("//*[@id='qualificationgroupView']//h3[@class='ng-binding']");

    By TermUsageTabXpath=By.xpath("//*[@id='qualificationgroupView']//a[text()='Term Usage']");
        
    By RateSheetCode=By.xpath("//*[@id='ratesheetSection']//div[@class='pull-right xl-header pad-r-5 ng-binding']");
  
   
    public void TermUsageTab() 
    {
    	navigate_to("Term Usage Tab","Term Usage Table Name", TermUsageTabXpath, SectionRateSheetCodeLink);
    	fixed_wait_time(3);
    	if(IsElementDisplayed("Section RateSheetCode Link", SectionRateSheetCodeLink))
		{	
    		oReport.AddStepResult("Term usage Tab", "Upon clicking on Term usage Tab in Qualification group,its Navigated to Term Usage tab successfully", "PASS");
		    mouse_hover("SectionRateSheetCode", SectionRateSheetCode); 
		    dclick_button("SectionRateSheetCodeLink", SectionRateSheetCodeLink);
		   		    	
	    	if((get_field_value("RateSheetCode", RateSheetCode)).contains(get_field_value("SectionRateSheetCodeLink", SectionRateSheetCodeLink)))
	    		oReport.AddStepResult("RateSheet Tab","Upon clicking on RateSheet code link in Term usage Tab,its Navigated to Ratesheet tab successfully","PASS");
    		else
    			oReport.AddStepResult("RateSheet Tab","Upon clicking on RateSheet code link in Term usage Tab,its not Navigated to Ratesheet tab","FAIL");
    	}
    	else
			oReport.AddStepResult("Term usage Tab","Upon clicking on Term usage Tab in Qualification group,Not Navigated to Term usage tab", "FAIL");		
	}
 
	
    By ExclusionUsageTabXpath=By.xpath("//*[@id='qualificationgroupView']//a[text()='Exclusion Usage']");
    
    
    public void ExclusionUsageTab() 
    {  
    	navigate_to("Exclusion Usage Tab","Exclusion Usage Table Name", ExclusionUsageTabXpath, SectionRateSheetCodeLink);
    	fixed_wait_time(3);
	    if(IsElementDisplayed("Section RateSheetCode Link", SectionRateSheetCodeLink))
	    {
	    	oReport.AddStepResult("Exclusion usage Tab", "Navigated to Exclusion Usage tab successfully", "PASS");
    		mouse_hover("SectionRateSheetCode", SectionRateSheetCode);
    		dclick_button("SectionRateSheetCodeLink", SectionRateSheetCodeLink);
    		
    		fixed_wait_time(5);
	    	if((get_field_value("RateSheetCode", RateSheetCode)).contains(get_field_value("SectionRateSheetCodeLink", SectionRateSheetCodeLink)))
	    		oReport.AddStepResult("RateSheet Tab","Upon clicking on RateSheet code link in Term usage Tab,its Navigated to Ratesheet tab successfully","PASS");
    		else
    			oReport.AddStepResult("RateSheet Tab","Upon clicking on RateSheet code link in Term usage Tab,its not Navigated to Ratesheet tab ","FAIL");
	    }
	    else
			oReport.AddStepResult("Exclusion usage Tab","Upon clicking on Exclusion usage Tab,Not Navigated to Exclusion usage tab", "FAIL");		
	}
	
    By dialysisUsage = By.xpath("//*[@id='qualificationgroupView']//a[text()='Dialysis Usage']");
    By dialysisUsageTable = By.xpath("//div[@class='col-lg-12 col-md-12 col-sm-12 portal-tab-content scroll-y-auto ng-scope']//table[@class='table table-condensed table-striped table-responsive table-hover data-table data-grid']");
    //Method to navigate to Dialysis Tab
    public void dialysisUsageTab()
    {
    	click_button("Dialysis Tab", dialysisUsage);
    	fixed_wait_time(5);
    	if(IsDisplayed("Dialysis Usage Table", dialysisUsageTable))
    		oReport.AddStepResult("Dialysis Usage Tab", "Clicked in Dialysis Usage tab, navigated to dialysis usage tab and verified that dialysis usage table is displayed", "PASS");
    	else
    		oReport.AddStepResult("Dialysis Usage Tab", "Clicked in Dialysis Usage tab and not navigated to dialysis usage tab", "FAIL");
    	
    }
    
    
	By ExportButtonXpath=By.xpath("//*[@id='qualificationgroupView']//a[@title='Export']");
	By recordsInPortal = By.xpath("//div[@class='pull-right -align-right helper-text ng-binding']");
    //Method for export validation
	public void ExportButton(String Tab1, String Tab2)
	{
		By tabs = By.xpath("//div[@tabs='tabs']//li[@class='portal-tab-pane ng-scope']");
		By usageTab1=By.xpath("//*[@id='qualificationgroupView']//a[text()='"+Tab1+"']");
		By usageTab2=By.xpath("//*[@id='qualificationgroupView']//a[text()='"+Tab2+"']");

    	if(IsDisplayed("Export Button", ExportButtonXpath))
    	{
    		click_button("click on Export button ", ExportButtonXpath);
    		fixed_wait_time(10);
    		String exportFileName = get_text(openedQG).replaceAll("\\s+", "");
    		oParameters.SetParameters("ExportedFileName", exportFileName+".xlsx");
    		if(isFileDownloaded(oParameters.GetParameters("ExportedFileName")))
    		{
    			if(!IsDisplayed(Tab1, usageTab1) && !IsDisplayed(Tab2, usageTab2))
    			{
    				oReport.AddStepResult("Usage Tab validation", "Usage tab is not displayed", "FAIL");
    			}
    			else
    			{
	    			if(!Tab1.isEmpty() && !Tab2.isEmpty()) 
	    			{
	    				//Method for Tab1 Usage
	        			click_button(Tab1+" "+"Tab", usageTab1);	
	        			fixed_wait_time(20);
	        			int fileRowCount = oExcelData.getRowCount1("Term Usage",(oParameters.GetParameters("downloadFilepath")+"/"+exportFileName+".xlsx"))-2;	 
	        			oParameters.SetParameters("noOfRecordsInPortal", get_text(recordsInPortal).replace("Terms","").trim());
	        			if(fileRowCount == Integer.parseInt(oParameters.GetParameters("noOfRecordsInPortal")))
	        				oReport.AddStepResult(Tab1+" "+"Tab", get_text(openedQG)+".xlsx"+" "+" downloaded file has "+Tab1+" sheet and validated that number of records in portal is equal to number of records in downloaded file", "Pass");
	        			else
	        				oReport.AddStepResult(Tab1+" "+"Tab", "Qualification Group file is downloaded without any error and validated that number of records in portal is not equal to number of records in downloaded file", "FAIL");
	        		
	        			//Method for Tab1 Usage
	        			click_button(Tab2+" "+"Tab", usageTab2);
	        			fixed_wait_time(20);
	        			int fileRowCount1 = oExcelData.getRowCount1("Exclusion Usage",(oParameters.GetParameters("downloadFilepath")+"/"+exportFileName+".xlsx"))-2;	 
	        			oParameters.SetParameters("noOfRecordsInPortal", get_text(recordsInPortal).replace("Exclusions","").trim());
	        			if(fileRowCount1 == Integer.parseInt(oParameters.GetParameters("noOfRecordsInPortal")))
	        				oReport.AddStepResult(Tab2+" "+"Tab", get_text(openedQG)+".xlsx"+" "+" downloaded file has "+Tab2+"sheet and validated that number of records in portal is equal to number of records in downloaded file", "Pass");
	        			else
	        				oReport.AddStepResult(Tab2+" "+"Tab", "Qualification Group file is downloaded without any error and validated that number of records in portal is not equal to number of records in downloaded file", "FAIL");
	    			}
	    			else if(!Tab1.isEmpty() && Tab2.isEmpty())
	    			{
	    				//Method for Terms Usage
	        			click_button(Tab1+" "+"Tab", usageTab1);	
	        			fixed_wait_time(20);
	        			int fileRowCount = oExcelData.getRowCount1("Term Usage",(oParameters.GetParameters("downloadFilepath")+"/"+exportFileName+".xlsx"))-2;	 
	        			oParameters.SetParameters("noOfRecordsInPortal", get_text(recordsInPortal).replace("Dialysis Usage","").trim());
	        			if(fileRowCount == Integer.parseInt(oParameters.GetParameters("noOfRecordsInPortal")))
	        				oReport.AddStepResult(Tab1+" "+"Tab", get_text(openedQG)+".xlsx"+" "+" downloaded file has  "+Tab1+" sheet and validated that number of records in portal is equal to number of records in downloaded file", "Pass");
	        			else
	        				oReport.AddStepResult(Tab1+" "+"Tab", "Qualification Group file is downloaded without any error and validated that number of records in portal is not equal to number of records in downloaded file", "FAIL");
	    			}
    			}
    		}	
    	}
	}
    
	//This Method is used for entering QG Details.
	public void QualificationDetails(String i)
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, oParameters.GetParameters("QualificationGroupName"+i));
		oParameters.SetParameters("QualifierGroupName", get_field_value("", QualifierGroupNameXpath));
		waitFor(QualificationGroupTypeXpath, "");
		fixed_wait_time(1);
		selectOption(QualificationGroupTypeXpath,"visibletext",oParameters.GetParameters("QualificationGroupType"+i));
		fixed_wait_time(2);
		click_button("", QualificationGroupBuildResultsXpath);
		enter_text_value("Qualification Group Name", QualificationGroupBuildResultsXpath, oParameters.GetParameters("QualificationGroupExpression"+i));
		
	}
	
    public void QG_CRUDTermExclusion()
    {
    	login("EDIT");
    	changePricingEngine(); 
    	xpathChange();
    	
    	navigateQualificationPlugin();
    	addQualificationGroup();
    	QualificationGroupDetails();
    	ValidationButton();
    	/*ClaimLevelCharges();
    	ValidationButton();
    	
    	BillingCode();
    	ValidationButton();
    	CurrentCharges();
    	ValidationButton();	     
    	CurrentPolicy();
    	ValidationButton();
    	ProcedureGroupData();
    	ValidationButton();
    	DiagnosisCode();
    	ValidationButton();
    	DRGCode();
    	ValidationButton();
    	
    	FeeScheduleData();
    	ValidationButton(); */       
    	saveButton();
    	
    	SearchQualificationGroup();
    	ModifyQualificationGroup();
    	EditPageValidationButton();
    	SaveAsQG(); 
    
    	DeleteQG();
    	DRGCancelScenario();
    	DeleteQG();
    	DRGDeleteScenario();
    	addQualificationGroup();
    	CancelQualificationGroupDetails();
    	ValidationButton();
    	CancelButton();	 
    	
    	navigateRatesheetPlugin();
    	SearchRateSheet();
    	TermsBeforeCount();
    	AddTerm();
    	AddTermSectionDetails();
    	SearchQualificationGroupInTerm();
    	RateTypeInTerm();
    	RSTermSaveButton();
    	TermsAfterCount();
    	ExclusionTabNavigation();
    	AddExclusion();
    	AddExclusionDetails();
    	SearchQualificationGroupInExlusions();
    	RateTypeInExclusion();
    	RSExclusionTermSaveButton();
    	navigateQualificationPlugin();
    	SearchCopyOfQualificationGroup();
    	TermUsageTab();
    	navigateQualificationPlugin();
    	SearchCopyOfQualificationGroup();
    	ExclusionUsageTab();
    	navigateQualificationPlugin();
    	SearchCopyOfQualificationGroup();
    	//ExportButton(); 
    	logout();
    	
    }
    
    
    

//QG-DRG Formula Scripts Begins here
        
   // By QualificationGroupTypeXpath=By.xpath(".//*[@id='expression-group-type']");
    //By CurrentPolicyXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Current Policy')]");
    //By TotalChargeAmountXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Total Charge Amount']");
    
	public void CancelQG_DRGformulaDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, oParameters.GetParameters("QG_DRGName")+System.currentTimeMillis());
		
		//waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"275");
		
		//waitFor(CurrentPolicyXpath, "");
		click_button("click on Current Policy ", CurrentPolicyXpath);
		
		click_button("click on Total Charge Amount ", TotalChargeAmountXpath);
		select_option("Select Operator EQ", OperatorsXpath,"4");
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("TotalChargeAmountNumber"));		
	}

	
	By TermEnvironmentXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Environment')]");
	
	//By CurrentPolicyXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Current Policy')]");
	
	By TotalChargeAmountXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Total Charge Amount']");
	
	public void AddQG_DRGformulaDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, oParameters.GetParameters("QG_DRGName")+System.currentTimeMillis());
	
		oParameters.SetParameters("QualifierGroupName_DRG", get_field_value("Get text value from  Qualification Group Name Field",QualifierGroupNameXpath));
		System.out.println(oParameters.GetParameters("QualifierGroupName_DRG"));	
	
		//waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"275");
		
		//waitFor(TermEnvironmentXpath, "");
		click_button("click Environment", TermEnvironmentXpath);
	
		//waitFor(CurrentPolicyXpath, "");
		click_button("click on Current Policy", CurrentPolicyXpath);
	
		
		
		//waitFor(TotalChargeAmountXpath, "");
		click_button("click on Total Charge Amount", TotalChargeAmountXpath);
		
		//waitFor(OperatorsXpath, "");
		select_option("Select Operator MISC", OperatorsXpath,"5");
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("TotalChargeAmount"));
		
		oReport.AddStepResult("Expression Error", "Expression is improper,hence error message gets displayed", "PASS");	
		
		System.out.println("Improper expression");
		click_button("click  Validate Button", ValidationButtonXpath);
	
		enter_text_value("QualificationGroupBuildResults", QualificationGroupBuildResultsXpath,"");   

		click_button("click on Total Charge Amount", TotalChargeAmountXpath);
		select_option("Select Operator EQ", OperatorsXpath,"4");	
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("TotalChargeAmount"));
	}
	
	By PPS_DRGXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'PPS DRG')]");
	
	By DRGUserRatesXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'DRG User Rates')]");
	
	By WeightXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Weight')]");
	
	public void QG_DRGFormula_PPSDRG()
	{
		enter_text_value("QualificationGroupBuildResults", QualificationGroupBuildResultsXpath,"");   
	
		//waitFor(PPS_DRGXpath, "");
		click_button("click On PPS DRG", PPS_DRGXpath);
	
		//waitFor(DRGUserRatesXpath, "");
		click_button("click On PPS DRG", DRGUserRatesXpath);
		
		//waitFor(WeightXpath, "");	
		click_button("click On PPS DRG", WeightXpath);
	
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("PPS_DRG"));
	}
	
	By DRGProviderValuesXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'DRG Provider Values')]");
	By BaseRateXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Base Rate']");
	
	public void QG_DRGFormulaDRGProviderValues()
	{
		enter_text_value("QualificationGroupBuildResults", QualificationGroupBuildResultsXpath,"");   
		
		//waitFor(DRGProviderValuesXpath, "");
		click_button("click On PPS DRG", DRGProviderValuesXpath);
		
		//waitFor(BaseRateXpath, "");
		click_button("click On PPS DRG", BaseRateXpath);	
	}

	
	By QualifierGroupName_PageTitlexpath = By.xpath("//*[@id='qualificationgroupView']//div[@class='document-title-bar pad-l-10']/div[1]");
	
	
	public void QG_DRGsaveButton()
	{
		click_button("Save Button", SaveButton);
		
		waitFor(QualifierGroupName_PageTitlexpath, "");
		oParameters.SetParameters("QGName_DRGPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_DRGPageTitle"));
	
		if(oParameters.GetParameters("QualifierGroupName_DRG").equals(oParameters.GetParameters("QGName_DRGPageTitle")))
			oReport.AddStepResult(" DRG QG Created", "Upon clicking on Save button,respective DRG Formula Qualification group page gets created and displayed", "PASS");	
		else
			oReport.AddStepResult(" DRG QG Created", "Upon clicking on Save button,respective DRG Formula Qualification group page gets created but not displayed", "FAIL");		
    	//fixed_wait_time(4);
	}

	
	By QualifierGroupName_PageTitle = By.xpath("//*[@id='qualificationgroupView']//div[@class='document-title-bar pad-l-10']/div[1]");

	
	public void SearchQG_DRG()
	{
		//waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QualifierGroupName_DRG"));
		performKeyBoardAction("ENTER");
		
		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QualifierGroupName_DRG") + "')]");
		
		click_button("click on particular QG ", SearchQGNameXpath);
		
		waitFor(QualifierGroupName_PageTitle, "");
		oParameters.SetParameters("QGName_DRGPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_DRGPageTitle"));
	
		if(oParameters.GetParameters("QualifierGroupName_DRG").equals(oParameters.GetParameters("QGName_DRGPageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}	

	//By QualificationGroupBuildResultsXpath=By.xpath(".//*[@id='qualificationgroupView']//*[@id='expression']");
	
	public void ModifyQG_DRGFormulaExpression()
	{
		enter_text_value_without_clear("Modifying a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, "");
	}

	
	public void SaveAsQG_DRG() 
	{	
		//waitFor(SaveAs, "");
		click_button("click Save As caret Button", SaveAs);
		
		click_button("click Save As Button", SaveAsButton);
		
		if(IsElementDisplayed("popup", PopupSaveButton))	
			click_button("click Save As popup Button", PopupSaveButton);
		else
			System.out.println("Pop up not appear");
	
		enter_text_value("SaveAs Name", QGSaveAsNameXpath, oParameters.GetParameters("SaveAsName")+ oParameters.GetParameters("QualifierGroupName_DRG"));
	
		oParameters.SetParameters("QG_DRGSaveAsName", get_field_value("Get text field value of SaveAs name", QGSaveAsNameXpath));
		System.out.println(oParameters.GetParameters("QG_DRGSaveAsName"));
		click_button("4th save button", saveAsPopUp4);
	
		waitFor(ValidationButtonXpath, "");
		waitFor(FirstElement, "");
	
		oParameters.SetParameters("QGName_DRGPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_DRGPageTitle"));

		if(oParameters.GetParameters("QG_DRGSaveAsName").equals(oParameters.GetParameters("QGName_DRGPageTitle")))
			oReport.AddStepResult("QG Page Validation:", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation:", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}

	
	By FirstElement = By.xpath("//ul[contains(@class,'data-list drillable-list scroll-y-auto')]/li[1]");
	
	By DeleteIconXpath = By.xpath("//ul[contains(@class,'data-list drillable-list scroll-y-auto')]/li[1]//a/i[@class='left fa fa-minus-square']");

	//Remove try catch
	public void DeleteQG()
	{
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath,oParameters.GetParameters("SearchQg"));
		
		if(IsElementDisplayed("FirstElement", FirstElement))
		{
			//waitFor(FirstElement, "");
			mouse_hover("FirstElement",FirstElement);
			dclick_button("DeleteIcon", DeleteIconXpath);
			oReport.AddStepResult("Delete Icon", "Upon clicking on Delete Icon,Popup gets appeared with delete and cancel options", "PASS");	
		}
		else
			oReport.AddStepResult("Delete Icon", "Upon clicking on Delete Icon,Popup not appeared with delete and cancel options", "FAIL");	
	}


	By DeleteQGpopupCancelButton = By.xpath("//*[@id='dialog']//*[@id='dialog_buttons']/input[@value='Cancel']");
	
	By DeleteQGpopupDeleteButton = By.xpath("//*[@id='dialog']//*[@id='dialog_buttons']/input[@value='Delete']");

	
	public void DRGCancelScenario()
	{
		if(IsElementDisplayed("Cancel Button displayed or not", DeleteQGpopupCancelButton))
		{
			click_button("Cancel Button", DeleteQGpopupCancelButton);
			oReport.AddStepResult("Cancel Button", "Upon clicking on cancel button within delete popup dialog box,popup gets disappeared without deleting Qualification group", "PASS");
		}
		else
			oReport.AddStepResult("Cancel Button", "Upon clicking on cancel button within delete popup dialog box,either popup not disappeared or popup gets disappeared by deleting Qualification group", "FAIL");		
	}

	
	public void DRGDeleteScenario()
	{
		if(IsElementDisplayed("Delete Button displayed or not", DeleteQGpopupDeleteButton))
		{
			click_button("Delete Button", DeleteQGpopupDeleteButton);
			oReport.AddStepResult("Delete Button", "Upon clicking on delete button within delete popup dialog box,popup gets disappeared by deleting Qualification group", "PASS");	
		}
		else
			oReport.AddStepResult("Delete Button", "Upon clicking on delete button within delete popup dialog box,popup gets disappeared without deleting Qualification group", "FAIL");		
		//fixed_wait_time(5);	
	}

	
	public void SearchQG_DRGInTerm()
	{
		// Need to pass "QGSaveAsName" as a string value
		enter_text_value_jscript("Enter text on Search Text Box", TermQualifierGroupNameXpath, oParameters.GetParameters("DRG_QgSaveAsName"));
		fixed_wait_time(2);
		performKeyBoardAction("ENTER");
		
	}


	By ShowDRGFormula = By.xpath("//label[@class='ng-binding']//input[@value='true']");
	
	By FormulaEnabled = By.xpath("//*[@id='addEditTermForm']//div[contains(@class,'col-lg-12 col-md-12 col-sm-12 ng-scope ng-binding')]");

	By DRGUserSetXpath=By.xpath("//*[@id='drgUserRateMaster']");
	
	By CalculationMethodXpath=By.xpath("//*[@id='drgUserCalculationMethodCode']");
	
	By DRGFormulaNameXpath=By.xpath("//*[@id='drgFormulaName']");
	
	public void QG_DRG_RateTypeInTerm()
	{
		waitFor(TermRateTypeXpath, "");
		select_option("Choose a Rate Type", TermRateTypeXpath, "6");

		//waitFor(DRGUserSetXpath, "");
		enter_text_value("Enter text on Search Text Box", DRGUserSetXpath,oParameters.GetParameters("DRG_UserSet"));
		fixed_wait_time(3);
		performKeyBoardAction("ENTER");
		
		waitFor(CalculationMethodXpath, "");
		select_option("Choose a Calculation Method", CalculationMethodXpath, "1");

		
		//waitFor(DRGFormulaNameXpath, "");
		enter_text_value("Enter text on Search Text Box", DRGFormulaNameXpath,oParameters.GetParameters("QG_DRGSaveAsName"));
		fixed_wait_time(3);
		performKeyBoardAction("ENTER");
		//fixed_wait_time(4);
		//fixed_wait_time(2);
	}

	//remove boolean declaration
	public void DRGExportButton() throws IOException
	{	
		if(IsElementDisplayed("Export Button displayed or not", ExportButtonXpath) == true)
		{
			click_button("click on Export button ", ExportButtonXpath);
			fixed_wait_time(3);
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		}
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
	
	
	public void QG_DRGFormulaExpression()
	{
		 login("EDIT");
	     changePricingEngine();
	     xpathChange();
	     navigateQualificationPlugin();
	     addQualificationGroup();
	     AddQG_DRGformulaDetails();
	     ValidationButton();
	     QG_DRGFormulaDRGProviderValues();
	     ValidationButton();
	     QG_DRGFormula_PPSDRG();
	     ValidationButton();
	     QG_DRGsaveButton();
	     SearchQG_DRG();
	     ModifyQG_DRGFormulaExpression();
	     EditPageValidationButton();
	     SaveAsQG_DRG();
	     
	     DeleteQG();
	     DRGCancelScenario();
	     DeleteQG();
	     DRGDeleteScenario();
	     addQualificationGroup();
	     CancelQG_DRGformulaDetails();
	     ValidationButton();
	     CancelButton();
	     
	     navigateRatesheetPlugin();
		 SearchRateSheet();
		 TermsBeforeCount();
		 AddTerm();
		 AddTermSectionDetails();
		 SearchQG_DRGInTerm();
		 QG_DRG_RateTypeInTerm();
		 RSTermSaveButton();
		 TermsAfterCount();
		 navigateQualificationPlugin();
		 SearchQG_DRG();
		// ExportButton();
		 logout();
	}
	

//QG-SECTION QUALIFICATION CODE BEGINS HERE

	public void AddQG_SectionDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, oParameters.GetParameters("QGSectionName") +System.currentTimeMillis());
		
		oParameters.SetParameters("QualifierGroupName_Section", get_field_value("Get text value from  Qualification Group Name Field",QualifierGroupNameXpath));

		System.out.println(oParameters.GetParameters("QualifierGroupName_Section"));	
	
		By QualificationGroupTypeXpath=By.xpath(".//*[@id='expression-group-type']");
	
		waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"201");
	
		By TermEnvironmentXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Environment')]");
		
		waitFor(TermEnvironmentXpath, "");
		click_button("click Environment", TermEnvironmentXpath);
	
		By ClaimLevel_AllLinesXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Claim Level - All Lines')]");
		
		waitFor(ClaimLevel_AllLinesXpath, "");
		click_button("click Claim Level_All Lines ", ClaimLevel_AllLinesXpath);

		By LengthOfStayXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Length of Stay')]");
		
		waitFor(LengthOfStayXpath, "");
		click_button("click Length Of stay", LengthOfStayXpath);
	
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");

		click_button("Add QualificationGroup", QualificationGroupBuildResultsXpath);
		fixed_wait_time(2);
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters( "LengthOfStay"));
	}

	
	public void SearchQG_Section()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QualifierGroupName_Section"));
		performKeyBoardAction("ENTER");

		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QualifierGroupName_Section") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);

		oParameters.SetParameters("QGName_SectionPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println(oParameters.GetParameters("QGName_SectionPageTitle"));
	
		if(oParameters.GetParameters("QualifierGroupName_Section").equals(oParameters.GetParameters("QGName_SectionPageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}	

	
	public void SaveAsQG_Section() 
	{		
		waitFor(SaveAs, "");
		click_button("click Save As caret Button", SaveAs);
		
		click_button("click Save As Button", SaveAsButton);
		
		if(IsDisplayed("Popup", PopupSaveButton))	
		{
			waitFor(PopupSaveButton, "");
			click_button("click Save As popup Button", PopupSaveButton);
			waitFor(QGSaveAsNameXpath, "");
		}
		else
			System.out.println("Pop up not appear");
		
		enter_text_value("SaveAs Name", QGSaveAsNameXpath, oParameters.GetParameters( "SaveAsName")+ oParameters.GetParameters("QualifierGroupName_Section"));
			
		oParameters.SetParameters("QG_SectionSaveAsName", get_field_value("Get text field value of SaveAs name", QGSaveAsNameXpath));
		System.out.println(oParameters.GetParameters("QG_SectionSaveAsName"));
		click_button("4th save button", saveAsPopUp4);

		waitFor(ValidationButtonXpath, "");
		waitFor(FirstElement, "");
		
		oParameters.SetParameters("QGName_SectionpageTitle",  get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println(oParameters.GetParameters("QGName_SectionpageTitle"));
  		if(oParameters.GetParameters("QG_SectionSaveAsName").equals(oParameters.GetParameters("QGName_SectionpageTitle")))
			oReport.AddStepResult("Section SaveAs Copy ", "Upon on clicking on SaveAs button in Qualification group, Copy of Section Qualification group Page gets created ", "PASS");	
		else
  			oReport.AddStepResult("Section SaveAs Copy", "Upon on clicking on SaveAs button in Qualification group, Copy of Section Qualification group Page not created ","FAIL");		
	}


	By RSAddSectionButtonXpath=By.xpath("//*[@id='sec-term-list']//input[@value='Add Section']");

	//Remove try
	public void RSAddSection() 
	{
		if(IsElementDisplayed("Add section button", RSAddSectionButtonXpath) == true)
		{
			click_button("click Add section button", RSAddSectionButtonXpath);
			fixed_wait_time(2);
			oReport.AddStepResult("Add section", "Upon clicking on Add Section Qualification group,Add Section Qualification group window gets opened", "PASS");
		}
		else
			oReport.AddStepResult("Add section", "Upon clicking on Add Section Qualification group,Add Section Qualification group window not opened", "FAIL");	
	}

	
	public void QGSection_saveButton()
	{
		click_button("Save Button", SaveButton);
	
		waitFor(QualifierGroupName_PageTitle, "");
		oParameters.SetParameters("QGName_TermPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_TermPageTitle"));
	
  		if(oParameters.GetParameters("QualifierGroupName_Section").equals(oParameters.GetParameters("QGName_TermPageTitle")))
			oReport.AddStepResult(" Section QG Created", "Upon clicking on Save button after filling all fields in 'Add Section qualification group' wimdow,Created Section qualification page gets saved and opened", "PASS");	
		else
  			oReport.AddStepResult(" Section QG Created", "Upon clicking on Save button after filling all fields in 'Add Section qualification group' window,Created Section qualification page not saved and displayed", "FAIL");		
		fixed_wait_time(5);
	}


	By SectionNameXpath=By.xpath("//*[@id='addEditRateSheetSection']//input[@id='sectionName']");

	By qualificationGroupSectionxpath=By.xpath("//*[@id='addEditRateSheetSection']//input[@id='qualificationGroup']");

	By MaxRateTypeXpath=By.xpath("//*[@id='addEditRateSheetSection']//select[@id='maxRateType']");

	By MaxAmountXpath=By.xpath("//*[@id='addEditRateSheetSection']//input[@id='maxRateAmount']");

	By SectionSaveButtonXpath=By.xpath("//*[@id='addEditRateSheetSection']//input[@id='button.saveId']");

	//remove try
	public void RSAQG_SectionDetails() 
	{
	// Need to pass QG_SectionSaveAsName as a string value
		if(IsElementDisplayed("section name", SectionNameXpath) == true)
		{
			waitFor(SectionNameXpath, "");
		  	enter_text_value("Enter a Section name", SectionNameXpath, "Section"+System.currentTimeMillis());
		  	
		  	enter_text_value("Enter text on Search Text Box", qualificationGroupSectionxpath,oParameters.GetParameters("QG_SectionSaveAsName"));
		  	fixed_wait_time(3);
			performKeyBoardAction("ENTER");
			
			
			select_option("Select Rate type", MaxRateTypeXpath,"1");
			
			enter_text_value("Enter an amount", MaxAmountXpath, "50");
			
			click_button("click on save", SectionSaveButtonXpath);
			fixed_wait_time(5);
			
			oReport.AddStepResult("Add section", "Upon clicking on 'Add Section Icon' in Ratesheet Terms Tab, Section window gets opened where all fields are filled and upon clicking on Save button, Section gets saved", "PASS");
		}
		else
			oReport.AddStepResult("Add section", "Upon clicking on 'Add Section Icon' in Ratesheet Terms Tab, Section window gets opened where all fields are filled and upon clicking on Save button, Section not saved", "FAIL");	
	}


	By SectionUsageTabXpath=By.xpath("//*[@id='qualificationgroupView']//a[text()='Section Usage']");

	By SectionRateSheetCode=By.xpath("//*[@id='qualificationgroupView']//tr[1]/td[1]");
	
	By SectionRateSheetCodeLink = By.xpath("//*[@id='qualificationgroupView']//tr[1]/td[1]/a[@class='link ng-binding']");

	
	public void SectionUsageTab() 
	{	
		navigate_to("click on Section usage tab","RateSheet Link", SectionUsageTabXpath, SectionRateSheetCodeLink);
	
		if(IsElementDisplayed("SectionRateSheetCodeLink", SectionRateSheetCodeLink) == true)
		{
			fixed_wait_time(2);
			oReport.AddStepResult("Section usage Tab", "Upon clicking on Section usage Tab in Section Qualification group,its  Navigated to Section Usage tab successfully", "PASS");
			
			waitFor(SectionRateSheetCode, "");
			fixed_wait_time(2);
	    	mouse_hover("SectionRateSheetCode", SectionRateSheetCode);
	    	dclick_button("SectionRateSheetCodeLink", SectionRateSheetCodeLink);
	    	System.out.println("RateSheetCode :"+RateSheetCode);
	    	System.out.println(" SectionRateSheetCodeLink :"+SectionRateSheetCodeLink);

	    	if((get_field_value("RateSheetCode", RateSheetCode)).contains(get_field_value("SectionRateSheetCodeLink", SectionRateSheetCodeLink)))
	    		oReport.AddStepResult("RateSheet Tab","Upon clicking on RateSheet code link in Section usage Tab,its Navigated to Ratesheet tab successfully","PASS");
	    	else
	    		oReport.AddStepResult("RateSheet Tab","Upon clicking on RateSheet code link in Section usage Tab,not Navigated to Ratesheet tab ","FAIL");
	    }
		else
			oReport.AddStepResult("Section usage Tab","Upon clicking on Section usage Tab in Section Qualification group,Not Navigated to Section usage tab", "FAIL");		
	}


	public void SearchQG_CopyOfSection()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QG_SectionSaveAsName"));
		performKeyBoardAction("ENTER");

		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QG_SectionSaveAsName") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);
		
		oParameters.SetParameters("QGName_SectionPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_SectionPageTitle"));
		
		if(oParameters.GetParameters("QG_SectionSaveAsName").equals(oParameters.GetParameters("QGName_SectionpageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
	   		oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
	
	
	public void QG_SectionQualification()
	{
		 login("EDIT");
	     changePricingEngine();
	     xpathChange();
	     navigateQualificationPlugin();
	     addQualificationGroup();
	     AddQG_SectionDetails();
	     ValidationButton();
	     ClaimLevelCharges();
	     ValidationButton();
	     
	     BillingCode();
	     ValidationButton();
	     CurrentCharges();
	     ValidationButton();	     
	     CurrentPolicy();
	     ValidationButton();
	     ProcedureGroupData();
	     ValidationButton();
	     DiagnosisCode();
	     ValidationButton();
	     DRGCode();
	     ValidationButton(); 
	       
	     QGSection_saveButton();
	     SearchQG_Section();
	     ModifyQualificationGroup();
	     EditPageValidationButton();
	     SaveAsQG_Section();
	     
	     DeleteQG();
	     DRGCancelScenario();
	     DeleteQG();
	     DRGDeleteScenario();
	     addQualificationGroup();
		 CancelQualificationGroupDetails();
		 ValidationButton();
		 CancelButton();
		 
		 navigateRatesheetPlugin();
		 SearchRateSheet();
		 RSAddSection();
		 RSAQG_SectionDetails();
		 navigateQualificationPlugin();
		 SearchQG_CopyOfSection();
		 SectionUsageTab();
		 navigateQualificationPlugin();
		// ExportButton(); 
		 logout();
	}
	
		
//QG- Revenue code begins here
		
	
	public void AddQG_RevenueCodeDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, oParameters.GetParameters("QGRevenueCodeName")+System.currentTimeMillis());
			
		oParameters.SetParameters("QualifierGroupName_RC", get_field_value("Get text value from  Qualification Group Name Field",QualifierGroupNameXpath));
		System.out.println(oParameters.GetParameters("QualifierGroupName_RC"));
		
		By QualificationGroupTypeXpath=By.xpath(".//*[@id='expression-group-type']");
	
		waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"202");
				
		By TermEnvironmentXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Environment')]");
		
		waitFor(TermEnvironmentXpath, "");
		click_button("click Environment", TermEnvironmentXpath);

		By RevenueCodeXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Revenue Code')]");
		
		waitFor(RevenueCodeXpath, "");
		click_button("click Revenue code", RevenueCodeXpath);
		
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");
			
		click_button("Enter Expression in QualificationGroup", QualificationGroupBuildResultsXpath);
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("RevenueCodeNumber"));
	}

	
	public void QG_RCsaveButton()
	{
		click_button("Save Button", SaveButton);
		waitFor(QualifierGroupName_PageTitle, "");
		
		oParameters.SetParameters("QGName_TermPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_TermPageTitle"));
		
		if(oParameters.GetParameters("QualifierGroupName_RC").equals(oParameters.GetParameters("QGName_TermPageTitle")))
			oReport.AddStepResult(" Revenue Code QG Created", "Upon clicking on save button after filling all fields in Add Qualification group window,Created Revenue code Qaulification page gets saved and opened", "PASS");	
		else
			oReport.AddStepResult(" Revenue code QG Created", "Upon clicking on save button after filling all fields in Add Qualification group window,Created Revenue code Qaulification page not saved and opened", "FAIL");		
	}
	

	public void SearchQG_RC()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QualifierGroupName_RC"));
		performKeyBoardAction("ENTER");

		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QualifierGroupName_RC") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);
		
		oParameters.SetParameters("QGName_RCPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_RCPageTitle"));
		
		if(oParameters.GetParameters("QualifierGroupName_RC").equals(oParameters.GetParameters("QGName_RCPageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
			
			
	public void SaveAsQG_RC() 
	{	
		waitFor(SaveAs, "");
		click_button("click Save As caret Button", SaveAs);
		
		click_button("click Save As Button", SaveAsButton);
		
		if(IsDisplayed("Popup", PopupSaveButton))	
		{
			waitFor(PopupSaveButton, "");
			click_button("click Save As popup Button", PopupSaveButton);
			waitFor(QGSaveAsNameXpath, "");
		}
		else
			System.out.println("Pop up not appear");
	
		enter_text_value("SaveAs Name", QGSaveAsNameXpath, oParameters.GetParameters("SaveAsName")+ oParameters.GetParameters("QualifierGroupName_RC"));
		
		oParameters.SetParameters("QG_RCSaveAsName", get_field_value("Get text field value of SaveAs name", QGSaveAsNameXpath));
		System.out.println(oParameters.GetParameters("QG_RCSaveAsName"));
		click_button("4th save button", saveAsPopUp4);
		
		waitFor(ValidationButtonXpath, "");
		waitFor(FirstElement, "");
		
		oParameters.SetParameters("QG_RCSaveAsName", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_RCPageTitle"));
		
		if(oParameters.GetParameters("QualifierGroupName_RC").equals(oParameters.GetParameters("QGName_RCPageTitle")))
			oReport.AddStepResult("RC SaveAs Copy ", "Upon on clicking on SaveAs button in Qualification group, Copy of Revenue code Qualification group Page gets created ", "PASS");	
		else
			oReport.AddStepResult("RC SaveAs Copy", "Upon on clicking on SaveAs button in Qualification group, Copy of Revenue code Qualification group Page not created ","FAIL");		
	}
	
			
	public void CancelQG_RCDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, oParameters.GetParameters("QGRevenueCodeName")+System.currentTimeMillis());
				
		By RevenueCodeXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Revenue Code')]");
		waitFor(RevenueCodeXpath, "");
		click_button("click Revenue code", RevenueCodeXpath);
		
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		click_button("Enter Expression in QualificationGroup", QualificationGroupBuildResultsXpath);

		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("RevenueCodeNumber"));	
	}
	
	
	public void SearchQGInTerm_RC()
	{
		// QG_RCSaveAsName 
		enter_text_value("Enter text on Search Text Box", TermQualifierGroupNameXpath,oParameters.GetParameters("RC_QgSaveAsName"));
		fixed_wait_time(2);
		performKeyBoardAction("ENTER");
		
	}
	
	
	By TableBasisXpath=By.xpath("//*[@id='addEditTermForm']//div[@options='rateBasis']//select[@id='']");
	
	By RevCodeExpressionxpath=By.xpath("//*[@id='revCodeRateEntry0' and @trigger-search-event='keypress']");
	
	By PercentageXpath=By.xpath("//*[@id='rateEntries0' and @name='']");
	
	
	public void RateTypeInTerm_RC()
	{
		waitFor(TermRateTypeXpath, "");
		select_option("Choose a Rate Type", TermRateTypeXpath, "2");
		
		waitFor(TableBasisXpath, "");
		select_option("Choose a Table basis",TableBasisXpath , "3");
		
		waitFor(RevCodeExpressionxpath, "");
		enter_text_value("Enter text on Search Text Box", RevCodeExpressionxpath,oParameters.GetParameters("RevCodeExpression"));
		fixed_wait_time(3);
		performKeyBoardAction("ENTER");
		//fixed_wait_time(3);
		waitFor(PercentageXpath, "");
		enter_text_value("click on Search Text Box", PercentageXpath,oParameters.GetParameters("RevCodePercentage"));
	}
	
	
	public void QG_RevenueCode()
	{
		 login("EDIT");
	     changePricingEngine();
	     xpathChange();
	     navigateQualificationPlugin();
	     addQualificationGroup();
	     AddQG_RevenueCodeDetails();
	     ValidationButton();
	     QG_RCsaveButton();
	     SearchQG_RC();
	     ModifyQualificationGroup();
	     EditPageValidationButton();
	     SaveAsQG_RC();
	     
	     DeleteQG();
	     DRGCancelScenario();
	     DeleteQG();
	     DRGDeleteScenario();
	     addQualificationGroup();
	     CancelQG_RCDetails();
	     ValidationButton();
	     CancelButton();
	     
	     navigateRatesheetPlugin();
		 SearchRateSheet();
		 TermsBeforeCount();
		 AddTerm();
		 AddTermSectionDetails();
		 SearchQGInTerm_RC();
		 RateTypeInTerm_RC();
		 RSTermSaveButton();
		 TermsAfterCount();
		 navigateQualificationPlugin();
		 SearchQG_RC();
		// ExportButton();
		 logout();
	}
	
	
//QG-STOP LOSS FORMULA code begins here
		
	public void AddQG_StopLossFormulaDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath,oParameters.GetParameters("QGStopLossFormula")+System.currentTimeMillis());
			
		oParameters.SetParameters("QualifierGroupName_SLF", get_field_value("Get text value from  Qualification Group Name Field",QualifierGroupNameXpath));
		System.out.println(oParameters.GetParameters("QualifierGroupName_SLF"));	
		
		By QualificationGroupTypeXpath=By.xpath(".//*[@id='expression-group-type']");
	
		waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"200");
		
		By TermEnvironmentXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Environment')]");
		
		waitFor(TermEnvironmentXpath, "");
		click_button("click Environment", TermEnvironmentXpath);
		
		By StopLossDataXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Stop Loss Data')]");
		
		waitFor(StopLossDataXpath, "");
		click_button("click Stop Loss Data", StopLossDataXpath);
		
		By LengthOfStayXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Length of Stay')]");
		
		waitFor(LengthOfStayXpath, "");
		click_button("click Length Of stay", LengthOfStayXpath);
		
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");
			
		click_button("Enter Expression in QualificationGroup", QualificationGroupBuildResultsXpath);
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("LengthOfStay"));
		click_button("click  Validate Button", ValidationButtonXpath);
		
			
		enter_text_value("QualificationGroupBuildResults", QualificationGroupBuildResultsXpath,"");
		
		By CurrentPolicyXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Current Policy')]");
		
		waitFor(CurrentPolicyXpath, "");
		click_button("click on Current Policy", CurrentPolicyXpath);
		
		By TotalChargeAmountXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Total Charge Amount']");
		waitFor(TotalChargeAmountXpath, "");
		click_button("click on Total Charge Amount", TotalChargeAmountXpath);
		
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("TotalChargeAmount"));
		click_button("click  Validate Button", ValidationButtonXpath);				
	}

	
	public void SearchQG_SLF()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QualifierGroupName_SLF"));
		performKeyBoardAction("ENTER");

		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QualifierGroupName_SLF") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);

		waitFor(QualifierGroupName_PageTitle, "");
		oParameters.SetParameters("QGName_SLFPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_SLFPageTitle"));
	
		if(oParameters.GetParameters("QualifierGroupName_SLF").equals(oParameters.GetParameters("QGName_SLFPageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
		
	
	public void QG_SLFsaveButton()
	{
		click_button("Save Button", SaveButton);
		waitFor(QualifierGroupName_PageTitlexpath, "");
	
		oParameters.SetParameters("QGName_SLFPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_SLFPageTitle"));
		
		if(oParameters.GetParameters("QualifierGroupName_SLF").equals(oParameters.GetParameters("QGName_SLFPageTitle")))
			oReport.AddStepResult(" Stop loss Formula QG Created", "Upon clicking on save button after filling all fields in Add Qualification group window,Created StopLoss Formula Qualification group page gets saved and opened", "PASS");	
		else
			oReport.AddStepResult(" Stop loss formula QG Created", "Upon clicking on save button after filling all fields in Add Qualification group window,Created StopLoss Formula Qualification group page not saved and opened", "FAIL");		
	}


	public void SaveAsQG_SLF() 
	{	
		waitFor(SaveAs, "");
		click_button("click Save As caret Button", SaveAs);
		
		click_button("click Save As Button", SaveAsButton);
		
		if(IsElementDisplayed("Popup", PopupSaveButton) == true)	
		{
			waitFor(PopupSaveButton, "");
			click_button("click Save As popup Button", PopupSaveButton);
			waitFor(QGSaveAsNameXpath, "");
		}
		else
			System.out.println("Pop up not appear");
		
		enter_text_value("SaveAs Name", QGSaveAsNameXpath, "copyof"+ oParameters.GetParameters("QualifierGroupName_SLF"));
		
		
		oParameters.SetParameters("QG_SLFSaveAsName", get_field_value("Get text field value of SaveAs name", QGSaveAsNameXpath));
		System.out.println("stop loss formula name :"+oParameters.GetParameters("QG_SLFSaveAsName"));
		click_button("4th save button", saveAsPopUp4);
	
		waitFor(ValidationButtonXpath, "");
		waitFor(FirstElement, "");
		
		oParameters.SetParameters("QGName_SLFPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_SLFPageTitle"));
		
		if(oParameters.GetParameters("QG_SLFSaveAsName").equals(oParameters.GetParameters("QGName_SLFPageTitle")))
			oReport.AddStepResult("SLF SaveAs Copy ", "Upon on clicking on SaveAs button in Qualification group, Copy of Stop Loss formula Qualification group Page gets created", "PASS");	
		else
			oReport.AddStepResult("SLF SaveAs Copy", "Upon on clicking on SaveAs button in Qualification group, Copy of Stop Loss formula Qualification group Page not created","FAIL");		
	}	
		
	
	public void CancelQG_SLformulaDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, "QG_SLF"+System.currentTimeMillis());
			
		By QualificationGroupTypeXpath=By.xpath(".//*[@id='expression-group-type']");
	
		waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"275");
		
		By CurrentPolicyXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Current Policy')]");
		
		waitFor(CurrentPolicyXpath, "");
		click_button("click on Current Policy ", CurrentPolicyXpath);
		
		By TotalChargeAmountXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Total Charge Amount']");
		
		click_button("click on Total Charge Amount ", TotalChargeAmountXpath);
		select_option("Select Operator EQ", OperatorsXpath,"4");
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("TotalChargeAmountNumber"));		
	}
		
		
	By StopLossTab = By.xpath("//*[@id='ratesheetSection']//li[@role='presentation']/a[contains(text(),'Stop Loss')]");
	
	By StopLossNameValidation = By.xpath("//*[@id='sec-term-list']/div[@class='col-lg-3 col-md-3 col-sm-3 col-xs-3 ng-binding' and contains(text(),'Stop Loss')]");
		
	
	public void StopLossTabNavigation() 
	{
		waitFor(StopLossTab, "");
		navigate_to("Stop Loss Tab","Stop Loss section name", StopLossTab,StopLossNameValidation);
			
		if(IsElementDisplayed("StopLossName", StopLossNameValidation) == true)
			oReport.AddStepResult("StopLoss Tab", "Upon clicking on Stop loss Tab in Ratesheet,its Navigated to StopLoss tab successfully", "PASS");	
		else
			oReport.AddStepResult("StopLoss Tab", "Upon clicking on Stop loss Tab in Ratesheet, Not Navigated to StopLoss tab ", "FAIL");		
	}
		

	
	By stopLossTermsCount = By.xpath("//ratesheet-stoploss-sections//ul[@class='data-list']/li[2]//span[@class='pull-right small ng-binding' and contains(text(),'Terms')]");
	
	public void StopLossTermBeforeCount()
	{
		oParameters.SetParameters("TermsBeforeCount", get_field_value("Terms Count", stopLossTermsCount).replace(" Terms",""));
		System.out.println("TermsBeforeCount:"+oParameters.GetParameters("TermsBeforeCount"));
	}
	
	
	By AddStoplossTermButtonXpath=By.xpath("//*[@id='ratesheetStoploss']//li[@ng-mouseover='showSCB=true'][1]//a[@title='Add Terms']");
	
	//Remove try n catch n remove boolean declaratin
	public void AddStopLossTerm()
	{
		Boolean RSAddStopLossTermButton = IsElementDisplayed("Checking Add stop loss term button for existing terms is displayed or not",  AddStoplossTermButtonXpath);
		waitFor(AddStoplossTermButtonXpath, "");
	
		if(RSAddStopLossTermButton==true)
		{
			try
			{
				dclick_button("AddStoplossTermButton", AddStoplossTermButtonXpath);
				oReport.AddStepResult("Add Stop loss Term", "Upon clicking on Add Term in stop loss Tab,Add term window gets opened","PASS");
			}	
			catch(Exception e)
			{
				System.out.println("Exception Thrown" +e.getMessage());
			}
		}
		else
		{
			System.out.println("No add term icon present");
			oReport.AddStepResult("Add Term", "Upon clicking on Add Term in stop loss Tab,Add term window not opened","FAIL");
		}
	}
		
	
	By TermNameinStopLossxpath = By.xpath("//*[@id='addSectionModal']//*[@id='termName']");
		
	By TypeNameinStopLossxpath = By.xpath("//*[@id='addSectionModal']//select[@id='typeName']");
		
	By ThresholdAmountxpath = By.xpath("//*[@id='thresholdAmount']");
		
	By searchQGxpath = By.xpath("//*[@id='qualifierExp1']");
		
	By Ratetypexpath = By.xpath("//*[@id='addStopLossTerm']//select[@id='']");
		
	By FormulaNamexpath = By.xpath("//*[@id='addSectionModal']//input[@id='formulaName']");
		
	By StoplossSaveButtonXpath=By.xpath("//*[@id='addSectionModal']//input[@id='button.saveId']");
		
		
	public void AddStopLossTermDetails()
	{
		waitFor(TermNameinStopLossxpath, "");
		enter_text_value("Enter a Term name", TermNameinStopLossxpath,oParameters.GetParameters("SLTerm")+System.currentTimeMillis());	
		
		select_option("Type NAme", TypeNameinStopLossxpath, "0");
		
		// Need to pass "QG_SLFSaveAsName" as a string value
		enter_text_value("Enter text on Search Text Box", searchQGxpath,oParameters.GetParameters("SLF_QGsearch"));
		fixed_wait_time(2);
		performKeyBoardAction("ENTER");
		
		waitFor(Ratetypexpath, "");
		select_option("RateType", Ratetypexpath, "1");
		
		waitFor(FormulaNamexpath, "");
		enter_text_value("Enter text on Search Text Box", FormulaNamexpath, oParameters.GetParameters("QG_SLFSaveAsName"));
		fixed_wait_time(2);
		performKeyBoardAction("ENTER");
		
		
		waitFor(StoplossSaveButtonXpath, "");
		click_button("Save Button", StoplossSaveButtonXpath);
		

	}
	
	
	public void StopLossTermAfterCount()
	{
		waitFor(stopLossTermsCount, "");
		oParameters.SetParameters("TermsAfterCount", get_field_value("Terms Count", stopLossTermsCount).replace(" Terms",""));
		System.out.println("TermsAfterCount:"+oParameters.GetParameters("TermsAfterCount"));
		
		if (Integer.parseInt(oParameters.GetParameters("TermsAfterCount")) == Integer.parseInt(oParameters.GetParameters("TermsBeforeCount")))
			waitFor(stopLossTermsCount, "");
			oParameters.SetParameters("TermsAfterCount", get_field_value("Terms Count", TermsCount).replace(" Terms",""));
			System.out.println("TermsAfterCount:"+oParameters.GetParameters("TermsAfterCount"));
		
			if (Integer.parseInt(oParameters.GetParameters("TermsAfterCount")) > Integer.parseInt(oParameters.GetParameters("TermsBeforeCount")))
			{
				oReport.AddStepResult("Term Created", "Upon clicking on Save button for adding StopLoss Term, Term gets added successfully", "PASS");
			}
			else
			{
				oReport.AddStepResult("Term Created", "Upon clicking on Save button for adding StopLoss Term, Term not added successfully", "FAIL");
			}
	}
	
		
	By StopLossUsagexpath = By.xpath("//*[@id='qualificationgroupView']//a[contains(.,'Stop Loss Usage')]");
	
	
	public void StopLossUsageTab() 
	{
		navigate_to("StopLoss Usage Tab","StopLoss Usage Table Name", StopLossUsagexpath, SectionRateSheetCodeLink);
		if( IsElementDisplayed("SectionRateSheetCodeLink", SectionRateSheetCodeLink) == true)
		{
			fixed_wait_time(3);
		
			oReport.AddStepResult("StopLoss usage Tab", "Upon clicking on Stop Loss usgae Tab in Qualification group,its Navigated to StopLoss Usage tab successfully", "PASS");
			
			waitFor(SectionRateSheetCode, "");
			mouse_hover("SectionRateSheetCode", SectionRateSheetCode);
		    	
			dclick_button("SectionRateSheetCodeLink", SectionRateSheetCodeLink);
			System.out.println("RateSheetCode"+RateSheetCode);
			System.out.println("SectionRateSheetCodeLink"+SectionRateSheetCodeLink);

	    	if((get_field_value("RateSheetCode", RateSheetCode)).contains(get_field_value("SectionRateSheetCodeLink", SectionRateSheetCodeLink)))
	    		oReport.AddStepResult("RateSheet Tab","Upon clicking on RateSheet code link in Term usage Tab,its Navigated to Ratesheet tab successfully","PASS");
			else
				oReport.AddStepResult("RateSheet Tab","Upon clicking on RateSheet code link in Term usage Tab,not Navigated to Ratesheet tab ","FAIL");
		}
		else
			oReport.AddStepResult("Term usage Tab","Upon clicking on Stop Loss usgae Tab in Qualification group,Not Navigated to Term usage tab", "FAIL");		
	}
		 
	
	public void SearchQG_CopyOfSLF()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QG_SLFSaveAsName"));
		performKeyBoardAction("ENTER");
	
		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QG_SLFSaveAsName") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);
		
		waitFor(QualifierGroupName_PageTitle, "");
	
		oParameters.SetParameters("QGName_SLFPageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
	
		System.out.println("Page Title Validation"+oParameters.GetParameters("QG_SLFSaveAsName"));
		
		if(oParameters.GetParameters("QG_SLFSaveAsName").equals(oParameters.GetParameters("QGName_SLFPageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
	
	
	public void QG_StopLossFormulaQualification()
	{
		 login("EDIT");
	     changePricingEngine();
	     xpathChange();
	     navigateQualificationPlugin();
	     addQualificationGroup();
	     AddQG_StopLossFormulaDetails();
	     QG_DRGFormulaDRGProviderValues();
	     ValidationButton();
	     QG_DRGFormula_PPSDRG();
	     ValidationButton();
	     QG_SLFsaveButton();
	     SearchQG_SLF();
	     ModifyQualificationGroup();
	     EditPageValidationButton();
	     SaveAsQG_SLF();
	     
	     DeleteQG();
	     DRGCancelScenario();
	     DeleteQG();
	     DRGDeleteScenario();
	     addQualificationGroup();
	     CancelQG_SLformulaDetails();
	     ValidationButton();
	     CancelButton();
	     
	     navigateRatesheetPlugin();
		 SearchRateSheet();
		 StopLossTabNavigation();
		 StopLossTermBeforeCount();
		 AddStopLossTerm();
		 AddStopLossTermDetails();
		 StopLossTermAfterCount();
		 navigateQualificationPlugin();
		 SearchQG_CopyOfSLF();
		 StopLossUsageTab();
		 navigateQualificationPlugin();
		 SearchQG_CopyOfSLF();
		// ExportButton();
		 logout();
	}
	
//Stop Loss code begins here
					 
	public void AddQG_StopLossDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath,oParameters.GetParameters("QGStopLoss")+System.currentTimeMillis());
		fixed_wait_time(1);
		
		oParameters.SetParameters("QualifierGroupName_SL", get_field_value("Get text value from  Qualification Group Name Field",QualifierGroupNameXpath));
	
		System.out.println(oParameters.GetParameters("QualifierGroupName_SL"));	
		
		By QualificationGroupTypeXpath=By.xpath(".//*[@id='expression-group-type']");
	
		waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"204");
		
		By TermEnvironmentXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Environment')]");
		
		waitFor(TermEnvironmentXpath, "");
		click_button("click Environment", TermEnvironmentXpath);
		
		By StopLossDataXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Stop Loss Data')]");
		
		waitFor(StopLossDataXpath, "");
		click_button("click Stop Loss Data", StopLossDataXpath);
		
		By LengthOfStayXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Length of Stay')]");
		
		waitFor(LengthOfStayXpath, "");
		click_button("click Length Of stay", LengthOfStayXpath);
		
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");
		
		click_button("Enter Expression in QualificationGroup", QualificationGroupBuildResultsXpath);
		
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath,oParameters.GetParameters("LengthOfStay"));
		click_button("click  Validate Button", ValidationButtonXpath);
		
		enter_text_value("QualificationGroupBuildResults", QualificationGroupBuildResultsXpath,"");      
		
		By CurrentPolicyXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Current Policy')]");
		
		waitFor(CurrentPolicyXpath, "");
		click_button("click on Current Policy", CurrentPolicyXpath);
		
		By TotalChargeAmountXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Total Charge Amount']");
		
		waitFor(TotalChargeAmountXpath, "");
		click_button("click on Total Charge Amount", TotalChargeAmountXpath);
		
		waitFor(OperatorsXpath, "");
		select_option("Select Operator EQ", OperatorsXpath,"4");
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("TotalChargeAmount"));
		click_button("click  Validate Button", ValidationButtonXpath);		
	}
			
					 
	public void SearchQG_Stoploss()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QualifierGroupName_SL"));
		performKeyBoardAction("ENTER");

		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QualifierGroupName_SL") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);
		waitFor(QualifierGroupName_PageTitle, "");
		
		oParameters.SetParameters("QGName_SLPageTitle",  get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
	
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_SLPageTitle"));
		
		if(oParameters.GetParameters("QualifierGroupName_SL").equals(oParameters.GetParameters("QGName_SLPageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
	
	
	public void SaveAsQG_SL() 
	{
		fixed_wait_time(3);	
		click_button("click Save As caret Button", SaveAs);
		
		click_button("click Save As Button", SaveAsButton);
		
		if(IsElementDisplayed("Popup", PopupSaveButton) == true)	
		{
			waitFor(PopupSaveButton, "");
			click_button("click Save As popup Button", PopupSaveButton);
			waitFor(QGSaveAsNameXpath, "");
		}
		else
			System.out.println("Pop up not appear");
	
		enter_text_value("SaveAs Name", QGSaveAsNameXpath, "copyof"+ oParameters.GetParameters("QualifierGroupName_SL"));
		
		oParameters.SetParameters("QG_SLSaveAsName", get_field_value("Get text field value of SaveAs name", QGSaveAsNameXpath));
		System.out.println("stop loss formula name :"+oParameters.GetParameters("QG_SLSaveAsName"));
		click_button("4th save button", saveAsPopUp4);
		
		waitFor(ValidationButtonXpath, "");
		waitFor(FirstElement, "");
		
		oParameters.SetParameters("QGName_SLpageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_SLpageTitle"));
		
		if(oParameters.GetParameters("QG_SLSaveAsName").equals(oParameters.GetParameters("QGName_SLPageTitle")))
			oReport.AddStepResult("SL SaveAs Copy ", "Upon on clicking on SaveAs button in Qualification group, Copy of Stop Loss Qualification group Page gets created", "PASS");	
		else
			oReport.AddStepResult("SL SaveAs Copy", "Upon on clicking on SaveAs button in Qualification group, Copy of Stop loss Qualification group Page not created","FAIL");		
	}	
					
	
	public void CancelQG_StoplossDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath,oParameters.GetParameters("QGStopLoss")+System.currentTimeMillis());
		
		By QualificationGroupTypeXpath=By.xpath(".//*[@id='expression-group-type']");
	
		waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"204");
		
		By CurrentPolicyXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Current Policy')]");
		
		waitFor(CurrentPolicyXpath, "");
		click_button("click on Current Policy ", CurrentPolicyXpath);
		
		By TotalChargeAmountXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[text()='Total Charge Amount']");
		
		click_button("click on Total Charge Amount ", TotalChargeAmountXpath);
		select_option("Select Operator EQ", OperatorsXpath,"4");
		enter_text_value_without_clear("Entering a value in Qualification Group Build text box", QualificationGroupBuildResultsXpath, oParameters.GetParameters("TotalChargeAmountNumber"));		
	}
	
	
	public void SearchQG_CopyOfStoploss()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QG_SLSaveAsName"));
		performKeyBoardAction("ENTER");
	
		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QG_SLSaveAsName") + "')]");
		click_button("click on particular QG ", SearchQGNameXpath);
		
		waitFor(QualifierGroupName_PageTitle, "");		
		oParameters.SetParameters("QGName_SLpageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_SLpageTitle"));
		
		if(oParameters.GetParameters("QG_SLSaveAsName").equals(oParameters.GetParameters("QGName_SLPageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
				 
	
	public void AddStopLossQGTermDetails()
	{
		waitFor(TermNameinStopLossxpath, "");
		enter_text_value("Enter a Term name", TermNameinStopLossxpath, oParameters.GetParameters("SLTerm")+System.currentTimeMillis());	
		
		select_option("Type NAme", TypeNameinStopLossxpath, "0");
		waitFor(searchQGxpath, "");
	
		// Need to pass "QG_SLFSaveAsName" as a string value
		enter_text_value("Enter text on Search Text Box", searchQGxpath,oParameters.GetParameters("QG_SLSaveAsName"));
		fixed_wait_time(2);
		performKeyBoardAction("ENTER");
		
		
		waitFor(Ratetypexpath, "");
		select_option("RateType", Ratetypexpath,oParameters.GetParameters("RateType"));
		
		waitFor(FormulaNamexpath, "");
		enter_text_value("Enter text on Search Text Box", FormulaNamexpath,oParameters.GetParameters("FormulaName"));
		fixed_wait_time(2);
		performKeyBoardAction("ENTER");
		
		waitFor(StoplossSaveButtonXpath, "");
		click_button("Save Button", StoplossSaveButtonXpath);
	}
	
	
	public void QG_SLsaveButton()
	{
		click_button("Save Button", SaveButton);
		
		waitFor(QualifierGroupName_PageTitlexpath, "");
		oParameters.SetParameters("QGName_SLPageTitle",  get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGName_SLPageTitle"));
	
		if(oParameters.GetParameters("QualifierGroupName_SL").equals(oParameters.GetParameters("QGName_SLPageTitle")))
			oReport.AddStepResult(" Stop loss  QG Created", "Upon clicking on save button after filling all fields in Add Qualification group window,Created StopLoss  Qualification group page gets saved and opened", "PASS");	
		else
			oReport.AddStepResult(" Stop loss  QG Created", "Upon clicking on save button after filling all fields in Add Qualification group window,Created StopLoss  Qualification group page not saved and opened", "FAIL");		
		fixed_wait_time(4);
	}
	
	
	public void QG_StopLossQualification()
	{
		 login("EDIT");
	     changePricingEngine();
	     xpathChange();
	     navigateQualificationPlugin();
	     addQualificationGroup();
	     AddQG_StopLossDetails();
	     BillingCode();
	     ValidationButton();
	     
	     QG_DRGFormulaDRGProviderValues();
	     ValidationButton();
	     QG_DRGFormula_PPSDRG();
	     ValidationButton();
	     
	     QG_SLsaveButton();
	     SearchQG_Stoploss();
	     ModifyQualificationGroup();
	     EditPageValidationButton();
	     SaveAsQG_SL();
	     
	     DeleteQG();
	     DRGCancelScenario();
	     DeleteQG();
	     DRGDeleteScenario();
	     addQualificationGroup();
	     CancelQG_StoplossDetails();
	     ValidationButton();
	     CancelButton();
	     
	     navigateRatesheetPlugin();
		 SearchRateSheet();
		 StopLossTabNavigation();
		 StopLossTermBeforeCount();
		 AddStopLossTerm();
		 AddStopLossQGTermDetails();
		 StopLossTermAfterCount();
		 navigateQualificationPlugin();
		 SearchQG_CopyOfStoploss();
		 StopLossUsageTab();
		 navigateQualificationPlugin();
		 SearchQG_CopyOfStoploss();
		 //ExportButton();
		 logout();
	}
	
	 
//View only permission- QG
	
	By qgPage = By.xpath("//div[@class='msg-blurb'][contains(.,'Nothing is selected. Select a Qualification Group Type to begin.')]");
	By QGFirstline = By.xpath("//*[@id='qualificationgroupView']//div[@class='first-line ng-scope ng-binding']");
	By addQGLink = By.xpath("//div[@class='msg-blurb'][contains(.,'Add a Qualification Group')]");
	By termsList = By.xpath("//ul[@class='data-list drillable-list scroll-y-auto']//li[1]"); 
	By addQGIcon = By.xpath("//div[@id='qualificationgroupView']//i[@class='left fa fa-plus-square']");
	By qgDropDown = By.xpath("//div[@id='styledDropdown']//span[contains(.,'Select a Qualification Group Type')]");
	public void navigateQualificationPlugin_Viewmode() 
	{
		navigate_to("Qualification Groups","QGFirstline", QualificationGroupTab,QGFirstline);
		fixed_wait_time(2);
		if(!IsDisplayed("Add Qualification Group Link", addQGLink) && !IsDisplayed("Add Qualification Group Icon", addQGIcon))
			oReport.AddStepResult("QG plugin Navigation", "In View mode, Upon clicking on Qualification groups Tab, its Navigated to QG Plugin successfully and verified that Add Qualification Group link or add button is not displayed", "PASS");	
		else
			oReport.AddStepResult("QG plugin Navigation", "In view mode, Upon clicking on Qualification groups Tab,Not Navigated to QG Plugin ", "FAIL");
	}  
	 
	
	public void navigateToQualificationPlugin() 
	{
		fixed_wait_time(2);
		navigate_to("Qualification Groups","", QualificationGroupTab,null);
	}
		 		
	//String searchname = "All Charges";
	
	public void SearchQGTerm_View()
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath,oParameters.GetParameters("searchname"));
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		
		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("searchname") + "')]");
		
		click_button("click on particular QG ", SearchQGNameXpath);
		
		String QGName_TermPageTitle = get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle);
		System.out.println("Page Title Validation"+QGName_TermPageTitle);
		
		if(oParameters.GetParameters("QualifierGroupName").equals(QGName_TermPageTitle))
		{
			fixed_wait_time(3);
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		}
		else
		{
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
		}
	}
	
	
	By ViewSaveButtonxpath=By.xpath("//*[@id='fullFooter']//button[@type='button'][1]");
	
	//Method for checking Save button in View Mode
	public void QGSaveAsButton_view() 
	{	
		if(!IsDisplayed("Add Document Icon", ViewSaveButtonxpath))
		{
			oReport.AddStepResult("QG SaveAs button", "SaveAs button in qualification group is not present in view mode", "PASS");
		}
		else
		{
			oReport.AddStepResult("QG SaveAs button", "SaveAs button in qualification group is present in view mode", "FAIL");
		}
	}
	
			
	public void DeleteQG_view()
	{
		mouse_hover("FirstElement", FirstElement);
		if (!IsDisplayed("DeleteIcon", DeleteIconXpath))
			//hover.moveToElement(DeleteIconXpath);mouse_hover("DeleteIcon", DeleteIconXpath);	
			oReport.AddStepResult("Delete Icon", "Hover over Qualification Group and verified that delete icon is not displayed", "PASS");
		else
			oReport.AddStepResult("Delete Icon", "Hover over Qualification Group and verified that delete icon is displayed", "FAIL");
	}
	
	
	public void QG_ViewOnlyPermission()
	{
		 login("VIEW");
	     changePricingEngine();
	    //xpathChange();
	     navigateQualificationPlugin_Viewmode();//Method for navigating to QG 
	     oPPSLibrary.selectGroupType("Term/Exclusions Qualification",qgDropDown,termsList);//Method for selecting QG
	     fixed_wait_time(60);
	     SearchQualifier("termExclusions");//Method for searching and selecting particular Qualification Group
	     fixed_wait_time(60);
	     QGSaveAsButton_view();
	     DeleteQG_view();
	     ExportButton("Term Usage","Exclusion Usage");
	     deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+oParameters.GetParameters("ExportedFileName"));
	     TermUsageTab();
	     navigate_to("Qualification Groups","Opened Qualification Group page", QualificationGroupTab,openedQG);
	     ExclusionUsageTab();
	     navigate_to("Qualification Groups","Opened Qualification Group page", QualificationGroupTab,openedQG);
	    
	    //DrgFormula Expression Qualification.
	     oPPSLibrary.selectGroupType("DRG Formula Expression",qgDropDown,termsList);//Method for selecting QG
	     SearchQualifier("DRG"); 
	     QGSaveAsButton_view();
	     DeleteQG_view();
	     ExportButton("Term Usage","");
	     deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+oParameters.GetParameters("ExportedFileName"));
	     TermUsageTab();
	     navigate_to("Qualification Groups","Opened Qualification Group page", QualificationGroupTab,openedQG);
		
	     //Section Expression Qualification 
	     oPPSLibrary.selectGroupType("Section Qualification",qgDropDown,termsList);//Method for selecting QG
	     SearchQualifier("sectionQualification");
		 QGSaveAsButton_view();
	     DeleteQG_view();
	     ExportButton("Section Usage","");
	     deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+oParameters.GetParameters("ExportedFileName"));
	     SectionUsageTab();
	     navigate_to("Qualification Groups","Opened Qualification Group page", QualificationGroupTab,openedQG);
	
		//RevenueCode Expression Qualification
	     oPPSLibrary.selectGroupType("Revenue Code Qualification",qgDropDown,termsList);//Method for selecting QG
		 SearchQualifier("revenueCode");
		 QGSaveAsButton_view();
	     DeleteQG_view();
	     ExportButton("Term Usage","Exclusion Usage");
	     deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+oParameters.GetParameters("ExportedFileName"));
	     TermUsageTab();
	     navigate_to("Qualification Groups","Opened Qualification Group page", QualificationGroupTab,openedQG);
	     ExclusionUsageTab();
	     navigate_to("Qualification Groups","Opened Qualification Group page", QualificationGroupTab,openedQG);
	   
	     //stopLossFormula Expression Qualification
	     oPPSLibrary.selectGroupType("Stop Loss Formula Expression",qgDropDown,termsList);//Method for selecting QG
	     SearchQualifier("stopLossFormula");
		 QGSaveAsButton_view();
	     DeleteQG_view();
	     ExportButton("Stop Loss Usage","");
	     deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+oParameters.GetParameters("ExportedFileName"));
	     StopLossUsageTab();
	     navigate_to("Qualification Groups","Opened Qualification Group page", QualificationGroupTab,openedQG);
	 
	     //StopLoss Qualification
	     oPPSLibrary.selectGroupType("Stop Loss Qualification",qgDropDown,termsList);//Method for selecting QG
	     SearchQualifier("stopLossQualification");
		 QGSaveAsButton_view();
	     DeleteQG_view();
	     ExportButton("Stop Loss Usage","");
	     deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+oParameters.GetParameters("ExportedFileName"));
	     StopLossUsageTab();
	     navigate_to("Qualification Groups","Opened Qualification Group page", QualificationGroupTab,openedQG);
	 
	     //Service Qualification
	     oPPSLibrary.selectGroupType("Service Qualification",qgDropDown,termsList);//Method for selecting QG
	     SearchQualifier("serviceQualification");
		 QGSaveAsButton_view();
	     DeleteQG_view();
	     ExportButton("Dialysis Usage","");
	     deleteFile(oParameters.GetParameters("downloadFilepath")+"/"+oParameters.GetParameters("ExportedFileName"));
	     dialysisUsageTab();
	  
	     //Method for logout
	     logout();
	}
		
	
// Service QG code begins here
			 		
	
	By ModifierCodexpath = By.xpath("//*[@id='environment']//p[text()='Modifier Code']");
	
	public void AddQG_ServiceDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath,oParameters.GetParameters("QGServiceName")+System.currentTimeMillis());
		
		oParameters.SetParameters("QualifierGroupName_service", get_field_value("Get text value from  Qualification Group Name Field",QualifierGroupNameXpath));
	
		System.out.println(oParameters.GetParameters("QualifierGroupName_service"));	
		
		By QualificationGroupTypeXpath=By.xpath(".//*[@id='expression-group-type']");
		
		waitFor(QualificationGroupTypeXpath, "");
		click_button("click on QualificationGroup Type",  QualificationGroupTypeXpath);
		select_option("Select Qualification Group Type", QualificationGroupTypeXpath,"203");
		
		By TermEnvironmentXpath=By.xpath("//*[@id='addQualificationGroupModal']//p[contains(.,'Environment')]");
		
		waitFor(TermEnvironmentXpath, "");
		click_button("click Environment", TermEnvironmentXpath);
	
		click_button("click Current Charge", CurrentChargeXpath);
		
		waitFor(ProcedureModifiersXpath, "");
		click_button("click Current Charge", ProcedureModifiersXpath);
		
		waitFor(ModifierCodexpath, "");
		select_option("Select Misc Quotes ", MiscXpath,"5");
	
		enter_text_value_without_clear("enter a value", QualificationGroupBuildResultsXpath,oParameters.GetParameters("ModifierCodeNumber"));
	
		select_option("Select Misc Quotes ", MiscXpath,"5");
	
		select_option("Select Operator IN", OperatorsXpath,"2");
	
		select_option("Select Misc Quotes ", MiscXpath,"3");
	
		click_button("click Amount For Value in Code",ModifierCodexpath);
	
		select_option("Select Misc Quotes ", MiscXpath,"4");
	}
	
	
	By LoadingSpinnerxpath = By.xpath("//*[@id='addQualExpressionBuilder']//div[@class='loadingSpinner']");
	
	//remove boolean
	public void QG_servicesaveButton()
	{	
		click_button("Save Button", SaveButton);
		waitFor(QualifierGroupName_PageTitlexpath, "");
	
		oParameters.SetParameters("QGNameServicepageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGNameServicepageTitle"));
		
		if(oParameters.GetParameters("QualifierGroupName_service").equals(oParameters.GetParameters("QGNameServicepageTitle")))
			oReport.AddStepResult(" service QG Created", "Upon clicking on save button after filling all fields in Add Qualification group window,Created service Qualification group page gets saved and opened ", "PASS");	
		else
			oReport.AddStepResult(" service QG Created", "Upon clicking on save button after filling all fields in Add Qualification group window,Created service Qualification group page not  saved and opened", "FAIL");
	}
	
	
	public void SearchQG_service()	
	{
		waitFor(SearchEngingeXpath, "");
		enter_text_value("Enter text on Search Text Box", SearchEngingeXpath, oParameters.GetParameters("QualifierGroupName_service"));
		performKeyBoardAction("ENTER");
		//fixed_wait_time(3);
	
		By SearchQGNameXpath=By.xpath("//div[contains(text(),'" + oParameters.GetParameters("QualifierGroupName_service") + "')]");
		
		click_button("click on particular QG ", SearchQGNameXpath);
		//fixed_wait_time(4);
		waitFor(QualifierGroupName_PageTitle, "");
		oParameters.SetParameters("QGNameServicepageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGNameServicepageTitle"));
		
		if(oParameters.GetParameters("QualifierGroupName_service").equals(oParameters.GetParameters("QGNameServicepageTitle")))
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page gets opened", "PASS");	
		else
			oReport.AddStepResult("QG Page Validation", "Upon clicking on particular Qualification group from the search list, Selected Qualification group page not opened", "FAIL");		
	}
	
	
	public void SaveAsQGservice() 
	{		
		click_button("click Save As caret Button", SaveAs);
		
		click_button("click Save As Button", SaveAsButton);
	
		if(IsElementDisplayed("Popup", PopupSaveButton) == true)	
		{
			waitFor(PopupSaveButton, "");
			click_button("click Save As popup Button", PopupSaveButton);
			waitFor(QGSaveAsNameXpath, "");
		}
		else
			System.out.println("Pop up not appear");
		enter_text_value("SaveAs Name", QGSaveAsNameXpath, oParameters.GetParameters("SaveAsName")+ oParameters.GetParameters("QualifierGroupName_service"));
	
		oParameters.SetParameters("QGServiceSaveAsName", get_field_value("Get text field value of SaveAs name", QGSaveAsNameXpath));
		System.out.println(oParameters.GetParameters("QGServiceSaveAsName"));

		click_button("4th save button", saveAsPopUp4);

		waitFor(ValidationButtonXpath, "");
		waitFor(FirstElement, "");
		
		oParameters.SetParameters("QGNameServicepageTitle", get_field_value("QualifierGroupName Page Title", QualifierGroupName_PageTitle));
		System.out.println("Page Title Validation"+oParameters.GetParameters("QGNameServicepageTitle"));
	
		if(oParameters.GetParameters("QGServiceSaveAsName").equals(oParameters.GetParameters("QGNameServicepageTitle")))
			oReport.AddStepResult("service SaveAs Copy ", "Upon on clicking on SaveAs button in Qualification group, Copy of service Qualification group Page gets created  ", "PASS");	
		else
			oReport.AddStepResult("Service SaveAs Copy", "Upon on clicking on SaveAs button in Qualification group, Copy of service Qualification group Page not created ","FAIL");		
	}
	
	
	public void CancelQG_serviceDetails()
	{
		enter_text_value("Qualification Group Name", QualifierGroupNameXpath, "QG_service"+System.currentTimeMillis());

		click_button("click Current Charge", CurrentChargeXpath);
		waitFor(ProcedureModifiersXpath, "");
		click_button("click Current Charge", ProcedureModifiersXpath);
		
		waitFor(ModifierCodexpath, "");
		select_option("Select Misc Quotes ", MiscXpath,"5");
	
		enter_text_value_without_clear("enter a value", QualificationGroupBuildResultsXpath,oParameters.GetParameters("ModifierCodeNumber"));
	
		select_option("Select Misc Quotes ", MiscXpath,"5");
	
		select_option("Select Operator IN", OperatorsXpath,"2");

		select_option("Select Misc Quotes ", MiscXpath,"3");
	
		click_button("click Amount For Value in Code",ModifierCodexpath);
	
		select_option("Select Misc Quotes ", MiscXpath,"4");
	}
	
	
	By PPStab = By.xpath("//*[@id='nav']//ul/li/a[@href='#/cm/pps' and contains(text(),'PPS')]");
	
	By DropdownPPS = By.xpath("//*[@id='styledDropdown']/a/span[contains(.,'Select a PPS')]");
	
	
	public void navigatePPSPlugin() 
	{
		waitFor(PPStab, "");
		navigate_to("PPS","PPS page displayed",PPStab,DropdownPPS);
		
		if( IsElementDisplayed("DropdownPPS", DropdownPPS))
			oReport.AddStepResult("PPS plugin Navigation", "Upon clicking on PPS Tab, its Navigated to PPS plugin successfully", "PASS");	
		else
			oReport.AddStepResult("PPS plugin Navigation", "Upon clicking on PPS Tab,Not Navigated to PPS Plugin ", "FAIL");
	}

	
	By PPSdialysisxpath =By.xpath("//*[@id='i[__valueField]']/a[contains(.,'PPS Dialysis')]");
	
	By RateFactorsxpath =By.xpath("//*[@id='ppView']//a[text()='Rate Factors']");
	
	By SearchRateFactorsxpath =By.xpath("//*[@id='ppView']//input[@placeholder='Search Rate Factors']");
	
	By RateFactor_PageTitlexpath =By.xpath("//*[@id='ppView']//div[@class='pad-l-10 col-md-6 col-md-5 col-sm-6 large-header ng-binding']");
	
	
	public void SelectPPSdialysis()
	{
		click_button("click on drop down", DropdownPPS);
		
		waitFor(PPSdialysisxpath, "");
		click_button("click on PPSdialysis", PPSdialysisxpath);
		
		waitFor(RateFactorsxpath, "");
		click_button("click on Ratefactors", RateFactorsxpath);
		
		waitFor(SearchRateFactorsxpath, "");
		enter_text_value("Entering a value in search box", SearchRateFactorsxpath,oParameters.GetParameters("RatefactorName"));
		performKeyBoardAction("ENTER");
	
		By SearchServiceQGXpath=By.xpath("//li[contains(text(),'" + oParameters.GetParameters("RatefactorName") + "')]");
	
		click_button("click on particular QG ", SearchServiceQGXpath);

		waitFor(RateFactor_PageTitlexpath, "");
		oParameters.SetParameters("RatefactorpageTitle", get_field_value("RateFactor Page Title", RateFactor_PageTitlexpath));
		System.out.println("Page Title Validation"+oParameters.GetParameters("RatefactorpageTitle"));
		
		if(oParameters.GetParameters("RatefactorName").equals(oParameters.GetParameters("RatefactorpageTitle")))		
			oReport.AddStepResult("Ratefactor Page Validation", "Selected Ratefactor page gets opened", "PASS");		
		else
			oReport.AddStepResult("Ratefactor Page Validation", "Selected Ratefactor page Not opened", "FAIL");	
	}
	
	
	By LabFeescheduleQG =By.xpath("//*[@id='labQualGroupId']");
	
	By RatefactorSavebutton = By.xpath("//*[@id='rfPeriodSave']");
	
	
	public void searchServiceQGinRatefactor()
	{
		waitFor(LabFeescheduleQG, "");
		enter_text_value("clear field", LabFeescheduleQG, " ");
		enter_text_value("Enter text on Search Text Box", LabFeescheduleQG,oParameters.GetParameters("QGServiceSaveAsName"));
		fixed_wait_time(3);
		performKeyBoardAction("ENTER");
		
	
		click_button("Rate factor Save Button", RatefactorSavebutton);	
	}
	
	
	public void QG_ServiceQualification() 
	{
		login("EDIT");
	    changePricingEngine(); 
	    xpathChange();
	    navigateQualificationPlugin();
	    addQualificationGroup();
	    AddQG_ServiceDetails();
	    ValidationButton();
	    QG_servicesaveButton();
	    SearchQG_service();
	    ModifyQualificationGroup();
	    EditPageValidationButton();
	    SaveAsQGservice(); 
	   
	    DeleteQG();
	    DRGCancelScenario();
	    DeleteQG();
	    DRGDeleteScenario();
	    addQualificationGroup();
	    CancelQG_serviceDetails();
	    ValidationButton();
	    CancelButton();
	    
	    navigatePPSPlugin();
	    SelectPPSdialysis();
	    searchServiceQGinRatefactor();
		navigateQualificationPlugin();
		SearchQG_service();
	//	ExportButton(); //- Defect->to be validated after defect is fixed.
		logout();
	}
}
		




	
	



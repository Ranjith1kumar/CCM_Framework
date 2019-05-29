package contractManagement;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import libraries.*;
import libraries.Parameters;

public class QGLibrary extends CCMLibrary
{
	RateSheetLibrary oRateSheetLibrary = new RateSheetLibrary(driver, oReport, oParameters);
	ExcelData oExcelData = new ExcelData(oParameters);
	PPSLibrary oPPSLibrary = new PPSLibrary(driver, oReport, oParameters);
	
		
	public QGLibrary(WebDriver driver, Report oReport,Parameters oParameters) 
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
	
	By firstQualifierRespectivePage = By.xpath("//div[@id='qualificationgroupView']//div[@class='col-lg-6 col-md-6 col-sm-6 col-xs-12 large-header pad-l-0 ng-binding']");
	
	By firstQualifierRespectivePageDC = By.xpath("//div[@id='qualificationgroupView']//div[@class='col-lg-12 col-md-12 col-sm-12 col-xs-12 large-document-header pad-l-0 hide-overflow ng-binding']");

	By SearchRatesheetNameXpath=By.xpath("//div[@class='ng-binding'][contains(.,'"+oParameters.GetParameters("RatesheetName")+"')]");
	
	By SearchRatesheetNameXpathDC=By.xpath("//div[@class='ng-binding' and contains(.,'"+oParameters.GetParameters("RatesheetName")+"')]");

	By QualificationGroupTab = By.xpath("//div[@id='nav']//a[contains(text(),'Qualification Groups')]");
	
	By TermExclusionsQualification = By.xpath("//li[@id=\"i[__valueField]\"]/a[contains(.,'Term/E')]");
		
	By firstElementName = By.xpath("//ul[contains(@class,'data-list drillable-list scroll-y-auto')]//li[1]//div[@class='col-lg-11 col-md-11 col-sm-11 ng-binding']");
	
	By searchExpression = By.xpath("//div[@id='qualificationgroupView']//input[contains(@title,'Search Qualifier Expression')]");
	
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
			
		if(IsElementDisplayed("First Qualification Gorup", firstElement))
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
	public void SearchQualifier()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		
		click_button("QG Search box", searchExpression);
		performKeyBoardAction("ENTER");
			
		oParameters.SetParameters("particularQualifier", get_text(firstElement));
		
		enter_text_value("SearchExpression",searchExpression,oParameters.GetParameters("particularQualifier"));
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
		navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
		xpathChange();
		
		ArrayList<By> oarraylist1 = new ArrayList<>();
		
		oarraylist1.add(TermExclusionsQualification);
		oarraylist1.add(DRG_qualificationGroup);
		oarraylist1.add(sectionQualifierGroup);
		oarraylist1.add(Revenue_code_qualificationgroup);
		oarraylist1.add(StopLossFormula_QualificationGroup);
		oarraylist1.add(StopLoss_QualificationGroup);
		oarraylist1.add(Service_QualificationGroup);
		
		for(int i=0;i<oarraylist1.size();i++)
		{
			QualificationGroup(oarraylist1.get(i));
			searchBoxValidation();
			FirstQualificationGroup(firstElementName);
			SearchQualifier();
		}
		
		logout();
	}
		
	By qualificationGroupLink = By.xpath("//a[contains(.,'Qualification Groups')]");
	
	By qualificationGroupTypeDD = By.xpath("//div[@id='qualificationgroupView']//a[@role='button'][@class='btn btn-light view-bg-text']");
	
	By addQualificationGroupButton = By.xpath("//div[contains(.,'Add Qualification Group')][@role='button']");
	
	By addQualificationGroupWindowTitle = By.xpath("//div[@title='Add Qualification Group']");
	
	//Method to generate Qualification Group Dropdown Xpath
	public By generateQGDDXpath(String QGType)
	{
		By qgType = By.xpath("//div[@items='caeTypes']//ul[@class='dropdown-menu'][@role='menu']//li//a[contains(.,'" + QGType + "')]");
		return qgType;
	}
	
	
	By qgList = By.xpath("//ul[@class='data-list drillable-list scroll-y-auto']");
	
	//Method to select QG type from dropdown
	public void selectQGType(String QGType)
	{
		if(IsElementDisplayed("Qualification Group Type Dropdown", qualificationGroupTypeDD))
		{
			click_button("Qualificatio Groupn Type Dropdown", qualificationGroupTypeDD);
			
			By typeOfQG = generateQGDDXpath(QGType);
			click_button(QGType, typeOfQG);
			
			waitFor(qgList, "Qualification Group List");
		
			if(QGType.equalsIgnoreCase(get_field_value("Qualification Group type Drop Down", qualificationGroupTypeDD)))
				oReport.AddStepResult("QG Type Selected", "Clicked on Select a QG type drop down and clicked on " + QGType + "option and verified that resective page is opened", "PASS");
			else
				oReport.AddStepResult("QG Type Selected", "Clicked on Select a QG type drop down and clicked on " + QGType + "option however resective page is not opened", "FAIL");
		}
	}
	
	
	By environmentLink = By.xpath("//p[contains(.,'Environment')]");

	
	//Method to generate xpath for Main Attribute
	public By main_sub_AttributeXpath(String attributeName)
	{
		By mainAttribute = By.xpath("//div[@id='environment']//ul//li//p[contains(.,'" + attributeName + "')]");
		return mainAttribute;
	}
	
	
	By qualificationGroupNameTextBox = By.xpath("//div[@id='addQualificationGroupModal']//*[@id='expression-name']");
	
	By validationButton = By.xpath("//button[@id='validationButton']");
	
	By addQGSaveButton = By.xpath("//div[@id='fullFooter']//input[@value='Save']");
	
	By addQGCancelButton = By.xpath("//div[@id='fullFooter']//input[@value='Cancel']");
	
	By popupCancelButton = By.xpath("//div[@id='dialog']//input[@value='Cancel']");
	
	By popupDiscardButton = By.xpath("//div[@id='dialog']//input[@value='Discard']");
	
	By EQOperator = By.xpath("//select[@id='symbols1']//option[contains(.,'EQ')]");
	
	By plusOperator = By.xpath("//select[@id='symbols1']//option[contains(.,'+')]");
	
	By expressionTextArea = By.xpath("//textarea[@id='expression']");
	
	By validLabel = By.xpath("//div[contains(.,'Valid')][@class='fa fa-check ng-binding']");
	
	//Method to add QG details on Add QG window
	public void addQGDetails(String qgName)
	{
		//oParameters.SetParameters("QGName", get_random_string(5));

		oParameters.SetParameters("QualificationGroupName", qgName + System.currentTimeMillis());
	
		enter_text_value_jscript("QG Name", qualificationGroupNameTextBox, oParameters.GetParameters("QualificationGroupName"));

	}
	
	//Method to generate Valid expression for Term/Exclusion
	public void term_exclusion_valid_expression_validation()
	{
		//To click on environmant
		if(IsDisplayed("", main_sub_AttributeXpath("Claim Level - All Lines")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Claim Level - All Lines", main_sub_AttributeXpath("Claim Level - All Lines"));
		click_button("Accident Indicator", main_sub_AttributeXpath("Age at Admit Date"));
		click_button("Equals Operator", EQOperator);
		click_button("Expression Textbox", expressionTextArea);
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		oReport.AddStepResult("Expression entered", "Valid expression entered", "SCREENSHOT");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
				
		if(IsElementDisplayed("Valid Expression Label", validLabel))
			oReport.AddStepResult("Term QG valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is displayed, hence validation successfull", "PASS");
		else
			oReport.AddStepResult("Term QG valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is not displayed, hence validation is not successfull", "FAIL");
	}
	
	public void section_exclusion_valid_expression_validation()
	{
		//To click on environmant
		if(IsDisplayed("", main_sub_AttributeXpath("Claim Level - All Lines")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Claim Level - All Lines", main_sub_AttributeXpath("Claim Level - All Lines"));
		click_button("Accident Indicator", main_sub_AttributeXpath("Age at Admit Date"));
		click_button("Equals Operator", EQOperator);
		click_button("Expression Textbox", expressionTextArea);
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		oReport.AddStepResult("Expression entered", "Valid expression entered", "SCREENSHOT");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Valid Expression Label", validLabel))
			oReport.AddStepResult("Section QG valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is displayed, hence validation successfull", "PASS");
		else
			oReport.AddStepResult("Section QG valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is not displayed, hence validation is not successfull", "FAIL");
	}
	
	public void revenueCodeQualification_valid_expression_validation()
	{
		if(IsDisplayed("", main_sub_AttributeXpath("Revenue Code")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);

		click_button("Claim Level - All Lines", main_sub_AttributeXpath("Revenue Code"));
		
		click_button("Equals Operator", EQOperator);
		click_button("Expression Textbox", expressionTextArea);
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		oReport.AddStepResult("Expression entered", "Valid expression entered", "SCREENSHOT");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Valid Expression Label", validLabel))
			oReport.AddStepResult("Section QG valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is displayed, hence validation successfull", "PASS");
		else
			oReport.AddStepResult("Section QG valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is not displayed, hence validation is not successfull", "FAIL");
	}
	
	public void stopLossFormula_valid_expression_validation()
	{
		if(IsDisplayed("", main_sub_AttributeXpath("Stop Loss Data")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Stop Loss Data", main_sub_AttributeXpath("Stop Loss Data"));
		click_button("Covered Charge Amount", main_sub_AttributeXpath("Covered Charge Amount"));
		
		click_button("Equals Operator", EQOperator);
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value("Value to enter", expressionTextArea, "CurrentPolicy.coveredDays *10");
		
		oReport.AddStepResult("Expression entered", "Valid expression entered", "SCREENSHOT");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Valid Expression Label", validLabel))
			oReport.AddStepResult("StopLoss Formula Qualification valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is displayed, hence validation successfull", "PASS");
		else
			oReport.AddStepResult("StopLoss Formula Qualification valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is not displayed, hence validation is not successfull", "FAIL");
	}
	
	public void stopLossQualification_valid_expression_validation()
	{
		if(IsDisplayed("", main_sub_AttributeXpath("Stop Loss Data")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Stop Loss Data", main_sub_AttributeXpath("Stop Loss Data"));
		click_button("Covered Charge Amount", main_sub_AttributeXpath("Covered Charge Amount"));
		
		click_button("Equals Operator", EQOperator);
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		oReport.AddStepResult("Expression entered", "Valid expression entered", "SCREENSHOT");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Valid Expression Label", validLabel))
			oReport.AddStepResult("StopLoss Qualification valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is displayed, hence validation successfull", "PASS");
		else
			oReport.AddStepResult("StopLoss Qualification valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is not displayed, hence validation is not successfull", "FAIL");
	}
	
	public void serviceQualification_valid_expression_validation()
	{
		if(IsDisplayed("", main_sub_AttributeXpath("Current Charge")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Claim Level - All Lines", main_sub_AttributeXpath("Current Charge"));
		click_button("Charge Amount", main_sub_AttributeXpath("Charge Amount"));
		click_button("Equals Operator", EQOperator);
		
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Valid Expression Label", validLabel))
			oReport.AddStepResult("Service Qualification valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is displayed, hence validation successfull", "PASS");
		else
			oReport.AddStepResult("Service Qualification valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is not displayed, hence validation is not successfull", "FAIL");
	}
	
	
	By invalidLabel = By.xpath("//div[@class='error']//div[contains(.,'Not Valid:')]");
	
	//Method to generate invalid Expression for Term/Exclusion
	public void term_exclusion_invalid_expression_validation()
	{
		//To click on environmant
		if(IsDisplayed("", main_sub_AttributeXpath("Claim Level - All Lines")))
				System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Claim Level - All Lines", main_sub_AttributeXpath("Claim Level - All Lines"));
		click_button("Accident Indicator", main_sub_AttributeXpath("Age at Admit Date"));
		click_button("Plus Operator", plusOperator);
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Invalid Expression Label", invalidLabel))
			oReport.AddStepResult("Term QG invalid expression validation", "Entered invalid expression, clicked on validate button and verified that invalid label is displayed, hence validation is successfull", "PASS");
		else
			oReport.AddStepResult("Term QG invalid expression validation", "Entered valid expression, clicked on validate button and verified that invalid label is displayed, hence validation is not successfull", "FAIL");
	}
	
	
	public void sectionQualification_invalid_expression_validation()
	{
		//To click on environmant
		if(IsDisplayed("", main_sub_AttributeXpath("Claim Level - All Lines")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
				
		click_button("Claim Level - All Lines", main_sub_AttributeXpath("Claim Level - All Lines"));
		click_button("Accident Indicator", main_sub_AttributeXpath("Age at Admit Date"));
		click_button("Plus Operator", plusOperator);
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Invalid Expression Label", invalidLabel))
			oReport.AddStepResult("Section QG invalid expression validation", "Entered invalid expression, clicked on validate button and verified that invalid label is displayed, hence validation is successfull", "PASS");
		else
			oReport.AddStepResult("Section QG invalid expression validation", "Entered valid expression, clicked on validate button and verified that invalid label is displayed, hence validation is not successfull", "FAIL");
	}
	
	public void revenueCodeQualification_invalid_expression_validation()
	{
		if(IsDisplayed("", main_sub_AttributeXpath("Revenue Code")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Claim Level - All Lines", main_sub_AttributeXpath("Revenue Code"));
		click_button("Plus Operator", plusOperator);
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Invalid Expression Label", invalidLabel))
			oReport.AddStepResult("Revenue QG invalid expression validation", "Entered invalid expression, clicked on validate button and verified that invalid label is displayed, hence validation is successfull", "PASS");
		else
			oReport.AddStepResult("Revenue QG invalid expression validation", "Entered valid expression, clicked on validate button and verified that invalid label is displayed, hence validation is not successfull", "FAIL");
	}
	
	public void stopLossFormula_invalid_expression_validation()
	{
		if(IsDisplayed("", main_sub_AttributeXpath("Stop Loss Data")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Stop Loss Data", main_sub_AttributeXpath("Stop Loss Data"));
		click_button("Covered Charge Amount", main_sub_AttributeXpath("Covered Charge Amount"));
		
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Invalid Expression Label", invalidLabel))
			oReport.AddStepResult("StopLoss Formula QG invalid expression validation", "Entered invalid expression, clicked on validate button and verified that invalid label is displayed, hence validation is successfull", "PASS");
		else
			oReport.AddStepResult("StopLoss Formula QG invalid expression validation", "Entered valid expression, clicked on validate button and verified that invalid label is displayed, hence validation is not successfull", "FAIL");

	}
	

	public void stopLossQualification_invalid_expression_validation()
	{
		if(IsDisplayed("", main_sub_AttributeXpath("Stop Loss Data")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Stop Loss Data", main_sub_AttributeXpath("Stop Loss Data"));
		click_button("Covered Charge Amount", main_sub_AttributeXpath("Covered Charge Amount"));
		click_button("Plus Operator", plusOperator);
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Invalid Expression Label", invalidLabel))
			oReport.AddStepResult("StopLoss QG invalid expression validation", "Entered invalid expression, clicked on validate button and verified that invalid label is displayed, hence validation is successfull", "PASS");
		else
			oReport.AddStepResult("StopLoss QG invalid expression validation", "Entered valid expression, clicked on validate button and verified that invalid label is displayed, hence validation is not successfull", "FAIL");
	}
	
	public void serviceQualification_invalid_expression_validation()
	{
		if(IsDisplayed("", main_sub_AttributeXpath("Current Charge")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Claim Level - All Lines", main_sub_AttributeXpath("Current Charge"));
		click_button("Charge Amount", main_sub_AttributeXpath("Charge Amount"));
		click_button("Plus Operator", plusOperator);
		
		click_button("Expression Textbox", expressionTextArea);
		
		enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Invalid Expression Label", invalidLabel))
			oReport.AddStepResult("Service QG invalid expression validation", "Entered invalid expression, clicked on validate button and verified that invalid label is displayed, hence validation is successfull", "PASS");
		else
			oReport.AddStepResult("Service QG invalid expression validation", "Entered valid expression, clicked on validate button and verified that invalid label is displayed, hence validation is not successfull", "FAIL");
	}
	
	By QGNotSavedRedBox = By.xpath("//span[contains(.,' Qualification group not saved.')]");
	
	By errorSavingQGExprpopup = By.xpath("//div[@class='medium-header bold ng-binding']");
	
	By popupOKButton = By.xpath("//input[@value='OK']");
	
	By unsavedChangesPopup = By.xpath("//div[@class='msg-area']//div[contains(.,'You have unsaved changes')]");
	
	//Method to validate newly added QG
	public void addQGValidation(String qgType, String qgName)
	{
		if(IsDisplayed("QG not saved Red Box Error", QGNotSavedRedBox) || IsDisplayed("Error Saving Expression Popup", errorSavingQGExprpopup))
		{
			click_button("popup Ok Button", popupOKButton);
			addQGDetails(qgName);
			clickSaveButton("Add QG window Save Button", "Add Qualification Group Window", addQGSaveButton, addQualificationGroupWindowTitle);			
		}
			
		By addedQGTitle = By.xpath("//div[@id='qualificationgroupView']//div[@ng-if='selectedExpression.id']//div[text()='" + qgName + "']");
		
		if(oParameters.GetParameters("QualificationGroupName").equalsIgnoreCase(get_field_value("Added QG page Title", addedQGTitle)))
			oReport.AddStepResult("Add " + qgType + "QG validation", "Entered " + qgName + " and verified " + qgType + "QG created successfully", "PASS");
		else
			oReport.AddStepResult("Add " + qgType + "QG validation", "Entered " + qgName + " and verified " + qgType + "QG not created", "FAIL");
	}
	
	
	By searchQualificationgroupSearchBox = By.xpath("//div[@id='qualificationgroupView']//input[@title='Search Qualifier Expression ']");
	
	By qgTitle = By.xpath("//div[@id='qualificationgroupView']//div[@class='document-title-bar pad-l-10']/div[1]");
	
	//Method to search QG
	public void searchQualificationGroup(String qgName)
	{
		enter_text_value("Enter text on Search Text Box", searchQualificationgroupSearchBox, qgName);
		oReport.AddStepResult("Enter QG name in Search box", "Entered " + qgName + " insearch box", "SCREENSHOT");
				
		By searchedQG=By.xpath("//div[@title='" + qgName + "']");
		
		if(click_button("click on particular QG ", searchedQG))
		{
			//Need to remove these 2 lines Its a defect
			if(IsDisplayed("You have unsaved changes popup", unsavedChangesPopup))
				click_button("Popup Discard Button", popupDiscardButton);
			
			if(get_field_value("QG Title", qgTitle).equalsIgnoreCase(qgName))
				oReport.AddStepResult("Search Qualification Group", "Entered " + qgName + " QG name in search box and verified that searched QG opened successfully", "PASS");	
			else
				oReport.AddStepResult("Search Qualification Group", "Entered " + qgName + " QG name in search box, however it failed to open respective QG", "FAIL");	
		}
		else	
			oReport.AddStepResult("Search Qualification Group", "Entered " + qgName + " QG name in search box, however entered QG not found in the list", "FAIL");
	}
	
	
	By editQGNameTextBox = By.xpath("//form[@id='editQualExpressionBuilder']//input[@id='expression-name']");
	
	By editQGSaveButton = By.xpath("//div[@id='fullFooter']//button[contains(.,'Save')]");
	
	By successQGMessage = By.xpath("//ul[@class='success-items']");
	
	//Qualification Group Edit Scenario
	public void editQGName(String typeOfQG)
	{
		enter_text_value_without_clear(typeOfQG + " Edit QG Name", editQGNameTextBox, get_random_string(2));
		oParameters.SetParameters("EditedQualificationGroup", get_field_value("Edited QG Name", editQGNameTextBox));
		
		click_button(typeOfQG + " Edit QG Save Button", editQGSaveButton);
		
		waitFor(successQGMessage, "Success QG message");
		
		By editedQGTitle = By.xpath("//div[@id='qualificationgroupView']//div[@class='document-title-bar pad-l-10']/div[contains(.,'" + oParameters.GetParameters("QualificationGroupName") + "')]");
		
		waitFor(editedQGTitle, "Edited QG Title");
		
		if(get_field_value(typeOfQG + " Edited QG Title", qgTitle).equalsIgnoreCase(oParameters.GetParameters("EditedQualificationGroup")))
			oReport.AddStepResult(typeOfQG + " Edit Qualification Group Name", "Entered " + oParameters.GetParameters("EditedQualificationGroup") + " QG name in text box for "+ typeOfQG + " and verified that QG name changed successfully", "PASS");	
		else
			oReport.AddStepResult("Edit Qualification Group Name", "Entered " + oParameters.GetParameters("EditedQualificationGroup") + " QG name in text box for " + typeOfQG + " , however QG name is not changed", "FAIL");
		
		//somehow we neesd to write QG name to excel
		//oParameters.SetParameters("FormulaName", oParameters.GetParameters("EditedQualificationGroup"));
		//oParameters.SetParameters("QualificationGroup2", oParameters.GetParameters("QualificationGroup"));
	}
	
	
	By saveAsQGButton = By.xpath("//div[@id='fullFooter']//button[@class='btn  btn-primary dropdown-toggle']");
	
	By saveAsQGLink = By.xpath("//a[contains(.,'Save As...')]");
	
	By saveAsQGPopup = By.xpath("//form[@id='qualifierSaveAsTemplate']");
	
	By saveAsQGPopupNameTextBox = By.xpath("//input[@name='qualifierSaveAsTemplateName']");
	
	By saveAsQGPopupSaveButton = By.xpath("//form[@id='qualifierSaveAsTemplate']//input[@value='Save']");
	
	
	//Qualification SaveAs Scenario
	public void saveAsQGScenario(String typeOfQG)
	{
		if(!IsDisplayed(typeOfQG + " QG Save As Button", saveAsQGButton) && !IsElementEnabled(typeOfQG + " QG Save As Button", saveAsQGButton))
			oReport.AddStepResult(typeOfQG + "QG Save AS Button", "Save As Button is not Displayed/Enabled for " + typeOfQG + " type of QG", "FAIL");
		else
		{
			click_button(typeOfQG + " QG Save As Button", saveAsQGButton);
			
			click_button(typeOfQG + " QG Save As Link", saveAsQGLink);
			
			if(!IsElementDisplayed(typeOfQG + " QG Save As Popup", saveAsQGPopup))
				oReport.AddStepResult(typeOfQG + " QG Save As Popup", "QG save As popup is not displayed for " + typeOfQG + " type of QG", "FAIL");
			else
			{
				oReport.AddStepResult(typeOfQG + " QG Save As Popup", "QG save As popup is displayed for " + typeOfQG + " type of QG", "PASS");
				
				if(get_field_value("Save As GQ Popup Name textbox value", saveAsQGPopupNameTextBox).isEmpty())
				{
					oParameters.SetParameters("saveAsQGName", "CopyOf" + oParameters.GetParameters("QualificationGroupName"));
					enter_text_value_jscript("saveAsQGName", saveAsQGPopupNameTextBox, oParameters.GetParameters("saveAsQGName"));
				}
				else
					oParameters.SetParameters("saveAsQGName", get_field_value("Save As GQ Popup Name textbox value", saveAsQGPopupNameTextBox));
				
				clickSaveButton("Save as  QG popup save button", "Save As QG popup", saveAsQGPopupSaveButton, saveAsQGPopup);
				
				waitFor(successQGMessage, "Success QG Message");
				
				if(oParameters.GetParameters("saveAsQGName").equalsIgnoreCase(get_field_value("Save As QG Title", qgTitle)))
					oReport.AddStepResult(typeOfQG + " QG save As scenario", "Entered "+ oParameters.GetParameters("saveAsQGName") + " for " + typeOfQG + " QG and verified that it has opened successfully", "PASS");
				else
					oReport.AddStepResult(typeOfQG + " QG save As scenario", "Entered "+ oParameters.GetParameters("saveAsQGName") + " for " + typeOfQG + " QG and verified that it has not opened successfully", "FAIL");
			}
		}
	}
	
	
	By deleteQGPopup = By.xpath("//div[@id='dialog']");
	
	By deleteQGPopupDeleteButton = By.xpath("//div[@id='dialog']//input[@value='Delete']");
	
	By deleteQGPopupCancelButton = By.xpath("//div[@id='dialog']//input[@value='Cancel']");
	
	public void deleteQGCancelScenario(String qgName)
	{
		enter_text_value("Enter text on Search Text Box", searchQualificationgroupSearchBox, qgName);
		
		By searchedQG=By.xpath("//div[@title='" + qgName + "']");
		
		mouse_hover("Hower on " + qgName + " QG", searchedQG);
		
		By deleteQGButton = By.xpath("//li[@class='data-row drillable hand-cursor ng-scope']/div[@title='" + qgName + "']/..//a/i[@class='left fa fa-minus-square']");
		
		clickAddButton(qgName + " QG Delete button", "Delete QG popup", deleteQGButton, deleteQGPopup);
		
		clickSaveButton("Delete QG popup Cancel Button", "Delete QG popup", deleteQGPopupCancelButton, deleteQGPopup);
		
		mouse_hover("Hower on " + qgName + " QG", searchedQG);
		
		clickAddButton(qgName + " QG Delete button", "Delete QG popup", deleteQGButton, deleteQGPopup);
	
		clickSaveButton("Delete QG popup Delete Button", "Delete QG popup", deleteQGPopupDeleteButton, deleteQGPopup);
		
		enter_text_value("Enter text on Search Text Box", searchQualificationgroupSearchBox, qgName);
		
		if(IsDisplayed(qgName + " QG", searchedQG))
			oReport.AddStepResult(qgName + " QG", "Deleted " + qgName + " QG, however QG is still displayed in search list, hence QG delete scenario is failed", "FAIL");	
		else
			oReport.AddStepResult(qgName + " QG", "Deleted " + qgName + " QG, and QG is not displayed in search list, hence QG delete scenario is passed", "PASS");	
	}
	
	//Method to Delete QG
	public void deleteQualificationGroup(String qgName)
	{
		enter_text_value("Enter text on Search Text Box", searchQualificationgroupSearchBox, qgName);
			
		By searchedQG=By.xpath("//div[@title='" + qgName + "']");
			
		mouse_hover("Hower on " + qgName + " QG", searchedQG);
			
		By deleteQGButton = By.xpath("//li[@class='data-row drillable hand-cursor ng-scope']/div[@title='" + qgName + "']/..//a/i[@class='left fa fa-minus-square']");
			
		clickAddButton(qgName + " QG Delete button", "Delete QG popup", deleteQGButton, deleteQGPopup);
			
		clickSaveButton("Delete QG popup Delete Button", "Delete QG popup", deleteQGPopupDeleteButton, deleteQGPopup);
	}
	
	//Method to Delete QG
	public void deleteQualificationGrouptocheckerrormessage(String qgName)
	{
		
		By redBoxError = By.xpath("//ul[@class='error-items']");	
		
		By redBoxErrorCloseIcon = By.xpath("//span[@ng-show='canShowCloseBox']");
		
		enter_text_value("Enter text on Search Text Box", searchQualificationgroupSearchBox, qgName);
			
		By searchedQG=By.xpath("//div[@title='" + qgName + "']");
			
		mouse_hover("Hower on " + qgName + " QG", searchedQG);
			
		By deleteQGButton = By.xpath("//li[@class='data-row drillable hand-cursor ng-scope']/div[@title='" + qgName + "']/..//a/i[@class='left fa fa-minus-square']");
			
		click_button("Delete Icon QG", deleteQGButton);
		
		click_button("Delete Button in Message Popup", deleteQGPopupDeleteButton);
		
		if(IsDisplayed("RedBoxError", redBoxError))
		{
			oReport.AddStepResult("Red Box Screen", "Red Box Validation", "PASS");
			click_button("RedBoxCloseButton", redBoxErrorCloseIcon);
		}
		else
		{
			oReport.AddStepResult("Red Box Error CLosed", "Upon clicking on delete button, associated Qualification Group is deleted ", "FAIL");
			click_button("RedBoxCloseButton", redBoxErrorCloseIcon);
		}
	
		
	}

	public void redboxCheckQG(String qgName)
	{
		if(oParameters.GetParameters("REDBOX_APPEARED").equalsIgnoreCase("YES"))
		{
			cancelScenario("Add Service Qualification window cancel button", "Add StopLoss Code QG Popup cancel Button", "Add StopLoss Code QG popup Discard button", addQGCancelButton, popupCancelButton, popupDiscardButton);
	    	
			enter_text_value("Enter text on Search Text Box", searchQualificationgroupSearchBox, qgName);
			
			By searchedQG=By.xpath("//div[@title='" + qgName + "']");
				
			mouse_hover("Hower on " + qgName + " QG", searchedQG);
				
			By deleteQGButton = By.xpath("//li[@class='data-row drillable hand-cursor ng-scope']/div[@title='" + qgName + "']/..//a/i[@class='left fa fa-minus-square']");
				
			clickAddButton(qgName + " QG Delete button", "Delete QG popup", deleteQGButton, deleteQGPopup);
				
			clickSaveButton("Delete QG popup Delete Button", "Delete QG popup", deleteQGPopupDeleteButton, deleteQGPopup);
		}
		else
		{
			System.out.println("Redbox not appeared...");
		}
	}
	
	By termUsageTab = By.xpath("//a[contains(.,'Exclusion Usage')]");
	
	By exclusionUsageTab = By.xpath("//a[contains(.,'Exclusion Usage')]");
	
	//Method to do Usage tab validation
	public void usageTabValidation(String tabName, String rateSheetName)
	{
		By usageTab = By.xpath("//a[contains(.,'" + tabName + "')]");
		
		if(IsDisplayed(tabName, usageTab))
		{
			click_button(tabName, usageTab);
			
			By usageTabTitle = By.xpath("//h3[contains(.,'" + tabName + "')]");
			
			if(IsDisplayed(tabName, usageTabTitle))
			{
				oReport.AddStepResult(tabName + " Validation", "Clicked on " + tabName + " and verified that respective tab is displayed", "PASS");
				
				if(tabName.equalsIgnoreCase("Dialysis Usage"))
				{
					By dialysisRateFactor = By.xpath("//td[contains(.,'"+rateSheetName +"')]");
					if(IsElementDisplayed("Dialysis Rate Factor", dialysisRateFactor))
						oReport.AddStepResult("Created Dialysis Rate Factor", "Service Qualification Group is used in "+ oParameters.GetParameters("RateFactorSetName") + "Dialysis Rate Factor and could see it in Dialysis Usage tab of Service Qualification type of QG", "PASS");
					else
						oReport.AddStepResult("Created Dialysis Rate Factor", "Service Qualification Group is used in "+ oParameters.GetParameters("RateFactorSetName") + " however couldnt see it in Dialysis Usage tab of Service Qualification type of QG", "PASS");
				}
				else
				{
					By rateSheetLink = By.xpath("//a[contains(.,'" + rateSheetName.toUpperCase() + "')]");
					
					click_button(rateSheetName, rateSheetLink);
					
					rateSheetValidation(rateSheetName);
				}
			}
			else
				oReport.AddStepResult(tabName + " Validation", "Clicked on " + tabName + " and verified that respective tab is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult(tabName, tabName + " not displayed", "FAIL");
		
	}
	
	By rateSheetCode = By.xpath("//div[@class='document-title-bar ng-scope']/div[@class='pull-right xl-header pad-r-5 ng-binding']");
	
	//Method to check if correct rate sheet is opened or not
	public void rateSheetValidation(String rateSheetName)
	{
		
		
		//By rateSheetTitle = By.xpath("//div[@class='document-title-bar ng-scope']//div[contains(.,'" + rateSheetName + "')]");
		
		//if(IsDisplayed(rateSheetName, rateSheetTitle))
		if(rateSheetName.equalsIgnoreCase(get_field_value("Rate Sheet Code", rateSheetCode).replace("Code ", "")))
			oReport.AddStepResult(rateSheetName + " Validation", rateSheetName + " matched with opened RateSheet in Rate sheet plugin", "PASS");
		else
			oReport.AddStepResult(rateSheetName + " Validation", rateSheetName + " didnt matched with opened RateSheet in Rate sheet plugin", "FAIL");	
	}
	
	By addQGpopupCancelButton = By.xpath("");
		
	By qgDetailsLabel = By.xpath("//a[contains(.,'Details')]");
	
	
	public void term_exclusion_qualification() 
	{
		login("EDIT");
    	changePricingEngine(); 
    	
    	//To read data from RateSheets_TestData excel 
     	//oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","TERM");
     	
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	//To select Term/Exclusion type of QG from drop down
    	selectQGType("Term/Exclusions Qualification");
    	
    	//To click on ADD QG botton
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	//To add Term_Exclusion QG details
    	addQGDetails("TERM_EXCLUSION_QG");
    	//Term Exclusion Invalid expression
    	term_exclusion_invalid_expression_validation();
    	//Add term Exclusion window Cancel scenario
    	cancelScenario("Add Term QG window cancel button", "Add Term QG Popup cancel Button", "Add Term QG popup Discard button", addQGCancelButton, popupCancelButton, popupDiscardButton);
    	
    	//To click on ADD QG botton
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	//To add Term_Exclusion QG details
    	addQGDetails("TERM_EXCLUSION_QG");
    	//Term Exclusion valid expression
    	term_exclusion_valid_expression_validation();
    	//To click on Add Term/Exclusion window save button
		clickSaveButton("Add QG window Save Button", "Add Qualification Group Window", addQGSaveButton, addQualificationGroupWindowTitle);
		//Add new Term Exclusion QG validation
    	addQGValidation("TermExclusion", oParameters.GetParameters("QualificationGroupName"));
    	
    	//oExcelData.setExcelData1("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 2, 9, oParameters.GetParameters("QualificationGroupName"));
    	//oExcelData.setExcelData1("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 1, 9, oParameters.GetParameters("QualificationGroupName"));
    	oExcelData.setExcelData("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 2, 9, oParameters.GetParameters("QualificationGroupName"));
    	oExcelData.setExcelData("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 1, 9, oParameters.GetParameters("QualificationGroupName"));
    	//To save same QG with new name (Save As Scenario)
    	saveAsQGScenario("TERM_EXCLUSION");
    	
    	//To edit existing QG name
    	editQGName("TERM_EXCLUSION");
    	
    	//To search QG
    	searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	//Navigate to Rate Sheet Plugin
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);
    	
    	//To read data from excel to create ratesheet
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "Term_Exclusion_RS");
    	
    	//To create Rate Sheet
    	oRateSheetLibrary.addRateSheetIcon();
		oRateSheetLibrary.AddRateSheetDetails("");
		oRateSheetLibrary.rateSheetSaveButton("");
		
		//To read data from excel to create Section
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "QG_Section");
		//To create section
		oRateSheetLibrary.AddSectionDetails("","");
    	
		//To read data from excelt o create term
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","TERM");
		
    	//To create term 
		//to click on add term button
		oRateSheetLibrary.addTermButton("", oParameters.GetParameters("RateSheetName"), oParameters.GetParameters("SectionName"));

		//To select Rate type from drp down
	    oRateSheetLibrary.selectRateType("");
	    //Add term save button and validation
	    oRateSheetLibrary.termSaveButton("");
	    	
	    //Navigate to Qualificatio Group Plugin
	    navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
	    	
	    //On QG page Usage tabs validation
	    usageTabValidation(oParameters.GetParameters("UsageTab"), oParameters.GetParameters("RateSheetCode"));
    	
    	
	    //To read data from RateSheets_TestData excel 
	    oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","TERM_EXCL");
	     	
    	//To click on add term button
	    oRateSheetLibrary.addTermButton("", oParameters.GetParameters("RateSheetName"), oParameters.GetParameters("SectionName"));

	    //To select Rate type from drp down
    	oRateSheetLibrary.selectRateType("");
    	//Add term save button and validation
    	oRateSheetLibrary.termSaveButton("");
    	
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	//On QG page Usage tabs validation
    	usageTabValidation("Exclusion Usage", oParameters.GetParameters("RateSheetCode"));
    	
    	
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	//Term Exclusion Export Scenario
    	ExportButton("Term Usage", "Exclusion Usage");
    	
    	//Navigate to Rate Sheet Plugin
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);
    	
    	//To Delete Terms in a ratesheet
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "Term_Exclusion_RS");
    	oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"),oParameters.GetParameters("RateSheetName"));
    	
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("EditedQualificationGroup"));
    	
    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	//To logout of portal
    	logout();
    }
	
	By nullOption = By.xpath("//select[@id='symbols2']/option[contains(.,'NULL')]");
	
	public void DRG_Formula_Invalid_Expression_Validation()
	{
		//To click on environmant
		if(IsDisplayed("", main_sub_AttributeXpath("Claim Level - All Lines")))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		click_button("Current Policy", currentPoilicy);
		click_button("Covered Days", coveredDays);
		click_button("Null Option", nullOption);
		//click_button("Expression Textbox", expressionTextArea);
		
		//enter_text_value_without_clear("Value to enter", expressionTextArea, "5");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Validate Button Pressed", "Validate Button Pressed", "SCREENSHOT");
		
		if(IsElementDisplayed("Invalid Expression Label", invalidLabel))
			oReport.AddStepResult("DRG Formula QG invalid expression validation", "Entered invalid expression, clicked on validate button and verified that invalid label is displayed, hence validation is successfull", "PASS");
		else
			oReport.AddStepResult("DRG Formula QG invalid expression validation", "Entered valid expression, clicked on validate button and verified that invalid label is displayed, hence validation is not successfull", "FAIL");
	}
	
	
	By currentPoilicy = By.xpath("//p[contains(.,'Current Policy')]");
	
	By coveredDays = By.xpath("//p[contains(.,'Covered Days')]");
	
	
	public void DRG_Formula_Valid_Expression_Validation()
	{
		//To click on environmant
		if(IsDisplayed("Current Policy",currentPoilicy))
			System.out.println("already expanded");
		else
			click_button("Environment Link", environmentLink);
		
		/*click_button("Current Policy", currentPoilicy);
		click_button("Covered Days", coveredDays);
		click_button("Equals Operator", EQOperator);
		click_button("Expression Textbox", expressionTextArea);*/
		enter_text_value("Value to enter", expressionTextArea, "( CurrentPolicy.totalChargeAmount - CurrentPolicy.totalAncillaryChargeAmount )* CurrentPolicy.coveredDays");
		
		click_button("Validate Button", validationButton);
		
		oReport.AddStepResult("Valid Expression Label", "Valid expression Label", "SCREENSHOT");
		
		if(IsElementDisplayed("Valid Expression Label", validLabel))
			oReport.AddStepResult("DRG Formula QG valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is displayed, hence validation successfull", "PASS");
		else
			oReport.AddStepResult("DRG Formula QG valid expression validation", "Entered valid expression, clicked in validate button and verified that valid label is not displayed, hence validation is not successfull", "FAIL");
	}	
	
	By qgTypeDD = By.xpath("//select[@id='expression-group-type']");
	
	public void qg_type(String qgType)
	{
		if(IsDisplayed(qgType, qgTypeDD))
			//dclick_button(qgType, qgTypeDD);
		select_option(qgType, qgTypeDD, qgType);
	}
	
	public void DRG_Formula_Expression() 
	{
		login("EDIT");
    	changePricingEngine(); 
      	
    	//To read data from RateSheets_TestData excel 
     	//oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","TERM_DRG");
     	
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Group", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	//To select DRG Formula type of QG from drop down
    	selectQGType("DRG Formula Expression");
    	
    	//To click on ADD QG botton
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	//To add Term_Exclusion QG details
    	addQGDetails("DRG_FORMULA_QG");
    	//Term Exclusion Invalid expression
    	DRG_Formula_Invalid_Expression_Validation();
    	//Add term Exclusion window Cancel scenario
    	cancelScenario("Add DRG Formula QG window cancel button", "Add DRG Formula QG Popup cancel Button", "Add DRG Formula QG popup Discard button", addQGCancelButton, popupCancelButton, popupDiscardButton);
    	
    	
    	//Creating DRG QG
    	
    	//To read data from RateSheets_TestData excel 
     	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","DRG_FORMULA");
     	
     	//To click on ADD QG botton
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	//To add Term_Exclusion QG details
    	addQGDetails("DRG_FORMULA_QG");
    	//Term Exclusion valid expression
    	DRG_Formula_Valid_Expression_Validation();
    	//To click on Add Term/Exclusion window save button
		clickSaveButton("Add QG window Save Button", "Add Qualification Group Window", addQGSaveButton, addQualificationGroupWindowTitle);
		//Add new Term Exclusion QG validation
    	addQGValidation("DRG_Formula", oParameters.GetParameters("QualificationGroupName"));
    	
    	
    	oExcelData.setExcelData("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 3, 16, oParameters.GetParameters("QualificationGroupName"));
    	
    	//To save same QG with new name (Save As Scenario)
    	saveAsQGScenario("DRG_Formula");
    	
    	//To edit existing QG name
    	editQGName("DRG_Formula");
    	
    	//To search QG
    	searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	//Navigate to Rate Sheet Plugin
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);
    	
    	//To read data from RateSheets_TestData excel 
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","RateSheet_VR","DRG_Formula_RS");
    	
    	oRateSheetLibrary.addRateSheetIcon();
		oRateSheetLibrary.AddRateSheetDetails("");
		oRateSheetLibrary.rateSheetSaveButton("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "QG_Section");
		oRateSheetLibrary.AddSectionDetails("","");
    	
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","DRG_FORMULA");		
    	//To click on add term button
    	oRateSheetLibrary.addTermButton("", oParameters.GetParameters("RateSheetName"), oParameters.GetParameters("SectionName"));
    	//To select Rate type from drp down
    	oRateSheetLibrary.selectRateType("");
    	//Add term save button and validation
    	oRateSheetLibrary.termSaveButton("");
    	
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	//To search QG
    	//searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	//On QG page Usage tabs validation
    	usageTabValidation("Term Usage", oParameters.GetParameters("RateSheetCode"));
    	   	
    	//Term Exclusion Export Scenario
    	ExportButton("Term Usage", "");
    	
    	//Navigate to Rate Sheet Plugin
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);
    	
    	//To Delete Terms in a ratesheet
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "DRG_FORMULA_RS");
    	oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"),oParameters.GetParameters("RateSheetName"));
    	
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	
    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("EditedQualificationGroup"));
    	
    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	//To logout of portal
    	logout();
    	
	}
	
	public void Section_Qualification() 
	{
		login("EDIT");
    	changePricingEngine(); 
    	xpathChange();
    	
    	//oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","SECTION");
    	
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	selectQGType("Section Qualification");
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	
    	addQGDetails("SECTION_QG");
    	sectionQualification_invalid_expression_validation();
    	cancelScenario("Add Section QG window cancel button", "Add Section QG Popup cancel Button", "Add Section QG popup Discard button", addQGCancelButton, popupCancelButton, popupDiscardButton);
    	
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
	   	addQGDetails("SECTION_QG");
       	section_exclusion_valid_expression_validation();
       	clickSaveButton("Add QG window Save Button", "Add Qualification Group Window", addQGSaveButton, addQualificationGroupWindowTitle);
	
    	addQGValidation("Section Qualification", oParameters.GetParameters("QualificationGroupName"));
	
    	oExcelData.setExcelData("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", 14, 11, oParameters.GetParameters("QualificationGroupName"));
    	
    	saveAsQGScenario("Section Qualification");
    	
    	editQGName("Section Qualification");
    	
    	searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);
    	
    	
    	//Code to create Ratesheet
    	
    	//To read data from RateSheets_TestData excel 
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","RateSheet_VR","Section_Qualification_RS");
    	
    	oRateSheetLibrary.addRateSheetIcon();
		oRateSheetLibrary.AddRateSheetDetails("");
		oRateSheetLibrary.rateSheetSaveButton("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "QG_Section_1");
		oRateSheetLibrary.AddSectionDetails("","");
		
		navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","RateSheet_VR","Section_Qualification_RS");
    	//On QG page Usage tabs validation
    	usageTabValidation("Section Usage", oParameters.GetParameters("RateSheetCode"));
    	   	
    	//Navigate to Rate Sheet Plugin
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);
    	
    	//To Delete Terms in a ratesheet
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "Section_Qualification_RS");
    	oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"),oParameters.GetParameters("RateSheetName"));
    
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);

    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("EditedQualificationGroup"));
    	
    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	//To logout of portal
    	logout();
	}
	
	public void Revenue_Code_Qualification() 
	{
		login("EDIT");
    	changePricingEngine(); 
    	xpathChange();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","REVENUE");
    	
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	selectQGType("Revenue Code Qualification");
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	
    	addQGDetails("REVENUE_QG");
    	revenueCodeQualification_invalid_expression_validation();
    	cancelScenario("Add Revenue Code QG window cancel button", "Add Revenue Code QG Popup cancel Button", "Add Revenue Code QG popup Discard button", addQGCancelButton, popupCancelButton, popupDiscardButton);
    	
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
	   	addQGDetails("REVENUE_QG");
	   	revenueCodeQualification_valid_expression_validation();
	   	clickSaveButton("Add QG window Save Button", "Add Qualification Group Window", addQGSaveButton, addQualificationGroupWindowTitle);
	
	   	addQGValidation("Revenue Code Qualification", oParameters.GetParameters("QualificationGroupName"));
	   	
	   	oExcelData.setExcelData("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 5, 12, oParameters.GetParameters("QualificationGroupName"));
	   	
	   	saveAsQGScenario("Revenue Code Qualification");
	   	
	   	editQGName("Revenue Code Qualification");
	   	
	   	searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);

    	//To read data from RateSheets_TestData excel 
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","RateSheet_VR","Revenue_Code_Qualification_RS");
    	
    	oRateSheetLibrary.addRateSheetIcon();
		oRateSheetLibrary.AddRateSheetDetails("");
		oRateSheetLibrary.rateSheetSaveButton("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetSection", "QG_Section");
		oRateSheetLibrary.AddSectionDetails("","");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","REVENUE");		
    	//To click on add term button
    	oRateSheetLibrary.addTermButton("", oParameters.GetParameters("RateSheetName"), oParameters.GetParameters("SectionName"));
    	//To select Rate type from drp down
    	oRateSheetLibrary.selectRateType("");
    	//Add term save button and validation
    	oRateSheetLibrary.termSaveButton("");
    	
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	//On QG page Usage tabs validation
    	usageTabValidation("Term Usage", oParameters.GetParameters("RateSheetCode"));
    	   
    	//To Delete a ratesheet
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "Revenue_Code_Qualification_RS");
    	oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"),oParameters.GetParameters("RateSheetName"));
    
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);

    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("EditedQualificationGroup"));
    	
    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
	
    	//To logout of portal
    	logout();
	}
	
	public void StopLoss_Formula_Expression() 
	{
		login("EDIT");
    	changePricingEngine(); 
    	xpathChange();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","STOPLOSS_FORMULA");
    	
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	selectQGType("Stop Loss Formula Expression");
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	

    	addQGDetails("STOPLOSS_FORMULA_QG");
    	stopLossFormula_invalid_expression_validation();
    	cancelScenario("Add StopLoss Qualification window cancel button", "Add StopLoss Code QG Popup cancel Button", "Add StopLoss Code QG popup Discard button", addQGCancelButton, popupCancelButton, popupDiscardButton);
    	
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
	   	addQGDetails("STOPLOSS_FORMULA_QG");
	   	stopLossFormula_valid_expression_validation();
	   	clickSaveButton("Add QG window Save Button", "Add Qualification Group Window", addQGSaveButton, addQualificationGroupWindowTitle);
	
	   	addQGValidation("StopLoss Qualification", oParameters.GetParameters("QualificationGroupName"));
	   	
	   	oExcelData.setExcelData("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 6, 16, oParameters.GetParameters("QualificationGroupName"));
	   	
	   	saveAsQGScenario("Stop Loss Qualification");
	   	
	   	editQGName("Stop Loss Qualification");
	   	
	   	searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
	   	
	   	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);

	   	
	   	
	  //To read data from RateSheets_TestData excel 
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","RateSheet_VR","Stop_Loss_Formula_Expression_RS");
    	
    	oRateSheetLibrary.addRateSheetIcon();
		oRateSheetLibrary.AddRateSheetDetails("");
		oRateSheetLibrary.rateSheetSaveButton("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetStopLossSection", "QG_Section");
		oRateSheetLibrary.clickStopLossTab();		
		oRateSheetLibrary.addSectionIcon(); 		
		oRateSheetLibrary.StopLossSection("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", "STOPLOSS_FORMULA");
		oRateSheetLibrary.addTermButton("",oParameters.GetParameters("RateSheetName"),oParameters.GetParameters("SectionName"));
		oRateSheetLibrary.selectRateType("");
		oRateSheetLibrary.termSaveButton("");
				
	   	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
	   	//On QG page Usage tabs validation
    	usageTabValidation("Term Usage", oParameters.GetParameters("RateSheetName"));
    	   	
    	//Term Exclusion Export Scenario
    	ExportButton("Term Usage", "");
    	
    	//Navigate to Rate Sheet Plugin
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);
    	
    	//To Delete Terms in a ratesheet
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "Stop_Loss_Formula_Expression_RS");
    	oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"),oParameters.GetParameters("RateSheetName"));
    
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);

	   	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("EditedQualificationGroup"));
    	
    	//To Delete QG
    	deleteQualificationGrouptocheckerrormessage(oParameters.GetParameters("QualificationGroupName"));
	
    	//To logout of portal
    	logout();
	}

   


	
	public void StopLoss_Qualification() 
	{
		login("EDIT");
    	changePricingEngine(); 
    	xpathChange();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","STOPLOSS_QUALIFICATION");
    	
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	selectQGType("Stop Loss Qualification");
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	
    	addQGDetails("STOPLOSS_QUALIFICATION_QG");
    	stopLossQualification_invalid_expression_validation();
    	cancelScenario("Add StopLoss Qualification window cancel button", "Add StopLoss Code QG Popup cancel Button", "Add StopLoss Code QG popup Discard button", addQGCancelButton, popupCancelButton, popupDiscardButton);
    	
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
	   	addQGDetails("STOPLOSS_QUALIFICATION_QG");
	   	stopLossQualification_valid_expression_validation();
	   	clickSaveButton("Add QG window Save Button", "Add Qualification Group Window", addQGSaveButton, addQualificationGroupWindowTitle);
	
	   	addQGValidation("StopLoss Qualification", oParameters.GetParameters("QualificationGroupName"));
	   	
	   	oExcelData.setExcelData("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 7, 9, oParameters.GetParameters("QualificationGroupName"));
	   	
	   	saveAsQGScenario("Stop Loss Qualification");
	   	
	   	editQGName("Stop Loss Qualification");
	   	
	   	searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
	   	
	   	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);

	   	//To read data from RateSheets_TestData excel 
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","RateSheet_VR","Stop_Loss_Qualification_RS");
    	
    	oRateSheetLibrary.addRateSheetIcon();
		oRateSheetLibrary.AddRateSheetDetails("");
		oRateSheetLibrary.rateSheetSaveButton("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheetStopLossSection", "QG_Section");
		

		oRateSheetLibrary.clickStopLossTab();
		
		oRateSheetLibrary.StopLossSection("");
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", "STOPLOSS_QUALIFICATION");
		oRateSheetLibrary.addTermButton("",oParameters.GetParameters("RateSheetName"),oParameters.GetParameters("SectionName"));
		oRateSheetLibrary.selectRateType("");
		oRateSheetLibrary.termSaveButton("");
		
		navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);

    	//On QG page Usage tabs validation
    	usageTabValidation("Stop Loss Usage", oParameters.GetParameters("RateSheetCode"));
    	   	
    	//Navigate to Rate Sheet Plugin
    	navigate_to("Rate Sheets Plugin", "Rate Sheet Search box", oRateSheetLibrary.rateSheetsPlugin, oRateSheetLibrary.rateSheetSearch);
    	
    	//To Delete a ratesheet
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx", "RateSheet_VR", "Stop_Loss_Qualification_RS");
    	oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"),oParameters.GetParameters("RateSheetName"));
    
    	//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);

	 	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("EditedQualificationGroup"));
    	
    	//To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
	
    	//To logout of portal
    	logout();
	}
	
	
	public void Service_Qualification() 
	{
		login("EDIT");
    	changePricingEngine(); 
    	xpathChange();
    	
    	oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "RateSheets_TestData.xlsx","QG_TestData","SERVICE_QUALIFICATION");
    	
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	selectQGType("Service Qualification");
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
    	
    	addQGDetails("SERVICE_QUALIFICATION_QG");
    	serviceQualification_invalid_expression_validation();
    	cancelScenario("Add Service Qualification window cancel button", "Add StopLoss Code QG Popup cancel Button", "Add StopLoss Code QG popup Discard button", addQGCancelButton, popupCancelButton, popupDiscardButton);
    	
    	clickAddButton("Add Qualification Group Button", "Add Qualification Group Window Title", addQualificationGroupButton, addQualificationGroupWindowTitle);
	   	addQGDetails("SERVICE_QUALIFICATION_QG");
	   	serviceQualification_valid_expression_validation();
	   	clickSaveButton("Add QG window Save Button", "Add Qualification Group Window", addQGSaveButton, addQualificationGroupWindowTitle);
	   
	   	addQGValidation("Service Qualification", oParameters.GetParameters("QualificationGroupName"));
	   	
	   	oExcelData.setExcelData("C:\\ccm\\SupportingFiles\\", "RateSheets_TestData.xlsx", "QG_TestData", 8, 9, oParameters.GetParameters("QualificationGroupName"));
	   	
	   	saveAsQGScenario("Service Qualification");
	   	
	   	editQGName("Service Qualification");
	   	
	   	searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
	   	
	    //To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("EditedQualificationGroup"));
	   	
    	//Navigate to PPS plugi
	   	navigate_to("PPS Plugin", "Select PPS Group type to begin.", oPPSLibrary.PPSplugin, oPPSLibrary.PluginPage);
	    //Method to select type of PPS 
	   	oPPSLibrary.selectGroupType("PPS Dialysis",oPPSLibrary.SelectGroupDropDown,oPPSLibrary.OpenPageValidation);
	    //Method navigates to Rate Factor tab
	   	navigate_to("Rate Factors", " Nothing is selected.", oPPSLibrary.rateFactorsTab, oPPSLibrary.OpenPageValidation);
	   	
	   	//To create Rate factor
		oPPSLibrary.addRateFactor(); 
		oPPSLibrary.addPeriodWindow(oPPSLibrary.addRateFactorPeriodWindow); 
		oPPSLibrary.addRateFactorPeriod(10); 
		oPPSLibrary.enterCommonFactorValues(); 
		oPPSLibrary.saveRateFactor(oParameters.GetParameters("QualificationGroupName"));
		
		//Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
    	searchQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	//Usage tab validation
		usageTabValidation("Dialysis Usage", oParameters.GetParameters("RateFactorSetName"));
		
		//Navigate to PPS plugi
	   	navigate("PPS Plugin", oPPSLibrary.PPSplugin);
	   	// to delete Rate factor
	   	oPPSLibrary.deletePPS("Search Rate Factors",oParameters.GetParameters("RateFactorSetName"),oPPSLibrary.rateFactorDeleteIcon);
		
	    //Navigate to Qualificatio Group Plugin
    	navigate_to("Qualification Groups", "Qualification Group Type DropDown", qualificationGroupLink, qualificationGroupTypeDD);
    	
	    //To Delete QG
    	deleteQualificationGroup(oParameters.GetParameters("QualificationGroupName"));
    	
    	//To logout of portal
    	logout();
	}
	
	
	// QG View Only VR code
	
	
	By addQGLink = By.xpath("//div[@class='msg-blurb'][contains(.,'Add a Qualification Group')]");
	By addQGIcon = By.xpath("//div[@id='qualificationgroupView']//i[@class='left fa fa-plus-square']");
	By QGFirstline = By.xpath("//*[@id='qualificationgroupView']//div[@class='first-line ng-scope ng-binding']");
	By termsList = By.xpath("//ul[@class='data-list drillable-list scroll-y-auto']//li[1]");
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
		
	By ViewSaveButtonxpath=By.xpath("//*[@id='fullFooter']//button[@type='button'][1]");
	
	//Method for checking Save button in View Mode
	public void QGSaveAsButton_view() 
	{	
		if(!IsDisplayed("Add Document Icon", ViewSaveButtonxpath))
			oReport.AddStepResult("QG SaveAs button", "SaveAs button in qualification group is not present in view mode", "PASS");
		else
			oReport.AddStepResult("QG SaveAs button", "SaveAs button in qualification group is present in view mode", "FAIL");
	}
	
	By FirstElement = By.xpath("//ul[contains(@class,'data-list drillable-list scroll-y-auto')]/li[1]");
	
	By DeleteIconXpath = By.xpath("//ul[contains(@class,'data-list drillable-list scroll-y-auto')]/li[1]//a/i[@class='left fa fa-minus-square']");

	
	public void DeleteQG_view()
	{
		mouse_hover("FirstElement", FirstElement);
		if (!IsDisplayed("DeleteIcon", DeleteIconXpath))
			//hover.moveToElement(DeleteIconXpath);mouse_hover("DeleteIcon", DeleteIconXpath);	
			oReport.AddStepResult("Delete Icon", "Hover over Qualification Group and verified that delete icon is not displayed", "PASS");
		else
			oReport.AddStepResult("Delete Icon", "Hover over Qualification Group and verified that delete icon is displayed", "FAIL");
	}
	
	By ExportButtonXpath=By.xpath("//*[@id='qualificationgroupView']//a[@title='Export']");
	By recordsInPortal = By.xpath("//div[@class='pull-right -align-right helper-text ng-binding']");
    //Method for export validation
	public void ExportButton(String Tab1, String Tab2)
	{
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
    
	By UsageTableName=By.xpath("//*[@id='qualificationgroupView']//h3[@class='ng-binding']");

	By TermUsageTabXpath=By.xpath("//*[@id='qualificationgroupView']//a[text()='Term Usage']");
	
	By RateSheetCode=By.xpath("//*[@id='ratesheetSection']//div[@class='pull-right xl-header pad-r-5 ng-binding']");
	
	By SectionUsageTabXpath=By.xpath("//*[@id='qualificationgroupView']//a[text()='Section Usage']");

	By SectionRateSheetCode=By.xpath("//*[@id='qualificationgroupView']//tr[1]/td[1]");
	
	By SectionRateSheetCodeLink = By.xpath("//*[@id='qualificationgroupView']//tr[1]/td[1]/a[@class='link ng-binding']");

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
    
	public void QG_ViewOnlyPermission()
	{
		 login("VIEW");
	     changePricingEngine();
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
	
}

package libraries;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class CCMLibrary extends CommonPages 
{
	public CCMLibrary(WebDriver driver, Report oReport, Parameters oParameters) 
	{
		super(driver, oReport, oParameters);
	}

	By userNameTextBox = By.xpath(".//*[@id='authUsername']");

	By passwordTextBox = By.xpath(".//*[@id='authPassword']");

	By loginButton = By.xpath(".//*[@id='login']");

	By contractAddButton = By.xpath("//div/a[@title='Add Contract']");

	By nothingIsSelected = By.xpath("//*[@id='contractView']//div[@ng-if='options.firstLine'][contains(.,'Nothing is selected.')]");

	
	//This method is used to login to CCM Portal
	public void login(String login_Type) 
	{
		if(!IsDisplayed("Login Page", userNameTextBox))
		{
			oReport.AddStepResult("Portal Login Page", oParameters.GetParameters("ENVIRONMENT") + " Environment is down, login page not displayed", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			return;
		}
		
		//To set EXECUTION_DATE parameter 
		oParameters.SetParameters("EXECUTION_DATE", get_specified_date(0, "YYYY-MM-dd HH:MM:SS"));
		//To set EXECUTION_ID parameter
		oParameters.SetParameters("EXECUTION_ID", Long.toString(System.currentTimeMillis()));
		//To set LOGIN_TYPE parameter
		oParameters.SetParameters("LOGIN_TYPE", login_Type);

		//To set UserName and password parameters for login purpose
		if (oParameters.GetParameters("LOGIN_TYPE").equalsIgnoreCase("EDIT")) 
		{
			oParameters.SetParameters("USER_NAME", oParameters.GetParameters("EDIT_USER_NAME"));
			oParameters.SetParameters("PASSWORD", oParameters.GetParameters("EDIT_PASSWORD"));
		} 
		else if (oParameters.GetParameters("LOGIN_TYPE").equalsIgnoreCase("VIEW")) 
		{
			oParameters.SetParameters("USER_NAME", oParameters.GetParameters("VIEWONLY_USER_NAME"));
			oParameters.SetParameters("PASSWORD", oParameters.GetParameters("VIEWONLY_PASSWORD"));
		}
		else 
		{
			oParameters.SetParameters("USER_NAME", oParameters.GetParameters("CUSTOM_USER_NAME"));
			oParameters.SetParameters("PASSWORD", oParameters.GetParameters("CUSTOM_PASSWORD"));
		}

		//To enter username in username textbox
		enter_text_value("UserName", userNameTextBox, oParameters.GetParameters("USER_NAME"));
		//To enter password in password textbox
		enter_text_value("Password", passwordTextBox, oParameters.GetParameters("PASSWORD"));
		//To click on login button
		click_button("Login Button", loginButton);
		
		
		//To check if login is successfull or not
		if (IsFieldDisplayed("Nothing is Selected String", nothingIsSelected)) 
		{
			waitFor(nothingIsSelected, "Nothing is Selected String");
			oReport.AddStepResult("Login","Login to " + oParameters.GetParameters("ENVIRONMENT") + " Portal Successfull", "PASS");
			//To set CONTINUED_EXECUTION parameter value to YES
			oParameters.SetParameters("CONTINUED_EXECUTION", "YES");
		}
		else 
		{
			oReport.AddStepResult("Login","Login to " + oParameters.GetParameters("ENVIRONMENT") + " Portal UnSuccessfull", "FAIL");
			//To set CONTINUED_EXECUTION parameter value to NO
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}

		//If CODE_VERSION parameter is set to YES it navigate to info page and adds screenshot to report
		if (oParameters.GetParameters("CODE_VERSION").equalsIgnoreCase("YES")) 
		{
			String info = oParameters.GetParameters("URL").replace("login.jsp", "info");
			
			driver.navigate().to(info);
		
			System.out.println("Checking Code Version...");
			
			oReport.AddStepResult("", oParameters.GetParameters("ENVIRONMENT") + " Code Version", "SCREENSHOT");
			
			driver.navigate().back();
		}
	}

	By PriceEngineXpath = By.xpath("//*[@id='portal-top-header']/div[2]/ul/li[2]/select");

	By PPSTabXpath = By.xpath("//*[@id='nav']/ul/li[6]/a");

	//This method is used to change pricing engine 
	public void changePricingEngine() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//To check if Pricing Engine drop down is displayed and if yes change it to respective pricing engine 
		if(IsElementDisplayed("Pricing Engine DropDown", PriceEngineXpath))
		{
			selectOption(PriceEngineXpath, "visibletext", oParameters.GetParameters("TENANT"));
			
			By changedPricingEngineXpath = By.xpath("//*[@id='portal-top-header']/div[2]/ul/li[2]/select/option[contains(.,'" + oParameters.GetParameters("TENANT") + "')]");
			waitFor(changedPricingEngineXpath, oParameters.GetParameters("TENANT") + " Tenant");
			
			highLighterMethod(changedPricingEngineXpath);
			
			oReport.AddStepResult("Pricing Engine", "Changed Pricing Engine to " + oParameters.GetParameters("TENANT"), "SCREENSHOT");
		}
		else
			System.out.println("Pricing Engine dropdown is not displayed");
		
		//To check if it has changed to respective pricing engine
		if (IsElementDisplayed("Add term button is displayed in PPS Plugin", PPSTabXpath))
			System.out.println("It is in " + oParameters.GetParameters("ENVIRONMENT") + " Environment and "	+ oParameters.GetParameters("TENANT") + " Tenant");
		else
			System.out.println("It is not in Soarian Pricing mode");
		
	}

	By logout = By.xpath("//span[contains(text(),'Logout')]");
	
	//This method is used to log out of CCM portal
	public void logout() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//To check if Logout button is displayed/ enable to perform click operation
		if(IsElementDisplayed("Logout Button", logout) && IsElementEnabled("Logout Button", logout))
		{
			//To click on Logout Button
			click_button("Logout Button", logout);
			
			oReport.AddStepResult("Logout Window Screenshot", "Logged out of Portal", "SCREENSHOT");
			/*//To check if login button is diplayed /enabled
			if (IsElementDisplayed("Login Button", loginButton))
				oReport.AddStepResult("Logout", "Clicked on logout button and verified that it has successfully logged out of portal", "PASS");
			else
				oReport.AddStepResult("Logout", "Clicked on logout button, but it has failed to log out of portal", "FAIL");*/
		}
		else
			oReport.AddStepResult("Click on logout Button", "Logout button is not displayed/enabled, hence failed to click on it", "FAIL");
	}

	//This method is used to navigate to any plugin/suit in CCM portal
	public void navigate_to(String pluginName, String afterNavigateElementName, By pluginNameElement, By navigateElement) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(navigateElement, afterNavigateElementName);
		
		//To check if clickElement button is displayed/ enable to perform click operation
		if(!IsElementDisplayed(pluginName, pluginNameElement) && !IsElementEnabled(pluginName, pluginNameElement))
		{
			oReport.AddStepResult(pluginName, pluginName + " is not Displayed/Enabled", "PASS");
			return;
		}
		else
		{
			//To click on clickableElement
			click_button(pluginName, pluginNameElement);
			
			//To check if navigation is successfull or not 
			if(IsElementDisplayed(afterNavigateElementName, navigateElement))
				oReport.AddStepResult("Navigate to " + afterNavigateElementName, "Clicked on " + pluginName + " and verified that it has navigated to " + pluginName, "PASS");
			else				
				oReport.AddStepResult("Navigate to " + afterNavigateElementName, "Clicked on " + pluginName + ", but " + afterNavigateElementName + " element is not displayed, hence it has failed to navigate to " + pluginName + " plugin", "FATAL");
		}
	}
	
	
	public void navigate(String pluginName, By pluginNameElement) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//To check if clickElement button is displayed/ enable to perform click operation
		if(!IsElementDisplayed(pluginName, pluginNameElement) && !IsElementEnabled(pluginName, pluginNameElement))
		{
			oReport.AddStepResult(pluginName, pluginName + " is not Displayed/Enabled", "PASS");
			return;
		}
		else
		{
			//To click on clickableElement
			click_button(pluginName, pluginNameElement);
		}
	}
	
	//This method is used to perform cancel scenario
	public void cancelScenario(String cancel, String popupCancel, String popupDiscard, By cancelButton, By popupCancelButton, By popupDiscardButton)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//To check if cancel button is displayed and enabled 
		if(!IsElementDisplayed(cancel, cancelButton) && !IsElementEnabled(cancel, cancelButton))
		{
			oReport.AddStepResult(cancel, cancel + " is not Displayed/Enabled", "FAIL");
			return;
		}
		else				
		{
			//To click on cancel Button
			click_button(cancel, cancelButton);
			oReport.AddStepResult(cancel, "Clicked on " + cancel + " and Popup appeared", "PASS");
			
			//To check if cancel button on popup is displayed and enabled 
			if(!IsElementDisplayed(popupCancel, popupCancelButton) && !IsElementEnabled(popupCancel, popupCancelButton))
				oReport.AddStepResult(popupCancel, popupCancel + " on popup is not Displayed/Enabled", "FAIL");
			else				
			{
				//To click on Popup cancle Button
				click_button(popupCancel, popupCancelButton);
				oReport.AddStepResult(popupCancel, "Clicked on " + popupCancel + " and popup disappeared", "PASS");
			}
		}
			
		if(!IsElementDisplayed(cancel, cancelButton) && !IsElementEnabled(cancel, cancelButton))
		{
			oReport.AddStepResult(cancel, cancel + " is not Displayed/Enabled", "FAIL");
			return;
		}
		else				
		{
			//To click on cancel Button
			click_button(cancel, cancelButton);
			oReport.AddStepResult(cancel, "Clicked on " + cancel + " and Popup appeared", "PASS");
			
			//To check if discard button on popup is displayed and enabled 
			if(!IsElementDisplayed(popupDiscard, popupDiscardButton) && !IsElementEnabled(popupDiscard, popupDiscardButton))
			{
				oReport.AddStepResult(popupDiscard, popupDiscard + " on popup is not Displayed/Enabled", "FAIL");
				return;
			}
			else				
			{
				//To click on Popup discard Button
				click_button(popupDiscard, popupDiscardButton);
				oReport.AddStepResult(popupDiscard, "Clicked on " + popupDiscard + " and popup disappeared", "PASS");
			}
		}
	}
		
	//This method is used to click on any Add button
	public void clickAddButton(String addButton, String addWindow, By addButtonElement, By addWindowElement)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//to check if respective button is displayed/enabled to perform click operation
		if(!IsElementDisplayed(addButton, addButtonElement) && !IsElementEnabled(addButton, addButtonElement))
		{
			//Adding step result to report
			oReport.AddStepResult(addButton, addButton + " not Displayed/Enabled", "FATAL");
			return;
		}
		else
		{
			//To click on addButton element
			click_button(addButton, addButtonElement);
			
			//To check if add window is displayed upon clicking on add button element
			if(IsElementDisplayed(addWindow, addWindowElement))
				oReport.AddStepResult(addWindow, "Clicked on " + addButton + " and verified that " + addWindow + " is displayed", "PASS");
			else
				oReport.AddStepResult(addWindow, "Clicked on " + addButton + " and verified that " + addWindow + " is not displayed", "FAIL");
		}
	}
	
	//Method for selecting the value from the drop down
	public void selectDropDownValue(String buttonName, String valueToSelect, String validatingElement, By dropDownButton, By dropDownValue, By validationElement)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}			
		//To check if respective button is displayed and enabled to perform click operation
		if(!IsElementDisplayed(buttonName, dropDownButton) && !IsElementEnabled(buttonName, dropDownButton))
		{
			//Adding step result to report
			oReport.AddStepResult(buttonName, buttonName + " not Displayed/Enabled", "FATAL");
			return;
		}
		else
		{
			//To click on Drop Down button
			click_button(buttonName, dropDownButton);		
			// To click on selected drop down value
			click_button(valueToSelect, dropDownValue);		
			//To check if particular page is displayed or not
			if(IsElementDisplayed(validatingElement, validationElement))
				oReport.AddStepResult(valueToSelect, "Clicked on " + valueToSelect + " and verified that " + validatingElement + " is displayed", "PASS");
			else
				oReport.AddStepResult(valueToSelect, "Clicked on " + valueToSelect + " and verified that " + validatingElement + " is not displayed", "FAIL");
		}		
	}

	By redBoxError = By.xpath("//ul[@class='error-items']");	
	
	By redBoxErrorCloseIcon = By.xpath("//span[@ng-show='canShowCloseBox']");

	//This method is to click on save button on any page
	public void clickSaveButton(String saveButton, String addWindow, By saveButtonElement, By addWindowElement)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//to check if respective button is displayed and enabled to perform click operation
		if(!IsElementDisplayed(saveButton, saveButtonElement) && !IsElementEnabled(saveButton, saveButtonElement))
		{
			//Adding step result to report
			oReport.AddStepResult(saveButton, saveButton + " not Displayed/Enabled", "FATAL");
			return;
		}
		else
		{
			//To click on SAve Button
			click_button(saveButton, saveButtonElement);
			
			if(!IsDisplayed("RedBoxError", redBoxError))
				System.out.println("Red box error not found");
			else
			{
				oReport.AddStepResult("Red Box Screen", "Red Box Notification", "SCREENSHOT");
				click_button("RedBoxCloseButton", redBoxErrorCloseIcon);
				oReport.AddStepResult("Red Box Error CLosed", "Upon clicking on " + saveButton + "red box appeared, hence closed redbox", "FAIL");
				return;
			}
			
			//To check if add window is closed upon clicking on save button element
			if(waitForIsInvisible(addWindowElement, addWindow))
				oReport.AddStepResult(addWindow, "Clicked on " + saveButton + " and verified that " + addWindow + " is closed", "PASS");
			else
			{
				oReport.AddStepResult(addWindow, "Clicked on " + saveButton + " and verified that " + addWindow + " is not closed", "FAIL");
				//To set CONTINUED_EXECUTION parameter value to YES
				oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			}
		}
	}

	//This method is used to select the existing set by checking check box
	public void copyFromExisting(String checkBoxButton, String searchExistingSet, String selectPeriod, By checkBoxElement, By searchElement, By selectPeriodElement, By selectUserRateSet, By selectFirstElement, String valueToEnter)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
			
		//To check if respective button is displayed to perform click operation
		if(!IsElementDisplayed(checkBoxButton, checkBoxElement))
		{
			//Adding step result to report
			oReport.AddStepResult(checkBoxButton, checkBoxButton + " not Displayed/Enabled", "FATAL");
			return;
		}
		else
		{
			//Click on checkbox to copy from existing set
			click_button(checkBoxButton, checkBoxElement);
				
			// Search and select existing set 
			if(!IsElementDisplayed(searchExistingSet, searchElement) && !IsElementEnabled(searchExistingSet, searchElement))
			{
				//Adding step result to report
				oReport.AddStepResult(checkBoxButton, checkBoxButton + " not Displayed/Enabled", "FATAL");
				return;
			}	
			else
				enter_text_value(searchExistingSet, searchElement, valueToEnter);
			
			if(IsDisplayed("Select User Rate Set", selectUserRateSet))
				click_button("Select first element", selectFirstElement);
			
			fixed_wait_time(2);
			
			if(IsDisplayed("Select the Period", selectPeriodElement))
				select_option(selectPeriod, selectPeriodElement, "0");
			else
				oReport.AddStepResult("Period selected", "Clicked on copy existing checkbox, searched and selected existing "+ valueToEnter + " "+"but Period fiels is not displayed ","WARNING");
		}
	}
			
	
	//Method for getting text of selected drop down value
	public String getSelectecText(String FieldName, By elemDesc, String oValue)
	{		
		WebElement oSelectElement = null;
		Select oSelect = null;
		oSelectElement = driver.findElement(elemDesc);
		oSelect = new Select(oSelectElement);
		String rVal = oSelect.getFirstSelectedOption().getText();
		oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + rVal, "DONE");
		return rVal.trim();
	}
	
	
	By contractManagement = By.xpath("//div[@id='portal-main-nav']//a[contains(.,'Contract Management')]");

	By analytics = By.xpath("//div[@id='portal-main-nav']//a[contains(.,'Analytics')]");

	By configuration = By.xpath("//div[@id='portal-main-nav']//a[contains(.,'Configuration')]");

	By contracts = By.xpath("//div[@id='nav']//a[contains(text(),'Contracts')]");

	By rateSheets = By.xpath("//div[@id='nav']//a[contains(text(),'Rate Sheets')]");

	By qualificationGroups = By.xpath("//div[@id='nav']//a[contains(text(),'Qualification Groups')]");

	By schedules = By.xpath("//div[@id='nav']//a[contains(text(),'Schedules')]");

	By codes = By.xpath("//div[@id='nav']//a[contains(text(),'Codes')]");

	By PPS = By.xpath("//div[@id='nav']//a[contains(text(),'PPS')]");

	By repricing = By.xpath("//div[@id='nav']//a[contains(text(),'Repricing')]");

	By models = By.xpath("//div[@id='nav']//a[contains(text(),'Models')]");

	By testPrice = By.xpath("//div[@id='nav']//a[contains(text(),'Test Price')]");

	By reports = By.xpath("//div[@id='nav']//a[contains(text(),'Reports')]");

	By dashBoard = By.xpath("//div[@id='nav']//a[contains(text(),'Dashboard')]");

	By comparativeAnalysis = By.xpath("//div[@id='nav']//a[contains(text(),'Comparative')]");

	By userAuthorization = By.xpath("//div[@id='nav']//a[contains(text(),'User Authorization')]");

	By providerGroupings = By.xpath("//div[@id='nav']//a[contains(text(),'Provider Groupings')]");

	By userGroups = By.xpath("//div[@id='nav']//a[contains(text(),'User Groups')]");

	By codeMaintenance = By.xpath("//a[text()='Code Maintenance']");

	// Method to switch between different suits
	public void navigate_to_suit(String suitName) 
	{
		switch(suitName) 
		{
			case "CONTRACT_MANAGEMENT":
				click_button(suitName, contractManagement);
				break;
			case "ANALYTICS":
				click_button(suitName, analytics);
				break;
			case "CONFIGURATION":
				click_button(suitName, configuration);
				break;
		}
	}

	// Method to switch between different Plugins
	public void navigate_to_plugin(String pluginName) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
				
		switch(pluginName) 
		{
			case "CONTRACTS":
				click_button(pluginName, contracts);
				break;
	
			case "RATE_SHEETS":
				click_button(pluginName, rateSheets);
				break;
	
			case "QUALIFICATION_GROUPS":
				click_button(pluginName, qualificationGroups);
				break;
	
			case "SCHEDULES":
				click_button(pluginName, schedules);
				break;
	
			case "CODES":
				click_button(pluginName, codes);
				break;
	
			case "REPRICING":
				click_button(pluginName, repricing);
				break;
	
			case "MODELS":
				click_button(pluginName, models);
				break;
	
			case "TESTPRICE":
				click_button(pluginName, testPrice);
				break;
	
			case "PPS":
				click_button(pluginName, PPS);
				break;
	
			case "REPORTS":
				click_button(pluginName, reports);
				break;
	
			case "DASHBOARD":
				click_button(pluginName, dashBoard);
				break;
	
			case "COMPARATIVE_ANALYSIS":
				click_button(pluginName, comparativeAnalysis);
				break;
	
			case "USER_AUTHORIZATION":
				click_button(pluginName, userAuthorization);
				break;
	
			case "PROVIDER_GROUPINGS":
				click_button(pluginName, providerGroupings);
				break;
	
			case "USER_GROUPS":
				click_button(pluginName, userGroups);
				break;
	
			case "CODE_MAINTENANCE":
				click_button(pluginName, codeMaintenance);
				break;
		}
	}

	public int selectOptionByIndex(By elemDesc, String FieldName, int selectInt) 
	{
		WebElement oWebElement = driver.findElement(elemDesc);
		Select oSelect = new Select(oWebElement);
		oSelect.selectByIndex(selectInt);
		return selectInt;
	}

	public int getOptions(String FieldName, By elemDesc) 
	{
		int dropDownSize = 0;

		if (IsElementDisplayed(FieldName, elemDesc)) 
		{
			System.out.println("IsElementDisplayed : FieldName : " + FieldName + ":True");

			WebElement oWebElement = driver.findElement(elemDesc);
			Select oSelect = new Select(oWebElement);
			dropDownSize = oSelect.getOptions().size();
		}
		else
			System.out.println("IsElementDisplayed : FieldName : " + FieldName + ":False");

		return dropDownSize;
	}
	
	
	public void highLighterMethod(By elementDesc)
	{
		WebElement oWebElement = driver.findElement(elementDesc);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", oWebElement);
		oReport.AddStepResult("", "", "SCREENSHOT");
	}
	
	By rateSheetFacility = By.xpath("//form[@id='addEditRatesheetForm']//input[@id='facilitySearch']");

	By allCodesFacility = By.xpath("//form[@id='addEditCodeSetForm']//input[@id='facilitySearch']");
/*
	By PPS_DRG_UserRatesFacility = By.xpath("//form[@id='addUserRateSet']//input[@id='facilitySearch']");

	By PPS_DRG_Grouper_OptionsFacility = By.xpath("//form[@id='addDrgGrouperOptionsSet']//input[@id='facilitySearch']");

	By PPS_DRG_Provider_ValuesFacility = By.xpath("//form[@id='addProviderValuesSet']//input[@id='facilitySearch']");

	By PPS_DRG_Grouping_DefinitionFacility = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='facilitySearch']");

	//By PPS_CMG_User_RatesFacility = By.xpath("//form[@id='addCmgUserRateSet']//input[@id='facilitySearch']");

	//By PPS_CMG_Provider_ValuesFacility = By.xpath("//form[@id='addcmgProviderValuesSet']//input[@id='facilitySearch']");
*/
	By PPS_Facility = By.xpath("//div[@class='workflow  modal-medium']//input[@id='facilitySearch']");
	
	By contractsFacility = By.xpath("//form[@id='associatedRateSheetFrm']//input[@id='facilitySearch']");
	
	By TestPriceFacility = By.xpath("//form[@id='TestPriceTemplateSaveConfirmDialog']//input[@id='facility']");
			
	/*By PPS_RUG_User_RatesFacility = By.xpath("//form[@id='addRugUserRateSet']//input[@id='facilitySearch']");

	By PPS_APC_APG_Provider_ValuesFacility = By.xpath("//form[@id='addcmgProviderValuesSet']//input[@id='facilitySearch']");

	By PPS_APC_APG_Grouper_OptionsFacility = By.xpath("//form[@id='addApcGrouperOptions']//input[@id='facilitySearch']");

	By PPS_APC_APG_Grouping_DefinitionsFacility = By.xpath("//form[@id='addGroupingDefinitionSet']//input[@id='facilitySearch']");

	By PPS_Dialysis_Comorbidity_CategoryFacility = By.xpath("//form[@id='addComorbidityCategorySet']//input[@id='facilitySearch']");

	By PPS_Dialysis_Rate_FactorsFacility = By.xpath("//form[@id='addRateFactorSet']//input[@id='facilitySearch']");

	By Fee_SchedulesFacility = By.xpath("//workflow-modal[@id='addScheduleModal']//input[@id='facilitySearch']");

	By Modifier_ScheduleFacility = By.xpath("//form[@id='addEditModifierDiscount']//input[@id='facilitySearch']");

	By Group_Code_Rates_SchedulesFacility = By.xpath("//form[@id='addGroupCodeRateModal']//input[@id='facilitySearch']");

	By Related_Procedure_ScheduleFacility = By.xpath("//form[@id='addReatedScheduleForm']//input[@id='facilitySearch']");
*/
	
	public void facilityName(String pageName, String facilityName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
		
		switch(pageName)
		{
			case	"RATESHEET":
					enterIfDisplayed(pageName, rateSheetFacility, facilityName);
					break;
					
			case	"CODES":
					enterIfDisplayed("Facility Name", allCodesFacility, facilityName);
					break;
			
			case	"PPS":
					enterIfDisplayed("Facility Name", PPS_Facility, facilityName);
					break;
				
			case	"CONTRACTS":
					enterIfDisplayed("Facility Name", contractsFacility, facilityName);
					break;
			
			case	"SCHEDULES":
					enterIfDisplayed("Facility Name", PPS_Facility, facilityName);
					break;	
			
			case    "TESTPRICE":
					enterIfDisplayed("Facility Name", TestPriceFacility, facilityName);
					
					
			/*case	"PPS_DRG_GROUPER_OPTIONS":
					enter_text_value("Facility Name", PPS_DRG_Grouper_OptionsFacility, facilityName);
					break;
					
			case	"PPS_DRG_PROVIDER_VALUES":
					enter_text_value("Facility Name", PPS_DRG_Provider_ValuesFacility, facilityName);
					break;
					
			case	"PPS_DRG_GROUPING_DEFINITIONS":
					enter_text_value("Facility Name", PPS_DRG_Grouping_DefinitionFacility, facilityName);
					break;
					
			case	"PPS_CMG_USER_RATES":
					enter_text_value("Facility Name", PPS_CMG_User_RatesFacility, facilityName);
					break;
				
			case	"PPS_CMG_PROVIDER_VALUES":
					enter_text_value("Facility Name", PPS_CMG_Provider_ValuesFacility, facilityName);
					break;
				
			case	"PPS_RUG_USER_RATES":
					enter_text_value("Facility Name", PPS_RUG_User_RatesFacility, facilityName);
					break;
				
			case	"PPS_APC_APG_PROVIDER_VALUES":
					enter_text_value("Facility Name", PPS_APC_APG_Provider_ValuesFacility, facilityName);
					break;
				
			case	"PPS_APC_APG_PROVIDER_OPTIONS":
					enter_text_value("Facility Name", PPS_APC_APG_Grouper_OptionsFacility, facilityName);
					break;
					
			case	"PPS_APC_APG_GROUPING_DEFINITIONS":
					enter_text_value("Facility Name", PPS_APC_APG_Grouping_DefinitionsFacility, facilityName);
					break;
				
			case	"PPS_DIALYSIS_COMORBIDITY_CATEGORY":
					enter_text_value("Facility Name", PPS_Dialysis_Comorbidity_CategoryFacility, facilityName);
					break;
					
			case	"PPS_DIALYSIS_RATE_FACTOR":
					enter_text_value("Facility Name", PPS_Dialysis_Rate_FactorsFacility, facilityName);
					break;
				
			case	"FEE_SCHEDULES":
					enter_text_value("Facility Name", Fee_SchedulesFacility, facilityName);
					break;
				
			case	"MODIFIER_SCHEDULE":
					enter_text_value("Facility Name", Modifier_ScheduleFacility, facilityName);
					break;
			
			case	"GROUP_CODE_RATES_SCHEDULE":
					enter_text_value("Facility Name", Group_Code_Rates_SchedulesFacility, facilityName);
					break;
		
			case	"RELATED_PROCEDURE_SCHEDULE":
					enter_text_value("Facility Name", Related_Procedure_ScheduleFacility, facilityName);
					break;*/
		}
	}
	
	
	public void enterIfDisplayed(String fieldName, By elementDesc, String facilityName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
				
		if(IsDisplayed(fieldName, elementDesc))
		{
			enter_text_value(fieldName, elementDesc, facilityName);
			
			By firstFacility = By.xpath("//a[.//text()='" +facilityName+"'][1]");
			if(IsDisplayed("Facility DropDown", firstFacility))
				click_button("First Facility", firstFacility);
		}	
		else
			System.out.println("Facility field not displayed");
	}
	
	
	/*public void enterFacilityName(String facilityPageName, String facilityName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}	
		facilityName(facilityPageName, facilityName);
	}*/
}

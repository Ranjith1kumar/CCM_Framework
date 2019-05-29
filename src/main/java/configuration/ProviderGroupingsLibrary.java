package configuration;

import java.util.List;
import org.openqa.selenium.*;
import contractManagement.ContractLibrary;
import libraries.*;

/**
 * @author C16137
 *
 */

public class ProviderGroupingsLibrary extends CCMLibrary
{
	ContractLibrary oContractLibrary = new ContractLibrary(Browser.driver, Browser.oReport, oParameters);
	
	UserGroupsLibrary ouserGroups = new UserGroupsLibrary(Browser.driver, Browser.oReport, oParameters); 
	
	public ProviderGroupingsLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}

	By providerGroupings=By.xpath("//a[text()='Provider Groupings']");
	
	By providergroups=By.xpath("//div[@id='providerView']//div[contains(text(),'Groups')]");
	
	//This method is used to navigate to provider plugin.
	public void ProviderGroups()
	{	
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		navigate_to("Provider groupings", "groups", providerGroupings, providergroups);
	}
	
	By firstGroupProviderGroup=By.xpath("//div[@id='providerView']//li[1]//div[@class='col-lg-10 col-md-10 ng-binding']");
	
	By searchProvider=By.xpath("//div[@id='providerView']//div[@class='page-action-bar portal-header']//input[contains(@class,'search-text-box input-sm form-control')]");
	
	By providers=By.xpath("//div[@id='providerView']//td[@class='border-right']//ul[@class='data-list no-border']//li//div[1]");
	
	By listProviders=By.xpath("//div[@id='providerView']//td[@class='border-right']//div[@class='list-header ng-scope ng-binding']");
	
	By providerAddIcon = By.xpath("//div[@id='providerView']//span[text()='Add Group']/..");
	
	By NoProviders=By.xpath("//div[@id='providerView']//div[@class='col-lg-12 col-md-12 ng-binding']");
	
	By TotallistProvider = By.xpath("//div[@id='providerView']//li//div[@class='col-lg-10 col-md-10 ng-binding']");
	
	//This method is used to search provider group and click on provider group.
	public void searchAndSelectViewOnlyProviderGroups()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		 List<WebElement> Totalprovidergroup= convertToWebElements(TotallistProvider);
		 
		 for(int i=1;i<=Totalprovidergroup.size();i++)
		 {
			 By TotalProviderGroup = By.xpath("//div[@id='providerView']//li["+i+"]//div[@class='col-lg-10 col-md-10 ng-binding']");
			 click_button("provider Group", TotalProviderGroup);	
			 fixed_wait_time(2);
			 
			 if(IsDisplayed("Providers list", providers))
			 {
				 String[] Providers=get_field_value("Tablelist", listProviders).split("\\s");
				 String TotalProvider=Providers[0];
					
				 oParameters.SetParameters("NumberOfProviders", String.valueOf(get_table_row_count(providers)));
					
				 if(TotalProvider.equals(oParameters.GetParameters("NumberOfProviders")) || get_text(NoProviders).equalsIgnoreCase(oParameters.GetParameters("Noproviders")))
					 oReport.AddStepResult("Provider group"," Clicked on provider group and verified that list of provider added to the group is displayed", "PASS");
				 else
					 oReport.AddStepResult("Provider group"," Clicked on provider group and verified that list of provider added to the group is not displayed", "FAIL");
				 break;	 
			 }
		}
	}
	
	//This Method is used to validate Add icon.
	public void addIconProviderGroupsViewOnlyPermission()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Add icon", providerAddIcon))
			oReport.AddStepResult("Add Icon or Add user Group", " Verified that Add icon or Add User Group is displayed", "FAIL");
		else
			oReport.AddStepResult("Add Icon or Add user Group", " Verified that Add icon or Add User Group is not displayed", "PASS");
	}

	By firstProviders=By.xpath("//div[@id='providerView']//td[@class='border-right']//ul[@class='data-list no-border']//li[1]//div[1]");
	
	By addedProviders=By.xpath("//div[@id='providerView']//div[@class='large-header accent-min ng-binding']");
	
	By NoProviderExistsForThisGroup=By.xpath("//div[@id='providerView']//div[text()='No providers exist for this group.']");
	
	//This method is used click on particular provider group.
	public void providerGroup()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("First Provider", firstProviders)  || IsDisplayed("No provider exist for this group", NoProviderExistsForThisGroup))
		{
			if(IsDisplayed("First Provider", firstProviders))
			{
				oReport.AddStepResult("First Provider", "Clicked on particular provider group and verified that added provider is displayed in provider list coloumn ", "PASS");
				oParameters.SetParameters("firstProviderName", get_text(firstProviders));
				click_button("First provider group", firstProviders);
				
				if(oParameters.GetParameters("firstProviderName").equalsIgnoreCase(get_text(addedProviders)))
					oReport.AddStepResult("first provider group", " Clicked on first provider group and verified that added providers or facilities is displayed", "PASS");
				else
					oReport.AddStepResult("first provider group", " Clicked on first provider group and verified that added providers or facilities is not displayed", "FAIL");
			}
			else 
			{
				oReport.AddStepResult("First Provider", "Clicked on particular provider group and verified that 'NO Provider Exists For This Group Message' is displayed in provider list coloumn ", "PASS");
				oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			}	
		}
		else
			oReport.AddStepResult("First Provider", "Clicked on First Provider group and verified that either 'Added Provider' or 'No Provider Exists for this group messgae' is not displayed", "FAIL");
	}
	
	By addNewProviderIcon=By.xpath("//div[@id='providerView']//span[text()='Add New Provider']");
	
	//This method is used to validate Add New Provider Icon.
	public void addNewProviderIconViewOnly()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Add new Provider icon", addNewProviderIcon))
			oReport.AddStepResult("Add New Provider icon", "Verified that Add New Provider icon is displayed", "FAIL");
		else
			oReport.AddStepResult("Add New Provider icon", "Verified that Add New Provider icon is not displayed", "PASS");
	}
	
	By deleteIconProvider=By.xpath("//div[@id='providerView']//ul[@class='data-list no-border']//li[1]//i[1]");
	
	//This method is used to Hover over on providers and validating delete icon.
	public void hoverOverProviders()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("First Provider group", firstProviders);
		
		if(IsDisplayed("Delete icon", deleteIconProvider))
			oReport.AddStepResult("Hover over first provider group", " Hover over on first provider group and verified that delete icon is displayed", "FAIL");
		else
			oReport.AddStepResult("Hover over first provider group", "Hover over on first provider group and verified that delete icon is not displayed", "PASS");
	}
	
	//This method is used to Hover over on Test Group and validating delete icon and edit icon.
	public void hoverOverTestGroup()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("First group ", firstGroupProviderGroup);
		
		if(IsDisplayed("Edit Icon", deleteIconProvider))
			oReport.AddStepResult("Hover over first group", "Hover over on first group and verified that edit icon and delete icon is displayed", "FAIL");
		else
			oReport.AddStepResult("Hover over first group", "Hover over on first group and verified that edit icon and delete icon is not displayed", "PASS");
	}
	
	//This method is used to execute Providers Groups View Only Permission.
	public void providerGroupingsViewOnly()
	{
		login("VIEW");
		changePricingEngine();
		ouserGroups.navigateToConfigurationSuite();
		ProviderGroups();
		searchAndSelectViewOnlyProviderGroups();
		addIconProviderGroupsViewOnlyPermission();
		providerGroup();
		addNewProviderIconViewOnly();
		hoverOverProviders();
		hoverOverTestGroup();
		logout();
	}
	
	By searchCloseIcon=By.xpath("//div[@id='providerView']//div[@class='fa fa-times-circle close-icon']");
	
	//This Method is used to validate Add icon.
	public void addIconProviderGroupsValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search Users Group", searchProvider, "");
		performKeyBoardAction("ENTER");
		
		if(IsElementDisplayed("Add icon", providerAddIcon))
			oReport.AddStepResult("Add icon or Add User Group", "Verified that Add icon or Add User Group is displayed", "PASS");
		else
			oReport.AddStepResult("Add icon or Add User Group", "Verified that Add icon or Add User Group is not displayed", "FAIL");
	
		if(IsDisplayed("All Provider Group", allProviderGroups))
			addGroupName();
		else
		{
			addGroupName();
			addGroupName();
		}
	}
	
	By allProviderGroups=By.xpath("//div[@id='providerView']//li//div[@class='col-lg-10 col-md-10 ng-binding']");
	
	By providerGroupNameFeild=By.xpath("//div[@id='providerView']//div[@class='data-row list-action ng-scope']//input[@class='form-control ng-pristine ng-invalid ng-invalid-required']");
	
	//This Method is used to add Provider user group
	public void addGroupName()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("UserProviderGroupCount", String.valueOf(get_table_row_count(allProviderGroups)));
		dclick_button("Add Group",providerAddIcon);
		
		if(IsElementEnabled("User Group Feild", providerGroupNameFeild))
		{	
			oReport.AddStepResult("Add user Group", "Clicked on add user group and verified that user group text feild is enabled", "PASS");
			enter_text_value("User Group Name Feild", providerGroupNameFeild, oParameters.GetParameters("addNewUserGroup")+System.currentTimeMillis());
			performKeyBoardAction("ENTER"); 
			
			oParameters.SetParameters("NewProviderAdded", get_field_value("Adding Provider", providerGroupNameFeild));
			fixed_wait_time(3);
			
			List<WebElement> allProviderGroupslist = convertToWebElements(allProviderGroups);
			
			if(allProviderGroupslist.size() > Integer.parseInt(oParameters.GetParameters("UserProviderGroupCount")))
				oReport.AddStepResult("Add Provider group", "Added Provider Group name and verified that new provider group is added to the list", "PASS");
			else
				oReport.AddStepResult("Add Provider group", "Added Provider Group name and verified that new provider group is not added to the list", "FAIL");
		}	
		else
			oReport.AddStepResult("Add user Group", "Clicked on add user group and verified that user group text feild is not enabled", "FAIL");
	}
	
	By addProvider=By.xpath("//div[@id='providerView']//div[contains(text(),'Add Provider')]");
	
	By facilityDropDown=By.xpath("//div[@id='providerView']//span[text()='Facility Name']");
	
	By facilityName=By.xpath("//div[@id='providerView']//ul//a[contains(.,'Facility Name')]");
	
	By facilityNpi=By.xpath("//div[@id='providerView']//ul//a[contains(.,'Facility NPI')]");
	
	By providerName=By.xpath("//div[@id='providerView']//ul//a[contains(.,'Provider Name')]");
	
	By providerNpi=By.xpath("//div[@id='providerView']//ul//a[contains(.,'Provider NPI')]");
	
	//This method is used to click on add new provider icon.
	public void addNewProviderIcon()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		dclick_button("Add New Provider", addNewProviderIcon);
		
		if(IsElementDisplayed("Add new provider", addProvider))
		{
			oReport.AddStepResult("Add new provider icon", "Clicked on add new provider icon and verified that serach feild is displayed", "PASS");
			fixed_wait_time(2);
			
			/*dclick_button("Facility dropdown", facilityDropDown);
			fixed_wait_time(2);
			
			if(oParameters.GetParameters("Name").equalsIgnoreCase(get_text(facilityName)))
				oReport.AddStepResult("Facility Name", "Clicked on Facilty Name Drop Down  and verified that "+oParameters.GetParameters("Name")+" is present in the list", "PASS");
			else
				oReport.AddStepResult("Facility Name", "Clicked on Facilty Name Drop Down  and verified that "+oParameters.GetParameters("Name")+" is not present in the list", "FAIL");
			
			if(oParameters.GetParameters("NPI").equalsIgnoreCase(get_text(facilityNpi)))
				oReport.AddStepResult("Facility NPI", "Clicked on Facilty Name Drop Down  and verified that "+oParameters.GetParameters("NPI")+" is present in the list", "PASS");
			else
				oReport.AddStepResult("Facility NPI", "Clicked on Facilty Name Drop Down  and verified that "+oParameters.GetParameters("NPI")+" is not present in the list", "FAIL");
			
			if(oParameters.GetParameters("Provider").equalsIgnoreCase(get_text(providerName)))
				oReport.AddStepResult("Provider Name", "Clicked on Facilty Name Drop Down  and verified that "+oParameters.GetParameters("Provider")+" is present in the list", "PASS");	
			else
				oReport.AddStepResult("Provider Name", "Clicked on Facilty Name Drop Down  and verified that "+oParameters.GetParameters("Provider")+" is not present in the list", "FAIL");
			
			if(oParameters.GetParameters("NPI2").equalsIgnoreCase(get_text(providerNpi)))
				oReport.AddStepResult("Provider NPI", "Clicked on Facilty Name Drop Down  and verified that "+oParameters.GetParameters("NPI2")+" is present in the list", "PASS");			
			else
				oReport.AddStepResult("Provider NPI", "Clicked on Facilty Name Drop Down  and verified that "+oParameters.GetParameters("NPI2")+" is not present in the list", "FAIL");
	*/	}
		else
			oReport.AddStepResult("Add new provider icon", "Clicked on add new provider icon and verified that serach feild is not displayed", "FAIL");
	}
	
	By searchfeild=By.xpath("//div[@id='providerView']//div[@class='list-header light']//input[@class='search-text-box input-sm form-control pad-l-25 search-icon']");
	
	By firstProvider=By.xpath("//div[@id='providerView']//div[@class='portal-content adjust-height ng-scope']//ul[@class='data-list no-border']//li[1]"); 
	
	By firstProviderCheckbox=By.xpath("//input[@id='provider-npi-0']");
	
	By addProviderButton=By.xpath("//div[@id='providerView']//div[@class='form-button-panel align-bottom portal-content-footer ng-scope']//input[@value='Add']");
	
	By ErrorOccured=By.xpath("//div[@id='portal-main']//div[text()='An error occurred on the server.']");
	
	//This method is used to check few providers.
	public void checkFacilities()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		//dclick_button("Facility dropdown", facilityDropDown);
		javaScriptExecutor("Facility dropdown", facilityDropDown);
		fixed_wait_time(1);
		click_button("Facility NPI", facilityNpi);
		
		enter_text_value("Search provider ", searchfeild, oParameters.GetParameters("SearchProvider"));
		performKeyBoardAction("ENTER");
		
		longWaitFor(addProviderButton, "Add Provider Button");
		
		if(IsDisplayed("First provider", firstProvider))
		{
			oReport.AddStepResult("Search provider", "Clicked on search provider and verified that provider list are displayed", "PASS");
			click_button("First Provider", firstProviderCheckbox);
			click_button("Add Provider button",addProviderButton);
			
			if(IsDisplayed("An Error occurred on the server", ErrorOccured))
			{	
				waitFor(ErrorOccured,"Red Box Error");
				oReport.AddStepResult("An Error occurred on the server", "Clicked on Add New Provider icon and checked few Facilities and clicked on add button and verified that selected facilities is not added to test group and it is showing error message", "FAIL");
				oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			}
			else
				oReport.AddStepResult("An Error occurred on the server", "Clicked on Add New Provider icon and checked few Facilities and clicked on add button and verified that selected facilities added to test group", "PASS");
		}
		else
		{
			oReport.AddStepResult("Search provider", "Clicked on search provider and verified that provider list are not displayed and it is taking to much of time to load all provider", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}
	}
	
	By editProviderIcon=By.xpath("//div[@id='providerView']//a[@class='link-btn hand-cursor ng-isolate-scope']//i[@class='left fa fa-pencil']");
	
	By deleteProviderIcon=By.xpath("//div[@id='providerView']//a[@class='link-btn hand-cursor ng-isolate-scope']//i[@class='left fa fa-minus-square']");
	
	By editGroupNameFeild=By.xpath("//input[@id='providerGroupNameEdit']");
	
	//This method is used to hover over on test group and performed edit icon.
	public void hoverTestGroup()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("First Group", searchProvider, oParameters.GetParameters("addNewUserGroup"));
		performKeyBoardAction("ENTER");
		mouse_hover("Hover over first group", firstGroupProviderGroup);
		
		if(IsElementDisplayed("Edit Provider Icon", editProviderIcon) && IsElementDisplayed("Delete Provider Icon", deleteProviderIcon))
		{
			oReport.AddStepResult("Hover over on first provider Group", " Hover over on first provider Group and verified that Edit icon and Delete icon is displayed", "PASS");
			click_button("Edit Provider Icon",editProviderIcon);
			enter_text_value("Edit Group Name Feild", editGroupNameFeild, oParameters.GetParameters("addNewUserGroup")+System.currentTimeMillis());
			performKeyBoardAction("ENTER");
			
			fixed_wait_time(2);
			click_button("Search Close icon", searchCloseIcon);
			enter_text_value("First Group", searchProvider, "");
			performKeyBoardAction("ENTER");
		}	
		else
			oReport.AddStepResult("Hover over on first provider Group", "Hover over on first provider Group and verified that Edit icon and Delete icon is not displayed", "FAIL");
	}
		
	By deleteGroupTab=By.xpath("//div[@id='dialog']//div[contains(text(),'Delete Group')]");
	
	By deleteTestGroup=By.xpath("//div[@id='providerView']//li//div[@class='col-lg-10 col-md-10 ng-binding']");
	
	By deletePopUpYesButton=By.xpath("//div[@id='dialog_buttons']/input[@value='Yes']");
	
	By NoProviderExist = By.xpath("//div[@id='providerView']//div[text()='No providers exist for this group.']");
	
	//This method is used to hover over on test group and performed delete icon.
	public void hoverTestGroupDelete()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("First Group", searchProvider, oParameters.GetParameters("addNewUserGroup"));
		performKeyBoardAction("ENTER");
		
		mouse_hover("Hover over first group", firstGroupProviderGroup);
							
		if( IsElementDisplayed("Delete Provider Icon", deleteProviderIcon))
		{
			oReport.AddStepResult("Hover over on first provider Group", "Hover over on first provider Group and verified that Edit icon and Delete icon is displayed", "PASS");
			click_button("Edit Provider Icon",deleteProviderIcon);
						
			if(IsElementDisplayed("Delete Group Tab", deleteGroupTab))
			{
				oReport.AddStepResult("Delete Icon", "Clicked on delete icon and verified that delete group tab is displayed", "PASS");
				click_button("Delete tab yes button", deletePopUpYesButton);
				fixed_wait_time(2);
					
				if(IsElementDisplayed("Add Provider Group", providerAddIcon))
					oReport.AddStepResult("Delete Icon", "Clicked on delete icon and verified that respective provider group is deleted", "PASS");
				else
					oReport.AddStepResult("Delete Icon", "Clicked on delete icon and verified that respective provider group is not deleted", "FAIL");
			}
			else
				oReport.AddStepResult("Delete Icon", "Clicked on delete icon and verified that delete group tab is not displayed", "FAIL");
		}	
		else
			oReport.AddStepResult("Hover over on first provider Group", "Hover over on first provider Group and verified that Edit icon and Delete icon is not displayed", "FAIL");				
	}
	
	
	By contract=By.xpath("//div[@id='portal-main-nav']//a[contains(.,'Contract Management')]");
		
	By searchContractBegin=By.xpath("//div[@id='contractView']//div[@class='second-line ng-scope ng-binding']");
	
	//This method is used to navigate to contract.
	public void navigateContract()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		navigate_to(" Contract management", "Search for contract", contract, searchContractBegin);
	}
	
	By searchContract=By.xpath("//div[@id='contractView']//input[@class='search-text-box input-sm form-control pad-l-25 search-icon']");
	
	By firstContract=By.xpath("//ul[@id='contract-select']//li[1]");
	
	//This method is used to search for test contract.
	public void searchTestContract()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search Contract", searchContract, oParameters.GetParameters("searchContracts"));
		performKeyBoardAction("ENTER");
		
		if(IsElementDisplayed("First Contract", firstContract))
			oReport.AddStepResult("Searched for contracts", "Searched for contracts and contracts list are displayed", "PASS");
		else
			oReport.AddStepResult("Searched for contracts", "Searched for contracts and contracts list are not displayed", "FAIL");
	}
	
	By firstContractName=By.xpath("//ul[@id='contract-select']//li[1]//div[@class='contract-name']//div[@class='field-value ng-binding']");
	
	By respectiveContract=By.xpath("//div[@id='contractView']//div[@class='col-lg-12 col-md-12 col-sm-12 col-xs-12 xl-header ng-binding']");
	
	By associateRateSheet=By.xpath("//div[@id='contractView']//span[contains(text(),'Associate Rate Sheet')]");
	
	By ErrorMessage=By.xpath("//ul[@class='error-items']//li[@class='nopadding notification-item ng-scope ng-isolate-scope notification-error']//b[text()='Error.']/..");
	
	By ErrorMessageCloseIcon=By.xpath("//ul[@class='error-items']//li//span[@class='pull-right close-icon icon fa fa-times-circle']");

	By contractsName=By.xpath("//ul[@id='contract-select']//li//div[@class='contract-name']//div[@class='field-value ng-binding']");
	
	//This method is used to click on first contract and click on rate sheet association
	public void rateSheetAssociation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		List<WebElement> list = convertToWebElements(contractsName);
		
		for(int i=0;i<list.size();i++)
		{
			int index=i+1;
			By Contracts=By.xpath("//ul[@id='contract-select']//li["+index+"]");
			oParameters.SetParameters("FirstContractName", list.get(i).getText());
			
			click_button("first contract", Contracts);
			waitFor(respectiveContract,"Respective Contract Page");
			fixed_wait_time(10);
			
			if(oParameters.GetParameters("FirstContractName").equalsIgnoreCase(get_text(respectiveContract)))
			{
				waitFor(associateRateSheet,"Associate Rate Sheet Icon");
				oReport.AddStepResult("First Contract", "Clicked on first contract and verified that respective contract details page is opened", "PASS");
				
				if(IsElementDisplayed("Rate sheet association", associateRateSheet))
					oReport.AddStepResult(" clicked on first contract", "clicked on first contract and verified that rate association link is present", "PASS");
				else
					oReport.AddStepResult(" clicked on first contract", "clicked on first contract and verified that rate association link is not present", "FAIL");
				break;
			}		
			else
				oReport.AddStepResult("First Contract", "Clicked on first contract and verified that respective contract details page is not opened", "FAIL");
		}
	}
	
	By addRateSheetWindow=By.xpath("//div[@id='contractAssociationId']//div[@title='Add Rate Sheet Association']");
	
	By AddedRateSheetCount=By.xpath("//div[@id='contractView']//ul[@class='data-list']//li");
	
	By pastButton=By.xpath("//div[@id='segmented-button--0']");
	
	//This method is used to verfiying add rate sheet association window.
	public void addRateSheetAssociation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		oParameters.SetParameters("alreadyAddedRateSheetCount", String.valueOf((get_table_row_count(AddedRateSheetCount))));
		dclick_button("Rate sheet association", associateRateSheet);
		
		if(IsElementDisplayed("Add rate sheet window", addRateSheetWindow))
			oReport.AddStepResult("Clicked on associate rate sheet", "Clicked on associate rate sheet and verified that Add rate sheet association window is displayed", "PASS");
		else
			oReport.AddStepResult("Clicked on associate rate sheet", "Clicked on associate rate sheet and verified that Add rate sheet association window is not displayed", "FAIL");
	}
	
	By facilityNameDropDown=By.xpath("//div[@id='styledDropdown']//span[contains(text(),'Facility Name')]/..");
	
	By providerGroup=By.xpath("//li[@id='i[__valueField]']/a[contains(.,'Provider Group')]");
	
	By serviceProviderNameFeild=By.xpath("//form[@id='associatedRateSheetFrm']//input[@id='serviceProviderName']");
	
	By providerGroupTestList=By.xpath("//ul[@id='npiContainer']/li[32]/a");
	
	By providerGroupTestListCount=By.xpath("//ul[@id='npiContainer']/li/a");
	
	By firstProviderGroupValue = By.xpath("//ul[@id='npiContainer']//li[@class='ng-scope'][1]");
	 
	//This method is used to select provider group from service facility.
	public void selectProviderGroup()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Facility Name dropdown", facilityNameDropDown);
		click_button("Provider group", providerGroup);
		
		/*click_button("Service Provider Name", serviceProviderNameFeild);
		performKeyBoardAction("ENTER");
		click_button("First Provider Group Value", firstProviderGroupValue);
		*/
		enter_text_value("Service Provider Name", serviceProviderNameFeild, oParameters.GetParameters("addNewUserGroup"));
		
		oParameters.SetParameters("ProviderGroupCount", String.valueOf(get_table_row_count(providerGroupTestListCount)));
		By lastproviderGroup=By.xpath("//ul[@id='npiContainer']/li["+oParameters.GetParameters("ProviderGroupCount")+"]/a");
		
		if(Integer.parseInt(oParameters.GetParameters("ProviderGroupCount")) > 0 )
		{
			oReport.AddStepResult("Selected provider group and searched for test", "Selected provider group and searched for test and verified that test group list is displayed", "PASS");
			click_button("First test group", lastproviderGroup);
		}
		else
			oReport.AddStepResult("Selected provider group and searched for test", "Selected provider group and searched for test and verified that test group list is not displayed", "FAIL");
	}
	
	By healthPlanName=By.xpath("//input[@id='healthPlanDesc']");
	
	By healthPlanAlias=By.xpath("//input[@id='healthPlanAlias']");
	
	By rateSheet=By.xpath("//input[@id='ratesheetCode']");
	
	By firstRateSheet=By.xpath("//ul[@id='-list']//li[1]");
	
	By qualifyClaims=By.xpath("//select[@id='effectiveDateType']");
	
	By claimType=By.xpath("//select[@id='claimType']");
	
	By addRateSheetAssociationSaveButton=By.xpath("//div[@id='contentFooter']//input[1]");
	
	By fromDate = By.xpath("//form[@id='associatedRateSheetFrm']//input[@id='fromDate']");
	
	By toDate = By.xpath("//form[@id='associatedRateSheetFrm']//input[@id='toDate']");
	
	//This method is used to fill Rate sheet details.
	public void fillRateSheetDetails()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		facilityName("CONTRACTS", "Apollo srn facility");
		
		selectProviderGroup();
		
		enter_text_value("Health Plan Name", healthPlanName, oParameters.GetParameters("HealthPlanName"));
		enter_text_value("Health Plan Alias", healthPlanAlias, oParameters.GetParameters("HealthPlanAlias"));
		
		enter_text_value("Select rate sheet", rateSheet, "");
		performKeyBoardAction("ENTER");
		waitFor(oContractLibrary.rateSheetDropDownFirstElement, "First Rate Sheet");
		click_button("First rate Sheet", oContractLibrary.rateSheetDropDownFirstElement);
		
		selectOption(qualifyClaims, "value", "0");
		selectOption(claimType,"value","0");
		
		oParameters.SetParameters("FromDate",get_field_value("",fromDate));
		oParameters.SetParameters("AddRateSheetFromDate", get_next_date(oParameters.GetParameters("FromDate"), 2));
		
		click_button("TO Date", toDate);
		enter_text_value("To Date ", toDate, oParameters.GetParameters("AddRateSheetFromDate"));
	}
	
	By RedBoxError=By.xpath("//ul[@class='error-items']//li//span[@class='text-up ng-binding']");
	
	//This method is used to verifying that new rate sheet association is added.
	public void VerifingRateSheetAdded()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Red Box Error", RedBoxError))
		{
			oReport.AddStepResult("Rate Sheet Association", "Clicked on Rate sheet assocciation and selected provider group along with filled all other mandatory feilds and clicked on save button and verified that rate sheet association is not added", "FAIL");
			oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
		}	
		else
		{
			fixed_wait_time(2);
			List<WebElement> rateSheetCount =convertToWebElements(AddedRateSheetCount);
	
			if(rateSheetCount.size() > Integer.parseInt(oParameters.GetParameters("alreadyAddedRateSheetCount")))
				oReport.AddStepResult("Rate Sheet Association", "Clicked on Rate sheet assocciation Filled all the mandatory feilds of rate sheet and clicked on save button and verified that new Rate sheet association is added", "PASS");
			else
				oReport.AddStepResult("Rate Sheet Association", "Clicked on Rate sheet assocciation Filled all the mandatory feilds of rate sheet and clicked on save button and verified that new Rate sheet association is not added", "FAIL");
		}	
	}
	
	By firstrateSheet=By.xpath("//div[@id='contractView']//ul[@class='data-list']//li[2]");
	
	By firstrateSheetDeletebutton=By.xpath("//div[@id='contractView']//ul[@class='data-list']/li[2]//i[@class='left fa fa-times-circle']");
	
	By deleteAssociatedRateSheetTab=By.xpath("//div[@id='dialog']//div[contains(text(),'Delete Associated Rate Sheet')]");
	
	By YesButton=By.xpath("//div[@id='dialog_buttons']/input[@value='Yes']");
	
	//This method is used to delete a rate sheet.
	public void deleteRateSheet()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("All Tab",oContractLibrary.AllTabXpath);
		mouse_hover("first RateSheet", firstrateSheet);
		dclick_button("first RateSheet Delete button",firstrateSheetDeletebutton);
		
		if(IsElementDisplayed("Delete Associated Rate Sheet Tab", deleteAssociatedRateSheetTab))
		{
			oReport.AddStepResult("Delete Icon", "Clicked on first Rate sheet delete button and verified that Delete Associated Rate Sheet Tab is displayed", "PASS");
			click_button("Dailog box Yes Button", YesButton);
		}
		else
			oReport.AddStepResult("Delete Icon", "Clicked on first Rate sheet delete button and verified that Delete Associated Rate Sheet Tab is not displayed", "FAIL");
	}
	
	By configurationSuite=By.xpath("//div[@id='portal-main-nav']//a[text()='Configuration']");
	
	//This method is used navigate to configuration suite.
	public void navigateToConfiguration()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(associateRateSheet,"Associate Rate Sheet Icon");
		navigate_to("Configuration Suite", "User Authorization", configurationSuite, providergroups);
	}
	
	
	//This method is used to execute Providers groupings.
	public void ConfigurationProviderGroupings()
	{
		login("EDIT");
		changePricingEngine();
		ouserGroups.navigateToConfigurationSuite();
		ProviderGroups();
		addIconProviderGroupsValidation();
		
		addNewProviderIcon();
		checkFacilities();
		searchAndSelectViewOnlyProviderGroups();
		hoverTestGroup();
	
		navigateContract();
		searchTestContract();
		rateSheetAssociation();
		addRateSheetAssociation();
		//selectProviderGroup();
		fillRateSheetDetails();
		oContractLibrary.RSASaveButton();
		VerifingRateSheetAdded();
		deleteRateSheet();
		navigateToConfiguration();
		hoverTestGroupDelete();
		logout();
	}
}

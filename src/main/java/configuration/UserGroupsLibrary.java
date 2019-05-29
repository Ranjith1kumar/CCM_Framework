package configuration;

import java.util.List;
import org.openqa.selenium.*;
import libraries.*;

/**
 * @author C16137
 *
 */

public class UserGroupsLibrary extends CCMLibrary
{
	
	ExcelData oExcelData = new ExcelData(oParameters);
	
	public UserGroupsLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}
	
	By configurationSuite=By.xpath("//div[@id='portal-main-nav']//a[text()='Configuration']");
	
	By userAuthorization=By.xpath("//div[@id='portal-main']//a[text()='User Authorization']");
	
	//This Method is used to navigate from contract management to configuration Suite.
	public void navigateToConfigurationSuite()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oReport.AddStepResult("HighLight Element", "Element Highlighted is done", "SCREENSHOT");
		navigate_to("Configuration Suite", "User Authorization", configurationSuite, userAuthorization);
	}	
	
	By userGroups=By.xpath("//a[text()='User Groups']");
	
	By groupslist=By.xpath("//div[@id='usergroupView']//ul[@class='data-list no-border']//li");
	
	By groups=By.xpath("//div[@id='usergroupView']//div[contains(text(),'Groups')]");
			
	//This Method is used to User Group plugin.
	public void navigateToUserGroups()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(userGroups,"Users Groups Plugin");
		navigate_to("User Groups", "Groups", userGroups, groups);
	}
	
	By firstGroup=By.xpath("//div[@id='usergroupView']//li[1]//div[@class='col-lg-10 col-md-10 ng-binding']");
	
	By searchGroups=By.xpath("//div[@id='usergroupView']//div[@class='page-action-bar portal-header']//input[contains(@class,'search-text-box input-sm form-control')]");
	
	By groupSummaryTitle=By.xpath("//div[@id='usergroupView']//ul[@class='data-list no-border ng-scope']//div[@class='large-header accent-min ng-binding']");
	
	By testGrouptable=By.xpath("//div[@id='usergroupView']//td[@class='border-right']//li");
	
	By secondGroup=By.xpath("//div[@id='usergroupView']//li[2]//div[@class='col-lg-10 col-md-10 ng-binding']");
	
	By groupSummary=By.xpath("//div[@id='usergroupView']//td[@class='border-right']//li[2]");
	
	By users=By.xpath("//div[@id='usergroupView']//td[@class='border-right']//li[3]");
	
	By permissions=By.xpath("//div[@id='usergroupView']//td[@class='border-right']//li[4]");
	
	//This Method is used to search user group and click on user group.
	public void searchAndSelectViewOnly()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("FirstGroupName", get_text(firstGroup));
		enter_text_value("Search Users Group", searchGroups, oParameters.GetParameters("FirstGroupName"));
		performKeyBoardAction("ENTER");
		
		click_button("First User Group",firstGroup);
		
		if(oParameters.GetParameters("FirstGroupName").equals(get_text(groupSummaryTitle)))
		{
			waitFor(groupSummaryTitle,"Group Summary Tittle");
			oReport.AddStepResult("Searched User Group", "Searched with user group and verified that respective user group is displayed", "PASS");
			
			if(oParameters.GetParameters("Groupsummary").equalsIgnoreCase(get_text(groupSummary)) && oParameters.GetParameters("Users").equalsIgnoreCase(get_text(users)))
			{
				if(oParameters.GetParameters("Permissions").equalsIgnoreCase(get_text(permissions)))
					oReport.AddStepResult("User Group", "Clicked on test user group and verified that 'Test Group' 'Group summary' 'Users' 'Permissions' is displayed", "PASS");
				else 
					oReport.AddStepResult("User Group", "Clicked on test user group and verified that 'Test Group' 'Group summary' 'Users' 'Permissions' is not displayed", "FAIL");
			}
		}	
		else
			oReport.AddStepResult("Searched User Group", "Searched with user group and verified that respective user group is not displayed", "FAIL");
	}
	
	By UserGroup=By.xpath("//div[@id='usergroupView']//div[@class='bottom-toolbar-container']//li[1]");
	
	By addUsersGroup=By.xpath("//div[@id='usergroupView']//div[contains(text(),'Add Users to Group')]");
	
	//This Method is used to click on Users Section.
	public void userSection()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Users", users);
	
		if(IsDisplayed("First User group", UserGroup))
			oReport.AddStepResult("User Group", "Clicked on users section and verified that list of users using the group is displayed", "PASS");
		else
			oReport.AddStepResult("User Group", "Clicked on users section and verified that list of users using the group is not displayed", "PASS");
	}
	
	By addIcon=By.xpath("//div[@id='usergroupView']//span[text()='Add User Group']/..");
	
	//This Method is used to validate Add icon.
	public void addIconValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Add Icon", addIcon))
			oReport.AddStepResult("Add Icon", "Verified that Add icon or Add User Group is displayed", "FAIL");
		else
			oReport.AddStepResult("Add Icon", "Verified that Add icon or Add User Group is not displayed", "PASS");
	}
	
	By editIcon=By.xpath("//div[@id='usergroupView']//li[@class='data-row drillable hand-cursor position-relative ng-scope ui-selected']//i[@class='left fa fa-pencil']");
	
	By deleteIcon=By.xpath("//div[@id='usergroupView']//li[@class='data-row drillable hand-cursor position-relative ng-scope ui-selected']//i[@class='left fa fa-minus-square']");

	//This Method is used to validate Edit and Delete icon.
	public void editDeleteIconValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		mouse_hover("First Group", firstGroup);
		
	   if(IsDisplayed("Edit icon Validation", editIcon) && IsDisplayed("Delete icon Validation", deleteIcon))	
		   oReport.AddStepResult("Hover over User group", "Hover over User group and verified that Edit and Delete icon is displayed", "FAIL");
	   else
		   oReport.AddStepResult("Hover over User group", "Hover over User group and verified that Edit and Delete icon is not displayed", "PASS");
	}
	
	By downloadUserreport=By.xpath("//div[@id='usergroupView']//a[contains(@class,'button hand-cursor link-btn hand-cursor')]//i[@class='left fa fa-cloud-download']");
	
	//This Method is used to validate download user report.
	//@SuppressWarnings("static-access")
	public void downloadUsergroupIcon()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(2);
		dclick_button("Download User report",downloadUserreport);
		
		try 
		{
			fixed_wait_time(4);
			//Runtime.getRuntime().exec("C:\\CCM\\Download_savebutton.exe");
			csvToExcel();
	    	@SuppressWarnings("static-access")
			int count = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"),"xlsx"));
	    	
	    	if(count >= 0) 
	    		oReport.AddStepResult("download user group","Clicked on download user group and verified that all "+count+" user's groups are downloaded ","PASS");
	    	else
	    		oReport.AddStepResult("download user group","Clicked on download user group and verified that all "+count+" user's groups are not downloaded ","FAIL");
	    }
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//This Method is used to execute Configure User Groups View Only permission.
	public void ConfigureViewOnlyPermission()
	{
		login("VIEW");
		changePricingEngine();
		navigateToConfigurationSuite();
		navigateToUserGroups();
		searchAndSelectViewOnly();
		userSection();
		addIconValidation();
		editDeleteIconValidation();
		downloadUsergroupIcon();
		logout();
	}
	
	By permissionSection=By.xpath("//div[@id='usergroupView']//span[text()='Permissions']");
	
	By compartive=By.xpath("//div[@id='app-list-frame-new']//label[contains(.,'Comparative Analysis')]");
	
	By compartiveCheckbox=By.xpath("//input[@id='users:analytics.analytics']");
	
	By codes=By.xpath("//div[@id='app-list-frame-new']//label[contains(.,'Codes')]");
	
	By codesCheckbox=By.xpath("//input[@id='users:cm.codes.edit']");
	
	By comparativeAnalysis=By.xpath("//div[@id='usergroupView']//span[contains(.,'Comparative Analysis')]");
	
	//This Method is used to click on permissions Section
	public void permissionsSection()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("permissions section",permissionSection);
		
		oParameters.SetParameters("ComparativeAnalysis", get_text(compartive));
		oParameters.SetParameters("CodesPermissions", get_text(codes));
		
		if(IsElementSelected("Compartive Checkobox", compartiveCheckbox))
			System.out.println("Compartive CheckBox is already selected");
		else
		{
			fixed_wait_time(2);
			click_button("Compartive CheckBox", compartiveCheckbox);
			click_button("Save Button", savebutton);
		}
		
		fixed_wait_time(2);
		click_button("Group Summary", groupSummary);
			
		if(oParameters.GetParameters("ComparativeAnalysis").equalsIgnoreCase(get_text(comparativeAnalysis)))
			oReport.AddStepResult("Permission Section", "Clicked on permission section and verified that permission given to the group is displayed", "PASS");
		else
			oReport.AddStepResult("Permission Section", "Clicked on permission section and verified that permission given to the group is not displayed", "FAIL");
	}
	
	By addUserGroup=By.xpath("//div[@id='usergroupView']//span[text()='Add User Group']");
	
	By userGroupFeild=By.xpath("//input[@id='addUserGroupName']");
	
	By allGroups=By.xpath("//div[@id='usergroupView']//li//div[@class='col-lg-10 col-md-10 ng-binding']");
	
	//This Method is used to add user group.
	@SuppressWarnings("unused")
	public void addUserGroup()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
	    dclick_button("Add user Group", addUserGroup);
	               
	    if(IsElementEnabled("User Group Feild", userGroupFeild))
	    {       
	    	oReport.AddStepResult("User Group", "Clicked on add user group and verified that user group text feild is enabled", "PASS");
	    	enter_text_value("User Group Name Feild", userGroupFeild, oParameters.GetParameters("UserGroupName")+System.currentTimeMillis());
	    	oParameters.SetParameters("CreatedUserGroupName", get_field_value("User Group", userGroupFeild));
	    	performKeyBoardAction("ENTER");
	    	
	    	waitFor(allGroups,"All User Group");
	    	List<WebElement> list =convertToWebElements(allGroups);
	    	
	    	for(int i=0;i<=list.size();i++)
	    	{
	    		if(oParameters.GetParameters("CreatedUserGroupName").equals(list.get(i).getText()))
	    			oReport.AddStepResult("Added new User Group", "Added new user group and verified that new user group is created under list", "PASS");
	    		break;
	        }
	    }       
	    else
	    	oReport.AddStepResult("User Group", "Clicked on add user group and verified that user group text feild is not enabled", "FAIL");
	}	
	
	By allUserGroup = By.xpath("//div[@id='usergroupView']//li//div[@class='col-lg-10 col-md-10 ng-binding']");
	
	public void CreateUserGroups()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("All Users Groups", allUserGroup))
		{
			System.out.println("Already User Groups are Present");			
			addUserGroup();
		}	
		else
		{
			addUserGroup();
			addUserGroup();
		}
	}

	By analytics=By.xpath("//div[@id='app-list-frame-new']//label[contains(.,'Analytics')]");
		
	By contractManagement=By.xpath("//div[@id='app-list-frame-new']//label[contains(.,'Contract Management')]");
			
	By checkbox=By.xpath("//div[@id='usergroupView']//input[@type='checkbox']");
		
	By repricingCheckbox=By.xpath("//input[@id='users:cm.repricing.edit']");
		
	By repricingView=By.xpath("//label[contains(.,'Repricing')]/../..//input[@value='view']");
		
	By savebutton=By.xpath("//div[@id='usergroupView']//input[@value='Save']");
		
	By Codes=By.xpath("//div[@id='usergroupView']//span[contains(.,'Codes')]");
		
	//This method is used to select a user group and clicking on permissions.
	public void searchCreatedUserGroup()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Search Users Group", searchGroups, oParameters.GetParameters("UserGroupName"));
		performKeyBoardAction("ENTER");
		click_button("First group",firstGroup);
		click_button("permissions section",permissionSection);
	
		if(IsElementDisplayed("Analytics plugin", analytics) && IsElementDisplayed("Contract management", contractManagement))
		{	
			waitFor(analytics,"Analytics in permissions section");
			oReport.AddStepResult("Permission Section", "Clicked on permission and verified that list suites and plugins are displayed", "PASS");
		}
		else
			oReport.AddStepResult("Permission Section", "Clicked on permission and verified that list suites and plugins are not displayed", "FAIL");
			
		click_button("codes checkbox",codesCheckbox);
		click_button("repricing checkbox",repricingCheckbox);
		fixed_wait_time(3);
		click_button("repricng View radio button",repricingView);
		
		click_button("Save button",savebutton);
		click_button("Group Summary", groupSummary);
		waitFor(Codes,"Description column in Group Summary");
			
		if(oParameters.GetParameters("CodesPermissions").equalsIgnoreCase(get_text(Codes)))
			oReport.AddStepResult("Respective access to plugin" ,"Verified that respective access to plugin is saved for the user", "PASS");
		else
			oReport.AddStepResult("Respective access to plugin" ,"Verified that respective access to plugin is not saved for the user", "FAIL");
	}
		
	By repricingcheckbox=By.xpath("//input[@id='users:cm.repricing.edit']");
	
	By contractcheckbox=By.xpath("//input[@id='app1']");
	
	public void permissions()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("permissions section",permissionSection);
	
		if(IsElementDisplayed("Analytics plugin", analytics) && IsElementDisplayed("Contract management", contractManagement))
			oReport.AddStepResult("Clicked on permission", "Clicked on permission and verified that list suites and plugins are displayed", "PASS");
		else
			oReport.AddStepResult("Clicked on permission", "Clicked on permission and verified that list suites and plugins are not displayed", "FAIL");

		click_button("Codes checkbox",codesCheckbox);
		click_button("repricing checkbox",repricingcheckbox);
		click_button("Contract checkbox",contractcheckbox);
		click_button("Save button",savebutton);
	}
		
	By searchUser=By.xpath("//div[@id='usergroupView']//div[contains(@class,'search-text-box no-border')]//input[contains(@class,'search-text-box input-sm form-control')]");
		
	By addUserstoGroup=By.xpath("//div[@id='usergroupView']//div[contains(text(),'Add Users to Group')]");
		
	By firstUserAccount=By.xpath("//div[@id='usergroupView']//li[1]/label[@class='col-lg-12 col-md-12']//span[@class='ng-binding']");
	
	By firstUserAccountChecckbox=By.xpath("//input[@id='addUserGroup-user-0']");
		
	By addButton=By.xpath("//div[@id='usergroupView']//div[contains(@class,'form-button-panel portal-content-footer align-bottom')]//input[@value='Add']");
	
	By delete=By.xpath("//div[@class='portal-right-sidebar adjust-height ng-scope']//div[@class='pull-right icon-container delete-align']//i[1]");
	
	By deletePopUpYesButton=By.xpath("//div[@id='dialog_buttons']/input[@value='Yes']");
	
	//This Method is used to click on Users section. 
	public void usersSection()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(2);
		click_button("Users Section",users);
		
		if(IsElementDisplayed("Add Users to Group tab", addUserstoGroup))
		{	
			oReport.AddStepResult("Users Section", "Clicked on Users Section and verified that add Users to Group tab is displayed", "PASS");
			
			if(IsDisplayed("First User", firstAccount))
			{	
				oReport.AddStepResult("First User account group", "First User account group is displayed", "PASS");
				mouse_hover("Hover over First Group", firstAccount);
				click_button("Delete First User Group", delete);
				click_button("Dailog box button", deletePopUpYesButton);
			}
			else	
				oReport.AddStepResult("First User account group", "First User account group is not displayed", "PASS");
		}
		else
			oReport.AddStepResult("Users Section", "Clicked on Users Section and verified that add Users to Group tab is not displayed", "FAIL");
		
			
		enter_text_value("Search User", searchUser, oParameters.GetParameters("SearchUser"));
		performKeyBoardAction("ENTER");
		waitFor(firstUserAccount,"First Users");
		waitFor(firstUserAccountChecckbox,"First Users Checkbox");
		fixed_wait_time(2);
			
		if(IsDisplayed("Users Account", firstUserAccount))
			oReport.AddStepResult("Searched by user group", "Searched by user and verified that list of user account is displayed", "PASS");
		else
			oReport.AddStepResult("Searched by user group", "Searched by user and verified that list of user account is not displayed", "FAIL");
	}
		
	By firstAccount=By.xpath("//ul/li[1]/div[@class='col-lg-11 col-md-11 ng-binding']");
		
	//This Method is used to select user account and click on add button.
	public void selectUserAccount()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("FirstUserAccount", get_field_value("User Account", firstUserAccount));
		
		if(IsElementSelected("User Checkbox", firstUserAccountChecckbox))
			click_button("Add button",addButton);
		else
		{
			click_button("First user account checkbox",firstUserAccountChecckbox);
			click_button("Add button",addButton);
		}
			
		waitFor(firstAccount,"First Added user to group");
		oParameters.SetParameters("FirstUser", get_field_value(" User Account", firstAccount));
			
		if(IsDisplayed("first user account", firstAccount))
			oReport.AddStepResult("First User Account", "Selected 'Test' and clicked on add button and verified that 'Test' user is added to the user group", "PASS");
		else
			oReport.AddStepResult("First User Account", "Selected 'Test' and clicked on add button and verified that 'Test' user is not added to the user group", "FAIL");
	}
		
	//This method is used to click on permission. 
	public void permissionSectionValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("permissions section",permissionSection);
	
		if(IsElementDisplayed("Analytics plugin", analytics) && IsElementDisplayed("Contract management", contractManagement))
		{	
			waitFor(analytics,"Analytics in permission section");
			oReport.AddStepResult("Permission Section", "Selected Test Group and clicked on permission section and verified that permission given to the test group is displayed", "PASS");
		}
		else
			oReport.AddStepResult("Permission Section", "Selected Test Group and clicked on permission section and verified that permission given to the test group is not displayed", "FAIL");
	}
	
	By usersExist=By.xpath("//div[@id='usergroupView']//div[@class='medium-header margin-b-10 ng-binding']");
		
	By userAccountNumber=By.xpath("//ul//div[@class='col-lg-11 col-md-11 ng-binding']");
		
	//This method is used to click on Users. 
	public void usersValidation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Users Section",users);
	
		if(IsElementDisplayed("Add Users to Group tab", addUserstoGroup))
		{	
			oReport.AddStepResult("Add User Group Tab", "Clicked on Users Section and verified that add Users to Group tab is displayed", "PASS");
			oParameters.SetParameters("UserAccount", String.valueOf(get_table_row_count(userAccountNumber)));
			
			click_button("Group Summary", groupSummary);
			waitFor(groupSummaryTitle,"Group Summary Tittle");
			oParameters.SetParameters("UserExistCount", get_field_value("User exist for this group", usersExist).replace(" Users exist for this group",""));
			
			if(oParameters.GetParameters("UserAccount").equals(oParameters.GetParameters("UserExistCount")))
				oReport.AddStepResult("Group summary", "Clicked on Group summary and verified that summary is displayed according to modification", "PASS");
			else
				oReport.AddStepResult("Group summary", "Clicked on Group summary and verified that summary is not displayed according to modification", "FAIL");
		}
		else
			oReport.AddStepResult("Add User Group Tab", "Clicked on Users Section and verified that add Users to Group tab is not displayed", "FAIL");
	}
		
	By userGroupEdit=By.xpath("//div[@id='usergroupView']//div[@class='pull-left first icon-container']//i[@class='left fa fa-pencil']");
		
	By userGroupDelete=By.xpath("//div[@id='usergroupView']//div[@class='pull-right icon-container delete-align']//i[@class='left fa fa-minus-square']");
		
	By EditUserGroupFeild=By.xpath("//input[@id='userGroupNameEdit']");
		
	//This method is used to hover over user group.
	public void HoveroverUserGroup()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
	
		enter_text_value("Search Users Group", searchGroups, oParameters.GetParameters("UserGroupName"));
		performKeyBoardAction("ENTER");
	
		mouse_hover("First Group", firstGroup);
		
		if(IsElementDisplayed("User Group Edit", userGroupEdit) && IsElementDisplayed("User Group Delete", userGroupDelete))
		{	
			oReport.AddStepResult("User Group", " Clicked on user group and verified that Edit and Deleted icon is displayed", "PASS");
			click_button("User Group Edit", userGroupEdit);
			
			enter_text_value("User Group Name Feild", EditUserGroupFeild, oParameters.GetParameters("UserGroupName")+System.currentTimeMillis());
			performKeyBoardAction("ENTER");
			waitFor(firstGroup,"First User Group");
			
			enter_text_value("Search Users Group", searchGroups, oParameters.GetParameters("UserGroupName"));
			performKeyBoardAction("Enter");
			
			if(get_text(firstGroup).contains(oParameters.GetParameters("UserGrSoupName")))
				oReport.AddStepResult("Modified Group name", " Modified Group name and verified that modified name is displayed", "PASS");
			else
				oReport.AddStepResult("Modified Group name", " Modified Group name and verified that modified name is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("User Group", "Clicked on user group and verified that Edit and Deleted icon is not displayed", "FAIL");
	}
		
	By deleteUserGroupPopUp=By.xpath("//div[@id='dialog']//div[@class='medium-header bold ng-binding']");
		
	By deletePopUpNoButton=By.xpath("//div[@id='dialog_buttons']/input[@value='No']");
		
	//This Method is used to delete a user Group.		
	public void deleteUserGroup()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
	
		mouse_hover("First Group", firstGroup);
			
		if(IsElementDisplayed("User Group Edit", userGroupEdit) && IsElementDisplayed("User Group Delete", userGroupDelete))
		{	
			oReport.AddStepResult("User Group", "Clicked on user group and verified that Edit and Deleted icon is displayed", "PASS");
			click_button("User Group Delete", userGroupDelete);
				
			if(IsElementDisplayed("Delete user group", deleteUserGroupPopUp))
			{	
				oReport.AddStepResult("Delete icon", "Clicked on delete icon and verified that 'Delete user Group Pop Up' is displayed ", "PASS");
				click_button("Delete PopUp No Button", deletePopUpYesButton);
			}
			else
				oReport.AddStepResult("Delete icon", "Clicked on delete icon and verified that 'Delete user Group Pop Up' is not displayed ", "FAIL");
		}
		else
			oReport.AddStepResult("User Group", "Clicked on user group and verified that Edit and Deleted icon is not displayed", "FAIL");
	}
		
	//This method is used to execute Configure user Groups VR.
	public void configureUserGroups()
	{
		login("EDIT");
		changePricingEngine();
		navigateToConfigurationSuite();
		navigateToUserGroups();
		CreateUserGroups();
		searchAndSelectViewOnly();
		userSection();
		permissionsSection();
		searchCreatedUserGroup();
		permissions();
		usersSection();
		selectUserAccount();
		searchAndSelectViewOnly();
		permissionSectionValidation();
		usersValidation();
		HoveroverUserGroup();
		downloadUsergroupIcon();
		deleteUserGroup();
		logout();
	}
} 		
package analytics;

import java.io.*;
import java.text.*;
import java.util.Date;
import org.openqa.selenium.*;
import libraries.*;

public class ReportsLibrary extends CCMLibrary
{
	ExcelData oExcelData;

	public ReportsLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}

	
	public void xpathChange() 
	{
		if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA")) 
		{
			list120DayReport = list120DayReportDC;
			claimRepricingReport = claimRepricingReportDC;
			contractErrorClaimReport = contractErrorClaimReportDC;
			contractListingReport = contractListingReportDC;
			rateSheetAssociationReport = rateSheetAssociationReportDC;
			qualificationGroupUsageReport = qualificationGroupUsageReportDC;
			rateSheetTermsReport = rateSheetTermsReportDC;
			
		}
		else if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT")) 
		{
			list120DayReport = list120DayReportDC;
			claimRepricingReport = claimRepricingReportDC;
			contractErrorClaimReport = contractErrorClaimReportDC;
			contractListingReport = contractListingReportDC;
			rateSheetAssociationReport = rateSheetAssociationReportDC;
			qualificationGroupUsageReport = qualificationGroupUsageReportDC;
			rateSheetTermsReport = rateSheetTermsReportDC;				
		}
		else if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVTEST")) 
		{
		}
	}
	
	
	By userNameTextBox = By.xpath(".//*[@id='authUsername']");
	
	By logout = By.xpath("//span[contains(text(),'Logout')]");
	
	
	// Logout
	public void portalLogout()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(By.xpath("//span[contains(text(),'Logout')]"), "");
		click_button("Logout Button", logout);
		fixed_wait_time(10);
		/*waitFor(userNameTextBox, "User Name text box");
		oReport.AddStepResult("Logout", "Successfully logged out of portal", "SCREENSHOT");*/
	}
	

	By analyticsSuite = By.xpath("//div//a[text()='Analytics']");

	By reportsPlugIn = By.xpath("//*[@id='reports'][contains(text(),'Reports')]");

	By comparativeAnalysisPlugin = By.xpath("//ul//a[@id='reports'][text()='Comparative Analysis']");

	
	// Navigate to Analytics
	public void navigate_to_Analytics()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(analyticsSuite, "Analytics Suite");
		navigate_to("Analytics Suite", "Reports PlugIn", analyticsSuite, reportsPlugIn);
		
		if(!IsDisplayed("Reports PlugIn", reportsPlugIn))
			oParameters.SetParameters("Continued_Execution", "No");
	}
	
	
	// To Select Report
	public void select_Report(String reportType)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		By reportName = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'"+reportType+"')]");
				
		if(IsElementDisplayed("selectAReportButton", selectAReportButton))
		{
			click_button("Select a Report drop down", selectAReportButton);
			click_button(reportType+" Option", reportName);
			waitForIsInvisible(loadingIcon, "Loading Icon");
			waitFor(createReportButton, "Create Report Button");

			if(IsElementDisplayed("Create Report Button", createReportButton))
				oReport.AddStepResult(reportType, reportType+" Page displayed without any error", "PASS");
			else
				oReport.AddStepResult(reportType, "Selected "+reportType+" form select a report drop down but that Page is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("select A Report Button ", "select a report button not displayed", "INFO");
	}
	

	// To Select Report
	public void select_Report_1(String reportType)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
				
		By reportName = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'"+reportType+"')]");
				
		if(IsElementDisplayed("selectAReportButton", selectReportButton))
		{
			click_button("Select a Report drop down", selectReportButton);
			click_button(reportType+" Option", reportName);
			waitFor(createReportButton, "Create Report Button");

			if(IsElementDisplayed("Create Report Button", createReportButton))
				oReport.AddStepResult(reportType, reportType+" Page displayed without any error", "PASS");
			else
				oReport.AddStepResult(reportType, "Selected "+reportType+" form select a report drop down but that Page is not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("select A Report Button ", "select a report button not displayed", "INFO");
	}
	
	
	By selectAReportButton = By.xpath("//div[@class='document-title-bar margin-b-10']//span[contains(text(),'Select a report')]");
	
	By selectReportButton = By.xpath("//div[@id='title-bar']//div[@id='styledDropdown']/a[@class='btn view-bg-text btn-new-light btn-lg']");

	By list120DayReport = By.xpath("//div[@class='styled-dropdown black-text-when-disabled ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'120 Day Report')]");

	By list120DayReportDC = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'120 Day Report')]");
	
	By createReportButton = By.xpath("//button[contains(text(),'Create Report')]");

	
	// Create Report Button
	public void createReportButton()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsElementDisplayed("Create Button", createReportButton))
		{
			oReport.AddStepResult("Create Report Button", "Create Report button is displayed", "PASS");
			click_button("Create Report Button", createReportButton);
			fixed_wait_time(10);
			
			waitForIsInvisible(loadingIcon, "Loading Icon");
			
			if(IsDisplayed("Create report button", createReportButton))
			{
				oReport.AddStepResult("Report page", "Clicked on Create report button and verified that page is displayed", "PASS");
			}
			else
			{
				oReport.AddStepResult("Report page", "Clicked on Create report button but that page is not loaded completely", "FAIL");
			}
			
			
		} 
		else
			oReport.AddStepResult("Create Report Button", "Create Report button not displayed", "FAIL");
	}
	
	
	By renewalTermDate = By.xpath("//table[@report-title='title-bar']/tbody/tr[1]/td[10]");

	
	// 120 Day Contracts reports validation
	public void report_120Day_Contracts()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("get_specified_date", get_specified_date(120));
		oParameters.SetParameters("terminationDate", get_field_value("Renewal Term Date", (renewalTermDate)));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		try
		{
			Date date1 = sdf.parse(oParameters.GetParameters("get_specified_date"));
			Date date2 = sdf.parse(oParameters.GetParameters("terminationDate"));
			System.out.println("date1 : " + sdf.format(date1));
			System.out.println("date2 : " + sdf.format(date2));

			if (date1.after(date2))
				oReport.AddStepResult("120 Day Reports ", "contracts falling under 120 days are Displayed", "PASS");
			else
				oReport.AddStepResult("120 Day Reports ","120 Day Reports opened but contracts falling under 120 days are not displayed", "FAIL");
		}
		catch(ParseException e)
		{
			System.out.println("Exception Caught" + e);
		}	
	}

	
	By contractsPlugIn = By.xpath("//li/a[text()='Contracts']");

	By addFilterElement = By.xpath("//span[text()='Add Filter']");

	By contractManagerElement = By.xpath("//li//a[text()='Contract Manager']");

	By contractNameElement = By.xpath("//li//a[text()='Contract Name']");

	By negotiatorElement = By.xpath("//li//a[text()='Negotiator']");

	By contractNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'contractName')\"]");

	By contractManagerColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'contractManager')\"]");

	public By filtersTextBox = By.xpath("//div/input[@title='Enter text to filter and press enter.'][@class='text']");

	By contractorName = By.xpath("//div[@id='contractView']//div[@class='col-lg-12 col-md-12 col-sm-12 col-xs-12 xl-header ng-binding']");

	By negotiatorColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'contractNegotiator')\"] ");

	public By clearFiltersButton = By.xpath("//*[@id='filter-report']//i[@class='left fa-lg fa fa-times-circle']");

	By downloadCSVButton = By.xpath("//div[@title='Download CSV']//i[@class='left fa fa-cloud-download']");

	By errorMessage = By.xpath("//li[1]/span[@class='notification-icon icon fa fa-minus-circle notification-error-icon'][@icon='minus-circle']");

	By errorMessageClose = By.xpath("//li[1]/span[@class='pull-right close-icon icon fa fa-times-circle'][@icon='times-circle']");

	
//	public void contractNameFilterReports()
	{
		
	}

	// Filter Reports
	public void filterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Filter report using contract manager

		oParameters.SetParameters("ContractManagerBeforeFilter",get_field_value("Contract Manager", contractManagerColumn).toLowerCase());
		addFilter("Contract Manager");
		enter_text_value("Search Expression", filtersTextBox, oParameters.GetParameters("ContractManagerBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("ContractName", get_field_value("Contract Name", contractNameColumn));
		oParameters.SetParameters("ContractManagerAfterFilter",	get_field_value("Contract Manager", contractManagerColumn).toLowerCase());

		if(oParameters.GetParameters("ContractManagerAfterFilter").contains(oParameters.GetParameters("ContractManagerBeforeFilter"))) 
		{
			waitFor(contractManagerColumn, "120 Day reports table");
			oReport.AddStepResult("Contract manager reports ","Reports are filtered with Contract Manager those Reports are Displayed", "PASS");
		}
		else
		{
			waitFor(contractManagerColumn, "120 Day Reports table");
			oReport.AddStepResult("Contract manager reports ","Reports are filtered with Contract Manager but those Reports are not Displayed", "FAIL");
		}

		// Contract link Validation

		click_button("Contract Name", contractNameColumn);
		waitFor(contractsPlugIn, "Contracts PlugIn");

		if(IsDisplayed("Error Message", errorMessage))
		{
			oReport.AddStepResult("Error Message","Clicked on Contract Name, page navigated to Contract plugin but Displaying Error Message and that selected contract is not Displayed ","FAIL");
			click_button("Error Message Close Icon", errorMessageClose);
		}
		else
		{
			oParameters.SetParameters("ContractNameAfterNavigation", get_field_value("Contract Name", contractorName));

			if(IsElementDisplayed("Contracts", contractsPlugIn) && oParameters.GetParameters("ContractName").equalsIgnoreCase(oParameters.GetParameters("ContractNameAfterNavigation")))
				oReport.AddStepResult("Selected Contract", "Selected Contract Displayed", "PASS");
			else
				oReport.AddStepResult("Selected Contract ","Clicked on Contract Name, page not navigated to Contract plugin and selected contract is not Displayed ","FAIL");
		}

		waitFor(analyticsSuite, "Analytics Suite");
		navigate_to("Analytics Suite", "Reports PlugIn", analyticsSuite, reportsPlugIn);
		waitFor(noOfRecordsElement, "No of Records");

		if(IsDisplayed("Contractors Name", contractManagerColumn))
			oReport.AddStepResult("Contractors Name", "Contractors Name Displayed", "PASS");
		else
			oReport.AddStepResult("Contractors Name","Clicked on Analytics but it doesn't navigated and Contract name not Displayed", "FAIL");

		// Filter Reports using Contract Name

		clearFilters();
		createReport();
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("ContractNameBeforeFilter", get_field_value("Contract Name", contractNameColumn));
		addFilter("Contract Name");
		enter_text_value("SearchExpression", filtersTextBox, oParameters.GetParameters("ContractNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("ContractNameAfterFilter", get_field_value("Contract Name", contractNameColumn));

		if(oParameters.GetParameters("ContractNameBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractNameAfterFilter")))
			oReport.AddStepResult("Contract Name Reports ", "Contract Name filter  reports Displayed", "PASS");
		else
			oReport.AddStepResult("Contract Name Reports ", "Reports are filtered with Contract Name but those reports are not Displayed", "FAIL");

		// Filter Reports using negotiator

		clearFilters();
		createReport();
		waitFor(addFilterElement, "Add Filter");
		oParameters.SetParameters("NegotiatorNameBeforeFilter", get_field_value("Negotiator Name", negotiatorColumn));
		addFilter("Negotiator");
		enter_text_value("SearchExpression", filtersTextBox, oParameters.GetParameters("NegotiatorNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(negotiatorColumn, "120 Day reports table");
		oParameters.SetParameters("NegotiatorNameAfterFilter", get_field_value("Negotiator Name", negotiatorColumn));

		if(oParameters.GetParameters("NegotiatorNameBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("NegotiatorNameAfterFilter")))
			oReport.AddStepResult("Filtered Negotiator reports", "Negotiator filter reports Displayed", "PASS");
		else
			oReport.AddStepResult("Filtered Negotiator reports", "Reports are filtered with Negotiator Name but those reports are not Displayed", "FAIL");
	}

	
	// Records Comparison
	public void comparing120DayDownloadedReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("NoOfRecords",get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));

		CreateFolder(oParameters.GetParameters("downloadFilepath"));
		waitFor(downloadCSVButton, "Download CSV Button");

		click_button("Download Button", downloadCSVButton);
		fixed_wait_time(5);

		if(isFileDownloaded("120DayReport.zip")) 
		{
			unZipFolder(oParameters.GetParameters("downloadFilepath")+"/120DayReport.zip",oParameters.GetParameters("downloadFilepath"));
			File zipFile = new File(oParameters.GetParameters("downloadFilepath")+"/120DayReport.zip");
			zipFile.delete();
			csvToExcel();
			@SuppressWarnings("static-access")
			int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
			oParameters.SetParameters("RecordsInExcel", String.valueOf(recordsCount));

			if(oParameters.GetParameters("NoOfRecords").equals(oParameters.GetParameters("RecordsInExcel")))
				oReport.AddStepResult("Downloaded Records", "Downloaded Records Matched", "PASS");
			else
				oReport.AddStepResult("Downloaded Records",	"Records downloaded in excel file but number of record in portal Not Matched with number of records in downloaded excel file","FAIL");
		}
		else
			oReport.AddStepResult("Downloaded File", "Clicked on 'Download CSV' button but zip file is not downloaded ","FAIL");
	}

	
	By resultPerPageDropDown = By.xpath("//span[@class='pull-left per-page margin-t-m2 ng-scope']");

	By results50 = By.xpath("//option[contains(text(),'results per page')][@value='50']");

	
	// No of Results per page validation
	public void resultsPerPage(String str)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By resultsPerPage = By.xpath("//option[contains(text(),'results per page')][@value='" + str + "']");

		click_button("Results Per Page DropDown", resultPerPageDropDown);
		click_button("Results per page", resultsPerPage);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("ResultsPerPage", get_field_value("No. Of Results", resultsPerPage));

		if(oParameters.GetParameters("ResultsPerPage").contains(oParameters.GetParameters("ResultsPerPage"))) 
		{
			if(oParameters.GetParameters("ResultsPerPage").contains("50"))
				oReport.AddStepResult("50 results per page", "50 results per page Displayed", "PASS");
			else if(oParameters.GetParameters("ResultsPerPage").contains("100"))
				oReport.AddStepResult("100 results per page", "100 results per page Displayed", "PASS");
			else if(oParameters.GetParameters("ResultsPerPage").contains("200"))
				oReport.AddStepResult("200 results per page", "200 results per page Displayed", "PASS");
		}
		else
			oReport.AddStepResult("Desired Results per page", "Desired Results per page Not Displayed", "FAIL");
	}

	
	By bookmarkButton = By.xpath("//span/button[1][contains(@class,'fa fa-bookmark')]");

	By bookmarkButtonHavingBookmark = By.xpath("//span/button[2][contains(@class,'fa fa-bookmark')]");

	By saveBookmarkLink = By.xpath("//div[contains(text(),'Save current page as a bookmark...')]");

	By addBookmarkWindow = By.xpath("//div[@title='Create Bookmark']");

	By noOfRecordsElement = By.xpath("//span[@class='ng-binding'][contains(text(),'Records')]");

	By bookmarkNameTextBox = By.xpath("//input[@id='bookmarkName']");

	By bookmarkSaveButton = By.xpath("//span/input[@id='Id'][@value='Save']");

	By bookmarkCancelButton = By.xpath("//span/input[@id='Id'][@value='Cancel']");

	By bookmarkSuccessNotification = By.xpath("//span[contains(text(),'Bookmark created')]");

	By bookmarkError = By.xpath("//span[@class='text-up ng-binding'][contains(text(),' There is a bookmark by that name already.')]");

	By bookmarkEditedNotification = By.xpath("//span[contains(text(),'Bookmark edited')]");

	By bookmarkDeletedNotification = By.xpath("/html/body/div[1]/ul[4]/li/span[3]");

	By discardBookmark = By.xpath("//*[@id='dialog_buttons']/input[@value='Discard']");

	
	// Bookmark validation
	public void createBookmark() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(noOfRecordsElement, "No of Records");
		oParameters.SetParameters("NoOfRecordsBeforeBM", get_field_value("No. of Records", noOfRecordsElement));

		if(IsDisplayed("Bookmark Button without any Bookmark", bookmarkButton))
		{
			click_button("Bookmark Button", bookmarkButton);
			waitFor(saveBookmarkLink, "Save Bookmark Link");
		}
		else
		{
			click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);
			waitFor(saveBookmarkLink, "Save Bookmark Link");
		}

		click_button("Save Book mark link", saveBookmarkLink);

		if(IsElementDisplayed("Create Bookmark Window", addBookmarkWindow))
			oReport.AddStepResult("Create Bookmark Window", "Create Bookmark Window Displayed", "PASS");
		else
			oReport.AddStepResult("Create Bookmark Window",	"Clicked on Save current page as a bookmark but Create Bookmark Window is not Displayed", "FAIL");

		enter_text_value("Bookmark Name", bookmarkNameTextBox,oParameters.GetParameters("BookmarkName")+System.currentTimeMillis());
		oParameters.SetParameters("NewBookmarkName", get_field_value("Bookmark Name", bookmarkNameTextBox));
		click_button("Save Button", bookmarkSaveButton);
		waitFor(bookmarkSuccessNotification, "Bookmark Created Notification");

		if(IsDisplayed("Bookmark Created Notification", bookmarkSuccessNotification))
			oReport.AddStepResult("Bookmark Created Successfully","Bookmark Created Successfully notification Displayed", "PASS");
		else
			oReport.AddStepResult("Bookmark Created Successfully","Bookmark created but Bookmark Created Successfully notification not Displayed", "FAIL");

		// Existing Bookmark validation

		if(IsDisplayed("Save Bookmark Link", saveBookmarkLink)) 
		{}
		else
			click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);

		By noOfBookmarks = By.xpath(".//*[@id='userPref']/div/ul/li");
		oParameters.SetParameters("bookmarksCount", String.valueOf(get_table_row_count(noOfBookmarks)));

		By currentbookmark = By.xpath("//*[@id='userPref']/div/ul/li/span[text()='" + oParameters.GetParameters("NewBookmarkName") + "']");

		oParameters.SetParameters("get_text_BM", get_field_value("Created Bookmark", currentbookmark));
		mouse_hover("Existing Bookmark", currentbookmark);

		if(IsDisplayed("Created Bookmark", currentbookmark))
			oReport.AddStepResult("Existing Bookmark",	"Existing Bookmark :"+oParameters.GetParameters("get_text_BM")+" Displayed", "PASS");
		else
			oReport.AddStepResult("Existing Bookmark",	"Bookmark created but that Bookmark not Displayed under bookmarks", "FAIL");

		click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);
	}

	
	// Book mark validation
	public void bookmarkValidation()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);
		fixed_wait_time(3);
		click_button("Save Bookmark link", saveBookmarkLink);
		enter_text_value("Bookmark Name", bookmarkNameTextBox,oParameters.GetParameters("BookmarkName")+System.currentTimeMillis());
		click_button("Save Button", bookmarkSaveButton);
		waitFor(bookmarkSuccessNotification, "Bookmark Created Notification");

		if(IsDisplayed("Bookmark Created", bookmarkSuccessNotification))
			oReport.AddStepResult("Bookmark Created Successfully","Bookmark Created Successfully notification Displayed", "PASS");
		else
			oReport.AddStepResult("Bookmark Created notification ",	"Bookmark created but Bookmark Created Successfully notification not Displayed", "FAIL");

		if(IsDisplayed("Save Bookmark Link", saveBookmarkLink))
		{}
		else
			click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);

		By noOfBookmarks = By.xpath(".//*[@id='userPref']/div/ul/li");
		
		oParameters.SetParameters("bookmarksCount", String.valueOf(get_table_row_count(noOfBookmarks)));
		oParameters.SetParameters("firstCreatedBM",	String.valueOf(Integer.parseInt(oParameters.GetParameters("bookmarksCount")) - 1));

		By previousBookmark = By.xpath("//*[@id='userPref']/div/ul/li/span[contains(text(),'"+ oParameters.GetParameters("NewBookmarkName") + "')]");

		waitFor(saveBookmarkLink, "Save Bookmark Link");
		click_button("Recently Created Bookmark", previousBookmark);
		fixed_wait_time(3);
		waitForIsInvisible(loadingIcon, "Loading Icon");
		waitFor(noOfRecordsElement, "No of Records");
		oParameters.SetParameters("NoOfRecordsAfterBM", get_field_value("No. of Records", noOfRecordsElement));

		if(oParameters.GetParameters("NoOfRecordsBeforeBM").equalsIgnoreCase(oParameters.GetParameters("NoOfRecordsAfterBM")))
		{
			waitFor(noOfRecordsElement, "No of Records");
			oReport.AddStepResult("Bookmark page", "Previously saved bookmark page is displayed", "PASS");
		}
		else
		{
			waitFor(noOfRecordsElement, "No of Records");
			oReport.AddStepResult("Bookmark page","Clicked on previously created bookmark but it doesn't navigated and that page is not Displayed",	"FAIL");
		}

		// Edit and Delete book mark icon check

		By bookmarkEdit = By.xpath("//span[text()='" + oParameters.GetParameters("NewBookmarkName")+"']/..//a[@style='display: inline;']/i[@class='left fa fa-pencil']");

		By bookmarkDelete = By.xpath("//span[contains(text(),'" + oParameters.GetParameters("NewBookmarkName")+"')]/../a[@style='display: inline;']/i[1]");

		click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);
		mouse_hover("Previous Bookmark", previousBookmark);

		if(IsDisplayed("Bookmark Edit Icon", bookmarkEdit) && IsDisplayed("Bookmark Delete Icon", bookmarkDelete))
			oReport.AddStepResult("Edit and Delete Icon","Mouse hoverd to created bookmark, That Bookmark Edit and Delete Icon Displayed", "PASS");
		else
			oReport.AddStepResult("Edit and Delete Icon","Mouse hoverd to created bookmark but Edit and Delete Icon is not Displayed", "FAIL");

		// Duplicate book mark validation

		click_button("Save Book mark link", saveBookmarkLink);
		enter_text_value("Bookmark Name", bookmarkNameTextBox, oParameters.GetParameters("get_text_BM"));
		click_button("Save Button", bookmarkSaveButton);

		if(IsDisplayed("Bookmark Error Message", bookmarkError)) 
		{
			oReport.AddStepResult("Duplicate Notification", "Duplicate Notification Displayed", "PASS");
			click_button("Cancel Button", bookmarkCancelButton);
		}
		else
		{
			oReport.AddStepResult("Duplicate Notification ","Tried to create a new bookmark with existing bookmark name but Duplicate Notification is not Displayed","FAIL");
			click_button("Cancel Button", bookmarkCancelButton);
		}

		// Editing book mark

		mouse_hover("Previous Bookmark", previousBookmark);
		click_button("Edit Bookmark", bookmarkEdit);
		enter_text_value_without_clear("Bookmark Name Text Box", bookmarkNameTextBox,oParameters.GetParameters("EditBookmarkValue"));
		click_button("Save Button", bookmarkSaveButton);
		waitFor(bookmarkEditedNotification, "Bookmark Edited Notification");

		if(IsDisplayed("Bookmark Edited Notification", bookmarkEditedNotification))
			oReport.AddStepResult("Bookmark Edited Notification", "Bookmark Edited Notification Displayed", "PASS");
		else
			oReport.AddStepResult("Bookmark Edited Notification","Bookmark name edited but Bookmark Edited Notification is not Displayed", "FAIL");

		if(IsDisplayed("Save Bookmark Link", saveBookmarkLink))
		{}
		else
			click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);

		// Deleting book mark

		mouse_hover("Previous Bookmark", previousBookmark);
		click_button("Delete Bookmark", bookmarkDelete);
		waitFor(discardBookmark, "Discard Bookmark Button");

		if(IsDisplayed("Delete Bookmark Popup", discardBookmark))
			oReport.AddStepResult("Delete Bookmark Popup", "Delete Bookmark Popup Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Bookmark Popup","Clicked on Delete bookmark but Delete Bookmark Popup not Displayed", "FAIL");

		click_button("Discard Button", discardBookmark);
		fixed_wait_time(3);

		if(IsDisplayed("Bookmark Deleted Nofitification", bookmarkDeletedNotification))
			oReport.AddStepResult("Bookmark Deleted Notification", "Bookmark Deleted Notification Displayed", "PASS");
		else
			oReport.AddStepResult("Bookmark Deleted Notification",	"Bookmark deleted but Bookmark Deleted Notification is not Displayed", "FAIL");

		// Creating book mark with deleted book mark Name

		if(IsDisplayed("Save Bookmark Link", saveBookmarkLink))
		{}
		else
			click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);

		click_button("Save Book mark link", saveBookmarkLink);
		enter_text_value("Bookmark Name", bookmarkNameTextBox,
		oParameters.GetParameters("get_text_BM") + oParameters.GetParameters("EditBookmarkValue"));
		click_button("Save Button", bookmarkSaveButton);
		waitFor(bookmarkSuccessNotification, "Bookmark Created Notification");

		if(IsDisplayed("Bookmark Created Notification", bookmarkSuccessNotification))
			oReport.AddStepResult("Bookmark Created Successfully",	"Bookmark created Successfully with Deleted bookmark Name ", "PASS");
		else
			oReport.AddStepResult("Bookmark Created Successfully", "Created bookmark with deleted bookmark name but Bookmark Created Successfully notification is not Displayed","FAIL");

		click_button("Bookmark Button with Bookmark", bookmarkButtonHavingBookmark);
	}

	
	By settingsButton = By.xpath("//button[@title='Settings']");

	By settingsWindow = By.xpath("//div[@class='xl-header truncate ng-binding'][contains(@title,'Settings')]");

	
	// Settings Validation
	public void settingsWindow()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Setting", settingsButton);

		if(IsElementDisplayed("Settings Window", settingsWindow))
			oReport.AddStepResult("Settings Window", "Settings window displayed", "PASS");
		else
			oReport.AddStepResult("Settings Window", "Clicked on settings button but Settings window is not displayed",	"FAIL");
		
		if(!IsDisplayed("Settings Window", settingsWindow))
			oParameters.SetParameters("Continued_Execution", "No");
	}

	
	By contractNameCheckBox = By.xpath("//li/input[@id='contractName'][@type='checkbox']");

	By settingsSaveButton = By.xpath("//input[@id='Id'][@value='Save']");

	By contractNameHeader = By.xpath("//div[@class='heading ng-scope'][contains(text(),'Contract Name')]");
	
	
	// Validation of temporary Changes in settings
	public void temporaryChangesInSettings1(String checkBoxName,By elementName)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By checkBox = By.xpath("//li/input[@id='"+checkBoxName+"'][@type='checkbox']");
		
		click_button(checkBoxName+" Check Box", checkBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed(checkBoxName+" Column", elementName))
			oReport.AddStepResult("Uncheck "+checkBoxName+" column", "Unchecked "+checkBoxName+" option in settings window but still it is displayed in Reports page","FAIL");
		else
			oReport.AddStepResult("Uncheck "+checkBoxName+" column", "Unchecked "+checkBoxName+" option in settings window that column is not displayed in Reports page","PASS");
	}

	
	// Validation of temporary Changes in settings
	public void temporaryChangesInSettings()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Contract Name Check Box", contractNameCheckBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Contract Name Column", contractNameHeader))
			oReport.AddStepResult("Uncheck Contracts Name column",	"Unchecked Contract Name option in settings window but still it is displayed in Reports page","FAIL");
		else
			oReport.AddStepResult("Uncheck Contracts Name column",	"Unchecked Contract Name option in settings window that column is not displayed in Reports page","PASS");
	}
	
	
	// Default Columns Validation
	public void defaultColumnsValidation1()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Contract Name Column", contractNameHeader))
			oReport.AddStepResult("Contract Name Column", "Contract Name Column Displayed", "PASS");
		else
			oReport.AddStepResult("Checked Contracts Name column", "Checked Contract Name option in settings window but still it is not displayed in Reports page",	"FAIL");
	}

	
	// Default Columns Validation
	public void defaultColumnsValidation()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Contract Name Column", contractNameHeader))
			oReport.AddStepResult("Contract Name Column", "Contract Name Column Displayed", "PASS");
		else
			oReport.AddStepResult("Checked Contracts Name column", "Checked Contract Name option in settings window but still it is not displayed in Reports page",	"FAIL");
	}

	
	By saveMyDefaultsRadio = By.xpath("//label[contains(.,'Save current settings as My Default')]/input");

	By resetMyDefaultsRadio = By.xpath("//label[contains(.,'Reset to My Default')]/input");

	By resetSystemDefaultsRadio = By.xpath("//label[contains(.,'Reset to System Default')]/input");

	By contractManagementSuite = By.xpath("//li/a[contains(text(),'Contract Management')]");

	
	// Default Changes in settings validation
	public void defaultChangesInSetting()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Contract Name Check Box", contractNameCheckBox);
		click_button("Save as My Defaults", saveMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Contract Name Column", contractNameHeader))
			oReport.AddStepResult("Uncheck Contracts Name column",	"Unchecked Contract Name option in settings window but still it is displayed in Reports page",	"FAIL");
		else
			oReport.AddStepResult("Uncheck Contracts Name column",	"Unchecked Contract Name option in settings window that column is not displayed in Reports page", "PASS");

		click_button("Settings", settingsButton);
		click_button("Contract Name Check Box", contractNameCheckBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(contractNameHeader, "Reports Table");

		if(IsDisplayed("Contract Name Column", contractNameHeader))
			oReport.AddStepResult("Contract Name Column", "Contract Name Column Displayed", "PASS");
		else
			oReport.AddStepResult("Checked Contracts Name column","Checked Contract Name option in settings window but still it is not displayed in Reports page","FAIL");

		navigate_to("Navigate Contract Management Suite", "Contract Plugin", contractManagementSuite, contractsPlugIn);

		navigate_to("Navigate to Analytics Suite", "Contract Name Column", analyticsSuite, contractNameHeader);
	}

	
	By reportsTable = By.xpath("//div[@class='adjust-height default-table']/table/tbody");

	
	// Reset to My Defaults Validation
	public void resetToDefaults()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Reset to My Defaults", resetMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		fixed_wait_time(5);
		waitForIsInvisible(loadingIcon, "Loading Icon");		

		if(IsDisplayed("Contract Name Column", contractNameHeader)) 
		{
			fixed_wait_time(2);
			waitFor(reportsTable, "Reports Table");
			oReport.AddStepResult("Uncheck Contracts Name column ",	"Checked Contract Name option in settings window and saved changes temporarily then settings saved as Reset to My Default but still Contract Name column displayed in reports page ","FAIL");
		}
		else
		{
			fixed_wait_time(2);
			waitFor(reportsTable, "Reports Table");
			oReport.AddStepResult("Contract Name Column", "Contract Name Column Not Displayed", "PASS");
		}

		click_button("Settings", settingsButton);
		click_button("Reset to System Defaults", resetSystemDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		fixed_wait_time(2);
		waitFor(reportsTable, "Reports Table");

		if(IsDisplayed("Contract Name Column", contractNameHeader))
		{
			fixed_wait_time(2);
			waitFor(reportsTable, "Reports Table");
			oReport.AddStepResult("Contract Name Column", "Contract Name Column Displayed", "PASS");
		}
		else
		{
			fixed_wait_time(2);
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Checked Contracts Name column","Checked Contract Name option in settings window but still it is not displayed in Reports page","FAIL");
		}

		click_button("Settings", settingsButton);
		click_button("Save as My Defaults", saveMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");
	}

	
	By fullScreenIcon = By.xpath("//span[@title='Full Screen']");

	By exitFullScreen = By.xpath("//span[@title='Exit Full Screen']");

	
	// Full screen Validation
	public void fullScreenValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Full Screen", fullScreenIcon);
		waitFor(noOfRecordsElement, "No of Records");

		if(IsDisplayed("Exit Full Screen Icon", exitFullScreen)) 
		{
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Full Screen", "Full screen maximized", "PASS");
		}
		else
		{
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Full Screen", "Clicked on full screen icon but that page not maximized", "FAIL");
		}

		click_button("Exit Screen", exitFullScreen);

		if(IsDisplayed("Full Screen Icon", fullScreenIcon)) 
		{
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Normal Screen", "Normal screen displayed", "PASS");
		}
		else
		{
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Full Screen exit ", "Clicked on exit full screen icon but normal page not displayed","FAIL");
		}
	}

	
	By contractListingReport = By.xpath("//div[@class='styled-dropdown black-text-when-disabled ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Contract Listing Report')]");

	By contractListingReportDC = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Contract Listing Report')]");
	
	By reportsDropDown = By.xpath("//div[@id='title-bar']//a[contains(@class,'btn view-bg-text btn-new-light btn-lg')]");

	
	// This method will navigate to other reports
	public void navigatingToOtherReport(String reportType1,String reportType2) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		By totalColumnsBeforeNavigation = By.xpath("//div[@id='report-table-parent']//thead/tr/td");
		oParameters.SetParameters("NoOfColumnsBeforeNavigation",String.valueOf(get_table_row_count(totalColumnsBeforeNavigation)));
		select_Report_1(reportType1);

		select_Report_1(reportType2);
		createReport();
		
		longWaitFor(createReportButton, "Create Report Button");

		By totalColumnsAfterNavigation = By.xpath("//div[@id='report-table-parent']//thead/tr/td");
		
		oParameters.SetParameters("NoOfColumnsAfterNavigation",String.valueOf(get_table_row_count(totalColumnsAfterNavigation)));

		if (Integer.parseInt(oParameters.GetParameters("NoOfColumnsBeforeNavigation")) == Integer.parseInt(oParameters.GetParameters("NoOfColumnsAfterNavigation")))
			oReport.AddStepResult(reportType2, ""+reportType2+"s displayed", "PASS");
		else
			oReport.AddStepResult(reportType2,"Navigated back to "+reportType2+" from "+reportType1+" but, number of columns presented in "+reportType2+" before navigation those columns are not displayed after navigation ","FAIL");
	}
	
	
	By multiSelectDropDown = By.xpath("//ul[@class='dropdown-menu multiSelectDropdown']");
		
	
	// This method for selecting filter drop down Multiple check boxes 
	public void selectMultipleCheckBoxes(String multiSelectFields)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(multiSelectDropDown, "Multi Select DropDown");			
		String checkBoxNames [] = multiSelectFields.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for (int i=0; i<=numberOfItems-1; i++)
		{
			String elements = checkBoxNames[i];
				 
			By multiSelectCheckBox = By.xpath("//ul[@class='dropdown-menu multiSelectDropdown']//label[contains(.,'"+elements+"')]/input[@type='checkbox']");
			scroll(elements, multiSelectCheckBox);
			click_button("Multi Select Check box",multiSelectCheckBox);
		}			
		click_button("Multi Select Window Save", multiSelectWindowSave);
	}

	
	// 120 Day Reports VR
	public void Reports120Day_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigate_to_Analytics();
		select_Report("120 Day Report");
		createReportButton();
		report_120Day_Contracts();
		filterReports();
		comparing120DayDownloadedReports();
		resultsPerPage("50");
		resultsPerPage("100");
		resultsPerPage("200");
		createBookmark();
		clearFilters();
		workflowStatusReports("Active,Draft,Inactive");
		bookmarkValidation();
		settingsWindow();
		temporaryChangesInSettings();
		portalLogout();
		deleteCookies();
		login("EDIT");
		changePricingEngine();
		navigate_to_Analytics();
		select_Report("120 Day Report");
		createReportButton();
		defaultColumnsValidation();
		defaultChangesInSetting();
		resetToDefaults();
		fullScreenValidation();
		navigatingToOtherReport("Contract Listing Report","120 Day Report");
		logout();
	}

	
	// Claim Repricing Report VR
	
	By processDateDD = By.xpath("//li[@id='af']//div[text()='Process Date']");

	By claimRepricingReport = By.xpath("//div[@class='styled-dropdown black-text-when-disabled ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Claim Repricing Report')]");

	By claimRepricingReportDC = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Claim Repricing Report')]");
	
	By dateRange = By.xpath("//*[@id='af']//div[@class='filter-value pull-left ng-scope ng-binding']");
	
	By startDate = By.xpath("//input[@id='date-from']");
	
	By endDate = By.xpath("//input[@id='date-to']");

	public By createReportDD = By.xpath("//button[@class='btn btn-primary btn-create-report dropdown-toggle']");

	By disabledCloudButton = By.xpath("//*[@disabled='disabled'][@icon='cloud-download']");

	By repriceClaimsElement = By.xpath("//li[@title='Minimum of one filter must be set to create a reprice job.']");

	By serviceFacilityElement = By.xpath("//li/a[contains(text(),'Service Facility')]");

	By filtersDropDown = By.xpath("//a[@class='filter-label hand-cursor pull-left ng-scope']");

	By multiOperandButton = By.xpath("//*[@data-template='selectMultiOperand']");

	By multiSelectOption = By.xpath("//li[@class='ng-scope']//a[contains(text(),'Multiselect')]");

	By multiSelectWindow = By.xpath("//*[@class='title'][contains(text(),'Create filter with multiple criteria')]");

	By multiSelectWindowSearch = By.xpath("//input[@title='Type search criteria and click enter to search']");

	By selectAll = By.xpath("//span[contains(text(),'Select All')]");

	By deSelectAll = By.xpath("//span[contains(text(),'Deselect All')]");

	By clearResultsButton = By.xpath("//input[@value='Clear Results']");

	By addSelectedButton = By.xpath("//input[@value='Add Selected']");

	By selectedItemsHeader = By.xpath("//li[@class='list-header']");

	
	// Navigate to Claim Repricing Report
	public void navigateToClaimRepricingReport()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		navigate_to_Analytics();
		
		if(IsElementDisplayed("Select a Report", selectAReportButton)) 
		{
			click_button("Select a Report DropDown", selectAReportButton);
			click_button("Claim Repricing Report", claimRepricingReport);
			waitFor(createReportButton, "Create Report Button");

			if(IsElementDisplayed("Create Report Button", createReportButton))
				oReport.AddStepResult("Claim Repricing Report", "Claim Repricing Report Page displayed without any error", "PASS");
			else
				oReport.AddStepResult("Claim Repricing Report", "Selected Claim Repricing Report form select a report drop down but that Page is not displayed", "FAIL");
	
/*			if(IsElementDisplayed("Process Date DD", processDateDD))
				oReport.AddStepResult("Process Date", "Process Date Displayed", "PASS");
			else
				oReport.AddStepResult("Process Date", "Claim Repricing Report page displayed but process date is not displayed as a default filter ", "FAIL");*/
		} 
		else
			oReport.AddStepResult("select A Report Button", "select A Report Button not displayed", "INFO");
	}
	
	
	// Process Date Records
	public void reportsWithProcessDate()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Process Date");
		
	//	click_button("Date Range", dateRange);
		click_button("Start Date", startDate);

		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
			enter_text_value_without_clear("Start Date", startDate, get_specified_date(-6));
		else if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT"))
			enter_text_value_without_clear("Start Date", startDate, get_specified_date(-10));
		else
			enter_text_value_without_clear("Start Date", startDate, get_specified_date(-1000));
		
		enter_text_value_without_clear("End Date", endDate, get_specified_date(0));

		performKeyBoardAction("ENTER");
		createReport();

		if(IsElementEnabled("Reprice Button", createReportDD)&& IsDisplayed("No. Of Records", noOfRecordsElement)&& IsElementEnabled("Download CSV Button", downloadCSVButton)) 
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Reprice and Download Buttons", "Reprice and Download Buttons Enabled", "PASS");
		} 
		else
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Reprice and Download Buttons","Reports filterd with Process Date but that records are not displayed, reprice button is not enabled and download icon aslo not enabled.",	"FAIL");
		}
	}

	
	// Clearing Filters
	public void clearFilters() 
	{
		click_button("Clear Filters Button", clearFiltersButton);
	}

	
	// Add Filter
	public void addFilter(String filtername)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Add Filter Button", addFilterElement);
		click_button("Filters DropDown", filtersDropDown);
		
		By selectField = By.xpath("//li/a[text()='"+filtername+"']");
		scroll(filtername, selectField);
		click_button(filtername, selectField);
	}

	
	// Creating Report
	public void createReport() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Create Report", createReportButton);
		fixed_wait_time(10);		
		waitForIsInvisible(loadingIcon, "Loading Icon");
		waitFor(createReportButton, "Create Report Button");
		
		if(!IsDisplayed("Content header", firstHeaderPath))
		{
			click_button("Create Report", createReportButton);
			fixed_wait_time(10);	
		}			
	}
	

	// Clearing and Creating Reports
	public void unFilteredReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clearFilters();
		createReport();

		if(IsElementEnabled("Reprice Button", createReportDD)&& IsDisplayed("No. Of Records", noOfRecordsElement)&& IsDisplayed("Disabled Download Button", disabledCloudButton)) 
		{
			fixed_wait_time(2);
			oReport.AddStepResult("Results after clear filters", "Results after clear filters Displayed", "PASS");
		}
		else
			oReport.AddStepResult("Clearing filters","Cleared filters but complete report not displayed with Reprice button enabled and download icon is not disabled ","FAIL");
	}
	

	// Reprice Claims Check
	public void repriceClaimsCheck() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Create Reports DropDown", createReportDD);
		mouse_hover("Reprice Claims", repriceClaimsElement);
		oParameters.SetParameters("RepriceTooltip", getToolTipValue(repriceClaimsElement));

		if(oParameters.GetParameters("RepriceTooltip").equals("Minimum of one filter must be set to create a reprice job."))
			oReport.AddStepResult("Reprice Claims Title", "Reprice Claims Title Displayed", "PASS");
		else
			oReport.AddStepResult("Reprice Claims Title","Mouse hovered to Reprice Claims but Minimum one filter must be set to create a reprice job tag is not displayed ","FAIL");

		performKeyBoardAction("ESCAPE");
	}

	
	// Service Facility Multi Select window
	public void serviceFacilityMultiSelectWindow() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Service Facility");
		click_button("Multi Operand Button", multiOperandButton);
		click_button("Multi Select Option", multiSelectOption);

		if(IsElementDisplayed("Multi Select Option Window", multiSelectWindow)) 
		{
			fixed_wait_time(2);
			oReport.AddStepResult("Multi Select Option Window", "Multi Select Option Window Displayed", "PASS");
		}
		else
			oReport.AddStepResult("Multi Select Option Window",	"choosed service facility and selected multi select option from operators drop down but multi select service facility window not displayed ","FAIL");
	}

	
	By noResultMessage = By.xpath("//li[@class='no-results ng-binding'][contains(.,'No result')]");

	By firstResult = By.xpath("//ul[@id='facility-select']//li[1]//label");

	By cancelSearchButton = By.xpath("//div[@class='fa fa-times-circle close-icon']");

	By firstCheckBox = By.xpath("//ul[@id='facility-select']//li[1]//label/input");

	By multiFacilityWindowCancel = By.xpath("//input[@class='btn btn-default'][@value='Cancel']");

	
	// Search and checking Results
	public void multiSelectWindowValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		performKeyBoardAction("ENTER");
		fixed_wait_time(3);
		waitFor(firstResult, "Service Facility Multi select window result");

		if(IsDisplayed("Search Result", firstResult))
		{
			click_button("Select All Option", selectAll);

			if(IsElementSelected("First Check Box", firstCheckBox))
				oReport.AddStepResult("Multi Search all facilities", "Multi Search all facilities Selected", "PASS");
			else
				oReport.AddStepResult("Multi Search all facilities","Clicked on select all button but all facilities not selected", "FAIL");

			// deselecting all

			click_button("Deselect All", deSelectAll);

			if (IsElementSelected("First Check Box", firstCheckBox))
				oReport.AddStepResult("Deselect Search all facilities",	"Clicked on Deselect all button but still all facilities are selected", "FAIL");
			else
				oReport.AddStepResult("Multi Search all facilities", "Multi Search all facilities Not Selected","PASS");

			// clear results

			click_button("Clear Results", clearResultsButton);

			if (IsDisplayed("No results message", noResultMessage))
				oReport.AddStepResult("Search List", "Search List Cleared", "PASS");
			else
				oReport.AddStepResult("Clear Search List", "Clicked on Clear result but all list is not cleared ",	"FAIL");

			// Searching with last element

			click_button("Multi search Window", multiSelectWindowSearch);
			performKeyBoardAction("ENTER");

			By totalSearchResult = By.xpath("//ul[@id='facility-select']//li//label");

			oParameters.SetParameters("noOfSearchResults", String.valueOf(get_table_row_count(totalSearchResult)));

			firstCheckBox = By.xpath("//ul[@id='facility-select']//li[1]//label");

			By lastSearchResult = By.xpath("//ul[@id='facility-select']//li[" + oParameters.GetParameters("noOfSearchResults") + "]//label");

			oParameters.SetParameters("lastSearchResult", get_field_value("Search Result", lastSearchResult));
			enter_text_value("Mulitiple Search Window Search", multiSelectWindowSearch,	oParameters.GetParameters("lastSearchResult"));
			performKeyBoardAction("ENTER");
			click_button("First Result", firstResult);
			click_button("Add Selected", addSelectedButton);

			if(IsDisplayed("Selected Items Header", selectedItemsHeader))
				oReport.AddStepResult("Selected Items", "Selected Items Added", "PASS");
			else
				oReport.AddStepResult("Add Selected Items",	"Clicked on Add Selected result but those selected facilities are not added ", "FAIL");

			// Saving Selected result

			By saveButton = By.xpath("//input[@class='btn btn-primary'][@value='Save']");

			By savedFacilities = By.xpath("//div[@class='filter-value pull-left hide-overflow ng-scope ng-binding']");

			click_button("Save Button", saveButton);
			waitFor(createReportButton, "Create Report Button");

			if(IsDisplayed("Saved Facilities", savedFacilities))
				oReport.AddStepResult("Saved Facilities", "Saved Facilities Displayed", "PASS");
			else
				oReport.AddStepResult("Saved Facilities"," After adding selected result clicked on Save but facility is not saved and number of facilities selected not displayed in service facility test field",	"FAIL");

			// clicking on input field and checking multi select window

			mouse_hover("Saved Facilities", savedFacilities);
			fixed_wait_time(2);
			click_button("Facility Input Field", savedFacilities);

			if(IsElementDisplayed("Multi Select Window", multiSelectWindow))
				oReport.AddStepResult("Multi Select Edit Window", "Multi Select Edit Window Displayed", "PASS");
			else
				oReport.AddStepResult("Multi Select Option Window",	"clicked on saved service facility but multi select service facility window not displayed ","FAIL");

			// Deselect all

			click_button("Deselect All", deSelectAll);

			if(IsElementSelected("First Check Box", firstCheckBox))
				oReport.AddStepResult("Deselect Search all facilities",	"Clicked on Deselect all button but still all facilities are selected", "FAIL");
			else
				oReport.AddStepResult("Search Facilities", "Search Facilities Not Selected", "PASS");

			firstCheckBox = By.xpath("//ul[@id='facility-select']//li[1]//label/input");

			// Select all checking

			click_button("Select All", selectAll);

			if(IsElementSelected("First Check Box", firstCheckBox))
				oReport.AddStepResult("Search Facilities", "Search Facilities Selected", "PASS");
			else
				oReport.AddStepResult("Multi Search all facilities","Clicked on select all button but all facilities not selected", "FAIL");

			// Clearing result and checking

			click_button("Clear Results", clearResultsButton);

			if(IsDisplayed("No results message", noResultMessage))
				oReport.AddStepResult("Search List", "Search List Cleared", "PASS");
			else
				oReport.AddStepResult("Clear Search List", " Clicked on clear results but searched result not Cleared",	"FAIL");

			// Searching with First search result

			click_button("Clearing Multi search Window Result", cancelSearchButton);
			click_button("Multi search Window", multiSelectWindowSearch);
			performKeyBoardAction("ENTER");
			waitFor(firstResult, "Service Facility Multi select window result");
			oParameters.SetParameters("FirstSearchResult", get_field_value("Search Result", firstResult));
			enter_text_value("Mulitiple Search Window Search", multiSelectWindowSearch,	oParameters.GetParameters("FirstSearchResult"));
			performKeyBoardAction("ENTER");
			waitFor(firstResult, "Service Facility Multi select window result");
			click_button("First Result", firstCheckBox);
			click_button("Add Selected", addSelectedButton);
			click_button("Save Button", saveButton);
			waitFor(savedFacilities, "Added Facilities");

			if(IsDisplayed("Saved Facilities", savedFacilities))
				oReport.AddStepResult("Saved Facilities", "Saved Facilities Displayed", "PASS");
			else
				oReport.AddStepResult("Saved Facilities", " After adding selected result clicked on Save but facility is not saved and number of facilities selected not displayed in service facility test field",	"FAIL");

			// Creating reports for search results

			createReport();

			if(IsDisplayed("No Filters Result Message", noFilterResultMessage))
				oReport.AddStepResult("Facility Reports ", "Facility Reports No Facility Results Returned", "INFO");
			else if(IsDisplayed("Apply Filters", applyFilterMessage))
				oReport.AddStepResult("Saved Facilities", " Selected results are added to service facility test field then clicked on create report but selected service facility reports are not displayed","FAIL");
			else 
			{
				By reportResultFirstColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']//span[@ng-click=\"expandRow('0' , row, 'serviceFacilityName')\"]");

				oParameters.SetParameters("firstColumn",get_field_value("First column result Text", reportResultFirstColumn));

				if (oParameters.GetParameters("firstSearchResult").equalsIgnoreCase(oParameters.GetParameters("firstColumn"))) 
				{
					fixed_wait_time(4);
					oReport.AddStepResult("Created report Result", "Created report Result Displayed", "PASS");
				}
				else
					oReport.AddStepResult("Saved Facilities"," Selected results are added to service facility test field then clicked on create report but selected service facility reports are not displayed","FAIL");
			}
			clearFilters();
		}
		else
		{
			oReport.AddStepResult("Search Result","Service Facility Multiselect window not displaying any service facility records ", "FAIL");
			click_button("Multi Facility Window Cancel", multiFacilityWindowCancel);

			if(IsDisplayed("Clear Filters Button", clearFiltersButton))
				click_button("Clear Filters Button", clearFiltersButton);
		}
	}

	
	// Claim ID Filter Reports
	public void claimIDFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(claimIDColumn, "Reports Table");
		click_button("Last Page Button", lastPageButton);
		waitFor(claimIDColumn, "Reports Table");
		oParameters.SetParameters("ClaimIDBeforeFilter", get_field_value("Claim ID", claimIDColumn));
		addFilter("Claim Id");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("ClaimIDBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(claimIDColumn, "Reports Table");

		if(IsDisplayed("Claim ID Column", claimIDColumn)) 
		{
			oParameters.SetParameters("ClaimIDAfterFilter", get_field_value("Claim ID", claimIDColumn));

			if (oParameters.GetParameters("ClaimIDBeforeFilter").equals(oParameters.GetParameters("ClaimIDAfterFilter")))
				oReport.AddStepResult("Claim ID Reports ", "Claim ID filter Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Claim ID Reports ", "Reports are filtered based on Claim ID  but those reports are not displayed", "FAIL");
		} 
		else
			oReport.AddStepResult("Claim ID","Navigated to  reports last page then reports are filtered with Claim ID but that respective records are not displayed from first page ","FAIL");

		// Checking other page records

		if(IsDisplayed("Second Page", secondPageButton))
			click_button("Second Page", secondPageButton);
		else 
		{}
		
		waitFor(claimIDColumn, "Reports Table");
		oParameters.SetParameters("ClaimIDAfterFilter1stPage", get_field_value("Claim ID", claimIDColumn));

		if(oParameters.GetParameters("ClaimIDBeforeFilter").equals(oParameters.GetParameters("ClaimIDAfterFilter1stPage"))) 
		{
			waitFor(claimIDColumn, "Reports Table");
			oReport.AddStepResult("Claim ID Reports", "Claim ID respective page records are Displayed", "PASS");
		}
		else
			oReport.AddStepResult("Claim ID Reports","Reports are filtered based on Claim ID  but those reports are not displayed", "FAIL");

		navigatingToLastandPrevPages("ClaimIDLastPage", "ClaimIDPrevPage", "Claim ID", claimIDColumn);

		if(oParameters.GetParameters("ClaimIDBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ClaimIDLastPage"))&& oParameters.GetParameters("ClaimIDBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ClaimIDPrevPage")))
			oReport.AddStepResult("Calim ID Reports", "Claim ID Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Claim ID Reports","Reports are filtered based on Claim ID but those reports are not displayed ", "FAIL");

		clearFilters();
	}

	
	By claimHistoryElement = By.xpath("//li/a[contains(text(),'Claim History')]");

	public By historyCurrentElement = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),'Current')]");

	By claimHistoryColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'isLatest')\"]");

	By claimHistoryInputField = By.xpath("//div[@title='Current']");

	By historyPastElement = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),'Past')]");

	
	// Claim History Filter Reports
	public void claimHistoryReports()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Claim History Current reports

		addFilter("Claim History");
		click_button("History Current Option", historyCurrentElement);
		createReport();
		oParameters.SetParameters("HistoryCurrent", get_field_value("Claim History type", claimHistoryColumn));

		if(oParameters.GetParameters("HistoryCurrent").equals(oParameters.GetParameters("ClaimCurrent")))
			oReport.AddStepResult("Claim Histroy Filter Result", "Claim Histroy Filter Result Displayed", "PASS");
		else
			oReport.AddStepResult("Claim Histroy Filter Result","Reports are filtered based on Claim Histroy type as Current but those Results are not displayed",	"FAIL");

		// Claim History Past reports

		click_button("Claim History Input Field", claimHistoryInputField);
		click_button("Claim History Past Element", historyPastElement);
		createReport();
		oParameters.SetParameters("HistoryPast", get_field_value("Claim History type", claimHistoryColumn));
		fixed_wait_time(3);
		
		if(oParameters.GetParameters("HistoryPast").equals(oParameters.GetParameters("ClaimPast")))
			oReport.AddStepResult("Claim Histroy Filter Result", "Claim Histroy Filter Result Displayed", "PASS");
		else
			oReport.AddStepResult("Claim Histroy Filter Result", "Reports are filtered based on Claim Histroy type as Past but those Results are not displayed","FAIL");

		By pastInputField = By.xpath("//div[@title='Past']");

		By inputFieldCancel = By.xpath("//div[@class='open']//div[@class='pull-right hand-cursor ng-binding'][contains(text(),'Cancel')]");

		click_button("Claim History Input Field", pastInputField);
		click_button("Claim History Input Cancel Button", inputFieldCancel);

		if(IsDisplayed("Claim History Input Cancel Button", inputFieldCancel))
			oReport.AddStepResult("Claim History Input DropDown","Clicked on Claim History input Cancel button but still Claim History Input DropDown is Displayed","FAIL");
		else
			oReport.AddStepResult("Claim History Input DropDown", "Claim History Input DropDown Not Displayed", "PASS");

		clearFilters();
	}

	
	By claimTypeElement = By.xpath("//li/a[contains(text(),'Claim Type')]");

	By claimTypecolumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'claimType')\"]");

	By claimInstitutionalInputField = By.xpath("//div[@title='Institutional'][contains(text(),'Institutional')]");


	
	// Claim Type Filter Reports
	public void claimTypeReports(String claimType) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Institutional Claim and Professional Claim

		By claimTypeElement = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),'"+claimType+"')]");
		
		addFilter("Claim Type");
		click_button("Claim Institutional Option", claimTypeElement);
		createReport();
		oParameters.SetParameters("InstitutionalValue", get_field_value("Claim type", claimTypecolumn));

		if(oParameters.GetParameters("InstitutionalValue").equals(claimType))
			oReport.AddStepResult("Institutional Claim Results", ""+claimType+" Claim Results Displayed", "PASS");
		else
			oReport.AddStepResult("Institutional Claim Results","Reports are filtered based on "+claimType+" Claim but those results are not Displayed", "FAIL");		
	}

	
	By repriceStatusElement = By.xpath("//li/a[contains(text(),'Reprice Status')]");

	By repriceStatus0Option = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),0)]");

	By repriceStatusColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'repriceStatus')\"]");

	By repriceStatusInputField = By.xpath("//div[@title='0'][contains(text(),'0')]");

	By repriceStatus1Option = By.xpath("//ul[@ng-hide='af.isMultiSelectField']//li[@class='filter-field ng-scope']/a[contains(text(),'1')]");

	
	// Reprice Status Filter Reports
	public void repriceStatusReports(String repriceStatus) 
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = repriceStatus.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for (int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}
			
			createReport();
			addFilter("Reprice Status");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(repriceStatusColumn, "Reports Table");
			oParameters.SetParameters("RepriceStatusValue", get_field_value("Reprice Status", repriceStatusColumn));

			if(oParameters.GetParameters("RepriceStatus").contains(oParameters.GetParameters("RepriceStatusValue")))
				oReport.AddStepResult("Reprice Status Zero Results", "Reports are filtered based on Reprice Status, verified that Reprice Status results displayed", "PASS");
			else
				oReport.AddStepResult("Reprice Status Zero Results","Reports are filtered based on Reprice Status "+word+" but those reports are Not Displayed", "FAIL");
			
			clearFilters();
		}
	}
	
	
    By coordinationofBenefitsColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'cobRank')\"]");
    
	
    // Coordination of Benefits Filter reports
    public void coordinationofBenefitsFilterReports(String coordinationofBenefits)
    {
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = coordinationofBenefits.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for (int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}
			
			createReport();
			addFilter("Coordination of Benefits");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(coordinationofBenefitsColumn, "Reports Table");
			oParameters.SetParameters("CoordinationofBenefitsValue", get_field_value("Coordination of Benefits ", coordinationofBenefitsColumn).toUpperCase());

			if(oParameters.GetParameters("CoordinationofBenefitsVal").contains(oParameters.GetParameters("CoordinationofBenefitsValue")))
				oReport.AddStepResult("Coordination of Benefits Reports", "Coordination of Benefits Results Displayed", "PASS");
			else
				oReport.AddStepResult("Coordination of Benefits Reports","Reports are filtered based on Coordination of Benefits "+word+" but those reports are not displayed", "FAIL");
			
			clearFilters();
		}
    }
	
    
	By claimStatusCheckBox = By.xpath("//*[@id='claimStatusDesc']");

	By claimStatusElement = By.xpath("//li/a[contains(text(),'Claim Status')]");

	By multiSelectWindowSave = By.xpath("//ul[@class='dropdown-menu multiSelectDropdown']//input[@value='Save']");

	By claimStatusColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'claimStatusDesc')\"]");

	By claimStatusCanceled = By.xpath("//label[contains(text(),'Canceled')]/input");

	By claimStatusDenied = By.xpath("//label[contains(text(),'Denied')]/input");

	By claimStatusSubmitted = By.xpath("//label[contains(text(),'Submitted')]/input");

	By claimStatusTransmitted = By.xpath("//label[contains(text(),'Transmitted')]/input");

	By claimStatusVoided = By.xpath("//label[contains(text(),'Voided')]/input");

	
	// Claim Status Filter Reports
	public void claimStatusReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Transmitted Claim Status

		click_button("Settings Button", settingsButton);
		click_button("Claim Status Check Box", claimStatusCheckBox);
		click_button("Settins Save Button", settingsSaveButton);
		addFilter("Claim Status");
		click_button("Claim Status Transmitted", claimStatusTransmitted);
		click_button("Claim Status Input Save", multiSelectWindowSave);
		createReport();
		oParameters.SetParameters("ClaimStatusTransmitted", get_field_value("Claim Status", claimStatusColumn));

		if(oParameters.GetParameters("ClaimStatusTransmitted").equals(oParameters.GetParameters("ClaimTransmitted"))) 
		{
			waitFor(claimStatusColumn, "Reports Table");
			oReport.AddStepResult("Claim Status Transmitted", "Claim Status Transmitted Displayed", "PASS");
		}
		else
		{
			waitFor(claimStatusColumn, "Reports Table");
			oReport.AddStepResult("Claim Status Transmitted","Reports are filtered based on Claim Status Transmitted but those reports are Not Displayed","FAIL");
		}
		clearFilters();
		addFilter("Claim Status");
		click_button("Claim Status Canceled", claimStatusCanceled);
		click_button("Claim Status Denied", claimStatusDenied);
		click_button("Claim Status Submitted", claimStatusSubmitted);
		click_button("Claim Status Transmitted", claimStatusTransmitted);
		click_button("Claim Status Voided", claimStatusVoided);
		click_button("Claim Status Input Save", multiSelectWindowSave);
		createReport();
		waitFor(claimStatusColumn, "Reports Table");
		oParameters.SetParameters("ClaimStatusAllInputResult", get_field_value("Claim Status", claimStatusColumn));

		if(oParameters.GetParameters("ClaimAllStatus").contains(oParameters.GetParameters("ClaimStatusAllInputResult"))) 
		{
			waitFor(claimStatusColumn, "Reports Table");
			oReport.AddStepResult("Claim Status All Input Result", "Claim Status All Input Result Displayed", "PASS");
		}
		else
		{
			waitFor(claimStatusColumn, "Reports Table");
			oReport.AddStepResult("Claim Status All Input Result","Reports are filtered based on Claim Status but those reports are not Displayed", "FAIL");
		}
		clearFilters();
	}

	
	By billTypeElement = By.xpath("//li/a[contains(text(),'Bill Type')]");

	public By billTypeOperators = By.xpath("//a[@class='pull-left operand ng-scope ng-binding'][@data-trigger='click']");

	By billTypeContainsOperator = By.xpath("//a[contains(text(),'+ Contains')]");

	By billTypeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'billType')\"]");

	By billTypeHeader = By.xpath("//*[@id='report-table-parent']//table/thead/tr/td//div[contains(text(),'Bill Type')]");

	By billTypeOperator = By.xpath("//*[@id='af1']/div/a[3][@data-trigger='click']");

	By billTypeExactOperator = By.xpath("//a[contains(text(),'= Exact')]");

	By billTypeNotExactOperator = By.xpath("//a[contains(text(),' Not Exact')]");

	By billTypeDoesNotContain = By.xpath("//a[contains(text(),'- Does Not Contain')]");

	
	// Bill Type Filter Reports
	public void billTypeReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Bill Type with Contains

		addFilter("Bill Type");
		click_button("Bill Type Operators Button", billTypeOperators);
		click_button("Bill Type Contains Operator", billTypeContainsOperator);
		enter_text_value("Filters Input Text Box", filtersTextBox, oParameters.GetParameters("BillTypeValue"));
		performKeyBoardAction("ENTER");
		createReport();
		scroll_to_element(billTypeHeader, createReportButton);
		oParameters.SetParameters("BillTypeContains", get_field_value("Bill Type", billTypeColumn));

		if(oParameters.GetParameters("BillTypeContains").equals(oParameters.GetParameters("BillTypeValue")))
			oReport.AddStepResult("Bill Type Contains Result", "Bill Type Contains Result Displayed", "PASS");
		else
			oReport.AddStepResult("Bill Type Contains Result",	"Reports are filtered based on Bill Type Contains but those reports are not Displayed", "FAIL");

		// Bill Type with Exact

		click_button("Bill Type Operators Button", billTypeOperator);
		click_button("Bill Type Exact Operator", billTypeExactOperator);
		createReport();
		oParameters.SetParameters("BillTypeExact", get_field_value("Bill Type", billTypeColumn));

		if(oParameters.GetParameters("BillTypeExact").equals(oParameters.GetParameters("BillTypeValue"))) 
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Bill Type Exact Result", "Bill Type Exact Result Displayed", "PASS");
		}
		else
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Bill Type Exact Result",	"Reports are filtered based on Bill Type Exact but those reports are not Displayed", "FAIL");
		}
		
		// Bill Type With Not Exact

		click_button("Bill Type Operators Button", billTypeOperator);
		click_button("Bill Type Not Exact Operator", billTypeNotExactOperator);
		createReport();
		oParameters.SetParameters("BillTypeNotExact", get_field_value("Bill Type", billTypeColumn));

		if(oParameters.GetParameters("BillTypeNotExact").equals(oParameters.GetParameters("BillTypeValue")))
			oReport.AddStepResult("Bill Type Not Exact Result",	"Reports are filtered based on Bill Type not Exact but those reports are not Displayed", "FAIL");
		else
			oReport.AddStepResult("Bill Type Not Exact Result", "Bill Type Not Exact Result Displayed", "PASS");

		// Bill Type With Does Not Contain

		click_button("Bill Type Operators Button", billTypeOperator);
		click_button("Bill Type Does Not Contain Operator", billTypeDoesNotContain);
		createReport();
		oParameters.SetParameters("BillTypeNotContain", get_field_value("Bill Type", billTypeColumn));

		if(oParameters.GetParameters("BillTypeNotContain").equals(oParameters.GetParameters("BillTypeValue")))
			oReport.AddStepResult("Bill Type Does Not Contain Result",	"Reports are filtered based on Bill Type not Contains  but those reports are not Displayed","FAIL");
		else
			oReport.AddStepResult("Bill Type Does Not Contain Result", "Bill Type Does Not Contain Result Displayed","PASS");

		clearFilters();
	}

	
	By admitDateElement = By.xpath("//li/a[contains(text(),'Admit Date')]");

	By admitStartDate = By.xpath("//*[@id='date-from']");

	By admitEndDate = By.xpath("//*[@id='date-to']");

	By admitDateColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[17]");

	
	// Admit Date Filter Reports
	public void admitDateReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Admit Date");
		oParameters.SetParameters("SystemCurrentDate", get_specified_date(0));
		oParameters.SetParameters("AdmitStartDate", get_specified_date(-1000));
		enter_text_value("Admit Start Date", admitStartDate, oParameters.GetParameters("AdmitStartDate"));
		enter_text_value("Admit End Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(admitDateColumn, "Reports Table");
		oParameters.SetParameters("AdmitDate2", getToolTipValue(admitDateColumn));

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		try 
		{
			Date AdmitDate2 = sdf.parse(oParameters.GetParameters("AdmitDate2"));
			Date AdmitDate1 = sdf.parse(oParameters.GetParameters("AdmitStartDate"));

			if(AdmitDate2.after(AdmitDate1)) 
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Admit Date Results", "Admit Date Results Displayed", "PASS");
			}
			else 
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Admit Date Results",	"Reports are filtered based on Admit date but those reports are not Displayed", "FAIL");
			}
		}
		catch(ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		clearFilters();
	}

	
	By statementFromDateElement = By.xpath("//li/a[contains(text(),'Statement From Date')]");

	By statementFromDateColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[38]");

	
	// Statement From Date Filter Reports
	public void statementFromDateReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Statement From Date");
		click_button("Calendar Start Date", processStartDate);
		waitFor(calendarClose, "Calendar Close Button");

		if(IsDisplayed("Populated Calendar", calendarClose))
			oReport.AddStepResult("Populated Calendar", "Populated Calendar Displayed", "PASS");
		else
			oReport.AddStepResult("Populated Calendar",	"Clicked on calendar icon but populated calendar is not Displayed", "FAIL");

		performKeyBoardAction("ESC");
		addFilter("Statement From Date");
		oParameters.SetParameters("SystemCurrentDate", get_specified_date(0));
		oParameters.SetParameters("StatementFromDate", get_specified_date(-1000));
		enter_text_value("Statement From Date", admitStartDate, oParameters.GetParameters("StatementFromDate"));
		enter_text_value("Statement Through Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(statementFromDateColumn, "Reports Table");
		oParameters.SetParameters("StatementFromDate2", getToolTipValue(statementFromDateColumn));

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		scroll_to_element(statementFromDateColumn, createReportButton);

		try 
		{
			Date StatementFromDate2 = sdf.parse(oParameters.GetParameters("StatementFromDate2"));
			Date StatementFromDate1 = sdf.parse(oParameters.GetParameters("StatementFromDate"));

			if (StatementFromDate2.after(StatementFromDate1)) 
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Statement Form Date Results", "Statement Form Date Results Displayed", "PASS");
			}
			else
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Statement From Date Results","Reports are filtered based on Statement Form date but those reports are not Displayed","FAIL");
			}
		}
		catch (ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		clearFilters();
	}

	
	By statementThroughDateElement = By.xpath("//li/a[contains(text(),'Statement Through Date')]");

	By statementThroughDateColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[39]");

	
	// Statement From Date Filter Reports
	public void statementThroughDateReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Statement Through Date");
		click_button("Calendar Start Date", processStartDate);
		waitFor(calendarClose, "Calendar Close Button");

		if(IsDisplayed("Populated Calendar", calendarClose))
			oReport.AddStepResult("Populated Calendar", "Populated Calendar Displayed", "PASS");
		else
			oReport.AddStepResult("Populated Calendar",	"Clicked on calendar icon but populated calendar is not Displayed", "FAIL");

		performKeyBoardAction("ESC");
		addFilter("Statement Through Date");
		oParameters.SetParameters("SystemCurrentDate", get_specified_date(0));
		oParameters.SetParameters("StatementFromDate", get_specified_date(-1000));
		enter_text_value("Statement From Date", admitStartDate, oParameters.GetParameters("StatementFromDate"));
		enter_text_value("Statement Through Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(statementFromDateColumn, "Reports Table");
		oParameters.SetParameters("StatementThroughDate2", getToolTipValue(statementThroughDateColumn));

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		scroll_to_element(statementThroughDateColumn, createReportButton);

		try 
		{
			Date StatementFromDate2 = sdf.parse(oParameters.GetParameters("StatementFromDate2"));
			Date StatementFromDate1 = sdf.parse(oParameters.GetParameters("StatementFromDate"));

			if (StatementFromDate2.after(StatementFromDate1)) 
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Statement Through Date Results", "Statement Through Date Results Displayed",	"PASS");
			}
			else
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Statement Through Date Results",	"Reports are filtered based on Statement Through date but those reports are not Displayed",	"FAIL");
			}
		}
		catch (ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		clearFilters();
	}
	
	
    By version = By.xpath("//a[text()='Version']");
    
    By versionFirstValue = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow number ng-scope']/span[@ng-click=\"expandRow('0' , row, 'version')\"]");
    
    
    //Filter by version
    public void versionFilterReports()
    {
    	oParameters.SetParameters("versionFirstValue", get_field_value("Version Field value",versionFirstValue));
    	addFilter("Version");         
    	enter_text_value("Text value", filtersTextBox, oParameters.GetParameters("versionFirstValue"));
    	performKeyBoardAction("ENTER");
    	createReport();         
    	CreateFolder(oParameters.GetParameters("downloadFilepath"));            
    	waitFor(downloadCSVButton, "Download CSV Button");          
    	click_button("Download Button", downloadCSVButton);         
    	fixed_wait_time(5);
    	
    	if(isFileDownloaded("ContractClaimReport.zip"))
    		oReport.AddStepResult("Version reports", "Reports are filtered based on Version then clicked on Download icon those reports are downloaded in zip file", "PASS");
    	else
    		oReport.AddStepResult("Version reports", "Reports are filtered based on Version then clicked on Download icon but those reports are not downloaded in zip file", "FAIL");
    }

	
	By processDateElement = By.xpath("//li/a[contains(text(),'Process Date')]");

	By processStartDate = By.xpath("//label[@for='date-from']");

	By calendarClose = By.xpath("//a[@title='Close the datepicker']");

	By processDateColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[44]");

	
	// Filter with Process Date
	public void processDateReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Process Date");
		click_button("Calendar Start Date", processStartDate);
		waitFor(calendarClose, "Calendar Close Button");

		if(IsDisplayed("Populated Calendar", calendarClose))
			oReport.AddStepResult("Populated Calendar", "Populated Calendar Displayed", "PASS");
		else
			oReport.AddStepResult("Populated Calendar", "Clicked on calendar icon but populated calendar is not Displayed", "FAIL");

		performKeyBoardAction("ESC");
		addFilter("Process Date");

		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-25));
		else if (oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT"))
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-1000));
		else
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-1000));

		oParameters.SetParameters("SystemCurrentDate", get_specified_date(-10));
		enter_text_value("Process Start Date", admitStartDate, oParameters.GetParameters("ProcessStartDate"));
		enter_text_value("Process End Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		createReport();
	//	scroll_to_element(processDateColumn, createReportButton);
		waitFor(createReportButton, "Create Report Button");
		
		String processDate = get_table_row_value(HeaderPath, ContentPath, "Process Date", 1).substring(0,10);
				
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		try
		{
			Date date1 = sdf.parse(oParameters.GetParameters("ProcessStartDate"));
			Date date2 = sdf.parse(processDate);
			System.out.println("date1 : " + sdf.format(date1));
			System.out.println("date2 : " + sdf.format(date2));

			if (date1.after(date2) || date2.equals(oParameters.GetParameters("SystemCurrentDate")))
				oReport.AddStepResult("Process Date Records", "Process Date Records Displayed", "PASS");
			else
				oReport.AddStepResult("Process Date Records","Reports are filtered based on Process date but those reports are not Displayed", "FAIL");
		}
		catch(ParseException e)
		{
			System.out.println("Exception Caught" + e);
		}
	}
	

	public By repriceClaimsElement1 = By.xpath("//a[@id='showhiderepricebutton'][contains(text(),'Reprice Claims...')]");

	public By repriceJobTexBox = By.xpath("//*[@id='createRepriceTemplateName']");

	public By saveReprice = By.xpath("//input[@value='Reprice']");

	By repriceJobMessage = By.xpath("//h5[contains(.,'Reprice Job Submitted')]");

	public By createdRepriceJobLink = By.xpath("//a[contains(.,'Click to view the Repriced Job Now.')]");

	By repricingPortalBar = By.xpath("//div[@class='portal-action-bar']//div[@class='col-lg-12 col-md-12 col-sm-12 xl-header ng-binding']");

	
	// Creating Repricing Job
	public void creatingRepricingJob() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Create Report DropDown", createReportDD);
		click_button("Reprice Claims...", repriceClaimsElement1);
		waitFor(repriceJobTexBox, "Create Reprice Job Name text box");
		enter_text_value("Reprice Job Text Box", repriceJobTexBox, oParameters.GetParameters("RepriceJobName"));
		fixed_wait_time(3);
		click_button("Creating Reprice", saveReprice);
		fixed_wait_time(5);
		waitFor(repriceJobMessage, "Reprice Job Submitted");

		if(IsDisplayed("Reprice Job Submitted Message", repriceJobMessage)) 
		{
			waitFor(repriceJobMessage, "Reprice Job Submitted");
			oReport.AddStepResult("Submitted Job Message", "Submitted Job Message Displayed", "PASS");
		}
		else
		{
			fixed_wait_time(2);
			oReport.AddStepResult("Submitted Job Message",	"Click on reprice button and create a reprice job but that job submitted pop up message not displayed ","FAIL");
		}

		click_button("Created Reprice Job Link", createdRepriceJobLink);
		waitFor(repricingPortalBar, "Repricing Job Title Bar");
		oParameters.SetParameters("RepriceJobInHeader", get_field_value("RepriceJobName", repricingPortalBar));
		fixed_wait_time(4);

		if(oParameters.GetParameters("RepriceJobInHeader").contains(oParameters.GetParameters("RepriceJobName")))
			oReport.AddStepResult("Submitted Job", "Submitted Job Displayed", "PASS");
		else
			oReport.AddStepResult("Submitted Job",	"Clicked on view reprice job but page not navigated to Repricing Plugin or submitted job is not displayed","FAIL");

		navigate_to_Analytics();
		waitFor(clearFiltersButton, "Clear All Filters Button");

		if(IsDisplayed("Process Date Column", processDateColumn))
			oReport.AddStepResult("Previous Process Date Records", "Previous Process Date Records Displayed", "PASS");
		else
			oReport.AddStepResult("Previous Process Date Records",	"Navigating  back to reports plugin but the previously opened records are not displayed", "FAIL");
	}

	
	By claimIdColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'claimId')\"] ");

	By testPriceButton = By.xpath("//input[@value='Test Price']");

	By testPricePlugIn = By.xpath("//a[contains(text(),'Test Price')]");

	
	// Clicking on Claim ID and Test Price
	public void testPriceDataValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Claim ID", claimIdColumn);
		waitFor(testPriceButton, "Test Price Button");
		fixed_wait_time(2);
		click_button("Test Price Button", testPriceButton);
		waitFor(testPricePlugIn, "Test Price PlugIn");

		if(IsDisplayed("Test Price PlugIn", testPricePlugIn))
			oReport.AddStepResult("Test Price PlugIn and New Claim Tab","Test Price PlugIn and New Claim Tab Displayed", "PASS");
		else
			oReport.AddStepResult("Test Price PlugIn and New Claim Tab","Clicked on claim id and Test Price button but new claim tab is not displayed and page is not navigated to test price plugin",	"FAIL");

		By slider = By.xpath("//*[@id='periodCol']/label");

		By destination = By.xpath("//label[contains(.,'Total Charges')]");

		waitFor(slider, "Test Price Plugin");
		scroll_to_element(destination, slider);

		oReport.AddStepResult("Populated Data", "Populated Data Displayed", "PASS");

		navigate_to_Analytics();
		waitFor(createReportButton, "Create Report Button");
	}

	
	public By rateSheetColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'rateSheetAlias')\"]");

	By rateSheetColumnHeader = By.xpath("//*[@id='report-table-parent']//table/thead/tr/td//div[contains(text(),'Rate Sheet')]");

	By rateSheetTitleBar = By.xpath("//div[@class='document-title-bar ng-scope']/div[@class='pull-right xl-header pad-r-5 ng-binding']");

	
	// Rate Sheet validation
	public void rateSheetValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}		

    	addFilter("Rate Sheet");         
    	enter_text_value("Text value", filtersTextBox, "AML");
    	performKeyBoardAction("ENTER");
    	createReport();
    	oParameters.SetParameters("RateSheetName", get_field_value("Rate Sheet Name", rateSheetColumn));
    	
    	click_button("Rate Sheet Link", rateSheetColumn);
    	waitFor(rateSheetTitleBar, "RateSheet Title Bar");
    	oParameters.SetParameters("RateSheetTitleText",	get_field_value("Rate Sheet Title Text", rateSheetTitleBar).replace("Code ", ""));
    	fixed_wait_time(3);
    	
    	if(oParameters.GetParameters("RateSheetName").equalsIgnoreCase(oParameters.GetParameters("RateSheetTitleText")))
    		oReport.AddStepResult("Navigated and Selected Rate Sheet","Navigated and Selected Rate Sheet Displayed", "PASS");
    	else
    		oReport.AddStepResult("Navigated and Selected Rate Sheet","Clicked on Rate Sheet link but page not navigated to Rate Sheet and it doesn't displayed the selected rate sheet ","FAIL");

		navigate_to_Analytics();
		clearFilters();
	}

	
	By firstPageButton = By.xpath("//li[@class='num first']");

	By secondPageButton = By.xpath("//li[@class='num']/a[contains(text(),'2')]");

	By lastPageButton = By.xpath("//li[@class='num last']");

	By selctedSecondPage = By.xpath("//li[@class='num selected']/a[contains(text(),'2')]");

	By nextPageButton = By.xpath("//li[@class='next ng-scope ng-binding'][contains(text(),'Next')]");

	By prevPageButton = By.xpath("//li[@class='prev ng-scope ng-binding'][contains(text(),'Prev')]");

	By disabledPrevPageButton = By.xpath("//li[@class='prev disabled ng-scope ng-binding'][contains(text(),'Prev')]");

	
	// Page Navigations
	public void pageNavigations() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Second page

		if(IsDisplayed("Second Page Button", secondPageButton)) 
		{
			click_button("Second Page Button", secondPageButton);
			waitFor(selctedSecondPage, "selcted Second Page");

			if(IsDisplayed("Selected Second Page", selctedSecondPage)) 
			{
				fixed_wait_time(3);
				oReport.AddStepResult("Clicked Page Results", "Clicked Page Results Displayed", "PASS");
				click_button("Back to First Page", firstPageButton);
				waitFor(secondPageButton, "second Page Button");
			}
			else
			{
				fixed_wait_time(2);
				oReport.AddStepResult("Clicked Page Results","Clicked on Second page but that respective records not displayed", "FAIL");
			}
		} 
		else
			oReport.AddStepResult("Page Button", "No Next Pages", "INFO");

		// Next Page

		if(IsDisplayed("Next Page Button", nextPageButton)) 
		{
			click_button("Next Page Button", nextPageButton);
			waitFor(prevPageButton, "prev Page Button");

			if(IsDisplayed("Prev Page Button", prevPageButton)) 
			{
				waitFor(prevPageButton, "prev Page Button");
				oReport.AddStepResult("Next Page Results", "Next Page Results Displayed", "PASS");
			}
			else
			{
				fixed_wait_time(2);
				oReport.AddStepResult("Next Page Results","Clicked on Next page but that respective records not displayed", "FAIL");
			}
		} 
		else
			oReport.AddStepResult("Next Page Button", "No Next Page", "INFO");

		// Previous Page

		if(IsDisplayed("Prev Page Button", prevPageButton)) 
		{
			click_button("Prev Page Button", prevPageButton);
			waitFor(createReportButton, "Create Report Button");

			if(IsDisplayed("Disabled Prev Page Button", disabledPrevPageButton)) 
			{
				waitFor(disabledPrevPageButton, "disabled Prev Page Button");
				oReport.AddStepResult("Prev Page Results", "Prev Page Results Displayed", "PASS");
			}
			else
			{
				fixed_wait_time(2);
				oReport.AddStepResult("Prev Page Results",	"Clicked on Prev page but that respective records not displayed", "FAIL");
			}
		}
		else
			oReport.AddStepResult("Prev Page Button", "No Prev Page", "INFO");
	}

	
	By adjustmentElement = By.xpath("//li/a[contains(text(),'Adjustment')]");

	By adjustmentOperatorElement = By.xpath("//a[contains(text(),'Greater Than or Equal to')]");
	
	public By adjustmentOperatorElementLessthan = By.xpath("//a[contains(text(),'≤ Less Than or Equal to')]");

	By adjustmentColumnHeader = By.xpath("//*[@id='report-table-parent']//table/thead/tr/td//div[contains(text(),'Adjustment')]");

	By adjustmentColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[25]");

	
	// Adjustment Filter Reports
	public void adjustmentReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		//click_button("Adjustments Column Header", adjustmentColumnHeader);
		waitFor(createReportButton, "Create Report Button");
		longWaitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("AdjustmentValue", get_field_value("Adjustment Column", adjustmentColumn).replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\\*","").replaceAll("\\$","")); 
		addFilter("Adjustment");
		click_button("Adjustment Operator Button", billTypeOperators);
		click_button("Greater Than or Equal to Button", adjustmentOperatorElement);
		enter_text_value("Filters Input Text Box", filtersTextBox,oParameters.GetParameters("AdjustmentValue").replaceAll("[($)*]", ""));
		oParameters.SetParameters("AdjustmentValue", get_field_value("Adjustment Column", filtersTextBox));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("AdjustmentValue1", get_field_value("Adjustment Column", adjustmentColumn).replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\\*","").replaceAll("\\$","")); 
		
		if(Float.parseFloat(oParameters.GetParameters("AdjustmentValue"))<=Float.parseFloat(oParameters.GetParameters("AdjustmentValue1")))
			oReport.AddStepResult("Adjustment Value Records", "Adjustment Value Records Displayed", "PASS");
		else
			oReport.AddStepResult("Adjustment Value Records", "Reports filtered based on Adjustment with greater than or equal to operator but those reports are not Displayed","FAIL");
	}

	
	By reportResultFirstColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'serviceFacilityName')\"]");

	By serviceFacilityHeader = By.xpath("//div[@class='heading ng-scope'][contains(text(),'Service Facility')]");

	
	// Column sorting
	public void sortingResults() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
/*		addFilter("Claim Id");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("ClaimIDBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();*/
		createReport();
		click_button("Service Facility Header", serviceFacilityHeader);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("ServiceFacilityFirstColumn",get_field_value("Service Facility First Column Text", reportResultFirstColumn));
		click_button("Service Facility Header", serviceFacilityHeader);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("ServiceFacilityFirstColumn1",get_field_value("Service Facility First Column Text", reportResultFirstColumn));

		if(oParameters.GetParameters("ServiceFacilityFirstColumn").equals(oParameters.GetParameters("ServiceFacilityFirstColumn1")))
			oReport.AddStepResult("Column Sorted Results","Mouse hovered and clicked on Service Facility Header but that column is not sorted from descending to ascending order ",	"FAIL");
		else
			oReport.AddStepResult("Column Sorted Results", "Column Sorted Results Displayed", "PASS");
	}

	
	By serviceFacilityCheckBox = By.xpath("//input[@id='serviceFacilityName']");
	
	By billingProviderCheckBox = By.xpath("//input[@id='billingProviderName']");
	
	By billingProviderHeader = By.xpath("//div[@class='heading ng-scope'][text()='Billing Provider']");

	
	// temporary Changes in settings
	public void temporarilyChangedSettings() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Billing Provider Check Box", billingProviderCheckBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Billing Provider Header", billingProviderHeader))
			oReport.AddStepResult("Uncheck Billing Provider","Unchecked Billing Provider option in settings window but still it is displayed in Reports page","FAIL");
		else
			oReport.AddStepResult("Billing Provider Header", "Billing Provider header not displayed", "PASS");
	}

	// Default Columns check
	public void defaultColumnsCheck() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(billingProviderHeader, "Reports Table");

		if(IsDisplayed("Billing Provider Header", billingProviderHeader))
			oReport.AddStepResult("Billing Provider Header", "Billing Provider Header Displayed", "PASS");
		else
			oReport.AddStepResult("Billing Provider","Checked Billing Provider option in settings window but still it is Not displayed in Reports page","FAIL");
	}
	

	// Default Changes in settings
	public void defaultSettings() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Billing Provider Check Box", billingProviderCheckBox);
		click_button("Save as My Defaults", saveMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Billing Provider Header", billingProviderHeader))
			oReport.AddStepResult("Unchecked Billing Provider option",	"Unchecked Billing Provider option in settings window but still it is displayed in Reports page","FAIL");
		else
			oReport.AddStepResult("Billing Provider Header", "Billing Provider header not displayed", "PASS");
	}

	
	// Temporary Settings
	public void temporarySettings() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Billing Provider Check Box", billingProviderCheckBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Billing Provider Header", billingProviderHeader))
			oReport.AddStepResult("Billing Provider Header", "Billing Provider Header Displayed", "PASS");
		else
			oReport.AddStepResult("Checked Billing Provider optoin", "Checked Billing Provider option in settings window but it is not displayed in Reports page",	"FAIL");

		navigate_to("Navigate Contract Management Suite", "Contract Plugin", contractManagementSuite, contractsPlugIn);

		navigate_to("Navigate to Analytics Suite", "Billing Provider Column Header", analyticsSuite, billingProviderHeader);
	}

	
	// Reset to My Defaults
	public void resetToMyDefaultSettings() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Reset to My Defaults", resetMyDefaultsRadio);
		fixed_wait_time(3);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Billing Provider Header", billingProviderHeader)) 
		{
			fixed_wait_time(2);
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Billing Provider option", "Checked Billing Provider option in settings window and saved changes temporarily then settings saved as Reset to My Default but still Billing Provider column displayed in reports page  ","FAIL");
		}
		else
		{
			fixed_wait_time(2);
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Billing Provider Header", "Billing Provider header not displayed", "PASS");
		}
	}

	
	// Reset To System Defaults
	public void resetToSystemDefaults() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Reset to System Defaults", resetSystemDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Billing Provider Header", billingProviderHeader)) 
		{
			fixed_wait_time(2);
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Billing Provider Header", "Billing Provider Header Displayed", "PASS");
		}
		else
		{
			fixed_wait_time(2);
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Billing Provider Header", "Checked Billing Provider option in settings window but still it is not displayed in Reports page","FAIL");
		}
		click_button("Settings", settingsButton);
		click_button("Save as My Defaults", saveMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");
	}

	
	By claimMoreTab = By.xpath("//*[@id='styledDropdown']/a[1]/span[3][contains(text(),'More')]");

	By closeAllTabsOption = By.xpath("//a[@class='style-sub-menu ng-binding'][@title='Close All Tabs']");

	By openedClaimTab = By.xpath("//*[@class='ng-binding tabPos']");

	By claimTabCloseButton = By.xpath("//li[@class='portal-tab-pane tab-view hand-cursor ng-scope active']//span/i[@class='left fa fa-times-circle closeButton hand-cursor']");

	
	// Claim ID
	public void claimIDReport() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Claim ID", claimIdColumn);
		waitFor(claimMoreTab, "claim More Tab");

		if(IsDisplayed("Claim More Tab", claimMoreTab))
			oReport.AddStepResult("Claim Tab and More Tab", "Claim Tab and More Tab Displayed", "PASS");
		else
			oReport.AddStepResult("Claim Tab and More Tab",	"Clicedk on Claim Id but that claim tab along with More Tab is not displayed", "FAIL");

		mouse_hover("Claim More Tab", claimMoreTab);
		waitFor(closeAllTabsOption, "claim More tab close all tabs option");

		if(IsDisplayed("Close All Tabs Option", closeAllTabsOption))
			oReport.AddStepResult("Close All Tabs Option", "Close All Tabs Option Displayed", "PASS");
		else
			oReport.AddStepResult("Close All Tabs Option",	"Mouse hovered to more tab but close All Tabs option is not displayed ", "FAIL");

		mouse_hover("Claim Tab", openedClaimTab);
		click_button("Claim Tab Close Button", claimTabCloseButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Create Report Button", createReportButton))
			oReport.AddStepResult("Claim Tab", "Claim Tab Closed", "PASS");
		else
			oReport.AddStepResult("Claim Tab",	"Mouse hovered to claim tab but close icon is not displayed and claim tab not closed", "FAIL");
	}

	
	By tooManyTabsInfo = By.xpath("//span[contains(.,' Too many tabs open. You must close a tab before opening another.')]");

	By toomanyTabsNotificationClose = By.xpath("//span[@class='pull-right close-icon icon fa fa-times-circle'][@icon='times-circle']");

	
	// Too many tabs notification Validation
	public void manyTabsNotification() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Results Per Page", resultPerPageDropDown);
		click_button("50 Results per page", results50);
		waitFor(createReportButton, "Create Report Button");

		// to open 20 claims
		for(int i = 0; i < 21;) 
		{
			By claimId = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('"	+ i + "' , row, 'claimId')\"]");
			click_button("Claim Id", claimId);
			waitFor(testPriceButton, "test Price Button");
			By reportLink = By.xpath("//a[@title='Report'][contains(.,'Report')]");
			click_button("Report Button", reportLink);
			waitFor(claimId, "Reports Table");
			i++;
		}

		if(IsDisplayed("Too Many Tabs Info", tooManyTabsInfo))
			oReport.AddStepResult("Too Many Tabs Info", "Too Many Tabs Info Displayed", "PASS");
		else
			oReport.AddStepResult("Too Many Tabs Info",	"20 claims opened and clicked on 21 claim but Too many tabs info not displayed ", "FAIL");

		click_button("Too Many tabs Notification Close", toomanyTabsNotificationClose);
		createReport();
	}

	
	By repriceStatusHeader = By.xpath("//div[@class='heading ng-scope'][contains(text(),'Reprice Status')]");

	public By EORtitleBar = By.xpath("//*[@id='claimHeader']/div[contains(text(),'Explanation of Reimbursement')]");

	By coveredCharges = By.xpath("//*[@id='claimSummary']//td[@model='ClaimTotals.CoveredCharges']/span[@ng-class='calcNumberClass(model) + isRedClass(isRed)']");

	By contractualAdjustmentValue = By.xpath("//*[@id='claimSummary']//td[@model='ClaimTotals.ContractualAdjustment']/span[@ng-class='calcNumberClass(model) + isRedClass(isRed)']");

	By totalExpectedReimbursement = By.xpath("//table[@id='lineItems']//tr[@class='footer-title']//td//span[@class='green'] ");

	By repriceStatus1 = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),1)]");

	By printIcon = By.xpath("//i[@class='left fa fa-print']");

	By includeRadio = By.xpath("//input[@type='radio'][@value='includeNote']");

	By excludeRadio = By.xpath("//input[@type='radio'][@value='excludeNote']");

	By printButton = By.xpath("//input[@value='Print']");

	By specificPagePrintRadio = By.xpath("//input[@type='radio'][@class='page-settings-custom-radio']");

	By customInputBox = By.xpath("//*[@id='page-settings-custom-input']");

	By printPageCancel = By.xpath("//button[@class='cancel'][contains(text(),'Cancel')]");

	By expectedReimbursementValue = By.xpath("//table[@id='lineItems']/tbody/tr//td[@class='topRightCell font15']/span");

	
	// Reprice Status validation
	public void repriceStatusValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Reprice Status");
		selectMultipleCheckBoxes("1 - Success");
		addFilter("Patient Name");
		enter_text_value("Search Box", filtersTextBox, "HP, CCM HEALTH PLAN HMO"); // oParameters.GetParameters("ContractManagerBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		click_button("Reprice Status", repriceStatusColumn);
		switchToNewWindow(2);
		waitFor(EORtitleBar, "EOR title Bar");

		if(IsDisplayed("EOR Text", EORtitleBar))
			oReport.AddStepResult("EOR Page", "EOR Page Displayed", "PASS");
		else
			oReport.AddStepResult("EOR Page","Clicked on reprice status but that EOR is not displayed in new browser tab", "FAIL");

		oParameters.SetParameters("EORText", get_field_value("EOR", rateSheetTitleBar));

		Float coveredChargesTotal = Float.valueOf(get_field_value("Covered Charges", coveredCharges).replace("$", ""));

		Float contractualAdjustment = Float.valueOf(get_field_value("Contractual Adjustment Value", contractualAdjustmentValue).replaceAll("[, $,()]", ""));

		Float valueOf1 = Float.valueOf(get_field_value("Total Expected Reimbursement", totalExpectedReimbursement).replaceAll("[, $,()]", ""));

		int noOfReimbursements = get_table_row_count(expectedReimbursementValue);
		float reimbursementValues = 0;

		for(int i = 1; i <= noOfReimbursements; i++) 
		{
			By reimbursementValue = By.xpath("//table[@id='lineItems']/tbody/tr[" + i + "]//td[@class='topRightCell font15']/span");

			Float valueOf = Float.valueOf(get_field_value("Reimbursement Value", reimbursementValue).replaceAll("[,n/a $,()]", ""));

			reimbursementValues = valueOf + reimbursementValues;
		}

		scroll_to_element(totalExpectedReimbursement, coveredCharges);

		if(reimbursementValues == valueOf1 && coveredChargesTotal - reimbursementValues == contractualAdjustment)
			oReport.AddStepResult("EOR, Claim Summary and Reprice By Terms","EOR, Claim Summary and Reprice By Terms Displayed", "PASS");
		else
			oReport.AddStepResult("EOR, Claim Summary and Reprice By Terms","Details under Explanation of Reimbursement, claim summary, claim and Reprice By Terms not displayed accurately","FAIL");

		
/*		click_button("Print Page Icon", printIcon);
		waitFor(printButton, "print Button");
		fixed_wait_time(4);
		switchToNewWindow(3);
		click_button("Select Specific Page Radio", specificPagePrintRadio);
		enter_text_value_without_clear("Custom Page TextBox", customInputBox, oParameters.GetParameters("PageNumber"));
		fixed_wait_time(3);

		oReport.AddStepResult("Include Term Notes", "Include Term Notes Displayed", "PASS");

		click_button("Cancel Print Page Button", printPageCancel);
		switchToNewWindow(2);
		waitFor(printIcon, "print Icon");
		click_button("Print Page Icon", printIcon);
		fixed_wait_time(4);
		switchToNewWindow(3);
		waitFor(specificPagePrintRadio, "specific Page Print Radio");
		click_button("Select Specific Page Radio", specificPagePrintRadio);
		enter_text_value_without_clear("Custom Page TextBox", customInputBox, oParameters.GetParameters("PageNumber"));
		fixed_wait_time(3);

		oReport.AddStepResult("Exclude Term Notes", "Exclude Term Notes Not Displayed", "PASS");

		click_button("Cancel Print Page Button", printPageCancel);
		switchToNewWindow(2);*/

		fixed_wait_time(3);
		windowHandle("parent");
		waitFor(createReportButton, "Create Report Button");
		clearFilters();
	}

	
//	By loadingIcon = By.xpath("//div[@ng-if='$parent.totalRecords > 0']//div[@class='loadingSpinner']");
	
	By loadingIcon = By.xpath("(//div[@class='loading-icon-mask']//div[@class='loadingSpinner'][text()='Loading...'])[2]");
	
	//(//*[@id='report-table-parent']/div/div)[1]
	
	//(//div[@class='loading-icon-mask']//div[@class='loadingSpinner'][text()='Loading...'])[2]

	By contractManagerHeader = By.xpath("//*[@id='report-table-parent']//table/thead/tr/td//div[contains(text(),'Contract Manager')]");

	
	// Contract Manager Filter Reports
	public void contractManagerReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Contract Manager Filter Reports

		if(IsDisplayed("Contract Manager", contractManagerColumn)) 
		{
			addFilter("Contract Manager");
			oParameters.SetParameters("ContractManagerBeforeFilter",get_field_value("Contract Manager Name", contractManagerColumn).toLowerCase());
			enter_text_value("Search Box", filtersTextBox, oParameters.GetParameters("ContractManagerBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			scroll_to_element(contractManagerHeader, createReportButton);
			oParameters.SetParameters("ContractManagerAfterFilter",get_field_value("Contract Manager Name", contractManagerColumn).toLowerCase());

			if(oParameters.GetParameters("ContractManagerBeforeFilter").contains(oParameters.GetParameters("ContractManagerAfterFilter")))
				oReport.AddStepResult("Contract Manager Reports", "Contract Manager Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Contract Manager Reports","Reports are filtered based on Contract Manager but those reports are not displayed", "FAIL");
		}
		else 
		{
			click_button("Contract Manager Header", contractManagerHeader);
			waitFor(createReportButton, "Create Report Button");
			addFilter("Contract Manager");
			oParameters.SetParameters("ContractManagerBeforeFilter",get_field_value("Contract Manager Name", contractManagerColumn).toLowerCase());
			enter_text_value("Search Box", filtersTextBox, oParameters.GetParameters("ContractManagerBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			scroll_to_element(contractManagerHeader, createReportButton);
			oParameters.SetParameters("ContractManagerAfterFilter",	get_field_value("Contract Manager Name", contractManagerColumn).toLowerCase());

			if(oParameters.GetParameters("ContractManagerBeforeFilter").contains(oParameters.GetParameters("ContractManagerAfterFilter")))
				oReport.AddStepResult("Contract Manager Reports", "Contract Manager Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Contract Manager Reports","Reports are filtered based on Contract Manager but those reports are not displayed", "FAIL");
		}
	}

	
	// Downloading and checking Records
	public void comparingClaimRepricingDownloadedReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Process Date");

		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-5));
		else if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT"))
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-10));
		else
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-1000));

		enter_text_value("Process Start Date", admitStartDate, oParameters.GetParameters("ProcessStartDate"));
		enter_text_value("Process End Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		createReport();
		click_button("Create Reports Button", createReportButton);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("noOfRecords",get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));

		CreateFolder(oParameters.GetParameters("downloadFilepath"));
		waitFor(downloadCSVButton, "Download CSV Button");
		click_button("Download Button", downloadCSVButton);
		fixed_wait_time(5);

		if(isFileDownloaded("ContractClaimReport.zip")) 
		{
			unZipFolder(oParameters.GetParameters("downloadFilepath")+"/ContractClaimReport.zip",oParameters.GetParameters("downloadFilepath"));
			File zipFile = new File(oParameters.GetParameters("downloadFilepath")+"/ContractClaimReport.zip");
			zipFile.delete();
			csvToExcel();
			@SuppressWarnings("static-access")
			int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
			oParameters.SetParameters("recordsInExcel", String.valueOf(recordsCount));

			if(oParameters.GetParameters("noOfRecords").equals(oParameters.GetParameters("recordsInExcel")))
				oReport.AddStepResult("Downloaded Records", "Downloaded Records Matched", "PASS");
			else
				oReport.AddStepResult("Downloaded Records",	"Records downloaded in excel file but number of record in portal Not Matched with number of records in downloaded excel file","FAIL");
		} 
		else
			oReport.AddStepResult("Downloaded File", "Clicked on 'Donwload CSV' button but zip file is not downloaded ","FAIL");
	}

	
	// Logging with User 2
	public void viewUserLogin() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		login("GAGAN");
		changePricingEngine();
		navigate_to_Analytics();
		select_Report("Claim Repricing Report");
		createReport();

		if(IsDisplayed("Reprice Button", createReportDD))
			oReport.AddStepResult("Reprice Button", "Reprice button not disabled", "FAIL");
		else
			oReport.AddStepResult("Reprice Button", "Reprice Button Disabled", "PASS");

		// Clicking on Claim ID

		click_button("Claim ID", claimIdColumn);
		fixed_wait_time(6);

		if(IsDisplayed("Test Price Button", testPriceButton))
			oReport.AddStepResult("Test Price Button","Clicked on claim id but that new claim tab is displayed with Test Price button", "FAIL");
		else
			oReport.AddStepResult("Test Price Button", "Test Price Button Not Displayed", "PASS");

		// Disabled EOR option validation

		mouse_hover("Claim Tab", openedClaimTab);
		click_button("Claim Tab Close Button", claimTabCloseButton);
		waitFor(createReportButton, "Create Report Button");

		oParameters.SetParameters("RepriceStatus", get_field_value("Reprice Status", repriceStatusColumn));

		if(oParameters.GetParameters("RepriceStatus").contains("0")) 
		{
			click_button("Reprice Status Header", repriceStatusHeader);
			waitFor(createReportButton, "Create Report Button");
			click_button("Reprice Status", repriceStatusColumn);
			fixed_wait_time(3);

			if(IsDisplayed("Create Report Button", createReportButton))
				oReport.AddStepResult("EOR Option", "EOR Option Disabled", "PASS");
			else
				oReport.AddStepResult("EOR Option", "EOR option not disabled", "FAIL");
		} 
		else
		{
			click_button("Reprice Status", repriceStatusColumn);
			fixed_wait_time(3);

			if(IsElementDisplayed("Create Report Button", createReportButton))
				oReport.AddStepResult("EOR Option", "EOR Option Disabled", "PASS");
			else
				oReport.AddStepResult("EOR Option", "EOR option not disabled", "FAIL");
		}
	}

	
	By healthPlanCheckBox = By.xpath("//input[@id='healthPlanName']");

	
	// Health Plan option
	public void healthPlanOption() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Health Plan Check Box", healthPlanCheckBox))
			oReport.AddStepResult("Health Plan CheckBox","On 'Claim Repricing Report Settings' window 'Health Plan' option displaying twice ", "FAIL");
		else
			oReport.AddStepResult("Health Plan CheckBox","On 'Claim Repricing Report Settings' window 'Health Plan' option displayed only Once", "PASS");
	}

	
	// Claim Repricing Report VR 1
	public void claimRepricingReport_VR1() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		//navigateToClaimRepricingReport();
		navigate_to_Analytics();
		select_Report("Claim Repricing Report");
		claimIDFilterReports();
		claimHistoryReports();
		claimTypeReports("Institutional");
		clearFilters();
		claimTypeReports("Professional");
		clearFilters();
		repriceStatusReports("1 - Success");
		repriceStatusReports("0 - Failed,1 - Success,2 - Error");
		originReports("Original Repricing,On-Demand Repricing,Batch Repricing");
		coordinationofBenefitsFilterReports("Primary,Secondary,Tertiary");
		claimStatusReports();
		billTypeReports();
		admitDateReports();
		statementFromDateReports();
		statementThroughDateReports();
		versionFilterReports();
		processDateReports();
		resultsPerPage("100");
		pageNavigations();
		createBookmark();
		clearFilters();
		adjustmentReports();
		bookmarkValidation();
		clearFilters();
		sortingResults();
		settingsWindow();
		healthPlanOption();
		temporarilyChangedSettings();
		portalLogout();
		login("EDIT");
		changePricingEngine();
		navigate_to_Analytics();
		select_Report("Claim Repricing Report");
		defaultColumnsCheck();
		defaultSettings();
		temporarySettings();
		resetToMyDefaultSettings();
		resetToSystemDefaults();
		fullScreenValidation();
		claimIDReport();
		contractManagerReports();
		navigatingToOtherReport("Contract Listing Report","Claim Repricing Report");
		logout();
	}

	
	// Claim Repricing Report VR 2
	public void claimRepricingReport_VR2()
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigate_to_Analytics();
		select_Report("Claim Repricing Report");
		reportsWithProcessDate();
		creatingRepricingJob();
		unFilteredReports();
		repriceClaimsCheck();
		serviceFacilityMultiSelectWindow();
		multiSelectWindowValidation();
		testPriceDataValidation();
		rateSheetValidation();
		manyTabsNotification();
		repriceStatusValidation();
		comparingClaimRepricingDownloadedReports();
		logout();
		viewUserLogin();
		logout();		
	}
	
	
	// Contract Error Claim Report VR

	
	By contractErrorClaimReport = By.xpath("//div[@class='styled-dropdown black-text-when-disabled ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Contract Error Claim Report')]");

	By contractErrorClaimReportDC = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Contract Error Claim Report')]");
	
	By claimIDElement = By.xpath("//li/a[contains(text(),'Claim Id')]");

	public By claimIDColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'claimId')\"]");

	
	// Claim ID Filter Reports
	public void claimIDReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(claimIDColumn, "Reports Table");
		oParameters.SetParameters("ClaimIDBeforeFilter", get_field_value("Claim ID", claimIDColumn));
		addFilter("Claim Id");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("ClaimIDBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(claimIDColumn, "Reports Table");
		oParameters.SetParameters("ClaimIDAfterFilter", get_field_value("Claim ID", claimIDColumn));

		if(oParameters.GetParameters("ClaimIDBeforeFilter").equals(oParameters.GetParameters("ClaimIDAfterFilter"))&& IsElementEnabled("Download CSV Button", downloadCSVButton))
			oReport.AddStepResult("Claim ID Reports and Download Icon", "Claim ID Reports and Download Icon Displayed",	"PASS");
		else
			oReport.AddStepResult("Claim ID Reports and Download Icon",	"Reports are filtered based on Claim ID  but those reports are not displayed, and download icon not enabled.","FAIL");

		// Clearing Claim ID filter and Creating reports

		clearFilters();
		createReport();
		waitFor(claimIDColumn, "Reports Table");

		if(IsDisplayed("Clear Filters Button", clearFiltersButton)) 
		{
			waitFor(createReportButton, "create Report Button");
			oReport.AddStepResult("Complete Report","Cleared filters but complete report not displayed ", "FAIL");
		} 
		else
		{
			waitFor(createReportButton, "create Report Button");
			oReport.AddStepResult("Complete Report", "Complete Report Displayed", "PASS");
		}
	}

	
	By originColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'origin')\"]");

	By originElement = By.xpath("//li/a[contains(text(),'Origin')]");

	By originBatchRepricing = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),'Batch Repricing')]");

	
	// Origin Filter Reports
	public void originReports(String origin) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = origin.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for (int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}
			
			createReport();
			addFilter("Origin");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(originColumn, "Reports Table");
			oParameters.SetParameters("OriginAfter", get_field_value("Origin", originColumn));

			if(oParameters.GetParameters("OriginValue").contains(oParameters.GetParameters("OriginAfter")))
				oReport.AddStepResult("Origin Reports", "Reports are filtered based on Origin, verified that Origin Reports are Displayed", "PASS");
			else
				oReport.AddStepResult("Origin Reports",	"Reports are filtered based on Origin "+word+" but those reports are not displayed", "FAIL");
		
			clearFilters();
		}
	}

	
	By patientNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'patientName')\"]");

	By patientNameElement = By.xpath("//li/a[contains(text(),'Patient Name')]");
	
	By patientHeader = By.xpath("//*[@title='Patient Name']");

	
	// Patient Name Filter Reports
	public void patientNameReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Patient Name with Contains

		createReport();
		waitFor(patientNameColumn, "Reports Table");
		click_button("PatientName", patientHeader);
		oParameters.SetParameters("PatientNameBeforeFilter", get_field_value("Patient Name", patientNameColumn));
		addFilter("Patient Name");
		click_button("Patient Name Operators Button", billTypeOperators);
		click_button("Patient Name Contains Operator", billTypeContainsOperator);
		enter_text_value("Filters Input Text Box", filtersTextBox,oParameters.GetParameters("PatientNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(patientNameColumn, "Reports Table");
		oParameters.SetParameters("PatientNameAfterFilter", get_field_value("Patient Name", patientNameColumn));

		if(oParameters.GetParameters("PatientNameBeforeFilter").equals(oParameters.GetParameters("PatientNameAfterFilter")))
			oReport.AddStepResult("Patient Name Contains Result", "Patient Name Contains Result Displayed", "PASS");
		else
			oReport.AddStepResult("Patient Name Contains Result","Reports are filtered based on Patient Name with Contains operator but those reports are not displayed ",	"FAIL");

		// Patient Name with Exact

		click_button("Patient Name Operators Button", billTypeOperator);
		click_button("Patient Name Exact Operator", billTypeExactOperator);
		createReport();
		oParameters.SetParameters("PatientNameExact", get_field_value("Patient Name", patientNameColumn));

		if(oParameters.GetParameters("PatientNameBeforeFilter").equals(oParameters.GetParameters("PatientNameExact")))
			oReport.AddStepResult("Patient Name Exact Result", "Patient Name Exact Result Displayed", "PASS");
		else
			oReport.AddStepResult("Patient Name Exact Result", "Reports are filtered based on Patient Name with Exact operator but those reports are not displayed ","FAIL");

		// Patient Name With Not Exact

		click_button("Patient Name Operators Button", billTypeOperator);
		click_button("Patient Name Not Exact Operator", billTypeNotExactOperator);
		createReport();
		oParameters.SetParameters("PatientNameNotExact", get_field_value("Patient Name", patientNameColumn));

		if(oParameters.GetParameters("PatientNameBeforeFilter").equals(oParameters.GetParameters("PatientNameNotExact")))
			oReport.AddStepResult("Patient Name Not Exact Result","Reports are filtered based on Patient Name with not Exact operator but those reports are not displayed ","FAIL");
		else
			oReport.AddStepResult("Patient Name Not Exact Result", "Patient Name Not Exact Result Displayed", "PASS");

		// Patient Name With Does Not Contain

		click_button("Patient Name Operators Button", billTypeOperator);
		click_button("Patient Name Does Not Contain Operator", billTypeDoesNotContain);
		createReport();
		oParameters.SetParameters("PatientNameNotContain", get_field_value("Patient Name", patientNameColumn));

		if(oParameters.GetParameters("PatientNameBeforeFilter").equals(oParameters.GetParameters("PatientNameNotContain")))
			oReport.AddStepResult("Patient Name Does Not Contain Result","Reports are filtered based on Patient Name with does not contain operator but those reports are not displayed ",	"FAIL");
		else
			oReport.AddStepResult("Patient Name Does Not Contain Result","Patient Name Does Not Contain Result Displayed", "PASS");

		clearFilters();
	}

	
	By processDateRanges = By.xpath("//*[@title='Process Date']//div[@class='filter-value pull-left ng-scope ng-binding']");

	By claimErrorProcessDateColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[15]");

	
	// Process Date Filter Reports
	public void processDateFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Process Date");

		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-6));
		else if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT"))
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-1000));
		else
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-1000));

		oParameters.SetParameters("SystemCurrentDate", get_specified_date(-1));
		enter_text_value("Process Start Date", admitStartDate, oParameters.GetParameters("ProcessStartDate"));
		enter_text_value("Process End Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		waitFor(processDateRanges, "Process Date Range");

		if(IsDisplayed("Entered Date Ranges", processDateRanges))
			oReport.AddStepResult("Entered Date Ranges", "Entered Date Ranges Displayed", "PASS");
		else
			oReport.AddStepResult("Entered Date Ranges", "Entered the date ranges but those date rages not displayed","FAIL");

		createReport();
		longWaitFor(createReportButton, "Create Report Button");
		
		String processDate = get_table_row_value(HeaderPath, ContentPath, "Process Date", 1).substring(0,10);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		try
		{
			Date date1 = sdf.parse(oParameters.GetParameters("ProcessStartDate"));
			Date date2 = sdf.parse(processDate);
			System.out.println("date1 : " + sdf.format(date1));
			System.out.println("date2 : " + sdf.format(date2));

			if (date1.after(date2) || date2.equals(oParameters.GetParameters("SystemCurrentDate")))
				oReport.AddStepResult("Process Date Records", "Process Date Records Displayed", "PASS");
			else
				oReport.AddStepResult("Process Date Records","Reports are filtered based on Process date but those reports are not Displayed", "FAIL");
		}
		catch(ParseException e)
		{
			System.out.println("Exception Caught" + e);
		}
		clearFilters();
	}

	
	By transmissionDateElement = By.xpath("//li/a[contains(text(),'Transmission Date')]");

	By transmissionDateColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[16]");

	By noFilterResultMessage = By.xpath("//*[@id='report-table-parent']//div[contains(text(),'No results returned.')]");

	
	// Transmission Date Filter Reports
	public void transmissionDateReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		addFilter("Transmission Date");
		click_button("Calendar Start Date", processStartDate);
		waitFor(calendarClose, "Calendar Close button");

		if(IsDisplayed("Populated Calendar", calendarClose))
			oReport.AddStepResult("Populated Calendar", "Populated Calendar Displayed", "PASS");
		else
			oReport.AddStepResult("Populated Calendar",	"clicked on calendar icon but calendar is not populated and date ranges can't be selected ","FAIL");

		performKeyBoardAction("ESC");
		addFilter("Transmission Date");
		oParameters.SetParameters("StartDate", get_specified_date(-14));
		oParameters.SetParameters("SystemCurrentDate", get_specified_date(0));
		enter_text_value("Transmission Start Date", admitStartDate, oParameters.GetParameters("StartDate"));
		enter_text_value("Transmission End Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("TransmissionDate", get_field_value("Transmission Date", transmissionDateColumn));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		try 
		{
			Date date1 = sdf.parse(oParameters.GetParameters("StartDate"));
			Date date2 = sdf.parse(oParameters.GetParameters("TransmissionDate"));
			System.out.println("date1 : " + sdf.format(date1));
			System.out.println("date2 : " + sdf.format(date2));

			if(date1.before(date2) || date1.equals(date2))
				oReport.AddStepResult("Transmission Date Records", "Transmission Date Records Displayed", "PASS");
			else
				oReport.AddStepResult("Transmission Date Records",	"Reports are filtered based on Transmission Date but those reports are not displayed", "FAIL");
		}
		catch (ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		clearFilters();
	}

	
	By responseErrorCodeElement = By.xpath("//li/a[contains(text(),'Response Error Code')]");

	By responseErrorCodeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'responseErrorCode')\"]");

	
	// Response Error Code Filter Reports
	public void responseErrorCodeReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(responseErrorCodeColumn, "Reports Table");
		oParameters.SetParameters("ResponseErrorCodeBeforeFilter",get_field_value("Response Error Code", responseErrorCodeColumn));
		addFilter("Response Error Code");
		enter_text_value("Response Error Code", filtersTextBox,	oParameters.GetParameters("ResponseErrorCodeBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(responseErrorCodeColumn, "Reports Table");
		oParameters.SetParameters("ResponseErrorCodeAfterFilter",get_field_value("Response Error Code", responseErrorCodeColumn));

		if(oParameters.GetParameters("ResponseErrorCodeBeforeFilter").equals(oParameters.GetParameters("ResponseErrorCodeAfterFilter")))
			oReport.AddStepResult("Response Error Code Reports", "Response Error Code Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Response Error Code Reports", "Reports are filtered based on Response Error Code but those reports are not displayed", "FAIL");

		clearFilters();
	}

	
	By responseErrorDescriptionElement = By.xpath("//li/a[contains(text(),'Response Error Description')]");

	By responseErrorDescriptionColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'responseErrorDescription')\"]");

	
	// Response Error Description Filter Reports
	public void responseErrorDescriptionReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(responseErrorDescriptionColumn, "Reports Table");
		oParameters.SetParameters("ResponseErrorDescriptionBeforeFilter",get_field_value("Response Error Description", responseErrorDescriptionColumn));
		addFilter("Response Error Description");
		enter_text_value("Response Error Description", filtersTextBox,oParameters.GetParameters("ResponseErrorDescriptionBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(responseErrorDescriptionColumn, "Reports Table");
		oParameters.SetParameters("ResponseErrorDescriptionAfterFilter",get_field_value("Response Error Description", responseErrorDescriptionColumn));

		if(oParameters.GetParameters("ResponseErrorDescriptionBeforeFilter").equals(oParameters.GetParameters("ResponseErrorDescriptionAfterFilter")))
			oReport.AddStepResult("Response Error Description Reports", "Response Error Description Reports Displayed",	"PASS");
		else
			oReport.AddStepResult("Response Error Description Reports",	"Reports are filtered based on Response Error Description but those reports are not displayed",	"FAIL");

		clearFilters();
	}

	
	By encounterElement = By.xpath("//li/a[contains(text(),'Encounter')]");

	By encounterColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'encounterNbr')\"]");

	By encounterHeader = By.xpath("//*[@id='report-table-parent']//table/thead/tr/td//div[contains(text(),'Encounter')]");

	
	// Encounter Filter Reports
	public void encounterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Encounter", encounterColumn))
		{
			oParameters.SetParameters("EncounterBeforeFilter", get_field_value("Encounter Column", encounterColumn));
			addFilter("Encounter");
			enter_text_value("Encounter Text Box", filtersTextBox, oParameters.GetParameters("EncounterBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			oParameters.SetParameters("EncounterAfterFilter", get_field_value("Encounter Value", encounterColumn));

			if(oParameters.GetParameters("EncounterBeforeFilter").equals(oParameters.GetParameters("EncounterAfterFilter")))
				oReport.AddStepResult("Encounter Reports", "Encounter Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Encounter Reports","Reports are filtered based on Encounter but those reports are not displayed", "FAIL");

			clearFilters();
		} 
		else
		{
			click_button("Encounter Header", encounterHeader);
			waitFor(createReportButton, "create Report Button");
			oParameters.SetParameters("EncounterBeforeFilter", get_field_value("Encounter Column", encounterColumn));
			addFilter("Encounter");
			enter_text_value("Encounter Text Box", filtersTextBox, oParameters.GetParameters("EncounterBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			oParameters.SetParameters("EncounterAfterFilter", get_field_value("Encounter Value", encounterColumn));

			if(oParameters.GetParameters("EncounterBeforeFilter").equals(oParameters.GetParameters("EncounterAfterFilter")))
				oReport.AddStepResult("Encounter Reports", "Encounter Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Encounter Reports", "Reports are filtered based on Encounter but those reports are not displayed", "FAIL");

			clearFilters();
		}
	}

	
	// Comparing downloaded Reports
	public void comparingContractErrorDownloadedReports()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Process Date");

		if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("QA"))
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-7));
		else if(oParameters.GetParameters("ENVIRONMENT").equalsIgnoreCase("DEVCERT"))
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-1000));
		else
			oParameters.SetParameters("ProcessStartDate", get_specified_date(-1000));

		enter_text_value("Process Start Date", admitStartDate, oParameters.GetParameters("ProcessStartDate"));
		enter_text_value("Process End Date", admitEndDate, get_specified_date(-1));// oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		click_button("Create Reports Button", createReportButton);
		waitFor(createReportButton, "create Report Button");
		oParameters.SetParameters("NoOfRecords",get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));

		CreateFolder(oParameters.GetParameters("downloadFilepath"));
		waitFor(downloadCSVButton, "Download CSV Button");

		click_button("Download Button", downloadCSVButton);
		fixed_wait_time(5);

		if(isFileDownloaded("ContractErrorClaimReport.zip"))
		{
			unZipFolder(oParameters.GetParameters("downloadFilepath")+"/ContractErrorClaimReport.zip",oParameters.GetParameters("downloadFilepath"));
			File zipFile = new File(oParameters.GetParameters("downloadFilepath")+"/ContractErrorClaimReport.zip");
			zipFile.delete();
			csvToExcel();
			@SuppressWarnings("static-access")
			int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
			oParameters.SetParameters("RecordsInExcel", String.valueOf(recordsCount));

			if(oParameters.GetParameters("NoOfRecords").equals(oParameters.GetParameters("RecordsInExcel")))
				oReport.AddStepResult("Downloaded Records", "Downloaded Records Matched", "PASS");
			else
				oReport.AddStepResult("Downloaded Records ", "Records downloaded in excel file but number of record in portal Not Matched with number of records in downloaded excel file",	"FAIL");
		}
		else
			oReport.AddStepResult("Downloaded File", "Clicked on 'Donwload CSV' button but zip file is not downloaded ","FAIL");
	}

	
	// Contract Error Claim Report VR
	public void contractErrorClaimReport_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigate_to_Analytics();
		select_Report("Contract Error Claim Report");
		claimIDReports();
		serviceFacilityMultiSelectWindow();
		multiSelectWindowValidation();
		claimTypeReports("Institutional");
		clearFilters();
		claimTypeReports("Professional");
		originReports("Original Repricing,On-Demand Repricing,Batch Repricing");
		patientNameReports();
		processDateFilterReports();
		transmissionDateReports();
		responseErrorCodeReports();
		responseErrorDescriptionReports();
		rateSheetValidation();
		resultsPerPage("100");
		pageNavigations();
		createBookmark();
		encounterReports();
		bookmarkValidation();
		sortingResults();
		settingsWindow();
		temporarilyChangedSettings();
		portalLogout();
		login("EDIT");
		changePricingEngine();
		navigate_to_Analytics();
		select_Report("Contract Error Claim Report");
		defaultColumnsCheck();
		defaultSettings();
		temporarySettings();
		resetToMyDefaultSettings();
		resetToSystemDefaults();
		fullScreenValidation();
		navigatingToOtherReport("Contract Listing Report","Contract Error Claim Report");
		comparingContractErrorDownloadedReports();
		logout();
	}

	
	// Contract Listing Report VR

	
	By applyFilterMessage = By.xpath("//*[@id='report-table-parent']//div[2][contains(.,'Apply a filter or click Create Report.')]");
	
	By payerNameElement = By.xpath("//li//a[contains(text(),'Payer Name')]");

	By payerNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'payerName')\"]");

	
	// Payer Name Filter Reports
	public void payerNameReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		oParameters.SetParameters("PayerNameBeforeFilter", get_field_value("Payer Name Column", payerNameColumn));
		addFilter("Payer Name");
		enter_text_value("Payer Name Search Box", filtersTextBox, oParameters.GetParameters("PayerNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("PayerNameAfterFilter", get_field_value("Payer Name Column", payerNameColumn));

		if(oParameters.GetParameters("PayerNameBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("PayerNameAfterFilter"))&& IsElementEnabled("Download CSV Button", downloadCSVButton))
			oReport.AddStepResult("Payer Name Reports", "Payer Name Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Payer Name Reports",	"Reports filterd based on Payer Name but that records are not displayed and download icon aslo not enabled.","FAIL");

		clearFilters();
		createReport();

		if(IsDisplayed("No. Of Records", noOfRecordsElement))
			oReport.AddStepResult("Results after clear filters", "Results after clear filters Displayed", "PASS");
		else
			oReport.AddStepResult("Results after clear filters","Cleared filters but complete report not displayed ", "FAIL");
	}

	
	By workflowStatusElement = By.xpath("//li//a[contains(text(),'Workflow Status')]");

	By workStatusDraft = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),'Draft')]");

	By workflowColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'description')\"]");

		
	// Work flow Status Filter Reports
	public void workflowStatusReports(String status) 
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = status.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for (int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}			
			
			addFilter("Workflow Status");
			selectMultipleCheckBoxes(word);
			createReport();
			oParameters.SetParameters("WorkFlowStatusAfterFilters", get_field_value("WorkFlow Status Column", workflowColumn));

			if(IsDisplayed("No result returned message", noFilterResultMessage))
				oReport.AddStepResult("Workflow Status Reports", "Reports are filtered based on Workflow Status "+word+" but those reports are not displayed", "FAIL");		
			else if(oParameters.GetParameters("WorkflowStatus").contains(oParameters.GetParameters("WorkFlowStatusAfterFilters")))
				oReport.AddStepResult("Workflow Status Reports", "Reports are filtered based on Workflow Status, verified that Workflow Status reports are displayed", "PASS");
			else
				oReport.AddStepResult("Workflow Status Reports","Reports are filtered based on Workflow Status "+word+" but those reports are not displayed", "FAIL");				
        	
			clearFilters();
		}		
	}		


	By contractedElement = By.xpath("//li//a[contains(text(),'Contracted')]");

	By contractedYes = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),'Yes')]");

	By contractedColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'contracted')\"]");

	
	// Contracted Reports
	public void contractedReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Contracted");
		click_button("Contracted Status Yes Option", contractedYes);
		createReport();
		oParameters.SetParameters("ContractedStatus", get_field_value("Contracted Status Column", contractedColumn));

		if(oParameters.GetParameters("ContractedStatus").equalsIgnoreCase(oParameters.GetParameters("ContractedYes")))
			oReport.AddStepResult("Contracted Status Reports", "Contracted Status Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Contracted Status Reports","Reports are filtered based on Contracted Status but those reports are not Displayed", "FAIL");
	}

	
	// Contract Name Filter Reports
	public void contractNameReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Contract Name with Contains

		clearFilters();
		createReport();
		waitFor(contractNameColumn, "Reports Table");
		oParameters.SetParameters("ContractNameBeforeFilter", get_field_value("Contract Name", contractNameColumn));
		addFilter("Contract Name");
		click_button("Contract Name Operators Button", billTypeOperators);
		click_button("Contract Name Contains Operator", billTypeContainsOperator);
		enter_text_value("Filters Input Text Box", filtersTextBox,oParameters.GetParameters("ContractNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(contractNameColumn, "Reports Table");
		oParameters.SetParameters("ContractNameAfterFilter", get_field_value("Contract Name", contractNameColumn));

		if(oParameters.GetParameters("ContractNameBeforeFilter").equals(oParameters.GetParameters("ContractNameAfterFilter")))
			oReport.AddStepResult("Contract Name Contains Result", "Contract Name Contains Result Displayed", "PASS");
		else
			oReport.AddStepResult("Contract Name Contains Result",	"Reports are filtered based on Contract Name with Contains Operator but those reports are not Displayed","FAIL");

		// Contract Name with Exact

		click_button("Contract Name Operators Button", billTypeOperators);
		click_button("Contract Name Exact Operator", billTypeExactOperator);
		createReport();
		oParameters.SetParameters("ContractNameExact", get_field_value("Contract Name", contractNameColumn));

		if(oParameters.GetParameters("ContractNameBeforeFilter").equals(oParameters.GetParameters("ContractNameExact")))
			oReport.AddStepResult("Contract Name Exact Result", "Contract Name Exact Result Displayed", "PASS");
		else
			oReport.AddStepResult("Contract Name Exact Result",	"Reports are filtered based on Contract Name with Exact Operator but those reports are not Displayed","FAIL");

		// Contract Name With Not Exact

		click_button("Contract Name Operators Button", billTypeOperator);
		click_button("Contract Name Not Exact Operator", billTypeNotExactOperator);
		createReport();
		oParameters.SetParameters("ContractNameNotExact", get_field_value("Contract Name", contractNameColumn));

		if(oParameters.GetParameters("ContractNameBeforeFilter").equals(oParameters.GetParameters("ContractNameNotExact")))
			oReport.AddStepResult("Contract Name Not Exact Result",	"Reports are filtered based on Contract Name with not Exact Operator but those reports are not Displayed","FAIL");
		else
			oReport.AddStepResult("Contract Name Not Exact Result", "Contract Name Not Exact Result Displayed", "PASS");

		// Contract Name With Does Not Contain

		click_button("Contract Name Operators Button", billTypeOperator);
		click_button("Contract Name Does Not Contain Operator", billTypeDoesNotContain);
		createReport();
		oParameters.SetParameters("ContractNameNotContain", get_field_value("Contract Name", contractNameColumn));

		if(oParameters.GetParameters("ContractNameBeforeFilter").equals(oParameters.GetParameters("ContractNameNotContain")))
			oReport.AddStepResult("Contract Name Does Not Contain Result","Reports are filtered based on Contract Name with Does not Contain Operator but those reports are not Displayed",	"FAIL");
		else
			oReport.AddStepResult("Contract Name Does Not Contain Result",	"Contract Name Does Not Contain Result Displayed", "PASS");

		clearFilters();
	}

	
	By regionElement = By.xpath("//li//a[contains(text(),'Region')]");

	By regionHeader = By.xpath("//div[@class='heading ng-scope'][contains(text(),'Region')]");

	By regionColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'region')\"]");

	
	// Region Filter Reports
	public void regionReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if (IsDisplayed("Region Column", regionColumn)) 
		{
			oParameters.SetParameters("RegionBeforeFilter", get_field_value("Region Column", regionColumn));
			addFilter("Region");
			enter_text_value("Region Search Box", filtersTextBox, oParameters.GetParameters("RegionBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			oParameters.SetParameters("RegionAfterFilter", get_field_value("Region Column", regionColumn));

			if(oParameters.GetParameters("RegionBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("RegionAfterFilter")))
				oReport.AddStepResult("Region Reports", "Region Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Region Reports",	"Reports are filtered based on Region but those reports are not displayed", "FAIL");
		} 
		else 
		{
			click_button("Region Header", regionHeader);
			waitFor(regionColumn, "Reports Table");
			oParameters.SetParameters("RegionBeforeFilter", get_field_value("Region Column", regionColumn));
			addFilter("Region");
			enter_text_value("Region Search Box", filtersTextBox, oParameters.GetParameters("RegionBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			oParameters.SetParameters("RegionAfterFilter", get_field_value("Region Column", regionColumn));

			if(oParameters.GetParameters("RegionBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("RegionAfterFilter")))
				oReport.AddStepResult("Region Reports", "Region Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Region Reports",	"Reports are filtered based on Region but those reports are not displayed", "FAIL");
		}
	}

	
	By contatctNameHeader = By.xpath("//*[@title='Contract Name']"); 
	
	
	// Contract Name Link Validation
	public void contractLinkValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clearFilters();
		createReport();
		click_button("ContractNameTableHeader", contatctNameHeader);
		fixed_wait_time(5);
		oParameters.SetParameters("ContractNameBeforeFilter", get_field_value("Contract Name", contractNameColumn));
		click_button("Contract Name", contractNameColumn);
		waitFor(contractorName, "Contract Title bar");

		if(IsDisplayed("Error Message", errorMessage)) 
		{
			oReport.AddStepResult("Error Message",	"Clicked on Contract Name, page navigated to Contract plugin but Displaying Error Message and that selected contract is not Displayed ","FAIL");
			click_button("Error Message Close Icon", errorMessageClose);
		}
		else
		{
			oParameters.SetParameters("ContractNameAfterNavigation", get_field_value("Contract Name", contractorName));

			if(IsDisplayed("Contracts", contractsPlugIn)&&oParameters.GetParameters("ContractNameBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractNameAfterNavigation")))
				oReport.AddStepResult("Selected Contract", "Selected Contract Displayed", "PASS");
			else
				oReport.AddStepResult("Selected Contract",	"Clicked on Contract Name  but that selected contract doesn't navigated to Contract plugin and Not displayed","FAIL");
		}
		waitFor(analyticsSuite, "Analytics Suite");
		navigate_to("Analytics Suite", "Reports PlugIn", analyticsSuite, reportsPlugIn);
		waitFor(createReportButton, "create Report Button");

		if(IsDisplayed("Contractors Name", contractNameColumn))
			oReport.AddStepResult("Previous Reports", "Previous Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Previous Reports", "Clicked on Analytics but it doesn't navigated and Contract name Not displayed", "FAIL");
	}

	
	// Contract Name Filter Reports
	public void contractNameFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();

		if(IsDisplayed("Create Report Button", createReportButton)) 
		{}
		else
			oReport.AddStepResult("", "Loading icon is still present, so report is not yet available for further processing", "WARNING");

		waitFor(createReportButton, "create Report Button");
		oParameters.SetParameters("ContractNameBeforeFilter", get_field_value("Contract Name", contractNameColumn));
		addFilter("Contract Name");
		enter_text_value("Contract Name ", filtersTextBox, oParameters.GetParameters("ContractNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		fixed_wait_time(3);
		waitFor(createReportButton, "create Report Button");
		oParameters.SetParameters("ContractNameAfterFilter", get_field_value("Contract Name", contractNameColumn));

		if(oParameters.GetParameters("ContractNameBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractNameAfterFilter")))
			oReport.AddStepResult("Contractors Name", "Contractors Name Displayed", "PASS");
		else
			oReport.AddStepResult("Contractors Name", "Reports are filtered based on Contract Name but those reports are not displayed", "FAIL");
	}

	
	// Comparing Contract Listing Downloaded Reports
	public void comparingContractListingDownloadedReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("PayerName", get_field_value("Payer Name Column", payerNameColumn));
		addFilter("Payer Name");
		enter_text_value("Payer Name Search Box", filtersTextBox, oParameters.GetParameters("PayerName"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("NoOfRecords",get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));

		CreateFolder(oParameters.GetParameters("downloadFilepath"));
		waitFor(downloadCSVButton, "Download CSV Button");

		click_button("Download Button", downloadCSVButton);
		fixed_wait_time(5);

		if(isFileDownloaded("ContractListingReport.zip")) 
		{
			unZipFolder(oParameters.GetParameters("downloadFilepath")+"/ContractListingReport.zip",oParameters.GetParameters("downloadFilepath"));
			File zipFile = new File(oParameters.GetParameters("downloadFilepath")+"/ContractListingReport.zip");
			zipFile.delete();
			csvToExcel();
			@SuppressWarnings("static-access")
			int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
			oParameters.SetParameters("RecordsInExcel", String.valueOf(recordsCount));

			if(oParameters.GetParameters("NoOfRecords").equals(oParameters.GetParameters("RecordsInExcel")))
				oReport.AddStepResult("Downloaded Records", "Downloaded Records Matched", "PASS");
			else
				oReport.AddStepResult("Downloaded Records ", "Records downloaded in excel file but number of record in portal Not Matched with number of records in downloaded excel file",	"FAIL");
		}
		else
			oReport.AddStepResult("Downloaded File", "Clicked on 'Donwload CSV' button but zip file is not downloaded ", "FAIL");
	}

	
	// Contract Listing Report VR Methods
	public void contractListingReport_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigate_to_Analytics();
		select_Report("Contract Listing Report");
		payerNameReports();
		workflowStatusReports("Active,Draft,Inactive");
		contractedReports();
		contractNameReports();
		regionReports();
		contractLinkValidation();
		resultsPerPage("100");
		pageNavigations();
		createBookmark();
		contractNameFilterReports();
		bookmarkValidation();
		settingsWindow();
		temporaryChangesInSettings();
		portalLogout();
		login("EDIT");
		changePricingEngine();
		navigate_to_Analytics();
		select_Report("Contract Listing Report");
		createReportButton();
		defaultColumnsValidation();
		defaultChangesInSetting();
		resetToDefaults();
		fullScreenValidation();
		navigatingToOtherReport("Claim Repricing Report","Contract Listing Report");
		comparingContractListingDownloadedReports();
		logout();
	}

	
	// RateSheet Association Report VR

	
	By rateSheetAssociationReport = By.xpath("//div[@class='styled-dropdown black-text-when-disabled ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Rate Sheet Association Report')]");

	By rateSheetAssociationReportDC = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Rate Sheet Association Report')]");
	
	By healthPlanElement = By.xpath("//li//a[text()='Health Plan']");

	By healthPlanColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'healthPlanAlias')\"]");

	
	// Navigate to Last and Previous page and to get the first element value
	public void navigatingToLastandPrevPages(String lastPageParameter, String prevPageParameter, String fieldName,By elemDesc) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Last Page Button", lastPageButton);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters(lastPageParameter, get_field_value(fieldName, elemDesc));
		click_button("Prev Page Button", prevPageButton);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters(prevPageParameter, get_field_value(fieldName, elemDesc));
	}

	
	// Health Plan Filter Reports
	public void healthPlanReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("HealthPlanBeforeFilter", get_field_value("Health Plan", healthPlanColumn));
		addFilter("Health Plan");
		enter_text_value("Health Plan Search Box", filtersTextBox, oParameters.GetParameters("HealthPlanBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("HealthPlanAfterFilter", get_field_value("Health Plan", healthPlanColumn));

		if(IsDisplayed("Results Last Page", lastPageButton)) 
		{
			navigatingToLastandPrevPages("HealthPlanLastPage", "HealthPlanPrevPage", "Health Plan", healthPlanColumn);

			if(oParameters.GetParameters("HealthPlanBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("HealthPlanAfterFilter"))&& oParameters.GetParameters("HealthPlanBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("HealthPlanLastPage"))&& oParameters.GetParameters("HealthPlanBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("HealthPlanPrevPage")))
				oReport.AddStepResult("Health Plan Reports", "Health Plan Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Health Plan Reports", "Reports are filtered based on Health Plan but those reports are not displayed ","FAIL");
		}
		else
		{
			if(oParameters.GetParameters("HealthPlanBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("HealthPlanAfterFilter")))
				oReport.AddStepResult("Health Plan Reports", "Health Plan Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Health Plan Reports", "Reports are filtered based on Health Plan but those reports are not displayed","FAIL");
		}
	}

	
	By collapsedButton = By.xpath("//span[@ng-click=\"expandRow('0' , row, 'providerDescription')\"]//i[@class='fa fa-caret-down expanded']");

	By expandAllGroups = By.xpath("//span[text()='Expand all groups']");

	By expandButton = By.xpath("//span[@ng-click=\"expandRow('0' , row, 'providerDescription')\"]//i[@class='fa fa-caret-right collapsed']");

	By collapseAllGroups = By.xpath("//a[@class='pull-left button hand-cursor link-btn hand-cursor ng-isolate-scope']//span[contains(.,'Collapse all groups')]");

	By collapsedButton1 = By.xpath("//span[@ng-click=\"expandRow('1' , row, 'providerDescription')\"]//i[@class='fa fa-caret-down expanded']");

	By expandButton1 = By.xpath("//span[@ng-click=\"expandRow('2' , row, 'providerDescription')\"]//i[@class='fa fa-caret-right collapsed']");

	
	// Expand all and Collapse all groups Validation
	public void expandCollapseGroupsValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clearFilters();
		createReport();
		waitFor(expandAllGroups, "Expand All Groups link");
		fixed_wait_time(10);
		click_button("Expand All Groups", expandAllGroups);
		fixed_wait_time(2);

		if(IsDisplayed("Collapse Button", collapsedButton)&& IsDisplayed("Collapse All Groups", collapseAllGroups))
			oReport.AddStepResult("Expanded Groups", "Expanded Groups Displayed", "PASS");
		else
			oReport.AddStepResult("Expanded Groups","Clicked on Expand all groups link but all groups are not expanded and Collapse all groups link is not enabled","FAIL");

		click_button("Collapse All Groups", collapseAllGroups);

		if(IsDisplayed("Expand Button", expandButton)&& IsDisplayed("Expand All Groups", expandAllGroups))
			oReport.AddStepResult("Collapsed Groups", "Collapsed Groups Displayed", "PASS");
		else
			oReport.AddStepResult("Collapsed Groups", "Clicked on Collapse all groups link but all groups are not collapsed and Expand all groups link is not enabled ","FAIL");

		// Expanding First link

		click_button("First Link", serviceProviderColumn);
		scroll_to_element(expandButton1, serviceProviderColumn);

		if(IsDisplayed("Expanded Second Link", collapsedButton1))
			oReport.AddStepResult("Expanded First Link","Mouse Hovered and clicked on first group but that group is not expanded", "FAIL");
		else
			oReport.AddStepResult("Expanded First Link", "Expanded First Link Displayed", "PASS");

		// Collapsing first Link

		click_button("First Link", serviceProviderColumn);

		if(IsDisplayed("Expand First Link", expandButton))
			oReport.AddStepResult("Collapsed First Link", "Collapsed First Link Displayed", "PASS");
		else
			oReport.AddStepResult("Collapsed First Link", "Clicked on first group but that group is not Collapsed",	"FAIL");
	}

	
	By healthPlanDescriptionElement = By.xpath("//li//a[text()='Health Plan Description']");

	By healthPlanDescriptionColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'healthPlanDescription')\"]");

	By healthPlanDescriptionHeader = By.xpath("//*[@id='report-table-parent']//table/thead/tr/td//div[contains(text(),'Health Plan Description')]");

	
	// Health Plan Description Filter Reports
	public void healthPlanDescriptionReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clearFilters();
		createReport();
		waitFor(healthPlanDescriptionColumn, "Reports Table");
		addFilter("Health Plan Description");
		enter_text_value("Health Plan Description Search Box", filtersTextBox,oParameters.GetParameters("HealthPlanDescriptionForFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(healthPlanDescriptionColumn, "Reports Table");
		oParameters.SetParameters("HealthPlanDescriptionAfterFilter",get_field_value("Health Plan Description", healthPlanDescriptionColumn));

		if(IsDisplayed("Results Last Page", lastPageButton)) 
		{
			navigatingToLastandPrevPages("HealthPlanDescriptionLastPage", "HealthPlanDescriptionPrevPage","Health Plan Description", healthPlanDescriptionColumn);

			if(oParameters.GetParameters("HealthPlanDescriptionForFilter").equalsIgnoreCase(oParameters.GetParameters("HealthPlanDescriptionAfterFilter"))&& oParameters.GetParameters("HealthPlanDescriptionForFilter").equalsIgnoreCase(oParameters.GetParameters("HealthPlanDescriptionLastPage"))&& oParameters.GetParameters("HealthPlanDescriptionForFilter").equalsIgnoreCase(oParameters.GetParameters("HealthPlanDescriptionPrevPage")))
				oReport.AddStepResult("Health Plan Description Reports", "Health Plan Description Reports Displayed","PASS");
			else
				oReport.AddStepResult("Health Plan Description Reports", "Reports are filtered based on Health Plan Description but those reports are not Displayed", "FAIL");
		} 
		else
		{
			if(oParameters.GetParameters("HealthPlanDescriptionForFilter").equalsIgnoreCase(oParameters.GetParameters("HealthPlanDescriptionAfterFilter")))
				oReport.AddStepResult("Health Plan Description Reports", "Health Plan Description Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Health Plan Description Reports", "Reports are filtered based on Health Plan Description but those reports are not Displayed", "FAIL");
		}
	}

	
	By serviceProviderElement = By.xpath("//li//a[text()='Service Provider']");

	By serviceProviderColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'providerDescription')\"]");

	By serviceProviderData = By.xpath("//div[@id='report-table-parent']//tr[1][@class='ng-scope show-group']//td[3]//ul[@class='provider-groups']/li[1]");

	
	// Service Provider Filter Reports
	public void serviceProviderReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clearFilters();
		createReport();
		fixed_wait_time(3);
		click_button("Service Provider", serviceProviderColumn);
		waitFor(serviceProviderData, "Reports Table");
		oParameters.SetParameters("ServiceProviderBeforeFilter",get_field_value("Service Provider", serviceProviderData));
		addFilter("Service Provider");
		enter_text_value("Service Provider Search Box", filtersTextBox,oParameters.GetParameters("ServiceProviderBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		click_button("Service Provider", serviceProviderColumn);
		oParameters.SetParameters("ServiceProviderAfterFilter",	get_field_value("Service Provider", serviceProviderData));

		if(oParameters.GetParameters("ServiceProviderBeforeFilter").contains(oParameters.GetParameters("ServiceProviderAfterFilter"))) 
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Service Provider Reports", "Service Provider Reports Displayed", "PASS");
		}
		else
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Service Provider Reports","Reports are filtered based on Service Provider but those reports are not Displayed", "FAIL");
		}
	}

	
	By serviceProviderNPIElement = By.xpath("//li//a[text()='Service Provider NPI']");

	By serviceProviderNPIColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'providerAlias')\"]");

	By serviceProviderNPIData = By.xpath("//div[@id='report-table-parent']//tr[1][@class='ng-scope show-group']//td[4]//ul[@class='provider-groups']/li[1]");

	
	// Service Provider NPI Filter Reports
	public void serviceProviderNPIReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clearFilters();
		createReport();
		fixed_wait_time(3);
		click_button("Service Provider NPI", serviceProviderColumn);
		oParameters.SetParameters("ServiceProviderNPIBeforeFilter",	get_field_value("Service Provider NPI", serviceProviderNPIData));
		addFilter("Service Provider NPI");
		enter_text_value("Service Provider NPI Search Box", filtersTextBox,	oParameters.GetParameters("ServiceProviderNPIBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(serviceProviderColumn, "Reports Table");
		click_button("Service Provider NPI", serviceProviderColumn);
		oParameters.SetParameters("ServiceProviderNPIAfterFilter",get_field_value("Service Provider NPI", serviceProviderNPIData));

		if(oParameters.GetParameters("ServiceProviderNPIBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ServiceProviderNPIAfterFilter"))) 
		{
			waitFor(serviceProviderNPIData, "Reports Table");
			oReport.AddStepResult("Service Provider NPI Reports", "Service Provider NPI Reports Displayed", "PASS");
		}
		else
		{
			waitFor(serviceProviderNPIData, "Reports Table");
			oReport.AddStepResult("Service Provider NPI Reports","Reports are filtered based on Service Provider NPI but those reports are not Displayed", "FAIL");
		}
	}
	

	By taxonomyElement = By.xpath("//li//a[text()='Taxonomy']");

	By taxonomyColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'taxonomyAlias')\"]");

	By taxonomyHeader = By.xpath("//*[@id='report-table-parent']//table/thead/tr/td//div[contains(text(),'Taxonomy')]");

	
	// Taxonomy Filter Reports
	public void taxonomyReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		clearFilters();
		createReport();

		if(IsDisplayed("Taxonomy Column", taxonomyColumn)) 
		{
			oParameters.SetParameters("TaxonomyBeforeFilter", get_field_value("Taxonomy Column", taxonomyColumn));
			addFilter("Taxonomy");
			enter_text_value("Taxonomy Search Box", filtersTextBox, oParameters.GetParameters("TaxonomyBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			oParameters.SetParameters("TaxonomyAfterFilter", get_field_value("Taxonomy Column", taxonomyColumn));

			if(IsDisplayed("Results Last Page", lastPageButton)) 
			{
				navigatingToLastandPrevPages("TaxonomyLastPage", "TaxonomyPrevPage", "Taxonomy", taxonomyColumn);

				if(oParameters.GetParameters("TaxonomyBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("TaxonomyAfterFilter"))&& oParameters.GetParameters("TaxonomyBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("TaxonomyLastPage"))&& oParameters.GetParameters("TaxonomyBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("TaxonomyPrevPage")))
					oReport.AddStepResult("Taxonomy Reports", "Taxonomy Reports Displayed", "PASS");
				else
					oReport.AddStepResult("Taxonomy Reports","Reports are filtered based on Taxonomy but those reports are not Displayed", "FAIL");
			}
			else
			{
				if(oParameters.GetParameters("TaxonomyBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("TaxonomyAfterFilter")))
					oReport.AddStepResult("Taxonomy Reports", "Taxonomy Reports Displayed", "PASS");
				else
					oReport.AddStepResult("Taxonomy Reports", "Reports are filtered based on Taxonomy but those reports are not Displayed", "FAIL");
			}
		} 
		else 
		{
			click_button("Taxonomy Header", taxonomyHeader);
			waitFor(taxonomyHeader, "Reports Table");
			oParameters.SetParameters("TaxonomyBeforeFilter", get_field_value("Taxonomy Column", taxonomyColumn));
			addFilter("Taxonomy");
			enter_text_value("Taxonomy Search Box", filtersTextBox, oParameters.GetParameters("TaxonomyBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			oParameters.SetParameters("TaxonomyAfterFilter", get_field_value("Taxonomy Column", taxonomyColumn));

			if(IsDisplayed("Results Last Page", lastPageButton)) 
			{
				navigatingToLastandPrevPages("TaxonomyLastPage", "TaxonomyPrevPage", "Taxonomy", taxonomyColumn);

				if(oParameters.GetParameters("TaxonomyBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("TaxonomyAfterFilter"))&& oParameters.GetParameters("TaxonomyBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("TaxonomyLastPage"))&& oParameters.GetParameters("TaxonomyBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("TaxonomyPrevPage")))
					oReport.AddStepResult("Taxonomy Reports", "Taxonomy Reports Displayed", "PASS");
				else
					oReport.AddStepResult("Taxonomy Reports","Reports are filtered based on Taxonomy but those reports are not Displayed", "FAIL");
			} 
			else
			{
				if(oParameters.GetParameters("TaxonomyBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("TaxonomyAfterFilter")))
					oReport.AddStepResult("Taxonomy Reports", "Taxonomy Reports Displayed", "PASS");
				else
					oReport.AddStepResult("Taxonomy Reports", "Reports are filtered based on Taxonomy but those reports are not Displayed", "FAIL");
			}
		}
	}

	
	By ContractManagerHeaderpath = By.xpath("//*[@title='Contract Manager']");
	
	
	// Contract Manager Filter Reports
	public void contractManagerFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Contract Name with Contains

		clearFilters();
		createReport();
		click_button("Contatct Manager Header", ContractManagerHeaderpath);
		fixed_wait_time(5); 
		waitFor(contractManagerColumn, "Reports Table");
		oParameters.SetParameters("ContractManagerBeforeFilter",get_field_value("Contract Manager", contractManagerColumn));
		addFilter("Contract Manager");
		click_button("Contract Manager Operators Button", billTypeOperators);
		click_button("Contract Manager Contains Operator", billTypeContainsOperator);
		enter_text_value("Filters Input Text Box", filtersTextBox,oParameters.GetParameters("ContractManagerBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(contractNameColumn, "Reports Table");
		oParameters.SetParameters("ContractManagerAfterFilter",	get_field_value("Contract Manager", contractManagerColumn));

		if(IsDisplayed("Results Last Page", lastPageButton))
		{
			navigatingToLastandPrevPages("ContractManagerLastPage", "ContractManagerPrevPage", "Contract Manager",contractManagerColumn);

			if(oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerAfterFilter"))&& oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerLastPage"))&& oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerPrevPage")))
				oReport.AddStepResult("Contract Manager Contains Result", "Contract Manager Contains Result Displayed","PASS");
			else
				oReport.AddStepResult("Contract Manager Contains Result","Reports are filtered with Contract Manager Contains Operator but those reports are not Displayed","FAIL");
		}
		else
		{
			if(oParameters.GetParameters("ContractManagerBeforeFilter").equals(oParameters.GetParameters("ContractManagerAfterFilter")))
				oReport.AddStepResult("Contract Manager Contains Result", "Contract Manager Contains Result Displayed",	"PASS");
			else
				oReport.AddStepResult("Contract Manager Contains Result", "Reports are filtered with Contract Manager Contains Operator but those reports are not Displayed","FAIL");
		}

		// Contract Name with Exact

		click_button("Contract Manager Operators Button", billTypeOperators);
		click_button("Contract Manager Exact Operator", billTypeExactOperator);
		createReport();
		oParameters.SetParameters("ContractManagerExact", get_field_value("Contract Manager", contractManagerColumn));

		if(IsDisplayed("Results Last Page", lastPageButton)) 
		{
			navigatingToLastandPrevPages("ContractManagerLastPage", "ContractManagerPrevPage", "Contract Manager",contractManagerColumn);

			if(oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerExact"))&& oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerLastPage"))&& oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerPrevPage")))
				oReport.AddStepResult("Contract Manager Exact Result", "Contract Manager Exact Result Displayed","PASS");
			else
				oReport.AddStepResult("Contract Manager Exact Result", "Reports are filtered with Contract Manager Exact Operator but those reports are not Displayed",	"FAIL");
		} 
		else
		{
			if(oParameters.GetParameters("ContractManagerBeforeFilter").equals(oParameters.GetParameters("ContractManagerExact")))
				oReport.AddStepResult("Contract Manager Exact Result", "Contract Manager Exact Result Displayed","PASS");
			else
				oReport.AddStepResult("Contract Manager Exact Result",	"Reports are filtered with Contract Manager Exact Operator but those reports are not Displayed","FAIL");
		}

		// Contract Name With Not Exact

		click_button("Contract Manager Operators Button", billTypeOperator);
		click_button("Contract Manager Not Exact Operator", billTypeNotExactOperator);
		createReport();
		oParameters.SetParameters("ContractManagerNotExact",get_field_value("Contract Manager", contractManagerColumn));

		if(IsDisplayed("Results Last Page", lastPageButton)) 
		{
			navigatingToLastandPrevPages("ContractManagerLastPage", "ContractManagerPrevPage", "Contract Manager",contractManagerColumn);

			if(oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerNotExact")) || oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerLastPage")) || oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerPrevPage")))
				oReport.AddStepResult("Contract Manager Not Exact Result", "Reports are filtered with Contract Manager not Exact Operator but those reports are not Displayed",	"FAIL");
			else
				oReport.AddStepResult("Contract Manager Not Exact Result", "Contract Manager Not Exact Result Displayed", "PASS");
		} 
		else
		{
			if(oParameters.GetParameters("ContractManagerBeforeFilter").equals(oParameters.GetParameters("ContractManagerNotExact")))
				oReport.AddStepResult("Contract Manager Not Exact Result", "Reports are filtered with Contract Manager not Exact Operator but those reports are not Displayed",	"FAIL");
			else
				oReport.AddStepResult("Contract Manager Not Exact Result",	"Contract Manager Not Exact Result Displayed", "PASS");
		}

		// Contract Name With Does Not Contain

		click_button("Contract Manager Operators Button", billTypeOperator);
		click_button("Contract Manager Does Not Contain Operator", billTypeDoesNotContain);
		createReport();
		oParameters.SetParameters("ContractManagerNotContain",get_field_value("Contract Manager", contractManagerColumn));

		if(IsDisplayed("Results Last Page", lastPageButton)) 
		{
			navigatingToLastandPrevPages("ContractManagerLastPage", "ContractManagerPrevPage", "Contract Manager",contractManagerColumn);

			if(oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerNotContain")) || oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerLastPage")) || oParameters.GetParameters("ContractManagerBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("ContractManagerPrevPage")))
				oReport.AddStepResult("Contract Manager Does Not Contain Result", "Reports are filtered with Contract Manager Does not Contain Operator but those reports are not Displayed", "FAIL");
			else
				oReport.AddStepResult("Contract Manager Does Not Contain Result", "Contract Manager Does Not Contain Result Displayed", "PASS");
		} 
		else
		{
			if(oParameters.GetParameters("ContractManagerBeforeFilter").equals(oParameters.GetParameters("ContractManagerNotContain")))
				oReport.AddStepResult("Contract Manager Does Not Contain Result","Reports are filtered with Contract Manager Does not Contain Operator but those reports are not Displayed","FAIL");
			else
				oReport.AddStepResult("Contract Manager Does Not Contain Result", "Contract Manager Does Not Contain Result Displayed", "PASS");
		}
		clearFilters();
	}

	
	By rateSheetElement = By.xpath("//li//a[text()='Rate Sheet']");

	
	// Rate Sheet Filter Reports
	public void rateSheetReports()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();

		if(IsDisplayed("Rate Sheet", rateSheetColumn)) 
		{
			oParameters.SetParameters("RateSheetBeforeFilter", get_field_value("Rate Sheet Column", rateSheetColumn));
			addFilter("Rate Sheet");
			enter_text_value("Rate Sheet Search", filtersTextBox, oParameters.GetParameters("RateSheetBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			waitFor(rateSheetColumn, "Reports Table");
			oParameters.SetParameters("RateSheetAfterFilter", get_field_value("Rate Sheet Column", rateSheetColumn));
		} 
		else
		{
			click_button("Rate Sheet Header", rateSheetColumnHeader);
			waitFor(rateSheetColumn, "Reports Table");
			oParameters.SetParameters("RateSheetBeforeFilter", get_field_value("Rate Sheet Column", rateSheetColumn));
			addFilter("Rate Sheet");
			enter_text_value("Rate Sheet Search", filtersTextBox, oParameters.GetParameters("RateSheetBeforeFilter"));
			performKeyBoardAction("ENTER");
			createReport();
			waitFor(rateSheetColumn, "Reports Table");
			oParameters.SetParameters("RateSheetAfterFilter", get_field_value("Rate Sheet Column", rateSheetColumn));
		}
		if(IsDisplayed("Results Last Page", lastPageButton)) 
		{
			navigatingToLastandPrevPages("RateSheetLastPage", "RateSheetPrevPage", "Rate Sheet", rateSheetColumn);

			if(oParameters.GetParameters("RateSheetBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("RateSheetAfterFilter"))&& oParameters.GetParameters("RateSheetBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("RateSheetLastPage"))&& oParameters.GetParameters("RateSheetBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("RateSheetPrevPage")))
				oReport.AddStepResult("Rate Sheet Reports", "Rate Sheet filter Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Rate Sheet Reports",	"Reports are filtered based on Rate Sheet but those reports are not Displayed", "FAIL");
		} 
		else
		{
			if(oParameters.GetParameters("RateSheetBeforeFilter").equalsIgnoreCase(oParameters.GetParameters("RateSheetAfterFilter")))
				oReport.AddStepResult("Rate Sheet Reports", "Rate Sheet filter Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Rate Sheet Reports",	"Reports are filtered based on Rate Sheet but those reports are not Displayed", "FAIL");
		}
	}

	
	// Contract Name Link Validation
	public void contractNameValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("ContractNameBeforeNavigation", get_field_value("Contract Name", contractNameColumn));
		click_button("Contract Name", contractNameColumn);
		waitFor(contractsPlugIn, "Contracts PlugIn");

		if(IsDisplayed("Error Message", errorMessage)) 
		{
			oReport.AddStepResult("Error Message", "Clicked on Contract Name, page navigated to Contract plugin but Displaying Error Message and that selected contract is not Displayed ",	"FAIL");
			click_button("Error Message Close Icon", errorMessageClose);
		} 
		else
		{
			oParameters.SetParameters("ContractNameAfterNavigation", get_field_value("Contract Name", contractorName));

			if(IsDisplayed("Contracts", contractsPlugIn)&& oParameters.GetParameters("ContractNameBeforeNavigation").equalsIgnoreCase(oParameters.GetParameters("ContractNameAfterNavigation")))
				oReport.AddStepResult("Selected Contract", "Selected Contract Displayed", "PASS");
			else
				oReport.AddStepResult("Selected Contract", "Clicked on Contract Name but that selected contract doesn't navigated to Contract plugin not Displayed","FAIL");
		}

		// Navigating back to Analytics Suite

		waitFor(analyticsSuite, "Analytics Suite");
		navigate_to("Analytics Suite", "Reports PlugIn", analyticsSuite, reportsPlugIn);
		waitFor(noOfRecordsElement, "No of Records");

		if(IsDisplayed("Contractors Name", contractManagerColumn))
			oReport.AddStepResult("Contractors Name", "Contractors Name Displayed", "PASS");
		else
			oReport.AddStepResult("Contractors Name", "Clicked on Analytics but it doesn't navigated and Contract name not Displayed", "FAIL");
	}

	
	// Comparing downloaded Records
	public void comparingRateSheetAssociationDownloadedRecords() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("RateSheet", get_field_value("Rate Sheet Column", rateSheetColumn));
		addFilter("Rate Sheet");
		enter_text_value("Rate Sheet Search Box", filtersTextBox, oParameters.GetParameters("RateSheet"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("NoOfRecords",get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));

		CreateFolder(oParameters.GetParameters("downloadFilepath"));
		waitFor(downloadCSVButton, "Download CSV Button");

		click_button("Download Button", downloadCSVButton);
		fixed_wait_time(5);

		if(isFileDownloaded("RateSheetAssociationReport.zip")) 
		{
			unZipFolder(oParameters.GetParameters("downloadFilepath")+"/RateSheetAssociationReport.zip",oParameters.GetParameters("downloadFilepath"));
			File zipFile = new File(oParameters.GetParameters("downloadFilepath")+"/RateSheetAssociationReport.zip");
			zipFile.delete();
			csvToExcel();
			@SuppressWarnings("static-access")
			int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
			oParameters.SetParameters("RecordsInExcel", String.valueOf(recordsCount));

			if(oParameters.GetParameters("NoOfRecords").equals(oParameters.GetParameters("RecordsInExcel")))
				oReport.AddStepResult("Downloaded Records", "Downloaded Records Matched", "PASS");
			else
				oReport.AddStepResult("Downloaded Records",	"Records downloaded in excel file but number of record in portal Not Matched with number of records in downloaded excel file",	"FAIL");
		}
		else
			oReport.AddStepResult("Downloaded File", "Clicked on 'Donwload CSV' button but zip file is not downloaded ","FAIL");
	}

	
	// RateSheet Association Report VR
	public void rateSheetAssociationReport_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigate_to_Analytics();
		select_Report("Rate Sheet Association Report");
		createReport();
		healthPlanReports();
		expandCollapseGroupsValidation();
		contractNameFilterReports();
		healthPlanDescriptionReports();
		serviceProviderReports();
		serviceProviderNPIReports();
		taxonomyReports();
		contractManagerFilterReports();
		rateSheetReports();
		contractNameValidation();
		rateSheetValidation();
	//	clearFilters();
		resultsPerPage("100");
		pageNavigations();
		createBookmark();
		contractNameFilterReports();
		bookmarkValidation();
		settingsWindow();
		temporaryChangesInSettings();
		portalLogout();
		login("EDIT");
		changePricingEngine();
		navigate_to_Analytics();
		select_Report("Rate Sheet Association Report");
		createReport();
		defaultColumnsValidation();
		defaultChangesInSetting();
		resetToDefaults();
		fullScreenValidation();
		navigatingToOtherReport("Claim Repricing Report","Rate Sheet Association Report");
		comparingRateSheetAssociationDownloadedRecords();
		logout();
	}

	
	// Comparative Analysis Report VR
	
	By modelsPlugIn = By.xpath("//a[text()='Models']");

	By addModelLink = By.xpath("//a[contains(.,'Add a Model')]");

	
	// Navigating to Models PlugIn
	public void navigatingToModels() 
	{
		click_button("Models PlugIn", modelsPlugIn);
		waitFor(addModelLink, "Add a Model Link");

		if(IsDisplayed("Add a Model", addModelLink))
			oReport.AddStepResult("Models PlugIn", "Models PlugIn Displayed", "PASS");
		else
			oReport.AddStepResult("Models PlugIn", "Clicked on Models plugin but Models PlugIn page Not Displayed", "FAIL");
	}

	
	By searchBox = By.xpath("//input[@placeholder='Search Models']");

	By firstJob = By.xpath("//div[@id='model-search-results']//ul//li[1]");

	By allUsersCheckBox = By.xpath("//input[@id='showAllModelJobs']");

	By viewModelResultsButton = By.xpath("//span[text()='View Model Results']");

	By comparativeAnalysisPlugIn = By.xpath("//a[text()='Comparative Analysis']");

	By heatMapButton = By.xpath("//span[text()='Heat Map']");

	By heatMap = By.xpath("//td[3][@style='color: rgb(0, 0, 0); background-color: rgb(255, 128, 18); min-width: 100px;']");

	By switchAxisButton = By.xpath("//a[@title='Switch Axis'][@icon='retweet']");

	By filterButton = By.xpath("//a[@title='Filter'][@icon='filter']");

	By filterWindow = By.xpath("//ul/li[text()='Filter']");

	By productBeforeSwitch = By.xpath("//td[@class='col-header _0 col ld_0']//div[@class='pivot-cell-text']");

	By productAfterSwitch = By.xpath("//td[@class='row-header _0']//div[@class='pivot-cell-text']");

	By filterElement = By.xpath("//*[@class='row _1'][@transform='translate(0,26)']");

	By pivotGrandTotal = By.xpath("//td[@class='hand-cursor last-row row r_0 col ld_0 v_0']//div[@class='pivot-cell-text']");

	By filterClear = By.xpath("//a[@ng-show='hasFilters'][text()='Clear All']");

	
	// Job Validation
	public void jobValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("All user Jobs CheckBox", allUsersCheckBox);
		enter_text_value("Job Search Box", searchBox,"copy of test eb model 28");// "1/31/2018 111");// "EB Test 10.17");
		performKeyBoardAction("ENTER");
		waitFor(firstJob, "Models first job");
		click_button("Sorted First Job", firstJob);
		waitFor(viewModelResultsButton, "View Model Results Button");
		fixed_wait_time(3);
		click_button("View Model Results Button", viewModelResultsButton);
		waitFor(comparativeAnalysisPlugIn, "Comparative Analysis PlugIn");

		if(IsDisplayed("Heat map Button", heatMapButton))
			oReport.AddStepResult("Comparative Analysis Navigation", "Comparative Analysis Navigation Successful", "PASS");
		else
			oReport.AddStepResult("Comparative Analysis Navigation", "Clicked on View model result button but the page not navigated to Comparative Analysis page",	"FAIL");

		// Clicking on Heat map

		click_button("Heat Map Button", heatMapButton);

		if(IsDisplayed("Heat Map", heatMap))
			oReport.AddStepResult("Heat Map Information", "Heat Map Information Displayed", "PASS");
		else
			oReport.AddStepResult("Heat Map Information", "Clicked on Heat Map but that Heat Map information not displayed", "FAIL");

		// Switching Axis

		switchingAxis();

		// Filters Check

		click_button("Filters Button", filterButton);
		waitFor(filterWindow, "Comparative Analysis Filter Window");

		if(IsDisplayed("Filters Window", filterWindow))
			oReport.AddStepResult("Filters Window", "Filters Window Displayed", "PASS");
		else
			oReport.AddStepResult("Filters Window",	"Clicked on Filter icon but that Filter/Settings tab not displayed on left side of the panel with filters",	"FAIL");

		oParameters.SetParameters("FilterElementValue", get_field_value("Filters Element", filterElement));
		click_button("Filters Element", filterElement);
		fixed_wait_time(3);
		waitFor(pivotGrandTotal, "Pivot Grand Total");
		oParameters.SetParameters("GrandTotal", get_field_value("Grand Total", pivotGrandTotal));

		if(oParameters.GetParameters("FilterElementValue").contains(oParameters.GetParameters("GrandTotal")))
			oReport.AddStepResult("Selected Filter Report", "Selected Filter Report Displayed", "PASS");
		else
			oReport.AddStepResult("Selected Filter Report", "Clicked on filter but that respective record not displayed under pivot table", "FAIL");

		// Clearing Filters

		waitFor(filterClear, "Comparative Analysis Filter Clear");
		click_button("Clear All Filters", filterClear);
		fixed_wait_time(3);
		oParameters.SetParameters("GrandTotalWithoutFilter", get_field_value("Grand Total", pivotGrandTotal));

		if(oParameters.GetParameters("GrandTotal").equalsIgnoreCase(oParameters.GetParameters("GrandTotalWithoutFilter")))
			oReport.AddStepResult("Complete Pivot Table", "Clicked on clear but that filters are not cleared and complete pivot table is not displayed ","FAIL");
		else
			oReport.AddStepResult("Complete Pivot Table", "Complete Pivot Table Displayed", "PASS");
	}

	
	By filterSettings = By.xpath("//ul/li[text()='Settings']");

	By columnsRateSheet = By.xpath("//div[@class='sort-row']//div[@title='Rate Sheet']");

	By columnsProduct = By.xpath("//div[@class='sort-row']//div[@title='Product']");

	By rowsServiceLine = By.xpath("//div[@class='sort-row']//div[@title='Service Line']");

	By rowSectionName = By.xpath("//div[@class='text hide-overflow ng-binding'][@title='Section Name']");

	By rowTerm = By.xpath("//div[@class='text hide-overflow ng-binding'][@title='Term']");

	By pivotTableColumnHeader = By.xpath("//td[@class='dim colDim _0']/div");

	By pivotTableRowHeader = By.xpath("//td[@class='dim rowDim _0']/div");

	By valuesExpectedReimbursement = By.xpath("//div[@class='text hide-overflow ng-binding'][@title='Expected Reimbursement']");

	By valuesBase = By.xpath("//div[@class='text hide-overflow ng-binding'][@title='% of Base']");

	By valuesHeader = By.xpath("//td[@class='col-header _2 col ld_0']/div");

	By rowFieldDeleteIcon = By.xpath("//*[@id='pivot-table-row-labels']//a[@style='display: inline;']");

	By columnFieldDeleteIcon = By.xpath("//*[@id='pivot-table-col-labels']//a[@style='display: inline;']");

	By rowsAddFieldDD = By.xpath("//*[@id='pivot-table-row-labels']//a[@class='btn btn-light view-bg-text form-control']");

	By columnsAddFieldDD = By.xpath("//*[@id='pivot-table-col-labels']//a[@class='btn btn-light view-bg-text form-control']");

	By valuesAddFieldDD = By.xpath("//ul[@data-sort-list-id='Values']//div[@id='styledDropdown']/a[@class='btn btn-light view-bg-text form-control']");

	By columnDDProduct = By.xpath("//ul[@data-sort-list-id='ColumnLabels']//a[contains(.,'Product')]");

	
	// Settings and Drag and Drop
	public void settingsValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Filters Settings", filterSettings);

		// Drag and Drop in Columns Field

		oParameters.SetParameters("PivotTableFieldNameBeforeDrag",get_field_value("Pivot Table Field", pivotTableColumnHeader));
		scroll_to_element(columnsProduct,columnsRateSheet);
		waitFor(pivotTableColumnHeader, "Pivot Table Header");
		oParameters.SetParameters("PivotTableFieldNameAfterDrag",get_field_value("Pivot Table Field", pivotTableColumnHeader));

		if(oParameters.GetParameters("PivotTableFieldNameBeforeDrag").equals(oParameters.GetParameters("PivotTableFieldNameAfterDrag")))
			oReport.AddStepResult("Pivot Table after Drag and Drop", "Under Column Fields dragged the Product into Rate Sheet field but pivot table not displayed accordingly ", "FAIL");
		else
			oReport.AddStepResult("Pivot Table after Drag and Drop", "Under Column Fields dragged the Product into Rate Sheet field, Verified that pivot table is displayed accordingly ","PASS");

		// Drag and Drop from Columns to Row Fields

		oParameters.SetParameters("PivotTableFieldNameBeforeDrag",get_field_value("Pivot Table Field", pivotTableRowHeader));
		scroll_to_element(rowsServiceLine, columnsProduct);
		waitFor(pivotTableRowHeader, "Pivot Table Header");
		oParameters.SetParameters("PivotTableFieldNameAfterDrag",get_field_value("Pivot Table Field", pivotTableRowHeader));

		if(oParameters.GetParameters("PivotTableFieldNameBeforeDrag").equals(oParameters.GetParameters("PivotTableFieldNameAfterDrag")))
			oReport.AddStepResult("Pivot Table after Drag and Drop", "Dragged Column fields into Row Fields but pivot table not displayed accordingly ", "FAIL");
		else
			oReport.AddStepResult("Pivot Table after Drag and Drop", "Dragged Column fields into Row Fields, Verified that pivot table is displayed accordingly ","PASS");

		// Deleting Row Fields Field

		mouse_hover("Rows Term Field", rowTerm);
		click_button("Field Delete Icon", rowFieldDeleteIcon);
		waitFor(rowsAddFieldDD, "Rows Add Field Dropdown");

		if(IsDisplayed("Row Fileds Add Field Drop Down", rowsAddFieldDD) || IsDisplayed("Column Fileds Add Field Drop Down", columnsAddFieldDD))
			oReport.AddStepResult("Add Field Drop Down", "Row field deleted, Verified that field is deleted and Add Field button displayed ", "PASS");
		else
			oReport.AddStepResult("Add Field Drop Down", "Clicked on Row field delete icon but that field is not deleted and Add Field button not displayed ", "FAIL");

		// Drag and Drop from Row Fields to Columns Fields

		oParameters.SetParameters("PivotTableFieldNameBeforeDrag",get_field_value("Pivot Table Field", pivotTableRowHeader));
		scroll_to_element(columnsRateSheet, columnsProduct);
		waitFor(pivotTableRowHeader, "Pivot Table Header");
		oParameters.SetParameters("PivotTableFieldNameAfterDrag",get_field_value("Pivot Table Field", pivotTableRowHeader));

		if(oParameters.GetParameters("PivotTableFieldNameBeforeDrag").equals(oParameters.GetParameters("PivotTableFieldNameAfterDrag")))
			oReport.AddStepResult("Pivot Table after Drag and Drop", "Dragged Row fields into Column Fields but pivot table not displayed accordingly ", "FAIL");
		else
			oReport.AddStepResult("Pivot Table after Drag and Drop", "Dragged Row fields into Column Fields, Verified that pivot table is displayed accordingly ","PASS");

		// Deleting Columns Fields Field

		mouse_hover("Columns Product Field", columnsProduct);
		click_button("Field Delete Icon", columnFieldDeleteIcon);
		waitFor(columnsAddFieldDD, "Columns Add Field Dropdown");

		if(IsDisplayed("Column Fields Add Field Drop Down", columnsAddFieldDD))
			oReport.AddStepResult("Add Field Drop Down", "Column field deleted, Verified that field is deleted and Add Field button displayed ", "PASS");
		else
			oReport.AddStepResult("Add Field Drop Down", "Clicked on Column field delete icon but that field is not deleted and Add Field button not displayed ", "FAIL");

		// Adding deleted Fields

		oParameters.SetParameters("PivotTableFieldNameAfterDelete",get_field_value("Pivot Table Field", pivotTableColumnHeader));
		
		if(IsDisplayed("Column Fields Add Field Drop Down", columnsAddFieldDD)) 
		{
			click_button("Add Field Drop Down", columnsAddFieldDD);
			click_button("Product Field", columnDDProduct);
			waitFor(pivotTableColumnHeader, "Pivot Table Header");
			oParameters.SetParameters("PivotTableFieldNameAfterAdding",	get_field_value("Pivot Table Field", pivotTableColumnHeader));

			if(oParameters.GetParameters("PivotTableFieldNameAfterDelete").equals(oParameters.GetParameters("PivotTableFieldNameAfterAdding")))
				oReport.AddStepResult("Pivot Table After Adding Fields", "Click on Add Field drop down and selected the field to be added, Verified that new field is added in respective Column ","PASS");
			else
				oReport.AddStepResult("Pivot Table After Adding Fields", "Click on Add Field drop down and selected the field to be added but that new field is not added in respective Column ", "FAIL");

			// Drag and Drop in Columns Field

			oParameters.SetParameters("PivotTableFieldNameBeforeDrag",get_field_value("Pivot Table Field", pivotTableColumnHeader));
			scroll_to_element(columnsRateSheet, columnsProduct);
			waitFor(pivotTableColumnHeader, "Pivot Table Header");
			oParameters.SetParameters("PivotTableFieldNameAfterDrag",get_field_value("Pivot Table Field", pivotTableColumnHeader));

			if(oParameters.GetParameters("PivotTableFieldNameBeforeDrag").equals(oParameters.GetParameters("PivotTableFieldNameAfterDrag")))
				oReport.AddStepResult("Pivot Table after Drag and Drop", "Under Column Fields dragged the Product into Rate Sheet field but pivot table not displayed accordingly ", "FAIL");
			else
				oReport.AddStepResult("Pivot Table after Drag and Drop", "Under Column Fields dragged the Product into Rate Sheet field, Verified that pivot table is displayed accordingly ","PASS");
		}
		else
			oReport.AddStepResult("Column Fields Add Field Drop Down", "Column Fields Add Field Drop Down Not Displayed", "FAIL");

		// Drag and Drop in Values

		oParameters.SetParameters("PivotTableFieldNameBeforeDrag", get_field_value("Pivot Table Field", valuesHeader));
		scroll_to_element(valuesExpectedReimbursement, valuesBase);
		waitFor(valuesHeader, "Values Header");
		oParameters.SetParameters("PivotTableFieldNameAfterDrag", get_field_value("Pivot Table Field", valuesHeader));

		if(oParameters.GetParameters("PivotTableFieldNameBeforeDrag").equals(oParameters.GetParameters("PivotTableFieldNameAfterDrag")))
			oReport.AddStepResult("Pivot Table after Drag and Drop", "Under Values changed the order of the fields but that pivot table is not displayed accordingly ", "FAIL");
		else
			oReport.AddStepResult("Pivot Table after Drag and Drop", "Under Values changed the order of the fields, Verified that pivot table is displayed accordingly ","PASS");
	}
	

	By valuesDDCases = By.xpath("//ul[@data-sort-list-id='Values']//li[@id='i[__valueField]']/a[contains(.,'Cases')]");

	By casesColumn = By.xpath("//td[3][@class='col-header _2']//span[text()='Cases']");

	By casesValue = By.xpath("//td[3][@class='hand-cursor row r_0 col ld_1 v_1']/.//div/span");

	By casesWindow = By.xpath("//div[@id='details-title']");

	
	// Choosing Cases Option from Drop Down
	public void choosingCases() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Values Drop Down", valuesAddFieldDD);
		click_button("Cases", valuesDDCases);
		waitFor(casesColumn, "Cases Column");

		if(IsDisplayed("Cases Column", casesColumn))
			oReport.AddStepResult("Cases Field", "Added Cases Field under Values, Verified that cases field displayed under pivot table ", "PASS");
		else
			oReport.AddStepResult("Cases Field", "Added Cases Field under Values, But that cases field not displayed under pivot table ", "FAIL");

		// Clicking on Cases Value

		oParameters.SetParameters("CasesCount", get_field_value("Cases count", casesValue));
		dclick_button("Cases Value", casesValue);
		waitFor(casesWindow, "Cases Window");

		if(IsDisplayed("Cases Window", casesWindow))
			oReport.AddStepResult("Cases Window", "Clicked on cases value, Verified that model window displayed with unique encounters ", "PASS");
		else
			oReport.AddStepResult("Cases Window", "Clicked on cases value but that model window is not displayed ", "FAIL");
	}
	
	
	By encounterColumnCA = By.xpath("//div[@class='ngReactGridInnerViewPort']//tr//td[1]/div");
	
	By contentPath = By.xpath("//div[@class='ngReactGridBody']//tbody/tr");
	
	By headerPath = By.xpath("//div[@class='ngReactGridHeaderWrapper']//thead/tr/th");

	
	public void validatingCasesCountWithEncounters()
	{		
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		int encounterCount = get_table_row_count(encounterColumnCA);
		
		String[] encounters = new String[encounterCount];
		String Vals = "";
		
		// to get encounter values
		for (int i=1; i<=encounterCount; i++) 
		{
		//	String get_table_row_value = get_table_row_value(headerPath, contentPath, "Encounter", i);	
			
			By encounterValue = By.xpath("//div[@class='ngReactGridInnerViewPort']//tr["+i+"]//td[1]/div");
			
			String encounterValues = get_field_value("Encounter Value", encounterValue);	
			
			encounters [i-1] = encounterValues;
		}
		
	//	String encounterData = String.join(",", encounters).replace(",", " ");
		
		for(int i=0;i<encounters.length;i++)
		{			
			int encounter = i+1;			
			
			if(!(Integer.valueOf(encounter)>= encounters.length))
			{		
				if(!(encounters[i].equalsIgnoreCase(encounters[i+1])))
				{
					if(!Vals.contains(encounters[i])||encounters[i].isEmpty())
					{
						Vals = encounters[i] +","+ Vals ;
						System.out.println(i);
					}
				}
			}	
		}	
		
		String checkBoxNames [] = Vals.split(",");	
		int counterblank=0,counterval=0;
		
		for(int j=0 ;j<checkBoxNames.length;j++)
		{
			if (checkBoxNames[j].isEmpty())
				counterblank = counterblank + 1;
			else if(!checkBoxNames[j].isEmpty())
				counterval = counterval+1;
		}
		
		if(counterblank>0)
			counterblank = 1;
		
		int numberOfItems = counterval+counterblank;
		
		if(String.valueOf(numberOfItems).equals(oParameters.GetParameters("CasesCount")))
			oReport.AddStepResult("", "Verified that number of cases displayed in the table is equal to number of unique encounters ", "PASS");
		else
			oReport.AddStepResult("", "Verified that number of cases displayed in the table is not equal to number of unique encounters ", "FAIL");	
	}	
	
	
	By casesWindowSearch = By.xpath("//input[@class='search-icon search-text-box']");

	By casesPatientColumn = By.xpath("//div[@class='ngReactGridInnerViewPort']//tr[1]//td[2]/div");

	
	// Patient Name Reports
	public void patientReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Cases Window Search Box", casesWindowSearch, "Edwards");
		waitFor(casesPatientColumn, "Cases Patient Column");
		oParameters.SetParameters("CasesPatientName", get_field_value("Patient Name", casesPatientColumn));

		if(oParameters.GetParameters("CasesPatientName").contains("Edwards"))
			oReport.AddStepResult("Patient Name Reports", "Records filtered with Patient Name, Verified that respective records displayed", "PASS");
		else
			oReport.AddStepResult("Patient Name Reports", "Records filtered with Patient Name but that respective records not displayed", "FAIL");
	}

	
	By entriesPerPageDD = By.xpath("//div[@class='ngReactGridShowPerPage']//select");

	
	// 50 Entries per page
	public void entriesPerPage(String noOfRecords) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		select_option("Entries Per Page", entriesPerPageDD, noOfRecords);
		fixed_wait_time(3);

		oParameters.SetParameters("EntriesPerPage", get_field_value("Entries", entriesPerPageDD));

		if(oParameters.GetParameters("EntriesPerPage").contains(noOfRecords))
			oReport.AddStepResult("Entries Per Page", "Clicked on entries per page drop down and choosed 50 entries, Verified that 50 records displayed per page", "PASS");
		else
			oReport.AddStepResult("Entries Per Page", "Clicked on entries per page drop down and choosed 50 entries but 50 records per page not displayed", "FAIL");
	}

	
	By firstPage = By.xpath("//div[@class='ngReactGridPagination']//a[text()='First']");

	By lastPage = By.xpath("//div[@class='ngReactGridPagination']//a[text()='Last']");

	By prevPage = By.xpath("//div[@class='ngReactGridPagination']//a[text()='Prev']");

	By nextPage = By.xpath("//div[@class='ngReactGridPagination']//a[text()='Next']");

	By secondPage = By.xpath("//div[@class='ngReactGridPagination']//a[text()='2']");

	By selectedPage = By.xpath("//div[@class='ngReactGridPagination']//li[@class='active']//a[text()='2']");

	
	// Page Navigations
	public void pageNavigationsCA() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		enter_text_value("Cases Window Search Box", casesWindowSearch, "");
		performKeyBoardAction("ENTER");
		fixed_wait_time(2);

		// Second page

		if(IsDisplayed("Second Page Button", secondPage)) 
		{
			click_button("Second Page Button", secondPage);
			waitFor(secondPage, "Second Page Button");

			if(IsDisplayed("Selected Second Page", selectedPage)) 
			{
				oReport.AddStepResult("Clicked Page Results", "Clicked on second page, Verified that respective records displayed", "PASS");
				click_button("Back to First Page", firstPage);
				waitFor(secondPage, "Second Page Button");
			}
			else
				oReport.AddStepResult("Clicked Page Results", "Clicked on second page but that respective records not Displayed", "FAIL");
		} 
		else
			oReport.AddStepResult("Page Button", "No Next Pages", "INFO");

		// Next Page

		if(IsDisplayed("Next Page Button", nextPage)) 
		{
			click_button("Next Page Button", nextPage);
			waitFor(prevPage, "Previous Page Button");

			if(IsDisplayed("Prev Page Button", prevPage))
				oReport.AddStepResult("Next Page Results", "Clicked on next page button, Verified that records present in next page displayed", "PASS");
			else
				oReport.AddStepResult("Next Page Results", "Clicked on next page button but that page records bot displayed ", "FAIL");
		} 
		else
			oReport.AddStepResult("Next Page Button", "No Next Page", "INFO");

		// Previous Page

		if(IsDisplayed("Prev Page Button", prevPage)) 
		{
			click_button("Prev Page Button", prevPage);
			waitFor(nextPage, "Next Page Button");

			if(IsDisplayed("Next Page Button", nextPage))
				oReport.AddStepResult("Prev Page Results", "Clicked on previous page button, Verified that records present in previous page displayed", "PASS");
			else
				oReport.AddStepResult("Prev Page Results", "Clicked on previous page button but previous page records not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("Prev Page Button", "No Prev Page", "INFO");

		// Last Page

		if(IsDisplayed("Last Page Button", lastPage)) 
		{
			click_button("Last Page Button", lastPage);
			waitFor(firstPage, "First Page Button");

			if(IsDisplayed("First Page Button", firstPage))
				oReport.AddStepResult("Last Page Results", "Clicked on Last page button, Verified that records in last page displayed ", "PASS");
			else
				oReport.AddStepResult("Last Page Results", "Clicked on Last page button but last page records not displayed ", "FAIL");
		} 
		else
			oReport.AddStepResult("Last Page Button", "No Last Page", "INFO");

		// First Page

		if(IsDisplayed("Last Page Button", firstPage)) 
		{
			click_button("Last Page Button", firstPage);
			waitFor(lastPage, "Last Page Button");

			if(IsDisplayed("First Page Button", lastPage))
				oReport.AddStepResult("First Page Results", "Clicked on First page button, Verified that records in first page displayed", "PASS");
			else
				oReport.AddStepResult("First Page Results", "Clicked on First page button but first page records not displayed", "FAIL");
		}
		else
			oReport.AddStepResult("First Page Button", "No First Page", "INFO");
	}

	
	By casesWindowDownloadButton = By.xpath("//table[@class='toolbar-container pull-right']//a[@class='btn btn-default hand-cursor link-btn hand-cursor ng-isolate-scope btn-xs']");

	By downloadCasesCSV = By.xpath("//table[@class='toolbar-container pull-right']//li[@id='i[__valueField]'][contains(.,'CSV')]");

	By closeWindowButton = By.xpath("//a[@title='Close']");

	
	// downloading records
	public void downloadingRecords() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		CreateFolder(oParameters.GetParameters("downloadFilepath"));
		waitFor(casesWindowDownloadButton, "Cases Window Download Button");

		click_button("Cases Window Download Button", casesWindowDownloadButton);
		click_button("CSV Option", downloadCasesCSV);
		fixed_wait_time(5);

		csvToExcel();
		@SuppressWarnings("static-access")
		int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
		oParameters.SetParameters("RecordsInExcel", String.valueOf(recordsCount));

		if(oParameters.GetParameters("RecordsInExcel").equals("0"))
			oReport.AddStepResult("CSV Records", "Clicked on download icon but records not downloaded in CSV format", "FAIL");
		else
			oReport.AddStepResult("CSV Records", "Clicked on download icon, Verified that records downloaded in CSV format", "PASS");		
		
		File excelFile = new File(oParameters.GetParameters("downloadFilepath")+"/model-details .xlsx");
		excelFile.delete();
		File csvFile = new File(oParameters.GetParameters("downloadFilepath")+"/model-details.csv");
		csvFile.delete();		 
	}

	
	// Closing model window
	public void closeModelWindow()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Close Button", closeWindowButton);
		waitFor(switchAxisButton, "Switch Axis Button");
		
		if(IsDisplayed("Model Window Close", closeWindowButton))
			oReport.AddStepResult("", "Clicked on window close icon but that model window is not closed ", "FAIL");
		else
			oReport.AddStepResult("", "Clicked on window close icon, Verified that model window is closed ", "PASS");
	}
	
	
	// switching axis
	public void switchingAxis() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		fixed_wait_time(3);
		oParameters.SetParameters("ProductNameBeforeSwitch", get_field_value("Product Name", productBeforeSwitch));
		click_button("Switch Axis", switchAxisButton);
		waitFor(productAfterSwitch, "Comparative Analysis Table");
		oParameters.SetParameters("ProductNameAfterSwitch", get_field_value("Product Name", productAfterSwitch));

		if(oParameters.GetParameters("ProductNameBeforeSwitch").equalsIgnoreCase(oParameters.GetParameters("ProductNameAfterSwitch")))
			oReport.AddStepResult("Switched Pivot Table", "Clicked on switch axis icon, Verified that pivot table with switched axis displayed", "PASS");
		else
			oReport.AddStepResult("Switched Pivot Table", "Clicked on switch axis icon but that pivot table with switched axis not displayed", "FAIL");
		
		click_button("Switch Axis", switchAxisButton);
		fixed_wait_time(2);
	}

	
	By drillDown = By.xpath("//td[@class='row-header _0']//span[@title='Drill Down']");
	
	By drillData = By.xpath("//tbody//tr[2]/td[@class='row-header _1']/div");

	
	// Pivot Table Drop down
	public void pivotTableDropDownValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		if(IsDisplayed("Drill down Button", drillDown)) 
		{
			click_button("Drill Down Button", drillDown);
			fixed_wait_time(3);

			if(IsDisplayed("Pivot Data", drillData))
				oReport.AddStepResult("Drilled Data", "In pivot table clicked on drop down arrow, Verified that fields are drilled down", "PASS");
			else
				oReport.AddStepResult("Drilled Data", "In pivot table clicked on drop down arrow but that fields not drilled down", "FAIL");
		}
		else
			oReport.AddStepResult("Drill down Button", "Drill down Button Not Displayed", "FAIL");
	}

	
	By bookmarkButtonCA = By.xpath("//button[@title='Bookmark'][@icon='bookmark-o']");

	By bookmarkButtonWithBookmark = By.xpath("//div/button[@title='Bookmark'][@icon='bookmark']");
	
	By settingsButtonCA = By.xpath("//a[@title='Settings']");

	
	// Bookmark validation
	public void bookmarkValidationCA() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		oParameters.SetParameters("PivotTableFieldBeforeBookmark",get_field_value("Pivot Table Field", pivotTableColumnHeader));
		click_button("Book mark Button", bookmarkButtonWithBookmark);
		waitFor(saveBookmarkLink,"Save Bookmark Link");
		click_button("Save Bookmark Link", saveBookmarkLink);
		waitFor(addBookmarkWindow, "Add Bookmark Window");
		
		if(IsElementDisplayed("addBookmarkWindow", addBookmarkWindow))
			oReport.AddStepResult("Book mark window", "Create Bookmark Window Displayed", "PASS");
		else
			oReport.AddStepResult("Create Bookmark Window",	"Clicked on Save current page as a bookmark but Create Bookmark Window is not Displayed", "FAIL");

		// Creating Book mark
		
		enter_text_value("Bookmark Name", bookmarkNameTextBox,oParameters.GetParameters("BookmarkName")+System.currentTimeMillis());
		oParameters.SetParameters("NewBookmarkName", get_field_value("Bookmark Name", bookmarkNameTextBox));
		click_button("Save Button", bookmarkSaveButton);
		waitFor(bookmarkSuccessNotification, "Bookmark Created Notification");

		if(IsDisplayed("Bookmark Created Notification", bookmarkSuccessNotification))
			oReport.AddStepResult("Bookmark Created Successfully","Bookmark Created Successfully notification Displayed", "PASS");
		else
			oReport.AddStepResult("Bookmark Created Successfully","Bookmark created but Bookmark Created Successfully notification not Displayed", "FAIL");
		
		// Existing Book mark validation

		if(IsDisplayed("Save Bookmark Link", saveBookmarkLink)) 
		{}
		else
			click_button("Bookmark Button with Bookmark", bookmarkButtonWithBookmark);

		By noOfBookmarks = By.xpath(".//*[@id='userPref']/div/ul/li");
		oParameters.SetParameters("bookmarksCount", String.valueOf(get_table_row_count(noOfBookmarks)));

		By currentbookmark = By.xpath("//*[@id='userPref']/div/ul/li/span[text()='" + oParameters.GetParameters("NewBookmarkName") + "']");

		oParameters.SetParameters("get_text_BM", get_field_value("Created Bookmark", currentbookmark));
		waitFor(currentbookmark, "Newly Created Bookmark");
		mouse_hover("Existing Bookmark", currentbookmark);

		if(IsDisplayed("Created Bookmark", currentbookmark))
			oReport.AddStepResult("Existing Bookmark",	"Existing Bookmark :"+oParameters.GetParameters("get_text_BM")+" Displayed", "PASS");
		else
			oReport.AddStepResult("Existing Bookmark",	"Bookmark created but that Bookmark not Displayed under bookmarks", "FAIL");
		
		click_button("Switch Axis", switchAxisButton);
		
		// Clicking on Previously created bookmark
		
		if(IsDisplayed("Save Bookmark Link", saveBookmarkLink))
		{}
		else
			click_button("Bookmark Button with Bookmark", bookmarkButtonWithBookmark);

		noOfBookmarks = By.xpath(".//*[@id='userPref']/div/ul/li");
		
		oParameters.SetParameters("bookmarksCount", String.valueOf(get_table_row_count(noOfBookmarks)));
		oParameters.SetParameters("firstCreatedBM",	String.valueOf(Integer.parseInt(oParameters.GetParameters("bookmarksCount")) - 1));

		By previousBookmark = By.xpath("//*[@id='userPref']/div/ul/li/span[contains(text(),'"+ oParameters.GetParameters("NewBookmarkName") + "')]");

		waitFor(saveBookmarkLink, "Save Bookmark Link");
		click_button("Recently Created Bookmark", previousBookmark);
		waitFor(pivotTableColumnHeader, "Pivot Table Header");
		
		oParameters.SetParameters("PivotTableFieldAfterBookmark",get_field_value("Pivot Table Field", pivotTableColumnHeader));
		
		if(oParameters.GetParameters("PivotTableFieldBeforeBookmark").equalsIgnoreCase(oParameters.GetParameters("PivotTableFieldAfterBookmark")))
			oReport.AddStepResult("Bookmark page", "Previously saved bookmark page is displayed", "PASS");
		else
			oReport.AddStepResult("Bookmark page","Clicked on previously created bookmark but it doesn't navigated and that page is not Displayed",	"FAIL");

		// Edit and Delete Bookmark icon check

		By bookmarkEdit = By.xpath("//span[text()='"+oParameters.GetParameters("NewBookmarkName")+"']/..//a[@style='display: inline;']/i[@class='left fa fa-pencil']");

		By bookmarkDelete = By.xpath("//span[contains(text(),'"+oParameters.GetParameters("NewBookmarkName")+"')]/../a[@style='display: inline;']/i[1]");
		
		click_button("Settings Button", settingsButtonCA);	
		
		click_button("Bookmark Button with Bookmark", bookmarkButtonWithBookmark);
		waitFor(previousBookmark, "Previously Created Bookmark");
		mouse_hover("Previous Bookmark", previousBookmark);

		if(IsDisplayed("Bookmark Edit", bookmarkEdit) && IsDisplayed("Bookmark Delete", bookmarkDelete))
			oReport.AddStepResult("Edit and Delete Icon","Mouse hoverd to created bookmark, That Bookmark Edit and Delete Icon Displayed", "PASS");
		else
			oReport.AddStepResult("Edit and Delete Icon","Mouse hoverd to created bookmark but Edit and Delete Icon is not Displayed", "FAIL");
		
		// Duplicate bookmark validation

		click_button("Save Book mark link", saveBookmarkLink);
		enter_text_value("Bookmark Name", bookmarkNameTextBox, oParameters.GetParameters("get_text_BM"));
		click_button("Save Button", bookmarkSaveButton);

		if(IsDisplayed("Bookmark Error Message", bookmarkError)) 
		{
			oReport.AddStepResult("Duplicate Notification", "Duplicate Notification Displayed", "PASS");
			click_button("Cancel Button", bookmarkCancelButton);
		}
		else
		{
			oReport.AddStepResult("Duplicate Notification ","Tried to create a new bookmark with existing bookmark name but Duplicate Notification is not Displayed","FAIL");
			click_button("Cancel Button", bookmarkCancelButton);
		}
		
		// Editing Bookmark

		mouse_hover("Previous Bookmark", previousBookmark);
		click_button("Edit Bookmark", bookmarkEdit);
		enter_text_value_without_clear("Bookmark Name Text Box", bookmarkNameTextBox,"Edited");//oParameters.GetParameters("EditBookmarkValue"));
		click_button("Save Button", bookmarkSaveButton);
		waitFor(bookmarkEditedNotification, "Bookmark Edited Notification");

		if(IsDisplayed("Bookmark Edited Notification", bookmarkEditedNotification))
			oReport.AddStepResult("Bookmark Edited Notification", "Bookmark Edited Notification Displayed", "PASS");
		else
			oReport.AddStepResult("Bookmark Edited Notification","Bookmark name edited but Bookmark Edited Notification is not Displayed", "FAIL");
		
		if (IsDisplayed("Save Bookmark Link", saveBookmarkLink))
		{}
		else
			click_button("Bookmark Button with Bookmark", bookmarkButtonWithBookmark);

		// Deleting bookmark

		mouse_hover("Previous Bookmark", previousBookmark);
		click_button("Delete Bookmark", bookmarkDelete);
		waitFor(discardBookmark, "Discard Bookmark Button");

		if(IsDisplayed("Delete Bookmark Popup", discardBookmark))
			oReport.AddStepResult("Delete Bookmark Popup", "Delete Bookmark Popup Displayed", "PASS");
		else
			oReport.AddStepResult("Delete Bookmark Popup","Clicked on Delete bookmark but Delete Bookmark Popup not Displayed", "FAIL");
		
		click_button("Discard Button", discardBookmark);
		fixed_wait_time(3);

		if(IsDisplayed("Bookmark Deleted Nofitification", bookmarkDeletedNotification))
			oReport.AddStepResult("Bookmark Deleted Notification", "Bookmark Deleted Notification Displayed", "PASS");
		else
			oReport.AddStepResult("Bookmark Deleted Notification",	"Bookmark deleted but Bookmark Deleted Notification is not Displayed", "FAIL");
		
		// Creating bookmark with deleted Bookmark Name

		if(IsDisplayed("Save Bookmark Link", saveBookmarkLink))
		{}
		else
			click_button("Bookmark Button with Bookmark", bookmarkButtonWithBookmark);

		waitFor(saveBookmarkLink, "Save Bookmark Link");
		click_button("Save Book mark link", saveBookmarkLink);
		enter_text_value("Bookmark Name", bookmarkNameTextBox,oParameters.GetParameters("get_text_BM")+"Edited");
		click_button("Save Button", bookmarkSaveButton);
		waitFor(bookmarkSuccessNotification, "Bookmark Created Notification");

		if(IsDisplayed("Bookmark Created Notification", bookmarkSuccessNotification))
			oReport.AddStepResult("Bookmark Created Successfully",	"Bookmark created Successfully with Deleted bookmark Name ", "PASS");
		else
			oReport.AddStepResult("Bookmark Created Successfully", "Created bookmark with deleted bookmark name but Bookmark Created Successfully notification is not Displayed","FAIL");

		click_button("Bookmark Button with Bookmark", bookmarkButtonWithBookmark);
	}
	
	
	By fullScreenButton = By.xpath("//a[@title='Full Screen']/i[@class='left fa fa-arrows-alt']");
	
	By exitFullScreenButton = By.xpath("//a[@title='Restore']/i[@class='left fa fa-compress']");
	
	
	// Full Screen Validation
	public void fullScreenIcon()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Full Screen Button", fullScreenButton);
		fixed_wait_time(3);
		//waitFor(exitFullScreenButton, "Exit Full Screen Button");
		
		if(IsDisplayed("Exit Full Screen Button", exitFullScreenButton))
			oReport.AddStepResult("Full Screen", "Clicked on Full screen icon, Verified that pivot table is displayed in full screen without any error ", "PASS");
		else
			oReport.AddStepResult("Full Screen", "Clicked on Full screen icon but pivot table is not displayed in full screen ", "FAIL");
		
		// Restoring to normal page
		
		click_button("Exit Full Screen Button", exitFullScreenButton);
		fixed_wait_time(3);
		//waitFor(fullScreenButton,"Full Screen Button");
		
		if(IsDisplayed("Full Screen Button", fullScreenButton))
			oReport.AddStepResult("Normal Screen", "Clicked on Restore icon, Verified that pivot table is displayed in normal page size without any error", "PASS");
		else
			oReport.AddStepResult("Normal Screen", "Clicked on Restore icon but pivot table is not displayed in normal page size ", "FAIL");		
	}
	
	
	By downloadIcon = By.xpath("//div[@items='pivotDownloadVals']//a[@class='btn btn-default hand-cursor link-btn hand-cursor ng-isolate-scope btn-xs']");
	
	By modelSummaryCSVButton = By.xpath("//li[@id='i[__valueField]']//a[contains(.,'Model Summary CSV')]");
	
	
	
	// Download icon and checking downloaded file
	public void downloadIcon()
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Download Icon", downloadIcon);
		click_button("Model Summary CSV Button", modelSummaryCSVButton);
		fixed_wait_time(5);
		
		if(isFileDownloaded("Contract-Model.csv")) 
			oReport.AddStepResult("", "Clicked on download icon and choosed Model Summary CSV option, Verified that records are downloaded in respective format ", "PASS");
		else
			oReport.AddStepResult("", "Clicked on download icon and choosed Model Summary CSV option but records are not downloaded in respective format ", "FAIL");

		File csvFile = new File(oParameters.GetParameters("downloadFilepath")+"/Contract-Model.csv");
		csvFile.delete();		
	}

	
	// Comparative Analysis Report_VR
	public void comparativeAnalysisReport_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigatingToModels();
		jobValidation();
		settingsValidation();
		choosingCases();
		entriesPerPage("50");
		validatingCasesCountWithEncounters();
		patientReports();
		entriesPerPage("5");
		pageNavigationsCA();
		downloadingRecords();
		closeModelWindow();		
		switchingAxis();
		pivotTableDropDownValidation();
		bookmarkValidationCA();
		fullScreenIcon();
		downloadIcon();
		logout();
	}

	
	// Qualification Group Usage Report VR

	
	By qualificationGroupUsageReport = By.xpath("//div[@class='styled-dropdown black-text-when-disabled ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Qualification Group Usage Report')]");

	By qualificationGroupUsageReportDC = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Qualification Group Usage Report')]");
	
	By qualificationNameHeader = By.xpath("//div[@class='heading ng-scope'][contains(text(),'Qualification Name')]");
	
	By qualificationTypeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'qualificationType')\"]");

	By qualificationTypeElement = By.xpath("//li/a[contains(text(),'Qualification Type')]");

	By DRGTypeQualificationElement = By.xpath("//a[text()='DRG Formula Expression']");

	By qualificationTypeInputField = By.xpath("//div[@class='filter-value pull-left ng-scope ng-binding']");

	By stopLossQualificationInputField = By.xpath("//a[@ng-click='af.searchText = e.Value'][text()='Stop Loss Qualification']");

	
	// Filter Reports
	// Qualification Type Filter Reports
	public void qualificationTypeFilterReports(String qualificationType) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = qualificationType.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for (int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}
			
			createReport();
			waitFor(qualificationTypeColumn, "Reports Table");
			addFilter("Qualification Type");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(qualificationTypeColumn, "Reports Table");
			oParameters.SetParameters("DRGQualificationType",get_field_value("DRG Qualification Type", qualificationTypeColumn));

			if(oParameters.GetParameters("QualificationTypeValue").contains(oParameters.GetParameters("QualificationTypeValue")) && IsElementEnabled("Download CSV Button", downloadCSVButton))
				oReport.AddStepResult("Qualification Type Reports",	"Reports are filtered based Qualification Type as "+word+", verified that those reports are displayed and Download Icon enabled ", "PASS");
			else
				oReport.AddStepResult("Qualification Type Reports",	"Reports are filtered based on "+word+" Qualification Type but those reports are not displayed and Download Icon is not enabled","FAIL");

			clearFilters();
		}
	}

	
	By qualificationNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'qualificationName')\"]");

	By qualificationNameElement = By.xpath("//li/a[contains(text(),'Qualification Name')]");

	
	// Qualification Name Filter Reports
	public void qualificationNameFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Qualification Name with Contains Operator

		createReport();
		waitFor(qualificationNameColumn, "Reports Table");
		oParameters.SetParameters("QualificationNameBeforeFilter",get_field_value("Qualification Name Column", qualificationNameColumn));
		addFilter("Qualification Name");
		click_button("Qualification Name Operators Button", billTypeOperators);
		click_button("Qualification Name Contains Operator", billTypeContainsOperator);
		enter_text_value("Filters Input Text Box", filtersTextBox,oParameters.GetParameters("QualificationNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("QualificationNameAfterFilter",get_field_value("Qualification Name Column", qualificationNameColumn));

		if(oParameters.GetParameters("QualificationNameBeforeFilter").contains(oParameters.GetParameters("QualificationNameAfterFilter")))
			oReport.AddStepResult("Qualification Name Contains Result",	"Qualification Name Contains filters reports are Displayed", "PASS");
		else
			oReport.AddStepResult("Qualification Name Contains Result",	"Reports are filtered based on Qualification Name Contains but those reports are not Displayed","FAIL");

		// Qualification Name with Exact Operator

		click_button("Qualification Name Operators Button", billTypeOperator);
		click_button("Qualification Name Exact Operator", billTypeExactOperator);
		createReport();
		oParameters.SetParameters("QualificationNameExact",	get_field_value("Qualification Name", qualificationNameColumn));

		if(oParameters.GetParameters("QualificationNameBeforeFilter").equals(oParameters.GetParameters("QualificationNameExact")))
			oReport.AddStepResult("Qualification Name Exact Result", "Qualification Name Exact Result Displayed","PASS");
		else
			oReport.AddStepResult("Qualification Name Exact Result", "Reports are filtered based on Qualification Name with Exact operator but those reports are not displayed ","FAIL");

		// Qualification Name With Not Exact

		click_button("Qualification Name Operators Button", billTypeOperator);
		click_button("Qualification Name Not Exact Operator", billTypeNotExactOperator);
		createReport();
		oParameters.SetParameters("QualificationNameNotExact",get_field_value("Qualification Name", qualificationNameColumn));

		if(oParameters.GetParameters("QualificationNameBeforeFilter").equals(oParameters.GetParameters("QualificationNameNotExact"))) 
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Qualification Name Not Exact Result", "Reports are filtered based on Qualification Name with not Exact operator but those reports are not displayed ","FAIL");
		}
		else
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Qualification Name Not Exact Result","Qualification Name Not Exact Result Displayed", "PASS");
		}

		// Qualification Name With Does Not Contain

		click_button("Qualification Name Operators Button", billTypeOperator);
		click_button("Qualification Name Does Not Contain Operator", billTypeDoesNotContain);
		createReport();
		oParameters.SetParameters("QualificationNameNotContain",get_field_value("Qualification Name", qualificationNameColumn));

		if(oParameters.GetParameters("QualificationNameBeforeFilter").equals(oParameters.GetParameters("QualificationNameNotContain"))) 
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Qualification Name Does Not Contain Result",	"Reports are filtered based on Qualification Name with does not contain operator but those reports are not displayed ",	"FAIL");
		}
		else
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Qualification Name Does Not Contain Result",	"Qualification Name Does Not Contain Result Displayed", "PASS");
		}
		clearFilters();
	}

	
	By rateSheetCodeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'rateSheetCode')\"]");

	By rateSheetCodeElement = By.xpath("//li/a[contains(text(),'Rate Sheet Code')]");

	
	// Rate Sheet Code Filter Reports
	public void rateSheetCodeFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(rateSheetCodeColumn, "Reports Table");
		oParameters.SetParameters("RatesheetCodeBeforeFilter", get_field_value("Ratesheet Code", rateSheetCodeColumn));
		addFilter("Rate Sheet Code");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("RatesheetCodeBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetCodeColumn, "Reports Table");
		oParameters.SetParameters("RatesheetCodeAfterFilter", get_field_value("Ratesheet Code", rateSheetCodeColumn));

		if(oParameters.GetParameters("RatesheetCodeBeforeFilter").equals(oParameters.GetParameters("RatesheetCodeAfterFilter")))
			oReport.AddStepResult("Ratesheet Code Reports", "Ratesheet Code Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Ratesheet Code Reports",	"Reports are filtered based on Ratesheet Code but those reports are not displayed ", "FAIL");

		// Rate Sheet Code Filter Reports

		clearFilters();
		createReport();
		waitFor(rateSheetCodeColumn, "Reports Table");
		addFilter("Rate Sheet Code");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("RateSheetCodeValue"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetCodeColumn, "Reports Table");
		oParameters.SetParameters("EditedRatesheetCodeAfterFilter",	get_field_value("Ratesheet Code", rateSheetCodeColumn));

		if(oParameters.GetParameters("EditedRatesheetCodeAfterFilter").equals(oParameters.GetParameters("RateSheetCodeValue")))
			oReport.AddStepResult("Ratesheet Code Reports", "Ratesheet Code Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Ratesheet Code Reports",	"Reports are filtered based on Ratesheet Code but those reports are not displayed ", "FAIL");

		clearFilters();
	}

	
	By rateSheetTermTypeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'ratesheetTermType')\"]");

	By rateSheetTermTypeElement = By.xpath("//li/a[contains(text(),'Rate Sheet Term Type')]");

	By rateSheetTermElementOption = By.xpath("//a[text()='Term']");

	By rateSheetInputFieldExclusion = By.xpath("//a[@ng-click='af.searchText = e.Value'][text()='Exclusion']");

	
	// Rate Sheet Term Type Filter Reports
	public void rateSheetTermTypeFilterReports(String rateSheetTermType) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = rateSheetTermType.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for(int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}
			
			createReport();
			waitFor(rateSheetTermTypeColumn, "Reports Table");
			addFilter("Rate Sheet Term Type");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(rateSheetTermTypeColumn, "Reports Table");
			oParameters.SetParameters("RateSheetTermType",get_field_value("Rate Sheet Term Type", rateSheetTermTypeColumn));

			if(oParameters.GetParameters("RateSheetTermType").contains(oParameters.GetParameters("RateSheetTermType")))
				oReport.AddStepResult("Rate Sheet Term Type Reports", "Reports are filtered Rate Sheet Term Type as "+word+", verified that those Reports are Displayed ",	"PASS");
			else
				oReport.AddStepResult("Rate Sheet Term Type Reports", "Reports are filtered Rate Sheet Term Type as "+word+" but those reports are not displayed ","FAIL");

			clearFilters();
		}
	}

	
	By rateSheetSectionColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'ratesheetSection')\"]");

	By rateSheetSectionElement = By.xpath("//li/a[contains(text(),'Rate Sheet Section')]");

	
	// Rate Sheet Section Filter Reports
	public void rateSheetSectionFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(rateSheetSectionColumn, "Reports Table");
		oParameters.SetParameters("RatesheetSectionBeforeFilter",get_field_value("Ratesheet Section", rateSheetSectionColumn));
		addFilter("Rate Sheet Section");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("RatesheetSectionBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetSectionColumn, "Reports Table");
		oParameters.SetParameters("RatesheetSectionAfterFilter",get_field_value("Ratesheet Section", rateSheetSectionColumn));

		if(oParameters.GetParameters("RatesheetSectionBeforeFilter").equals(oParameters.GetParameters("RatesheetSectionAfterFilter")))
			oReport.AddStepResult("Ratesheet Section Reports", "Ratesheet Section Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Ratesheet Section Reports", "Reports are filtered based on Ratesheet Section but those reports are not displayed ", "FAIL");

		// Rate Sheet Section Filter Reports

		clearFilters();
		createReport();
		waitFor(rateSheetSectionColumn, "Reports Table");
		addFilter("Rate Sheet Section");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("RateSheetSectionValue"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetCodeColumn, "Reports Table");
		oParameters.SetParameters("EditedRatesheetSectionAfterFilter",get_field_value("Ratesheet Section", rateSheetSectionColumn));

		if(oParameters.GetParameters("EditedRatesheetSectionAfterFilter").equals(oParameters.GetParameters("RateSheetSectionValue")))
			oReport.AddStepResult("Ratesheet Section Reports", "Ratesheet Section Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Ratesheet Section Reports", "Reports are filtered based on Ratesheet Section but those reports are not displayed ", "FAIL");

		clearFilters();
	}

	By rateSheetTermColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('2' , row, 'ratesheetTerm')\"]");

	By rateSheetTermElement = By.xpath("//li/a[text()='Rate Sheet Term']");

	
	// Rate Sheet Term Filter Reports
	public void rateSheetTermFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(qualificationTypeColumn, "Reports Table");
		addFilter("Rate Sheet Term");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("RateSheetTermValue"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetTermColumn, "Reports Table");
		oParameters.SetParameters("RateSheetTermValueName", get_field_value("Rate Sheet Term ", rateSheetTermColumn));

		if(oParameters.GetParameters("RateSheetTermValueName").equals(oParameters.GetParameters("RateSheetTermValue")))
			oReport.AddStepResult("Rate Sheet Term Reports", "Rate Sheet Term Filter Reports are Displayed ", "PASS");
		else
			oReport.AddStepResult("Rate Sheet Term Reports","Reports are filtered based on Rate Sheet Term but those reports are not displayed ", "FAIL");

		// Rate Sheet Term Filter Reports

		clearFilters();
		createReport();
		waitFor(rateSheetSectionColumn, "Reports Table");
		addFilter("Rate Sheet Term");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("RateSheetTermValue1"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetTermColumn, "Reports Table");
		oParameters.SetParameters("RateSheetTermValueName1", get_field_value("Rate Sheet Term ", rateSheetTermColumn));

		if(oParameters.GetParameters("RateSheetTermValueName1").equals(oParameters.GetParameters("RateSheetTermValue1")))
			oReport.AddStepResult("Rate Sheet Term Reports", "Rate Sheet Term Filter Reports are Displayed", "PASS");
		else
			oReport.AddStepResult("Rate Sheet Term Reports","Reports are filtered based on Rate Sheet Term but those reports are not displayed", "FAIL");

		clearFilters();
	}

	
	// Qualification Name Filter Reports
	public void qualificationFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(qualificationNameColumn, "Reports Table");
		oParameters.SetParameters("QualificationNameBeforeFilter",get_field_value("Qualification Name Column", qualificationNameColumn));
		addFilter("Qualification Name");
		enter_text_value("Filters Input Text Box", filtersTextBox,oParameters.GetParameters("QualificationNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("QualificationNameAfterFilter",get_field_value("Qualification Name Column", qualificationNameColumn));

		if(oParameters.GetParameters("QualificationNameBeforeFilter").contains(oParameters.GetParameters("QualificationNameAfterFilter")))
			oReport.AddStepResult("Qualification Name Contains Result",	"Qualification Name Contains filters reports are Displayed", "PASS");
		else
			oReport.AddStepResult("Qualification Name Contains Result",	"Reports are filtered based on Qualification Name Contains but those reports are not Displayed","FAIL");
	}
	

	// Column Sorting
	public void columnSorting() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		click_button("Qualification Name Header", qualificationNameHeader);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("QualificationNameColumnBeforeSort",get_field_value("Qualification Name Column Text", qualificationNameColumn));
		click_button("Qualification Name Header", qualificationNameHeader);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("QualificationNameColumnAfterSort",get_field_value("Qualification Name Column Text", qualificationNameColumn));

		if(oParameters.GetParameters("QualificationNameColumnBeforeSort").equals(oParameters.GetParameters("QualificationNameColumnAfterSort")))
			oReport.AddStepResult("Column Sorted Results","Mouse hovered and clicked on Qualification Name Header but that column is not sorted from descending to ascending order ","FAIL");
		else
			oReport.AddStepResult("Column Sorted Results", "Qualification Name Column Sorted Results Displayed","PASS");
	}

	
	By qualificationNameCheckBox = By.xpath("//li/input[@id='qualificationName'][@type='checkbox']");

	
	// Doing Temporary Changes in QG usage Report Settings
	public void temporaryChangesInQGUsageReportSettings() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Qualification Name Check Box", qualificationNameCheckBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Qualification Name Column", qualificationNameHeader))
			oReport.AddStepResult("Uncheck Qualification Name column", "Unchecked Qualification Name option in settings window but still it is displayed in Reports page", "FAIL");
		else
			oReport.AddStepResult("Uncheck Qualification Name column", "Unchecked Qualification Name option in settings window that column is not displayed in Reports page", "PASS");
	}

	
	// Default Columns Validation
	public void QGusageReportdefaultColumnsValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Qualification Name Column", qualificationNameHeader))
			oReport.AddStepResult("Qualification Name Column", "Qualification Name Column Displayed", "PASS");
		else
			oReport.AddStepResult("Checked Qualification Name column", "Checked Qualification Name option in settings window but still it is Not displayed in Reports page", "FAIL");
	}

	
	// Default Changes in settings validation
	public void defaultChangesInQGUsageReportSetting() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Qualification Name Check Box", qualificationNameCheckBox);
		click_button("Save as My Defaults", saveMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Qualification Name Column", qualificationNameHeader))
			oReport.AddStepResult("Uncheck Qualification Name column", "Unchecked Qualification Name option in settings window but still it is displayed in Reports page", "FAIL");
		else
			oReport.AddStepResult("Uncheck Qualification Name column", "Unchecked Qualification Name option in settings window that column is not displayed in Reports page", "PASS");

		click_button("Settings", settingsButton);
		click_button("Qualification Name Check Box", qualificationNameCheckBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(qualificationNameHeader, "Reports Table");

		if(IsDisplayed("Qualification Name Column", qualificationNameHeader))
			oReport.AddStepResult("Qualification Name Column", "Qualification Name Column Displayed", "PASS");
		else
			oReport.AddStepResult("Checked Qualification Name column", "Checked Qualification Name option in settings window but still it is Not displayed in Reports page", "FAIL");

		navigate_to("Navigate Contract Management Suite", "Contract Plugin", contractManagementSuite, contractsPlugIn);

		navigate_to("Navigate to Analytics Suite", "Contract Name Column", analyticsSuite, qualificationNameHeader);
	}

	
	// Reset to My Defaults Validation
	public void resetToQGUsageReportsDefaultSettings() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Reset to My Defaults", resetMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(reportsTable, "Reports Table");

		if(IsDisplayed("Qualification Name Column", qualificationNameHeader)) 
		{
			fixed_wait_time(2);
			waitFor(reportsTable, "Reports Table");
			oReport.AddStepResult("Uncheck Qualification Name column ", "Checked Qualification Name option in settings window and saved changes temporarily then settings saved as Reset to My Default but still Qualification Name column displayed in reports page ",	"FAIL");
		}
		else 
		{
			fixed_wait_time(2);
			waitFor(qualificationNameHeader, "Reports Table");
			oReport.AddStepResult("Qualification Name Column", "Qualification Name Column Not Displayed", "PASS");
		}

		click_button("Settings", settingsButton);
		click_button("Reset to System Defaults", resetSystemDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(reportsTable, "Reports Table");

		if(IsDisplayed("Qualification Name Column", qualificationNameHeader)) 
		{
			fixed_wait_time(2);
			waitFor(reportsTable, "Reports Table");
			oReport.AddStepResult("Qualification Name Column", "Qualification Name Column Displayed", "PASS");
		}
		else 
		{
			fixed_wait_time(2);
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Checked Qualification Name column", "Checked Qualification Name option in settings window but still it is Not displayed in Reports page", "FAIL");
		}

		click_button("Settings", settingsButton);
		click_button("Save as My Defaults", saveMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");
	}

	
	// Comparing Qualification Group Usage Downloaded Reports
	public void comparingQualificationGroupUsageDownloadedReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(qualificationNameColumn, "Reports Table");
		oParameters.SetParameters("QualificationName",get_field_value("Qualification Name Column", qualificationNameColumn));
		addFilter("Qualification Name");
		enter_text_value("Filters Input Text Box", filtersTextBox, oParameters.GetParameters("QualificationName"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("NoOfRecords",get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));

		CreateFolder(oParameters.GetParameters("downloadFilepath"));
		waitFor(downloadCSVButton, "Download CSV Button");

		click_button("Download Button", downloadCSVButton);
		fixed_wait_time(5);

		if(isFileDownloaded("QualificationGroupUsageReport.zip")) 
		{
			unZipFolder(oParameters.GetParameters("downloadFilepath")+"/QualificationGroupUsageReport.zip",oParameters.GetParameters("downloadFilepath"));
			File zipFile = new File(oParameters.GetParameters("downloadFilepath")+"/QualificationGroupUsageReport.zip");
			zipFile.delete();
			csvToExcel();
			@SuppressWarnings("static-access")
			int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
			oParameters.SetParameters("RecordsInExcel", String.valueOf(recordsCount));

			if(oParameters.GetParameters("NoOfRecords").equals(oParameters.GetParameters("RecordsInExcel")))
				oReport.AddStepResult("Downloaded Records", "Downloaded Records Matched", "PASS");
			else
				oReport.AddStepResult("Downloaded Records ", "Records downloaded in excel file but number of record in portal Not Matched with number of records in downloaded excel file",	"FAIL");
		} 
		else
			oReport.AddStepResult("Downloaded File", "Clicked on 'Donwload CSV' button but zip file is not downloaded ", "FAIL");
	}

	
	// Qualification Group Usage Report VR
	public void qualificationGroupUsageReport_VR() 
	{
		login("EDIT");
		changePricingEngine();
		xpathChange();
		navigate_to_Analytics();
		select_Report("Qualification Group Usage Report");
		qualificationTypeFilterReports("DRG Formula Expression,Revenue Code Qualification,Stop Loss Formula Expression,Stop Loss Qualification,Section Qualification,Term/Exclusions Qualification");
		qualificationNameFilterReports();
		rateSheetCodeFilterReports();
		rateSheetTermTypeFilterReports("Term,Exclusion,Stop Loss");
		rateSheetSectionFilterReports();
		resultsPerPage("100");
		pageNavigations();
		createBookmark();
		qualificationFilterReports();
		bookmarkValidation();
		columnSorting();
		settingsWindow();
		temporaryChangesInQGUsageReportSettings();
		portalLogout();
		login("EDIT");
		changePricingEngine();
		navigate_to_Analytics();
		select_Report("Qualification Group Usage Report");
		createReportButton();
		QGusageReportdefaultColumnsValidation();
		defaultChangesInQGUsageReportSetting();
		resetToQGUsageReportsDefaultSettings();
		fullScreenValidation();
		navigatingToOtherReport("Claim Repricing Report","Qualification Group Usage Report");
		comparingQualificationGroupUsageDownloadedReports();
		logout();
	}

	
	// Rate Sheet Terms Report VR

	By rateSheetTermsReport = By.xpath("//div[@class='styled-dropdown black-text-when-disabled ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Rate Sheet Terms Report')]");

	By rateSheetTermsReportDC = By.xpath("//div[@class='styled-dropdown black-text-when-disabled position-relative ng-isolate-scope ng-pristine ng-valid open']//li[@id='i[__valueField]']/a[contains(.,'Rate Sheet Terms Report')]");
	
	By rateSheetNameHeader = By.xpath("//div[@class='heading ng-scope'][contains(text(),'Rate Sheet Name')]");
	
	By statusCodeElement = By.xpath("//li//a[contains(text(),'Status Code')]");

	By statusCodeActiveOption = By.xpath("//li[@class='filter-field ng-scope']/a[contains(text(),'Active')]");

	By statusCodeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'statusCode')\"]");

	By statusCodeInactiveOption = By.xpath("//a[@ng-click='af.searchText = e.Value'][text()='Inactive']");

	
	// Section Code Filter Reports
	public void statusCodeFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Active Status Code filter reports

		addFilter("Status Code");
		click_button("Status Code Active Option", statusCodeActiveOption);
		createReport();
		waitFor(statusCodeColumn, "Reports Table");
		oParameters.SetParameters("ActiveStatusCode", get_field_value("status Code Column", statusCodeColumn));

		if(oParameters.GetParameters("ActiveStatusCode").equalsIgnoreCase(oParameters.GetParameters("StatusCodeActiveValue")))
			oReport.AddStepResult("RateSheet Terms Reports", "Active Rate Sheet Terms Reports Displayed", "PASS");
		else
			oReport.AddStepResult("RateSheet Terms Reports", "Reports are filtered based on Status Code as Active but those reports are not Displayed", "FAIL");

		// Inactive Status Code filter reports

		click_button("Section Code Input Field", qualificationTypeInputField);
		click_button("Section Code Inactive Input Field Element", statusCodeInactiveOption);
		createReport();
		waitFor(statusCodeColumn, "Reports Table");
		oParameters.SetParameters("InactiveStatusCode", get_field_value("Status Code Column", statusCodeColumn));

		if(oParameters.GetParameters("InactiveStatusCode").equals(oParameters.GetParameters("StatusCodeInactiveValue")))
			oReport.AddStepResult("RateSheet Terms Reports","Section Code Type as Inactive Filter Reports are Displayed", "PASS");
		else
			oReport.AddStepResult("RateSheet Terms Reports", "Reports are filtered based on Status Code as Inactive but those reports are not Displayed", "FAIL");

		clearFilters();
	}

	
	By rateSheetNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'rateSheetName')\"]");

	By rateSheetNameElement = By.xpath("//li/a[contains(text(),'Rate Sheet Name')]");

	
	// Rate Sheet Name Filter Reports
	public void rateSheetNameFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(rateSheetNameColumn, "Reports Table");
		oParameters.SetParameters("RateSheetNameBeforeFilter", get_field_value("Rate sheet Name", rateSheetNameColumn));
		addFilter("Rate Sheet Name");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("RateSheetNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetNameColumn, "Reports Table");
		oParameters.SetParameters("RateSheetNameAfterFilter", get_field_value("Ratesheet Name", rateSheetNameColumn));

		if(oParameters.GetParameters("RateSheetNameBeforeFilter").equals(oParameters.GetParameters("RateSheetNameAfterFilter")))
			oReport.AddStepResult("Ratesheet Name Reports", "Ratesheet Name Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Ratesheet Name Reports",	"Reports are filtered based on Ratesheet Name but those reports are not displayed ", "FAIL");

		clearFilters();
	}

	
	By rateSheetPeriodEffectiveDateElement = By.xpath("//li/a[contains(text(),'Rate Sheet Period Effective Date')]");

	By rateSheetPeriodEffectiveDateColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[3]");

	
	// Rate Sheet Period Effective Date Filter Reports
	public void rateSheetPeriodEffectiveDateFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Rate Sheet Period Effective Date");
		oParameters.SetParameters("SystemCurrentDate", get_specified_date(0));
		oParameters.SetParameters("RateSheetPeriodEffectiveStartDate", get_specified_date(-1000));
		enter_text_value("Rate Sheet Period Start Date", admitStartDate,oParameters.GetParameters("RateSheetPeriodEffectiveStartDate"));
		enter_text_value("Rate Sheet Period End Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetPeriodEffectiveDateColumn, "Reports Table");
		oParameters.SetParameters("RateSheetPeriodDate2", getToolTipValue(rateSheetPeriodEffectiveDateColumn));

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		try 
		{
			Date rateSheetPeriodEffectiveDate2 = sdf.parse(oParameters.GetParameters("RateSheetPeriodDate2"));
			Date rateSheetPeriodEffectiveDate1 = sdf.parse(oParameters.GetParameters("RateSheetPeriodEffectiveStartDate"));

			if(rateSheetPeriodEffectiveDate2.after(rateSheetPeriodEffectiveDate1)) 
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Rate Sheet period Date Results",	"Rate Sheet Period Effective Date Results Displayed", "PASS");
			}
			else 
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Rate Sheet Period Date Results",	"Reports are filtered based on Rate Sheet Period Effective date but those reports are not Displayed","FAIL");
			}
		}
		catch(ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		clearFilters();
	}

	
	By rateSheetPeriodTerminationDateElement = By.xpath("//li/a[contains(text(),'Rate Sheet Period Termination Date')]");

	By rateSheetPeriodTerminationDateColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[4]");

	
	// Rate Sheet Period Termination Date Filter Reports
	public void rateSheetPeriodTerminationDateFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Rate Sheet Period Termination Date");
		oParameters.SetParameters("SystemCurrentDate", get_specified_date(0));
		oParameters.SetParameters("RateSheetPeriodTerminationStartDate", get_specified_date(-1000));
		enter_text_value("Rate Sheet Period Start Date", admitStartDate,oParameters.GetParameters("RateSheetPeriodTerminationStartDate"));
		enter_text_value("Rate Sheet Period End Date", admitEndDate, oParameters.GetParameters("SystemCurrentDate"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(rateSheetPeriodTerminationDateColumn, "Reports Table");
		oParameters.SetParameters("RateSheetPeriodTerminationDate2",getToolTipValue(rateSheetPeriodTerminationDateColumn));

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		try
		{
			Date rateSheetPeriodTerminationDate2 = sdf.parse(oParameters.GetParameters("RateSheetPeriodTerminationDate2"));
			Date rateSheetPeriodTerminationDate1 = sdf.parse(oParameters.GetParameters("RateSheetPeriodTerminationStartDate"));

			if(rateSheetPeriodTerminationDate2.after(rateSheetPeriodTerminationDate1)) 
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Rate Sheet period Termination Date Results",	"Rate Sheet Period Termination Date Results Displayed", "PASS");
			}
			else
			{
				waitFor(createReportButton, "Create Report Button");
				oReport.AddStepResult("Rate Sheet Period Date Termination Results",	"Reports are filtered based on Rate Sheet Period Termination date but those reports are not Displayed",	"FAIL");
			}
		} 
		catch(ParseException e) 
		{
			System.out.println("Exception Caught" + e);
		}
		clearFilters();
	}

	
	By sectionNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'sectionName')\"]");

	By sectionNameElement = By.xpath("//li/a[contains(text(),'Section Name')]");

	By sectionaNameHeader = By.xpath("//*[@title='Section Name']");
	
	By firstHeaderPath = By.xpath("//table[@class='data-table-container']//thead/tr/td[1]");
	
	By HeaderPath = By.xpath("//table[@class='data-table-container']//thead/tr/td");
	
	By ContentPath = By.xpath("//table[@class='data-table-container']//tbody//tbody/tr"); 
	
	
	// Section Name Filter Reports
	public void sectionNameFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		
		String SectionName= get_table_row_value(HeaderPath, ContentPath, "Section Name", 1);
		
		if(SectionName.equalsIgnoreCase("N/A"))
			click_button("SectionName Header", sectionaNameHeader);

		waitFor(sectionNameColumn, "Reports Table");
		
		oParameters.SetParameters("SectionNameBeforeFilter", get_field_value("Section Name", sectionNameColumn));
		addFilter("Section Name");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("SectionNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(sectionNameColumn, "Reports Table");
		oParameters.SetParameters("SectionNameAfterFilter", get_field_value("Section Name", sectionNameColumn));

		if(oParameters.GetParameters("SectionNameBeforeFilter").equals(oParameters.GetParameters("SectionNameAfterFilter")))
			oReport.AddStepResult("Section Name Reports", "Section Name Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Section Name Reports","Reports are filtered based on Section Name but those reports are not displayed ", "FAIL");

		clearFilters();
	}

	
	By termNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'termName')\"]");

	By termNameElement = By.xpath("//li/a[contains(text(),'Term Name')]");

	
	// Term Name Filter Reports
	public void termNameFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(termNameColumn, "Reports Table");
		oParameters.SetParameters("TermNameBeforeFilter", get_field_value("Term Name", termNameColumn));
		addFilter("Term Name");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("TermNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(termNameColumn, "Reports Table");
		oParameters.SetParameters("TermNameAfterFilter", get_field_value("Term Name", termNameColumn));

		if(oParameters.GetParameters("TermNameBeforeFilter").equals(oParameters.GetParameters("TermNameAfterFilter")))
			oReport.AddStepResult("Term Name Reports", "Term Name Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Term Name Reports", "Reports are filtered based on Term Name but those reports are not displayed ", "FAIL");

		clearFilters();
	}

	
	By rateTypeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'rateType')\"]");

	By rateTypeElement = By.xpath("//li/a[contains(text(),'Rate Type')]");

	By rateTypeTieredOption = By.xpath("//li[@class='filter-field ng-scope']/a[text()='Tiered']");

	By rateTypePerDiemInputField = By.xpath("//a[@ng-click='af.searchText = e.Value'][text()='Per Diem']");

	
	// Rate Type Filter Reports
	public void rateTypeFilterReports(String rateType) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = rateType.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for(int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}			
			createReport();
			waitFor(rateTypeColumn, "Reports Table");
			addFilter("Rate Type");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(rateTypeColumn, "Reports Table");
			oParameters.SetParameters("RateTypeValue", get_field_value("Rate Type", rateTypeColumn));

			if(oParameters.GetParameters("RateTypeTieredValue").contains(oParameters.GetParameters("RateTypeValue")))
				oReport.AddStepResult("Rate Type Reports", "Rate Type as Tiered Filter Reports are Displayed ", "PASS");
			else
				oReport.AddStepResult("Rate Type Reports", "Reports are filtered based on Rate Term Type as "+word+" but those reports are not displayed ", "FAIL");

			clearFilters();
		}
	}

	
	By scheduleTypeColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[9]");

	By scheduleTypeElement = By.xpath("//li/a[contains(text(),'Schedule Type')]");

	By modifierScheduleTypeOption = By.xpath("//a[text()='Modifier Schedules']");

	By scheduleTypeFeeScheduleInputOption = By.xpath("//a[@ng-click='af.searchText = e.Value'][text()='Fee Schedule']");

	
	// Schedule Type Filter Reports
	public void scheduleTypeFilterReports(String scheduleType) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = scheduleType.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for(int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}			
			createReport();
			addFilter("Schedule Type");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(scheduleTypeColumn, "Reports Table");
			oParameters.SetParameters("ScheduleTypeValue", get_field_value("Schedule Type", scheduleTypeColumn));

			if(oParameters.GetParameters("ScheduleType").contains(oParameters.GetParameters("ScheduleTypeValue")))
				oReport.AddStepResult("Schedule Type Reports", "Schedule Type Filter Reports are Displayed ", "PASS");
			else
				oReport.AddStepResult("Schedule Type Reports", "Reports are filtered based on Schedule Type as "+word+" Schedules but those reports are not displayed ","FAIL");

			clearFilters();
		}
	}

	
	By scheduleNameColumn = By.xpath("//*[@id='report-table-parent']//table/tbody/tr[1]/td[10]");

	By scheduleNameElement = By.xpath("//li/a[contains(text(),'Schedule Name')]");

	
	// Schedule Name Filter Reports
	public void scheduleNameFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		// Schedule Name with Contains Operator

		createReport();
		waitFor(scheduleNameColumn, "Reports Table");
		addFilter("Schedule Name");
		click_button("Schedule Name Operators Button", billTypeOperators);
		click_button("Schedule Name Contains Operator", billTypeContainsOperator);
		enter_text_value("Filters Input Text Box", filtersTextBox, oParameters.GetParameters("ScheduleNameValue"));
		performKeyBoardAction("ENTER");
		createReport();
		oParameters.SetParameters("ScheduleNameAfterFilter",get_field_value("Schedule Name Column", scheduleNameColumn));

		if(oParameters.GetParameters("ScheduleNameValue").contains(oParameters.GetParameters("ScheduleNameAfterFilter")))
			oReport.AddStepResult("Schedule Name Contains Result", "Schedule Name Contains filters reports are Displayed", "PASS");
		else
			oReport.AddStepResult("Schedule Name Contains Result", "Reports are filtered based on Schedule Name Contains but those reports are not Displayed", "FAIL");

		// Schedule Name with Exact Operator

		click_button("Schedule Name Operators Button", billTypeOperator);
		click_button("Schedule Name Exact Operator", billTypeExactOperator);
		createReport();
		oParameters.SetParameters("ScheduleNameExact", get_field_value("Schedule Name", scheduleNameColumn));

		if(oParameters.GetParameters("ScheduleNameValue").equals(oParameters.GetParameters("ScheduleNameExact")))
			oReport.AddStepResult("Schedule Name Exact Result", "Schedule Name Exact Result Displayed", "PASS");
		else
			oReport.AddStepResult("Schedule Name Exact Result",	"Reports are filtered based on Schedule Name with Exact operator but those reports are not displayed ",	"FAIL");

		// Schedule Name With Not Exact

		click_button("Schedule Name Operators Button", billTypeOperator);
		click_button("Schedule Name Not Exact Operator", billTypeNotExactOperator);
		createReport();
		oParameters.SetParameters("ScheduleNameNotExact", get_field_value("Schedule Name", scheduleNameColumn));

		if(oParameters.GetParameters("ScheduleNameValue").equals(oParameters.GetParameters("ScheduleNameNotExact"))) 
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Schedule Name Not Exact Result",	"Reports are filtered based on Schedule Name with not Exact operator but those reports are not displayed ", "FAIL");
		} 
		else
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Schedule Name Not Exact Result", "Schedule Name Not Exact Result Displayed", "PASS");
		}

		// Schedule Name With Does Not Contain

		click_button("Schedule Name Operators Button", billTypeOperator);
		click_button("Schedule Name Does Not Contain Operator", billTypeDoesNotContain);
		createReport();
		oParameters.SetParameters("ScheduleNameNotContain", get_field_value("Schedule Name", scheduleNameColumn));

		if(oParameters.GetParameters("ScheduleNameValue").equals(oParameters.GetParameters("ScheduleNameNotContain"))) 
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Schedule Name Does Not Contain Result", "Reports are filtered based on Schedule Name with does not contain operator but those reports are not displayed ", "FAIL");
		}
		else
		{
			waitFor(createReportButton, "Create Report Button");
			oReport.AddStepResult("Schedule Name Does Not Contain Result", "Schedule Name Does Not Contain Result Displayed", "PASS");
		}
		clearFilters();
	}

	
	By sectionTermTypeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'termType')\"]");
	
	
	public void sectionTermTypeFilterReports(String sectionTermType)
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = sectionTermType.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for(int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}
			
			addFilter("Section/Term Type");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(sectionTermTypeColumn, "Reports Table");
			oParameters.SetParameters("SectionTermTypeValue", get_field_value("Section Term Type Column", sectionTermTypeColumn));

			if(oParameters.GetParameters("SectionTermType").contains(oParameters.GetParameters("SectionTermTypeValue")))
				oReport.AddStepResult("Section Term Type Reports", "Section Term Type Filter Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Sectoin Term Type Reports", "Reports are filtered based on Section Term Type as "+word+" but those reports are not displayed ", "FAIL");

			clearFilters();
		}
	}
	
	
	By codeElement = By.xpath("//li/a[(text()='Code')]");

	By codeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'code')\"]");

	
	// Code Filter Reports
	public void codeFilterReports(String code) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = code.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for(int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}
			
			addFilter("Code");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(codeColumn, "Reports Table");
			oParameters.SetParameters("CodeAfterFilter", get_field_value("Code", codeColumn));

			if(oParameters.GetParameters("CodeValue").contains(oParameters.GetParameters("CodeAfterFilter")))
				oReport.AddStepResult("Code Reports", "Code Filter Reports Displayed", "PASS");
			else
				oReport.AddStepResult("Code Reports", "Reports are filtered based on "+word+" but those reports are not displayed ", "FAIL");

			clearFilters();
		}		
	}

	
	By codeNameElement = By.xpath("//li/a[(text()='Code Name')]");

	By codeNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'codeName')\"]");

	
	// Code Name Filter Reports
	public void codeNameFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("Code Name");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("CodeNamePTCAValue"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(codeNameColumn, "Reports Table");
		oParameters.SetParameters("CodeNameAfterFilter", get_field_value("Code Name", codeNameColumn));

		if(oParameters.GetParameters("CodeNameAfterFilter").equals(oParameters.GetParameters("CodeNamePTCAValue")))
			oReport.AddStepResult("Code Name Reports", "Code Name Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Code Name Reports", "Reports are filtered based on Code Name but those reports are not displayed ", "FAIL");

		// Code Name Filter Reports

		clearFilters();
		createReport();
		addFilter("Code Name");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("CodeNameWoundCareValue"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(codeNameColumn, "Reports Table");
		oParameters.SetParameters("EditedCodeNameAfterFilter", get_field_value("Code Name", codeNameColumn));

		if(oParameters.GetParameters("EditedCodeNameAfterFilter").equals(oParameters.GetParameters("CodeNameWoundCareValue")))
			oReport.AddStepResult("Code Name Reports", "Code Name Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("Code Name Reports", "Reports are filtered based on Code name but those reports are not displayed ", "FAIL");

		clearFilters();
	}

	
	By PPSGroupTypeColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow enum ng-scope']/span[@ng-click=\"expandRow('0' , row, 'pps')\"]");

	By PPSGroupTypeElement = By.xpath("//li/a[contains(text(),'PPS Group Type')]");

	By PPSCMGOption = By.xpath("//a[text()='CMG']");

	By PPSGroupDRGInputField = By.xpath("//a[@ng-click='af.searchText = e.Value'][text()='DRG']");

	
	// PPS Group Type Filter Reports
	public void PPSGroupTypeFilterReports(String PPSGroupType) 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String checkBoxNames [] = PPSGroupType.split(",");		
		int numberOfItems = checkBoxNames.length;
		
		for(int i=0; i<numberOfItems; i++)
		{
			String word="";
			for(int j=0;j<=i;j++)
        	{
				String tempWord=checkBoxNames[j];
        		if(j!=0)
        			word=word+","+tempWord;
        		else
        			word=tempWord;				
        	}
			
			createReport();
			addFilter("PPS Group Type");
			selectMultipleCheckBoxes(word);
			createReport();
			waitFor(PPSGroupTypeColumn, "Reports Table");
			oParameters.SetParameters("PPSGroupTypeValue", get_field_value("PPS Group Type", PPSGroupTypeColumn));

			if(oParameters.GetParameters("PPSGroupType").contains(oParameters.GetParameters("PPSGroupTypeValue")))
				oReport.AddStepResult("PPS Group Type Reports", "PPS Group Type Filter Reports are Displayed ", "PASS");
			else
				oReport.AddStepResult("PPS Group Type Reports",	"Reports are filtered based on PPS Group Type as "+word+" but those reports are not displayed ", "FAIL");

			clearFilters();
		}
	}

	
	By PPSNameElement = By.xpath("//li/a[(text()='PPS Name')]");

	By PPSNameColumn = By.xpath("//*[@id='report-table-parent']//td[@class='hide-overflow string ng-scope']/span[@ng-click=\"expandRow('0' , row, 'ppsName')\"]");

	
	// PPS Name Filter Reports
	public void PPSNameFilterReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		addFilter("PPS Name");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("PPSNameRUGExerciseValue"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(PPSNameColumn, "Reports Table");
		oParameters.SetParameters("PPSNameAfterFilter", get_field_value("PPS Name", PPSNameColumn));

		if(oParameters.GetParameters("PPSNameAfterFilter").equals(oParameters.GetParameters("PPSNameRUGExerciseValue")))
			oReport.AddStepResult("PPS Name Reports", "PPS Name Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("PPS Name Reports", "Reports are filtered based on PPS Name but those reports are not displayed ", "FAIL");

		// PPS Name Filter Reports

		clearFilters();
		createReport();
		addFilter("PPS Name");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("PPSNameoctobercopyValue"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(PPSNameColumn, "Reports Table");
		oParameters.SetParameters("EditedPPSNameAfterFilter", get_field_value("PPS Name", PPSNameColumn));

		if(oParameters.GetParameters("EditedPPSNameAfterFilter").equals(oParameters.GetParameters("PPSNameoctobercopyValue")))
			oReport.AddStepResult("PPS Name Reports", "PPS Name Filter Reports Displayed", "PASS");
		else
			oReport.AddStepResult("PPS Name Reports", "Reports are filtered based on PPS name but those reports are not displayed ", "FAIL");

		clearFilters();
	}

	
	// Rate Sheet Code validation
	public void rateSheetCodeValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(rateSheetCodeColumn, "Reports Table");
		oParameters.SetParameters("RateSheetCodeName", get_field_value("Rate Sheet Code", rateSheetCodeColumn));
		click_button("Rate Sheet Code Link", rateSheetCodeColumn);
		waitFor(rateSheetTitleBar, "Rate Sheet Title bar");
		oParameters.SetParameters("RateSheetTitleText",get_field_value("Rate Sheet Title Text", rateSheetTitleBar).replace("Code ", ""));
		fixed_wait_time(3);

		if(oParameters.GetParameters("RateSheetCodeName").equalsIgnoreCase(oParameters.GetParameters("RateSheetTitleText")))
			oReport.AddStepResult("Navigated and Selected Rate Sheet", "Navigated and Selected Rate Sheet Displayed","PASS");
		else
			oReport.AddStepResult("Navigated and Selected Rate Sheet", "Clicked on Rate Sheet Code link but page not navigated to Rate Sheet and it doesn't displayed the selected rate sheet ", "FAIL");

		navigate_to_Analytics();
	}

	
	// Rate Sheet Name Column Sorting
	public void rateSheetNameColumnSorting() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		click_button("Rate Sheet Name Header", rateSheetNameHeader);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("RateSheetNameColumnBeforeSort",get_field_value("Rate Sheet Name Column Text", rateSheetNameColumn));
		click_button("Rate Sheet Name Header", rateSheetNameHeader);
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("RateSheetNameColumnAfterSort",get_field_value("Rate Sheet Name Column Text", rateSheetNameColumn));

		if(oParameters.GetParameters("RateSheetNameColumnBeforeSort").equals(oParameters.GetParameters("RateSheetNameColumnAfterSort")))
			oReport.AddStepResult("Column Sorted Results","Mouse hovered and clicked on Rate Sheet Name Header but that column is not sorted from descending to ascending order ","FAIL");
		else
			oReport.AddStepResult("Column Sorted Results", "Rate Sheet Name Column Sorted Results Displayed", "PASS");
	}

	
	By rateSheetNameCheckBox = By.xpath("//li/input[@id='rateSheetName'][@type='checkbox']");

	
	// Doing Temporary Changes in Rate Sheet Terms Report Settings
	public void temporaryChangesInRateSheetTermsReportSettings() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Rate Sheet Name Check Box", rateSheetNameCheckBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Rate Sheet Name Column", rateSheetNameHeader))
			oReport.AddStepResult("Uncheck Rate Sheet Name column",	"Unchecked Rate Sheet Name option in settings window but still it is displayed in Reports page","FAIL");
		else
			oReport.AddStepResult("Uncheck Rate Sheet Name column",	"Unchecked Rate Sheet Name option in settings window that column is not displayed in Reports page",	"PASS");
	}

	
	// Default Columns Validation
	public void rateSheetTermsReportdefaultColumnsValidation() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Rate Sheet Name Column", rateSheetNameHeader))
			oReport.AddStepResult("Rate Sheet Name Column", "Rate Sheet Name Column Displayed", "PASS");
		else
			oReport.AddStepResult("Checked Rate Sheet Name column",	"Checked Rate Sheet Name option in settings window but still it is Not displayed in Reports page","FAIL");
	}

	
	// Default Changes in settings validation
	public void defaultChangesInRateSheetTermsReportSetting() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		click_button("Settings", settingsButton);
		click_button("Rate Sheet Name Check Box", rateSheetNameCheckBox);
		click_button("Save as My Defaults", saveMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");

		if(IsDisplayed("Rate Sheet Name Column", rateSheetNameHeader))
			oReport.AddStepResult("Uncheck Rate Sheet Name column",	"Unchecked Rate Sheet Name option in settings window but still it is displayed in Reports page","FAIL");
		else
			oReport.AddStepResult("Uncheck Qualification Name column", "Unchecked Rate Sheet Name option in settings window that column is not displayed in Reports page", "PASS");

		click_button("Settings", settingsButton);
		click_button("Rate Sheet Name Check Box", rateSheetNameCheckBox);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(rateSheetNameHeader, "Reports Table");

		if(IsDisplayed("Rate Sheet Name Column", rateSheetNameHeader))
			oReport.AddStepResult("Rate Sheet Name Column", "Rate Sheet Name Column Displayed", "PASS");
		else
			oReport.AddStepResult("Checked Rate Sheet Name column",	"Checked Rate Sheet Name option in settings window but still it is Not displayed in Reports page","FAIL");

		navigate_to("Navigate Contract Management Suite", "Contract Plugin", contractManagementSuite, contractsPlugIn);

		navigate_to("Navigate to Analytics Suite", "Contract Name Column", analyticsSuite, qualificationNameHeader);
	}

	
	// Reset to My Defaults Validation
	public void resetToRateSheetTermsReportsDefaultSettings() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		click_button("Settings", settingsButton);
		click_button("Reset to My Defaults", resetMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(reportsTable, "Reports Table");

		if(IsDisplayed("Rate Sheet Name Column", rateSheetNameHeader)) 
		{
			fixed_wait_time(2);
			waitFor(reportsTable, "Reports Table");
			oReport.AddStepResult("Uncheck Rate Sheet Name column ", "Checked Rate Sheet Name option in settings window and saved changes temporarily then settings saved as Reset to My Default but still Qualification Name column displayed in reports page ","FAIL");
		} 
		else
		{
			fixed_wait_time(2);
			waitFor(rateSheetNameHeader, "Reports Table");
			oReport.AddStepResult("Rate Sheet Name Column", "Rate Sheet Name Column Not Displayed", "PASS");
		}

		click_button("Settings", settingsButton);
		click_button("Reset to System Defaults", resetSystemDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(reportsTable, "Reports Table");

		if(IsDisplayed("Rate Sheet Name Column", rateSheetNameHeader)) 
		{
			fixed_wait_time(2);
			waitFor(reportsTable, "Reports Table");
			oReport.AddStepResult("Rate Sheet Name Column", "Rate Sheet Name Column Displayed", "PASS");
		}
		else
		{
			fixed_wait_time(2);
			waitFor(addFilterElement, "Add Filter");
			oReport.AddStepResult("Checked Rate Sheet Name column",	"Checked Rate Sheet Name option in settings window but still it is Not displayed in Reports page","FAIL");
		}
		click_button("Settings", settingsButton);
		click_button("Save as My Defaults", saveMyDefaultsRadio);
		click_button("Settings Save Button", settingsSaveButton);
		waitFor(createReportButton, "Create Report Button");
	}

	
	// Comparing Rate Sheet Terms Downloaded Reports
	public void comparingRateSheetTermsDownloadedReports() 
	{
		//To check the status of "Continued_Execution" parameter, if it is set to NO it will skip this method, if not it will resume the execution 
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			//Adding step result to report
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		createReport();
		waitFor(termNameColumn, "Reports Table");
		oParameters.SetParameters("TermNameBeforeFilter", get_field_value("Term Name", termNameColumn));
		addFilter("Term Name");
		enter_text_value("Filters Text Box", filtersTextBox, oParameters.GetParameters("TermNameBeforeFilter"));
		performKeyBoardAction("ENTER");
		createReport();
		waitFor(termNameColumn, "Reports Table");
		waitFor(createReportButton, "Create Report Button");
		oParameters.SetParameters("NoOfRecords",get_field_value("No. of Records ", noOfRecordsElement).replaceAll("[, Records]", ""));

		CreateFolder(oParameters.GetParameters("downloadFilepath"));
		waitFor(downloadCSVButton, "Download CSV Button");

		click_button("Download Button", downloadCSVButton);
		fixed_wait_time(5);

		if(isFileDownloaded("RateSheetTermsUsageReport.zip")) 
		{
			unZipFolder(oParameters.GetParameters("downloadFilepath")+"/RateSheetTermsUsageReport.zip",oParameters.GetParameters("downloadFilepath"));
			File zipFile = new File(oParameters.GetParameters("downloadFilepath")+"/RateSheetTermsUsageReport.zip");
			zipFile.delete();
			csvToExcel();
			@SuppressWarnings("static-access")
			int recordsCount = oExcelData.getRowCount(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
			oParameters.SetParameters("RecordsInExcel", String.valueOf(recordsCount));

			if(oParameters.GetParameters("NoOfRecords").equals(oParameters.GetParameters("RecordsInExcel")))
				oReport.AddStepResult("Downloaded Records", "Downloaded Records Matched", "PASS");
			else
				oReport.AddStepResult("Downloaded Records ", "Records downloaded in excel file but number of record in portal Not Matched with number of records in downloaded excel file",	"FAIL");
		}
		else
			oReport.AddStepResult("Downloaded File", "Clicked on 'Donwload CSV' button but zip file is not downloaded ", "FAIL");
	}

	
	// Rate Sheet Terms Report VR
	public void rateSheetTermsReport_VR() 
	{
		login("EDIT");		
		changePricingEngine();
		xpathChange();
		navigate_to_Analytics();
		select_Report("Rate Sheet Terms Report");
		statusCodeFilterReports();
		rateSheetNameFilterReports();
		rateSheetPeriodEffectiveDateFilterReports();
		rateSheetPeriodTerminationDateFilterReports();
		sectionNameFilterReports();
		termNameFilterReports();
		//rateTypeFilterReports("Percentage,Tiered,Per Diem,Formula,By Revenue Code,Fee Schedule,Per Case,Per Hour,Per Minute,Per Length of Stay,Per Service,Additive Tiered,DRG Pricer Rate,DRG User Rate,DRG User Payment Rate,DRG User Weight Rate,APC/APG Pricer Rate,Per Minute Unit Rate,Variable Additive Tiered Rate,Flat,Procedure Group Rate,Discount Tiered Rate,Per Unit Rate,RUG User Rate,Dosage Quantity,IRF CMG Pricer Rate,Dialysis PPS Rate,Revenue Code Per Day or Per Case,PPS Professional Rate");
		rateTypeFilterReports("Percentage,Tiered,Per Diem,Formula,By Revenue Code,Fee Schedule,Per Case,Per Hour,Per Minute,Per Length of Stay,Per Service,Additive Tiered,DRG Pricer Rate,DRG User Rate,APC/APG Pricer Rate,Per Minute Unit Rate,Variable Additive Tiered Rate,Flat,Procedure Group Rate,Discount Tiered Rate,Per Unit Rate,RUG User Rate,Dosage Quantity,IRF CMG Pricer Rate,Dialysis PPS Rate,Revenue Code Per Day or Per Case,PPS Professional Rate");
		scheduleTypeFilterReports("Fee Schedule,Group Rate Codes,Modifier Schedules,Related Procedure Schedules");
		scheduleNameFilterReports();
		sectionTermTypeFilterReports("Term,Exclusion,Stop Loss");
		codeFilterReports("Diagnosis,DRG,HCPCS/CPT,ICD");
		codeNameFilterReports();
		PPSGroupTypeFilterReports("APC/APG,CMG,Dialysis,DRG,RUG");
		PPSNameFilterReports();
		rateSheetCodeValidation();
		resultsPerPage("100");
		pageNavigations();
		createBookmark();
		sectionNameFilterReports();
		bookmarkValidation();
		rateSheetNameColumnSorting();
		settingsWindow();
		temporaryChangesInRateSheetTermsReportSettings();
		portalLogout();
		login("EDIT");
		changePricingEngine();
		navigate_to_Analytics();
		select_Report("Rate Sheet Terms Report");
		createReportButton();
		rateSheetTermsReportdefaultColumnsValidation();
		defaultChangesInRateSheetTermsReportSetting();
		resetToRateSheetTermsReportsDefaultSettings();
		fullScreenValidation();
		navigatingToOtherReport("Claim Repricing Report","Rate Sheet Terms Report");
		comparingRateSheetTermsDownloadedReports();
		logout();
	}
}
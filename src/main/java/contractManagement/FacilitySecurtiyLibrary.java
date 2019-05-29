package contractManagement;

import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import libraries.CCMLibrary;
import libraries.Parameters;
import libraries.Report;

public class FacilitySecurtiyLibrary extends CCMLibrary{

	public FacilitySecurtiyLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
		
	}

	By configuration_Suite = By.xpath("//a[text()='Configuration']");
	
	
	public void navigation_COnfiguration()
	{
		navigate("Configuration", configuration_Suite);
	}
	
	
	By Facility = By.xpath("//a[text()='Facility']");
	
	public void Navigation_Facility()
	{
		navigate("Facility", Facility);
	}
	
	
	By AddFacility = By.xpath("//span[text()='Add Facility']");
	
	By AddFacilityWindow = By.xpath("//div[@title='Add Facility']");
	
	//This method is used for to click add Facility Icon.
	public void AddFacility()
	{
		click_button("Add Facility Icon", AddFacility);
		
		if(IsDisplayed("Add Facility Window ", AddFacilityWindow))
			oReport.AddStepResult("Add Faility Link", "Clicked on Add Facility icon and verified that add faility window is displayed", "PASS");
		else
			oReport.AddStepResult("Add Faility Link", "Clicked on Add Facility icon and verified that add faility window is not displayed", "FAIL");
	}
	
	
	By FacilityName_Feild = By.xpath("//input[@id='facilityName']");
	
	By AddProviderToFacilitySearchBox = By.xpath("//input[@id='facilityNpiSearch']");
	
	By AddFacilityWindow_SaveButton = By.xpath("//div[@id='showFacilityMasterModal']//input[@value='Save']");
	
	By AddedFacilityNameList = By.xpath("//div[@id='facilityTemplate']//ul[@class='data-list drillable-list pad-l-0']//li");
	
	@SuppressWarnings("unused")
	public void AddFacilityDetails(String facilityName,String addProviderToFacility)
	{
		
		enter_text_value("Facility Name", FacilityName_Feild, facilityName+System.currentTimeMillis());
		
		oParameters.SetParameters("AddedFailityName", get_field_value("Facility", FacilityName_Feild));
		
		enter_text_value("Add Provider to Facility Search Box ", AddProviderToFacilitySearchBox, "");
		performKeyBoardAction("ENTER");
		
		By SelectFacilityName = By.xpath("//span[text()='"+addProviderToFacility+"']");
		
		waitFor(SelectFacilityName, "Waiting For Facility Name");
		
		click_button("Add Provider To Facility", SelectFacilityName);
		
		if(IsEnabled("Add Facility window Save Button", AddFacilityWindow_SaveButton))
		{
			oReport.AddStepResult("", "Filled All Details in Add Facilty", "PASS");
			click_button("Add Facility Save Button", AddFacilityWindow_SaveButton);
		}
		
		 List<WebElement> list  = convertToWebElements(AddedFacilityNameList);
		 int j = 0;
		 
		 for(int i=1;i<=list.size();i++)
		 {
			if( list.get(i).getText().equals(oParameters.GetParameters("AddedFailityName")))
			
				oReport.AddStepResult("", "", "PASS");
			 	j =i;
				break;
		 }
		 
		 if(j > list.size())
			 oReport.AddStepResult("", "", "FAIL");
			 
		 
		
		
	}
	
	
	public void FacilityCheck()
	{
		login("EDIT");
		changePricingEngine();
		
		navigation_COnfiguration();
		
		Navigation_Facility();
		AddFacility();
		AddFacilityDetails(oParameters.GetParameters("FacilityName"),"");
		
		logout();
		
	}
	
	
	
	
	
}

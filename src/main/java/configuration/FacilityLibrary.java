package configuration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import libraries.CCMLibrary;
import libraries.ExcelData;
import libraries.Parameters;
import libraries.Report;

public class FacilityLibrary extends CCMLibrary
{
	ExcelData oExcelData = new ExcelData(oParameters);

	public FacilityLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}

	
	By facilityPlugin = By.xpath("//a[text()='Facility']");
	
	By addFacilityLink = By.xpath("//a[contains(.,'Add a Facility')]");
	
	
	
	// Navigating to Rate Sheets
	public void navigateToFacility()
	{
		navigate_to("Facility Plugin", "Add a Facility Link", facilityPlugin, addFacilityLink);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

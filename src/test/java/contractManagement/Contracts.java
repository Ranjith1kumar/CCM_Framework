package contractManagement;

import contractManagement.ContractLibrary;
import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;

public class Contracts 
{
	Browser oBrowser;
	ContractLibrary oContractLibrary;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;

	String className[] = this.getClass().getName().toUpperCase().split("\\."); 

	@BeforeMethod()
	public void InitailSetup() 
	{
		oParameters = new Parameters();
		oParameters.SetParameters("CLASSNAME", className[1]);
	}
//
	@BeforeClass()
	public void InitailSetup1() 
	{
		oClassParameters = new Parameters();
		oClassParameters.SetParameters("CLASSNAME", className[1]);
		oClassReport = new ClassReport(oClassParameters);
	}

	@Test()
	public void CCM_VR_Soarian_Contracts_Contracted_And_Non_Contracted_Contracts() 
	{
		
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try
		{
			oBrowser = new Browser(oParameters);
			oContractLibrary = new ContractLibrary(Browser.driver, Browser.oReport, oParameters);

			oContractLibrary.Contracted_NonContracted();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Contracts_CRUD_RateSheet_Association() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

			oBrowser = new Browser(oParameters);
			oContractLibrary = new ContractLibrary(Browser.driver, Browser.oReport, oParameters);

			oContractLibrary.CRUD_RatesheetAssociation();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Contracts_Search_Contracts() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

			oBrowser = new Browser(oParameters);
			oContractLibrary = new ContractLibrary(Browser.driver, Browser.oReport, oParameters);

			oContractLibrary.Contracts_SearchContracts();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test
	public void CCM_VR_Soarian_Contracts_View_Only_Permission() 
	{
		try
		{
			oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

			oBrowser = new Browser(oParameters);
			oContractLibrary = new ContractLibrary(Browser.driver, Browser.oReport, oParameters);

			oContractLibrary.Contracts_ViewOnlyPermission();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	
	@Test()
	public void CCM_VR_Soarian_Contracts_Contracted_And_Non_Contracted_Contracts1() 
	{
		
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try
		{
			oBrowser = new Browser(oParameters);
			oContractLibrary = new ContractLibrary(Browser.driver, Browser.oReport, oParameters);

			oContractLibrary.Contracted_NonContracted();
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught" + e);
		}
	}

	
	@AfterMethod
	public void FinalSetup() 
	{
		oBrowser.cleanup();
		oClassReport.AddClassResult(oParameters.GetParameters("TESTNAME"), oParameters.GetParameters("TestStatus"),	oParameters.GetParameters("HTML_REPORT_NAME"));
		Browser.oAuraSetup.ALM_JIRA_Update(oParameters);
	}

	@AfterClass
	public void FinalSetup1() 
	{
		oClassReport.cleanup();
	}
}
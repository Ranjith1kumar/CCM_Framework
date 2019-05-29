package testPriceWorkFlows;


import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.*;
import libraries.*;
import libraries.Parameters;

public class TestPriceScenarios
{
	Browser oBrowser;
	TestPriceScenariosLibrary oTestPriceScenarios;
	Parameters oParameters, oClassParameters;
	ClassReport oClassReport;

	String className[] = this.getClass().getName().toUpperCase().split("\\."); // Package Name

	@BeforeMethod
	public void InitailSetup() 
	{
		oParameters = new Parameters();
		oParameters.SetParameters("CLASSNAME", className[1]);
	}

	@BeforeClass
	public void InitailSetup1() 
	{
		oClassParameters = new Parameters();
		oClassParameters.SetParameters("CLASSNAME", className[1]);
		oClassReport = new ClassReport(oClassParameters);
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_EndToEnd_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPriceEndToEndScenario();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
			
			//If Any Exception Caught in TestPriceEndToEnd_VR means i need to delete Ratesheet which i'am created in EndToEnd VR, So i'am using DeleteRateSheet_TestPriceEndToEnd_VR method in Catch Block.
			//oTestPriceScenarios.DeleteRateSheet_TestPriceEndToEnd_VR();
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_TieredRate() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_TieredRate();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_FeeSchedule() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());

		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_FeeSchedule();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_DosageQuantity() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);
			
			oTestPriceScenarios.TestPrice_RateSheet_RateType_Dosage();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_ProcedureGroup() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_ProcedureGroup();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_PerCase_FlatRates_And_Percentage() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_PerCase();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_PerService_FlatRates_And_Percentage() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_PerService();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_PerDiem_FlatRates_And_Percentage() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_PerDiem();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_PerLengthOfStay_FlatRates_And_Percentage() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_PerLengthOfStay();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Percentage_FlatRates_And_Percentage() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_Percentage();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_FlatRates_And_PercentageRate_Min_Max_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_FlatRates_Percentage_MinMax();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	@Test()
	public void CCM_VR_Soarian_TestPrice_AdditiveTiered_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_AdditiveTiered();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_IRF_CMG_Pricer_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_IRF_CMG();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_RUG_User_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_RugUser();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_DRG_User_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_DRGUser();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_ExclusionTerm_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_ExclusionTerm();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_APC_Pricing_With_APC_Pricer() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_APC_Pricing_APC_Pricer();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Medicaid_APR_Grouping_And_Pricing_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_Medicaid_APR_Grouping();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Medicare_APC_Grouping_AND_Pricing_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_Medicare_APC_Grouping();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Medicare_DRG_Pricer_RateType_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_Medicare_DRG_Pricer();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Tricare_APC_Grouping_And_Pricing_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_Tricare_APC_Grouping();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}

	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Tricare_IP_Grouping_And_Pricing_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_Tricare_IP_Grouping();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Medicare_IP_Grouping_And_Pricing_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_Medicare_IP_Psychiatric();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_LongTerm_AcuteCare_And_Pricing_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_LongTerm_AcuteCarePricing();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_EAPG_Grouping_And_Pricing_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_EAPG_Grouping();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Dialysis_PPS_Rate_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_DialysisPPS_Rate();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_PerHour_RateType_FlatRates_And_Percentage_Rate() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_PerHour_FlatRates_Percentage();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_PerMinute_RateType_FlatRates_And_Percentage_Rate() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_PerMinute_FlatRates_Percentage();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_ByRevenueCode_RateType_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_RevenueCode();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_ByRevenueCode_PerDay_PerCase_RateType_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_RevenueCode_PerDay_PerCase();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Negative_Testing_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_Negative_Testing();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_DRG_Reassignment_Kansas_Medicaid_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_DRG_Reassignment_Kansas();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_StopLoss_With_Additive_Type_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_StopLoss_AdditiveType();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_StopLoss_With_Average_Day_Type_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_StopLoss_AverageDay();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_StopLoss_With_Replacement_Type_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.TestPrice_RateSheet_RateType_StopLoss_Replacement();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught" + e);
		}
	}
	
	@Test()
	public void CCM_VR_Soarian_TestPrice_Professional_Claim_Scenarios() 
	{
		oParameters.SetParameters("TESTNAME", Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		
		try 
		{
			oBrowser = new Browser(oParameters);
			oTestPriceScenarios = new TestPriceScenariosLibrary(Browser.driver, Browser.oReport, oParameters);

			oTestPriceScenarios.Testprice_RateSheet_RateType_Professional_Claim();
		}
		catch (Exception e) 
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


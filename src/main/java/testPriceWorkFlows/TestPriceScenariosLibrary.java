package testPriceWorkFlows;

import java.text.NumberFormat;
import java.util.List;
import org.openqa.selenium.*;
import contractManagement.RateSheetLibrary;
import libraries.*;
import prerequisites.PrerequisitesLibrary;

/**
 * @author C16137
 *
 */

public class TestPriceScenariosLibrary extends CCMLibrary
{
	RateSheetLibrary oRateSheetLibrary = new RateSheetLibrary(driver, oReport, oParameters);

	PrerequisitesLibrary oPrerequisitesLibrary =  new PrerequisitesLibrary(driver, oReport, oParameters);

	ExcelData oExcelData = new ExcelData(oParameters);


	public TestPriceScenariosLibrary(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}


	By EorRevCode = By.xpath("//tr[@class='odd']//ul[@class='paramList font12 lineInfo']//div[1][@class='APCParam']//div[@class='APCValue']");

	By totalCharges = By.xpath("//div[@id='testpriceView']//label[text()='Total Charges']/following-sibling::span[1]");

	public By testPriceButton = By.xpath("//input[@value='Test Price']");

	By ExplanationOfReimbursement = By.xpath("//div[@id='claimHeader']//div[text()='Explanation of Reimbursement']");

	By CoveredCharges = By.xpath("//div[@id='claimSummary']//td[@class='currency ng-isolate-scope']//span[@class='ng-binding green']");

	By totalExpectedReimbursement = By.xpath("//table[@id='lineItems']//tr[@class='footer-title']//td//span[@class='green']");

	By expectedReimbursementValue = By.xpath("//table[@id='lineItems']/tbody/tr//td[@class='topRightCell font15']/span");

	By insurancePayment = By.xpath("//*[@id='claimSummary']//td[@model='ClaimTotals.ExpectedReimbursement']/span[@ng-class='calcNumberClass(model) + isRedClass(isRed)']");

	By ContractualAdjustment = By.xpath("//div[@id='claimSummary']//td[@model='ClaimTotals.ContractualAdjustment']//span[contains(@class,'ng-binding')]");

	By TotalTerms = By.xpath("//div[@id='pricingDetails']//tr//td[1]//span[@class='detailValue']");

	By AddingCharges = By.xpath("//div[@id='testpriceView']//input[@name='charges']");
	
	By units = By.xpath("//div[@id='testpriceView']//input[@name='units']");
	

	//After entering all the values( i.e Rev code,Units,Charges) this method is used for (Test Pricing) calculation. 
	@SuppressWarnings({ "unused", "unlikely-arg-type" })
	public void TestPriceValidation(String coveredCharges,String contractualAdjustment,String InsurancePayment)
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
						
		oReport.AddStepResult("UB-04 Claim / CMS-1500 Details","Entered Rate Sheet Code and All line items Detils,Daignosis Details successfully ","PASS");
		waitFor(totalCharges,"Total Charges");
		String TotalChargesValues = get_text(totalCharges).replaceAll("[,n/a $,()]","");

		double TotalchargeValues = Double.parseDouble(TotalChargesValues);

		NumberFormat numberformat = NumberFormat.getInstance();
		
		numberformat.setMaximumFractionDigits(0);
		String charges = numberformat.format(TotalchargeValues);
		
		if(oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_ProcedureGroup") && coveredCharges.equals("3400.00") || coveredCharges.equals("6900.00"))
		{
			int noOfUnits = get_table_row_count(units);
			double ChargesValues = 0.00;
			
			for(int i=0;i<=noOfUnits;i++)
			{
				By Units = By.xpath("//input[@id='units"+i+"']");
					
				if(get_field_value("Units", Units).isEmpty())
					System.out.println("Units Value is empty:" + i );
				else
				{
					By ChargeValue = By.xpath("//input[@id='charges"+i+"']");
					Float valueOf = Float.valueOf(get_field_value("Reimbursement Value",ChargeValue).replaceAll("[,n/a $,()]",""));
					ChargesValues = valueOf+ChargesValues; 
				}
			}
			numberformat.setMaximumFractionDigits(0);
			String myString = numberformat.format(ChargesValues).replace(",", "");
			
			if(coveredCharges.replaceAll("^0*|\\.0*$", "").equals(myString.replaceAll("\\.0*$,","")))
				oReport.AddStepResult("Total Charges", "Entered all the "+myString+" charges value and verified that  it is equal to total "+coveredCharges+" charge value ", "PASS");
			else
				oReport.AddStepResult("Total Charges", "Entered all the "+myString+" charges value and verified that  it is not equal to total "+coveredCharges+" charge value ", "FAIL");			
		}
		else
		{
			int noOfCharges = get_table_row_count(AddingCharges);
			double  ChargesValues = 0.00 ;

			for(int i=0;i<=noOfCharges-1;i++)
			{
				By ChargeValue = By.xpath("//input[@id='charges"+i+"']");

				if(isTextBoxBlank("Charges Value", ChargeValue))
					System.out.println("Charges value is empty:" + i);
				else
				{	
					Float valueOf = Float.valueOf(get_field_value("Reimbursement Value",ChargeValue).replaceAll("[,n/a $,()]",""));
					ChargesValues = valueOf+ChargesValues;      
				}  
			}

			scroll("Total Charge", totalCharges);
			oReport.AddStepResult("Total Charge", "Total Charge Value", "SCREENSHOT");
		
			try
			{
				numberformat.setMaximumFractionDigits(0);
				String myString = numberformat.format(ChargesValues);

				if(charges.replaceAll("^0*|\\.0*$", "").equals(myString.replaceAll("\\.0*$","")))
					oReport.AddStepResult("Total Charges", "Entered all the "+myString+" charges value and verified that  it is equal to total "+charges+" charge value ", "PASS");
				else
					oReport.AddStepResult("Total Charges", "Entered all the "+myString+" charges value and verified that  it is not equal to total "+charges+" charge value ", "FAIL");			
			}	
			catch(Exception e)
			{
				System.out.println("Exception:"+e.getMessage());
			}
		}	
		
		fixed_wait_time(1);
		click_button("Test Price Button", testPriceButton);
		fixed_wait_time(3);
		driver.switchTo().frame("testPriceEorView");
		waitFor(ExplanationOfReimbursement,"Explanation OF Reimbursement");
 
		if(IsDisplayed("Explanation Of Reimbursement", ExplanationOfReimbursement))
		{
			oReport.AddStepResult("Explanation Of Reimbursement", "Clicked on Test price and verified that EOR is displayed", "PASS");
			
			oParameters.SetParameters("EOR_CoveredCharges", get_field_value("Covered Charges", CoveredCharges).replaceAll("[,n/a $,()]",""));
			fixed_wait_time(2);
			System.out.println(coveredCharges);
			System.out.println(oParameters.GetParameters("EOR_CoveredCharges"));
			
			if(coveredCharges.equals(oParameters.GetParameters("EOR_CoveredCharges")))
				oReport.AddStepResult("Total Charges", "Entered Rev code,Units,Charges and verified that Total Charges is "+TotalChargesValues+" and clicked on 'Test Price' and it is equal to Covered charges is " +oParameters.GetParameters("EOR_CoveredCharges")+ " is displayed ", "PASS");
			else
				oReport.AddStepResult("Total Charges", "Entered Rev code,Units,Charges and verified that Total Charges is "+TotalChargesValues+" and clicked on 'Test Price' and it is not equal to Covered charges is " +oParameters.GetParameters("EOR_CoveredCharges")+ " is not displayed ", "FAIL");
		}
		else
			oReport.AddStepResult("Explanation Of Reimbursement", "Clicked on Test price and verified that EOR is not displayed", "FAIL");


		Float TotalExpectedReimbursement = Float.valueOf(get_field_value("Total Expected Reimbursement", totalExpectedReimbursement).replaceAll("[, $,()]",""));

		int noOfReimbursements = get_table_row_count(expectedReimbursementValue);
		float  reimbursementValues = 0 ;          

		for(int i=1;i<=noOfReimbursements;i++)
		{
			By reimbursementValue = By.xpath("//table[@id='lineItems']/tbody/tr["+i+"]//td[@class='topRightCell font15']/span");

			Float valueOf = Float.valueOf(get_field_value("Reimbursement Value", reimbursementValue).replaceAll("[,n/a $,()]",""));

			reimbursementValues = valueOf+reimbursementValues;      
		}

		scroll("Total Expected Riembursement Value", totalExpectedReimbursement);
		oReport.AddStepResult("Total Riembursement", "Total Riembursement Value ", "SCREENSHOT");
		
		List<WebElement> totalTermsCount=convertToWebElements(TotalTerms);												
		
		for(int i=1;i<=totalTermsCount.size();i++)
		{
			By PricingTerm = By.xpath("//div[@id='pricingDetails']//tr["+i+"]//td[1]//span[@class='detailValue']");
			
			String pricing  = get_field_value("Pricingterm", PricingTerm);

			if(pricing.isEmpty()==false)
			{
				String[] pricingarray=pricing.split("\\s");
				String PricingTermName=pricingarray[1];
				oParameters.SetParameters("PricingTermName", PricingTermName);
				break;
			}
		}
		
		try
		{
			numberformat.setMaximumFractionDigits(0);
			String reimbursement = numberformat.format(reimbursementValues);
			String TotalReimbutsement = numberformat.format(TotalExpectedReimbursement);
			
			if(!(oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_StopLoss_With_Average_Day_Type_Scenarios") || 
					oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_APC_Pricing_With_APC_Pricer") ||
					   oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_Tricare_APC_Grouping_And_Pricing_Scenarios") ||
					   	oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_Medicare_APC_Grouping_AND_Pricing_Scenarios") ||
					   		oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_EAPG_Grouping_And_Pricing_Scenarios")))
			{
				if(reimbursement.replaceAll("[,n/a $,()]","").equals(TotalReimbutsement.replaceAll("[,n/a $,()]","")))
					oReport.AddStepResult("Line Items and Expected Reimbursment", "In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, after adding all the Line Items the expected reimbursement value is "+reimbursementValues +" and it is equal to actual reimbursement value "+TotalExpectedReimbursement+"'", "PASS");
				else
					oReport.AddStepResult("Line Items and Expected Reimbursment", "In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, after adding all the Line Items the expected reimbursement value is "+reimbursementValues +" and it is not equal to actual reimbursement value "+TotalExpectedReimbursement+"'", "FAIL");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e.getMessage());
		}

		scroll("Covered Charges", CoveredCharges);
		
		if(TotalExpectedReimbursement.equals(Float.valueOf(get_text(insurancePayment).replaceAll("[,n/a $,()]",""))))
			oReport.AddStepResult("Expected Reimbursement and insurance payment", "In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, Clicked on Test price and verified that Expected reimbursement value "+TotalExpectedReimbursement+" is equal to actual insurance payment "+get_text(insurancePayment).replaceAll("[,n/a $,()]","")+" ", "PASS");
		else
			oReport.AddStepResult("Expected Reimbursement and insurance payment", "In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, Clicked on Test price and verified that Expected reimbursement value "+TotalExpectedReimbursement+" is not equal to actual insurance payment "+get_text(insurancePayment).replaceAll("[,n/a $,()]","")+" ", "FAIL");

		if(get_field_value("Covered Charges", CoveredCharges).replaceAll("[,n/a $,()]", "").equals(coveredCharges))
			oReport.AddStepResult("Covered Charges Validation", "In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, Clicked on Test price and verified that Covered Charges value is "+coveredCharges+" displayed as Expected", "PASS");
		else
			oReport.AddStepResult("Covered Charges Validation", "In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, Clicked on Test price and verified that Covered Charges value is "+coveredCharges+" not displayed as Expected", "FAIL");

		if(get_field_value("Contractual Adjustment", ContractualAdjustment).replaceAll("[,n/a $,()]", "").equals(contractualAdjustment))
			oReport.AddStepResult("Contractual Adjustment Validation","In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, Clicked on Test Price and verified that contractual Adjustment value is "+contractualAdjustment+" displayed as Expected" , "PASS");
		else
			oReport.AddStepResult("Contractual Adjustment Validation","In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, Clicked on Test Price and verified that contractual Adjustment value is "+contractualAdjustment+" not displayed as Expected" , "FAIL");

		if(get_field_value("Total Expected Reimbursement", totalExpectedReimbursement).replaceAll("[, $,()]","").equals(InsurancePayment))
			oReport.AddStepResult("Insurance Payment Validation", "In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, Clicked on Test Price and verified that Insurance Payment value is "+InsurancePayment+" displayed as Expected", "PASS");
		else
			oReport.AddStepResult("Insurance Payment Validation", "In "+oParameters.GetParameters("TESTNAME")+" VR, "+oParameters.GetParameters("PricingTermName")+" Term, Clicked on Test Price and verified that Insurance Payment value is "+InsurancePayment+" not displayed as Expected", "FAIL");
	}


	By 	UB_04Claim = By.xpath("//form[@id='testPriceUB04']//ul[@class='cm-nav-tabs']//a[text()='UB-04 Claim']");

	public By CMS_1500Claim = By.xpath("//ul[@class='cm-nav-tabs']//a[text()='CMS-1500 Claim']");
	
	By clearAllFeilds = By.xpath("//form[@id='testPriceUB04']//input[@value='Clear All Fields']");

	By RateSheetCode = By.xpath("//form[@id='testPriceUB04']//input[@id='payerContracts']");

	//This method is used to clear all the feilds.
	public void navigateBackToUB04Tab_CMS1500()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		driver.switchTo().defaultContent();
		
		if(oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_CMS_1500_Claim_Form") || oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_Professional_Claim_Scenarios"))
			click_button("CMS-1500 Claim", CMS_1500Claim);
		else
			click_button("UB-04 Claim", UB_04Claim);
			
		
		if(oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_UB04_Claim_Form"))
			oReport.AddStepResult("UB_04Claim", "Clicked on UB_04Claim and verified that it has navigated to UB_04Claim Sheet", "PASS");
		else if(oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_CMS_1500_Claim_Form") || oParameters.GetParameters("TESTNAME").equalsIgnoreCase("CCM_VR_Soarian_TestPrice_Professional_Claim_Scenarios"))
			oReport.AddStepResult("CMS-1500 Claim", "Clicked on CMS-1500Claim and verified that it has navigated to CMS-1500Claim Sheet", "PASS");
		else
		{
			waitFor(clearAllFeilds,"Clear All Feilds Button");
			
			click_button("Clear All Feilds", clearAllFeilds);

			if(isTextBoxBlank("Rate Sheet Name ", RateSheetCode))
				oReport.AddStepResult("Clear All Feilds", "Clicked on clear button and verified that all the feilds are cleared completely", "PASS");
			else
				oReport.AddStepResult("Clear All Feilds", "Clicked on clear button and verified that all the feilds are not cleared completely", "FAIL");
		}
	}


	public void TestPrice_RateSheet_Section_Creation()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}

		//To Create RateSheet.
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "RateSheetCreation_TestPrice", "TestPrice_EndToEnd_Flow_NonPPS");

		oRateSheetLibrary.addRateSheetIcon();
		oRateSheetLibrary.AddRateSheetDetails("");
		oRateSheetLibrary.rateSheetSaveButton("");


		//To Create Section.

		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "SectionCreation_TestPrice", "");
		int rowCount = oExcelData.getRowCount1("SectionCreation_TestPrice", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");
		
		for(int i=1;i<rowCount;i++)
		{
			oRateSheetLibrary.AddSectionDetails(String.valueOf(i),"");
		}
	}


	public void TestPrice_EndToEnd_Scenario()
	{
		if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
		{			
			oReport.AddStepResult("Skipped Method :", "Skipped Method : " + Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase(), "INFO");
			return ;
		}
		
		String[] rateType = oParameters.GetParameters("SheetName1").split(",");

		int rateTypeLength= rateType.length;

		for(int i=1;i<=rateTypeLength;i++)
		{
			oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx",rateType[i-1] ,"");

			if(rateType[i-1].equals("Term_IRF_CMG_Pricer") || rateType[i-1].equals("Term_RUGUser") || rateType[i-1].equals("Term_DRGUser"))
			{
				oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "RateSheetCreation_TestPrice", "TestPrice_EndToEnd_Flow_PPS");

				oRateSheetLibrary.addRateSheetIcon();
				oRateSheetLibrary.AddRateSheetDetails("");
				oRateSheetLibrary.rateSheetSaveButton("");

				oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "SectionCreation_TestPrice", "");
				int rowCount = oExcelData.getRowCount1("SectionCreation_TestPrice", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

				for(int k=1;k<rowCount;k++)
				{
					oRateSheetLibrary.AddSectionDetails(String.valueOf(k),"");
				}
			}

			for(int j=1;j<=i;)
			{	
				oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx",rateType[i-1],"");
				int RowCount = oExcelData.getRowCount1(rateType[i-1], "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

				if(rateType[i-1].equals("Term_Exclusion_AdditiveTiered"))
				{	
					for(int l=1;l<RowCount;l++)
					{	
						oRateSheetLibrary.addTermButton(String.valueOf(l),oParameters.GetParameters("RateSheetName"+l),oParameters.GetParameters("SectionName"+l));
						oRateSheetLibrary.selectRateType(String.valueOf(l));
						oRateSheetLibrary.termSaveButton(String.valueOf(l));
					}
					break;
				}
				else
				{
					oRateSheetLibrary.addTermButton(String.valueOf(j),oParameters.GetParameters("RateSheetName"+j),oParameters.GetParameters("SectionName"+j));
					oRateSheetLibrary.selectRateType(String.valueOf(j));
					oRateSheetLibrary.MaxMinRateType(String.valueOf(i),rateType[i-1],"TespriceVRTestData.xlsx");
					oRateSheetLibrary.termSaveButton(String.valueOf(j));

					break;
				}	
			}

			oPrerequisitesLibrary.navigateToTestPrice();

			oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceEndToEndScenario", "");
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			
			
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();


			oRateSheetLibrary.navigateToRateSheets();
		}

		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "RateSheetCreation_TestPrice", "");
		int rowCount = oExcelData.getRowCount1("RateSheetCreation_TestPrice", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int v=1;v<rowCount;v++)
		{
			oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"+v),oParameters.GetParameters("RateSheetName"+v));
		}
	}

	
	/*//This method is used for delete Ratesheet
	public void DeleteRateSheet_TestPriceEndToEnd_VR()
	{
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "RateSheetCreation_TestPrice", "");
		int rowCount = oExcelData.getRowCount1("RateSheetCreation_TestPrice", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		oRateSheetLibrary.navigateToRateSheets();
		
		for(int v=1;v<rowCount;v++)
		{
			oRateSheetLibrary.deleteRatesheet(oParameters.GetParameters("RateSheetType"+v),oParameters.GetParameters("RateSheetName"+v));
		}
	}
	*/
	
	//CCM_WF_Soarian_TestPrice_EndToEnd_Scenarios
	public void TestPriceEndToEndScenario()
	{
		login("EDIT");
		changePricingEngine();
		oRateSheetLibrary.navigateToRateSheets();
		TestPrice_RateSheet_Section_Creation();
		TestPrice_EndToEnd_Scenario();
		logout();
	}


	//CCM_TestPrice_WF_Soarian_RateSheet_RateType_TieredRate
	public void TestPrice_RateSheet_RateType_TieredRate()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();

		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypeTieredRate", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypeTieredRate", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int r=1;r<rowCount;r++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(r));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+r+""),oParameters.GetParameters("ContractualAdjustments"+r+""),oParameters.GetParameters("InsurancePayment"+r+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}

	//CCM_TestPrice_WF_Soarian_RateSheet_RateType_FeeSchedule
	public void TestPrice_RateSheet_RateType_FeeSchedule()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();

		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypeFeeSchedule", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypeFeeSchedule", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_Dosage()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypeDosageQuantity", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypeDosageQuantity", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_ProcedureGroup()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypeProcedureGroup", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypeProcedureGroup", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_PerCase()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypePerCase", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypePerCase", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_PerService()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypePerService", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypePerService", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_PerDiem()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypePerDiem", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypePerDiem", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_PerLengthOfStay()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypePerLengthStay", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypePerLengthStay", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_Percentage()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypePercentage", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypePercentage", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_FlatRates_Percentage_MinMax()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "FlatRatesAndPecentage_MinMax", "");
		int rowCount = oExcelData.getRowCount1("FlatRatesAndPecentage_MinMax", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	public void TestPrice_RateSheet_RateType_AdditiveTiered()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypeAdditiveTiered", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypeAdditiveTiered", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_IRF_CMG()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateTypeIRF_CMG", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateTypeIRF_CMG", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_RugUser()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateType_RUG", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateType_RUG", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_DRGUser()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPriceRateType_DRG", "");
		int rowCount = oExcelData.getRowCount1("TestPriceRateType_DRG", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_ExclusionTerm()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_ExclusionTerm", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_ExclusionTerm", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_APC_Pricing_APC_Pricer()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_APC_Pricing_APC_Price", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_APC_Pricing_APC_Price", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_Medicaid_APR_Grouping()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_Medicaid_APR_Grouping", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_Medicaid_APR_Grouping", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_Medicare_APC_Grouping()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_Medicare_APC_Grouping", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_Medicare_APC_Grouping", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_Medicare_DRG_Pricer()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_Medicare_DRG_Pricer", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_Medicare_DRG_Pricer", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_Tricare_APC_Grouping()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_Tricare_APC_Grouping", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_Tricare_APC_Grouping", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_Tricare_IP_Grouping()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_Tricare_IP_Grouping", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_Tricare_IP_Grouping", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_Medicare_IP_Psychiatric()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_Medicare_IP_Grouping", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_Medicare_IP_Grouping", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_LongTerm_AcuteCarePricing()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_LongTerm_Acute", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_LongTerm_Acute", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_EAPG_Grouping()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_EAPG_Grouping", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_EAPG_Grouping", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_DialysisPPS_Rate()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_Dialysis_PPS", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_Dialysis_PPS", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_PerHour_FlatRates_Percentage()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_PerHour_FlatRates_Per", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_PerHour_FlatRates_Per", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_PerMinute_FlatRates_Percentage()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_PerMinute_Flat_Percen", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_PerMinute_Flat_Percen", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_RevenueCode()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_ByRevenueCode", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_ByRevenueCode", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_RevenueCode_PerDay_PerCase()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_Revenue_PerDay_PerCas", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_Revenue_PerDay_PerCas", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_Negative_Testing()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_NegativeTesting", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_NegativeTesting", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_DRG_Reassignment_Kansas()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_DRGReassignment", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_DRGReassignment", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_StopLoss_AdditiveType()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_StopLoss_Additive", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_StopLoss_Additive", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	
	public void TestPrice_RateSheet_RateType_StopLoss_AverageDay()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_StopLoss_Average", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_StopLoss_Average", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void TestPrice_RateSheet_RateType_StopLoss_Replacement()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_StopLoss_Replacement", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_StopLoss_Replacement", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");

		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_UB04_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i+""),oParameters.GetParameters("ContractualAdjustments"+i+""),oParameters.GetParameters("InsurancePayment"+i+""));
			navigateBackToUB04Tab_CMS1500();
		}
		
		logout();
	}
	
	public void Testprice_RateSheet_RateType_Professional_Claim()
	{
		login("EDIT");
		changePricingEngine();
		oPrerequisitesLibrary.navigateToTestPrice();
		oPrerequisitesLibrary.navigateCMS1500Claim();
		
		oExcelData.readExcelRow("C:\\CCM\\SupportingFiles\\", "TespriceVRTestData.xlsx", "TestPrice_ProfessionalClaim", "");
		int rowCount = oExcelData.getRowCount1("TestPrice_ProfessionalClaim", "C:\\CCM\\SupportingFiles\\TespriceVRTestData.xlsx");
		
		for(int i=1;i<rowCount;i++)
		{
			oPrerequisitesLibrary.templetBuild_CMS1500_Claim(String.valueOf(i));
			TestPriceValidation(oParameters.GetParameters("CoveredCharges"+i),oParameters.GetParameters("ContractualAdjustments"+i),oParameters.GetParameters("InsurancePayment"+i));
			
			navigateBackToUB04Tab_CMS1500();
			//oPrerequisitesLibrary.ChangeChargeValue_Validation();
		}
		
		logout();
	}
}

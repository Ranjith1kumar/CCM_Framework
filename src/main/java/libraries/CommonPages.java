package libraries;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.*;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import com.opencsv.CSVReader;

public class CommonPages
{
	protected WebDriver driver;
	protected long StartTime;
	protected Report oReport;
	protected Parameters oParameters;
	static Toolkit toolkit;
	static Timer timer;

	public CommonPages(WebDriver driver, Report oReport, Parameters oParameters) 
	{
		this.driver = driver;
		this.oReport = oReport;
		this.oParameters = oParameters;
	}

	public static TimerTask run() 
	{
		System.out.println("Time's up!");
		toolkit.beep();
		System.out.println("Delaye more than 5 second");
		return null;
	}

	
	public boolean enter_text_value_without_clear(String FieldName, By elemDesc, String FVtoEnter) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemDesc, FieldName);
		
		try 
		{
			WebElement oWebElement = driver.findElement(elemDesc);
			oWebElement.click();
			oWebElement.sendKeys(FVtoEnter);
			if (FieldName.equalsIgnoreCase("Password"))
				oReport.AddStepResult("enter_text_value", "Enter Password", "DONE");
			else
				oReport.AddStepResult("enter_text_value", "FieldName : " + FieldName + "     Value : " + FVtoEnter,	"DONE");
			return true;
		}
		catch (Exception eObj) 
		{
			System.out.println("Unhandled Error : " + eObj.getMessage());
			oReport.AddStepResult("enter_text_value", "FieldName : " + FieldName + "     Value : " + FVtoEnter +" Exception Message : " + eObj.getMessage(),	"UNKNOWN");
			return false;
		}
	}

	public boolean enter_text_value(String FieldName, By elemDesc, String FVtoEnter) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemDesc, FieldName);
		
		try 
		{
			WebElement oWebElement = driver.findElement(elemDesc);
			oWebElement.click();
			oWebElement.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			//oWebElement.clear();
			fixed_wait_time(1);
			oWebElement.sendKeys(FVtoEnter);
			if (FieldName.equalsIgnoreCase("Password"))
				oReport.AddStepResult("enter_text_value", "Enter Password", "DONE");
			else
				oReport.AddStepResult("enter_text_value", "FieldName : " + FieldName + "     Value : " + FVtoEnter,	"DONE");
			return true;
		}
		catch (Exception eObj) 
		{
			System.out.println("Unhandled Error : " + eObj.getMessage());
			oReport.AddStepResult("enter_text_value", "FieldName : " + FieldName + "     Value : " + FVtoEnter +" Exception Message : " + eObj.getMessage(),"UNKNOWN");
			return false;
		}
	}

	public boolean enter_text_value_jscript(String FieldName, By elemDesc, String FVtoEnter) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemDesc, FieldName);
		
		try 
		{
			JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
			String newFVtoEnter = FVtoEnter + " ";

			WebElement oWebElement = driver.findElement(elemDesc);
			oWebElement.click();
			oWebElement.clear();
			fixed_wait_time(1);
			myExecutor.executeScript("arguments[0].value='" + newFVtoEnter + "'", oWebElement);
			performKeyBoardAction("BACKSPACE");
			if (FieldName.equalsIgnoreCase("Password"))
				oReport.AddStepResult("enter_text_value", "Enter Password", "DONE");
			else
				oReport.AddStepResult("enter_text_value", "FieldName : " + FieldName + "     Value : " + FVtoEnter,	"DONE");
			return true;
		} 
		catch (Exception eObj) 
		{
			System.out.println("Unhandled Error : " + eObj.getMessage());
			oReport.AddStepResult("enter_text_value", "FieldName : " + FieldName + "     Value : " + FVtoEnter +" Exception Message : " + eObj.getMessage(),	"UNKNOWN");
			return false;
		}
	}

	public boolean enter_text_value(String FieldName, By elemDesc, String FVtoEnter, int objIndex) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemDesc, FieldName);
		
		try 
		{
			List<WebElement> oWebElement = driver.findElements(elemDesc);
			if (oWebElement.size() >= objIndex) 
			{
				oWebElement.get(objIndex - 1).click();
				oWebElement.get(objIndex - 1).clear();
				oWebElement.get(objIndex - 1).sendKeys(FVtoEnter);

				if (FieldName.equalsIgnoreCase("Password"))
					oReport.AddStepResult("enter_text_value", "Enter Password", "DONE");
				else
					oReport.AddStepResult("enter_text_value","FieldName : " + FieldName + ":Index : " + objIndex + "     Value : " + FVtoEnter, "DONE");
				return true;
			}
			else 
			{
				oReport.AddStepResult("enter_text_value","Unable to find FieldName : " + FieldName + ":Index : " + objIndex, "FAIL");
				return false;
			}

		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("enter_text_value", "FieldName : " + FieldName + "     Value : " + FVtoEnter +" Exception Message : " + eObj.getMessage(),	"UNKNOWN");
			return false;
		}
	}

	public boolean click_button(String FieldName, By elemDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemDesc, FieldName);
		
		WebElement oWebElement = null;

		try 
		{
			if (driver.findElements(elemDesc).size() > 0) 
			{
				oWebElement = driver.findElement(elemDesc);
				if (click_button(FieldName, oWebElement))
					return true;
				else
					return false;
			}
			else 
			{
				oReport.AddStepResult("click_button1", FieldName + " : Not Found", "FAIL");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			if (eObj.getMessage().contains("Other element would receive the click")) 
			{
				Actions actions = new Actions(driver);
				actions.moveToElement(oWebElement).click().perform();
				return true;
			}
			else 
			{
				oReport.AddStepResult("click_button1", "Click on : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
				System.out.println("click_button1 - Exception : " + eObj.getMessage());
				return false;
			}
		}
	}

	public boolean clear_filter(String FieldName, By elemDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemDesc, FieldName);
		
		WebElement oWebElement = null;

		try 
		{
			if (driver.findElements(elemDesc).size() > 0) 
			{
				oWebElement = driver.findElement(elemDesc);
				if (click_button(FieldName, oWebElement))
					return true;
				else
					return false;
			}
			else 
			{
				oReport.AddStepResult("click_button", FieldName + " : Not Found", "FAIL");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			if (eObj.getMessage().contains("Other element would receive the click")) 
			{
				Actions actions = new Actions(driver);
				actions.moveToElement(oWebElement).click().perform();
				return true;
			}
			else 
			{
				oReport.AddStepResult("click_button", "Click on : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
				System.out.println("click_button - Exception : " + eObj.getMessage());
				return false;
			}
		}
	}
	
	public boolean click_button(String FieldName, By elemDesc, int objIndex) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemDesc, FieldName);
		
		WebElement oWebElement = null;

		try 
		{
			if (driver.findElements(elemDesc).size() > 0) 
			{
				List<WebElement> oWebElementList = driver.findElements(elemDesc);
				if (oWebElementList.size() >= objIndex) 
				{
					oWebElement = oWebElementList.get(objIndex - 1);
					if (click_button(FieldName, oWebElement))
						return true;
					else
						return false;
				}
				else 
				{
					oReport.AddStepResult("click_button2","Unable to find button : " + FieldName + ":Index : " + objIndex, "FAIL");
					return false;
				}
			} 
			else 
			{
				oReport.AddStepResult("click_button2", FieldName + " : Not Found", "DONE");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("click_button2", "Click on : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("click_button2 - Exception : " + eObj.getMessage());
			return false;
		}
	}

	public boolean click_button(String FieldName, WebElement oWebElement) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		try 
		{
			if (oWebElement.isDisplayed()) 
			{
				oWebElement.click();
				oReport.AddStepResult("click_button", "Clicked on : " + FieldName, "DONE");
				return true;
			}
			else 
			{
				oReport.AddStepResult("click_button", FieldName + " : Not Found", "FAIL");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			if (eObj.getMessage().contains("is not clickable at point")	|| eObj.getMessage().contains("Other element would receive the click")) 
			{
				System.out.println("Error Handling for Exception : " + eObj.getMessage());

				Actions actions = new Actions(driver);
				actions.moveToElement(oWebElement).click().perform();

				oReport.AddStepResult("click_button", "Clicked on : " + FieldName, "DONE");
				return true;
			} 
			else 
			{
				oReport.AddStepResult("click_button", "Clicked on : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
				System.out.println("Exception : " + eObj.getMessage());
				return false;
			}
		}
	}

	public void clickOnFirstMatchingElement(By elementDesc) 
	{
		try
		{
			List<WebElement> oWebElement = driver.findElements(elementDesc);
			oWebElement.get(1).click();
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}
	
	public void hower_click(String FieldName, By elementDesc)
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return;
		try
		{
			waitFor(elementDesc, FieldName);
			
			WebElement button = driver.findElement(elementDesc);
			Actions action = new Actions(driver);
			action.moveToElement(button).doubleClick().build().perform();
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}
	
	public void dclick_button(String FieldName, By elementDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return;
		try
		{
			waitFor(elementDesc, FieldName);
			
			WebElement button = driver.findElement(elementDesc);
			Actions action = new Actions(driver);
			action.moveToElement(button).doubleClick().build().perform();
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public boolean mouse_hover(String FieldName, By elementDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elementDesc, FieldName);
		
		try 
		{
			if (driver.findElement(elementDesc).isDisplayed()) 
			{
				Actions action = new Actions(driver);
				WebElement oWebElement = driver.findElement(elementDesc);
				action.moveToElement(oWebElement).build().perform();
				
				oReport.AddStepResult("Mouse Hover", "Hovered on : " + FieldName, "DONE");
				return true;
			}
			else 
			{
				oReport.AddStepResult("Mouse Hover", FieldName + " : Not Found", "FAIL");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("Mouse Hover", "Hovered on : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Mouse Hover - Exception : " + eObj.getMessage());
			return false;
		}
	}	
	

	public boolean PerformMouseHoverOperation(String FieldName, By elemDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemDesc, FieldName);
		
		try 
		{
			if (driver.findElement(elemDesc).isDisplayed()) 
			{
				Actions actions = new Actions(driver);
				WebElement element = driver.findElement(elemDesc);
				actions.moveToElement(element);
				actions.click();

				actions.build().perform();

				oReport.AddStepResult("Mouse Hover", "Hovered on : " + FieldName, "DONE");
				return true;
			}
			else 
			{
				oReport.AddStepResult("Mouse Hover", FieldName + " : Not Found", "FAIL");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("Mouse Hover", "Hovered on : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Mouse Hover - Exception : " + eObj.getMessage());
			return false;
		}
	}
	
	public void performKeyBoardAction(String action) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return;
		try
		{
			Actions keyaction = new Actions(driver);
			switch (action) 
			{
			case "TAB":
				keyaction.sendKeys(Keys.TAB).perform();
				break;
			case "ARROWDOWN":
				keyaction.sendKeys(Keys.ARROW_DOWN).perform();
				break;
			case "ARROWUP":
				keyaction.sendKeys(Keys.ARROW_UP).perform();
				break;
			case "ARROWLEFT":
				keyaction.sendKeys(Keys.ARROW_LEFT).perform();
				break;
			case "ARROWRIGHT":
				keyaction.sendKeys(Keys.ARROW_RIGHT).perform();
				break;
			case "ESC":
				keyaction.sendKeys(Keys.ESCAPE).perform();
				break;
			case "BACKSPACE":
				keyaction.sendKeys(Keys.BACK_SPACE).perform();
				break;
			default:
				keyaction.sendKeys(Keys.ENTER).perform();
				// notificationMessages();
			}
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public boolean scroll_to_element(By Slider, By Destination) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(Destination, "");
		
		try 
		{
			if (driver.findElement(Destination).isDisplayed()) 
			{
				WebElement slider = driver.findElement(Slider);
				WebElement destination = driver.findElement(Destination);
				Actions move = new Actions(driver);
				Actions action = move.dragAndDrop(destination, slider);
				action.build().perform();
				action.build().perform();

				oReport.AddStepResult("Scroll to Element", "Scrolled to : " + Destination.toString(), "DONE");
				return true;
			}
			else 
			{
				oReport.AddStepResult("Scroll to Element", Destination.toString() + " : Not Found", "FAIL");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("Scroll to Element", "Scrolled to : " + "Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Scroll to Elementr - Exception : " + eObj.getMessage());
			return false;
		}	
	}

	public void PerformMouseOperation(String fieldName, By elemDesc, String FVtoenter) 
	{
		waitFor(elemDesc, fieldName);
		try
		{
			Actions actions = new Actions(driver);
			WebElement element = driver.findElement(elemDesc);
			actions.moveToElement(element);
			actions.click();
			actions.sendKeys(FVtoenter);
			actions.build().perform();
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public boolean IsElementSelected(String FieldName, By elemdesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elemdesc, FieldName);
		
		try 
		{
			if (driver.findElement(elemdesc).isSelected()) 
			{
				System.out.println("IsElementSelected : FieldName : " + FieldName + " ## True");
				return true;
			}
			else 
			{
				System.out.println("IsElementSelected : FieldName : " + FieldName + " ## False");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("IsElementSelected", "IsElementSelected : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("IsElementSelected - Exception : " + eObj.getMessage());
			return false;
		}
	}

	public boolean IsElementDisplayed(String FieldName, By elementDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elementDesc, FieldName);
		
		try 
		{
			if (driver.findElement(elementDesc).isDisplayed()) 
			{
				System.out.println("IsElementDisplayed : FieldName : " + FieldName + ":True");
				return true;
			}
			else 
			{
				System.out.println("IsElementDisplayed : FieldName : " + FieldName + ":False");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("IsElementDisplayed", "IsElementDisplayed : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("IsElementDisplayed - Exception : " + eObj.getMessage());
			return false;
		}
	}

	
	public boolean IsDisplayed(String FieldName, By elementDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elementDesc, FieldName);
		
		try 
		{
			if (driver.findElement(elementDesc).isDisplayed()) 
			{
				System.out.println("IsDisplayed : FieldName : " + FieldName + ":True");
				return true;
			}
			else 
			{
				System.out.println("IsDisplayed : FieldName : " + FieldName + ":False");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			return false;
		}
	}
	
	
	public boolean IsElementEnabled(String FieldName, By elementDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elementDesc, FieldName);
		
		try 
		{
			if (driver.findElement(elementDesc).isEnabled()) 
			{
				System.out.println("IsElementEnabled : FieldName : " + FieldName + ":True");
				return true;
			}
			else 
			{
				System.out.println("IsElementEnabled : FieldName : " + FieldName + ":False");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("IsElementEnabled", "IsElementEnabled : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("IsElementEnabled - Exception : " + eObj.getMessage());
			return false;
		}
	}
	
	public boolean IsEnabled(String FieldName, By elementDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
	
		try 
		{
			if (driver.findElement(elementDesc).isEnabled()) 
			{
				System.out.println("IsEnabled : FieldName : " + FieldName + ":True");
				return true;
			}
			else 
			{
				System.out.println("IsEnabled : FieldName : " + FieldName + ":False");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			return false;
		}
	}
	
	public boolean IsFieldDisplayed(String FieldName, By elementDesc) 
	{
		if(oParameters.GetParameters("CONTINUED_EXECUTION").equalsIgnoreCase("NO"))
			return false;
		
		waitFor(elementDesc, FieldName);
		
		try 
		{
			if (driver.findElements(elementDesc).size() > 0) 
			{
				System.out.println("IsFieldDisplayed : FieldName : " + FieldName + ":True");
				return true;
			}
			else 
			{
				System.out.println("IsFieldDisplayed : FieldName : " + FieldName + ":False");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("IsFieldDisplayed", "IsFieldDisplayed : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("IsFieldDisplayed - Exception : " + eObj.getMessage());
			return false;
		}
	}
	
	public boolean isTextBoxBlank(String FieldName, By elemdesc) 
	{
		waitFor(elemdesc, FieldName);
		
		try 
		{
			WebElement owebelement = driver.findElement(elemdesc);

			if (owebelement.getAttribute("value").isEmpty()) 
			{
				System.out.println("isTextBoxBlank : FieldName : " + FieldName + " ## True");
				return true;
			}
			else 
			{
				System.out.println("isTextBoxBlank : FieldName : " + FieldName + " ## False");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("isTextBoxBlank", "isTextBoxBlank : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("isTextBoxBlank - Exception : " + eObj.getMessage());
			return false;
		}
	}

	public boolean isAttribtuePresent(By elemdesc, String FieldName) 
	{
		waitFor(elemdesc, FieldName);
		
		WebElement element = driver.findElement(elemdesc);
		Boolean result = null;
		try 
		{
			String value = element.getAttribute(FieldName);
			if (value != null)
				result = true;
			else
				result = false;
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("isAttribtuePresent", "isAttribtuePresent : " + FieldName +" Exception Message : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception Caught :" + eObj);
		}
		return result;
	}

	public void ReminderBeep(int seconds) 
	{
		try
		{
			toolkit = Toolkit.getDefaultToolkit();
			timer = new Timer();
			timer.schedule(run(), seconds * 1000);
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public String get_random_alphanumeric(int strLength) 
	{
		Random rand = new Random();

		String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		char[] text = new char[strLength];

		for (int i = 0; i < strLength; i++) 
		{
			text[i] = characters.charAt(rand.nextInt(characters.length()));
		}

		return new String(text);
	}

	public String get_random_string(int strLength) 
	{
		Random rand = new Random();

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		char[] text = new char[strLength];

		for (int i = 0; i < strLength; i++) 
		{
			text[i] = characters.charAt(rand.nextInt(characters.length()));
		}

		return new String(text);
	}

	public String get_random_number(int strLength) 
	{
		Random rand = new Random();

		int StartNum = 0, EndNum = 0, tempNum = 0;
		String rVal = "";

		do 
		{
			if (strLength > 5)
				tempNum = 5;
			else
				tempNum = strLength;

			switch (tempNum) 
			{
			case 1:
				StartNum = 0;
				EndNum = 9;
				break;
			case 2:
				StartNum = 10;
				EndNum = 89;
				break;
			case 3:
				StartNum = 100;
				EndNum = 899;
				break;
			case 4:
				StartNum = 1000;
				EndNum = 8999;
				break;
			default:
				StartNum = 10000;
				EndNum = 89999;
			}

			long n = rand.nextInt(EndNum) + StartNum;

			if (rVal.isEmpty())
				rVal = Long.toString(n);
			else
				rVal = rVal + Long.toString(n);

			strLength = strLength - 5;

		} 
		while (strLength > 0);

		return rVal;
	}

	public long get_random_number(int StartNum, int EndNum) 
	{
		Random rand = new Random();

		long n = rand.nextInt(EndNum - StartNum + 1) + StartNum;

		return n;
	}

	public String get_specified_date(int NoOfDays) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.DAY_OF_YEAR, NoOfDays);

		return sdf.format(c.getTime());
	}

	public static String get_specified_date(int noOfDays, String date, String dateFormat) 
	{
		DateTimeFormatter formatters = null;
		LocalDate date1 = null;

		System.out.println("Given Date : " + date);

		String[] s = date.split("/");

		String month = s[0];
		String day = s[1];
		String year = s[2];

		if (dateFormat.equalsIgnoreCase("MM/dd/YYYY")) 
		{
			date1 = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)).plusDays(noOfDays);
			formatters = DateTimeFormatter.ofPattern(dateFormat);

		}
		else if (dateFormat.equalsIgnoreCase("dd/MM/YYYY")) 
		{
			LocalDate date4 = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)).plusDays(noOfDays);
			formatters = DateTimeFormatter.ofPattern(dateFormat);
			return date4.format(formatters);
		}

		return date1.format(formatters);
	}

	public static String get_specified_date(int NoOfDays, String DateFormat) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);

		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.DATE, NoOfDays);

		return sdf.format(c.getTime());
		//test
	}

	public String get_next_date(String givenDate, int noOfDays) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		String nextDaysDate = null;
		try 
		{
			cal.setTime(dateFormat.parse(givenDate));
			cal.add(Calendar.DATE, noOfDays);
			nextDaysDate = dateFormat.format(cal.getTime());

		}
		catch (Exception ex) 
		{
			System.out.println("Exception Caught " + ex);
		}
		finally 
		{
			dateFormat = null;
			cal = null;
		}
		return nextDaysDate;
	}
	
	public String get_time_stamp() 
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		strDate = strDate.replaceAll("[()-.: ]+", "_");
		return strDate;
	}

	public String get_attribute_value(String FieldName, WebElement oWebElement, By elemDesc, String Attribute) 
	{
		String value = "";

		try 
		{
			value = oWebElement.findElement(elemDesc).getAttribute(Attribute);
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("get_attribute_value", "get_attribute_value : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
		}

		System.out.println("get_attribute_value - FieldName : " + FieldName + "  Value: " + value);
		return value;
	}

	public String get_attribute_value(String FieldName, By elemDesc, String Attribute) 
	{
		String value = "";

		try 
		{
			value = driver.findElement(elemDesc).getAttribute(Attribute);

		}
		catch (Exception eObj) 
		{
			System.out.println("Exception : " + eObj.getMessage());
		}

		System.out.println("get_attribute_value - FieldName : " + FieldName + "  Value: " + value);
		return value;
	}
	
	public String get_field_value(String FieldName, By elemDesc) 
	{
		waitFor(elemDesc, FieldName);
		
		String str = "", rVal = "";
		int ec = 0;

		try 
		{
			List<WebElement> oWebElement = driver.findElements(elemDesc);
			for (ec = 0; ec < oWebElement.size(); ec++) 
			{
				str = "";
				if (!oWebElement.get(ec).getText().isEmpty())
					str = oWebElement.get(ec).getText();
				else if (oWebElement.get(ec).getAttribute("value") != null)
					str = oWebElement.get(ec).getAttribute("value");
				else if (oWebElement.get(ec).getAttribute("title") != null) 
				{
					str = oWebElement.get(ec).getAttribute("title");
					if (str.isEmpty())
						if (oWebElement.get(ec).getAttribute("data-title") != null)
							str = oWebElement.get(ec).getAttribute("data-title");
				}

				if (str.isEmpty()) 
				{
					str = oWebElement.get(ec).getAttribute("innerHTML");

					String[] t3 = str.split("<");

					if (t3.length > 1) 
					{
						str = "";
						for (int cd = 0; cd < t3.length; cd++) 
						{
							String[] t2 = t3[cd].split(">");
							for (int df = 1; df < t2.length; df += 2) 
							{
								if (str.isEmpty())
									str = t2[df];
								else
									str = str + "|" + t2[df];
							}
						}
					}
				}
				rVal = rVal + "|" + str.trim();
			}

			if (!rVal.isEmpty())
				rVal = rVal.substring(1, rVal.length());
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("get_field_value", "get_field_value : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("get_field_value - Exception : " + eObj.getMessage());
		}

		if (ec == 0)
			System.out.println(FieldName + " is not found");
		else
			oReport.AddStepResult("get_field_value", "FieldName : " + FieldName + "     Value : " + rVal, "DONE");

		return rVal.trim();
	}

	public String get_text(By elemDessc) 
	{
		waitFor(elemDessc, "");
		try
		{
			String value = null;
			WebElement webElement = driver.findElement(elemDessc);
			value = webElement.getText();
			return value;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return null;
		}
	}

	public String getToolTipValue(By elementDesc) 
	{
		try
		{
			WebElement we = driver.findElement(elementDesc);
			return we.getAttribute("title");
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return null;
		}
	}
	
	public int get_table_row_count(By rowObject) 
	{
		try
		{
			int returnVal = driver.findElements(rowObject).size();
			System.out.println("get_table_row_count - " + returnVal);
			return returnVal;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return 0;
		}
	}

	public String get_table_row_value(int ColumnIndex, By ContentPath, String ColumnName, int RowKey) 
	{
		String Constr = "", ConrVal = "";
		try 
		{
			List<WebElement> ContentList = driver.findElements(ContentPath);

			if (ContentList.size() >= RowKey) 
			{
				if (ContentList.size() > 0) 
				{
					WebElement Cell = ContentList.get(RowKey).findElement(By.xpath("div[" + (ColumnIndex + 1) + "]"));
					if (!Cell.getText().isEmpty())
						Constr = Cell.getText();
					else if (Cell.getAttribute("value") != null)
						Constr = Cell.getAttribute("value");
					else if (Cell.getAttribute("title") != null) 
					{
						Constr = Cell.getAttribute("title");
						if (Constr.isEmpty())
							if (Cell.getAttribute("data-title") != null)
								Constr = Cell.getAttribute("data-title");
					}
					ConrVal = Constr.trim();
				}
			}
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("get_table_row_value", "get_table_row_value : " + ColumnName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("get_table_row_value :: Exception : " + eObj.getMessage());
		}

		return ConrVal;
	}

	public int get_table_row_number(By HeaderPath, By ContentPath, String ColumnName, String RowValue) 
	{
		List<WebElement> HeaderList = driver.findElements(HeaderPath);
		List<WebElement> ContentList = driver.findElements(ContentPath);
		int i, j;
		String str = "", rVal = "", Constr = "", ConrVal = "";
		try
		{
			for (i = 0; i < HeaderList.size(); i++) 
			{
				if (HeaderList.size() > 0) 
				{
					if (!HeaderList.get(i).getText().isEmpty())
						str = HeaderList.get(i).getText();
					else if (HeaderList.get(i).getAttribute("value") != null)
						str = HeaderList.get(i).getAttribute("value");
					else if (HeaderList.get(i).getAttribute("title") != null) 
					{
						str = HeaderList.get(i).getAttribute("title");
						if (str.isEmpty())
							if (HeaderList.get(i).getAttribute("data-title") != null)
								str = HeaderList.get(i).getAttribute("data-title");
					}
					rVal = str.trim();
	
					if (rVal.equalsIgnoreCase(ColumnName))
						break;
				} 
				else
					System.out.println("Header element is not found");
			}
	
			if (i == HeaderList.size()) 
			{
				oReport.AddStepResult("get_table_row_number", "Header Not found ", "FAIL");
				return 0;
			}
			else 
			{
				for (j = 0; j < ContentList.size(); j++) 
				{
					if (ContentList.size() > 0) 
					{
						WebElement ContentElement = ContentList.get(j).findElement(By.xpath("div[" + (i + 1) + "]"));
						if (!ContentElement.getText().isEmpty())
							Constr = ContentElement.getText();
						else if (ContentElement.getAttribute("value") != null)
							Constr = ContentElement.getAttribute("value");
						else if (ContentElement.getAttribute("title") != null) 
						{
							Constr = ContentElement.getAttribute("title");
							if (Constr.isEmpty())
								if (ContentElement.getAttribute("data-title") != null)
									Constr = ContentElement.getAttribute("data-title");
						}
						ConrVal = Constr.trim();
	
						if (ConrVal.equalsIgnoreCase(RowValue))
							break;
					} 
					else
						System.out.println("Content element is not found");
				}
			}
	
			if (j == ContentList.size())
				return 0;
			else
				return j + 1;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return 0;
		}
	}

	public int get_table_row_number(int ColumnIndex, By ContentPath, String ColumnName, String RowValue) 
	{
		List<WebElement> ContentList = driver.findElements(ContentPath);
		int j;
		String Constr = "", ConrVal = "";
		try
		{
			
			for (j = 0; j < ContentList.size(); j++) 
			{
				if (ContentList.size() > 0) 
				{
					WebElement ContentElement = ContentList.get(j).findElement(By.xpath("div[" + ColumnIndex + "]"));
					if (!ContentElement.getText().isEmpty())
						Constr = ContentElement.getText();
					else if (ContentElement.getAttribute("value") != null)
						Constr = ContentElement.getAttribute("value");
					else if (ContentElement.getAttribute("title") != null) 
					{
						Constr = ContentElement.getAttribute("title");
						if (Constr.isEmpty())
							if (ContentElement.getAttribute("data-title") != null)
								Constr = ContentElement.getAttribute("data-title");
					}
					ConrVal = Constr.trim();
	
					if (ConrVal.equalsIgnoreCase(RowValue))
						break;
				} 
				else
					System.out.println("Content element is not found");
			}
	
			if (j == ContentList.size())
				return 0;
			else
				return j + 1;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return 0;
		}
	}

	public int get_list_index(By ListPath, String ListName) 
	{
		List<WebElement> elemList = driver.findElements(ListPath);
		int i = -1;
		try
		{
			for (i = 0; i < elemList.size(); i++) 
			{
				if (elemList.get(i).getText().equalsIgnoreCase(ListName))
					break;
			}
	
			System.out.println("get_list_index :: Index Found: " + i);
	
			return i;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return 0;
		}
	}

	public void click_on_table_row_value(By HeaderPath, By ContentPath, String ColumnName, int RowKey) 
	{
		int i;

		try 
		{
			List<WebElement> HeaderList = driver.findElements(HeaderPath);
			List<WebElement> ContentList = driver.findElements(ContentPath);

			for (i = 0; i <= HeaderList.size(); i++) 
			{
				if (HeaderList.get(i).getText().equalsIgnoreCase(ColumnName))
					break;
			}

			if (ContentList.size() >= RowKey) 
			{
				WebElement Cell = ContentList.get(RowKey).findElement(By.xpath("div[" + (i + 1) + "]/div"));
				Cell.click();
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("Click_on_table_row_value", "Click_on_table_row_value : " + ColumnName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Click_on_table_row_value :: Exception : " + eObj.getMessage());
		}
	}

	public void click_on_table_row(By HeaderPath, By ContentPath, String ColumnName, int RowKey) 
	{
		int i;

		try 
		{
			List<WebElement> HeaderList = driver.findElements(HeaderPath);
			List<WebElement> ContentList = driver.findElements(ContentPath);

			for (i = 0; i <= HeaderList.size(); i++) 
			{
				if (HeaderList.get(i).getText().equalsIgnoreCase(ColumnName))
					break;
			}

			if (ContentList.size() >= RowKey) 
			{
				WebElement Cell = ContentList.get(RowKey).findElement(By.xpath("td[" + (i + 2) + "]"));
				Cell.click();
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("Click_on_table_row_value", "Click_on_table_row_value : " + ColumnName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Click_on_table_row_value :: Exception : " + eObj.getMessage());
		}
	}

	public void set_table_row_value(By HeaderPath, By ContentPath, String ColumnName, String Value) 
	{
		List<WebElement> HeaderList = driver.findElements(HeaderPath);
		List<WebElement> ContentList = driver.findElements(ContentPath);
		int i;
		for (i = 0; i <= HeaderList.size(); i++) 
		{
			if (HeaderList.get(i).getAttribute("title").trim().equalsIgnoreCase(ColumnName))
				break;
		}

		WebElement Cell = ContentList.get(0).findElement(By.xpath("div[" + (i + 2) + "]"));

		int k = 1;
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) 
		{
			try 
			{
				Cell.click();
				k++;
				if (k > 2) 
				{
					result = true;
					break;
				}

			} 
			catch (StaleElementReferenceException e) 
			{
			}
			attempts++;
		}
		if (result) 
		{
			WebElement Cell1 = ContentList.get(0).findElement(By.xpath("div[" + (i + 2) + "]/input[@type='textbox']"));
			Cell1.sendKeys(Value);
		}
		else 
		{
			throw new RuntimeException("Data was not entered in the table for column ->" + ColumnName);
		}
	}

	public void set_table_text_value(By HeaderPath, By ContentPath, String ColumnName, String Value) 
	{
		try
		{
			List<WebElement> HeaderList = driver.findElements(HeaderPath);
			List<WebElement> ContentList = driver.findElements(ContentPath);
			int i;
	
			for (i = 0; i <= HeaderList.size(); i++) 
			{
				if (HeaderList.get(i).getAttribute("data-title").trim().equalsIgnoreCase(ColumnName))
					break;
			}
	
			WebElement Cell = ContentList.get(0).findElement(By.xpath("td[" + (i + 2) + "]"));
			Cell.click();
			Cell.click();
			fixed_wait_time(1);
	
			WebElement Cell1 = ContentList.get(0).findElement(By.xpath("td[" + (i + 2) + "]/input[@type='text']"));
			Cell1.sendKeys(Value);
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public void dClick_table_list_value(By HeaderPath, By ContentPath, By ListPath, String ColumnName, String Value) 
	{
		try
		{
			List<WebElement> HeaderList = driver.findElements(HeaderPath);
			List<WebElement> ContentList = driver.findElements(ContentPath);
	
			int i;
	
			for (i = 0; i <= HeaderList.size(); i++) 
			{
				if (HeaderList.get(i).getAttribute("title").trim().equalsIgnoreCase(ColumnName))
					break;
			}
	
			WebElement Cell = ContentList.get(0).findElement(By.xpath("div[" + (i + 1) + "]"));
			Cell.click();
			Cell.click();
	
			dclick_button("Scenario Type", ListPath);
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public boolean Select_table_list_value(String FieldName, By OptionPath, String oValue) 
	{
		int j;
		try
		{
			
			List<WebElement> SelectList = driver.findElements(OptionPath);
	
			for (j = 0; j <= SelectList.size(); j++) 
			{
				if (SelectList.get(j).getText().equalsIgnoreCase(oValue))
					break;
			}
			return true;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return false;
		}
	}

	
	public boolean isFileDownloaded(String fileName) 
	{
		try
		{
			boolean flag = false;
	
			File dir = new File(oParameters.GetParameters("downloadFilepath"));
			File[] dir_contents = dir.listFiles();
	
			for (int i = 0; i < dir_contents.length; i++) 
			{
				if (dir_contents[i].getName().equals(fileName))
					return flag = true;
			}
			return flag;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return false;
		}
	}
	
	public boolean select_option(String FieldName, By elemDesc, String oValue) 
	{
		waitFor(elemDesc, FieldName);
		
		WebElement oSelectElement = null;
		List<WebElement> oElementList = null;
		Select oSelect = null;
		String aValue = "";

		if (oValue.isEmpty()) 
		{
			oReport.AddStepResult("select_option","Field Name : " + FieldName + " NULL OR BLANK VALUE CANNOT BE SELECTED", "WARNING");
			return false;
		}

		if (driver.findElements(elemDesc).size() == 0) 
		{
			System.out.println(FieldName + " is not found");
			return false;
		}

		try 
		{
			oSelectElement = driver.findElement(elemDesc);
			oSelect = new Select(oSelectElement);
			oSelect.selectByValue(oValue);
			oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + oValue, "DONE");
			return true;
		}
		catch (Exception e) 
		{
			try 
			{
				if (oElementList.size() > 0) 
				{
					try 
					{
						oElementList.get(0).click();
					}
					catch (Exception exp) 
					{
						oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + oValue,"WARNING");
						return false;
					}
					aValue = oElementList.get(0).getText().trim();
					oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + aValue,"DONE");
					return true;
				}
				else 
				{
					oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + oValue,"WARNING");
					return false;
				}
			} 
			catch (Exception eObj) 
			{
				System.out.println("Exception Message : " + eObj.getMessage());
				oReport.AddStepResult("select_option", "select_option : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
				return false;
			}
		}
	}

	public boolean select_option(String FieldName, By elemDesc, String oValue, int objIndex) 
	{
		waitFor(elemDesc, FieldName);
		
		List<WebElement> oSelectElement = null;
		Select oSelect = null;
		String aValue = "";

		if (!oValue.isEmpty()) 
		{
			System.out.println("Field Name : " + FieldName + " - NULL OR BLANK VALUE CANNOT BE SELECTED");
			return false;
		}

		if (driver.findElements(elemDesc).size() == 0) 
		{
			System.out.println(FieldName + " is not found");
			return false;
		}

		try 
		{
			oSelectElement = driver.findElements(elemDesc);

			if (oSelectElement.size() >= objIndex) 
			{
				oSelect = new Select(oSelectElement.get(objIndex - 1));
				oSelect.selectByValue(oValue);
				oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + oValue, "DONE");
				return true;
			}

			else 
			{
				oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + oValue,"WARNING");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			for (int i = 0; i < oSelect.getOptions().size(); i++) 
			{
				System.out.println("Item " + (i + 1) + " - " + oSelect.getOptions().get(i).getText());
				aValue = oSelect.getOptions().get(i).getText();
				if (aValue.contains(oValue)) 
				{
					oSelect.selectByIndex(i);
					oReport.AddStepResult("select_option", "select_option : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
					return true;
				}
			}

			System.out.println("Field Name : " + FieldName + "     Value : " + oValue + " - NOT SELECTED");
			return false;
		}
	}

	public void selectOption(By elemDesc, String type, String selectValue) 
	{
		try
		{
			WebElement oWebElement = driver.findElement(elemDesc);
			Select oSelect = new Select(oWebElement);
	
			switch (type) 
			{
			case "value":
				if(!selectValue.isEmpty())
				oSelect.selectByValue(selectValue);
				break;
			case "visibletext":
				if(!selectValue.isEmpty())
				oSelect.selectByVisibleText(selectValue);
				break;
			}
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public boolean select_option(String FieldName, WebElement oSelectElement, By elemDesc, String oValue) 
	{
		waitFor(elemDesc, FieldName);
		
		try 
		{
			if (!oValue.isEmpty()) 
			{
				Select oSelect = new Select(oSelectElement.findElement(elemDesc));
				oSelect.selectByValue(oValue);
				oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + oValue, "DONE");
				return true;
			}
			else 
			{
				oReport.AddStepResult("select_option", "Field Name : " + FieldName + "     Value : " + oValue,"WARNING");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("select_option", "select_option : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
			return false;
		}
	}

	public boolean select_list_value(String FieldName, By elemDesc, String FVtoSelect) 
	{
		waitFor(elemDesc, FieldName);
		
		try 
		{
			List<WebElement> ItemsList = driver.findElements(elemDesc);

			for (WebElement option : ItemsList) 
			{
				if (option.getText().equals(FVtoSelect)) 
				{
					fixed_wait_time(1);
					option.click();
					oReport.AddStepResult("select_list_value","FieldName : " + FieldName + " #     Value : " + FVtoSelect, "DONE");
					return true;
				}
			}

			oReport.AddStepResult("select_list_value", "FieldName : " + FieldName + "     Value : " + FVtoSelect,"FAIL");
			return false;
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("select_option", "select_option : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
			return false;
		}
	}


	
	public boolean check_text_value(String FieldName, By elemDesc, String FVtoCheck) 
	{
		waitFor(elemDesc, FieldName);
		
		try 
		{
			String actual = driver.findElement(elemDesc).getText();

			if (actual.equals(FVtoCheck)) 
			{
				oReport.AddStepResult("check_text_value", "FieldName : " + FieldName + ":Actual Value : " + actual,	"PASS");
				return true;
			}
			else 
			{
				oReport.AddStepResult("check_text_value","FieldName : " + FieldName + " :Expected Value : " + FVtoCheck + ":Actual Value : " + actual,"FAIL");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("check_text_value", "check_text_value : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
			return false;
		}
	}

	public boolean check_text_value(String FieldName, WebElement oWebElement, By elemDesc, String FVtoCheck) 
	{
		waitFor(elemDesc, FieldName);
		
		try 
		{
			String actual = oWebElement.findElement(elemDesc).getText();

			if (actual.equals(FVtoCheck)) 
			{
				oReport.AddStepResult("check_text_value", "FieldName : " + FieldName + ":Actual Value : " + actual,	"PASS");
				return true;
			}
			else 
			{
				oReport.AddStepResult("check_text_value","FieldName : " + FieldName + " :Expected Value : " + FVtoCheck + ":Actual Value : " + actual,"FAIL");
				return false;
			}

		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("check_text_value", "check_text_value : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
			return false;
		}
	}

	public boolean check_attribute_value(String FieldName, WebElement oWebElement, By elemDesc, String Attribute,String FVtoCheck) 
	{
		waitFor(elemDesc, FieldName);
		
		try 
		{
			String actual = oWebElement.findElement(elemDesc).getAttribute(Attribute);

			if (actual.equals(FVtoCheck)) 
			{
				oReport.AddStepResult("check_attribute_value","FieldName : " + FieldName + "  has expected value     Value : " + actual, "PASS");
				return true;
			}
			else 
			{
				oReport.AddStepResult("check_attribute_value","FieldName : " + FieldName + "  does not have expected value     Value : " + FVtoCheck+ " Actual Value      Value: " + actual,"FAIL");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("check_attribute_value", "check_attribute_value : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
			return false;
		}
	}

	

	public boolean compare_text_value(String FieldName, String ActualValue, String ExpectedValue) 
	{
		if (ActualValue == null)
			ActualValue = "";

		if (ExpectedValue == null)
			ExpectedValue = "";

		if (ActualValue.equals(ExpectedValue)) 
		{
			oReport.AddStepResult("compare_text_value",	"FieldName : " + FieldName + "  has expected value : " + ExpectedValue, "PASS");
			return true;
		}
		else 
		{
			oReport.AddStepResult("compare_text_value", "FieldName : " + FieldName + " :Expected value : "+ ExpectedValue + ":Actual Value : " + ActualValue, "FAIL");
			return false;
		}
	}

	public boolean contains_text_value(String FieldName, String ActualValue, String ExpectedValue) 
	{
		if (ActualValue.contains(ExpectedValue)) 
		{
			oReport.AddStepResult("contains_text_value","FieldName : " + FieldName + "  contains expected value : " + ActualValue, "PASS");
			return true;
		}
		else 
		{
			oReport.AddStepResult("contains_text_value", "FieldName : " + FieldName + " :Expected value : "	+ ExpectedValue + ":Actual Value : " + ActualValue, "FAIL");
			return false;
		}
	}

	@SuppressWarnings("null")
	public boolean waitFor(By elementDesc, String elementName) 
	{
		try 
		{
			fixed_wait_time(1);
			WebDriverWait wait = new WebDriverWait(driver, 30);

			String startTime = get_specified_date(0, "YYYY-MM-dd HH:mm:ss");
			WebElement finder = wait.until(ExpectedConditions.visibilityOfElementLocated(elementDesc));
			String endTime = get_specified_date(0, "YYYY-MM-dd HH:mm:ss");

			int delay = timeDifference(startTime, endTime);

			if (delay > 5)
				oReport.AddStepResult("Delay", elementName + " element is taking long time to load", "WARNING");

			return finder.isDisplayed();
		}
		catch (Exception ex) 
		{
			//oReport.AddStepResult("waitFor", "Exception : " + ex.getMessage(), "INFO");
			System.out.println("waitFor Exception : " + elementName + " element couldnt load within time" );
			return false;
		}
	}
	
	public boolean longWaitFor(By elementDesc, String elementName) 
	{
		try 
		{
			fixed_wait_time(1);
			WebDriverWait wait = new WebDriverWait(driver, 300);

			String startTime = get_specified_date(0, "YYYY-MM-dd HH:mm:ss");
			WebElement finder = wait.until(ExpectedConditions.visibilityOfElementLocated(elementDesc));
			String endTime = get_specified_date(0, "YYYY-MM-dd HH:mm:ss");

			int delay = timeDifference(startTime, endTime);

			/*if (delay > 300)
				oReport.AddStepResult("Delay", elementName + " is taking long time to load", "WARNING");
			else*/
			oReport.AddStepResult("Total Delay", elementName + " element is taking " + delay +" seconds to load", "INFO");

			return finder.isDisplayed();
		}
		catch (Exception ex) 
		{
			//oReport.AddStepResult("waitFor", "Exception : " + ex.getMessage(), "INFO");
			System.out.println("LongWaitFor Exception : " + elementName + " element couldnt load within 5 min" );
			return false;
		}
	}

	public WebDriverWait waitTime(long waitFor) 
	{
		try
		{
			return new WebDriverWait(driver, waitFor);
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return null;
		}
	}

	public void WaitForLoading(String sValue) 
	{
		System.out.println("Wait For : " + sValue);

		By LoadingIcon = By.cssSelector("div.k-loading-mask > div.k-loading-image");
		By SpinningIcon = By.cssSelector("div#and-container>div.and-spinner");
		By GirdSpinnerObj = By.xpath("//td[@class='td-grid-spinner']");
		By UploadIcon = By.xpath("//div[@class='ossui-attachment-upload-loading-icon']");

		long StartTime = System.currentTimeMillis();
		try 
		{
			do 
			{
				fixed_wait_time(1);
				if (driver.findElements(LoadingIcon).size() == 0)
					if (driver.findElement(SpinningIcon).getAttribute("style").contains("display: none"))
						if (driver.findElements(GirdSpinnerObj).size() == 0)
							if (driver.findElements(UploadIcon).size() == 0)
								break;
			}
			while ((System.currentTimeMillis() - StartTime) <= 180000);
		} 
		catch (Exception e) 
		{
			if (e.getMessage().contains("Unable to locate element"))
				System.out.println("Unable to locate object");
			else
				System.out.println("Exception occured " + e.getMessage());
		}

		System.out.println("Time Taken : " + (System.currentTimeMillis() - StartTime));
	}

	public boolean WaitForObjectProperty(String FieldName, By elemDesc, String attributeName, String attributeValue) 
	{
		long StartTime = System.currentTimeMillis();

		try 
		{
			if (attributeName.equalsIgnoreCase("Exist")) 
			{
				if (attributeValue.equalsIgnoreCase("True"))
					while (!IsFieldDisplayed(FieldName, elemDesc) && (System.currentTimeMillis() - StartTime) <= 180000)
						fixed_wait_time(1);
				else if (attributeValue.equalsIgnoreCase("False"))
					while (IsFieldDisplayed(FieldName, elemDesc) && (System.currentTimeMillis() - StartTime) <= 180000)
						fixed_wait_time(1);
				else
					return false;
			}
			else 
			{
				if (IsFieldDisplayed(FieldName, elemDesc)) 
				{
					WebElement WebElem = driver.findElement(elemDesc);
					String ActAttributeValue = WebElem.getAttribute(attributeValue);
					while ((!ActAttributeValue.contains(attributeValue)	&& ((System.currentTimeMillis() - StartTime) <= 180000))) 
					{
						if (IsFieldDisplayed(FieldName, elemDesc))
							ActAttributeValue = WebElem.getAttribute(attributeValue);
						else
							return false;
					}
				}
				else
					return false;
			}
		}
		catch (Exception expObj) 
		{
			System.out.println("WaitForObjectProperty ::: Exception occured " + expObj.getMessage());
			return false;
		}

		System.out.println("WaitForObjectProperty ::: FieldName : " + FieldName + ":Attribute Name : " + attributeName+ ":Attribute Value : " + attributeValue + ":Time Taken :" + (System.currentTimeMillis() - StartTime));
		return true;
	}

	public boolean waitForIsClickable(By elementDesc, String FieldName) 
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement finder = wait.until(ExpectedConditions.elementToBeClickable(elementDesc));
			return finder.isDisplayed();
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("check_attribute_value", "check_attribute_value : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			return false;
		}
	}
	
	public boolean waitForIsInvisible(By elementDesc, String FieldName) 
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver, 180);
			
			String startTime = get_specified_date(0, "YYYY-MM-dd HH:mm:ss");
			//WebElement finder = wait.until(ExpectedConditions.visibilityOfElementLocated(elementDesc));
			boolean finder = wait.until(ExpectedConditions.invisibilityOfElementLocated(elementDesc));
			String endTime = get_specified_date(0, "YYYY-MM-dd HH:mm:ss");

			int delay = timeDifference(startTime, endTime);

			if (delay >= 170)
			{
				oReport.AddStepResult("Delay",FieldName + " element is taking long time to load, Total time taken is : " + delay, "WARNING");
				oParameters.SetParameters("CONTINUED_EXECUTION", "NO");
			}
			return finder;
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("check_attribute_value", "check_attribute_value : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			oParameters.SetParameters("Continued_Execution", "NO");
			return false;
		}
	}
	
	public void fixed_wait_time(int TimeInSeconds) 
	{
		try 
		{
			Thread.sleep(TimeInSeconds * 1000);
		}
		catch (InterruptedException e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(),"Unexpected Exception : " + e.getMessage(), "FAIL");
		}
	}
	
	public void implicitWait(int seconds) 
	{
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	
	public int timeDifference(String startTime, String endTime) 
	{
		int startTimeMin = 0;
		int startTimeSec = 0;
		int endTimeMin = 0;
		int endTimeSec = 0;

		try 
		{
			String[] Time = startTime.split(" ");
			String[] Time1 = Time[1].split(":");
			startTimeMin = Integer.parseInt(Time1[1]);
			startTimeSec = Integer.parseInt(Time1[2]);

			String[] eTime = endTime.split(" ");
			String[] eTime1 = eTime[1].split(":");
			endTimeMin = Integer.parseInt(eTime1[1]);
			endTimeSec = Integer.parseInt(eTime1[2]);

			if (endTimeMin > startTimeMin) 
			{
				endTimeSec = endTimeSec + 60;
				endTimeSec = endTimeSec - startTimeSec;
			}
			else 
			{
				endTimeSec = endTimeSec - startTimeSec;
			}

		} 
		catch (Exception e) 
		{

		}
		return endTimeSec;
	}

	public void refresh_page() 
	{
		try
		{
			driver.navigate().refresh();
			fixed_wait_time(5);
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public void StartTimer() 
	{
		StartTime = System.currentTimeMillis();
	}

	public boolean untilExpectedTime() 
	{
		Long eT = (System.currentTimeMillis() - StartTime) / 1000;
		if (eT > 120)
			return false;
		else
			return true;
	}

	public void check_Alert(String action) 
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			String warning = alert.getText();
			oReport.AddStepResult("Warning Message", "Displayed warning : " + warning, "DONE");
			if (action == "accept")
				alert.accept();
			else
				alert.dismiss();
		}
		catch (Exception e) 
		{
			System.out.println("Exception : " + e.getMessage());
		}
	}

	public boolean check_current_url(String FieldName, String FVtoCheck) 
	{
		try 
		{
			String actual = driver.getCurrentUrl();

			if (actual.equals(FVtoCheck)) 
			{
				oReport.AddStepResult("check_current_url", "FieldName : " + FieldName + ":Actual Value : " + actual,"PASS");
				return true;
			}
			else 
			{
				oReport.AddStepResult("check_current_url","FieldName : " + FieldName + " :Expected Value : " + FVtoCheck + ":Actual Value : " + actual,"FAIL");
				return false;
			}
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("check_current_url", "check_current_url : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("check_current_url : Exception : " + eObj.getMessage());
			return false;
		}
	}

	public boolean check_page_title(String FieldName, String FVtoCheck) 
	{
		try 
		{
			String actual = driver.getTitle();

			if (actual.equals(FVtoCheck)) 
			{
				oReport.AddStepResult("check_page_title", " Field Name " + FieldName + ":Actual Value : " + actual,"PASS");
				return true;
			}
			else 
			{
				oReport.AddStepResult("check_page_title","FieldName : " + FieldName + " :Expected Value : " + FVtoCheck + ":Actual Value : " + actual,"FAIL");
				return false;
			}
		}
		catch (Exception eObj) 
		{
			oReport.AddStepResult("check_page_title", "check_page_title : " + FieldName +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
			return false;
		}
	}

	public void check_checkbox(By locator) 
	{
		try
		{
			WebElement we = driver.findElement(locator);
			if (!(we.isSelected())) 
			{
				we.click();
			}
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}
	
	
	public void switchToNewWindow(int windowNumber) 
	{
		try
		{
			Set<String> windowHandles = driver.getWindowHandles();
			Iterator<String> oIterator = windowHandles.iterator();
			int i = 1;
			while (oIterator.hasNext() && i < 10) 
			{
				String popupHandle = oIterator.next().toString();
				driver.switchTo().window(popupHandle);
				System.out.println("Window title is : " + driver.getTitle());
				if (i == windowNumber)
					break;
				i++;
			}
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
	}

	public void windowHandle(String window) 
	{
		try 
		{
			Set<String> wh = driver.getWindowHandles();
			Iterator<String> it = wh.iterator();

			String parent = it.next();
			String child = it.next();

			if (window == "parent")
				driver.switchTo().window(parent);
			else
				driver.switchTo().window(child);
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("windowHandle", "windowHandle : " +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Caught Exception" + eObj);
		}
	}

	public String navigate_to_newtab(String URL) 
	{
		try 
		{
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

			driver.switchTo().window(tabs.get(1));

			driver.get(URL);

			fixed_wait_time(20);

			return tabs.get(0);
		}

		catch (Exception eObj) 
		{
			oReport.AddStepResult("navigate_to_newtab", "navigate_to_newtab : " +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
			return "";
		}
	}

	public void navigate_back_to_main_tab(String oldTab) 
	{
		try 
		{
			driver.close();

			fixed_wait_time(5);

			driver.switchTo().window(oldTab);

			driver.switchTo().defaultContent();
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("navigate_back_to_main_tab", "navigate_back_to_main_tab : " +"Exception : " + eObj.getMessage(), "UNKNOWN");
			System.out.println("Exception : " + eObj.getMessage());
		}
	}

	public boolean run_autoit_file(String FileName, String arguments) throws Exception 
	{
		String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		oReport.AddStepResult(currentMethodName, "Executing Auto it file : " + FileName, "DONE");

		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();

		if (relativePath.endsWith("bin"))
			relativePath = new File(System.getProperty("user.dir")).getParent();

		relativePath = relativePath + "\\src\\test\\seleniumdriver\\" + FileName + "_"
				+ System.getenv("PROCESSOR_ARCHITECTURE") + ".exe";

		oReport.AddStepResult(currentMethodName, "Dummy Step", "DONE");
		fixed_wait_time(2);

		try 
		{
			if (check_file_exist(relativePath)) 
			{
				if (arguments.isEmpty())
					Runtime.getRuntime().exec(relativePath);
				else
					Runtime.getRuntime().exec(relativePath + " " + arguments);
				oReport.AddStepResult(currentMethodName, "Auto it file : " + FileName + " executed successfully","PASS");
				return true;
			}
			else
				return false;
		} 
		catch (Exception eObj) 
		{
			oReport.AddStepResult("run_autoit_file", "run_autoit_file : " + FileName + "Exception : " + eObj.getMessage(), "UNKNOWN");
			return false;
		}
	}

	public void unZipFolder(String zipFile, String outputFolder) 
	{
		byte[] buffer = new byte[1024];

		try 
		{
			// create output directory is not exists
			File folder = new File(outputFolder);
			if (!folder.exists())
				folder.mkdir();

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) 
			{
				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);

				System.out.println("file unzip : " + newFile.getAbsoluteFile());

				// create all non exists folders, else you will hit FileNotFoundException for
				// compressed folder
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) 
				{
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");

		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}

	public boolean check_file_exist(Path filePath) 
	{
		if (Files.exists(filePath))
			return true;
		else
			return false;
	}

	public boolean check_file_exist(String filePath) 
	{
		return check_file_exist(Paths.get(filePath));
	}

	
	public boolean save_text_file(String filePath, String fileContent) 
	{
		File file = null;
		BufferedWriter output = null;

		try 
		{
			file = new File(filePath);
			output = new BufferedWriter(new FileWriter(file));
			output.write(fileContent);
			output.close();
		}
		catch (Exception e) 
		{
			System.out.println("Unhandled Error Message : " + e.getMessage());
			return false;
		}

		return true;
	}

	public static File getTheNewestFile(String filePath, String ext) 
	{
		File theNewestFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + ext);
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) 
		{
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			theNewestFile = files[0];
		}
		return theNewestFile;
	}

	public void deleteFile(String filePath) 
	{
		File file = new File(filePath);
		file.delete();
	}

	public static void CreateFolder(String path) 
	{
		File dirPath = new File(path);

		if (!dirPath.exists()) 
		{
			System.out.println("creating directory: " + dirPath.getName());
			try 
			{
				dirPath.mkdir();
			}
			catch (Exception e) 
			{
				System.out.println("Exception Caught :" + e);
			}
		} 
		else 
		{
			System.out.println("Folder already present");
		}
	}

	public void csvToExcel() 
	{
		@SuppressWarnings("resource")
		Workbook wb = new HSSFWorkbook();
		CreationHelper helper = wb.getCreationHelper();
		File latestFile = getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "csv");
		String fileName = latestFile.getName().replace(".csv", " ");
		Sheet sheet = wb.createSheet(fileName);
		try 
		{
			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "csv")));
			String[] line;
			int r = 0;
			while ((line = reader.readNext()) != null) 
			{
				Row row = sheet.createRow((short) r++);

				for (int i = 0; i < line.length; i++)
					row.createCell(i).setCellValue(helper.createRichTextString(line[i]));
			}

			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(oParameters.GetParameters("downloadFilepath") + "/" + fileName + ".xlsx");
			wb.write(fileOut);
			fileOut.close();
		}
		catch (Exception e) 
		{
			System.out.println("Exception +" + e);
		}
	}

	public WebElement convertToWebElement(By elementDesc) 
	{
		try
		{
			WebElement oWebElement = driver.findElement(elementDesc);
			return oWebElement;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return null;
		}
	}

	public List<WebElement> convertToWebElements(By elementDesc) 
	{
		try
		{
			List<WebElement> oWebElement = driver.findElements(elementDesc);
			return oWebElement;
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return null;
		}
	}

	
	public void javaScriptExecutor(String FieldName, By elemdesc) 
	{
		try 
		{
			WebElement owebelement = driver.findElement(elemdesc);

			if (owebelement.isDisplayed()) 
			{
				oReport.AddStepResult("click_button", "Click on : " + FieldName, "DONE");
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].click()", owebelement);
			}
			else
				oReport.AddStepResult("click_button", FieldName + " : Not Found", "FAIL");
		}
		catch (Exception eObj) 
		{
			System.out.println("Exception : " + eObj.getMessage());
		}
	}
	
	public void scroll(String FieldName, By elemDesc)
	 {
		try
		{
		 WebElement element = driver.findElement(elemDesc);
		 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		 System.out.println("Scroll successfull to Field "+FieldName);
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
		 
	 } 
	
	public int noOfRows(By eleDesc)
	{
		try
		{
			return driver.findElements(eleDesc).size();
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
			return 0;
		}
	}
	
	public boolean click_on_link_in_table_row (By HeaderPath, By ContentPath, String ColumnName, int RowKey)
    {
      if(oParameters.GetParameters("Continued_Execution").equalsIgnoreCase("No"))
                    return false;
             int i;
      
             try
             {
                    List<WebElement> HeaderList = driver.findElements(HeaderPath);
                    List<WebElement> ContentList = driver.findElements(ContentPath);
                    
                    if (HeaderList.size()>0)
                    {
                           
                           for(i=0; i<= HeaderList.size();i++)
                           {
                                  if(HeaderList.get(i).getText().equalsIgnoreCase(ColumnName))
                                  break;
                           }
                             
                           if(ContentList.size()>=RowKey)
                           {
                                 WebElement Cell = ContentList.get(RowKey).findElement(By.xpath("div["+ (i+1) +"]//a"));
                               Cell.click();
                               
                           }
                           return true;
                    }
                    else
                           return false;
             }
             catch(Exception ExpObj)
             {      
                    //oReport.AddStepResult("click_on_table_row1","Exception :"+ExpObj.getMessage(),"UNKNOWN");
                    //System.out.println("Click_on_table_row_value -Exception - " + ExpObj.getMessage());
                    return false;
             }
       }
	
	public String get_table_row_value(By HeaderPath, By ContentPath, String ColumnName, int RowKey)
    {
		waitFor(ContentPath, ColumnName);
		
          int i;
          String str = "", rVal="", Constr = "", ConrVal="";
          try
          {
                 List<WebElement> HeaderList = driver.findElements(HeaderPath);
                 List<WebElement> ContentList = driver.findElements(ContentPath);
                              
                 for(i=0; i<= HeaderList.size();i++)
                 {
                        
                        if(HeaderList.size()>0)
                        {
                              if(!HeaderList.get(i).getText().isEmpty())
                                     str = HeaderList.get(i).getText();
                                     
                              else if(HeaderList.get(i).getAttribute("value") != null)
                                     str = HeaderList.get(i).getAttribute("value");
                              else if(HeaderList.get(i).getAttribute("title") != null)
                              {
                                     str = HeaderList.get(i).getAttribute("title");
                                     if(str.isEmpty())
                                            if(HeaderList.get(i).getAttribute("data-title") != null)
                                                  str = HeaderList.get(i).getAttribute("data-title");
                              }
                              rVal = str.trim();
                              
                              if(rVal.equalsIgnoreCase(ColumnName))
                                     break;
                        }
                        else
                              System.out.println("Header element is not found");
                 }
                 
                 if(ContentList.size()>=RowKey)
                 {
                        if(ContentList.size()>0)
                        {
                              WebElement Cell = ContentList.get(RowKey).findElement(By.xpath("div["+ (i+1) +"]"));
                              if(!Cell.getText().isEmpty())
                                     Constr = Cell.getText();
                              else if(Cell.getAttribute("value") != null)
                                     Constr = Cell.getAttribute("value");
                              else if(Cell.getAttribute("title") != null)
                              {
                                     Constr = Cell.getAttribute("title");
                                     if(Constr.isEmpty())
                                            if(Cell.getAttribute("data-title") != null)
                                                  Constr = Cell.getAttribute("data-title");
                              }
                              ConrVal = Constr.trim();
                        }
                 }
          }
          catch(Exception ExpObj)
          {
                 oReport.AddStepResult("get_table_row_value","Exception :"+ExpObj.getMessage(),"UNKNOWN");
                 System.out.println("get_table_row_value -Exception - " + ExpObj.getMessage());
          }
          
          return ConrVal;
          
    }

	
	public void deleteCookies()
	{
		try
		{
			driver.manage().deleteAllCookies();
		}
		catch(Exception e)
		{
			System.out.println("Exception caught : "+e);
		}
	} 
	
	public  void FileHandled() throws InterruptedException, AWTException
	{
		try
		{
			Robot robot = new Robot();
			 
			Thread.sleep(1000);
			 
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			
			// Press CTRL+V
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			
			// Release CTRL+V
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
		
	}

	public void OpenFile() throws IOException
	{
		try
		{
			String ra  = String.valueOf(getTheNewestFile(oParameters.GetParameters("downloadFilepath"), "xlsx"));
			
			File file = new File(ra);
	        
	        //first check if Desktop is supported by Platform or not
	        if(!Desktop.isDesktopSupported())
	        {
	            System.out.println("Desktop is not supported");
	            return;
	        }
	        
	        Desktop desktop = Desktop.getDesktop();
	        if(file.exists()) desktop.open(file);
	        
	        fixed_wait_time(3);
	        oReport.AddStepResult("File", "Downloaded File ScreenShot", "SCREENSHOT");
		}
		catch (Exception e) 
		{
			oReport.AddStepResult(Thread.currentThread().getStackTrace()[1].getMethodName(), "Unexpected Exception - " + e.getMessage(), "UNKNOWN");
		}
       
    }
}

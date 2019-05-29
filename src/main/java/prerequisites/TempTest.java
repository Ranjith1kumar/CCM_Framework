package prerequisites;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libraries.*;

/**
 * @author C16137
 *
 */

public class TempTest extends CCMLibrary
{
	ExcelData oExcelData = new ExcelData(oParameters);
	WebDriver driver = new FirefoxDriver();
	protected static ExtentReports report;
	protected static ExtentTest logger;
	
	public TempTest(WebDriver driver, Report oReport, Parameters oParameters)
	{
		super(driver, oReport, oParameters);
	}

	public static void setExcelData(String filePath, String fileName, String sheetName, int rowNum, int cellNum, String data) 
	{
		try 
		{
			filePath = filePath + fileName;

			// pick the excel file for reading
			FileInputStream fis = new FileInputStream(filePath);
			// Get the workbook
			Workbook wb = WorkbookFactory.create(fis);
			// Get a particular sheet from that workbook
			Sheet s = wb.getSheet(sheetName);
			// Get a particular row from that sheet. If data has to
			// be written to an empty row, use createRow() instead
			// of getRow()
			Row r = s.getRow(rowNum);
			// To write data to an empty cell we need to create
			// a particular cell in that row using createCell.
			Cell c = r.createCell(cellNum);
			// c.setCellType(Cell.CELL_TYPE_STRING);
			// set the string value present to that cell
			c.setCellValue(data);
			// use FileOutputStream to write data back to Excel File
			FileOutputStream fos = new FileOutputStream(filePath);
			wb.write(fos);
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught :" + e);
		}
	}
	
	public String screenShot()
	{
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		String scrShotPath = "C://CCM" + "/" + System.currentTimeMillis();

		try 
		{
			FileUtils.copyFile(src, new File(scrShotPath));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return scrShotPath;
	}
	
	public static void Test()
	{
		String X = "a,s,f,e,g,c,m";
		System.out.println(X.indexOf("a"));
	}
	
	public static String readFileAsString(String fileName)throws Exception 
	{ 
	    String data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data; 
	} 
	
	 @SuppressWarnings("unused")
	public static void fileCompression() throws Exception
	{
		String s = readFileAsString("C:\\CCM\\Ranjith.txt");
		String[] arg1 = s.split("HL");
		int k = 0;
		int j = 0;
		String result;
		List<String> res = new ArrayList<>();
		
		for(String foo : arg1)
		{
			if(!foo.isEmpty())
			{	
				int i = 0;
				
				Pattern p = Pattern.compile("CLM");
				Matcher m = p.matcher(foo);
				while (m.find()) 
				{
					i++;
				}
				System.out.println(foo+"--CLM="+i);
				
				if(i>1)
				{
					result="FAIL";
					k++;
				}
				else
					result="PASS";
					
				j = i+j;
				res.add(result);
				
				 
			System.out.println("----------------------");	
			}
		}	
		
		System.out.println("CLM Count:"+j);
		System.out.println("Result Contains :"+ res);
		System.out.println("FAIL Count :"+ k);
		
		if(res.contains("FAIL"))
			System.out.println("FAIL");
		else
			System.out.println("PASS");
		}	
	 
	
	public static void main(String[] args) throws InterruptedException, AWTException
	{
		//report();
		//Test();
		//demo("RanjithKumar");
		
		/*String e = "34,00";
		System.out.println(e.replace(",",""));*/
		
		//practise();
		
		//Shifting_String("Ranjith Kumar");
		
		
		
	}
	 
	 public static void report()
	 {
		 String reportPath;
		 reportPath = "C://CCM//ExecutionReport//Ranjith";
		 
		 String reportName = reportPath + "-" + System.currentTimeMillis() + ".Html";
		 System.out.println(reportName);
		 
		 boolean isFolderCreated = (new File(reportPath)).mkdir();
		 
		 if (isFolderCreated)
				System.out.println("Report Folder created");
			else
				System.out.println("Report Folder already exist");
		 
		 report = new ExtentReports(reportName, true);
		 logger = report.startTest("Automation");

	 }
	 
	 public static void demo(String value)
	 {
		String str = value; 
		Map<Character,Integer> map = new HashMap<Character,Integer>();
		
		for(int i=0;i<str.length();i++)
		{
			char number = str.charAt(i);
			
			if(!map.containsKey(number))
				map.put(str.charAt(i),1);
			else
				map.put(str.charAt(i), map.get(number)+1);
		}
		
		String result = map.toString();
		
		String[] list = result.split(",");
		
		for(int i=0 ;i<list.length;i++)
		{
			System.out.println(list[i].replaceAll("[{}]", " "));
		}
	 }
	 
	 public static void Shifting_String(String value)
	 {
		String str = value;
		LinkedList<String> list = new LinkedList<>();
		
		
		for(int i=0 ; i<str.length();i++)
		{
			char c = str.charAt(i);
			
			list.add(String.valueOf(c));
		}
		
		String result = list.toString();
		
		String[] li = result.split(",");
		
		for(int i=0 ;i<li.length;i++)
		{	
			System.out.print(li[i]);
		}
	}
	 
	 
	 public static void practise()
	 {
		 int arr[] = new int[] {1,2,4,5};
		 
		 Vector<Integer> v = new Vector<>();
		 
		 Hashtable<Integer, String> ah = new Hashtable<>();
		 
		 v.addElement(2);
		 v.addElement(5);
		 
		 ah.put(1, "Ranjith");
		 ah.put(2, "DELL");
		 
		 System.out.println(arr[3]);
		 System.out.println(v.elementAt(1));
		 System.out.println(ah.get(2));
	 }
	 
	 
}	
	

		
		


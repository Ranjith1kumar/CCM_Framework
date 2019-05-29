package libraries;

import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.WebDriver;
import com.google.common.collect.*;

public class ExcelData
{
	private static XSSFSheet sheet = null;
	private static XSSFRow row = null;
	private static XSSFCell cell = null;
	public static XSSFWorkbook workbook;
	static String parameterName = null;
	static Multimap<String, String> testDataParam = LinkedListMultimap.create();
	public static InputStream fileToRead;
	int sheetCount;

	protected WebDriver driver;
	protected Parameters oParameters;

	public ExcelData(Parameters oParameters) 
	{
		this.oParameters = oParameters;
	}

	// Method to read content of specific row based on keyValue/ to read all records
	// from one perticular sheet
	@SuppressWarnings("unused")
	public void readExcelRow(String filePath, String fileName, String sheetName, String keyValue) 
	{
		try 
		{
			// Create an object of File class to open xlsx file
			File file = new File(filePath + "\\" + fileName);

			// Create an object of FileInputStream class to read excel file
			FileInputStream inputStream = new FileInputStream(file);

			Workbook workBook = null;

			// Find the file extension by splitting file name in substring and getting only
			// extension name
			String fileExtensionName = fileName.substring(fileName.indexOf("."));

			// Check condition if the file is xlsx file
			if (fileExtensionName.equals(".xlsx")) 
			{
				// If it is xlsx file then create object of XSSFWorkbook class
				workBook = new XSSFWorkbook(inputStream);
			}
			// Check condition if the file is xls file
			else if (fileExtensionName.equals(".xls")) 
			{
				// If it is xls file then create object of XSSFWorkbook class
				workBook = new HSSFWorkbook(inputStream);
			}
			else
				System.out.println("Some other Format");

			// Read sheet inside the workbook by its name
			Sheet readSheet = workBook.getSheet(sheetName);

			// Find number of rows in excel file
			int rowCount = readSheet.getLastRowNum();

			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i <= rowCount; i++) 
			{
				Row rowHeading = readSheet.getRow(0);
				Row row = readSheet.getRow(i);

				String cellVal = row.getCell(0).getStringCellValue();

				if (!keyValue.isEmpty() && cellVal.equalsIgnoreCase(keyValue)) 
				{
					int colCount = row.getLastCellNum();

					// Create a loop to get cell values in a row
					for (int j = 0; j < colCount; j++) 
					{
						oParameters.SetParameters(rowHeading.getCell(j).getStringCellValue(),
								row.getCell(j).getStringCellValue());
					}
				} 
				else if (keyValue.isEmpty()) 
				{
					int colCount = row.getLastCellNum();

					// Create a loop to get cell values in a row
					for (int j = 0; j < colCount; j++) 
					{

						oParameters.SetParameters(rowHeading.getCell(j).getStringCellValue() + i,
								row.getCell(j).getStringCellValue());
						
						// Print Excel data in console
						System.out.println(rowHeading.getCell(j).getStringCellValue() + i + "-"
								+ row.getCell(j).getStringCellValue());
					}
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught : " + e);
		}
	}

	// Method to read data from one perticular cell using rowname and column name
	public void readCellData(String filePath, String fileName, String sheetName, String rowName, String columnName) 
	{
		try 
		{
			// Create an object of File class to open xlsx file
			File file = new File(filePath + "\\" + fileName);

			// Create an object of FileInputStream class to read excel file
			FileInputStream inputStream = new FileInputStream(file);

			Workbook workBook = null;

			// Find the file extension by splitting file name in substring and getting only
			// extension name
			String fileExtensionName = fileName.substring(fileName.indexOf("."));

			// Check condition if the file is xlsx file
			if (fileExtensionName.equals(".xlsx")) 
			{
				// If it is xlsx file then create object of XSSFWorkbook class
				workBook = new XSSFWorkbook(inputStream);
			}
			// Check condition if the file is xls file
			else if (fileExtensionName.equals(".xls")) 
			{
				// If it is xls file then create object of XSSFWorkbook class
				workBook = new HSSFWorkbook(inputStream);
			}
			else 
			{
				System.out.println("Some other Format");
			}

			// Read sheet inside the workbook by its name
			Sheet readSheet = workBook.getSheet(sheetName);

			// Find number of rows in excel file
			int rowCount = readSheet.getLastRowNum();

			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i <= rowCount; i++) 
			{
				// To get first row Data that is Heading
				Row rowHeading = readSheet.getRow(0);

				// To get ith row data
				Row row = readSheet.getRow(i);

				// To get first cell content
				String cellVal = row.getCell(0).getStringCellValue();

				if (cellVal.equalsIgnoreCase(rowName)) 
				{
					// To get last row number
					int colCount = row.getLastCellNum();

					// Create a loop to get cell values in a row
					for (int j = 0; j < colCount; j++) 
					{
						// To get column heading
						String columnHeading = rowHeading.getCell(j).getStringCellValue();

						if (columnHeading.equalsIgnoreCase(columnName)) 
						{
							oParameters.SetParameters(rowHeading.getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
							System.out.println(rowHeading.getCell(j).getStringCellValue() + i + "-"	+ row.getCell(j).getStringCellValue());
						}
					}
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught : " + e);
		}
	}

	// Method to read data from one perticular cell based on row number and coulmn
	// number
	public void readCellData(String filePath, String fileName, String sheetName, int rowNumber, int columnNumber) 
	{
		try 
		{
			// Create an object of File class to open xlsx file
			File file = new File(filePath + "\\" + fileName);

			// Create an object of FileInputStream class to read excel file
			FileInputStream inputStream = new FileInputStream(file);

			Workbook workBook = null;

			// Find the file extension by splitting file name in substring and getting only
			// extension name
			String fileExtensionName = fileName.substring(fileName.indexOf("."));

			// Check condition if the file is xlsx file
			if (fileExtensionName.equals(".xlsx")) 
			{
				// If it is xlsx file then create object of XSSFWorkbook class
				workBook = new XSSFWorkbook(inputStream);
			}
			// Check condition if the file is xls file
			else if (fileExtensionName.equals(".xls")) 
			{
				// If it is xls file then create object of XSSFWorkbook class
				workBook = new HSSFWorkbook(inputStream);
			}
			else 
			{
				System.out.println("Some other Format");
			}
			// Read sheet inside the workbook by its name
			Sheet readSheet = workBook.getSheet(sheetName);

			// Find number of rows in excel file
			int rowCount = readSheet.getLastRowNum();

			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i <= rowCount; i++) 
			{
				// To get first row Data that is Heading
				Row rowHeading = readSheet.getRow(0);

				// To get ith row data
				Row row = readSheet.getRow(i);

				// To get first cell content
				// String cellVal = row.getCell(0).getStringCellValue();

				if (i == rowNumber) 
				{
					// To get last row number
					int colCount = row.getLastCellNum();

					// Create a loop to get cell values in a row
					for (int j = 1; j <= colCount; j++) 
					{
						// To get column heading
						String columnHeading = rowHeading.getCell(j - 1).getStringCellValue();

						if (j == columnNumber) 
						{
							oParameters.SetParameters(rowHeading.getCell(j).getStringCellValue(),row.getCell(j).getStringCellValue());
							System.out.println(oParameters.GetParameters(rowHeading.getCell(j).getStringCellValue()));
							break;
						}
						else if (j == colCount)
							System.out.println("No column found");
					}
				} 
				else
					System.out.println("No row found");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught : " + e);
		}
	}


	public String getExcelData(String sheetName, int rowNum, int cellNum, String filePath) 
	{
		String val = null;
		try 
		{
			FileInputStream fis = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			Row r = s.getRow(rowNum);
			Cell c = r.getCell(cellNum);
			val = c.getStringCellValue();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught :" + e);
		}

		System.out.println(val);
		return val;
	}

	// Set data to excel cell. We need to pass 4 args, sheet,
	// row num, cell num and data
	public void setExcelData1(String filePath, String fileName, String sheetName, int rowNum, int cellNum, String data) 
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
			Row r = s.createRow(rowNum);
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
	
	
	public void setExcelData(String filePath, String fileName,String sheetName, int rowNum, int cellNum, String data)
	{
		try 
		{
			//pick the excel file for reading
			FileInputStream fis = new FileInputStream(filePath+fileName);
			//Get the workbook 
			Workbook wb = WorkbookFactory.create(fis);
			//Get a particular sheet from that workbook
			Sheet s = wb.getSheet(sheetName);
			//Get a particular row from that sheet. If data has to
			//be written to an empty row, use createRow() instead
			//of getRow()
			Row r = s.getRow(rowNum);
			//To write data to an empty cell we need to create 
			//a particular cell in that row using createCell.
			Cell c = r.createCell(cellNum);
			//c.setCellType(Cell.CELL_TYPE_STRING);
			//set the string value present to that cell
			c.setCellValue(data);
			//use FileOutputStream to write data back to Excel File
			FileOutputStream fos = new FileOutputStream(filePath+fileName);
			wb.write(fos);
		}
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
	}
	
	
	
	public void writeExcel(String filePath, String fileName, String sheetName,int rowNum,String data) throws IOException
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
			
		    int rowCount = s.getLastRowNum()-s.getFirstRowNum();
		     //Row row = s.getRow(0);
		    //Row newRow = s.createRow(rowCount);
		    int colCount = s.getRow(rowNum).getLastCellNum();
		    System.out.println(rowCount);
		     //System.out.println(newRow);
		    System.out.println(colCount);

		    for(int j = 0; j < s.getLastRowNum(); j++)
		    {
		    	s.getRow(rowNum).createCell(colCount).setCellValue(data);
		        //    Cell cell = newRow.createCell(j);
                //  cell.setCellValue(dataToWrite[j]);
		    }
		    FileOutputStream fout = new FileOutputStream(filePath);
		    wb.write(fout);
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught :" + e);
		}
	}
	

	// Set data to excel cell. We need to pass 4 args, sheet,
	// row num, cell num and data
	public void setExcelData(String sheetName, int rowNum, int cellNum, String data, File img, String filePath) 
	{
		try 
		{
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

	public int getRowCountWithSheetName(String sheetName, String filePath) 
	{
		int count = 0;

		try 
		{
			FileInputStream fis = new FileInputStream(filePath);
			Workbook wb;
			wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			count = s.getLastRowNum();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Cought :" + e);
		}
		return count;
	}

	public int getRowCount(String path) 
	{
		int count = 0;

		try 
		{
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheetName = wb.getSheetAt(0);
			count = sheetName.getLastRowNum();
			System.out.println("Row Number Count" + (count));
		}
		catch (Exception e) 
		{
			System.out.println("Exception Cought :" + e);
		}
		return count;
	}

	public static int getRowCount(File path) 
	{
		int count = 0;

		try 
		{
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheetName = wb.getSheetAt(0);
			count = sheetName.getLastRowNum();
		}
		catch (Exception e) 
		{
			System.out.println("Exception Cought :" + e);
		}

		System.out.println("Row Number Count" + (count));
		return count;
	}

	public int getSheetCount(String filepath) 
	{
		try 
		{
			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		sheetCount = workbook.getNumberOfSheets();
		return sheetCount;
	}

	public String getSheetName(int sheetCount, String filepath) 
	{
		try 
		{
			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return workbook.getSheetName(sheetCount);
	}

	public int getRowCount1(String sheetName, String filepath) 
	{
		int number = 0;
		try 
		{
			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		int index = workbook.getSheetIndex(sheetName);

		if (index == -1)
			return 0;
		else 
		{
			sheet = workbook.getSheetAt(index);
			number = sheet.getLastRowNum() + 1;
		}
		return number;
	}

	public int getColumnCount(String sheetName, String filepath) 
	{
		// check if sheet exists
		if (!isSheetExist(sheetName, filepath))
			return -1;
		try 
		{
			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;
		return row.getLastCellNum();
	}

	public int getColumnCount(int sheetIndex, String filepath, int rownum) 
	{
		// check if sheet exists
		if (sheetIndex < 0)
			return -1;
		try 
		{
			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		sheet = workbook.getSheetAt(sheetIndex);
		row = sheet.getRow(rownum);

		if (row == null)
			return -1;

		return row.getLastCellNum();
	}

	public boolean isSheetExist(String sheetName, String filepath) 
	{
		try 
		{
			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) 
		{
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		}
		else
			return true;
	}

	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, String colName, int rowNum, String filepath) 
	{
		try 
		{
			if (rowNum <= 0)
				return "";
			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) 
			{
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) 
			{
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) 
				{
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

					// System.out.println(cellText);
				}
				return cellText;
			}
			else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "Cell is Empty";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, int colNum, int rowNum, String filepath) 
	{
		try 
		{
			if (rowNum <= 0)
				return "";
			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);
			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();

			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) 
			{
				String cellText = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
				return cellText;
			} 
			else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) 
			{
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) 
				{
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}
				return cellText;
			} 
			else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "Cell is Empty";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	@SuppressWarnings("deprecation")
	public static String getCellData(int sheetIndex, int colNum, int rowNum, String filepath) 
	{
		try 
		{
			if (rowNum <= 0)
				return "";

			if (sheetIndex == -1)
				return "";

			fileToRead = new FileInputStream(filepath);
			workbook = new XSSFWorkbook(fileToRead);

			sheet = workbook.getSheetAt(sheetIndex);
			row = sheet.getRow(rowNum - 1);

			if (row == null)
				return "";

			cell = row.getCell(colNum);

			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();

			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) 
			{
				String cellText = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
				return cellText;
			}
			else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) 
			{
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) 
				{
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
				}
				return cellText;
			} 
			else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "Cell is Empty";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	public String getAllData(String sheetName, String filepath) 
	{
		int rowCount = getRowCount1(sheetName, filepath);
		int colCount = getColumnCount(0, filepath, 1);
		for (int row = 0; row <= rowCount; row++) 
		{
			for (int col = 0; col <= colCount + 1; col++) 
			{
				parameterName = getCellData(0, col, row, filepath);

				System.out.println(parameterName);

				testDataParam.put(parameterName, parameterName);
			}
		}

		System.out.println(testDataParam.containsKey("Carrier"));
		return parameterName;
	}

	public Multimap<String, String> getrowData(int UserInputRow, String filepath) 
	{
		int colCount = getColumnCount(0, filepath, 1);

		for (int col = 0; col <= colCount + 1; col++) 
		{
			parameterName = getCellData(0, col, UserInputRow, filepath);

			testDataParam.put(parameterName, parameterName);
		}

		return testDataParam;
	}
}

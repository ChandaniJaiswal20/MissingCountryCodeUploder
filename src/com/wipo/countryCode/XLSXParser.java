package com.wipo.countryCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXParser 
{
	
	
	public static Logger logger	=	Logger.getLogger(XLSXParser.class);
	private String schemaFileName;
	private Map<String,String> countryCodeMap	=	new HashMap<String,String>();
	//private 
	
	
	public Map<String,String> getCountryCodeMap(){
		
		  return countryCodeMap;
	}

	public XLSXParser(String schemaFileName) { 
			this.schemaFileName	=	schemaFileName;
			
			this.parseXLSX(); 
		 } 

	
	public  void 	parseXLSX() 
	{	FileInputStream file =null;
	
		try
		{
			 file = new FileInputStream(new File(schemaFileName));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				
				 String countryCode	=	null;
				 String 	countryName	=	null;
				 
				 Row row = rowIterator.next();
				
				if(row.getRowNum()==0){
					
					continue;
				}
				
			//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while (cellIterator.hasNext()) 
				{
					
					Cell cell = cellIterator.next();
										
					switch (cell.getColumnIndex()) 
					{
						case 0:
							countryName	=	cell.getStringCellValue();
						//	System.out.println(countryName);
							break;
						case 2:
							countryCode=	cell.getStringCellValue();
						//	System.out.println(countryCode);
							break;
							default:continue;
					}
					
				
					
				}
				//System.out.println(""); change of row
			//	System.out.println(countryCode +"    "+ countryName);
			//	System.out.println(countryCode );
				 countryCodeMap.put(countryCode, countryName);
				
			}
			
			file.close();
			
			
		//	System.out.println(countryCodeMap + "  ");
		//	System.out.println(countryCodeMap.size() + "  ");
			//logger.error("Error occurred while parsing :: "+ schemaFileName ,e);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Error occurred while parsing :: "+ schemaFileName ,e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error occurred while parsing :: "+ schemaFileName ,e);
		}
		
	}
	
	
	public static void main(String[] args) 
	{
	
			
				XLSXParser xparser	=	 new XLSXParser("ISO-Alpha2-All.xlsx");
				
				
				//FileInputStream file = new FileInputStream(new File("ISO-Alpha2-All.xlsx"));
				xparser.parseXLSX();
				
				System.out.println(xparser.getCountryCodeMap());
				
			//	file.close();
				
	
			
	
	}
	
}

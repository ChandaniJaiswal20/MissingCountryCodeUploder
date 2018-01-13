package com.howtodoinjava.demo.poi;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelDemo 
{
	public static void main(String[] args) 
	{
		try
		{
			FileInputStream file = new FileInputStream(new File("ISO-Alpha2-All.xlsx"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				
				if(row.getRowNum()==0){
					
					continue;
				}
				
			
				
				
				//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while (cellIterator.hasNext()) 
				{
					//	int colomn_count	=	0;
					Cell cell = cellIterator.next();
					
						if(cell.getColumnIndex()==1){
							
							continue;
						}
						else if(cell.getColumnIndex()==3){
							
							continue;
						}
						else{
							
						System.out.print(cell.getStringCellValue() + "\t");
						
						}
					//Check the cell type and format accordingly
						/*
					switch (cell.getCellType()) 
					{
						case Cell.CELL_TYPE_NUMERIC:
							System.out.print(cell.getNumericCellValue() + "\t");
							break;
						case Cell.CELL_TYPE_STRING:
							System.out.print(cell.getStringCellValue() + "\t");
							break;
					}
					**/
					
				}
				System.out.println("");
			}
			file.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

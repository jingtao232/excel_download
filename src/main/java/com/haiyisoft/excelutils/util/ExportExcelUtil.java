package com.haiyisoft.excelutils.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExportExcelUtil {

	/**
	 * 导出Excel
	 *
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容
	 * @param wb HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

		final int SEGMENT_VALUE = 60000;

		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		final int sheetNum = (int) Math.ceil((float) values.length / SEGMENT_VALUE);
		for (int num = 1; num <= sheetNum; num++) {
			// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet(sheetName + num);

			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
			HSSFRow row = sheet.createRow(0);

			// 第四步，创建单元格格式，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

			// 声明列对象
			HSSFCell cell = null;

			// 创建标题
			for (int i = 0; i < title.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(title[i]);
				cell.setCellStyle(style);
			}


			int valueLength = 0;
			if (num != sheetNum) {
				valueLength = SEGMENT_VALUE * num;
			} else {
				valueLength = values.length - (SEGMENT_VALUE * (sheetNum - 1));
			}

			// 创建内容
			for (int i = (num - 1) * SEGMENT_VALUE; i < valueLength + (num - 1) * SEGMENT_VALUE; i++) {
				row = sheet.createRow(i - (num - 1) * SEGMENT_VALUE + 1);
				for (int j = 0; j < values[i].length; j++) {
					// 将内容按顺序赋给对应的列对象
					row.createCell(j).setCellValue(values[i][j]);
				}
			}


			// 让列宽随着导出的列长自动适应
			for (int colNum = 0; colNum < values[0].length; colNum++) {
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
					HSSFRow currentRow;
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}

					if (currentRow.getCell(colNum) != null) {
						HSSFCell currentCell = currentRow.getCell(colNum);
						if (currentCell.getCellType() == CellType.STRING) {
							int length = currentCell.getStringCellValue().getBytes().length;
							if (columnWidth < length) {
								columnWidth = length;
							}
						}
					}
				}

				if (colNum == 0) {
					sheet.setColumnWidth(colNum, (columnWidth + 2) * 256);
				} else {
					sheet.setColumnWidth(colNum, (columnWidth + 9) * 100 );
				}
			}

		}


		return wb;
	}

}

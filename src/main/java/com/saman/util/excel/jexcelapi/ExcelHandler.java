package com.saman.util.excel.jexcelapi;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.junit.Before;
import org.junit.Test;

public class ExcelHandler {

	private static final String EXCEL_FILE = "src/main/resources/com/saman/util/excel/jexcelapi/test.xls";

	private static final String IMAGE_FILE = "src/main/resources/com/saman/util/excel/jexcelapi/test.png";

	@Before
	public void create() throws Exception {
		// 打开文件
		WritableWorkbook book = Workbook.createWorkbook(new File(EXCEL_FILE));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet = book.createSheet(" 第一页 ", 0);
		// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
		// 以及单元格内容为test
		Label label = new Label(0, 0, " test ");

		// 将定义好的单元格添加到工作表中
		sheet.addCell(label);

		// 写入数据并关闭文件
		book.write();
		book.close();
	}

	@Test
	public void read() throws Exception {
		Workbook book = Workbook.getWorkbook(new File(EXCEL_FILE));
		// 获得第一个工作表对象
		Sheet sheet = book.getSheet(0);
		// 得到第一列第一行的单元格
		Cell cell1 = sheet.getCell(0, 0);
		String result = cell1.getContents();
		System.out.println(result);
		book.close();
	}

	@Test
	public void mergeCells() throws Exception {
		WritableWorkbook book = Workbook.createWorkbook(new File(EXCEL_FILE));
		WritableSheet sheet = book.createSheet("第一页", 0);
		sheet.mergeCells(3, 3, 6, 6); // 合并单元格

		// 设置填充内容的格式
		WritableFont font = new WritableFont(WritableFont.TIMES, 30,
				WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		format.setAlignment(jxl.format.Alignment.CENTRE); // 水平居中
		format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 垂直居中
		format.setBackground(jxl.format.Colour.BLUE); // 背景颜色和样式
		format.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THICK); // 边框样式

		Label label = new Label(3, 3, "合并", format); // 添加内容
		sheet.addCell(label);
		book.write();
		book.close();
	}

	@Test
	public void createImage() throws Exception {
		WritableWorkbook book = Workbook.createWorkbook(new File(EXCEL_FILE));
		WritableSheet sheet = book.createSheet("第一页", 0);
		WritableImage image = new WritableImage(8, 8, 4.3, 8.7, // 定义图片格式
				new File(IMAGE_FILE));
		sheet.addImage(image); // 添加图片

		book.write();
		book.close();
	}

}

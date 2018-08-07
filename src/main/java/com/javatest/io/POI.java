package com.javatest.io;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by ShenShuaihu on 2018/8/6.
 */
public class POI {
    public static void main(String[] args) {

        File file = new File("files/培训班成员分组-Java&HAP.xlsx");
        try {
            FileInputStream fis = new FileInputStream(file);
            /*HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet("序号");
            HSSFRow row = sheet.getRow(0);
            HSSFCell cell = row.getCell(0);
           */

            //读取xlsx文件
            XSSFWorkbook xssfWorkbook = null;
            //寻找目录读取文件
            xssfWorkbook = new XSSFWorkbook(fis);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);      // 获取Sheet
            int xssfRoWNumber = xssfSheet.getPhysicalNumberOfRows();   // 获取一共多少行
          /*  XSSFRow xssfRow = xssfSheet.getRow(0);      // 获取行
            XSSFRow xssfRow1 = xssfSheet.getRow(1);
*/
            ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
            //遍历xlsx中的sheet
            //  int xssfRoWNumber = xssfWorkbook.getNumberOfSheets();
            System.out.println("共多少行 : " + xssfRoWNumber);

            for (int numRoW = 0; numRoW < xssfRoWNumber; numRoW++) {       // 便利行
                if (xssfSheet == null) {
                    continue;
                }
                XSSFRow xssfRow = xssfSheet.getRow(numRoW); // 获取每一行

                int xssfCellNumber = xssfRow.getLastCellNum();  // 获取该行多少元素

                // System.out.println ("每行数据量："+xssfCellNumber );

                for (int numCell = 0; numCell < xssfCellNumber; numCell++) {   // 便利行的内容
                    XSSFCell xssfCell = xssfRow.getCell(numCell);   // 获取每行的内容

                    if (xssfCell == null) {
                        continue;
                    }
                    System.out.print(xssfCell + " | ");
                    //   System.out.println(xssfCell.toString() + " ");

                    //}
                }
                System.out.println();

                System.out.println("---------------------------------------");
            }
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

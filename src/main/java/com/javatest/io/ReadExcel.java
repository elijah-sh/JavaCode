package com.javatest.io;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by ShenShuaihu on 2018/8/6.
 */
public class ReadExcel {

    public static void main(String[] args) throws Exception {
        ReadExcel re = new ReadExcel();
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        List<Integer> list = re.random(num);

        Collections.sort(list);//默认排序(从小到大)
        System.out.println(list.toString());
            for (int i= 0;i<num;i++){

                System.out.println(re.getEmployee(list.get(i)).toString());

            }
    }

    /**
     *  产生无重复的随机数
     * @param num  需要产生几个
     * @return
     */
    public List<Integer> random(int num){
        List<Integer> list = new ArrayList<>();
        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>(list.size());
        Random rand = new Random();
        int size = 0;
        if (num>48){
            System.out.println("没有这么多人");
            return list;
        }
        while (size!=num){
            int ii= rand.nextInt(48);
            set.add(ii);
            size = set.size();
        }
        list.addAll(set);
        return list;
    }

    /**
     *  通过pid 查询员工,这样不太好，不断的IO操作，造成资源浪费
     * @param pid
     * @return
     * @throws Exception
     */
    public Employee getEmployee(Integer pid) throws Exception {
        Integer numRoW = pid;
        File file = new File("files/培训班成员分组-Java&HAP.xlsx");
        FileInputStream fis = new FileInputStream(file);
        //读取xlsx文件
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);      // 获取Sheet
        XSSFRow xssfRow = xssfSheet.getRow(numRoW); // 获取某一行

        int xssfCellNumber = xssfRow.getLastCellNum();  // 获取该行多少元素  每行数据量
        Employee employee = new Employee();
        for (int numCell = 0; numCell < xssfCellNumber; numCell++) {   // 便利行的内容
            XSSFCell xssfCell = xssfRow.getCell(numCell);   // 获取每行的内容
         //   System.out.print(xssfCell + " | ");
        }
        String department = xssfRow.getCell(1).toString();
          String name = xssfRow.getCell(2).toString();
          String sex = xssfRow.getCell(3).toString();
        String employeeId = xssfRow.getCell(4).toString() ;
        String groupId =  xssfRow.getCell(5).toString() ;
          String direction = xssfRow.getCell(6).toString();
          String remarks = xssfRow.getCell(7).toString();
        employee.setPid(pid);
        employee.setDepartment(department );
        employee.setName(name);
        employee.setSex(sex);
        employee.setEmployeeId(employeeId);
        employee.setGroupId(groupId);
        employee.setDirection(direction);
        employee.setRemarks(remarks);

        fis.close();
        return employee;
    }

}


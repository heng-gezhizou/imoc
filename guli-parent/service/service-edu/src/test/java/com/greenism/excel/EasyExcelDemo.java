package com.greenism.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelDemo {

    @Test
    public void writeDemo(){
        String fileName = "D:\\easy.xlsx";
        EasyExcel.write(fileName, DemoData.class).sheet("学生信息").doWrite(listDemo());
    }

    @Test
    public void readDemo(){
        String fileName = "D:\\easy.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();

    }

    public List<DemoData> listDemo(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }

}

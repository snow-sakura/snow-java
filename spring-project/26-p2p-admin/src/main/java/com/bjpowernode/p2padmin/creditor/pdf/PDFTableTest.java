package com.bjpowernode.p2padmin.creditor.pdf;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PDFTableTest {
    public static void main(String[] args) throws Exception {
        createTable();

    }



    public static String  createTable(){
        String tableFilePath=PDFTableTest.class.getClassLoader().getResource("").getFile()+new Date().getTime()+".pdf";
        Document document=new Document();
        try {
            FileOutputStream fos=new FileOutputStream(tableFilePath);
            PdfWriter writer= PdfWriter.getInstance(document,fos);
            document.open();
            PdfPTable pdfPTable=new PdfPTable(3);//参数指定表格的列宽
            List<PdfPRow> rowsList= pdfPTable.getRows();//获取表格所有的行集合对象，默认情况这个集合长度为0
            PdfPCell[] pdfPCell=new PdfPCell[3];//定义单元格数组，这个数组的长度应该与表格的列宽相同
            //定义字库对象否则无法显示中文数据
            BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font font=new Font(bfChinese);
            String headers[]={"姓名","年龄","性别"};//自定义一个字符串的数组，用于填充表格中的表头
            PdfPRow row=null;//定义一个行对象
            for(int i=0;i<pdfPCell.length;i++){//将表头中的数据设置到单元格数组中
                Phrase p=new Phrase(headers[i],font);//将数据添加到单元格中，并设置字库
                pdfPCell[i]=new PdfPCell();
                pdfPCell[i].setPhrase(p);
            }
            row=new PdfPRow(pdfPCell);//根据单元那个数组初始化行对象
            rowsList.add(row);//将行对象添加到行集合中
            String data[]={"张三","23","nan"};//表格中的具体数据，应该是从数据库中读取出来的一个List集合

            for(int i=1;i<=5;i++){//循环创建表格的行并添加到行集合中，这个循环应该根据数据库中的数据List集合决定循环多少次
                pdfPCell=new PdfPCell[3];
                for(int j=0;j<3;j++){
                    Phrase p=new Phrase(data[j]+j,font);//将数据添加到单元格中，并设置字库
                    pdfPCell[j]=new PdfPCell();
                    pdfPCell[j].setPhrase( p);
                }
               PdfPRow row2=new PdfPRow(pdfPCell);
                rowsList.add(row2);
            }
            document.add(pdfPTable);//将表格写入pdf中
            document.close();
            writer.close();

            return tableFilePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}

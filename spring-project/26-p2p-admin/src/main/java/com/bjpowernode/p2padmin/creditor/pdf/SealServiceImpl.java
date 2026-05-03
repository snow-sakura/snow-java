package com.bjpowernode.p2padmin.creditor.pdf;


import com.bjpowernode.p2padmin.creditor.model.CreditorRights;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;


import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 合同生成及签章的Service接口实现
 *
 * @author yanglijun
 *
 */

public class SealServiceImpl {

    private static final String LOAN_CONTRACT_TEMPLATE = "loan_contract.pdf";

    /**
     * 公布webservice方法，提供合同签章
     *
     * @param
     * @return
     */

    public static void main(String[] args) {
        CreditorRights creditorVO=new CreditorRights();
        creditorVO.setApplyNo("0001");
        creditorVO.setApplyPurpose("maifang");
        creditorVO.setAuditLoanMoney(21700.00);
        creditorVO.setAuditLoanTerm(1);
        creditorVO.setBorrowerIdcard("1234567890身份证");
        creditorVO.setBorrowerPresentAddress("北京");
        creditorVO.setBorrowerRealname("zhangfei");
        creditorVO.setBorrowerSex(1);
        creditorVO.setCollectFinishTime(new Date());
        pdfSeal(creditorVO);
    }

    public static String pdfSeal (CreditorRights creditorVO) {
        try {
            //获取合同模板文件所在路径
            String fileName = SealServiceImpl.class.getClassLoader().getResource(LOAN_CONTRACT_TEMPLATE).getFile();
            //读取借款合同模板文件并设置数据 iText
            PdfReader pdfReader = new PdfReader(fileName);
            //获取合同生成后存放路径
            String outFile_template = SealServiceImpl.class.getClassLoader().getResource("").getFile()+creditorVO.getApplyNo()+"temp.pdf";
            //文件输出流，输出合同文件到指定的路径
            FileOutputStream fos = new FileOutputStream(outFile_template);
            //获取一个向pdf模板写数据的对象
            PdfStamper stamper = new PdfStamper(pdfReader, fos);
            stamper.setFormFlattening(true);//如果为true 则生产后的pdf是不可编辑的
            //将数据一一设置到pdf合同模板中
            stamper.getAcroFields().setField("protocolNumber", creditorVO.getApplyNo());
            stamper.getAcroFields().setField("borrowerRealname", creditorVO.getBorrowerRealname());
            stamper.getAcroFields().setField("borrowerIdcard", creditorVO.getBorrowerIdcard());
            stamper.getAcroFields().setField("borrowerSex", creditorVO.getBorrowerSex()==1?"男":"女");
            stamper.getAcroFields().setField("borrowerPresentAddress", creditorVO.getBorrowerPresentAddress());
            stamper.getAcroFields().setField("applyPurpose", creditorVO.getApplyPurpose());
            stamper.getAcroFields().setField("returnWay", "等额本息");
            stamper.getAcroFields().setField("returnTerm", String.valueOf(creditorVO.getAuditLoanTerm()));
            stamper.getAcroFields().setField("returnDay", "15");











            //募资完成时间
            Date collectFinishTime = creditorVO.getCollectFinishTime();
            //计算还款开始时间
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(collectFinishTime);
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            Date startDate = startCalendar.getTime();
            //计算还款结束时间
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(collectFinishTime);
            endCalendar.add(Calendar.MONTH, creditorVO.getAuditLoanTerm());
            Date endDate = endCalendar.getTime();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            //设置还款开始和结束时间
            stamper.getAcroFields().setField("returnStartAndEndDate", sdf.format(startDate) + " ~ " + sdf.format(endDate));












            //借款金额（大写）
            double borrowAmount = creditorVO.getAuditLoanMoney().doubleValue();
            //人民币数字转换为大写汉字
            //去掉小树点并保留 角和分中的数据
            String strMoney =  ((int)borrowAmount*100)+"";
            String strNumber[]={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
            for (int ii=8; ii>=0; ii--) {
                String text = "jk_0"+ii;
                if (strMoney.length()>=(ii+1)) {
                    String resuleText = ""+strMoney.charAt(strMoney.length()-1-ii);
                    stamper.getAcroFields().setField(text,strNumber[Integer.parseInt(resuleText)] );
                } else {
                    stamper.getAcroFields().setField(text, "");
                }
            }














            //提取pdf中的表单输入域字段
            AcroFields form = stamper.getAcroFields();
            //通过域字段名获取所在页和坐标，左下角为起点
            int pageNo = form.getFieldPositions("Chapter").get(0).page;
            Rectangle signRect = form.getFieldPositions("Chapter").get(0).position;
            float x = signRect.getLeft();
            float y = signRect.getBottom();
//            //画公章，并得到公章图片的路径
//            String imgPath = MySealUtils.drawCircularChapter(Constants.CHAPTER_NAME, Constants.CHAPTER_COMPANY_NAME, Constants.CHAPTER_SOCIAL_NUMBER);
            //读图片
            String imagePath=SealServiceImpl.class.getClassLoader().getResource("cb0ba62b-e16c-4735-b91b-423da87a1ff9.bmp").getFile();
            Image image = Image.getInstance(imagePath);
            //获取操作的页面
            PdfContentByte under = stamper.getOverContent(pageNo);
            //根据域字段的大小缩放图片
            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            //设置位置
            image.setAbsolutePosition(x, y);
            //添加图片
            under.addImage(image);
            //关闭操作
//            PdfWriter writer= stamper.getWriter();
            stamper.close();
            pdfReader.close();
            fos.flush();
            fos.close();


            String tableFilePath=PDFTableTest.createTable();
            List<String> list=new ArrayList<String>();
            list.add(outFile_template);
            list.add(tableFilePath);

            String creditorPath=SealServiceImpl.class.getClassLoader().getResource("").getFile()+"creditor"+creditorVO.getApplyNo()+".pdf";
            PDFTest.mergePDFs(list,creditorPath,true);

            //删除使用后的pdf文件
            File file=new File(outFile_template);
            file.delete();
            file=new File(tableFilePath);
            file.delete();


            return   creditorPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
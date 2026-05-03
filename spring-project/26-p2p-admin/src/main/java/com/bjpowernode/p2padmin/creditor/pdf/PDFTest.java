package com.bjpowernode.p2padmin.creditor.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ClassName:PDFTest
 * Package:com.bjpowernode.p2padmin.creditor.pdf
 * Description:
 *
 * @date:2018/10/30 11:49
 * @author:bjpowernode.com
 */
public class PDFTest {
    /**
     * 合并PDF文件操作
     *
     * @param inputFileList
     * @param outputFile
     * @param paginate
     */
    public static void mergePDFs(List<String> inputFileList, String outputFile, boolean paginate) {
        //logger.info("pdf文件合并......");
        Document document = new Document();
        PdfWriter writer = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            List<PdfReader> readers = new ArrayList<PdfReader>();
            for (String filePath : inputFileList) {
                fis = new FileInputStream(filePath);
                PdfReader pdfReader = new PdfReader(fis);
                readers.add(pdfReader);
            }
            fos = new FileOutputStream(outputFile);
            writer = PdfWriter.getInstance(document, fos);

            document.open();
            //设置字体
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent();

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;

            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();
                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);
                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, String.valueOf(currentPageNumber), 290, 50, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (document.isOpen()) {
                    document.close();
                }
                if (null != fis) {
                    fis.close();
                }
                if (null != fos) {
                    fos.close();
                }
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

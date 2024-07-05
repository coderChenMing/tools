package com.learn.java8.pdf2word.v1;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

import java.io.FileOutputStream;

public class PDFHelper3 {
    public static void main(String[] args) {
        //pdf2doc("C:\\Users\\13381\\Desktop\\一升二数学衔接暑假作业.pdf");
        pdf2doc("D:\\test\\回执单.pdf");
    }


    //pdf转doc
    public static void pdf2doc(String pdfPath) {
        long old = System.currentTimeMillis();
        try {
            //新建一个word文档
            String wordPath = pdfPath.substring(0, pdfPath.lastIndexOf(".")) + ".docx";
            FileOutputStream os = new FileOutputStream(wordPath);
            //doc是将要被转化的word文档
            Document doc = new Document(pdfPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.DocX);
            os.close();
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Pdf 转 Word 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Pdf 转 Word 失败...");
            e.printStackTrace();
        }
    }
}

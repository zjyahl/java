package com.zjy.tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PDFtool {
	private static final String DEST = "/home/zjy/tmp/1.pdf";
    private static final String HTML_TEMPLATE = "/home/zjy/tmp/template.html";
	private static final String FONT = "/home/zjy/tmp/simsun.ttf";
	
	public static void genPdf() throws FileNotFoundException, DocumentException {
		Document doc = new Document();
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("/home/zjy/tmp/1.pdf"));
		doc.open();
		Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		String text = "11 dd大大pdf 嘎嘎";
		doc.add(new Paragraph(text, f1));
		System.out.println(text);
		doc.close();
		writer.close();
		System.out.println("over");
	}
	
	public static void htmlPDF() throws FileNotFoundException, IOException, DocumentException {
		Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTML_TEMPLATE), null, Charset.forName("UTF-8"), fontImp);
        document.close();
	}
	public static void htmlCssPDF() throws com.lowagie.text.DocumentException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(HTML_TEMPLATE)));
		String row = null;
		StringBuilder html = new StringBuilder();
		while ((row = reader.readLine()) != null){
		    html.append(row);
		}
		reader.close();
		 ITextRenderer render = new ITextRenderer();
	        ITextFontResolver fontResolver = render.getFontResolver();
	        fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
	        render.setDocumentFromString(html.toString());
	        render.getSharedContext().setBaseURL("file:///home/zjy/tmp/");
	        render.layout();
	        render.createPDF(new FileOutputStream(DEST));
	}
	

	public static void main(String[] args) throws DocumentException, FileNotFoundException, com.lowagie.text.DocumentException {
		try {
			htmlCssPDF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

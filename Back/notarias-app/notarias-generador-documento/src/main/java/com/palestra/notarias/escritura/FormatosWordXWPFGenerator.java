package com.palestra.notarias.escritura;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class FormatosWordXWPFGenerator {

	public static void main(String[] args) throws InvalidFormatException {
		String filePath = "//devpal//notaria//.config//prev//prueba word2.docx";

		replaceText("$actor", "SOLIDA ADMINISTRADORA DE PORTAFOLIOS, S.A DE C.V., SOFOM, ER, GRUPO FINANCIERO BANORTE.",
				filePath);
		// saveWord(filePath, doc);

	}

	private static void replaceText(String findText, String replaceText, String filePath) {
		try {
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(filePath));
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						if (text != null && text.contains(findText)) {
							text = text.replace(findText, replaceText);
							r.setText(text, 0);
						}
					}
				}
			}

			doc.write(new FileOutputStream("//devpal//notaria//.config//prev//nuevo.docx"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

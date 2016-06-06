package com.netmap.html;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.html.dom.HTMLBuilder;
import org.apache.html.dom.HTMLDocumentImpl;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.html.HTMLDocument;


public class BuildHtml {

	public static void main(String[] args) {
		try {
			HTMLBuilder builder = new HTMLBuilder();
			HTMLDocument document = new HTMLDocumentImpl();//builder.getHTMLDocument();
			DocumentFragment fragment = document.createDocumentFragment();
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer();
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			trans.transform(new DOMSource(fragment), result);
			String xmlString = sw.toString();
			System.out.println(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

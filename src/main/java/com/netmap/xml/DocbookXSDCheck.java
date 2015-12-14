package com.netmap.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class DocbookXSDCheck {

	public static void main(String[] args) throws SAXException, IOException {
		// 1. Lookup a factory for the W3C XML Schema language
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		// 2. Compile the schema.
		// Here the schema is loaded from a java.io.File, but you could use
		// a java.net.URL or a javax.xml.transform.Source instead.
		File schemaLocation = new File("/opt/xml/docbook/xsd/docbook.xsd");
		Schema schema = factory.newSchema(schemaLocation);
		// 3. Get a validator from the schema.
		Validator validator = schema.newValidator();
		// 4. Parse the document you want to check.
		Source source = new StreamSource(args[0]);
		// 5. Check the document
		try {
			validator.validate(source);
			System.out.println(args[0] + " is valid.");
		} catch (SAXException ex) {
			System.out.println(args[0] + " is not valid because ");
			System.out.println(ex.getMessage());
		}
	}
}
package com.netmap.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class DocbookRELAXNGCheck {
	public static void main(String[] args) throws SAXException, IOException {
		// 1. Specify you want a factory for RELAX NG
		SchemaFactory factory = SchemaFactory.newInstance("http://relaxng.org/ns/structure/1.0");
		// 2. Load the specific schema you want.
		// Here I load it from a java.io.File, but we could also use a
		// java.net.URL or a javax.xml.transform.Source
		File schemaLocation = new File("/opt/xml/docbook/rng/docbook.rng");

		// 3. Compile the schema.
		Schema schema = factory.newSchema(schemaLocation);

		// 4. Get a validator from the schema.
		Validator validator = schema.newValidator();

		// 5. Parse the document you want to check.
		String input = "file:///Users/elharo/Projects/workspace/CS905/build/Java_Course_Notes.xml";
		Source source = new StreamSource(input);
		// 6. Check the document
		try {
			validator.validate(source);
			System.out.println(input + " is valid.");
		} catch (SAXException ex) {
			System.out.println(input + " is not valid because ");
			System.out.println(ex.getMessage());
		}
	}
}
package net.omniblock.network.library.addons.xmladdon;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLReader {

	protected Reader reader = new StringReader("");

	protected String base = "<xml></xml>";
	protected XMLStreamReader xml;

	public XMLReader(String xml) {

		this.base = xml;

	}

	public Object get(String xpath, XPathConstants type) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;

		try {

			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(base)));

		} catch (SAXException | IOException | ParserConfigurationException e) {

			e.printStackTrace();

		}

		if (document == null || builder == null)
			return null;

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath path = xPathfactory.newXPath();
		XPathExpression expr = null;

		try {

			expr = path.compile(xpath);

		} catch (XPathExpressionException e) {

			e.printStackTrace();

		}

		if (expr == null)
			return null;

		try {

			return expr.evaluate(document);

		} catch (XPathExpressionException e) {

			e.printStackTrace();

		}

		return null;

	}

	public String get(String xpath) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;

		try {

			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(base)));

		} catch (SAXException | IOException | ParserConfigurationException e) {

			e.printStackTrace();

		}

		if (document == null || builder == null)
			return null;

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath path = xPathfactory.newXPath();
		XPathExpression expr = null;

		try {

			expr = path.compile(xpath);

		} catch (XPathExpressionException e) {

			e.printStackTrace();

		}

		if (expr == null)
			return null;

		try {

			return (String) expr.evaluate(document, XPathConstants.STRING);

		} catch (XPathExpressionException e) {

			e.printStackTrace();

		}

		return null;

	}

	public boolean contains(String tag) {

		if (base.contains(tag))
			return true;
		return false;

	}

}

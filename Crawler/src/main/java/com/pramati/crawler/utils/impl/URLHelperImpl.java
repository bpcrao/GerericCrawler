package com.pramati.crawler.utils.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import org.apache.log4j.Logger;

import com.pramati.crawler.utils.URLHelper;

/**
 * singleton class
 * 
 * purpose:- used to manipulate url's and getting url text and other URL information.
 * @author himanshuk
 *
 */
public class URLHelperImpl implements URLHelper{
	private final Logger log=Logger.getLogger(URLHelperImpl.class);

	public String getPageContentInTxtFrmt(URL url) throws Exception {
		return getPageContent(url);
	}

	private String getPageContent(URL url) throws Exception {
		BufferedInputStream reader = null;
		Writer writer = null;
		try {
			reader = new BufferedInputStream(url.openStream());
			writer = new StringWriter();
			for (int c = reader.read(); c != -1; c = reader.read()) {
				writer.write(c);
			}
		} catch (IOException e) {
			log.error("PROBLEM_IN_URL_READING " + url.toString());
			throw e;
		}
		return writer.toString();
	}

}

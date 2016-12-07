/**
 * 
 */
package com.sainsburys.web.scrape;

import java.io.IOException;

import org.json.simple.JSONObject;

/**
 * @author Jagannatha
 * 
 *         This is interface provides a method to scrape webpage and return
 *         specific data for a given input string URL.
 */
public interface WebScraper {

	/*
	 * This method parse the input web page and return parsed data within
	 * JSONObject.
	 * 
	 * @param url String
	 * 
	 * @return JSONObject
	 * 
	 * @exception IOException
	 */

	public JSONObject scrapeWebPage(String strURL) throws IOException;

}

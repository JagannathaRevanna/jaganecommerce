package com.sainsburys.web.scrape;

import java.io.IOException;
import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import com.sainsburys.web.scrape.WebScraper;
import com.sainsburys.web.scrape.WebScraperImpl;

/**
 * 
 * @author Jagannatha 
 * This test class used to test WebScraper implementation.
 *
 */

public class WebScraperTest {

	private static final Log log = LogFactory.getLog(WebScraperImpl.class);

	/**
	 * This test case is used for testing null input and it is expected to
	 * return null object.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testForNullInput() throws IOException {
		WebScraper webScraper = new WebScraperImpl();
		JSONObject jsonObj = webScraper.scrapeWebPage(null);
		log.info("outputObj:    " + jsonObj);
		assertNull(jsonObj);
	}

	/**
	 * This test case is used for testing empty string input and it is expected
	 * to return null object.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testForEmptyInput() throws IOException {
		WebScraper webScraper = new WebScraperImpl();
		String strURL = "";
		JSONObject jsonObj = webScraper.scrapeWebPage("");
		log.info("outputObj:    " + jsonObj);
		assertNull(jsonObj);
	}

	/**
	 * This test case is used for testing invalid input URL and it is expected
	 * to throw IOException.
	 * 
	 * @throws IOException
	 */

	@Test(expected = IOException.class)
	public void testForInvalidURL() throws IOException {
		WebScraper webScraper = new WebScraperImpl();
		String strURL = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/invalidpage.html";
		JSONObject jsonObj = webScraper.scrapeWebPage(strURL);
	}

	/**
	 * This test case is used for testing valid URL and result should be
	 * successful that return non null JSONObject containing JSONArray.
	 * 
	 * @throws IOException
	 */

	@Test
	public void testForValidURL() throws IOException {
		WebScraper webScraper = new WebScraperImpl();
		String strURL = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
		JSONObject jsonObj = webScraper.scrapeWebPage(strURL);
		JSONArray jsonArray = null;
		String total = null;
		if (jsonObj != null){
			jsonArray = (JSONArray) jsonObj.get("results");
			total = (String) jsonObj.get("total");
		} 
		log.info("total:    " + total);
		log.info("jsonArray:    " + jsonArray.size());
		assertTrue(jsonArray.size() > 0);
	}

}

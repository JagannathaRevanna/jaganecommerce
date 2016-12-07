package com.sainsburys.web.scrape;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Jagannatha 
 * 
 * This class provides implementation for WebScraper
 * interface. This implementation class uses third party scraping tool
 * Jsoup to parse html page and return specific data as required by the
 * client.
 */

public class WebScraperImpl implements WebScraper {

	private static final Log log = LogFactory.getLog(WebScraperImpl.class);

	/**
	 * This method accepts web URL as a string input and returns a JSON array of
	 * data for all the products on the page. The returned JSONObjects are 
	 * populated with title, unit-price, page size and description info.
	 * 
	 * @param strURL String
	 * @return JSONObject
	 * @exception IOException
	 */

	@SuppressWarnings("unchecked")
	public JSONObject scrapeWebPage(String strURL) throws IOException {
		log.debug("Entering Method... WebScraperImpl.scrapeWebPage()");
		Document doc;
		JSONObject outputObj = new JSONObject();
		BigDecimal totalPrice = new BigDecimal("0.00");

		if ((strURL == null) || (strURL.equals(""))) {
			return null;
		}
		JSONArray jsonArray = new JSONArray();

		// fetch document over HTTP
		doc = Jsoup.connect(strURL).get();
		log.debug("document string  : " + doc.toString());
		String title = doc.title();
		log.info("title is: " + title);

		// Get list of product elements
		Elements products = doc.select("div.product");

		for (Element product : products) {
			Element productName = product.select("div.productinfo h3 a").first();
			log.debug("product text : " + productName.text());

			String strLink = productName.attr("href");
			log.debug("\nlink : " + strLink);

			// fetch document for each product link
			Document productLinkDoc = Jsoup.connect(strLink).get();

			String strDescription = productLinkDoc.select("meta[name=description]").get(0).attr("content");
			log.debug("product decription : " + strDescription);

			String strSize = productLinkDoc.toString() != null ? productLinkDoc.toString().length() / 1024 + "kb"
					: "0kb";
			log.debug("product link size : " + strSize);

			Element price = product.select("div.pricing p.pricePerUnit").first();
			String strPrice = price.text() != null ? price.text().split("/")[0] : "0.00";
			strPrice = strPrice.substring(6);
			log.debug("product price : " + strPrice);

			totalPrice = totalPrice.add(new BigDecimal(strPrice));

			// constructing and populating json arry
			JSONObject obj = new JSONObject();
			obj.put("title", productName.text());
			obj.put("size", strSize);
			obj.put("unit_price", strPrice);
			obj.put("description", strDescription);
			jsonArray.add(obj);
		} // end of products loop

		// populating output object
		outputObj.put("results", jsonArray);
		outputObj.put("total", totalPrice.toString());
		log.info("outputObj:    " + outputObj);

		log.debug("Exiting  Method... WebScraperImpl.scrapeWebPage()");
		return outputObj;
	} // end of scrapeWebPage method
}

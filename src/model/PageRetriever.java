package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
	- The PageRetriever class retrieves web pages and stores them for later analysis by the PageParser class.
	- Only URLs specifying HTML or text documents should be retrieved.
	- Each unique URL should be retrieved only once.
	- This class will need to utilize to a collection of URLs waiting to be retrieved,
	- as well as a repository in which to store the documents' contents.
*/

public class PageRetriever implements Runnable {
	//http://jsoup.org/
	public static final int PARSE_LIMIT = 15;
	private String my_url;
	
	public PageRetriever() {
		my_url = "NULL";
	}
	
	/**
	 * 
	 * @param beginURL The initial URL to begin the crawl represented as a String.
	 * */
	public PageRetriever(String beginURL) {
		my_url = beginURL;
	}
	
	/**
	 * Retrieves a single URL and returns a single html document
	 * 
	 * @param url THe page's url
	 * @return The html document form of the website's url 
	 * */
	public Document retrieveDocument() {
		try {
			return Jsoup.connect(my_url).get();
		} catch(IOException e) {
			System.out.println("Could not convert the provided url link to a document: " + my_url);
		}
		return null;
	}
	
	public void setURL(final String the_url) {
		my_url = the_url;
	}
	
	@Override
	public void run() {
		retrieveDocument();
	}
	
}

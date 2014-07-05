package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import model.KeyWord;
import model.PageParser;
import model.PageRetriever;

import org.jsoup.nodes.Document;

public class WebCrawler {

	/**
	 * Max number of algorithm loops to enforce the limit of # of web pages to crawl.
	 * */
	private static int CRAWL_LIMIT = 15;

	/**
	 * A queue that keeps of all the URLs to crawl.
	 * */
	private final Queue<String> my_urls;

	/**
	 * List of keywords to search for every crawl
	 * */
	private final Set<KeyWord> my_words;

	/**
	 * A set containing all the parsed web sites. 
	 * */
	private Set<String> websitesCrawled;

	/**
	 * An object responsible for retrieving the HTML Documents of each URL found in the crawl
	 * */
	private PageRetriever my_retriever;

	/**
	 * An object responsible for parsing relative links within each URL, that matches any of the search keywords
	 * */
	private PageParser my_parser;
	
	/**
	 * Constructor set up. Initializes all fields.
	 * 
	 * @param the_url: Initial URL to start the crawl.
	 * @param the_words : Keyword to search.
	 */
	public WebCrawler(final String the_url, final Set<KeyWord> the_words) {
		my_urls = new PriorityQueue<String>();
		my_urls.add(the_url);
		
		my_words = new HashSet<KeyWord>();
		my_words.addAll(the_words);

		my_retriever = new PageRetriever();
		my_parser = new PageParser();
		
		websitesCrawled = new HashSet<String>();
		websitesCrawled.add(the_url);
	}
	

	/**
	 * System start up. Begins the algorithm for Webcrawler.
	 */
	public void start(){
		//The removed url from the priority queue 'my_urls'.
		String removedUrl;

		//Queue of HTML documents that corresponds to each url in the queue 'my_urls'
		Queue<Document> docs;
		
		boolean foundNewURL;
		int prevSize;
		
		//algorithm below closely follows as shown on google docs
		//still needs some changes
		do {
			foundNewURL = false;
			while(!my_urls.isEmpty() && (websitesCrawled.size() < CRAWL_LIMIT)) {
				removedUrl = my_urls.remove();
				my_retriever.addURL(removedUrl);

				docs = my_retriever.retrieveDocuments();
				my_parser.addDocument(docs);

				for (String url : my_parser.parseAllDocuments(my_words)) {
					prevSize = websitesCrawled.size();
					websitesCrawled.add(url);
					foundNewURL = (websitesCrawled.size() != prevSize); 
					if (foundNewURL) {
						my_urls.add(url);
					}
				}
				my_parser.deleteContents();
				System.out.println(websitesCrawled.size());
			}
		} while (foundNewURL);
		
	}
	
	public static void main(String[] args) {
		String beginURL = "http://jsoup.org/";
		Set<KeyWord> searchKeyWords = new HashSet<KeyWord>();
		searchKeyWords.add(new KeyWord("bugs"));
		searchKeyWords.add(new KeyWord("Discussion"));
		searchKeyWords.add(new KeyWord("Stack Overflow"));
		
		WebCrawler crawler = new WebCrawler(beginURL, searchKeyWords);
		crawler.start();
		System.out.println(crawler.websitesCrawled);
	}
	
}

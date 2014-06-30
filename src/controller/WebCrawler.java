package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import model.KeyWord;
import model.PageParser;
import model.PageRetriever;

import org.jsoup.nodes.Document;

public class WebCrawler {

	/**
	 * Max number of algorithm loops to enforce the limit of # of web pages to crawl.
	 * */
	private static int PAGES_PARSED_LIMIT = 15;

	/**
	 * A queue that keeps of all the URLs to crawl.
	 * */
	private final Queue<String> my_urls;

	/**
	 * List of keywords to search for every crawl
	 * */
	private final List<KeyWord> my_words;

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
	public WebCrawler(final String the_url, final List<KeyWord> the_words) {
		my_urls = new PriorityQueue<String>();
		my_urls.add(the_url);
		
		//shallow copy, copies size and pointer
		my_words = new ArrayList<KeyWord>(the_words);

		//deep copy, copies actual values on separate memory
		Collections.copy(my_words, the_words);

		my_retriever = new PageRetriever();
		my_parser = new PageParser();
	}
	

	/**
	 * System start up. Begins the algorithm for Webcrawler.
	 */
	public void start(){
		//The removed url from the priority queue 'my_urls'.
		String removedUrl;

		//Queue of HTML documents that corresponds to each url in the queue 'my_urls'
		Queue<Document> docs;
		
		
		//URL queue storage for relative links (links that matches keywords in current url 'removedUrl')
		Queue<String> urlsToParse = new PriorityQueue<String>();
		
		do {
			while(!my_urls.isEmpty()) {
				removedUrl = my_urls.remove();
				my_retriever.addURL(removedUrl);

				docs = my_retriever.retrieveDocuments();
				my_parser.addDocument(docs);

				Queue<String> temp = my_parser.parseAllDocuments(my_words);
				while (!temp.isEmpty()) {
					urlsToParse.add(temp.remove());
				}
			}
			
		} while (!urlsToParse.isEmpty());
	}
	
}

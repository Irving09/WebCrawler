package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import model.KeyWord;
import model.PageParser;
import model.PageRetriever;

import org.jsoup.nodes.Document;

public class WebCrawler {
	private static int PAGES_PARSED_LIMIT = 15; //Max number of the algorithm loops to enforce the limit of # of webpage crawl.
	private final Queue<String> my_url; //A queue that keeps allt he URLs.
	private final List<KeyWord> my_words; //List of searching key words in the URL.
	private PageRetriever my_retriever;
	private PageParser my_parser;

	/**
	 * Set up.
	 * @param the_url: Initial URL to start the crawl.
	 * @param the_words : Keyword to search.
	 */
	public WebCrawler(final String the_url, final List<KeyWord> the_words) {
		my_url = new PriorityQueue<String>(); //A queue that keeps all the URLs.
		my_url.add(the_url);

		my_words = new ArrayList<KeyWord>(the_words); //this is a shallow copy.

		Collections.copy(my_words, the_words); // return an actual deep copy of the_words.

		my_parser = new PageParser();
	}
	

	/**
	 * Start the program to crawl.
	 */
	public void start(){
		Queue<String> urlsToParse = new PriorityQueue<String>();
		String removedUrl;
		
		Queue<Document> doc;//grab the queue from page retriever

		do {
			while(!my_url.isEmpty()) {
				removedUrl = my_url.remove();
				my_retriever = new PageRetriever(removedUrl);
				doc = my_retriever.retrieveDocuments();
				//parse the documents: 
				my_parser.addDocument(doc);
				my_parser.parseAllDocuments();
			}
			
		}
		while (!urlsToParse.isEmpty());
		
		

		
		


	}
}

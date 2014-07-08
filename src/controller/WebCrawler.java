package controller;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import model.KeyWord;
import model.PageAnalyzer;
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

	private PageAnalyzer my_analyzer;
	
	public WebCrawler() {
		my_urls = new PriorityQueue<String>();
		my_words = new HashSet<KeyWord>();
		my_retriever = new PageRetriever();
		my_parser = new PageParser();
		websitesCrawled = new HashSet<String>();
		my_analyzer = new PageAnalyzer();
	}
	
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
		
		my_analyzer = new PageAnalyzer();
		
		websitesCrawled = new HashSet<String>();
		websitesCrawled.add(the_url);
	}
	
	public void setBeginURL(final String the_url) {
		my_urls.add(the_url);
		websitesCrawled.add(the_url);
	}
	
	public void setSearchKeyWords(final Set<KeyWord> the_words) {
		my_words.addAll(the_words);
	}
	
	public Queue<String> getURLs() {
		return my_urls;
	}
	
	public Set<KeyWord> getKeyWords() {
		return my_words;
	}
	
	public Set<String> getWebSitesCrawled() {
		return websitesCrawled;
	}
	
	/**
	 * System start up. Begins the algorithm for Webcrawler.
	 */
	public void start(){
		System.err.println(my_words);
//		clearContents();
		
		//The removed url from the priority queue 'my_urls'.
		String removedUrl;

		//HTML document that corresponds to the removedUrl
		Document page;
		
		Queue<String> relativeURLs;
		Queue<String> urlsToParse = new PriorityQueue<String>();
		
		int prevSize;
		
		//check the queue of URL strings, and crawl limit
		while(!my_urls.isEmpty() && (websitesCrawled.size() < CRAWL_LIMIT)) {
			
			//remove the url that is at the top of the queue
			removedUrl = my_urls.remove();

			//convert the url to a document (i.e. page)
			page = my_retriever.retrieveDocument(removedUrl);

			/*relativeURLs represent ALL clickable links that matches search keywords
						residing in a single web page (i.e. 'page')*/
			//parse the document page, get all related links and save into the variable 'relativeURLs'
			if (page != null) {
				relativeURLs = my_parser.parseDocument(page, my_words);

				//analyze the page and compare search key words (i.e. 'my_words')
					//NOTE: analyzePage method will update the total hits of every keyword 
				my_analyzer.analyzePage(page, my_words);

				//add all the related urls from analyzer to urls-to-be-parsed
				while (!relativeURLs.isEmpty()) 
					urlsToParse.add(relativeURLs.remove());
			}

			//for loop iterates all urlsToBeParsed
			for (String url : urlsToParse) {
				prevSize = websitesCrawled.size();
				websitesCrawled.add(url);
				//check whether the website url has been crawled before
				if (websitesCrawled.size() != prevSize) {
					//only add urls to be parsed if they have not yet been visited
					my_urls.add(url);
				}
			}
//			my_parser.deleteContents();
			urlsToParse.clear();
//			my_analyzer.deleteContents();
			System.err.println(websitesCrawled.size());
		}

		System.err.println("ended loop");
		System.out.println();
		System.out.println("Word\t\t\tTotal Hits");
		for (KeyWord w : my_words) {
			System.out.println(w.string() + "\t\t\t" + w.total());
		}
//		System.err.println(my_words);
	}
	
	public void clearContents() {
		my_urls.clear();
		my_words.clear();

		my_retriever.deleteContents();
		my_parser.deleteContents();
		my_analyzer.deleteContents();

		websitesCrawled.clear();
	}
	
	public static void main(String[] args) {
		String beginURL = "http://jsoup.org/";
		Set<KeyWord> searchKeyWords = new HashSet<KeyWord>();
		searchKeyWords.add(new KeyWord("bugs"));
		searchKeyWords.add(new KeyWord("Discussion"));
		searchKeyWords.add(new KeyWord("Download"));
		searchKeyWords.add(new KeyWord("Stack Overflow"));
		
		WebCrawler crawler = new WebCrawler(beginURL, searchKeyWords);
		System.out.println("searchKeys: " + crawler.getKeyWords());
		System.out.println("beginURL: " + crawler.getURLs());
		crawler.start();
		System.out.println(crawler.websitesCrawled);
		System.out.println(crawler.websitesCrawled.size());
		
		System.out.println("searchKeys: " + crawler.getKeyWords());
		System.out.println("beginURL: " + crawler.getURLs());
		crawler.start();
		System.out.println(crawler.websitesCrawled);
		System.out.println(crawler.websitesCrawled.size());
	}
	
}

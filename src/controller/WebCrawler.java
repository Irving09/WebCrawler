package controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import javax.swing.JOptionPane;

import model.KeyWord;
import model.MultiTaskPage;
import model.PageAnalyzer;
import model.PageParser;
import model.PageRetriever;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebCrawler {

	/**
	 * Max number of algorithm loops to enforce the limit of # of web pages to crawl.
	 * */
	public static final int CRAWL_LIMIT = 20;
	private static final int THREAD_NUM = 10;

	/*For multi thread*/
	private MultiTaskPage[] multiTaskPage = new MultiTaskPage[THREAD_NUM];

	/**
	 * A queue that keeps of all the URLs to crawl.
	 * */
	private final Queue<String> urlsToBeParsed;

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
	 * An object responsible for parsing relative links that matches any of the search keywords for a web page
	 * */
	private PageParser my_parser;

	/**
	 * An object responsible for analyzing the total hits of certain keyword in an html document
	 * */
	private PageAnalyzer my_analyzer;

	/**
	 * Total nano seconds the crawl algorithm takes to parse webpages.
	 * */
	private long totalNanoTime;
	
	/**
	 * A no argument constructor that initializes all the objects and data structures in the WebCrawler class
	 * */
	public WebCrawler() {
		urlsToBeParsed = new PriorityQueue<String>();
		my_words = new HashSet<KeyWord>();
		my_retriever = new PageRetriever();
		my_parser = new PageParser();
		websitesCrawled = new HashSet<String>();
		my_analyzer = new PageAnalyzer();
		totalNanoTime = 0;
	}

	/*Not needed in the actual UI, but is used for testing*/
	/**
	 * Constructor set up. Initializes all fields.
	 * 
	 * @param the_url: Initial URL to start the crawl.
	 * @param the_words : Keyword to search.
	 */
	public WebCrawler(final String the_url, final Set<KeyWord> the_words) {
		urlsToBeParsed = new PriorityQueue<String>();
		urlsToBeParsed.add(the_url);
		
		my_words = new HashSet<KeyWord>();
		my_words.addAll(the_words);

		my_retriever = new PageRetriever(the_url);
		my_parser = new PageParser();
		
		my_analyzer = new PageAnalyzer();
		
		websitesCrawled = new HashSet<String>();
		websitesCrawled.add(the_url);
	}

	/**
	 * Assigns the initial url where the crawl starts.
	 * 
	 * @param the_url The initial url.
	 * */
	public void setBeginURL(final String the_url) {
		urlsToBeParsed.add(the_url);
		websitesCrawled.add(the_url);
	}

	/**
	 * Checks whether a url is valid or not.
	 * 
	 * @param the_url The initial url to begin the crawl
	 * @return Returns true if the parameter is a valid url for Jsoup's connection request
	 * */
	public boolean isValidURL(final String the_url) {
		try {
			Jsoup.connect(the_url).get();
			return true;
		} catch (Exception e) {
			//will catch the invalid url here if connect.get cannot make a connection
			JOptionPane.showMessageDialog(null, "JSoup failed to connect to the url: " + the_url, "MultiThread alert", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public void addKeyWord(final KeyWord new_word) {
		my_words.add(new_word);
	}

	/**
	 * Sets the specified set of KeyWords for the WebCrawler to search for
	 * 
	 * @param the_words Set of KeyWord
	 * */
	public void setSearchKeyWords(final Set<KeyWord> the_words) {
		my_words.addAll(the_words);
	}

	/**
	 * Gets all the urls to be parsed.
	 * 
	 *  @return A queue of urls, with each url as a String.
	 * */
	public Queue<String> getURLs() {
		return urlsToBeParsed;
	}

	/**
	 * Returns the set of keywords to search for during the crawl.
	 * 
	 * @return The set of keywords to search for.
	 * */
	public Set<KeyWord> getKeyWords() {
		return my_words;
	}
	
	public Set<String> getWebSitesCrawled() {
		return websitesCrawled;
	}
	
	public void startMultiThread() {
		init();
		startThreads();
	}
	
	private final void init() {
		for (int threadNum = 0; threadNum < THREAD_NUM; threadNum++) {
			multiTaskPage[threadNum] = new MultiTaskPage(urlsToBeParsed, my_words, websitesCrawled);
		}
	}
	
	private final void startThreads() {
		for (int threadNum = 0; threadNum < THREAD_NUM; threadNum++) {
			new Thread(multiTaskPage[threadNum]).start();
		}
	}
	
	/**
	 * System start up. Begins the algorithm for Webcrawler.
	 */
	public void startSingleThread(){
		//The removed url from the priority queue 'my_urls'.
		String removedUrl;

		//HTML document that corresponds to the removedUrl
		Document page;

		//relativeURLs represent ALL clickaable links within a page
		Queue<String> relativeURLs;

		//previous size of the websitesCrawled before crawling a website
		int prevSize;
		
		long start = System.nanoTime();
		
		//check the queue of URL strings, and crawl limit
		while(!urlsToBeParsed.isEmpty() && (websitesCrawled.size() < CRAWL_LIMIT)) {
			
			//remove the url that is at the top of the queue
			removedUrl = urlsToBeParsed.remove();
			my_retriever.setURL(removedUrl);
			//convert the url to a document (i.e. page)
			page = my_retriever.retrieveDocument();

			/*relativeURLs represent ALL clickable links that matches search keywords
						residing in a single web page (i.e. 'page')*/
			//parse the document page, get all related links and save into the variable 'relativeURLs'
			if (page != null) {
				relativeURLs = my_parser.parseDocument(page, my_words);

				//analyze the page and compare search key words (i.e. 'my_words')
					//NOTE: analyzePage method will update the total hits of every keyword 
				my_analyzer.analyzePage(page, my_words);

				String url;
				
				//iterates all urlsToBeParsed
				while (!relativeURLs.isEmpty()) {
					//get the url from top of the relativeURL queue
					url = relativeURLs.remove();
					prevSize = websitesCrawled.size();
					websitesCrawled.add(url);
					
					//check whether the website url has been crawled before
					if (websitesCrawled.size() != prevSize) {
						//add all the related urls parsed by the parser to urls-to-be-parsed
						urlsToBeParsed.add(url);
					}
				}
			}
			
			System.err.println(websitesCrawled.size());
		}
		
		long end = System.nanoTime();
		totalNanoTime = end - start;

		System.err.println("ended loop");
		System.out.println();
		System.out.println("Word\t\t\tTotal Hits");
		for (KeyWord w : my_words) {
			System.out.println(w.toString() + "\t\t\t" + w.totalHits());
		}
		//System.err.println("totalTime: " + totalTime());
		System.err.println("totalNanoTime: " + totalNanoTime());
	}

	/*Method NOT used*/
	public void clearContents() {
		urlsToBeParsed.clear();
		for (KeyWord word : my_words)
			word.clearHits();
		
//		my_retriever.deleteContents();
		my_parser.deleteContents();
		my_analyzer.deleteContents();

		websitesCrawled.clear();
	}
	
	public void removeKeyWord(KeyWord word) {
		my_words.remove(word);
	}
	
	public double getAvgWordPerPage() {
		int totalHits = 0;
		for (KeyWord word : my_words)
			totalHits += word.totalHits();
		return totalHits / websitesCrawled.size();
	}
	
	public double getAvgURLsPerPage() {
		return my_parser.totalURLSize() / websitesCrawled.size();
	}
	
	public long totalNanoTime() {
		return totalNanoTime;
	}

	public double totalTime() {
		return (double) totalNanoTime / 1000000000.0;
	}
	
	public long avgParseNanoTime() {
		return totalNanoTime / websitesCrawled.size();
	}
	
	public int getWordTotalHits(final KeyWord word) {
		Iterator<KeyWord> itr = my_words.iterator();
		while (itr.hasNext()) {
			KeyWord temp = itr.next();
			if (temp.equals(word))
				return temp.totalHits();
		}
		throw new IllegalArgumentException("Word " + word + " doesnt exist in the set of search keywords");
	}
	
	public int getWordTotalHits(String word) {
		Iterator<KeyWord> itr = my_words.iterator();
		while (itr.hasNext()) {
			KeyWord temp = itr.next();
			if (temp.toString().toLowerCase().equals(word.toLowerCase()))
				return temp.totalHits();
		}
		throw new IllegalArgumentException("Word " + word + " doesnt exist in the set of search keywords");
	}
	
	public double getWordAvgHits(String word) {
		return (double) getWordTotalHits(word) / (double) websitesCrawled.size();
	}
	
	public static void main(String[] args) {
		String beginURL = "http://jsoup.org/";
		Set<KeyWord> searchKeyWords = new HashSet<KeyWord>();
		searchKeyWords.add(new KeyWord("bugs"));
		searchKeyWords.add(new KeyWord("Discussion"));
		searchKeyWords.add(new KeyWord("Download"));
		searchKeyWords.add(new KeyWord("Stack Overflow"));
		
//		
		WebCrawler crawler = new WebCrawler(beginURL, searchKeyWords);
		System.out.println("searchKeys: " + crawler.getKeyWords());
		System.out.println("beginURL: " + crawler.getURLs());
		crawler.startSingleThread();
		System.out.println("totalNanoTime: " + crawler.totalNanoTime());
		System.out.println("totalTime: " + crawler.totalTime());
		System.out.println("avgHits/page: " + crawler.getAvgWordPerPage());
		System.out.println(crawler.websitesCrawled);
		System.out.println("total hits: (bugs) \t" + crawler.getWordTotalHits("bugs"));
		System.out.println(crawler.websitesCrawled.size());
	}
	
}

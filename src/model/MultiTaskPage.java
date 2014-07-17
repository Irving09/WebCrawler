package model;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.jsoup.nodes.Document;

import controller.WebCrawler;

public class MultiTaskPage implements Runnable {
	private PageRetriever myRetriever;
	private PageParser myParser;
	private PageAnalyzer myAnalyzer;
	private Queue<String> urlsToBeParsed;
	private Set<KeyWord> searchKeyWords;
	private Set<String> websitesCrawled;

	public MultiTaskPage(Queue<String> the_urlsToBeParsed, Set<KeyWord> the_searchKeys,
			Set<String> the_websitesCrawled) {
		myRetriever = new PageRetriever();
		myParser = new PageParser();
		myAnalyzer = new PageAnalyzer();
		urlsToBeParsed = the_urlsToBeParsed;
		searchKeyWords = the_searchKeys;
		websitesCrawled = the_websitesCrawled;
	}
	public PageRetriever getRetriever() {
		return myRetriever;
	}
	public PageParser getParser() {
		return myParser;
	}
	public PageAnalyzer getAnalyzer() {
		return myAnalyzer;
	}
	public Queue<String> getUrlsToBeParsed() {
		return urlsToBeParsed;
	}
	public Set<KeyWord> getSearchKeyWords() {
		return searchKeyWords;
	}
	public Set<String> getWebSitesCrawled() {
		return websitesCrawled;
	}

	@Override
	public void run() {
		while (!urlsToBeParsed.isEmpty() && (websitesCrawled.size() < WebCrawler.CRAWL_LIMIT)) {
			synchronized(this) {
				myRetriever.setURL(urlsToBeParsed.remove());
			}

			Document page = myRetriever.retrieveDocument();
			int prevSize;
			if (page != null) {
				synchronized(this) {
					myAnalyzer.analyzePage(page, searchKeyWords);
				}
				Queue<String> relativeURLs = myParser.parseDocument(page, searchKeyWords);
				String url;
				//iterates all urlsToBeParsed
				while (!relativeURLs.isEmpty()) {
					//get the url from top of the relativeURL queue
					url = relativeURLs.remove();
					synchronized(this) { 
						prevSize = websitesCrawled.size();
						websitesCrawled.add(url);

						//check whether the website url has been crawled before
						if (websitesCrawled.size() != prevSize) {
							//add all the related urls parsed by the parser to urls-to-be-parsed
							urlsToBeParsed.add(url);
						}
					}
				}
			}
		}
	}

}

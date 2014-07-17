package model;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.jsoup.nodes.Document;

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
	
	@Override
	public void run() {
		synchronized(this) {
			myRetriever.setURL(urlsToBeParsed.remove());
		}
		
		Document page = myRetriever.retrieveDocument();
		int prevSize;
		if (page != null) {
			myAnalyzer.analyzePage(page, searchKeyWords);
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

package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageRetriever {
	public static final int PARSE_LIMIT = 15;

	//http://jsoup.org/cookbook/extracting-data/example-list-links
	public static void main(final String... args) {
//		System.out.println(retrieveLinks("http://jsoup.org/cookbook/extracting-data/example-list-links", "Bugs"));
//		String test = "abcdefg";
//		String test2 = "efg";
//		System.out.println(test.contains(test2));
		
		
	}
	
	/**
	 * 
	 * @param beginURL The initial URL to begin the crawl represented as a String.
	 * @param wordMap Serves as a mapping of String objects to KeyWord objects (i.e. search key words). 
	 * */
	public PageRetriever(String beginURL, List<KeyWord> searchKeyWords) {
		String currURL = beginURL;
		Queue<String> urlQueue = new PriorityQueue<String>();
		int parsed = 0;
		while (parsed <= PARSE_LIMIT) {
			//keep retrieving links, looping through each keyword
			//for (every word 'w' in the list searchKeyWords)
			for (KeyWord w : searchKeyWords) {
				for (String link : retrieveLinks(currURL, w.keyWord())) {
					urlQueue.add(link);
				}
			}
			currURL = urlQueue.remove();
			parsed++;
		}
	}
	
	public static List<String> retrieveLinks(String the_url, String searchKey) {
		Document doc;
		try {
			//connects to the url specified and converts it to Document full of Strings text
			doc = Jsoup.connect(the_url).get();

			//stores all href (links) elements into an array of ELements
			Elements links = doc.select("a[href]");
			
			List<String> result = new ArrayList<String>();

			//Note: elements from an html page with attributes of href, (links to other pages)
			//Iterates all links within the page
			for (Element e : links) {
				//e.text() is the hyperlinked text in string
				if (e.text().toLowerCase().contains(searchKey.toLowerCase())) {
					//e.attr("abs:href") gives the URL of the link in String
					result.add(e.attr("abs:href"));
				}
			}
			return result;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//For single thread, how do we know which link to prioritize?
	//Or do we care which links to crawl first?
	
	//For single thread,
	//First get all the links from that page and store any links that matches the keyword
	
}

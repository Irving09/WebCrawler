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

public class PageRetriever {
	//http://jsoup.org/cookbook/extracting-data/example-list-links
	public static final int PARSE_LIMIT = 15;

	/**
	 * The queue of urls for the webcrawler to crawl
	 * */
	private Queue<String> urlQueue;
	
	public PageRetriever() {
		urlQueue = new PriorityQueue<String>();
	}

	/**
	 * 
	 * @param beginURL The initial URL to begin the crawl represented as a String.
	 * @param wordMap Serves as a mapping of String objects to KeyWord objects (i.e. search key words). 
	 * */
	public PageRetriever(String beginURL) {
		urlQueue = new PriorityQueue<String>();
		urlQueue.add(beginURL);
	}

	/**
	 * Retrieves all documents associated to the queue of URLs 'urlQueue'.
	 * */
	public Queue<Document> retrieveDocuments() {
		Queue<Document> documents = new PriorityQueue<Document>();
		String url;
		while (!urlQueue.isEmpty()) {
			url = urlQueue.remove();
			try {
				documents.add(Jsoup.connect(url).get());
			} catch (IOException e) {
				System.out.println("Could not convert the provided url link to a document: " + url);
			}
		}
		return documents;
	}

	/**
	 * Adds a single url to the queue.
	 * */
	public void addURL(String url) {
		urlQueue.add(url);
	}

	/**
	 * Adds a queue of urls to the queue
	 * */
	public void addURLqueue(Queue<String> urlsToParse) {
		Iterator<String> itr = urlsToParse.iterator();
		while (itr.hasNext())
			urlQueue.add(itr.next());
	}

	/**
	 * Accessor to the urlQueue
	 * */
	public Queue<String> getURLQueue() {
		return urlQueue;
	}
	
}

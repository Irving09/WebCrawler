package model;

import java.io.IOException;
import java.util.ArrayList;
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
	private Queue<String> urlQueue;

	/**
	 * 
	 * @param beginURL The initial URL to begin the crawl represented as a String.
	 * @param wordMap Serves as a mapping of String objects to KeyWord objects (i.e. search key words). 
	 * */
	public PageRetriever(String beginURL) {
		urlQueue = new PriorityQueue<String>();
		urlQueue.add(beginURL);
	}
	
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
	
	public Queue<String> getURLQueue() {
		return urlQueue;
	}
	
	public static void main(final String... args) {
//		List<KeyWord> words = new ArrayList<KeyWord>();
//		words.add(new KeyWord("Bugs"));
//		words.add(new KeyWord("Document"));
//		PageRetriever test = new PageRetriever("http://jsoup.org/cookbook/extracting-data/example-list-links");

	}
}

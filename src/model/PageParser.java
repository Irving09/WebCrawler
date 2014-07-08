package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
	- The PageParser class parses each retrieved document into words and embedded URLs.

	- New URLs found in a web page (but not text documents), should be stored for later retrieval.
	
	- Both absolute URLs, e.g.,
		<a href="http://someplatform.xxx/directory/page.html">,
		- and relative URLs, e.g., 
		<a href="../anotherdirectory/anotherpage.html">, should be handled.
*/

public class PageParser {
	
	/**
	 * A queue that holds html documents
	 * */
	private Queue<Document> htmlDocs; 

	/**
	 * Initializes all private fields
	 * */
	public PageParser() {
		htmlDocs = new PriorityQueue<Document>();
	}

	/**
	 * Initializes all private fields and adds all documents into the queue
	 * */
	public PageParser(Queue<Document> documents) {
		htmlDocs = new PriorityQueue<Document>();
		while (!documents.isEmpty())
			htmlDocs.add(documents.remove());
	}
	
	/**
	 * A method used to parse all documents for links that matches any of the search keywords.
	 * Returns a queue of links that matches any of the search keywords. 
	 * */
//	public Queue<String> parseAllDocuments(final Set<KeyWord> searchKeyWords) {
//		Queue<String> urlsToParse = new PriorityQueue<String>();
//		
//		Queue<String> relativeURLs;
//		while (!htmlDocs.isEmpty()) {
//			//relativeURLs represent ALL links within a single document
//			relativeURLs = parseDocument(htmlDocs.remove(), searchKeyWords);
//
//			while (!relativeURLs.isEmpty()) 
//				urlsToParse.add(relativeURLs.remove());
//		}
//		return urlsToParse;
//	}

	/**
	 * A helper method that parses a single document.
	 * Note that each document will have its own links.
	 * Returns a queue of urls. 
	 * */
	public Queue<String> parseDocument(final Document document, final Set<KeyWord> searchKeyWords) {
		Queue<String> relativeURLs = new PriorityQueue<String>();

		//Note: elements from an html page with attributes of href, (links to other pages)
		Elements links = document.select("a[href]");

		System.out.println();
		//Iterates all url links within the document
		for (Element e : links) {
			//only return links if any of their text matches a search keyword
			for (final KeyWord word : searchKeyWords) {
				//TODO Have to figure out how to efficiently check keyword matches
				//to any of the links in every page 'e'

				//if (link matches key word)
				if (e.text().toLowerCase().contains(word.string().toLowerCase())) {
					//e.attr("abs:href") gives the URL of the link in String
					relativeURLs.add(e.attr("abs:href"));
				}
			}
		}
		return relativeURLs;
	}
	
	public void deleteContents() {
		htmlDocs.clear();
	}
	
	public Queue<Document> getHtmlDocs() {
		return htmlDocs;
	}
	
	/**
	 * Add each document into the Queue of Documents to be parsed.
	 * @param the_doc document of a page.
	 */
	public void addDocuments(Queue<Document> the_doc) {
		Iterator<Document> itr = the_doc.iterator();
		
		while(itr.hasNext()){
			htmlDocs.add(itr.next());
		}
	}
}

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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
	private Queue<Document> htmlDocs; 
	public PageParser(Queue<Document> documents) {
		htmlDocs = new PriorityQueue<Document>();
		while (!documents.isEmpty())
			htmlDocs.add(documents.remove());
	}
	
	public Queue<String> parseAllDocuments() {
		Queue<String> urlsToParse = new PriorityQueue<String>();
		
		Queue<String> relativeURLs;
		while (!htmlDocs.isEmpty()) {
			relativeURLs = parseDocument(htmlDocs.remove());
			while (!relativeURLs.isEmpty())
				urlsToParse.add(relativeURLs.remove());
		}
		return urlsToParse;
	}
	
	private Queue<String> parseDocument(final Document document) {
		Queue<String> relativeURLs = new PriorityQueue<String>();

		//Note: elements from an html page with attributes of href, (links to other pages)
		Elements links = document.select("a[href]");

		//Iterates all links within the document
		for (Element e : links) {
			//e.attr("abs:href") gives the URL of the link in String
			relativeURLs.add(e.attr("abs:href"));
		}
		return relativeURLs;
	}
	
	public static void main(String[] args) {
		String initialURL = "http://jsoup.org/cookbook/extracting-data/example-list-links";
		Queue<Document> test = new PriorityQueue<Document>();
		PageRetriever retriever = new PageRetriever(initialURL);
		
		PageParser parser = new PageParser(retriever.retrieveDocuments());
		System.out.println(parser.parseAllDocuments());
	}
}

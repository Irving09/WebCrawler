package model;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/*
1) The total number of web pages crawled.
2) The average number of words per web page.
3) The average number of embedded URLs per web page.
4) For each of the ten selected keywords...
 	a) ...the total number of occurrences across all web page.
 	b) ...the average number of occurrences per web page.
5) The average amount of time required to parse each web page.
6) The total execution time of the entire program.
*/

/**
The PageAnalyzer class examines the words and embedded URLs within each retrieved document,
tabulating the summary statistics that will be reported when the web crawler finishes.
Keywords are identified in this class.
For simplicity, keywords should be case-insensitive and only whole words (not phrases) need to be supported.
After all web pages have been crawled, this class should have collected
all the necessary information for the UI to produce the summary report.
*/

public class PageAnalyzer {
	/*queue NOT used*/
	private Queue<Document> my_documents;

	public PageAnalyzer() {
		my_documents = new PriorityQueue<Document>();
	}
	//The average number of words per web page.
	//The average number of embedded URLs per web page.
	
	public void addDocuments(Queue<Document> doc) {
		my_documents.addAll(doc);
	}
	public void analyzePage(Document webPage, Set<KeyWord> searchKeys) {
		//TODO implement this more efficiently
//		Element head = webPage.head();
//		Element body = webPage.body();
		
		for (Element e : webPage.getAllElements()) {
			for (KeyWord word : searchKeys) {
				if (e.ownText().toLowerCase().contains(word.toString().toLowerCase())) {
					word.incrementHits();
				}
			}
		}
		
		System.out.println("analyzing webpage: " + webPage.location());
		System.out.println("Word\t\tTotal Hits");
		for (KeyWord word : searchKeys) {
			System.out.println(word + "\t\t" + word.totalHits());
		}
	}
	public void deleteContents() {
		my_documents.clear();
	}
}

package model;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageRetriever {
	//http://jsoup.org/cookbook/extracting-data/example-list-links
	public static void main(final String... args) {
		testRetrieveLinks();
	}
	
	public static void testRetrieveLinks() {
		//begin URL
		String url = "http://jsoup.org/cookbook/extracting-data/example-list-links";

		//Document object that will hold all the links from the url 'url'
		Document doc;
		try {
			//connects to the url specified and converts it to Document full of Strings text
			doc = Jsoup.connect(url).get();

			//stores all href (links) elements into an array of ELements
			Elements links = doc.select("a[href]");

			//Note: elements from an html page with attributes of href, (links to other pages)
			
			//Iterates all elements from the array of elements from 'links'
			Iterator<Element> itr = links.iterator();
			while (itr.hasNext()) {
				Element e = itr.next();
				System.out.println(e.text());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

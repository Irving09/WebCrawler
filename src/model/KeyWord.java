package model;

public class KeyWord {
	private String keyword;
	private double avgHits;
	private double totalHits;
	public KeyWord(String the_word) {
//		keyword = the_word;
//		avgHits = the_avgHits;
//		totalHits = the_totalHits;
		this(the_word, 0, 0);
	}
	public KeyWord(String the_word, double the_avgHits, double the_totalHits) {
		keyword = the_word;
		avgHits = the_avgHits;
		totalHits = the_totalHits;
	}
	public String string() {
		return keyword;
	}
	public double average() {
		return avgHits;
	}
	public double total() {
		return totalHits;
	}
	public String toString(){
		return keyword;
	}
}

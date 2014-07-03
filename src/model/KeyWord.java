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
	
	public boolean equals(final Object the_other) {
		if (this == the_other) {
			return true;
		} else if ((the_other != null) &&
				(the_other.getClass() == getClass())) {
			return keyword.equals(((KeyWord) the_other).string());
		} else {
			return false;
		}
	}
}

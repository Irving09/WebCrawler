package model;

public class KeyWord {
	private String keyword;
	private double totalHits;
	public KeyWord(String the_word) {
		this(the_word, 0);
	}
	public KeyWord(String the_word, double the_totalHits) {
		keyword = the_word;
		totalHits = the_totalHits;
	}
	public void addHit() {
		totalHits++;
	}
	public String string() {
		return keyword;
	}
	public double total() {
		return totalHits;
	}
	public String toString(){
		return keyword + " : " + totalHits;
	}
	
	public boolean equals(final Object the_other) {
		if (this == the_other) {
			return true;
		} else if ((the_other != null) &&
				(the_other.getClass() == getClass())) {
			return keyword.toLowerCase().equals(((KeyWord) the_other).string().toLowerCase());
		} else {
			return false;
		}
	}
}

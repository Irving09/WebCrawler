package model;

public class KeyWord {
	private String keyword;
	private int totalHits;
	public KeyWord(String the_word) {
		this(the_word, 0);
	}
	public KeyWord(String the_word, int the_totalHits) {
		keyword = the_word.toLowerCase();
		totalHits = the_totalHits;
	}
	public void incrementHits() {
		totalHits++;
	}
	public int totalHits() {
		return totalHits;
	}
	public String toString(){
		return keyword;// + " : " + totalHits;
	}

	/**
	 * Compares whether two KeyWord objects are equivalent.
	 * The equality of two KeyWord objects is NOT case sensitive.
	 * Ex) This method would return TRUE with the following comparisons
	 * 		Hello == HeLLO
	 * 
	 * @param the_other The other object to compare
	 * @return Returns true if both KeyWord strings are equal both in lowercase
	 * */
	public boolean equals(final Object the_other) {
		if (this == the_other) {
			return true;
		} else if ((the_other != null) &&
				(the_other.getClass() == getClass())) {
			return keyword.toLowerCase().equals(((KeyWord) the_other).toString().toLowerCase());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	public final void clearHits() {
		totalHits = 0;
	}
}

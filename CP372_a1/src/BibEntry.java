
public class BibEntry {
    	int isbn, year;
    	String title, author, pub;
    	
    	/*
    	 * Bibliography Entry constructor
    	 */
    	public BibEntry (int bookIsbn, String bookTitle, String bookAuthor, String bookPub, int bookYear) {
    		this.isbn = bookIsbn;
    		this.title = bookTitle;
    		this.author = bookAuthor;
    		this.pub = bookPub;
    		this.year = bookYear;
    	}
    	
    	public String parseEntry () {
    		String strIsbn = Integer.toString(this.isbn);
    		String strYear = Integer.toString(this.year);
    		
    		String parsedEntry = strIsbn + " " + this.title + " " + this.author + " " + this.pub + " " + strYear; 
    		
    		return parsedEntry;
    	}
    	
}
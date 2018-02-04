import java.util.Iterator;
import java.util.Set;

public class Protocol {
    	private static final int WAITING = 0;
    	private static final int GETREQUEST = 1;
    	private static final int UPDATEREQUEST = 2;
    	private static final int SUBMITREQUEST = 3;
    	private static final int REMOVEREQUEST = 4;
    	
    	private int state = WAITING;
    	
    	public String processInput(String clientRequest, Set<BibEntry> bibList) {
    		String serverResponse = null;
    		
    		if (state == WAITING) {
    			serverResponse = null;
    		} else if (state == GETREQUEST) {
    			Set<BibEntry> bibEntries = get(bibList, clientRequest);
    			
    			// Creates iterator for Set to turn BibEntry objects to strings
    			Iterator<BibEntry> it = bibEntries.iterator();
    			
    			while (it.hasNext()) { // Runs while there are still entries in the server response
    				serverResponse += it.next().parseEntry(); // Concatenate entry to the server response
    				serverResponse += "\n"; // Start a new line for each entry
    			}
    			
    		} else if (state == UPDATEREQUEST) {
    			String entry = update(bibList, clientRequest);
    			
    			serverResponse = "Entry was updated to: " + entry;
    			
    		} else if (state == SUBMITREQUEST) {
    			
    		} else if (state == REMOVEREQUEST) {
    			
    		} else {
    			serverResponse = null;
    			state = WAITING;
    		}
			return serverResponse;
    	}
    	
    	public Set<BibEntry> get (Set<BibEntry>bibList, String clientRequest) {
			
    		return bibList;
    	}
    	
    	public String update (Set<BibEntry> bibList, String clientRequest) {
    		Iterator<BibEntry> it = bibList.iterator();
    		int bibIsbn = 0, clientIsbn = 0;
    		String updatedEntry = null;
    		
    		while (it.hasNext()) {
    			bibIsbn = it.next().isbn;
    			if (bibIsbn == clientIsbn) {
    				updatedEntry = Integer.toString(bibIsbn);
    				break;
    			}
    			
    			updatedEntry = "";
    		}
    		
    		return updatedEntry;
    	}
//    	public String parseBibEntry (Set<BibEntry> bibList) {
//    		String parsedBibEntry = "";
//    		return parsedBibEntry;
//    	}
    }

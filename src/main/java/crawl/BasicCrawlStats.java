package crawl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Frank on 16/2/5.
 */

public class BasicCrawlStats {

    private HashMap<String, Integer> pagesToCrawl = new HashMap<String, Integer>();
    private PrintWriter writer;
    
    BasicCrawlStats() throws FileNotFoundException {
    	writer = new PrintWriter("crawledData/statsLog.txt");
    }

    public boolean intendToVisit(String url) throws URISyntaxException {
    	String urlR;
    	if ( url.lastIndexOf("?")!=-1 ) {
    		urlR = url.substring(0, url.lastIndexOf("?"));
    	} else urlR = url;
        String str = "calendar";

        if (urlR.length() > 300) {
        	writer.println("***Found extremely long URL***");
        	writer.println(urlR);
            return false;
        }
/*
        if (urlR.toLowerCase().contains(str)){
            return false;
        }
*/
        /**
         * if (urlR == null) { return true; }
         */
        
        try {
	        int count = 1;
	        if (this.pagesToCrawl.containsKey(urlR))
	            count = this.pagesToCrawl.get(urlR);
	
	        if (count >= 10)
	            return false;
	
	        this.pagesToCrawl.put(urlR, count + 1);
        } catch ( OutOfMemoryError e ) {
        	writer.println("***Out of memory, clear pagesToCrawl hashMap***");
        	pagesToCrawl.clear();
        }

        return true;
    }
}

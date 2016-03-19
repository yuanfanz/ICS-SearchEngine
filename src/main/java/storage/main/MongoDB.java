package storage.main;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDB implements InternalDB {
	public MongoDatabase db, dbTokens, dbPages;
	public final static String TOKEN_DB_NAME = "cs221_tokens";
	public final static String RAWPAGE_DB_NAME = "cs221_rawpages";
	public final static String PAGE_COLL_NAME = "URL_Pages";
	public final static String TOKEN_COLL_NAME = "tokens";
	public final static String TGRAM_COLL_NAME = "threeGrams";
	
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	public void init(String URI, String DB_Name) {
		// TODO Auto-generated method stub
		MongoClient client = new MongoClient( 
	      		 new MongoClientURI(
	      				URI) 
	      		 );
	       // Now connect to your databases
	    db = client.getDatabase(DB_Name);
	    dbTokens = client.getDatabase(TOKEN_DB_NAME);
	    dbPages = client.getDatabase(RAWPAGE_DB_NAME);
	    System.out.println("---MongoDB initialized---");
	} 
	
}

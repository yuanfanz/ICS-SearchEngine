package storage.main;

import static java.util.Arrays.asList;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.slf4j.LoggerFactory;

import com.mongodb.BulkWriteOperation;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;



/**
 * Operation related to URL page could be found here.
 * @author Yen
 *
 */
public class FileStorage {
	
	private static final FileStorage instance = null;
	private MongoDB DB;
	private MongoDatabase db;
	private MongoCursor<Document> iter;
	public final static String RAWPAGE_DB_NAME = "cs221_rawpages";
	public final static String PAGE_COLL_NAME = "URL_Pages";
	public final static String MONGOLAB_URI = 
			"mongodb://UCI_Handsomes:UCI_Handsomes@ds051635.mongolab.com:51635/cs221_rawpages";
	public final static String ICS_URI = 
			"mongodb://UCI_Handsomes:UCI_Handsomes@ramon-limon.ics.uci.edu:8888/"+RAWPAGE_DB_NAME;
	public final static String ICS_NOAUTH_URI = 
			"mongodb://ramon-limon.ics.uci.edu:8888/"+RAWPAGE_DB_NAME;
	public final static String LOCAL_URI = 
			"mongodb://127.0.0.1/"+RAWPAGE_DB_NAME;
	public final static String LOCAL_8888_URI = 
			"mongodb://127.0.0.1:8888/";
	public final static String JIAN_URI = 
			"mongodb://70.187.178.194/"+RAWPAGE_DB_NAME;
	
	private List bulkList;
	private Logger mongoLogger;
	
	/**
	 * connect to MONGOLAB_URI, ICS_URI or LOCAL_URI
	 * @param URI
	 */
	public FileStorage( String URI ) {
		bulkList = new ArrayList();
	    DB = new MongoDB();
		DB.init(URI, RAWPAGE_DB_NAME);
		
		//change logger property
		mongoLogger = (Logger) LoggerFactory.getLogger( "org.mongodb.driver.cluster" );
	    mongoLogger.setLevel(Level.WARN);
	       // Now connect to your databases
	    db = DB.db;
	}
	
	/**
	 * remove all stored pages in DB. Use it wisely.
	 */
	public void reset() {
		MongoCollection coll = db.getCollection(PAGE_COLL_NAME);
		coll.drop();
		IndexOptions IndOpt = new IndexOptions();
		IndOpt.unique(true);
		db.createCollection(PAGE_COLL_NAME);
		coll = db.getCollection(PAGE_COLL_NAME);
		coll.createIndex ( new Document("URL",1) , IndOpt);
	}
	
	public boolean insertURLPage(List<Map.Entry<String, String>> pages, List<String> titles, List<String> anchors, 
			List<Map<String,String>> metaTags) {
		List bulkList = new ArrayList();
		   for ( int i=0; i<pages.size(); i++ ) {
			   //insertToken( p.get(i).getT(), p.get(i).getE(), URL );
			   bulkList.add( new UpdateOneModel( new Document( "URL", pages.get(i).getKey() ),
					   new Document("$set", 
							   new Document( "URL", pages.get(i).getKey() )
							   .append( "title", titles.get(i) )
							   //.append( "anchor", anchors.get(i) )
							   .append( "content", pages.get(i).getValue() )
							   .append( "metaTags", metaTags.get(i) )
						).append("$addToSet", 
								new Document( "anchor", anchors.get(i) )
						)	//$set
					   , new UpdateOptions().upsert(true)
					  )	//UpdateOneModel
				);
		   }
		   BulkWriteOptions opt = new BulkWriteOptions();
		   db.getCollection(PAGE_COLL_NAME).bulkWrite(bulkList, opt.ordered(false));
		   return true;
	}
	
	public void appendAnchorText( List<String> URL, List<String> anchorText ) {
		List bulkList = new ArrayList();
		   for ( int i=0; i<URL.size(); i++ ) {
			   //insertToken( p.get(i).getT(), p.get(i).getE(), URL );
			   bulkList.add( new UpdateOneModel( new Document( "URL", URL.get(i) ),
					   new Document("$addToSet", 
								new Document( "anchor", anchorText.get(i) )
						)	//$set
					   , new UpdateOptions().upsert(true)
					  )	//UpdateOneModel
				);
		   }
		   BulkWriteOptions opt = new BulkWriteOptions();
		   db.getCollection(PAGE_COLL_NAME).bulkWrite(bulkList, opt.ordered(false));
	}
	
	/**
	* Set iterator of collection "URL_Pages". Use getNextPage() to retrieve page one by one.
	*/
   public void resetPagesIterator() {
	   iter = db.getCollection(PAGE_COLL_NAME).find().noCursorTimeout(true).sort(
			   new Document("URL",1) ).iterator();
	   
   }
   
   /**
	* Return HashMap type of ( [URL, text, title] , [anchorList] ). Call it multiple times until it returns null;
	* If return null, means no more element.
	* @return Map.Entry 
	*/
   public Map.Entry< List<String>, List<String> > getNextPage() {
	   HashMap<List<String>, List<String> > tmp = null;
	   if ( !iter.hasNext() ) return null;
	   Document tmpDoc = iter.next();
	   List<String> tmpList = new ArrayList<String>();
	   //List< Map<String,String> > tmpMeta = new ArrayList<Map<String,String>>();
	   List<String> tmpAnchors = (List<String>) tmpDoc.get("anchor"); 
	   
	   tmpList.add(tmpDoc.getString("URL"));
	   tmpList.add(tmpDoc.getString("content"));
	   tmpList.add(tmpDoc.getString("title"));
	   //tmpList.add(tmpDoc.getString("anchor"));
	   
	   
	   //tmpMeta.add( (Map<String, String>) ( (Document)tmpDoc.get("metaTags") ).get );
	   
	   tmp = new HashMap<List<String>, List<String> >();
	   //tmp.put( tmpDoc.getString("URL"), tmpDoc.getString("content") );
	   tmp.put( tmpList, tmpAnchors );
	   Map.Entry<List<String>, List<String> > ret = tmp.entrySet().iterator().next();
	   return ret;
   }
   
   @Deprecated
   /**
	* Get page content by URL. Page will be in String format. Only one page will be returned.
	* @param URL
	* @return page content in String format
	*/
   public String getPageByURL(String URL) {
	   FindIterable<Document> it = db.getCollection(PAGE_COLL_NAME).find( new Document("URL",URL) );
	   return it.iterator().next().getString("content");
   }
	
}

package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.BulkWriteException;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoException;

import data.Pair.Pair;
import storage.main.TokenStorage;

public class TokenStorageTest {

private static TokenStorage ts; 
	
	@BeforeClass
	public static void setupDB() throws InterruptedException {
		ts = new TokenStorage(TokenStorage.ICS_URI);
		//ts.reset();
		//Thread.sleep(1000);
	}
	
	/**
	 * import some data, couldn't use everytime
	 */
	@Ignore
	@Test
	public void insertTokenTest() {
		System.out.println("---Testing insertToken()---");
		/*
		for ( int i=1; i<=10; i++) {
	    	ts.insertToken("hi"+i, i, "TESTURL"+i, ts.TOKEN_COLL_NAME);
    	}
    	*/
		System.out.println("---Testing BULK insertToken()---");
		List<Pair> l = new ArrayList<Pair>();
		List<Integer> pos = new ArrayList<Integer>();
		for ( int i=11; i<=20; i++) {
	    	pos.add(i);
    	}
		l.add( new Pair("hi11",pos) );
    	l.add( new Pair("hi12",pos) );
    	l.add( new Pair("hi13",pos) );
		try {
			ts.insertToken(l, "TESTURLBULK", TokenStorage.TOKEN_COLL_NAME);
			ts.insertToken(l, "TESTURLBULK", TokenStorage.TOKEN_COLL_NAME);
			ts.insertToken(l, "TESTURLBULK2", TokenStorage.TOKEN_COLL_NAME);
			ts.insertToken(l, "TESTURLBULK3", TokenStorage.TOKEN_COLL_NAME);
			ts.insertToken(l, "TESTURLBULK111", TokenStorage.TGRAM_COLL_NAME);
			ts.insertToken(l, "TESTURLBULK222", TokenStorage.TGRAM_COLL_NAME);
			ts.insertToken(l, "TESTURLBULK333", TokenStorage.TGRAM_COLL_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("---Complete Testing insertToken()---");
	}
	
	
	@Ignore
	@Test
	public void getTokenFreqTest() {
		//what if there no such token? test will FAIL~!
		System.out.println("---Testing getTokenFreq()---");
		for ( int i=1; i<=10; i++ ) {
	//		System.out.println( "hi" + i + ": " + ts.getTokenFreq("hi" + i) );
		}
		System.out.println("---Complete Testing getTokenFreq()---");
	}
	
	@Ignore
	@Test
	public void getHighestFreq_TokenTest() {
		System.out.println("---Testing getHighestFreq_Token()---");
		System.out.println( ts.getHighestFreq(3, ts.TOKEN_COLL_NAME) );
		System.out.println( ts.getHighestFreq(2, ts.TGRAM_COLL_NAME) );
		System.out.println("---Complete Testing getHighestFreq_Token()---");
	}
	
	/*
	@Ignore
	@Test
	public void insert3GTest() {
		System.out.println("---Testing insert3G()---");
		for ( int i=1; i<=10; i++) {
	    	ts.insert3G("hi"+i+"hi"+(i+1)+"hi"+(i+2), i, "TESTURL"+i);
    	}
		List l = new ArrayList();
		for ( int i=11; i<=20; i++) {
	    	//l.add( new Pair("hi"+i+"hi"+(i+1)+"hi"+(i+2),i) );
			l.add( new Pair("hi",1) );	//test duplicate
    	}
		try {
			//if ( !l.isEmpty() )
				ts.insert3G(l, "TESTURLBULK");
		} catch (DuplicateKeyException e) {
			System.out.println(e.getMessage());
			
		} catch (MongoBulkWriteException e) {
			System.out.println(e.getMessage());
		} catch ( MongoException e ) {
			System.out.println(e.getMessage());
			assertTrue(false);
		}
		System.out.println("---Complete Testing insert3G()---");
	}
	*/
	
	

}

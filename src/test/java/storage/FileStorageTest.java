package storage;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import data.Pair.Pair;
import storage.main.FileStorage;

public class FileStorageTest {

	private static FileStorage fs;
	
	@BeforeClass
	public static void setupDB() throws InterruptedException {
		fs = new FileStorage(FileStorage.JIAN_URI);
		//fs.reset();
		//Thread.sleep(1000);
	}
	
	@Ignore
	@Test
    public void testConnetivity()
    {
    	System.out.println("---testing connectivity...---");
    	fs = new FileStorage(FileStorage.ICS_URI);
        assertTrue("Connectivity test complete", true);
    }
    
	@Ignore
	@Test
	public void testInsertPage() {
		System.out.println("---Testing insertPage()---");
		for ( int i=1; i<=10; i++) {
	    	//fs.insertURLPage("TESTURL"+i, "hi"+i);
    	}
		System.out.println("---Testing BULK insertPage()---");
		List l = new ArrayList();
		List<String> titleList = new ArrayList<String>();
		List<String> anchorList = new ArrayList<String>();
		List metaList = new ArrayList();
		for ( int i=11; i<=20; i++) {
	    	l.add( new AbstractMap.SimpleEntry<String, String>("TESTURL"+i, "hi"+i) );
	    	titleList.add( "title" + i);
	    	anchorList.add( "anchor" + i);
	    	metaList.add( new HashMap<String, String>() );
    	}
		try {
			fs.insertURLPage(l, titleList, anchorList, metaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("---Complete Testing insertPage()---");
	}
	
    //@Ignore
    @Test
    public void testGetPage() throws FileNotFoundException
    {
    	//PrintWriter writer = new PrintWriter("pageURLs.txt");
    	int totalPages = 0;
    	Set<Integer> s = new HashSet<Integer>();
    	System.out.println("---testing getPage---");
    	fs.resetPagesIterator();
    	Map.Entry<List<String>, List<String> > page = fs.getNextPage();
    	System.out.println("---printing out page URL---");
    	while ( page!=null && totalPages<500 ) {
    		totalPages++;
    		//writer.println(page.getKey().get(0));
    		//writer.println(page.getValue());
    		//writer.println("*******");
    		System.out.println("\n"+page.getKey().get(0));
    		System.out.println("---Printing out anchor texts---");
    		for ( String anchor : page.getValue()) {
    			System.out.println(anchor);
    		}
    		/*
    		if ( !s.add(page.getKey().get(0).hashCode()) ){
    			System.out.println("Duplicate!!");
    		}
    		/*
    		if ( !s.add(1) ){
    			System.out.println("Duplicate!! 1!!");
    		}
    		*/
    		//System.out.println("Page URL: " + page.getKey());
    		page = fs.getNextPage();
    		if ( totalPages%2000==0 )
    			System.out.println("Now in Page: " + totalPages);
    	}
    	//writer.println("Total Pages: "+totalPages);
    	//writer.close();
    	System.out.println("---getPage() function test complete---");
    	
        assertTrue(true);
    }
    /*
    @Ignore
    @Test
    public void testGetPageByURL() {
    	System.out.println("---testing getPageByURL()---");
    	fs.resetPagesIterator();
    	Map.Entry<String,String> page = fs.getNextPage();
    	System.out.println("---Printing out page content---");
    	String text = fs.getPageByURL(page.getKey());
    	System.out.println(text);
    	System.out.println("---getPageByURL() function test complete---");
        assertTrue(true);
    }
    */

}

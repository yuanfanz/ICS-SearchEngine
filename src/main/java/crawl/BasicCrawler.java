/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package crawl;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;
import data.text_processing.*;
import org.apache.http.Header;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import storage.main.*;
import data.*;

/**
 * @author Yasser Ganjisaffar
 */

public class BasicCrawler extends WebCrawler {

        private tokenGen tokengen = null;
        private final static Pattern IMAGE_EXTENSIONS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpeg|png|tiff|mid|mp2" +
                "|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|java|cpp|rm|smil|wmv|swf|wma|zip|rar|gz|ico|pfm|c|h|o))$");
        private int pagecount = 0;
        protected final Object mutex = new Object();
        
        private TokenStorage tokenstore;
        private BasicCrawlStats visitStats;
        private List<Map.Entry<String, String>> pages;
        private List<String> titleList;
        private List<Map<String, String>> metaTagList;
        private List<String> anchorList;
  
        @Override
        public void onStart() {
             //tokenstore = new TokenStorage(TokenStorage.ICS_URI);
             try {
				visitStats = new BasicCrawlStats();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             pages = new ArrayList();
             titleList = new ArrayList<String>();
             metaTagList = new ArrayList<Map<String,String>>();
             anchorList = new ArrayList<String>();
        }
  
        /**
         * You should implement this function to specify whether the given url
         * should be crawled or not (based on your crawling logic).
         */
        @Override
        public boolean shouldVisit(Page referringPage, WebURL url) {
              String href = url.getURL().toLowerCase();
              // Ignore the url if it has an extension that matches our defined set of image extensions.
              if (IMAGE_EXTENSIONS.matcher(href).matches()) {
                return false;
              }
              // Don't crawl the same pages too many times
            
              try {
                  if ( !visitStats.intendToVisit( url.getURL() ) ){
                    return false;
                  }
              } catch (URISyntaxException e) {
                  e.printStackTrace();
              }
			
              // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
              return href.contains(".ics.uci.edu/");
        }

      /**
       * This function is called when a page is fetched and ready to be processed
       * by your program.
       */
      @Override
      public void visit(Page page) {
    	  		pagecount++;
    	  		System.out.printf("pagecount is %d\n",pagecount);
    	  	
              //if(tokengen==null) tokengen = new tokenGen();
    	  	  boolean pagemod = false;
              int docid = page.getWebURL().getDocid();
              String url = page.getWebURL().getURL();
              String domain = page.getWebURL().getDomain();
              String path = page.getWebURL().getPath();
              String subDomain = page.getWebURL().getSubDomain();
              String parentUrl = page.getWebURL().getParentUrl();
              String anchor = page.getWebURL().getAnchor();

              logger.debug("Docid: {}", docid);
              logger.info("URL: {}", url);
              logger.debug("Domain: '{}'", domain);
              logger.debug("Sub-domain: '{}'", subDomain);
              logger.debug("Path: '{}'", path);
              logger.debug("Parent page: {}", parentUrl);
              logger.debug("Anchor text: {}", anchor);

              if (page.getParseData() instanceof HtmlParseData) {

                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String title = htmlParseData.getTitle();
                    Map<String,String> metaTags = htmlParseData.getMetaTags();
                    
                    //filestorage.insertURLPage(url,text);
                    AbstractMap.SimpleEntry<String,String> test = 
                    		new AbstractMap.SimpleEntry<String,String>(url, text);
                    //System.out.println(test.getKey()+" "+test.getValue());
                    pages.add(test);
                    titleList.add(title);
                    metaTagList.add(metaTags);
                    anchorList.add(anchor);
                    //commit into DB for every 20 pages
                    
                    if ( pagecount % 500 == 0 ) {
                    	System.out.println("**********Inserting**********************");
                    	filestorage.insertURLPage(pages, titleList, anchorList, metaTagList);
                    	pages.clear();
                    	titleList.clear();
                    	metaTagList.clear();
                    	anchorList.clear();
                    	System.out.println("**********Insert complete****************");
                    }
                    
                    String html = htmlParseData.getHtml();
                    Set<WebURL> links = htmlParseData.getOutgoingUrls();

                    logger.debug("Text length: {}", text.length());
                    logger.debug("Html length: {}", html.length());
                    logger.debug("Number of outgoing links: {}", links.size());
              }

              Header[] responseHeaders = page.getFetchResponseHeaders();
              if (responseHeaders != null) {
                    logger.debug("Response headers:");
                    for (Header header : responseHeaders) {
                        logger.debug("\t{}: {}", header.getName(), header.getValue());
                    }
              }
              logger.debug("=============");
      }
      
      public void onBeforeExit() {
    	    // Do nothing by default
    	    // Sub-classed can override this to add their custom functionality
    	  System.out.println("**********Final Inserting****************");
    	  if ( !pages.isEmpty() )
    		  filestorage.insertURLPage(pages, titleList, anchorList, metaTagList);
    	  pages.clear();
    	  titleList.clear();
      	  metaTagList.clear();
      	  anchorList.clear();
    	  System.out.println("**********Insert Complete****************");
      }
}
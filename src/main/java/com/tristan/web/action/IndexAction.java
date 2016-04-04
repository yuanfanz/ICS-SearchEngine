package com.tristan.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.tristan.web.model.Url;
import com.tristan.web.mongodb.MongoKit;
import com.tristan.web.mongodb.MongoQuery;
import com.tristan.web.util.HtmlKit;

import data.IRUtilities.Stemmer;
import data.IRUtilities.Stopwords;

public class IndexAction extends Controller{
	private String db = PropKit.use("config.properties").get("mongodb.db");
	private String db2 = PropKit.use("config.properties").get("mongodb.db2");
	@ActionKey("/")
	public void index() {
		render("Index.jsp");
	}
	@ActionKey("/search")
	public void search() {
		List<String> keywords = new LinkedList<String>();
		String key = getPara("key").toLowerCase();
		if (StrKit.isBlank(key)) {
			render("Index.jsp");
			return;
		}
		Stemmer s = new Stemmer();
		String[] keys = key.split("[^a-z0-9@]+");
		for(int i=0; i < keys.length; i++){
			if(keys[i].length()>0 && !Stopwords.isStopword(keys[i])){
				s.add(keys[i].toCharArray(),keys[i].length());
				s.stem();
				String tempstr = s.toString();
				//if(tempstr.length()==0) continue;
				keywords.add(tempstr);
			}
		}
//		String reduce = "function(doc, aggr){" + "aggr.count += 1;" + "        }";
		
		MongoQuery query = new MongoQuery();
		MongoKit.setDb(db);
		List<DBObject> dbObjects = null;
		for (String k : keywords) {
			//query.like("token",k);
			query.set("token",k);
			if ( dbObjects==null )  dbObjects = MongoKit.findByQuery("tokens", query, 30);
			else dbObjects.addAll( MongoKit.findByQuery("tokens", query, 30) );
		}
		
		
		List<Url> datas = process(dbObjects);
		for (Url  url : datas) {
			List<DBObject> urls = MongoKit.findByQuery("URLID", new MongoQuery().set("URLID", url.getUrlId()), 1);
			DBObject _url = urls.get(0);
			String uv = (String)_url.get("URL");
			url.setUrl(uv);
		}
		MongoKit.setDb(db2);
		for (Url  url : datas) {
			List<DBObject> urls = MongoKit.findByQuery("URL_Pages", new MongoQuery().set("URL", url.getUrl()), 1);
			DBObject _url = urls.get(0);
			if (_url == null) {
				continue;
			}
			String title = (String)_url.get("title");
			url.setTitle(title);
			String content = (String)_url.get("content");
			content = HtmlKit.trimHtmlTag(content);
			if (content != null && content.length() > 250) {
				content = content.substring(0, 200);
			}
			url.setContent(content);
		}
        setAttr("data", datas);
        keepPara();
		render("Index.jsp");
	}
	
	private List<Url> process(List<DBObject> results) {
		Map<Integer, Double> datas = new TreeMap<>();
		Map<String, Double> query = new HashMap<>();
		Map<Integer, HashMap<String, Double>> docs = new HashMap<Integer, HashMap<String, Double>>();
		//tokens
		for (DBObject dbObject : results) {
			//compute query vector
			Double IDF = Double.valueOf(dbObject.get("IDF").toString());
			query.put(dbObject.get("token").toString(), IDF);	//assume TF = 1
		}
		
		//token
		for (DBObject dbObject : results) {
			//docs.clear();
			BasicDBList list = (BasicDBList)dbObject.get("URLs");
			//URL list
			for (Object object : list) {
				DBObject object2 = (DBObject)object;
				Integer uriId = (Integer)object2.get("URL");
				Double score = Double.valueOf(object2.get("TFIDF").toString());
				
				/*
				Double o = datas.get(uriId);
				if (o == null) {
					datas.put(uriId, score);
				}else {
					datas.put(uriId, score + o);
				}
				*/
				
				HashMap<String, Double> tmp = docs.get(uriId);
				if ( tmp == null ) {	//還沒有在裡面
					tmp = new HashMap<String, Double>();
					tmp.put(dbObject.get("token").toString(), score);
					docs.put(uriId, tmp);
				} else {	//有在裡面，把tmp拿出來再insert
					tmp.put(dbObject.get("token").toString(), score);
					docs.put(uriId, tmp);
				}
				
			}
			
		}
		
		Iterator it = docs.entrySet().iterator();
		while ( it.hasNext() ) {	//datas
			Map.Entry pair = (Map.Entry) it.next();
			//put cos similarity to uriID
			datas.put((Integer)pair.getKey(), CosineSimilarity.calculateCosineSimilarity( (HashMap<String, Double>)query, 
					(HashMap<String, Double>)( pair.getValue() )) );
			//datas.put((Integer)pair.getKey(), 1.0 );
		}
		
		
		//System.out.println("HIHIHI");
		
		List<Url> result = new ArrayList<>();
		for (Map.Entry<Integer,Double> entry : datas.entrySet()) {
			Integer _key = entry.getKey();
			Double _value = entry.getValue();
			Url url = new Url("",_key,_value);
			result.add(url);
		}
		Collections.sort(result);
		Collections.reverse(result);
		
		if(result.size() >= 10){
			return result.subList(0, 10);
		}
		
		return result;
	}
}

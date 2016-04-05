package storage;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import storage.main.TokenStorage;

public class Proj2Answers {
	
	
	public static void main(String[] args) throws Exception {
		TokenStorage ts = new TokenStorage(TokenStorage.ICS_URI);
		//get freq
		List<Map.Entry<String,Integer>> tokenFreq;
		List<Map.Entry<String,Integer>> TGFreq;
		
		//tokenFreq = ts.getHighestFreq_Token(501);
		//TGFreq = ts.getHighestFreq_3G(20);
		
		PrintWriter tokenTxt = new PrintWriter("tokenFreq.txt");
		//PrintWriter TGTxt = new PrintWriter("TGFreq.txt");
		/*
		for ( int i=0; i<tokenFreq.size(); i++ ) {
			System.out.println(tokenFreq.get(i).getKey() + " " + tokenFreq.get(i).getValue());
			tokenTxt.println( tokenFreq.get(i).getKey() + " " + tokenFreq.get(i).getValue() );
		}
		*/
		/*
		for ( int i=0; i<TGFreq.size(); i++ ) {
			System.out.println(TGFreq.get(i).getKey() + " " + TGFreq.get(i).getValue());
			TGTxt.println( TGFreq.get(i).getKey() + " " + TGFreq.get(i).getValue() );
		}
		*/
		tokenTxt.close();
		//TGTxt.close();
	}
}

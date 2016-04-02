package storage;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import storage.main.TokenStorage;

public class computeTFIDF {
	public static void main(String[] args) throws Exception {
		TokenStorage ts;
		
		ts = new TokenStorage(TokenStorage.JIAN_URI);
		String toks[] = { "mondego", "machin", "learn", "softwar", "engin", "secur",
				"student", "affair", "graduat", "cours", "crista", "lope", "rest", "comput",
				"game", "inform", "retriev" };
		for ( String str : toks) {
			ts.computeTFIDF(ts.TOKEN_COLL_NAME, str);
		}
		return;
		
		/*
		if ( args.length==0 ){
			System.out.println("Argument 1: LOCAL, ICS, ICS_NOAUTH, JIAN");
			System.out.println("Argument 2: TOKEN, 3G");
			return;
			
		} else {
			if ( args[0].equals("LOCAL") ) {
				ts = new TokenStorage(TokenStorage.LOCAL_URI);
			} else if ( args[0].equals("ICS") ) {
				ts = new TokenStorage(TokenStorage.ICS_URI);
			} else if ( args[0].equals("ICS_NOAUTH") ) {
				ts = new TokenStorage(TokenStorage.ICS_NOAUTH_URI);
			} else if ( args[0].equals("JIAN") ) {
				ts = new TokenStorage(TokenStorage.JIAN_URI);
			} else {
				System.out.println("Argument: LOCAL, ICS, ICS_NOAUTH, JIAN");
				return;
			}
		}
		//get freq
		if ( args[1].equals("TOKEN") ){ 
			ts.computeTFIDF(ts.TOKEN_COLL_NAME, "");
		}else if ( args[1].equals("3G") ) {
			ts.computeTFIDF(ts.TGRAM_COLL_NAME, "");
		}
		*/
	}
}

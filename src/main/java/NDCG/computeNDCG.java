package NDCG;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class computeNDCG {
	//預設是兩個file要來做比對
	public static void main(String[] args) throws Exception {
		//args[0] = input file, args[1] = google file
		if ( args.length != 2 ) {
			System.out.println("arg[0] = ourFileName, arg[1] = googleFileName");
			return;
		}
		
		FileReader ourResult = new FileReader(args[1]);
		FileReader googleResult = new FileReader(args[0]);
        BufferedReader ourBR = new BufferedReader(ourResult);
        BufferedReader googleBR = new BufferedReader(googleResult);
        List<String> ourURLs = new ArrayList<String>();
        List<String> googleURLs = new ArrayList<String>();
        String line = null;
        int times = 5;
        while (times!=0) {	//will read 5 times
            ourURLs.add(ourBR.readLine());
            googleURLs.add(googleBR.readLine());
            times--;
        }
        ourBR.close();
        googleBR.close();
        
        //now let's compare
        double[] idealDCG = new double[5];
        double[] ourDCG= new double[5];
        double[] NDCG= new double[5];
        double score = 5;
        for ( int i=0; i<5; i++ ) {
        	if ( i==0 ) { 
        		idealDCG[i] = score;
        		ourDCG[i] = 0;
        		double tmpScore=5;	//5,4,3,2,1 for finding match
        		for ( int j=0; j<5; j++ ) {
	        		if ( ourURLs.get(i).equals( googleURLs.get(j) ) ) {
	        			ourDCG[i] = tmpScore;
	        			break;
	        		}
	        		tmpScore--;
        		}
        	}else {
        		idealDCG[i] = idealDCG[i-1] + score/(Math.log(i+1)/Math.log(2));
        		ourDCG[i] = ourDCG[i-1];
        		double tmpScore=5;	//5,4,3,2,1 for finding match
        		for ( int j=0; j<5; j++ ) {
	        		if ( ourURLs.get(i).equals( googleURLs.get(j) ) ) {
	        			ourDCG[i] = ourDCG[i-1] + tmpScore/(Math.log(i+1)/Math.log(2));
	        			break;
	        		}
	        		tmpScore--;
        		}
        	}
        	score--;
        }//compute ideal & our DCG
        
        //next computeNDCG
        for ( int i=0; i<5; i++ ) {
        	if ( i==0 ) NDCG[i] = ourDCG[i]/idealDCG[i];
        	else NDCG[i] = ourDCG[i]/idealDCG[i];
        	System.out.println("ourDCG " + (i+1) + " is : " + ourDCG[i]);
        	System.out.println("idealDCG " + (i+1) + " is : " + idealDCG[i]);
        	System.out.println("NDCG " + (i+1) + " is : " + NDCG[i]);
        	System.out.println();
        }
        
	}//main
}

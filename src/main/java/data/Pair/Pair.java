package data.Pair;

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.lang.*;
public class Pair implements Comparable<Pair>{
	   String t;
	   List<Integer> e;
	   public Pair(String t, List<Integer> e){
		   this.t = t;
		   this.e = e;
	   }
	   public String getT(){
		   return this.t;
	   }
	   public List<Integer> getE(){
		   return this.e;
	  }
	   /*public int hashCode(){
		  //System.out.println("hashcode call");
		   return this.t.hashCode() + this.e.hashCode();
	   }*/
		public int compareTo(Pair comparePair){
			int tempe = -(this.e.size() - comparePair.getE().size());
			if(tempe==0) tempe = this.t.compareTo(comparePair.getT());

			return tempe;
		}
	   /*@SuppressWarnings("unchecked")
	   @Override
	   public boolean equals(Object obj){
		   //System.out.println("equals call");
		   if((obj instanceof Pair)){
			Pair comparePair = (Pair) obj; 
			if(this.t.equals(comparePair.getT()) && (this.e.equals(comparePair.getE()))){
				return true;
			}
			return false;
		  }else{
			  return false;
		  }
	   }*/
	   
 }

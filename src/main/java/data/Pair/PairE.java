package data.Pair;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.*;
import java.math.BigInteger;
import java.lang.*;
public class PairE<T,E>{
	   T t;
	   E e;
	   public PairE(T t, E e){
		   this.t = t;
		   this.e = e;
	   }
	   public T getT(){
		   return this.t;
	   }
	   public E getE(){
		   return this.e;
	   }
	   public int hashCode(){
		  //System.out.println("hashcode call");
		   return this.t.hashCode() + this.e.hashCode();
	   }
	   @SuppressWarnings("unchecked")
	   @Override
	   public boolean equals(Object obj){
		   //System.out.println("equals call");
		   if((obj instanceof PairE<?,?>)){
			   PairE<T,E> comparePair = (PairE<T,E>) obj; 
			if(this.t.equals(comparePair.getT()) && (this.e.equals(comparePair.getE()))){
				return true;
			}
			return false;
		  }else{
			  return false;
		  }
	   }
	   
}
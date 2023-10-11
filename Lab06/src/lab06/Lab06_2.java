package lab06;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class Lab06_2 implements SorterL{
   
  public List<Comparable> generateRandList(int size,int seed){
      Random rand = new Random(seed);
      List<Comparable> x = new ArrayList<Comparable>();
      for(int i = 0; i < size; i++){
          x.add(rand.nextInt(10000));
      }
      return x;
  }

  public List<Comparable> subList(List<Comparable> arr,int start, int end){
      List<Comparable> x = new ArrayList<Comparable>();
      for(int i = start; i < end; i++){
          x.add(arr.get(i));
      }
      return x;
  }


  public List<Comparable> mergeLists(List<Comparable> ar1,List<Comparable> ar2){
      List<Comparable> ar3 = new ArrayList<Comparable>();
      int index1 = 0,
          index2 = 0;
      while(index1 < ar1.size() && index2 < ar2.size()){
          if(ar1.get(index1).compareTo(ar2.get(index2)) < 0){
              ar3.add(ar1.get(index1));
              index1++;
          } else if(ar2.get(index2).compareTo(ar1.get(index1)) < 1){
             ar3.add(ar2.get(index2));
             index2++;
          }
      }
      while(index1 < ar1.size()){
          ar3.add(ar1.get(index1));
          index1++;
      }
      while(index2 < ar2.size()){
          ar3.add(ar2.get(index2));
          index2++;
      }
      return ar3;
  }

  
  public List<Comparable> sort(List<Comparable> ar){
      if(ar.size() == 0){
          return null;
      } else if(ar.size() == 1){
          List<Comparable> x = new ArrayList<Comparable>();
          x.add(ar.get(0));
          return x;
      }
      List<Comparable> y = subList(ar, 0, ar.size() / 2);
      List<Comparable> z = subList(ar, ar.size()/2, ar.size());
      y = sort(y);
      z = sort(z);
      return mergeLists(y, z);
  } 
} 
package lab06;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.lang.reflect.Field;

public class MTester {
  static String mstr(int[] ar1,int[] ar2,int[] result) {
    return "mergeArrays("+Arrays.toString(ar1)+", "+Arrays.toString(ar2)+")="+Arrays.toString(result);
  }
  public static void main(String[] args) throws Exception {
    try {
      assert(false);
      throw new Error("Assertions are not enabled");
    } catch (AssertionError ae) {}
    assert args.length >= 1 : "You must specify which implementation you are testing";
    Class<?> c = Class.forName(args[0]);

    for(Field f : c.getDeclaredFields()) {
      assert false : "There should be no fields in this class";
    }

    for(int ntries=0;ntries<10;ntries++) {
      Sorter s = (Sorter)c.getDeclaredConstructor().newInstance();
      Random r = new Random();
      int seed = r.nextInt();
      int fac = 5+2*ntries;
      int len = r.nextInt(fac)+fac;
      r.setSeed(seed);
      int[] gen = s.generateRandArray(len,seed);
      assert gen != null : "generateRandArray() returned null";
      assert gen.length == len : "generateRandArray() returned an array with the wrong size";
      for(int i=0;i<gen.length;i++) {
        assert gen[i] == r.nextInt(10000) : "generateRandArray() returned an array with the wrong data";
      }

      int clen = 20+ntries;
      int[] c1 = s.generateRandArray(clen,ntries);
      int[] c2 = s.generateRandArray(clen,ntries);
      assert c1.length == clen && c2.length == clen : "generateRandArray() return an array with the wrong size";
      for(int i=0;i<c1.length;i++) {
        assert c1[i] == c2[i] : "generateRandArray() does not appear to be using the seed";
      }
      for(int iter=0;iter<10;iter++) {
        int i1 = r.nextInt(clen);
        int i2 = r.nextInt(clen);
        if(i1 == i2) continue;
        if(i1 > i2) {
            int it = i1;
            i1 = i2;
            i2 = it;
        }
        assert i1 < i2;
        int[] q = s.subArray(c1,i1,i2);
        assert q.length == i2 - i1 : "subArray() returned an array of the wrong size";
        for(int i=i1;i<i2;i++)
            assert q[i-i1] == c2[i] : "subArray() returned the wrong data";
        for(int i=0;i<c1.length;i++)
            assert c1[i] == c2[i] : "subArray() modified its input. It should not.";
      }

      HashMap<Integer,Integer> map = new HashMap<>();
      int[] gen2 = s.generateRandArray(len+1,seed+1);
      Arrays.sort(gen);
      Arrays.sort(gen2);
      int[] merged = s.mergeArrays(gen,gen2);
      String ms = mstr(gen,gen2,merged);
      assert merged != null : "mergeArrays() returned a null";
      assert merged.length == gen.length+gen2.length : "mergeArrays() returned an array with the wrong size";
      for(int i=0;i<merged.length-1;i++) 
        assert merged[i] <= merged[i+1] : "The mergeArrays() routine put a large element ahead of a small one.";
      for(int i=0;i<merged.length;i++) {
        Integer count = map.get(merged[i]);
        if(count == null)
          map.put(merged[i],1);
        else
          map.put(merged[i],count+1);
      }
      for(int i=0;i<gen.length;i++) {
        assert map.get(gen[i]) != null : "The mergeArrays() routine lost one of the values from ar1 : "+ms;
        Integer count = map.get(gen[i]);
        map.put(gen[i],count-1);
      }
      for(int i=0;i<gen2.length;i++) {
        assert map.get(gen2[i]) != null : "The mergeArrays() routine lost one of the values from ar2 : "+ms;
        Integer count = map.get(gen2[i]);
        map.put(gen2[i],count-1);
      }
      for(Map.Entry<Integer,Integer> e : map.entrySet()) {
        assert e.getValue() <= 0 : "The mergeArrays() result contains too many "+e.getValue()+"'s : "+ms;
        assert e.getValue() >= 0 : "The mergeArrays() result contains too few "+e.getValue()+"'s : "+ms;
      }

      r.setSeed(seed);
      gen = s.generateRandArray(2*len+1,seed);
      ms = Arrays.toString(gen);
      map.clear();
      for(int i=0;i<gen.length;i++) {
        Integer count = map.get(gen[i]);
        if(count == null)
          map.put(gen[i],1);
        else
          map.put(gen[i],count+1);
      }
      int[] sorted = s.sort(gen);
      for(int i=0;i<sorted.length;i++) {
        Integer count = map.get(sorted[i]);
        assert count != null : "The value "+sorted[i]+" appears in sorted but wasn't in "+ms;
        map.put(sorted[i],count-1);
      }
      for(Map.Entry<Integer,Integer> e : map.entrySet()) {
        assert e.getValue() <= 0 : "The sort() result contains too many "+e.getValue()+"'s : "+ms;
        assert e.getValue() >= 0 : "The sort() result contains too few "+e.getValue()+"'s : "+ms;
      }
      assert sorted != null : "sort() returns a null array";
      assert sorted.length == gen.length : "sort() returns an array with the wrong size";
      for(int i=0;i<sorted.length-1;i++) {
        assert sorted[i] <= sorted[i+1] : "sort() returns an unsorted array "+Arrays.toString(sorted);
      }
    }
    System.out.println("All tests passed");
  }
}
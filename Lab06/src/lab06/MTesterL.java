package lab06;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.Field;

public class MTesterL {

  static SorterL newInstance(Class<?> c) throws Exception {
    return (SorterL)c.getDeclaredConstructor().newInstance();
  }

  static boolean equals(Object o1,Object o2) {
    if(o1 == null && o2 == null) return true;
    if(o1 == null || o2 == null) return false;
    return o1.equals(o2);
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

    SorterL s = newInstance(c);
    for(int ntries=0;ntries<10;ntries++) {
      Random r = new Random();
      int seed = r.nextInt();
      int fac = 5+2*ntries;
      int len = r.nextInt(fac)+fac;
      r.setSeed(seed);
      List<Comparable> gen = s.generateRandList(len,seed);
      assert gen != null : "generateRandList() returned null";
      assert gen.size() == len : "generateRandList() returned an array with the wrong size";
      for(int i=0;i<gen.size();i++) {
        assert equals(gen.get(i),r.nextInt(10000)) : "generateRandList() returned an array with the wrong data";
      }


      int clen = 20+ntries;
      List<Comparable> c1 = s.generateRandList(clen,ntries);
      List<Comparable> c2 = s.generateRandList(clen,ntries);
      assert c1.size() == clen && c2.size() == clen : "generateRandList() return an array with the wrong size";
      for(int i=0;i<c1.size();i++) {
        assert equals(c1.get(i),c2.get(i)) : "generateRandList() does not appear to be using the seed";
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
        List<Comparable> q = s.subList(c1,i1,i2);
        assert q != null : "subList() returned null";
        assert q.size() == i2 - i1 : "subList() returned an array of the wrong size";
        for(int i=i1;i<i2;i++)
            assert equals(q.get(i-i1),c2.get(i)) : "subList() returned the wrong data";
        for(int i=0;i<c1.size();i++)
            assert equals(c1.get(i),c2.get(i)) : "subList() modified its input. It should not.";
      }

      HashMap<Comparable,Integer> map = new HashMap<>();
      List<Comparable> gen2 = s.generateRandList(len+1,seed+1);
      Collections.sort(gen);
      Collections.sort(gen2);
      List<Comparable> merged = s.mergeLists(gen,gen2);
      String ms = "mergeLists("+gen+", "+gen2+") = "+merged;
      assert merged != null : "mergeLists() returned a null";
      assert merged.size() == gen.size()+gen2.size() : "mergeLists() returned an array with the wrong size";
      for(int i=0;i<merged.size()-1;i++) 
        assert merged.get(i).compareTo(merged.get(i+1)) <= 0 : "The mergeLists() routine put a large element ahead of a small one.";
      for(int i=0;i<merged.size();i++) {
        Integer count = map.get(merged.get(i));
        if(count == null)
          map.put(merged.get(i),1);
        else
          map.put(merged.get(i),count+1);
      }
      for(int i=0;i<gen.size();i++) {
        assert map.get(gen.get(i)) != null : "The mergeLists() routine lost one of the values from ar1 : "+ms;
        Integer count = map.get(gen.get(i));
        map.put(gen.get(i),count-1);
      }
      for(int i=0;i<gen2.size();i++) {
        assert map.get(gen2.get(i)) != null : "The mergeLists() routine lost one of the values from ar2 : "+ms;
        Integer count = map.get(gen2.get(i));
        map.put(gen2.get(i),count-1);
      }
      for(Map.Entry<Comparable,Integer> e : map.entrySet()) {
        assert e.getValue() <= 0 : "The mergeLists() result contains too many "+e.getValue()+"'s : "+ms;
        assert e.getValue() >= 0 : "The mergeLists() result contains too few "+e.getValue()+"'s : "+ms;
      }

      r.setSeed(seed);
      gen = s.generateRandList(2*len+1,seed);
      ms = gen.toString();
      map.clear();
      for(int i=0;i<gen.size();i++) {
        Integer count = map.get(gen.get(i));
        if(count == null)
          map.put(gen.get(i),1);
        else
          map.put(gen.get(i),count+1);
      }
      List<Comparable> sorted = s.sort(gen);
      assert sorted != null : "sort() returned null";
      for(int i=0;i<sorted.size();i++) {
        Integer count = map.get(sorted.get(i));
        assert count != null : "The value "+sorted.get(i)+" appears in sorted but wasn't in "+ms;
        map.put(sorted.get(i),count-1);
      }
      for(Map.Entry<Comparable,Integer> e : map.entrySet()) {
        assert e.getValue() <= 0 : "The sort() result contains too many "+e.getValue()+"'s : "+ms;
        assert e.getValue() >= 0 : "The sort() result contains too few "+e.getValue()+"'s : "+ms;
      }
      assert sorted != null : "sort() returns a null array";
      assert sorted.size() == gen.size() : "sort() returns an array with the wrong size";
      for(int i=0;i<sorted.size()-1;i++) {
        assert sorted.get(i).compareTo(sorted.get(i+1)) <= 0 : "sort() returns an unsorted array "+sorted;
      }
    }
    Random r = new Random();
    String[] noun = new String[]{"Apple","Alien","Car","Airplane","Mushroom","Elevator","Boron"};
    List<Comparable> strings = new ArrayList<>();
    for(int i=0;i<100;i++) {
        String string = noun[r.nextInt(noun.length)] + r.nextInt(100) + noun[r.nextInt(noun.length)];
        strings.add(string);
    }
    List<Comparable> sortedStrings = s.sort(strings);
    assert strings.size() == sortedStrings.size() : "sort() gives the wrong size for strings";
    for(int i=1;i<sortedStrings.size();i++) {
        assert sortedStrings.get(i-1).compareTo(sortedStrings.get(i)) <= 0;
    }
    System.out.println("All tests passed");
  }
}
package lab05;

import java.util.*;

public class RecursionTester {
    public final static Random RAND = new Random();
    public static void testPermute(Object o) {
        Permute p = (Permute)o;
        int f = 1;
        for(int i=1;i<10;i++) {
            f *= i;
            assert p.fac(i) == f;
        }
        int r = RAND.nextInt(3)+3;
        int[] seq = p.sequence(r);
        assert seq.length == r: String.format("sequence returned a result of length %d instead of %d", seq.length, r);
        for(int i=0;i<seq.length;i++) {
            assert seq[i] == i : "Bad implementation of Permute.sequence()";
        }
        int v = RAND.nextInt(seq.length);
        int[] seq2 = p.remove(seq,v);
        assert seq2.length == seq.length - 1 : "The remove function should create an array one smaller";
        int j = 0;
        for(int i=0;i<seq.length;i++) {
            if(i != v)
                assert seq[i] == seq2[j++] : "Bad implementation of Permute.remove";
        }
        int val = RAND.nextInt(100);
        int[] seq3 = p.append(seq2,val);
        assert seq3.length == seq2.length + 1 : "The append function should create an array one bigger";
        for(int i=0;i<seq2.length;i++)
            assert seq2[i] == seq3[i] : "Bad implementation of Permute.append";
        assert seq3[seq2.length] == val : "Bad implementation of Permute.append";
        int[][] perm = p.permutation(seq);
        assert fac(r) == perm.length : "Wrong number of arrays in permutation result";
        for(int i=0;i<perm.length;i++) {
            assert perm[i].length == r : "Wrong size array in permutation result";
            Set<Integer> set = new TreeSet<>();
            for(j=0;j<perm[i].length;j++) {
                set.add(perm[i][j]);
            }
            assert set.size()==r : "Repeated data in permutation";
            j=0;
            for(Integer is : set) {
                assert is == j++ : "Extra element in sequence";
            }
            for(j=i+1;j<perm.length;j++)
                assert !equal(perm[i],perm[j]) : "Repeated array in permutation result";
        }
    }
    static boolean equal(int[] a,int[] b) {
        if(a.length != b.length) return false;
        for(int i=0;i<a.length;i++)
            if(a[i] != b[i])
                return false;
        return true;
    }
    static int fac(int n) {
        if(n<=1) return 1;
        return n*fac(n-1);
    }
    public static void testPermuteAL(Object o) {
        PermuteAL p = (PermuteAL)o;
        int r = RAND.nextInt(3)+3;
        ArrayList<Integer> seq = p.sequence(r);
        for(int i=0;i<seq.size();i++) {
            assert seq.get(i) == i : "Bad implementation of PermuteAL.sequence()";
        }
        ArrayList<ArrayList<Integer>> perm = p.permutation(seq);
        assert fac(r) == perm.size() : "Wrong number of arrays in permutation result";
            int j=0;
            for(int i=0;i<perm.size();i++) {
                assert perm.get(i).size() == r : "Wrong size array in permutation result";
                Set<Integer> set = new TreeSet<>();
                for(j=0;j<perm.get(i).size();j++) {
                    set.add(perm.get(i).get(j));
                }
                assert set.size()==r : "Repeated data in permutation";
                j=0;
                for(Integer is : set) {
                    assert is == j++ : "Extra element in sequence";
                }
                for(j=i+1;j<perm.size();j++)
                    assert !perm.get(i).equals(perm.get(j)) : "Repeated array in permutation result";
            }
    }
    public static void test(Object o) {
        if(o instanceof Permute)
            testPermute(o);
        else if(o instanceof PermuteAL)
            testPermuteAL(o);
        else
            throw new Error("class "+o.getClass().getName()+" implements neither Permute nor PermuteAL");
    }
    public static void main(String[] args) throws Exception {
        try {
            assert(false);
            throw new Error("Assertions are not enabled");
        } catch(AssertionError ae) {
        }
        assert args.length >= 1 : "You must specify which implementation you are testing";
        Object o = Class.forName(args[0]).getDeclaredConstructor().newInstance();
        test(o);
        System.out.println("Tests passed for class 1/2: " + o.getClass().getName());
        assert args.length == 2 : "You need to supply both the implementation for Permute and PermuteAL";
        Object o2 = Class.forName(args[1]).getDeclaredConstructor().newInstance();
        if(o instanceof Permute && o2 instanceof Permute)
            throw new Error("You must provide an implementation for both Permute and PermuteAL");
        if(o instanceof PermuteAL && o2 instanceof PermuteAL)
            throw new Error("You must provide an implementation for both Permute and PermuteAL");
        test(o2);
        System.out.println("Tests passed for class 2/2: " + o2.getClass().getName());
        System.out.println("All tests passed!");
    }
}
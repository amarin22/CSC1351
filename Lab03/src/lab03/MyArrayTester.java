package lab03;

import java.util.*;
import java.lang.reflect.*;

public class MyArrayTester {
    public static void assertString(String fmt,String a,String b) {
        if(a == null) a = "null";
        if(b == null) b = "null";
        assert a.equals(b) : String.format(fmt+": %s != %s", a, b);
    }
    public static void main(String[] args) throws Exception {
        try {
            assert false;
            throw new Error("Assertions need to be enabled.");
        } catch(AssertionError ae) {}
        Random rand = new Random();

        assert args.length == 1 : "You have to give the name of your class as argument";
        Class<?> c = Class.forName(args[0]);
        String cn = c.getName();

        final Class<?> STRING_CLASS = new String[0].getClass();
        boolean found_int = false;
        Field arrayField = null;
        for(Field f : c.getDeclaredFields()) {
            if(f.getName().startsWith("$")) continue;
            assert !List.class.isAssignableFrom(f.getType()) : "You may not use a List in your implementation";
            int mods = f.getModifiers();
            assert !Modifier.isStatic(mods) : "You should not use static fields";
            if(f.getType() == Integer.TYPE || f.getType() == Integer.class) {
                found_int = true;
            } else if(f.getType() == STRING_CLASS) {
                assert arrayField == null : "You only need one String array in your class. You appear to have more.";
                arrayField = f;
                arrayField.setAccessible(true);
            }
        }

        try {
            TestingHarness.verbose = true;
            TestingHarness th1 = new TestingHarness(cn+"()");

            TestingHarness.MethodDesc size = th1.getMethod("public int size()");
            TestingHarness.assertEq("size() of newly generated array not 0",0,size.call(th1));

            TestingHarness.MethodDesc get = th1.getMethod("public String get(int)");
            TestingHarness.assertEq("get(0) on empty array does not return null",null,get.call(th1,0));

            ArrayList<String> al = new ArrayList<>(Arrays.asList("Crater","Meteor","Rock","Martian","Phobos","Deimos","Regolith","Red"));
            Collections.shuffle(al);
            String s1 = al.get(0);
            String s2 = al.get(1);
            String s3 = al.get(2);

            TestingHarness.MethodDesc append = th1.getMethod("public void append(String)");
            for(int i=0;i<al.size();i++) {
                String s = al.get(i);
                append.call(th1,s);
                TestingHarness.assertEq("append() or size() is not working",i+1,size.call(th1));
                TestingHarness.assertEq("append() or get() is not working",s,get.call(th1,i));
            }

            th1 = new TestingHarness(cn+"()");
            TestingHarness.assertEq("The toString() method is incorrect","{}", th1.toString());

            TestingHarness.MethodDesc insert = th1.getMethod("public int insert(int,String)");
            insert.call(th1,0,s1);
            TestingHarness.assertEq("get(0) returns incorrect value",s1,get.call(th1,0));
            insert.call(th1,1,s2);
            TestingHarness.assertEq("get(0) returns incorrect value",s1,get.call(th1,0));
            TestingHarness.assertEq("get(1) returns incorrect value",s2,get.call(th1,1));
            insert.call(th1,0,s3);
            TestingHarness.assertEq("get(0) returns incorrect value",s3,get.call(th1,0));
            TestingHarness.assertEq("get(1) returns incorrect value",s1,get.call(th1,1));
            TestingHarness.assertEq("get(2) returns incorrect value",s2,get.call(th1,2));

            TestingHarness.assertEq("The toString() method is incorrect", "{"+s3+", "+s1+", "+s2+"}", th1.toString());
            TestingHarness.MethodDesc set = th1.getMethod("public void set(int,String)");
            for(int i=0;i<3;i++) {
                Object str1 = get.call(th1,i);
                String str2 = al.get(i+3);
                assert !TestingHarness.eq(str1,str2);
                set.call(th1, i, str2);
                TestingHarness.assertEq("The set() method is incorrect",str2,get.call(th1,i));
            }
            for(int i=0;i<3;i++) {
                TestingHarness.assertEq("The set() method is incorrect",al.get(i+3),get.call(th1,i));
            }
            for(int i=3;i+3<al.size();i++) {
                String s = (String)al.get(i+3);
                append.call(th1, s);
                TestingHarness.assertEq("size() or append() is incorrect",i+1,size.call(th1));
            }
            TestingHarness th4 = new TestingHarness(cn+"()");
            List<String> mirror = new ArrayList<>();
            int initSize = ((String[])arrayField.get(th4.getInstance())).length;
            int lastSize = initSize;
            boolean growthDetected = false;
            boolean adequateGrowthDetected = false;
            for(int i=0;i<10+2*initSize;i++) {
                String s = al.get(rand.nextInt(al.size()))+rand.nextInt(10000);
                mirror.add(s);
                append.call(th4, s);
                int tsize = ((String[])arrayField.get(th4.getInstance())).length;
                assert tsize >= i : "Your String array needs to be reallocated when enough strings are appended.";
                if(lastSize+1 < tsize) {
                    adequateGrowthDetected = true;
                }
                lastSize = tsize;
            }
            int finalSize = ((String[])arrayField.get(th4.getInstance())).length;
            assert adequateGrowthDetected : "Your array shouldn't need to reallocate every time something's appended";
            assert initSize > 0 : "Your String[] array should not be empty";
            assert finalSize > initSize : "Your string array needs to be reallocated when enough strings are appended";
            for(int i=0;i<mirror.size();i++) {
                TestingHarness.assertEq("Something is wrong with your append method",mirror.get(i), get.call(th4, i));
            }
            TestingHarness.assertEq("Something is wrong with your append or size method", mirror.size(), size.call(th4));

            int n = (Integer)size.call(th1);

            TestingHarness.MethodDesc remove1 = th1.getMethod("public String remove(int)");
            for(int i=0;i<n;i++) {
                Object str = remove1.call(th1,i);
                TestingHarness.assertEq("The remove(int) problem is incorrect",al.get(i+3),str);
                insert.call(th1,i,str);
            }

            TestingHarness.MethodDesc remove2 = th1.getMethod("public int remove(String)");
            TestingHarness.assertEq("THe remove(String) method is incorrect",-1,remove2.call(th1,al.get(0)));
            for(int i=0;i<n;i++) {
                String str = al.get(i+3);
                Object index = remove2.call(th1, str);
                TestingHarness.assertEq("The remove(int) method is incorrect",i,index);
                insert.call(th1,i,str);
            }
            TestingHarness.assertEq("The remove(String) method is incorrect",-1,remove2.call(th1,al.get(1)));

            assert MyArray.class.isAssignableFrom(th1.getClazz()) : cn+" does not implement MyArray";

            TestingHarness th2 = new TestingHarness(String.format("public %s(%s)",cn,MyArray.class.getName()),th1.getInstance());
            String save = th1.toString();
            TestingHarness.assertEq("The (MyArray) constructor is incorrect",save,th2.toString());

            for(int i=0;i<10;i++) {
                int index = rand.nextInt(n);
                int i2 = rand.nextInt(3);
                set.call(th1,i,al.get(i2));
                TestingHarness.assertEq("The (MyArray) constructor is incorrect",save,th2.toString());
            }

            for(int i=0;i<100;i++) {
                int n1 = rand.nextInt(n);
                int n2 = rand.nextInt(n);
                if(n1 >= n2) continue;
                String ctor = String.format("public %s(%s,int,int)",cn,MyArray.class.getName());
                TestingHarness th3 = new TestingHarness(ctor,th2.getInstance(),n1,n2);
                TestingHarness.assertEq("problem with "+ctor,n2-n1,size.call(th3));
                for(int j=n1;j<n2;j++) {
                    TestingHarness.assertEq("problem with "+ctor,get.call(th2,j),get.call(th3,j-n1));
                }
            }
        } catch (Error e) { // TestingHarness wraps ITE in Error
            Throwable maybeITE = e.getCause();
            if (maybeITE instanceof InvocationTargetException) {
                Throwable cause = maybeITE.getCause();
                if (cause instanceof Exception) {
                    throw (Exception) cause;
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }

        System.out.println("All tests passed.");
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab03;

/**
 *
 * @author alexm
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author steve
 */
public class TestingHarness {

    String pkg;
    private Object instance;
    
    public Object getInstance() {
        return instance;
    }
    public Class<?> getClazz() {
        return instance.getClass();
    }
    
    static class MethodDesc {
        String name;
        Class ret;
        Class[] args;
        Method m;
        public Object call(TestingHarness th, Object... args) {
            assert th.instance != null;
            assert m != null;
            try {
                return m.invoke(th.instance, args);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }
    
    Class<?> getType(String name) {
        if(name.endsWith("[]")) {
            name = name.substring(0,name.length()-2);
            Class<?> clazz = getType(name);
            Object arr = Array.newInstance(clazz, 0);
            return arr.getClass();
        }
        if("int".equals(name))
            return Integer.TYPE;
        if("void".equals(name))
            return Void.TYPE;
        if("short".equals(name))
            return Short.TYPE;
        if("long".equals(name))
            return Long.TYPE;
        if("byte".equals(name))
            return Byte.TYPE;
        if("char".equals(name))
            return Character.TYPE;
        if("double".equals(name))
            return Double.TYPE;
        if("float".equals(name))
            return Float.TYPE;
        if("boolean".equals(name))
            return Boolean.TYPE;
        if("String".equals(name))
            return String.class;
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            System.err.println("This class does not exist: "+name);
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    final static Pattern pat = Pattern.compile("(?:(public|private|protected)\\s+|)(\\S+)\\s+([\\w\\[\\]]+)\\s*\\((.*)\\)");
    
    String[] getSplit(String s) {
        if("".equals(s)) return new String[0];
        return s.split(",");
    }

    public MethodDesc getMethod(String method) {
        Matcher m = pat.matcher(method);
        m.matches();
        MethodDesc md = new MethodDesc();
        String access = m.group(1);
        md.ret = getType(m.group(2));
        md.name = m.group(3);
        String[] args = getSplit(m.group(4));
        md.args = new Class<?>[args.length];
        for (int i = 0; i < md.args.length; i++) {
            md.args[i] = getType(args[i]);
        }
        try {
            md.m = clazz.getDeclaredMethod(md.name, md.args);
        } catch (Exception ex) {
            raise("Failed to find a method that matches: "+method,ex);
        }
        assert md.m.getReturnType().equals(md.ret) :
            "Found method '"+method+"', but the return type was incorrect.";
        if(access != null) {
            int mods = md.m.getModifiers();
            String errStr = "Method '" + method + "' has wrong access. It should be " + access;
            if ("public".equals(access)) {
                assert Modifier.isPublic(mods) : errStr;
            }
            if ("private".equals(access)) {
                assert Modifier.isPrivate(mods) : errStr;
            }
            if ("protected".equals(access)) {
                assert Modifier.isProtected(mods) : errStr;
            }
        }
        md.m.setAccessible(true);

        return md;
    }
    
    Class<?> clazz;
    
    public TestingHarness(String ctor,Object... callArgs) {
        init(ctor,callArgs);
    }
    
    void init(String ctor,Object... callArgs) {
        Pattern cpat = Pattern.compile("(?:(public|private|protected)\\s+|)(\\S+)\\((.*)\\)");
        Matcher m = cpat.matcher(ctor);
        if(!m.matches())
            throw new Error("Bad ctor syntax");
        String access = m.group(1);
        String className = m.group(2);
        String[] args = getSplit(m.group(3));
        Class<?>[] cargs = new Class<?>[args.length];
        
        for(int i=0;i<args.length;i++)
            cargs[i] = getType(args[i]);
        
        // If the className is "?" we will search for the class in
        // the same directory as the TestingHarness
        if("?".equals(className)) {
            // Java returns file names with %20 in them. For some reason, this
            // breakes File.listFiles()
            String path = getClass().getResource(".").getPath().replace("%20", " ");
            File fpath = new File(path);
            File[] currDir = fpath.listFiles();
            assert currDir != null;
            Package cpkg = getClass().getPackage();
            pkg = cpkg == null ? "" : cpkg.getName()+".";
            for(File f : currDir) {
                String fname = f.getName();
                fname = fname.substring(0,fname.length()-".class".length());

                String cname = pkg + fname;
                try {
                    init2(access, cname, cargs, ctor, callArgs);
                    return;
                } catch(RuntimeException re) {
                    continue;
                }
            }
        }
        init2(access, className, cargs, ctor, callArgs);
    }
    void init2(String access, String className, Class[] cargs,String ctor, Object[] callArgs) {
        try {
            clazz = Class.forName(className);
        } catch(Exception e) {
            raise("Could not find class: "+className,e);
        }
        Constructor ctorm = null;
        StringBuilder sb = new StringBuilder();
        try {
            ctorm = clazz.getDeclaredConstructor(cargs);
            sb.append("find( ");
            for(int i=0;i<cargs.length;i++)
                sb.append(cargs[i].getName()+" ");
            sb.append(") call( ");
            for(int i=0;i<callArgs.length;i++)
                if(callArgs[i] == null) sb.append("null ");
                else sb.append(callArgs[i].getClass().getName()+" ");
            sb.append(")");
            ctorm.setAccessible(true);
            instance = ctorm.newInstance(callArgs);
        } catch(Exception e) {
            raise("Constructor issue: "+ctor+" "+sb.toString(),e);
        }
        if(access != null) {
            int mods = ctorm.getModifiers();
            String errStr = "Constructor '" + ctor + "' has wrong access. It should be " + access;
            if ("public".equals(access)) {
                assert Modifier.isPublic(mods) : errStr;
            }
            if ("private".equals(access)) {
                assert Modifier.isPrivate(mods) : errStr;
            }
            if ("protected".equals(access)) {
                assert Modifier.isProtected(mods) : errStr;
            }
        }
    }
    
    public static boolean verbose = false;
    
    void raise(String msg,Exception e) {
        if(verbose) throw new RuntimeException(msg, e);
        else throw new RuntimeException(msg);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestingHarness th = new TestingHarness("public testingharness.Foo(int)",3);
        MethodDesc md = th.getMethod("void bar(String)");
        md.call(th,"Hello");
        
        th = new TestingHarness("testingharness.Foo()");
        md = th.getMethod("int add(int,int)");
        System.out.println("add="+md.call(th,3,4));
        
        th = new TestingHarness("?()");
        md = th.getMethod("public void bar(String)");
        md.call(th,"Done");
        
        //th.getMethod("void dazzle()");
    }

    public static void assertEq(String fmt, Object a, Object b) {
        assert eq(a,b) : String.format(fmt+": %s != %s",fmt(a),fmt(b));
    }
    private static String fmt(Object a) {
        if(a == null) return "null";
        else return a.toString();
    }
    public static boolean eq(Object a, Object b) {
        if(a == null && b == null) return true;
        if(a == null || b == null) return false;
        if(a.getClass().isAssignableFrom(b.getClass())) {
            return b.equals(a);
        } else if(b.getClass().isAssignableFrom(a.getClass())) {
            return a.equals(b);
        } else {
            return a.equals(b) && b.equals(a);
        }
    }

    public Object call(String method,Object... args) {
        return getMethod(method).call(this,args);
    }

    public String toString() {
        return instance.toString();
    }
}
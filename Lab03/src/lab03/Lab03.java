/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab03;

/**
 *
 * @author alexm
 */
public class Lab03 implements MyArray{

    /**
     * @param args the command line arguments
     */
    
    /**
     * 
    * In addition to the interface below, the following constructors
    * are expected:
    *
    * 1: no arguments, creates empty MyArray
    * 2: one argument: MyArray mya, copies the input data from mya
    * 3: three arguments: Myarray mya, int start, int end
    *                     creates MyArray from data in mya 
    *                     range start (including) to end (excluding).
    *                     creates an empty array if the start or end
    *                     positions are invalid (i.e. don't make any
    *                     sense).
    **/
    private int size;
    private String[] x;
    
    public Lab03(){
        size = 0;
        x = new String[10];
    }
    
    public Lab03(MyArray mya){
        size = mya.size();
        x = new String[size];
        for(int i = 0; i < size; i++)
            x[i] = mya.get(i);
    }
    
    public Lab03(MyArray mya, int start, int end){
        if(start > 0 || end < mya.size()){
            size = end - start;
            x = new String[size];
            for(int i = start; i < end; i ++){
                x[i] = mya.get(i + start);
            }
        } else x = new String[0];
    }
    
    /*
     * Return a string-representation of the array that resembles the usual
     * array output, e.g.,:
     * "{}"         : for an empty array
     * "{something}": for an array with one element "something"
     * "{one, two}" : for an array with two elements "one" and "two"
     */
    @Override
    public String toString(){
        String str = "{";
        if(size > 1){
            int y = -1;
            for(int i = 0; i < size; i++){
                if(y >= 0)
                    str += ", ";
                str += x[i];
                y = i;
            }
        }
        return str + "}";
    }
    
    /*
    * Return current number of elements in array
    */
    @Override
    public int size() {
        return size;
    }

    /*
    * Set specific element in array. If element does not exist, do nothing.
    */
    @Override
    public void set(int pos, String str) {
        if(pos < size)
            x[pos] = str;
    }

    /*
    * Return the value of element at position 'pos'. Returns null if this element
    * does not exist.
    */
    @Override
    public String get(int pos) {
        String str = "";
        if(x.length >= 0 && pos < size){
           str = x[pos];
           return str;
        }else return null;        
    }

    /*
    * Return position (index) of first element in array with value 'str'. Return -1
    * if it does not exist.
    */
    @Override
    public int get(String str) {
        int a = 0;
        if(x.length != 0){
          for(int i = 0; i < size; i++){
              if(x[i].equals(str)){
                    a = i;
                    return a;
              } else a = -1;
           }
        }
        return a;
    }

    /*
    * Return whether an element with value 'str' exists in array.
    */
    @Override
    public boolean contains(String str) {
        boolean y = false;
        for(int i = 0; i < size; i++){
            if(x[i].equals(str))
                y = true;
        }
        return y;
    }

    /*
    * Append one element to the array (at the end).
    */
    @Override
    public void append(String str) {
          if(size >= x.length){
              String[] y = new String[x.length + 10];
              for(int i = 0; i < size; i++)
                  y[i] = x[i];
              y[size + 1] = str;
              x = y;
              size++;
          } else
          x[size] = str;
          size++;
    }

    /*
    * Insert element with value 'str' at position 'pos', leaving the order of the
    * other elements intact. Return the index at which it was inserted.
    * Only accept pos in range [0 ... size], return -1 otherwise.
    */
    @Override
    public int insert(int pos, String str) {
        if(pos >= 0 || pos < size){
          if(size >= x.length){
               String[] y = new String[x.length + 10];
               size++;
               for(int i = 0; i < pos; i++)
                     y[i] = x[i];
                 x = y;
             } 
               String b = x[pos];
               for(int i = pos + 1; i <= size; i++){
                   String temp = b;
                   b = x[i];
                   x[i] = temp;
               }
               x[pos] = str;
               return pos;
        } return -1;
    }

    /*
    * Remove element at position 'pos' from array. Don't do anything if this
    * element does not exist (also do not fail). Return the value that was removed.
    */
    @Override
    public String remove(int pos) {
        String str = null;
        if(pos < size || pos > 0){
            str = x[pos];
            for(int i = pos; i < size - 1; i++)
                x[i] = x[i + 1];
            size--;
        }
        return str;
    }

    /* Remove the first element with value 'str' from array. Return the index
    * of that element. Return -1 if no such element existed.
    */
    @Override
    public int remove(String str) {
        int z = 0;
        for(int i = 0; i < size; i++){
            if(x[i].equals(str)){
                remove(i);
                z = i;
                return z;
            }
        }
        z = -1;
        return z;
    }

    public static void main(String[] args) {
        
    }
}
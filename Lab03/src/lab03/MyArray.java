/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab03;

/**
 *
 * @author alexm
 */
/**
 * This interface is part of a utility library used in your
 * company to handle arrays. It is similar to ArrayLists, but
 * an implementation *must not use* ArrayLists, nor must it use
 * the 'Arrays' package, or any other Java package that helps
 * dealing with arrays. This is the one time you have to implement
 * this by yourself.
 * Your class only has to handle arrays of type "String", and it
 * must grow and shrinks dynamically.
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
public interface MyArray {
  /*
   * Return a string-representation of the array that resembles the usual
   * array output, e.g.,:
   * "{}"         : for an empty array
   * "{something}": for an array with one element "something"
   * "{one, two}" : for an array with two elements "one" and "two"
   */
  String toString();

  /*
   * Return current number of elements in array
   */
  int size();

  /*
   * Set specific element in array. If element does not exist, do nothing.
   */
  void set(int pos, String str);

  /*
   * Return the value of element at position 'pos'. Returns null if this element
   * does not exist.
   */
  String get(int pos);

  /*
   * Return position (index) of first element in array with value 'str'. Return -1
   * if it does not exist.
   */
  int get(String str);

  /*
   * Return whether an element with value 'str' exists in array.
   */
  boolean contains(String str);

  /*
   * Append one element to the array (at the end).
   */
  void append(String str);

  /*
   * Insert element with value 'str' at position 'pos', leaving the order of the
   * other elements intact. Return the index at which it was inserted.
   * Only accept pos in range [0 ... size], return -1 otherwise.
   */
  int insert(int pos, String str);

  /*
   * Remove element at position 'pos' from array. Don't do anything if this
   * element does not exist (also do not fail). Return the value that was removed.
   */
  String remove(int pos);

  /* Remove the first element with value 'str' from array. Return the index
   * of that element. Return -1 if no such element existed.
   */
  int remove(String str);
}
package lab06;

import java.util.List;

public interface SorterL {
  /**
   * Generate a list of random numbers.
   * Use the seed to set the seed of the
   * java.util.Random class, and generate
   * a list of length size. Random numbers
   * should be between 0 and 9999 inclusive.
   */
  List<Comparable> generateRandList(int size,int seed);

  /**
   * Create a copy of a list subset.
   * Copy the values of array arr beginning
   * with start up to (but not including) end.
   * The number of values in the returned array
   * should be equal to (end-start).
   */
  List<Comparable> subList(List<Comparable> arr,int start, int end);

  /**
   * Given two lists, ar1, and ar2,
   * both of which are sorted, merge
   * the two of them into a single
   * sorted list. Example: [1,3,5] and
   * [2,4] merge to [1,2,3,4,5]. 
   */
  List<Comparable> mergeLists(List<Comparable> ar1,List<Comparable> ar2);

  /**
   * Create a new list with the same
   * values as ar, but sorted. It should
   * work as follows
   * 1) If the list is size 1 or less,
   *    return a copy of the list.
   * 2) Create two lists, each containing
   *    approximately half the values. If
   *    the list is odd-sized, one of the
   *    two halves will be 1 bigger than
   *    the other. Example: if ar=[1,5,3,2,4]
   *    then ar1=[1,5,3] and ar2=[2,4]. 
   * 3) Call sort on each of the two lists
   *    created in step two.
   * 4) Return the result of merging the
   *    two sorted lists from step 3.
   */
  List<Comparable> sort(List<Comparable> ar);
}